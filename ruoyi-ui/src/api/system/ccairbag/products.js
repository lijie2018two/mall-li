import request from '@/utils/request'

// 查询商品列表
export function listProducts(query) {
  return request({
    url: '/system/ccairbag/products/list',
    method: 'get',
    params: query
  })
}

// 查询商品详细
export function getProducts(productId) {
  return request({
    url: '/system/ccairbag/products/' + productId,
    method: 'get'
  })
}

// 新增商品
export function addProducts(data) {
  return request({
    url: '/system/ccairbag/products',
    method: 'post',
    data: data
  })
}

// 修改商品
export function updateProducts(data) {
  return request({
    url: '/system/ccairbag/products',
    method: 'put',
    data: data
  })
}

// 删除商品
export function delProducts(productId) {
  return request({
    url: '/system/ccairbag/products/' + productId,
    method: 'delete'
  })
}
