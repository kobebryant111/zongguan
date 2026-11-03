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
            <el-col :span="5">  <div>
                <div class="maintree_title">流程分类树</div>
                <ts-multi-trees
                        style="width: 260px;height: 510px;  border-right: 1px solid #ddd;overflow-y:auto;"
                        :options="options"
                        ref="classifyTree"
                        @node-click="handleNodeClick">
                </ts-multi-trees>
             </div>
            </el-col>
            <el-col :span="14">
                <div >
                    <div class="select_title">可选流程</div>
                    <div style="margin-left: 20px">
                        <div  style="margin-top: 5px;margin-bottom: -10px">
                            <el-form inline>
                                <el-form-item label="流程键值">
                                    <el-input  size="mini" v-model="query.modelKeyLike"></el-input>
                                </el-form-item>
                                <el-form-item label="流程名称">
                                    <el-input  size="mini" v-model="query.modelName"></el-input>
                                </el-form-item>
                                <el-form-item>
                                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search" style="margin-left: 40px">查询</el-button>
                                </el-form-item>
                                <el-form-item>
                                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                                </el-form-item>
                            </el-form>
                        </div>
                        <ts-multi-table
                                ref="list"
                                :query="query"
                                :options="listOptions"
                                @reset-query="refreshQuery"
                                @selection-change="handleSelectionChange">
                            <!-- <el-table-column prop="tenantName" label="租户名称"  show-overflow-tooltip/> -->
                            <el-table-column prop="categoryName" label="分类名称" show-overflow-tooltip/>
                            <el-table-column prop="modelName" label="流程名称"/>
                            <el-table-column prop="modelKey" label="流程键值"/>
                            <el-table-column prop="createTime" label="发布时间">
                              <template slot-scope="scope"><i class="el-icon-time"></i><span style="margin-left: 10px">{{scope.row.createTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span></template>
                            </el-table-column>
                        </ts-multi-table>
                    </div>
                </div>
            </el-col>
            <el-col :span="5">
                <div>
                    <div class="select_title">已选流程</div>
                    <div style="margin-left: 10px;overflow:auto;height: 510px;border-left: 1px solid  #ddd; ">
                        <ul>
                            <li v-for="(item) in selectList" :key="item.id">{{item.modelKey}} ({{item.modelName}})</li>
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
      classifyIdList: [],
      options: {
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId },
        expandClickNode: false

      },
      query: {},
      listOptions: { // 必填
        size: 'mini',
        type: 'checkbox', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.processList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'data', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: { 'deploymentStatus': '1',tenantId: this.$store.state.user.userInfo.tenantId }, // 选填 表示接口的必需恒定不变的参数
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
  mounted() {
  },
  methods: {
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    handleSelectionChange(selection) {
      this.selectList = selection
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
    },
    search() {
      this.$refs['list'].serchHandel()
    },
    handleNodeClick(data, mark, node) {
      // 获取选择的类型
      let classifyIds = []
      this.recursionGetClassifyId(node,classifyIds)
      let classifyId = classifyIds.join(",")
      this.$set(this.listOptions.apiRequiredParams, 'classifyId', classifyId)
      // 需要重置列表的页数默认是1
      this.$refs['list'].refreshDataSource()
      if (node.plans) {
        this.plans = node.plans
      }
    },
    recursionGetClassifyId(node,classifyIds){
        // 递归获取所有子孙节点的分类id
        classifyIds.push(node.key)
        let childNodes = node.childNodes;
        if(childNodes.length > 0){
            for(let i = 0; i < childNodes.length; i++){
                let childNode = childNodes[i];
                this.recursionGetClassifyId(childNode,classifyIds)
            }
        }
        return classifyIds
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

    .select_people .box_cnt {
        margin-top: 10px;
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

    .select_people .box:first-child {
        border-right: solid 1px #ddd;
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

