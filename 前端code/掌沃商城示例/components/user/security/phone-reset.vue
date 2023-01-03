<template>
	<zw-dialog :hidden="hidden" title="修改安全手机" :width="450" :height="300" :showHeaderBg="false" borderRadius @close="closeDialog">
		<view class="form-wrap">
			<!-- 步骤条 -->
			<steps :steps="['输入新手机', '完成']" :step="step"></steps>

			<!-- 输入新手机表单 -->
			<view v-if="step == 0" @keydown.enter="goStep2">
				<view class="form-item-title">请输入新的安全手机号</view>
				<view class="form-item">
					<input placeholder="请输入手机号" v-model="phone" type="number" maxlength="11" />
				</view>
				<view class="form-item btn-wrap">
					<button class="btn-primary" @click="goStep2">下一步</button>
				</view>
			</view>
			<view v-if="step == 2">
				<view class="form-item-title tip">您已成功修改安全手机！</view>
				<view class="form-item btn-wrap">
					<button class="btn-primary" @click="closeDialog">返回我的账号</button>
				</view>
			</view>
		</view>
	</zw-dialog>
</template>

<script>
	import steps from '@/components/common/steps.vue';

	export default {
		components: {
			steps
		},
		props: {
			hidden: true
		},
		data() {
			return {
				step: 0,
				phone: '',
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
					// this.$emit('update:hidden', true);
					// this.$util.showErrorMsg('修改成功');
					uni.navigateBack({				
					 })
	
			},
			goStep2() {
				// 校验手机格式
				if (!this.$regular.phoneNumber.test(this.phone)) {
					return this.$util.showErrorMsg('请输入正确的手机号');
				}
				else {
					console.log(this.$store.state.loginUser.id);
					this.$util.request('/user/updatephone', {
						id: this.$store.state.loginUser.id,
						phone:this.phone
					}, (res) => {
					    console.log(res);
						if(res.data.code==200){
							this.step=2;
						}
					});
				}
			},
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

			&.small {
				color: #333333;
				margin-bottom: 20px;

				.phone-wrap {
					@extend %flex-align-center;

					.phone {
						display: inline-block;
						margin: 0 5px;
						color: $color-primary;
						max-width: 170px;
					}
				}
			}

			&.tip {
				color: #333333;
				text-align: center;
				padding: 10px;
			}
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

			.btn-send {
				border: 1px solid $border-color;
				height: 40px;
				line-height: 40px;
				padding: 0 20px;
				margin-left: 10px;
				cursor: pointer;
				transition: all .2s;

				&:hover {
					background-color: #ececec;
					color: #888;
				}

				&.disabled {
					cursor: default;
					color: #757575;

					&:hover {
						cursor: default;
						color: #757575;
						background-color: $bg-color;
					}
				}
			}

			&.btn-wrap {
				margin-top: 25px;
				justify-content: center;

				%btn {
					width: 122px;
					height: 35px;
					line-height: 35px;
					text-align: center;
					margin: 0 5px;
				}

				.btn-primary {
					@extend %btn;
					color: #ffffff;
					background-color: $color-primary;
				}

				.btn-gray {
					@extend %btn;
					color: #555;
					background-color: #FFFFFF;
					border: 1px solid $border-color;
				}
			}
		}
	}
</style>
