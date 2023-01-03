<template>
	<view class="wrapper">
		<view class="wrap">

			<view class="box-header">
				<view class="title">闪购</view>
				<view class="swiper-controls">
					<view class="item" :class="{'disabled': disabledLeft}" @click="goLeft(true);">
						<i class="icon i-left"></i>
					</view>
					<view class="item" :class="{'disabled': disabledRight}" @click="goRight(true);">
						<i class="icon i-right"></i>
					</view>
				</view>
			</view>
			<view class="box-body">
				<!-- 计时器 -->
				<view class="countdown-wrapper">
					<view class="round">{{startTime}} 场</view>
					<img class="countdown-icon" src="/static/image/home/flash-sale.png"></image>
					<view class="desc">{{countdownDesc}}</view>
					<view class="countdown">
						<view class="time">{{timeRemaining[0]}}</view>
						<view class="separator">:</view>
						<view class="time">{{timeRemaining[1]}}</view>
						<view class="separator">:</view>
						<view class="time">{{timeRemaining[2]}}</view>
					</view>
				</view>
				<!-- 商品列表 -->
				<view class="goods-wrapper">
					<view class="goods-wrap" :style="{'transform': `translateX(${-moveDistance}px)`}">
						<block v-for="(item, index) in goodsList" :key="index" v-if="index < 50">
							<navigator class="goods" :url="`/pages/goods/index?id=${item.id}`" :style="{'border-color': getColor(index)}">
								<img class="thumb" :src="item.thumbUrl" />
								<view class="name elip">{{item.name}}</view>
								<view class="desc elip">{{item.desc}}</view>
								<view class="prices">
									<view class="price-current">{{item.currentPrice}}元</view>
									<view class="price-original">{{item.originalPrice}}元</view>
								</view>
							</navigator>
						</block>
					</view>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	import seckill from '@/common/config/home/seckill.config.js';

	export default {
		data() {
			return {
				...seckill,
				startTime: '14:00',
				duration: '24:00',
				timeRemaining: ['00', '00', '00'],
				countdownMode: 0,
				moveDistance: 0
			}
		},
		computed: {
			countdownDesc() {
				let descs = ['距离结束还有', '本场已结束', '距离开始还有'];
				return descs[this.countdownMode];
			},
			goodsWrapWidth() {
				return this.goodsList.length * (234 + 14);
			},
			fourGoodsWidth() {
				return (234 + 14) * 4;
			},
			disabledLeft() {
				return this.moveDistance <= 0;
			},
			disabledRight() {
				return this.moveDistance >= this.goodsWrapWidth - this.fourGoodsWidth;
			}
		},
		mounted() {
			this.startCountdown();
			this.autoMove();
		},
		methods: {
			startCountdown() {
				this.timer = setInterval(() => {
					const now = new Date();
					// 获取今天的日期
					const todayArray = [now.getFullYear(), now.getMonth() + 1, now.getDate()];
					const today = todayArray.map(e => {
						return e < 10 ? '0' + e : e;
					}).join('/');

					const endTime = new Date(`${today} ${this.startTime}:00`);
					if (endTime.getTime() - now.getTime() < 0) {
						// 计算闪购结束时间
						const duration = this.duration.split(':');
						endTime.setHours(endTime.getHours() + parseInt(duration[0]));
						endTime.setMinutes(endTime.getMinutes() + parseInt(duration[1]));
					} else if (this.countdownMode != 2) {
						this.countdownMode = 2;
					}

					// 计算相差的时间
					const timeDiff = endTime.getTime() - now.getTime();
					if (timeDiff <= 0) {
						this.countdownMode = 1;
						clearInterval(this.timer);
						return;
					}
					const totalSeconds = Math.floor(timeDiff / 1000);
					const hours = Math.floor(totalSeconds / 60 / 60);
					const minutes = Math.floor((totalSeconds - hours * 60 * 60) / 60);
					const seconds = Math.floor(totalSeconds - hours * 60 * 60 - minutes * 60);
					this.timeRemaining = [hours, minutes, seconds].map(e => {
						return e < 10 ? '0' + e : e;
					});

					if (totalSeconds < 1) {
						if (this.countdownMode == 2) {
							this.countdownMode = 0;
						} else {
							this.countdownMode = 1;
							clearInterval(this.timer);
						}
					}
				}, 1000);
			},
			getColor(index) {
				return this.colors[index % this.colors.length];
			},
			goLeft(byClick) {
				if (this.disabledLeft) return;
				if (byClick) this.suspendAutoMove();
				if (this.moveDistance - this.fourGoodsWidth >= 0) {
					this.moveDistance -= this.fourGoodsWidth;
				} else {
					this.moveDistance -= this.moveDistance;
				}
			},
			goRight(byClick) {
				if (this.disabledRight) return;
				if (byClick) this.suspendAutoMove();
				if (this.goodsWrapWidth - this.moveDistance >= this.fourGoodsWidth * 2) {
					this.moveDistance += this.fourGoodsWidth;
				} else {
					this.moveDistance += this.goodsWrapWidth - this.moveDistance - this.fourGoodsWidth;
				}
			},
			suspendAutoMove() {
				clearInterval(this.autoMoveTimer);
				clearTimeout(this.startMoveTimer);
				this.startMoveTimer = setTimeout(() => {
					this.autoMove();
				}, 5000);
			},
			autoMove() {
				if (this.goodsWrapWidth > this.fourGoodsWidth) {
					let mode = 0;
					this.autoMoveTimer = setInterval(() => {
						if (!this.disabledRight && mode == 0) {
							this.goRight();
						} else {
							this.goLeft();
							mode = !this.disabledLeft ? 1 : 0;
						}
					}, 5000);
				}
			}
		}
	}
</script>

<style lang="scss">
	.wrapper {
		background-color: $bg-color-grey;

		.wrap {
			width: $page-width;
			margin: 22px auto 0;

			.box-header {
				height: 58px;
				@extend %flex-align-center;
				justify-content: space-between;

				.title {
					color: #333;
					font-size: 22px;
					font-weight: 200;
					line-height: 58px;
				}

				.swiper-controls {
					@extend %flex-align-center;

					.item {
						border: 1px solid $border-color;
						width: 24px;
						height: 16px;
						padding: 3px 5px;
						line-height: 16px;
						text-align: center;
						cursor: pointer;

						.icon {
							color: #b0b0b0;
							font-weight: bold;
							transition: color .5s;
						}

						&:last-child {
							border-left: none;
						}

						&:hover {
							.icon {
								color: $color-primary;
							}
						}

						&.disabled {
							cursor: not-allowed;

							.icon {
								color: $border-color;
							}

							&:hover {
								.icon {
									color: $border-color;
								}
							}
						}
					}
				}
			}

			.box-body {
				@extend %flex-align-center;

				.countdown-wrapper {
					width: 234px;
					height: 300px;
					padding-top: 39px;
					border-top: 1px solid #e53935;
					background: #f1eded;
					text-align: center;

					.round {
						font-size: 21px;
						color: #ef3a3b;
						padding-top: 15px;
					}

					.countdown-icon {
						margin: 25px auto;
						width: 34px;
						height: 53px;
					}

					.desc {
						color: rgba(0, 0, 0, .54);
						font-size: 15px;
					}

					.countdown {
						width: 168px;
						margin: 28px auto 0;
						@extend %flex-align-center;

						.time {
							width: 46px;
							height: 46px;
							background: #605751;
							color: #fff;
							font-size: 24px;
							line-height: 46px;
						}

						.separator {
							width: 15px;
							height: 46px;
							line-height: 46px;
							color: #605751;
							font-size: 28px;
						}
					}
				}

				.goods-wrapper {
					width: 978px;
					margin-left: 14px;
					overflow: hidden;

					.goods-wrap {
						@extend %flex-align-center;
						transition: all .5s;

						.goods {
							width: 234px;
							height: 300px;
							padding-top: 39px;
							margin-right: 14px;
							text-align: center;
							background-color: $bg-color;
							border-top: 1px solid;
							cursor: pointer;
							flex-shrink: 0;

							.thumb {
								width: 160px;
								height: 160px;
								margin: 0 auto 22px;
							}

							.name {
								margin: 0 20px 3px;
								font-weight: 400;
								color: #212121;
							}

							.desc {
								height: 18px;
								margin: 0 20px 12px;
								font-size: 12px;
								color: #b0b0b0;
							}

							.prices {
								@extend %flex-align-center;
								justify-content: center;

								.price-current {
									color: $color-primary;
									margin-right: 5px;
								}

								.price-original {
									color: #b0b0b0;
									text-decoration: line-through;
								}
							}
						}
					}
				}
			}
		}
	}
</style>
