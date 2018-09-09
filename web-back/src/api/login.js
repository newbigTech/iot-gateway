import request from '@/utils/request'

export function loginByUsername(mobile, password) {
  const data = {
    mobile,
    password
  }
  return request({
    url: '/api/v1/doLogin',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/login/logout',
    method: 'post'
  })
}

