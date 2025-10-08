import request from '@/utils/request'

// 查询业务员列表
export function listBusinessUser(query) {
  return request({
    url: '/system/businessUser/list',
    method: 'get',
    params: query
  })
}

// 查询业务员详细
export function getBusinessUser(id) {
  return request({
    url: '/system/businessUser/' + id,
    method: 'get'
  })
}

// 新增业务员
export function addBusinessUser(data) {
  return request({
    url: '/system/businessUser',
    method: 'post',
    data: data
  })
}

// 修改业务员
export function updateBusinessUser(data) {
  return request({
    url: '/system/businessUser',
    method: 'put',
    data: data
  })
}

// 删除业务员
export function delBusinessUser(id) {
  return request({
    url: '/system/businessUser/' + id,
    method: 'delete'
  })
}
