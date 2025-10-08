import request from '@/utils/request'

// 查询购物车列表
export function listCarts(query) {
  return request({
    url: '/system/ccairbag/carts/list',
    method: 'get',
    params: query
  })
}

// 查询购物车详细
export function getCarts(cartId) {
  return request({
    url: '/system/ccairbag/carts/' + cartId,
    method: 'get'
  })
}

// 新增购物车
export function addCarts(data) {
  return request({
    url: '/system/ccairbag/carts',
    method: 'post',
    data: data
  })
}

// 修改购物车
export function updateCarts(data) {
  return request({
    url: '/system/ccairbag/carts',
    method: 'put',
    data: data
  })
}

// 删除购物车
export function delCarts(cartId) {
  return request({
    url: '/system/ccairbag/carts/' + cartId,
    method: 'delete'
  })
}
