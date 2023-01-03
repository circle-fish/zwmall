<template>
	<view v-if="counts.length > 0">
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="个人中心" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="2-1">
			<view class="card-header">
				<view class="left">
					<img class="avatar" :src="loginUser.avatarUrl" />
					<view class="user-info">
						<view class="user-name elip">{{loginUser.name}}</view>
						<view class="tip">{{currentTime}}好~</view>
						<navigator class="link" url="/pages/user/profile/index">修改个人信息></navigator>
					</view>
				</view>
				<view class="right">
					<view class="item">
						<view class="name">账户安全：</view>
						<view class="value" :style="{'color': colors[accountInfo.securityLevel]}">{{levels[accountInfo.securityLevel]}}</view>
					</view>
					<view class="item">
						<view class="name">绑定手机：</view>
						<view class="value">{{accountInfo.phone}}</view>
					</view>
					<view class="item">
						<view class="name">绑定邮箱：</view>
						<view class="value">{{accountInfo.email || '未绑定'}}</view>
					</view>
				</view>
			</view>
			<view class="card-body">
				<view class="button" v-for="(item, index) in buttons" :key="index">
					<image class="btn-icon" :src="item.iconUrl"></image>
					<view class="right">
						<view class="top">
							<view class="name">{{item.title}}：</view>
							<view class="value">{{counts[index]}}</view>
						</view>
						<navigator class="link" :url="item.linkUrl">
							<view class="tip">{{item.tip}}</view>
							<i class="icon i-right"></i>
						</navigator>
					</view>
				</view>
			</view>
		</menu-wrapper>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import centerConfig from '@/common/config/user/center.config.js';

	import {
		mapState
	} from 'vuex';

	export default {
		components: {
			menuWrapper
		},
		data() {
			return {
				...centerConfig,
				accountInfo: {},
				counts: []
			}
		},
		computed: {
			...mapState(['loginUser']),
			currentTime() {
				const currentHour = new Date().getHours();
				const timeQuantums = ['凌晨', '上午', '下午', '晚上'];
				return timeQuantums[Math.floor(currentHour / 6)];
			}
		},
		onLoad() {
			this.getAccountInfo();
			this.findCounts();
		},
		methods: {
			getAccountInfo() {
				this.$util.request('/user/security/getAccountInfo', {}, (res) => {
					this.accountInfo = res.accountInfo;
				}, true);
			},
			findCounts() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/profile/counts', {}, (res) => {
					uni.hideLoading();
					this.counts = res;
				}, true);
			}
		}
	}
</script>

<style lang="scss">
	.card-header {
		padding-top: 24px;
		@extend %flex-align-center;

		.left {
			@extend %flex-align-center;

			.avatar {
				width: 150px;
				height: 150px;
				padding: 4px;
				border: 1px solid $border-color;
				border-radius: 50%;
				object-fit: fill;
			}

			.user-info {
				width: 302px;
				margin-left: 20px;

				.user-name {
					max-width: 220px;
					font-size: 24px;
					font-weight: 400;
					color: #616161;
				}

				.tip {
					margin-top: 5px;
					color: #b0b0b0;
				}

				.link {
					display: inline-block;
					font-size: 12px;
					margin-top: 10px;
					color: $color-primary;
				}
			}
		}

		.right {

			.item {
				@extend %flex-align-center;
				padding: 6px 0;
				color: #757575;
			}
		}
	}

	.card-body {
		display: flex;
		flex-wrap: wrap;
		margin-top: 40px;
		padding-top: 50px;
		border-top: 1px solid $border-color;

		.button {
			margin-bottom: 60px;
			@extend %flex-align-center;

			.btn-icon {
				width: 100px;
				height: 100px;
				border-radius: 50%;
			}

			.right {
				width: 276px;
				margin-left: 20px;

				.top {
					@extend %flex-align-center;
					font-size: 18px;
					font-weight: 400;

					.name {
						color: #757575;
					}

					.value {
						color: $color-primary;
					}
				}

				.link {
					line-height: 1.8;
					@extend %flex-align-center;
					color: #757575;
					cursor: pointer;

					&:hover {
						color: $color-primary;
					}

					.icon {
						font-weight: bold;
					}
				}
			}
		}
	}
</style>
