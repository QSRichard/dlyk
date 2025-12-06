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

    <el-table-column property="loginAct" label="账号"/>

    <el-table-column property="name" label="姓名"/>

    <el-table-column property="phone" label="手机"/>

    <el-table-column property="email" label="邮箱"/>

    <el-table-column property="createTime" label="创建时间"/>

    <el-table-column label="操作" show-overflow-tooltip>
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
        <el-button type="success" @click="edit(scope.row.id)">编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @prev-click="toPage"
      @next-click="toPage"
      @current-change="toPage"
  />

</template>
<style scoped>
.el-table {
  margin-top: 15px;
}

.el-pagination {
  margin-top: 15px;
}
</style>


<script lang="ts">
import {defineComponent} from 'vue'
import {doGet} from '../http/httpRequest'

export default defineComponent({
  name: "UserView",
  data() {

    return {
      // 用户列表数据
      userList: [{}],
      pageSize: 0,
      total: 0
    }
  },

  mounted() {
    this.getUserDataList(1)
  },

  methods: {
    // 勾选或者取消勾选 触发该函数
    handleSelectionChange() {

    },
    getUserDataList(current) {
      doGet("/api/users", {current: current}).then(resp => {
        if (resp.data.code === 200) {
          this.userList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // curren 当前页 参数值由 ele-plus 组件传
    toPage(current) {
      this.getUserDataList(current)
    },

    // 查看用户详情
    view(id) {
      console.log(id);
      // 跳转到 用户详情页路由
      this.$router.push("/dashboard/user/" + id)
    },
    edit(id) {

    },
    del(id) {

    }
  }
})
</script>


