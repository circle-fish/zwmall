<template>
	<view class="warpper" :class="{'top': top}" v-if="banner.pictureUrl">
		<navigator v-if="banner.linkMode == 0" class="warp" :url="`/pages/goods/index?id=${banner.goodsId}`">
			<img class="banner" :src="banner.pictureUrl" />
		</navigator>
		<a v-else class="warp" :href="banner.linkUrl" target="_blank">
			<img class="banner" :src="banner.pictureUrl" />
		</a>
	</view>
</template>

<script>
	export default {
		props: {
			banner: {
				type: Object,
				default () {
					return {};
				}
			},
			top: {
				type: Boolean,
				default: false
			}
		}
	}
</script>

<style lang="scss">
	.warpper {
		background-color: $bg-color-grey;
		
		&.top {
			
			.warp, .banner {
				width: 100% !important;
				padding: 0 !important;
			}
			
			.banner {
				margin-bottom: -5px;
				height: auto !important;
				max-height: 200px;
			}
		}

		.warp {
			width: $page-width;
			display: block;
			margin: 0 auto;
			padding: 22px 0;
			cursor: pointer;

			.banner {
				width: $page-width;
				height: 120px;
			}
		}
	}
</style>
