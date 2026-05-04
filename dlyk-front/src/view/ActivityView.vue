<template>
  <el-form :inline="true" :model="activityQuery" :rules="activityRules">
    <el-form-item label="负责人">
      <el-select
          class="ownerQuery"
          v-model="activityQuery.ownerId"
          placeholder="请选择负责人"
          @click="loadOwners"
          clearable>
        <el-option
            v-for="item in ownerOptions"
            :v-key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>


    <el-form-item label="活动名称">
      <el-input v-model="activityQuery.name" placeholder="请输入活动名称" clearable/>
    </el-form-item>


    <el-form-item label="活动时间">
      <el-date-picker
          v-model="activityQuery.activityTime"
          type="datetimerange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"/>
    </el-form-item>

    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activityQuery.cost" placeholder="请输入活动预算" clearable/>
    </el-form-item>


    <el-form-item label="创建时间">
      <el-date-picker
          v-model="activityQuery.createTime"
          type="datetime"
          placeholder="请选择活动时间"
          value-format="YYYY-MM-DD HH:mm:ss"
      />
    </el-form-item>


    <el-form-item>
      <el-button type="primary" @click="onSearch">搜索</el-button>
      <el-button type="primary" @click="onReset">重置</el-button>
    </el-form-item>
  </el-form>


  <el-button type="primary" @click="add">录入市场活动</el-button>
  <el-button type="danger" @click="batchDel">批量删除</el-button>

  <el-table
      :data="activityList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"/>

    <el-table-column type="index" label="序号" width="60"/>

    <el-table-column property="ownerDo.name" label="负责人"/>

    <el-table-column property="name" label="活动名称" show-overflow-tooltip/>

    <el-table-column property="startTime" label="开始时间" show-overflow-tooltip/>

    <el-table-column property="endTime" label="结束时间" show-overflow-tooltip/>

    <el-table-column property="cost" label="活动预算" show-overflow-tooltip/>

    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip/>

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


<script lang="ts">
import {defineComponent} from 'vue'
import {doGet} from '../http/httpRequest'

export default defineComponent({
  name: "ActivityView",

  data() {
    return {
      // 市场活动搜索表单选项
      activityQuery: {},

      activityList: [{
        ownerDo: {},
      }],
      pageSize: 0,

      total: 0,

      // 负责人下拉列表数据
      ownerOptions: [{}],

      activityRules: {
        cost: [
          {pattern: /^[0-9]+(\.[0-9]{2})?$/, message: '活动预算必须是整数或者两位小数', trigger: 'blur'}
        ]
      }
    }

  },

  mounted() {
    this.getData(1)
  },

  methods: {
    getData(current) {
      let startTime = '';
      let endTime = '';
      console.log(this.activityQuery.activityTime)
      for (let key in this.activityQuery.activityTime) {
        if (key === '0') {
          startTime = this.activityQuery.activityTime[key]
        }
        if (key === '1') {
          endTime = this.activityQuery.activityTime[key]
        }
      }
      console.log(startTime)
      console.log(endTime)
      doGet("/api/activity", {
        current: current,
        ownerId: this.activityQuery.ownerId,
        name: this.activityQuery.name,
        startTime: startTime,
        endTime: endTime,
        cost: this.activityQuery.cost,
        createTime: this.activityQuery.createTime
      }).then(resp => {
        console.log(resp)
        if (resp.data.code === 200) {
          this.activityList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    onSearch() {
      this.getData(1)
    },


    onReset() {
      this.activityQuery = {}
    },


    toPage(current) {
      this.getData(current)
    },

    // 加载负责人
    loadOwners() {
      doGet("/api/owner", {}).then(resp => {
        console.log(resp.data.data)
        if (resp.data.code === 200) {
          this.ownerOptions = resp.data.data;
        }
      })
    },

    add() {
      this.$router.push("/dashboard/activity/add")
    },

    edit(id) {
      this.$router.push("/dashboard/activity/edit/" + id)
    },

    view(id) {
      this.$router.push("/dashboard/activity/" + id)
    }
  },

  // curren 当前页 参数值由 ele-plus 组件传

})
</script>

<style scoped>

.el-form {
  margin-bottom: 20px;
}

.el-table {
  margin-top: 12px;
}

.el-pagination {
  margin-top: 12px;
}

.ownerQuery {
  width: 150px;
}
</style>