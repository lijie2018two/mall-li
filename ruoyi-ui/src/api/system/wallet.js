import request from '@/utils/request'

// 查询钱包总列表
export function listWallet(query) {
  return request({
    url: '/system/wallet/list',
    method: 'get',
    params: query
  })
}

// 查询钱包总详细
export function getWallet(id) {
  return request({
    url: '/system/wallet/' + id,
    method: 'get'
  })
}

// 新增钱包总
export function addWallet(data) {
  return request({
    url: '/system/wallet',
    method: 'post',
    data: data
  })
}

// 修改钱包总
export function updateWallet(data) {
  return request({
    url: '/system/wallet',
    method: 'put',
    data: data
  })
}

// 删除钱包总
export function delWallet(id) {
  return request({
    url: '/system/wallet/' + id,
    method: 'delete'
  })
}
