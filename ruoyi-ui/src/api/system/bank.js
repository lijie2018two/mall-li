import request from '@/utils/request'

// 查询用户绑定银行卡列表
export function listBank(query) {
  return request({
    url: '/system/bank/list',
    method: 'get',
    params: query
  })
}

// 查询用户绑定银行卡详细
export function getBank(id) {
  return request({
    url: '/system/bank/' + id,
    method: 'get'
  })
}

// 新增用户绑定银行卡
export function addBank(data) {
  return request({
    url: '/system/bank',
    method: 'post',
    data: data
  })
}

// 修改用户绑定银行卡
export function updateBank(data) {
  return request({
    url: '/system/bank',
    method: 'put',
    data: data
  })
}

// 删除用户绑定银行卡
export function delBank(id) {
  return request({
    url: '/system/bank/' + id,
    method: 'delete'
  })
}
