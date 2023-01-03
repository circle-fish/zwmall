<template>
	<view class="progress-wrap">
		<view class="progress">
			<view v-for="(item, index) in progresses" :key="index" :class="['item', {'active': item.value}, {'last': index == lastIndex}]">
				<view class="name">{{item.name}}</view>
				<view class="time" v-if="item.value">{{item.value | filterTime}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			time: {
				type: Object,
				default () {
					return {};
				}
			}
		},
		data() {
			return {
				lastIndex: 0
			}
		},
		computed: {
			progresses() {
				const steps = [{
						name: '下单',
						key: 'createTime'
					}, {
						name: '付款',
						key: 'paidTime'
					},
					{
						name: '配货',
						key: 'preparedTime'
					},
					{
						name: '出库',
						key: 'shippedTime'
					}, {
						name: '交易成功',
						key: 'receiptedTime'
					}
				];
				const array = [];
				steps.forEach((e, i) => {
					array.push({
						name: e.name,
						value: this.time[e.key]
					});

					if (this.time[e.key]) this.lastIndex = i;
				});
				return array;
			}
		},
		filters: {
			filterTime(datetime) {
				const units = ['年', '月', '日'];
				datetime = datetime.split(' ');
				return datetime[0].split('-').map((e, i) => e + units[i]).join('') + ' ' + datetime[1];
			}
		}
	}
</script>

<style lang="scss">
	.progress-wrap {
		padding-bottom: 40px;

		.progress {
			width: 880px;
			height: 20px;
			line-height: 20px;
			border-radius: 20px;
			font-size: 12px;
			background-color: #eee;
			@extend %flex-align-center;

			.item {
				width: 176px;
				height: 20px;
				text-align: center;
				position: relative;

				&:first-child {
					border-top-left-radius: 20px;
					border-bottom-left-radius: 20px;
				}

				&.active {
					background-color: $color-success;

					.name {
						color: #fff;
					}
				}

				&.last {
					border-top-right-radius: 20px;
					border-bottom-right-radius: 20px;
				}

				.name {
					color: $text-color;
				}

				.time {
					position: absolute;
					top: 100%;
					left: 0;
					right: 0;
					margin-top: 15px;
					color: #757575;
				}
			}
		}
	}
</style>
