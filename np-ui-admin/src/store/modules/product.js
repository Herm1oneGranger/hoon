import { productService } from '@/api'

const product = {
  namespaced: true,
  state: {
  },

  mutations: {

  },

  actions: {
    async getPrintLabelList ({ commit }, options) {
      const data = await productService.getPrintLabelList(options)
      return data
    },
    async uploadPrintLableFile ({ commit }, options) {
      const data = await productService.uploadPrintLableFile(options)
      return data
    },
    async exportPrintLable ({ commit }, options) {
      const data = await productService.exportPrintLable(options)
      return data
    },
   
    async getProductList ({ commit }, options) {
      const data = await productService.getProductList(options)
      return data
    },
    async addProduct ({ commit }, options) {
      const data = await productService.addProduct(options)
      return data
    },
    async editProduct ({ commit }, options) {
      const data = await productService.editProduct(options)
      return data
    },
    async exportProduct ({ commit }, options) {
      const data = await productService.exportProduct(options)
      return data
    },
    async getProductInfo ({ commit }, options) {
      const data = await productService.getProductInfo(options)
      return data
    },

    async exportVerifyRecord ({ commit }, options) {
      const data = await productService.exportVerifyRecord(options)
      return data
    },
    async getVerifyRecordList ({ commit }, options) {
      const data = await productService.getVerifyRecordList(options)
      return data
    },
    async getProductInfo ({ commit }, id) {
      const data = await productService.getProductInfo(id)
      return data
    },
    

    
  }
}

export default product
