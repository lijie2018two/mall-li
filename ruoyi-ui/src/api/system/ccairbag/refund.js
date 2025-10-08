import request from '@/utils/request'

// 查询订单退款列表
export function listRefund(query) {
  return request({
    url: '/system/ccairbag/refund/list',
    method: 'get',
    params: query
  })
}


// 处理卖家问题
// refundId: 退款ID，long类型
// 返回值: Promise对象
// 使用示例: handleSellerProblem(123456789).then(response => { /* 处理响应 */ })
export function handleSellerProblem(refundId) {
  return request({
    url: '/system/ccairbag/refund/handleSellerProblem',
    method: 'get',
    params: {
      refundId: Number(refundId) // 确保转换为数字类型
    }
  })
}

// 处理买家问题
// refundId: 退款ID，long类型
// 返回值: Promise对象
// 使用示例: handleBuyerProblem(123456789).then(response => { /* 处理响应 */ })
export function handleBuyerProblem(refundId) {
  return request({
    url: '/system/ccairbag/refund/handleBuyerProblem',
    method: 'get',
    params: {
      refundId: Number(refundId) // 确保转换为数字类型
    }
  })
}


