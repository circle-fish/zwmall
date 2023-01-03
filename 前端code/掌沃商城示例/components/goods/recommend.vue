<template>
	<view class="recommend-wrapper" v-if="goodsList.length >= 1">
		<view class="wrap">

			<!-- 标题 -->
			<view :class="['box-header', {'noline': hiddenHeaderLine}]">
				<view class="title">{{title}}</view>
			</view>
			<!-- 商品列表 -->
			<view class="box-body">
				<view class="goodsList-wrap" :style="{'transform': `translateX(${-(pageNumber - 1) * 1226}px)`}">
					<block v-for="(item, index) in goodsList" :key="index">
						<navigator class="goods" :url="`/pages/goods/index?id=${item.id}`">
							<!-- <img class="thumb" :src="item.thumbUrl" /> -->
							<image class="thumb" src="../../static/image/home/th.png"></image>
							<view class="name elip">{{item.name}}</view>
							<view class="price">{{item.price}}元{{item.skuType == 0 ? '' : '起'}}</view>
							<view class="comment-count" v-if="item.commentCount">
								{{item.commentCount | countToSimpleValue}}人好评
							</view>
							<view class="btn-addcart" @click.stop="goSku(item)">立即购买</view>
						</navigator>
					</block>
				</view>
			</view>
			<!-- 切换按钮 -->
			<view class="box-footer">
				<block v-for="(item, index) in totalPage" :key="index">
					<view :class="['item', {'active': index + 1 == pageNumber}]" @click="switchPage(index + 1)">
						<view class="pager"></view>
					</view>
				</block>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		props: {
			title: {
				type: String,
				default: '为您推荐'
			},
			hiddenHeaderLine: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				goodsList: [],
				pageNumber: 1
			}
		},
		filters: {
			countToSimpleValue(count) {
				if (count > 10000) {
					let value = (count / 10000) + '';
					// 不四舍五入，保留一位小数
					if (count % 10000 != 0) value = value.substr(0, value.indexOf('.') + 2);
					return value + '万';
				}
				return count;
			}
		},
		computed: {
			totalPage() {
				return Math.ceil(this.goodsList.length / 5);
			}
		},
		created() {
			this.findRecommends();
		},
		methods: {
			findRecommends() {
				this.$util.request('/goods/recommends', {}, (res) => {
					this.goodsList = res.data.data;
				}, true);
			},
			goSku(goods) {
				uni.navigateTo({
					url: `/pages/goods/index?id=${goods.id}&active=sku`
				});
			},
			switchPage(pageNumber) {
				this.pageNumber = pageNumber;
			}
		}
	}
</script>

<style lang="scss">
	.recommend-wrapper {
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			padding-bottom: 100px;

			%header-line {
				position: absolute;
				content: '';
				border-top: 1px solid $border-color;
				top: 50%;
				width: 30%;
			}

			.box-header {
				position: relative;
				text-align: center;
				margin-bottom: 20px;

				.title {
					height: 50px;
					font-size: 30px;
					font-weight: 400;
					color: #757575;
				}

				&::before {
					@extend %header-line;
					left: 0;
				}

				&::after {
					@extend %header-line;
					right: 0;
				}

				&.noline {

					&::before,
					&::after {
						border-top: none;
					}
				}
			}

			.box-body {
				width: $page-width;
				overflow: hidden;

				.goodsList-wrap {
					display: flex;
					transition: transform .5s;

					.goods {
						flex-shrink: 0;
						width: 232px;
						height: 300px;
						margin-right: 14px;
						margin-bottom: 14px;
						background-color: $bg-color;
						text-align: center;

						&:hover {
							.comment-count {
								display: none;
							}

							.btn-addcart {
								display: block;
							}
						}

						.thumb {
							width: 140px;
							height: 140px;
							cursor: pointer;
							padding: 40px 0 15px;
							height: 145px;
						}

						.name {
							color: $text-color;
							max-width: 210px;
							margin-bottom: 10px;
							font-weight: 400;
							cursor: pointer;
							padding: 0 10px;
						}

						.price {
							margin-bottom: 10px;
							color: $color-primary;
						}

						.comment-count {
							color: #757575;
						}

						.btn-addcart {
							border: 1px solid $color-primary;
							background-color: $bg-color;
							color: $color-primary;
							width: 120px;
							height: 28px;
							line-height: 28px;
							font-size: 12px;
							margin: 0 auto;
							cursor: pointer;
							transition: all .3s;
							display: none;

							&:hover {
								background-color: $color-primary;
								color: #FFFFFF;
							}
						}
					}
				}
			}

			.box-footer {
				@extend %flex-align-center;
				justify-content: center;
				margin-top: 10px;

				.item {
					width: 30px;
					height: 30px;
					cursor: pointer;
					margin: 0 10px;
					@extend %flex-align-center;
					justify-content: center;

					&:hover {
						.pager {
							background-color: $color-primary;
						}
					}

					&.active {
						cursor: default;

						.pager {
							background-color: transparent;
							border: 2px solid $color-primary;
						}
					}

					.pager {
						width: 5px;
						height: 5px;
						background-color: $bg-color-gray;
						border-radius: 50%;
					}
				}
			}
		}
	}
</style>
