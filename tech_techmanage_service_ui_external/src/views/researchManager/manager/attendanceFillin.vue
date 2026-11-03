<template>
  <!-- eslint- -disable -->
  <!-- 研发人员考勤 -->
  <div class="typeset-container" >
    <el-row :gutter="20" >
      <research-person-select ref="resPersonSelect" :multiple-choice="true" :show-red="true" :query-type="1" :list="selectedDepid" :date="attendanceMonth" />
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
            <!-- <buttonOrange :fns="addAttendance"  button-name="填写" size="small" /> -->
            <buttonOrange v-hasFunc="['1344_LICENCE']" :fns="getResearchAttendance" style="margin-left:10px;" button-name="获取考勤" size="small"/>
            <buttonOrange v-hasFunc="['1345_LICENCE']" :fns="outputAttendance" style="margin-left:10px;" button-name="下载" size="small"/>
            <!-- <buttonOrange style="margin-left:10px;" button-name="上传" :fns="inputAttendance" size="small" /> -->
            <attendFile v-hasFunc="['1346_LICENCE']" class="upload-demo" @exData="exALLDa"/>
            <buttonOrange v-hasFunc="['1342_LICENCE']" :fns="saveAttendance" style="margin-left:10px;" button-name="保存" size="small"/>
            <buttonOrange v-hasFunc="['1343_LICENCE']" :fns="submitAttendance" style="margin-left:10px;" button-name="提交" size="small"/>
            <!-- <buttonOrange style="margin-left:10px;" button-name="撤回" :fns="revokeAttendance" size="small" /> -->
            <el-button v-if="showBackBtn" style="margin-left:10px;" size="small" class="lastTopRightButton" @click="$router.back()">返回</el-button>

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
                  {{ scope.row.projectName }}(<span style="text-size:10px">{{ scope.row.projectCode }})<br>项目合同预算总金额(万元):{{ formatAmount(scope.row.budgetAmount) }}</span></span>
              </template>
            </el-table-column>
            <el-table-column v-if="attendanceData != undefined && attendanceData.length > 0" :formatter="(row, column, cellValue, index)=>(cellValue?(cellValue*100).toFixed(2)+'%':'')" prop="proportion" fixed="left" label="占比" align="center" width="60" >
              <template slot-scope="scope">
                {{ calculate(scope.row) }}
              </template>
            </el-table-column>
            <!-- <el-table-column prop="badge" label="工号" width="100" align="center"/>
                        <el-table-column prop="nature" label="研发人员性质" width="120" align="center" :formatter="(row, column, cellValue, index)=>getDictName('NEWKG_RD_NATURE',cellValue)"/>
                        <el-table-column prop="projectCode" label="研发项目编号" width="120" align="center"/>
                        <el-table-column prop="projectName" label="项目名称" width="120" align="center" />
                        <el-table-column prop="projectState" label="项目状态" align="center" width="120" :formatter="(row, column, cellValue, index)=>getDictName('RD_PROJECT_STATE',cellValue)" /> -->
            <template v-if="attendanceData != undefined && attendanceData.length > 0">
              <el-table-column v-for="(data,idx) in attendanceData[0]['rdCalendarList']" :key="idx" :label="formatDateColumnTitle(data['dayOfMonth'])" align="center" width="65" >
                <template slot-scope="scope">
                  <span>
                    <el-select
                      v-if="!scope.row.projectCode && scope.row.rdCalendarList[idx]['editing']"
                      v-model="scope.row.rdCalendarList[idx]['attendanceOrno']"
                      :disabled="isDisabled(scope.row,scope.row.rdCalendarList[idx])"
                      size="mini"
                      style="width: 60px"
                      class="left-align_select"
                      @change="attendanceOrnoChange(scope.row.rdCalendarList[idx])"
                    >
                      <el-option label="非工作日" value="2" disabled/>
                      <el-option label="出勤" value="1"/>
                      <el-option label="未出勤" value="0"/>
                    </el-select>
                    <span
                      v-if="!scope.row.projectCode && !scope.row.rdCalendarList[idx]['editing']"
                      :disabled="isDisabled(scope.row,scope.row.rdCalendarList[idx])"
                      class="span-wrapper"
                      @mouseenter="startEidt(scope.row.rdCalendarList,idx)"
                    >
                      <span
                        :class="{'not-editable': isDisabled(scope.row,scope.row.rdCalendarList[idx])}"
                        class="inline-span">
                        {{ attendanceText(scope.row.rdCalendarList[idx]['attendanceOrno']) }}
                      </span>
                    </span>
                    <div v-if="scope.row.rdCalendarList[idx]['attendanceOrno'] === '1'">
                      <span
                        v-if="scope.row.auditStatus != '1' && scope.row.auditStatus != '3'
                        && scope.row.projectCode ">
                        {{ scope.row.rdCalendarList[idx]['attendanceTime'] }}
                      </span>
                      <el-popover
                        v-hasFunc="['1341_LICENCE']"
                        v-if ="scope.row.projectCode &&
                          !(scope.row.auditStatus != '1' && scope.row.auditStatus != '3')
                        && scope.row.rdCalendarList[idx]['editing'] "
                        :disabled="poverDisabled(scope.row,scope.row.rdCalendarList[idx])"
                        placement="bottom"
                        title="编辑"
                        trigger="click"
                      >
                        <div v-if="scope.row.projectCode">
                          <el-button :type="checkedBtn(scope,idx,1)" size="mini" round style="display:inline-block" @click="choseWorkload(scope,idx,1)" >1</el-button>
                          <el-button :type="checkedBtn(scope,idx,0)" size="mini" round style="display:inline-block" @click="choseWorkload(scope,idx,0)" >0</el-button>
                          <el-button :type="checkedBtn(scope,idx,0.2)" size="mini" round style="display:inline-block" @click="choseWorkload(scope,idx,0.2)" >0.2</el-button>
                          <el-button :type="checkedBtn(scope,idx,0.5)" size="mini" round style="display:inline-block" @click="choseWorkload(scope,idx,0.5)" >0.5</el-button>
                          <el-button :type="checkedBtn(scope,idx,0.8)" size="mini" round style="display:inline-block" @click="choseWorkload(scope,idx,0.8)" >0.8</el-button>
                        </div>
                        <el-input
                          slot="reference"
                          :disabled="poverDisabled(scope.row,scope.row.rdCalendarList[idx])"
                          v-model="scope.row.rdCalendarList[idx]['attendanceTime']"
                          class="date-input"
                          @input="onInput(scope.row.rdCalendarList[idx],'attendanceTime')"/>
                      </el-popover>
                      <span
                        v-hasFunc="['1341_LICENCE']"
                        v-if ="scope.row.projectCode && !scope.row.rdCalendarList[idx]['editing'] &&
                        !(scope.row.auditStatus != '1' && scope.row.auditStatus != '3')"
                        class="input-span-wrapper"
                        @mouseenter="startEidt(scope.row.rdCalendarList,idx)">
                        <span
                          class="input-span"
                        >
                          {{ scope.row.rdCalendarList[idx]['attendanceTime'] }}
                        </span>
                      </span>

                    </div>
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
// /* eslint-disable */
import common from '@/api/common'
import PersonTable from '@/api/researchManager/rpersonTable'
import pager from '@/components/Pager'
import ResearchPersonSelect from './researchPersonSelect.vue'
import buttonOrange from '@/components/eleButton/buttonOrange'
import { groupBy } from '@/utils/index'
import attendFile from '@/components/UploadExcel/attendFile' // 导入上传表格组件
export default {
  name: 'AttendanceFillin',
  components: {
    pager,
    ResearchPersonSelect,
    buttonOrange,
    attendFile
  },
  data() {
    return {
      loading: false,
      attendanceMonth: '',
      monthList: [],
      attendance: [],
      attendanceData: [], // 考勤数据
      attendancePerson: [], // 考勤人员列表
      dialogIsShow: false,
      showBackBtn: false,
      fullScreenLoading: false,
      selectedDepid: [],
      personAttendance: [],
      batchSize: 5,
      index: 0
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
          this.$refs.resPersonSelect.requestPersonData(n)
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
        this.monthList = res.data.results.filter(res => {
          return res.attendanceStatus == '1'
        })
        if (this.attendanceMonth == '') {
          this.attendanceMonth = this.monthList[0]['attendanceDate']
        }
      } else {
        this.$message.error(res.data.message)
      }
    })
  },
  mounted() {
    this.loadMore()
    this.bindScroll()
  },
  beforeDestroy() {
    const body = this.$refs.projectTable.bodyWrapper
    body.removeEventListener('scroll', this.onScroll)
  },
  methods: {
    // 四舍五入显示
    formatAmount(cellValue) {
      return Number(cellValue).toFixed(2)
    },
    // 禁用下拉框
    isDisabled(data, params) {
      if (data.projects[0].auditStatus !== '1' && data.projects[0].auditStatus !== '3') return true
      if (params['isHoliday'] === 'Y') return true
      const today = new Date(params['calendarDate'].split('T')[0])
      const arrivalTime = new Date(data.arrivalTime)
      if (data.empstatus !== '0' && data.leaveTime) {
        const leaveTime = new Date(data.leaveTime)
        return !(today < leaveTime && today >= arrivalTime)
      }
      return today < arrivalTime
    },
    // 禁用输入框
    poverDisabled(data, params) {
      if (params.attendanceOrno === '0') {
        return true
      } else {
        return false
      }
    },
    // 是否出勤
    attendanceOrnoChange(data) {
      // debugger
      console.log(data)
      PersonTable.hrResourcesAllocation(data).then(res => {
        if (res.data.status == 200) {
          this.$message.success('出勤状态修改成功')
          this.getResearchAttendance()
        } else {
          this.$message.error('出勤状态修改失败')
        }
      })
    },
    // 处理单元格样式
    cellFormat({ row, column, rowIndex, columnIndex }) {
      if (columnIndex >= 2) {
        var cellData = row.rdCalendarList[columnIndex - 2]
        if (cellData != null && (cellData.isHoliday == 'Y' || (row.auditStatus != '1' && row.auditStatus != '3'))) {
          return 'background: #f5f5f5;'
        }
        if (cellData != null && cellData.attendanceOrno != '1') {
          return 'background: #f5f5f5;'
        }
      }
    },
    // 选择工作量
    choseWorkload(scope, colIndex, value) {
      this.$set(scope.row.rdCalendarList[colIndex], 'attendanceTime', value)
    },

    // 获取考勤数据
    getResearchAttendance() {
      const selectedData = this.$refs.resPersonSelect.getCheckNodes()
      this.selectedDepid = selectedData.map(item => item.badge)

      if (selectedData.length == 0) {
        this.$message.error('请选择至少一位员工')
        return false
      }

      const params = { commRdEmployeeDtoList: selectedData, ppmKqRdCalendar: { attendanceDate: this.attendanceMonth }}
      params.index = 1
      params.size = 1000
      // 获取研发项目考勤数据和人员考勤数据
      this.loading = true
      this.attendanceData = []
      // Promise.all([PersonTable.queryAttendance(params),PersonTable.queryPersonAttendance(params)]).then(datas => {
      //     this.loading = false
      //     if (datas[0].data.status == 200 && datas[1].data.status == 200) {
      //         const attendance = datas[0].data.results
      //         let personAttendance = datas[1].data.results

      //         //将研发项目考勤数据按人员进行分组
      //         let researchAttendance  = groupBy(attendance, person => {
      //             return [person.badge];
      //         });

      //         //将研发项目考勤数据拼入员工考勤数据中
      //         let rid= 0
      //         personAttendance.forEach((item,index) => {
      //             item.id = rid++
      //             // item.rdCalendarList.forEach(rd=>{
      //             //     if (rd.attendanceTime == '1') {
      //             //         rd.text = '出勤'
      //             //     }
      //             //     else if(rd.attendanceTime == '0.5'){
      //             //         rd.text = '半天假'
      //             //     }
      //             //     else if (rd.attendanceTime == '0') {
      //             //         rd.text = '请假'
      //             //     }

      //             // })

      //             researchAttendance.forEach(person => {

      //                 if(item.badge == person[0].badge){
      //                     person.forEach(p=>{
      //                         p.id = rid++
      //                     })

      //                     item.projects = person
      //                     return
      //                 }
      //             })
      //         })

      //         this.attendanceData = personAttendance
      //         console.log(this.attendanceData, "=====================+++==")

      //     } else {

      //         this.$message.error("获取数据异常")
      //     }

      // })
      PersonTable.queryAttendance(params).then(datas => {
        this.loading = false
        if (datas.data.status == 200) {
          this.attendance = datas.data.results
          this.reorgenAttend(this.attendance)
        } else {
          this.$message.error('获取数据异常')
        }
      })
    },
    // 人员进行分组
    reorgenAttend(attendance) {
      this.personAttendance = []
      // 将研发项目考勤数据按人员进行分组
      const researchAttendance = groupBy(attendance, person => {
        return [person.badge]
      })
      // 将研发项目考勤数据拼入员工考勤数据中
      let rid = 0
      researchAttendance.forEach(person => {
        const informat = {}
        informat.id = rid++
        informat.eid = person[0].eid
        informat.badge = person[0].badge
        informat.nature = person[0].nature
        informat.username = person[0].username
        informat.rdCalendarList = person[0].rdCalendarList
        informat.projects = person
        this.personAttendance.push(informat)
        person.forEach(p => {
          p.id = rid++
        })
      })
      // this.attendanceData = this.personAttendance
      this.index = 0
      this.loadMore()
    },
    loadMore() {
      if (this.index >= this.personAttendance.length) {
        return
      }
      this.loading = true
      const start = this.index
      const end = start + this.batchSize

      this.attendanceData.push(...this.personAttendance.slice(start, end))
      this.index = end
      this.loading = false
    },
    // 滚动事件
    onScroll(event) {
      const target = event.target
      const scrollTop = target.scrollTop
      const clientHeight = target.clientHeight
      const scrollHeight = target.scrollHeight
      if (!this.loading && scrollTop + clientHeight >= scrollHeight - 5) {
        this.loadMore()
      }
    },
    bindScroll() {
      const bady = this.$refs.projectTable.bodyWrapper
      bady.addEventListener('scroll', this.onScroll)
    },
    // 上传模板 获取导入excel传来的数据
    exALLDa(v) {
      if (this.attendance.length === 0) {
        this.$message.warning('请先获取考勤')
        return
      }

      this.attendance.forEach(person => {
        const target = v.find(item => item.工号 === person.badge && (item.研发项目.match(/\(([^)]*)\)/))[1] === person.projectCode)
        person.rdCalendarList.forEach(val => {
          if (val.isHoliday === 'N') {
            const key = val.dayOfMonth + '号'
            const pattern = /^(\d+\.?\d*|\.\d*)?$/
            if (pattern.test(target[key])) {
              val.attendanceTime = target[key]
            }
          }
        })
      })
      this.attendanceData = []
      this.reorgenAttend(this.attendance)
    },
    // 点击输入
    startEidt(row, idx) {
      this.$set(row[idx], 'editing', true)
    },
    attendanceText(val) {
      if (val === '1') {
        return '出勤'
      } else if (val === '0') {
        return '未出勤 '
      } else if (val === '2') {
        return '非工作日'
      }
    },
    // 占比计算
    calculate(val) {
      if (val.eidCode === undefined) return null
      let days = 0
      let time = 0
      val.rdCalendarList.forEach(val => {
        if (val.isHoliday !== 'Y' && val.attendanceOrno == '1') {
          time = time + ((typeof val.attendanceTime === 'number' && !isNaN(val.attendanceTime)) ? val.attendanceTime : Number(val.attendanceTime))
          days++
        }
      })
      return time === 0 ? null : (time / days * 100).toFixed(2) + '%'
    },
    onInput(item, key) {
      let val = item[key]
      const pattern = /^(\d+\.?\d*|\.\d*)?$/
      if (!pattern.test(val)) {
        val = val
          .replace(/[^\d.]/g, '')
          .replace(/^(\d*\.\d*?)\./g, '$1')
        item[key] = val
        this.$message.error('无效数值')
      }
    },
    // 下载导入模板
    outputAttendance() {
      const selectedData = this.$refs.resPersonSelect.getCheckNodes()
      if (selectedData.length === 0) {
        this.$message.error('请选择至少一位员工')
        return false
      }
      const params = { commRdEmployeeDtoList: selectedData,
        ppmKqRdCalendar: { attendanceDate: this.attendanceMonth }, _isDown: 'Y' }
      PersonTable.infoApproveCurrent(params).then(res => {
        common.download(res.data, this, this.attendanceMonth + '考勤信息导出文件.xls')
      })
    },
    // 保存
    saveAttendance() {
      this.$confirm('是否保存这' + this.personAttendance.length + '名员工的考勤表？', '考勤修改确认', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          if (this.personAttendance.length == 0) {
            this.$message.error('当前无考勤可以保存')
            return false
          }

          let reqData = []
          this.personAttendance.forEach(item => {
            reqData = reqData.concat(item.projects)
          })
          // console.log(reqData);

          this.loading = true
          PersonTable.saveAttendance(reqData).then(res => {
            this.loading = false
            if (res.data.status == 200) {
              this.$message.success('保存成功')
            } else {
              this.$message.error(res.data.message)
            }
            this.getResearchAttendance()
          })
        })
    },
    // 提交
    submitAttendance() {
      if (this.personAttendance.length == 0) {
        this.$message.error('当前无考勤可以提交')
        return false
      }

      this.$confirm('是否提交这' + this.personAttendance.length + '名员工的考勤表？', '考勤提交确认', { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' })
        .then(_ => {
          let reqData = []
          this.personAttendance.forEach(person => {
            person.projects.forEach(pro => {
              pro.rdCalendarList = pro.rdCalendarList.map(c => {
                if (c.attendanceTime == undefined) {
                  c.attendanceTime = '0'
                }
                return c
              })
            })

            reqData = reqData.concat(person.projects)
          })

          this.loading = true
          PersonTable.submitAttendance(reqData).then(res => {
            this.loading = false
            if (res.data.status == 200) {
              this.$message.success('提交成功')
              this.getResearchAttendance()
              this.$refs.resPersonSelect.requestPersonData(this.attendanceMonth)
            } else {
              this.$message.error(res.data.message)
            }
          })
        })
    },
    // 考勤填报撤回
    revokeAttendance() {

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

.left-align_select .el-input--suffix{
    padding:0px;
}
.left-align_select .el-input__inner{
    padding:3px;
}
.left-align_select .el-select__wrapper {
    border: none;
    box-shadow: none;
}
.span-wrapper{
  display:inline-block;
  margin-top: 8px;
  width: 100%;
  box-sizing: border-box
}
.inline-span{
  display: inline-block;
  box-sizing: border-box;
  height: 29px;
  width: 57px;
  line-height: 30px;

  align-items: center;
  padding: 0 11px;
  padding-left: 0px;

  background-color: #ffffff;
  border: 1px solid #dcdfe6;
  border-radius: 4px;

  color: #606266;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;

  transition: border-color .2s cubic-bezier(0.645, 0.045, 0.355, 1);
  font-size: 12px;

}
.not-editable {
  background-color: #f5f7fa;
  cursor: default
}

.inline-span:hover{
  background-color: #c0c4cc
}
.input-span-wrapper{
  display:inline-block;
  margin-top: 8px;
  width: 100%;
  box-sizing: border-box
}
.input-span{
  box-sizing: border-box;
  display: inline-block;
  background-color: #ffffff;
  height: 36px;
  width: 44px;
  line-height: 35px;
  border-radius: 4px;
  font-size: 15px;
  white-space: nowrap;
  overflow: hidden;
}
</style>
