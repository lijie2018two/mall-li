import request from '@/utils/request'

// 查询参数表列表
export function listParameters(query) {
  return request({
    url: '/system/ccairbag/parameters/list',
    method: 'get',
    params: query
  })
}

// 查询参数表详细
export function getParameters(parameterId) {
  return request({
    url: '/system/ccairbag/parameters/' + parameterId,
    method: 'get'
  })
}

// 新增参数表
export function addParameters(data) {
  return request({
    url: '/system/ccairbag/parameters',
    method: 'post',
    data: data
  })
}

// 修改参数表
export function updateParameters(data) {
  return request({
    url: '/system/ccairbag/parameters',
    method: 'put',
    data: data
  })
}

// 删除参数表
export function delParameters(parameterId) {
  return request({
    url: '/system/ccairbag/parameters/' + parameterId,
    method: 'delete'
  })
}
