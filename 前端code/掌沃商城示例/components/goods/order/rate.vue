<template>
	<view class="rate-wrapper">
		<view v-if="name" class="name">{{name}}</view>
		<view class="rate-wrap" v-for="index in 5" :key="index" @mouseenter="switchScore(index)">
			<!-- 垫底图标 -->
			<i class="icon i-star" :class="{'disabled': disabled}"></i>
			<!-- 激活图标 -->
			<view class="active-wrap" :style="{'width': iconWidth(index) + '%'}">
				<i class='icon i-star active' :class="{'disabled': disabled}"></i>
			</view>
		</view>
		<view v-if="showSuffix" class="score">{{score}}分</view>
	</view>
</template>

<script>
	export default {
		props: {
			score: {
				type: Number,
				default: 5
			},
			disabled: {
				type: Boolean,
				default: true
			},
			name: String,
			showSuffix: {
				type: Boolean,
				default: true
			}
		},
		methods: {
			iconWidth(index) {
				if (index < this.score) {
					return 100;
				} else if (index == Math.ceil(this.score)) {
					if (this.score % 1 != 0) {
						return this.score.toString().split('.')[1] * 10;
					} else {
						return 100;
					}
				}
				return 0;
			},
			switchScore(index) {
				if (!this.disabled) {
					this.$emit('change', index);
				}
			}
		}
	}
</script>

<style lang="scss">
	.name {
		margin-right: 15px;
		font-weight: bold;
		color: $text-color;
	}
	
	.rate-wrapper {
		@extend %flex-align-center;

		.rate-wrap {
			position: relative;
			margin-right: 5px;

			.icon {
				font-size: 20px;
				color: $text-color-grey;
				cursor: pointer;

				&.disabled {
					cursor: default;
				}

				&.active {
					color: $color-primary;
				}
			}

			.active-wrap {
				position: absolute;
				top: 0;
				left: 0;
				overflow: hidden;
			}
		}

		.score {
			margin-left: 5px;
			color: $text-color;
		}
	}
</style>
