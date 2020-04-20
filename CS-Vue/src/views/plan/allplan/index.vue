<template>
  <div style="">
    <div v-show="show">
      <el-select v-model="listQuery.grade" placeholder="年级" clearable class="filter-item">
        <el-option v-for="item in grades" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.college" placeholder="学院" clearable class="filter-item">
        <el-option v-for="item in colleges" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.profession" placeholder="专业" clearable class="filter-item">
        <el-option
          v-for="item in professions"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleAdd"
      >
        Add
      </el-button>
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
      <el-table-column
        type="index"
        align="center"
        fixed
      />
      <el-table-column label="ID" prop="id" sortable align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.cid }}</span>
        </template>
      </el-table-column>

      <el-table-column label="课程" min-width="150px">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学期" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学分" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.credit }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考核方式" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.evaluation }}</span>
        </template>
      </el-table-column>
      <el-table-column label="课程性质" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="建议修读学期" class-name="status-col" width="110">
        <template slot-scope="{row}">
          <span>{{ row.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column label="起始结束周" class-name="status-col" width="110">
        <template slot-scope="{row}">
          <span>{{ row.week }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" min-width="180px">
        <template slot-scope="{row}">
          <el-button size="mini" type="success" @click="handleUpdate(row)">
            Edit
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
      @pagination="getPlanList"
    />

    <el-dialog
      v-loading="searchLoading"
      title="课程增加"
      :visible.sync="searchShow"
    >

      <el-form
        ref="dataForm"
        :rules="rules"
        :model="addForm"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="课程">
          <el-autocomplete
            v-model="state"
            :fetch-suggestions="querySearchAsync"
            placeholder="请输入课程名或ID"
            :trigger-on-focus="false"
            class="filter-item"
            style="width: 80%;"
            clearable
            @select="handleSelect"
          >
            <template slot-scope="{ item }">
              <span class="addr">{{ item.cid }}</span>
              <div class="name">{{ item.name }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <div v-show="inputShow">
          <el-form-item label="年级" prop="grade">
            <el-input v-model="addForm.grade" />
          </el-form-item>
          <el-form-item label="学期" prop="semester">
            <el-input v-model="addForm.semester" />
          </el-form-item>
          <el-form-item label="学院" prop="college">
            <el-input v-model="addForm.college" />
          </el-form-item>
          <el-form-item label="专业" prop="profession">
            <el-input v-model="addForm.profession" />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="addPlan()">
          Confirm
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="ID" prop="cid">
          <el-input v-model="temp.cid" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input v-model="temp.credit" />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="temp.semester" />
        </el-form-item>
        <el-form-item label="考核方式" prop="evaluation">
          <el-select v-model="temp.evaluation" clearable>
            <el-option
              v-for="item in evaluations"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="课程性质" prop="type">
          <el-input v-model="temp.type" />
        </el-form-item>
        <el-form-item label="起始周" prop="week">
          <el-input v-model="temp.week" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Confirm
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
  </div>

</template>

<script>
import { fetchList, fetchPv, fetchAllList, createCourse, updateCourse } from '@/api/course'

import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { getPlanList, addPlan } from '@/api/plan'
import { getCourseList } from '@/api/apicourse'

export default {
  name: 'Allplan',
  components: {
    Pagination
  },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        Optional: 'success',
        NotOptional: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      filterText: '',
      show: true,
      tableKey: 0,
      list: null,
      alllist: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        college: undefined,
        grade: undefined,
        profession: undefined,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3, 4, 5],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['Optional', 'NotOptional'],
      showHidden: false,
      showSemester: false,
      rules: {
        cid: [{ required: true, message: 'ID is required', trigger: 'blur' }]
      },

      temp: {
        id: '',
        name: '',
        credit: '',
        teacher: '',
        stu_number: '',
        semester: '',
        status: '',
        rate: 3
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      evaluations: ['考察', '考核'],
      dialogPvVisible: false,
      pvData: [],

      grades: null,
      colleges: null,
      professions: null,

      addForm: {
        course: undefined,
        semester: undefined,
        grade: undefined,
        college: undefined,
        profession: undefined
      },
      searchLoading: false,
      searchShow: false,
      search: {
        id: null,
        name: null
      },
      courseList: null,
      state: '',
      addCourse: null,
      inputShow: false
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    // this.getList()
    this.getPlanList()
  },
  methods: {
    getPlanList() {
      this.listLoading = true
      getPlanList(this.listQuery).then(response => {
        const data = response.data
        this.list = data.items
        this.total = data.total
        this.grades = data.grades
        this.colleges = data.colleges
        this.professions = data.professions

        this.listLoading = false
      }).catch(function(err) {
        console.log(err)
      })
    },
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        const items = response.data.items
        for (const i in items) {
          // items[i].time = this.timeSort(items[i].time)
          items[i].time = this.sortByWeeks(items[i].time)
        }
        // console.log(items)
        this.list = items
        this.total = response.data.total
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
      fetchAllList().then(response => {
        this.alllist = response.items
      })
    },
    getCourseList() {
      getCourseList().then(response => {
        this.courseList = response.data.items
        this.searchLoading = false
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.toString().indexOf(value) !== -1
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
      this.getPlanList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
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
    handleAdd() {
      // this.searchLoading = true
      this.getCourseList()
      this.searchShow = true
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
          createCourse(this.temp).then(() => {
            this.list.unshift(this.temp)
            console.log(this.list)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      this.$notify({
        title: 'Success',
        message: 'Delete Successfully',
        type: 'success',
        duration: 2000
      })
      const index = this.list.indexOf(row)
      this.list.splice(index, 1)
    },
    resetTemp() {
      this.temp = {
        name: '',
        credit: '',
        teacher: '',
        stu_number: '',
        semester: '',
        status: '',
        rate: 3
      }
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
          updateCourse(tempData).then(() => {
            for (const v of this.list) {
              if (v.id === this.temp.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.temp)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    querySearchAsync(queryString, cb) {
      var courseList = this.courseList
      var results = queryString ? courseList.filter(item => {
        return ((item.name && item.name.indexOf(queryString) >= 0) || (item.cid && item.cid.indexOf(queryString) >= 0))
      }) : courseList
      cb(results)
    },
    handleSelect(item) {
      console.log(item)
      this.state = item.cid + '-' + item.name
      this.addCourse = item
      this.addForm.course = item.cid
      this.inputShow = true
    },
    addPlan() {
      addPlan(this.addForm).then(response => {
        if (parseInt(response.code) === 20000) {
          this.$message({
            message: response.message,
            type: 'success'
          })
        } else {
          this.$message.error(response.message)
        }
      }).catch(function(err) {
        this.$message.error(err)
      })
      this.searchShow = false
    }
  }

}
</script>

<style scoped>
  .name {
    text-overflow: ellipsis;
    overflow: hidden;
  }
  .addr {
    font-size: 12px;
    color: #b4b4b4;
  }
</style>
