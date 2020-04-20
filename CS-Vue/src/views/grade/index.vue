<template>
  <div class="app-container">
    <el-select v-model="listQuery.semester" clearable placeholder="请选择" style="margin-bottom: 2%">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      >
        <span style="float: left">{{ item.label }}</span>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
      </el-option>
    </el-select>
    <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
      Search
    </el-button>
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
      tooltip-effect="dark"
    >
      <el-table-column align="center" label="ID">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" align="center" label="课程">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="教师">
        <template slot-scope="scope">
          <span>{{ scope.row.teacher }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学期" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column label="成绩" align="center">
        <template slot-scope="scope">
          <span
            v-if="scope.row.grade>=60"
          >
            <el-tag type="success">{{ scope.row.grade }}</el-tag>
          </span>
          <span
            v-else
          >
            <el-tag type="danger">{{ scope.row.grade }}</el-tag>
          </span>
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

  </div>
</template>

<script>
import { fetchElectiveList } from '@/api/elective'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Grade',
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
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        rate: undefined,
        name: undefined,
        type: undefined,
        sort: '+id',
        semester: undefined
      },
      count: 0,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      multipleSelection: [],
      value: undefined,
      options: [{
        value: '1',
        label: '大一上'
      }, {
        value: '2',
        label: '大一下'
      }, {
        value: '3',
        label: '大二上'
      }, {
        value: '4',
        label: '大二下'
      }, {
        value: '5',
        label: '大三上'
      }, {
        value: '6',
        label: '大三下'
      }, {
        value: '7',
        label: '大四上'
      }, {
        value: '8',
        label: '大四下'
      }]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取课程信息
    getList() {
      this.listLoading = true
      fetchElectiveList(this.listQuery).then(response => {
        this.list = response.data.items
        console.log(this.list)
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }
  }
}
</script>

<style scoped>
  .edit-input {
    padding-right: 100px;
  }

  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 10px;
  }
</style>
