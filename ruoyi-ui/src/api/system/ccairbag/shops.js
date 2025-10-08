import request from '@/utils/request'

// 查询店铺列表
export function listShops(query) {
  return request({
    url: '/system/ccairbag/shops/list',
    method: 'get',
    params: query
  })
}

// 查询店铺详细
export function getShops(shopId) {
  return request({
    url: '/system/ccairbag/shops/' + shopId,
    method: 'get'
  })
}

// 新增店铺
export function addShops(data) {
  return request({
    url: '/system/ccairbag/shops',
    method: 'post',
    data: data
  })
}

// 修改店铺
export function updateShops(data) {
  return request({
    url: '/system/ccairbag/shops',
    method: 'put',
    data: data
  })
}

// 删除店铺
export function delShops(shopId) {
  return request({
    url: '/system/ccairbag/shops/' + shopId,
    method: 'delete'
  })
}
