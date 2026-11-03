<template>
    <div>
        <el-form  label-width="280px">
            <el-form-item  :key = key v-for="(value,key) in expandOptions"  :label="key + '：'" >
                <el-col :span="16">
                    <el-input  size="mini"  v-model="expandOptions[key]"></el-input>
                </el-col>
                <el-col :span="8">
<!--                <el-button  v-if=reg.test(value)  type="primary"  icon="el-icon-edit"  @click="updateVariable(key,value)" >修改</el-button>-->
                <el-button type="primary"  icon="el-icon-edit"  @click="updateVariable(key,value)" >修改</el-button>
                </el-col>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
  data() {
    return {
      processInstanceId: '',
      processDefinitionId: '',
      DialogInitData: '',
      expandOptions: {},
      reg: /^[a-z0-9A-Z._%]+$/
    }
  },
  created() {
    this.getInitData()
    this.getData()
  },
  methods: {
    getInitData() {
      // 通过公共方法获取初始化数据
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      this.DialogInitData = initData
      console.log('通过公共方法获取初始化数据:', this.DialogInitData)
      this.processInstanceId = this.DialogInitData.processInstanceId
    },
    getData() {
      const object = {
        processInstanceId: this.processInstanceId
      }
      this.$request({
        url: this.$api.findProcessVariable, // [必填] 请求地址
        data: object, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.$tsVue.success(response.description)
        this.expandOptions = response.data
        console.log('this.expandOptions', this.expandOptions)
      })
    },
    updateVariable(key,value) {

        const param ={
            processInstanceId: this.processInstanceId,
        }
        param[key] = value
        // param[key] = JSON.stringify(value)
        console.log(value)
        console.log(JSON.stringify(value))
        this.$request({
            url: this.$api.saveProcessVariable, // [必填] 请求地址
            data: param, // [选填] 请求参数l
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
            this.$tsVue.success(response.description)
        })
    }
  }
}
</script>

<style scoped>

</style>
