<template>
	<zw-dialog :hidden="hidden" :width="500" :height="100" :showHeaderBg="false" :hideFooter="false" :showFooterBg="false"
	 :showCancel="showCancel" :confirmText="confirmText" :cancelText="cancelText"
	 :buttonReverse="true" :showCloseBtn="false" @close="hidden = true;" @confirm="handleAction('confirm')" @cancel="handleAction('cancel')">
		<view class="message">{{message}}</view>
	</zw-dialog>
</template>

<script>
	export default {
		data() {
			return {
				hidden: true,
				message: '标题',
				showCancel: true,
				confirmText: '确定',
				cancelText: '取消',
				confirm: null,
				cancel: null
			}
		},
		methods: {
			handleAction(action) {
				this.hidden = true;

				if (action == 'confirm') {
					this.confirm();
				} else {
					this.cancel();
				}
			}
		}
	}
</script>

<style>
	.message {
		font-size: 18px;
		font-weight: 400;
		line-height: 50px;
		color: #424242;
		width: 100%;
		text-align: center;
	}
</style>
