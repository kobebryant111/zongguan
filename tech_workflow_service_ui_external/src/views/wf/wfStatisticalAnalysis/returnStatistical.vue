<!-- 退回统计分析 -->
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
            <el-tab-pane label="退回统计分析">
              <div>
                <div style="float: left">{{ wfName + '退回统计分析' }}</div>
                <el-button @click="backLast" style="float: right;margin-bottom: 10px" size="mini">返回上一级</el-button>
              </div>
              <el-table :data="tableData" style="width: 100%" size="mini" height="400">
                <el-table-column prop="name" label="流程名称">
                  <template slot-scope="scope">
                    <el-button @click="queryNext(scope.row)" size="mini" type="text">{{ scope.row.name }}</el-button>
                  </template>
                </el-table-column>
                <el-table-column prop="startCnt" label="发起次数"></el-table-column>
                <el-table-column label="退回统计" align="center">
                  <el-table-column prop="backCnt" label="退回次数" align="center"></el-table-column>
                  <el-table-column prop="backRate" label="退回率" align="center"></el-table-column>
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
            <el-tab-pane label="退回统计分析图表">
              <div id="return" class="chart"></div>
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
  name: 'returnStatistical',
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
      wfName: '',
      query: {
        wfValue: '',
        startYear: '',
        endYear: ''
      },
      modelKey: '',
      modelKeyNm: '',
      tableData: [],
      originTableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      barChartOption: {},
      returnChart: null
    }
  },
  mounted() {
    this.initBarChart()
  },
  methods: {
    selectWfName(data, selects) {
      this.modelKey = data
      this.modelKeyNm = selects[0].modelName
      this.wfName = '流程(' + selects[0].modelName + ')'
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
      this.qtype = 1
      this.queryReturnStatis()
    },
    reset() {
      this.query = {
        wfValue: '',
        startYear: '',
        endYear: ''
      }
    },
    queryReturnStatis() {
      //查询流程退回统计
      let param = {
        searchTimeBegin: this.query.startYear,
        searchTimeEnd: this.query.endYear,
        qtype: this.qtype,
        entity: {
          modelName: this.modelKeyNm,
          modelKey: this.modelKey,
          modelVersion: this.modelVersion
        }
      }
      this.$request({
        url: this.$api.returnStatistics, // [必填] 请求地址
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
    queryNext(row) {
      if (this.qtype === 1) {
        this.wfName = '流程版本(' + row.name + ')'
        this.modelVersion = row.modelVersion
        this.qtype = 2
        this.queryReturnStatis()
      }
    },
    backLast() {
      this.wfName = '流程(' + this.modelKeyNm + ')'
      if (this.qtype === 2) {
        this.qtype = 1
        this.queryReturnStatis()
      }
    },
    currentChange(number) {
      this.pageNum = number
      this.tableData = this.originTableData[this.pageNum - 1]
    },
    updateBarChart() {
      //柱状图数据更新
      this.barChartOption.xAxis[0].data = []
      this.barChartOption.series[0].data = []
      this.barChartOption.series[1].data = []
      this.tableData.forEach((item) => {
        this.barChartOption.xAxis[0].data.push(item.name)
        this.barChartOption.series[0].data.push(item.startCnt)
        this.barChartOption.series[1].data.push(item.backCnt)
      })
      this.returnChart.setOption(this.barChartOption)
    },
    initBarChart() {
      //流程执行时间统计图
      this.returnChart = echarts.init(document.getElementById('return'))

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
                '发起次数：' +
                params[0].data +
                '</br>' +
                '退回次数：' +
                params[1].data +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['发起次数', '退回次数']
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
            name: '发起次数',
            type: 'bar',
            data: []
          },
          {
            name: '退回次数',
            type: 'bar',
            data: []
          }
        ]
      }
      this.returnChart.setOption(this.barChartOption)
      window.addEventListener('resize', function() {
        if (this.returnChart) {
          this.returnChart.resize()
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
