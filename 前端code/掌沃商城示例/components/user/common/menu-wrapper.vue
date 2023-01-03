<template>
	<view class="menu-wrapper">
		<view class="wrap">

			<!-- 左侧菜单 -->
			<view class="menu-wrap">
				<view class="menu-group" v-for="(item, index) in menus" :key="index">
					<view class="title">{{item.title}}</view>
					<view class="menu-list">
						<block v-for="(menu, menuIndex) in item.list" :key="menuIndex">
							<navigator :class="['menu', {'active': isActive(index, menuIndex)}]" :url="menu.linkUrl">{{menu.name}}</navigator>
						</block>
					</view>
				</view>
			</view>
			<!-- 右侧内容 -->
			<view class="main-wrap">
				<slot></slot>
			</view>

		</view>
	</view>
</template>

<script>
	import menus from '@/common/config/user/left-menus.config.js';

	export default {
		props: {
			activeIndex: {
				type: String,
				default: '1-1',
				validator(value) {
					return /^[\d]-[\d]$/.test(value);
				}
			}
		},
		data() {
			return {
				menus,
				indexArray: []
			}
		},
		created() {
			if (!this.$store.getters.hasLogin) {
				const pages = getCurrentPages();
				const url = pages[pages.length - 1].route;
				uni.redirectTo({
					url: `/pages/user/login/index?callbackUrl=/${url}`
				});
			} else {
				this.indexArray = this.activeIndex.split('-').map(e => e - 1);
			}
		},
		methods: {
			isActive(index, menuIndex) {
				return index == this.indexArray[0] && menuIndex == this.indexArray[1];
			}
		}
	}
</script>

<style lang="scss">
	.menu-wrapper {
		padding-bottom: 40px;
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			display: flex;

			.menu-wrap {
				width: 234px;
				padding: 36px 0;
				background-color: $bg-color;

				.menu-group {
					margin: 0 48px 12px;

					.title {
						font-size: 16px;
						font-weight: 400;
						line-height: 52px;
						color: #333;
					}

					.menu-list {

						.menu {
							padding: 6px 0;
							color: #757575;
							cursor: pointer;

							&:hover {
								color: $text-color;
							}

							&.active {
								color: $color-primary;
							}
						}
					}
				}
			}

			.main-wrap {
				flex: 1;
				margin-left: 14px;
				background-color: $bg-color;
				padding: 36px 48px;
			}
		}
	}
</style>
