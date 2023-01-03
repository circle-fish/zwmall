<template>
	<view class="wrapper" v-if="category.name">
		<view class="wrap">

			<view class="box-header">
				<view class="name">{{category.name}}</view>
				<a class="btn-link" :href="`/pages/search/index?categoryId=${category.id}`" target="_blank">
					<text class="title">查看全部</text>
					<i class="icon i-right"></i>
				</a>
			</view>
			<view class="box-body">
				<!-- 商品大图 -->
				<view class="cover-wrapper">
					<block v-for="(item, index) in category.adverts" :key="index" v-if="index < 2">
						<navigator v-if="item.linkMode == 0" :url="`/pages/goods/index?id=${item.goodsId}`">
							<img class="cover" :src="item.pictureUrl" :style="{'height': coverHeight}" />
						</navigator>
						<a v-else :href="item.linkUrl" target="_blank">
							<img class="cover" :src="item.pictureUrl" :style="{'height': coverHeight}" />
						</a>
					</block>
				</view>
				<!-- 商品列表 -->
				<view class="goods-wrapper">
					<block v-for="(item, index) in category.goodsList" :key="index" v-if="index < 8">
						<navigator class="goods" :url="`/pages/goods/index?id=${item.id}`">
							<img class="thumb" :src="item.thumbUrl"></img>
							<view class="name elip">{{item.name}}</view>
							<view class="desc elip">{{item.title}}</view>
							<view class="prices">
								<view class="price-current">{{item.price}}元{{item.skuType == 0 ? '' : '起'}}</view>
								<view class="price-original" v-if="item.originalPrice && item.price < item.originalPrice">
									{{item.originalPrice}}元
								</view>
							</view>
						</navigator>
					</block>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		props: {
			category: {
				type: Object,
				default () {
					return {};
				}
			}
		},
		computed: {
			coverHeight() {
				return this.category.adverts.length > 1 ? '300px' : '614px';
			}
		}
	}
</script>

<style lang="scss">
	.wrapper {
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;

			.box-header {
				@extend %flex-align-center;
				justify-content: space-between;

				.name {
					font-size: 22px;
					font-weight: 200;
					line-height: 58px;
					color: #333;
				}

				.btn-link {
					cursor: pointer;

					&:hover {
						.title {
							color: $color-primary;
						}

						.icon {
							background-color: $color-primary;
						}
					}

					.title {
						font-size: 16px;
						line-height: 58px;
						color: #424242;
						transition: all .4s;
					}

					.icon {
						width: 12px;
						height: 12px;
						padding: 4px;
						margin-left: 8px;
						border-radius: 16px;
						font-size: 12px;
						line-height: 12px;
						background-color: #b0b0b0;
						color: #fff;
						transition: all .4s;
					}
				}
			}

			.box-body {
				display: flex;

				.cover-wrapper {
					width: 234px;
					height: 614px;
					display: flex;
					flex-direction: column;
					justify-content: space-between;

					.cover {
						width: 234px;
						cursor: pointer;
						transition: all .2s linear;

						&:hover {
							box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
							transform: translate3d(0, -2px, 0);
						}
					}
				}

				.goods-wrapper {
					width: 992px;
					display: flex;
					flex-wrap: wrap;

					.goods {
						width: 234px;
						height: 260px;
						padding: 20px 0;
						margin-left: 14px;
						margin-bottom: 14px;
						text-align: center;
						background-color: $bg-color;
						cursor: pointer;
						transition: all .2s linear;

						&:hover {
							box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
							transform: translate3d(0, -2px, 0);
						}

						.thumb {
							width: 160px;
							height: 160px;
							margin: 0 auto 18px;
						}

						.name {
							margin: 0 10px 2px;
							font-weight: 400;
							color: #333;
						}

						.desc {
							height: 18px;
							margin: 0 10px 10px;
							font-size: 12px;
							color: #b0b0b0;
						}

						.prices {
							@extend %flex-align-center;
							justify-content: center;

							.price-current {
								color: $color-primary;
							}

							.price-original {
								color: #b0b0b0;
								margin-left: 5px;
								text-decoration: line-through;
							}
						}
					}
				}
			}
		}
	}
</style>
