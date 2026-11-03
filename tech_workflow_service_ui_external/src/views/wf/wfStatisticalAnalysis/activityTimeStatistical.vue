<!-- 活动实例执行时间统计 -->
<template>
  <div style="margin-left: 30px;margin-right: 30px">
    <div class="layout" style="margin-top: 20px">
      <el-form ref="searchForm" inline>
        <el-form-item label="流程名称:">
          <ts-multi-select v-model="query.wfValue" :params="selectWfNameParam" @change="selectWfName"></ts-multi-select>
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
            <el-tab-pane label="活动实例执行时效统计分析">
              <div>
                <div style="float: left">{{ wfName + '时效统计分析' }}</div>
                <el-button @click="backLast" style="float: right;margin-bottom: 10px" size="mini">返回上一级</el-button>
              </div>
              <el-table :data="tableData" style="width: 100%" size="mini" height="400">
                <el-table-column prop="name" :label="qtype === 3 ?'月份':'流程名称'">
                  <template slot-scope="scope">
                    <el-button @click="queryNext(scope.row)" size="mini" type="text">{{ qtype === 2 ? scope.row.actName : scope.row.name }}</el-button>
                  </template>
                </el-table-column>
                <el-table-column label="时间(分)" align="center">
                  <el-table-column prop="average" label="平均执行时间"></el-table-column>
                  <el-table-column prop="maximum" label="最长执行时间"></el-table-column>
                  <el-table-column prop="minimum" label="最短执行时间"></el-table-column>
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
            <el-tab-pane label="活动实例执行时效统计分析图表">
              <div id="activity1" class="chart" :style="{ 'display': showMonth?'none':'unset' }"></div>
              <div id="activity2" class="chart" :style="{ 'display': showMonth?'unset':'none' }"></div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-col>
    </el-row>
    <el-dialog title="流程名称" :visible.sync="dialogVisible" width="400px">

    </el-dialog>
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'activityTimeStatistical',
  components: {},
  data() {
    return {
      selectWfNameParam: {
        width: '100%',
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        pagination: true, // 非必填 是否分页
        filterable: true,
        remote: true, // 非必填 是否通过远程搜索
        optionWidth: 600, // 非必填 宽度默认400
        header: ['流程键值', '流程名称'], // 表格的头部
        body: ['modelKey', 'modelName'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'modelName', // 下拉框显示内容，根据需要修改字段名称
        value: 'modelKey', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'modelName', // 查询条件的别名
        api: this.$api.processList, // 接口
        paramMode: 'params',
        param: {
          tenantId: this.$store.state.user.userInfo.tenantId
        }
      },
      dialogVisible: false,
      wfName: '',
      query: {
        wfValue: '',
        startYear: '',
        endYear: ''
      },
      qtype: '',// // 2按流程活动切片查询 3按月切片查询（一次只查一个流程版本的一个活动）
      tableData: [],
      originTableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      barChartOption: {},
      lineChartOption: {},
      activityTimeChart: null,
      activityMonthChart: null,
      showMonth: false,
      modelKey: '',
      modelKeyNm: '',
      modelVersion: '',
      modelVersionNm: '',
      actId: ''
    }
  },
  mounted() {
    this.initBarChart()
    this.initLineChart()
  },
  methods: {
    selectWfName(data, selects) {
      this.modelKey = data
      this.modelKeyNm = selects[0].modelName
      this.wfName = selects[0].modelName + '各版本的环节'
    },
    search() {
      if (this.query.wfValue == '') {
        this.$tsVue.warn('请选择流程名称')
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
      this.queryExecution()
    },
    reset() {
      this.query = {
        wfValue: '',
        startYear: '',
        endYear: ''
      }
      this.qtype = ''
    },
    queryExecution() {
      //流程实例各版本执行时间统计
      this.qtype = ''
      let param = {
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        qtype: 2,
        entitys: [
          { modelKey: this.modelKey }
        ]
      }
      this.$request({
        url: this.$api.executionTimeStatistics, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        this.pageSize = 10
        this.originTableData = this.setColumnList(response.data, 10)
        this.total = response.data.length
        this.tableData = this.originTableData[0]
        //设置图表数据
        this.updateBarChart()
      })
    },
    queryActivity() {
      //查询某流程活动节点list
      let param = {
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        qtype: this.qtype,
        modelKey: this.modelKey,
        modelVersion: this.modelVersion,
        actId: this.actId // 3按月切片查询时必填
      }
      this.$request({
        url: this.$api.activityTimeStatistics, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        console.log(response)
        this.total = response.data.length
        if (this.qtype === 3) {
          this.pageSize = 12
          this.originTableData = this.setColumnList(response.data, 12)
          this.tableData = this.originTableData[0]
          this.updateLineChart()
        } else {
          this.pageSize = 10
          this.originTableData = this.setColumnList(response.data, 10)
          this.tableData = this.originTableData[0]
          this.updateBarChart()
        }
      })
    },
    queryNext(row) {
      // 查询下一级
      if (this.qtype === '') {
        this.wfName = row.name + '环节'
        this.modelVersionNm = row.name
        this.modelVersion = row.modelVersion
        this.qtype =  2
        this.queryActivity()
        return
      }

      if (this.qtype === 2) {
        this.wfName = this.modelVersionNm + row.actName
        this.showMonth = true
        this.actId = row.actId
        this.qtype =  3
        this.queryActivity()
      }
    },
    currentChange(number) {
      //切换页数
      this.pageNum = number
      if (this.qtype === '' || this.qtype === 2) {
        this.tableData = this.originTableData[this.pageNum - 1]
        this.updateBarChart()
      }

      if (this.qtype === 3) {
        this.tableData = this.originTableData[this.pageNum - 1]
        this.updateLineChart()
      }
    },
    backLast() {
      //回退到上一级
      this.showMonth = false
      if(this.qtype === 3){
        this.qtype = 2
        this.wfName = this.modelVersionNm + '环节'
        this.queryActivity()
        return
      }

      if(this.qtype === 2){
        this.qtype = ''
        this.wfName = this.modelKeyNm + '各版本的环节'
        this.queryExecution()
      }
    },
    updateLineChart() {
      //折线图数据更新
      this.lineChartOption.series[0].data = []
      this.lineChartOption.series[1].data = []
      this.lineChartOption.series[2].data = []
      this.tableData.forEach((item) => {
        this.lineChartOption.series[0].data.push(item.average)
        this.lineChartOption.series[1].data.push(item.maximum)
        this.lineChartOption.series[2].data.push(item.minimum)
      })
      this.activityMonthChart.setOption(this.lineChartOption)
    },
    updateBarChart() {
      //柱状图数据更新
      this.barChartOption.xAxis[0].data = []
      this.barChartOption.series[0].data = []
      this.barChartOption.series[1].data = []
      this.barChartOption.series[2].data = []
      this.tableData.forEach((item) => {
        this.barChartOption.xAxis[0].data.push(item.name)
        this.barChartOption.series[0].data.push(item.average)
        this.barChartOption.series[1].data.push(item.maximum)
        this.barChartOption.series[2].data.push(item.minimum)
      })
      this.activityTimeChart.setOption(this.barChartOption)
    },
    initBarChart() {
      //流程执行时间统计图
      this.activityTimeChart = echarts.init(document.getElementById('activity1'))

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
                '平均执行时间：' +
                params[0].data +
                '分' +
                '</br>' +
                '最长执行时间：' +
                params[1].data +
                '分' +
                '</br>' +
                '最短执行时间：' +
                params[2].data +
                '分' +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['平均执行时间', '最长执行时间', '最短执行时间']
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
            name: '(分)',
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
            name: '平均执行时间',
            type: 'bar',
            data: []
          },
          {
            name: '最长执行时间',
            type: 'bar',
            data: []
          },
          {
            name: '最短执行时间',
            type: 'bar',
            data: []
          }
        ]
      }
      this.activityTimeChart.setOption(this.barChartOption)
      window.addEventListener('resize', function() {
        if (this.activityTimeChart) {
          this.activityTimeChart.resize()
        }
      })
    },
    initLineChart() {
      //单个流程执行时间折线图
      this.activityMonthChart = echarts.init(document.getElementById('activity2'))

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
                '平均执行时间：' +
                params[0].data +
                '分' +
                '</br>' +
                '最长执行时间：' +
                params[1].data +
                '分' +
                '</br>' +
                '最短执行时间：' +
                params[2].data +
                '分' +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['平均执行时间', '最长执行时间', '最短执行时间']
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
            name: '(分)',
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
            name: '平均执行时间',
            type: 'line',
            data: []
          },
          {
            name: '最长执行时间',
            type: 'line',
            data: []
          },
          {
            name: '最短执行时间',
            type: 'line',
            data: []
          }
        ]
      }
      this.activityMonthChart.setOption(this.lineChartOption)
      window.addEventListener('resize', function() {
        if (this.activityMonthChart) {
          this.activityMonthChart.resize()
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
