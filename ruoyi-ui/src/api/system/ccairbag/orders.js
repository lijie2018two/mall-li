import request from '@/utils/request'

// 查询订单列表
export function listOrders(query) {
  return request({
    url: '/system/ccairbag/orders/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrders(orderId) {
  return request({
    url: '/system/ccairbag/orders/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addOrders(data) {
  return request({
    url: '/system/ccairbag/orders',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrders(data) {
  return request({
    url: '/system/ccairbag/orders',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrders(orderId) {
  return request({
    url: '/system/ccairbag/orders/' + orderId,
    method: 'delete'
  })
}
