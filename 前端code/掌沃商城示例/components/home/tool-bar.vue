<template>
	<view class="tool-wrapper" @mouseleave="activeIndex = -1;">
		<block v-for="(item, index) in tools" :key="index" v-if="!onlyGotopBtn && index < 5">
			<a class="tool" :href="item.linkUrl || 'javascript:void(0);'" :target="item.linkUrl ? '_blank' : '_self'" :style="{'cursor': item.linkUrl ? 'pointer' : 'default'}"
			 @mouseenter="activeIndex = index;">
				<img class="icon" :src="activeIndex == index ? item.iconActiveUrl : item.iconUrl" />
				<view class="name">{{item.name}}</view>
			</a>
		</block>

		<!-- 回到顶部按钮 -->
		<view v-show="scrollTop > 1000" :class="['tool btn-totop', {'onlyme': onlyGotopBtn}]" @mouseenter="activeIndex = 5;" @click="goTop">
			<img class="icon" :src="activeIndex == 5 ? tools[5].iconActiveUrl : tools[5].iconUrl" />
			<view class="name">{{tools[5].name}}</view>
		</view>
		
	</view>
</template>

<script>
	import toolBarConfig from '@/common/config/home/tool-bar.config.js';

	export default {
		props: {
			scrollTop: {
				type: Number,
				default: 0
			},
			onlyGotopBtn: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				...toolBarConfig,
				activeIndex: -1
			}
		},
		methods: {
			goTop() {
				uni.pageScrollTo({
					scrollTop: 0,
					duration: 100
				});
			}
		}
	}
</script>

<style lang="scss">
	.tool-wrapper {
		position: fixed;
		right: 0;
		top: 50%;
		transform: translateY(-50%);
		z-index: 10;
		display: none;
		
		@media screen and (min-width: 1406px) {
			display: block !important;
		}

		.tool {
			display: block;
			width: 82px;
			height: 90px;
			margin-top: -1px;
			border: 1px solid #f5f5f5;
			text-align: center;
			background-color: #FFFFFF;

			&:hover {
				.name {
					color: #ff6700;
				}
			}

			&.btn-totop {
				cursor: pointer;
				margin-top: 14px;
				
				&.onlyme {
					margin-top: 500px;
				}
			}

			.icon {
				width: 30px;
				height: 30px;
				margin-top: 18px;
			}

			.name {
				color: #757575;
			}
		}
		
		%arrow {
			content: '';
			position: absolute;
			top: 20px;
			border: 8px solid transparent;
		}

	}
</style>
