<template>
  <div class="app-container">
    <div style="margin-top: 40px">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="年级/专业">
          <el-cascader
            :options="options"
            :props="{ expandTrigger: 'hover' }"
            @change="handleChange"></el-cascader>
        </el-form-item>
        <el-form-item label="讲师" prop="teaIds">
          <el-select v-model="form.teaIds" multiple placeholder="请选择" style="width: 412px">
            <el-option
              v-for="item in teacherOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程" prop="coIds">
          <el-select v-model="form.coIds" multiple placeholder="请选择" style="width: 412px">
            <el-option
              v-for="item in courseOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程时间" prop="startTime">
          <el-select v-model="form.startTime" placeholder="请选择">
            <el-option
              v-for="item in startOptions"
              :key="item.index"
              :label="item.value"
              :value="item.index">
            </el-option>
          </el-select>
          -
          <el-select v-model="form.endTime" placeholder="请选择">
            <el-option
              v-for="item in endOptions"
              :key="item.index"
              :label="item.value"
              :value="item.index"
              :disabled="item.disabled">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit(form)">立即创建</el-button>
          <el-button @click="reset(form)">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { fetchAll } from '@/api/teacher'
import { fetchAllList, createCourseChoice } from '@/api/course'

export default {
  name: 'CreateChoice',
  data() {
    return {
      value: [],
      options: [
        {
          value: 2016,
          label: '16级',
          children: [
            {
              label: '软件工程',
              value: '软件工程'
            },
            {
              value: '软件+道铁',
              label: '软件+道铁'
            },
            {
              value: '软件+桥梁',
              label: '软件+桥梁'
            },
            {
              value: '软件+交运',
              label: '软件+交运'
            },
            {
              value: '软件+轨道',
              label: '软件+轨道'
            },
            {
              value: '软件+电气',
              label: '软件+电气'
            }
          ]
        },
        {
          value: 2017,
          label: '17级',
          children: [
            {
              label: '软件工程',
              value: '软件工程'
            },
            {
              value: '软件+道铁',
              label: '软件+道铁'
            },
            {
              value: '软件+桥梁',
              label: '软件+桥梁'
            },
            {
              value: '软件+交运',
              label: '软件+交运'
            },
            {
              value: '软件+轨道',
              label: '软件+轨道'
            },
            {
              value: '软件+电气',
              label: '软件+电气'
            }
          ]
        },
        {
          value: 2018,
          label: '18级',
          children: [
            {
              label: '软件工程',
              value: '软件工程'
            },
            {
              value: '软件+道铁',
              label: '软件+道铁'
            },
            {
              value: '软件+桥梁',
              label: '软件+桥梁'
            },
            {
              value: '软件+交运',
              label: '软件+交运'
            },
            {
              value: '软件+轨道',
              label: '软件+轨道'
            },
            {
              value: '软件+电气',
              label: '软件+电气'
            }
          ]
        },
        {
          value: 2019,
          label: '19级',
          children: [
            {
              label: '软件工程',
              value: '软件工程'
            },
            {
              value: '软件+道铁',
              label: '软件+道铁'
            },
            {
              value: '软件+桥梁',
              label: '软件+桥梁'
            },
            {
              value: '软件+交运',
              label: '软件+交运'
            },
            {
              value: '软件+轨道',
              label: '软件+轨道'
            },
            {
              value: '软件+电气',
              label: '软件+电气'
            }
          ]
        }
      ],
      teacherOptions: [],
      courseOptions: [],
      startOptions: [
        { value: '第1周', index: 1 },
        { value: '第2周', index: 2 },
        { value: '第3周', index: 3 },
        { value: '第4周', index: 4 },
        { value: '第5周', index: 5 },
        { value: '第6周', index: 6 },
        { value: '第7周', index: 7 },
        { value: '第8周', index: 8 },
        { value: '第9周', index: 9 },
        { value: '第10周', index: 10 },
        { value: '第11周', index: 11 },
        { value: '第12周', index: 12 },
        { value: '第13周', index: 13 },
        { value: '第14周', index: 14 },
        { value: '第15周', index: 15 },
        { value: '第16周', index: 16 },
        { value: '第17周', index: 17 },
        { value: '第18周', index: 18 },
        { value: '第19周', index: 19 },
        { value: '第20周', index: 20 },
        { value: '第12周', index: 21 }
      ],
      form: {
        grade: undefined,
        college: '',
        teaIds: undefined,
        coIds: undefined,
        startTime: undefined,
        endTime: undefined,
      }
    }
  },
  methods: {
    handleChange(value) {
      this.form.grade = value[0]
      this.form.college = value[1]
      console.log(value)
    },
    getTeachers() {
      fetchAll().then(response => {
        console.log(response.data.list)
        const teachers = response.data.list
        for (let i = 0; i < teachers.length; i++) {
          console.log(teachers[i])
          this.teacherOptions.push({
            value: teachers[i].id,
            label: teachers[i].name
          })
        }
      })
    },
    getCourses() {
      fetchAllList().then(response => {
        console.log({ course: response.data.list })
        const courses = response.data.list
        for (let i = 0; i < courses.length; i++) {
          this.courseOptions.push({
            value: courses[i].id,
            label: courses[i].name
          })
        }
      })
    },
    onSubmit(form) {
      createCourseChoice(form).then(resp => {
        if (resp.data.msg) {
          this.$message.error(resp.data.msg)
        } else {
          this.$message({
            message: '操作 Success',
            type: 'success'
          })
        }
      })
    },
    reset(form) {
      this.$refs['form'].resetFields()
      this.form.grade = ''
      this.form.college = ''
    }
  },
  created() {
    this.getTeachers()
    this.getCourses()
  },
  computed: {
    endOptions: function() {
      const endOptions = []
      const startTime = this.form.startTime
      let disable = true
      for (const opt of this.startOptions) {
        if (opt.index >= startTime) {
          disable = false
        }
        endOptions.push(
          {
            value: opt.value,
            index: opt.index,
            disabled: disable
          }
        )
      }
      return endOptions
    }
  }
}
</script>

<style scoped>

</style>
