import store from '@/store/index.js';

/**
 * 动态设置http地址
 */
const getBaseUrl = () => {
	const baseUrl = {
		//dev: 'http://localhost:8083',
		dev: 'http://localhost:8083/MYSSM_war_exploded',
		online: 'https://api.zwmall.chengdongqing.top'
	};
	return baseUrl.dev;
}

export default {
	/**
	 * 接口基础路径
	 */
	baseUrl: getBaseUrl(),
	wsUrl: getBaseUrl().replace("http", "ws"),
	/**
	 * 数据请求
	 */
	request(url, data = {}, callback = () => {}, isGet = true) {
		uni.request({
			url: this.baseUrl + url,
			method: "GET",
			// header: {
			// 	'Content-Type': 'application/x-www-form-urlencoded',
			// 	token: store.getters.token
			// },
			data,
			// data:{
			// 	username:"zhangsan",
			// 	password:"123456"
			// },
			complete: (res) => {
				console.log(res);
				switch (res.data.code) {
					case "200":
						callback(res);
						break;
					case 401:
						store.commit('logout');
						uni.redirectTo({
							url: '/pages/user/login/index'
						});
						callback();
						break;
					default:
						callback({
							state: 'fail',
							msg: '系统繁忙，请稍后再试...'
						});
						break;
				}
			}
		});
	},
	/**
	 * 文件上传
	 */
	upload(url, path, callback = () => {}, fileName = 'image') {
		uni.uploadFile({
			url: this.baseUrl + url,
			header: {
				token: store.getters.token
			},
			filePath: path,
			name: fileName,
			complete: (res) => {
				if (res.statusCode == 200) {
					callback(JSON.parse(res.data));
				} else {
					callback({
						state: 'fail',
						msg: '上传失败'
					});
				}
			}
		});
	},
	/**
	 * 获取随机数
	 */
	getRandomStr(range = 32) {
		let str = '',
			arr = [];
		// 生成字符数组
		for (let i = 48; i <= 57; i++) arr.push(String.fromCharCode(i));
		for (let i = 97; i <= 122; i++) arr.push(String.fromCharCode(i));
		for (let i = 65; i <= 90; i++) arr.push(String.fromCharCode(i));
		// 生成随机数
		for (let i = 0; i < range; i++) {
			const index = Math.round(Math.random() * (arr.length - 1));
			str += arr[index];
		}
		return str;
	},
	/**
	 * 计算时间差
	 */
	timeDiff(startTime, endTime) {
		// 计算相差的时间
		const timeDiff = endTime.getTime() - startTime.getTime();
		if (timeDiff <= 0) return false;

		const totalSeconds = Math.floor(timeDiff / 1000);
		const hours = Math.floor(totalSeconds / 60 / 60);
		const minutes = Math.floor((totalSeconds - hours * 60 * 60) / 60);
		const seconds = Math.floor(totalSeconds - hours * 60 * 60 - minutes * 60);
		const timeDiffArray = [hours, minutes, seconds].map(e => {
			return e < 10 ? '0' + e : e;
		});
		return timeDiffArray;
	},
	/**
	 * 提示错误信息
	 */
	showErrorMsg(msg) {
		uni.showToast({
			title: msg,
			icon: 'none'
		});
		return false;
	}
}
