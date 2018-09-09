import request from '@/utils/request'

export function fetchSysUserList(query) {
  return request({
    url: '/api/v1/sysUser/list?' + query,
    method: 'get'
  })
}

export function fetchSysRoleList(query) {
  return request({
    url: '/api/v1/sysRole/list?' + query,
    method: 'get'
  })
}
export function fetchSysDictList(query) {
  return request({
    url: '/api/v1/sysDict/list?' + query,
    method: 'get'
  })
}
export function addSysDict(data) {
  return request({
    url: '/api/v1/sysDict/add',
    method: 'post',
    data: data
  })
}
export function updateSysDict(data) {
  return request({
    url: '/api/v1/sysDict/update',
    method: 'post',
    data: data
  })
}
export function deleteSysDict(id) {
  return request({
    url: '/api/v1/sysDict/delete',
    method: 'post',
    data: {
      id: id
    }
  })
}

export function getSysOrgTree(query) {
  return request({
    url: '/api/v1/sysOrg/tree?' + query,
    method: 'get'
  })
}

export function getSysOrgList(query) {
  return request({
    url: '/api/v1/sysOrg/list?' + query,
    method: 'get'
  })
}

export function addSysOrg(data) {
  return request({
    url: '/api/v1/sysOrg/add',
    method: 'post',
    data: data
  })
}
export function updateSysOrg(data) {
  return request({
    url: '/api/v1/sysOrg/update',
    method: 'post',
    data: data
  })
}
export function deleteSysOrg(data) {
  return request({
    url: '/api/v1/sysOrg/delete',
    method: 'post',
    data: data
  })
}

export function getResourceTree(query) {
  return request({
    url: '/api/v1/sysResource/list',
    method: 'get',
    params: query
  })
}
export function addSysResource(data) {
  return request({
    url: '/api/v1/sysResource/add',
    method: 'post',
    data: data
  })
}
export function updateSysResource(data) {
  return request({
    url: '/api/v1/sysResource/update',
    method: 'post',
    data: data
  })
}
export function deleteSysResource(data) {
  return request({
    url: '/api/v1/sysResource/delete',
    method: 'post',
    data: data
  })
}
