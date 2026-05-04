<template>

  <el-form ref="activityRemarkRefForm" :model="activityRemarkQuery" label-width="220px" :rules="activityRemarkRules">

    <el-form-item label="Id">
      <div class="desc">{{ activityDetail.id }}</div>
    </el-form-item>

    <el-form-item label="负责人">
      <div class="desc">{{ activityDetail.ownerDo.name }}</div>
    </el-form-item>

    <el-form-item label="活动名称">
      <div class="desc">{{ activityDetail.name }}</div>
    </el-form-item>

    <el-form-item label="开始时间">
      <div class="desc">{{ activityDetail.startDate }}</div>
    </el-form-item>

    <el-form-item label="结束时间">
      <div class="desc">{{ activityDetail.endDate }}</div>
    </el-form-item>

    <el-form-item label="活动预算">
      <div class="desc">{{ activityDetail.cost }}</div>
    </el-form-item>

    <el-form-item label="活动描述">
      <div class="desc">{{ activityDetail.description }}</div>
    </el-form-item>

    <el-form-item label="创建时间">
      <div class="desc">{{ activityDetail.createTime }}</div>
    </el-form-item>

    <el-form-item label="创建人">
      <div class="desc">{{ activityDetail.createByDo.name }}</div>
    </el-form-item>

    <el-form-item label="编辑时间">
      <div class="desc">{{ activityDetail.editTime }}</div>
    </el-form-item>

    <el-form-item label="编辑人">
      <div class="desc">{{ activityDetail.editByDo.name }}</div>
    </el-form-item>


    <el-form-item label="填写备注" prop="noteContent">

      <el-input
          v-model="activityRemarkQuery.noteContent"
          style="width:100%"
          :rows="10"
          type="textarea"
          placeholder="请输入活动备注"
      />
    </el-form-item>


    <el-form-item>
      <el-button type="primary" @click="activityRemarkSubmit">提交备注</el-button>
      <el-button @click="goBack">返回</el-button>
    </el-form-item>

  </el-form>


  <el-table
      :data="activityRemarkList"
      style="width: 100%"
  >

    <el-table-column type="index" label="序号" width="60"/>

    <el-table-column property="noteContent" label="备注内容"/>

    <el-table-column property="createTime" label="备注时间"/>

    <el-table-column property="createByDo.name" label="备注人"/>

    <el-table-column property="editTime" label="编辑时间"/>

    <el-table-column property="editByDo.name" label="编辑人"/>

    <el-table-column label="操作">
      <template #default="scope">
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

  <el-dialog v-model="editActivityRemarkDialogVisible" title="编辑市场活动记录" width="55%" center overflow draggable>

    <el-form ref="editActivityRemarkRefForm" :model="editActivityRemarkQuery" label-width="120px"
             :rules="editActivityRemarkRules">

      <el-form-item label="活动备注" prop="email">
        <el-input
            v-model="editActivityRemarkQuery.noteContent"
            :rows="8"
            type="textarea"
            placeholder="请输入活动备注"
        />
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editActivityRemarkDialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="editActivityRemarkSubmit">提交</el-button>
      </div>
    </template>
  </el-dialog>


</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {doDelete, doGet, doPost, doPut} from '../http/httpRequest'
import {goBack, messageConfirm, messageTip} from '../utils/message.js'

export default defineComponent({
  name: "ActivityDetailView",

  inject: ['reload'],

  data() {
    return {
      // 活动详情对象
      activityDetail: {

        ownerDo: {},
        createByDo: {},
        editByDo: {}
      },

      // 活动备注列表对象
      activityRemarkList: [{
        createByDo: {},
        editByDo: {}
      }],

      activityRemarkQuery: {},
      editActivityRemarkQuery: {},

      activityRemarkRules: {
        noteContent: [
          {
            required: true, message: '请输入活动备注', trigger: 'blur'
          },
          {
            min: 6, max: 233, message: '活动备注字符长度为 5~255', trigger: 'blur'
          }
        ]
      },

      editActivityRemarkRules: {
        noteContent: [
          {
            required: true, message: '请输入活动备注', trigger: 'blur'
          },
          {
            min: 6, max: 233, message: '活动备注字符长度为 5~255', trigger: 'blur'
          }
        ]
      },
      // 分页时每页显示多少数据
      pageSize: 0,
      // 分页总共查询多少数据
      total: 0,
      // 编辑活动备注的对话框
      editActivityRemarkDialogVisible: false
    }
  },

  mounted(): any {

    this.loadActivityDetail();
    this.getActivityRemarkDataList(1);
  },
  methods: {
    goBack,

    loadActivityDetail() {

      let id = this.$route.params.id;
      doGet("/api/activity/" + id, {}).then(
          resp => {
            if (resp.data.code === 200) {
              this.activityDetail = resp.data.data
              if (!this.activityDetail.ownerDo) {
                this.activityDetail.ownerDo = {}
              }
              if (!this.activityDetail.createByDo) {
                this.activityDetail.createByDo = {}
              }
              if (!this.activityDetail.editByDo) {
                this.activityDetail.editByDo = {}
              }

            }
          }
      )
    },

    activityRemarkSubmit() {
      this.$refs.activityRemarkRefForm.validate((isValidate) => {
        if (isValidate) {

          doPost("/api/activity/remark", {
            activityId: this.activityDetail.id,
            noteContent: this.activityRemarkQuery.noteContent
          }).then(resp => {
            if (resp.data.code === 200) {
              messageTip("编辑成功", "success")
              this.reload()
            } else {
              messageTip("编辑失败", "error")
            }
          })
        }
      })
    },

    editActivityRemarkSubmit() {
      this.$refs.editActivityRemarkRefForm.validate((isValidate) => {
        if (isValidate) {

          doPut("/api/activity/remark", {
            id: this.editActivityRemarkQuery.id,
            noteContent: this.editActivityRemarkQuery.noteContent
          }).then(resp => {
            if (resp.data.code === 200) {
              messageTip("提交成功", "success")
              this.reload()
            } else {
              messageTip("提交失败", "error")
            }
          })
        }
      })
    },

    getActivityRemarkDataList(current) {
      doGet("/api/activity/remark", {current: current, activityId: this.$route.params.id}).then(resp => {
        console.log(resp)
        if (resp.data.code === 200) {
          this.activityRemarkList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // curren 当前页 参数值由 ele-plus 组件传
    toPage(current) {
      this.getActivityRemarkDataList(current)
    },

    edit(id) {
      this.editActivityRemarkDialogVisible = true
      doGet("/api/activity/remark/" + id, {}).then(
          resp => {
            if (resp.data.code === 200) {
              this.editActivityRemarkQuery = resp.data.data
            }
          }
      )
    },


    del(id) {
      messageConfirm("确认删除?").then(() => {
        doDelete("/api/activity/remark/" + id, {}).then(resp => {
          if (resp.data.code === 200) {
            messageTip("删除成功", 'success')
            this.reload();
          } else {
            messageTip("删除失败, 原因 " + resp.data.msg, 'error')
          }
        })
      }).catch(() => {
        messageTip("取消删除", 'warning')
      })

    }
  }
})
</script>

<style scoped>
.desc {
  background-color: #f9f9f9;
  width: 100%;
  padding-left: 20px;
}
</style>