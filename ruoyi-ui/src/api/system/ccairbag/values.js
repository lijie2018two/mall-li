import request from '@/utils/request'

// 查询商品参数值表列表
export function listValues(query) {
  return request({
    url: '/system/ccairbag/values/list',
    method: 'get',
    params: query
  })
}

// 查询商品参数值表详细
export function getValues(valueId) {
  return request({
    url: '/system/ccairbag/values/' + valueId,
    method: 'get'
  })
}

// 新增商品参数值表
export function addValues(data) {
  return request({
    url: '/system/ccairbag/values',
    method: 'post',
    data: data
  })
}

// 修改商品参数值表
export function updateValues(data) {
  return request({
    url: '/system/ccairbag/values',
    method: 'put',
    data: data
  })
}

// 删除商品参数值表
export function delValues(valueId) {
  return request({
    url: '/system/ccairbag/values/' + valueId,
    method: 'delete'
  })
}
