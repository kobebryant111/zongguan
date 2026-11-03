<template>
  <div>
    <!-- 头部 -->
    <div class="top">
      <el-row type="flex" justify="space-between" align="center">
        <el-col :span="22">
          <h2>详情</h2>
        </el-col>
        <el-col :span="2">
          <el-button style="margin-top: 5px" @click="toClose">关闭</el-button>
        </el-col>
      </el-row>
    </div>
    <!-- 个人信息 -->
    <div class="marginWrapper">
      <h3>个人信息</h3>
      <el-form ref="formData" :model="formData">
        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-form-item label="工号">
              <el-input v-model="formData.jobId" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="所属部门">
              <el-input v-model="formData.deptName" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="姓名">
              <el-input v-model="formData.userName" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="供应商名称">
              <el-input v-model="formData.supplierName" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="状态">
              <el-input v-model="formData.stateName" disabled/>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-form-item label="入场时间">
              <el-input v-model="formData.entryTime" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="离场时间">
              <el-input v-model="formData.leavingTime" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item :label="formData.outsourceType == 1 ? '系统名称' : '项目名称' ">
              <el-input v-model="formData.systemProjectName" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="项目经理">
              <el-input v-model="formData.pmName" disabled/>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="4">
                        <el-form-item label="外包方式">
                            <el-input v-model="formData.outsourceTypeName" disabled></el-input>
                        </el-form-item>
                    </el-col> -->
          <el-col :span="4">
            <el-form-item label="忘记打卡次数">
              <el-input v-model="formData.forgetClockInNumber" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between">

          <el-col :span="4">
            <el-form-item label="迟到次数">
              <el-input v-model="formData.belateNumber" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="早退次数">
              <el-input v-model="formData.leaveEarlyNumber" disabled/>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="4">
                        <el-form-item label="旷工次数">
                            <el-input v-model="formData.absentNumber" disabled></el-input>
                        </el-form-item>
                    </el-col> -->
          <!-- <el-col :span="4">
                        <el-form-item label="年假总天数">
                            <el-input v-model="formData.absentNumber" disabled></el-input>
                        </el-form-item>
                    </el-col> -->
          <!-- <el-col :span="4">
                        <el-form-item label="请假次数">
                            <el-input v-model="formData.leaveNumber" disabled></el-input>
                        </el-form-item>
                    </el-col> -->
          <el-col :span="4">
            <el-form-item label="现有剩余调休(天)">
              <el-input v-model="formData.annualVacationNumber" disabled/>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="异常次数">
              <el-input v-model="formData.exceptionNumber" disabled/>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="当月应出勤">
              <el-input v-model="formData.requiredAttendanceDays" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-between">

          <el-col :span="4">
            <el-form-item label="实际出勤天数">
              <el-input v-model="formData.actualAttendanceDays" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="当月加班时长(h)">
              <el-input v-model="formData.overtimeHours" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="考勤年月">
              <el-input v-model="formData.attendanceMonth" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="4"/>
          <el-col :span="4"/>
        </el-row>
      </el-form>
    </div>
    <!-- 考勤记录 -->
    <div class="marginWrapper">
      <h3>考勤记录</h3>
      <el-table :data="tableData" :max-height="clienHeight" :row-class-name="tableRowClassName">
        <!-- <el-table-column prop="deptName" label="所属部门" fixed width="120px" align="center" ></el-table-column> -->
        <el-table-column prop="userName" label="姓名" width="80px" align="center"/>
        <el-table-column prop="attendanceDate" label="日期" width="100px" align="center"/>
        <el-table-column prop="outsourceType" label="人员性质" width="100px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.outsourceType === '1'">人力外包</span>
            <span v-if="scope.row.outsourceType === '0'">项目外包</span>
          </template>
        </el-table-column>
        <el-table-column prop="isHoliday" label="是否节假日" width="90px" align="center">
          <template slot-scope="scope">
            {{ scope.row.isHoliday === '0' ? '否' : '是' }}
          </template>
        </el-table-column>
        <!-- <el-table-column prop="overtimeStartTime" label="加班开始时间" width="200px" align="center"></el-table-column> -->
        <el-table-column prop="workingTime" label="上班时间" width="100px" align="center"/>
        <el-table-column prop="closingTime" label="下班时间" width="100px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.whetherOvernight === '1'">{{ scope.row.closingTime + '+' }}</span>
            <span v-else>{{ scope.row.closingTime }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="overtimeStartTime" label="加班开始时间" width="105px" align="center">
          <template slot-scope="scope">
            <span v-show="scope.row.isHoliday === '0'">{{ scope.row.overtimeStartTime }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="overtimeHours" label="加班时长(min)" width="115px" align="center"/>
        <el-table-column prop="pmName" label="项目经理" width="80px" align="center"/>
        <el-table-column prop="amWhetherAbnormal" label="上午是否异常" width="105px" align="center" >
          <template slot-scope="scope">
            <!-- <el-select v-model="scope.row.amWhetherAbnormal" v-show="scope.row.actionType" >
                            <el-option
                                v-for="item in options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select> -->
            <span >{{ scope.row.amWhetherAbnormal === '1' ? "是" : scope.row.amWhetherAbnormal === '' ? "" : '否' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="上午异常原因" width="105px" align="center">
          <template slot-scope="scope">
            <el-select
              v-show="scope.row.actionType"
              v-model="scope.row.amAbnormalReason"
              :disabled="scope.row.amWhetherAbnormal == 1 ? false : true"
            >
              <el-option
                v-for="item in amAbnormalReasonList"
                :key="item.dictid"
                :label="item.dictname"
                :value="item.dictid"
              />
            </el-select>
            <span v-show="!scope.row.actionType">
              {{ getReasonName(scope.row.amAbnormalReason) }}
            </span>
            <!-- <span v-show="!scope.row.actionType" v-for="(item, index) in scope.row.abnormalReasonList" :key="index">
                                 {{  item == "0" ? "忘记打卡 ":
                                    item == "1" ? '迟到 ' :
                                    item == "2" ? '早退 ':
                                    item == "3" ? "年假半天 " :
                                    item == "4" ? "年假一天 " :
                                    item == "5" ? '事假半天 ' :
                                    item == "6" ? "事假一天 " :
                                    item == "7" ? "旷工半天 " :
                                    item == "8" ? "旷工一天 " :
                                    item == "11" ? "忘打卡 " :
                                    item == "12" ? "调休 " :
                                    item == "13" ? "迟到 " :
                                    item == "14" ? "早退 " :
                                    item == "15" ? "请假 " :null}}
                            </span> -->
          </template>
        </el-table-column>
        <el-table-column prop="amRemark" label="上午异常备注" width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input v-show="scope.row.actionType" v-model="scope.row.amRemark" :disabled="scope.row.amWhetherAbnormal == 1 ? false : true"/>
            <span v-show="!scope.row.actionType">
              {{ scope.row.amRemark }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="amWhetherHandle" label="上午是否处理" width="105px" align="center" >
          <template slot-scope="scope">
            <span >{{ scope.row.amWhetherHandle === '1' ? "未处理" : scope.row.amWhetherHandle === '2' ? "已处理" : '' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="pmWhetherAbnormal" label="下午是否异常" width="105px" align="center">
          <template slot-scope="scope">
            <!-- <el-select v-model="scope.row.pmWhetherAbnormal" v-show="scope.row.actionType" >
                            <el-option
                                v-for="item in options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.value"
                            ></el-option>
                        </el-select> -->
            <span >{{ scope.row.pmWhetherAbnormal === '1' ? "是" : scope.row.pmWhetherAbnormal === '' ? "" : '否' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="下午异常原因" width="105px" align="center">
          <template slot-scope="scope">
            <el-select
              v-show="scope.row.actionType"
              v-model="scope.row.pmAbnormalReason"
              :disabled="scope.row.pmWhetherAbnormal == 1 ? false : true"
            >
              <el-option
                v-for="item in pmAbnormalReasonList"
                :key="item.dictid"
                :label="item.dictname"
                :value="item.dictid"
              />
            </el-select>
            <span v-show="!scope.row.actionType">
              {{ getReasonName(scope.row.pmAbnormalReason) }}
            </span>
            <!-- <span v-show="!scope.row.actionType" v-for="(item, index) in scope.row.abnormalReasonList" :key="index">

                            {{  item == "0" ? "忘记打卡 ":
                                item == "1" ? '迟到 ' :
                                item == "2" ? '早退 ':
                                item == "3" ? "年假半天 " :
                                item == "4" ? "年假一天 " :
                                item == "5" ? '事假半天 ' :
                                item == "6" ? "事假一天 " :
                                item == "7" ? "旷工半天 " :
                                item == "8" ? "旷工一天 " :
                                item == "11" ? "忘打卡 " :
                                item == "12" ? "调休 " :
                                item == "13" ? "迟到 " :
                                item == "14" ? "早退 " :
                                item == "15" ? "请假 " :null}}
                        </span> -->
          </template>
        </el-table-column>
        <el-table-column prop="pmRemark" label="下午异常备注" width="100px" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input v-show="scope.row.actionType" v-model="scope.row.pmRemark" :disabled="scope.row.pmWhetherAbnormal == 1 ? false : true"/>
            <span v-show="!scope.row.actionType">
              {{ scope.row.pmRemark }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="pmWhetherHandle" label="下午是否处理" width="105px" align="center" >
          <template slot-scope="scope">
            <span >{{ scope.row.pmWhetherHandle === '1' ? "未处理" : scope.row.pmWhetherHandle === '2' ? "已处理" : '' }}</span>

          </template>
        </el-table-column>
        <!-- <el-table-column prop="" label="操作" width="100px">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="toModify(scope.row)" v-show="!scope.row.actionType">修改</el-link>
                        <el-link type="warning" :underline="false" @click="toSave(scope.row,scope.$index)" v-show="scope.row.actionType">保存</el-link>
                    </template>
                </el-table-column> -->
      </el-table>

      <!-- 弹框 -->
      <el-dialog
        :visible="dialogVisible"
        :before-close="handleClose"
        title="提示"
      >
        <span>该异常考勤信息已无法修改</span>
        <span slot="footer" >
          <el-button @click="dialogVisible=false">取 消</el-button>
          <el-button @click="dialogVisible=false">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import common from '@/api/common'
import { update, queryDetail } from '@/api/kqManager/wbAttendance'
import { getDicEntry, getDictListForStatus } from '@/api/eosDictEntry'

export default {
  data() {
    return {
      formData: {},
      tableData: [],
      id: '',
      options: [
        {
          id: 0,
          label: '否',
          value: '0'
        },
        {
          id: 1,
          label: '是',
          value: '1'
        }
      ],
      amAbnormalReasonList: [], // 上午异常原因
      pmAbnormalReasonList: [], // 下午异常原因
      abnormalReasonList: [], // 显示对应的异常原因
      dialogVisible: false, // 弹框
      isEdit: false,
      form: {
        attendanceMonth: '', // 考勤年月
        userName: '', // 姓名
        deptId: '', // 所属部门
        supplierId: '', // 供应商名称
        outsourceType: '', // 外包类型
        pmValue: '', // 项目经理
        type: '',
        timeType: '',
        otherExportTime: ''
      },
      clienHeight: ''
    }
  },
  created() {
    this.id = this.$route.query.id.row.id || ''
    this.form = this.$route.query.id.form
    this.getDic()
    this.getReasonDict()
    this.getInfo()
    this.clienHeight = document.body.clientHeight - 330
  },
  methods: {
    getInfo() {
      const loading = common.loading(this)
      queryDetail(this.id).then(res => {
        console.log(res)
        loading.close()
        if (res.data.status === 200) {
          this.$nextTick(() => {
            this.formData = res.data.results
            if (this.formData.exemptionNumber == undefined) {
              this.formData.exemptionNumber = '2'
            }
            this.tableData = res.data.results.kqRecordDtoList
          })
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },
    getDic() {
      getDicEntry({ dicttypeid: 'KQ_RECORD_REASON_DICT' }).then(res => {
        // console.log(res,"字典");
        const { results, status } = res.data
        if (status == 200) {
          this.abnormalReasonList = results
        }
      })
    },
    getReasonDict() {
      getDictListForStatus({ dicttypeid: 'KQ_RECORD_REASON_DICT', status: 1 }).then(res => {
        const { results, status } = res.data
        if (status == 200) {
          this.amAbnormalReasonList = results
          this.pmAbnormalReasonList = results
        }
      })
    },
    getReasonName(val) {
      var value = val
      if (value == undefined) {
        return ''
      }
      // console.log("选择的数据:",value)
      if (value.includes('/')) {
        const arr = value.split('/')
        console.log('选择的数据成数组:', arr)
        let retVal = ''
        arr.forEach(item => {
          for (var i = 0; i < this.abnormalReasonList.length; i++) {
            if (this.abnormalReasonList[i].dictid === item) {
              retVal = retVal + ' ' + this.abnormalReasonList[i].dictname
              continue
            }
          }
        })
        console.log('返回的值：', retVal)
        return retVal
      } else {
        for (var i = 0; i < this.abnormalReasonList.length; i++) {
          if (this.abnormalReasonList[i].dictid === val) {
            return this.abnormalReasonList[i].dictname
          }
        }
      }
    },

    // 修改按钮
    toModify(row) {
      // console.log(row, '修改按钮')
      if (row.whetherUpdate) {
        this.$message.error('当前时间段已无法修改上个月考勤')
        return
      }
      row.actionType = true
    },
    // 保存按钮
    toSave(row) {
      console.log(row, '保存按钮')
      // 需传ID
      const rowData = JSON.parse(JSON.stringify(row))
      if (rowData.amWhetherAbnormal === '1' && (rowData.amAbnormalReason == undefined || rowData.amAbnormalReason == '')) {
        return this.$message.error('请填写上午异常原因')
      }
      if (rowData.pmWhetherAbnormal === '1' && (rowData.pmAbnormalReason == undefined || rowData.pmAbnormalReason == '')) {
        return this.$message.error('请填写下午异常原因')
      }

      const data = common.paramsFormat(rowData)
      update(data).then(res => {
        console.log(res, '66666666666666')
      })
      row.actionType = false
    },

    toClose() {
      this.$router.push({
        name: 'wbAttendance',
        query: {
          id: {
            if: this.form,
            type: true
          }
        }
      })
    },
    handleClose: function() {
      this.dialogVisible = false
    },
    handleSelectAbnormal(row, date) {
      // console.log(row, date)
      if (date === 'am') {
        row.amAbnormalReasonList = []
      } else if (date === 'pm') {
        row.pmAbnormalReasonList = []
      }
    },
    tableRowClassName({ row, rowIndex }) {
      // console.log(row, rowIndex, 'qqqqqqqqqqqqqqqqqqqqqqqq')
      if (row.amWhetherAbnormal === '1' || row.pmWhetherAbnormal === '1') {
        return 'warring-row'
      }
    }
  }
}
</script>

<style scoped>
.margin_bottom {
    margin-bottom: 15px;
}
.marginWrapper {
    margin: 15px;
    background-color: #ffffff;
    padding: 15px;
}
.top {
    background-color: #ffffff;
    padding: 20px;
}
</style>

<style>
.el-table .warring-row {
    color: #ff590b;
}
</style>
