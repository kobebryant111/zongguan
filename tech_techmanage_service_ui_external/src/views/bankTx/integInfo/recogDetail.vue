<!-- 已确认积分表 -->
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
      </div>
      <el-table
        :data="crewData"
        fit
        border
        highlight-current-row
        height="510"
      >
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
  </div>
</template>
<script>
import common from '@/api/common'
import { queryPagerForRecog } from '@/api/bankTx/integInfoOpera'
import pager from '@/components/Pager'
import Moment from 'moment'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'

export default {
  name: 'ApproverDetail',
  components: { pager },
  data() {
    return {
      dataTypeDictId: 'UNRECOG_INTEG_DATA_ENCOD',
      workTypeDictId: 'UNRECOG_INTEG_WORK_ENCOD',
      searchForm: {
        registerData: null, // 登记日期
        dataType: null, // 日期类型
        startTime: null, // 开始时间
        endTime: null, // 结束日期
        integNum: null, // 产生调休（天）
        workType: null, // 工作分类
        integReason: null, // 原因
        integCurrentApprover: null // 当前审批人
      },
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
      crewData: [] // 列表分页数据
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
      queryPagerForRecog(data).then(res => {
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
