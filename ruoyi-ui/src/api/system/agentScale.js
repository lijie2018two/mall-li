import request from '@/utils/request'

// 查询代理商抽佣设置 代理商的抽佣设置列表
export function listAgentScale(query) {
  return request({
    url: '/system/agentScale/list',
    method: 'get',
    params: query
  })
}

// 查询代理商抽佣设置 代理商的抽佣设置详细
export function getAgentScale(id) {
  return request({
    url: '/system/agentScale/' + id,
    method: 'get'
  })
}

// 新增代理商抽佣设置 代理商的抽佣设置
export function addAgentScale(data) {
  return request({
    url: '/system/agentScale',
    method: 'post',
    data: data
  })
}

// 修改代理商抽佣设置 代理商的抽佣设置
export function updateAgentScale(data) {
  return request({
    url: '/system/agentScale',
    method: 'put',
    data: data
  })
}

// 删除代理商抽佣设置 代理商的抽佣设置
export function delAgentScale(id) {
  return request({
    url: '/system/agentScale/' + id,
    method: 'delete'
  })
}
