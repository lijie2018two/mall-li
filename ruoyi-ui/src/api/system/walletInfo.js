import request from '@/utils/request'

// 查询钱包明细列表
export function listWalletInfo(query) {
  return request({
    url: '/system/walletInfo/list',
    method: 'get',
    params: query
  })
}

// 查询钱包明细详细
export function getWalletInfo(id) {
  return request({
    url: '/system/walletInfo/' + id,
    method: 'get'
  })
}

// 新增钱包明细
export function addWalletInfo(data) {
  return request({
    url: '/system/walletInfo',
    method: 'post',
    data: data
  })
}

// 修改钱包明细
export function updateWalletInfo(data) {
  return request({
    url: '/system/walletInfo',
    method: 'put',
    data: data
  })
}

// 删除钱包明细
export function delWalletInfo(id) {
  return request({
    url: '/system/walletInfo/' + id,
    method: 'delete'
  })
}
