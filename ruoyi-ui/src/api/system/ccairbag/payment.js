import request from '@/utils/request'

// 查询订单支付列表
export function listPayment(query) {
  return request({
    url: '/system/ccairbag/payment/list',
    method: 'get',
    params: query
  })
}

// 查询订单支付详细
export function getPayment(paymentId) {
  return request({
    url: '/system/ccairbag/payment/' + paymentId,
    method: 'get'
  })
}

// 新增订单支付
export function addPayment(data) {
  return request({
    url: '/system/ccairbag/payment',
    method: 'post',
    data: data
  })
}

// 修改订单支付
export function updatePayment(data) {
  return request({
    url: '/system/ccairbag/payment',
    method: 'put',
    data: data
  })
}

// 删除订单支付
export function delPayment(paymentId) {
  return request({
    url: '/system/ccairbag/payment/' + paymentId,
    method: 'delete'
  })
}
