import request from '@/utils/request'

// 查询投诉评价列表
export function listPropose(query) {
  return request({
    url: '/system/propose/list',
    method: 'get',
    params: query
  })
}

export function listProposeExt(query) {
  return request({
    url: '/system/propose/tslist',
    method: 'get',
    params: query
  })
}

export function getProposeExt(id) {
  return request({
    url: '/system/propose/ts/' + id,
    method: 'get'
  })
}

// 查询投诉评价详细
export function getPropose(id) {
  return request({
    url: '/system/propose/' + id,
    method: 'get'
  })
}

// 新增投诉评价
export function addPropose(data) {
  return request({
    url: '/system/propose',
    method: 'post',
    data: data
  })
}

// 修改投诉评价
export function updatePropose(data) {
  return request({
    url: '/system/propose',
    method: 'put',
    data: data
  })
}

export function updateTsPropose(data) {
  return request({
    url: '/system/propose/updateTsPropose',
    method: 'post',
    data: data
  })
}


// 删除投诉评价
export function delPropose(id) {
  return request({
    url: '/system/propose/' + id,
    method: 'delete'
  })
}
