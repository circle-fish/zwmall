import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
	strict: true,
	state: {
		loginUser: {},
		cart: []
	},
	getters: {
		hasLogin(state) {
			return state.loginUser.id ? true : false;
		},
		token(state) {
			return state.loginUser.id || '';
		}
	},
	mutations: {
		/**
		 * 登录
		 * @param {Object} state
		 * @param {Object} loginUser
		 */
		login(state, loginUser) {
			if (loginUser.id) {
				loginUser.id = loginUser.id;
			}
			// 保存登录信息
			state.loginUser = loginUser;
			console.log(state.loginUser.password);
			// 保存登录信息到当前会话
			sessionStorage.setItem('loginUser', JSON.stringify(loginUser));
		},
		/**
		 * 退出登录
		 * @param {Object} state
		 */
		logout(state) {
			// 清空登录信息及购物车
			state.loginUser = {};
			state.cart = [];
			// 清除全部缓存
			sessionStorage.clear();
			uni.clearStorage();
		},
		/**
		 * 将商品加入到购物车
		 * @param {Object} state
		 * @param {Object} goods
		 */
		putGoods(state, goods) {
			// 判断该商品是否已存在
			const index = state.cart.findIndex(e => e.id == goods.id);
			if (index != -1) {
				const oldGoods = state.cart[index];
				if (oldGoods.count + 1 > oldGoods.maxCount) return;
				// 更新数量
				oldGoods.count++;
				state.cart.splice(index, 1, oldGoods);
			} else {
				// 添加到购物车
				state.cart.unshift(goods);
			}
			// 持久化购物车
			uni.setStorage({
				key: 'cart',
				data: state.cart
			});
		},
		/**
		 * 更新购物车商品
		 * @param {Object} state
		 * @param {Object} goods
		 */
		updateGoods(state, payload) {
			const {
				id,
				count,
				checked
			} = payload;
			// 找到该商品索引
			const index = state.cart.findIndex(e => e.id == id);
			if (index != -1) {
				const goods = state.cart[index];
				// 更新商品数量
				if (count) goods.count = count;
				// 更新选中状态
				if (typeof checked == 'boolean') goods.checked = checked;
				// 替换购物车中的商品
				state.cart.splice(index, 1, goods);
				// 持久化购物车
				uni.setStorage({
					key: 'cart',
					data: state.cart
				});
			}
		},
		/**
		 * 从购物车清除商品
		 * @param {Object} state
		 * @param {Object} id
		 */
		removeGoods(state, id) {
			// 找到该商品索引
			const index = state.cart.findIndex(e => e.id == id);
			if (index != -1) {
				// 删除该商品
				state.cart.splice(index, 1);
				// 持久化购物车
				uni.setStorage({
					key: 'cart',
					data: state.cart
				});
			}
		},
		initCart(state, cart) {
			if (cart && cart.length > 0) {
				state.cart = cart;
				// 持久化购物车
				uni.setStorage({
					key: 'cart',
					data: cart
				});
			}
		}
	},
	actions: {
		putGoods(context, goods) {
			return new Promise((resolve, reject) => {
				const oldGoods = context.state.cart.find(e => e.id == goods.id);
				if (oldGoods && oldGoods.count + 1 > goods.maxCount) {
					reject();
				} else {
					context.commit('putGoods', goods);
					resolve();
				}
			});
		}
	}
});
