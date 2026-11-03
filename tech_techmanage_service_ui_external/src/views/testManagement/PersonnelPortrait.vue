<!--   人员画像-->
<template>
  <div class="typeset-container">
    <!-- 查询部分 -->
    <div class="typeset-head">
      <el-form :model="form">
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="所属团队:" label-width="73px">
              <el-select v-model="form.textTeam" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in textTeamList"
                  :key="item.id"
                  :label="item.text"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="所属小组:" label-width="73px">
              <el-select v-model="form.textGroup" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in textGroupList"
                  :key="item.id"
                  :label="item.text"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="工号:" label-width="73px">
              <el-input v-model="form.badge" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="姓名:" label-width="73px">
              <el-input v-model="form.username" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="日期：" label-width="80px">
              <el-date-picker
                v-model="form.entryTime"
                type="daterange"
                style="width:250px;"
                value-format="yyyy-MM-dd"
                start-placeholder="开始时间"
                end-placeholder="结束时间" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="4">
            <el-form-item label="供应商名称:" label-width="88px">
              <el-select v-model="form.outsourcname" filterable clearable placeholder="请选择">
                <el-option
                  v-for="item in outsourcnameList"
                  :key="item.id"
                  :label="item.text"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否包含自建项目:" label-width="150px">
              <el-switch v-model="form.switch" active-text="是" inactive-text="否"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <div style="height: 36px;line-height: 36px;">
              <clean-btn :forms="form" size="small" />
              <buttonOrange :fns="submitBtn" style="margin-left: 10px" button-name="查询" size="mini"/>
              <buttonOrange :fns="submitBtnInfo" button-name="下载" size="mini" />
            </div>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 下拉菜单 -->
    <div class="typeset-body">
      <div class="testApectacularsContainer">
        <el-dropdown trigger="click" style="padding-left: 12px">
          <el-tooltip open-delay="1000" class="item" effect="light" content="显隐列" placement="top">
            <span class="el-dropdown-link" circle>
              <el-button size="mini" circle>
                <i class="el-icon-menu"/>
              </el-button>
            </span>
          </el-tooltip>
          <el-dropdown-menu slot="dropdown" class="drepdown-table">
            <el-tooltip open-delay="1000" class="item" effect="light" content="全选/取消" placement="top">
              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" class="custom-checkbox" @change="handCheckAllChange">
                全选
              </el-checkbox>
            </el-tooltip>
            <hr>
            <div v-for ="(column, index ) in columns" :key="column.prop" style="padding-left: 12px">
              <el-tooltip open-delay="1000" class="item" effect="dark" content="是否显示" placement="top">
                <el-checkbox v-model="column.visible" class="customed-checkbox" style="width: 130px" @change="handCheckChange" >
                  {{ column.label }}
                </el-checkbox>
              </el-tooltip>
              <el-tooltip open-delay="1000" class="item" effect="light" content= "左冻结" placement="top">
                <el-button :class="{'highlighted': column.isHighlightedLeft}" size="mini" type="text" @click="togglePinLeft(index)" >
                  <i :class="['el-icon-bottom-left']"/>
                </el-button>
              </el-tooltip>
              <el-tooltip open-delay="1000" class="item" effect="light" content= "右冻结" placement="top">
                <el-button :class="{'highlighted': column.isHighlightedRight}" size="mini" type="text" @click="togglePinRight(index)" >
                  <i :class="['el-icon-bottom-right']"/>
                </el-button>
              </el-tooltip>
            </div>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <p/>
      <!-- 列表部分 -->
      <el-table
        v-loading="loading"
        ref="multipleTable"
        :data="crewData"
        border
        fit
        height="600"
        element-loading-text="数据加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="gba(0,0,0,0.8)"
        style="width: 100%"
      >
        <el-table-column
          v-for="column in filteredColuns"
          :key="column.prop"
          :fixed="column.fixed"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
        />
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
    </div>
  </div>
</template>

<script>
import common from '@/api/common'
import draggable from 'vuedraggable'
import cleanBtn from '@/components/CleanBtn'
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange'
import { queryPager, queryPagerInfo } from '@/api/testManagementApi/personnerPortait'
export default {
  name: 'PersonnelPortrait',
  components: {
    draggable,
    buttonOrange,
    cleanBtn,
    pager
  },
  data() {
    return {
      form: {
        switch: false
      },
      textTeamList: [], // 所属团队
      textGroupList: [], // 所属小组
      outsourcnameList: [], // 供应商名称
      crewData: [
        { badge: '234566', username: '张三', textTeam: '测试团队', textGroup: '测试小组',
          presenceState: '在职' }
      ], // 人员画像列表
      selecctedColumns: ['工号', '姓名', '所属团队', '所属小组', '成员属性',
        '供应商名称', '案例编写数', '执行案例总数', '冒烟执行案例数', 'SIT执行案例数',
        'UAT执行案例数', '未否决缺陷数', '有效缺陷总数', '高级缺陷数', '中级缺陷数',
        '低级缺陷数', '优化缺陷数', '冒烟缺陷发现率', 'SIT缺陷发现率', 'UAT缺陷发现率',
        '加班时长', '出勤天数', '报工时长'],
      columns: [
        { prop: 'badge', label: '工号', width: '100', visible: true, fixed: 'left', isHighlightedLeft: true, isHighlightedRight: false },
        { prop: 'username', label: '姓名', width: '100', visible: true, fixed: 'left', isHighlightedLeft: true, isHighlightedRight: false },
        { prop: 'textTeam', label: '所属团队', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'textGroup', label: '所属小组', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'presenceState', label: '成员属性', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'outsourcname', label: '供应商名称', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'caseWritTime', label: '案例编写数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'caseNumAll', label: '执行案例总数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'smokeCaseNum', label: '冒烟执行案例数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'SITcaseNum', label: 'SIT执行案例数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'UATcaseNum', label: 'UAT执行案例数', width: '120', visible: true, fixed: false, isHighlisHighlightedLeftighted: false, isHighlightedRight: false },
        { prop: 'notVetoedFlawNum', label: '未否决缺陷数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'validFlawNum', label: '有效缺陷总数', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'advancedFlawNum', label: '高级缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'middlleFlawNum', label: '中级缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'lowFlawNum', label: '低级缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'optimizeFlawNum', label: '优化缺陷数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'smokeFlawRatio', label: '冒烟缺陷发现率', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'SITFlawRatio', label: 'SIT缺陷发现率', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'UATFlawRatio', label: 'UAT缺陷发现率', width: '120', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'overtimeHours', label: '加班时长', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'requiredAttendanceDays', label: '出勤天数', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false },
        { prop: 'reportLongHours', label: '报工时长', width: '100', visible: true, fixed: false, isHighlightedLeft: false, isHighlightedRight: false }
      ],
      totalNumber: 1,
      isIndeterminate: false,
      checkAll: true
    }
  },
  computed: {
    filteredColuns() {
      return this.columns.filter(column => column.visible)
    }
  },
  mounted() {
    this.getInit()
  },
  methods: {
    // 左冻结
    togglePinLeft(index) {
      const data = this.columns[index].fixed
      if (!data || data === 'right') {
        this.columns[index].fixed = 'left'
      } else if (data === 'left') {
        this.columns[index].fixed = false
      }
      // 按钮高亮
      const lightLeft = this.columns[index].isHighlightedLeft
      const lightRight = this.columns[index].isHighlightedRight
      if (!lightLeft && lightRight) {
        this.columns[index].isHighlightedRight = this.columns[index].isHighlightedLeft
      }
      this.columns[index].isHighlightedLeft = !this.columns[index].isHighlightedLeft
    },
    // 右冻结
    togglePinRight(index) {
      const data = this.columns[index].fixed
      if (!data || data === 'left') {
        this.columns[index].fixed = 'right'
      } else if (data === 'right') {
        this.columns[index].fixed = false
      }
      // 按钮高亮
      const lightLeft = this.columns[index].isHighlightedLeft
      const lightRight = this.columns[index].isHighlightedRight
      if (lightLeft && !lightRight) {
        this.columns[index].isHighlightedLeft = this.columns[index].isHighlightedRight
      }
      this.columns[index].isHighlightedRight = !this.columns[index].isHighlightedRight
    },
    // 全选/取消
    handCheckAllChange() {
      this.columns.forEach(item => {
        item.visible = this.checkAll
      })
      this.handCheckChange()
    },
    // 全选效果显示
    handCheckChange() {
      this.checkAll = this.columns.every(item => item.visible === true)
      this.isIndeterminate = this.columns.some(item => item.visible === false)
      const allT = this.columns.every(item => item.visible === true)
      const allF = this.columns.every(item => item.visible === false)
      if (allT || allF) {
        this.isIndeterminate = false
      }
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
    },
    // 分页初始化
    getInit() {
      this.getPager(this.size, this.index)
    },
    // 分页
    getPager: function(size, index) {
      const params = { ...this.form }
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 查询
    submitBtn() {
      this.index = 1
      this.getPager(this.size, this.index)
    },
    // 获取列表数据
    getPagerList(params) {
      this.loading = true
      const self = this
      queryPager(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.crewData = res.data.results
          self.totalNumber = res.data.total
          if (self.totalNumber <= 0) {
            return false
          }
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 下载
    submitBtnInfo() {
      const size = this.size
      const index = this.index
      const params = { ...this.form, size, index, _isDown: 'Y' }
      queryPagerInfo(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          self.crewData = res.data.results
          common.download(res.data, self, '人员画像.xls')
        }
      })
    }
  }
}
</script>

<style scoped>
.typeset-container {
  padding: 10px;
}
.typeset-head {
  background-color: #ffffff;
  padding: 10px;
}
.typeset-body {
  padding: 10px 15px 15px 15px;
  background-color: #ffffff;
  /* height: calc(100vh - 262px); */
  border-radius: 4px;
  margin-top: 10px;
}
.pager-zj {
  text-align: center;
}

.drepdown-table {
  position: relative;
}
.custom-checkbox {
  display: block;
  margin-bottom: 0px;
  margin-top: 5px;
  margin-left: 12px;
}
/**定义下拉菜单区域大小 */
.el-dropdown-menu.drepdown-table {
  width: 220px;  /* 设置宽度 */
  height: 690px; /* 设置高度 */
  padding: 50; /** 移除内边距 */
  overflow: hidden; /**允许内容滚动 */
}
.left-align {
  justify-content: flex-start;
}
/**调整表格的样式 */
.custom-dropdown-table {
  width: 100%;
  font-size: 12px; /**设置表格字体大小 */
}
.testApectacularsContainer {
  background-color: #ffffff;
  margin-top: 5px;
  padding: 15px;
  text-align: right;
}

.highlighted {
  background-color: white;
  color: #ffd723;
}

</style>
