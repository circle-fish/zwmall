<template>
	<view>

		<!-- 顶部导航栏 -->
		<view class="top-navbar">
			<view class="wrap">
				<!-- 左侧手机操作系统链接 -->
				<view class="links os-links">
					<block v-for="(item, index) in osLinks" :key="index">
						<a class="link" :href="item.url" target="_blank">{{item.name}}</a>
						<text v-if="index < osLinks.length - 1" class="separator">|</text>
					</block>
				</view>
				<!-- 右侧用户相关链接 -->
				<view class="links user-links">
					<block v-if="hasLogin">
						<user-navs></user-navs>
					</block>
					<block v-else>
						<view class="link" @click="showAnnouncement('login')">登录</view>
						<text class="separator">|</text>
						<view class="link" @click="showAnnouncement('register')">注册</view>
					</block>
					<!-- 购物车 -->
					<mini-cart></mini-cart>
				</view>
			</view>
		</view>
		<!-- 头部主体 -->
		<view class="header-main" @mouseleave="closeGoodsList">
			<view class="container">
				<view class="header-left">
					<!-- logo -->
					<logo></logo>
					<!-- 全部商品链接 -->
					<navigator class="all-link" url="/pages/search/index?keyword=all">全部商品</navigator>
				</view>
				<!-- 品牌列表 -->
				<view class="header-brands" @mouseleave="closeGoodsList">
					<block v-for="(item, index) in brands" :key="index" v-if="index < 9">
						<navigator class="nav-item" :url="`/pages/search/index?brandId=${item.id}`" @mouseenter="openGoodsList(index)">
							{{item.name}}
						</navigator>
					</block>
				</view>
				<!-- 搜索区域 -->
				<view class="header-search">
					<!-- 搜索框 -->
					<view class="search-box" :class="{'active': searchFocus}">
						<view class="input-container">
							<!-- 输入框 -->
							<input class="search-input" @focus="keywordIndex = -1;searchFocus = true;" @blur="onSearchBlur" @input="searchKeywords"
							 @confirm="goSearch(false)" @keydown.up="chooseKeyword('up')" @keydown.down="chooseKeyword('down')" v-model="keyword" />
							<!-- 推荐商品 -->
							<view class="hot-goodsList" v-if="!searchFocus && !keyword">
								<block v-for="(item, index) in hotGoodsList" :key="index" v-if="index < 2">
									<navigator class="item elip" :url="`/pages/goods/index?id=${item.id}`">{{item.name}}</navigator>
								</block>
							</view>
						</view>
						<view class="search-btn" :class="{'active': searchFocus}" @click="goSearch(false)">
							<i class="icon i-search"></i>
						</view>
					</view>
					<!-- 提示列表 -->
					<view class="keyword-list" v-if="searchFocus && promptKeywords.length > 0">
						<block v-for="(item, index) in promptKeywords" :key="index" v-if="index < 10">
							<view :class="['item elip', {'active': keywordIndex == index}]" @click="goSearch(item)">{{item}}</view>
						</block>
					</view>
				</view>
			</view>
			<!-- 商品面板 -->
			<view class="goods-panel" :class="{'active': showGoodsList && brandIndex > -1 && brands[brandIndex].goodsList.length > 0}"
			 @mouseenter="stopClose">
				<view class="goods-container" v-if="brandIndex > -1">
					<block v-for="(goods, index) in brands[brandIndex].goodsList" :key="index" v-if="index < 6">
						<navigator class="goods" :url="`/pages/goods/index?id=${goods.id}`">
							<image class="thumb" :src="goods.thumbUrl" mode="aspectFit"></image>
							<view class="name elip">{{goods.name}}</view>
							<view class="price">{{goods.price}}元{{goods.skuType == 0 ? '' : '起'}}</view>
						</navigator>
						<view class="separator" v-if="index < 6 && index < brands[brandIndex].goodsList.length - 1"></view>
					</block>
				</view>
			</view>
		</view>

		<!-- 登录注册前的协议声明 -->
		<licenses :hidden.sync="hiddenAnnouncement" @agreed="agreedAgreements"></licenses>

	</view>
</template>

<script>
	import logo from '@/components/common/logo.vue';
	import userNavs from '@/components/user/common/top-navs.vue';
	import miniCart from '@/components/cart/mini-cart.vue';
	import licenses from '@/components/user/common/licenses.vue';

	import headerConfig from '@/common/config/common/header.config.js';

	import {
		mapGetters
	} from 'vuex';

	export default {
		components: {
			logo,
			userNavs,
			miniCart,
			licenses
		},
		props: {
			searchKeyword: {
				type: String,
				default: ''
			}
		},
		data() {
			return {
				osLinks: headerConfig.osLinks,
				brands: [],
				brandIndex: -1,
				showGoodsList: false,
				searchFocus: false,
				hotGoodsList: [],
				promptKeywords: [],
				keyword: this.searchKeyword,
				keywordIndex: -1,
				hiddenAnnouncement: true
			}
		},
		watch: {
			searchKeyword(val) {
				this.keyword = val;
			},
			keywordIndex(index) {
				const keyword = this.promptKeywords[index];
				if (keyword) this.keyword = keyword;
			}
		},
		activated() {
			this.keyword = this.searchKeyword;
		},
		computed: {
			...mapGetters(['hasLogin']),
			currentRoute() {
				const pages = getCurrentPages();
				return pages[pages.length - 1].route;
			}
		},
		created() {
			this.findHeaderBrands();
			this.findHotGoodsList();
		},
		methods: {
			findHeaderBrands() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/brand/headerBrands', {}, (res) => {
					uni.hideLoading();
					this.brands = res.data.data;
				}, true);
			},
			findHotGoodsList() {
				this.$util.request('/goods/hotGoodsList', {}, (res) => {
					res.forEach((e, i) => {
						// 填充热销商品
						if (i < 2) this.hotGoodsList.push(e);
						// 填充热搜词
						this.promptKeywords.push(e.name);
					});
					this.keywords = this.promptKeywords;
				}, true);
			},
			showAnnouncement(page) {
				this.goPage = page;
				this.hiddenAnnouncement = false;
			},
			agreedAgreements() {
				uni.navigateTo({
					url: `/pages/user/${this.goPage}/index?callbackUrl=/${this.currentRoute}`,
					success: () => {
						this.hiddenAnnouncement = true;
					}
				});
			},
			openGoodsList(index) {
				this.brandIndex = index;
				this.showGoodsList = true;
			},
			closeGoodsList() {
				this.showGoodsList = false;
				this.timer = setTimeout(() => {
					this.brandIndex = -1;
				}, 300);
			},
			stopClose() {
				this.showGoodsList = true;
				clearTimeout(this.timer);
			},
			searchKeywords(e) {
				this.keywordIndex = -1;
				const value = e.detail.value;

				if (value) {
					this.$util.request('/goods/promptKeywords', {
						keyword: value
					}, (res) => {
						this.promptKeywords = res;
					}, true);
				} else {
					this.promptKeywords = this.keywords;
				}
			},
			chooseKeyword(direction = 'up') {
				if (direction == 'up') {
					if (this.keywordIndex <= 0) {
						this.keywordIndex = this.promptKeywords.length - 1;
					} else {
						this.keywordIndex--;
					}
				} else {
					if (this.keywordIndex >= this.promptKeywords.length - 1) {
						this.keywordIndex = 0;
					} else {
						this.keywordIndex++;
					}
				}
			},
			onSearchBlur() {
				setTimeout(() => {
					this.searchFocus = false;
				}, 200);
			},
			goSearch(keyword) {
				if (keyword) this.keyword = keyword;
				if (!this.keyword) return;
				uni.navigateTo({
					url: `/pages/search/index?keyword=${this.keyword}`
				});
			}
		}
	}
</script>

<style lang="scss">
	.top-navbar {
		width: 100%;
		position: relative;
		height: 40px;
		font-size: 12px;
		background-color: $text-color;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			@extend %flex-align-center;
			justify-content: space-between;

			.links {
				height: 40px;
				line-height: 40px;
				@extend %flex-align-center;

				.link {
					color: #b0b0b0;
					cursor: pointer;
					line-height: 40px;

					&:hover {
						color: #FFFFFF;
					}
				}

				.separator {
					margin: 0 .4rem;
					color: #424242;
					line-height: 40px;
				}
			}
		}
	}

	.header-main {
		position: relative;
		height: 100px;

		.container {
			width: $page-width;
			margin: 0 auto;
			@extend %flex-align-center;

			.header-left {
				width: 234px;
				@extend %flex-align-center;
				justify-content: space-between;

				.all-link {
					color: #333333;
					font-size: 16px;
					cursor: pointer;
					margin-right: 10px;

					&:hover {
						color: $color-primary;
					}
				}
			}

			.header-brands {
				height: 100px;
				padding-left: 10px;
				@extend %flex-align-center;

				.nav-item {
					margin: 0 15px;
					cursor: pointer;
					height: 100px;
					line-height: 100px;

					&:hover {
						color: $color-primary;
					}
				}
			}

			.header-search {
				margin-left: auto;
				position: relative;

				.search-box {
					@extend %flex-align-center;
					border: 1px solid $border-color;
					width: 296px;
					height: 50px;
					transition: all .2s;

					&:hover {
						border: 1px solid #b0b0b0;

						.search-btn {
							border-left: 1px solid #b0b0b0;
						}
					}

					&.active {
						border: 1px solid $color-primary;
					}

					.input-container {
						position: relative;

						.search-input {
							width: 223px;
							height: 48px;
							padding: 0 10px;
							line-height: 48px;
						}

						.hot-goodsList {
							position: absolute;
							top: 16px;
							right: 10px;
							@extend %flex-align-center;

							.item {
								display: block;
								max-width: 85px;
								margin-left: 5px;
								padding: 0 5px;
								font-size: 12px;
								background: #eee;
								color: #757575;
								transition: all .2s;
								cursor: pointer;

								&:hover {
									color: #FFFFFF;
									background-color: $color-primary;
								}
							}
						}
					}

					.search-btn {
						border-left: 1px solid $border-color;
						@extend %flex-align-center;
						justify-content: center;
						width: 52px;
						height: 50px;
						color: #616161;
						cursor: pointer;
						transition: all .2s;

						.icon {
							font-size: 20px;
							font-weight: bold;
						}

						&:hover {
							color: #FFFFFF;
							background-color: $color-primary;
							border: 1px solid $color-primary;
							margin-right: -1px;
						}

						&.active {
							border-left: 1px solid $color-primary;
						}
					}
				}

				.keyword-list {
					z-index: 6;
					position: absolute;
					left: 0;
					width: 243px;
					border: 1px solid $color-primary;
					border-top: none;
					background-color: #FFFFFF;

					.item {
						max-width: 220px;
						font-size: 12px;
						padding: 6px 15px;
						cursor: pointer;

						&:hover {
							background-color: #fafafa;
						}

						&.active {
							background-color: #fafafa;
						}
					}
				}
			}
		}

		.goods-panel {
			background-color: #FFFFFF;
			z-index: 5;
			position: absolute;
			top: 100%;
			left: 0;
			right: 0;
			height: 0;
			overflow: hidden;
			transition: box-shadow .2s, height .3s ease-in;

			&.active {
				height: 229px;
				border-top: 1px solid $border-color;
				box-shadow: 0 3px 4px rgba(0, 0, 0, .18);
			}

			.goods-container {
				width: $page-width;
				margin: 0 auto;
				@extend %flex-align-center;

				.goods {
					text-align: center;
					cursor: pointer;
					font-size: 12px;
					padding: 35px 12px 25px;

					&:first-child {
						margin-left: 50px;
					}

					.thumb {
						width: 160px;
						height: 110px;
						margin-bottom: 15px;
					}

					.name {
						color: #333;
						line-height: 20px;
						max-width: 200px;
					}

					.price {
						color: $color-primary;
						line-height: 20px;
					}
				}

				.separator {
					height: 100px;
					border-right: 1px solid $border-color;
					margin-top: -50px;
				}
			}
		}
	}
</style>
