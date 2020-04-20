import request from '@/utils/request'
import { req } from '@/utils/request'

export function fetchList(query) {
  return req({
    url: '/admin/course/list',
    method: 'get',
    params: query
  })
}

export function fetchAllList() {
  return req({
    url: '/admin/course/findALl',
    method: 'get'
  })
}

export function fetchCourse(id) {
  return request({
    url: '/course/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchCourseChose(id) {
  return req({
    url: '/student/course/list',
    method: 'get',
    params: id
  })
}

export function fetchPv(pv) {
  return request({
    url: '/course/pv',
    method: 'get',
    params: { pv }
  })
}

export function createCourse(data) {
  return req({
    url: '/admin/course/create',
    method: 'post',
    data
  })
}

export function createCourseChoice(data) {
  return req({
    url: '/admin/cc/create',
    method: 'post',
    data
  })
}

export function fetchCcList(query) {
  return req({
    url: '/admin/cc/list',
    method: 'get',
    params: query
  })
}

export function updateCourse(data) {
  return request({
    url: '/course/update',
    method: 'post',
    data
  })
}

export function fetchRecList(query) {
  return request({
    url: '/course/recList',
    method: 'get',
    params: query
  })
}

export function deleteOne(data) {
  return req({
    url: '/admin/course/deleteById',
    method: 'delete',
    data
  })
}
export function ccDeleteOne(data) {
  return req({
    url: '/admin/cc/deleteOne',
    method: 'delete',
    data
  })
}

export function changeOpt(data) {
  return req({
    url: '/admin/course/changeOpt',
    method: 'put',
    data
  })
}

export function ccChangeOpt(data) {
  return req({
    url: '/admin/cc/changeOpt',
    method: 'put',
    data
  })
}

export function courseTest() {
  return req({
    url: 'constant/test',
    method: 'get'
  })
}

export function courseTest1() {
  return req({
    url: 'constant/test1',
    method: 'get'
  })
}

