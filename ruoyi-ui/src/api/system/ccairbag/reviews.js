import request from '@/utils/request'

// 查询商品评论列表
export function listReviews(query) {
  return request({
    url: '/system/ccairbag/reviews/list',
    method: 'get',
    params: query
  })
}

// 查询商品评论详细
export function getReviews(reviewId) {
  return request({
    url: '/system/ccairbag/reviews/' + reviewId,
    method: 'get'
  })
}

// 新增商品评论
export function addReviews(data) {
  return request({
    url: '/system/ccairbag/reviews',
    method: 'post',
    data: data
  })
}

// 修改商品评论
export function updateReviews(data) {
  return request({
    url: '/system/ccairbag/reviews',
    method: 'put',
    data: data
  })
}

// 删除商品评论
export function delReviews(reviewId) {
  return request({
    url: '/system/ccairbag/reviews/' + reviewId,
    method: 'delete'
  })
}
