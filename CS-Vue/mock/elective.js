import Mock from 'mockjs'

const List = []
const count = 20
const eList = []
const ecount = 20

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    'id': '@id',
    'name': '@ctitle',
    'teacher': '@cname',
    'semester': '@integer(1,10)',
    'grade': '@integer(1,100)',
    'rate': '@integer(1,5)',
    'credit': '@integer(1,12)',
    'time|1-16': [{
      'date': '@date(yyyy-MM-dd)',
      'weeks': '@integer(1,20)',
      'week': '@integer(1,7)',
      'lesson': '@integer(1,5)'
    }],
    // 'irate': '@integer(1,5)'
    'irate': '@integer(0,5)',
    'number': '@integer(1,120)'
  }))
}

for (let i = 0; i < ecount; i++) {
  eList.push(Mock.mock({
    'id': '@id',
    'cid': 'id',
    'stuid': '@id',
    'rate': 'integer(1,5)',
    'usual': '@integer(1,30)',
    'testscore': '@integer(1,70)'
  }))
}

export default [{
  url: '/elective/list',
  type: 'get',
  response: config => {
    const { nameid, page = 1, limit = 10, sort, semester, credit } = config.query
    // 处理过滤查找
    let mockList = List.filter(item => {
      if (credit && item.credit.toString() !== credit.toString()) return false
      if (semester && item.semester.toString() !== semester.toString()) return false
      if (nameid && item.name.indexOf(nameid) < 0 && item.id.toString().indexOf(nameid) < 0) return false
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
  url: '/elective/elist',
  type: 'get',
  response: config => {
    const { sort, cid } = config.query

    // todo 按照学号排序
    // 查找对应课程
    // let mockList = eList.filter(item => {
    //   if (cid && item.cid.toString() !== cid.toString()) return false
    //   return true
    // })
    //
    // if (sort === '-id') {
    //   mockList = mockList.reverse()
    // }

    return {
      code: 20000,
      data: {
        total: eList.length,
        items: eList
      }
    }
  }
}]

