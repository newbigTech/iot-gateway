import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { getToken, TokenKey, removeToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000 // request timeout
})

// request拦截器
service.interceptors.request.use(config => {
  // Do something before request is sent
  if (store.getters.token) {
    config.headers[TokenKey] = getToken()
  }
  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
  Promise.reject(error)
})
// respone interceptor
service.interceptors.response.use(function(response) {
  // 正常的请求前拦截,在这里处理
  return response
}, function(error) {
  // 非200请求时的错误处理
  const res = error.response.data // 请求data
  const status = error.response.status // 请求状态吗
  Message({
    message: res.msg || '未知错误',
    type: 'error',
    duration: 5 * 1000
  })
  // console.log(res)
  if (status === 401) {
    // 原来的token 可能是错的,需要删除
    removeToken()
    // iam 未登录错误
    window.location.href = res.result
  }
  // Do something with response error
  return Promise.reject(error)
})
export default service
