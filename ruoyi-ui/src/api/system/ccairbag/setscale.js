import request from '@/utils/request'

// 查询税收比例
export function listSetscale(query) {
  return request({
    url: '/system/ccairbag/setscale/list',
    method: 'get',
    params: query
  })
}

// 查询税收比例
export function getSetscale(id) {
  return request({
    url: '/system/ccairbag/setscale/' + id,
    method: 'get'
  })
}

// 新增税收比例
export function addSetscale(data) {
  return request({
    url: '/system/ccairbag/setscale',
    method: 'post',
    data: data
  })
}

// 修改税收比例
export function updateSetscale(data) {
  return request({
    url: '/system/ccairbag/setscale',
    method: 'put',
    data: data
  })
}

// 删除税收比例
export function delSetscale(id) {
  return request({
    url: '/system/ccairbag/setscale/' + id,
    method: 'delete'
  })
}
