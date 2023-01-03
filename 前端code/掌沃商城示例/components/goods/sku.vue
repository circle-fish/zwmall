<template>
	<view>

		<!-- 登录提示 -->
		<view class="login-notic" v-if="!hasLogin && showTips">
			<text class="title">为方便您购买，请提前登录</text>
			<view class="btn-login" @click="goLogin">立即登录</view>
			<i class="icon i-close" @click="showTips = false;"></i>
		</view>

		<view class="goods-wrapper" v-if="goods.id">
			<view class="wrap">

				<!-- 轮播图 -->
				<view class="banner-wrap" v-if="banners.length > 0">
					<swiper class="banner-swiper" :current="bannerIndex" :autoplay="autoplay" indicator-dots circular @change="bannerChange"
						ref="banner">
					<swiper-item class="swiper-item" v-for="(item, index) in banners" :key="index">
						<image class="cover" :src="item" mode="aspectFill"></image>
					</swiper-item>
					</swiper>
					<!-- 左右切换箭头 -->
					<view class="btn btn-left" @click="switchBanner(false)">
						<i class="icon i-left"></i>
					</view>
					<view class="btn btn-right" @click="switchBanner(true)">
						<i class="icon i-right"></i>
					</view>
				</view>

				<!-- 图片放大镜 -->
				<canvas v-if="magnifier.show" canvas-id="magnifier" class="magnifier" :style="{'top': magnifier.top + 'px', 'left': magnifier.left + 'px'}"></canvas>

				<!-- 商品详情 -->
				<view class="goods-main">
					<view class="goods-name">{{goods.name}}</view>
					<view class="goods-desc">{{goods.introduction}}</view>
					<view class="goods-price">{{currentPrice}}元</view>
					<view class="sku-list">
						<view class="sku-wrap" v-for="(item, index) in specs" :key="index">
							<view class="title">选择{{item.name}}</view>
							<view class="items">
								<block v-for="(attr, attrIndex) in item.items" :key="attrIndex">
									<view :class="['item', {'active': attrIndex == skuIndexs[index]}]" @click="switchSku(index, attrIndex)">
										<image v-if="index == specs.length - 1" class="thumb" :src="getThumb(attrIndex)" mode="aspectFill"></image>
										<!-- <image class="thumb" src="../../static/image/common/logo.png"></image> -->
										<text class="name elip">{{attr}}</text>
									</view>
								</block>
							</view>
						</view>
					</view>
					<view class="current-goods">
						<view class="goods-info">
							<view class="name">{{fullName}}</view>
							<view class="price-wrap">
								<view class="price">{{currentPrice}}元</view>
								<view class="price original" v-if="originalPrice && originalPrice > currentPrice">{{originalPrice}}元</view>
							</view>
						</view>
						<view class="subtotal">总计：{{currentPrice}}元</view>
					</view>
					<view class="btn-wrap">
						<button :class="['btn-primary', {'disabled': sellout}]" @click="putToCart">
							{{sellout ? '该商品已售罄' : '加入购物车'}}
						</button>
					</view>
				</view>

			</view>
		</view>

		<!-- 价格说明 -->
		<view class="price-desc" v-if="goods.id">
			<view class="wrap">
				<view class="title">价格说明</view>
				<image class="image" src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/a482afa34053b1b32ece1023475af7fb.jpeg" mode="aspectFill"></image>
			</view>
		</view>

	</view>
</template>

<script>
	import {
		mapGetters
	} from 'vuex';
	import store from '@/store/index.js';

	export default {
		props: {
			goodsId: Number
		},
		data() {
			return {
				tmp: ["../../static/image/home/th.png"],
				params: {},
				goods: {},
				skus: [],
				skuIndex: 0,
				skuIndexs: [],
				skuDisabled: false,
				bannerIndex: 0,
				autoplay: true,
				showTips: true,
				liked: false,
				magnifier: {
					show: false,
					top: 0,
					left: 0
				}
			}
		},
		computed: {
			...mapGetters(['hasLogin']),
			specs() {
				this.attrs = this.skus.map(e => JSON.parse(e.goodsAttr));
				const keys = this.attrs[0].map(e => e.key);
				const specs = [];
				keys.forEach(key => {
					const items = new Set();
					this.attrs.forEach(attr => {
						attr.forEach(obj => {
							if (obj.key == key) items.add(obj.value);
						});
					});
					specs.push({
						name: key,
						items: Array.from(items)
					});
					this.skuIndexs.push(0);
				});
				return specs;
			},
			banners() {
				//return this.tmp;
				console.log(this.skus[this.skuIndex].bannerUrls.split(','));
				return this.skus[this.skuIndex].bannerUrls.split(',');
			},
			currentPrice() {
				//return this.goods.price;
				return this.skus[this.skuIndex].price;
			},
			originalPrice() {
				return this.skus[this.skuIndex].originalPrice;
				//return this.goods.price;
			},
			fullName() {
				let name = this.goods.name;
				if (this.goods.skuType == 1) {
					this.specs.forEach((spec, i) => {
						name += ' ' + spec.items[this.skuIndexs[i]];
					});
				}
				return name;
			},
			sellout() {
				return this.skus[this.skuIndex].sellout || this.skuDisabled;
			},
			skuId() {
				return this.skus[this.skuIndex].id;
			}
		},
		created() {
			this.findSkus();
		},
		mounted() {
			this.$set(this.params, 'isShow', false);
			this.findSkus();
			this.initMagnifier();
		},
		methods: {
			handleShow() {
			    // 更新对象中的属性值使v-if生效
			    this.$set(this.params, 'isShow', true)
			},
			findSkus() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/goods/detail', {
					detail: this.goodsId
				}, (res) => {
					uni.hideLoading();
					if (res.data.code == '200') {
						this.$set(this,'goods',res.data.data.goods);
						this.$set(this,'skus',res.data.data.skus);
					} else {
						this.$alert(res.data.msg, {
							showCancel: false,
							confirmText: '我知道了'
						}, () => {
							uni.redirectTo({
								url: '/pages/index'
							});
						});
					}
				}, true);
			},
			getThumb(index) {
				return this.skus[index].bannerUrls.split(',')[0];
			},
			switchSku(index, attrIndex) {
				this.skuIndexs[index] = attrIndex;
				this.$set(this.skuIndexs, index, attrIndex);
				//获取当前每级规格的值
				const values = [];
				this.specs.forEach((spec, i) => {
					values.push(spec.items[this.skuIndexs[i]]);
				});

				const skuIndex = this.attrs.findIndex(items => {
					let count = 0;
					values.forEach((val, i) => {
						if (val == items[i].value) {
							count++;
						}
					});
					return count == values.length;
				});

				// 找到skuIndex，找不到则不改变skuIndex，表现为库存为空
				if (skuIndex != -1) {
					this.skuIndex = skuIndex;
					this.bannerIndex = 0;
				}
				this.skuDisabled = skuIndex == -1;
			},
			bannerChange(e) {
				this.bannerIndex = e.detail.current;
			},
			switchBanner(toNext) {
				this.autoplay = false;

				if (toNext) {
					if (this.bannerIndex < this.banners.length - 1) {
						this.bannerIndex++;
					} else {
						this.bannerIndex = 0;
					}
				} else {
					if (this.bannerIndex == 0) {
						this.bannerIndex = this.banners.length - 1;
					} else {
						this.bannerIndex--;
					}
				}

				setTimeout(() => {
					this.autoplay = true;
				}, 2000);
			},
			initMagnifier() {
				setTimeout(() => {
					const banner = this.$refs.banner.$el;
					const ctx = uni.createCanvasContext('magnifier');

					banner.onmousemove = e => {
						this.autoplay = false;
						this.magnifier = {
							show: true,
							top: e.y + 20,
							left: e.x + 20
						};

						ctx.scale(3, 3);
						ctx.drawImage(e.srcElement.currentSrc, -e.offsetX / 3 - 10, -e.offsetY / 2 + 50, 300, 300);
						ctx.draw();
					}

					banner.onmouseleave = () => {
						this.magnifier.show = false;
						this.autoplay = true;
					}
				}, 1000);
			},
			goLogin() {
				uni.navigateTo({
					url: '/pages/user/login/index?callbackUrl=' + encodeURIComponent(
						`/pages/goods/index?id=${this.goods.id}&active=sku`)
				});
			},
			putToCart() {
				console.log("putToCart");
				if (this.sellout) return;

				// 提交购物车
				new Promise((resolve, reject) => {
					if (!this.hasLogin) {
						console.log("nologin");
						this.goLogin();
						return;
					} else {
						uni.showLoading({
							title: '处理中...',
							mask: true
						});
						console.log("request");
						console.log(this.goodsId);
						console.log(this.skuId);
						console.log(store.getters.token);
						this.$util.request('/cart/addcart', {
							goodsid: this.goodsId,
							skuid: this.skuId,
							token: store.getters.token
						}, (res) => {
							uni.hideLoading();
							if (res.data.code == '200') {
								this.$alert('加入购物车成功', {
									showCancel: false
								});
								resolve();
							} else {
								this.$alert(res.msg, {
									showCancel: false
								});
							}
						});
					}
				}).then(() => {
					console.log("dispatch");
					// 封装商品信息
					const goods = {
						id: this.skuId,
						goodsId: this.goodsId,
						name: this.fullName,
						thumbUrl: this.banners[0],
						price: this.currentPrice,
						maxCount: this.goods.maxBuy,
						checked: true,
						count: 1
					};
					this.$store.dispatch('putGoods', goods).then(() => {
						console.log("dispatch");
						uni.navigateTo({
							url: '/pages/cart/add-success?goodsName=' + encodeURIComponent(goods.name)
						});
					}).catch(() => {
						this.$alert('商品加入购物车数量超过限购数', {
							showCancel: false
						});
					});
				});
			}
		}
	}
</script>

<style lang="scss">
	.login-notic {
		background-color: $bg-color-grey;
		width: 100%;
		height: 48px;
		line-height: 48px;
		@extend %flex-align-center;
		justify-content: center;

		.title {
			color: #333;
		}

		.btn-login {
			color: #ff6700;
			margin-left: 25px;
			cursor: pointer;
		}

		.icon {
			color: #b5b5b5;
			font-size: 12px;
			font-weight: bold;
			margin-left: 10px;
			cursor: pointer;
		}
	}

	.goods-wrapper {
		background-color: $bg-color;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			display: flex;
			padding-top: 32px;

			.banner-wrap {
				width: 560px;
				height: 560px;
				position: relative;
				margin-right: 58px;

				.banner-swiper,
				.cover {
					width: 560px;
					height: 560px;
					cursor: pointer;
				}

				.btn {
					position: absolute;
					top: 50%;
					width: 41px;
					height: 69px;
					transform: translateY(-50%);
					cursor: pointer;
					border-radius: 2px;
					@extend %flex-align-center;
					justify-content: center;

					&:hover {
						background-color: rgba(0, 0, 0, .6);
					}

					.icon {
						font-size: 30px;
						color: $border-color;
					}

					&.btn-left {
						left: 0;
					}

					&.btn-right {
						right: 0;
					}
				}

				/deep/ .uni-swiper-dots {
					bottom: 0;

					.uni-swiper-dot {
						display: inline-block;
						width: 50px;
						height: 2.5px;
						margin-right: 10px;
						background-color: #ccc;
						cursor: pointer;
						border-radius: 0;

						&.uni-swiper-dot-active {
							background-color: #a3a3a3;
						}
					}
				}
			}

			.magnifier {
				z-index: 1;
				width: 300px;
				height: 300px;
				position: fixed;
				overflow: hidden;
				border-radius: 50%;
				border: 1px solid #999;
				background-color: #FFFFFF;
			}

			.goods-main {

				.goods-name {
					font-size: 24px;
					font-weight: normal;
					color: #212121;
					line-height: 1;
				}

				.goods-desc {
					color: #b0b0b0;
					padding-top: 8px;
				}

				.goods-price {
					font-size: 18px;
					line-height: 1;
					color: $color-primary;
					border-bottom: 1px solid #e0e0e0;
					padding: 20px 0;
				}

				.sku-list {
					margin-top: 40px;

					.sku-wrap {
						margin-bottom: 30px;

						.title {
							line-height: 1;
							font-size: 18px;
						}

						.items {
							display: flex;
							flex-wrap: wrap;
							width: 612px;
							overflow-x: hidden;

							.item {
								height: 42px;
								width: 298px;
								line-height: 42px;
								margin-top: 15px;
								margin-right: 6px;
								cursor: pointer;
								font-size: 16px;
								transition: all .2s ease;
								@extend %flex-align-center;
								justify-content: center;
								border: 1px solid $border-color;

								&.active {
									border-color: $color-primary;

									.name {
										color: $color-primary;
									}

									.price {
										color: #757575;
									}
								}

								.name {
									display: inline-block;
									max-width: 170px;
									color: $text-color;
								}

								.thumb {
									width: 16px;
									height: 16px;
									margin-right: 10px;
								}

								.price {
									color: #b0b0b0;
									margin-left: 20px;
								}
							}
						}
					}
				}

				.current-goods {
					background: #f9f9fa;
					padding: 30px;
					margin-bottom: 30px;

					.goods-info {
						@extend %flex-align-center;
						justify-content: space-between;
						line-height: 30px;
						color: #616161;

						.price-wrap {
							@extend %flex-align-center;

							.original {
								text-decoration: line-through;
								margin-left: 5px;
							}
						}
					}

					.subtotal {
						color: $color-primary;
						font-size: 24px;
						padding-top: 20px;
						line-height: 30px;
					}
				}

				.btn-wrap {
					@extend %flex-align-center;

					.btn-primary {
						width: 300px;
						height: 54px;
						line-height: 54px;
						font-size: 16px;
						color: #FFFFFF;
						background-color: $color-primary;
						text-align: center;
						margin: 0;

						&:hover {
							opacity: .8;
						}

						&.disabled {
							cursor: not-allowed;
							background-color: $bg-color-gray;
						}
					}

				}
			}
		}
	}

	.price-desc {
		margin-top: 50px;
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			padding-bottom: 50px;

			.title {
				font-size: 22px;
				font-weight: normal;
				padding: 1em 0;
			}

			.image {
				width: 100%;
				height: 189px;
			}
		}
	}
</style>
