<template>
  <el-button type="primary" @click="addUser">添加用户</el-button>
  <el-button type="danger" @click="batchDel">批量删除</el-button>

  <el-table
      :data="userList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"/>

    <el-table-column type="index" label="序号" width="60"/>

    <el-table-column property="loginAct" label="账号"/>

    <el-table-column property="name" label="姓名" show-overflow-tooltip/>

    <el-table-column property="phone" label="手机" show-overflow-tooltip/>

    <el-table-column property="email" label="邮箱" show-overflow-tooltip/>

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

  <!- 新增用户对话框 -->
  <el-dialog
      v-model="userDialogOverflowVisible"
      :title="userQuery.id > 0 ? '编辑用户' : '新增用户' "
      width="55%"
      draggable
      overflow
      center
  >
    <el-form ref="addUserRefForm" :model="userQuery" label-width="120px" , :rules="addUserRule">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="userQuery.loginAct"/>
      </el-form-item>


      <el-form-item label="密码" v-if="userQuery.id > 0">
        <el-input type="password" v-model="userQuery.loginPwd"/>
      </el-form-item>

      <el-form-item label="密码" prop="loginPwd" v-else>
        <el-input type="password" v-model="userQuery.loginPwd"/>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="userQuery.name"/>
      </el-form-item>

      <el-form-item label="手机" prop="phone">
        <el-input v-model="userQuery.phone"/>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userQuery.email"/>
      </el-form-item>


      <el-form-item label="账号未过期" prop="accountNoExpired">
        <el-select v-model="userQuery.accountNoExpired" placeholder="请选择" style="width: 240px">
          <el-option
              v-for="item in options"
              :v-key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>


      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="userQuery.credentialsNoExpired" placeholder="请选择" style="width: 240px">
          <el-option
              v-for="item in options"
              :v-key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>


      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="userQuery.accountNoLocked" placeholder="请选择" style="width: 240px">
          <el-option
              v-for="item in options"
              :v-key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>


      <el-form-item label="账号是否启用" prop="accountEnabled">
        <el-select v-model="userQuery.accountEnabled" placeholder="请选择" style="width: 240px">
          <el-option
              v-for="item in options"
              :v-key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="userDialogOverflowVisible = false">关闭</el-button>
        <el-button type="primary" @click="userSubmit">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>
<style scoped>
.el-table {
  margin-top: 15px;
}

.el-pagination {
  margin-top: 15px;
}

.el-select {
  width: 100%;
}
</style>


<script lang="ts">
import {defineComponent} from 'vue'
import {doDelete, doGet, doPost, doPut} from '../http/httpRequest'
import {messageConfirm, messageTip} from '../utils/message'

export default defineComponent({
  name: "UserView",
  data() {

    return {
      // 用户列表数据
      userList: [{}],
      pageSize: 0,
      total: 0,

      userDialogOverflowVisible: false,
      userQuery: {},
      addUserRule: {
        loginAct: [
          {
            required: true, message: '请输入账号', trigger: 'blur'
          }
        ],
        loginPwd: [
          {
            required: true, message: '请输入密码', trigger: 'blur'
          },
          {
            min: 3, max: 15, message: '密码最少为3位，最多为15位', trigger: 'blur'
          }
        ],
        name: [
          {
            required: true, message: '请输入姓名', trigger: 'blur'
          },
          {
            pattern: /^[\u4e00-\u9fa5]+$/, message: '姓名必须为中文', trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true, message: '请输入手机', trigger: 'blur'
          },
          {
            pattern: /^1[3-9]\d{9}$/, message: '手机号码格式有误', trigger: 'blur'
          }
        ],
        email: [
          {
            required: true, message: '请输入邮箱', trigger: 'blur'
          },
          {
            pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '邮箱格式有误', trigger: 'blur'
          }
        ],

        credentialsNoExpired: [
          {
            required: true, message: '请选择密码是否过期', trigger: 'blur'
          }
        ],


        accountNoExpired: [
          {
            required: true, message: '请选择账号是否过期', trigger: 'blur'
          }
        ],


        accountNoLocked: [
          {
            required: true, message: '请选择账号是否锁定', trigger: 'blur'
          }
        ],

        accountEnabled: [
          {
            required: true, message: '请选择账号是否启用', trigger: 'blur'
          }
        ],
      },

      options: [
        {
          label: '是', value: 1
        },
        {
          label: '否', value: 0
        }
      ],

      selectUserIdList: [],
    }
  },

  // 子页面使用inject 使用父页面的数据或函数
  inject: ['reload'],

  mounted() {
    this.getUserDataList(1)
  },

  methods: {
    // 勾选或者取消勾选 触发该函数
    // ele 官方注入 将选择的数据放入 函数参数中
    handleSelectionChange(selectItemArray) {
      console.log(selectItemArray)

      this.selectUserIdList = [];
      selectItemArray.forEach(data => {
        let id = data.id;
        this.selectUserIdList.push(id);
      })
    },
    getUserDataList(current) {
      doGet("/api/users", {current: current}).then(resp => {
        console.log(resp)
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

    // 新增用户提交
    userSubmit() {
      let formData = new FormData();

      // 只添加明确需要的字段，且确保值是 string/number/boolean
      const fields = ['id', 'loginAct', 'loginPwd', 'name', 'phone', 'email',
        'accountNoExpired', 'credentialsNoExpired', 'accountNoLocked', 'accountEnabled'];

      for (let field of fields) {
        // console.log(field)
        // formData.append(field, this.userQuery[field])
        if (this.userQuery[field] !== undefined && this.userQuery[field] !== null) {
          formData.append(field, String(this.userQuery[field]))
        }
      }

      // console.log("fromData")
      // console.log(formData)
      // 验证通过才提交
      this.$refs.addUserRefForm.validate((isvalid) => {
        if (isvalid) {
          if (this.userQuery.id > 0) {  // 编辑用户
            doPut("/api/user/edit", formData).then(
                resp => {
                  console.log(resp)
                  if (resp.data.code === 200) {
                    messageTip("编辑成功", 'success')
                    // 刷新页面
                    this.reload();
                  } else {
                    messageTip("编辑失败", 'error')
                  }
                }
            )
          } else { // 新增用户
            doPost("/api/user/create", formData).then(
                resp => {
                  console.log(resp)
                  if (resp.data.code === 200) {
                    messageTip("提交成功", 'success')

                    // 刷新页面
                    this.reload();
                  } else {
                    messageTip("提交失败", 'error')
                  }
                }
            )
          }
        }
        console.log(isvalid)
      })
    },

    // 查看用户详情
    view(id) {
      console.log(id);
      // 跳转到 用户详情页路由
      this.$router.push("/dashboard/user/" + id)
    },
    del(id) {
      messageConfirm("确认删除?").then(() => {
        doDelete("/api/user/delete/" + id, {}).then(resp => {
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
    },


    // 点击新增 弹出对话框
    addUser() {
      this.userDialogOverflowVisible = true;
      this.userQuery = {};
    },

    // 点击编辑 弹出对话框
    edit(id) {
      this.userDialogOverflowVisible = true;
      this.loadUserInfo(id)
    },

    loadUserInfo(id) {
      doGet("/api/user/" + id, {}).then(
          resp => {
            console.log(resp)
            if (resp.data.code === 200) {
              this.userQuery = resp.data.data;
              this.userQuery.loginPwd = "";
            }
          }
      )
    },

    batchDel() {

      if (this.selectUserIdList.length <= 0) {
        messageTip("请选择删除数据", 'warning');
        return;
      }

      // 将数字数组转为字符串
      let ids = this.selectUserIdList.join(",")


      messageConfirm("确认批量删除?").then(() => {

        doDelete("/api/user/batch/del", {
          ids: ids
        }).then(resp => {
          if (resp.data.code === 200) {
            messageTip("批量删除成功", 'success')
            this.reload();
          } else {
            messageTip("批量删除失败, 原因 " + resp.data.msg, 'error')
          }
        })
      }).catch(() => {
        messageTip("取消批量删除", 'warning')
      })
    }
  }
})
</script>


