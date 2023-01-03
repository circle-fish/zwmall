<template>
	<view class="wrapper" v-if="listLength > 0">
		<view class="wrap" :class="{'separator': separator}">

			<view class="label elip">{{name}}：</view>
			<view class="list" :style="[{'height': listHeight + 'px'}]">
				<block v-for="(item, index) in list" :key="index">
					<view class="item">
						<text :class="['name elip', {'active': activeIndex == index}]" @click="$emit('change', index)">
							{{item.name}}
						</text>
					</view>
				</block>
			</view>
			<view class="btn-more" v-if="listLength > 7" @click="isOpen = !isOpen;">
				<text>更多</text>
				<i class="icon i-right" :class="{'active': isOpen}"></i>
			</view>

		</view>
	</view>
</template>

<script>
	export default {
		props: {
			name: {
				type: String
			},
			list: {
				type: Array,
				default () {
					return [];
				}
			},
			activeIndex: {
				type: Number,
				default: 0
			},
			separator: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				isOpen: false
			}
		},
		computed: {
			listLength() {
				return this.list.length;
			},
			listHeight() {
				let height = 60;
				if (this.isOpen && this.listLength > 7) {
					height *= Math.ceil(this.listLength / 7);
				}
				return height;
			}
		},
		watch: {
			activeIndex(e) {
				if (e > 6 && !this.isOpen) this.isOpen = true;
			}
		}
	}
</script>

<style lang="scss">
	.wrapper {
		background-color: $bg-color;

		.wrap {
			width: $page-width;
			margin: 0 auto;
			display: flex;
			min-height: 60px;
			position: relative;

			&.separator {
				border-top: 1px solid $border-color;
			}

			.label {
				color: #b0b0b0;
				min-width: 124px;
				line-height: 60px;
			}

			.list {
				display: flex;
				flex-wrap: wrap;
				overflow: hidden;
				transition: height .3s ease-in;

				.item {
					width: 149px;
					height: 60px;
					line-height: 60px;

					.name {
						max-width: 145px;
						display: inline-block;
						color: #424242;
						cursor: pointer;

						&.active,
						&:hover {
							color: $color-primary;
						}
					}
				}
			}

			.btn-more {
				line-height: 60px;
				cursor: pointer;
				color: #757575;
				flex-shrink: 0;

				.icon {
					display: inline-block;
					font-size: 12px;
					font-weight: bold;
					color: #cccccc;
					margin-left: 4px;
					transform: rotate(90deg);
					transition: transform .3s;

					&.active {
						transform: rotate(-90deg);
					}
				}
			}
		}
	}
</style>
