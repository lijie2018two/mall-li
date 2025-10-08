import request from '@/utils/request'

// 查询微信订单列表
export function listPay(query) {
  return request({
    url: '/system/pay/list',
    method: 'get',
    params: query
  })
}

// 查询微信订单详细
export function getPay(id) {
  return request({
    url: '/system/pay/' + id,
    method: 'get'
  })
}

// 新增微信订单
export function addPay(data) {
  return request({
    url: '/system/pay',
    method: 'post',
    data: data
  })
}

// 修改微信订单
export function updatePay(data) {
  return request({
    url: '/system/pay',
    method: 'put',
    data: data
  })
}

// 删除微信订单
export function delPay(id) {
  return request({
    url: '/system/pay/' + id,
    method: 'delete'
  })
}
