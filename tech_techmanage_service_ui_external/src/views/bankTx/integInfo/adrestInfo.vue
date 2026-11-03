/**
 * 调休详情
 */
<template>
  <div class="AdrestInfo-container">
    <div class="AdrestInfo-head">
      <el-form :model="form">
        <el-row :gutter="10">
          <el-col :span="5">
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
            <el-form-item label="审批状态:" label-width="100px">
              <el-select v-model="form.approvalStatus" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in approvalList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
              <el-button size="small" type="primary" @click="submitBtn">查 询</el-button>
              <el-button size="small" @click="clearSearch">重 置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div class="AdrestInfo-body">
      <div style="text-align: right">
        <el-button size="small" plain @click="userComleave">
          使用调休
        </el-button>
      </div>
      <el-table
        v-loading="loading"
        :data="crewDataList"
        :default-sort="{prop: 'comleaveData', order: 'descending'}"
        height="730"
        style="width: 100%"
        stripe
        align = "center"
        element-loaging-text= "数据加载中"
        element-loading-spinner= "el-icon-loading"
        element-loading-background= "gba(0,0,0,0.8)"
      >
        <el-table-column label="序号" align="center" type = "index" width="50"/>
        <el-table-column :formatter="timeStyple" prop="comleaveData" label="调休日期" sortable align="center" width="50"/>
        <el-table-column prop="intergNum" label="调休长度(天)" align="center" width="50"/>
        <el-table-column prop="comleaveReason" label="原因(备注)" align="center" width="50"/>
        <el-table-column prop="approvalStatus" label="审批状态" align="center" width="50">
          <template slot-scope="scope">
            <span>{{ getDictName(personAttrDictId,scope.row.approvalStatus) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="approverName" label="当前审批人" align="center" width="50"/>
        <el-table-column prop="comleaveBackReason" label="退回原因" align="center" width="50">
          <template slot-scope="scope">
            <span v-if="scope.row.approvalStatus !=='0' && scope.row.approvalStatus !=='1'">{{ scope.row.comleaveBackReason }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" >
          <template slot-scope="scope">
            <el-link
              v-if = "scope.row.approvalStatus==='0' || scope.row.approvalStatus==='1' || scope.row.approvalStatus==='2' || scope.row.approvalStatus==='3' "
              type="primary"
              size="mini"
              @click="handDetail(scope.row)">
              详情
            </el-link>
            <el-link
              v-if = "scope.row.approvalStatus==='2' || scope.row.approvalStatus==='3'"
              type="primary"
              size="mini"
              @click="handEdit(scope.row)">
              编辑
            </el-link>
            <el-link
              v-if = "scope.row.approvalStatus==='2' || scope.row.approvalStatus==='3'"
              type="primary"
              size="mini"
              @click="handDelete(scope.row)">
              删除
            </el-link>
            <el-link
              v-if = "scope.row.approvalStatus==='2'"
              type="primary"
              size="mini"
              @click="handSubmit(scope.row)">
              提交
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
          title="使用调休"
          width="40%"
        >
          <el-form ref="crewData" :model="crewData" :rules="rules" label-width="120px">
            <el-form-item label="姓名" prop ="empName" >
              <el-input v-model="crewData.empName" :disabled="true" style="width: 600px"/>
            </el-form-item>
            <el-form-item label="工号" prop ="badge" >
              <el-input v-model="crewData.badge" :disabled="true" style="width: 600px"/>
            </el-form-item>
            <el-form-item label="调休日期" prop ="comleaveData" >
              <!-- <span>{{ crewData }}</span> -->
              <el-date-picker
                v-model="crewData.comleaveData"
                :disabled="userLeaveChild"
                :picker-options="pickerOptions"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
                style="width: 600px"/>
            </el-form-item>
            <el-form-item label="调休时间(天)" prop ="intergNum" >
              <el-select v-model="crewData.intergNum" :disabled="userLeaveChild" placeholder="请选择调休时间（天）" style="width: 150px" @change="judgeIntergNum" >
                <el-option :value= "0.5" label="0.5" />
                <el-option :value= "1" label="1" />
              </el-select>
            </el-form-item>
            <el-form-item label="备注" prop ="comleaveReason" >
              <el-input v-model="crewData.comleaveReason" :rows="2" :disabled="userLeaveChild" type="textarea" maxlength="300" show-word-limit style="width: 600px"/>
            </el-form-item>
          </el-form>
          <span slot="footer" style="display: flex; justify-content: space-bwtween: align-items: center; width: 100%">
            可使用调休: {{ generateLeave }}天
          </span>
          <span slot="footer">
            <el-button type="primary" @click="userLeave = false">取消</el-button>
            <el-button v-if="!userLeaveChild" type="primary" @click="submit">提交</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
// import common from '@/api/common'
import pager from '@/components/Pager'
import { pagerList, submitRest, deleteRest, userRest } from '@/api/bankTx/integInfo'
import store from '@/store'
import Moment from 'moment'
// eslint-disable-next-line
import { getDictName } from '@/utils/dict'
export default {
  name: 'AdrestInfo',
  components: {
    pager
  },
  data() {
    const today = new Date()
    const endOfYear = new Date(today.getFullYear(), 11, 31)
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() <
          today.getTime() || time.getTime() > endOfYear.getTime()
        }
      },
      personAttrDictId: 'NEWKG_RD_APPROVAL_STATUS', // 字典值
      userLeaveChild: false,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      totalNumber: 0,
      value: '',
      name: '',
      loading: false,
      form: {
        entryTime: [], // 时间区间
        // startDate: '', // 开始日期
        // endDate: '', // 结束日期
        approvalStatus: '' // 审批状态
      },
      approvalList: [
        {
          value: 0,
          label: '审批通过'
        },
        {
          value: 1,
          label: '审批中'
        },
        {
          value: 2,
          label: '草稿'
        },
        {
          value: 3,
          label: '退回'
        }
      ],
      crewDataList: [], // 调休信息列表
      userLeave: false,
      crewData: {
        empName: '', // 姓名
        badge: '', // 工号
        comleaveData: '',
        approvalDate: '', // 调休日期
        intergNum: 0, // 调休时间(天)
        comleaveReason: '', // 备注
        approvalStatus: '' // 审批状态
      }, // 调休信息
      generateLeave: 0,
      rules: {
        empName: [
          { required: true, message: '请输入姓名工号', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '姓名工号不能为空格', trigger: 'blur' }
        ],
        badge: [
          { required: true, message: '请输入姓名工号', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '姓名工号不能为空格', trigger: 'blur' }
        ],
        comleaveData: [
          { required: true, message: '请选择日期', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '选择日期不能为空格', trigger: 'blur' }
        ],
        intergNum: [
          { required: true, message: '请选择调休时间（天）', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '调休时间不能为空格', trigger: 'blur' }
        ],
        comleaveReason: [
          { required: true, message: '请输入备注', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '备注不能为空格', trigger: 'blur' }
        ]
      }

    }
  },
  created() {
    this.name = store.getters.name
    this.badge = store.getters.userId
    const params = {}
    params.enpName = this.name
    params.badge = this.badge
    userRest(params).then(res => {
      if (res.data.status === 200) {
        this.generateLeave = res.data.results
      }
    })
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
    /**
     * 查询
     */
    submitBtn() {
      this.getPager(this.size, this.index)
    },
    // 判断调休时长
    judgeIntergNum() {
      if (this.crewData.intergNum > this.generateLeave) {
        this.$alert('当前积分不足', { type: 'warning' })
        this.crewData.intergNum = 0
      }
    },
    // 分页
    getPager(size, index) {
      const params = { ...this.form }
      params.size = size
      params.index = index
      params.empName = this.name
      params.badge = this.badge
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 重置
    clearSearch() {
      this.form.entryTime = []
      this.form.approvalStatus = ''
      this.submitBtn()
    },
    getPagerList(params) {
      this.loading = true
      const self = this
      pagerList(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.crewDataList = res.data.results
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.message.error(res.data.message)
        }
      })
    },
    /**
     * 使用调休提交
     */
    submit() {
      this.$refs.crewData.validate((valid) => {
        const self = this
        const date = self.crewDataList.some(item => {
          debugger
          if (item.comleaveData.split('T')[0] === this.crewData.comleaveData && item.intergNum + this.crewData.intergNum > 1) {
            if (item.approvalStatus === '0' || item.approvalStatus === '1') {
              return true
            }
          }
        })
        if (date) {
          this.$alert('当日调休已使用', { type: 'warning' })
          return
        }
        if (valid) {
          this.$confirm('确认提交', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
            .then(_ => {
              this.crewData.approvalStatus = '1'
              submitRest(this.crewData).then(res => {
                if (res.data.status === 200) {
                  this.$message.success('提交成功')
                } else {
                  this.$message.error(res.data.message)
                }
                this.submitBtn()
              }
              )
              this.userLeaveCancel()
            })
        }
      })
    },
    // 详情
    handDetail(data) {
      this.userLeaveChild = true
      this.userLeave = true
      this.crewData = data
    },
    // 编辑
    handEdit(data) {
      this.userLeave = true
      this.userLeaveChild = false
      this.crewData = data
    },
    // 删除
    handDelete(data) {
      this.$confirm('确认删除', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          deleteRest(data).then(res => {
            if (res.data.status === 200) {
              this.$message.success('删除成功')
            } else {
              this.$message.error(res.data.message)
            }
            this.submitBtn()
          })
        })
    },
    // 提交
    handSubmit(data) {
      this.crewData = data
      this.submit()
    },
    // 时间格式化
    timeStyple: function(row, cloumn) {
      let time = ''
      if (row.comleaveData != null) {
        time = Moment(row.comleaveData).format('YYYY-MM-DD')
      }
      return time
    },
    // crewData: {
    //     empName: '', // 姓名
    //     badge: '', // 工号
    //     approvalDate: '', // 调休日期
    //     intergNum: 0, // 调休时间(天)
    //     comleaveReason: '', // 备注
    //     approvalStatus: '' // 审批状态
    //   },
    handleClose() {
      debugger
      const date = {}
      date.empName = this.crewData.empName
      date.badge = this.crewData.badge
      date.comleaveData = this.crewData.comleaveData
      date.intergNum = this.crewData.intergNum
      date.comleaveReason = this.crewData.comleaveReason
      date.approvalStatus = '2'
      if (this.userLeaveChild === true) {
        this.userLeaveCancel()
        return
      }
      this.$confirm('是否保存', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          submitRest(date).then(res => {
            if (res.data.status === 200) {
              this.$message.success('已存入草稿')
            } else {
              this.$message.error(res.data.message)
            }
            this.submitBtn()
          }
          )
        }
        )
      this.userLeaveCancel()
    },
    userComleave() {
      // this.crewData.intergNum = null
      // this.crewData.comleaveReason = ''
      // this.crewData.comleaveData = ''
      this.crewData.empName = this.name
      this.crewData.badge = this.badge
      const params = {}
      params.enpName = this.name
      params.badge = this.badge
      userRest(params).then(res => {
        if (res.data.status === 200) {
          this.generateLeave = res.data.results
          if (res.data.results <= 0) {
            this.$alert('当前积分不足', { type: 'warning' })
          } else {
            this.userLeave = true
            this.userLeaveChild = false
          }
        } else {
          this.$message.error(res.data.message)
        }
        this.submitBtn()
      })
    },
    // 取消弹窗
    userLeaveCancel() {
      this.userLeave = false
      this.crewData = {}
      this.crewData.backReason = ''
    }
  }
}
</script>

<style scoped>
.AdrestInfo-container {
  padding: 10px;
}
.AdrestInfo-head {
  background-color: #ffffff;
  padding: 10px;
}
.AdrestInfo-button {
  padding: 10px;
  background-color: #ffffff;
}
.AdrestInfo-body {
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
