<template>
  <el-form ref="loginRefForm" :model="userDetail" label-width="220px">
    <el-form-item label="Id">
      {{ userDetail.id }}
    </el-form-item>
    <el-form-item label="账号">
      {{ userDetail.loginAct }}
    </el-form-item>

    <el-form-item label="密码">
      *********
    </el-form-item>

    <el-form-item label="姓名">
      {{ userDetail.name }}
    </el-form-item>

    <el-form-item label="手机">
      {{ userDetail.phone }}
    </el-form-item>

    <el-form-item label="邮箱">
      {{ userDetail.email }}
    </el-form-item>

    <el-form-item label="账号未过期">
      {{ userDetail.accountNoExpired === 1 ? '是' : '否' }}
    </el-form-item>

    <el-form-item label="密码未过期">
      {{ userDetail.credentialsNoExpired === 1 ? '是' : '否' }}
    </el-form-item>


    <el-form-item label="账号未锁定">
      {{ userDetail.accountNoLocked === 1 ? '是' : '否' }}
    </el-form-item>


    <el-form-item label="账号是否启用">
      {{ userDetail.accountEnabled === 1 ? '是' : '否' }}
    </el-form-item>


    <el-form-item label="创建时间">
      {{ userDetail.createTime }}
    </el-form-item>

    <el-form-item label="创建人">
      {{ userDetail.createByDO.name }}
    </el-form-item>

    <el-form-item label="编辑时间">
      {{ userDetail.editByDO.name }}
    </el-form-item>

    <el-form-item label="编辑人">
      {{ userDetail.editBy }}
    </el-form-item>

    <el-form-item label="最近登录时间">
      {{ userDetail.lastLoginTime }}
    </el-form-item>


    <el-form-item>
      <el-button type="success" @click="goBack">返回</el-button>
    </el-form-item>
  </el-form>
</template>


<script lang="ts">
import {defineComponent} from 'vue'
import {doGet} from '../http/httpRequest'

export default defineComponent({
  name: "UserDetailView",
  mounted(): any {
    this.loadUserDeatail()
  },

  data() {
    // 用户详情对象
    return {

      userDetail: {
        createByDO: {},
        editByDO: {}
      }
    }
  },

  methods: {
    loadUserDeatail() {
      let id = this.$route.params.id;

      doGet("/api/user/" + id, {}).then(resp => {
        if (resp.data.code === 200) {
          this.userDetail = resp.data.data;
          console.log(resp)
        }
        if (!this.userDetail.editByDO) {
          this.userDetail.editByDO = {};
        }
        if (!this.userDetail.createByDO) {
          this.userDetail.createByDO = {};
        }
      })
    },
    goBack() {
      this.$router.go(-1);
    }
  }
})
</script>

<style scoped>

</style>