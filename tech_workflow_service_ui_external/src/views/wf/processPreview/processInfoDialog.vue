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
                <el-form-item label="流程实例Id：" >
                    <el-input   size="mini" v-model.trim="model.processInstanceId" :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item  label="流程定义Id：" >
                    <el-input  size="mini" v-model.trim="model.processDefineId" :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="流程定义名称:">
                    <el-input   size="mini" v-model.trim="model.processDefineName"  :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="创建者：">
                    <el-input   size="mini" v-model.trim="model.userId"  :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="启动时间：">
                    <el-input  size="mini" v-model.trim="model.startTime"  :disabled="true"></el-input>
                </el-form-item>
            </div>
            <div class="col_td">
                <el-form-item label="结束时间：">
                    <el-input   size="mini" v-model.trim="model.endTime"  :disabled="true"></el-input>
                </el-form-item>
            </div>
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
        processInstanceId: '',
        processDefineId: '',
        processDefineName: '',
        startTime:'',
        endTime:'',
        userId:''

      },
      tableData: [],
      selectData: []
    }
  },
  watch:{
      model:{
            deep:true,
            handler:function (value) {
                console.log(value)
                let startTime=this.formatTimer(value.startTime)
                value.startTime=startTime
                
                return value
            }
        }
  },
  created() {
      console.log("进来了啊222")
      console.log(this.id)
    // this.getInitData()
  },
  mounted(){
      this.getInitData()
  },
  methods: {
      formatTimer(value) {
      let date = new Date(value);
      let y = date.getFullYear();
      let MM = date.getMonth() + 1;
      MM = MM < 10 ? "0" + MM : MM;
      let d = date.getDate();
      d = d < 10 ? "0" + d : d;
      let h = date.getHours();
      h = h < 10 ? "0" + h : h;
      let m = date.getMinutes();
      m = m < 10 ? "0" + m : m;
      let s = date.getSeconds();
      s = s < 10 ? "0" + s : s;
      return y + "-" + MM + "-" + d + " " + h + ":" + m+":"+s;
    },
    getInitData: function() {
      // 通过公共方法获取初始化数据
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      console.log("初始化数据")
      console.log(dialogObj)
    },
    // handleSelectionChange(val) {
    //   this.selectData = val
    // },
    // addUser() {
    //   if (this.userText === '') {
    //     return
    //   }
    //   const userList = this.userText.split(',')
    //   userList.forEach((object) => {
    //     const node = {
    //       id: Math.floor((Math.random() * 10000) + 1).toString(),
    //       groupId: '',
    //       procDefId: '',
    //       procInstId: '',
    //       rev: '',
    //       taskId: '',
    //       type: '',
    //       userId: object
    //     }
    //     if (object !== '') {
    //       this.tableData.push(node)
    //     }
    //   })
    // },
    // deleteUser() {
    //   if (this.selectData.length === 0) {
    //     this.$tsVue.error('请选择一条数据', () => {})
    //   } else {
    //     // 全选那就直接清掉
    //     if (this.selectData.length === this.tableData.length) {
    //       this.tableData = []
    //     } else {
    //       for (let i = 0; i < this.selectData.length; i++) {
    //         for (let j = 0; j < this.tableData.length; j++) {
    //           if (this.selectData[i].id === this.tableData[j].id) {
    //             this.tableData.splice(j, 1)
    //           }
    //         }
    //       }
    //     }
    //   }
    // }
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

