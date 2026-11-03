<template>
    <div>
        <div>
            <el-form ref="formRef" :model="model" label-width="70px">
                <el-form-item label="节 点：" prop="tenantId">
                    <ts-multi-select v-model="selectValue"  :params="selectParam"></ts-multi-select>
                </el-form-item>
                <el-form inline>
                <el-form-item label="登录名：">
                    <el-input  size="mini" v-model="query.loginName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                </el-form-item>
                    <el-form-item label="">
                        <el-input  size="mini" v-model="userId" placeholder = "可自定义输入"></el-input>
                    </el-form-item>
                </el-form>
            </el-form>
        </div>
        <div>
            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery" @row-click="handleRowClick">
                <el-table-column prop="userLoginName" label="登陆名" min-width="80" show-overflow-tooltip/>
                <el-table-column prop="useId" min-width="150"  label="用户编号"/>
                <el-table-column prop="userName" label="真实名"/>
                <el-table-column prop="sexCd" :formatter="conversionSex" label="性别"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
export default {
  name: 'WorkFlowInterveneDialog',
  data() {
    return {
      query: {},
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.userList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: {}, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      },
      DialogInitData:{},
      selectParam: { options: [], size: 'mini', width: '60%' },
      userId: '',
      model: {
        activityId: '',
        activityName: '',
        businessKey: '',
        executionId: '',
        isSuspended: '',
        processDefineId: '',
        processDefineKey: '',
        processDefineName: '',
        processDefineVersion: '',
        processInstanceId: '',
        processVariables: {},
        tenantId: '',
        tsRowIndex: 0
      },
      selectValue: ''
    }
  },
  created(){
    this.getInitData()
  },
  methods: {
    //获取上个父组件传送的data数据
    getInitData(){
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      this.DialogInitData = initData
    },
    handleRowClick(data) {
      console.log('c ascas', data)
      this.userId = data.row.userLoginName
    },
    conversionSex(row, column) {
      if (row[column.property] === '1') {
        return '男'
      } else {
        return '女'
      }
    },
    reset() {
      this.$refs['list'].resetData()
    },
    search() {
      this.$refs['list'].serchHandel()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    }
  },
  watch: {
    // 监听数据 进行下拉框的输入框数据
    'model.activityId': function(newValue) {
      const data = {
        processDefineId: this.DialogInitData.processDefineId
      }
      this.$request({
        url: this.$api.getWfPointData, // [必填] 请求地址
        data: data, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认datalabel
      }).then((response) => {
        if (response.data.WfPoint.length !== 0) {
          const pointArr = response.data.WfPoint
          for (let i = 0; i < pointArr.length; i++) {
            // 不是会签的;不是当前节点的才显示
            if (!pointArr[i].taskDefinition && pointArr[i].taskKey !== this.model.activityId) {
              const object = {
                text: pointArr[i].taskName + ',' + pointArr[i].taskKey,
                value: pointArr[i].taskKey
              }
              this.selectParam.options.push(object)
            }
          }
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
