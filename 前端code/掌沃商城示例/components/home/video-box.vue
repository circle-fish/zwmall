<template>
	<view class="wrapper" v-if="videos.length > 0">
		<view class="wrap">

			<view class="box-header">
				<view class="name">视频</view>
				<a class="btn-link" href="/pages/video/index" target="_blank">
					<text class="title">查看全部</text>
					<i class="icon i-right"></i>
				</a>
			</view>
			<view class="box-body">
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

			<play-dialog :hidden.sync="hiddenVideo" :title="currentVideo.name" :videoUrl="currentVideo.videoUrl"></play-dialog>

		</view>
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
			this.findVideos();
		},
		methods: {
			findVideos() {
				this.$util.request('/video', {}, (res) => {
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
		padding-bottom: 20px;

		.wrap {
			width: $page-width;
			margin: 0 auto;

			.box-header {
				@extend %flex-align-center;
				justify-content: space-between;

				.name {
					font-size: 22px;
					font-weight: 200;
					line-height: 58px;
					color: #333;
				}

				.btn-link {
					cursor: pointer;

					&:hover {
						.title {
							color: $color-primary;
						}

						.icon {
							background-color: $color-primary;
						}
					}

					.title {
						font-size: 16px;
						line-height: 58px;
						color: #424242;
						transition: all .4s;
					}

					.icon {
						width: 12px;
						height: 12px;
						padding: 4px;
						margin-left: 8px;
						border-radius: 16px;
						font-size: 12px;
						line-height: 12px;
						background-color: #b0b0b0;
						color: #fff;
						transition: all .4s;
					}
				}
			}

			.box-body {
				@extend %flex-align-center;
				justify-content: space-between;

				.video {
					cursor: pointer;
					width: 296px;
					height: 285px;
					background-color: $bg-color;
					transition: all .2s linear;

					&:hover {
						box-shadow: 0 15px 30px rgba(0, 0, 0, .1);
						transform: translate3d(0, -2px, 0);
					}

					.cover-wrap {
						position: relative;
						width: 296px;
						height: 180px;

						&:hover {
							.btn-play {
								background-color: $color-primary;
								border-color: $color-primary;
							}
						}

						.cover {
							width: 296px;
							height: 180px;
						}

						.btn-play {
							position: absolute;
							left: 20px;
							bottom: 10px;
							width: 32px;
							height: 20px;
							line-height: 18px;
							border: 2px solid #fff;
							border-radius: 12px;
							background-color: rgba(0, 0, 0, .6);
							transition: all .2s;
							text-align: center;

							.icon {
								color: #fff;
								font-size: 13px;
							}
						}
					}

					.video-info {
						margin-top: 28px;
						text-align: center;

						.title {
							margin: 0 14px 6px;
							font-weight: 400;
							color: #333;
						}

						.desc {
							height: 18px;
							margin: 0 14px;
							font-size: 12px;
							color: #b0b0b0;
						}
					}
				}
			}
		}
	}
</style>
