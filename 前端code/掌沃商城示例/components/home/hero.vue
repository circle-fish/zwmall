<template>
	<view v-if="banners.length > 0">
		<view class="container">

			<!-- 轮播图 -->
			<view class="banner-wrap">
				<swiper class="banner-swiper" :current="bannerIndex" :autoplay="autoplay" indicator-dots circular vertical @change="bannerChange">
					<swiper-item class="swiper-item" v-for="(item, index) in banners" :key="index">
						<navigator v-if="item.linkMode == 0" :url="`/pages/goods/index?id=${item.goodsId}`">
							<image class="cover" :src="item.pictureUrl"></image>
						</navigator>
						<a v-else :href="item.linkUrl" target="_blank">
							<image class="cover" :src="item.pictureUrl"></image>
						</a>
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

			<!-- 商品类别 -->
			<view class="category-wrap" @mouseleave="categoryIndex = -1;">
				<!-- 左侧类别列表 -->
				<view class="category-panel">
					<block v-for="(item, index) in categories" :key="index" v-if="index < 10">
						<view class="category" :class="{'active': categoryIndex == index}" @mouseenter="categoryIndex = index;">
							<view class="name">{{item.name}}</view>
							<i class="icon i-right"></i>
						</view>
					</block>
				</view>
				<!-- 右侧类别下属商品 -->
				<view class="goods-panel" v-if="categoryIndex > -1 && categories[categoryIndex].goodsList.length > 0" :style="{'width': goodsPanelWidth + 'px' }">
					<block v-for="(item, index) in categories[categoryIndex].goodsList" :key="index" v-if="index < 24">
						<navigator class="goods" :url="`/pages/goods/index?id=${item.id}`">
							<img class="thumb" :src="item.thumbUrl" />
							<view class="name elip">{{item.name}}</view>
						</navigator>
					</block>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				banners: [],
				bannerIndex: 0,
				categories: [],
				categoryIndex: -1,
				autoplay: true
			}
		},
		computed: {
			goodsPanelWidth() {
				if (this.categoryIndex > -1) {
					const goodsCount = this.categories[this.categoryIndex].goodsList.length;
					if (goodsCount > 0) {
						let columns = Math.floor(goodsCount / 6);
						if (goodsCount % 6 > 0) columns++;
						if (columns > 4) columns = 4;
						return columns * 248;
					}
				}
			}
		},
		created() {
			this.findBanners();
			this.findCategories();
		},
		methods: {
			findBanners() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/banner/slides', {}, (res) => {
					uni.hideLoading();
					this.banners = res;
				}, true);
			},
			findCategories() {
				this.$util.request('/category/heroCategories', {}, (res) => {
					this.categories = res;
				}, true);
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
			}
		}
	}
</script>

<style lang="scss">
	.container {
		width: $page-width;
		margin: 0 auto;
		position: relative;

		.banner-wrap {
			position: relative;

			/deep/ .uni-swiper-wrapper .uni-swiper-dots {
				right: 20px;
				left: auto;
				top: 92%;
				display: flex;
				transform: translateY(0);

				.uni-swiper-dot {
					width: 6px;
					height: 6px;
					margin: 0 5px;
					border: 2px solid;
					border-color: hsla(0, 0%, 100%, .3);
					border-radius: 10px;
					background: rgba(0, 0, 0, .4);

					&.uni-swiper-dot-active {
						background: hsla(0, 0%, 100%, .4);
						border-color: rgba(0, 0, 0, .4);
					}
				}
			}

			&,
			.banner-swiper,
			.cover {
				width: $page-width;
				height: 460px;
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
					left: 234px;
				}

				&.btn-right {
					right: 0;
				}
			}
		}

		.category-wrap {
			position: absolute;
			top: 0;
			left: 0;
			bottom: 0;
			display: flex;

			.category-panel {
				width: 234px;
				padding: 20px 0;
				background-color: rgba(0, 0, 0, .4);

				.category {
					@extend %flex-align-center;
					justify-content: space-between;
					padding-left: 30px;
					padding-right: 20px;
					height: 42px;
					line-height: 42px;
					cursor: pointer;

					&:hover,
					&.active {
						background-color: $color-primary;
					}

					.name {
						color: #FFFFFF;
					}

					.icon {
						color: #FFFFFF;
						margin-top: 3px;
					}
				}
			}

			.goods-panel {
				display: flex;
				flex-direction: column;
				flex-wrap: wrap;
				background-color: $bg-color;
				border: 1px solid $border-color;
				box-shadow: 0 8px 16px rgba(0, 0, 0, .18);

				.goods {
					@extend %flex-align-center;
					padding: 18px 20px;

					.thumb {
						width: 40px;
						height: 40px;
						margin-right: 12px;
					}

					.name {
						max-width: 150px;
						line-height: 40px;
						color: $text-color;
						cursor: pointer;

						&:hover {
							color: $color-primary;
						}
					}
				}
			}
		}
	}
</style>
