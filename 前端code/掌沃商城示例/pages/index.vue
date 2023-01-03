<template>
	<view>

		<!-- 顶部横幅 -->
		<banner v-if="topBanner" :banner="topBanner" top></banner>

		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 商品类别及轮播图  -->
		<hero></hero>

		<!-- 相关通道及推荐海报 -->
		<hero-sub></hero-sub>

		<!-- 右侧工具栏 -->
		<tool-bar :scrollTop="scrollTop"></tool-bar>

		<!-- 闪购 -->
		<flash-sale></flash-sale>

		<block v-for="(item, index) in 7" :key="index">
			<!-- 横幅 -->
			<banner v-if="index < adverts.length" :banner="adverts[index]"></banner>
			<!-- 商品类别下的商品展示 -->
			<brick v-if="index < brickCategories.length" :category="brickCategories[index]"></brick>
		</block>

		<!-- 视频展示 -->
		<video-box></video-box>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import hero from '@/components/home/hero.vue';
	import heroSub from '@/components/home/hero-sub.vue';
	import toolBar from '@/components/home/tool-bar.vue';
	import flashSale from '@/components/home/flash-sale.vue';
	import banner from '@/components/home/banner.vue';
	import brick from '@/components/home/brick.vue';
	import videoBox from '@/components/home/video-box.vue';

	import LazyLoad from '@/common/tools/lazyload.js';

	export default {
		components: {
			hero,
			heroSub,
			toolBar,
			flashSale,
			banner,
			brick,
			videoBox
		},
		data() {
			return {
				topBanner: null,
				scrollTop: 0,
				adverts: [],
				brickCategories: []
			}
		},
		onLoad() {
			this.findTopBanner();
			this.findAdverts();
			this.findBrickCategories();
		},
		onShow() {
			// 监听滚动
			window.addEventListener('scroll', this.onScroll);
		},
		onHide() {
			// 取消滚动监听
			window.removeEventListener('scroll', this.onScroll);
		},
		methods: {
			/**
			 * 解决onPageScroll有时不触发的问题
			 */
			onScroll() {
				this.scrollTop = window.scrollY;
			},
			findTopBanner() {
				this.$util.request('/banner/top', {}, (res) => {
					if (res && res.length > 0) this.topBanner = res[0];
				}, true);
			},
			findAdverts() {
				this.$util.request('/banner/adverts', {}, (res) => {
					this.adverts = res;
				}, true);
			},
			findBrickCategories() {
				this.$util.request('/category/brickCategories', {}, (res) => {
					this.brickCategories = res;

					setTimeout(() => {
						new LazyLoad('img');
					}, 500);
				}, true);
			}
		}
	}
</script>