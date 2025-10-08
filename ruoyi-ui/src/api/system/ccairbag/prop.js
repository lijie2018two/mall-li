import request from '@/utils/request'

// 查询商品属性列表
export function listProp(query) {
  return request({
    url: '/system/ccairbag/prop/list',
    method: 'get',
    params: query
  })
}

// 查询商品属性详细
export function getProp(propId) {
  return request({
    url: '/system/ccairbag/prop/' + propId,
    method: 'get'
  })
}

// 新增商品属性
export function addProp(data) {
  return request({
    url: '/system/ccairbag/prop',
    method: 'post',
    data: data
  })
}

// 修改商品属性
export function updateProp(data) {
  return request({
    url: '/system/ccairbag/prop',
    method: 'put',
    data: data
  })
}

// 删除商品属性
export function delProp(propId) {
  return request({
    url: '/system/ccairbag/prop/' + propId,
    method: 'delete'
  })
}
