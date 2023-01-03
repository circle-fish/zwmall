<template>
	<view>

		<!-- 页首 -->
		<zw-header></zw-header>

		<!-- 面包屑 -->
		<bread-crumb pages="视频列表" separator="/"></bread-crumb>

		<view class="wrapper">
			<view class="wrap">

				<!-- 标题 -->
				<view class="page-title">全部视频</view>

				<!-- 视频列表 -->
				<view class="videos" v-if="videos.length > 0">
					<block v-for="(item, index) in videos" :key="index">
						<view class="video" title="点击播放视频" @click="videoPlay(item)">
							<!-- 视频封面 -->
							<view class="cover-wrap">
								<img class="cover" :src="item.coverUrl" />
								<view class="btn-play">
									<i class="icon i-play"></i>
								</view>
							</view>
							<!-- 视频信息 -->
							<view class="video-info">
								<view class="title elip">{{item.name}}</view>
								<view class="desc elip">{{item.title}}</view>
							</view>
						</view>
					</block>
				</view>
				<view class="video-empty" v-else>抱歉，暂时没有任何视频哦~</view>
			</view>
		</view>

		<!-- 视频播放弹窗 -->
		<play-dialog :hidden.sync="hiddenVideo" :title="currentVideo.name" :videoUrl="currentVideo.videoUrl"></play-dialog>

		<!-- 页脚 -->
		<zw-footer></zw-footer>

	</view>
</template>

<script>
	import playDialog from '@/components/video/play-dialog.vue';

	export default {
		components: {
			playDialog
		},
		data() {
			return {
				videos: [],
				hiddenVideo: true,
				currentVideo: {}
			}
		},
		created() {
			this.findAllVideos();
		},
		methods: {
			findAllVideos() {
				uni.showLoading({
					title: '加载中...',
					mask: true
				});
				this.$util.request('/video', {
					all: true
				}, (res) => {
					uni.hideLoading();
					this.videos = res;
				}, true);
			},
			videoPlay(video) {
				this.currentVideo = video;
				this.hiddenVideo = false;
			}
		}
	}
</script>

<style lang="scss">
	.wrapper {
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 0 auto;

			.page-title {
				background-color: $bg-color;
				height: 80px;
				line-height: 80px;
				text-indent: 40px;
				color: #424242;
				font-size: 20px;
				font-weight: 400;
			}

			.videos {
				margin-top: 14px;
				@extend %flex-align-center;
				flex-wrap: wrap;
				justify-content: space-between;

				.video {
					cursor: pointer;
					width: 606px;
					height: 490px;
					margin-bottom: 14px;
					background-color: $bg-color;
					transition: all .2s linear;

					&:hover {
						box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
						transform: translate3d(0, -2px, 0);
					}

					.cover-wrap {
						position: relative;
						width: 606px;
						height: 360px;

						&:hover {
							.btn-play {
								background-color: $color-primary;
								border-color: $color-primary;
							}
						}

						.cover {
							width: 606px;
							height: 360px;
						}

						.btn-play {
							position: absolute;
							left: 40px;
							bottom: 20px;
							width: 66px;
							height: 44px;
							line-height: 18px;
							border: 2px solid #fff;
							border-radius: 24px;
							transition: all .2s;
							text-align: center;

							.icon {
								color: #fff;
								font-size: 20px;
								line-height: 44px;
							}
						}
					}

					.video-info {
						margin-top: 28px;
						padding-left: 40px;

						.title {
							font-size: 24px;
							font-weight: 400;
							color: #333;
							line-height: 1.2;
						}

						.desc {
							margin-top: 10px;
							color: #757575;
						}
					}
				}
			}
			
			.video-empty {
				color: $text-color;
				padding: 3rem 0;
			}
		}
	}
</style>
