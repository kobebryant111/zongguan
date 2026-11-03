<!-- 未确认积分表 -->
<template>
  <div class="list-container interviewList">
    <!-- 修改后 -->
    <el-form inline>
      <div class="interviewList_head">
        <el-row>
          <el-col :span="100">
            <div class="searchClass">
              <el-form-item label="日期" label-width="150">
                <el-date-picker
                  v-model="searchForm.entryTime"
                  type="daterange"
                  size="large"
                  range-separator="-"
                  value-format="yyyy-MM-dd"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"/>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="100">
            <el-form-item>
              <el-button @click="clearSearch">重 置</el-button>
              <el-button type="primary" @click="submitBtn">查 询</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>

    <div class="employeeContainer">
      <div class="projectContainer_head">
        <div class="projectContainer_head_en"/>
        <el-row type="flex" justify="end">
          <el-col :span="12">
            <button-orange :button-name="reregist" :size="sizes" :fns="buttonReregist"/>
          </el-col>
          <el-col :span="12">
            <button-orange :button-name="submitInteg" :size="sizes" :fns="submitIntegInfo"/>
          </el-col>
        </el-row>
      </div>
      <el-table
        :data="crewData"
        fit
        border
        highlight-current-row
        height="510"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="序号" prop="index" width="55" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :show-overflow-tooltip="true"
          :formatter="registerDataFormat"
          label="登记日期"
          sortable
          prop="registerData"
          width="150"
        />
        <el-table-column :show-overflow-tooltip="true" label="日期类型" prop="dataType" width="150" >
          <template slot-scope="scope">
            <span v-if="!scope.row.edited">{{ getDictName(dataTypeDictId,scope.row.dataType) }}</span>
            <el-select v-if="scope.row.edited" v-model="scope.row.dataType" >
              <el-option
                v-for="item in getDataTypeOptions"
                :key="item.dictId"
                :label="item.dictName"
                :value="item.dictId"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          :show-overflow-tooltip="true"
          :formatter="startTimeFormat"
          label="开始时间"
          prop="startTime"
          width="200"
        />
        <el-table-column
          :show-overflow-tooltip="true"
          :formatter="endTimeFormat"
          label="结束时间"
          prop="endTime"
          width="200"
        />
        <el-table-column
          :show-overflow-tooltip="true"
          label="产生调休（天）"
          prop="integNum"
          width="100"
        />
        <el-table-column :show-overflow-tooltip="true" label="工作分类" prop="workType" width="100">
          <template slot-scope="scope">
            <span v-if="!scope.row.edited">{{ getDictName(workTypeDictId,scope.row.workType) }}</span>
            <el-select v-if="scope.row.edited" v-model="scope.row.workType">
              <el-option
                v-for="item in getWorkTypeOptions"
                :key="item.dictId"
                :label="item.dictName"
                :value="item.dictId"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          :show-overflow-tooltip="true"
          label="原因"
          prop="integReason"
          width="100"
        />
        <el-table-column
          :show-overflow-tooltip="true"
          label="退回原因"
          prop="integBackReason"
          width="100"
        />
        <el-table-column label="操作" width="122">
          <template slot-scope="scope">
            <el-link
              :underline="false"
              type="warning"
              @click="delRow(scope.row)"
            >删除</el-link
            >
            <el-link
              :underline="false"
              type="warning"
              @click="handelEdit(scope.row)"
            >编辑</el-link
            >
            <el-link
              :underline="false"
              type="warning"
              @click="submitInfo(scope.row)"
            >提交</el-link
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pager_zb">
        <pager
          ref = "pager"
          :fns="getPager"
          :total="totalNumber"
          :page-size="size"
          v-model="$attrs"
        />
      </div>
    </div>

    <!-- dialog -->
    <div>
      <template>
        <el-dialog :title="title" :visible.sync="dialogClose" width="30%">
          <el-form ref="menuForm" :model="menuForm" :rules="rules" status-icon label-width="100px" >
            <el-form-item v-if="isFasle" label="主键ID:" prop="detailId">
              <el-input v-model="menuForm.detailId" disabled class="form-input"/>
            </el-form-item>
            <el-form-item v-if="isFasle" label="用户姓名:" prop="detailId">
              <el-input v-model="menuForm.empName" disabled class="form-input"/>
            </el-form-item>
            <el-form-item v-if="isFasle" label="用户工号:" prop="detailId">
              <el-input v-model="menuForm.badge" disabled class="form-input"/>
            </el-form-item>
            <el-form-item label="姓 名/工 号:" prop="nameBadge" >
              <el-input v-model="menuForm.nameBadge" disabled class="form-input"/>
            </el-form-item>
            <el-form-item label="登  记  日  期:" prop="registerData" style="margin-top:20px" >
              <el-date-picker
                v-model="menuForm.registerData"
                type="date"
                placeholder="选择日期"
                class="form-input"
                value-format="yyyy-MM-dd"/>
            </el-form-item>
            <el-form-item label="开  始  时  间:" prop="startTime" style="margin-top:20px" >
              <el-date-picker
                v-model="menuForm.startTime"
                type="datetime"
                placeholder="选择时间"
                class="form-input"
                value-format="yyyy-MM-dd HH:mm:ss"
                @blur="getIsHoliday"/>
            </el-form-item>
            <el-form-item label="结  束  时  间:" prop="endTime" style="margin-top:20px" >
              <el-date-picker
                v-model="menuForm.endTime"
                type="datetime"
                placeholder="选择时间"
                class="form-input"
                value-format="yyyy-MM-dd HH:mm:ss"/>
            </el-form-item>
            <el-form-item label="工  作  分  类:" prop="workType" style="margin-top:20px">
              <el-select v-model="menuForm.workType" placeholder="请选择" style="width: 300px;">
                <el-option
                  v-for="item in getWorkTypeOptions"
                  :key="item.dictId"
                  :label="item.dictName"
                  :value="item.dictId"/>
              </el-select>
            </el-form-item>
            <el-form-item label="原         因:" prop="integReason" style="margin-top:20px" >
              <el-input v-model="menuForm.integReason" :placeholder="placeholderStr" type="textarea" maxlength="200" class="form-input"/>
            </el-form-item>
          </el-form>
          <div class="dialog-footer">
            <el-button @click="dialogCancel">取消</el-button>
            <el-button type="primary" @click="dialogAddSubmit">保存</el-button>
          </div>
        </el-dialog>
      </template>
    </div>
  </div>
</template>
<script>
import common from '@/api/common'
import { queryPager, del, saveInfo, submitMsg, submit, judgIsHoliday } from '@/api/bankTx/integInfoOpera'
import pager from '@/components/Pager'
import cleanBtn from '@/components/CleanBtn'
import buttonOrange from '@/components/eleButton/buttonOrange' // 引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import Moment from 'moment'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'

export default {
  name: 'UnrecongInteg',
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      dataTypeDictId: 'UNRECOG_INTEG_DATA_ENCOD',
      workTypeDictId: 'UNRECOG_INTEG_WORK_ENCOD',
      title: '', // dialog 标题
      placeholderStr: '请输入原因',
      isFasle: false,
      isHg: true,
      searchForm: {
        registerData: null, // 登记日期
        dataType: null, // 日期类型
        startTime: null, // 开始时间
        endTime: null, // 结束日期
        integNum: null, // 产生调休（天）
        workType: null, // 工作分类
        integReason: null, // 原因
        integBackReason: null // 退回原因
      },
      ids: [], // 积分信息表主键
      dialogClose: false, // dialog 显示
      isHoliday: '', // 是否是节假日
      menuForm: { // 表单
        empName: '', // 姓名
        badge: '', // 工号
        nameBadge: '', // 姓名/工号
        registerData: '', // 登记日期
        startTime: '', // 开始时间
        endTime: '', // 结束时间
        workType: '', // 工作分类
        integReason: '' // 原因
      },
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      crewData: [], // 列表分页数据
      reregist: '补登记', // 发起需求按钮
      submitInteg: '提交', // 发起提交信息请求按钮
      sizes: 'small', // 按钮大小
      rules: {
        nameBadge: [
          { required: true, message: '姓名/工号不能为空', trigger: 'blur' }
        ],
        registerData: [
          { required: true, message: '请输入登记日期', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '请输入开始时间', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '请输入结束时间', trigger: 'blur' }
        ],
        workType: [
          { required: true, message: '请选择工作分类', trigger: 'blur' }
        ],
        integReason: [
          { required: true, message: '请 输 入 原 因', trigger: 'blur' }
        ]
      }
    }
  },

  computed: {
    getWorkTypeOptions() {
      return getTypeDicts(this.workTypeDictId)
    },
    getDataTypeOptions() {
      return getTypeDicts(this.dataTypeDictId)
    }
  },

  created() {
    this.loadInit()
  },

  methods: {
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.detailId).join()
    },
    /**
     * 分页初始化方法
     */
    loadInit: function() {
      const params = {}
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },

    // 获取列表数据
    getPagerList(params) {
      const data = common.paramsFormat(params)
      const self = this
      queryPager(data).then(res => {
        if (res.data.status === 200) {
          self.crewData = res.data.results
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            this.$message.warning('未查询到相关数据!')
            return false
          }
          this.$message.success('数据查询成功!')
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 编辑
    handelEdit(params) {
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
      this.title = '登记'
      this.dialogClose = true
      this.menuForm.detailId = params.detailId
      this.menuForm.empName = params.empName
      this.menuForm.badge = params.badge
      this.menuForm.nameBadge = params.empName + '(' + params.badge + ')'
      this.menuForm.registerData = Moment(params.endTime).format('YYYY-MM-DD')
      this.menuForm.startTime = Moment(params.startTime).format('YYYY-MM-DD HH:mm:ss')
      this.menuForm.endTime = Moment(params.endTime).format('YYYY-MM-DD HH:mm:ss')
      this.menuForm.workType = params.workType
      this.menuForm.integReason = params.integReason
      this.getIsHoliday()
    },

    // 补登记
    buttonReregist() {
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
      this.title = '补登记'
      this.placeholderStr = '请说明补登记原因'
      this.dialogClose = true
      // eslint-disable-next-line no-unused-vars
      const userId = this.$store.getters.userId
      // eslint-disable-next-line no-unused-vars
      const userName = this.$store.getters.name
      this.menuForm.nameBadge = userName + '(' + userId + ')'
      this.menuForm.empName = userName
      this.menuForm.badge = userId
    },

    submitInfo(val) {
      const loading = common.loading(this)
      submit(val)
        .then(res => {
          loading.close()
          if (res.data.status === 200) {
            this.$message.success('数据提交成功')
            this.submitBtn()
          } else {
            this.$message.error('数据提交失败')
          }
        })
        .catch(error => {
          loading.close()
          this.$message.error(error)
        })
    },

    submitIntegInfo() {
      if (this.ids.length <= 0) {
        this.$alert('最少选择一条数据', { type: 'warning' })
        return false
      }
      const param = { ids: this.ids }
      const datas = common.paramsFormat(param)
      this.$confirm('是否确定提交选中的数据项？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(_ => {
          submitMsg(datas).then(res => {
            if (res.data.status !== 200) {
              this.$alert(res.data.message, { type: 'error' })
            } else {
              this.dialogCancel()
              this.$message({
                type: 'success',
                message: res.data.message
              })
              this.dialogClose = false
            }
            this.loadInit()
          })
        })
        .catch(_ => { })
    },

    /**
     * 取消按钮,关闭dialog
     */
    dialogCancel: function() {
      this.dialogClose = false
      Object.keys(this.menuForm).forEach(key => { this.menuForm[key] = '' })
    },

    /**
     * 判断当前填写的日期是否符合添加积分的规则
     */
    integVerify(datas) {
      this.isHg = true
      const startTime = datas.startTime
      const endTime = datas.endTime
      var dataStart = new Date(startTime).getTime()
      var dataEnd = new Date(endTime).getTime()

      const date = (dataEnd - dataStart) / 1000
      // 计算出时间差小时数
      var leavel = date / (3600)

      if (this.isHoliday === '1') {
        if (leavel < 4) {
          this.isHg = false
        }
      } else if (this.isHoliday === '0') {
        const morning5NextDay = this.getQueryTime(startTime, 5, 1)
        var morning5Next = new Date(morning5NextDay).getTime()
        const num = dataEnd - morning5Next
        if (num > 0) {
          this.isHg = false
        }

        const todayMorning23 = this.getQueryTime(startTime, 23, 0)
        var today23hours = new Date(todayMorning23).getTime()
        const num1 = dataEnd - today23hours
        if (num1 < 0) {
          this.isHg = false
        }
      }
    },

    getQueryTime(dateString, hours, addDay) {
      const date = new Date(dateString)
      date.setDate(date.getDate() + addDay)
      date.setHours(hours)
      date.setMinutes(0)
      date.setSeconds(0)
      date.setMilliseconds(0)

      function formatDate(date) {
        const year = date.getFullYear()
        const month = ('0' + (date.getMonth() + 1)).slice(-2)
        const day = ('0' + (date.getDate())).slice(-2)
        const hours = ('0' + (date.getHours())).slice(-2)
        const minutes = ('0' + (date.getMinutes())).slice(-2)
        const seconds = ('0' + (date.getSeconds())).slice(-2)
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
      }
      return formatDate(date)
    },

    /**
     * 判断是否是节假日
     */
    getIsHoliday() {
      this.isHoliday = ''
      const param = { startTime: this.menuForm.startTime }
      const datas = common.paramsFormat(param)
      judgIsHoliday(datas).then(res => {
        if (res.data.status === 200) {
          this.isHoliday = res.data.results
        }
      })
    },

    /**
     *  提交保存
     */
    dialogAddSubmit: function() {
      const datas = common.paramsFormat(this.menuForm)
      this.integVerify(this.menuForm)
      if (!this.isHg) {
        this.$alert('不符合积分生成规则，请重新输入。', { type: 'warning' })
        return false
      }

      console.log('保存数据datas', datas)
      this.$refs.menuForm.validate((valid) => {
        if (valid) {
          saveInfo(datas).then(res => {
            if (res.data.status !== 200) {
              this.$alert(res.data.message, { type: 'error' })
            } else {
              this.dialogCancel()
              this.$message({
                type: 'success',
                message: res.data.message
              })
              this.dialogClose = false
              this.loadInit()
            }
          })
        } else {
          this.$message({
            showClose: true,
            message: '操作失败,请填写必填项',
            type: 'error'
          })
          return false
        }
      })
    },

    // 删除行
    delRow(val) {
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const loading = common.loading(this)
          del(val)
            .then(res => {
              loading.close()
              if (res.data.status === 200) {
                this.$message.success('删除成功')
                this.submitBtn()
              } else {
                this.$message.error('删除失败')
              }
            })
            .catch(error => {
              loading.close()
              this.$message.error(error)
            })
        })
        // eslint-disable-next-line handle-callback-err
        .catch(error => {})
    },
    // 时间格式化
    startTimeFormat: function(row, cloumn) {
      let time = ''
      if (row.startTime != null) {
        time = Moment(row.startTime).format('YYYY/MM/DD HH:mm:ss')
      }
      return time
    },
    endTimeFormat: function(row, cloumn) {
      let time = ''
      if (row.endTime != null) {
        time = Moment(row.endTime).format('YYYY/MM/DD HH:mm:ss')
      }
      return time
    },
    registerDataFormat: function(row, cloumn) {
      let time = ''
      if (row.registerData != null) {
        time = Moment(row.registerData).format('YYYY/MM/DD')
      }
      return time
    },

    /**
     * 搜索事件
     */
    submitBtn: function() {
      const params = this.searchForm
      this.getPagerList(params)
    },

    // 重置按钮
    clearSearch() {
      this.searchForm.entryTime = ''
    },

    /**
     * 分页触发
     */
    getPager: function(size, index) {
      const params = {}
      params.entryTime = this.searchForm.entryTime
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    }
  }
}
</script>
<style scoped>
.form-input {
  width: 300px;
}
.dialog-footer {
    margin-top: 10px;
    text-align: right;
}

.td-right {
  text-align: right;
}

.td-left {
  text-align: left;
}

.interviewList {
  padding: 15px;
}

.required {
    color: red;
}

.interviewList_head {
  background-color: #ffffff;
  padding: 18px;
  display: flex;
  justify-content: space-between;
}

.employeeContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
}

.projectContainer_head {
  float: right;
  margin-bottom: 10px;
}

.projectContainer_head_en {
  width: 160px;
}

.requireList_head_right {
  float: right;
}

.requireList_head_right div {
  display: inline-block;
}

.requireList_head_right_clean {
  margin-right: 10px;
}

.interviewList_head_right_orange {
  margin: 0 6px 0 10px;
}

/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
</style>
<style>
/* el-input修改 */
.interviewList .el-input--prefix .el-input__inner {
  padding-left: 80px;
}

.interviewList .el-input__prefix {
  left: 5px;
  color: #262626;
  font-size: 14px;
}

/* element表单修改 */
.interviewList .el-form-item {
  margin-bottom: 0px;
}

/* 表头背景色 */
.interviewList .el-table th {
  background: #fafafa;
}
.searchClass .el-form-item__content {
  width: 65% !important;
}
</style>
