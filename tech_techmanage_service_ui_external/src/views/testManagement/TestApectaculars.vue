<!-- 测试看板 -->
<template>
  <div class="list-container interviewList">
    <el-form v-show="showSearch" inline>
      <div class="interviewList_head">
        <el-row>
          <el-form-item label="故事编号:" label-width="73px" placeholder="请填入故事编号">
            <el-input v-model="searchForm.storyNum"/>
          </el-form-item>
          <el-form-item label="用户故事:" label-width="73px" placeholder="请填入用户故事">
            <el-input v-model="searchForm.storyName"/>
          </el-form-item>
          <el-form-item label="计划状态:" label-width="73px">
            <el-select v-model="searchForm.planState" filterable placeholder="请选择状态">
              <el-option
                v-for="item in planState_options"
                :key="item.dictid"
                :label="item.dictname"
                :value="item.dictid"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="上线时间:" label-width="73px">
            <el-date-picker
              v-model="searchForm.entryTime"
              type="daterange"
              range-separator="-"
              value-format="yyyy-MM-dd"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              style="width: 80%"/>
          </el-form-item>
          <el-form-item>
            <el-button @click="clearSearch">重 置</el-button>
            <el-button type="primary" @click="queryBtn">查 询</el-button>
          </el-form-item>
          <div class="testApectacularsContainer_head">
            <el-row type="flex" justify="space-between">
              <button-orange :button-name="downLoadButname" :size="sizes" :fns="downLoadMsg"/>
            </el-row>
          </div>
        </el-row>
      </div>
    </el-form>
    <div>
      <div class="testApectacularsContainer">
        <right-toolbar :show-search.sync="showSearch" :columns="columns" @queryTable="queryBtn" @doLayoutTable="toDoLayout"/>
      </div>
      <el-table
        ref="multipleTable"
        :data="crewData"
        border
        highlight-current-row
        height="510"
      >
        <el-table-column
          v-if="columns[0].visible"
          :fixed="columns[0].fixed"
          :show-overflow-tooltip="true"
          label="故事编号"
          prop="storyNum"
          width="100"
        />
        <el-table-column
          v-if="columns[1].visible"
          :fixed="columns[1].fixed"
          :show-overflow-tooltip="true"
          label="故事名称"
          prop="storyName"
          width="100"
        />
        <el-table-column
          v-if="columns[2].visible"
          :fixed="columns[2].fixed"
          :show-overflow-tooltip="true"
          :formatter="onLineDateFormat"
          label="上线时间"
          sortable
          prop="onLineDate"
          width="150"
        />
        <el-table-column
          v-if="columns[3].visible"
          :fixed="columns[3].fixed"
          :show-overflow-tooltip="true"
          label="计划状态"
          prop="planState"
          width="100">
          <template slot-scope="scope">
            <span v-if="!scope.row.edited">{{ getDictName(planStateDictId,scope.row.planState) }}</span>
            <el-select v-if="scope.row.edited" v-model="scope.row.planState">
              <el-option
                v-for="item in getPlanStateOptions"
                :key="item.dictId"
                :label="item.dictName"
                :value="item.dictId"
              />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column
          v-if="columns[4].visible"
          :fixed="columns[4].fixed"
          :show-overflow-tooltip="true"
          label="冒烟案例数"
          prop="smokeCaseNum"
          width="100"
        />
        <el-table-column
          v-if="columns[5].visible"
          :fixed="columns[5].fixed"
          :show-overflow-tooltip="true"
          label="SIT案例数"
          prop="sitCaseNum"
          width="100"
        />
        <el-table-column
          v-if="columns[6].visible"
          :fixed="columns[6].fixed"
          :show-overflow-tooltip="true"
          label="UAT案例数"
          prop="uatCaseNum"
          width="100"
        />
        <el-table-column
          v-if="columns[7].visible"
          :fixed="columns[7].fixed"
          :show-overflow-tooltip="true"
          label="测试案例总数"
          prop="testCaseAmount"
          width="100"
        />
        <el-table-column
          v-if="columns[8].visible"
          :fixed="columns[8].fixed"
          :show-overflow-tooltip="true"
          label="功能正案例数"
          prop="funProCaseNum"
          width="100"
        />
        <el-table-column
          v-if="columns[9].visible"
          :fixed="columns[9].fixed"
          :show-overflow-tooltip="true"
          label="整体反案例总数"
          prop="entProCaseAmount"
          width="100"
        />
        <el-table-column
          v-if="columns[10].visible"
          :fixed="columns[10].fixed"
          :show-overflow-tooltip="true"
          label="功能反案例数"
          prop="funContraCaseNum"
          width="100"
        />
        <el-table-column
          v-if="columns[11].visible"
          :fixed="columns[11].fixed"
          :show-overflow-tooltip="true"
          label="整体正案例总数"
          prop="entContraCaseAmount"
          width="100"
        />
        <el-table-column
          v-if="columns[12].visible"
          :fixed="columns[12].fixed"
          :show-overflow-tooltip="true"
          label="功能正反案例比"
          prop="funProetCasescale"
          width="100"
        />
        <el-table-column
          v-if="columns[13].visible"
          :fixed="columns[13].fixed"
          :show-overflow-tooltip="true"
          label="整体正反案例比"
          prop="entProetCasescale"
          width="100"
        />
        <el-table-column
          v-if="columns[14].visible"
          :fixed="columns[14].fixed"
          :show-overflow-tooltip="true"
          label="高级缺陷数"
          prop="highDefectNum"
          width="100"
        />
        <el-table-column
          v-if="columns[15].visible"
          :fixed="columns[15].fixed"
          :show-overflow-tooltip="true"
          label="中级缺陷数"
          prop="midDefectNum"
          width="100"
        />
        <el-table-column
          v-if="columns[16].visible"
          :fixed="columns[16].fixed"
          :show-overflow-tooltip="true"
          label="低级缺陷数"
          prop="lowDefectNum"
          width="100"
        />
        <el-table-column
          v-if="columns[17].visible"
          :fixed="columns[17].fixed"
          :show-overflow-tooltip="true"
          label="SIT缺陷率"
          prop="sitDefectScale"
          width="100"
        />
        <el-table-column
          v-if="columns[18].visible"
          :fixed="columns[18].fixed"
          :show-overflow-tooltip="true"
          label="UAT缺陷率"
          prop="uatDefectScale"
          width="100"
        />
        <el-table-column
          v-if="columns[19].visible"
          :fixed="columns[19].fixed"
          :show-overflow-tooltip="true"
          label="预估工作量"
          prop="forecastWorkload"
          width="100"
        />
        <el-table-column
          v-if="columns[20].visible"
          :fixed="columns[20].fixed"
          :show-overflow-tooltip="true"
          label="实际工作量"
          prop="reaWorkload"
          width="100"
        />
        <el-table-column
          v-if="columns[21].visible"
          :fixed="columns[21].fixed"
          :show-overflow-tooltip="true"
          label="偏差比"
          prop="deviationRatio"
          width="100"
        />
        <el-table-column
          v-if="columns[22].visible"
          :fixed="columns[22].fixed"
          :show-overflow-tooltip="true"
          :formatter="planDeliveryDateFormat"
          label="计划交付日期"
          prop="planDeliveryDate"
          width="200"
        />
        <el-table-column
          v-if="columns[23].visible"
          :fixed="columns[23].fixed"
          :show-overflow-tooltip="true"
          :formatter="reaDeliveryDateFormat"
          label="实际交付日期"
          prop="reaDeliveryDate"
          width="200"
        />
        <el-table-column
          v-if="columns[24].visible"
          :fixed="columns[24].fixed"
          :show-overflow-tooltip="true"
          label="当前测试进度"
          prop="curTestProgress"
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
import { queryPager, queryPagerInfo } from '@/api/testManagementApi/testApectaculars'
import pager from '@/components/Pager'
import cleanBtn from '@/components/CleanBtn'
import buttonOrange from '@/components/eleButton/buttonOrange' // 引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import Moment from 'moment'
// eslint-disable-next-line no-unused-vars
import { getDictName, getTypeDicts } from '@/utils/dict'
import { getDicEntry } from '@/api/eosDictEntry'

export default {
  name: 'TestApectaculars',
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: { pager, cleanBtn, buttonOrange, buttonCommon },
  data() {
    return {
      showSearch: true,
      planStateDictId: 'PLAN_STATE',
      searchForm: {
        storyNum: null, // 故事编号
        storyName: null, // 用户故事
        planState: null, // 计划状态
        entryTime: null // 上线时间
      },
      ids: [], // 积分信息表主键
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      crewData: [], // 列表分页数据
      planState_options: [], // 计划状态下拉框
      downLoadButname: '下载', // 发起提交信息请求按钮
      sizes: 'small', // 按钮大小
      columns: [
        { key: 0, label: '故事编号', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 1, label: '故事名称', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 2, label: '上线时间', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 3, label: '计划状态', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 4, label: '冒烟案例数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 5, label: 'SIT案例数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 6, label: 'UAT案例数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 7, label: '测试案例总数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 8, label: '功能正案例数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 9, label: '整体反案例总数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 10, label: '功能反案例数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 11, label: '整体正案例总数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 12, label: '功能正反案例比', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 13, label: '整体正反案例比', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 14, label: '高级缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 15, label: '中级缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 16, label: '低级缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 17, label: 'SIT缺陷率', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 18, label: 'UAT缺陷率', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 19, label: '预估工作量', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 20, label: '实际工作量', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 21, label: '偏差比', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 22, label: '计划交付日期', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 23, label: '实际交付日期', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { key: 24, label: '当前测试进度', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false }
      ]
    }
  },

  computed: {
    getPlanStateOptions() {
      return getTypeDicts(this.planStateDictId)
    }
  },

  created() {
    this.getDic()
    this.loadInit()

    this.getPagerList()
  },

  methods: {
    /**
     * 分页初始化方法
     */
    loadInit: function() {
      this.getDic()
      const params = {}
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },

    // 查询业务字典
    getDic() {
      getDicEntry({
        dicttypeid: 'PLAN_STATE'
      }).then(res => {
        if (res.data.status !== 200) {
          this.$alert(res.data.message, { type: 'error' })
          return false
        }
        const data = res.data.results
        // 状态
        this.planState_options = data.filter(item => {
          return item.dicttypeid === 'PLAN_STATE'
        })
        return data
      })
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

    downLoadMsg() {
      const self = this
      const params = this.searchForm
      params._isDown = 'Y'
      queryPagerInfo(params).then(res => {
        common.download(res.data, self, '测试看板信息表.xls')
      })
    },

    // 时间格式化
    planDeliveryDateFormat: function(row, cloumn) {
      let time = ''
      if (row.startTime != null) {
        time = Moment(row.startTime).format('YYYY/MM/DD HH:mm:ss')
      }
      return time
    },
    reaDeliveryDateFormat: function(row, cloumn) {
      let time = ''
      if (row.endTime != null) {
        time = Moment(row.endTime).format('YYYY/MM/DD HH:mm:ss')
      }
      return time
    },
    onLineDateFormat: function(row, cloumn) {
      let time = ''
      if (row.onLineDate != null) {
        time = Moment(row.onLineDate).format('YYYY/MM/DD')
      }
      return time
    },

    /**
     * 搜索事件
     */
    queryBtn: function() {
      const params = this.searchForm
      this.getPagerList(params)
    },
    // 从新布局
    toDoLayout() {
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
    },

    // 重置按钮
    clearSearch() {
      this.searchForm.entryTime = ''
      this.searchForm.storyNum = ''
      this.searchForm.userStory = ''
      this.searchForm.planState = ''
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
.el-divider {
  margin: 10px 0;
}
.el-checkbox-group{
  display: flex;
  flex-direction: column;
}
.popover{
  display: flex;
  justify-content: flex-end;
  font-size: 30px;
}
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

.testApectacularsContainer {
  background-color: #ffffff;
  margin-top: 15px;
  padding: 15px;
  text-align: right;
}

.testApectacularsContainer_head {
  float: right;
  margin-bottom: 10px;
}

.custom-button{
  float:  right;
  margin-right: 50px;
  margin-top: -6px;
  font-size: 13px;
}

.custom-button:hover{
  background-color:  transparent;
  color: #ad64a4;
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
