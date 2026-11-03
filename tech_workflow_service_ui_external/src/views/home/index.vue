<!--
 * @Author: amberself
 * @Date: 2020-04-07 09:47:49
 * @LastEditors: amberself
 * @LastEditTime: 2020-08-04 16:20:33
 * @Description:
-->
<template>
  <div class="app-container">
    <div class="ts-row">
      <div class="ts-col-10">
        <div class="ts-body mission-box">
          <div class="ts-body-header">
            <div class="header-left"><span class="header-icon icon-mission"></span>待办事项</div>
            <el-button type="primary" size="mini">更多 ></el-button>
          </div>
          <div class="ts-body-content">
            <ul class="topic-box">
              <li class="topic-item blue">
                <h6 class="topic-title">业务的交接活动项目业务的交接活动 项目业务的。</h6>
                <div class="topic-detail">
                  <span>2020/07/22</span>
                  <span><i class="num">1</i>件</span>
                </div>
              </li>
              <li class="topic-item yellow">
                <h6 class="topic-title">业务的交务的交接活动项目业务的。</h6>
                <div class="topic-detail">
                  <span>2020/07/22</span>
                  <span><i class="num">2</i>件</span>
                </div>
              </li>
              <li class="topic-item orange">
                <h6 class="topic-title">项目变更审批项目变更审批</h6>
                <div class="topic-detail">
                  <span>2020/07/22</span>
                  <span><i class="num">1</i>件</span>
                </div>
              </li>
              <li class="topic-item green">
                <h6 class="topic-title">业务的交接活动项目业务的交接活动务的。</h6>
                <div class="topic-detail">
                  <span>2020/07/22</span>
                  <span><i class="num">3</i>件</span>
                </div>
              </li>
            </ul>
            <ts-multi-table class="home-table" :options="options">
              <el-table-column prop="date" label="日期"></el-table-column>
              <el-table-column prop="title" label="标题" min-width="130"></el-table-column>
              <el-table-column prop="content" label="内容" min-width="250"></el-table-column>
              <el-table-column label="操作"><el-button type="primary" size="mini">查看</el-button></el-table-column>
            </ts-multi-table>
          </div>
        </div>
      </div>
      <div class="ts-col-fixed">
        <div class="ts-body bg-grey">
          <div class="ts-row-space">
            <div class="ts-col-10">
              <div class="square-box">
                <span class="icon-box yellow"><i class="iconfont iconyongxinshenqing"></i></span>
                <span>用信申请</span>
              </div>
            </div>
            <div class="ts-col-10">
              <div class="square-box">
                <span class="icon-box red"><i class="iconfont iconedushenqing"></i></span>
                <span>额度申请</span>
              </div>
            </div>
            <div class="ts-col-10">
              <div class="square-box">
                <span class="icon-box green"><i class="iconfont icondaicuishou"></i></span>
                <span>待催收</span>
              </div>
            </div>
          </div>
          <div class="ts-row-space">
            <div class="ts-col-10">
              <div class="square-box">
                <span class="icon-box blue"><i class="iconfont iconchaxunjindu"></i></span>
                <span>进度查询</span>
              </div>
            </div>
            <div class="ts-col-10">
              <div class="square-box">
                <span class="icon-box orange"><i class="iconfont iconyujing"></i></span>
                <span>预警</span>
              </div>
            </div>
            <div class="ts-col-10">
              <div class="square-box">
                <span class="icon-box icon-more">···</span>
              </div>
            </div>
          </div>
          <div class="tip-box">
            <div class="ts-body-header">
              <div class="header-left"><span class="header-icon icon-tip"></span>事件提醒</div>
              <el-button type="primary" size="mini">更多 ></el-button>
            </div>
            <div class="ts-body-content">
              <ul class="tip-box-list">
                <li class="tip-box-item" v-for="(item,index) in tipMockData" :key="index">
                  <span :class="['tip-icon', getClass(item.level)]">{{getLevelText(item.level)}}</span>
                  <span class="tip-content">{{item.content}}</span>
                </li>
                <!-- <li>
                  <span class="tip-icon">23</span>
                  <span>交通银行:高管辞任公告 交通银行 : 高管辞任公告</span>
                </li> -->
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="ts-row">
      <div class="ts-col-50">
        <div class="ts-body">
          <div class="ts-body-header"><div class="header-left"><span class="header-icon icon-warn"></span>预警信息</div></div>
          <div class="ts-body-content">
            <div id="chart" class="chart-box"></div>
          </div>
        </div>
      </div>
      <div class="ts-col-50">
        <div class="ts-body">
          <div class="ts-body-header"><div class="header-left"><span class="header-icon  icon-percent"></span>不良信贷分类占比</div></div>
          <div class="ts-body-content">
            <div id="chart2" class="chart-box"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import echarts from 'echarts'

export default {
  name: 'Dashboard',
  data() {
    return {
      options: {
        stripe: false,
        isNeedPagination: false, // 选填 是否需要分页器，默认是true
        pageNum: 0, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        layout: 'total, prev, pager, next', // 选填 分页器轮廓
        tbMockData: [
          {
            date: '2020-08-02',
            title: '环球网',
            content: '教育部公开8起违反教师职业准则典型问题 | 嗨！七点出发'
          },
          {
            date: '2020-08-02',
            title: '每日漫画故事',
            content: '土耳其东部反恐行动进入新阶段 超千名安全人员参与行动'
          },
          {
            date: '2020-08-02',
            title: '业务的名称',
            content: '讨论任务清单类 APP 相关话题，推荐你使用过值得推荐的待办事项'
          },
          {
            date: '2020-08-01',
            title: '央视网新闻',
            content: '印度首批5架阵风战机从法国飞往本土，未来或出现在中印边境'
          },
          {
            date: '2020-08-01',
            title: '新华网客户端',
            content: '讨论任务清单类 APP 相关话题，推荐你使用过值得推荐的待办事项'
          },
          {
            date: '2020-07-31',
            title: '环球时报新媒体',
            content: '澳洲外长今天抵美访问 莫里森表态9月会亲自再去'
          },
          {
            date: '2020-07-31',
            title: '新闻传播中心融媒体',
            content: '澳洲外长今天抵美访问 莫里森表态9月会亲自再去'
          },
          {
            date: '2020-07-31',
            title: '业务的名称',
            content: '铜仁夯实大数据基础设施建设'
          },
          {
            date: '2020-07-30',
            title: '业务的名称',
            content: '澳洲外长今天抵美访问 莫里森表态9月会亲自再去'
          },
          {
            date: '2020-07-30',
            title: '中国青年网',
            content: '被控受贿600余万元，安徽中医药大学原校长王键受审'
          }
        ]
      },
      tipMockData: [
        {
          level: 1,
          content: '交通银行:高管辞任公告 交通银行 : 高管辞任公告 ',
          date: '07/22'
        },
        {
          level: 1,
          content: '中国建设银行临沂分行关于清理企业银行账户的公告',
          date: '07/20'
        },
        {
          level: 2,
          content: '成都银行股份有限公司关于董事任职资格',
          date: '07/19'
        },
        {
          level: 3,
          content: '启明星辰信息技术集团股份有限公司关于',
          date: '07/19'
        },
        {
          level: 3,
          content: '中国银行保险监督管理委员会北京监管局 关于银行业',
          date: '07/18'
        },
        {
          level: 1,
          content: '央行公告|人民银行招80人!学历最低高中起报!年薪12万+!',
          date: '07/16'
        }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  mounted() {
    this.initPieChart()
    this.initChart()
  },
  methods: {
    initPieChart() {
      const myChart = echarts.init(document.getElementById('chart'))
      const option = {
        color: ['#3396FE', '#FAAD14', '#E22635', '#FAD714', '#16D556'], // 环形图每块的颜色
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          right: '10%',
          top: '20%',
          data: ['融资到期预警', '合同到期预警', '池模式融资预警', '租赁到期预警', '其他预警']
        },
        series: [
          {
            name: '预警信息',
            type: 'pie',
            radius: '60%',
            center: ['36%', '40%'],
            data: [
              { value: 160, name: '合同到期预警' },
              { value: 104, name: '池模式融资预警' },
              { value: 185, name: '租赁到期预警' },
              { value: 348, name: '其他预警' },
              { value: 505, name: '融资到期预警' }

            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      myChart.setOption(option)

      // 建议加上以下这一行代码，不加的效果图如下（当浏览器窗口缩小的时候）。超过了div的界限（红色边框）
      window.addEventListener('resize', function() {
        myChart.resize()
      })
    },
    initChart() {
      const chart2 = echarts.init(document.getElementById('chart2'))
      const option = {
        color: ['#3396FE', '#FAAD14', '#E22635', '#FAD714', '#16D556'], // 环形图每块的颜色
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        // legend: {
        //   orient: 'vertical',
        //   left: 10,
        //   data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        // },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: ['60%', '70%'],
            avoidLabelOverlap: true,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '30',
                fontWeight: 'bold'
              }
            },
            data: [
              { value: 20, name: '次级' },
              { value: 40, name: '可疑' },
              { value: 10, name: '损失' },
              { value: 10, name: '损失2' },
              { value: 10, name: '损失3' }
            ]
          }
        ]
      }
      chart2.setOption(option)

      // 建议加上以下这一行代码，不加的效果图如下（当浏览器窗口缩小的时候）。超过了div的界限（红色边框）
      window.addEventListener('resize', function() {
        chart2.resize()
      })
    },
    getLevelText(level) {
      switch (level) {
        case 1: return '严重'
        case 2: return '重大'
        case 3: return '一般'
        default: return '一般'
      }
    },
    getClass(level) {
      switch (level) {
        case 1: return 'level-1'
        case 2: return 'level-2'
        case 3: return 'level-3'
        default: return 'level-1'
      }
    }
  }
}
</script>

<style lang="scss">
  @import "~@/styles/mixins/mixin.scss";
  $home_yellow: #FAD714;
  $home_red: #E22635;
  $home_green: #16D556;
  $home_blue: #3396FE;
  $home_orange: #FAAD14;

  .ts-row {
    display: flex;
    justify-content: left;
    [class*=ts-col-]:nth-child(odd) {
      padding-right: 24px;
    }
  }
  [class*=ts-col-] {
    margin-bottom: 24px;
    .ts-body {
      height: 100%;
      padding: 0 24px;
      border-radius: 4px;
      background-color: #ffffff;
      &.mission-box {
        border-bottom: 1px solid #E6EEF7;
      }
      &-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 60px;
        line-height: 60px;
        font-size: 16px;
        font-weight: bold;
        color: #333333;
      }
      .header-left {
        display: flex;
        align-items: center;
      }
      .header-icon {
        margin-right: 8px;
        width: 22px;
        height: 22px;
        background-image: url('./../../assets/images/home/home.png');
        &.icon-mission {
          background-position: -2px -5px;
        }
        &.icon-tip {
          background-position: -32px -5px;
        }
        &.icon-warn {
          background-position: -63px -5px;
        }
        &.icon-percent {
          background-position: -96px -5px;
        }
      }
      &.bg-grey {
        display: flex;
        flex-direction: column;
        padding: 0;
        background-color: #f6f7f9;
      }
    }
    .square-box {
      // width: 100%;
      // min-width: 120px;
      // height: 0;
      // padding-bottom: 100%;
      width: 160px;
      height: 160px;
      padding: 30px 48px 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      font-size: 14px;
      .icon-box {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 66px;
        height: 66px;
        margin-bottom: 20px;
        border-radius:10px;
        background: #ffffff;
        &.icon-more {
          margin-top: 20px;
          font-size: 42px;
          color: #CCCCCC;
        }
        .iconfont {
          color: #ffffff;
          font-size: 30px;
        }
        &.yellow {
          background: $home_yellow;
        }
        &.red {
          background: $home_red;
        }
        &.green {
          background: $home_green;
        }
        &.blue {
          background: $home_blue;
        }
        &.orange {
          background: $home_orange;
        }
      }
    }
  }
  .ts-row-space {
    display: flex;
    justify-content: space-between;
    [class*=ts-col-]:nth-child(odd) {
      padding-right: 0;
    }
    [class*=ts-col-]:nth-child(2n) {
      margin: 0 24px 24px;
    }
    [class*=ts-col-] {
      box-shadow:0px 0px 4px 0px rgba(0,123,255,0.1);
      border-radius:4px;
      background: #ffffff;
    }
  }
  .ts-col- {
    &10 {
      flex: 1;
    }
    &30 {
      flex: 3;
    }
    &50 {
      flex: 5;
    }
    &70 {
      flex: 7;
    }
  }
  .chart-box {
    height: 300px;
  }
  .topic-box {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    .el-table__body {
      width: auto!important;
    }
    .topic-item {
      position: relative;
      flex: 1;
      padding: 12px 24px;
      border-radius:4px;
      box-shadow:0px 0px 6px 0px rgba(0,123,255,0.1);
      background: url('./../../assets/images/home/shade.png') no-repeat bottom right;
      &:before {
        content: '';
        position: absolute;
        top: 12px;
        left: 0;
        width: 6px;
        height: 16px;
        background: $home_blue;
      }
      &.blue:before {
        background: $home_blue;
      }
      &.yellow:before {
        background: $home_yellow;
      }
      &.orange:before {
        background: $home_orange;
      }
      &.green:before {
        background: $home_green;
      }
      &+.topic-item {
        margin-left: 24px;
      }
      .topic-title {
        margin-bottom: 40px;
        line-clamp: 2;
        -webkit-line-clamp:2;
        -webkit-box-orient:vertical;
        word-break:break-all;
        height: 44px;
        line-height: 22px;
        overflow: hidden;
      }
      .topic-detail {
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: #4D4D4D;
        .num {
          font-style: normal;
          font-size: 30px;
          color: #007BFF;
        }
      }
    }
  }
  .tip-box {
    flex: 1;
    background: #ffffff;
    padding: 0 24px;
    &-list {
      margin-top: 6px;
    }
    &-item {
      height: 42px;
      display: flex;
      justify-content: left;
      .tip-icon {
        margin-right: 10px;
        width:34px;
        height:16px;
        font-size: 12px;
        text-align: center;
        color: #ffffff;
        border-radius:2px 8px 2px 8px;
        &.level-1 {
          background: $home_red;
        }
        &.level-2 {
          background: $home_yellow;
        }
        &.level-3 {
          background: $home_blue;
        }
      }
      .tip-content {
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
  }
  .home-table {
    .el-table {
      color: #333333;
    }
    .el-table th, .el-table tr {
      height: 50px;
    }
    .el-table__header-wrapper {
      display: none;
    }
    .el-table--border, .el-table--group {
      border: none;
    }
    .el-table td, .el-table th.is-leaf {
      border-bottom: 1px solid #E6EEF7;
    }
    .el-table--border::after,
    .el-table--group::after,
    .el-table::before {
      background-color: #ffffff;
    }
  }
</style>

