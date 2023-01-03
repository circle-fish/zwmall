<template>
	<navigator class="logo-container" url="/pages/index" title="商城首页">
		<i class="icon i-home"></i>
		<img class="logo" src="/static/image/common/logo1.png" />
	</navigator>
</template>

<style lang="scss">
	.logo-container {
		@extend %flex-align-center;
		width: 55px;
		overflow: hidden;
		cursor: pointer;

		&:hover {
			.i-home {
				margin-left: 0;
			}
		}

		.logo {
			flex-shrink: 0;
			width: 55px;
			height: 55px;
		}

		.i-home {
			flex-shrink: 0;
			font-size: 25px;
			background-color: $color-primary;
			width: 55px;
			height: 55px;
			color: #fff;
			@extend %flex-align-center;
			justify-content: center;
			margin-left: -55px;
			transition: all .2s;
		}
	}
</style>
