import request from '@/utils/request'

// 查询静态资源列表
export function listStatic(query) {
  return request({
    url: '/system/static/list',
    method: 'get',
    params: query
  })
}

// 查询静态资源详细
export function getStatic(id) {
  return request({
    url: '/system/static/' + id,
    method: 'get'
  })
}

// 新增静态资源
export function addStatic(data) {
  return request({
    url: '/system/static',
    method: 'post',
    data: data
  })
}

// 修改静态资源
export function updateStatic(data) {
  return request({
    url: '/system/static',
    method: 'put',
    data: data
  })
}

// 删除静态资源
export function delStatic(id) {
  return request({
    url: '/system/static/' + id,
    method: 'delete'
  })
}
