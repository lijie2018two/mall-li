import request from '@/utils/request'

// 查询商品属性值列表
export function listValue(query) {
  return request({
    url: '/system/ccairbag/value/list',
    method: 'get',
    params: query
  })
}

// 查询商品属性值详细
export function getValue(valueId) {
  return request({
    url: '/system/ccairbag/value/' + valueId,
    method: 'get'
  })
}

// 新增商品属性值
export function addValue(data) {
  return request({
    url: '/system/ccairbag/value',
    method: 'post',
    data: data
  })
}

// 修改商品属性值
export function updateValue(data) {
  return request({
    url: '/system/ccairbag/value',
    method: 'put',
    data: data
  })
}

// 删除商品属性值
export function delValue(valueId) {
  return request({
    url: '/system/ccairbag/value/' + valueId,
    method: 'delete'
  })
}
