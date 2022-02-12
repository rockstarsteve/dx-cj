import request from '@/utils/request'

// 查询员工中奖中间列表
export function listEmpprize(query) {
  return request({
    url: '/cj/empprize/list',
    method: 'get',
    params: query
  })
}

// 查询员工中奖中间详细
export function getEmpprize(id) {
  return request({
    url: '/cj/empprize/' + id,
    method: 'get'
  })
}

// 新增员工中奖中间
export function addEmpprize(data) {
  return request({
    url: '/cj/empprize',
    method: 'post',
    data: data
  })
}

// 修改员工中奖中间
export function updateEmpprize(data) {
  return request({
    url: '/cj/empprize',
    method: 'put',
    data: data
  })
}

// 删除员工中奖中间
export function delEmpprize(id) {
  return request({
    url: '/cj/empprize/' + id,
    method: 'delete'
  })
}
