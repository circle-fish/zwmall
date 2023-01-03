<template>
	<view>
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="个人中心" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="2-2">
			<view class="title">喜欢的商品</view>
			<view v-if="goodsList.length > 0" class="goods-list">
				<view class="goods" v-for="(item, index) in goodsList" :key="index">
					<navigator :url="`/pages/goods/index?id=${item.goodsId}`">
						<image class="thumb" :src="item.thumbUrl" mode="aspectFill"></image>
						<view class="name elip">{{item.name}}</view>
					</navigator>
					<view class="price">{{item.price}}元</view>
					<view class="btn-wrap">
						<button class="btn btn-gray" @click="deleteGoods(item.id)">删除</button>
						<button class="btn btn-primary" @click="goDetail(item.goodsId)">查看详情</button>
					</view>
				</view>
			</view>
			<view v-else class="empty-tip">您暂未收藏任何商品。</view>
		</menu-wrapper>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';

	export default {
		components: {
			menuWrapper
		},
		data() {
			return {
				goodsList: []
			}
		},
		onLoad() {
			this.findGoodsList();
		},
		methods: {
			findGoodsList() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/favorite', {}, (res) => {
					uni.hideLoading();
					this.goodsList = res;
				}, true);
			},
			deleteGoods(id) {
				this.$alert('确定要删除吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/favorite/delete', {
						id
					}, (res) => {
						uni.hideLoading();
						this.findGoodsList();
					});
				});
			},
			goDetail(id) {
				uni.navigateTo({
					url: `/pages/goods/index?id=${id}`
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

	.goods-list {
		display: flex;
		flex-wrap: wrap;

		.goods {
			width: 260px;
			height: 348px;
			padding-top: 40px;
			padding-right: 34px;
			border-bottom: 1px solid #e0e0e0;
			text-align: center;

			&:hover {

				.btn-wrap {
					display: flex;
				}
			}

			.thumb {
				width: 200px;
				height: 200px;
			}

			.name {
				margin-top: 10px;
				max-width: 250px;
				color: $text-color;
				font-weight: 400;
			}

			.price {
				margin-top: 5px;
				color: $color-primary;
			}

			.btn-wrap {
				margin-top: 36px;
				@extend %flex-align-center;
				justify-content: center;
				display: none;

				.btn {
					width: 108px;
					height: 30px;
					line-height: 28px;
					margin: 0 7px;
					font-size: 12px;
					cursor: pointer;

					&.btn-gray {
						border: 1px solid #b0b0b0;
						background-color: #fff;
						color: #b0b0b0;

						&:hover {
							color: #fff;
							background-color: #757575;
							border-color: #757575;
						}
					}

					&.btn-primary {
						background-color: $color-primary;
						border: 1px solid $color-primary;
						color: #fff;

						&:hover {
							opacity: .8;
						}
					}
				}
			}
		}
	}

	.empty-tip {
		margin: 60px 0;
		font-size: 18px;
		text-align: center;
		color: #b0b0b0;
	}
</style>
