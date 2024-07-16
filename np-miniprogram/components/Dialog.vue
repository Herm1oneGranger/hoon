<template>
	<uni-popup type="center" ref="popup">
		<view class="popup-dialog">
			<view class="popup-dialog-title">提示</view>

			<view class="popup-dialog-content">
				<view class="desc">{{text}}</view>
				<view class="content-item" v-if="showNotice" @click="handleGotoWebview">
					<uni-icons class="icon" style="transform: translateY(2px);" custom-prefix="iconfont" color="#00CCFF"
						type="icon-gonggao" size="26"></uni-icons>
					</uni-icons>
					公告链接
				</view>
				<view class="content-item" @click="handleCallPhone">
					<uni-icons class="icon" custom-prefix="iconfont" color="#00CCFF" type="icon-dianhua"
						size="20"></uni-icons>
					<!-- <uni-icons  type="phone" color="#"
						size="20"></uni-icons> -->
					{{telephone}}
				</view>
			</view>

			<view class="popup-dialog-footer">
				<button @click="$refs.popup.close()">确认</button>
			</view>

		</view>
	</uni-popup>
</template>

<script>
	import constants from '@/constants/index'

	export default {
		name: "Dialog",
		props: {

		},
		data() {
			return {
				text: '',
				website: '',
				showNotice: false
			};
		},
		computed: {
			telephone() {
				return constants.telephone
			}
		},
		methods: {
			open(text, showNotice = false) {
				this.text = text
				this.showNotice = showNotice
				this.$refs.popup.open()
			},
			handleCallPhone() {
				uni.makePhoneCall({
					phoneNumber: this.telephone.trim(),
				})
			},
			handleGotoWebview() {
				uni.navigateTo({
					url: `/pages/webview/webview`
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.popup-dialog {
		width: 310px;
		// height: 300px;
		border-radius: 8px;
		background: #fff;
		color: $uni-primary;

		.popup-dialog-title {
			text-align: center;
			font-size: 16px;
			font-weight: 600;
			padding-top: 25px;
			box-sizing: border-box;
			color: #333;
		}

		.popup-dialog-content {
			padding: 20px;
			box-sizing: border-box;
			text-align: center;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;

			.desc {
				// margin-bottom: 4px;
			}

			.content-item {
				display: flex;
				align-items: center;
				justify-content: flex-start;
				color: #00CCFF;
				width: 140px;
				margin-top: 6px;

				.icon {
					margin-right: 2px;
				}
			}
		}

		.popup-dialog-footer {
			padding: 8px 24px 16px;
			box-sizing: border-box;

			button {
				color: #fff;
				height: 36px;
				background: $uni-primary;
				text-align: center;
				padding: 0;
				border-radius: 4px;
				font-size: 14px;
				font-weight: 500;
			}
		}
	}
</style>