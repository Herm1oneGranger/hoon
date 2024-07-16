import config from "../config";
import store from '../store'

const request = (options) => {
	// 拼接完整的接口路径
	options.url = `${config.apiBaseUrl}${options.url}`

	//判断是都携带参数
	if (!options.data) {
		options.data = {};
	}

	// 请求头
	const headers = {
		...(options.header || {}),
	}
	options.header = headers

	// 超时时间
	options.timeout = 600 * 1000

	const promise = new Promise(function(resolve, reject) {
		uni.request(options).then(responses => {
			// 异常
			if (responses[0]) {
				reject({
					message: "网络超时"
				});
			} else {
				let {
					data
				} = responses[1]; // 如果返回的结果是data.data的
				if (data.code !== 200) {
					reject(data)
					return
				}

				resolve(data.data);
			}
		}).catch(error => {
			reject(error);
		})
	})

	return promise;
};

export default request;