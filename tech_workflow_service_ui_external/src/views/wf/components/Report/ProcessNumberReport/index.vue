<template>
    <div style="height: 525px;padding-top: 50px;padding-left: 30px;" >
        <ts-chart :params="params" v-if = show ></ts-chart>
    </div>
</template>

<script>
export default {
  name: 'ProcessNumberReport',
  data() {
    return {
      show: false,
      params: {
        type: 'pie',
        series: [
          {
            name: '运行流程数',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
              { value: 335, name: '融资到期预警' },
              { value: 310, name: '合同到期预警' },
              { value: 234, name: '池模式融资预警' },
              { value: 135, name: '租赁到期预警' },
              { value: 548, name: '其他预警' }
            ],
            label: {
              normal: {
                formatter: '{b}: \n({d}%)',
                textStyle: {
                  fontWeight: 'normal',
                  fontSize: 13
                }
              }
            },
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ],
        title: {
          text: '业务流程数量',
          x: 'center',
          padding: [5, 5, 5, 220]// padding: [5,5,5,5],标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        toolbox: {},
        grid: {},
        color: ['#65A5EA', '#66D586', '#FAA75E', '#D49CEF', '#8EBBEC'], // 颜色
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['融资到期预警', '合同到期预警', '池模式融资预警', '租赁到期预警', '其他预警'],
          formatter: function(value) {
            return value.length > 5 ? value.substring(0, 5) + '..' : value
          }
        }
      }
    }
  },
  methods: {
    initData() {
      this.$request({
        url: this.$api.getProcessNumberReport, // [必填] 请求地址
        data: { 'tenantId': this.$store.state.user.userInfo.tenantId, number: '5' }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          const list = response.data
          this.params.series[0].data = []
          this.params.legend.data = []
          for (let i = 0; i < list.length; i++) {
            const node = {
              'value': list[i].processNumber,
              'name': list[i].processName
            }
            this.params.series[0].data.push(node)
            this.params.legend.data.push(list[i].processName)
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
