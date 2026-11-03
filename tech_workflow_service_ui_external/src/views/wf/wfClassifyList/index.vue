<template>
    <div class="app-container">
        <el-row :gutter="25">
            <div style="width:300px; float:left;padding-left:20px;padding-right:20px;">
                <div class="ts-pane-box">
                    <el-tabs type="border-card" >
                        <el-tab-pane label="流程分类树">
                                <ts-multi-trees
                                        style="width: 100%;height: 400px;overflow-y:auto;"
                                        :options="options"
                                        ref="classifyTree"
                                        @node-click="handleNodeClick">
                                </ts-multi-trees>
                                <div class="maintree_button">
                                    <el-button type="primary" style="width: 70px" size="mini" @click="add">新增分类</el-button>
                                    <el-button type="primary" size="mini" style="width: 90px" @click="addNode">新增子分类</el-button>
                                    <el-button type="danger" style="width: 50px" size="mini" @click="deleteNode">删除</el-button>
                                </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
            <div style="margin-left:300px;margin-right: 20px">
                <div class="ts-tabs-box">
                    <el-tabs>
                        <div>
                            <div class="leftmenu_navtitle">基本信息</div>
                            <div class="allcontent">
                                <form name="classifyForm">
                                    <div class="col_td">
                                        <div class="col4 text_right">上级编号：</div>
                                        <div class="wd190 float_left">
                                            <el-input  size="mini" v-model="form.parentCode"  :disabled="true" />
                                        </div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right">上级名称：</div>
                                        <div class="wd190 float_left"> <el-input size="mini" v-model="form.parentName"  :disabled="true" /></div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>分类编码：</div>
                                        <div class="wd190 float_left"><el-input  size="mini" v-model="form.code" /></div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>分类名称：</div>
                                        <div class="wd190 float_left"><el-input  size="mini" v-model="form.name" /></div>
                                    </div>

                                    <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>是否可用：</div>
                                        <div class="wd190 float_left">
                                            <ts-multi-select v-model="form.useable" size ="mini" :params="selectParam"></ts-multi-select>
                                        </div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>是否默认：</div>
                                        <div class="wd190 float_left">
                                            <ts-multi-select v-model="form.isDefault" size ="mini" :params="selectParam"></ts-multi-select>
                                        </div>
                                    </div>
                                    <div class="col_tr">
                                        <div  style="margin-left:16.5%;">备注：</div>
                                        <div >
                                            <el-input class="layui-textarea" :rows="4" type="textarea" placeholder="请输入内容" v-model="form.remarks" maxlength="10" show-word-limi/>
                                        </div>
                                    </div>
                                    <div class="col_td ng-scope">
                                        <div class="col4 text_right"></div>
                                        <div class="col15 text_right">
                                            <el-button type="primary" size="mini" @click="save" icon="el-icon-check">保存</el-button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </el-tabs>
                </div>
            </div>
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
      form: {
        parentCode: '',
        parentName: '',
        code: '',
        id: '',
        parentId: '0',
        name: '',
        remarks: '',
        useable: '',
        isDefault:'',
        parentIds: '',
        tenantId: this.$store.state.user.userInfo.tenantId
      },
      selectParam: { options: [{ label: '是', value: '1' }, { label: '否', value: '0' }], text: 'label', value: 'value',
        size: 'mini', width: '190px' },
      options: {
        // onlyChildSelected:true,// 选填，是否只允许选择叶子节点
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId },
        callback: (data) => {
          // 构建树map 方便后期查找映射父节点
          data.forEach(object => {
            this.treeDataMap[object.id] = object
          })
        }
      },
      // 构建树map 方便后期查找映射父节点
      treeDataMap: {},
      node: {
        class: '',
        code: '',
        createBy: '',
        createDate: '',
        delFlag: '',
        id: '',
        name: '',
        new: '',
        pId: '',
        parentId: '',
        parentIds: '',
        sort: '',
        type: '',
        updateBy: '',
        updateDate: '',
        useable: ''
      }
    }
  },
  methods: {
    addNode() {
      if (this.node.id === '') {
        this.$tsVue.error('请选择一种分类！', () => {})
      } else {
        this.form.parentName = this.node.name
        this.form.parentCode = this.node.code
        this.form.parentId = this.node.id
        this.form.parentIds = this.node.parentIds
        this.form.id = ''
        this.form.code = ''
        this.form.name = ''
        this.form.remarks = ''
      }
    },
    add() {
      this.form.parentName = ''
      this.form.parentCode = ''
      this.form.parentId = '0'
      this.form.code = ''
      this.form.name = ''
      this.form.remarks = ''
      this.form.id = ''
    },
    deleteNode() {
      if (this.node.id === '') {
        this.$tsVue.error('请选择一种分类！', () => {})
      } else {
        this.$confirm('慎重：确定删除该分类以及所有的子分类？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const data = {
            id: this.node.id
          }
          this.$request({
            url: this.$api.deleteClassify, // [必填] 请求地址
            data: data, // [选填] 请求参数
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
          }).then((response) => {
            this.$tsVue.success(response.description, () => {})
            this.$refs['classifyTree'].updateTree()
          })
        }).catch(() => {
        })
      }
    },
    save() {
      if(this.form.parentId!="0" && this.form.isDefault=="1"){
        this.$tsVue.error("子目录不能选择为默认")
      }else{
        this.$request({
        url: this.$api.saveClassify, // [必填] 请求地址
        data: this.form, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['classifyTree'].updateTree()
        })
        }
    },

    handleNodeClick(node) {
      this.node = node
      // 根节点的情况
      if (this.treeDataMap[node.pId] === undefined) {
        this.form.parentCode = ''
        this.form.parentName = ''
      } else {
        this.form.parentCode = this.treeDataMap[node.pId].code
        this.form.parentName = this.treeDataMap[node.pId].name
      }
      this.form.id = node.id
      this.form.code = node.code
      this.form.name = node.name
      this.form.remarks = node.remarks
      this.form.useable = node.useable
    }
  }
}
</script>

<style lang="scss" scoped>
    .col_td {
        width:50%;
        float:left;
        height:28px;
        margin-top: 15px;
        line-height:28px;
        margin-bottom:8px;
    }
    .col_tr {
        width:100%;
        float:left;
        margin-top: 15px;
        display:table;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
    }
    .red_input {
        font-size:13px;
        color:#FF0000;
        display:inline-block;
        font-weight:bold;
        margin-right:5px;
        font-style:normal;
    }
    .leftmenu_navtitle {
        height:32px;
        line-height:32px;
        font-weight:bold;
        border-bottom:solid 3px #4586E0;
    }
    .col1,.col2,.col3,.col4,.col5,.col6,.col7,.col8,.col9,.col10 {
        line-height:26px;
    }
    .maintree_button {
        width:100%;
        height:38px;
        line-height:38px;
        text-align:center
    }
    .layui-textarea {
        margin-top: -20px;
        margin-left: 20%;
        width: 62%;
        line-height:20px;
        position:relative
    }
    .allcontent {
        width:98%;
        padding:1%;
        display:table;
        margin-bottom:20px;
    }
    .col4 {
        width:40%;
        float:left;
    }
    .text_right {
        text-align:right;
    }
    .textarea {
        margin-right: -40px;
    }
    .wd190 {
        width:190px !important;
        position:relative;
    }
    .float_left {
        float:left;
    }
</style>
