<template>
	<view>

		<!-- 头部 -->
		<mini-header title="我的购物车" :tips="hasGoods ? '温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算' : ''" :mustLogin="false"></mini-header>

		<!-- 空购物车 -->
		<view class="empty-wrapper" v-if="!hasGoods">
			<view class="wrap">
				<img class="avatar" src="https://s01.mifile.cn/i/cart/cart-empty.png" />
				<view class="tips-area">
					<view class="empty-tip">您的购物车还是空的！</view>
					<view class="login-tip" v-if="!hasLogin">登录后将显示您之前加入的商品</view>
					<view class="btn-wrap">
						<navigator v-if="!hasLogin" class="btn btn-primary" url="/pages/user/login/index?callbackUrl=/pages/cart/index">立即登录</navigator>
						<navigator :class="['btn', {'btn-primary': hasLogin}]" url="/pages/search/index?keyword=all">马上去购物</navigator>
					</view>
				</view>
			</view>
		</view>
		<!-- 购物车列表 -->
		<view class="cart-wrapper" v-if="hasGoods">
			<view class="wrap">
				<view class="cart-header">
					<label @click="allCheckChange">
						<checkbox disabled :checked="allChecked" color="#FF6700"></checkbox>
						<text style="margin-left: 10px;">全选</text>
					</label>
					<view class="col-name">商品名称</view>
					<view class="col-price">单价</view>
					<view class="col-count">数量</view>
					<view class="col-subtotal">小计</view>
					<view class="col-action">操作</view>
				</view>
				<view class="cart-main">
					<block v-for="(item, index) in cart" :key="index">
						<view class="goods">
							<label @click="checkChange(item.id, item.checked)">
								<checkbox disabled :checked="item.checked" color="#FF6700"></checkbox>
							</label>
							<img class="thumb" :src="item.thumbUrl" mode="aspectFit" @click="goGoods(item.goodsId)"></image>
							<view class="name elip" @click="goGoods(item.goodsId)">{{item.name}}</view>
							<view class="price">{{item.price}}元</view>
							<count :count="item.count" :maxCount="item.maxCount" @change="countChange($event, item.id)"></count>
							<view class="subtotal">{{item.price | calcSubtotal(item.count)}}元</view>
							<view class="btn-close" @click="removeGoods(item.id)">
								<i class="icon i-close"></i>
							</view>
						</view>
					</block>
				</view>
				<view class="cart-footer" :class="{'fixed': fixedBar}">
					<view class="left">
						<navigator class="shopping-link" url="/pages/search/index?keyword=all">
							继续购物
						</navigator>
						<view class="separator"></view>
						<view class="delete-link" @click="removeGoodsList">删除</view>
						<view class="separator"></view>
						<view class="tips">
							共 <text class="count">{{totalCount}}</text> 件商品，已选择 <text class="count">{{checkedCount}}</text> 件
						</view>
					</view>
					<view class="right">
						<view class="total-amount">
							<text class="desc">合计：</text>
							<text class="amount">{{totalAmount}}</text>
							<text class="desc">元</text>
						</view>
						<button :class="['btn-primary', {'disabled': !hasChecked}]" @click="goCheckout">去结算</button>
						<!-- 提示勾选商品 -->
						<view v-if="!hasChecked" class="nochecked-tip">请勾选需要结算的商品</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 商品推荐 -->
		<recommend id="recommend" :title="hasGoods ? '买购物车中商品的人还买了' : '为您推荐'"></recommend>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import miniHeader from '@/components/common/mini-header.vue';
	import count from '@/components/cart/count.vue';
	import recommend from '@/components/goods/recommend.vue';

	import {
		mapState,
		mapGetters
	} from 'vuex';

	export default {
		components: {
			miniHeader,
			count,
			recommend
		},
		computed: {
			...mapState(['cart']),
			...mapGetters(['hasLogin']),
			hasGoods() {
				return this.cart.length > 0;
			},
			totalCount() {
				let totalCount = 0;
				this.cart.forEach(e => {
					totalCount += e.count;
				});
				return totalCount;
			},
			checkedGoodsList() {
				return this.cart.filter(e => e.checked);
			},
			checkedCount() {
				let count = 0;
				this.checkedGoodsList.forEach(e => {
					count += e.count;
				});
				return count;
			},
			hasChecked() {
				return this.checkedCount > 0;
			},
			allChecked() {
				return this.checkedCount == this.totalCount;
			},
			totalAmount() {
				let totalAmount = 0;
				this.checkedGoodsList.forEach(e => {
					totalAmount += e.price * e.count;
				});
				return totalAmount.toFixed(2);
			}
		},
		filters: {
			calcSubtotal(price, count) {
				return (price * count).toFixed(2);
			}
		},
		data() {
			return {
				fixedBar: false
			}
		},
		onLoad() {
			this.$cart.initCart();
		},
		onPageScroll(e) {
			clearTimeout(this.timer);
			this.timer = setTimeout(() => {
				uni.createSelectorQuery().select('#recommend').boundingClientRect().exec(e => {
					this.fixedBar = e[0].top > this.windowHeight;
				});
			}, 10);
		},
		onReady() {
			this.windowHeight = uni.getSystemInfoSync().windowHeight;
		},
		methods: {
			countChange(count, id) {
				this.$cart.update(id, {
					count
				}).catch(msg => {
					this.$alert(msg, {
						showCancel: false
					});
				});
			},
			removeGoods(id) {
				this.$alert('确定删除吗？', {}, () => {
					this.$cart.removeGoods(id).catch(msg => {
						this.$alert(msg, {
							showCancel: false
						});
					});
				});
			},
			allCheckChange() {
				const checked = this.allChecked;
				this.cart.forEach(e => {
					this.checkChange(e.id, checked);
				});
			},
			checkChange(id, checked) {
				this.$cart.update(id, {
					checked: !checked
				}).catch(msg => {
					this.$alert(msg);
				});
			},
			removeGoodsList() {
				if (!this.hasChecked) {
					this.$alert('请至少选择一项', {
						showCancel: false,
						confirmText: '我知道了'
					});
				} else {
					this.$alert(`确定删除已选的商品吗？`, {}, () => {
						this.checkedGoodsList.forEach(e => {
							this.$cart.removeGoods(e.id);
						});
					});
				}
			},
			goGoods(id) {
				uni.navigateTo({
					url: `/pages/goods/index?id=${id}`
				});
			},
			goCheckout() {
				if (!this.hasChecked) return;

				if (this.hasLogin) {
					uni.navigateTo({
						url: '/pages/order/checkout'
					});
				} else {
					uni.navigateTo({
						url: '/pages/user/login/index?callbackUrl=/pages/cart/index'
					});
				}
			}
		}
	}
</script>

<style lang="scss">
	.empty-wrapper {
		display: flex;
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 65px auto 130px;
			@extend %flex-align-center;
			padding-left: 300px;

			.avatar {
				height: 273px;
			}

			.tips-area {
				margin-left: 50px;

				.empty-tip {
					font-size: 36px;
					font-weight: bold;
					color: #b0b0b0;
					margin-bottom: 15px;
				}

				.login-tip {
					font-size: 20px;
					color: #b0b0b0;
					margin-bottom: 20px;
				}

				.btn-wrap {
					display: flex;

					.btn {
						width: 170px;
						height: 48px;
						line-height: 48px;
						text-align: center;
						margin-right: 15px;
						color: $color-primary;
						border: 1px solid $color-primary;
						transition: all .3s;

						&:hover {
							background-color: $bg-color;
						}

						&.btn-primary {
							background-color: $color-primary;
							border-color: $color-primary;
							color: #fff;

							&:hover {
								opacity: .8;
								background-color: $color-primary;
							}
						}
					}
				}
			}
		}
	}

	.cart-wrapper {
		background-color: $bg-color-grey;
		padding: 38px;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			color: #424242;

			checkbox {
				transform: scale(.8);
			}

			.cart-header {
				@extend %flex-align-center;
				height: 70px;
				line-height: 70px;

				background-color: $bg-color;

				label {
					width: 85px;
					padding-left: 20px;

					/deep/ checkbox {
						cursor: pointer;
					}
				}

				.col-name {
					width: 380px;
					margin-left: 140px;
				}

				.col-price {
					width: 140px;
					text-align: center;
				}

				.col-count {
					width: 150px;
					text-align: center;
				}

				.col-subtotal {
					width: 200px;
					text-align: center;
				}

				.col-action {
					width: 100px;
					text-align: center;
				}
			}

			.cart-main {
				background-color: $bg-color;

				.goods {
					@extend %flex-align-center;
					border-top: 1px solid $border-color;
					height: 115px;
					line-height: 115px;

					label {
						width: 70px;
						padding-left: 30px;
						z-index: 1;
						cursor: pointer;

						/deep/ checkbox {
							margin-left: -9px;
							cursor: pointer;
						}
					}

					.thumb {
						width: 98px;
						height: 98px;
						margin-right: 40px;
						cursor: pointer;
					}

					.name {
						color: #424242;
						font-size: 18px;
						width: 360px;
						padding-right: 20px;
						cursor: pointer;
					}

					.price {
						color: #424242;
						font-size: 16px;
						width: 140px;
						text-align: center;
					}

					.subtotal {
						color: $color-primary;
						font-size: 16px;
						width: 200px;
						text-align: center;
					}

					.btn-close {
						width: 100px;
						text-align: center;

						.icon {
							color: #757575;
							font-size: 12px;
							font-weight: bold;
							cursor: pointer;
							border-radius: 50%;
							padding: 5px;
							transition: all .3s;

							&:hover {
								color: #FFFFFF;
								background-color: $color-error;
							}
						}
					}
				}
			}

			.cart-footer {
				z-index: 5;
				margin-top: 20px;
				background-color: $bg-color;
				@extend %flex-align-center;
				justify-content: space-between;
				height: 48px;

				&.fixed {
					position: fixed;
					bottom: 0;
					width: $page-width;
					margin: 0 auto;
					background-color: #fafafa;
					box-shadow: 0 -3px 6px rgba(0, 0, 0, 0.1);
					animation: show .3s forwards;

					@keyframes show {
						from {
							bottom: -100%;
						}

						to {
							bottom: 0;
						}
					}
				}

				.left {
					color: #757575;
					@extend %flex-align-center;

					.shopping-link {
						transition: color .2s;
						margin-left: 30px;

						&:hover {
							color: $color-primary;
						}
					}

					.delete-link {
						transition: color .2s;
						cursor: pointer;

						&:hover {
							color: $color-error;
						}
					}

					.separator {
						border-right: 1px solid #eee;
						height: 18px;
						margin: 0 20px;
					}

					.tips {
						.count {
							color: $color-primary;
							margin: 0 5px;
						}
					}
				}

				.right {
					position: relative;
					@extend %flex-align-center;

					.total-amount {
						color: $color-primary;

						.desc {
							margin-left: 2px;
						}

						.amount {
							font-size: 28px;
						}
					}

					.btn-primary {
						width: 200px;
						height: 48px;
						line-height: 48px;
						font-size: 18px;
						margin-left: 50px;
						color: #FFFFFF;
						background-color: $color-primary;

						&.disabled {
							background: #e0e0e0;
							color: #b0b0b0;
							cursor: not-allowed;
						}

						&:hover {
							opacity: .8;
						}
					}

					.nochecked-tip {
						width: 200px;
						height: 48px;
						line-height: 48px;
						position: absolute;
						top: -58px;
						right: 0;
						background-color: #fff;
						border: 1px solid $color-primary;
						color: $color-primary;
						text-align: center;

						&::before {
							content: '';
							position: absolute;
							bottom: -17px;
							right: 45%;
							border: 8px solid transparent;
							border-top-color: $color-primary;
						}

						&::after {
							content: '';
							position: absolute;
							bottom: -16px;
							right: 45%;
							border: 8px solid transparent;
							border-top-color: #FFFFFF;
						}
					}
				}
			}
		}
	}
</style>
>
