import {
	homeService
} from '@/api'

const materialIn = {
	namespaced: true,
	state: {},

	mutations: {},

	actions: {
		async transferLocation({
			commit
		}, options) {
			const data = await homeService.transferLocation(options)
			return data
		},
		async verify({
			commit
		}, options) {
			const data = await homeService.verify(options)
			return data
		},
		async verifyNoMnr({
			commit
		}, options) {
			const data = await homeService.verifyNoMnr(options)
			return data
		},
		async getUserInfo({
			commit
		}, options) {
			const data = await homeService.getUserInfo(options)
			return data
		},
		async getUserToken({
			commit
		}, options) {
			const data = await homeService.getUserToken(options)
			return data
		},
		async getProductInfo({
			commit
		}, options) {
			const data = await homeService.getProductInfo(options)
			return data
		},

		async getProductImg({
			commit
		}, options) {
			const data = await homeService.getProductImg(options)
			return data
		},
	}
}

export default materialIn