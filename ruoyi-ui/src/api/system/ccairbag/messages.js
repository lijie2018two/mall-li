import request from '@/utils/request'

// 查询消息列表
export function listMessages(query) {
  return request({
    url: '/system/ccairbag/messages/list',
    method: 'get',
    params: query
  })
}

// 查询消息详细
export function getMessages(messageId) {
  return request({
    url: '/system/ccairbag/messages/' + messageId,
    method: 'get'
  })
}

// 新增消息
export function addMessages(data) {
  return request({
    url: '/system/ccairbag/messages',
    method: 'post',
    data: data
  })
}

// 修改消息
export function updateMessages(data) {
  return request({
    url: '/system/ccairbag/messages',
    method: 'put',
    data: data
  })
}

// 删除消息
export function delMessages(messageId) {
  return request({
    url: '/system/ccairbag/messages/' + messageId,
    method: 'delete'
  })
}
