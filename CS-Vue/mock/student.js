// import Mock from 'mockjs'
const Mock = require('mockjs')

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    'id': '@id',
    'stuid': '@id',
    'name': '@cname',
    'password': '@integer(10,20)',
    'email': '@email',
    'sex|1': ['男', '女'],
    'college': '@ctitle',
    'profession': '@ctitle',
    'class': '@natural',
    'phone': '@natural',
    'regTime': '@date(yyyy-MM-dd)',
    'lastTime': '@date(yyyy-MM-dd)'
  }))
}

export default [{
  url: '/student/profile',
  type: 'get',
  response: config => {
    return {
      code: 20000,
      data: {
        profile: List[0]
      }
    }
  }
}, {
  url: '/student/updateInfo',
  type: 'post',
  response: config => {
    return {
      code: 20000,
      data: {
        message: '更新成功'
      }
    }
  }
}, {
  url: '/student/list',
  type: 'get',
  response: config => {
    const { page = 1, limit = 10, search } = config.query
    // console.log(config.query)
    if (search) {
      var mockList = List.filter(item => {
        if (item.name.indexOf(search) >= 0 || item.stuid.toString().indexOf(search) >= 0 || item.college.indexOf(search) >= 0 || item.class.toString().indexOf(search) >= 0 || item.profession.indexOf(search) >= 0) {
          return true
        }
      })
      const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

      return {
        code: 20000,
        data: {
          total: mockList.length,
          items: pageList
        }
      }
    } else {
      mockList = List
      const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

      return {
        code: 20000,
        data: {
          total: List.length,
          items: pageList
        }
      }
    }
  }
}, {
  url: '/student/detail',
  type: 'get',
  response: config => {
    const { id } = config.query
    for (const stu of List) {
      if (stu.id === +id) {
        return {
          code: 20000,
          data: stu

        }
      }
    }
  }
}, {
  url: '/student/create',
  type: 'post',
  response: _ => {
    return {
      code: 20000,
      data: 'success'
    }
  }
}, {
  url: '/student/update',
  type: 'post',
  response: _ => {
    return {
      code: 20000,
      data: 'success'
    }
  }
}, {
  url: '/student/pv',
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
}]
