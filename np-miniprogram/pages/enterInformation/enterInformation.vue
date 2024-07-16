<template>
	<view class="wrapper">
		<image class="nav-bar" src="/static/images/nav-bar.png"></image>
		<view class="titie">请输入铭牌上产品相关信息</view>

		<view class="input-wrapper">
			<uni-easyinput trim="all" v-model="fd" placeholder="生产订单号"></uni-easyinput>
			<text class="interval">—</text>
			<uni-easyinput class="sn" trim="all" v-model="sn" placeholder="序列号"></uni-easyinput>

		</view>

		<view class="btn" @click="handleSubmit">提交</view>

		<Dialog ref="dialog"></Dialog>
	</view>
</template>

<script>
	import Dialog from '@/components/Dialog'
	export default {
		components: {
			Dialog
		},
		onLoad(options) {
			console.log(options)
			this.externalInfo = JSON.parse(decodeURIComponent(options.info))
		},
		data() {
			return {
				sn: '',
				fd: '',
				externalInfo: {}
			};
		},
		methods: {
			async handleSubmit() {
				if (!this.fd) {
					uni.showToast({
						title: '请输入生产订单号',
						icon: 'none',
						duration: 1000
					})
					return
				}
				if (!this.sn) {
					uni.showToast({
						title: '请输入序列号',
						icon: 'none',
						duration: 1000
					})
					return
				}



				try {
					uni.showLoading({
						title: "正在校验"
					})

					const options = {
						...this.externalInfo,
						sn: this.sn,
						fd: this.fd
					}
					const data = await this.$store.dispatch('home/verifyNoMnr', options)

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
					uni.showToast({
						title: e.message || '校验失败',
						icon: 'none',
						duration: 1000
					})
				} finally {
					uni.hideLoading()
				}

			}
		}
	}
</script>

<style lang="scss" scoped>
	.wrapper {
		background-color: $page-bg;
		height: 100%;
		padding: 30px 20px 0px 20px;
		box-sizing: border-box;
		overflow: hidden;
	}

	.nav-bar {
		height: 8px;
		width: 100vw;
		position: absolute;
		top: 0px;
		left: 0px;
	}

	.input-wrapper {
		display: flex;
		align-items: center;
		margin-top: 48px;

		.interval {
			font-size: 14px;
			font-weight: bold;
			margin: 0 4px;
		}

		.sn {
			// width: 100px;
		}
	}

	.btn {
		width: 100%;
		background: $uni-primary;
		color: #fff;
		height: 48px;
		border-radius: 4px;
		display: flex;
		align-items: center;
		justify-content: center;
		margin-top: 32px;
		font-size: 20px;

	}

	.titie {
		font-size: 20px;
		text-align: center;
		color: #333;
		margin-top: 12px;
	}
</style>