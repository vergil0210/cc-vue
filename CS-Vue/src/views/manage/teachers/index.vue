<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        placeholder="姓名"
        style="width: 150px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-select
        v-model="listQuery.gender"
        placeholder="性别"
        style="width: 150px;"
        class="filter-item"
        clearable>
        <el-option v-for="item in genderOptions" :key="item" :label="item" :value="item"></el-option>
      </el-select>
      <el-select
        v-model="listQuery.title"
        placeholder="职称"
        style="width: 150px;"
        class="filter-item"
        clearable>
        <el-option v-for="item in titleOptions" :key="item" :label="item" :value="item"></el-option>
      </el-select>
<!--      <el-input-->
<!--        v-model="listQuery.title"-->
<!--        placeholder="学院"-->
<!--        style="width: 150px"-->
<!--        class="filter-item"-->
<!--        clearable-->
<!--        @keyup.enter.native="handleFilter"-->
<!--      />-->
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >
        Add
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
<!--      <el-checkbox v-model="showHidden" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">-->
<!--        Show Hidden Column-->
<!--      </el-checkbox>-->
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
      <el-table-column label="教工号" prop="id" min-width="150px" sortable>
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          <span>{{ scope.row.gender }}</span>
        </template>
      </el-table-column>
      <el-table-column  label="职称" align="center">
        <template slot-scope="scope">
          <span style="color:red;">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="学院" align="center">-->
<!--        <template slot-scope="{row}">-->
<!--          <span>{{ row.college }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" min-width="160">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            Edit
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
<!--        <el-form-item v-show="dialogStatus==='create'" label="ID" prop="id">-->
<!--          <el-input v-model="temp.id" />-->
<!--        </el-form-item>-->
        <el-form-item label="教工号" prop="id">
          <el-input v-model.number="temp.id" clearable :disabled="dialogStatus!=='create'"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="temp.name" clearable/>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="temp.gender" placeholder="请选择" clearable style="width: 330px;">
            <el-option
              v-for="(item,index) in genderOptions"
              :key="index"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title" >
          <el-select v-model="temp.title" placeholder="请选择" clearable style="width: 330px;">
            <el-option
              v-for="(item,index) in titleOptions"
              :key="index"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="temp.email" clearable/>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="temp.phone" clearable/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false" plain>
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()" plain>
          保存
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
import { fetchList, fetchPv, createTeacher, deleteOne } from '@/api/teacher'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ComplexTable',
  components: { Pagination },
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
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        sort: '+id',
        gender: '',
        name: '',
        title: '',
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['Optional', 'NotOptional'],
      genderOptions: ['男', '女'],
      titleOptions: ['助教', '讲师', '副教授', '教授', '其他'],
      showHidden: false,
      showSemester: false,
      temp: {
        name: '',
        id: '',
        email: '',
        gender: '',
        phone: '',
        college: '',
        title: ''
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
          { pattern: /^\d{6,20}$/, message: '长度在 6 到 20 个字符', trigger: 'blur' },
          { type: 'number', message: '必须为数字值' }
        ],
        name: [{ required: true, message: '请填写该选项', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择该选项', trigger: 'change' }],
        title: [{ required: true, message: '请选择该选项', trigger: 'change' }],
        email: [{ required: true, message: '请填写该选项', trigger: 'blur' }],
        phone: [{ required: true, message: '请填写该选项', trigger: 'blur' }]
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
        this.list = response.data.list
        this.total = response.data.list.length
        // console.log(this.list)

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
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
          createTeacher(this.temp).then((resp) => {
            if (resp.data.msg) {
              this.$notify.error({
                title: 'failed',
                message: 'Created failed',
                duration: 2000
              })
              return false
            } else {
              this.list.unshift(this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: 'Success',
                message: 'Created Successfully',
                type: 'success',
                duration: 2000
              })
            }
          })
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
          createTeacher(tempData).then((resp) => {
            if (resp.data.msg) {
              this.$notify.error({
                title: 'error',
                message: resp.data.msg,
                duration: 2000
              })
            } else {
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
            }
          })
        }
      })
    },
    handleDelete(row) {
      console.log(row)
      // const tempData = Object.assign({}, row)
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
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
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
    }
  }
}
</script>
