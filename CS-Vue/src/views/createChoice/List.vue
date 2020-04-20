<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.coName"
        placeholder="课程名"
        style="width: 100px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.teaName"
        placeholder="教师名"
        style="width: 100px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="listQuery.college" placeholder="专业" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in collegeOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.semester" placeholder="学期" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in semesterOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.status" placeholder="状态" clearable class="filter-item" style="width: 130px">
        <el-option
          v-for="item in statusOptions"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>
      <!--            <el-select v-model="listQuery.sort" style="width: 150px" class="filter-item" @change="handleFilter">-->
      <!--              <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />-->
      <!--            </el-select>-->
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >
        添加
      </el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >
        Export
      </el-button>
      <el-checkbox v-model="showHidden" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">
        Show Hidden Column
      </el-checkbox>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column  v-if="showHidden" label="课程号" sortable align="center" width="180">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.coId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="课程" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.coName }}</span>
        </template>
      </el-table-column>
      <el-table-column  v-if="showHidden" label="教工号" sortable align="center" width="180">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.teaId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="教师" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.teaName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="专业" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.college }}</span>
        </template>
      </el-table-column>
      <el-table-column  v-if="showHidden" label="开始时间"  sortable align="center" width="180">
        <template slot-scope="scope">
          <span style="color:red;">第{{ scope.row.startTime }}周</span>
        </template>
      </el-table-column>
      <el-table-column  v-if="showHidden" label="结束时间" prop="id" sortable align="center" width="180">
        <template slot-scope="scope">
          <span style="color:red;">第{{ scope.row.endTime }}周</span>
        </template>
      </el-table-column>
      <!--      ======================      -->
      <!--      <el-table-column v-if="showHidden" label="Reviewer" width="100" align="center">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span style="color:red;">{{ scope.row.reviewer }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="学期" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.semester }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="考核类型" width="100" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ scope.row.accessType }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="课程类型" width="100" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ scope.row.courseType }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="学分" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.credit }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最大人数" align="center" width="100">
        <template slot-scope="{row}">
          <span v-if="row.maxUser">{{ row.maxUser }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <el-table-column label="当前人数" align="center" width="100">
        <template slot-scope="{row}">
          <span v-if="row.currentUser">{{ row.currentUser }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- todo 只读分数展示允许半星-->
      <el-table-column label="评分" width="100">
        <template slot-scope="scope">
          <svg-icon v-for="n in scope.row.rate" :key="n" icon-class="star" class="meta-item__icon" />
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="110">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" width="240">
        <template slot-scope="{row}">
          <el-button
            v-if="row.status!='可选'"
            size="mini"
            type="success"
            @click="handleModifyStatus(row,'可选')"
          >
            可选
          </el-button>
          <el-button v-if="row.status!='不可选'" size="mini" @click="handleModifyStatus(row,'不可选')">
            不可选
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { ccChangeOpt, courseTest, createCourse, ccDeleteOne, fetchAllList, fetchPv, fetchCcList } from '@/api/course'
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'

export default {
  name: 'ListVue',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        '可选': 'success',
        '不可选': 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tableKey: 0,
      // 储存table中的展示数据
      list: {
        coId: undefined,
        coName: '',
        teaId: undefined,
        teaName: '',
        startTime: undefined,
        endTime: undefined,
        semester: '',
        college: '',
        credit: undefined,
        maxUser: undefined,
        currentUser: undefined,
        rate: undefined,
        status: ''
      },
      alllist: null,
      total: 0,
      listLoading: true,
      // 查询条件
      listQuery: {
        page: 1,
        limit: 10,
        semester: '',
        status: '',
        coName: '',
        teaName: ''
      },
      collegeOptions: ['软件工程', '软件+道铁', '软件+桥梁', '软件+交运', '软件+轨道', '软件+电气'],
      importanceOptions: [1, 2, 3, 4, 5],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['可选', '不可选'],
      accessTypeOptions: ['考试', '考察'],
      courseTypeOptions: ['必修', '选修'],
      semesterOptions: ['大一-上', '大一-下', '大二-上', '大二-下', '大三-上', '大三-下', '大四-上', '大四-下'],
      showHidden: false,
      showSemester: false,
      temp: {
        id: '',
        name: '',
        credit: '',
        teacher: '',
        maxUser: '',
        semester: '',
        amountTime: undefined,
        status: '',
        rate: 3,
        accessType: '',
        courseType: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        id: [
          { required: true, message: '请输入id', trigger: 'blur' },
          { pattern: /^\d{6,10}$/, message: '长度在 6 到 10 个字符', trigger: 'blur' },
          { type: 'number', message: '必须为数字值' }
        ],
        name: [{ required: true, message: '请输入课程名', trigger: 'blur' }],
        credit: [
          { required: true, message: '请输入该课程学分', trigger: 'blur' },
          { type: 'number', message: '必须为数字值' }
        ],
        maxUser: [
          { required: true, message: '请输入课程最大人数', trigger: 'blur' },
          { type: 'number', message: '必须为数字值' }
        ],
        semester: [{ required: true, message: '请输入课程所属学期', trigger: 'change' }],
        status: [{ required: true, message: '请确认该课程是否可选', trigger: 'change' }],
        accessType: [{ required: true, message: '请选择该课程考核类型', trigger: 'change' }],
        courseType: [{ required: true, message: '请确认该课程课程类型', trigger: 'change' }],
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }],
        amountTime: [
          { required: true, message: '请输入学时', trigger: 'blur' },
          { type: 'number', message: '必须为数字值' }
        ]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchCcList(this.listQuery).then(response => {
        console.log(response)
        const items = response.data.list
        // for (const i in items) {
        //   // items[i].time = this.timeSort(items[i].time)
        //   items[i].time = this.sortByWeeks(items[i].time)
        //   // addMock(items[i])
        // }
        // console.log(items)
        this.list = items
        this.total = response.data.list.length
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
      fetchAllList().then(response => {
        this.alllist = response.items
      })
    },
    timeSort(list) {
      // console.log(list)
      return list.slice().sort((a, b) => {
        return Date.parse(a.date) - Date.parse(b.date)
      })
    },
    sortByWeeks(list) {
      return list.sort((a, b) => {
        if (a.weeks < b.weeks) {
          return -1
        } else if (a.weeks === b.weeks) {
          if (a.week < b.week) {
            return -1
          } else {
            return 1
          }
        } else {
          return 1
        }
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      ccChangeOpt(row).then(resp => {
        if (resp.data.msg) {
          this.$message.error(resp.data.msg)
        } else {
          this.$message({
            message: '操作Success',
            type: 'success'
          })
          row.status = status
        }
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        name: '',
        credit: '',
        teacher: '',
        maxUser: '',
        semester: '',
        status: '',
        rate: 3
      }
    },
    handleCreate() {
      this.$router.push('/createChoice/index')
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createCourse(this.temp).then((response) => {
            if (response.data.msg) {
              this.$notify.error({
                title: 'error',
                message: response.data.msg,
                type: 'success',
                duration: 2000
              })
            } else {
              console.log(response)
              this.list.unshift(this.temp)
              console.log(this.list)
              this.$notify({
                title: 'Success',
                message: 'Created Successfully',
                type: 'success',
                duration: 2000
              })
            }
          })
          this.dialogFormVisible = false
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          createCourse(this.temp).then((response) => {
            if (response.data.msg) {
              this.$notify.error({
                title: 'error',
                message: response.data.msg,
                duration: 2000
              })
            } else {
              // 手动更新data中的数据
              for (const v of this.list) {
                if (v.id === this.temp.id) {
                  const index = this.list.indexOf(v)
                  this.list.splice(index, 1, this.temp)
                  break
                }
              }
              this.$notify({
                title: 'Success',
                message: 'update Successfully',
                type: 'success',
                duration: 2000
              })
            }
            this.dialogFormVisible = false
          })
        }
      })
    },
    handleDelete(row) {
      ccDeleteOne(row).then(resp => {
        if (resp.data.msg) {
          this.$notify.error({
            title: 'error',
            message: resp.data.msg,
            duration: 2000
          })
        } else {
          this.$notify({
            title: 'Success',
            message: 'Delete Successfully',
            type: 'success',
            duration: 2000
          })
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
        }
      })
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleDownload() {
      this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const header = ['id', 'name', 'teacher', 'reviewer', 'credit', 'rate', 'maxUser']
          const data = this.formatJson(header, this.alllist)
          excel.export_json_to_excel({
            header: header,
            data,
            filename: 'table-list'
          })
          this.downloadLoading = false
        })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    axoisTest() {
      // eslint-disable-next-line no-undef
      courseTest().then(response => {
        console.log(response)
      })
    },
    axoisTest2() {
      // eslint-disable-next-line no-undef
      courseTest1().then(response => {
        console.log(response)
      })
    }
  }
}
</script>

<style scoped>

</style>
