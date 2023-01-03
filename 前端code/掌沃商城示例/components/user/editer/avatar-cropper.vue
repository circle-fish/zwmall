<template>
	<zw-dialog :hidden="hidden" title="修改头像" :width="450" :height="350" :showHeaderBg="false" :hideFooter="false"
	 :showFooterBg="false" cancelText="重新选择" borderRadius @close="closeDialog" @cancel="chooseImage" @confirm="submit">
		<view class="cropper-wrap">
			<!-- 承载图片画布 -->
			<canvas class="canvas-image" canvas-id="imageCanvas"></canvas>
			<!-- 裁剪框画布 -->
			<canvas class="canvas-cropper" canvas-id="cropperCanvas" ref="cropperCanvas" @mousedown="onMoveStart"></canvas>
		</view>
	</zw-dialog>
</template>

<script>
	const width = 150;

	export default {
		props: {
			hidden: {
				type: Boolean,
				default: true
			},
			imagePath: {
				type: String,
				default: ''
			}
		},
		watch: {
			imagePath() {
				this.initAvatar();
			}
		},
		data() {
			return {
				offset: {
					x: 0,
					y: 0
				}
			}
		},
		methods: {
			initAvatar() {
				// 获取画布上下文
				this.imageCtx = uni.createCanvasContext('imageCanvas', this);
				this.cropperCtx = uni.createCanvasContext('cropperCanvas', this);

				// 绘制图片
				this.drawImage();

				// 绘制裁剪框
				setTimeout(() => {
					this.drawCropper();
				}, 50);
			},
			drawImage() {
				// 获取该图片的宽高信息
				uni.getImageInfo({
					src: this.imagePath,
					success: (res) => {
						// 图片绘制的宽度，固定为300
						const dWidth = 300;
						// 图片绘制的高度，公式：高宽比*300，完整展示图片且不变形
						const dHeight = res.height / res.width * dWidth;
						// 图片在画布横轴的起始位置，固定为0
						const dx = 0;
						// 图片在画布竖轴的起始位置，公式：(300-图片将要绘制的高)/2
						const dy = (dWidth - dHeight) / 2;
						// 添加图片绘制描述
						this.imageCtx.drawImage(this.imagePath, dx, dy, dWidth, dHeight);
						// 执行绘制
						this.imageCtx.draw();
					}
				});
			},
			drawCropper(offsetX = 0, offsetY = 0) {
				const defaultPoint = 75;
				// 累加上次的偏移
				offsetX += this.offset.x;
				offsetY += this.offset.y;
				// 绘制遮罩层
				this.cropperCtx.setFillStyle('rgba(0, 0, 0, .5)');
				this.cropperCtx.fillRect(0, 0, 300, 300);
				// 清除裁剪框背景
				this.cropperCtx.clearRect(defaultPoint + offsetX, defaultPoint + offsetY, width, width);
				// 绘制裁剪框边框
				this.cropperCtx.setStrokeStyle('#00aeff');
				this.cropperCtx.setLineWidth(1);
				this.cropperCtx.moveTo(defaultPoint + offsetX, defaultPoint + offsetY);
				this.cropperCtx.lineTo(defaultPoint + width + offsetX, defaultPoint + offsetY);
				this.cropperCtx.lineTo(defaultPoint + width + offsetX, defaultPoint + width + offsetY);
				this.cropperCtx.lineTo(defaultPoint + offsetX, defaultPoint + width + offsetY);
				this.cropperCtx.lineTo(defaultPoint + offsetX, defaultPoint + offsetY);
				this.cropperCtx.stroke();
				// 执行绘制
				this.cropperCtx.draw();

				// 保存当前裁剪框起始坐标
				this.cropperPosition = {
					x: defaultPoint + offsetX,
					y: defaultPoint + offsetY
				};

				// 记录偏移
				this.offset = {
					x: offsetX,
					y: offsetY
				};
			},
			onMoveStart(e) {
				const canvas = this.$refs.cropperCanvas.$el;
				let isFirst = true;
				let startX = 0;
				let startY = 0;

				// 监听鼠标移动
				canvas.onmousemove = e => {
					const x = e.clientX;
					const y = e.clientY;
					if (isFirst) {
						// 获取开始的坐标
						startX = x;
						startY = y;
						isFirst = false;
					} else {
						// 重新绘制裁剪框的位置
						let offsetX = (x - startX) / 15;
						let offsetY = (y - startY) / 15;
						this.drawCropper(offsetX, offsetY);
					}
				}

				// 松开鼠标停止移动
				canvas.onmouseup = () => {
					canvas.onmousemove = null;
				}
			},
			chooseImage() {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						this.$emit('update:imagePath', res.tempFilePaths[0]);
					}
				});
			},
			closeDialog() {
				this.$emit('update:hidden', true);
			},
			submit() {
				// 截取指定图片的指定区域
				uni.canvasToTempFilePath({
					canvasId: 'imageCanvas',
					x: this.cropperPosition.x,
					y: this.cropperPosition.y,
					width: width,
					height: width,
					success: (res) => {
						this.$emit('change', res.tempFilePath);
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.cropper-wrap {
		width: 100%;
		margin: 0 20px;
		border-top: 1px solid $border-color;
		@extend %flex-align-center;
		justify-content: center;

		.canvas-image {
			position: relative;
			width: 300px;
			height: 300px;
			background-color: #000000;
		}

		.canvas-cropper {
			z-index: 2;
			position: absolute;
			width: 300px;
			height: 300px;
			cursor: move;
		}
	}
</style>
