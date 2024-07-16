import config from '@/config/api.config'
import { createAuthInstance } from './general'
import qs from 'qs'

const baseUrl = `${config.apiHost}`


const getProductList  = async (parameter) => {
  const url = `/product/list?${qs.stringify(parameter)}`
  const { data } = await createAuthInstance(baseUrl).get(url, parameter)
  return data
}

const addProduct  = async (parameter) => {
  const url = `/product/add`
  const { data } = await createAuthInstance(baseUrl).post(url, parameter)
  return data
}

const editProduct  = async (parameter) => {
  const url = `/product/edit`
  const { data } = await createAuthInstance(baseUrl).put(url, parameter)
  return data
}

const exportProduct  = async (parameter) => {
  const url = `/product/export`
  const { data } = await createAuthInstance(baseUrl).post(url, parameter,{
    responseType:'blob'
  })
  return data
}

const getProductInfo  = async (id) => {
  const url = `/product/getInfo/${id}`
  const { data } = await createAuthInstance(baseUrl).get(url)
  return data
}



const getPrintLabelList  = async (parameter) => {
  const url = `/product/print/list?${qs.stringify(parameter)}`
  const { data } = await createAuthInstance(baseUrl).get(url, parameter)
  return data
}

const uploadPrintLableFile  = async (formdata) => {
  const url = `/product/print/importData`
  const { data } = await createAuthInstance(baseUrl).post(url, formdata)
  return data
}

const exportPrintLable  = async (parameter) => {
  const url = `/product/print/export`
  const { data } = await createAuthInstance(baseUrl).post(url, parameter,{
    responseType:'blob'
  })
  return data
}

const getVerifyRecordList  = async (parameter) => {
  const url = `/product/verify/list?${qs.stringify(parameter)}`
  const { data } = await createAuthInstance(baseUrl).get(url, parameter)
  return data
}

const exportVerifyRecord  = async (parameter) => {
  const url = `/product/verify/export`
  const { data } = await createAuthInstance(baseUrl).post(url, parameter,{
    responseType:'blob'
  })
  return data
}


export const productService = {
  getProductList,
  addProduct,
  editProduct,
  exportProduct,
  getProductInfo,

  getPrintLabelList,
  uploadPrintLableFile,
  exportPrintLable,

  getVerifyRecordList,
  exportVerifyRecord
}
