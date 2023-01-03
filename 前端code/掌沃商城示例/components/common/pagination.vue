<template>
	<view class="pagination-wrapper" v-if="totalPage > 1" @click="switchPage">

		<!-- 左箭头 -->
		<view class="arrow" v-if="pageNumber > 1" :data-number="pageNumber - 1">
			<i class="icon i-left"></i>
		</view>

		<!-- 页码数据 -->
		<view class="page-numbers">
			<!-- 第一页 -->
			<view v-if="totalPage > 0" :class="['number', {'active': pageNumber == 1}]" data-number="1">1</view>

			<!-- 左边省略的页 -->
			<view v-if="hasPrevMore" class="number more">...</view>

			<!-- 中间页 -->
			<block v-for="(item, index) in pagers" :key="index">
				<view :class="['number', {'active': pageNumber == item}]" :data-number="item">{{item}}</view>
			</block>

			<!-- 右边省略的页 -->
			<view v-if="hasNextMore" class="number more">...</view>

			<!-- 最后一页 -->
			<view v-if="totalPage > 1" :class="['number', {'active': pageNumber == totalPage}]" :data-number="totalPage">{{totalPage}}</view>
		</view>

		<!-- 右箭头 -->
		<view class="arrow" v-if="pageNumber < totalPage" :data-number="pageNumber + 1">
			<i class="icon i-right"></i>
		</view>

	</view>
</template>

<script>
	export default {
		props: {
			pageNumber: {
				type: Number,
				default: 1
			},
			pageSize: {
				type: Number,
				default: 20
			},
			totalSize: {
				type: Number,
				default: 10
			}
		},
		computed: {
			totalPage() {
				return Math.ceil(this.totalSize / this.pageSize);
			},
			pagers() {
				const pagers = [];
				if (this.totalPage <= 7) {
					for (let i = 2; i < this.totalPage; i++) pagers.push(i);
					this.hasPrevMore = this.hasNextMore = false;
				} else {
					if (this.pageNumber <= 3) {
						for (let i = 2; i <= 4; i++) pagers.push(i);
						this.hasNextMore = true;
						this.hasPrevMore = false;
					} else if (this.pageNumber > this.totalPage - 3) {
						for (let i = this.totalPage - 3; i < this.totalPage; i++) pagers.push(i);
						this.hasPrevMore = true;
						this.hasNextMore = false;
					} else {
						for (let i = this.pageNumber - 1; i <= this.pageNumber + 1; i++) pagers.push(i);
						this.hasPrevMore = this.hasNextMore = true;
					}
				}
				return pagers;
			}
		},
		data() {
			return {
				hasPrevMore: false,
				hasNextMore: false
			}
		},
		methods: {
			switchPage(e) {
				let number = e.target.dataset.number;
				if (number) {
					number = Number(number);
					if (number != this.pageNumber) this.$emit('change', number);
				}
			}
		}
	}
</script>

<style lang="scss">
	.pagination-wrapper {
		margin: 15px auto;
		@extend %flex-align-center;
		justify-content: center;

		%item {
			width: 48px;
			height: 24px;
			padding: 3px 0;
			margin: 0 7px;
			line-height: 24px;
			color: #b0b0b0;
			cursor: pointer;
			text-align: center;
			transition: all .2s linear;

			&:hover {
				background-color: $bg-color-gray;
				color: #fff;
			}
		}

		.arrow {
			@extend %item;

			.icon {
				font-size: 20px;
				font-weight: bold;
			}
		}

		.page-numbers {
			@extend %flex-align-center;

			.number {
				@extend %item;

				&.active {
					background-color: #757575;
					color: #fff;
					cursor: default;
				}

				&.more {
					cursor: default;

					&:hover {
						background-color: transparent;
						color: #b0b0b0;
					}
				}
			}
		}
	}
</style>
