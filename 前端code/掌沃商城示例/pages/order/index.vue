<template>
	<view>
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="交易订单" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="1-1">
			<!-- 标题 -->
			<view class="title">我的订单</view>
			<!-- 订单筛选栏 -->
			<view class="filter-wrap">
				<view class="status-wrap">
					<block v-for="(item, index) in statuses" :key="index">
						<view :class="['status', {'active': index == statusIndex}]" @click="switchStatus(index)">
							{{item}}
						</view>
					</block>
				</view>
				<view class="search-wrap">
					<input class="input-search" placeholder="输入商品名称、订单号" v-model="keyword" @confirm="findOrders" maxlength="20" />
					<view class="btn-search" @click="findOrders">
						<i class="icon i-search"></i>
					</view>
				</view>
			</view>
			<!-- 订单列表 -->
			<view v-if="orders.length > 0" class="order-wrap">
				<block v-for="(item, index) in orders" :key="index">
					<view :class="['order', {'warning': item.status == 0}, {'green': item.status >= 2 && item.status <= 6}]">
						<view class="header-wrap">
							<view class="top">
								<view class="status">{{item.statusDesc}}</view>
								<view class="btn-delete" v-if="item.status == 0 || item.status == 40" @click="deleteOrder(item.id, index)">
									<i class="icon i-delete"></i>
								</view>
							</view>
							<view class="bottom">
								<view class="left">
									<view class="detail">{{item.createTime | filterTime}}</view>
									<view class="separator">|</view>
									<view class="detail">{{item.consignee}}</view>
									<view class="separator">|</view>
									<view class="detail">
										订单号：
										<navigator class="orderno" :url="`/pages/order/detail?orderNo=${item.orderNo}`">{{item.orderNo}}</navigator>
									</view>
									<block v-if="item.payMode">
										<view class="separator">|</view>
										<view class="detail">{{item.payMode}}</view>
									</block>
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
									<a :href="`/pages/goods/index?id=${goods.goodsId}`" target="_blank">
										<image class="thumb" :src="goods.thumbUrl" mode="aspectFit"></image>
									</a>
									<view class="detail">
										<a class="name elip" :href="`/pages/goods/index?id=${goods.goodsId}`" target="_blank">
											{{goods.name}}
										</a>
										<view class="more">{{goods.price}}元 x {{goods.count}}</view>
									</view>
								</view>
							</view>
							<view class="right">
								<navigator v-if="item.status == 0" class="btn btn-primary" :url="`/pages/order/pay/index?orderNo=${item.orderNo}`">立即付款</navigator>
								<view v-if="item.status == 6" class="btn btn-primary" @click="confirmReceipt(item)">确认已收货</view>
								<navigator class="btn" :url="`/pages/order/detail?orderNo=${item.orderNo}`">订单详情</navigator>
								<view v-if="item.status == 20" class="btn">申请售后</view>
								<view class="btn">联系客服</view>
							</view>
						</view>
					</view>
				</block>

				<!-- 分页条 -->
				<!-- <pagination :pageNumber="pageNumber" :totalSize="totalSize" @change="pageChange"></pagination> -->
			</view>
			<!-- 没有数据提示 -->
			<view v-else class="empty-tip">暂未查询到相关订单记录。</view>
		</menu-wrapper>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import pagination from '@/components/common/pagination.vue';

	export default {
		components: {
			menuWrapper,
			pagination
		},
		data() {
			return {
				statuses: ['全部订单', '待付款', '待收货', '已收货', '已取消'],
				statusIndex: 0,
				orders: [],
				keyword: '',
				// pageNumber: 1,
				// totalSize: 1
			}
		},
		filters: {
			filterTime(datetime) {
				const units = ['年', '月', '日'];
				datetime = datetime.split(' ');
				return datetime[0].split('-').map((e, i) => e + units[i]).join('') + ' ' + datetime[1];
			}
		},
		onLoad(e) {
			if (e.status) {
				try {
					const status = Number(e.status);
					if (status > 0 && status <= 4) {
						this.statusIndex = status;
					}
				} catch (e) {
					console.error(e);
				}
			}
            console.log("onshow");
			this.findOrders();
		},
		methods: {
			findOrders(isSwitchPage = false) {
				// if (!isSwitchPage) this.pageNumber = 1;

				uni.showLoading({
					title: '加载中...',
					mask: true
				});
			    console.log("=========");
			  //   uni.request({
			  //   	url:"http://192.168.208.196:8083/MYSSM_war_exploded/order/orders",
					// data:{
					// 	userId:this.$store.state.loginUser.id,
					// 	statusIndex:this.statusIndex
					// },
					// success:(res)=> {
					// 	console.log(res);
					// 	this.orders=res.data.data;
					// 	console.log(this.orders);
					// 	uni.hideLoading();
					// }
			  //   })
				
				this.$util.request('/order/orders', {
					userId:this.$store.state.loginUser.id,
					statusIndex:this.statusIndex
				}, (res) => {
					uni.hideLoading();
                    console.log(res);
					this.orders = res.data.data;
					// this.totalSize = res.totalRow;
				}, true);
			},
			switchStatus(index) {
				this.statusIndex = index;
				this.findOrders();
			},
			confirmReceipt(item) {
				this.$alert('确定已收货吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/order/confirmReceipt', {
						id: item.id
					}, (res) => {
						uni.hideLoading();

						if (res.state == 'ok') {
							item.status == 8;
							item.statusDesc = '已收货';

							this.$alert('操作成功', {
								showCancel: false
							});
						} else {
							this.$alert(res.msg, {
								showCancel: false
							});
						}
					});
				});
			},
			deleteOrder(id, index) {
				this.$alert('确定删除该订单吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/order/delete', {
						id
					}, (res) => {
						uni.hideLoading();

						if (res.state == 'ok') {
							this.orders.splice(index, 1);
						} else {
							this.$alert(res.msg);
						}
					});
				});
			},
			// pageChange(e) {
			// 	this.pageNumber = e;
			// 	this.findOrders(true);

			// 	uni.pageScrollTo({
			// 		scrollTop: 0,
			// 		duration: 0
			// 	});
			// }
		}
	}
</script>

<style lang="scss">
	.title {
		font-size: 30px;
		color: #757575;
		line-height: 68px;
	}

	.filter-wrap {
		padding: 16px 0;
		@extend %flex-align-center;
		justify-content: space-between;

		.status-wrap {
			@extend %flex-align-center;

			.status {
				font-size: 16px;
				line-height: 1.25;
				padding: 0 20px;
				border-left: 1px solid #e0e0e0;
				color: #757575;
				cursor: pointer;

				&:first-child {
					border-left: none;
					padding-left: 0;
				}

				&:hover {
					color: $text-color;
				}

				&.active {
					color: $color-primary;
				}
			}
		}

		.search-wrap {
			@extend %flex-align-center;

			&:hover {

				.input-search,
				.btn-search {
					border-color: #b0b0b0;
				}
			}

			.input-search {
				width: 189px;
				height: 40px;
				line-height: 40px;
				padding: 0 15px;
				border: 1px solid $border-color;
				font-size: 12px;
				color: #757575;
				transition: all .2s;
			}

			.btn-search {
				width: 40px;
				height: 40px;
				line-height: 40px;
				text-align: center;
				cursor: pointer;
				transition: all .2s;
				margin-left: -1px;
				border: 1px solid $border-color;

				.i-search {
					color: #616161;
					font-size: 18px;
					font-weight: bold;
				}

				&:hover {
					border-color: $color-primary;
					background-color: $color-primary;

					.i-search {
						color: #FFFFFF;
					}
				}
			}
		}
	}

	.order-wrap {

		.order {
			border: 1px solid #B0B0B0;
			margin-bottom: 20px;

			&.warning {
				border-color: $color-primary;

				.header-wrap {
					background-color: rgba($color-primary, .1);

					.status {
						color: $color-primary;
					}
				}
			}

			&.green {
				border-color: $color-success;

				.header-wrap {
					background-color: rgba($color-success, .1);

					.status {
						color: $color-success;
					}
				}
			}

			.header-wrap {
				padding: 25px 30px;
				border-bottom: 1px solid $border-color;

				.top {
					@extend %flex-align-center;
					justify-content: space-between;

					.status {
						color: #b0b0b0;
						font-size: 18px;
					}

					.btn-delete {
						margin-top: -15px;
						margin-right: -15px;
						cursor: pointer;

						.i-delete {
							font-size: 24px;
							color: #B0B0B0;
							opacity: 0;
						}
					}
				}

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

						.desc {
							color: #757575;
						}

						.amount {
							font-size: 28px;
							color: $text-color;
							margin-right: 5px;
						}
					}
				}
			}

			.main-wrap {
				display: flex;
				justify-content: space-between;
				padding: 20px 30px;
				padding-bottom: 0;

				.left {
					padding: 10px 0;

					.goods {
						@extend %flex-align-center;

						.thumb {
							width: 80px;
							height: 80px;
						}

						.detail {
							margin-left: 20px;
							color: $text-color;
							line-height: 18px;

							.name {
								color: $text-color;
								display: inline-block;
								cursor: pointer;
								max-width: 500px;

								&:hover {
									color: $color-primary;
								}
							}

							.more {
								font-size: 13px;
							}
						}
					}
				}

				.right {

					.btn {
						border: 1px solid #b0b0b0;
						background-color: $bg-color;
						color: #757575;
						width: 118px;
						height: 28px;
						font-size: 13px;
						line-height: 28px;
						text-align: center;
						cursor: pointer;
						transition: all .3s;
						margin-bottom: 10px;

						&.btn-primary {
							background-color: $color-primary;
							color: #FFFFFF;
							border-color: $color-primary;

							&:hover {
								background-color: $color-primary;
								color: #FFFFFF;
								border-color: $color-primary;
								opacity: .9;
							}
						}

						&:hover {
							color: #fff;
							background-color: #757575;
							border-color: #757575;
						}
					}
				}
			}

			&:hover {

				.header-wrap .top .btn-delete .icon {
					opacity: 1;
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
