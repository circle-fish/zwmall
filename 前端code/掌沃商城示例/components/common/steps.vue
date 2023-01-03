<template>
	<view class="steps-wrapper">
		<block v-for="(item, index) in steps" :key="index">
			<view :class="['step', {'active': index <= step}]">
				<view class="name">{{item}}</view>
				<view :class="['dot', {'noline': index == step}]">·</view>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		props: {
			steps: {
				type: Array,
				default () {
					return ['第一步', '第二步', '完成'];
				}
			},
			step: {
				type: Number,
				default: 0
			}
		}
	}
</script>

<style lang="scss">
	.steps-wrapper {
		@extend %flex-align-center;
		margin-bottom: 20px;

		.step {
			flex: 1;
			text-align: center;
			line-height: 20px;

			&.active {

				.name,
				.dot {
					color: $color-primary;
				}

				.dot::before,
				.dot::after {
					background-color: $color-primary;
				}
			}

			.name {
				color: #9d9d9d;
			}

			%step-line {
				position: absolute;
				background-color: $border-color;
				content: '';
				height: 1px;
				bottom: 6px;
			}

			.dot {
				font-size: 45px;
				color: #9d9d9d;
				text-align: center;
				position: relative;

				&::before {
					@extend %step-line;
					left: 0;
					width: 40%;
				}

				&::after {
					@extend %step-line;
					right: 0;
					width: 40%;
				}
				
				&.noline::after {
					background-color: $border-color;
				}
			}

			&:first-child {

				.dot::before {
					height: 0;
				}
			}

			&:last-child {

				.dot::after {
					height: 0;
				}
			}
		}
	}
</style>
