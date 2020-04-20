import request from '@/utils/request'

export function fetchPlanList(query) {
  return request({
    url: '/plan/list',
    method: 'get',
    params: query
  })
}

export function getPlanList(query) {
  return request({
    url: '/plan/getList',
    method: 'get',
    params: query
  })
}

export function addPlan(params) {
  return request({
    url: '/plan/add',
    method: 'post',
    params
  })
}
