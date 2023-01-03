<template>
	<view v-if="promos.length > 0">
		<view class="container">

			<!-- 通道列表 -->
			<view class="channel-wrap">
				<block v-for="(item, index) in channels" :key="index">
					<a class="channel" target="_blank">
						<img class="thumb" :src="item.thumbUrl" />
						<view class="name">{{item.name}}</view>
					</a>
				</block>
			</view>

			<!-- 海报区域 -->
			<view class="promo-wrap">
				<block v-for="(item, index) in promos" :key="index">
					<navigator v-if="item.linkMode == 0" class="promo" :url="`/pages/goods/index?id=${item.goodsId}`">
						<img class="cover" :src="item.pictureUrl" />
					</navigator>
					<a v-else class="promo" :href="item.linkUrl" target="_blank">
						<img class="cover" :src="item.pictureUrl" />
					</a>
				</block>
			</view>

		</view>
	</view>
</template>

<script>
	import heroSubConfig from '@/common/config/home/hero-sub.config.js';

	export default {
		data() {
			return {
				channels: heroSubConfig.channels,
				promos: []
			}
		},
		created() {
			this.findPromos();
		},
		methods: {
			findPromos() {
				this.$util.request('/banner/promos', {}, (res) => {
					this.promos = res;
				}, true);
			}
		}
	}
</script>

<style lang="scss">
	.container {
		width: $page-width;
		margin: 14px auto 0;
		display: flex;

		.channel-wrap {
			width: 234px;
			height: 170px;
			background-color: #5f5750;
			display: flex;
			flex-wrap: wrap;

			.channel {
				@extend %flex-align-center;
				flex-direction: column;
				justify-content: center;
				width: 78px;
				height: 82px;
				position: relative;
				cursor: pointer;
				opacity: .7;
				transition: opacity .2s;

				&:hover {
					opacity: 1;
				}

				.thumb {
					width: 24px;
					height: 24px;
					margin-bottom: 4px;
				}

				.name {
					font-size: 12px;
					color: #fff;
				}

				&::before {
					@extend %border;
					top: 0;
					left: 6px;
					right: 6px;
					border-top: 1px solid #665e57;
				}

				&::after {
					@extend %border;
					top: 6px;
					bottom: 6px;
					right: 0;
					border-right: 1px solid #665e57;
				}
			}

			%border {
				position: absolute;
				content: '';
			}
		}

		.promo-wrap {
			margin-left: auto;
			display: flex;

			.promo {
				width: 316px;
				height: 170px;
				margin-right: 15px;
				transition: box-shadow .2s;

				&:last-child {
					margin-right: 0;
				}

				&:hover {
					box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
				}

				.cover {
					width: 316px;
					height: 170px;
				}
			}
		}
	}
</style>
