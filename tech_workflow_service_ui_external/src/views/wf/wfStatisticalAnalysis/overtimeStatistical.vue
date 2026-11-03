<!-- 超时统计分析 -->
<template>
  <div style="margin-left: 30px;margin-right: 30px">
    <div class="layout" style="margin-top: 20px">
      <el-form ref="searchForm" inline>
        <el-form-item label="业务目录:">
          <ts-multi-tree
              v-model="query.wfValue"
              ref="mTree"
              :options="treeOptions"
              @node-click="handleNodeClick"></ts-multi-tree>
        </el-form-item>
        <el-form-item label="时间段:">
          <el-date-picker
              style="width: 160px"
              v-model="query.startYear"
              value-format="yyyy"
              type="year"
              placeholder="开始年">
          </el-date-picker>
          <span> - </span>
          <el-date-picker
              style="width: 160px"
              v-model="query.endYear"
              value-format="yyyy"
              type="year"
              placeholder="结束年">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="24" style="padding-bottom: 10px">
      <el-col :span="14">
        <div class="ts-tabs-box">
          <el-tabs>
            <el-tab-pane label="流程超时统计分析">
              <div>
                <div style="float: left">{{ wfName }}</div>
                <el-button @click="backLast" style="float: right;margin-bottom: 10px" size="mini">返回上一级</el-button>
              </div>
              <el-table :data="tableData" style="width: 100%" size="mini" height="400">
                <el-table-column prop="name" :label="qtype === 3 ?'月份':'流程名称'">
                  <template slot-scope="scope">
                    <el-button @click="queryNext(scope.row)" size="mini" type="text">{{ scope.row.name }}</el-button>
                  </template>
                </el-table-column>
                <el-table-column label="流程超时统计" align="center">
                  <el-table-column prop="instCompleted" label="已完成" align="center"></el-table-column>
                  <el-table-column prop="instTimeout" label="超时" align="center">
                    <template slot-scope="scope">
                      <el-button @click="getOverInfo(1,scope.row)" size="mini" type="text">{{
                          scope.row.instTimeout
                        }}
                      </el-button>
                    </template>
                  </el-table-column>
                  <el-table-column prop="instRate" label="超时率" align="center"></el-table-column>
                </el-table-column>
                <el-table-column label="任务超时统计" align="center">
                  <el-table-column prop="actCompleted" label="已完成" align="center"></el-table-column>
                  <el-table-column prop="actTimeout" label="超时" align="center">
                    <template slot-scope="scope">
                      <el-button @click="getOverInfo(2,scope.row)" size="mini" type="text">{{
                          scope.row.actTimeout
                        }}
                      </el-button>
                    </template>
                  </el-table-column>
                  <el-table-column prop="actRate" label="超时率" align="center"></el-table-column>
                </el-table-column>
              </el-table>
              <div style="margin-top: 15px;text-align:right;">
                <el-pagination
                    @current-change="currentChange"
                    :current-page="pageNum"
                    :page-size="pageSize"
                    layout="total, prev, pager, next, jumper"
                    :total="total">
                </el-pagination>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="ts-tabs-box">
          <el-tabs>
            <el-tab-pane label="流程超时统计分析图表">
              <div id="overtime1" class="chart" :style="{ 'display': showMonth?'none':'unset' }"></div>
              <div id="overtime3" class="chart" :style="{ 'display': showMonth?'unset':'none' }"></div>
            </el-tab-pane>
            <el-tab-pane label="任务超时统计分析图表">
              <div id="overtime2" class="chart" :style="{ 'display': showMonth?'none':'unset' }"></div>
              <div id="overtime4" class="chart" :style="{ 'display': showMonth?'unset':'none' }"></div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
    <ts-multi-dialogs :visible.sync="visibleDialog" :options="options"></ts-multi-dialogs>
  </div>
</template>

<script>
import echarts from 'echarts'
import overtimeInfoListDialog from './overtimeInfoListDialog'
import TsMultiDialogs from '@/components/TsMultiDialogs/index'

export default {
  name: 'overtimeStatistical',
  components: { TsMultiDialogs },
  data() {
    return {
      query: {
        wfValue: '',
        startYear: '',
        endYear: ''
      },
      wfName: '',
      treeOptions: {
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId }
      },
      visibleDialog: false,
      options: {
        title: '超时详细信息', // [必填]
        resource: overtimeInfoListDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '80%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: null, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [null, null], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      tableData: [],
      originTableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      showMonth: false,
      qtype: '',
      modelKey: '',
      modelKeyNm: '',
      classifyId: '',
      classifyIdNm: '',
      barChartOption1: {},
      barChartOption2: {},
      lineChartOption1: {},
      lineChartOption2: {},
      overtimeBarChart1: null,
      overtimeBarChart2: null,
      overtimeLineChart1: null,
      overtimeLineChart2: null
    }
  },
  mounted() {
    this.initBarChart1()
    this.initBarChart2()
    this.initLineChart1()
    this.initLineChart2()
  },
  methods: {
    handleNodeClick(node) {
      this.wfName = node.name + '流程和任务超时统计分析'
      this.classifyIdNm = node.name
      this.classifyId = node.id
    },
    search() {
      if (this.query.wfValue == '') {
        this.$tsVue.warn('请选择业务目录')
        return
      }
      if (!this.query.startYear) {
        this.$tsVue.warn('起始年份不能为空')
        return
      }
      if (!this.query.endYear) {
        this.$tsVue.warn('结束年份不能为空')
        return
      }
      if (parseInt(this.query.startYear) > parseInt(this.query.endYear)) {
        this.$tsVue.warn('起始年份不能大于结束年份')
        return
      }
      if (parseInt(this.query.endYear) - parseInt(this.query.startYear) > 11) {
        this.$tsVue.warn('起止年份不能超过12年')
        return
      }
      this.showMonth = false
      this.queryProcessList()
    },
    reset() {
      this.query = {
        wfValue: '',
        startYear: '',
        endYear: ''
      }
      this.qtype = ''
    },
    queryProcessList() {
      //查询流程列表
      let param = {
        classifyId: this.classifyId,
        pageNum: this.pageNum,
        pageSize: 10,
        tenantId: this.$store.state.user.userInfo.tenantId
      }
      this.$request({
        url: this.$api.processList, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.total = response.data.grid.total
        let array = []
        response.data.grid.list.forEach((item) => {
          let data = {
            modelKey: item.modelKey
          }
          array.push(data)
        })
        this.qtype = 1
        this.queryOvertime(array)
      })
    },
    queryVersionList() {
      //查询流程版本list
      let array = []
      let data = {
        modelKey: this.modelKey
      }
      array.push(data)
      this.qtype = 2
      this.queryOvertime(array)
    },
    queryOvertime(entitys) {
      //查询超时统计
      let param = {
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        qtype: this.qtype,
        entitys: entitys
      }
      this.$request({
        url: this.$api.overtimeStatistics, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        if (this.qtype === 3) {
          this.pageSize = 12
          this.originTableData = this.setColumnList(response.data, 12)
          this.total = response.data.length
          this.tableData = this.originTableData[0]
          //设置图表数据
          this.updateLineChart1()
          this.updateLineChart2()
        }
        if (this.qtype !== 3) {
          this.pageSize = 10
          if (this.qtype === 2) {
            this.originTableData = this.setColumnList(response.data, 10)
            this.total = response.data.length
            this.tableData = this.originTableData[0]
          } else {
            this.tableData = response.data
          }
          //设置图表数据
          this.updateBarChart1()
          this.updateBarChart2()
        }
      })
    },
    currentChange(number) {
      //切换页数
      this.pageNum = number
      if (this.qtype === 1) {
        this.queryProcessList()
      }
      if (this.qtype === 2) {
        this.tableData = this.originTableData[this.pageNum - 1]
        this.updateBarChart1()
        this.updateBarChart2()
      }
      if (this.qtype === 3) {
        this.tableData = this.originTableData[this.pageNum - 1]
        this.updateLineChart1()
        this.updateLineChart2()
      }
    },
    queryNext(row) {
      //查询下一级
      if (this.qtype !== 3) {
        this.wfName = row.name + '流程和任务超时统计分析'
      }
      //查询流程版本
      if (this.qtype === 1) {
        this.pageNum = 1
        this.modelKey = row.modelKey
        this.modelKeyNm = row.name
        this.queryVersionList()
        return
      }
      //查询流程版本月份
      if (this.qtype === 2) {
        let array = []
        let data = {
          modelKey: this.modelKey,
          modelVersion: row.modelVersion
        }
        array.push(data)
        this.qtype = 3
        this.showMonth = true
        this.queryOvertime(array)
      }
    },
    backLast() {
      //回退到上一级
      this.showMonth = false
      if (this.qtype === 3) {
        this.wfName = this.modelKeyNm + '流程和任务超时统计分析'
        this.queryVersionList()
        return
      }
      if (this.qtype === 2) {
        this.wfName = this.classifyIdNm + '流程和任务超时统计分析'
        this.queryProcessList()
      }
    },
    getOverInfo(type, row) {
      //查询超时明细
      if (type === 1 && row.instTimeout == '0') {
        return
      }
      if (type === 2 && row.actTimeout == '0') {
        return
      }
      this.options.data = {
        type: type,
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        searchTimeDetail: this.qtype === 3 ? row.name : undefined,
        modelName: this.qtype === 3 ? this.modelKeyNm :row.name,
        modelKey: row.modelKey,
        modelVersion: row.modelVersion ? row.modelVersion : undefined
      }
      this.visibleDialog = true
    },
    updateBarChart1() {
      //流程柱状图数据更新
      this.barChartOption1.xAxis[0].data = []
      this.barChartOption1.series[0].data = []
      this.barChartOption1.series[1].data = []
      this.tableData.forEach((item) => {
        this.barChartOption1.xAxis[0].data.push(item.name)
        this.barChartOption1.series[0].data.push(item.instCompleted)
        this.barChartOption1.series[1].data.push(item.instTimeout)
      })
      this.overtimeBarChart1.setOption(this.barChartOption1)
    },
    updateBarChart2() {
      //活动柱状图数据更新
      this.barChartOption2.xAxis[0].data = []
      this.barChartOption2.series[0].data = []
      this.barChartOption2.series[1].data = []
      this.tableData.forEach((item) => {
        this.barChartOption2.xAxis[0].data.push(item.name)
        this.barChartOption2.series[0].data.push(item.actCompleted)
        this.barChartOption2.series[1].data.push(item.actTimeout)
      })
      this.overtimeBarChart2.setOption(this.barChartOption2)
    },
    updateLineChart1() {
      //流程按月超时率折线图数据更新
      this.lineChartOption1.series[0].data = []
      this.tableData.forEach((item) => {
        let rate = item.actRate.substring(0,item.actRate.indexOf('%'))
        this.lineChartOption1.series[0].data.push(rate)
      })
      this.overtimeLineChart1.setOption(this.lineChartOption1)
    },
    updateLineChart2() {
      //活动按月超时率折线图数据更新
      this.lineChartOption2.series[0].data = []
      this.tableData.forEach((item) => {
        let rate = item.actRate.substring(0,item.actRate.indexOf('%'))
        this.lineChartOption2.series[0].data.push(rate)
      })
      this.overtimeLineChart2.setOption(this.lineChartOption2)
    },
    initBarChart1() {
      //流程超时柱状图
      this.overtimeBarChart1 = echarts.init(document.getElementById('overtime1'))

      this.barChartOption1 = {
        color: ['#007BFF', '#4BDAFF', '#FFC336'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter(params) {
            let data =
                params[0].name +
                '</br>' +
                '已完成：' +
                params[0].data +
                '</br>' +
                '超时：' +
                params[1].data +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['已完成', '超时']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: []
          }
        ],
        yAxis: [
          {
            name: '',
            nameLocation: 'end', //可选：'start'，'middle'，'end'
            nameGap: 8,
            nameTextStyle: { padding: [5, 40, 0, 0] },
            type: 'value',
            axisLabel: {
              show: true
            },
            splitLine: {
              show: true,
              lineStyle: {
                width: 0.5
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '已完成',
            type: 'bar',
            data: []
          },
          {
            name: '超时',
            type: 'bar',
            data: []
          }
        ]
      }
      this.overtimeBarChart1.setOption(this.barChartOption1)
      window.addEventListener('resize', function() {
        if (this.overtimeBarChart1) {
          this.overtimeBarChart1.resize()
        }
      })
    },
    initBarChart2() {
      //任务超时柱状图
      this.overtimeBarChart2 = echarts.init(document.getElementById('overtime2'))

      this.barChartOption2 = {
        color: ['#007BFF', '#4BDAFF', '#FFC336'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter(params) {
            let data =
                params[0].name +
                '</br>' +
                '已完成：' +
                params[0].data +
                '</br>' +
                '超时：' +
                params[1].data +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['已完成', '超时']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: []
          }
        ],
        yAxis: [
          {
            name: '',
            nameLocation: 'end', //可选：'start'，'middle'，'end'
            nameGap: 8,
            nameTextStyle: { padding: [5, 40, 0, 0] },
            type: 'value',
            axisLabel: {
              show: true
            },
            splitLine: {
              show: true,
              lineStyle: {
                width: 0.5
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '已完成',
            type: 'bar',
            data: []
          },
          {
            name: '超时',
            type: 'bar',
            data: []
          }
        ]
      }
      this.overtimeBarChart2.setOption(this.barChartOption2)
      window.addEventListener('resize', function() {
        if (this.overtimeBarChart2) {
          this.overtimeBarChart2.resize()
        }
      })
    },
    initLineChart1() {
      //流程超时折线图
      this.overtimeLineChart1 = echarts.init(document.getElementById('overtime3'))

      this.lineChartOption1 = {
        color: ['#007BFF', '#4BDAFF', '#FFC336'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter(params) {
            let data =
                params[0].name +
                '</br>' +
                '超时率：' +
                params[0].data + '%' +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['超时率']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            axisLabel: {
              show: true,
              interval: 0
              // rotate:40,
            },
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            name: '(%)',
            nameLocation: 'end', //可选：'start'，'middle'，'end'
            nameGap: 8,
            nameTextStyle: { padding: [5, 40, 0, 0] },
            type: 'value',
            axisLabel: {
              show: true
            },
            splitLine: {
              show: true,
              lineStyle: {
                width: 0.5
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '超时率',
            type: 'line',
            data: []
          }
        ]
      }
      this.overtimeLineChart1.setOption(this.lineChartOption1)
      window.addEventListener('resize', function() {
        if (this.overtimeLineChart1) {
          this.overtimeLineChart1.resize()
        }
      })
    },
    initLineChart2() {
      //任务超时折线图
      this.overtimeLineChart2 = echarts.init(document.getElementById('overtime4'))

      this.lineChartOption2 = {
        color: ['#007BFF', '#4BDAFF', '#FFC336'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter(params) {
            let data =
                params[0].name +
                '</br>' +
                '超时率：' +
                params[0].data + '%' +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['超时率']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            axisLabel: {
              show: true,
              interval: 0
              // rotate:40,
            },
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            name: '(%)',
            nameLocation: 'end', //可选：'start'，'middle'，'end'
            nameGap: 8,
            nameTextStyle: { padding: [5, 40, 0, 0] },
            type: 'value',
            axisLabel: {
              show: true
            },
            splitLine: {
              show: true,
              lineStyle: {
                width: 0.5
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '超时率',
            type: 'line',
            data: []
          }
        ]
      }
      this.overtimeLineChart2.setOption(this.lineChartOption2)
      window.addEventListener('resize', function() {
        if (this.overtimeLineChart2) {
          this.overtimeLineChart2.resize()
        }
      })
    },
    setColumnList(rows, num) {
      let ArrayResult = []
      if (rows.length > 0) {
        let rowArray = []
        for (let i = 0; i < rows.length; i++) {
          rowArray.push(rows[i])
          if (rowArray.length === num && i !== 0) {
            ArrayResult.push(rowArray)
            rowArray = []
          }
          if ((rows.length - 1) === i && rowArray.length !== num) {
            ArrayResult.push(rowArray)
          }
        }
      }
      return ArrayResult
    }
  }
}
</script>

<style lang="scss" scoped>
.ts-tabs-box {
  /deep/ .el-tabs__content {
    padding-top: 0 !important;
  }
}

.chart {
  width: 500px;
  height: 480px;
}

.layout {
  padding: 10px;
}
</style>
