<template>
	<uni-popup ref="infoPopup" type="bottom" :mask-click="false">
		<view class="zheshow">
			<view class="cen_ter">
				<view class="box_At">
					<view class="box_At_text">获取您的昵称</view>
					<view class="box_At_co">获取用户昵称信息，主要用于完善个人资料，向用户提供更好使用体验</view>
					<view class="Brn_S">
						<view class="btn_btns" @click="$refs.infoPopup.close()">取消</view>
						<button class="btn" open-type="getPhoneNumber" @getphonenumber="getphonenumber">
							<view class="btn">确认</view>
						</button>
					</view>
				</view>
			</view>
		</view>
	</uni-popup>
</template>

<script>
	export default {
		name: "UserInfo",
		data() {
			return {

			};
		},
		methods: {

			getUserInfo() {
				return new Promise((reslove, reject) => {
					uni.getUserProfile({
						desc: '用户登录',
						success: res => {
							console.log(res)
							reslove(res) // 用户的信息
						},
						fail(res) {
							reject(res)
						}
					})
				})


			},
			getphonenumber(e) {
				console.log(e)
				console.log(e.detail.rawData)

			},
			onOpen() {

				this.$refs.infoPopup.open()
			}
		}
	}
</script>

<style scoped lang="scss">
	.zheshow {
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.3);
		position: fixed;
		top: 0;
		left: 0;
		display: flex;
		align-items: center;
		align-items: flex-end;

		.mast {
			margin-top: 6rpx;
		}

		.Brn_S {
			width: 70%;
			height: 100rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin: 10rpx auto;
		}

		.btn_btns {
			width: 300rpx;
			height: 80rpx;
			background: antiquewhite;
			display: flex;
			align-items: center;
			justify-content: center;
			border-radius: 10rpx;
			margin-right: 70rpx;
			background-color: #fafafa;
			color: $uni-primary;
		}

		.imgs {
			position: absolute;
			right: 6%;
			width: 32rpx;
			height: 32rpx;
		}

		.img {
			width: 90rpx;
			height: 90rpx;
			border-radius: 50%;
			margin-left: 80rpx;

			image {
				width: 100%;
				height: 100%;
				border-radius: 50%;
			}
		}

		.cen_ter {
			width: 100%;
			height: 300rpx;
			border-top-left-radius: 30rpx;
			border-top-right-radius: 30rpx;
			background-color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;

			.box_At {
				width: 90%;
				height: 92%;
				margin-top: 20rpx;
				display: flex;
				flex-direction: column;

				.box_At_text {
					font-weight: bold;
					font-size: 30rpx
				}

				.box_At_co {
					font-size: 28rpx;
					color: #ababab;
					margin-top: 24rpx;
					margin-bottom: 24rpx;
				}

				margin-top: 12rpx;

				.acvter_all {
					font-size: 28rpx;
					color: #ababab;
					margin-left: 80rpx;
				}
			}

			.btn {
				width: 300rpx;
				margin: 35rpx auto;
				height: 80rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				background-color: $uni-primary;
				color: #FFFFFF;
				border-radius: 10rpx;
				font-size: 30rpx;
			}
		}
	}

	button {
		border-radius: 30rpx;
		height: 80rpx !important;
		padding-left: 0 !important;
		padding-right: 0 !important;
		background-color: rgba(0, 0, 0, 0) !important;
		color: #ababab !important;
		font-family: PingFang SC !important;
	}

	button:after {
		top: 0;
		left: 0;
		border: 1px solid rgba(0, 0, 0, 0) !important;
		-webkit-transform: scale(.5);
		transform: scale(.5);
		-webkit-transform-origin: 0 0;
		transform-origin: 0 0;
		box-sizing: border-box;
		border-radius: 10px;
	}
</style>