<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "UserView"
})
</script>

<template>
  用户列表页
  <el-button type="primary">添加用户</el-button>
  <el-button type="danger">批量删除</el-button>
</template>

<style scoped>

</style>