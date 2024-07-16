import { dashboardService } from '@/api'

const dashboard = {
  namespaced: true,
  state: {
  },

  mutations: {

  },

  actions: {
    async getTotalCount ({ commit }, options) {
      const data = await dashboardService.getTotalCount(options)
      return data
    },
    async getWeekActivatedLabel ({ commit }, options) {
      const data = await dashboardService.getWeekActivatedLabel(options)
      return data
    },
    async getWeekCheckedPeople ({ commit }, options) {
      const data = await dashboardService.getWeekCheckedPeople(options)
      return data
    },
    async getWeekRiskRecord ({ commit }, options) {
      const data = await dashboardService.getWeekRiskRecord(options)
      return data
    },
    async getYearRiskRecord ({ commit }, options) {
      const data = await dashboardService.getYearRiskRecord(options)
      return data
    },
    async getWeekCheckedRecord ({ commit }, options) {
      const data = await dashboardService.getWeekCheckedRecord(options)
      return data
    },
    async getYearCheckedRecord ({ commit }, options) {
      const data = await dashboardService.getYearCheckedRecord(options)
      return data
    },
    async getAddressByProvince ({ commit }, options) {
      const data = await dashboardService.getAddressByProvince(options)
      return data
    },
    async getAddressData ({ commit }, options) {
      const data = await dashboardService.getAddressData(options)
      return data
    },
    
    
    
    
    
    
    
  }
}

export default dashboard
