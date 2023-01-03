<template>
	<view class="navs-wrap">
		<view class="user-navs">
			<navigator :class="['navs-trigger', {'gray': isWhiteBack}]">
				<text class="user-name elip">{{$store.state.loginUser.name}}</text>
				<i class="icon i-right"></i>
			</navigator>
			<!-- 菜单列表 -->
			<view class="nav-list">
				<block v-for="(item, index) in topNavs" :key="index">
					<navigator class="nav" :url="item.linkUrl">{{item.name}}</navigator>
				</block>
				<view class="nav" @click="logout">退出登录</view>
			</view>
		</view>
		<text class="separator" :class="{'gray': isWhiteBack}">|</text>
		<navigator class="link" :class="{'no-hover': isWhiteBack}" url="/pages/order/index">我的订单</navigator>
	</view>
</template>

<script>
	import topNavs from '@/common/config/user/top-navs.config.js';

	export default {
		props: {
			isWhiteBack: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				topNavs
			}
		},
		methods: {
			logout() {
				this.$alert('确定退出登录吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/uesr/logout', {}, () => {
						uni.hideLoading();
						
						this.$store.commit('logout');
						uni.reLaunch({
							url: '/pages/index'
						});
					});
				});
			}
		}
	}
</script>

<style lang="scss">
	.navs-wrap {
		@extend %flex-align-center;

		.user-navs {
			font-size: 12px;
			position: relative;

			&:hover {

				.navs-trigger {
					color: #424242;
					background-color: $bg-color;
				}

				.nav-list {
					height: 150px;
					padding: 7px 0;
				}
			}

			.navs-trigger {
				position: relative;
				z-index: 10;
				width: 110px;
				height: 40px;
				color: #b0b0b0;
				@extend %flex-align-center;
				justify-content: center;
				
				&.gray {
					color: #757575;
				}

				&:hover {
					color: $color-primary;
				}

				.user-name {
					display: inline-block;
					max-width: 75px;
				}

				.icon {
					font-size: 12px;
					font-weight: bold;
					margin-left: 5px;
					transform: rotate(90deg);
				}
			}

			.nav-list {
				z-index: 9;
				position: absolute;
				left: 0;
				right: 0;
				top: 100%;
				color: #424242;
				height: 0;
				overflow: hidden;
				background-color: $bg-color;
				box-shadow: 0 2px 10px rgba(0, 0, 0, .15);
				transition: height .3s;

				.nav {
					height: 30px;
					line-height: 30px;
					text-align: center;
					cursor: pointer;

					&:hover {
						color: $color-primary;
						background-color: $bg-color-grey;
					}
				}
			}
		}

		.link {
			color: #b0b0b0;
			cursor: pointer;
			line-height: 40px;

			&:hover {
				color: #FFFFFF;
			}
			
			&.no-hover {
				color: #757575;
				
				&:hover {
					color: #757575;
				}
			}
		}

		.separator {
			margin: 0 .4rem;
			color: #424242;
			line-height: 40px;
			
			&.gray {
				color: #e0e0e0;
			}
		}
	}
</style>
