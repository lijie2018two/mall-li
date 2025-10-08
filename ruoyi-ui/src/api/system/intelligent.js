import request from '@/utils/request'

// 查询达人 达人基础信息列表
export function listIntelligent(query) {
  return request({
    url: '/system/intelligent/list',
    method: 'get',
    params: query
  })
}

// 查询达人 达人基础信息详细
export function getIntelligent(id) {
  return request({
    url: '/system/intelligent/' + id,
    method: 'get'
  })
}

// 新增达人 达人基础信息
export function addIntelligent(data) {
  return request({
    url: '/system/intelligent',
    method: 'post',
    data: data
  })
}

// 修改达人 达人基础信息
export function updateIntelligent(data) {
  return request({
    url: '/system/intelligent',
    method: 'put',
    data: data
  })
}

// 删除达人 达人基础信息
export function delIntelligent(id) {
  return request({
    url: '/system/intelligent/' + id,
    method: 'delete'
  })
}


export function listIntelligentExt(query) {
  return request({
    url: '/system/intelligent/listExt',
    method: 'get',
    params: query
  })
}

export function updateIntelligentExt(data) {
  return request({
    url: '/system/intelligent/updateIntelligentExt',
    method: 'post',
    data: data
  })
}



export function process(data) {
  return request({
    url: '/system/intelligent/process',
    method: 'post',
    data: data
  })
}