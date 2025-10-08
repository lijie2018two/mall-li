import request from '@/utils/request'

// 查询用户通知关联列表
export function listNotificationuser(query) {
  return request({
    url: '/system/ccairbag/Notificationuser/list',
    method: 'get',
    params: query
  })
}

// 查询用户通知关联详细
export function getNotificationuser(id) {
  return request({
    url: '/system/ccairbag/Notificationuser/' + id,
    method: 'get'
  })
}

// 新增用户通知关联
export function addNotificationuser(data) {
  return request({
    url: '/system/ccairbag/Notificationuser',
    method: 'post',
    data: data
  })
}

// 修改用户通知关联
export function updateNotificationuser(data) {
  return request({
    url: '/system/ccairbag/Notificationuser',
    method: 'put',
    data: data
  })
}

// 删除用户通知关联
export function delNotificationuser(id) {
  return request({
    url: '/system/ccairbag/Notificationuser/' + id,
    method: 'delete'
  })
}
