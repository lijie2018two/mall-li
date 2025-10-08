import request from '@/utils/request'

// 查询高校数据列表
export function listSchool(query) {
  return request({
    url: '/system/school/list',
    method: 'get',
    params: query
  })
}

// 查询高校数据详细
export function getSchool(id) {
  return request({
    url: '/system/school/' + id,
    method: 'get'
  })
}

// 新增高校数据
export function addSchool(data) {
  return request({
    url: '/system/school',
    method: 'post',
    data: data
  })
}

// 修改高校数据
export function updateSchool(data) {
  return request({
    url: '/system/school',
    method: 'put',
    data: data
  })
}

// 删除高校数据
export function delSchool(id) {
  return request({
    url: '/system/school/' + id,
    method: 'delete'
  })
}
