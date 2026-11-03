<template>
  <div class="employee-container">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="员工工号">
          <el-input v-model="paramForm.jobidResponsible" placeholder="员工工号"/>
        </el-form-item>
        <el-form-item prop="empname" label="人员名称">
          <el-input v-model="paramForm.nameResponsible" placeholder="人员名称"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="CMDBEemployData" fit border height="400" highlight-current-row stripe @row-click="selectedRow">
        <el-table-column label="员工工号" prop="jobidResponsible" align="center" />
        <el-table-column label="人员名称" prop="nameResponsible" align="center" />
      </el-table>
      <el-row type="flex" justify="space-between">
        <el-col :span="12">
          <pager :fns="getPager" :total="totalNumber" :page-size="size" v-model="$attrs"/>
        </el-col>
        <el-col :span="4">
          <el-button v-if="resetPwdVisible" style="margin: 10px 0 0 0" @click="resetPwd">密码重置</el-button>
          <el-button v-if="resetPwdVisible" style="margin: 10px 0 0 0" @click="clossCode">{{ btnName }}</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import common from '@/api/common'
import store from '@/store'
import request from '@/utils/request'
import pager from '@/components/Pager'
import { systemListAllSelect, resetUserPwd, clossverifyCode } from '@/api/rdmanager/rdPasswordMage'
import cleanBtn from '@/components/CleanBtn'
export default {
  name: 'CMDBEmployee',
  components: { pager, cleanBtn },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramForm: {},
      selectedRowUserId: '',
      resetPwdVisible: false,
      btnName: '',
      CMDBEemployData: []
    }
  },
  mounted() {
    // this.init({ size: this.size, index: this.index })
    this.getInit()
    const userId = store.getters && store.getters.userId
    if (userId === 'sysadmin') {
      this.resetPwdVisible = true
    }
    this.init()
  },
  methods: {
    init() {
      const data = common.paramsFormat()
      request({
        url: '/ppmOperatorKey/selectverifyCode',
        method: 'post',
        data: data
      }).then(res => {
        if (res.data.results === 'Y') {
          this.btnName = '关闭验证码'
        } else {
          this.btnName = '开启验证码'
        }
      })
    },
    // 分页初始化
    getInit() {
      this.getPager()
    },
    getPager: function(size, index) {
      const params = {}
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    getPagerList(params) {
      this.loading = true
      const self = this
      systemListAllSelect(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.CMDBEemployData = res.data.results

          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    /**
             * 搜索事件
             */
    submitBtn: function() {
      const params = this.paramForm
      this.getPagerList(params) // bin
    },
    // 选中行
    selectedRow(row, column, event) {
      // console.log('selectedRow', row, column, event)
      console.log('selectedRow', row.jobidResponsible)
      this.selectedRowUserId = row.id
    },
    // 重置密码
    resetPwd() {
      console.log('重置密码', this.selectedRowUserId)
      if (this.selectedRowUserId === '') {
        this.$message({
          type: 'warning',
          message: '请选择用户'
        })
        return
      }
      this.$confirm('该操作将重置选中角色密码，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {}
        params.id = this.selectedRowUserId
        resetUserPwd(params).then(res => {
          if (res.data.status === 200) {
            this.$message({
              type: 'success',
              message: '重置成功'
            })
          } else {
            this.$message.error(res.data.message)
          }
        })
      })
    },
    /** 关闭验证码 */
    clossCode() {
      this.$confirm('该操作' + this.btnName + '，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        clossverifyCode().then(res => {
          if (res.data.status === 200) {
            this.$message({
              type: 'success',
              message: '操作成功'
            })
            this.init()
          } else {
            this.$message.error(res.data.message)
          }
        })
      })
    }
  }
}
</script>
