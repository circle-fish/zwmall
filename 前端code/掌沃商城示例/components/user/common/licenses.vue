<template>
	<zw-dialog :hidden="hidden" title="协议声明" :width="800" :height="420" :hideFooter="false" confirmText="同意" cancelText="不同意"
	 @close="closeDialog" @confirm="agreed" @cancel="closeDialog">
		<view>
			<view class="agreement-header">
				<block v-for="(item, index) in agreements" :key="index">
					<text class="agree-tab" @click="agreementIndex = index">《{{item.name}}》</text>
				</block>
				请您仔细阅读以上协议，其中有对您权利义务的特别约定等重要条款，同意后方可使用本软件
			</view>
			<view class="agreement-body">
				<iframe width="100%" height="300px" frameborder="0" :srcdoc="agreementIndex == 0 ? userAgreement : false" :src="agreementIndex > 0 ? agreements[agreementIndex].linkUrl : false"></iframe>
			</view>
		</view>
	</zw-dialog>
</template>

<script>
	import agreementConfig from '@/common/config/user/agreement.config.js';

	export default {
		props: {
			hidden: {
				type: Boolean,
				default: true
			}
		},
		data() {
			return {
				...agreementConfig,
				agreementIndex: 0
			}
		},
		methods: {
			closeDialog() {
				this.$emit('update:hidden', true);
				this.agreementIndex = 0;
			},
			agreed() {
				this.$emit('agreed');
			}
		}
	}
</script>

<style lang="scss">
	.agreement-header {
		padding: 20px;
		color: $text-color;

		.agree-tab {
			cursor: pointer;
			color: $color-primary;
		}
	}

	.agreement-body {
		padding: 20px;
	}
</style>
