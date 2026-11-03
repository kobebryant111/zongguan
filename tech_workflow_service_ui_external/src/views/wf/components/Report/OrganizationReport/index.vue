<template>
    <div style="margin:20px 30px  35px;">
        <div style="margin-top: 20px;margin-left: 5px; padding-top:20px;" >
            <div class="inDiv">
                <p>开始日期 - 结束日期：</p>
            </div>
            <div class="inDiv">
                <ts-multi-date-picker v-model="time" :params="params"/>
            </div>
            <div class="inDiv" style="margin-top: 30px">
                <el-button icon="el-icon-search" size="mini" type="primary" @click="updateView" >更新报表</el-button>
                <el-button  type="primary" size="mini" icon="el-icon-s-promotion" @click="switchView" :style = redButton>红榜</el-button>
                <el-button  type="primary" size="mini" icon="el-icon-s-promotion" @click="switchView" :style = blueButton>蓝榜</el-button>
            </div>
        </div>
        <div style="margin-top: 30px">
            <div id="clearModulesChart"></div>
            <div id="OrganizationModulesChart" style="height: 400px;"></div>
        </div>
    </div>
</template>

<script>
import echarts from 'echarts'
export default {
  name: 'OrganizationReport',
  data() {
    return {
      time: '',
      redButton: 'color: red',
      blueButton: '',
      orderType: 'desc',
      orderBy: 'avgTime desc',
      topName: '红',
      params: {
        size: 'mini',
        type: 'daterange',
        placeholder: '选择日期时间',
        format: 'yyyy/MM/dd',
        valueFormat: 'yyyy-MM-dd',
        rangeSeparator: '-',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期'
      }
    }
  },
  mounted() {
    const param = { 'orderType': this.orderType, 'orderBy': this.orderBy, 'type': 'organization' }
    this.param = param
    this.getXYEchartInfo(param)
  },
  methods: {
    updateView() {
      let param
      if (this.time != null) {
        param = { 'orderType': this.orderType, 'orderBy': this.orderBy, 'beginDate': this.time[0], 'endDate': this.time[1], 'type': 'organization' }
      } else {
        param = { 'orderType': this.orderType, 'orderBy': this.orderBy, 'type': 'organization' }
      }
      this.getXYEchartInfo(param)
    },
    switchView() {
      if (this.orderType === 'desc') {
        this.orderType = 'asc'
        this.orderBy = 'avgTime asc'
        this.topName = '蓝'
        this.blueButton = 'color: blue'
        this.redButton = ''
        const param = { 'orderType': this.orderType, 'orderBy': this.orderBy, 'type': 'organization' }
        this.getXYEchartInfo(param)
      } else {
        this.orderType = 'desc'
        this.orderBy = 'avgTime desc'
        this.topName = '红'
        this.redButton = 'color: red'
        this.blueButton = ''
        const param = { 'orderType': this.orderType, 'orderBy': this.orderBy, 'type': 'organization' }
        this.getXYEchartInfo(param)
      }
    },
    // 折线图、柱状图展示数据获取
    getXYEchartInfo(param) {
      this.$request({
        url: this.$api.wfProEffInfo, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          const y1Value = response.data.y1Value
          const y2Value = response.data.y2Value
          const xValue = response.data.xValue
          const top = response.data.top
          // 绘制图
          this.showXYEchartViews(y1Value, y2Value, this.topName, top, xValue)
        } else {
          this.$tsVue.error(response.description)
        }
      }).catch(() => {
      })
    },
    // 应用柱状、折现echart图表展示效果
    showXYEchartViews(y1Value, y2Value, topName, top, xValue) {
      // 再次触发刷新原先图片div，清除原先div内容
      const myChart = echarts.init(document.getElementById('OrganizationModulesChart'))
      let option = null
      option = {
        dataZoom: {
          realtime: true, // 拖动滚动条时是否动态的更新图表数据
          height: 20, // 滚动条高度
          start: 0, // 滚动条开始位置（共100等份）
          end: 60// 结束位置（共100等份）
        },
        title: {
          text: '机构处理效率' + topName + '榜TOP' + top,
          subtext: '单位(分钟/人数)'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['平均时间', '处理流程数量']
        },
        color: ['#65A5EA', '#66D586', '#FAA75E', '#D49CEF', '#8EBBEC'], // 颜色
        toolbox: {
          show: true,
          feature: {
            dataView: { show: false, readOnly: false },
            magicType: { show: false, type: ['line', 'bar'] },
            restore: { show: false },
            saveAsImage: { show: false }
          }
        },
        calculable: true,
        xAxis: [
          {
            type: 'category',
            data: xValue,
            axisLabel: {
              interval: 0
            },
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '平均时间',
            type: 'bar',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  position: 'top',
                  formatter: '{c}'
                }
              }
            },
            data: y1Value,
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: '平均值' }
              ]
            }
          },
          {
            name: '处理流程数量',
            type: 'bar',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  position: 'top',
                  formatter: '{c}'
                }
              }
            },
            data: y2Value,
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            },
            markLine: {
              data: [
                { type: 'average', name: '平均值' }
              ]
            }
          }
        ]
      }
      if (option && typeof option === 'object') {
        myChart.setOption(option, true)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
    .inDiv {
        display: inline-block;
        margin-right: 10px;
    }
</style>
