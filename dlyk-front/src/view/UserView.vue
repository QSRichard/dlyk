<template>
  <el-button type="primary">添加用户</el-button>
  <el-button type="danger">批量删除</el-button>

  <el-table
      :data="userList"
      row-key="id"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"/>

    <el-table-column type="index" label="序号" width="60"/>

    <el-table-column property="name" label="账号"/>

    <el-table-column property="address" label="姓名"/>

    <el-table-column property="address" label="手机"/>

    <el-table-column property="address" label="邮箱"/>

    <el-table-column property="address" label="创建时间"/>

    <el-table-column property="address" label="操作">
      详情 编辑 删除
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>


<script lang="ts">
import {defineComponent} from 'vue'
import {doGet} from '../http/httpRequest'

export default defineComponent({
  name: "UserView",
  data() {

    return {
      // 用户列表数据
      userList: [{}]
    }
  },

  mounted() {
    this.getUserDataList(1)
  },

  methods: {
    handleSelectionChange() {

    },
    getUserDataList(current) {
      doGet("/api/users", {current: current}).then(resp => {
        if (resp.data.code === 200) {
          this.userList = resp.data.data;
        }
      })
    }
  }
})
</script>


