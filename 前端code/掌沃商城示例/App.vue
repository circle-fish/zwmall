<script>
	export default {
		onLaunch() {
			// 初始化登录信息
			this.initLoginUser();

			// 初始化购物车
			this.initCart();
		},
		onPageNotFound() {
			// 页面不存在时自动重定向到首页
			uni.redirectTo({
				url: 'pages/index'
			});
		},
		methods: {
			initLoginUser() {
				let loginUser = sessionStorage.getItem('loginUser');
				if (loginUser) {
					loginUser = JSON.parse(loginUser);
					if (loginUser.token) this.$store.commit('login', loginUser);
				}
			},
			initCart() {
				this.$cart.initCart();
			}
		}
	}
</script>

<style lang="scss">
	@import './common/styles/app.scss';

	* {
		outline: none;
	}

	body {
		height: auto;
		min-width: $page-width;
		overflow-x: auto;
		font-size: 14px;
	}

	a {
		text-decoration: none;
	}

	button {
		font-size: 14px;
		transition: all .4s;
		border-radius: 0;

		&:active {
			opacity: .8;
		}

		&:after {
			border: none;
		}
	}

	/* 图片显示模式 */
	.thumb {
		object-fit: contain;
	}

	/* 省略长文本 */
	.elip {
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}
</style>
