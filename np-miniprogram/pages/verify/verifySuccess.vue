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
				<view class="product-content-box" :style="{backgroundImage:'url('+imgPath+')'}">
					<!-- <image mode=" aspectFit" :src="imgPath"> -->
					</image>
				</view>
			</view>



			<view class="product-info">
				<view class="title">{{productInfo.productBrand}}</view>
				<!-- <view class="sub-title">品牌：{{productInfo.productBrand}}</view> -->
				<view class="sub-title">型号：{{productInfo.productName}}</view>
				<view class="sub-title">物料编号：{{productInfo.mnr}}</view>
				<view class="sub-title">订单编号：{{productInfo.fd}}</view>
				<view class="sub-title">序列号：{{productInfo.sn}}</view>
			</view>

			<div class="text-content">
				<span>*</span> 请核对图示产品信息和铭牌是否一致匹配，如有问题请联系客服电话。
			</div>

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

	// const verifyResultMap = {
	// 	success: {
	// 		text: '该商品为正品',
	// 		color: '#7DBB31'
	// 	},
	// 	failed: {
	// 		text: '该商品存在风险',
	// 		color: '#525F6B'
	// 	}
	// }

	export default {
		components: {
			Dialog
		},
		data() {
			return {
				productInfo: {},
				verifyResult: {
					text: '该商品为正品',
					color: '#7DBB31'
				},
				imgPath: ''
			}
		},
		computed: {
			telephone: () => constants.telephone
		},
		methods: {
			async getProductInfo(id) {
				const data = await this.$store.dispatch('home/getProductInfo', id)
				this.productInfo = data
				this.getProductImg(data.imgPath)

			},
			async getProductImg(filePath) {
				const data = await this.$store.dispatch('home/getProductImg', filePath)
				this.imgPath = `data:image/png;base64,${data}`
			},
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
		onLoad(options) {
			this.getProductInfo(options.productId)
		}
	}
</script>

<style lang="scss" scoped>
	$text-color: #7DBB31;

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
		display: flex;
		justify-content: center;
		margin-top: 8px;

		.product-content-box {
			width: 240px;
			height: 168px;
			border: 2px solid #E8EEF1;
			border-radius: 4px;
			display: flex;
			justify-content: center;
			align-items: center;
			padding: 18px 12px;
			box-sizing: border-box;
			background-repeat: no-repeat;
			background-size: contain;
			background-position: center
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

	.text-content {
		display: flex;
		justify-content: center;
		// margin-top: 8px;
		color: #002b49;
		font-size: 14px;
		margin-bottom: 8px;

		span {
			margin-right: 4px;
			color: #ff4d4f;
			font-size: 14px;
			content: "*";
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