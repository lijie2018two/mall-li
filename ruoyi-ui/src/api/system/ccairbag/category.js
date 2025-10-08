import request from '@/utils/request'

// 查询商品类目列表
export function listCategory(query) {
  return request({
    url: '/system/ccairbag/category/list',
    method: 'get',
    params: query
  })
}

// 查询商品类目详细
export function getCategory(categoryId) {
  return request({
    url: '/system/ccairbag/category/' + categoryId,
    method: 'get'
  })
}

// 新增商品类目
export function addCategory(data) {
  return request({
    url: '/system/ccairbag/category',
    method: 'post',
    data: data
  })
}

// 修改商品类目
export function updateCategory(data) {
  return request({
    url: '/system/ccairbag/category',
    method: 'put',
    data: data
  })
}

// 删除商品类目
export function delCategory(categoryId) {
  return request({
    url: '/system/ccairbag/category/' + categoryId,
    method: 'delete'
  })
}
