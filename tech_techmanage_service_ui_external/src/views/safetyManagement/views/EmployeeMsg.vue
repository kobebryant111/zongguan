<template>
  <div class="employee-container">
    <div class="param-form-container">
      <el-form :model="paramForm" inline>
        <el-form-item prop="badge" label="员工工号">
          <el-input v-model="paramForm.badge" placeholder="员工工号"/>
        </el-form-item>
        <el-form-item prop="empname" label="人员名称">
          <el-input v-model="paramForm.empname" placeholder="人员名称"/>
        </el-form-item>
        <el-form-item prop="mobiletelphone" label="联系电话">
          <el-input v-model="paramForm.mobiletelphone" placeholder="联系电话"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitBtn">查询</el-button>
          <clean-btn :forms="paramForm" />
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table :data="employData" fit border height="400" highlight-current-row stripe @row-click="selectedRow">
        <el-table-column label="员工工号" prop="badge" align="center" />
        <el-table-column label="人员名称" prop="empname" align="center"/>
        <el-table-column label="联系电话" prop="mobiletelphone" align="center" show-overflow-tooltip/>
      </el-table>
      <el-row type="flex" justify="space-between">
        <el-col :span="12">
          <pager :fns="getPager" :total="totalNumber" :page-size="size" v-model="$attrs"/>
        </el-col>
        <el-col :span="4">
          <el-button :disabled="resetPwdVisible" :style="{ display: resetPwdVisible?'none':''}" style="margin: 10px 0 0 0" @click="resetPwd">密码重置</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import pager from '@/components/Pager'
import request from '@/utils/request'
import common from '@/api/common'
import organization from '@/views/plugins/systemPlug/Organization'
import department from '@/views/plugins/systemPlug/Department'
import cleanBtn from '@/components/CleanBtn'
import { resetUserPwd } from '@/api/login'
export default {
  components: { pager, organization, department, cleanBtn },
  props: {
    roleData: {
      type: [Array, Object],
      required: true
    },
    resetPwdVisible: {
      type: Boolean,
      default: false
    },
    // 点击表格工号传值
    tableData: {
      type: Object,
      default: function() {
        return {}
      }
    }
  },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramForm: {
        badge: '',
        empname: '',
        mobiletelphone: ''
      },
      employData: [],
      dialogVisible: false, // dailog 是否显示
      selectedRowUserId: ''
      // resetPwdVisible: true
    }
  },
  // mounted() {
  //   console.log(this.tableData,'屬性值')
  // },
  // created() {
  //   // this.init()
  // },
  methods: {
    // 初始化
    init(params) {
      console.log(this.tableData)

      const paramsData = {
        size: this.size,
        index: this.index,
        depcode: this.tableData.flag === '1' ? this.tableData.depcode : this.tableData.businessUnitDepcode,
        ...params
      }

      const dataObj = common.paramsFormat(paramsData)
      console.log(paramsData, dataObj, 'lslslsls')
      request({
        url: '/safetyOfficerList/queryEmployeePager',
        method: 'post',
        data: dataObj
      }).then(res => {
        this.employData = res.data.results
        this.totalNumber = res.data.total
      })
    },
    closeInput() {
      this.paramForm.badge = ''
      this.paramForm.empname = ''
      this.paramForm.mobiletelphone = ''
    },
    // 分页触发
    getPager: function(pageSize, currentPage) {
      const params = {}
      params.size = pageSize
      params.index = currentPage
      params.badge = this.paramForm.badge
      params.empname = this.paramForm.empname
      params.mobiletelphone = this.paramForm.mobiletelphone
      this.init(params)
    },
    // 搜索
    submitBtn: function() {
      const params = {}
      // params.size = this.size;
      // params.index = this.index;
      params.badge = this.paramForm.badge
      params.empname = this.paramForm.empname
      params.mobiletelphone = this.paramForm.mobiletelphone
      this.init(params)
    },
    // 机构列表
    showOrg: function() {
      this.$refs.organization.falg = true
      this.$refs.organization.filterText = ''
    },
    // 部门列表
    showDep: function() {
      if (this.paramForm.compid) {
        this.$refs.department.dialogClose = true
        this.$refs.department.filterText = ''
        this.$refs.department.init({ 'compid': this.paramForm.compid, 'size': this.$refs.department.size, 'index': this.$refs.department.index })
      }
    },
    // 获取机构节点信息
    getOrgNode: function(node) {
      this.paramForm.compName = node.location
      this.paramForm.compid = node.compid
      this.$refs.department.init({ 'compid': node.compid, 'size': this.$refs.department.size, 'index': this.$refs.department.index })
    },
    // 获取部门节点信息
    getDeptNode: function(node) {
      this.paramForm.depName = node.location
      this.paramForm.depid = node.depid
    },
    // 选中行
    selectedRow(row, column, event) {
      console.log('selectedRow', row, column, event)
      this.$emit('chickRow', row)
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
        params.userid = this.selectedRowUserId
        resetUserPwd(params).then(res => {
          console.log('1111111111111', res)
          if (res.data.status === 200) {
            this.$message({
              type: 'success',
              message: '重置成功'
            })
          } else {
            this.$message.error(res.data.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消重置'
        })
      })
    }
  }
}
</script>
