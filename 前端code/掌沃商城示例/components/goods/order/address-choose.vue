<template>
	<view>
		<view class="shipping-wrap">
			<view class="title">收货地址</view>
			<view class="address-wrap">
				<block v-for="(item, index) in addresses" :key="index" v-if="showAllAddress || index < 3">
					<view :class="['box', {'active': index == addressIndex}]" @click="chooseAddress(index)">
						<view class="box-address">
							<view class="top">
								<view class="user-name">{{item.name}}</view>
								<view class="address-label">{{item.label}}</view>
							</view>
							<view class="phone-number">{{item.phone}}</view>
							<view class="address-detail">{{item.province}} {{item.city}} {{item.district}} {{item.address}}</view>
						</view>
						<view class="btn-wrap">
							<view class="btn" @click="editAddress(item, index)">修改</view>
						</view>
					</view>
				</block>
				<view class="box box-add" @click="addAddress">
					<view class="btn-add">+</view>
					<view class="desc">添加新地址</view>
				</view>
			</view>
			<!-- 显示全部地址按钮 -->
			<view v-if="addresses.length >= 4" class="btn-more" @click="showAllAddress = !showAllAddress">
				<text>显示更多收货地址</text>
				<i class="icon i-right" :class="{'active': showAllAddress}"></i>
			</view>
		</view>

		<!-- 添加/修改收货地址 -->
		<address-editer :hidden.sync="hiddenDialog" :address="address" @change="addressConfirm"></address-editer>
	</view>
</template>

<script>
	import addressEditer from '@/components/user/editer/address-editer.vue';

	export default {
		components: {
			addressEditer
		},
		data() {
			return {
				addresses: [],
				addressIndex: -1,
				showAllAddress: false,
				hiddenDialog: true,
				address: {},
				isUpdate: false
			}
		},
		watch: {
			showAllAddress(e) {
				if (!e && this.addressIndex >= 3) {
					const firstAddress = this.addresses.splice(0, 1, this.addresses[this.addressIndex])[0];
					this.addresses[this.addressIndex] = firstAddress;
					this.addressIndex = 0;
				}
			},
			addressIndex(e) {
				this.$emit('change', this.addresses[e]);
			}
		},
		created() {
			this.findAllAddress();
		},
		methods: {
			findAllAddress(isUpdate = false) {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/address', {}, (res) => {
					uni.hideLoading();

					this.addresses = res;
					if (res.length > 0) this.$emit('returnFirst', res[0]);

					if (isUpdate && this.addressIndex != -1) {
						this.$emit('change', this.addresses[this.addressIndex]);
					}
				}, true);
			},
			addAddress() {
				this.isUpdate = false;
				this.address = {
					name: '',
					phone: '',
					province: '',
					city: '',
					district: '',
					address: '',
					label: ''
				};
				this.hiddenDialog = false;
			},
			editAddress(item, index) {
				this.addressIndex = index;
				this.address = item;
				this.isUpdate = true;
				this.hiddenDialog = false;
			},
			addressConfirm(e) {
				uni.showLoading({
					title: '处理中...',
					mask: true
				});

				const api = this.isUpdate ? 'update' : 'save';
				this.$util.request('/address/' + api, e, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.hiddenDialog = true;
						this.findAllAddress(true);
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
			},
			chooseAddress(index) {
				this.addressIndex = index;
			},
			updateCurrent() {
				this.editAddress(this.addresses[this.addressIndex], this.addressIndex);
			}
		}
	}
</script>

<style lang="scss">
	.shipping-wrap {
		padding: 48px;
		padding-bottom: 0;

		.title {
			color: $text-color;
			font-size: 18px;
			line-height: 20px;
		}

		.address-wrap {
			margin-top: 30px;
			display: flex;
			flex-wrap: wrap;
			margin-right: -18px;

			.box {
				cursor: pointer;
				width: 268px;
				height: 178px;
				border: 1px solid $border-color;
				margin-right: 17px;
				margin-bottom: 24px;
				position: relative;
				transition: all .4s ease;

				&:hover {
					border-color: #b0b0b0;
				}

				&.active {
					border-color: $color-primary;

					.btn-wrap {
						display: flex;
					}
				}

				&.box-add {
					@extend %flex-align-center;
					justify-content: center;
					flex-direction: column;
					cursor: pointer;
					transition: all .4s ease;

					&:hover {

						.btn-add {
							background-color: #757575;
						}

						.desc {
							color: #757575;
						}
					}

					.btn-add {
						width: 30px;
						height: 30px;
						font-size: 28px;
						line-height: 26px;
						background-color: $border-color;
						border-radius: 50%;
						color: #FFFFFF;
						text-align: center;
						transition: all .4s ease;
					}

					.desc {
						margin-top: 8px;
						color: #b0b0b0;
						transition: all .4s ease;
					}
				}

				.box-address {
					position: relative;
					padding: 15px 24px 0;

					.top {
						line-height: 30px;
						margin-bottom: 10px;
						@extend %flex-align-center;
						justify-content: space-between;

						.user-name {
							font-size: 18px;
							color: $text-color;
						}

						.address-label {
							color: #757575;
						}
					}

					.phone-number,
					.address-detail {
						line-height: 22px;
						color: #757575;
					}
				}

				.btn-wrap {
					position: absolute;
					right: 24px;
					bottom: 10px;
					line-height: 22px;
					color: $color-primary;
					@extend %flex-align-center;
					display: none;

					.btn {
						cursor: pointer;
						margin-left: 10px;
					}
				}
			}
		}

		.btn-more {
			width: 100%;
			height: 50px;
			line-height: 50px;
			margin-bottom: 30px;
			background-color: #eee;
			color: #424242;
			text-align: center;
			cursor: pointer;

			.icon {
				display: inline-block;
				font-size: 12px;
				font-weight: bold;
				transform: rotate(90deg);
				margin-left: 5px;
				transition: transform .5s;

				&.active {
					transform: rotate(-90deg);
				}
			}
		}
	}
</style>
