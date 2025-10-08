import request from '@/utils/request'

// 查询订单详情列表
export function listDetails(query) {
  return request({
    url: '/system/ccairbag/details/list',
    method: 'get',
    params: query
  })
}

// 查询订单详情详细
export function getDetails(detailId) {
  return request({
    url: '/system/ccairbag/details/' + detailId,
    method: 'get'
  })
}

// 新增订单详情
export function addDetails(data) {
  return request({
    url: '/system/ccairbag/details',
    method: 'post',
    data: data
  })
}

// 修改订单详情
export function updateDetails(data) {
  return request({
    url: '/system/ccairbag/details',
    method: 'put',
    data: data
  })
}

// 删除订单详情
export function delDetails(detailId) {
  return request({
    url: '/system/ccairbag/details/' + detailId,
    method: 'delete'
  })
}
