import request from '@/utils/request'

// 查询sku列表
export function listSkus(query) {
  return request({
    url: '/system/ccairbag/skus/list',
    method: 'get',
    params: query
  })
}

// 查询sku详细
export function getSkus(skuId) {
  return request({
    url: '/system/ccairbag/skus/' + skuId,
    method: 'get'
  })
}

// 新增sku
export function addSkus(data) {
  return request({
    url: '/system/ccairbag/skus',
    method: 'post',
    data: data
  })
}

// 修改sku
export function updateSkus(data) {
  return request({
    url: '/system/ccairbag/skus',
    method: 'put',
    data: data
  })
}

// 删除sku
export function delSkus(skuId) {
  return request({
    url: '/system/ccairbag/skus/' + skuId,
    method: 'delete'
  })
}
