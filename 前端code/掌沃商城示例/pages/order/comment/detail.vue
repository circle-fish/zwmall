<template>
	<view v-if="goodsList.length > 0">
		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb :pages="[{name: '订单评价', linkUrl: '/pages/order/comment/index'}, {name: '服务评价'}]" separator="/"></bread-crumb>

		<!-- 左侧导航栏 -->
		<menu-wrapper active-index="1-2">
			<!-- 标题 -->
			<view class="title">服务评价</view>
			<!-- 快递评价 -->
			<view class="express-comment">
				<view class="main-wrap">
					<view class="left">
						<rate class="rate" name="快递包装" :score="express.packagingScore" :disabled="expressDisabled" :showSuffix="false"
						 @change="packagingScoreChange"></rate>
						<rate class="rate" name="快递速度" :score="express.speedScore" :disabled="expressDisabled" :showSuffix="false"
						 @change="speedScoreChange"></rate>
					</view>
					<view class="right comment-box">
						<textarea class="textarea-comment" placeholder="还有想说的吗？您的意见对我们非常重要" maxlength="500" auto-height v-model="express.comment"
						 :disabled="expressDisabled"></textarea>
						<view class="font-count">{{express.comment.length}}/500</view>
					</view>
				</view>
				<view class="btn-wrap" v-if="!expressDisabled">
					<button class="btn" @click="submitExpressComment">发表评价</button>
				</view>
			</view>
			<!-- 商品评价 -->
			<view class="goods-comment" v-for="(item, index) in goodsList" :key="index">
				<view class="main-wrap">
					<navigator :url="`/pages/goods/index?id=${item.goodsId}`">
						<image class="thumb" :src="item.thumbUrl" mode="aspectFit"></image>
					</navigator>
					<view class="right">
						<view class="top">
							<rate class="rate" name="评分" :score="item.score" :disabled="item.scoreStatus == 1" :showSuffix="false" @change="goodsScoreChange($event, index)"></rate>
							<view class="level">{{levels[item.score - 1]}}</view>
						</view>
						<view class="bottom comment-box">
							<textarea class="textarea-comment" placeholder="外形如何？品质如何？写写你的感受分享给网友吧!" maxlength="500" auto-height v-model="item.comment"
							 :disabled="item.scoreStatus == 1"></textarea>
							<view class="font-count">{{item.comment.length}}/500</view>
						</view>
					</view>
				</view>
				<view class="bottom-wrap">
					<navigator class="goods-name" :url="`/pages/goods/index?id=${item.goodsId}`">{{item.name}}</navigator>
					<view class="right">
						<block v-for="(url, photoIndex) in item.photoUrls" :key="photoIndex">
							<image class="photo" :src="url" mode="aspectFill" @click="previewPhoto(url)" @contextmenu.prevent="deletePhoto(item.photoUrls, photoIndex, item.scoreStatus)"></image>
						</block>
						<view class="btn-upload" @click="choosePhoto(item.photoUrls)" v-if="item.photoUrls.length < 6 && item.scoreStatus == 0">
							<i class="icon i-plus"></i>
						</view>
						<button class="btn" @click="submitGoodsComment(item)" v-if="item.scoreStatus == 0">发表评价</button>
					</view>
				</view>
			</view>
		</menu-wrapper>

		<!-- 页脚 -->
		<zw-footer></zw-footer>
	</view>
</template>

<script>
	import menuWrapper from '@/components/user/common/menu-wrapper.vue';
	import rate from '@/components/order/rate.vue';

	export default {
		components: {
			menuWrapper,
			rate
		},
		data() {
			return {
				orderNo: '',
				express: {},
				goodsList: [],
				levels: ['失望', '一般', '满意', '喜欢', '超爱']
			}
		},
		onLoad(e) {
			if (!e.orderNo) {
				uni.redirectTo({
					url: '/pages/index'
				});
				return;
			}
			this.orderNo = e.orderNo;

			this.findDetails();
		},
		computed: {
			expressDisabled() {
				return this.express.scoreStatus == 1;
			}
		},
		methods: {
			findDetails() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/order/comment/details', {
					orderNo: this.orderNo
				}, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.express = res.express;
						this.goodsList = res.goodsList.map(e => {
							e.photoUrls = e.photoUrls ? e.photoUrls.split(',') : [];
							return e;
						});
					} else {
						this.$alert(res.msg, {
							showCancel: false
						});
					}
				}, true);
			},
			packagingScoreChange(e) {
				this.express.packagingScore = e;
			},
			speedScoreChange(e) {
				this.express.speedScore = e;
			},
			goodsScoreChange(score, index) {
				this.goodsList[index].score = score;
			},
			submitExpressComment() {
				uni.showLoading({
					title: '提交中...',
					mask: true
				});
				this.$util.request('/order/comment/submitExpressComment', {
					orderNo: this.orderNo,
					packagingScore: this.express.packagingScore,
					speedScore: this.express.speedScore,
					comment: this.express.comment
				}, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.findDetails();
					} else {
						this.$alert(res.msg, {
							showCancel: false
						});
					}
				});
			},
			choosePhoto(photos) {
				const count = 6 - photos.length;
				uni.chooseImage({
					count: 6 - photos.length,
					success: (res) => {
						const paths = res.tempFilePaths.filter((e, i) => i < count);
						paths.forEach(e => {
							uni.showLoading({
								title: '上传中...',
								mask: true
							});
							this.$util.upload('/order/comment/uploadPhoto', e, (res) => {
								uni.hideLoading();

								if (res.state == 'ok') {
									photos.push(res.url);
								} else {
									this.$util.showErrorMsg(res.msg);
								}
							}, 'comment');
						});
					}
				});
			},
			previewPhoto(url) {
				uni.previewImage({
					urls: [url]
				});
			},
			deletePhoto(urls, index, status) {
				if (status == 1) return;

				this.$alert('确定删除该图片吗？', {}, () => {
					urls.splice(index, 1);
				});
			},
			submitGoodsComment(goods) {
				uni.showLoading({
					title: '提交中...',
					mask: true
				});
				this.$util.request('/order/comment/submitGoodsComment', {
					orderNo: this.orderNo,
					skuId: goods.skuId,
					score: goods.score,
					comment: goods.comment,
					photoUrls: goods.photoUrls.join(',')
				}, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						this.findDetails();
					} else {
						this.$alert(res.msg, {
							showCancel: false
						});
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.title {
		font-size: 30px;
		font-weight: bold;
		color: #757575;
		line-height: 68px;
	}

	.express-comment {
		padding: 25px 0;

		.main-wrap {
			@extend %flex-align-center;

			.left {
				margin-right: 50px;

				.rate {
					margin: 10px 0;
				}
			}

			.right {
				flex: 1;

			}
		}

		.btn-wrap {
			display: flex;
			justify-content: flex-end;
			padding-top: 15px;
		}
	}

	.goods-comment {
		padding: 30px 0;
		border-top: 1px solid $border-color;

		.main-wrap {
			@extend %flex-align-center;

			.thumb {
				width: 120px;
				height: 120px;
				cursor: pointer;
				border: 1px solid $border-color;
			}

			.right {
				flex: 1;
				margin-left: 20px;

				.top {
					@extend %flex-align-center;
					line-height: 40px;

					.level {
						margin-left: 15px;
						color: #ffaa00;
					}
				}
			}

			.textarea-comment {
				font-size: 14px;
			}
		}

		.bottom-wrap {
			padding-top: 15px;
			@extend %flex-align-center;
			justify-content: space-between;

			.goods-name {
				max-width: 120px;
				color: $text-color;
				text-align: center;
				cursor: pointer;

				&:hover {
					color: $color-primary;
				}
			}

			.right {
				flex: 1;
				margin-left: 20px;
				@extend %flex-align-center;

				.btn-upload {
					width: 90px;
					height: 90px;
					line-height: 90px;
					text-align: center;
					cursor: pointer;
					transition: all .3s;
					margin-right: 15px;
					background-color: $bg-color-grey;

					&:hover {
						background-color: $border-color;
					}

					.icon {
						color: #999;
						font-size: 28px;
					}
				}

				.photo {
					width: 90px;
					height: 90px;
					cursor: pointer;
					margin-right: 15px;
				}

				.btn {
					margin-left: auto;
				}
			}
		}
	}

	.comment-box {
		position: relative;
		padding: 20px;
		padding-bottom: 30px;
		border: 1px solid $border-color;

		.textarea-comment {
			width: 100%;
			min-height: 30px;
			font-size: 16px;
			color: $text-color;
		}

		.font-count {
			position: absolute;
			right: 10px;
			bottom: 10px;
			font-size: 12px;
			color: $text-color-grey;
		}
	}

	.btn {
		border: 1px solid $border-color;
		color: #757575;
		text-align: center;
		width: 100px;
		height: 30px;
		line-height: 30px;
		margin: 0;
		font-size: 12px;
		cursor: pointer;
		background-color: $bg-color;

		&:hover {
			color: #fff;
			background-color: $color-primary;
			border-color: $color-primary;
		}
	}
</style>
