import request from '@/utils/request'

// 查询消息会话列表
export function listConversations(query) {
  return request({
    url: '/system/ccairbag/conversations/list',
    method: 'get',
    params: query
  })
}

// 查询消息会话详细
export function getConversations(conversationId) {
  return request({
    url: '/system/ccairbag/conversations/' + conversationId,
    method: 'get'
  })
}

// 新增消息会话
export function addConversations(data) {
  return request({
    url: '/system/ccairbag/conversations',
    method: 'post',
    data: data
  })
}

// 修改消息会话
export function updateConversations(data) {
  return request({
    url: '/system/ccairbag/conversations',
    method: 'put',
    data: data
  })
}

// 删除消息会话
export function delConversations(conversationId) {
  return request({
    url: '/system/ccairbag/conversations/' + conversationId,
    method: 'delete'
  })
}
