<template>
	<view>

		<view class="card-wrapper">
			<view class="card-wrap">
				<!-- 头部logo和title -->
				<view class="card-header">
					<navigator url="/pages/index" style="cursor: pointer;">
						<img class="logo" src="/static/image/common/logo1.png"></image>
					</navigator>
					<view class="title">账号登录</view>
				</view>

				<!-- 账号密码登录 -->
				<block>
					<!-- 登录表单 -->
					<form class="form form-password" @submit="accountLogin" @keydown.enter="accountLogin">
						<view class="form-item">
							<input class="input" placeholder="邮箱/手机号码" maxlength="30" v-model="formData.username" />
						</view>
						<view class="form-item">
							<input class="input" placeholder="密码" maxlength="20" password v-model="formData.password" />
						</view>
						<button class="btn-primary" form-type="submit">登录</button>
					</form>
					<!-- 相关链接 -->
					<view class="card-footer">
						<view class="links">
							<navigator class="link" url="../register/index">立即注册</navigator>
							<text class="separator">|</text>
							<navigator class="link" url="../password/retrieve">找回密码</navigator>
						</view>
					</view>
				</block>
				
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
				formData: {
					username: '',
					password: '',
					phone: '',
					captcha: ''
				},
				sendCode: {
					text: '获取验证码',
					disabled: false
				}
			}
		},
		onLoad(e) {
			if (e.callbackUrl) this.callbackUrl = e.callbackUrl;
		},
		methods: {
			accountLogin() {
				const {
					username,
					password
				} = this.formData;
				// if (!this.$regular.phoneNumber.test(account) && !this.$regular.email.test(account)) {
				// 	return this.$util.showErrorMsg('请输入正确的账号');
				// }
				// if (!this.$regular.password.test(password)) {
				// 	return this.$util.showErrorMsg('请输入正确的密码');
				// }

				uni.showLoading({
					title: '登录中...',
					mask: true
				});
				this.$util.request('/user/enter', {
					username,
					password
				}, (res) => {
					uni.hideLoading();
                    console.log(res);
					if(res.data.code==200){
						this.loginSuccess(
						   res.data.data
						)
					}
					// if (res.data.code == 200) {
					// 	this.loginSuccess({
					// 		res.loginsuer
					// 	});
					// } else {
					// 	this.$util.showErrorMsg(res.msg);
					// }
				});
			},
			sendSMSCode() {
				if (this.sendCode.disabled) return;
				if (!this.$regular.phoneNumber.test(this.formData.phone)) {
					return this.$util.showErrorMsg('请输入正确的手机号');
				}

				this.sendCode.disabled = true;
				this.sendCode.text = '发送中...';

				new Promise((resolve, reject) => {
					this.$util.request('/login/sendSMSCaptcha', {
						phone: this.formData.phone
					}, (res) => {
						if (res.state == 'ok') {
							resolve();
						} else {
							reject(res.msg);
						}
					});
				}).then(() => {
					let seconds = 60;
					const timer = setInterval(() => {
						if (seconds <= 0) {
							clearInterval(timer);
							this.sendCode.disabled = false;
							this.sendCode.text = '重新发送';
						} else {
							this.sendCode.text = `重新发送（${seconds}）`;
							seconds--;
						}
					}, 1000);
				}).catch(e => {
					this.$util.showErrorMsg(e);

					this.sendCode.disabled = false;
					this.sendCode.text = '重新发送';
				});
			},
			captchaLogin(e) {
				const {
					phone,
					captcha
				} = this.formData;
				if (!this.$regular.phoneNumber.test(phone)) {
					return this.$util.showErrorMsg('请输入正确的手机号');
				}
				if (!this.$regular.captcha.test(captcha)) {
					return this.$util.showErrorMsg('请输入正确的验证码');
				}

				uni.showLoading({
					title: '登录中...',
					mask: true
				});
				this.$util.request('/login/captchaLogin', {
					phone,
					captcha
				}, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.loginSuccess(res.loginUser);
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
			},
			loginSuccess(loginUser) {
				this.$store.commit('login', loginUser);
				this.$cart.syncCart();
				console.log("success");
				uni.navigateTo({
					// url:"../profile/index"
					url:"../../index"
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: $bg-color-grey;
	}

	.card-wrapper {
		background-color: $bg-color;
		width: 660px;
		margin: 80px auto;
		overflow: hidden;

		.card-wrap {
			width: 356px;
			margin: 40px auto 80px;

			.card-header {
				text-align: center;

				.logo {
					width: 60px;
					height: 60px;
					border-radius: 2px;
				}

				.title {
					font-size: 30px;
					color: $text-color;
					padding-top: 22px;
				}
			}

			.form {
				display: block;
				margin: 22px auto;

				.form-item {
					@extend %flex-align-center;
					height: 50px;
					line-height: 50px;
					margin-bottom: 14px;
					border: 1px solid $border-color;

					.input {
						flex: 1;
						height: 50px;
						line-height: 50px;
						padding: 0 14px;
						color: $text-color;
					}
				}

				&.form-smscode {
					.country-code {
						width: 60px;
						height: 50px;
						line-height: 50px;
						color: $text-color;
						text-align: center;
						border-right: 1px solid $border-color;
					}

					.btn-send {
						height: 50px;
						line-height: 50px;
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
				}

				.btn-primary {
					background-color: $color-primary;
					height: 50px;
					line-height: 50px;
					margin-bottom: 14px;
					color: #fff;
					cursor: pointer;
				}
			}

			.card-footer {
				@extend %flex-align-center;
				justify-content: space-between;

				.login-mode {
					color: $color-primary;
					cursor: pointer;
				}

				.links {
					@extend %flex-align-center;

					.link {
						color: #757575;
						cursor: pointer;

						&:hover {
							color: $color-primary;
						}
					}

					.separator {
						margin: 0 5px;
						color: $border-color;
					}
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
