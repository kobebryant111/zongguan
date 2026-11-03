// 调休审批
<template>
  <div class="leave-container">
    <div class="leave-head">
      <el-form>
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="姓名" label-width="73px">
              <el-input v-model="form.empName" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="工号:" label-width="73px">
              <el-input v-model="form.badge" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="日期选择" label-width="80px">
              <el-date-picker
                v-model="form.entryTime"
                type="daterange"
                range-separator=" * "
                value-format="yyyy-MM-dd"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item>
              <el-button size="small" type="primary" @click="submitBtn">查 询</el-button>
              <el-button size="small" @click="clearSearch">重 置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="leave-body">
      <div style="text-align: right">
        <el-button size="small" plain @click="confirmLeaveChoice">
          确认
        </el-button>
        <el-button size="small" plain @click="retureLeave">
          退回
        </el-button>
      </div>
      <el-table
        v-loading="loading"
        ref="multipleTable"
        :data="crewDataList"
        :default-sort="{prop: 'comleaveData', order: 'descending'}"
        height="730"
        style="width: 100%"
        stripe
        align = "center"
        element-loaging-text= "数据加载中"
        element-loading-spinner= "el-icon-loading"
        element-loading-background= "gba(0,0,0,0.8)"
        @selection-change = "handSelection">
        <el-table-column type="selection" width="10"/>
        <el-table-column label="工号" prop="badge" width="50"/>
        <el-table-column label="姓名" prop="empName" width="50"/>
        <el-table-column :formatter="timeStyple" label="调休日期" prop ="comleaveData" sortable width="50"/>
        <el-table-column label="调休时间(天)" prop ="intergNum" width="50"/>
        <el-table-column label="原因（备注）" prop ="comleaveReason" width="50"/>
        <el-table-column label="操作" width="120" >
          <template slot-scope="scope">
            <el-link
              type="primary"
              size="mini"
              @click="handDetail(scope.row)">
              详情
            </el-link>
            <el-link
              type="primary"
              size="mini"
              @click="handConfirm(scope.row)">
              同意
            </el-link>
            <el-link
              type="primary"
              size="mini"
              @click="handRuture(scope.row)">
              退回
            </el-link>
          </template>
        </el-table-column>
      </el-table>
      <div class="pager-zj">
        <pager
          ref="pager"
          :fns="getPager"
          :total="totalNumber"
          :page-size="size"
          :current-page="index"
          v-model="$attrs"
        />
      </div>
      <div style="float:right;display:inline-block;">
        <el-dialog
          :visible.sync="approvalLeave"
          title="使用调休"
          width="40%"
        >
          <el-form ref="crewData" :model="crewData" label-width="120px">
            <el-form-item label="姓名" prop ="empName" required>
              <el-input v-model="crewData.empName" :disabled="true" style="width: 600px"/>
            </el-form-item>
            <el-form-item label="工号" prop ="badge" required>
              <el-input v-model="crewData.badge" :disabled="true" style="width: 600px"/>
            </el-form-item>
            <el-form-item label="调休日期" prop ="comleaveData" >
              <!-- <span>{{ crewData.comleaveData }}</span> -->
              <el-date-picker v-model="crewData.comleaveData" :disabled="true" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" type="date" placeholder="选择日期" style="width: 600px"/>
            </el-form-item>
            <el-form-item label="调休时间(天)" prop ="intergNum" >
              <el-select v-model="crewData.intergNum" :disabled="true" placeholder="请选择调休时间（天）" style="width: 150px">
                <el-option label="0.5" value="0.5" />
                <el-option label="1" value="1" />
              </el-select>
            </el-form-item>
            <el-form-item label="备注" prop ="comleaveReason" >
              <el-input v-model="crewData.comleaveReason" :rows="2" :disabled="true" type="textarea" maxlength="300" show-word-limit style="width: 600px"/>
            </el-form-item>
          </el-form>
          <span slot="footer" style="display: flex; justify-content: space-bwtween: align-items: center; width: 100%">
            可使用调休: {{ generateLeave }}天
          </span>
          <span slot="footer">
            <el-button type="primary" @click="ruturn">退回</el-button>
            <el-button type="primary" @click="Confirm">同意</el-button>
          </span>
        </el-dialog>
      </div>
      <div style="float:right;display:inline-block;">
        <el-dialog
          :visible.sync="userLeave"
          :before-close="handleClose"
          title="退回"
          width="40%">
          <el-form ref="crewData" :model="crewData" :rules="rules" label-width="120px">
            <el-form-item label="退回原因" prop ="backReason" >
              <el-input v-model="crewData.backReason" :rows="5" type="textarea" maxlength="300" show-word-limit style="width: 600px"/>
            </el-form-item>
          </el-form>
          <span slot="footer">
            <el-button type="primary" @click="userLeaveCancel">取消</el-button>
            <el-button type="primary" @click="returnLeaveChoice">退回</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import Moment from 'moment'
import { leaveList, userRest, confirmLeave } from '@/api/bankTx/integInfo'
import pager from '@/components/Pager'
export default {
  name: 'LeaveApproval',
  components: {
    pager
  },
  data() {
    return {
      crewDataList: [], // 积分审批列表
      form: {
        empName: '' // 姓名
      },
      crewData: {
        empName: '', // 姓名
        badge: '', // 工号
        approvalDate: '', // 调休日期
        intergNum: 0, // 调休时间(天)
        comleaveReason: '', // 备注
        approvalStatus: '' // 审批状态
      }, // 调休信息
      userLeave: false,
      loading: false,
      approvalLeave: false,
      generateLeave: 0,
      totalNumber: 0,
      multipleSelection: [],
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      rules: {
        backReason: [
          { required: true, message: '请输入退回原因', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '退回原因不能为空格', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.getInit()
  },
  methods: {
    /**
    * 分页初始化方法
    */
    getInit() {
      this.getPager(this.size, this.index)
    },
    // 分页
    getPager(size, index) {
      const params = { ...this.form }
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    getPagerList(params) {
      this.loading = true
      const self = this
      leaveList(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.crewDataList = res.data.results
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 积分审批退回(所选)
    returnLeaveChoice() {
      this.$refs.crewData.validate((valid) => {
        if (valid) {
          const params = {}
          params.ppmKepAprovalDtos = this.multipleSelection
          params.approvalStatus = '3'
          params.comleaveBackReason = this.crewData.backReason
          this.userLeave = false
          this.approvalLeaves(params)
        }
      })
    },
    // 同意调休审批(所选)
    confirmLeaveChoice() {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请至少选择一项')
        return
      }
      this.$confirm('确认提交', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          const params = {}
          params.ppmKepAprovalDtos = this.multipleSelection
          params.approvalStatus = '0'
          this.approvalLeaves(params)
        })
    },
    // row同意
    handConfirm(data) {
      this.$confirm('确认提交', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          const params = {}
          params.ppmKepAprovalDtos = []
          params.ppmKepAprovalDtos.push(data)
          params.approvalStatus = '0'
          this.approvalLeaves(params)
        })
    },
    // row退回
    handRuture(data) {
      this.userLeave = true
      this.multipleSelection.push(data)
    },
    // 审批
    approvalLeaves(params) {
      confirmLeave(params).then(res => {
        if (res.data.status === 200) {
          this.$message.success('审批成功')
        } else {
          this.$message.error(res.data.message)
        }
        this.submitBtn()
        this.userLeaveCancel()
      })
    },
    handleClose() {
      if (this.crewData.backReason === '' || !this.crewData.backReason) {
        this.userLeaveCancel()
      } else {
        this.$confirm('确认退出', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
          .then(_ => {
            this.userLeaveCancel()
          })
      }
    },
    // 退回
    retureLeave() {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请至少选择一项')
        return
      }
      this.userLeave = true
    },
    // 时间格式化
    timeStyple: function(row, cloumn) {
      let time = ''
      if (row.comleaveData != null) {
        time = Moment(row.comleaveData).format('YYYY-MM-DD')
      }
      return time
    },
    /**
     * 查询
     */
    submitBtn() {
      this.getPager(this.size, this.index)
    },
    clearSearch() {
      this.form = []
      this.submitBtn()
    },
    // 详情
    handDetail(data) {
      this.approvalLeave = true
      this.crewData = data
      const params = {}
      params.enpName = data.name
      params.badge = data.badge
      userRest(params).then(res => {
        if (res.data.status === 200) {
          this.generateLeave = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // detail同意
    Confirm() {
      const params = {}
      params.ppmKepAprovalDtos = []
      params.ppmKepAprovalDtos.push(this.crewData)
      params.approvalStatus = '0'
      this.approvalLeaves(params)
      this.approvalLeave = false
    },
    // detail退回
    ruturn() {
      this.userLeave = true
      this.multipleSelection = []
      this.multipleSelection.push(this.crewData)
      this.approvalLeave = false
    },
    // 多选
    handSelection(val) {
      this.multipleSelection = val
    },
    // 取消弹窗
    userLeaveCancel() {
      this.userLeave = false
      this.crewData.backReason = ''
      this.multipleSelection = []
      this.$refs.multipleTable.clearSelection()
    }
  }
}
</script>

<style scoped>
.leave-container {
  padding: 10px;
}
.leave-body {
  padding: 10px 15px 15px 15px;
  background-color: #ffffff;
  height: calc(100vh - 262px);
  border-radius: 4px;
  margin-top: 10px;
}
.pager-zj {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
.leave-head {
  background-color: #ffffff;
  padding: 10px;
}
</style>
