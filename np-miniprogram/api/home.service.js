import request from './general'

const WX_URL = `https://api.weixin.qq.com`
const WX_MAP_URL = `https://apis.map.qq.com`

const verify = async (data) => {
	const url =
		`/product/verify/check?appId=${data.appId}&mnr=${data.mnr}&fd=${data.fd}&sn=${data.sn}&position=${data.position}&province=${data.province}&city=${data.city}&userPhoneOS=${data.userPhoneOS}`
	const method = 'POST'

	return request({
		url,
		method,
		data
	})
}

const verifyNoMnr = async (data) => {
	const url =
		`/product/verify/checkNoMnr`
	const method = 'POST'

	return request({
		url,
		method,
		data
	})
}



const getProductInfo = async (id) => {
	const url = `/product/verify/getInfo/${id}`
	const method = 'GET'

	return request({
		url,
		method,
	})
}


const getProductImg = async (imgPath) => {
	const url = `/product/verify/getProductPicture/${imgPath}`
	const method = 'GET'

	return request({
		url,
		method,
	})
}


const transferLocation = async (data) => {
	return new Promise((reslove, reject) => {
		wx.request({
			url: `${WX_MAP_URL}/ws/geocoder/v1/?location=${data.latitude},${data.longitude}&key=	
Y5YBZ-NUTE6-KRDSU-ECXS7-3L6FS-5CFKG`,

			success: function(data) {
				reslove(data.data.result)
			},
			fail: (res) => {
				reject(res)
			}
		})
	})
}

const getUserToken = async (data) => {
	const url = `/product/verify/getOpenId`
	const method = 'POST'

	return request({
		url,
		method,
		data: {
			...data,
		}
	})
}

const getUserInfo = async (data) => {
	return new Promise((reslove, reject) => {
		wx.request({
			url: `${WX_URL}/sns/userinfo?access_token=${data.token}&openid=${data.openId}&lang=zh_CN`,
			success: function(data) {
				reslove(data.data)
			},
			fail: (res) => {
				reject(res)
			}
		})
	})
}

const getUserPhonenumber = async (data) => {
	return new Promise((reslove, reject) => {
		wx.request({
			url: `${WX_URL}/wxa/business/getuserphonenumber?access_token=${data.token}&code=${data.code}`,
			success: function(data) {
				reslove(data.data)
			},
			fail: (res) => {
				reject(res)
			}
		})
	})
}

export const homeService = {
	verify,
	verifyNoMnr,
	transferLocation,
	getUserInfo,
	getUserToken,
	getProductInfo,
	getProductImg,
	getUserPhonenumber
}