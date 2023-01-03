<template>
	<view class="mini-cart" @mouseenter="showMiniCart = true;" @mouseleave="showMiniCart = false;">
		<navigator :class="['cart-trigger', {'active': hasGoods}]" url="/pages/cart/index">
			<i class="icon i-cart"></i>
			<text class="title">购物车（{{totalGoods}}）</text>
		</navigator>
		<!-- 购物车列表 -->
		<view class="cart-container">
			<view v-if="!hasGoods" class="cart-empty" :class="{'active': showMiniCart}">购物车中还没有商品，赶紧选购吧！</view>
			<view v-else class="goods-box" :style="{'height': (showMiniCart ? (goodsList.length > 4 ? 470 : goodsList.length * 90 + 70) : 0) + 'px'}">
				<!-- 商品列表 -->
				<view class="goods-list">
					<view class="item" v-for="(item, index) in goodsList" :key="index">
						<navigator class="goods-link" :url="'/pages/goods/index?id=' + item.goodsId">
							<img class="thumb" :src="item.thumbUrl" />
							<text class="name elip">{{item.name}}</text>
						</navigator>
						<view class="price">{{item.price}}元 x {{item.count}}</view>
						<view class="btn-close">
							<i class="icon i-close" @click="removeGoods(item.id)"></i>
						</view>
					</view>
				</view>
				<!-- 购物车信息 -->
				<view class="cart-info">
					<view class="total">
						<view class="count">
							共<text style="margin: 0 3px;">{{totalGoods}}</text>件商品
						</view>
						<view class="price">
							<text class="value">{{totalPrice}}</text>元
						</view>
					</view>
					<navigator class="btn-cart" url="/pages/cart/index">去购物车结算</navigator>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapGetters
	} from 'vuex';

	export default {
		data() {
			return {
				showMiniCart: false
			}
		},
		computed: {
			...mapState(['cart']),
			...mapGetters(['hasLogin']),
			goodsList() {
				return this.cart.filter(e => e.checked);
			},
			totalGoods() {
				let count = 0;
				this.goodsList.forEach(e => {
					count += e.count;
				});
				return count;
			},
			hasGoods() {
				return this.totalGoods > 0;
			},
			totalPrice() {
				let price = 0;
				this.goodsList.forEach(e => {
					price += e.price * e.count;
				});
				return price.toFixed(2);
			}
		},
		activated() {
			this.showMiniCart = false;
		},
		methods: {
			removeGoods(id) {
				this.$cart.removeGoods(id).catch(msg => {
					this.$alert(msg, {
						showCancel: false
					});
				});
			}
		}
	}
</script>

<style lang="scss">
	.mini-cart {
		z-index: 7;
		position: relative;
		text-align: center;
		margin-left: 1.5rem;

		&:hover {
			.cart-trigger {
				color: $color-primary !important;
				background-color: #FFFFFF !important;
			}
		}

		.cart-trigger {
			position: relative;
			z-index: 10;
			width: 120px;
			height: 40px;
			line-height: 40px;
			cursor: pointer;
			transition: all .2s;
			color: #b0b0b0;
			background: #424242;

			.title {
				margin-left: 5px;
			}

			&.active {
				color: #FFFFFF;
				background-color: $color-primary;
			}
		}

		.cart-container {
			z-index: 5;
			position: absolute;
			right: 0;
			width: 316px;
			color: #424242;
			background-color: $bg-color;
			box-shadow: 0 2px 10px rgba(0, 0, 0, .15);

			.cart-empty {
				height: 0;
				overflow: hidden;
				line-height: 100px;
				transition: height .3s;

				&.active {
					height: 100px;
				}
			}

			.goods-box {
				overflow: hidden;
				transition: height .3s;

				.goods-list {
					padding: 0 20px;
					max-height: 400px;
					overflow-y: auto;

					.item {
						color: #424242;
						height: 60px;
						padding: 15px 0;
						@extend %flex-align-center;
						border-bottom: 1px solid $border-color;

						&:last-child {
							border: none;
						}

						.goods-link {
							cursor: pointer;
							@extend %flex-align-center;

							.thumb {
								width: 60px;
								height: 60px;
							}

							.name {
								max-width: 100px;

								&:hover {
									color: $color-primary;
								}
							}
						}

						.price {
							margin-left: auto;
							margin-top: -20px;
						}

						.btn-close {
							cursor: pointer;
							width: 30px;

							.i-close {
								color: #b0b0b0;
								font-size: 12px;
								display: none;

								&:hover {
									color: #424242;
								}
							}
						}

						&:hover {
							.i-close {
								display: block;
							}
						}
					}
				}

				.cart-info {
					background-color: #fafafa;
					@extend %flex-align-center;
					padding: 15px 20px;

					.total {
						text-align: left;
						margin-right: auto;

						.count {
							color: #757575;
							font-size: 12px;
							line-height: 1;
						}

						.price {
							line-height: 1;
							margin-top: 5px;

							.value {
								color: $color-primary;
								font-size: 24px;
								font-weight: 400;
								margin-right: 2px;
							}
						}
					}

					.btn-cart {
						width: 130px;
						line-height: 40px;
						color: #F5F5F5;
						cursor: pointer;
						background-color: $color-primary;
					}
				}
			}
		}
	}
</style>
