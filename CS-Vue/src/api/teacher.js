import request from '@/utils/request'
import { req } from '@/utils/request'

export function fetchList(query) {
  return req({
    url: 'admin/teacher/list',
    method: 'get',
    params: query
  })
}

export function fetchAll() {
  return req({
    url: 'admin/teacher/findAll',
    method: 'get'
  })
}

export function fetchDetail(id) {
  return request({
    url: '/teacher/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/teacher/pv',
    method: 'get',
    params: { pv }
  })
}

export function createTeacher(data) {
  return req({
    url: 'admin/teacher/create',
    method: 'post',
    data
  })
}

export function updateTeacher(data) {
  return request({
    url: '/teacher/update',
    method: 'post',
    data
  })
}

export function deleteOne(data) {
  return req({
    url: '/admin/teacher/deleteById',
    method: 'delete',
    data
  })
}

