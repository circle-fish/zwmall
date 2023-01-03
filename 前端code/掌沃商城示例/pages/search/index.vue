<template>
	<view>

		<!-- 页首 -->
		<zw-header :searchKeyword="keyword == 'all' ? '' : keyword"></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="搜索"></bread-crumb>

		<!-- 类别栏 -->
		<filter-list name="类别" :list="categories" :activeIndex="categoryIndex" @change="categoryChange"></filter-list>
		<!-- 品牌栏 -->
		<filter-list name="品牌" :list="brands" :activeIndex="brandIndex" separator @change="brandChange"></filter-list>

		<!-- 主体内容 -->
		<view class="search-wrapper">
			<view class="wrap">
				<!-- 商品列表 -->
				<view class="goods-wrap" v-if="goodsList.length > 0">
					<block v-for="(item, index) in goodsList" :key="index">
						<navigator class="goods" :url="`/pages/goods/index?id=${item.id}`">
							<img class="thumb" :src="item.thumbUrls[item.activeThumb || 0]" />
							<view class="name elip">{{item.name}}</view>
							<view class="price">{{item.price}}元{{item.skuType == 1 ? '起' : ''}}</view>
							<view class="thumbs">
								<block v-for="(thumb, thumbIndex) in item.thumbUrls" :key="thumbIndex">
									<image :class="['thumb-mini', {'active': item.activeThumb == thumbIndex && item.thumbUrls.length > 1}]" :src="thumb"
									 mode="aspectFit" @mouseenter="thumbSwitch(index, item, thumbIndex)"></image>
								</block>
							</view>
						</navigator>
					</block>
				</view>
				<view class="goods-empty" v-else>抱歉，没有找到符合条件的商品~</view>

				<!-- 分页条 -->
				<pagination :pageNumber="pageNumber" :totalSize="totalSize" @change="pageChange"></pagination>
			</view>
		</view>

		<!-- 商品推荐 -->
		<recommend title="猜你喜欢"></recommend>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import filterList from '@/components/search/filter-list.vue';
	import pagination from '@/components/common/pagination.vue';
	import recommend from '@/components/goods/recommend.vue';

	export default {
		components: {
			filterList,
			pagination,
			recommend
		},
		data() {
			return {
				keyword: '',
				categories: [{
					id: -1,
					name: '全部'
				}],
				categoryIndex: 0,
				categoryId: -1,
				brands: [{
					id: -1,
					name: '全部'
				}],
				brandIndex: 0,
				brandId: -1,
				sortIndex: 0,
				priceUp: true,
				goodsList: [],
				pageNumber: 1,
				totalSize: 0
			}
		},
		onLoad(e) {
			if (e.keyword) this.keyword = e.keyword;
			if (e.categoryId) this.categoryId = e.categoryId;
			if (e.brandId) this.brandId = e.brandId;

			this.initFilters();
			this.search();
		},
		computed: {
			sortMode() {
				const sortKeys = ['new', 'praise', 'sales', 'price-up', 'price-down'];
				let index = this.sortIndex;
				if (this.sortIndex == this.sortModes.length - 1) index = this.priceUp ? 3 : 4;
				return sortKeys[index];
			}
		},
		methods: {
			initFilters() {
				this.$util.request('/category', {}, (res) => {
					this.categories.push(...res);
					
					if (this.categoryId != -1) {
						this.categoryIndex = this.categories.findIndex(e => e.id == this.categoryId);
					}
				}, true);
				this.$util.request('/brand/allbrand', {}, (res) => {
					this.brands.push(...res);
					
					if (this.brandId != -1) {
						this.brandIndex = this.brands.findIndex(e => e.id == this.brandId);
					}
				}, true);
			},
			search(isSwitchPage = false) {
				if (!isSwitchPage) this.pageNumber = 1;

				uni.showLoading({
					title: '搜索中...',
					mask: true
				});
				this.$util.request('/goods/search', {
					keyword: this.keyword,
					categoryId: this.categoryId,
					brandId: this.brandId
				}, (res) => {
					uni.hideLoading();
					// 获取商品列表
					this.goodsList = res.data.data.map(e => {
						// 定义图片数组
						if (e.thumbUrls) {
							e.thumbUrls = e.thumbUrls.split(',');
							// 最多取3张图片
							if (e.thumbUrls.length > 3) e.thumbUrls.length = 3;
						} else {
							e.thumbUrls = [e.thumbUrl];
						}
						return e;
					});
					// 获取总数量
					this.totalSize = res.totalRow;
				}, true);
			},
			categoryChange(index) {
				this.categoryIndex = index;
				this.categoryId = this.categories[index].id;
				this.search();
			},
			brandChange(index) {
				this.brandIndex = index;
				this.brandId = this.brands[index].id;
				this.search();
			},
			sortChange(index) {
				if (index == this.sortIndex) {
					if (index == 3) this.priceUp = !this.priceUp;
					else return;
				} else {
					this.sortIndex = index;
					if (!this.priceUp) this.priceUp = true;
				}
				this.search();
			},
			thumbSwitch(index, item, thumbIndex) {
				item.activeThumb = thumbIndex;
				// 触发页面更新
				this.$set(this.goodsList, index, item);
			},
			pageChange(e) {
				this.pageNumber = e;
				this.search(true);

				uni.pageScrollTo({
					scrollTop: 0,
					duration: 0
				});
			}
		}
	}
</script>

<style lang="scss">
	.search-wrapper {
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			padding: 20px 0 100px;
			background-color: $bg-color-grey;

			.goods-wrap {
				display: flex;
				flex-wrap: wrap;
				width: 1240px;

				.goods {
					width: 296px;
					height: 352px;
					padding-top: 42px;
					margin-right: 14px;
					margin-bottom: 14px;
					background-color: $bg-color;
					transition: box-shadow .5s linear;
					display: flex;
					flex-direction: column;
					align-items: center;

					&:hover {
						box-shadow: 0 5px 15px rgba(0, 0, 0, .2);
					}

					.thumb {
						width: 200px;
						height: 200px;
						cursor: pointer;
						margin-bottom: 16px;
					}

					.name {
						color: $text-color;
						width: 230px;
						margin-bottom: 3px;
						font-weight: 400;
						cursor: pointer;
						text-align: center;
					}

					.price {
						margin-bottom: 15px;
						color: $color-primary;
					}

					.thumbs {
						@extend %flex-align-center;

						.thumb-mini {
							width: 34px;
							height: 34px;
							margin: 0 4px;
							border: 1px solid #e0e0e0;
							cursor: pointer;
							transition: border-color .2s linear;

							&.active,
							&:hover {
								border-color: $color-primary;
							}
						}
					}
				}
			}

			.goods-empty {
				color: $text-color;
				margin-top: 1rem;
			}
		}
	}
</style>
