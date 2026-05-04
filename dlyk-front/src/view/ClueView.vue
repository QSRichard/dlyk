<template>
  <el-button type="primary" class="btn" @click="addClue">录入线索</el-button>

  <el-button type="success" class="btn" @click="importExcel">导入线索</el-button>


  <el-button type="danger" class="btn" @click="batchDel">批量删除</el-button>


  <el-table
      :data="clueList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"/>

    <el-table-column type="index" label="序号" width="60"/>

    <el-table-column property="ownerDo.name" label="负责人" width="60"/>

    <el-table-column property="activityDo.name" label="所属活动" width="60"/>

    <el-table-column label="姓名" width="60">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.fullName }} </a>
      </template>
    </el-table-column>

    <el-table-column property="appellationDo.typeValue" label="称呼"/>

    <el-table-column property="phone" label="手机" show-overflow-tooltip/>

    <el-table-column property="weixin" label="微信" show-overflow-tooltip/>

    <el-table-column property="needLoanDo.typeValue" label="是否贷款" show-overflow-tooltip/>

    <el-table-column property="intentionStateDo.typeValue" label="意向状态" show-overflow-tooltip/>


    <el-table-column property="intentionProductDo.name" label="意向产品" show-overflow-tooltip/>

    <el-table-column property="stateDo.typeValue" label="线索状态" show-overflow-tooltip/>
    <el-table-column property="sourceDo.typeValue" label="线索来源" show-overflow-tooltip/>
    <el-table-column property="nextContactTime" label="下次联系时间" show-overflow-tooltip/>

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
  name: "ClueView",


  mounted(): any {
    this.getClueDataList();
  },


  data() {
    return {
      clueList: [{
        ownerDo: {},
        activityDo: {},
        appellationDo: {},
        needLoanDo: {},
        intentionStateDo: {},
        intentionProductDo: {},
        stateDo: {},
        sourceDo: {},
      }],

      pageSize: 0,
      total: 0
    }
  },

  methods: {
    handleSelectionChange() {

    },

    getClueDataList(current) {
      doGet("/api/clues", {current: current}).then(resp => {
        // console.log(resp)
        if (resp.data.code === 200) {
          this.clueList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // curren 当前页 参数值由 ele-plus 组件传
    toPage(current) {
      this.getClueDataList(current)
    },

  }
})
</script>


<style scoped>

</style>