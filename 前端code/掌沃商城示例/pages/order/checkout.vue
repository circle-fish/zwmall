<template>
	<view>

		<!-- 头部 -->
		<mini-header title="确认订单"></mini-header>

		<!-- 主体内容 -->
		<view class="wrapper">
			<view class="wrap">
				<!-- 收货地址 -->
				<address-choose ref="shippingAddress" @returnFirst="getFirstAddress" @change="onAddressChange"></address-choose>
				<!-- 商品信息 -->
				<view class="goods-wrap">
					<view class="title">商品列表</view>
					<block v-for="(item, index) in goodsList" :key="index">
						<view class="goods">
							<image class="thumb" :src="item.thumbUrl" mode="aspectFit"></image>
							<navigator class="name elip" :url="`/pages/goods/index?id=${item.goodsId}`">{{item.name}}</navigator>
							<view class="subtotal">{{item.price}}元 x {{item.count}}</view>
							<view class="amount">{{item.price | calcSubtotal(item.count)}}元</view>
						</view>
					</block>
				</view>
				<!-- 邮费信息 -->
				<view class="express-wrap">
					<view class="name">配送方式</view>
					<view class="value" v-if="expressFee">快递配送（运费{{expressFee}}元）</view>
					<view class="value" v-else>包邮</view>
				</view>
				<!-- 订单小计 -->
				<view class="subtotal-wrap">
					<view class="item">
						<view class="name">商品件数：</view>
						<view class="value">{{goodsCount}}件</view>
					</view>
					<view class="item">
						<view class="name">商品总价：</view>
						<view class="value">{{goodsAmount}}元</view>
					</view>
					<view class="item">
						<view class="name">活动优惠：</view>
						<view class="value">-0元</view>
					</view>
					<view class="item">
						<view class="name">运费：</view>
						<view class="value">{{expressFee}}元</view>
					</view>
					<view class="item">
						<view class="name">应付总额：</view>
						<view class="value">
							<text style="font-size: 30px;">{{totalAmount}}</text>元
						</view>
					</view>
				</view>
				<!-- 底部按钮 -->
				<view class="bottom-wrap">
					<view class="address-wrap" v-if="address.id">
						<view class="user-info">{{address.name}} {{address.phone}}</view>
						<view class="address-info">
							<text>{{address.province}} {{address.city}} {{address.district}} {{address.address}}</text>
							<text class="btn-update" @click="updateAddress">修改</text>
						</view>
					</view>
					<view class="btn-wrap">
						<button class="btn btn-gray" @click="goBack">返回购物车</button>
						<button class="btn btn-primary" @click="goOrder">{{address.id ? '立即下单' : '去结算'}}</button>
					</view>
				</view>
			</view>
		</view>

		<!-- 选择收货地址提示 -->
		<view class="fixed-bar" v-if="fixedBar && !address.id && firstAddress.name">
			<view class="wrap">
				<view class="address-wrap">
					<text style="margin-right: 25px;">{{firstAddress.name}}</text>
					<text style="margin-right: 25px;">{{firstAddress.phone}}</text>
					<text>{{firstAddress.province}} {{firstAddress.city}} {{firstAddress.district}} {{firstAddress.address}}</text>
				</view>
				<button class="btn" @click="chooseFirstAddress">选择该收货地址</button>
			</view>
		</view>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import miniHeader from '@/components/common/mini-header.vue';
	import addressChoose from '@/components/order/address-choose.vue';

	import {
		mapState
	} from 'vuex';

	export default {
		components: {
			miniHeader,
			addressChoose
		},
		data() {
			return {
				address: {},
				firstAddress: {},
				fixedBar: false,
				expressFee: 18
			}
		},
		computed: {
			...mapState(['cart']),
			goodsList() {
				return this.cart.filter(e => e.checked);
			},
			goodsCount() {
				let count = 0;
				this.goodsList.forEach(e => {
					count += e.count;
				});
				return count;
			},
			goodsAmount() {
				let goodsAmount = 0;
				this.goodsList.forEach(e => {
					goodsAmount += e.price * e.count;
				});
				return goodsAmount.toFixed(2);
			},
			totalAmount() {
				return Number(this.goodsAmount) + this.expressFee;
			}
		},
		filters: {
			calcSubtotal(price, count) {
				return (price * count).toFixed(2);
			}
		},
		onPageScroll(e) {
			clearTimeout(this.timer);
			this.timer = setTimeout(() => {
				this.fixedBar = e.scrollTop > 300;
			}, 10);
		},
		onReady() {
		},
		methods: {
			getFirstAddress(e) {
				this.firstAddress = e;
			},
			onAddressChange(e) {
				this.address = e;
			},
			chooseFirstAddress() {
				this.$refs.shippingAddress.chooseAddress(0);

				uni.pageScrollTo({
					scrollTop: 0,
					duration: 200
				});
			},
			updateAddress() {
				this.$refs.shippingAddress.updateCurrent();

				uni.pageScrollTo({
					scrollTop: 150
				});
			},
			goBack() {
				uni.redirectTo({
					url: '/pages/cart/index'
				});
			},
			goOrder() {
				if (!this.address.id) {
					this.$alert('请选择收货地址', {
						showCancel: false,
						confirmText: '我知道了'
					});
					return;
				}

				this.$alert('确定提交订单吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					let address=this.address.id;
					console.log(this.goodsList);
					// uni.request({
					// 	url:"http://192.168.208.196:8083/MYSSM_war_exploded/order/save",
					// 	dataType:"json",
					// 	data:{
					// 		userId:this.$store.state.loginUser.id,
					// 		totalAmount:this.totalAmount,
					// 		goodsList:JSON.stringify(this.goodsList),
					// 		address:this.address.id
					// 	},
					// 	success: (res) => {
					// 			uni.hideLoading();
							
					// 			if (res.data.code == '200') {
					// 				// 删除购物车中已选商品
					// 				this.goodsList.forEach(e => {
					// 					this.$cart.removeGoods(e.id);
					// 				});
					// 				console.log("==============");
					// 		        console.log(res);
					// 				uni.redirectTo({
					// 					url: `/pages/order/pay/index?orderNo=${res.data.data}`
					// 				});
					// 			} else {
					// 				this.$alert(res.msg, {
					// 					showCancel: false
					// 				});
					// 	       }
					// 	}
					// })
					this.$util.request('/order/save', {
						userId:this.$store.state.loginUser.id,
						totalAmount:this.totalAmount,
						goodsList:JSON.stringify(this.goodsList),
						address:this.address.id
					}, (res) => {
						uni.hideLoading();
					
						if (res.data.code == '200') {
							// 删除购物车中已选商品
							this.goodsList.forEach(e => {
								this.$cart.removeGoods(e.id);
							});
							console.log("==============");
							console.log(res);
							uni.redirectTo({
								url: `/pages/order/pay/index?orderNo=${res.data.data}`
							});
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
	.header-wrapper {
		height: 100px;
		border-bottom: 2px solid $color-primary;

		.wrap {
			width: $page-width;
			height: 100px;
			margin: 0 auto;
			@extend %flex-align-center;

			.title {
				font-size: 28px;
				line-height: 48px;
				color: #424242;
				margin-left: 48px;
			}

			.top-navbar {
				margin-left: auto;
				@extend %flex-align-center;
				color: #757575;
				font-size: 12px;
			}
		}
	}

	.wrapper {
		background-color: $bg-color-grey;
		padding: 40px 0 60px;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			background-color: $bg-color;

			.goods-wrap {
				padding: 0 48px;

				.title {
					color: $text-color;
					font-size: 18px;
					line-height: 40px;
					border-bottom: 1px solid $border-color;
				}

				.goods {
					@extend %flex-align-center;
					padding: 15px 0;

					.thumb {
						width: 30px;
						height: 30px;
						margin-right: 10px;
					}

					.name {
						display: inline-block;
						width: 650px;
						color: #424242;
						cursor: pointer;
					}

					.subtotal {
						color: #424242;
						width: 150px;
						text-align: center;
					}

					.amount {
						margin-left: 100px;
						width: 190px;
						text-align: center;
						color: #ff6700;
					}
				}
			}

			.express-wrap {
				border-top: 1px solid $border-color;
				padding: 25px 0;
				margin: 0 48px;
				@extend %flex-align-center;

				.name {
					color: #333;
					font-size: 18px;
					width: 150px;
				}

				.value {
					color: $color-primary;
				}
			}

			.subtotal-wrap {
				border-top: 1px solid $border-color;
				padding: 25px 0;
				margin: 0 48px;
				display: flex;
				flex-direction: column;
				align-items: flex-end;

				.item {
					line-height: 2;
					@extend %flex-align-center;

					.name {
						color: #757575;
					}

					.value {
						min-width: 100px;
						text-align: right;
						color: $color-primary;
					}
				}
			}

			.bottom-wrap {
				@extend %flex-align-center;
				border-top: 2px solid #f5f5f5;
				padding: 25px 48px;

				.address-wrap {
					line-height: 20px;
					color: #424242;

					.btn-update {
						color: $color-primary;
						margin-left: 5px;
						cursor: pointer;
					}
				}

				.btn-wrap {
					margin-left: auto;
					@extend %flex-align-center;
					justify-content: flex-end;

					.btn {
						width: 158px;
						height: 38px;
						line-height: 38px;
						text-align: center;
						cursor: pointer;
						margin: 0;
						margin-left: 30px;

						&.btn-gray {
							border: 1px solid #b0b0b0;
							background-color: $bg-color;
							color: rgba(0, 0, 0, 0.27);
						}

						&.btn-primary {
							color: #FFFFFF;
							border: 1px solid $color-primary;
							background-color: $color-primary;
						}

						&:hover {
							opacity: .8;
						}
					}
				}
			}
		}
	}

	.fixed-bar {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 10;
		height: 70px;
		line-height: 70px;
		background-color: $bg-color;
		box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
		animation: open .5s forwards;

		@keyframes open {
			from {
				margin-top: -50%;
			}

			to {
				margin-top: 0;
			}
		}

		.wrap {
			width: $page-width;
			margin: 0 auto;
			@extend %flex-align-center;
			justify-content: space-between;

			.address-wrap {
				color: #757575;
			}

			.btn {
				margin-right: 0;
				width: 160px;
				height: 40px;
				line-height: 40px;
				background-color: $color-primary;
				color: #FFFFFF;
				text-align: center;
				cursor: pointer;

				&:hover {
					background-color: #f25807;
				}
			}
		}
	}
</style>
