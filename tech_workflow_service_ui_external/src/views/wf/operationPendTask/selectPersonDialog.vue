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
    <div class="select_people" >
        <el-row>
            <el-col :span="5">
                <div>
                <div class="maintree_title">部门/机构</div>
                <ts-multi-trees
                        style="width: 260px;height: 510px;  border-right: 1px solid #ddd;"
                        :options="options"
                        ref="classifyTree"
                        >
                </ts-multi-trees>
            </div>
            </el-col>
            <el-col :span="14">
                <div >
                    <div class="select_title">可选人员</div>
                    <div style="margin-left: 20px">
                        <template>
                            <el-table :data="tableData" style="width: 100%" size="mini"  @selection-change="handleSelectionChange">>
                                <el-table-column type="selection" width="55"></el-table-column>
                                <el-table-column prop="userLoginName" label="登录名" ></el-table-column>
                                <el-table-column prop="userName" label="姓名" ></el-table-column>
                                <el-table-column prop="deptName" label="所属部门"></el-table-column>
                                <el-table-column prop="orgName" label="所属机构"></el-table-column>
                                <el-table-column prop="roleName" label="角色"></el-table-column>
                            </el-table>
                        </template>
                        <div style="margin-top: 15px;text-align:center;">
                            <el-pagination
                                    @current-change="currentChange"
                                    :current-page="pageNum"
                                    :page-size="10"
                                    layout="prev, pager, next, jumper"
                                    :total="total">
                            </el-pagination>
                        </div>
                    </div>
                </div>
            </el-col>
            <el-col :span="5">
                <div>
                    <div class="select_title">已选人员</div>
                    <div style="margin-left: 10px;overflow:auto;height: 510px;border-left: 1px solid  #ddd; ">
                        <ul>
                            <li v-for="(item) in selectUserList" :key="item.id">{{item.userLoginName}} ({{item.userName}})</li>
                        </ul>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script>

export default {
  props: {
    initData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      modelId: '',
      selectList: [],
      options: {
        checkbox: true, // 节点是否显示checkbox
        checkNode: true, // checkbox为true时使用 非必填，选中节点是否选中文本框
        checkedKeys: [], // 有初始值需要数据反显时，每条数据的key值，返回的数据需要唯一标识字段key
        onlyChildSelected: true, // 选填，是否只允许选择叶子节点 默认false
        apiName: this.$api.getDepartmentTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        callback: (data) => {}
      },
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      // 弹窗的初始化数据由页面打开弹窗时赋值给弹窗的
      dialogInitData: '',
      selectUserMap: {},
      selectUserList: []
    }
  },
  mounted() {
    this.initDialogData()
    this.getTableData()
  },
  methods: {
    handleSelectionChange(val) {
      console.log(val)
      this.selectUserMap[this.pageNum] = val
      let userArr = []
      for (const key in this.selectUserMap) {
        userArr = userArr.concat(this.selectUserMap[key])
      }
      this.selectUserList = userArr
    },
    currentChange(currentChange) {
      this.pageNum = currentChange
      this.getTableData()
    },
    initDialogData() {
      // 初始化表格数据截取pageSize 做分页
      const dialogObj = this.$tsVue.tansunDialogData(this)
      const { initData } = dialogObj
      this.dialogInitData = initData
      console.log('initData', initData)
    },
    getTableData() {
      const param = { 'pageNum': this.pageNum, 'pageSize': this.pageSize }
      if (this.dialogInitData.nextCandidateDepts !== '' && this.dialogInitData.nextCandidateDepts !== null) {
        param['deptCodes'] = this.dialogInitData.nextCandidateDepts.join(',')
      }
      if (this.dialogInitData.nextCandidateOrgs !== '' && this.dialogInitData.nextCandidateOrgs !== null) {
        param['orgCodes'] = this.dialogInitData.nextCandidateOrgs.join(',')
      }
      if (this.dialogInitData.nextCandidateRoles !== '' && this.dialogInitData.nextCandidateRoles !== null) {
        param['roleCodes'] = this.dialogInitData.nextCandidateRoles.join(',')
      }
      if (this.dialogInitData.nextCandidateUsers !== '' && this.dialogInitData.nextCandidateUsers !== null) {
        // 这要做特殊处理去除${user}
        let value = ''
        for (let i = 0; i < this.dialogInitData.nextCandidateUsers; i++) {
          if (this.dialogInitData.nextCandidateUsers[i] !== '${user}') {
            value += this.dialogInitData.nextCandidateUsers[i]
          }
        }
        param['userLoginNames'] = value
      }
      this.$request({
        formdata: true,
        url: this.$api.workflowUser, // [必填] 请求地址
        data: param, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        this.tableData = response.data.grid.list
        this.total = response.data.grid.total
      })
    }

  }
}
</script>
<style scoped>
    .maintree_title {
        width:265px;
        height:35px;
        line-height:35px;
        padding-left:10px;
        background:#4586E0;
        color:#FFF;
        font-weight: bold;
    }
    .select_people {
        overflow: hidden;
    }
    .select_people .box_cnt li {
        padding-left: 20px;
        line-height: 24px;
    }

    .select_people .box_cnt li i {
        color: #4195F0;
        font-size: 14px;
        padding-right: 5px;
    }
    .select_people .select_title {
        background:#4586E0;
        height: 36px;
        padding: 4px 0;
        text-indent: 20px;
        font-weight: bold;
        clear: both;
        color: white;
    }
</style>

