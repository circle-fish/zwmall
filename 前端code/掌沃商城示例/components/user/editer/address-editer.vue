<template>
	<zw-dialog :hidden="hidden" :title="title" :hideFooter="false" :width="660" :height="290" @close="closeDialog" @cancel="closeDialog"
	 @confirm="submit">
		<view class="form-wrap">
			<view class="form-row">
				<view class="form-item" :class="[{'focus': focusIndex == 0}, {'active': shippingAddress.name}]" @click="focusIndex = 0">
					<input v-model="shippingAddress.name" maxlength="20" :focus="focusIndex == 0" @blur="focusIndex = -1;" />
					<text class="placeholder">姓名</text>
				</view>
				<view class="form-item" :class="[{'focus': focusIndex == 1}, {'active': shippingAddress.phone}]" @click="focusIndex = 1">
					<input v-model="shippingAddress.phone" maxlength="11" type="number" :placeholder="focusIndex == 1 ? oldPhone : ''"
					 placeholder-style="color: #b0b0b0;" :focus="focusIndex == 1" @blur="focusIndex = -1;" />
					<text class="placeholder">手机号</text>
				</view>
			</view>
			<view class="form-row">
				<view class="form-item address" @click="hiddenCityPicker = false">
					<input :value="mainAddress" placeholder="选择省 / 市 / 区" disabled />
					<i class="icon i-right" style="cursor: pointer;"></i>
				</view>
			</view>
			<view class="form-row">
				<view class="form-item long-text" :class="[{'focus': focusIndex == 2}, {'active': shippingAddress.address}]" @click="focusIndex = 2">
					<textarea v-model="shippingAddress.address" maxlength="50" :placeholder="focusIndex == 2 ? '详细地址，路名或街道名称，门牌号' : ''"
					 placeholder-style="color: #b0b0b0;" :focus="focusIndex == 2" @blur="focusIndex = -1;"></textarea>
					<text class="placeholder">详细地址</text>
				</view>
			</view>
			<view class="form-row">
				<view class="form-item" :class="[{'focus': focusIndex == 3}, {'active': shippingAddress.label}]" @click="focusIndex = 3">
					<input v-model="shippingAddress.label" maxlength="5" :placeholder="focusIndex == 3 ? '如&quot;家&quot;、&quot;公司&quot;。限5个字内' : ''"
					 placeholder-style="color: #b0b0b0;" :focus="focusIndex == 3" @blur="focusIndex = -1;" />
					<text class="placeholder">地址标签</text>
				</view>
			</view>

			<!-- 地区选择器 -->
			<city-picker :hidden.sync="hiddenCityPicker" @change="mainAddressChange"></city-picker>
		</view>
	</zw-dialog>
</template>

<script>
	import cityPicker from '@/components/user/picker/city-picker.vue';

	export default {
		components: {
			cityPicker
		},
		props: {
			hidden: {
				type: Boolean,
				default: false
			},
			address: {
				type: Object,
				default () {
					return {};
				}
			}
		},
		data() {
			return {
				shippingAddress: {},
				focusIndex: -1,
				oldPhone: '',
				hiddenCityPicker: true
			}
		},
		watch: {
			hidden(e) {
				if (e) {
					this.hiddenCityPicker = true;
				}
			},
			address(e) {
				this.shippingAddress = {
					...e
				};
                
				if (e.id) {
					this.oldPhone = e.phone;
					this.shippingAddress.phone = '';
				} else {
					this.oldPhone = '';
				}
			}
		},
		computed: {
			title() {
				return `${this.address.id ? '修改' : '添加'}收货地址`;
			},
			mainAddress() {
				if (this.shippingAddress.district) {
					return this.shippingAddress.province + ' ' + this.shippingAddress.city + ' ' + this.shippingAddress.district;
				}
				return '';
			}
		},
		methods: {
			closeDialog() {
				this.$emit('update:hidden', true);
			},
			mainAddressChange(e) {
				this.shippingAddress = {
					...Object.assign(this.shippingAddress, e)
				};
				this.hiddenCityPicker = true;
			},
			submit() {
				if (!this.checkForm()) return;
				if (!this.shippingAddress.label) this.shippingAddress.label = '';
				this.shippingAddress.userId=this.$store.state.loginUser.id;
				this.$emit('change', this.shippingAddress);
				this.closeDialog();
			},
			checkForm() {
				if (!this.$regular.nickName.test(this.shippingAddress.name)) {
					return this.$util.showErrorMsg('请输入正确的收货人姓名');
				}
				if (!this.$regular.phoneNumber.test(this.shippingAddress.phone) && (this.shippingAddress.phone || !this.oldPhone)) {
					return this.$util.showErrorMsg('请输入正确的收货人手机号');
				}
				if (!this.mainAddress) {
					return this.$util.showErrorMsg('请选择省市区');
				}
				if (!this.shippingAddress.address || this.shippingAddress.address.length < 5) {
					return this.$util.showErrorMsg('请输入正确的详细地址');
				}
				return true;
			}
		}
	}
</script>

<style lang="scss">
	.form-wrap {
		width: 100%;
		padding: 20px;
		position: relative;

		.form-row {
			margin-bottom: 14px;
			@extend %flex-align-center;

			.form-item {
				flex: 1;
				position: relative;
				border: 1px solid $border-color;
				transition: border-color .2s linear;

				&:hover {
					border-color: #B0B0B0;
				}

				&.focus {
					border-color: $color-primary;

					.placeholder {
						font-size: 12px;
						top: -11px;
						color: $color-primary;
					}
				}

				&.active {

					.placeholder {
						font-size: 12px;
						top: -11px;
					}
				}

				&.address {

					&,
					/deep/ .uni-input-input {
						cursor: pointer;
					}
				}

				&.long-text {
					display: flex;

					textarea {
						height: 3em;
						resize: vertical;
						padding: 10px 16px;
						flex: 1;
					}
				}

				&:nth-child(2) {
					margin-left: 14px;
				}

				input {
					z-index: 2;
					height: 40px;
					padding: 0 16px;
					line-height: 40px;
				}

				.placeholder {
					position: absolute;
					left: 12px;
					top: 11px;
					z-index: 1;
					padding: 0 3px;
					line-height: 18px;
					background-color: $bg-color;
					color: #b0b0b0;
					cursor: text;
					transition: all .2s linear;
				}

				.i-right {
					position: absolute;
					top: 50%;
					right: 6px;
					height: 24px;
					line-height: 24px;
					margin-top: -12px;
					font-size: 18px;
					color: #b0b0b0;
					font-weight: bold;
					transform: rotate(90deg);
				}
			}
		}
	}
</style>
