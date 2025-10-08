import request from '@/utils/request'

// 查询敏感字管理列表
export function listKeywords(query) {
  return request({
    url: '/system/keywords/list',
    method: 'get',
    params: query
  })
}

// 查询敏感字管理详细
export function getKeywords(id) {
  return request({
    url: '/system/keywords/' + id,
    method: 'get'
  })
}

// 新增敏感字管理
export function addKeywords(data) {
  return request({
    url: '/system/keywords',
    method: 'post',
    data: data
  })
}

// 修改敏感字管理
export function updateKeywords(data) {
  return request({
    url: '/system/keywords',
    method: 'put',
    data: data
  })
}

// 删除敏感字管理
export function delKeywords(id) {
  return request({
    url: '/system/keywords/' + id,
    method: 'delete'
  })
}
