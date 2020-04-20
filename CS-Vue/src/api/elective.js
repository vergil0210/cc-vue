import request from '@/utils/request'

export function fetchElectiveList(query) {
  return request({
    url: '/elective/list',
    method: 'get',
    params: query
  })
}

export function fetcheList(query) {
  return request({
    url: '/elective/elist',
    method: 'get',
    params: query
  })
}
