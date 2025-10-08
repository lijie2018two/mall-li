import request from '@/utils/request'

// 查询商品标签关系表列表
export function listReference(query) {
  return request({
    url: '/system/ccairbag/reference/list',
    method: 'get',
    params: query
  })
}

// 查询商品标签关系表详细
export function getReference(referenceId) {
  return request({
    url: '/system/ccairbag/reference/' + referenceId,
    method: 'get'
  })
}

// 新增商品标签关系表
export function addReference(data) {
  return request({
    url: '/system/ccairbag/reference',
    method: 'post',
    data: data
  })
}

// 修改商品标签关系表
export function updateReference(data) {
  return request({
    url: '/system/ccairbag/reference',
    method: 'put',
    data: data
  })
}

// 删除商品标签关系表
export function delReference(referenceId) {
  return request({
    url: '/system/ccairbag/reference/' + referenceId,
    method: 'delete'
  })
}
