<template>
	<view>
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="个人信息" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="3-2">
			<view class="card-header">
				<view class="title">安全等级</view>
				<view class="score">
					<view class="value" :style="{'color': colors[accountInfo.securityLevel]}">{{accountInfo.score}}</view>
					<view class="unit">分</view>
				</view>
				<slider class="score-slider" :value="accountInfo.score" :block-size="14" :activeColor="colors[accountInfo.securityLevel]"
				 disabled></slider>
				<view class="tip level-3">{{securityTips[accountInfo.securityLevel]}}</view>
			</view>
			<view class="card-body">
				<view class="account-row">
					<view class="icon lock"></view>
					<view class="main">
						<view class="title">账号密码</view>
						<view class="desc">用于保护帐号信息和登录安全</view>
					</view>
					<view class="btn" @click="this.hiddenPwdReset=false;">修改</view>
				</view>
				<view class="account-row">
					<view class="icon email"></view>
					<view class="main">
						<view class="title">安全邮箱 {{accountInfo.email}}</view>
						<view class="desc">{{accountInfo.email ? '安全邮箱可以用于登录帐号，重置密码或其他安全验证' : '暂未绑定'}}</view>
					</view>
					<view class="btn" @click="this.hiddenEmailReset = false;">修改</view>
				</view>
				<view class="account-row">
					<view class="icon phone"></view>
					<view class="main">
						<view class="title">安全手机 {{accountInfo.phone}}</view>
						<view class="desc">安全手机可以用于登录帐号，重置密码或其他安全验证</view>
					</view>
					<view class="btn" @click="this.hiddenPhoneReset = false;">修改</view>
				</view>
			</view>
		</menu-wrapper>

		<!-- 密码重置 -->
		<password-reset :hidden.sync="hiddenPwdReset"></password-reset>
		<!-- 邮箱重置 -->
		<email-reset :hidden.sync="hiddenEmailReset" @change="getAccountInfo"></email-reset>
		<!-- 手机号重置 -->
		<phone-reset :hidden.sync="hiddenPhoneReset" @change="getAccountInfo"></phone-reset>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import passwordReset from '@/components/user/security/password-reset.vue';
	import emailReset from '@/components/user/security/email-reset.vue';
	import phoneReset from '@/components/user/security/phone-reset.vue';

	import centerConfig from '@/common/config/user/center.config.js';

	export default {
		components: {
			menuWrapper,
			passwordReset,
			emailReset,
			phoneReset
		},
		data() {
			return {
				...centerConfig,
				hiddenPwdReset: true,
				hiddenEmailReset: true,
				hiddenPhoneReset: true,
				accountInfo: {}
			}
		},
		onLoad(e) {
			if (e.service) this[`hidden${e.service}Reset`] = false;
			this.getAccountInfo();
		},
		methods: {
			change111(){
				this.hiddenPhoneReset=false;
				console.log("dasdsad");
				console.log(this.hiddenPhoneReset);
			},
			getAccountInfo() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});

				this.$util.request('/security/getAccountInfo', {}, (res) => {
					uni.hideLoading();

					this.accountInfo = res.accountInfo;
					// 计算分数
					const scores = [30, 60, 100];
					this.accountInfo.score = scores[this.accountInfo.securityLevel];
				}, true);
			}
		}
	}
</script>

<style lang="scss">
	.card-header {
		@extend %flex-align-center;
		padding: 30px 0;

		.title {
			font-size: 20px;
			color: #333;
			font-weight: bold;
		}

		.score {
			margin-left: 15px;
			@extend %flex-align-center;

			.value {
				font-size: 20px;
				font-weight: bold;
			}

			.unit {
				color: #9d9d9d;
				margin-left: 5px;
			}
		}

		.score-slider {
			width: 360px;
			margin-left: 5px;

			/deep/ .uni-slider-handle-wrapper {
				height: 4px;
				cursor: default;

				.uni-slider-handle {
					cursor: default;
				}
			}
		}
	}

	.card-body {

		.account-row {
			padding: 30px 0;
			border-top: 1px solid $border-color;
			@extend %flex-align-center;

			.icon {
				width: 40px;
				height: 40px;
				background: url('https://account.xiaomi.com/static/res/b52bef2/account-static/respassport/acc-2014/img/device-icos.png') no-repeat;

				&.email {
					background-position: -40px 0;
				}

				&.phone {
					background-position: -200px 0;
				}
			}

			.main {
				margin-left: 15px;

				.title {
					color: #555;
					font-size: 16px;
					line-height: 1.5;
					font-weight: bold;
				}

				.desc {
					color: #9d9d9d;
					line-height: 1.5;
				}
			}

			.btn {
				margin-left: auto;
				color: #555;
				border: 1px solid #e8e8e8;
				padding: 7px 40px;
				background-color: $bg-color;
				cursor: pointer;
				transition: all .2s;

				&:hover {
					background-color: #f3f3f4;
				}
			}
		}
	}
</style>
