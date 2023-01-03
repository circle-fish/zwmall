<template>
	<view>

		<view class="card-wrapper">
			<view class="card-wrap">
				<view class="card-header">
					<navigator url="/pages/index" style="cursor: pointer;">
						<img class="logo" src="../../../static/image/home/th.png" />
					</navigator>
					<view class="title">找回密码</view>
				</view>
				<form class="card-body" @submit="retrieve">
					<view class="form-item">
						<input class="input" placeholder="邮箱/手机号码" name="account" maxlength="30" v-model="account" />
					</view>
					<view class="form-item">
						<input class="input" placeholder="验证码" name="captcha" maxlength="6" type="number" />
						<view class="btn-send" :class="{'disabled': sendCode.disabled}" @click="sendCaptcha">{{sendCode.text}}</view>
					</view>
					<view class="form-item">
						<input class="input" placeholder="请输入新密码" name="password" maxlength="20" password />
					</view>
					<view class="form-item">
						<input class="input" placeholder="再次输入密码" name="passwordRepeat" maxlength="20" password />
					</view>
					<view class="form-item border-none">
						<button class="btn-primary" form-type="submit">立即提交</button>
					</view>
				</form>
				<view class="card-footer">
					<text class="question">已知密码？</text>
					<navigator class="link" url="/pages/user/login/index">返回登录</navigator>
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
				account: '',
				sendCode: {
					text: '获取验证码',
					disabled: false
				}
			}
		},
		methods: {
			sendCaptcha() {
				if (this.sendCode.disabled) return;
				if (!this.$regular.phoneNumber.test(this.account) && !this.$regular.email.test(this.account)) {
					return this.$util.showErrorMsg('该账号不存在');
				}

				this.sendCode.disabled = true;
				this.sendCode.text = '发送中...';

				new Promise((resolve, reject) => {
					this.$util.request('/security/sendCaptchaForRetrievePwd', {
						account: this.account
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
			retrieve(e) {
				const data = e.detail.value;
				if (!this.checkForm(data)) return;

				uni.showLoading({
					title: '处理中...',
					mask: true
				});
				this.$util.request('/security/retrievePassword', data, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.$alert('密码重置成功！', {
							showCancel: false,
							confirmText: '立即去登录'
						}, () => {
							uni.redirectTo({
								url: '/pages/user/login/index'
							});
						});
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
			},
			checkForm(e) {
				if (!this.$regular.phoneNumber.test(this.account) && !this.$regular.email.test(this.account)) {
					return this.$util.showErrorMsg('该账号不存在');
				}
				if (!this.$regular.captcha.test(e.captcha)) {
					return this.$util.showErrorMsg('验证码错误');
				}
				if (!this.$regular.password.test(e.password)) {
					return this.$util.showErrorMsg('密码格式错误');
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
						margin-top: 20px;
						margin-bottom: 14px;
						color: #fff;
						cursor: pointer;
						width: 100%;
					}

					&.border-none {
						border: none;
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
