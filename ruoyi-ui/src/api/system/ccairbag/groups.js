import request from '@/utils/request'

// 查询参数组列表
export function listGroups(query) {
  return request({
    url: '/system/ccairbag/groups/list',
    method: 'get',
    params: query
  })
}

// 查询参数组详细
export function getGroups(groupId) {
  return request({
    url: '/system/ccairbag/groups/' + groupId,
    method: 'get'
  })
}

// 新增参数组
export function addGroups(data) {
  return request({
    url: '/system/ccairbag/groups',
    method: 'post',
    data: data
  })
}

// 修改参数组
export function updateGroups(data) {
  return request({
    url: '/system/ccairbag/groups',
    method: 'put',
    data: data
  })
}

// 删除参数组
export function delGroups(groupId) {
  return request({
    url: '/system/ccairbag/groups/' + groupId,
    method: 'delete'
  })
}
