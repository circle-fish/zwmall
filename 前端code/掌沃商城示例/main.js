import Vue from 'vue';
import App from './App';
import store from './store';

import util from './common/tools/util.js';
import cart from './common/tools/cart.js';
import regular from './common/tools/regular.js';

// 初始化全局JS弹窗
import './components/common/alert';

// 全局组件
Vue.component('zw-header', () => import('@/components/common/header.vue'));
Vue.component('zw-footer', () => import('@/components/common/footer.vue'));
Vue.component('zw-dialog', () => import('@/components/common/dialog.vue'));
Vue.component('bread-crumb', () => import('@/components/common/bread-crumb.vue'));

// 全局变量
Vue.prototype.$util = util;
Vue.prototype.$cart = cart;
Vue.prototype.$regular = regular;

Vue.config.productionTip = false;

App.mpType = 'app';

new Vue({
	store,
	...App
}).$mount();
