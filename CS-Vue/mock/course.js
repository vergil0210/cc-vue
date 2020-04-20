import Mock from 'mockjs'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    'id': '@integer(10000,99999)',
    'name': '@ctitle',
    'period': '@integer(10,20)',
    'credit': '@integer(1,8)',
    'semester': '@integer(1,10)',
    'teacher': '@cname',
    'stu_number': '@integer(40,120)',
    'time|1-16': [{
      'date': '@date(yyyy-MM-dd)',
      'weeks': '@integer(1,20)',
      'week': '@integer(1,7)',
      'lesson': '@integer(1,5)',
      'room': '@natural'
    }],
    'college': '@title',
    'rate': '@integer(1,5)',
    'reviewer': '@first',
    'status|1': ['Optional', 'NotOptional']
  }))
}
export default [{
  url: '/course/list',
  type: 'get',
  response: config => {
    const { rate, status, name, page = 1, limit = 10, sort, credit, remain } = config.query

    // 处理过滤查找
    let mockList = List.filter(item => {
      if (rate && item.rate !== +rate) return false
      if (status && item.status !== status) return false
      if (credit && item.credit !== +credit) return false
      if (remain && item.stu_number === 0) return false
      if (name && item.name.indexOf(name) < 0) return false
      return true
    })

    if (sort === '-id') {
      mockList = mockList.reverse()
    }

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

    return {
      code: 20000,
      data: {
        total: mockList.length,
        items: pageList
      }
    }
  }
},
{
  url: '/course/recList',
  type: 'get',
  response: config => {
    const { rate, status, name, page = 1, limit = 3, sort, credit, remain } = config.query

    // 处理过滤查找
    let mockList = List.filter(item => {
      if (rate && item.rate !== +rate) return false
      if (status && item.status !== status) return false
      if (credit && item.credit !== +credit) return false
      if (remain && item.stu_number === 0) return false
      if (name && item.name.indexOf(name) < 0) return false
      return true
    })

    if (sort === '-id') {
      mockList = mockList.reverse()
    }

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

    return {
      code: 20000,
      data: {
        total: mockList.length,
        items: pageList
      }
    }
  }
},
{
  url: '/course/allList',
  type: 'get',
  response: config => {
    return {
      code: 20000,
      items: List
    }
  }
},
{
  url: '/course/detail',
  type: 'get',
  response: config => {
    const { id } = config.query
    for (const course of List) {
      if (course.id === +id) {
        return {
          code: 20000,
          data: course
        }
      }
    }
  }
}, {
  url: '/article/pv',
  type: 'get',
  response: _ => {
    return {
      code: 20000,
      data: {
        pvData: [
          { key: 'PC', pv: 1024 },
          { key: 'mobile', pv: 1024 },
          { key: 'ios', pv: 1024 },
          { key: 'android', pv: 1024 }
        ]
      }
    }
  }
}, {
  url: '/course/create',
  type: 'post',
  response: _ => {
    return {
      code: 20000,
      data: 'success'
    }
  }
}, {
  url: '/course/update',
  type: 'post',
  response: _ => {
    return {
      code: 20000,
      data: 'success'
    }
  }
}]
