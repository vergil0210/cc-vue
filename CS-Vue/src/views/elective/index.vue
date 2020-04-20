<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.nameid"
        placeholder="Name/ID"
        style="width: 400px;"
        class="filter-item"
        clearable
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="listQuery.semester" placeholder="Semester" clearable class="filter-item">
        <el-option v-for="item in 12" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.credit" placeholder="Credit" clearable class="filter-item">
        <el-option v-for="item in credits" :key="item" :label="item" :value="item" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
    </div>
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
      tooltip-effect="dark"
      @sort-change="sortChange"
    >
      <el-table-column align="center" sortable label="ID">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" align="center" label="课程">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column width="100" align="center" label="教师">
        <template slot-scope="scope">
          <span>{{ scope.row.teacher }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学期" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.semester }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评分" width="100">
        <template slot-scope="scope">
          <svg-icon v-for="n in +scope.row.rate" :key="n" icon-class="star" class="meta-item__icon" />
        </template>
      </el-table-column>
      <el-table-column label="本人评分" width="100">
        <template v-if="scope.row.irate>0" slot-scope="scope">
          <svg-icon v-for="n in +scope.row.irate" :key="n" icon-class="star" class="meta-item__icon" />
        </template>
      </el-table-column>
      <el-table-column label="学分" width="100" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.credit }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="上课时间" min-width="110">
        <template slot-scope="scope">
          <el-dropdown>
            <span class="el-dropdown-link">
              学时：{{ scope.row.period }}
              <i class="el-icon-arrow-down el-icon--right" />
            </span>
            <el-dropdown-menu
              slot="dropdown"
            >
              <el-dropdown-item
                v-for="t of scope.row.time"
                disabled
              >{{ t.weeks }}周-星期{{ t.week }}-{{ t.lesson }}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" @click="handleRate(row)">
            Rate
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

  </div>
</template>

<script>
import { fetchElectiveList } from '@/api/elective'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'Elective',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      list: null,
      total: 0,
      credits: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        nameid: undefined,
        credit: undefined,
        semester: undefined,
        sort: '+id'
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      irateHidden: false
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
        const items = response.data.items
        // console.log(items)
        for (const i in items) {
          items[i].time = this.sortByWeeks(items[i].time)
        }
        this.list = items
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
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
    handleRate(row) {
      console.log(row)
      this.$prompt('请输入评分', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPlaceholder: row.irate,
        inputValidator: (value) => {
          if (!isNaN(Number(value))) {
            if (value < 1 || value > 5) {
              return false
            }
          } else {
            return '非法数据，评分在1~5之间'
          }
        },
        inputErrorMessage: '评分在1~5之间'
      }
      ).then(({ value }) => {
        this.$message({
          type: 'success',
          message: '你的评分是: ' + value
        })
        for (const v of this.list) {
          if (v.id === row.id) {
            const index = this.list.indexOf(v)
            v.irate = value
            this.list.splice(index, 1, v)
            break
          }
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消评分'
        })
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
