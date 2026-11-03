
<template>
    <div class="app-container">
        <el-row :gutter="25">
            <el-col >
                <div class="ts-tabs-box">
                    <el-tabs>
                        <div>
                            <el-form inline label-width="120px">
                                <el-form-item label="流程定义ID" >
                                    <el-input placeholder="请输入流程名称" size="mini" v-model="query.processDefId"></el-input>
                                </el-form-item>
                                <el-form-item label="任务定义ID">
                                    <el-input placeholder="请输入任务ID" size="mini" v-model="query.taskDefKey"></el-input>
                                </el-form-item>

                                <el-form-item>
                                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                                </el-form-item>
                                <el-form-item>
                                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                                </el-form-item>
                                <el-form-item>
                                    <el-button :icon="icon" type="primary" size="mini" @click="additionalQuery" ></el-button>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div  v-show="isQuery" style="margin-top: -5px">
                            <el-form inline label-width="120px">
                                <el-form-item label="代理人">
                                    <el-input placeholder="请输入代理人" size="mini" v-model="query.agentUser"></el-input>
                                </el-form-item>
                                <el-form-item label="委托人">
                                    <el-input placeholder="请输入委托人" size="mini" v-model="query.trustUser"></el-input>
                                </el-form-item>
                            </el-form>
                        </div>
                        <div class="buttonDiv">
                            <el-button  type="primary" size="mini" icon="el-icon-s-promotion" @click="createAgent">新增</el-button>
                            <el-button  type="primary" size="mini"  icon="el-icon-edit" @click="updateAgent">修改</el-button>
                             <el-button  type="primary" size="mini"  icon="el-icon-delete"  @click="deleteAgent">删除</el-button>
                        </div>
                        <div>
                            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery" @row-click="childClick">
                                <el-table-column prop="processDefName" label="流程名称"/>
                                <el-table-column prop="agentUser" label="代理人"/>
                                <el-table-column prop="trustUser" label="委托人"/>
                                <el-table-column prop="type" label="类型">
                                 <template slot-scope="scope">
                                        <span v-if="scope.row.type == 'user'">个人</span>
                                        <span v-if="scope.row.type == 'role'">角色</span>
                                        <span v-if="scope.row.type == 'org'">机构</span>
                                 </template>
                                </el-table-column>
                                <el-table-column prop="startDate" label="开始时间">
                                     <template slot-scope="scope">
                                          <span>{{ scope.row.startDate | tsDate('YYYY-MM-DD')}}</span>
                                     </template>

                                </el-table-column>
                                <el-table-column prop="endDate" label="结束时间">
                                        <template slot-scope="scope">
                                          <span>{{ scope.row.endDate | tsDate('YYYY-MM-DD')}}</span>
                                     </template>

                                </el-table-column>
                                <el-table-column prop="agentType" label="代理类型">
                                 <template slot-scope="scope">
                                        <span v-if="scope.row.agentType == '0'">部分代理</span>
                                        <span v-if="scope.row.agentType == '1'">全部代理</span>
                                 </template>
                                </el-table-column>
                                <el-table-column prop="effectInd" label="是否有效">
                                  <template slot-scope="scope">
                                        <span v-if="scope.row.effectInd == '1'">已生效</span>
                                  </template>
                                </el-table-column>
                            </ts-multi-table>
                        </div>
                    </el-tabs>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>

export default {

  data() {
    return {
      treeStyle: {
        'height': '400px'
      },
      isMultiSelect: false,
      icon: 'el-icon-arrow-down',
      isQuery: false,
      userList: [],
      query: { }, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.findWorkflowAgentList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        initTable: false,
        apiRequiredParams: {
          tenantId: this.$store.state.user.userInfo.tenantId
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
    handleNodeClick(node) {
      this.$refs['list'].refreshDataSource()
    },
    userChanage(e) {
    },

    createAgent() {
      const obj = {}
      debugger
      obj.tenantId = this.$store.state.user.userInfo.tenantId
      obj.operType = 'add'
      this.$tsVue.tansunPushNoAddTag(this, '/wf/eidtAgent', obj)
    },
    updateAgent() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      const obj = {}
      debugger
      obj.tenantId = this.$store.state.user.userInfo.tenantId
      obj.data = object
      obj.operType = 'edit'
      this.$tsVue.tansunPushNoAddTag(this, '/wf/eidtAgent', obj)
    },
    deleteAgent() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      const _this = this
      this.$tsVue.confirm('确定删除!', () => {
        _this.$request({
          type: 'post',
          url: _this.$api.deleteWfAgent,
          data: object
        }).then(res => {
          this.$tsVue.success(res.description)
          this.$refs['list'].serchHandel()
        })
      })
    },
    additionalQuery() {
      if (this.isQuery) {
        this.isQuery = false
        this.icon = 'el-icon-arrow-down'
      } else {
        this.isQuery = true
        this.icon = 'el-icon-arrow-up'
      }
    },
    childClick(data) {
      this.$emit('childByValue', data)
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
      this.$refs['classifyTree'].resetChecked()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.$refs['list'].serchHandel()
    },
    searchAll() {
      this.$refs['list'].serchHandel()
    }
  },
  mounted() {
    const _this = this
    this.$refs['list'].serchHandel()
  },
  created() {
    debugger
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        margin-left: 10px;
        margin-top: 10px;
    }
    .buttonDiv{
        margin-bottom: 10px;
    }
    .mright_10 {
        margin-right: 10px;
    }

    .text_color {
        color: #3B8EDA
    }
    .maintree_title {
        width:100%;
        height:35px;
        line-height:35px;
        padding-left:10px;
        background:#4586E0;
        color:#FFF;
        font-weight: bold;
    }

    .leftmenu_navtitle {
      height:32px;
      line-height:32px;
      font-weight:bold;
      border-bottom:solid 3px #4586E0;
    }
    .funBox {
        display: flex;
        justify-content: flex-start;
    }
    .moneyCon {
        margin-top: 20px;
        display: flex;

        .item {
            margin-left: auto;
        }
    }
</style>
