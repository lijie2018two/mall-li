import request from '@/utils/request'

// 查询物流商品关联列表
export function listLogistics(query) {
  return request({
    url: '/system/ccairbag/logistics/list',
    method: 'get',
    params: query
  })
}

// 查询物流商品关联详细
export function getLogistics(id) {
  return request({
    url: '/system/ccairbag/logistics/' + id,
    method: 'get'
  })
}

// 新增物流商品关联
export function addLogistics(data) {
  return request({
    url: '/system/ccairbag/logistics',
    method: 'post',
    data: data
  })
}

// 修改物流商品关联
export function updateLogistics(data) {
  return request({
    url: '/system/ccairbag/logistics',
    method: 'put',
    data: data
  })
}

// 删除物流商品关联
export function delLogistics(id) {
  return request({
    url: '/system/ccairbag/logistics/' + id,
    method: 'delete'
  })
}
