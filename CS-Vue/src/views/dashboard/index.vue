<template>
  <el-table
    :data="tableData"
    border
    style="width: 100%"
    height="100%"
    row-style="height:100px"
  >
    <el-table-column
      prop="lesson"
      label="节\星期"
      width="100"
      fixed="left"
      align="center"
    />
    <el-table-column
      prop="Mon"
      label="一"
    />
    <el-table-column
      prop="Tues"
      label="二"
    />
    <el-table-column
      prop="Wed"
      label="三"
    />
    <el-table-column
      prop="Thur"
      label="四"
    />
    <el-table-column
      prop="Fri"
      label="五"
    />
    <el-table-column
      prop="Sat"
      label="六"
    />
    <el-table-column
      prop="Sun"
      label="日"
    />
  </el-table>
</template>

<script>
export default {
  name: 'Grade',
  data() {
    return {
      tableData: [{
        lesson: '1',
        Mon: '',
        Tues: '测试课程 A201',
        Wed: '',
        Thur: '',
        Fri: '测试课程 6-210',
        Sat: '',
        Sun: ''
      }, {
        lesson: '2',
        Mon: '测试课程 5-304',
        Tues: '',
        Wed: '',
        Thur: '',
        Fri: '',
        Sat: '',
        Sun: ''
      }, {
        lesson: '3',
        Mon: '',
        Tues: '',
        Wed: '测试课程 1-502',
        Thur: '',
        Fri: '',
        Sat: '',
        Sun: ''
      }, {
        lesson: '4',
        Mon: '',
        Tues: '',
        Wed: '',
        Thur: '',
        Fri: '',
        Sat: '测试课程 1-302',
        Sun: ''
      }, {
        lesson: '5',
        Mon: '',
        Tues: '',
        Wed: '测试课程 3-205',
        Thur: '',
        Fri: '',
        Sat: '',
        Sun: ''
      }]
    }
  }
}
</script>

<style scoped>

</style>
