<template>
	<view class="wrapper">
		<image class="nav-bar" src="/static/images/nav-bar.png"></image>

		<view class="main">
			<view class="verify-result">
				<view class="icon" :style="{background:verifyResult.color}">
					<uni-icons custom-prefix="iconfont" color="#fff" type="icon-zhengpin" size="26"></uni-icons>
				</view>
				<text :style="{color:verifyResult.color}">{{verifyResult.text}}</text>
			</view>

			<view class="product-content">
				<view class="product-content-box">
					<view class="mb-4">
						对假冒伪劣商品说不，打造健康公平的市场环境，需要大家共同的努力。
					</view>
					<view class="mb-4"> 力士乐为广大消费者提供<text class="fwt-500 blur-color">免费的产品鉴定服务</text> 。</view>
					<view class="mb-4">
						如果你怀疑购买到假货，欢迎拨打我们的官方热线或通过官方渠道，并提供以下信息。我们将助你辨别真假。
					</view>

					<view class="list mb-4">
						<view>
							买方公司名称及联系方式
						</view>
						<view>
							购买渠道信息
						</view>
						<view>
							购买凭证，例如：合同、发票、网上交易截图（价格等敏感信息可隐去）
						</view>
						<view>
							产品照片
						</view>
					</view>

					<view>
						<view>
							邮箱：Info@boschrexroth.com.cn
						</view>
						<view>
							网站：www.boschrexroth.com.cn
						</view>
					</view>
				</view>

			</view>

			<!-- <view class="product-info">
				<view class="title">{{productInfo.productBrand}}</view>
				<view class="sub-title">型号：{{productInfo.productName}}</view>
				<view class="sub-title">物料编号：{{productInfo.mnr}}</view>
				<view class="sub-title">订单编号：{{productInfo.fd}}</view>
				<view class="sub-title">序列号：{{productInfo.sn}}</view>
			</view> -->

			<view class="card">
				<view class="header">
					<view class="title">维修保养</view>
				</view>
				<view class="line"></view>
				<view class="content">
					<view class="item">
						<view class="label">400热线电话：</view>
						<view class="value active" @click="handleCallPhone">{{telephone}}</view>
					</view>
				</view>
			</view>

			<view class="card">
				<view class="header">
					<view class="title">产品资料中心</view>
				</view>
				<view class="line"></view>
				<view class="content">
					<view class="item">
						<view class="label">链接：</view>
						<view class="value active" @click="handleGotoMini">资料中心</view>
					</view>
				</view>
			</view>
		</view>


		<Dialog ref="dialog"></Dialog>
	</view>
</template>

<script>
	import constants from '@/constants/index'
	import Dialog from '@/components/Dialog'

	export default {
		components: {
			Dialog
		},
		data() {
			return {
				productInfo: {},
				verifyResult: {
					text: '该商品存在风险',
					color: '#525F6B'
				},
				imgPath: ''
			}
		},
		computed: {
			telephone: () => constants.telephone
		},
		methods: {
			handleCallPhone() {
				uni.makePhoneCall({
					phoneNumber: this.telephone.trim(),
				})
			},
			handleGotoMini() {
				uni.navigateToMiniProgram({
					appId: constants.miniprogramAppid,
					path: "pages/index/index"
				})
			}
		},
		onLoad() {
			setTimeout(() => {
				this.$refs.dialog.open("该产品可能为仿制品，请联系400客服电话获得更多帮助")
			}, 500)
		}
	}
</script>

<style lang="scss" scoped>
	$text-color: #7DBB31;

	.mb-4 {
		margin-bottom: 4px;
	}

	.blur-color {
		color: #00CCFF;
	}

	.fwt-500 {
		font-weight: bold;
	}

	page {
		height: 100%;
		position: relative;
	}

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

	.verify-result {
		display: flex;
		align-items: center;
		justify-content: center;
		margin-bottom: 12px;

		.icon {
			margin-right: 12px;
			border-radius: 50%;
			width: 46px;
			height: 46px;
			background: $text-color;
			display: flex;
			justify-content: center;
			align-items: center;
		}

		text {
			font-size: 20px;
			color: $text-color;
		}
	}

	.product-content {
		margin-top: 8px;
		margin-bottom: 14px;
		font-size: 13px;
		line-height: 22px;
		padding: 14px 10px;
		box-sizing: border-box;
		background: rgba(240, 245, 249, 1);
		border-radius: 4px;
		color: #002b49;

		.list {
			padding: 4px 0px;
			box-sizing: border-box;
			padding-left: 14px;

			view {
				position: relative;
				padding-left: 12px;
				line-height: 20px;
				margin-bottom: 2px;

				&::after {
					content: "";
					display: inline-block;
					width: 4px;
					height: 4px;
					border-radius: 50%;
					background: #002b49;
					position: absolute;
					top: 50%;
					left: 2px;
					transform: translateY(-50%);
				}
			}
		}
	}

	.product-info {
		margin-top: 20px;
		margin-bottom: 16px;
		padding-left: 8px;
		box-sizing: border-box;

		.title {
			color: $uni-primary;
			font-size: 20px;
			font-weight: 600;
			margin-bottom: 6px;
		}

		.sub-title {
			color: $uni-primary;
			font-size: 14px;
			margin-bottom: 4px;

		}
	}

	.card {
		width: 100%;
		padding: 6px 0px;
		background: $uni-primary;
		border-radius: 4px;
		position: relative;
		margin-bottom: 12px;

		.header {
			position: relative;
			color: #fff;
			box-sizing: border-box;
			padding: 3px 8px 0px 8px;

			.title {
				font-size: 16px;
				font-weight: bold;
			}
		}

		.line {
			width: 100%;
			height: 1px;
			background: #797979;
			margin: 6px 0;
		}

		.content {
			color: #fff;
			font-size: 14px;
			padding: 4px 8px;
			box-sizing: border-box;

			.item {
				display: flex;
				justify-content: space-between;
				margin-bottom: 6px;
				font-size: 14px;
			}

			.active {
				color: #00CCFF;
			}
		}

	}
</style>