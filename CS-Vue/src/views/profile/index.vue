<template>
  <div id="app" class="container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="基本信息" name="first">
        <div class="base_info">
          <el-row :gutter="24">
            <el-col :span="2" :offset="5">
              <el-upload
                class="avatar-uploader"
                action="https://jsonplaceholder.typicode.com/posts/"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img
                  :src="imageUrl"
                  class="avatar"
                  alt="avatar"
                  title="点击头像更新"
                  onmouseover="this.style.opacity=0.5;"
                  onmouseout="this.style.opacity=1;"
                >
              </el-upload>
            </el-col>
            <el-col :span="14" :offset="1">
              <el-row :gutter="24">
                <el-col :span="7">
                  <span class="name" style="margin-top: 1%;display: block">
                    {{ info.name }}
                    <svg-icon icon-class="woman" />
                  </span>
                </el-col>
                <el-col :span="7">
                  <el-button type="primary" size="small" icon="el-icon-edit">编辑个人信息</el-button>
                </el-col>
                <el-col :span="7" />
              </el-row>
              <el-row :gutter="24">
                <el-col :span="7">
                  <span class="font-small">账号ID：{{ info.stuid }}</span>
                </el-col>
                <el-col :span="7">
                  <span class="font-small">注册时间：{{ info.regTime }}</span>
                </el-col>
                <el-col :span="4">
                  <span class="font-small">身份：学生</span>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row :gutter="24" style="margin-top: 7%">
            <el-col :span="14" :offset="8">
              <span class="name">学院：{{ info.college }}</span>
            </el-col>
          </el-row>
          <el-row :gutter="24" style="margin-top: 3%">
            <el-col :span="14" :offset="8">
              <span class="name">专业：{{ info.profession }}</span>
            </el-col>
          </el-row>
          <el-row :gutter="24" style="margin-top: 3%">
            <el-col :span="14" :offset="8">
              <span class="name">班级：{{ info.class }}</span>
            </el-col>
          </el-row>
          <el-row />
          <el-row />
          <el-row />
          <el-row />
        </div>
      </el-tab-pane>
      <el-tab-pane label="安全信息" name="second">
        <div class="base_info">
          <!--    行1      -->
          <el-row :gutter="24">
            <el-col :span="14" :offset="3">
              <el-row :gutter="24">
                <el-col :span="7">
                  <span class="name" style="margin-top: 1%;display: block">{{ info.name }}</span>
                </el-col>
                <el-col :span="7">
                  <el-button type="primary" size="small" icon="el-icon-edit">编辑个人信息</el-button>
                </el-col>
              </el-row>
              <el-row :gutter="24">
                <el-col :span="7">
                  <span class="font-small">账号ID：{{ info.stuid }}</span>
                </el-col>
                <el-col :span="7">
                  <span class="font-small">上次登录时间：{{ info.lastTime }}</span>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row :gutter="24" style="margin-top: 7%">
            <el-col :span="14" :offset="3">
              <span class="page2">验证邮箱：{{ info.email }}</span>
              <div class="divider-line" />
            </el-col>
          </el-row>
          <el-row :gutter="24" style="margin-top: 3%">
            <el-col :span="14" :offset="3">
              <span class="page2">手机绑定：{{ info.phone }}</span>
              <!--              <a href="" style="color: #409EFF;">&nbsp;&nbsp;修改</a>-->
              <div class="divider-line" />
            </el-col>
          </el-row>
          <el-row :gutter="24" style="margin-top: 3%">
            <el-col :span="14" :offset="3">
              <span class="page2">账户密码：</span>
              <a href="" style="color: #409EFF;">修改密码</a>
              <div class="divider-line" />
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
      <el-tab-pane label="信息修改" name="third">
        <div class="base_info">
          <el-form ref="form" :model="form" label-width="80px" :rules="rules">
            <el-form-item label="昵称">
              <el-input v-model="form.name" />
            </el-form-item>
            <el-form-item label="性别">
              <el-radio v-model="form.sex" label="男">男</el-radio>
              <el-radio v-model="form.sex" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="学院">
              <el-input v-model="form.college" />
            </el-form-item>
            <el-form-item label="班级">
              <el-input v-model="form.class" />
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="form.phone" />
            </el-form-item>
            <el-form-item
              prop="email"
              label="邮箱"
            >
              <el-input v-model="form.email" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">更新</el-button>
              <el-button>取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { fetchInfo, updateInfo } from '../../api/student'
// import PanThumb from '@/components/PanThumb'

export default {
  name: 'Profile',
  data() {
    return {
      activeName: 'first',
      imageUrl: this.$store.state.user.avatar,
      info: {},
      form: {
        name: '123',
        sex: '1',
        college: '',
        class: '',
        phone: '',
        email: ''
      },
      rules: {
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]
        // phone: [
        //   { type: 'number', message: '请输入正确的手机号码', trigger: ['blur', 'change'] }
        // ]
      }
    }
  },
  computed: {
    ...mapGetters(['avatar'])
    // PanThumb
  },
  created() {
    this.getInfo()
  },
  methods: {
    onSubmit() {
      console.log(JSON.stringify(this.form))
      console.log('submit!')
      updateInfo(this.form).then(response => {
        for (const f in JSON.parse(JSON.stringify(this.form))) {
          this.info[f] = this.form[f]
        }
        this.$message(response.data.message)
      })
    },
    getInfo() {
      fetchInfo().then(response => {
        this.info = response.data.profile
        console.log(this.info)
        this.form = {
          name: this.info.name,
          sex: this.info.sex,
          college: this.info.college,
          class: this.info.class,
          phone: this.info.phone,
          email: this.info.email
        }
      })
    },
    handleClick(tab, event) {
      // console.log(tab, event)
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
      // console.log(this.imageUrl)
      this.$message.success('更新完成')
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    }
  }
}
</script>
<style scoped>
  .divider-line {
    height: 1px;
    border-top: 1px solid #d0d0d0;
    text-align: center;
  }

  .container {
    margin: 10px 30px;
  }

  .base_info {
    margin: 1% 2% 2% 1%;
    padding: 2%;
    /*box-shadow: 2px 2px 3px #aaaaaa;*/
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.15), 0 0 6px rgba(0, 0, 0, 0.15)
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar {
    width: 80px;
    height: 80px;
    display: block;
    border-radius: 7px;
  }

  .name {
    font-size: 20px;
    font-family: '微软雅黑', serif;
    font-weight: bold;
    align-content: center;
  }

  .el-row {
    margin-bottom: 20px;

  &
  :last-child {
    margin-bottom: 0;
  }

  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .font-small {
    margin-top: 2%;
    display: block;
    font-size: 12px;
  }

  .page2 {
    font-size: 16px;
    font-family: '微软雅黑', serif;
    font-weight: bold;
    align-content: center;
  }
</style>
