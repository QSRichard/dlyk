<template>
  <el-container>
    <el-aside width="200px">
      <img src="../assets/loginBox.svg">
      <p class="LoginText">
        欢迎使用动力云客系统
      </p>
    </el-aside>


    <el-main>
      <div class="LoginTitle">
        欢迎登录
      </div>
      <el-form ref="loginRefForm" :model="user" label-width="220px" :rules="loginRules">
        <el-form-item label="账号" prop="loginAct">
          <el-input v-model="user.loginAct"/>
        </el-form-item>
        <el-form-item label="密码" prop="loginPwd">
          <el-input type="password" v-model="user.loginPwd"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="login">登 录</el-button>
        </el-form-item>

        <el-form-item>
          <el-checkbox label="记住我" v-model="user.rememberMe"/>
        </el-form-item>
      </el-form>

    </el-main>
  </el-container>
</template>


<script lang="ts">
import {defineComponent} from 'vue'
import {doGet, doPost} from '../http/httpRequest'
import {getTokenName, messageTip, removeToken} from '../utils/message'

export default defineComponent({
  // 组件名字
  name: "LoginView",

  // 定义页面中用到的变量
  data() {
    return {
      // 对象变量定义
      user: {},
      // 定义字符串
      name: '',
      age: 0,
      arr: [],
      userList: [{}],
      loginRules: {
        // 定义多个验证规则
        loginAct: [
          {
            required: true, message: '请输入账号', trigger: 'blur'
          },
          {
            min: 3, max: 15
          }
        ],
        loginPwd: [
          {
            required: true, message: '请输入密码', trigger: 'blur'
          },
          {
            min: 3, max: 15, message: '密码最少为3位，最多为15位', trigger: 'blur'
          }
        ]
      }
    }
  },

  // 页面渲染dom元素后会触发该函数
  mounted() {
    this.freeLogin()
  },
  // method 属性 页面上使用的js函数 都在method中定义
  methods: {

    // 登录函数
    login() {
      this.$refs.loginRefForm.validate((isValid) => {
        if (isValid) {
          let formData = new FormData();
          formData.append("loginAct", this.user.loginAct)
          formData.append("loginPwd", this.user.loginPwd)
          formData.append("rememberMe", this.user.rememberMe)

          doPost("/api/login", formData).then(
              (resp) => {
                console.log(resp);
                if (resp.data.code === 200) {
                  messageTip('登录成功', 'success')

                  // 登录成功之后 删除历史存储的token
                  removeToken()

                  // 前端存储JWT
                  if (this.user.rememberMe === true) {
                    window.localStorage.setItem(getTokenName(), resp.data.data)
                  } else {
                    window.sessionStorage.setItem(getTokenName(), resp.data.data)
                  }
                  // 跳转到系统的主页面
                  window.location.href = '/dashboard';
                } else {
                  messageTip('登录失败', 'error')
                }
              }
          )
        }
      })
    },

    // 免密登录
    freeLogin() {
      let token = window.localStorage.getItem(getTokenName());
      // console.log("free login token " + token)
      if (token) {
        doGet("api/login/free", {}).then(resp => {
          if (resp.data.code == 200) {
            window.location.href = "/dashboard"
          }
        })
      }
    }
  }

})
</script>

<style scoped>
.el-aside {
  background: #1a1a1a;
  width: 40%;
  text-align: center;
}

.el-main {
  height: calc(100vh);
}

img {
  height: 710px;
}

.LoginText {
  color: #f9f9f9;
  font-size: 20px;
}

.el-form {
  width: 60%;
  margin: auto;
}

.LoginTitle {
  text-align: center;
  margin-top: 100px;
  margin-bottom: 15px;
  font-weight: bold;
}

.el-button {
  width: 720px;
}
</style>