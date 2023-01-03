<template>
	<zw-dialog :hidden="hidden" :title="title" @close="closeDialog">
		<video class="video" :src="videoUrl" autoplay :controls="showControls" @mouseenter="showControls = true" @mouseleave="showControls = false"></video>
	</zw-dialog>
</template>

<script>
	export default {
		props: {
			hidden: true,
			title: String,
			videoUrl: String
		},
		data() {
			return {
				showControls: false
			}
		},
		methods: {
			closeDialog() {
				this.$emit('update:hidden', true);
			}
		}
	}
</script>

<style>
	.video {
		width: 100%;
		height: 100%;
	}
</style>
