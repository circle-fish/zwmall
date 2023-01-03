<template>
	<view class="city-picker" v-if="!hidden">
		<view class="card-header">
			<text class="address-item" v-if="province.name" @click="province=city=district={};">{{province.name}}</text>
			<text class="address-item" v-if="city.name" @click="city=district={};">{{city.name}}</text>
			<text class="address-item" v-if="district.name">{{district.name}}</text>
			<text class="title">{{pickerTitle}}</text>
			<i class="icon i-close" @click="$emit('update:hidden', true)"></i>
		</view>
		<view class="card-body">
			<view class="item" v-for="(item, index) in cities" :key="index" @click="change(index, item.name)">{{item.name}}</view>
		</view>
	</view>
</template>

<script>
	import cities from '@/common/config/user/cities.config.js';

	export default {
		props: {
			hidden: {
				type: Boolean,
				default: true
			},
			deep: {
				type: Number,
				default: 3,
				validator: (value) => {
					return value >= 1 && value <= 3;
				}
			}
		},
		data() {
			return {
				province: {
					name: '',
					index: -1
				},
				city: {
					name: '',
					index: -1
				},
				district: {
					name: '',
					index: -1
				}
			}
		},
		watch: {
			hidden(e) {
				if (e) {
					this.province = {};
					this.city = {};
					this.district = {};
				}
			}
		},
		computed: {
			pickerTitle() {
				let title = '';
				if (!this.province.name) title = '选择省份/自治区';
				else if (!this.city.name) title = '选择城市/地区';
				else if (!this.district.name) title = '选择区县';
				return title;
			},
			cities() {
				let cityArray = [];
				if (!this.province.name) cityArray = cities;
				else if (!this.city.name) cityArray = cities[this.province.index].city;
				else if (!this.district.name) cityArray = cities[this.province.index].city[this.city.index].area;
				return cityArray;
			}
		},
		methods: {
			change(index, name) {
				// 赋值
				const address = {
					name,
					index
				};
				let returnAddress = false;
				if (!this.province.name) {
					if (this.deep == 1) returnAddress = true;
					this.province = address;
				} else if (!this.city.name && this.deep >= 2) {
					if (this.deep == 2) returnAddress = true;
					this.city = address;
				} else if (!this.district.name && this.deep == 3) {
					this.district = address;
					returnAddress = true;
				}
				// 返回值
				if (returnAddress) {
					const {
						province,
						city,
						district
					} = this;
					this.$emit('change', {
						province: province.name,
						city: city.name,
						district: district.name
					});
				}
			}
		}
	}
</script>

<style lang="scss">
	.city-picker {
		position: absolute;
		z-index: 3;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
		background-color: $bg-color;
		padding: 15px 20px;
		border-radius: 4px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);

		.card-header {
			position: relative;
			padding: 6px 0;
			line-height: 30px;
			border-bottom: 1px solid #e0e0e0;

			.address-item {
				color: $color-primary;
				margin-right: 5px;
				cursor: pointer;
			}

			.title {
				color: #757575;
			}

			.i-close {
				position: absolute;
				right: 0;
				top: 0;
				cursor: pointer;
				font-size: 10px;
				font-weight: bold;
				color: #B0B0B0;
			}
		}

		.card-body {
			max-height: 100%;
			overflow-y: auto;
			display: flex;
			flex-wrap: wrap;
			padding-top: 20px;
			background-color: $bg-color;

			.item {
				margin-right: 14px;
				line-height: 30px;
				margin-bottom: 6px;
				color: $text-color;
				cursor: pointer;

				&:hover {
					color: $color-primary;
				}
			}
		}
	}
</style>
