import request from '@/utils/request'

// 查询物流服务列表
export function listServices(query) {
  return request({
    url: '/system/ccairbag/services/list',
    method: 'get',
    params: query
  })
}

// 查询物流服务详细
export function getServices(serviceId) {
  return request({
    url: '/system/ccairbag/services/' + serviceId,
    method: 'get'
  })
}

// 新增物流服务
export function addServices(data) {
  return request({
    url: '/system/ccairbag/services',
    method: 'post',
    data: data
  })
}

// 修改物流服务
export function updateServices(data) {
  return request({
    url: '/system/ccairbag/services',
    method: 'put',
    data: data
  })
}

// 删除物流服务
export function delServices(serviceId) {
  return request({
    url: '/system/ccairbag/services/' + serviceId,
    method: 'delete'
  })
}
