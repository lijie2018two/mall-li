import request from '@/utils/request'

// 查询点赞列表
export function listEnjoy(query) {
  return request({
    url: '/system/enjoy/list',
    method: 'get',
    params: query
  })
}

// 查询点赞详细
export function getEnjoy(id) {
  return request({
    url: '/system/enjoy/' + id,
    method: 'get'
  })
}

// 新增点赞
export function addEnjoy(data) {
  return request({
    url: '/system/enjoy',
    method: 'post',
    data: data
  })
}

// 修改点赞
export function updateEnjoy(data) {
  return request({
    url: '/system/enjoy',
    method: 'put',
    data: data
  })
}

// 删除点赞
export function delEnjoy(id) {
  return request({
    url: '/system/enjoy/' + id,
    method: 'delete'
  })
}
