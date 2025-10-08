import request from '@/utils/request'

// 查询用户地址管理列表
export function listAddr(query) {
  return request({
    url: '/system/ccairbag/addr/list',
    method: 'get',
    params: query
  })
}

// 查询用户地址管理详细
export function getAddr(addrId) {
  return request({
    url: '/system/ccairbag/addr/' + addrId,
    method: 'get'
  })
}

// 新增用户地址管理
export function addAddr(data) {
  return request({
    url: '/system/ccairbag/addr',
    method: 'post',
    data: data
  })
}

// 修改用户地址管理
export function updateAddr(data) {
  return request({
    url: '/system/ccairbag/addr',
    method: 'put',
    data: data
  })
}

// 删除用户地址管理
export function delAddr(addrId) {
  return request({
    url: '/system/ccairbag/addr/' + addrId,
    method: 'delete'
  })
}
