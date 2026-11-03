/*
@Author: chenhuocan
@Date: 2020-03-12 20:48:09
@LastEditors:
@LastEditTime:
@Description: 首页
*/
<template>
    <div class="home-contain">
        <div class="hqcposition"><p class="ionposion"></p>当前位置：首页</div>
        <div class="hqcmenu">
            <div class="hqcmenu-area hqcmenu-mgright clrblue" @click="goCreateProcess">
                <div class="hqcmenu-pic"><img src="@/assets/images/home/icon_quick_menu01.png"></div>
                <div class="hqcmenu-text">
                    <div class="hqcmenu-name">流程创建管理</div>
                    <div class="hqcmenu-subname">快捷菜单入口</div>
                </div>
            </div>
            <div class="hqcmenu-area hqcmenu-mgright clrgreen" @click="goClassifyList">
                <div class="hqcmenu-pic"><img src="@/assets/images/home/icon_quick_menu02.png"></div>
                <div class="hqcmenu-text">
                    <div class="hqcmenu-name">流程分类管理</div>
                    <div class="hqcmenu-subname">快捷菜单入口</div>
                </div>
            </div>
            <div class="hqcmenu-area hqcmenu-mgright clryellow" @click="goTaskExpandPlan">
                <div class="hqcmenu-pic"><img src="@/assets/images/home/icon_quick_menu04.png"></div>
                <div class="hqcmenu-text">
                    <div class="hqcmenu-name">待办已办扩展方案</div>
                    <div class="hqcmenu-subname">快捷菜单入口</div>
                </div>
            </div>
            <div class="hqcmenu-area clrviolet" @click="goRunningList">
                <div class="hqcmenu-pic"><img src="@/assets/images/home/icon_quick_menu03.png"></div>
                <div class="hqcmenu-text">
                    <div class="hqcmenu-name">流程跟踪管理</div>
                    <div class="hqcmenu-subname">快捷菜单入口</div>
                </div>
            </div>
        </div><!--hqcmenu-->
        <div class="bottom-bottom">
            <!--待办事项-->
            <div class="hml-left hml-area">
                <div class="title" style="margin-bottom: 10px">
                    <div class="font-14 font-bold">待办事项</div>
                    <a class="font-14 color-blue" @click="goPendTask">更多</a>
                </div>
                <div style="padding: 0 10px 15px 10px">
                    <ts-multi-table ref="list"  :options="options"  >
                        <el-table-column prop="taskId" label="任务ID" min-width="40" show-overflow-tooltip/>
                        <el-table-column prop="activityName" label="任务名称"/>
                        <el-table-column prop="processInstanceId" label="流程实例ID" min-width="50"/>
                        <el-table-column prop="processDefineName" label="流程名称"/>
                        <el-table-column prop="businessKey" label="业务主键"/>
                        <el-table-column prop="processStartTime" label="任务开始时间"/>
                        <el-table-column prop="startUserId" label="发起人"  min-width="60" />
                    </ts-multi-table>
                </div>
            </div>
            <!--统计图表-->
            <div class="hml-right hml-area">
                <div class="title">
                    <div class="font-14 font-bold">统计图表</div>
                    <a class="font-14 color-blue" @click="goReport">更多</a>
                </div>
                <process-number-report style="width:70%; margin:0 auto" ></process-number-report>
            </div>
        </div>

    </div>
</template>

<script>
import ProcessNumberReport from '../wf/components/Report/ProcessNumberReport/index';
export default {
  components: { ProcessNumberReport },
  data() {
    return {
      options: { // 必填
        type: '', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.pendTask,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        initTable: true,
        apiRequiredParams: {
          'taskPoolStatus': '1',
          'claimStatus':'1'
        }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      }
    }
  },
  methods: {
    goCreateProcess() {
      this.$tsVue.tansunPushAddTag(this, '/wf/create')
    },
    goClassifyList() {
      this.$tsVue.tansunPushAddTag(this, '/wf/classify/wfClassifyList')
    },
    goRunningList() {
      this.$tsVue.tansunPushAddTag(this, '/wf/instance/wfRunningList')
    },
    goTaskExpandPlan() {
      this.$tsVue.tansunPushAddTag(this, '/wf/wfTaskExpandPlan')
    },
    goPendTask() {
      this.$tsVue.tansunPushAddTag(this, '/wf/wfPendTask')
    },
    goReport() {
      this.$tsVue.tansunPushAddTag(this, '/wf/wfProEff/wfProEffOrganiza')
    }
  }
}
</script>

<style lang="scss" scoped>
    .home-contain {
        margin:30px;
    }
    .dashboard {
        &-container {
            padding: 30px;
            background: #f5f5f5;
            /*width: 80%;*/
            /*height: 100%;*/
            /*position: fixed;*/
        }
    }

    .top {
        &-top {
            min-height: 160px;
            display: flex;
            flex-direction: row;
        }
        &-left {
            background: #ffffff;
            margin-right: 20px;
            display: flex;
            flex-direction: row;
            flex: 3;
        }
        &-right {
            flex: 3;
            border-left: 1px dashed #f2f2f2;
        }
    }

    .bottom {
        &-bottom {
            min-height: 260px;
            margin-top: 30px;
            display: flex;
            flex-direction: row;
        }
        &-left {
            background: #ffffff;
            flex: 1;
            margin-right: 20px;
        }
        &-right {
            background: #ffffff;
            flex: 1
        }
        &-list-block {
            display: flex;
            flex-direction: column
        }
    }

    .list {
        &-list {
            display: flex;
            flex-direction: row;
            align-items: center
        }
        &-title {
            font-size: 14px;
            text-align: center;
            font-weight: bold;
            color: #218aed;
        }
        &-content {
            font-size: 13px;
            text-align: center;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
            color:#555;
        }
    }

    .title {
        display: flex;
        flex-direction: row;
        height: 40px;
        justify-content: space-between;
        align-items: center;
        padding-left: 10px;
        padding-right: 10px;
        color:#333;
    }

    .font-12 {
        font-size: 12px;
    }

    .font-14 {
        font-size: 14px;
    }

    .font-bold {
        font-weight: bold;
    }

    .color-blue {
        color: #218aed;
    }

    .header {
        text-align: center;
        height: 160px;
        background: #ffffff;
        padding: 20px;
        flex: 1;
    }

    a.hover1:hover {
        color: #218aed;
    }
</style>

