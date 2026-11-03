<template>
  <div class="interviewList">
    <!-- 表单部分 -->
    <div class="form_box">
      <el-form ref="formRef" :model="form" label-width="80px">
        <el-row type="flex" justify="space-between">
          <el-col :span="6">
            <el-form-item label="考勤年月">
              <el-date-picker
                v-model="form.attendanceMonth"
                :default-value="defaultDate"
                type="monthrange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM"
                @change="getFilterData"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="姓名">
              <el-input v-model.trim="form.userName" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属部门">
              <el-select v-model="form.deptId" placeholder="请选择部门">
                <el-option
                  v-for="item in depData"
                  :key="item.id"
                  :value="item.id"
                  :label="item.text"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="供应商名称" label-width="100px">
              <el-select
                v-model="form.supplierId"
                placeholder="请选择供应商"
                filterable
              >
                <el-option
                  v-for="item in outsourceno_options"
                  :key="item.id"
                  :label="item.outsourcname"
                  :value="item.orgcode"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row type="flex" justify="space-bwtween">
          <!-- <el-col :span="6">
                        <el-form-item label="外包方式">
                            <el-select v-model="form.outsourceType" placeholder="请选择外包方式">
                                <el-option
                                    v-for="item in wbTypeData"
                                    :key="item.value"
                                    :value="item.value"
                                    :label="item.label"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col> -->
          <el-col :span="6">
            <el-form-item label="项目经理">
              <el-input v-model="form.pmValue" placeholder="请输入项目经理"/>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-row type="flex" justify="end">
              <clean-btn :size="sizes" :forms="this.form" />
              <button-orange :button-name="buttonSearch" :size="sizes" :fns="submitBtn"/>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 表格部分 -->
    <div class="table_box">
      <el-row type="flex" justify="end" class="margin_bottom">
        <!-- <el-button v-if="auth" @click="dataRun">数据跑批</el-button> -->
        <!-- <el-button v-if="auth" @click="initdataRun">初始化年假</el-button> -->
        <el-button v-if="auth" @click="overTimeConfig">加班开始时间配置</el-button>
        <el-upload
          v-if="kqAuth"
          :file-list="fileList"
          :show-file-list="false"
          :http-request="changeUpload"
          action="Fake Action"
        >
          <el-button>导入</el-button>
        </el-upload>
        <!-- <el-button @click="handleDownload">导入模板下载</el-button> -->
        <!-- <button-common
                    :buttonName="buttonExportMonth"
                    :size="sizes_export"
                    :fns="handelExportMonth"
                ></button-common>
                <button-common
                    :buttonName="buttonExportDays"
                    :size="sizes_export"
                    :fns="handelExportDays"
                ></button-common> -->
        <button-common :size="sizes_export" :fns="clickExportButton" button-name="考勤信息导出"/>
      </el-row>
      <el-table
        :data="crewData"
        highlight-current-row
        height="510"
        @row-dblclick="dbTable"
      >
        <el-table-column
          prop="jobId"
          label="工号"
          align="center"
          width="100"
        />
        <el-table-column
          prop="deptName"
          label="所属部门"
          align="center"
          width="100"
        >
          <!-- <template slot-scope="scope">
                        {{ scope.row.deptName }}
                    </template> -->
        </el-table-column>
        <el-table-column
          prop="userName"
          label="姓名"
          align="center"
          width="100"
        />
        <el-table-column
          prop="supplierName"
          label="供应商名称"
          align="center"
          width="200"
          show-overflow-tooltip
        />
        <el-table-column
          prop="state"
          label="状态"
          align="center"
          width="100"
        >
          <template slot-scope="scope">
            <span v-show="scope.row.state == 0">在职</span>
            <span v-show="scope.row.state == 1">离岗</span>
            <span v-show="scope.row.state == 2">待入场</span>
          </template>
        </el-table-column>
        <el-table-column
          :formatter="createtimeFormat"
          prop="attendanceMonth"
          label="考勤年月"
          align="center"
          width="100"
        />
        <el-table-column
          prop="overtimeHours"
          label="当月加班时长(h)"
          align="center"
          width="100"
        />
        <el-table-column
          prop="pmName"
          label="项目经理"
          align="center"
          width="100"
        />
        <!-- <el-table-column
                    prop="outsourceType"
                    label="外包方式"
                    align="center"
                    width="100"
                >
                    <template slot-scope="scope">
                        <span v-show="scope.row.outsourceType == 0">项目外包</span>
                        <span v-show="scope.row.outsourceType == 1">人力外包</span>
                    </template>
                </el-table-column> -->
        <el-table-column
          prop="requiredAttendanceDays"
          label="当月应出勤"
          align="center"
          width="100"
        />
        <el-table-column
          prop="actualAttendanceDays"
          label="实际出勤"
          align="center"
          width="100"
        />
        <el-table-column
          label="操作"
          width="80"
        >
          <template slot-scope="scope">
            <el-link :underline="false" type="warning" @click="toDetails(scope.row, form)">详情</el-link>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div class="pager_zb">
        <pager ref="pager" :fns="getPager" :total="totalNumber" :page-size="size" v-model="$attrs" />
      </div>

      <!-- 弹框部分 -->
      <el-dialog :visible="dislogVisible" :before-close="handleClose" title="开始加班时间配置" >
        <el-form :model="overTimeForm" :inline="true">
          <el-form-item label="加班开始时间">
            <el-time-picker
              v-model="overTimeForm.startTime"
              :picker-options="{
                selectableRange: '18:00:00-23:59:59'
              }"
              placeholder="请选择开始加班时间"
              format="HH:mm:ss"
              value-format="HH:mm:ss"
            />
          </el-form-item>
          <el-form-item label="生效日期">
            <el-date-picker
              v-model="overTimeForm.effectDate"
              :picker-options="pickerOptions"
              placeholder="请选择开始加班日期"
              type="date"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleClose">取 消</el-button>
          <el-button type="primary" @click="btnOk">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog :visible.sync="exportDialogVisiable" :before-close="handleClose" title="导出信息" width="50%" >
        <div>
          <el-form ref="form" :model="form">
            <el-row type="flex" justify="space-between">
              <el-col :span="11">
                <p>导出类型</p>
                <el-form-item prop="type">
                  <el-select v-model="form.type" placeholder="请选择" @change="exportTypeChange">
                    <el-option v-for="item in exportOptions" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <!-- <el-col :span="11" v-if="typeTimeVisiable">
                                <p>未处理考勤查询月份</p>
                                <el-form-item  prop="type">
                                    <el-select v-model="form.timeType" placeholder="请选择"  @change="exportTimeChange">
                                        <el-option v-for="item in exportTimeOptions" :key="item.value" :label="item.label" :value="item.value" />
                                    </el-select>

                                </el-form-item>
                            </el-col> -->
              <el-col v-if="workVisiable" :span="11">
                <p>可减免的异常扣减天数</p>
                <el-form-item prop="monthlyReasonableAbnormalDays">
                  <el-select v-model="form.monthlyReasonableAbnormalDays">
                    <el-option value="0"/>
                    <el-option value="1"/>
                    <el-option value="2"/>
                    <el-option value="3"/>
                    <el-option value="4"/>
                    <el-option value="5"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col v-if="otherExportTimeVisiable" :span="11">
                <p>选择时间</p>
                <el-form-item prop="otherExportTime">
                  <!-- <el-select v-model="form.type" placeholder="请选择" @change="exportTypeChange">
                                        <el-option v-for="item in exportOptions" :key="item.value" :label="item.label" :value="item.value" :disabled="item.disabled"/>
                                    </el-select> -->
                  <el-date-picker
                    v-model="form.otherExportTime"
                    :picker-options="pickerOptionTimes"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    value-format="yyyy-MM-dd"/>
                </el-form-item>
              </el-col>
            </el-row>
            <!-- <el-row type="flex" justify="space-between" >
                            <el-col :span="11" v-if="otherExportTimeVisiable">
                                <p>选择时间</p>
                                <el-form-item  prop="otherExportTime">
                                    <el-date-picker
                                        v-model="form.otherExportTime"
                                        type="daterange"
                                        range-separator="至"
                                        start-placeholder="开始日期"
                                        end-placeholder="结束日期"
                                        :picker-options="pickerOptionTimes"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="11">
                            </el-col>
                        </el-row> -->
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="handleClose" >取 消</el-button>
          <el-button type="primary" @click="onExport">导出</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import common from '@/api/common'
import { queryPager, importExcel, importErrorExcel, queryRole, exportNoVerify, exportExcel, exportRecordExcel, queryOvertimeConfig, addOvertimeConfig, infoDataCalculateRunBatch, annualVacationBatch, exportWorkloadExcel } from '@/api/kqManager/wbAttendance'
import pager from '@/components/Pager'
import { queryResourceDept } from '@/api/integrated/wbryzrlc'
import Moment from 'moment'
import { getDicEntry } from '@/api/eosDictEntry'
import { getListAll } from '@/api/common/workflowApi'
import cleanBtn from '@/components/CleanBtn'
import buttonOrange from '@/components/eleButton/buttonOrange' // 引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import { getDownloadUrl } from '@/api/common/flowApi'
export default {
  components: {
    pager,
    cleanBtn,
    buttonOrange,
    buttonCommon
  },
  data() {
    return {
      totalNumber: 0, // 总条数
      size: 10, // 每页条数 初始值 默认：5
      index: 1, // 页数 初始值 默认：1
      form: {
        attendanceMonth: '', // 考勤年月
        userName: '', // 姓名
        deptId: '', // 所属部门
        supplierId: '', // 供应商名称
        outsourceType: '', // 外包类型
        pmValue: '', // 项目经理
        type: '',
        timeType: '',
        otherExportTime: '',
        monthlyReasonableAbnormalDays: ''
      },

      // 部门数据
      depData: [],
      // 供应商名字数据
      outsourceno_options: [],
      // 外包类型数据
      wbTypeData: [
        {
          value: '1',
          label: '人力外包'
        },
        {
          value: '0',
          label: '项目外包'
        }
      ],
      // 表格数据来源
      crewData: [],
      dislogVisible: false, // 弹框
      // 加班时间表单
      overTimeForm: {
        startTime: '', // 加班开始时间
        effectDate: '' // 加班生效日期
      },
      status: '1',
      peoForm_options: [], // 人员类型
      sizes: 'small', // 按钮大小
      buttonSearch: '查 询', // 查询按钮
      fileList: [], // 文件上传
      buttonExportMonth: '导出月明细', // 导出按钮
      buttonExportDays: '导出日明细', // 导出按钮
      sizes_export: 'medium', // 按钮大小
      defaultDate: '', // 默认日期
      exportDialogVisiable: false,
      auth: false,
      kqAuth: false,
      typeTimeVisiable: false,
      otherExportTimeVisiable: false,
      workVisiable: false,
      exportTimeOptions: [
        {
          value: 'LASY',
          label: '上月'
        },
        {
          value: 'NOW',
          label: '当月'
        },
        {
          value: 'OTHER',
          label: '自定义'
        }
      ],
      exportOptions: [
        {
          value: 'EMONTH',
          label: '月明细'
        },
        {
          value: 'EDAYS',
          label: '日明细'
        },
        {
          value: 'WORKLOAD',
          label: '工作量明细'
        }
      ],
      pickerOptions: {
        disabledDate(time) {
          // let zero = Date.now() -8.64e7;
          // console.log(zero,"6666666666666666666")
          // return time.getTime() < zero;
          const date = new Date()
          const year = date.getFullYear()
          let month = date.getMonth() + 1
          if (month >= 1 && month <= 9) {
            month = '0' + month
          }
          const currentdate = year.toString() + month.toString()
          // console.log(currentdate,'当前月份')
          const timeyear = time.getFullYear()
          let timemonth = time.getMonth() + 1
          if (timemonth >= 1 && timemonth <= 9) {
            timemonth = '0' + timemonth
          }
          const timedate = timeyear.toString() + timemonth.toString()
          return timedate < currentdate
          // const month = 30*24*60*60*1000
          // return time.getTime() < new Date() || time.getTime() > new Date().getTime() + month;
        }
      },
      pickerOptionTimes: {
        shortcuts: [{
          text: '昨日',
          onClick(picker) {
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', [start, start])
          }
        }, {
          text: '今日',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '本月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setDate(1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '上月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            end.setMonth(start.getMonth())
            start.setMonth(start.getMonth() - 1)
            end.setDate(0)
            start.setDate(1)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  computed: {
  },
  created() {
    this.getNowTime()
    this.getPermission()
    if (this.$route.query.id) {
      this.form = this.$route.query.id.if
    }
    this.getInit()
  },
  mounted() {
    this.getWbcom()
    this.filDep()
    // this.form.monthlyReasonableAbnormalDays = '2'
  },
  methods: {

    exportTypeChange() {
      if (this.form.type == 'ENOVER') {
        this.otherExportTimeVisiable = true
        this.workVisiable = false
        this.form.monthlyReasonableAbnormalDays = ''
      } else if (this.form.type == 'WORKLOAD') {
        this.workVisiable = true
        this.otherExportTimeVisiable = false
        this.form.timeType = ''
        this.form.monthlyReasonableAbnormalDays = '2'
      } else {
        this.workVisiable = false
        this.otherExportTimeVisiable = false
        this.form.monthlyReasonableAbnormalDays = ''
        this.form.timeType = ''
      }
      // this.otherExportTimeVisiable = false;
    },

    clickExportButton() {
      // let exportOption = this.exportOptions;
      if ((this.auth || this.kqAuth) && this.exportOptions.length < 4) {
        this.exportOptions.push({ value: 'ENOVER', label: '未处理明细' })
      }
      this.form.type = ''
      this.workVisiable = false
      this.exportDialogVisiable = true
    },
    // 分页初始化
    getInit() {
      const paramsQuery = window.localStorage.getItem('queryParams')
      console.log(paramsQuery)
      let queryParams = null
      if (typeof paramsQuery === 'undefined') {
        queryParams = JSON.parse(paramsQuery)
      }
      console.log('queryParams', queryParams)
      let params = {}
      if (queryParams == null || Object.keys('queryParams').length == 0) {
        params.size = this.size
        params.index = this.index
        params.attendanceMonth = this.defaultDate
        if (this.$route.query.id) {
          params = this.$route.query.id.if
        }
      } else {
        window.localStorage.clear('queryParams')
        if (queryParams.attendanceMonth) {
          this.$set(this.form, 'attendanceMonth', queryParams.attendanceMonth)
        }
        this.form.userName = queryParams.userName
        this.form.deptId = queryParams.deptId
        this.form.supplierId = queryParams.supplierId
        this.form.outsourceType = queryParams.outsourceType
        this.form.pmValue = queryParams.pmValue

        params = queryParams
        params.size = this.size
        params.index = this.index
      }

      this.getPagerList(params)
      this.$router.go(1)
    },
    // 获取列表数据
    getPagerList(params) {
      const data = common.paramsFormat(params)
      queryPager(data).then(res => {
        console.log(res, 'res')
        if (res.data.status == 200) {
          this.crewData = res.data.results
          this.totalNumber = res.data.total
          if (this.totalNumber <= 0) {
            return false
          }
        }
      })
    },
    // 查询按钮
    submitBtn() {
      const params = this.form
      for (var k in params) {
        if (params[k] && this.index > 1) {
          this.index = 1
        }
      }
      params.size = this.size
      params.index = this.index
      this.getPagerList(params)
    },
    // 详情按钮
    toDetails(row, form) {
      console.log(row.id, form, 'row')
      window.localStorage.setItem('queryParams', JSON.stringify(form))
      this.$router.push({
        name: 'detils',
        query: {
          id: {
            row,
            form
          }

        }
      })
    },
    /**
         * 分页触发
         */
    getPager: function(size, index) {
      const params = this.form
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    // 加班时间开始配置
    overTimeConfig() {
      this.dislogVisible = true
      // let data = this.status
      console.log(this.status)
      queryOvertimeConfig(this.status).then(res => {
        // console.log(res)
        this.overTimeForm = res.data.results
      })
    },
    handleClose() {
      this.dislogVisible = false
      this.exportDialogVisiable = false
      this.form.type = ''
      // this.form.timeType = '';
      // this.typeTimeVisiable = false;
      this.otherExportTime = ''
      this.otherExportTimeVisiable = false
    },
    btnOk() {
      console.log(this.overTimeForm)
      if (this.overTimeForm.startTime && this.overTimeForm.effectDate) {
        const data = this.overTimeForm
        // console.log(data)
        addOvertimeConfig(data).then(res => {
          console.log(res)
          if (res.data.status == 200) {
            this.$message.success('加班时间配置成功')
          }
        })
        this.dislogVisible = false
      } else {
        return this.$message.error('请填入数据')
      }
    },
    //  时间格式化
    createtimeFormat: function(row, cloumn) {
      let time = ''
      if (row.attendanceMonth != null) {
        time = Moment(row.attendanceMonth).format('YYYY-MM')
      }
      return time
    },
    // 查询业务字典
    // 获取部门
    filDep() {
      const params = {}
      params.objectcode = '6000000300'
      params.objectgrade = '2'
      queryResourceDept(params)
        .then(res => {
          var fild = res.data.results[0].children
          const name = res.data.results[0].children[0].text
          this.depData = fild.filter(tt => {
            tt.text = tt.text.replace(name + '-', '')
            return tt
          })
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 查询外包商
    getWbcom() {
      getListAll()
        .then(res => {
          this.outsourceno_options = res.data.results
        })
        .catch(error => {
          this.$message.error(error)
        })
    },
    // 导入
    async changeUpload(file, fileList) {
      // console.log(file, fileList)
      var formData = new FormData()
      formData.append('file', file.file)
      const data = formData
      importExcel(data).then(res => {
        const { message, status, results } = res.data
        if (status == '200') {
          if (message == 'false') {
            const self = this
            console.log(results, '导入参数')
            const params = JSON.stringify(results)
            // console.log(params)
            return importErrorExcel(params).then(res => {
              common.download(res.data, self, '人员异常原因.xls')
            })
          } else {
            this.$message.success('导入成功')
          }
        } else {
          this.$message.error(message)
        }
        this.getInit()
      })
    },
    // 导入模板下载
    handleDownload() {
      const loading = common.loading(this)
      getDownloadUrl({ key: 'PPM_NEW_KQXX' }).then(res => {
        loading.close()
        if (res.data.status == 200) {
          const self = this
          common.downloadJl(res.data.results.documentno, self)
        } else {
          this.$message.error('获取下载地址失败')
        }
      })
    },
    // 导出
    onExport() {
      var searchData = this.form
      searchData.deptId = searchData.deptId
      var type = searchData.type
      console.log('111111111111111')
      if (type == 'WORKLOAD') {
        this.handelExportWorkload()
        this.exportDialogVisiable = false
      } else if (type == 'EMONTH') {
        this.handelExportMonth()
        this.exportDialogVisiable = false
      } else if (type == 'EDAYS') {
        this.handelExportDays()
        this.exportDialogVisiable = false
      } else if (type == 'ENOVER') {
        this.handelExportNoVerify()
        this.exportDialogVisiable = false
      } else {
        this.$message.error('导出类型不可为空！')
      }
    },
    // 工作量明细导出
    handelExportWorkload() {
      var searchData = this.form
      searchData.deptId = searchData.deptId
      console.log(searchData)
      const params = common.paramsFormat(searchData)

      const self = this
      const loading = common.loading(this)
      exportWorkloadExcel(params).then(res => {
        loading.close()
        Moment(new Date()).format('YYYYMMDD')
        common.download(res.data, self, Moment(new Date()).format('YYYYMMDD') + '工作量明细考勤.xls')
      })
    },
    // 导出未处理异常考勤
    handelExportNoVerify() {
      var searchData = this.form
      searchData.deptId = searchData.deptId
      const params = common.paramsFormat(searchData)
      const self = this
      console.log('888', searchData.otherExportTime)
      if (searchData.otherExportTime != undefined && searchData.otherExportTime.length == 2) {
        const loading = common.loading(this)
        exportNoVerify(params).then(res => {
          loading.close()
          common.download(res.data, self, '未处理异常考勤.xls')
        })
      } else {
        this.$message.error('时间需为周期时间')
      }
    },
    // 导出
    handelExportMonth() {
      var searchData = this.form
      searchData.deptId = searchData.deptId
      // console.log(searchData)
      const params = common.paramsFormat(searchData)

      const self = this
      const loading = common.loading(this)
      exportExcel(params).then(res => {
        loading.close()
        common.download(res.data, self, '外包考勤.xls')
      })
    },
    // 导出
    handelExportDays() {
      var searchData = this.form
      searchData.deptId = searchData.deptId
      // console.log(searchData)
      const params = common.paramsFormat(searchData)
      const self = this
      // console.log(params,'1212121')
      exportRecordExcel(params).then(res => {
        // console.log(res)
        common.download(res.data, self, '外包考勤详情.xls')
      })
    },
    getFilterData() {
      this.$emit('getFilterData', this.form)
    },
    // 获取当前年月份
    getNowTime() {
      var now = new Date()
      var year = now.getFullYear()
      var month = now.getMonth() + 1
      month = month.toString().padStart(2, '0')
      this.defaultDate = `${year}-${month}`
      this.$set(this.form, 'attendanceMonth', this.defaultDate)
    },
    // 获取权限
    getPermission() {
      queryRole().then(res => {
        if (res.data.status == 200) {
          // console.log(res,"获取权限")
          const roles = res.data.results
          let auth = false
          let kqAuth = false
          roles.forEach(function(item) {
            if (item.roleId == '00000001') {
              auth = true
            }
            if (item.roleId == 'KQ_YANGUANBU' || item.roleId == 'PERMANAGE') {
              kqAuth = true
            }
          })
          this.auth = auth
          this.kqAuth = kqAuth

          // console.log("auth",this.auth);
        } else {
          this.$message.error('查询权限失败')
        }
      })
    },
    // 数据跑批
    dataRun() {
      infoDataCalculateRunBatch().then(res => {
        // console.log(res, '数据跑批')
        this.$message.success('数据跑批启动')
        this.getInit()
      })
    },
    // 初始化年假
    initdataRun() {
      annualVacationBatch().then(res => {
        // console.log(res, '数据跑批')
        this.$message.success('初始化年假')
        this.getInit()
      })
    },
    // 双击表格
    dbTable(val) {
      // console.log(val)
      this.toDetails(val)
    }
  }
}
</script>

<style scoped>
.interviewList {
    width: 100%;
    /* height: 100%; */
    padding: 15px;
}
.form_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px 18px 0 18px;
}
.table_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px;
}
.margin_bottom {
    margin-bottom: 15px
}
/* 分页组件位置 */
.pager_zb {
    width: 100%;
    display: flex;
    flex-direction: row-reverse;
}
</style>

<style>
.el-button {
    margin-left: 15px
}
.el-row {
    margin-bottom:  15px
}
</style>
