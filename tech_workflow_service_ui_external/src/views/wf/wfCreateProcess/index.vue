<template>
    <div class="app-container">
        <el-row :gutter="25">
            <el-col :span="4">
                <div class="ts-pane-box">
                    <el-tabs type="border-card" >
                        <el-tab-pane label="流程分类树">
                            <ts-trees
                                    style="width: 100%;height: 400px;overflow-y:auto;"
                                    :options="treeOptions"
                                    ref="classifyTree"
                                    @node-click="handleNodeClick"
                                    @node-drop="nodeDrop"
                                    >
                            </ts-trees>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </el-col>
            <el-col :span="20" >
                <div class="ts-tabs-box">
                    <el-tabs>
                        <div style="margin-left: 30px;margin-right: 30px">
                            <div class="layout">
                                <el-form inline>
                                    <el-form-item label="流程键值：">
                                        <el-input  size="mini" v-model="query.modelKeyLike"></el-input>
                                    </el-form-item>
                                    <el-form-item label="流程名称：">
                                        <el-input  size="mini" v-model="query.modelName"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button icon="el-icon-search" size="mini" type="primary" @click="searchAll">查询所有分类</el-button>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button :icon="icon" type="primary" size="mini" @click="additionalQuery" ></el-button>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <div class="buttonDiv">
                                <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline"  @click="createProcess">创建流程</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-document-copy" @click="copyProcess">复制流程</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-edit-outline" @click="design">设计流程</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-s-promotion" @click="publish">发布</el-button>
<!--                                <el-button  type="primary" size="mini" icon="el-icon-s-promotion" @click="update">更新</el-button>-->
                                <el-button  type="primary" size="mini" icon="el-icon-video-pause" @click="suspendOrActivate('suspend')">挂起</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-video-pause" @click="suspendOrActivate('activate')">激活</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-upload2" @click="importBpmn">导入</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-download" @click="exportBpmn">导出</el-button>
                                <el-button  type="danger"  size="mini" icon="el-icon-delete" @click="deleteProcess">删除</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-date" @click="historyVersion">历史版本</el-button>
                                <el-button  type="primary" size="mini" icon="el-icon-video-play"  @click="startProcess">启动测试</el-button>
                            </div>
                            <ts-multi-dialog :visible.sync="visible" :options="formOptions"></ts-multi-dialog>
                            <ts-multi-dialog  ref="importNode" :visible.sync="visibleImport" :options="importOptions"></ts-multi-dialog>
                            <ts-multi-dialog :visible.sync="visibleVersionList" :options="versionListOptions"></ts-multi-dialog>
                            <ts-multi-dialog  ref="exportNode" :visible.sync="visibleBatchExport" :options="batchExportOptions"></ts-multi-dialog>
                            <div>
                                <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
<!--                                    <el-table-column prop="id" label="主键" min-width="60" show-overflow-tooltip/>-->
<!--                                    <el-table-column prop="tenantName" label="租户编号"/>-->
<!--                                    <el-table-column prop="categoryName" width="180" label="分类名称"/>-->
                                    <el-table-column prop="modelKey" label="流程键值"/>
                                    <el-table-column prop="modelName" label="流程名称"/>
                                    <el-table-column prop="modelVersion" width="80" label="当前版本"/>
                                    <el-table-column prop="deploymentId" width="80" :formatter="conversionState" label="发布状态"/>
                                    <el-table-column prop="createTime" label="发布时间" min-width="100"   sortable>
                                        <template slot-scope="scope"><i class="el-icon-time"></i><span style="margin-left: 10px">{{scope.row.createTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span></template>
                                    </el-table-column>
                                    <el-table-column prop="suspended" :formatter="conversionSuspended" min-width="50"  label="是否挂起"/>
<!--                                    <el-table-column prop="createDate" label="创建时间" min-width="100" sortable>-->
<!--                                        <template slot-scope="scope"><i class="el-icon-time"></i><span style="margin-left: 10px">{{scope.row.createTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span></template>-->
<!--                                    </el-table-column>-->
                                    <el-table-column label="模板" min-width="50" >
                                        <template slot-scope="scope">
                                            <el-switch
                                                    v-model="scope.row.isTemplate"
                                                    :active-value="1"
                                                    :inactive-value="0"
                                                    @change="updateIsTemplate($event,scope.row,scope.$index)"
                                            />
                                        </template>
                                    </el-table-column>
                                    <!--<el-table-column prop="updateDate" label="修改时间" min-width="100" >-->
                                        <!--<template slot-scope="scope"><i class="el-icon-time"></i><span style="margin-left: 10px">{{scope.row.lastUpdateTime | tsDate('YYYY-MM-DD HH:mm:ss')}}</span></template>-->
                                    <!--</el-table-column>-->
                                </ts-multi-table>
                            </div>
                        </div>
                    </el-tabs>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import processDialog from './processDialog.vue'
import processImportDialog from './processImportDialog.vue'
import processVersionListDialog from './processVersionListDialog.vue'
import processBatchExportDialog from './processBatchExportDialog.vue'
export default {
  data() {
    return {
      isQuery: false,
      isMultiSelect: false,
      save: true,
      icon: 'el-icon-arrow-down',
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      visibleImport: false,
      visibleVersionList: false,
      visibleBatchExport: false,
      query: {
      }, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.processList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 session，默认是data
        apiRequiredParams: { tenantId: this.$store.state.user.userInfo.tenantId }, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      },
      formOptions: {
        title: '创建流程', // [必填]
        resource: processDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.submit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      batchExportOptions: {
        title: '批量导出流程', // [必填]
        resource: processBatchExportDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '1300px', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '导 出'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.exportSubmit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      importOptions: {
        title: '导入流程', // [必填]
        resource: processImportDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: { batch: '0',hasFile:"0" }, // [可不填] 弹框中组件页面的初始化数据
        width: '50%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: ['取 消', '提 交'], // [可不填] 默认值['取 消', '确 定']
        callbacks: [this.cancel, this.uploadSubmit], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '' // [可不填] Dialog 的自定义类名 |  | ——
      },
      versionListOptions: {
        title: '历史版本', // [必填]
        resource: processVersionListDialog, // 模拟懒加载组件
        modelName: 'model', // [选填] 组件页面el-form标签的:model属性值的名称，默认为'model'
        data: {}, // [可不填] 弹框中组件页面的初始化数据
        width: '60%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: this.beforeClose, // [可不填] 关闭右上角弹框前的回调, tion(done, data)，done 用于关闭 Dialog, data为表单数据
        buttons: [], // [可不填] 默认值['取 消', '确 定']
        callbacks: [], // [若没有取消和确定按钮时可不填] 若取消按钮或提交按钮没有回调，可写默认值null 如[null, this.submit], tion(done, data)，done 用于关闭 Dialog, data为表单数据
        showCancelBtn: false,
        showConfirmBtn: false,
        modal: true, // [可不填] 是否需要遮罩层 | boolean | 默认true
        showClose: true, // [可不填] 是否显示关闭按钮 | boolean | true
        center: false, // [可不填] 是否对头部和底部采用居中布局 | boolean | false
        fullscreen: false, // [可不填] 是否为全屏 Dialog | boolean | false
        customClass: '', // [可不填] Dialog 的自定义类名 |  | ——
        modalAppendToBody: false
      },
      treeOptions: {
        apiName: this.$api.classifyTree, // 接口 传url
        method: 'post', // 非必填 请求方式 默认post
        paramMode: 'params',
        params: { tenantId: this.$store.state.user.userInfo.tenantId,
        type:'1' },
        draggable: true, // 是否开启拖拽节点功能
        expandClickNode: false,
        allowDrag: (node) => {
          // 判断节点能否被拖拽
          return node.data.isCategoryDir===0;
        },
        allowDrop: (draggingNode, dropNode, type) => {
          var targetNode=dropNode.data
          //首先判断拖拽的目标节点是否为分类节点，如果不是则不允许拖拽
          if(targetNode.isCategoryDir!==1){
            return type === 'prev';
          }else{
            // 拖拽时判定目标节点能否被放置。type 参数有三种情况：'prev'、'inner' 和 'next'，分别表示放置在目标节点前、插入至目标节点和放置在目标节点后
            return type === 'inner';
          }

        },
      },
    }
  },
  mounted() {},
  methods: {
    // 拖拽成功时触发的事件	共四个参数，
    // 依次为：被拖拽节点对应的 Node、结束拖拽时最后进入的节点（可能为空）、被拖拽节点的放置位置（before、after、inner）、event
    nodeDrop(dragNdoe, dropNode, dropType, event) {
      console.log("拖拽结束")
      var targetNode=dropNode.data
      console.log(event)
      console.log('node drop', dragNdoe, dropNode, dropType)
      //首先判断拖拽的目标节点是否为分类节点，如果不是则不允许拖拽
      if(targetNode.isCategoryDir!==1){
        this.$tsVue.error("请将节点拖拽到目录!")
        return false;
      }else{
        let data={
          key:dragNdoe.data.processDefineKey,
          tenantId:this.$store.state.user.userInfo.tenantId,
          name:dragNdoe.data.processName,
          classifyId:targetNode.id,
        }
      // 请求先试探下有没有流出可以导出
      this.$request({
        url: this.$api.dropProcess, // [必填] 请求地址
        data: data, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          this.$tsVue.success(response.description)
        }
      })
      }
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    conversionState(row, column) {
      if (row[column.property] === '') {
        return '未发布'
      } else {
        return '已发布'
      }
    },
    conversionSuspended(row, column) {
      if (row[column.property] === '2') {
        return '激活'
      } else {
        return '挂起'
      }
    },
    // 流程批量导出
    exportSubmit(data, done, datum) {
      const selectList = datum.selectList
      const classifyIdList = datum.classifyIdList
      console.log(classifyIdList)
      const keys = []
      const classifyIds = []
      let url = ''
      if (selectList.length !== 0) {
        for (let i = 0; i < selectList.length; i++) {
          keys.push(selectList[i].modelKey)
        }
      }
      if (classifyIdList.length !== 0) {
        for (let i = 0; i < classifyIdList.length; i++) {
          classifyIds.push(classifyIdList[i])
        }
      }// 全量导出
      console.log(keys)
      if (classifyIds.length === 0 && selectList.length === 0) {
        url = process.env.VUE_APP_BASE_API + this.$api.batchModelBpmn + '?isBeforeDownload=' + true
        const data = { 'isBeforeDownload': false }
        this.exportRequest(url, data, done)
      } else {
        const keysString = keys.join(',')
        const classifyIdsString = classifyIds.join(',')
        url = process.env.VUE_APP_BASE_API + this.$api.batchModelBpmn + '?keys=' + keys + '&classifyIds=' + classifyIds + '&isBeforeDownload=' + true
        const data = { 'isBeforeDownload': false, 'keys': keysString, 'classifyIds': classifyIdsString }
        this.exportRequest(url, data, done)
      }
    },
    exportRequest(url, data, done) {
      // 请求先试探下有没有流出可以导出
      this.$request({
        url: this.$api.batchModelBpmn, // [必填] 请求地址
        data: data, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'data' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          const this_ = this
          data.isBeforeDownload = true
          this.$tsVue.tsRequestByGetBlob(this.$request, {
            url: this.$api.batchModelBpmn,
            params: data,
            cb: function(res) {
              this_.$tsVue.success(response.description)
            }
          })
          done()
        }
      })
    },
    // 流程导入
    uploadSubmit(data, done, datum) {
      if(data.hasFile=='0'){
          this.$tsVue.error("未添加信息或未添加文件");
          return;
      }
      const uploadObj = this.$refs.importNode.$refs.parentRef.$refs.upload
      console.log(uploadObj)
      // 修改这个组件来点参数
      uploadObj.params.data = {
        classify: data.classifyId,
        key: data.key,
        tenantId: data.tenantId,
        batch: data.batch,
        name: data.name,
        description: data.description
      }
      this.$nextTick(() => {
        const result = uploadObj.submit(uploadObj.params.data)
      })
    },
    // 弹窗提交复制流程
    submit(data, done) {
      console.log(data)
      const param = {
        tenantId: data.tenantId,
        classifyId: data.category,
        key: data.modelKey,
        name: data.modelName,
        description: data.description,
        templateModelId:data.templateModelId
      }
      // 保存调用
      if (this.save) {
        this.$request({
          url: this.$api.saveModel, // [必填] 请求地址
          data: param, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          // this.design()
          // this.$refs['list'].refreshDataSource()
          const object={}
          object.id = data.modelKey // 组件传部署ID的话无法去获取最新版本的source
          object.tenantId=data.tenantId
          const VISITEDVIEWS = JSON.parse(localStorage.getItem('VISITEDVIEWS')) || null
          // 判断是否已打开流程设计器标签页
          let hasTag = false
          VISITEDVIEWS.filter(item => {
            if (item.name === 'processDesigner') {
              hasTag = true
            }
          })
          if (hasTag) {
            this.$confirm('是否重新加载流程图', '信息', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'success'
            }).then(() => {
              object.needupdate = 'yes'
              this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', object)
            }).catch(() => {
            })
          } else {
            object.needupdate = 'yes'
            this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', object)
          }
          done()
        }) // 复制调用
      } else {
        const object = this.$refs['list'].getRadioData()
        param['id'] = object.id
        this.$request({
          url: this.$api.copyModel, // [必填] 请求地址
          data: param, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {})
          this.$refs['list'].refreshDataSource()
          done()
        })
        this.formOptions.data = ''
        this.id = ''
      }
    },
    historyVersion: function() {
      const object = this.$refs['list'].getRadioData()
      console.log(object)
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      } else if (object.deploymentId === '') {
        this.$tsVue.error('未发布的流程没有历史数据')
        return
      } else {
        this.versionListOptions.data = object
        this.versionListOptions.close = this.cancel
        this.visibleVersionList = true
      }
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
    importBpmn() {
      const user = this.$store.state.user.userInfo
      this.importOptions.data.tenantId = user.tenantId
      this.importOptions.data.tenantName = user.tenantName
      this.visibleImport = true
    },
    handleNodeClick(node) {
      if(node.isCategoryDir==1){
          // 获取选择的类型
          let classifyIds = []
          this.recursionGetClassifyId(node,classifyIds)
          let classifyId = classifyIds.join(",")
          this.$set(this.options.apiRequiredParams, 'classifyId', classifyId)
          this.$set(this.options.apiRequiredParams, 'modelKey', null)
          // 需要重置列表的页数默认是1
          this.$refs['list'].refreshDataSource()
          if (node.plans) {
              this.plans = node.plans
          }
      }else{
          // 点击流程定义查询流程定义自己
          console.log(node)
          this.$set(this.options.apiRequiredParams, 'modelKey', node.processDefineKey)
          this.$set(this.options.apiRequiredParams, 'classifyId', null)
          // 需要重置列表的页数默认是1
          this.$refs['list'].refreshDataSource()
          if (node.plans) {
              this.plans = node.plans
          }
      }
    },
    recursionGetClassifyId(node,classifyIds){
      // 递归获取所有子孙节点的分类id
      if(node.isCategoryDir==1){
          classifyIds.push(node.id)
      }
      let childNodes = node.children;
        if(childNodes && childNodes.length > 0){
        for(let i = 0; i < childNodes.length; i++){
          let childNode = childNodes[i];
          this.recursionGetClassifyId(childNode,classifyIds)
        }
      }
      return classifyIds
    },
    exportBpmn() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.visibleBatchExport = true
      } else {
        console.log(object)
        const param = { 'modelId': object.id, 'isBeforeDownload': true }
        this.$request({
          url: this.$api.downLoadModelBpmn, // [必填] 请求地址
          data: param, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          if (response.status === 200) {
            const this_ = this
            this.$tsVue.tsRequestByGetBlob(this.$request, {
              url: this.$api.downLoadModelBpmn,
              params: {
                'modelId': object.id
              },
              cb: function(res) {
                this_.$tsVue.success(response.description)
              }
            })
            // this.$tsVue.success(response.description)
          } else {
            this.$tsVue.error(response.description)
          }
        }).catch(() => {
        })
      }
    },
    createProcess() {
      this.save = true
      this.formOptions.title = '创建流程'
      this.formOptions.data = {}
      this.formOptions.data['isCreated'] = true
      const user = this.$store.state.user.userInfo
      this.formOptions.data.tenantId = user.tenantId
      this.formOptions.data.tenantName = user.tenantName
      this.visible = true
    },
    copyProcess() {
      const object = this.$refs['list'].getRadioData()
      console.log(object)
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        // 打开流程详情的模态框，模态框点击确认后复制流程
        this.formOptions.title = '复制流程'
        this.isCreated = false
        this.formOptions.data = object
        this.formOptions.data['description'] = object.description
        this.formOptions.data['modelKey'] = object.modelKey + '_copy'
        this.formOptions.data['isCreated'] = false
        const user = this.$store.state.user.userInfo
        this.formOptions.data.tenantId = user.tenantId
        this.formOptions.data.tenantName = user.tenantName
        this.save = false
        this.visible = true
      }
    },
    design() {
      const obj = this.$refs['list'].getRadioData()
      const temp = JSON.stringify(obj)
      const object = JSON.parse(temp)
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        object.id = object.modelKey // 组件传部署ID的话无法去获取最新版本的source
        const VISITEDVIEWS = JSON.parse(localStorage.getItem('VISITEDVIEWS')) || null
        // 判断是否已打开流程设计器标签页
        let hasTag = false
        VISITEDVIEWS.filter(item => {
          if (item.name === 'processDesigner') {
            hasTag = true
          }
        })
        if (hasTag) {
          this.$confirm('是否重新加载流程图', '信息', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'success'
          }).then(() => {
            object.needupdate = 'yes'
            this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', object)
          }).catch(() => {
          })
        } else {
          object.needupdate = 'yes'
          this.$tsVue.tansunPushAddTag(this, '/wf/processDesigner', object)
        }
      }
    },
    startProcess() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
        return
      } else {
        if (object.deploymentId === '') {
          this.$tsVue.error('流程发布后才能启动!')
          return
        }
        console.log(object)
        const form = {
          tenantId: object.tenantId,
          tenantName: object.tenantName,
          category: object.category,
          categoryName: object.categoryName,
          processDefKey: object.modelKey,
          proDefKey: object.modelKey,
          classifyCode: object.category
        }
        // 保存左边的测试用例树
        // this.$request({
        //   url: this.$api.saveTest, // [必填] 请求地址
        //   data: form, // [选填] 请求参数
        //   type: 'post', // [选填] String 请求方式 get|post 默认post
        //   paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        // }).then((response) => {
        //   this.$tsVue.success('跳转中')
        // })
        console.log(form)
        this.$tsVue.tansunPushAddTag(this, '/wf/startProcess', form)
      }
    },
    publish() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        const param = { 'id': object.id }
        this.$request({
          url: this.$api.publish, // [必填] 请求地址
          data: param, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          if (response.status === 200) {
            this.$tsVue.success(response.description, () => {
              this.$refs['list'].refreshDataSource()
            })
          }
        })
      }
    },
    update() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        const param = { 'deploymentId': object.deploymentId, 'id': object.id }
        const _this = this
        this.$tsVue.confirm('确认更新流程图', () => {
          _this.$request({
            url: _this.$api.updateDeploy, // [必填] 请求地址
            data: param, // [选填] 请求参数
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
          }).then((response) => {
            if (response.status === 200) {
              _this.$tsVue.success(response.description, () => {
                _this.$refs['list'].refreshDataSource()
              })
            }
          })
        })
      }
    },
    suspendOrActivate(operate) {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据')
      } else {
        if (object.deploymentId === '') {
          this.$tsVue.error('未发布的流程不能进行操作')
          return
        }
        if (object.suspended === 2 && operate === 'activate') {
          this.$tsVue.error('该流程已激活！')
          return
        }
        if (object.suspended === 3 && operate === 'suspend') {
          this.$tsVue.error('该流程已挂起！')
          return
        }
        console.log(object)
        const param = { 'operate': operate, 'procDefId': object.modelKey }
        this.$request({
          url: this.$api.suspendOrActivate, // [必填] 请求地址
          data: param, // [选填] 请求参数
          type: 'post', // [选填] String 请求方式 get|post 默认post
          paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
        }).then((response) => {
          this.$tsVue.success(response.description, () => {
            this.$refs['list'].refreshDataSource()
          })
        }).catch(() => {
        })
      }
    },
    beforeClose(data, done) {
      data = ''
      this.$refs['list'].refreshDataSource()
      done()
    },
    // 弹窗清空数据清除
    cancel(data, done) {
      data = ''
      this.$refs['list'].refreshDataSource()
      done()
    },
    deleteProcess() {
      const object = this.$refs['list'].getRadioData()
      if (object == null) {
        this.$tsVue.error('请选择一条数据', () => {})
      } else {
        this.$confirm('慎重：确定删除该流程以及所有版本关联数据？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const data = { tenantId: object.tenantId, key: object.modelKey }
          this.$request({
            url: this.$api.deleteProcess, // [必填] 请求地址
            data: data, // [选填] 请求参数
            type: 'post', // [选填] String 请求方式 get|post 默认post
            paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
          }).then((response) => {
            this.$tsVue.success(response.description)
            this.$refs['list'].refreshDataSource()
          })
        }).catch(() => {
        })
      }
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
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
      delete this.options.apiRequiredParams.classifyId
      this.$refs['list'].serchHandel()
    },
    updateIsTemplate(newValue, row, index) {
      this.$request({
        url: this.$api.updateIsTemplate, // [必填] 请求地址
        data: { 'modelId': row.id, 'isTemplate': newValue }, // [选填] 请求参数
        type: 'post', // [选填] String 请求方式 get|post 默认post
        paramMode: 'params' // [选填] String 传递参数方式 data|params 默认data
      }).then((response) => {
        if (response.status === 200) {
          this.$tsVue.success(response.description)
          this.$refs['list'].refreshDataSource()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        padding: 10px;
    }
    .buttonDiv{
        margin-bottom: 10px;
    }
</style>
