import request from '@/utils/request'

// 查询平台建议列表
export function listProposal(query) {
  return request({
    url: '/system/proposal/list',
    method: 'get',
    params: query
  })
}

// 查询平台建议详细
export function getProposal(id) {
  return request({
    url: '/system/proposal/' + id,
    method: 'get'
  })
}

// 新增平台建议
export function addProposal(data) {
  return request({
    url: '/system/proposal',
    method: 'post',
    data: data
  })
}

// 修改平台建议
export function updateProposal(data) {
  return request({
    url: '/system/proposal',
    method: 'put',
    data: data
  })
}

// 删除平台建议
export function delProposal(id) {
  return request({
    url: '/system/proposal/' + id,
    method: 'delete'
  })
}
