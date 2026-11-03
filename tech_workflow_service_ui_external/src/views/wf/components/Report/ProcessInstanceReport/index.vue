<template>
    <div style="margin-top: 20px;height: 500px;padding-top: 30px;padding-right: 30px" >
        <ts-chart :params="params" width="100%" height="450px" v-if="show"  ></ts-chart>
    </div>
</template>

<script>
export default {
  name: 'ProcessInstanceReport',
  data() {
    return {
      show: false,
      params: {
        // 自己配置option echart
        option: {
          title: {
            text: '业务流程数量明细',
            subtext: '单位(条)',
            padding: [5, 5, 5, 100]// padding: [5,5,5,5],标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距
          },
          color: ['#65A5EA', '#66D586', '#FAA75E', '#D49CEF', '#8EBBEC'], // 颜色
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          toolbox: {
            feature: {
              dataView: { show: false, readOnly: false },
              magicType: { show: false, type: ['line', 'bar'] },
              restore: { show: false },
              saveAsImage: { show: false }
            }
          },
          dataZoom: {
            realtime: true, // 拖动滚动条时是否动态的更新图表数据
            height: 20, // 滚动条高度
            start: 0, // 滚动条开始位置（共100等份）
            end: 60// 结束位置（共100等份）
          },
          legend: {
            data: ['运行流程', '结束流程']
          },
          xAxis: [
            {
              type: 'category',
              data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
              axisPointer: {
                type: 'shadow'
              },
              axisLabel: {
                interval: 0,
                rotate: 35,
                formatter: function(value) { // 只显示五个字 其余省略号
                  return value.length > 5 ? value.substring(0, 7) + '...' : value
                }
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '流程数',
              axisLabel: {
                formatter: '{value} 条'
              }
            }
          ],
          series: [
            {
              name: '运行流程',
              type: 'bar',
              data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            },
            {
              name: '结束流程',
              type: 'bar',
              data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
            }
          ]
        }
      }
    }
  },
  methods: {
    initData() {
      this.$request({
        url: this.$api.getRunProcessNumberReport, // [必填] 请求地址
        data: { 'tenantId': this.$store.state.user.userInfo.tenantId }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          const list = response.data
          this.params.option.xAxis[0].data = []
          this.params.option.series[0].data = []
          this.params.option.series[1].data = []
          for (let i = 0; i < list.length; i++) {
            this.params.option.xAxis[0].data.push(list[i].processName)
            this.params.option.series[0].data.push(list[i].runProcessNumber)
            this.params.option.series[1].data.push(list[i].endProcessNumber)
          }
        }
        this.show = true
      })
    }
  },
  created() {
    this.initData()
  }
}
</script>

<style scoped>

</style>
