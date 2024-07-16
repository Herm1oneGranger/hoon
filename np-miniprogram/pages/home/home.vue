<template>
	<view class="wrapper">
		<view class="header">
			<image src="/static/images/logo.svg"></image>
		</view>

		<view class="main">
			<view class="sub-title">欢迎进入</view>
			<view class="title">力士乐防伪验证</view>

			<view class="btn" @click="handleScan">扫描防伪码 <uni-icons class="icon" color="#fff" type="scan"
					size="24"></uni-icons>
			</view>
		</view>

		<view class="line"></view>

		<image class="bg" src="/static/images/home-circle.png" mode=""></image>

		<Dialog ref="dialog"></Dialog>
		<UserInfo ref="userInfo"></UserInfo>
	</view>
</template>

<script>
	import UserInfo from '@/components/UserInfo'
	import Dialog from '@/components/Dialog'

	function getParameters(str) {
		if (str.split("?").length == 1) {
			return {}
		}

		return JSON.parse(
			`{"${decodeURI(str.split("?")[1]).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"')}"}`)
	}

	export default {
		components: {
			Dialog,
			UserInfo
		},
		data() {
			return {
				id: '',
				type: '',
				openId: ''
			};
		},



		methods: {
			async getUserToken(code) {
				try {
					const userToken = await this.$store.dispatch('home/getUserToken', {
						code
					})
					return userToken
				} catch (e) {
					console.log('getUserToken', e)
					uni.showToast({
						title: '获取Openid失败',
						icon: 'none',
						duration: 1000
					})
					throw (e)
				}

			},
			// 获取用户信息
			getUserInfo() {
				return new Promise((reslove, reject) => {
					const openId = uni.getStorageSync('openId')
					if (openId) {
						reslove({
							openId
						})
						return
					}
					uni.login({
						provider: 'weixin',
						success: async loginRes => {
							try {
								const userToken = await this.getUserToken(loginRes.code)

								uni.setStorage({
									key: 'openId',
									data: userToken.openid
								})
								reslove({
									openId: userToken.openid
								})
							} catch (e) {
								console.log("getUserInfo", e)
								reject(e)
								//TODO handle the exception
							}

						}
					})
				})
			},
			getphonenumber(e) {
				console.log(e)
			},
			async getSystemInfo() {
				return new Promise((relove, reject) => {
					uni.getSystemInfo({
						success: (res) => {
							relove(res.platform)
						}
					})
				})

			},
			getLocation() {
				return new Promise((reslove, reject) => {
					uni.getLocation({
						type: 'gcj02',
						success(res) {
							reslove(res)
						},
						fail(res) {
							console.log(res, '11')
							uni.authorize({
								scope: "scope.userLocation",
								success(res) {
									uni.getLocation({
										type: 'gcj02',
										success: (
											res
										) => {
											reslove
												(
													res
												)
										}
									})
								},
								fail: function(res) {
									uni.showModal({
										title: "提示",
										content: "请授权，否则无法扫描",
										showCancel: true,
										cancelText: "取消",
										confirmText: "去授权",
										success(res) {
											if (res.confirm) {
												uni.openSetting({
													success(res) {
														if (res.authSetting[
																"scope.userLocation"
															] == true) {
															uni.showToast({
																title: '授权成功',
																icon: 'success',
																duration: 1000
															})

															uni.getLocation({
																type: 'gcj02',
																success: (
																	res
																) => {
																	reslove
																		(
																			res
																		)
																}
															})

														} else {
															uni.showToast({
																title: '授权失败',
																icon: 'none',
																duration: 1000
															})
															reject(res)
														}
													}

												})
											}
										}
									})


								}
							})
						}
					})
				})
			},

			async transferLocation(location) {
				try {
					const transferLocation = await this.$store.dispatch('home/transferLocation', location)
					return transferLocation
				} catch (e) {
					console.log('transferLocation', e)
					uni.showToast({
						title: '地理坐标解析失败',
						icon: 'none',
						duration: 1000
					})
					throw (e)
				}

			},
			async handleScan() {
				const userInfo = await this.getUserInfo()

				const location = await this.getLocation()

				const transferLocation = await this.transferLocation(location)

				const platform = await this.getSystemInfo()

				uni.scanCode({
					scanType: ['qrCode'],
					onlyFromCamera: true,
					success: async (res) => {
						try {
							uni.showLoading({
								title: "校验中"
							})


							const params = getParameters(res.result)

							const options = {
								position: `${location.latitude},${location.longitude}`,
								province: transferLocation.address_component.province,
								city: transferLocation.address_component.city,
								mnr: params.m,
								fd: params.f,
								sn: params.s,
								appId: userInfo.openId,
								userPhoneOS: platform
							}

							if (res.result.indexOf("https://etrace.rexrothlive.cn") > -1) {
								uni.navigateTo({
									url: `/pages/enterInformation/enterInformation?info=${encodeURIComponent(JSON.stringify(options))}`
								})
								return
							}

							// http://spf.boschrexroth.com.cn/e_certificate?eid=E8WwVJyjWNHx6FhduV4Y@v1.0

							if (res.result.indexOf("http://spf.boschrexroth.com.cn/e_certificate?eid=") > -
								1) {
								const message = `力士乐防伪验证平台已升级，针对旧版本纸质二维码商品的验证，请查看公告或联系400客服电话获得更多帮助`
								const showNotice = true
								this.$refs.dialog.open(message, showNotice)
								return
							}


							if (res.result.indexOf('dc-qr.com') < 0 || (!params.m || !params.s || !params
									.f)) {
								uni.showToast({
									title: "请扫描防伪码",
									icon: 'none',
									duration: 3000
								})
								return
							}

							const data = await this.$store.dispatch('home/verify', options)

							if (data.checkedResult === "2") {
								const message = `力士乐防伪验证方案将逐步涵盖旗下所有产品，该产品目前不在当前可验证范围内，请联系400客服电话获得更多帮助`
								this.$refs.dialog.open(message)
								return
							}

							if (data.checkedResult === "1") {
								uni.navigateTo({
									url: `/pages/verify/verifyFailed`
								})
								return
							}


							uni.navigateTo({
								url: `/pages/verify/verifySuccess?productId=${data.productID}`
							})
						} catch (e) {
							console.log(e)
							uni.showToast({
								title: e.message || '校验失败',
								icon: 'none',
								duration: 1000
							})

							//TODO handle the exception
						} finally {
							uni.hideLoading()
						}
					}
				})
			},

		},
		mounted() {
			// this.$refs.dialog.open("力士乐防伪验证平台已升级，针对旧版本纸质二维码商品的验证，请查看公告或联系400客服电话获得更多帮助")
			// uni.setEnableDebug({
			// 	enableDebug: true
			// })

			// this.getUserInfo()
			// this.$refs.userInfo.onOpen()
			// uni.navigateTo({
			// 	url: `/pages/home/verify?type=succcess&productId=1`
			// })
		}
	};
</script>

<style lang="scss" scoped>
	.wrapper {
		// background: url(/static/images/1.png);
		background: #fff;
		// background-size: 100% 100%;
		height: 100vh;
		padding: 20px 20px;
		box-sizing: border-box;
		position: relative;
		overflow: hidden;
	}

	.header {
		image {
			width: 140px;
			height: 64px
		}
	}

	.main {
		position: absolute;
		top: 25%;
		z-index: 2;

		.title {
			color: $uni-primary;
			font-size: 36px;
			font-weight: 600;
		}

		.sub-title {
			color: $uni-primary;
			font-size: 32px;
			margin-bottom: 4px;
		}

		.btn {
			width: 220px;
			background: $uni-primary;
			color: #fff;
			height: 48px;
			border-radius: 4px;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-top: 100px;
			font-size: 20px;

			.icon {
				margin-left: 12px;
				color: #fff;
				transform: translateY(2px);
			}
		}
	}

	.line {
		width: 47px;
		height: 100%;
		background: #e33d09;
		position: absolute;
		right: 33px;
		bottom: 0px;
		z-index: 10;

	}

	.bg {
		position: absolute;
		right: 101px;
		bottom: 127px;
		scale: 3;
		width: 150px;
		height: 150px;
	}
</style>