<template>
	<view class="date-picker" v-if="!hidden">
		<!-- 切换年月按钮 -->
		<view class="calendar-header">
			<view class="left">
				<view class="btn" title="上一年" @click="switchDate('year', 'substract')">
					<i class="icon i-left-double"></i>
				</view>
				<view class="btn" title="上一月" @click="switchDate('month', 'substract')">
					<i class="icon i-left"></i>
				</view>
			</view>
			<view class="center">
				<view class="date-label">{{year}} 年</view>
				<view class="date-label">{{month}} 月</view>
			</view>
			<view class="right">
				<view class="btn" title="下一月" @click="switchDate('month', 'plus')">
					<i class="icon i-right"></i>
				</view>
				<view class="btn" title="下一年" @click="switchDate('year', 'plus')">
					<i class="icon i-right-double"></i>
				</view>
			</view>

			<!-- 关闭按钮 -->
			<view class="icon i-close" @click="$emit('update:hidden', true);"></view>
		</view>
		<!-- 周日到周六 -->
		<view class="calendar-week">
			<view class="item" v-for="(item, index) in weeks" :key="index">{{item}}</view>
		</view>
		<!-- 日期数据 -->
		<view class="calendar-body">
			<block v-for="(item, index) in days" :key="index">
				<view :class="['day', {'active': item.active}, {'disabled': item.disabled}]" @click="change(item.day, item.disabled)">
					{{item.day}}
				</view>
			</block>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			hidden: {
				type: Boolean,
				default: true
			},
			value: ''
		},
		data() {
			return {
				weeks: ['日', '一', '二', '三', '四', '五', '六'],
				days: [],
				year: '',
				month: ''
			}
		},
		watch: {
			hidden(e) {
				if (!e) {
					this.calcToday();
				}
			}
		},
		created() {
			this.calcToday();
		},
		methods: {
			calcToday() {
				if (this.value) {
					const date = this.value.split('-');
					this.year = date[0];
					this.month = date[1];
					this.today = this.year + '/' + this.month + '/' + date[2];
				} else {
					// 获取今天的年月日
					const now = new Date();
					this.year = now.getFullYear();
					this.month = now.getMonth() + 1;
					this.today = this.year + '/' + this.month + '/' + now.getDate();
				}

				this.initCalendar();
			},
			initCalendar() {
				this.days = [];

				// 获取本月的总天数
				const totalDays = new Date(this.year, this.month, 0).getDate();

				// 查询本月第一天是星期几
				const firstDayWeek = new Date(this.year + '/' + this.month + '/1').getDay();
				// 查询本月最后一天是星期几
				const lastDayWeek = new Date(this.year + '/' + this.month + '/' + totalDays).getDay();

				// 获取上月的总天数
				const currentDate = new Date(this.year + '/' + this.month + '/1');
				currentDate.setMonth(currentDate.getMonth() - 1);
				const totalDaysOfLastMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0).getDate();
				// 获取第一周还差的天数
				const firstDaysDiff = 7 - firstDayWeek;
				// 渲染上月的日期
				for (let i = 0; i < firstDaysDiff; i++) {
					this.days.unshift({
						day: totalDaysOfLastMonth - i,
						disabled: true
					});
				}

				// 渲染本月的日期
				for (let i = 1; i <= totalDays; i++) {
					const day = this.year + '/' + this.month + '/' + i;
					this.days.push({
						day: i,
						active: day == this.today
					});
				}

				// 渲染下月的日期
				for (let i = 1; i <= 14; i++) {
					this.days.push({
						day: i,
						disabled: true
					});
				}
			},
			switchDate(type, mode) {
				const date = new Date(this.year + '/' + this.month + '/1');
				if (type == 'year') {
					const year = mode == 'plus' ? date.getFullYear() + 1 : date.getFullYear() - 1;
					date.setYear(year);
				} else {
					const month = mode == 'plus' ? date.getMonth() + 1 : date.getMonth() - 1;
					date.setMonth(month);
				}

				this.year = date.getFullYear();
				this.month = date.getMonth() + 1;

				this.initCalendar();
			},
			change(day, disabled) {
				if (disabled) return;
				const date = [this.year, this.month, day].map(e => {
					e = Number(e);
					return e < 10 ? '0' + e : e;
				}).join('-');
				this.$emit('change', date);
			}
		}
	}
</script>

<style lang="scss">
	.date-picker {
		width: 400px;
		height: 430px;
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 3;
		margin: auto;
		padding: 20px;
		background-color: $bg-color;
		border-radius: 4px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);

		.calendar-header {
			position: relative;
			padding-top: 20px;
			@extend %flex-align-center;
			justify-content: space-between;

			.left,
			.center,
			.right {
				@extend %flex-align-center;
				color: #303133;
			}

			.i-left,
			.i-right {
				font-size: 12px;
				font-weight: bold;
				margin: 0 15px;
				display: block;
				margin-top: 3px;
			}

			.date-label {
				font-size: 16px;
				font-weight: 500;
				padding: 0 5px;
				line-height: 22px;
				text-align: center;
				color: #606266;
			}

			.btn {
				cursor: pointer;

				&:hover {
					color: $color-primary;
				}
			}

			.i-close {
				position: absolute;
				right: 0px;
				top: -5px;
				cursor: pointer;
				font-size: 12px;
				font-weight: bold;
				color: #B0B0B0;
			}
		}

		.calendar-week {
			margin-top: 15px;
			display: flex;
			border-bottom: 1px solid #ebeef5;

			.item {
				width: 57px;
				height: 55px;
				text-align: center;
				color: #606266;
				@extend %flex-align-center;
				justify-content: center;
			}
		}

		.calendar-body {
			display: flex;
			flex-wrap: wrap;
			width: 400px;
			height: 340px;
			overflow: hidden;

			.day {
				width: 57px;
				height: 55px;
				color: #606266;
				@extend %flex-align-center;
				justify-content: center;
				cursor: pointer;

				&:hover,
				&.active {
					color: $color-primary;
				}

				&.disabled {
					color: #c0c4cc;
					cursor: default;
				}
			}
		}
	}
</style>
