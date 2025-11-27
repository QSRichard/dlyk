<script lang="ts">
import {defineComponent} from 'vue'
import {
  CreditCard,
  DataAnalysis,
  Film,
  Fold,
  Notification,
  OfficeBuilding,
  Operation,
  Setting,
  User,
  UserFilled
} from "@element-plus/icons-vue";

import {doGet} from '../http/httpRequest'
import {messageConfirm, messageTip, removeToken} from '../utils/message'

export default defineComponent({
  name: "Dashboard",

  data() {
    return {
      isCollapse: false,
      // 登录人信息
      user: {},
    }
  },

  // vue的函数钩子 mounted 在页面渲染后执行
  mounted(): any {
    // 加载当前用户
    this.loadLoginUser()
  },


  methods: {
    showMenu() {
      this.isCollapse = !this.isCollapse
    }
    ,
    loadLoginUser() {
      doGet("api/login/info", {}).then((resp) => {
        console.log(resp)
        this.user = resp.data.data;
      })
    },

    logout() {
      doGet("api/logout", {}).then(resp => {
        // 退出成功
        if (resp.data.code == 200) {
          removeToken()
          messageTip("退出成功", "success")
          window.location.href = '/'
          return
        }

        messageConfirm("退出异常, 是否强制退出？").then(() => {
          removeToken();
          window.location.href = '/'
        }).catch(() => {
          messageTip("取消强制退出", "warning")
        })
      })
    }
  },
  components: {Fold, Setting, UserFilled, CreditCard, Film, DataAnalysis, Notification, User, Operation, OfficeBuilding}
})
</script>

<template>
  <el-container>
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="menuTitle">@动力云客管理系统</div>
      <el-menu
          active-text-color="#ffd04b"
          background-color="#334157"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          style="border-right: solid 0px;"
          :collapse="isCollapse"
          :collapse-transition="false"
          :unique-opened="true"
          :router="true"
      >


        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <OfficeBuilding/>
            </el-icon>
            <span>市场活动</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Notification/>
            </el-icon>

            市场活动
          </el-menu-item>
          <el-menu-item index="1-2">
            <el-icon>
              <DataAnalysis></DataAnalysis>
            </el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Operation/>
            </el-icon>
            <span>线索管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Film></Film>
            </el-icon>
            线索管理
          </el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <User/>
            </el-icon>
            <span>客户管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <CreditCard></CreditCard>
            </el-icon>
            客户管理
          </el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="4">
          <template #title>
            <el-icon>
              <location/>
            </el-icon>
            <span>
              交易管理
            </span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <CreditCard></CreditCard>
            </el-icon>
            交易管理
          </el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="5">
          <template #title>
            <el-icon>
              <location/>
            </el-icon>
            <span>产品管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <CreditCard></CreditCard>
            </el-icon>
            产品管理
          </el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="6">
          <template #title>
            <el-icon>
              <location/>
            </el-icon>
            <span>字典管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <CreditCard></CreditCard>
            </el-icon>
            字典管理
          </el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="7">
          <template #title>
            <el-icon>
              <User/>
            </el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/dashboard/user">
            <el-icon>
              <UserFilled></UserFilled>
            </el-icon>
            用户管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="8">
          <template #title>
            <el-icon>
              <Setting/>
            </el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <CreditCard></CreditCard>
            </el-icon>
            系统管理
          </el-menu-item>
        </el-sub-menu>

      </el-menu>


    </el-aside>


    <el-container class="rightContent">
      <el-header>
        <el-icon class="show" @click="showMenu">
          <Fold></Fold>
        </el-icon>

        <el-dropdown :hide-on-click="false">
    <span class="el-dropdown-link">
      {{ user.name }}
      <el-icon class="el-icon--right"><arrow-down/></el-icon>
    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>


              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </el-header>


      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>@版权所有 动力节点</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-aside {
  background: #1a1a1a;
}

.el-header {
  background: azure;
  height: 35px;
  line-height: 35px;
}

.el-footer {
  background: aliceblue;
  height: 35px;
  text-align: center;
  line-height: 35px;
}

.rightContent {
  height: calc(100vh);
}

.menuTitle {
  height: 35px;
  line-height: 35px;
  color: #f9f9f9;
  text-align: center;
}

.show {
  cursor: pointer;
}

.el-dropdown {
  float: right;
  line-height: 35px;
}
</style>