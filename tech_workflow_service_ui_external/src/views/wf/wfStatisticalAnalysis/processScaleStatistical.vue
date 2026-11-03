<!-- 流程实例规模统计 -->
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
            <el-tab-pane label="流程实例规模统计分析">
              <div>
                <div style="float: left">{{ wfName + '流程规模统计分析' }}</div>
                <el-button @click="backLast" style="float: right;margin-bottom: 10px" size="mini">返回上一级</el-button>
              </div>
              <el-table :data="tableData" style="width: 100%" size="mini" height="400">
                <el-table-column prop="name" :label="qtype === 3 ?'月份':'流程名称'">
                  <template slot-scope="scope">
                    <el-button @click="queryNext(scope.row)" size="mini" type="text">{{ scope.row.name }}</el-button>
                  </template>
                </el-table-column>
                <el-table-column prop="active" label="启动"></el-table-column>
                <el-table-column prop="completed" label="完成"></el-table-column>
                <el-table-column prop="terminated" label="已终止"></el-table-column>
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
            <el-tab-pane label="流程规模统计分析图表">
              <div id="execution1" class="chart" :style="{ 'display': showMonth?'none':'unset' }"></div>
              <div id="execution2" class="chart" :style="{ 'display': showMonth?'unset':'none' }"></div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'processScaleStatistical',
  components: {},
  data() {
    return {
      dialogVisible: false,
      wfName: '',
      query: {
        wfValue: '',
        startYear: '',
        endYear: ''
      },
      treeOptions: {
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId }
      },
      modelKey: '',
      modelKeyNm: '',
      classifyId:'',
      classifyIdNm:'',
      qtype: 1,// 查询类型：1总体 2按流程版本（一次只查一个流程） 3按月（一次只查一个流程的一个版本）
      tableData: [],
      originTableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      barChartOption:{},
      lineChartOption:{},
      processScaleChart: null,
      processMonthChart: null,
      showMonth: false,
    }
  },
  mounted() {
    this.initBarChart()
    this.initLineChart()
  },
  methods: {
    selectWfValue() {
      this.dialogVisible = true
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
        this.queryProcessScale(array)
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
      this.queryProcessScale(array)
    },
    queryProcessScale(entitys) {
      //流程实例执行时间统计
      let param = {
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        qtype: this.qtype,
        entitys: entitys
      }
      this.$request({
        url: this.$api.processScaleStatistics, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        if(this.qtype === 3){
          this.pageSize = 12
          this.originTableData = this.setColumnList(response.data,12)
          this.total = response.data.length
          this.tableData = this.originTableData[0]
          //设置图表数据
          this.updateLineChart()
        }
        if (this.qtype !== 3) {
          this.pageSize = 10
          if(this.qtype === 2){
            this.originTableData = this.setColumnList(response.data,10)
            this.total = response.data.length
            this.tableData = this.originTableData[0]
          } else{
            this.tableData = response.data
          }
          //设置图表数据
          this.updateBarChart()
        }

      })
    },
    queryNext(row) {
      // 查询下一级
      if (this.qtype !== 3) {
        this.wfName = row.name
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
        this.queryProcessScale(array)
      }
    },
    currentChange(number) {
      //切换页数
      this.pageNum = number
      if (this.qtype === 1) {
        this.queryProcessList()
      }
      if (this.qtype === 2) {
        this.tableData = this.originTableData[this.pageNum - 1]
        this.updateBarChart()
      }
      if (this.qtype === 3) {
        this.tableData = this.originTableData[this.pageNum - 1]
        this.updateLineChart()
      }
    },
    updateLineChart(){
      //折线图数据更新
      this.lineChartOption.series[0].data = []
      this.lineChartOption.series[1].data = []
      this.lineChartOption.series[2].data = []
      this.tableData.forEach((item) => {
        this.lineChartOption.series[0].data.push(item.active)
        this.lineChartOption.series[1].data.push(item.completed)
        this.lineChartOption.series[2].data.push(item.terminated)
      })
      this.processMonthChart.setOption(this.lineChartOption)
    },
    updateBarChart(){
      //柱状图数据更新
      this.barChartOption.xAxis[0].data = []
      this.barChartOption.series[0].data = []
      this.barChartOption.series[1].data = []
      this.barChartOption.series[2].data = []
      this.tableData.forEach((item) => {
        this.barChartOption.xAxis[0].data.push(item.name)
        this.barChartOption.series[0].data.push(item.active)
        this.barChartOption.series[1].data.push(item.completed)
        this.barChartOption.series[2].data.push(item.terminated)
      })
      this.processScaleChart.setOption(this.barChartOption)
    },
    backLast() {
      //回退到上一级
      this.showMonth = false
      if(this.qtype === 3){
        this.wfName = this.modelKeyNm
        this.queryVersionList()
        return
      }
      if(this.qtype === 2){
        this.wfName = this.classifyIdNm
        this.pageNum = 1
        this.queryProcessList()
      }
    },
    initBarChart() {
      //流程规模统计图
      this.processScaleChart = echarts.init(document.getElementById('execution1'))

      this.barChartOption = {
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
                '启动：' +
                params[0].data +
                '</br>' +
                '完成：' +
                params[1].data +
                '</br>' +
                '已终止：' +
                params[2].data +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['启动', '完成', '已终止']
        },
        xAxis: [
          {
            type: 'category',
            axisLabel: {
              show: true,
              interval: 0,
              fontSize:9,
              // rotate:40,
            },
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
            name: '启动',
            type: 'bar',
            data: []
          },
          {
            name: '完成',
            type: 'bar',
            data: []
          },
          {
            name: '已终止',
            type: 'bar',
            data: []
          }
        ]
      }
      this.processScaleChart.setOption(this.barChartOption)
      window.addEventListener('resize', function() {
        if (this.processScaleChart) {
          this.processScaleChart.resize()
        }
      })
    },
    initLineChart() {
      //单个流程规模折线图
      this.processMonthChart = echarts.init(document.getElementById('execution2'))

      this.lineChartOption = {
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
                '启动：' +
                params[0].data +
                '</br>' +
                '完成：' +
                params[1].data +
                '</br>' +
                '已终止：' +
                params[2].data +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['启动', '完成', '已终止']
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
            name: '启动',
            type: 'line',
            data: []
          },
          {
            name: '完成',
            type: 'line',
            data: []
          },
          {
            name: '已终止',
            type: 'line',
            data: []
          }
        ]
      }
      this.processMonthChart.setOption(this.lineChartOption)
      window.addEventListener('resize', function() {
        if (this.processMonthChart) {
          this.processMonthChart.resize()
        }
      })
    },
    handleNodeClick(node) {
      this.wfName = node.name + '各'
      this.classifyIdNm = node.name + '各'
      this.classifyId = node.id
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
