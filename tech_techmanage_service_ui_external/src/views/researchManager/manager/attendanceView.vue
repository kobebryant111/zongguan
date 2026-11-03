<template>
  <!-- 研发人员考勤 -->
  <div v-loading class="typeset-container" >
    <el-row :gutter="20" >
      <attendance-view-person ref="attViewPerson" :multiple-choice="true" :show-red="true" :query-type="2" :list="selectedDepid" :date="attendanceMonth" />
      <el-col :span="18" >
        <el-row style="background:white;padding:10px;">
          <span>考勤周期：</span>
          <el-select v-model="attendanceMonth" style="display:inline-block;">
            <el-option
              v-for="item in monthList"
              :key="item.attendanceDate"
              :value="item.attendanceDate"
              :label="item.attendanceDate"
            />
          </el-select>
          <div style="float:right;display:inline-block;">
            <buttonOrange :fns="getResearchAttendance" style="margin-left:10px;" button-name="获取考勤" size="small"/>
            <buttonOrange :fns="outputCurrent" style="margin-left:10px;" button-name="导出当前" type="text" size="small"/>
            <buttonOrange v-hasFunc="['1371_LICENCE']" :fns="outputAllAttendance" style="margin-left:10px;" button-name="导出全部" type="text" size="small"/>
            <buttonOrange v-hasFunc="['1372_LICENCE']" :fns="outputYear" style="margin-left:10px;" button-name="导出年度考勤" type="text" size="small"/>
            <el-dialog
              :visible.sync="dialogVisible"
              :before-close="handleClose"
              title="考勤导出"
              width="30%">
              <el-select v-model="monthDataMonth" placeholder="请选择月份">
                <el-option
                  v-for="item in monthList"
                  :key="item.attendanceDate"
                  :value="item.attendanceDate"
                  :label="item.attendanceDate"
                />
              </el-select>
              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="outputAttendance">导出</el-button>
              </span>
            </el-dialog>
            <el-dialog
              :visible.sync="dialogYear"
              :before-close="handleClose"
              title="增加导出年度考勤"
              width="30%">
              <el-select v-model="yaerDataMonth" placeholder="请选择月份">
                <el-option
                  v-for="item in yearList"
                  :key="item"
                  :value="item"
                  :label="item"
                />
              </el-select>
              <span slot="footer" class="dialog-footer">
                <el-button @click="dialogYear = false">取消</el-button>
                <el-button type="primary" @click="outputYearAttendance">导出</el-button>
              </span>
            </el-dialog>
          </div>
        </el-row>
        <el-row style="background:white;padding:10px;">
          <el-table
            v-loading="loading"
            ref="projectTable"
            :data="attendanceData"
            :cell-style="cellFormat"
            :tree-props="{children:'projects',hasChildren:'hasChildren'}"
            element-loading-text="考勤数据加载中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="gba(0,0,0,0.8)"
            style="width: 100%;"
            stripe
            max-height="800"
            border
            align="center"
            default-expand-all
            row-key="id"
          >
            <el-table-column label="" fixed="left" width="300" >
              <template slot-scope="scope">
                <span v-if="!scope.row.projectCode">{{ scope.row.username + '('+ scope.row.badge+')' }}{{ checkFunc(['00000_HIDDEN']) === false ? '['+getDictName('NEWKG_RD_NATURE',scope.row.nature)+']' : '' }}</span>
                <span v-if="scope.row.projectCode">[{{ getDictName('RD_PROJECT_STATE',scope.row.projectState) }}]
                  {{ scope.row.projectName }}(<span style="text-size:10px">{{ scope.row.projectCode }})<br>项目合同预算总金额(万元):{{ scope.row.budgetAmount }}</span></span>
              </template>
            </el-table-column>
            <el-table-column
              v-if="attendanceData != undefined && attendanceData.length > 0"
              :formatter="proportionFormatter"
              prop="proportion"
              fixed="left"
              label="占比"
              align="center"
              width="60" >
              <template slot-scope="scope">
                {{ calculate(scope.row) }}
              </template>
            </el-table-column>
            <template v-if="attendanceData != undefined && attendanceData.length > 0">
              <el-table-column v-for="(data,idx) in attendanceData[0]['rdCalendarList']" :key="idx" :label="formatDateColumnTitle(data['dayOfMonth'])" align="center" width="55" >
                <template slot-scope="scope">
                  <span>
                    <span v-if="!scope.row.projectCode">{{ scope.row.rdCalendarList[idx]['text'] }}</span>
                    <span v-else-if="(!editable || scope.row.auditStatus != '2')&& scope.row.rdCalendarList[idx]['attendanceOrno'] === '1' ">{{ scope.row.rdCalendarList[idx]['attendanceTime'] }}</span>
                  </span>
                </template>
              </el-table-column>
            </template>
          </el-table>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import common from '@/api/common'
import PersonTable from '@/api/researchManager/rpersonTable'
import pager from '@/components/Pager'
import AttendanceViewPerson from './attendanceViewPerson.vue'
import buttonOrange from '@/components/eleButton/buttonOrange'
import { groupBy } from '@/utils/index'
export default {
  name: 'AttendanceView',
  components: {
    pager,
    AttendanceViewPerson,
    buttonOrange
  },
  data() {
    return {
      size: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      monthDataMonth: '',
      monthData: [],
      dialogVisible: false,
      dialogYear: false,
      submitEndLoading: false,
      loading: false,
      attendanceMonth: '',
      yaerDataMonth: '',
      monthList: [],
      yearList: [],
      attendanceData: [], // 考勤数据
      attendancePerson: [], // 考勤人员列表
      dialogIsShow: false,
      showBackBtn: false,
      fullScreenLoading: false,
      editable: false,
      selectedDepid: []
    }
  },
  computed: {
    formatDateColumnTitle() {
      return (date) => {
        return date + '日'
      }
    },
    checkedBtn() {
      return (scope, colIndex, value) => {
        if (scope.row.rdCalendarList[colIndex]['attendanceTime'] == value) {
          return 'primary'
        } else {
          return ''
        }
      }
    }

  },
  watch: {
    'attendanceMonth': {
      handler(n, o) {
        if (n) {
          this.attendanceData = []
          this.$refs.attViewPerson.requestPersonData(n)
        }
      }
    }

  },
  created() {
    if (this.$route.params.date != undefined) {
      this.attendanceMonth = this.$route.params.date
      this.showBackBtn = true
    }

    const params = {}
    params.size = 24
    params.index = 1
    PersonTable.queryMonthList(params).then(res => {
      if (res.data.status == 200) {
        this.monthData = res.data.results
        this.monthList = res.data.results.filter(res => {
          return res.attendanceStatus !== '0'
        })
        if (this.monthDataMonth == '') {
          this.monthDataMonth = this.monthList[0]['attendanceDate']
        }
        if (this.attendanceMonth == '') {
          this.attendanceMonth = this.monthList[0]['attendanceDate']
        }
      } else {
        this.$message.error(res.data.message)
      }
    })
  },
  methods: {
    proportionFormatter(row, column, cellValue, index) {
      if (index === 0) {
        return ''
      }
      if (cellValue === undefined) {
        return ''
      }
      if (cellValue === 0) {
        return '0%'
      } else {
        return (cellValue * 100).toFixed(2) + '%'
      }
    },
    // 处理单元格样式
    cellFormat({ row, column, rowIndex, columnIndex }) {
      if (columnIndex >= 2) {
        var cellData = row.rdCalendarList[columnIndex - 2]
        if (!this.editable || (cellData != null && (cellData.isHoliday == 'Y' || row.auditStatus != '2'))) {
          return 'background: #f5f5f5;'
        }
      }
    },
    // 关闭弹窗
    handleClose(done) {
      this.$confirm('确认关闭').then(_ => {
        done()
      }).catch(_ => {})
    },
    // 获取考勤数据
    getResearchAttendance() {
      const selectedData = this.$refs.attViewPerson.getCheckNodes()
      this.selectedDepid = selectedData.map(item => item.badge)

      if (selectedData.length === 0) {
        this.$message.error('请选择至少一位员工')
        return false
      }
      const params = { commRdEmployeeDtoList: selectedData, ppmKqRdCalendar: { attendanceDate: this.attendanceMonth }}
      params.index = 1
      params.size = 1000
      this.loading = true
      // 获取研发项目考勤数据和人员考勤数据
      Promise.all([PersonTable.queryAttendanceTabulation(params), PersonTable.queryPersonAttendanceTabulation(params)]).then(datas => {
        this.loading = false
        if (datas[0].data.status === 200 && datas[1].data.status === 200) {
          const attendance = datas[0].data.results

          const personAttendance = datas[1].data.results

          // 将研发项目考勤数据按人员进行分组
          const researchAttendance = groupBy(attendance, person => {
            return [person.badge]
          })

          // 将研发项目考勤数据拼入员工考勤数据中
          let rid = 0
          personAttendance.forEach((item, index) => {
            item.id = rid++
            item.rdCalendarList.forEach(rd => {
              if (rd.attendanceOrno === '1') {
                rd.text = '出勤'
              } else if (rd.attendanceOrno === '0') {
                rd.text = '未出勤'
              } else if (rd.attendanceOrno === '2') {
                rd.text = '非工作日'
              }
            })
            delete item.proportion
            researchAttendance.forEach(person => {
              if (item.badge === person[0].badge) {
                person.forEach(p => {
                  p.id = rid++
                })

                item.projects = person
                return
              }
            })
          })

          this.attendanceData = personAttendance
        } else {
          this.$message.error('获取数据异常')
        }
      })
    },
    // 占比计算
    calculate(val) {
      if (val.eidCode === undefined) return null
      let days = 0
      let time = 0
      val.rdCalendarList.forEach(val => {
        if (val.isHoliday !== 'Y') {
          time = time + ((typeof val.attendanceTime === 'number' && !isNaN(val.attendanceTime)) ? val.attendanceTime : Number(val.attendanceTime))
          days++
        }
      })
      return time === 0 ? null : (time / days * 100).toFixed(2) + '%'
    },
    // 导出全部考勤信息
    outputAttendance() {
      const list = this.monthData.filter(res => {
        return res.attendanceDate == this.monthDataMonth
      })
      // attendanceStatus: list[0]['attendanceStatus']
      const params = { ppmKqRdCalendar: { attendanceDate: this.monthDataMonth }, _isDown: 'Y' }
      this.$confirm('是否导出' + this.monthDataMonth + '考勤表？', '考勤导出确认',
        { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          console.log(params, '==========')
          const self = this
          PersonTable.infoApproveAttendance(params).then(res => {
            common.download(res.data, self, this.monthDataMonth + '考勤信息导出文件.xls')
          })
        })
      this.dialogVisible = false
    },
    // 导出年度考勤
    outputYearAttendance() {
      const yearOfCalendar = this.yaerDataMonth
      const params = { yearOfCalendar, _isDown: 'Y' }
      this.$confirm('是否导出' + this.yaerDataMonth + '考勤表？', '考勤导出确认',
        { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          console.log(params, '==========')
          const self = this
          PersonTable.importYearKq(params).then(res => {
            common.download(res.data, self, this.yaerDataMonth + '考勤信息导出文件.xls')
          })
        })
      this.dialogYear = false
    },
    // 增加导出年度考勤
    outputYear() {
      PersonTable.currentYear().then(data => {
        if (data.data.status === 200) {
          this.yearList = data.data.results
          this.dialogYear = true
        }
      })
    },
    outputAllAttendance() {
      this.dialogVisible = true
    },
    // 导出当前考勤
    outputCurrent() {
      const selectedData = this.$refs.attViewPerson.getCheckNodes()
      if (selectedData.length === 0) {
        this.$message.error('请选择至少一位员工')
        return false
      }
      const params = { commRdEmployeeDtoList: selectedData,
        ppmKqRdCalendar: { attendanceDate: this.attendanceMonth }, _isDown: 'Y' }
      PersonTable.infoApproveCurrent(params).then(res => {
        common.download(res.data, self, this.attendanceMonth + '考勤信息导出文件.xls')
      })
    }
  }
}
</script>

<style>
.typeset-container {
  padding: 20px;
}

.date-input .el-input__inner{
    border:0px;
    padding:0px;
    text-align: center;
}
</style>
