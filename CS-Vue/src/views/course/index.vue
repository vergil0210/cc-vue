<template>
  <div class="app-container">
    <div style="float:left;padding-right: 5%">
      <span>当前选择科目：<el-tag>{{ count }}</el-tag>  总学分：<el-tag type="success">{{ creditSum }}</el-tag> </span>
      <el-button type="primary" icon="el-icon-check" style="margin: 0 25px" @click="open()">提交</el-button>
    </div>
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
      <el-select v-model="listQuery.remain" placeholder="remain" clearable class="filter-item" style="width: 130px">
        <el-option
          v-for="item in statusOptions"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>
      <el-button v-if="activeName==='2'" v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
      <el-button v-else v-waves disabled class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Search
      </el-button>
    </div>

    <el-divider />
    <el-table
      ref="Table"
      v-loading="listLoading"
      :data="recList"
      border
      fit
      highlight-current-row
      style="width: 100%"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange"
      @sort-change="sortChange"
    >
      <el-table-column type="expand">
        <template slot-scope="scoped">
          <el-table
            :data="scoped.row.childModels"
            border
            highlight-current-row
            style="width: 100%;">
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
            <el-table-column label="评分" width="100">
              <template slot-scope="scope">
                <svg-icon v-for="n in scope.row.rate" :key="n" icon-class="star" class="meta-item__icon" />
              </template>
            </el-table-column>
            <el-table-column label="最大人数" align="center" width="100">
              <template slot-scope="{row}">
                <span v-if="row.maxUser">{{ row.maxUser }}</span>
                <span v-else>0</span>
              </template>
            </el-table-column>
            <el-table-column class-name="status-col" align="center" label="剩余可选" width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.maxUser - scope.row.currentUser }}</span>
              </template>
            </el-table-column>
            <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" min-width="160">
              <template slot-scope="{row}">
                <el-button
                  size="mini"
                  type="success"
                  @click="handleModifyStatus(row,scoped.row)"
                  :disabled="scoped.row.status === '已选'"
                >
                  选择
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column
        type="selection"
        width="55"
        align="center"
      />
      <el-table-column align="center" sortable label="ID" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" align="center" label="课程">
        <template slot-scope="scope">
          <span>{{ scope.row.coName }}</span>
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
      <el-table-column width="100" align="center" label="学时">
        <template slot-scope="scope">
          <span>{{ scope.row.amountTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="110">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            {{ row.status==="已选" ? "已选" : "待选" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width" min-width="160">
        <template slot-scope="{row}">
          <el-button
            size="mini"
            type="danger"
            @click="returnCourse(row)"
            :disabled="row.status === '待选'"
          >
            退选
          </el-button>
        </template>
      </el-table-column>
      <!--          <el-table-column align="center" label="上课时间" min-width="110">-->
      <!--            <template slot-scope="scope">-->
      <!--              <el-dropdown>-->
      <!--                <span class="el-dropdown-link">-->
      <!--                  学时：{{ scope.row.period }}<i class="el-icon-arrow-down el-icon&#45;&#45;right" />-->
      <!--                </span>-->
      <!--                <el-dropdown-menu-->
      <!--                  slot="dropdown"-->
      <!--                >-->
      <!--                  <el-dropdown-item-->
      <!--                    v-for="t of scope.row.time"-->
      <!--                    disabled-->
      <!--                  >{{ t.weeks }}周-星期{{ t.week }}-{{ t.lesson }}</el-dropdown-item>-->
      <!--                </el-dropdown-menu>-->
      <!--              </el-dropdown>-->
      <!--            </template>-->
      <!--          </el-table-column>-->
    </el-table>
<!--    <el-collapse v-model="activeName" accordion>-->
<!--      <el-collapse-item name="1">-->
<!--        <template slot="title">-->
<!--          <h3>课程推荐 Course recommendation</h3>&nbsp;(点击展开)<i class="header-icon el-icon-info" />-->
<!--        </template>-->
<!--      </el-collapse-item>-->
<!--      <el-collapse-item name="2">-->
<!--        <template slot="title">-->
<!--          <h3>课程选择 Course selection</h3>&nbsp;(点击展开)<i class="header-icon el-icon-info" />-->
<!--        </template>-->
<!--        <el-table-->
<!--          ref="multipleTable"-->
<!--          v-loading="listLoading"-->
<!--          :data="list"-->
<!--          border-->
<!--          fit-->
<!--          highlight-current-row-->
<!--          style="width: 100%"-->
<!--          tooltip-effect="dark"-->
<!--          @selection-change="handleSelectionChange"-->
<!--          @sort-change="sortChange"-->
<!--        >-->
<!--          <el-table-column-->
<!--            type="selection"-->
<!--            width="55"-->
<!--            align="center"-->
<!--          />-->
<!--          <el-table-column align="center" sortable label="ID" width="80">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.id }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->

<!--          <el-table-column min-width="150px" align="center" label="课程">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.name }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->

<!--          <el-table-column width="100" align="center" label="教师">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.teacher }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="学期" align="center">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.semester }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="评分" width="100">-->
<!--            <template slot-scope="scope">-->
<!--              <svg-icon v-for="n in +scope.row.rate" :key="n" icon-class="star" class="meta-item__icon" />-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="人数" align="center" width="100">-->
<!--            <template slot-scope="{row}">-->
<!--              <span-->
<!--                v-if="row.stu_number"-->
<!--                class="link-type"-->
<!--                @click="handleFetchPv(row.stu_number)"-->
<!--              >{{ row.stu_number }}</span>-->
<!--              <span v-else>0</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="学分" width="100" align="center">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.credit }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column width="100" align="center" label="学时">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.period }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->

<!--          <el-table-column class-name="status-col" align="center" label="剩余可选" width="100">-->
<!--            <template slot-scope="scope">-->
<!--              <span>{{ scope.row.stu_number }}</span>-->
<!--            </template>-->
<!--          </el-table-column>-->

<!--          <el-table-column align="center" label="上课时间" min-width="110">-->
<!--            <template slot-scope="scope">-->
<!--              <el-dropdown>-->
<!--                <span class="el-dropdown-link">-->
<!--                  学时：{{ scope.row.period }}<i class="el-icon-arrow-down el-icon&#45;&#45;right" />-->
<!--                </span>-->
<!--                <el-dropdown-menu-->
<!--                  slot="dropdown"-->
<!--                >-->
<!--                  <el-dropdown-item-->
<!--                    v-for="t of scope.row.time"-->
<!--                    disabled-->
<!--                  >{{ t.weeks }}周-星期{{ t.week }}-{{ t.lesson }}</el-dropdown-item>-->
<!--                </el-dropdown-menu>-->
<!--              </el-dropdown>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--        </el-table>-->
<!--        <pagination-->
<!--          v-show="total>0"-->
<!--          :total="total"-->
<!--          :page.sync="listQuery.page"-->
<!--          :limit.sync="listQuery.limit"-->
<!--          @pagination="getList"-->
<!--        />-->
<!--      </el-collapse-item>-->
<!--    </el-collapse>-->
  </div>
</template>

<script>
import { fetchCourseChose, fetchRecList } from '@/api/course'
import waves from '@/directive/waves' // waves directive
// import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: 'CourseList',
  // components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        '已选': 'success',
        '待选': 'warning'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      activeName: '2',
      checkList: [],
      recList: null,
      recTotal: 0,
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
        remain: undefined
      },
      // count: 0,
      importanceOptions: [1, 2, 3, 4, 5],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['Optional'],
      multipleSelection: [],
      creditSum: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取课程信息
    getList() {
      this.listLoading = true
      fetchCourseChose(this.listQuery).then(response => {
        console.log(response)
        this.recList = response.data.list
        // const items = this.list
        // for (const i in items) {
        //   items[i].time = this.sortByWeeks(items[i].time)
        // }
        // this.list = items
        // this.total = response.data.total
        let credits = 0
        for (const item of this.recList) {
          credits += item.credit
        }
        this.creditSum = credits
        this.listLoading = false
      })
      // fetchRecList().then(response => {
      //   const items = response.data.items
      //   for (const i in items) {
      //     items[i].time = this.sortByWeeks(items[i].time)
      //   }
      //   this.recList = items
      //   this.recTotal = response.data.total
      //   this.listLoading = false
      // })
    },
    handleModifyStatus(row, status) {

    }, handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }, sortByWeeks(list) {
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
    }, sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    }, sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    }, open() {
      this.$confirm('当前选择科目：' + this.count + '  总学分：' + this.creditSum, '提交', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'successs',
          message: '提交成功'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.creditSum = 0
      this.count = val.length
      for (const i of val) {
        this.creditSum += i.credit
      }
      this.checkList = val
    }
  },
  computed: {
    count: function() {
      let sum = 0
      if (this.recList) {
        for (const item of this.recList) {
          if (item.status === '已选') {
            sum += item.credit
          }
        }
      }
      return sum
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
