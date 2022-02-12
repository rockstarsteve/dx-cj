import request from '@/utils/request'

// 查询奖品列表
export function listPrize(query) {
  return request({
    url: '/cj/prize/list',
    method: 'get',
    params: query
  })
}

// 查询奖品详细
export function getPrize(id) {
  return request({
    url: '/cj/prize/' + id,
    method: 'get'
  })
}

// 新增奖品
export function addPrize(data) {
  return request({
    url: '/cj/prize',
    method: 'post',
    data: data
  })
}

// 修改奖品
export function updatePrize(data) {
  return request({
    url: '/cj/prize',
    method: 'put',
    data: data
  })
}

// 删除奖品
export function delPrize(id) {
  return request({
    url: '/cj/prize/' + id,
    method: 'delete'
  })
}
