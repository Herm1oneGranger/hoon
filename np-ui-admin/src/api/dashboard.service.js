import config from '@/config/api.config'
import { createAuthInstance } from './general'
import qs from 'qs'

const baseUrl = `${config.apiHost}`


const getTotalCount  = async () => {
  const url = `/dashboard/getTotalCount`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}

const getWeekActivatedLabel  = async () => {
  const url = `/dashboard/getWeekActivatedLabel`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}

const getWeekCheckedPeople  = async () => {
  const url = `/dashboard/getWeekCheckedPeople`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}

const getWeekRiskRecord  = async () => {
  const url = `/dashboard/getWeekRiskRecord`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}

const getYearRiskRecord  = async () => {
  const url = `/dashboard/getYearRiskRecord`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}


const getWeekCheckedRecord  = async () => {
  const url = `/dashboard/getWeekCheckedRecord`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}
const getYearCheckedRecord  = async () => {
  const url = `/dashboard/getYearCheckedRecord`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}

const getAddressByProvince  = async () => {
  const url = `/dashboard/getAddressByProvince`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}

const getAddressData  = async () => {
  const url = `/dashboard/getAddressData`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}








export const dashboardService = {
  getTotalCount,
  getWeekActivatedLabel,
  getWeekCheckedPeople,
  getYearRiskRecord,
  getWeekRiskRecord,
  getWeekCheckedRecord,
  getYearCheckedRecord,
  getAddressByProvince,
  getAddressData
}
