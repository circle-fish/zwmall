<template>
	<view>

		<!-- 头部 -->
		<mini-header title="支付订单"></mini-header>

		<!-- 订单信息 -->
		<view class="order-wrapper">
			<view class="wrap">
				<view class="top">
					<i class="icon i-success"></i>
					<view class="order-info">
						<view class="left">
							<view class="title">订单提交成功！去付款咯～</view>
							<view class="info-item">
								<text>请在</text>
								<text class="duration">{{timeRemaining}}</text>
								<text>内完成支付, 超时后将自动取消订单</text>
							</view>
							<view class="info-item address elip" :class="{'hide': showDetails}">
								<text>收货信息：</text>
								<text>{{order.consignee}}</text>
								<text>{{order.phone}}</text>
								<text>{{order.province}} {{order.city}} {{order.district}} {{order.address}}</text>
							</view>
						</view>
						<view class="right">
							<view class="pay-amount">
								<text>应付金额：</text>
								<text class="amount">{{order.payAmount}}</text>
								<text class="unit">元</text>
							</view>
							<view :class="['btn-detail', {'active': showDetails}]" @click="showDetails = !showDetails">
								<text>订单详情</text>
								<i class="icon i-right"></i>
							</view>
						</view>
					</view>
				</view>
				<view :class="['bottom', {'active': showDetails}]">
					<view class="bottom-wrap">
						<view class="detail-item">
							<view class="name">订单号码：</view>
							<view class="value orderno">{{order.orderNo}}</view>
						</view>
						<view class="detail-item">
							<view class="name">收货信息：</view>
							<view class="value">
								<text>{{order.consignee}}</text>
								<text>{{order.phone}}</text>
								<text>{{order.province}} {{order.city}} {{order.district}} {{order.address}}</text>
							</view>
						</view>
						<view class="detail-item" v-for="(value, index) in order.goodsList">
							<view class="name">商品名称：</view>
							<view class="valueelip" >{{value.name}}</view>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 选择支付方式 -->
		<view class="payment-wrapper">
			<view class="wrap">
				<view class="tip">选择以下支付方式付款</view>
				<view class="pay-platform">
					<view class="title">支付平台</view>
					<view class="platform-wrap">
						<image class="platform" src="/static/image/pay/alipay.png" mode="aspectFit"
							@click="requestPayment(0)"></image>
						<image class="platform" src="/static/image/pay/wechatpay.png" mode="aspectFit"
							@click="requestPayment(1)"></image>
					</view>
				</view>
			</view>
		</view>

		<!-- 支付弹窗 -->
		<zw-dialog :hidden="hiddenQrcode" :title="payMode == 0 ? '支付宝支付' : '微信支付'" :width="370" :height="400"
			@close="cancelPayment">
			<view class="qrcode-wrapper">
				<view class="qrcode-wrap">
					<image class="qrcode" :src="$util.baseUrl + `/order/pay/qrcode?content=${qrcodeUrl}`"
						mode="aspectFit"></image>
					<view class="logo-wrap">
						<image class="logo"
							:src="`/static/image/pay/${payMode == 0 ? 'alipay-logo.png' : 'wechat-logo.png'}`"
							mode="aspectFit"></image>
					</view>
				</view>
				<view class="tips">
					<view>请使用<text class="platform">{{payMode == 0 ? '支付宝' : '微信'}}</text>扫描</view>
					<view>二维码完成支付</view>
				</view>
			</view>
		</zw-dialog>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import miniHeader from '@/components/common/mini-header.vue';

	export default {
		components: {
			miniHeader
		},
		data() {
			return {
				timeRemaining: '',
				order: {},
				showDetails: false,
				qrcodeUrl: '',
				payMode: 0,
				hiddenQrcode: true
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
		onHide() {
			clearInterval(this.timer);
		},
		methods: {
			findBaseDetails(orderNo) {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				// uni.request({
				// 	url: "http://192.168.208.196:8083/MYSSM_war_exploded/order/pay/details",
				// 	data: {
				// 		orderNo: orderNo
				// 	},
				// 	success: (res) => {
				// 		uni.hideLoading();
				// 		console.log("1111111111111111");
				// 		console.log(res);
				// 		if (res.data.code == '200') {
				// 			this.order = res.data.data;
    //                         console.log(this.order);
				// 			this.startCountdown(this.order.createTime);
				// 		} else {
				// 			this.$alert(res.msg, {
				// 				showCancel: false
				// 			}, () => {
				// 				uni.redirectTo({
				// 					url: '/pages/index'
				// 				});
				// 			});
				// 		}
				// 	}
				// })
				this.$util.request('/order/pay/details', {
					orderNo: orderNo
				}, (res) => {
					uni.hideLoading();
                    console.log("1111111111111111");
					console.log(res);
					if (res.data.code == '200') {
						this.order = res.data.data;

						this.startCountdown(res.order.createTime);
					} else {
						this.$alert(res.msg, {
							showCancel: false
						}, () => {
							uni.redirectTo({
								url: '/pages/index'
							});
						});
					}
				}, true);
			},
			startCountdown(createTime) {
				this.timer = setInterval(() => {
					const startTime = new Date();
					// 计算到期时间
					const endTime = new Date(createTime);
					endTime.setMinutes(endTime.getMinutes() + 30);

					// 获取时间差
					const diff = this.$util.timeDiff(startTime, endTime);
					if (diff) {
						this.timeRemaining = `${diff[1]}分${diff[2]}秒`;
					} else {
						clearInterval(this.timer);

						uni.redirectTo({
							url: `/pages/order/detail?orderNo=${this.order.orderNo}`
						});
					}
				}, 1000);
			},
			requestPayment(mode) {
				uni.showLoading({
					title: '请求中...',
					mask: true
				});
				this.$util.request('/order/updatestatus', {
					orderNo: this.order.orderNo,
					status: 2
				}, (res) => {
					uni.hideLoading();

					if (res.data.code == '200') {
						// this.qrcodeUrl = encodeURIComponent(res.url);
						// this.payMode = mode;
						// this.hiddenQrcode = false;
						uni.navigateTo({
							url: `/pages/order/pay/pay-success?orderNo=${this.order.orderNo}`
						});
					} else {
						this.$alert(res.msg, {
							showCancel: false
						});
					}
				});
			},
			waitSuccess() {
				this.wsTask = uni.connectSocket({
					url: `${this.$util.wsUrl}/pay/status.ws/${this.order.orderNo}`,
					complete: () => {}
				});

				this.wsTask.onOpen(() => {
					this.wsTask.onMessage((res) => {
						try {
							res = JSON.parse(res.data);
							if (res.state == 'ok') {
								this.cancelPayment();

								uni.navigateTo({
									url: `/pages/order/pay/pay-success?orderNo=${this.order.orderNo}`
								});
							}
						} catch (e) {
							console.error(e);
						}
					});
				});
			},
			cancelPayment() {
				this.hiddenQrcode = true;

				// 断开ws连接
				try {
					this.wsTask.close();
				} catch (e) {
					console.error(e);
				}
			}
		}
	}
</script>

<style lang="scss">
	.order-wrapper {
		background-color: $bg-color-grey;
		padding-top: 38px;
		padding-bottom: 30px;

		.wrap {
			background-color: $bg-color;
			width: $page-width;
			margin: 0 auto;

			.top {
				display: flex;
				padding: 38px;

				.i-success {
					font-size: 100px;
					color: $color-success;
					margin-right: 48px;
				}

				.order-info {
					flex: 1;
					@extend %flex-align-center;
					justify-content: space-between;

					.left {

						.title {
							margin-bottom: 10px;
							font-size: 24px;
							line-height: 36px;
						}

						.info-item {
							color: #616161;
							margin-bottom: 5px;
							line-height: 2;

							.duration {
								margin: 0 5px;
								color: $color-primary;
							}

							&.address {
								max-width: 600px;

								text {
									margin-right: 10px;
								}

								&.hide {
									animation: hide .3s forwards;

									@keyframes hide {
										from {
											opacity: 1;
										}

										to {
											opacity: 0;
										}
									}
								}
							}
						}
					}

					.right {
						text-align: right;

						.pay-amount {
							margin-bottom: 20px;
							color: #757575;

							.amount {
								font-size: 24px;
								color: $color-primary;
							}

							.unit {
								color: $color-primary;
							}
						}

						.btn-detail {
							cursor: pointer;
							color: #757575;

							.icon {
								margin-left: 5px;
								font-size: 12px;
								font-weight: bold;
								display: inline-block;
								transform: rotate(90deg);
								transition: all .3s;
							}

							&:hover {
								color: $color-primary;
							}

							&.active {

								.icon {
									transform: rotate(-90deg);
								}
							}
						}
					}
				}
			}

			.bottom {
				height: 0;
				overflow: hidden;
				transition: height .6s;

				&.active {
					height: 150px;
				}

				.bottom-wrap {
					padding: 30px 0;
					border-top: 1px solid $border-color;
					margin-top: 10px;
					margin-left: 185px;
					margin-right: 38px;

					.detail-item {
						@extend %flex-align-center;
						line-height: 2;
						color: $text-color;

						.name {
							width: 85px;
						}

						.value {
							max-width: 900px;

							&.orderno {
								color: $color-primary;
							}

							text {
								margin-right: 10px;
							}
						}
					}
				}
			}
		}
	}

	.payment-wrapper {
		background-color: $bg-color-grey;
		padding-bottom: 38px;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			background-color: $bg-color;

			.tip {
				color: #424242;
				padding: 30px 0;
				margin: 0 48px;
				border-bottom: 1px solid $border-color;
				font-size: 18px;
			}

			.pay-platform {
				padding: 30px 48px;

				.title {
					font-size: 16px;
					font-weight: bold;
					color: #616161;
					margin-bottom: 20px;
				}

				.platform {
					width: 174px;
					height: 60px;
					cursor: pointer;
					margin-right: 14px;
					border: 1px solid $border-color;
					transition: border-color .3s;

					&:hover {
						border-color: $color-primary;
					}
				}
			}
		}
	}
 //    .detail-item{
	// 	display: flex;
	// 	flex-direction: column;
	// }
	.qrcode-wrapper {
		width: 100%;

		.qrcode-wrap {
			width: 290px;
			height: 290px;
			position: relative;
			margin: 30px auto 10px;

			.qrcode {
				width: 100%;
				height: 100%;
			}

			.logo-wrap {
				position: absolute;
				top: 50%;
				left: 50%;
				transform: translate(-50%, -50%);
				width: 45px;
				height: 45px;
				background-color: $bg-color;
				padding: 3px;
				border-radius: 10px;
				box-shadow: 0 0 5px $bg-color;

				.logo {
					width: 100%;
					height: 100%;
				}
			}
		}

		.tips {
			margin-bottom: 10px;
			color: $text-color;
			text-align: center;

			.platform {
				color: $color-primary;
				margin: 0 5px;
			}
		}
	}
</style>
