<!-- 流程版本统计分析 -->
<template>
  <div style="margin-left: 30px;margin-right: 30px">
    <div class="layout" style="margin-top: 20px">
      <el-form ref="searchForm" inline>
        <el-form-item label="业务目录:">
          <ts-multi-tree
              v-model="query.classifyValue"
              ref="mTree"
              :options="treeOptions"
              @node-click="handleNodeClick"></ts-multi-tree>
        </el-form-item>
        <el-form-item label="流程名称:">
          <el-input v-model="query.wfValue" size="mini" clearable></el-input>
          <!--          <ts-multi-select v-model="query.wfValue" :params="selectWfNameParam" @change="selectWfName"></ts-multi-select>-->
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
            <el-tab-pane label="流程版本统计分析">
              <div style="float: left;margin-bottom: 10px">版本统计分析结果</div>
              <el-table :data="tableData" style="width: 100%" size="mini" height="400">
                <el-table-column prop="modelName" label="流程显示(流程定义)名称">
                  <template slot-scope="scope">
                    <span>{{ scope.row.modelName + '(' + scope.row.modelKey + ')' }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="versionCnt" label="版本小计"></el-table-column>
                <el-table-column prop="proUpdateRate" label="变化率(天/个)"></el-table-column>
                <el-table-column prop="currentVersion" label="当前版本号"></el-table-column>
                <el-table-column prop="lastCreateTime" label="当前版本创建时间"></el-table-column>
                <el-table-column prop="proCreateTime" label="第一个版本创建时间"></el-table-column>
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
            <el-tab-pane label="流程版本统计分析图表">
              <div id="processVision" class="chart"></div>
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
  name: 'processVersion',
  components: {},
  data() {
    return {
      treeOptions: {
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId }
      },
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
      classifyId: '',
      classifyIdNm: '',
      query: {
        classifyValue: '',
        wfValue: ''
      },
      qtype: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      barChartOption: {},
      processVersionChart: null
    }
  },
  mounted() {
    this.initBarChart()
  },
  methods: {
    handleNodeClick(node) {
      // this.classifyIdNm = node.name
      // this.classifyId = node.id
      // this.selectWfNameParam.param = {
      //   classifyId: this.classifyId,
      //   tenantId: this.$store.state.user.userInfo.tenantId
      // }
    },
    selectWfName(data, selects) {
      this.wfName = selects[0].modelName
    },
    search() {
      this.pageNum = 1
      this.queryProcessVersion()
    },
    queryProcessVersion(entitys) {
      //查询流程版本统计
      let param = {
        tenantId: this.$store.state.user.userInfo.tenantId,
        modelNameLike: this.query.wfValue,
        classifyId: this.query.classifyValue,
        page: this.pageNum, // 分页参数
        pageSize: this.pageSize
      }
      this.$request({
        url: this.$api.processVersionStatistics, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post' // [选填] String 请求方式 get|post 默认post
      }).then((response) => {
        //设置图表数据
        this.total = response.data.grid.total
        this.tableData = response.data.grid.list
        this.updateBarChart()
      })
    },
    reset() {
      this.query = {
        classifyValue: '',
        wfValue: ''
      }
    },
    currentChange(number) {
      this.pageNum = number
      this.queryProcessVersion()
    },
    updateBarChart() {
      this.barChartOption.xAxis[0].data = []
      this.barChartOption.series[0].data = []
      this.tableData.forEach((item) => {
        this.barChartOption.xAxis[0].data.push(item.modelName)
        this.barChartOption.series[0].data.push(item.proUpdateRate)
      })
      this.processVersionChart.setOption(this.barChartOption)
    },
    initBarChart() {
      //流程规模统计图
      this.processVersionChart = echarts.init(document.getElementById('processVision'))

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
                '版本变化率：' +
                params[0].data +
                '</br>'
            return data
          }
        },
        legend: {
          orient: 'vertical',
          bottom: 'bottom',
          data: ['版本变化率']
        },
        xAxis: [
          {
            type: 'category',
            axisLabel: {
              show: true,
              interval: 0,
              fontSize: 9
              // rotate:40,
            },
            axisTick: { show: false },
            data: []
          }
        ],
        yAxis: [
          {
            name: '版本变化率\n(天/个)',
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
            name: '版本变化率',
            type: 'bar',
            data: []
          }
        ]
      }
      this.processVersionChart.setOption(this.barChartOption)
      window.addEventListener('resize', function() {
        if (this.processVersionChart) {
          this.processVersionChart.resize()
        }
      })
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
