import axios from 'axios'
import Adapter from 'axios-mock-adapter'
import { get } from 'lodash'
import util from '@/libs/util'
import { errorLog, errorCreate } from './tools'

/**
 * @description 创建请求实例
 */
function createService () {
  // 创建一个 axios 实例
  const service = axios.create()
  // 请求拦截
  service.interceptors.request.use(
    config => config,
    error => {
      // 发送失败
      console.log(error)
      return Promise.reject(error)
    }
  )
  // 响应拦截
  service.interceptors.response.use(
    response => {
      // dataAxios 是 axios 返回数据中的 data
      const dataAxios = response.data
      // 这个状态码是和后端约定的
      // code 是原生框架模拟的返回值，status是实际返回的
      let { code } = dataAxios
      if (dataAxios.status) {
        code = dataAxios.status
      }
      // else {
      //   { code } = dataAxios
      // }
      // 根据 code 进行判断
      if (code === undefined) {
        // 如果没有 code 代表这不是项目后端开发的接口 比如可能是 D2Admin 请求最新版本
        return dataAxios
      } else {
        // 有 code 代表这是一个后端接口 可以进行进一步的判断
        switch (code) {
          case 0:
            // [ 示例 ] code === 0 代表没有错误
            return dataAxios.data
          case 200:
            // code === 200 代表 ok
            return dataAxios.data
          case 500:
            // code === 500 代表 失败
            return 'fail'
          case 20000:
            // code === 20000 是 白泽容 的 OK
            return dataAxios.data
          case 40001:
            // code === 40001 代表 注册输入有误
            return { status: 40001, message: dataAxios.message, data: dataAxios.data }
          case 40002:
            // code === 40002 代表 当前用户名已存在
            return { status: 40002, message: '当前用户名已存在' }
          case 40003:
            // code === 40003 代表 密码错误或用户不存在
            return { status: 40003, message: '密码错误或用户不存在' }
          case 40004:
            // code === 40004 代表 密码错误或用户不存在
            return { status: 40004, message: '密码错误或用户不存在' }
          case 40005:
            // code === 40005 代表 未找到以下ID
            return { status: 40005, message: '未找到以下ID' }
          case 40006:
            // code === 40006 代表 物品已经存在异常
            return { status: 40006, message: '物品已经存在异常' }
          case 40009:
            // code === 40009 代表 后端未知异常
            return { status: 40009, message: '后端未知异常' }
          case 50001:
            // code === 50001 代表 用户增加未知异常
            return { status: 50001, message: '用户增加未知异常' }
          case 50002:
            // code === 50002 代表 增加资金异常
            return { status: 50002, message: '增加资金异常' }
          case 50003:
            // code === 50003 代表 用户日志记录异常
            return { status: 50003, message: '用户日志记录异常' }
          case 50004:
            // code === 50004 代表 申请状态更改未知异常
            return { status: 50004, message: '申请状态更改未知异常' }
          case 50005:
            // code === 50005 代表 未知异常
            return { status: 50005, message: '未知异常' }
          case 50006:
            // code === 50006 代表 更改回应状态未知异常
            return { status: 50006, message: '更改回应状态未知异常' }
          default:
            // 未设置的 code
            this.$log.push({
              message: '错误代码' + code + ':' + (dataAxios.message || dataAxios.msg),
              type: 'warning'
            })
            this.$notify({
              title: `没有设置状态码${code}的相应数据`,
              message: '错误代码' + code + ':' + (dataAxios.message || dataAxios.msg),
              position: 'bottom-left',
              type: 'warning'
            })
            console.warn(`没有设置状态码${code}的相应数据`)
            errorCreate(`${dataAxios.message || dataAxios.msg}: ${response.config.url}`)
            break
        }
      }
    },
    error => {
      const status = get(error, 'response.status')
      switch (status) {
        case 400: error.message = '请求错误'; break
        case 401: error.message = '未授权，请登录'; break
        case 403: error.message = '拒绝访问'; break
        case 404: error.message = `请求地址出错: ${error.response.config.url}`; break
        case 408: error.message = '请求超时'; break
        case 500: error.message = '服务器内部错误'; break
        case 501: error.message = '服务未实现'; break
        case 502: error.message = '网关错误'; break
        case 503: error.message = '服务不可用'; break
        case 504: error.message = '网关超时'; break
        case 505: error.message = 'HTTP版本不受支持'; break
        default: break
      }
      errorLog(error)
      return Promise.reject(error)
    }
  )
  return service
}

/**
 * @description 创建请求方法
 * @param {Object} service axios 实例
 */
function createRequestFunction (service) {
  return function (config) {
    const token = util.cookies.get('token')
    const configDefault = {
      headers: {
        Authorization: token,
        'Content-Type': get(config, 'headers.Content-Type', 'application/json')
      },
      timeout: 5000,
      withCredentials: true,
      baseURL: process.env.VUE_APP_API,
      data: {}
    }
    return service(Object.assign(configDefault, config))
  }
}

// 用于真实网络请求的实例和请求方法
export const service = createService()
export const request = createRequestFunction(service)

// 用于模拟网络请求的实例和请求方法
export const serviceForMock = createService()
export const requestForMock = createRequestFunction(serviceForMock)

// 网络请求数据模拟工具
export const mock = new Adapter(serviceForMock)
