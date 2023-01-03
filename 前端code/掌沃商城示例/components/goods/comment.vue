<template>
	<view class="comment-wrapper">
		<view class="wrap">

			<view class="comment-header" v-if="scores[0] > 0">
				<view class="title">大家认为</view>
				<view class="categories">
					<block v-for="(item, index) in categories" :key="index" v-if="index < 6">
						<view :class="['item', {'active': categoryIndex == index}]" @click="switchCategory(index)">{{item}}（{{scores[index]}}）</view>
					</block>
				</view>
			</view>
			<view class="comment-empty" v-else>该商品暂无评论</view>

			<view class="comment-main" v-if="scores[0] > 0">
				<view class="comment-top">
					<view class="title">热门评价</view>
					<view class="btn-check">
						<label @click="switchOnlyPhotos">
							<checkbox :checked="onlyHasPhotos" color="#FF6700"></checkbox>
							<text>只显示带图评价</text>
						</label>
					</view>
				</view>
				<view class="comment-mid">
					<view class="min-left">
						<view class="comment" v-for="(item, index) in comments" :key="index">
							<view class="top">
								<img class="avatar" :src="item.avatarUrl" />
								<view class="publish-info">
									<view class="user-name elip">{{item.userName}}</view>
									<view class="publish-date">{{item.createTime | filterDate}}</view>
								</view>
								<rate class="love" :score="item.score"></rate>
							</view>
							<view class="main">
								<view class="content">{{item.content}}</view>
								<view class="photos" v-if="item.photoUrls && item.photoUrls.length > 0">
									<block v-for="(photo, photoIndex) in item.photoUrls" :key="photoIndex">
										<image class="photo" :src="photo" mode="aspectFill" @click="previewPhoto(photo)"></image>
									</block>
								</view>
								<view :class="['like', {'active': item.liked}]" v-if="item.content || item.photoUrls">
									<view class="like-wrap" @click="switchLike(item)">
										<i class="icon i-like"></i>
										<text class="count">{{item.likes}}</text>
									</view>
								</view>
							</view>
						</view>
						<view class="comment-empty" v-if="comments.length == 0">暂无评论</view>

						<view class="btn-more" v-if="!isLastPage" @click="loadMore">加载更多</view>
					</view>
					<view class="min-right">
						<view class="score-result">
							<view class="title">
								<text class="value">{{satisfactions}}</text>
								<text>人购买后满意</text>
							</view>
							<view class="degree">
								<view class="value" :style="{'width': satisfactionDegree + '%'}">
									<block v-if="satisfactionDegree > 25">满意度：{{satisfactionDegree}}%</block>
								</view>
							</view>
						</view>
						<view class="btn-gotop" v-if="scrollTop > 500" @click="goTop"></view>
					</view>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	import rate from '@/components/order/rate.vue';

	export default {
		components: {
			rate
		},
		props: {
			goodsId: Number,
			scrollTop: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				categories: ['全部', '超爱', '喜欢', '满意', '一般', '失望'],
				categoryIndex: 0,
				scores: [],
				onlyHasPhotos: false,
				satisfactions: 0,
				satisfactionDegree: 0,
				comments: [],
				pageNumber: 1,
				isLastPage: false
			}
		},
		filters: {
			filterDate(date) {
				return date.split(' ')[0];
			}
		},
		created() {
			this.findScores();
			this.findComments();
		},
		computed: {
			score() {
				return this.categoryIndex != 0 ? Math.abs(this.categoryIndex - 6) : 0;
			}
		},
		methods: {
			findScores() {
				this.$util.request('/comment/goodsScores', {
					goodsId: this.goodsId
				}, (res) => {
					this.scores = res;

					// 计算满意人数
					this.satisfactions = res[1] + res[2] + res[3];
					// 计算满意度
					this.satisfactionDegree = (this.satisfactions / res[0] * 100).toFixed(0);
				}, true);
			},
			findComments(goNextPage = false) {
				if (!goNextPage) {
					this.pageNumber = 1;
				}

				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/comment', {
					goodsId: this.goodsId,
					score: this.score,
					onlyHasPhotos: this.onlyHasPhotos,
					pageNumber: this.pageNumber
				}, (res) => {
					uni.hideLoading();

					res.list.forEach(e => {
						if (e.photoUrls) e.photoUrls = e.photoUrls.split(',');
					});

					if (this.pageNumber == 1) {
						this.comments = res.list;
					} else {
						this.comments.push(...res.list);
					}
					this.isLastPage = res.lastPage;
				}, true);
			},
			loadMore() {
				this.pageNumber++;
				this.findComments(true);
			},
			switchCategory(index) {
				this.categoryIndex = index;
				this.findComments();
			},
			switchOnlyPhotos() {
				this.onlyHasPhotos = !this.onlyHasPhotos;
				this.findComments();
			},
			previewPhoto(url) {
				uni.previewImage({
					urls: [url]
				});
			},
			switchLike(item) {
				// 判断登录状态
				if (!this.$store.getters.hasLogin) {
					uni.navigateTo({
						url: '/pages/user/login/index?callbackUrl=' + encodeURIComponent(`/pages/goods/index?id=${this.goodsId}&active=comment`)
					});
					return;
				}

				uni.showLoading({
					title: '处理中...',
					mask: true
				});
				this.$util.request('/comment/switchLike', {
					id: item.id,
					liked: item.liked ? false : true
				}, (res) => {
					uni.hideLoading();

					if (res.state == 'ok') {
						item.liked = item.liked ? false : true;
						if (item.liked) {
							item.likes++;
						} else {
							item.likes--;
						}
					} else {
						this.$util.showErrorMsg(res.msg);
					}
				});
			},
			goTop() {
				uni.pageScrollTo({
					scrollTop: 0,
					duration: 100
				});
			}
		}
	}
</script>

<style lang="scss">
	.comment-wrapper {
		background-color: $bg-color-grey;
		display: flex;
		flex-direction: column;

		.wrap {
			width: $page-width;
			margin: 0 auto;

			.comment-header {
				margin: 30px 0;
				padding: 40px;
				background-color: $bg-color;

				.title {
					font-size: 18px;
					color: #757575;
					line-height: 28px;
				}

				.categories {
					margin-top: 14px;
					@extend %flex-align-center;
					justify-content: space-between;

					.item {
						min-width: 168px;
						height: 44px;
						line-height: 44px;
						text-align: center;
						color: #b0b0b0;
						border: 1px solid #eee;
						background: #f5f5f5;
						transition: all .2s;
						cursor: pointer;
						transition: all .2s;

						&:hover {
							opacity: .8;
						}

						&.active {
							border-color: $color-primary;
							background-color: $color-primary;
							color: #fff;
							cursor: default;
						}
					}
				}
			}

			.comment-empty {
				font-size: 20px;
				text-align: center;
				padding: 58.5px 0;
				margin: 30px 0;
				background-color: $bg-color;
			}

			.comment-main {
				padding-bottom: 30px;

				.comment-top {
					width: 792px;
					height: 50px;
					line-height: 40px;
					font-size: 22px;
					color: #616161;
					@extend %flex-align-center;
					justify-content: space-between;

					.title {
						line-height: 40px;
						font-size: 22px;
						color: #616161;
					}

					.btn-check {
						color: #b0b0b0;
						line-height: 40px;
						cursor: pointer;
						font-size: 14px;

						checkbox {
							transform: scale(.7);
						}
					}
				}

				.comment-mid {
					display: flex;

					.min-left {
						width: 792px;

						.comment {
							padding: 40px;
							background-color: $bg-color;
							margin-top: 15px;

							&:first-child {
								margin-top: 0;
							}

							.top {
								@extend %flex-align-center;

								.avatar {
									width: 47px;
									height: 47px;
								}

								.publish-info {
									margin-left: 10px;

									.user-name {
										max-width: 220px;
										height: 18px;
										line-height: 18px;
										font-size: 16px;
										color: #8d665a;
									}

									.publish-date {
										height: 20px;
										line-height: 20px;
										margin-top: 6px;
										color: #b0b0b0;
									}
								}

								.love {
									margin-left: auto;
								}
							}

							.main {
								padding-left: 56px;

								.content {
									margin-top: 8px;
									color: #5e5e5e;
									font-size: 18px;
									line-height: 27px;
								}

								.photos {
									margin-top: 15px;
									display: flex;
									flex-wrap: wrap;

									.photo {
										width: 160px;
										height: 160px;
										margin: 0 8px 8px 0;
										cursor: pointer;
									}
								}

								.like {
									margin-top: 8px;
									line-height: 33px;
									display: flex;

									&.active {
										.like-wrap .icon {
											color: $color-primary;
										}
									}

									.like-wrap {
										cursor: pointer;

										.icon {
											font-size: 22px;
											line-height: 33px;
											color: #757575;
										}

										.count {
											line-height: 33px;
											color: #757575;
											margin-left: 2px;
										}
									}
								}
							}
						}

						.btn-more {
							color: $color-primary;
							width: 100%;
							height: 45px;
							margin-top: 15px;
							line-height: 45px;
							background-color: $bg-color;
							text-align: center;
							cursor: pointer;
						}
					}

					.min-right {
						margin-left: auto;
						width: 420px;
						position: relative;

						.score-result {
							margin-bottom: 30px;
							padding: 22px 30px 40px;
							background-color: $bg-color;

							.title {
								color: #b0b0b0;

								.value {
									font-size: 60px;
									color: #ff6700;
								}
							}

							.degree {
								height: 22px;
								margin-top: 10px;
								background: #f1f2f6;
								position: relative;

								.value {
									height: 22px;
									line-height: 22px;
									background-color: $color-success;
									text-align: center;
									font-size: 12px;
									color: #fff;
									position: absolute;
									top: 0;
									left: 0;
								}
							}
						}

						.btn-gotop {
							position: absolute;
							bottom: 0;
							left: 0;
							margin-top: 15px;
							width: 110px;
							height: 110px;
							background: #fff url(//c1.mifile.cn/f/i/17/comment2017/arrow.png) 50% no-repeat;
							cursor: pointer;
						}
					}
				}
			}
		}
	}
</style>
