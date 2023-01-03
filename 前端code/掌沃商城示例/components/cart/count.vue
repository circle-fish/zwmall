<template>
	<view class="count-wrapper">
		<view class="btn btn-subtract" @click="countSubtract">-</view>
		<input class="input-count" type="number" maxlength="5" v-model="value" @blur="change" />
		<view class="btn btn-plus" @click="countPlus">+</view>
	</view>
</template>

<script>
	export default {
		props: {
			count: {
				type: Number,
				default: 1
			},
			maxCount: {
				type: Number,
				default: 5
			}
		},
		watch: {
			count(v) {
				this.value = v;
			}
		},
		data() {
			return {
				value: this.count
			}
		},
		methods: {
			change(e) {
				if (this.value > this.maxCount) {
					this.value = this.maxCount;
				} else {
					this.returnValue(e.detail.value);
				}
			},
			countSubtract() {
				if (this.count <= 1) return;
				this.returnValue(this.count - 1);
			},
			countPlus() {
				if (this.count >= this.maxCount) {
					this.$alert('商品加入购物车数量超过限购数', {
						showCancel: false
					});
					return;
				}
				this.returnValue(this.count + 1);
			},
			returnValue(count) {
				count = Math.floor(count);
				if (count == 0) count = 1;
				this.$emit('change', count);
			}
		}
	}
</script>

<style lang="scss">
	.count-wrapper {
		@extend %flex-align-center;
		border: 1px solid $border-color;
		font-size: 16px;
		text-align: center;
		width: 150px;
		height: 38px;

		.btn {
			width: 39px;
			height: 39px;
			line-height: 39px;
			color: #757575;
			font-size: 20px;
			transition: all .3s;
			cursor: pointer;

			&.btn-subtract {
				margin-left: -1px;
			}

			&.btn-plus {
				margin-right: -1px;
			}

			&:hover {
				background-color: $border-color;
			}
		}

		.input-count {
			width: 74px;
			height: 38px;
			line-height: 38px;
			color: #424242;
			font-size: 16px;
			text-align: center;
		}
	}
</style>
