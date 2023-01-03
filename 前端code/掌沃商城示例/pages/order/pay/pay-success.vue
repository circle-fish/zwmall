<template>
	<view>

		<!-- 头部 -->
		<zw-header></zw-header>

		<!-- 支付成功详情 -->
		<view class="wrapper">
			<view class="wrap">
				<view class="left">
					<view class="title">支付成功</view>
					<view class="amount">
						<text class="value">{{order.payAmount}}</text>元
					</view>
					<navigator class="btn-detail" :url="`/pages/order/detail?orderNo=${order.orderNo}`">查看订单详情</navigator>
					<view class="tips">
						<text>我们不会以任何理由要求您提供银行卡信息或支付额外费用</text>
						<text>请谨防钓鱼链接或诈骗电话。</text>
					</view>
				</view>
				<view class="right">
					<view class="detail-item">
						<view class="name">订单编号：</view>
						<view class="value">{{order.orderNo}}</view>
					</view>
					<view class="detail-item">
						<view class="name">收货信息：</view>
						<view class="value">
							<text>{{order.consignee}} {{order.phone}}</text>
							<text>{{order.province}} {{order.city}} {{order.district}} {{order.address}}</text>
						</view>
					</view>
					<view class="detail-item" v-for="(value, index) in order.goodsList">
						<view class="name">商品名称：</view>
						<view class="valueelip" >{{value.name}}</view>
					</view>
					<view class="qrcode-wrap">
						<image class="qrcode" src="https://c1.mifile.cn/f/i/15/pay/app-code.png" mode="aspectFit"></image>
						<view class="tips">通过商城app随时跟踪订单</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 商品推荐 -->
		<recommend title="为你推荐"></recommend>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import recommend from '@/components/goods/recommend.vue';

	export default {
		components: {
			recommend
		},
		data() {
			return {
				order: {}
			}
		},
		onLoad(e) {
			if (!e.orderNo) {
				uni.redirectTo({
					url: '/pages/index'
				});
				return;
			}
			
			this.findBaseDetails(e.orderNo);
		},
		methods: {
			findBaseDetails(orderNo) {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/order/pay/details', {
					orderNo: orderNo
				}, (res) => {
					uni.hideLoading();
		
					if (res.data.code == 200 ) {
						this.order = res.data.data;
					} else {
						uni.redirectTo({
							url: '/pages/index'
						});
					}
				}, true);
			}
		}
	}
</script>

<style lang="scss">
	.wrapper {
		background-color: $bg-color-grey;
		padding: 40px 0 60px;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			display: flex;
			height: 400px;

			.left {
				width: 506px;
				background-color: $color-success;
				text-align: center;

				.title {
					font-size: 48px;
					color: #fff;
					line-height: 1;
					margin-top: 70px;
					margin-bottom: 10px;
				}

				.amount {
					margin: 20px 0 30px;
					color: #ffff00;

					.value {
						font-size: 24px;
					}
				}

				.btn-detail {
					margin: 0 auto 42px;
					border: 1px solid #fff;
					background: transparent;
					color: #fff;
					width: 158px;
					height: 38px;
					line-height: 38px;
					text-align: center;
					cursor: pointer;
					transition: all .3s;

					&:hover {
						opacity: .8;
					}
				}

				.tips {
					font-size: 12px;
					color: $text-color;

					text {
						display: block;
					}
				}
			}

			.right {
				flex: 1;
				background-color: $bg-color;
				padding: 50px 60px;
				position: relative;

				.detail-item {
					line-height: 24px;
					margin-bottom: 8px;
					display: flex;

					.name {
						color: #b0b0b0;
					}

					.value {
						max-width: 420px;
						max-height: 240px;
						overflow: hidden;
						color: #757575;

						text {
							display: block;
						}
					}
				}

				.qrcode-wrap {
					width: 94px;
					position: absolute;
					right: 40px;
					bottom: 25px;
					text-align: center;

					.qrcode {
						width: 86px;
						height: 86px;
					}

					.tips {
						margin-top: 5px;
						font-size: 12px;
						color: $text-color;
					}
				}
			}
		}
	}
</style>
