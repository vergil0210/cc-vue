<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="Name"
        style="width: 200px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.credit"
        placeholder="Credit"
        style="width: 100px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="listQuery.rate" placeholder="Rate" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.status" placeholder="Status" clearable class="filter-item" style="width: 130px">
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
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
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
        v-waves
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
      <el-table-column label="课程号" prop="id" sortable align="center" width="180" fixed="">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="课程" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.name }}</span>
          <!--          <el-tag>{{ row.type | typeFilter }}</el-tag>-->
        </template>
      </el-table-column>
      <!--      ======================      -->
<!--      <el-table-column label="教师" width="100" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ scope.row.teacher }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <!--      ======================      -->
<!--      <el-table-column v-if="showHidden" label="Reviewer" width="100" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <span style="color:red;">{{ scope.row.reviewer }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column v-if="showHidden" label="学期" align="center">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="showHidden" label="学时" align="center">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.amountTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考核类型" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.accessType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="课程类型" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.courseType }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学分" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.credit }}</span>
        </template>
      </el-table-column>
<!--      &lt;!&ndash; todo 只读分数展示允许半星&ndash;&gt;-->
<!--      <el-table-column label="评分" width="100">-->
<!--        <template slot-scope="scope">-->
<!--          <svg-icon v-for="n in scope.row.rate" :key="n" icon-class="star" class="meta-item__icon" />-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="最大人数" align="center" width="100">
        <template slot-scope="{row}">
          <span
            v-if="row.maxUser"
            class="link-type"
          >{{ row.maxUser }}</span>
          <span v-else>0</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="Date" width="110" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-dropdown v-if="scope.row.time">-->
<!--            <span class="el-dropdown-link">-->
<!--              学时：{{ scope.row.time.length }}<i class="el-icon-arrow-down el-icon&#45;&#45;right" />-->
<!--            </span>-->
<!--            &lt;!&ndash;            <el-dropdown-menu&ndash;&gt;-->
<!--            &lt;!&ndash;              slot="dropdown"&ndash;&gt;-->
<!--            &lt;!&ndash;            >&ndash;&gt;-->
<!--            &lt;!&ndash;              <el-dropdown-item&ndash;&gt;-->
<!--            &lt;!&ndash;                v-for="t of scope.row.time"&ndash;&gt;-->
<!--            &lt;!&ndash;                dis    abled&ndash;&gt;-->
<!--&lt;!&ndash;              >{{ t.weeks }}周-星期{{ t.week }}-{{ t.lesson }}</el-dropdown-item>&ndash;&gt;-->
<!--&lt;!&ndash;            </el-dropdown-menu>&ndash;&gt;-->
<!--          </el-dropdown>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="状态" class-name="status-col" width="110">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" width="240">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            Edit
          </el-button>
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
            Delete
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
    <!--    ===========================添加课程===========================    -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="90px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="课程号" prop="id">
          <el-input v-if="dialogStatus!=='create'" v-model.number="temp.id" :disabled="true" />
          <el-input v-else v-model.number="temp.id" />
        </el-form-item>
        <el-form-item label="课程名" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model.number="temp.credit">
            <template slot="append">分</template>
          </el-input>
        </el-form-item>
        <el-form-item label="学时" prop="amountTime">
          <el-input v-model.number="temp.amountTime">
            <template slot="append">时</template>
          </el-input>
        </el-form-item>
<!--        <el-form-item label="教师" prop="teacher">-->
<!--          <el-input v-model="temp.teacher" />-->
<!--        </el-form-item>-->
        <el-form-item label="最大人数" prop="maxUser">
          <el-input v-model.number="temp.maxUser">
            <template slot="append">人</template>
          </el-input>
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-select v-model="temp.semester" class="filter-item" placeholder="Please select" style="width: 310px">
            <el-option v-for="item in semesterOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="考核类型" prop="accessType">
          <el-select v-model="temp.accessType" class="filter-item" placeholder="Please select" style="width: 310px">
            <el-option v-for="item in accessTypeOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程类型" prop="courseType">
          <el-select v-model="temp.courseType" class="filter-item" placeholder="Please select" style="width: 310px">
            <el-option v-for="item in courseTypeOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status" class="filter-item" placeholder="Please select" style="width: 310px">
            <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
<!--        <el-form-item label="评分" prop="rate">-->
<!--          <el-rate-->
<!--            v-model="temp.rate"-->
<!--            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"-->
<!--            :max="5"-->
<!--            style="margin-top:8px;"-->
<!--          />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
    <el-button @click="axoisTest()">测试</el-button>
    <el-button @click="axoisTest2()">测试</el-button>
  </div>
</template>

<script>
import { fetchList, fetchPv, fetchAllList, createCourse, courseTest, deleteOne, changeOpt } from '@/api/course'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]
// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})
export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        '可选': 'success',
        '不可选': 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  data() {
    return {
      tableKey: 0,
      // 储存table中的展示数据
      list: null,
      alllist: null,
      total: 0,
      listLoading: true,
      // 查询条件
      listQuery: {
        page: 1,
        limit: 10,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3, 4, 5],
      calendarTypeOptions,
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
      fetchList(this.listQuery).then(response => {
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
      changeOpt(row).then(resp => {
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
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
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
      deleteOne(row).then(resp => {
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
