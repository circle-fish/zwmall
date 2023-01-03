import Vue from 'vue';
import alert from './alert';

const constructor = Vue.extend(alert);
Vue.prototype.$alert = (message, options = {}, confirm = () => {}, cancel = () => {}) => {
	const instance = new constructor({
		data: {
			message,
			...options,
			confirm,
			cancel
		}
	}).$mount();
	document.body.appendChild(instance.$el);
	Vue.nextTick(() => {
		instance.hidden = false;
	});
	return instance;
}
