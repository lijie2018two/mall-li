import request from '@/utils/request'

// 查询总抽佣设置 设置比例后会刷新到所有代理商的抽佣设置列表
export function listScale(query) {
  return request({
    url: '/system/scale/list',
    method: 'get',
    params: query
  })
}

// 查询总抽佣设置 设置比例后会刷新到所有代理商的抽佣设置详细
export function getScale(id) {
  return request({
    url: '/system/scale/' + id,
    method: 'get'
  })
}

// 新增总抽佣设置 设置比例后会刷新到所有代理商的抽佣设置
export function addScale(data) {
  return request({
    url: '/system/scale',
    method: 'post',
    data: data
  })
}

// 修改总抽佣设置 设置比例后会刷新到所有代理商的抽佣设置
export function updateScale(data) {
  return request({
    url: '/system/scale',
    method: 'put',
    data: data
  })
}

// 删除总抽佣设置 设置比例后会刷新到所有代理商的抽佣设置
export function delScale(id) {
  return request({
    url: '/system/scale/' + id,
    method: 'delete'
  })
}
