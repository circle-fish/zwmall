<template>
	<view>

		<view class="card-wrapper">
			<view class="card-wrap">
				<view class="card-header">
					<navigator url="/pages/index" style="cursor: pointer;">
						<img class="logo" src="../../../static/image/common/logo1.png" />
					</navigator>
					<view class="title">注册账号</view>
				</view>
				<form class="card-body" @submit="register">
					<view class="form-item">
						<view class="country-code">+86</view>
						<input class="input" placeholder="手机号码" name="phone" maxlength="11" type="number" v-model="phone" />
					</view>
					<view class="form-item">
						<input class="input" placeholder="账户名" name="username" maxlength="20" />
					</view>
					<view class="form-item">
						<input class="input" placeholder="用户名" name="name" maxlength="20" />
					</view>
					<view class="form-item">
						<input class="input" placeholder="邮箱" name="email" maxlength="20" />
					</view>
					<view class="form-item">
						<input class="input" placeholder="请输入密码" name="password" maxlength="20" password />
					</view>
					<view class="form-item">
						<input class="input" placeholder="再次输入密码" name="passwordRepeat" maxlength="20" password />
					</view>
					<view class="form-item border-none agreement">
						<label>
							<checkbox-group @change="agreeChange">
								<checkbox color="#FF6700" value="agree"></checkbox>
							</checkbox-group>
							<text>已阅读并同意</text>
						</label>
						<a class="link" href="/pages/user/register/agreement" target="_blank">《注册协议》</a>
					</view>
					<view class="form-item border-none" :title="!agreed ? '请阅读并同意注册协议' : ''">
						<button :class="['btn-primary', {'disabled': !agreed}]" form-type="submit">立即注册</button>
					</view>
				</form>
				<view class="card-footer">
					<text class="question">已有账号？</text>
					<navigator class="link" url="/pages/user/login/index">去登录</navigator>
				</view>
			</view>
		</view>

		<!-- 版权标识 -->
		<view class="copyright">小米公司版权所有-京公网安备xxxxxxxxx号</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				phone: '',
				username:'',
				name:'',
				password:'',
				agreed: false
			}
		},
		methods: {
			agreeChange(e) {
				this.agreed = e.detail.value.length > 0;
			},
			register(e) {
				if (!this.agreed) return;
				const data = e.detail.value;
				console.log(data);
				if (!this.checkForm(data)) return;
				
				uni.showLoading({
					title: '处理中...',
					mask: true
				});
				this.$util.request('/user/register', data, (res) => {
					uni.hideLoading();
				
					if (res.data.code == 200) {
						this.$store.commit('login', res.data.data);
						// this.$cart.syncCart();
						
						uni.redirectTo({
							url: this.callbackUrl || '/pages/index'
						});
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
			},
			checkForm(e) {
				if (!this.$regular.phoneNumber.test(e.phone)) {
					return this.$util.showErrorMsg('手机号格式错误');
				}
				if (e.username=='') {
					return this.$util.showErrorMsg('请输入账户名');
				}
				if (e.name=='') {
					return this.$util.showErrorMsg('请输入用户名');
				}
				if (!this.$regular.email.test(e.email)) {
					return this.$util.showErrorMsg('请输入正确的邮箱');
				}
				if (!this.$regular.email.test(e.email)) {
					return this.$util.showErrorMsg('请输入正确的邮箱');
				}
				if(e.password==""){
					return this.$util.showErrorMsg('请输入密码');
				}
				if (e.passwordRepeat != e.password) {
					return this.$util.showErrorMsg('密码输入不一致');
				}

				return true;
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $bg-color-grey;
	}

	.card-wrapper {
		width: 660px;
		margin: 80px auto;
		background-color: $bg-color;

		.card-wrap {
			width: 332px;
			margin: 0 auto;
			padding-bottom: 20px;

			.card-header {
				text-align: center;

				.logo {
					width: 48px;
					height: 48px;
					margin-bottom: 40px;
				}

				.title {
					font-size: 30px;
					color: #333;
					line-height: 1.5;
				}
			}

			.card-body {
				display: block;
				margin-top: 22px;

				.form-item {
					@extend %flex-align-center;
					height: 45px;
					line-height: 45px;
					margin-bottom: 14px;
					border: 1px solid $border-color;

					.input {
						flex: 1;
						height: 45px;
						line-height: 45px;
						padding: 0 14px;
						color: $text-color;
					}

					.country-code {
						width: 60px;
						height: 45px;
						line-height: 45px;
						color: $text-color;
						text-align: center;
						border-right: 1px solid $border-color;
					}

					.btn-send {
						height: 45px;
						line-height: 45px;
						padding: 0 15px;
						color: #003AB5;
						cursor: pointer;
						text-align: center;
						border-left: 1px solid $border-color;

						&.disabled {
							cursor: default;
							color: #757575;
						}
					}

					.btn-primary {
						background-color: $color-primary;
						height: 45px;
						line-height: 45px;
						margin-bottom: 14px;
						color: #fff;
						cursor: pointer;
						width: 100%;

						&.disabled {
							opacity: .5;
							cursor: not-allowed;
						}
					}

					&.border-none {
						border: none;
					}

					&.agreement {
						height: 20px;
						line-height: 20px;
						margin-bottom: 18px;
						margin-top: 24px;

						label {
							@extend %flex-align-center;
							color: $text-color-grey;

							checkbox {
								transform: scale(.65);
								margin-left: -3px;
							}
						}

						.link {
							color: #003AB5;
						}
					}
				}
			}

			.card-footer {
				@extend %flex-align-center;
				justify-content: flex-end;
				font-size: 12px;

				.question {
					color: $text-color-grey;
				}

				.link {
					color: $color-primary;
				}
			}
		}
	}

	.copyright {
		position: fixed;
		bottom: 20px;
		left: 0;
		right: 0;
		padding: 10px;
		color: #757575;
		text-align: center;
	}
</style>
