<template> 
	<view>
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="个人信息" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="3-1">
			<view class="profile-wrap">
				<view class="avatar-wrap">
					<view class="avatar-main">
						<img class="avatar" :src="loginUser.avatarUrl">
						<view class="btn-edit" @click="chooseImage">
							<i class="icon i-edit"></i>
						</view>
					</view>
					<view class="btn-change" @click="chooseImage">修改头像</view>
				</view>
				<view class="base-wrap">
					<view class="title">基础资料</view>
					<view class="item">昵称：{{loginUser.name}}</view>
					<view class="item">性别：{{loginUser.sex }}</view>
					<view class="item">ID：{{loginUser.id }}</view>
					<!-- <view class="item">性别：{{loginUser.sex | matchGender}}</view> -->
					<!-- <view class="item">生日：{{loginUser.birthday | formatBirthday}}</view>
					<view class="item">地区：{{loginUser.region || '未知'}}</view>
					<view class="item">签名：{{loginUser.motto || '待完善'}}</view> -->
					<view class="btn-edit" @click="hiddenProfileEditer = false;">
						<i class="icon i-edit"></i>
						<text>编辑</text>
					</view>
				</view>
			</view>
		</menu-wrapper>

		<!-- 头像裁剪 -->
		<avatar-cropper :hidden.sync="hiddenAvatarCropper" :imagePath.sync="imagePath" @change="avatarChange">
		</avatar-cropper>

		<!-- 编辑资料 -->
		<profile-editer :hidden.sync="hiddenProfileEditer" :loginUser="loginUser" @change="profileChange">
		</profile-editer>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import avatarCropper from '@/components/user/editer/avatar-cropper.vue';
	import profileEditer from '@/components/user/editer/profile-editer.vue';

	import {
		mapState
	} from 'vuex';

	export default {
		components: {
			menuWrapper,
			avatarCropper,
			profileEditer
		},
		data() {
			return {
				imagePath: '',
				hiddenAvatarCropper: true,
				hiddenProfileEditer: true
			}
		},
		computed: {
			...mapState(['loginUser'])
		},
		filters: {
			// matchGender(sex) {
			// 	switch (sex) {
			// 		case "1":
			// 			return '男';
			// 		case "2":
			// 			return '女';
			// 		default:
			// 			return '未知';
			// 	}
			// },
			formatBirthday(birthday) {
				if (birthday) {
					const units = ['年', '月', '日'];
					return birthday.split('-').map((e, i) => e + units[i]).join('');
				}
				return '未知';
			}
		},
		methods: {
			chooseImage() {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						this.imagePath = res.tempFilePaths[0];
						this.hiddenAvatarCropper = false;
					}
				});
			},
			avatarChange(e) {
				uni.showLoading({
					title: '上传中...',
					mask: true
				});
				// uni.request({
				// 	url:"http://192.168.77.254:8083/MYSSM_war_exploded/user/upload",
				// 	data:{
				// 		upfile:e,
				// 		username:this.$store.state.loginUser.id,
				// 		password:this.$store.state.loginUser.password
				// 	},
					
				// 	header:{
				// 		"Content-Type": "multipart/form-data",
				// 	},
				// 	method: 'POST',
				// 	success: (res) => {
				// 		console.log(res);
				// 	}
				// })
				// console.log(e);
				// this.$util.request('/user/upload', {
				// 	filePath: e,
				// 	name: 'upfile',
				// 	formData:{
				// 		username:this.$store.state.loginUser.id,
				// 		password:this.$store.state.loginUser.password
				// 	},
				// }, (res) => {
				// 	console.log(res.data);
				// 	this.hiddenAvatarCropper = true;
				// 	uni.hideLoading();
				// 		if (res.data.code == "200") {
				// 			this.hiddenAvatarCropper = true;
				// 		}
				// 	});
				uni.uploadFile({
					url: "http://192.168.77.254:8083/MYSSM_war_exploded/user/upload", //后端用于处理图片并返回图片地址的接口 
					// url: "http://192.168.77.254:8083/MYSSM_war_exploded/user/upload?username="+this.$store.state.loginUser.id
					// +"&password="+this.$store.state.loginUser.password, //后端用于处理图片并返回图片地址的接口 
					filePath: e,
					// Content-Type:multipart/form-data,
					name: 'upfile',
					formData:{
						username:this.$store.state.loginUser.id,
						password:this.$store.state.loginUser.password
					},
					success: res => {
					console.log(res);
					uni.hideLoading();
						if (res.data.code == "200") {
							// const user = Object.assign({
							// 	...this.loginUser
							// }, {
							// 	avatarUrl: res.avatarUrl
							// });
							// this.$store.commit('login', user);

							this.hiddenAvatarCropper = true;
						}
					},
					fail: (err) => {
						this.$util.showErrorMsg(err);
					}
				});
				// uni.uploadFile('http://localhost:8083/upload', e, (res) => {
				// 	uni.hideLoading();
				//                 console.log(res);
				// 	// if (res.code == 200) {
				// 	// 	const user = Object.assign({
				// 	// 		...this.loginUser
				// 	// 	}, {
				// 	// 		avatarUrl: res.avatarUrl
				// 	// 	});
				// 	// 	this.$store.commit('login', user);

				// 	// 	this.hiddenAvatarCropper = true;
				// 	// } else {
				// 	// 	this.$util.showErrorMsg(res.msg);
				// 	// }
				// }, 'avatar');
			},
			profileChange(e) {
				uni.showLoading({
					title: '提交中...',
					mask: true
				});

				this.$util.request('/profile/update', e, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.$store.commit('login', e);
						this.hiddenProfileEditer = true;
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.profile-wrap {
		height: 100%;
		@extend %flex-align-center;

		.avatar-wrap {
			width: 300px;
			display: flex;
			flex-direction: column;
			align-items: center;

			.avatar-main {
				position: relative;
				width: 80px;
				height: 80px;
				border: 2px solid #e3e3e3;
				border-radius: 50%;
				padding: 4px;
				background-color: $bg-color;

				&:hover {

					.btn-edit {
						display: block;
					}
				}

				.avatar {
					width: 80px;
					height: 80px;
					border-radius: 50%;
				}

				.btn-edit {
					cursor: pointer;
					position: absolute;
					top: 21px;
					left: 21px;
					border: 1px solid $border-color;
					width: 43px;
					height: 43px;
					line-height: 43px;
					border-radius: 50%;
					text-align: center;
					background-color: rgba(0, 0, 0, .5);
					display: none;

					.icon {
						color: #FFFFFF;
						font-size: 16px;
					}
				}
			}

			.btn-change {
				color: #4a90e3;
				padding-top: 15px;
				cursor: pointer;
			}
		}

		.base-wrap {
			flex: 1;
			position: relative;

			.title {
				font-size: 16px;
				font-weight: bold;
				color: $text-color;
				line-height: 40px;
			}

			.item {
				line-height: 40px;
				color: #666;
			}

			.btn-edit {
				position: absolute;
				top: 0;
				right: 0;
				color: #4a90e3;
				line-height: 40px;
				cursor: pointer;

				.icon {
					color: #4a90e3;
					font-size: 16px;
					margin-right: 5px;
				}
			}
		}
	}
</style>
