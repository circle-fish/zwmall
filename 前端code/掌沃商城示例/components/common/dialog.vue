<template>
	<view class="dialog-wrapper" v-if="!hidden">
		<view :class="['dialog-wrap', {'radius': borderRadius}, {'willclose': willclose}, {'goup': buttonReverse}]" :style="{'width': width + 'px'}">

			<view :class="['box-header', {'hidden': !showHeaderBg}]">
				<view class="title">{{title}}</view>
				<view class="btn-close" v-if="showCloseBtn" @click="closeDialog">
					<i class="icon i-close"></i>
				</view>
			</view>
			<view class="box-body" :style="{'height': height + 'px'}">
				<!-- 注入内容 -->
				<slot></slot>
			</view>
			<view :class="['box-footer', {'hidden': !showFooterBg}, {'reverse': buttonReverse}]" v-if="!hideFooter">
				<button class="btn btn-primary" @click="$emit('confirm')" v-if="showConfrim">{{confirmText}}</button>
				<button class="btn btn-gray" @click="$emit('cancel')" v-if="showCancel">{{cancelText}}</button>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		props: {
			hidden: true,
			title: String,
			width: {
				type: Number,
				default: 880
			},
			height: {
				type: Number,
				default: 500
			},
			showHeaderBg: {
				type: Boolean,
				default: true
			},
			hideFooter: {
				type: Boolean,
				default: true
			},
			showFooterBg: {
				type: Boolean,
				default: true
			},
			showConfrim: {
				type: Boolean,
				default: true
			},
			showCancel: {
				type: Boolean,
				default: true
			},
			confirmText: {
				type: String,
				default: '确定'
			},
			cancelText: {
				type: String,
				default: '取消'
			},
			buttonReverse: {
				type: Boolean,
				default: false
			},
			borderRadius: false,
			showCloseBtn: {
				type: Boolean,
				default: true
			}
		},
		data() {
			return {
				willclose: false
			}
		},
		methods: {
			closeDialog() {
				this.willclose = true;
				setTimeout(() => {
					this.willclose = false;
					this.$emit('close');
				}, 100);
			}
		}
	}
</script>

<style lang="scss">
	.dialog-wrapper {
		z-index: 10;
		background-color: $bg-color-mask;
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		@extend %flex-align-center;
		justify-content: center;

		.dialog-wrap {
			background-color: #fff;
			border-radius: 2px;
			overflow: hidden;
			animation: dialog-open .3s forwards;
			
			&.radius {
				border-radius: 5px;
			}

			@keyframes dialog-open {
				from {
					transform: translateY(-5%);
				}

				to {
					transform: translateY(0);
				}
			}

			&.willclose {
				animation: dialog-close .2s forwards;
			}

			@keyframes dialog-close {
				to {
					opacity: .1;
					transform: translateY(-20%);
				}
			}

			&.goup {
				margin-top: -20%;
			}

			.box-header {
				height: 32px;
				padding: 14px 20px;
				line-height: 32px;
				background-color: #f5f5f5;
				@extend %flex-align-center;
				justify-content: space-between;

				&.hidden {
					background-color: $bg-color;
				}

				.title {
					font-size: 18px;
					font-weight: 400;
					color: #424242;
				}

				.btn-close {
					width: 25px;
					height: 25px;
					line-height: 25px;
					text-align: center;
					color: #757575;
					cursor: pointer;
					transition: all .2s;
					border-radius: 15px;

					&:hover {
						background-color: $color-error;

						.icon {
							color: #FFFFFF;
						}
					}

					.icon {
						color: #757575;
						font-size: 14px;
						font-weight: bold;
					}
				}
			}

			.box-body {
				width: 100%;
				display: flex;
			}

			.box-footer {
				@extend %flex-align-center;
				justify-content: center;
				height: 80px;
				border-top: 1px solid $border-color;
				background-color: $bg-color-grey;

				&.hidden {
					border-top: none;
					background-color: $bg-color;
				}

				&.reverse {
					margin-bottom: 20px;
					flex-direction: row-reverse;
				}

				.btn {
					width: 160px;
					height: 40px;
					margin: 0 7px;
					line-height: 40px;
					text-align: center;
					cursor: pointer;
					color: #FFFFFF;

					&.btn-primary {
						background-color: $color-primary;

						&:hover {
							opacity: .8;
						}
					}

					&.btn-gray {
						background-color: $bg-color-gray;

						&:hover {
							background-color: #757575;
						}
					}
				}
			}
		}
	}
</style>
