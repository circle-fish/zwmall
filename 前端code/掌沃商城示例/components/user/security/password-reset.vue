<template>
	<zw-dialog :hidden="hidden" title="修改密码" :width="450" :height="320" :showHeaderBg="false" :hideFooter="false"
	 :showFooterBg="false" borderRadius @close="closeDialog" @cancel="closeDialog" @confirm="submit">
		<view class="form-wrap" @keydown.enter="submit">
			<view class="form-item-title">原密码</view>
			<view class="form-item">
				<input placeholder="输入原密码" password v-model="oldPassword" />
			</view>
			<view class="form-item-title">新密码</view>
			<view class="form-item">
				<input placeholder="输入新密码" password v-model="newPassword" />
			</view>
			<view class="form-item">
				<input placeholder="重复新密码" password v-model="repeatPassword" />
			</view>
			
		</view>
	</zw-dialog>
</template>

<script>
	export default {
		props: {
			hidden: true
		},
		data() {
			return {
				oldPassword: '',
				newPassword: '',
				repeatPassword: '',
				captcha: '',
				random: ''
			}
		},
		created() {
			this.getRandom();
		},
		methods: {
			getRandom() {
				this.random = this.$util.getRandomStr();
			},
			closeDialog() {
				this.$emit('update:hidden', true);
			},
			submit() {
				if (!this.checkForm()) return;

				uni.showLoading({
					title: '处理中...',
					mask: true
				});

				this.$util.request('/user/updatepassword', {
					id:this.$store.state.loginUser.id,
					password:this.newPassword
				}, (res) => {
					uni.hideLoading();

					if (res.data.code == 200) {
						this.$store.commit('logout');

						this.$alert('密码修改成功！', {
							cancelText: '返回首页',
							confirmText: '重新登录'
						}, () => {
							this.closeDialog();
							uni.navigateTo({
								url: '/pages/user/login/index'
							});
						}, () => {
							this.closeDialog();
							uni.navigateTo({
								url: '/pages/index'
							});
						});
					} else {
						this.$util.showErrorMsg(res.msg);
						this.getRandom();
						this.captcha = '';
					}
				});
			},
			checkForm() {
				if (this.oldPassword!=this.$store.state.loginUser.password) {
					return this.$util.showErrorMsg('原密码错误');
				}
				

				return true;
			}
		}
	}
</script>

<style lang="scss">
	.form-wrap {
		width: 100%;
		margin: 0 20px;
		padding: 30px 40px 0;
		border-top: 1px solid $border-color;

		.form-item-title {
			font-size: 15px;
			color: #555;
			font-weight: bold;
			line-height: 30px;
		}

		.form-item {
			@extend %flex-align-center;
			margin-bottom: 10px;

			input {
				flex: 1;
				height: 40px;
				line-height: 40px;
				padding: 0 10px;
				color: #333;
				border: 1px solid #e8e8e8;
			}

			.captcha {
				width: 125px;
				height: 42px;
				margin-left: 15px;
				cursor: pointer;
			}
		}
	}
</style>
