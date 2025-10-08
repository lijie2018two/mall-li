import request from '@/utils/request'

// 查询语音回调记录列表
export function listLogs(query) {
  return request({
    url: '/system/logs/list',
    method: 'get',
    params: query
  })
}

// 查询语音回调记录详细
export function getLogs(voiceId) {
  return request({
    url: '/system/logs/' + voiceId,
    method: 'get'
  })
}

// 新增语音回调记录
export function addLogs(data) {
  return request({
    url: '/system/logs',
    method: 'post',
    data: data
  })
}

// 修改语音回调记录
export function updateLogs(data) {
  return request({
    url: '/system/logs',
    method: 'put',
    data: data
  })
}

// 删除语音回调记录
export function delLogs(voiceId) {
  return request({
    url: '/system/logs/' + voiceId,
    method: 'delete'
  })
}
