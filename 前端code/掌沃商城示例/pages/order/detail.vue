<template>
	<view v-if="order.id">
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb :pages="[{name: '交易订单', linkUrl: '/pages/order/index'}, {name: '订单详情'}]" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="1-1">
			<view class="title">订单详情</view>
			<view class="order-header">
				<view class="orderno">订单号：<text style="user-select: text;">{{order.orderNo}}</text></view>
				<view class="btn-wrap">
					<view class="btn">联系客服</view>
					<view v-if="order.status == 0" class="btn" @click="orderCancel">取消订单</view>
					<view v-if="order.status == 2 || order.status == 34" class="btn" @click="orderRefund">申请退款</view>
					<view v-if="order.status == 8 || order.status == 10" class="btn">申请售后</view>
					<view v-if="order.status == 6" class="btn btn-primary" @click="confirmReceipt">确认已收货</view>
					<navigator v-if="order.status == 0" class="btn btn-primary" :url="`/pages/order/pay/index?orderNo=${order.orderNo}`">立即付款</navigator>
				</view>
			</view>
			<view :class="['order-status', {'warnging': order.status == 0}, {'green': order.status >= 2 && order.status <= 10}]"
			 :title="order.remark">{{order.statusDesc}}</view>
			<order-progress :time="order.timeInfo"></order-progress>
			<view class="express-wrap" v-if="order.expressInfo">
				<text class="item">物流公司：</text>
				<a class="item link" :href="order.expressInfo.linkUrl" target="_blank">
					<text>{{order.expressInfo.name}}</text>
					<i class="icon i-right-double"></i>
				</a>
				<text class="item" style="user-select: text;">运单号：{{order.expressInfo.waybillNo}}</text>
			</view>
			<view class="goods-wrap">
				<view class="goods" v-for="(item, index) in order.goodsList" :key="index">
					<a :href="`/pages/goods/index?id=${item.goodsId}`" target="_blank">
						<image class="thumb" :src="item.thumbUrl" mode="aspectFit"></image>
					</a>
					<a :href="`/pages/goods/index?id=${item.goodsId}`" target="_blank">
						<view class="name elip">{{item.name}}</view>
					</a>
					<view class="subtotal">{{item.price}}元 x {{item.count}}</view>
				</view>
			</view>
			<view class="other-details">
				<view class="detail-title">收货信息</view>
				<view class="item">
					<view class="name">姓<text style="width: 28px;display: inline-block;"></text>名：</view>
					<view class="value">{{order.consignee}}</view>
				</view>
				<view class="item">
					<view class="name">联系电话：</view>
					<view class="value">{{order.phone}}</view>
				</view>
				<view class="item">
					<view class="name">收货地址：</view>
					<view class="value">{{order.province}} {{order.city}} {{order.district}}
						{{order.address}}</view>
				</view>
			</view>
			<view class="other-details" v-if="order.payMode">
				<view class="detail-title">支付方式</view>
				<view class="item">
					<view class="name">支付方式：</view>
					<view class="value">{{order.payMode}}</view>
				</view>
			</view>
			<view class="total-amount">
				<view class="item">
					<view class="name">商品总价：</view>
					<view class="value">{{order.goodsAmount.toFixed(2)}}元</view>
				</view>
				<view class="item">
					<view class="name">运费：</view>
					<view class="value">{{order.expressFee}}元</view>
				</view>
				<view class="item" v-if="order.discountAmount > 0">
					<view class="name">优惠金额：</view>
					<view class="value">{{order.discountAmount.toFixed(2)}}元</view>
				</view>
				<view class="item">
					<view class="name">实付金额：</view>
					<view class="value">
						<text class="pay-amount">{{order.payAmount.toFixed(2)}}</text>元
					</view>
				</view>
			</view>
		</menu-wrapper>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import orderProgress from '@/components/order/order-progress.vue';

	export default {
		components: {
			menuWrapper,
			orderProgress
		},
		data() {
			return {
				order: {}
			}
		},
		onLoad(e) {
			if (e.orderNo) {
				this.findDetails(e.orderNo);
			} else {
				uni.redirectTo({
					url: '/pages/index'
				});
			}
		},
		methods: {
			findDetails(orderNo) {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				// uni.request({
				// 	url:"http://192.168.208.196:8083/MYSSM_war_exploded/order/orderdetails",
				// 	data:{
				// 		orderNo:orderNo
				// 	},
				// 	success: (res) => {
				// 		console.log("------------------");
						
				// 		this.order=res.data.data[0];
				// 		console.log(this.order);
				// 		uni.hideLoading();
				// 	}
				// })
				this.$util.request('/order/orderdetails', {
					orderNo:orderNo
				}, (res) => {
					uni.hideLoading();
                    console.log("------------------");
					if (res.data.code == "200") {
						this.order = res.data.data[0];
						console.log(this.order);
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
			confirmReceipt() {
				this.$alert('确定已收货吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/order/confirmReceipt', {
						id: this.order.id
					}, (res) => {
						uni.hideLoading();

						if (res.state == 'ok') {
							this.findDetails(this.order.orderNo);

							this.$alert('操作成功', {
								showCancel: false
							});
						} else {
							this.$alert(res.msg, {
								showCancel: false
							});
						}
					});
				});
			},
			orderCancel() {
				this.$alert('确定取消订单吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/order/cancel', {
						id: this.order.id
					}, (res) => {
						uni.hideLoading();

						if (res.state == 'ok') {
							this.findDetails(this.order.orderNo);
						} else {
							this.$alert(res.msg, {
								showCancel: false
							});
						}
					});
				});
			},
			orderRefund() {
				this.$alert('确定申请退款吗？', {}, () => {
					uni.showLoading({
						title: '提交中...',
						mask: true
					});
					this.$util.request('/order/refund', {
						id: this.order.id
					}, (res) => {
						uni.hideLoading();

						if (res.state == 'ok') {
							this.findDetails(this.order.orderNo);
						} else {
							this.$alert(res.msg, {
								showCancel: false
							});
						}
					});
				});
			}
		}
	}
</script>

<style lang="scss">
	.title {
		font-size: 30px;
		color: #757575;
		line-height: 68px;
	}

	.order-header {
		display: flex;
		align-items: flex-end;
		justify-content: space-between;
		padding: 20px 0;
		border-bottom: 1px solid $border-color;

		.orderno {
			font-size: 18px;
			color: #333;
		}

		.btn-wrap {
			@extend %flex-align-center;

			.btn {
				margin-left: 10px;
				width: 118px;
				height: 28px;
				line-height: 28px;
				font-size: 12px;
				text-align: center;
				cursor: pointer;
				color: #757575;
				border: 1px solid #757575;
				transition: all .3s;

				&.btn-primary {
					background-color: $color-primary;
					color: #FFFFFF;
					border-color: $color-primary;

					&:hover {
						background-color: $color-primary;
						color: #FFFFFF;
						border-color: $color-primary;
						opacity: .9;
					}
				}

				&:hover {
					color: #fff;
					background-color: #757575;
					border-color: #757575;
				}
			}
		}
	}

	.order-status {
		color: #b0b0b0;
		font-size: 18px;
		margin: 25px 0;

		&.warnging {
			color: $color-primary;
		}

		&.green {
			color: $color-success;
		}
	}

	.express-wrap {
		margin: 15px 20px;
		line-height: 30px;
		border-bottom: 1px solid #cfeeab;

		.item {
			font-size: 12px;
			color: #757575;
		}

		.link {
			cursor: pointer;
			margin-right: 10px;

			.icon {
				font-size: 11px;
				margin-left: 2px;
			}

			&:hover {
				color: $color-primary;
			}
		}
	}

	.goods-wrap {
		margin-top: 20px;

		.goods {
			color: $text-color;
			height: 90px;
			border-bottom: 1px solid $border-color;
			@extend %flex-align-center;

			.thumb {
				width: 80px;
				height: 80px;
			}

			.name {
				color: $text-color;
				margin-left: 20px;
				min-width: 300px;
				max-width: 600px;
				padding-right: 20px;

				&:hover {
					color: $color-primary;
				}
			}
		}
	}

	.other-details {
		min-height: 120px;
		padding: 30px 0 12px;
		border-bottom: 1px solid $border-color;

		.detail-title {
			margin-bottom: 15px;
			font-size: 18px;
			color: #333;
		}

		.item {
			color: #757575;
			@extend %flex-align-center;

			.name {
				font-weight: bold;
				padding: 3px 0;
				padding-right: .3em;
			}
		}
	}

	.total-amount {
		padding-top: 30px;

		.item {
			@extend %flex-align-center;
			justify-content: flex-end;
			padding: 5px 0;

			.name {
				color: #757575;
			}

			.value {
				min-width: 120px;
				text-align: right;
				color: $color-primary;
			}

			.pay-amount {
				font-size: 30px;
			}
		}
	}
</style>
