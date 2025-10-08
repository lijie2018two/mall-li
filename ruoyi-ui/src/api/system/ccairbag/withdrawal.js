import request from '@/utils/request'

// 查询提现记录列表
export function listWithdrawal(query) {
  return request({
    url: '/system/withdrawal/list',
    method: 'get',
    params: query
  })
}

// 查询提现记录详细
export function getWithdrawal(id) {
  return request({
    url: '/system/withdrawal/' + id,
    method: 'get'
  })
}

// 新增提现记录
export function addWithdrawal(data) {
  return request({
    url: '/system/withdrawal',
    method: 'post',
    data: data
  })
}

// 修改提现记录
export function updateWithdrawal(data) {
  return request({
    url: '/system/withdrawal',
    method: 'put',
    data: data
  })
}

// 删除提现记录
export function delWithdrawal(id) {
  return request({
    url: '/system/withdrawal/' + id,
    method: 'delete'
  })
}
