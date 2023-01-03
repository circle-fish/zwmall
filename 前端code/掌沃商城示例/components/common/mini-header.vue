<template>
	<view class="header-wrapper">
		<view class="wrap">
			<logo></logo>
			<view class="title">{{title}}</view>
			<view class="tips" v-if="tips">{{tips}}</view>
			<view class="top-navbar">
				<block v-if="hasLogin">
					<user-navs isWhiteBack></user-navs>
				</block>
				<block v-else>
					<navigator class="nav-item" :url="`/pages/user/login/index?callbackUrl=${callbackUrl}`">登录</navigator>
					<view class="separator">|</view>
					<navigator class="nav-item" :url="`/pages/user/register/index?callbackUrl=${callbackUrl}`">注册</navigator>
				</block>
			</view>
		</view>
	</view>
</template>

<script>
	import logo from '@/components/common/logo.vue';
	import userNavs from '@/components/user/common/top-navs.vue';

	import {
		mapGetters
	} from 'vuex';

	export default {
		components: {
			logo,
			userNavs
		},
		props: {
			title: String,
			tips: String,
			mustLogin: {
				type: Boolean,
				default: true
			},
			callbackUrl: {
				type: String,
				default: '/pages/cart/index'
			}
		},
		computed: {
			...mapGetters(['hasLogin'])
		},
		created() {
			if (this.mustLogin && !this.hasLogin) {
				uni.redirectTo({
					url: '/pages/index'
				});
			}
		}
	}
</script>

<style lang="scss">
	.header-wrapper {
		height: 100px;
		border-bottom: 2px solid $color-primary;

		.wrap {
			width: $page-width;
			height: 100px;
			margin: 0 auto;
			@extend %flex-align-center;

			.title {
				font-size: 28px;
				line-height: 48px;
				color: #424242;
				margin-left: 48px;
			}

			.tips {
				height: 20px;
				line-height: 20px;
				margin-top: 15px;
				margin-left: 15px;
				color: #757575;
				font-size: 12px;
			}

			.top-navbar {
				margin-left: auto;
				@extend %flex-align-center;
				color: #757575;
				font-size: 12px;

				.nav-item {
					margin: 0 5px;
					cursor: pointer;
					transition: color .2s;

					&:hover {
						color: $color-primary;
					}
				}

				.separator {
					color: $border-color;
					line-height: 40px;
				}
			}
		}
	}
</style>
