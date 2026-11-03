// 积分审批
<template>
  <div class="points-container">
    <div class="points-head">
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
    <div class="points-body">
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
        <el-table-column label="登记日期" prop="registerData" sortable width="50"/>
        <el-table-column label="日期类型" prop="dataType" width="50">
          <template slot-scope="scope">
            <span>{{ getDictName(dataTypeDictId,scope.row.dataType) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="开始时间" prop="startTime" width="70"/>
        <el-table-column label="结束时间" prop="endTime" width="70"/>
        <el-table-column label="产生调休（天）" prop="integNum" width="50"/>
        <el-table-column label="工作分类" prop="workType" width="50">
          <template slot-scope="scope">
            <span>{{ getDictName(workTypeDictId,scope.row.workType) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="原因" prop="integReason" width="50"/>
        <el-table-column label="操作" width="120" >
          <template slot-scope="scope">
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
          :visible.sync="userLeave"
          :before-close="handleClose"
          title="退回"
          width="40%">
          <el-form ref="crewData" :model="crewData" :rules="rules" label-width="120px">
            <el-form-item label="退回原因" prop ="backReason" >
              <!-- {{ crewData.backReason }} -->
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
// eslint-disable-next-line
import { getDictName } from '@/utils/dict'
import pager from '@/components/Pager'
import { pointsList, confirmPoints } from '@/api/bankTx/integInfo'
export default {
  name: 'PointsApproval',
  components: {
    pager
  },
  data() {
    return {
      dataTypeDictId: 'UNRECOG_INTEG_DATA_ENCOD',
      workTypeDictId: 'UNRECOG_INTEG_WORK_ENCOD',
      crewDataList: [], // 积分审批列表
      form: {
      },
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      totalNumber: 0,
      userLeave: false,
      crewData: {},
      multipleSelection: [],
      loading: false,
      rules: {
        backReason: [
          { required: true, message: '请输入退回原因', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '退回原因不能为空格', trigger: 'blur' }
        ]
      }
    }
  },
  // created() {

  // },
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
      pointsList(params).then(res => {
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
    // 多选
    handSelection(val) {
      this.multipleSelection = val
    },
    // 同意调休审批
    handConfirm(data) {
      const params = {}
      params.integInfoList = []
      params.integInfoList.push(data)
      params.status = '2'
      this.$confirm('确认提交', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          confirmPoints(params).then(res => {
            if (res.data.status === 200) {
              this.$message.success('确认成功')
            } else {
              this.$message.error(res.data.message)
            }
            this.submitBtn()
          })
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
          params.integInfoList = this.multipleSelection
          params.status = '2'
          confirmPoints(params).then(res => {
            if (res.data.status === 200) {
              this.$message.success('确认成功')
            } else {
              this.$message.error(res.data.message)
            }
            this.submitBtn()
            this.userLeaveCancel()
          })
        })
    },
    // 退回调休审批
    handRuture(data) {
      this.multipleSelection = []
      this.multipleSelection.push(data)
      this.userLeave = true
    },
    // 积分审批退回(所选)
    returnLeaveChoice() {
      this.$refs.crewData.validate((valid) => {
        if (valid) {
          const params = {}
          params.integInfoList = this.multipleSelection
          params.status = '0'
          params.integBackReason = this.crewData.backReason
          this.userLeave = false
          confirmPoints(params).then(res => {
            if (res.data.status === 200) {
              this.$message.success('确认成功')
            } else {
              this.$message.error(res.data.message)
            }
            this.submitBtn()
            this.userLeaveCancel()
          })
        }
      })
    },
    // 退回
    retureLeave() {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请至少选择一项')
        return
      }
      this.userLeave = true
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
.points-container {
  padding: 10px;
}

.points-head {
  background-color: #ffffff;
  padding: 10px;
}
.points-body {
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
</style>
