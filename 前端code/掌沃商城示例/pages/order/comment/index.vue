<template>
	<view>
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="订单评价" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="1-2">
			<!-- 标题 -->
			<view class="title">订单评价</view>
			<!-- 待评价订单列表 -->
			<view v-if="orders.length > 0" class="order-wrap">
				<block v-for="(item, index) in orders" :key="index">
					<view class="order">
						<view class="header-wrap">
							<view class="bottom">
								<view class="left">
									<view class="detail">下单时间：{{item.createTime}}</view>
									<view class="separator"></view>
									<view class="detail">
										订单号：
										<navigator class="orderno" :url="`/pages/order/detail?orderNo=${item.orderNo}`">{{item.orderNo}}</navigator>
									</view>
								</view>
								<view class="right">
									<text class="desc">实付金额：</text>
									<text class="amount">{{item.payAmount.toFixed(2)}}</text>
									<text class="desc">元</text>
								</view>
							</view>
						</view>
						<view class="main-wrap">
							<view class="left">
								<view class="goods" v-for="(goods, goodsIndex) in item.goodsList" :key="goodsIndex">
									<navigator :url="`/pages/goods/index?id=${goods.goodsId}`">
										<image class="thumb" :src="goods.thumbUrl" mode="aspectFit"></image>
									</navigator>
								</view>
								<view class="tip">共{{item.goodsList.length}}种商品</view>
							</view>
							<view class="right">
								<navigator class="btn" :url="`/pages/order/detail?orderNo=${item.orderNo}`">订单详情</navigator>
								<navigator class="btn btn-primary" :url="`/pages/order/comment/detail?orderNo=${item.orderNo}`">去评价</navigator>
							</view>
						</view>
					</view>
				</block>
			</view>
			<!-- 没有数据提示 -->
			<view v-else class="empty-tip">您还没有待评价订单。</view>
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
				orders: []
			}
		},
		onLoad() {
			this.findOrders();
		},
		methods: {
			findOrders() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/order/comment', {},(res) => {
					uni.hideLoading();
					
					this.orders = res;
				}, true);
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
	
	.order-wrap {
	
		.order {
			border: 1px solid #B0B0B0;
			margin-bottom: 20px;
	
			.header-wrap {
				padding: 15px 30px;
				border-bottom: 1px solid $border-color;
	
				.bottom {
					@extend %flex-align-center;
					justify-content: space-between;
	
					.left {
						color: #757575;
						max-width: 600px;
						@extend %flex-align-center;
	
						.separator {
							color: $border-color;
							margin: 0 .5rem;
						}
						
						.orderno {
							display: inline-block;
	
							&:hover {
								color: $color-primary;
							}
						}
					}
	
					.right {
						color: #757575;
	
						.amount {
							font-size: 24px;
							margin-right: 5px;
						}
					}
				}
			}
	
			.main-wrap {
				@extend %flex-align-center;
				justify-content: space-between;
				padding: 20px 30px;
				padding-bottom: 0;
	
				.left {
					padding-top: 10px;
					@extend %flex-align-center;
					flex-wrap: wrap;
	
					.goods {
						margin-right: 10px;
						margin-bottom: 10px;
						
						.thumb {
							width: 80px;
							height: 80px;
						}
					}
					
					.tip {
						color: $text-color;
						font-size: 12px;
						margin-left: 10px;
						margin-bottom: 10px;
					}
				}
	
				.right {
	
					.btn {
						color: #757575;
						width: 118px;
						height: 28px;
						font-size: 13px;
						line-height: 28px;
						text-align: center;
						cursor: pointer;
						transition: all .3s;
						margin-bottom: 5px;
						
						&.btn-primary {
							color: #FFFFFF;
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
	
	.empty-tip {
		margin: 60px 0;
		font-size: 18px;
		text-align: center;
		color: #b0b0b0;
	}
</style>
