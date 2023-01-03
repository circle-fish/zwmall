<template>
	<view>
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="个人中心" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="2-3">
			<view class="title">收货地址</view>
			<view class="address-wrap">
				<view class="box box-add" @click="addAddress">
					<view class="btn-add">+</view>
					<view class="desc">添加新地址</view>
				</view>
				<view class="box" v-for="(item, index) in addresses" :key="index">
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
						<view class="btn" @click="deleteAddress(item.id)">删除</view>
					</view>
				</view>
			</view>
		</menu-wrapper>

		<!-- 添加/修改收货地址 -->
		<address-editer :hidden.sync="hiddenDialog" :address="address" @change="addressConfirm"></address-editer>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import addressEditer from '@/components/user/editer/address-editer.vue';

	export default {
		components: {
			menuWrapper,
			addressEditer
		},
		data() {
			return {
				addresses: [],
				hiddenDialog: true,
				address: {},
				addressIndex: -1
			}
		},
		onLoad() {
			this.findAllAddress();
		},
		methods: {
			findAllAddress() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				
				this.$util.request('/user_address/getaddress', {userId:this.$store.state.loginUser.id}, (res) => {
					console.log(res);
					uni.hideLoading();

					this.addresses = res.data.data;
				}, true);
			},
			addAddress() {
				this.addressIndex = -1;
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
				this.findAllAddress();
			},
			addressConfirm(e) {
				uni.showLoading({
					title: '处理中...',
					mask: true
				});

				const api = this.addressIndex == -1 ? 'addaddress' : 'updateaddress';
				this.$util.request('/user_address/' + api, e, (res) => {
					uni.hideLoading();

					if (res.code == 200) {
						this.hiddenDialog = true;
						this.findAllAddress();
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
				this.findAllAddress();
			},
			editAddress(item, index) {
				this.addressIndex = index;
				this.address = item;
				this.hiddenDialog = false;
				this.findAllAddress();
			},
			deleteAddress(id) {
				this.$alert('确定删除该地址吗？', {}, () => {
					uni.showLoading({
						title: '处理中...',
						mask: true
					});
					this.$util.request('/user_address/deleteaddress', {
						id: id
					}, (res) => {
						uni.hideLoading();

						if (res.state == 'ok') {
							this.findAllAddress();
						} else {
							this.$util.showErrorMsg(res.msg);
						}
					});
				});
				this.findAllAddress();
			}
		}
	}
</script>

<style lang="scss">
	.title {
		font-size: 30px;
		color: #757575;
		line-height: 68px;
	}

	.address-wrap {
		margin-top: 30px;
		display: flex;
		flex-wrap: wrap;

		.box {
			width: 268px;
			height: 178px;
			border: 1px solid $border-color;
			margin-right: 17px;
			margin-bottom: 20px;
			position: relative;
			transition: all .4s ease;

			&:hover {
				border-color: #b0b0b0;

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
						background-color: #b0b0b0;
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
</style>
