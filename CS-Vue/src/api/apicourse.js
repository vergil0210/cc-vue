import request from '@/utils/request'
import { req } from '@/utils/request'

export function getCourseList(query) {
  return request({
    url: '/api/course/list',
    method: 'get',
    params: query
  })
}

export function getChosenList(params) {
  return req({
    url: '/student/course/getChosenList',
    method: 'post',
    params
  })
}

export function addMock(params) {
  return request({
    url: '/api/course/addMock',
    method: 'post',
    params
  })
}

export function choseCourse(data) {
  return req({
    url: '/student/course/putAll',
    method: 'put',
    data
  })
}
