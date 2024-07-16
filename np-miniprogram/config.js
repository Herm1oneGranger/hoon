const baseApi = {
	develop: "http://143.64.233.91:8080", //本地开发环境地址
	trial: "https://etrace.rexrothlive.cn/api", //测试环境地址
	release: "https://etrace.rexrothlive.cn/api" //正式环境地址
}




// #ifdef MP
const accountInfo = uni.getAccountInfoSync();
const env = accountInfo.miniProgram.envVersion;

console.log('env--------', env)
if (!env) {
	console.error("获取运行环境失败!");
}
// #endif

// #ifdef H5
const env = 'develop'
// #endif

const config = {
	apiBaseUrl: baseApi[env]
};

export default config;