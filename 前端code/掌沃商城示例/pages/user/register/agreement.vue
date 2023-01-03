<template>
	<web-view :style="['width: 100%;', {'height': height + 'px'}]" src="https://static.account.xiaomi.com/html/agreement/user/zh_CN.html"></web-view>
</template>

<script>
	export default {
		data() {
			return {
				height: 0
			}
		},
		onReady() {
			this.height = uni.getSystemInfoSync().windowHeight;
		}
	}
</script>