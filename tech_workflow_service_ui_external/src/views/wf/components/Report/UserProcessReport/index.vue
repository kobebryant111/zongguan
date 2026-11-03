<template>
    <div style="margin-top: 20px;height: 520px;padding-top: 30px;padding-right: 30px" >
        <ts-chart :params="params" width="100%" height="450px" v-if="show"  ></ts-chart>
    </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'UserProcessReport',
  computed: {
    ...mapState({
      'userInfo': state => state.user.userInfo
    })
  },
  data() {
    return {
      show: false,
      params: {
        // 自己配置option echart
        option: {
          title: {
            text: '个人每月处理任务数',
            subtext: '单位(条/月)',
            padding: [5, 5, 5, 50]// padding: [5,5,5,5],标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距
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
            data: ['处理数']
          },
          xAxis: [
            {
              type: 'category',
              data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
              axisPointer: {
                type: 'shadow'
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              axisLabel: {
                formatter: '{value} 条'
              }
            }
          ],
          series: [
            {
              name: '处理数',
              type: 'line',
              data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            }
          ]
        }
      }
    }
  },
  methods: {
    initData() {
      this.$request({
        url: this.$api.getUserCompleteProcessReport, // [必填] 请求地址
        data: { 'tenantId': this.$store.state.user.userInfo.tenantId, 'userId': this.userInfo.loginName, 'year': new Date().getFullYear() }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          const list = response.data
          this.params.option.xAxis[0].data = []
          this.params.option.series[0].data = []
          for (let i = 0; i < list.length; i++) {
            this.params.option.xAxis[0].data.push(list[i].mont)
            this.params.option.series[0].data.push(list[i].coun)
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
