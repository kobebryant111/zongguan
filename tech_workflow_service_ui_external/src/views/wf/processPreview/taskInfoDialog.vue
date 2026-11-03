<!--
 * @Author: zhuofushen
 * @Date: 2020-03-13 15:45:43
 * @LastEditTime: 2020-04-09 21:01:29
 * @LastEditors: OBKoro1
 * @Description: 传递给弹框的表单组件
 * 注意点：
 * 1.el-form 标签中一定要设置ref名称，model数据，以及rules校验规则
 * 2.el-form-item 标签中必须定义prop属性来匹配校验的字段
 * 3.如果要自动过滤用户输入的首尾空白字符，可以给v-model添加trim修饰符：<input v-model.trim="msg">
 * 4.如果想自动将用户的输入值转为数值类型，可以给v-model添加number修饰符：<input v-model.number="age" type="number">
 -->
<template>
    <div>
        <el-form ref="formRef" :model="model"  label-width="100px" >
            <div class="col_td">
                <el-form-item label="节点Id：" >
                    <el-input   size="mini" v-model.trim="model.activityId" :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="流程定义Id：" >
                    <el-input  size="mini" v-model.trim="model.processDefineId" :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="任务Id：">
                    <el-input   size="mini" v-model.trim="model.taskId"  :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="任务名称：">
                    <el-input  size="mini" v-model.trim="model.taskName"  :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="完成状态：" >
                    <el-input   size="mini" v-model.trim="model.finish"  :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="处理人：" >
                    <el-input  size="mini" v-model.trim="model.assignee"></el-input>
                </el-form-item>
            </div>
            <div class="col_td2"  >
                <el-form-item label="处理意见：">
                    <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 4}" style="width: 96%" placeholder="多个别名用逗号，分割" size="mini" v-model.trim="model.comment"></el-input>
                </el-form-item>
            </div>
<!--            <div  v-if="model.status === '未阅'" class="bottomDiv" >-->
<!--                <p style="font-size: 20px;font-weight: bolder;margin-left: 20px">岗位池用户</p>-->
<!--                <div style="width:100%;height: 250px;">-->
<!--                    <div style="width: 25%;margin-top: 20px; margin-left: 20px; background-color: #F5F7FA ;height: 220px;float:left;overflow-y:auto;">-->
<!--                        <el-table-->
<!--                                size="mini"-->
<!--                                ref="multipleTable"-->
<!--                                :data="tableData"-->
<!--                                tooltip-effect="dark"-->
<!--                                style="width: 100%"-->
<!--                                @selection-change="handleSelectionChange">-->
<!--                            <el-table-column-->
<!--                                    type="selection"-->
<!--                                    width="55">-->
<!--                            </el-table-column>-->
<!--                            <el-table-column-->
<!--                                    prop="userId"-->
<!--                                    label="用户名"-->
<!--                                    width="150">-->
<!--                            </el-table-column>-->
<!--                        </el-table>-->
<!--                    </div>-->
<!--                    <div style="width: 65%;margin-top: 20px;  margin-right: 20px ;height: 210px;float:right;">-->
<!--                        <div style="width: 100%;height: 30px;margin-left: 15px">-->
<!--                            <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="addUser">增加</el-button>-->
<!--                            <el-button  type="danger"  size="mini" icon="el-icon-delete" @click="deleteUser">删除</el-button>-->
<!--                        </div>-->
<!--                        <div style="width: 95%;height: 150px;margin: 10px auto 0;">-->
<!--                            <el-input type="textarea" :autosize="{ minRows: 9, maxRows: 10}"  v-model.trim="userText" style="width: 100%" placeholder="多个别名用英文输入法下的逗号 ','分割" size="mini"></el-input>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                </div>-->
<!--            </div>-->
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'AddFormDialog',
  data() {
    return {
      userText: '',
      model: {
        activityId: '',
        processDefineId: '',
        taskId: '',
        taskName: '',
        status: '',
        assignee: '',
        comment: '',
        finish: ''
      },
      tableData: [],
      selectData: []
    }
  },
  created() {
    this.getInitData()
  },
  methods: {
    getInitData: function() {
      // 通过公共方法获取初始化数据
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      // 获取列表的数据
      this.$request({
        url: this.$api.candidateUserList, // [必填] 请求地址
        data: { 'taskId': initData.taskId }, // [选填] 请求参数
        type: 'get', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.data.candidateUserList.list !== '' && response.data.candidateUserList.list !== null) {
          this.tableData = response.data.candidateUserList.list
        }
        // 未完成的任务的处理人不对。才需要修改
        if (initData.finish === '未完成') {
          this.model.assignee = response.data.assignee
        }
      })
    },
    handleSelectionChange(val) {
      this.selectData = val
    },
    addUser() {
      if (this.userText === '') {
        return
      }
      const userList = this.userText.split(',')
      userList.forEach((object) => {
        const node = {
          id: Math.floor((Math.random() * 10000) + 1).toString(),
          groupId: '',
          procDefId: '',
          procInstId: '',
          rev: '',
          taskId: '',
          type: '',
          userId: object
        }
        if (object !== '') {
          this.tableData.push(node)
        }
      })
    },
    deleteUser() {
      if (this.selectData.length === 0) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        // 全选那就直接清掉
        if (this.selectData.length === this.tableData.length) {
          this.tableData = []
        } else {
          for (let i = 0; i < this.selectData.length; i++) {
            for (let j = 0; j < this.tableData.length; j++) {
              if (this.selectData[i].id === this.tableData[j].id) {
                this.tableData.splice(j, 1)
              }
            }
          }
        }
      }
    }
  }
}
</script>
<style scoped>
    .col_td {
        width:48%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }
    .bottomDiv{
        width:100%;
        float:left;
        height:300px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 35px;
    }
    .col_td2{
        width:100%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }

</style>

