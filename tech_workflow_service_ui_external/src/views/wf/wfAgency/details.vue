
<template>
    <div class="app-container">
        <el-row :gutter="25">
            <el-col >
                <div class="ts-tabs-box">
                    <el-tabs>
                        <div>
                            <el-form inline label-width="120px">
                                <el-form-item label="委托人" >
                                   <el-input size="mini" v-model="query.trustUser" type="text"  maxlength="10" :disabled="true" >
                                       <el-button slot="append" icon="el-icon-search"  @click="selectTrust"></el-button>
                                    </el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                                </el-form-item>
                                <el-form-item>
                                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                                </el-form-item>

                            </el-form>
                        </div>

                        <div class="buttonDiv">
                             <el-button  type="primary" size="mini"  icon="el-icon-delete"  @click="takeBack">收回</el-button>
                        </div>
                        <div>
                            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery" @row-click="childClick">

                                <el-table-column prop="processName" label="流程名称"/>
                                <el-table-column prop="taskName" label="任务名称"/>
                                <el-table-column prop="trustUser" label="委托人"/>
                                <el-table-column prop="agentUser" label="代办人"/>
                                 <el-table-column prop="delegateTime" label="委托日期">
                                       <template slot-scope="scope">
                                          <span>{{ scope.row.delegateTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span>
                                     </template>
                                 </el-table-column>
                                <el-table-column prop="status" label="当前状态">
                                     <template slot-scope="scope">
                                       <span v-if="scope.row.status ==0">未领取</span>
                                        <span v-if="scope.row.status ==2"><i class="red_input">已回收</i></span>
                                     </template>
                                </el-table-column>
                            </ts-multi-table>
                        </div>
                    </el-tabs>
                </div>
            </el-col>
        </el-row>
      <el-dialog title="委托人" :visible.sync="dialogVisible" width="30%">
               <tree height="330px" @node-click="nodeClick" type="user"></tree>
      </el-dialog>
    </div>
</template>

<script>
import tree from '@/components/Tree'
export default {
  components: {
    tree
  },
  data() {
    return {
      treeStyle: {
        'height': '400px'
      },
      dialogVisible: false,
      isMultiSelect: false,
      icon: 'el-icon-arrow-down',
      isQuery: false,
      userList: [],
      query: { trustUser: '' }, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'checkbox', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.findDelegateList,
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
    nodeClick(data) {
      this.query.trustUser = data.id
      debugger
      this.dialogVisible = false
    },
    takeBack() {
      const object = this.$refs['list'].getSelectedData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      }
      const ids = []
      for (const o of object) {
        if (o.status == '2') {
          this.$tsVue.warn('记录已收回,不能做收回操作！')
          return
        }
        ids.push(o.id)
      }
      const _this = this
      this.$tsVue.confirm('确定收回!', () => {
        _this.$request({
          type: 'post',
          url: _this.$api.takeBack,
          data: ids
        }).then(res => {
          this.$tsVue.success(res.description)
          this.$refs['list'].serchHandel()
        })
      })
    },
    selectTrust() {
      this.dialogVisible = true
    },
    childClick(data) {
      this.$emit('childByValue', data)
    },
    // 重置
    reset() {
      this.query.trustUser = ''
      this.$refs['list'].serchHandel()
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.options.apiRequiredParams.trustUser = this.query.trustUser
      this.$refs['list'].refreshCurrenPage()
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
