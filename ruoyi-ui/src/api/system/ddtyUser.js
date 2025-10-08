import request from '@/utils/request'

// 查询用户 微信授权注册列表
export function listDdtyUser(query) {
  return request({
    url: '/system/ddtyUser/list',
    method: 'get',
    params: query
  })
}

// 查询用户 微信授权注册详细
export function getDdtyUser(id) {
  return request({
    url: '/system/ddtyUser/' + id,
    method: 'get'
  })
}

// 新增用户 微信授权注册
export function addDdtyUser(data) {
  return request({
    url: '/system/ddtyUser',
    method: 'post',
    data: data
  })
}

// 修改用户 微信授权注册
export function updateDdtyUser(data) {
  return request({
    url: '/system/ddtyUser',
    method: 'put',
    data: data
  })
}

// 删除用户 微信授权注册
export function delDdtyUser(id) {
  return request({
    url: '/system/ddtyUser/' + id,
    method: 'delete'
  })
}


export function countUserActive(query) {
  return request({
    url: '/system/ddtyUser/countUserActive',
    method: 'get',
    params: query
  })
}