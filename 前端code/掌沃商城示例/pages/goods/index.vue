<template>
	<view>

		<!-- 页首 -->
		<zw-header></zw-header>
		<!-- 导航栏 -->
		<view class="goods-header" v-if="details.id" :class="{'fixed': scrollTop > 120}">
			<view class="wrap">
				<view class="goods-name">{{details.name}}</view>
				<view class="navs-bar">
					<block v-for="(item, index) in navs" :key="index">
						<view :class="['item', {'active': item.key == activeKey}]" @click="switchNav(item.key)">{{item.name}}</view>
						<view v-if="index < navs.length - 1" class="separator">|</view>
					</block>
					<view v-if="activeKey != 'sku'" class="btn-primary" @click="switchNav('sku')">立即购买</view>
				</view>
			</view>
		</view>

		<!-- 概述 -->
		<block v-if="activeKey == 'sketch'">
			<view class="goods-sketch" v-if="details.sketchType == 0">
				<img v-for="(item, index) in details.sketch" :key="index" :src="item" />
			</view>
			<view class="goods-sketch" v-else v-html="details.sketch"></view>
		</block>
		
		<!-- 参数 -->
		<view v-else-if="details.specs && activeKey == 'specs'" class="goods-specs" v-html="details.specs"></view>
		<!-- 评论 -->
		<comment v-else-if="activeKey == 'comment'" :goodsId="details.id" :scrollTop="scrollTop"></comment>
		<!-- 规格 -->
		<sku v-else-if="activeKey == 'sku'" :goodsId="details.id"></sku>
		<!-- 右侧工具栏 -->
		<tool-bar :scrollTop="scrollTop" onlyGotopBtn></tool-bar>
		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import toolBar from '@/components/home/tool-bar.vue';
	import comment from '@/components/goods/comment.vue';
	import sku from '@/components/goods/sku.vue';

	import LazyLoad from '@/common/tools/lazyload.js';

	export default {
		components: {
			toolBar,
			comment,
			sku
		},
		data() {
			return {
				navs: [{
						name: '概述',
						key: 'sketch'
					},
					{
						name: '参数',
						key: 'specs'
					},
					{
						name: '用户评价',
						key: 'comment'
					}
				],
				activeKey: 'sketch',
				details: {},
				scrollTop: 0
			}
		},
		onLoad(e) {
			if (!e.id) {
				uni.redirectTo({
					url: '/pages/index'
				});
				return;
			}

			this.findGoodsDetail(e.id, e.active);
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
		},
		methods: {
			findGoodsDetail(id, activeKey) {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/goods/detail', {
					detail: id
				}, (res) => {
					uni.hideLoading();
					// 判断该商品是否存在
					console.log("==========");
					console.log(res);
					if (res && res.data.data.goods.id) {
						if (res.data.data.goods.sketchType == 0) res.data.data.goods.sketch = res.data.data.goods.sketch.split(',');
						this.details = res.data.data.goods;
						if (!res.data.data.goods.specs) {
							this.navs.splice(1, 1);
						}
						if (activeKey && (activeKey == 'sku' || this.navs.findIndex(e => e.key == activeKey) != -1)) {
							this.activeKey = activeKey;
						}

						setTimeout(() => {
							// 修改网站标题
							uni.setNavigationBarTitle({
								title: `${res.data.data.goods.name} - xx商城`
							});
							// 开启懒加载
							if (this.activeKey == 'sketch') new LazyLoad('img');
						}, 100);
						
					} else {
						this.$alert('当前商品不存在或已下架', {
							cancelText: '返回首页',
							confirmText: '看看其它商品'
						}, () => {
							uni.redirectTo({
								url: '/pages/search/index'
							});
						}, () => {
							uni.redirectTo({
								url: '/pages/index'
							});
						});
					}
				}, true);
			},
			switchNav(key) {
				if (key != this.activeKey) {
					this.activeKey = key;

					uni.pageScrollTo({
						scrollTop: 0,
						duration: 0
					});
				}
			}
		}
	}
</script>

<style lang="scss">
	@import '@/common/styles/simditor';

	.goods-header {
		position: relative;
		background-color: $bg-color;
		border-top: 1px solid $border-color;
		border-bottom: 1px solid $border-color;
		box-shadow: 0 5px 5px rgba(0, 0, 0, .07);
		z-index: 1;

		&.fixed {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			animation: open .5s forwards;

			@keyframes open {
				from {
					margin-top: -50%;
				}

				to {
					margin-top: 0;
				}
			}
		}

		.wrap {
			width: $page-width;
			margin: 0 auto;
			@extend %flex-align-center;
			justify-content: space-between;

			.goods-name {
				font-size: 18px;
				font-weight: 400;
				line-height: 60px;
				color: #424242;
			}

			.navs-bar {
				@extend %flex-align-center;

				.item {
					cursor: pointer;
					color: #616161;
					transition: color .2s;

					&.active,
					&:hover {
						color: $color-primary;
					}

					&.active {
						cursor: default;
					}
				}

				.separator {
					color: #e0e0e0;
					margin: 0 .5em;
				}

				.btn-primary {
					color: #fff;
					margin-left: 10px;
					background-color: $color-primary;
					width: 120px;
					height: 30px;
					font-size: 12px;
					line-height: 30px;
					cursor: pointer;
					text-align: center;
					transition: all .3s;

					&:hover {
						opacity: .8;
					}
				}
			}
		}
	}

	.goods-sketch {
		width: $page-width;
		margin: 0 auto;
		margin-top: 10px;

		/deep/ img {
			width: 100%;
			margin-top: -4px;
			object-fit: cover;
		}
	}

	.goods-specs {
		width: $page-width;
		margin: 0 auto;
		margin-top: 10px;
		
		/deep/ img {
			max-width: 100%;
			object-fit: cover;
		}
	}
</style>
