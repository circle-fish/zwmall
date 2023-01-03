<template>
	<zw-dialog :hidden="hidden" title="编辑基础资料" :width="440" :height="330" :showHeaderBg="false" :hideFooter="false"
	 :showFooterBg="false" borderRadius confirmText="保存" @close="closeDialog" @cancel="closeDialog" @confirm="submit">
		<view class="form-wrap">
			<view class="form-item">
				<view class="name">昵称：</view>
				<view class="value">
					<input v-model="user.name" placeholder="昵称" maxlength="20" />
				</view>
			</view>
			<view class="form-item">
				<view class="name">性别：</view>
				<radio-group class="value" @change="genderChange">
					<label>
						<radio :checked="user.sex == '男'" value="男" color="#FF6700"></radio>
						<text>男</text>
					</label>
					<label>
						<radio :checked="user.sex == '女'" value="女" color="#FF6700"></radio>
						<text>女</text>
					</label>
				</radio-group>
			</view>
			<view class="form-item">
				<view class="name">生日：</view>
				<view class="value pointer" @click="hiddenDatePicker = false;">
					<input v-model="user.birth" placeholder="选择生日" disabled />
				</view>
			</view>
		</view>

		<!-- 生日选择 -->
		<date-picker :hidden.sync="hiddenDatePicker" :value="user.birthday" @change="birthdayChange"></date-picker>

		
	</zw-dialog>
</template>

<script>
	import datePicker from '@/components/user/picker/date-picker.vue';
	import cityPicker from '@/components/user/picker/city-picker.vue';

	export default {
		components: {
			datePicker,
		},
		props: {
			hidden: {
				type: Boolean,
				default: true
			},
			loginUser: {
				type: Object,
				default () {
					return {};
				}
			}
		},
		data() {
			return {
				user: {},
				hiddenDatePicker: true,
			}
		},
		created() {
			this.initUser();
		},
		watch: {
			hidden(e) {
				if (e) {
					this.initUser();
					this.hiddenDatePicker  = true;
				}
			}
		},
		methods: {
			initUser() {
				const user = {
					...this.loginUser
				};
				for (let key in user) {
					if (!user[key]) user[key] = '';
				}
				this.user = user;
			},
			closeDialog() {
				this.$emit('update:hidden', true);
			},
			genderChange(e) {
				this.user.sex = (e.detail.value);
			},
			birthdayChange(e) {
				this.user.birth = e;
				this.hiddenDatePicker = true;
			},
			submit() {
				if (!this.$regular.nickName.test(this.user.name)) {
					return this.$util.showErrorMsg('请输入正确的昵称');
				}
				if (this.user.sex !='男'&&this.user.sex !='女') {
					console.log("213123123"+this.user.sex);
					return this.$util.showErrorMsg('请选择性别');
				}
				
				this.$util.request('/user/updateself', {
					id: this.$store.state.loginUser.id,
					name:this.user.name,
					sex:this.user.sex,
					birth:this.user.birth,
				}, (res) => {
					uni.hideLoading();
				    console.log(res);
					if(res.data.code==200){
						this.$store.state.loginUser.name=this.user.name;
						this.$store.state.loginUser.sex=this.user.sex;
						this.$store.state.loginUser.birth=this.user.birth;
						this.$util.showErrorMsg('修改成功');
						setTimeout(function(){
						
						  uni.navigateBack({
							
						   })	
						},1000)
						
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.form-wrap {
		width: 100%;
		padding: 20px;

		.form-item {
			position: relative;
			padding-bottom: 15px;
			height: 40px;
			@extend %flex-align-center;

			.name {
				width: 60px;
				margin-right: 10px;
				text-align: right;
				font-weight: bold;
				color: #666;
			}

			.value {
				width: 320px;
				color: $text-color;

				&.pointer {

					&,
					/deep/ .uni-input-input {
						cursor: pointer;
					}
				}

				input {
					height: 40px;
					line-height: 40px;
					padding: 0 10px;
					border: 1px solid $border-color;
				}

				label {
					margin-right: 20px;

					radio {
						transform: scale(.8);
					}
				}
			}

			&.textarea {
				align-items: flex-start;
				height: 100px;

				textarea {
					width: 298px;
					padding: 10px;
					height: 60px;
					border: 1px solid $border-color;
				}
			}
		}
	}
	
	.city-picker-warpper {
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
		height: 285px;
	}
</style>
