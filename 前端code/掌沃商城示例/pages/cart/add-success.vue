<template>
	<view>

		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 加入成功提示 -->
		<view class="success-wrapper">
			<view class="wrap">
				<img class="success-icon" src="https://c1.mifile.cn/f/i/17/static/success.png" />
				<view class="success-info">
					<view class="title">已成功加入购物车！</view>
					<view class="goods-name">{{goodsName}}</view>
				</view>
				<view class="btn-wrap">
					<navigator class="btn" open-type="navigateBack">返回上一级</navigator>
					<navigator class="btn btn-primary" url="/pages/cart/index">去购物车结算</navigator>
				</view>
			</view>
		</view>

		<!-- 商品推荐 -->
		<recommend title="买购物车中商品的人还买了" hiddenHeaderLine></recommend>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import recommend from '@/components/goods/recommend.vue';

	export default {
		components: {
			recommend
		},
		data() {
			return {
				goodsName: ''
			}
		},
		onLoad(e) {
			if (e.goodsName) {
				this.goodsName = decodeURIComponent(e.goodsName);
			} else {
				uni.redirectTo({
					url: '/pages/index'
				});
			}
		}
	}
</script>

<style lang="scss">
	.success-wrapper {
		display: flex;
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			padding: 38px 0;
			@extend %flex-align-center;
			border-bottom: 1px solid $border-color;
			margin-bottom: 20px;

			.success-icon {
				width: 64px;
				height: 64px;
			}

			.success-info {
				margin-left: 20px;

				.title {
					color: #424242;
					font-size: 24px;
				}

				.goods-name {
					color: #757575;
				}
			}

			.btn-wrap {
				margin-left: auto;
				@extend %flex-align-center;

				.btn {
					width: 180px;
					margin-left: 12px;
					border: 1px solid #b0b0b0;
					background-color: $bg-color-grey;
					color: #757575;
					line-height: 38px;
					text-align: center;
					cursor: pointer;
					transition: all .4s;

					&:hover {
						background-color: $bg-color;
					}

					&.btn-primary {
						color: #FFFFFF;
						border-color: $color-primary;
						background-color: $color-primary;

						&:hover {
							opacity: .8;
							background-color: $color-primary;
						}
					}
				}
			}
		}
	}
</style>
