<!-- 信息科技外包申请 -->
<template>
  <div>
    <div class="typeset-container">
      <div class="typeset-head">
        <p class="nav_name">基本信息</p>
        <el-form ref="form" :rules="rules" :model="form">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="申请人" label-width="120px">
                <el-input v-model="form.empname" :disabled="true" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="申请时间" label-width="120px">
                <el-input v-model="form.alctionTime" :disabled="true" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="申请部门" label-width="120px">
                <el-input v-model="form.alctionLocation" :disabled="true" placeholder="请输入"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="外包需求编号" label-width="120px">
                <el-input v-model="form.outReqNum" :disabled="true" placeholder="请输入"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外包类别" prop="outType" label-width="120px">
                <el-select v-model="form.outType" placeholder="请输入">
                  <el-option label="咨询规划类" value="zxgf"/>
                  <el-option label="开发测试类" value="kfcs"/>
                  <el-option label="运行维护类" value="ywwh"/>
                  <el-option label="安全服务类" value="aqfw"/>
                  <el-option label="业务支持类" value="ywzc"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外包需求名称" prop="outreqName" label-width="120px">
                <el-input v-model="form.outreqName" type="text" maxlength="50" show-word-limit placeholder="请输入"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="申请部门负责人" prop="alctionDirector" label-width="120px">
                <!-- <el-input v-model="form.alctionDirector" placeholder="请输入"/> -->
                <el-select v-model="form.alctionDirector" placeholder="请输入">
                  <el-option
                    v-for="item of options"
                    :key="item.badge"
                    :label="item.empname"
                    :value="item.badge"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="外包用工部门" prop="outDepid" label-width="120px">
                <!-- <el-cascader v-model="form.outDepid" :options="orgTreeData" :props="cascaderProps" placeholder="请输入" @change="orgTreeDataChange()"/> -->
                <el-select v-model="form.outDepid" filterable placeholder="请输入" @change="orgTreeDataChange()">
                  <el-option
                    v-for="item of orgTreeData"
                    :key="item.id"
                    :label="item.text"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="用工部门负责人" prop="outDirector" label-width="120px">
                <el-input v-model="form.outDirector" :disabled="true" placeholder="请输入"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="外包需求简要说明" prop="outreqExplan" label-width="150px">
                <el-input v-model="form.outreqExplan" type="textarea" maxlength="100" show-word-limit placeholder="请输入"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="module_input">
        <div class="upload_box">
          <p style="color: rgb(255,110,110)">* </p>
          <p class="navd_name">外包业务需求审批报告</p>
          <div class="upload_box">
            <el-upload
              :on-success="handleApprovalSuccess"
              :file-list="form.fileList"
              :show-file-list="false"
              :action="uploadUrl"
              :data="fileInfo"
              :accept="'.doc,.docx,.pdf'"
              :before-upload="handleBeforeUpload"
              class="uploadApprovalMaterials"
              list-type="text"
            >
              <el-button slot="trigger" size="small" type="primary">上传附件</el-button>
            </el-upload>
            <el-button slot="trigger" size="small" @click="mbDownload" >下载模板</el-button>
          </div>
        </div>
        <el-table
          :data="form.fileList"
          border
          style="width:900px"
          height="350"
        >
          <el-table-column prop="fileName" label="文件名称" width="200"/>
          <el-table-column prop="createBy" label="上传人" width="200">
            <template slot-scope="scope">
              <span>{{ scope.row.createBy + '(' + scope.row.createBadge + ')' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="createDate" label="上传时间" width="200"/>
          <el-table-column label="操作" >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                @click="downloadFile(scope.row,scope.$index)">下载</el-button>
              <el-button
                type="text"
                size="small"
                @click="openNtkoWindow(scope.row,scope.$index)">预览</el-button>
              <el-button
                type="text"
                size="small"
                @click="deleteFile(scope.row,scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <p v-if="isP" style="color: rgb(255,110,110)">外包业务需求审批报告不能为空</p>
        <div class="button_zb">
          <el-button slot="trigger" type="primary" @click="save(form)">保存</el-button>
          <el-button slot="trigger" type="primary" @click="reset(form)">重置</el-button>
          <el-button slot="trigger" type="primary" @click="submit(form)">提交</el-button>
          <!-- <el-button v-show="ture" slot="trigger" type="primary">提交</el-button> -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>

import { queryWorkItem } from '@/api/integrated/wbryzrlc'
import store from '@/store'
import { getDownloadUrl } from '@/api/common/workflowApi'
import common from '@/api/common'
import { getNextSeqNo, queryInforOutsourcFile, downloadRdProjectFile, deleteInforFile, getAplyDirector,
  getOutDirector, saveInforTechApply, submitInforTechApply } from '@/api/inforTch/inforTchAply.js'
import PersonTable from '@/api/researchManager/rpersonTable'
const ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser // 引入ntko
export default {
  name: 'InforTchAply',
  data() {
    return {
      id: '',
      fileInfo: {},
      instanceid: '',
      bps: {},
      form: {
        id: '',
        instanceid: '',
        empname: '',
        badge: '',
        alctionTime: '',
        alctionDepid: '',
        alctionLocation: '',
        outReqNum: '',
        outType: '',
        outreqName: '',
        alctionDirector: '',
        outDepid: '',
        outDirector: '',
        outDirectorBadge: '',
        outreqExplan: '',
        bps: {},
        fileList: []
      },
      options: [],
      orgTreeData: [],
      isIE: '',
      isP: false,
      cascaderProps: {
        emitPath: false
      },
      uploadUrl: process.env.BASE_API + '/inforTechOut/upload',
      rules: {
        outType: [
          { required: true, message: '请输入外包类别', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        outreqName: [
          { required: true, message: '请输入外包需求名称', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        alctionDirector: [
          { required: true, message: '请输入申请部门负责人', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        outDepid: [
          { required: true, message: '请输入外包用工部门', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        outDirector: [
          { required: true, message: '请输入用工部门负责人', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ],
        outreqExplan: [
          { required: true, message: '请输入外包需求简要说明', trigger: 'blur' },
          { pattern: /.*\S+.*/, message: '不能为空格', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.initData()
  },
  mounted() {
    this.isIE = !!(window.ActiveXObject || 'ActiveXObject' in window) // 判断是否为IE浏览器
  },
  methods: {
    // 初始化
    initData() {
      this.form.id = ''
      this.form.empname = store.getters.name
      const now = new Date()
      this.form.alctionTime = now.getFullYear() + '-' +
      String(now.getMonth() + 1).padStart(2, '0') + '-' + String(now.getDate()).padStart(2, '0')
      this.form.alctionLocation = store.getters.location
      this.form.alctionDepid = store.getters.deptId
      this.form.badge = store.getters.userId
      const date = now.getFullYear() +
      String(now.getMonth() + 1).padStart(2, '0') + String(now.getDate()).padStart(2, '0')
      this.setData('KJWB', date)
      // 获取申请部门负责人
      getAplyDirector({ depid: this.form.alctionDepid }).then(res => {
        if (res.data.status === 200) {
          this.options = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
      PersonTable.queryOrgTree().then(res => {
        if (res.data.status === 200) {
          this.orgTreeData = res.data.results[0].children
        } else {
          this.$message.error(res.data.message)
        }
      })
      this.refresh()
    },
    // 提交
    submit() {
      this.$refs['form'].validate((valid) => {
        if (this.form.fileList.length === 0) {
          // this.$message.warning('外包业务需求审批报告不能为空')
          this.isP = true
          this.$message.warning('请先将内容填写完整')
          return
        } else {
          this.isP = false
        }
        if (valid) {
          // debugger
          this.form.id = this.id
          this.form.instanceid = this.instanceid
          this.form.bps = this.bps
          const params = { ...this.form }
          submitInforTechApply(params).then(res => {
            if (res.data.status === 200) {
              const message = common.getFlowMessgeNew(res.data.message)
              const messageArr = message.split(',')
              // this.messageNotify(messageArr);
              messageArr[2] = messageArr[2].split(')')[0] + ')'
              this.messageNotifyHnfzr(messageArr)
              this.initData()
            } else {
              this.$message.error(res.data.message)
            }
          })
        } else {
          this.$message.warning('请先将内容填写完整')
          console.log('error submit!!')
          return false
        }
      })
    },
    // 消息提示(多节点)
    messageNotifyHnfzr(messageVal) {
      if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(')')[0] + ')'
      }
      const messageSec = messageVal.slice(2).join('，')
      this.$notify({
        title: '提示',
        dangerouslyUseHTMLString: true,
        message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
        duration: 0
      })
    },
    // 刷新
    refresh() {
      this.form.outType = ''
      this.form.outreqName = ''
      this.form.alctionDirector = ''
      this.form.outDepid = ''
      this.form.outDirector = ''
      this.form.outreqExplan = ''
      this.form.fileList = []
    },
    // 重置
    reset() {
      this.form.outType = ''
      this.form.outreqName = ''
      this.form.alctionDirector = ''
      this.form.outDepid = ''
      this.form.outDirector = ''
      this.form.outreqExplan = ''
      this.form.fileList.map(item => {
        this.deleteFiles(item)
      })
    },
    // 保存
    save() {
      const loading = common.loading(this)
      this.form.id = this.id
      this.form.instanceid = this.instanceid
      const params = { ...this.form }
      saveInforTechApply(params).then(res => {
        // 关闭loading
        loading.close()
        if (res.data.status === 200) {
          this.$message.success('保存成功')
          this.getWorkItem(res.data.results.instanceid)
          this.id = res.data.results.id
          this.instanceid = res.data.results.instanceid
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    getWorkItem(val) {
      const loading = common.loading(this)
      const params = {}
      params.processInstID = val
      queryWorkItem(params).then(res => {
        loading.close()
        if (res.data.status === 200) {
          this.bps = res.data.results
          // this.workItemID = res.data.results.workItemID
          // this.processDefName = res.data.results.processDefName
        } else {
          this.$message.error('获取待办工作项详情失败')
        }
      })
    },
    orgTreeDataChange() {
      var qry = { outDepid: this.form.outDepid }
      getOutDirector(qry).then(res => {
        if (res.data.status === 200) {
          this.form.outDirector = res.data.results[0].empname
          this.form.outDirectorBadge = res.data.results[0].badge
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 上传前
    handleBeforeUpload() {
      this.fileInfo.outReqid = this.form.outReqNum
      this.fileInfo.badge = this.form.badge
    },
    // 上传
    handleApprovalSuccess(response, file, fileList) {
      if (response.status === 200) {
        this.queryFile()
      } else {
        this.$message.error(response.message)
      }
    },
    queryFile() {
      var qry = { outReqid: this.form.outReqNum }
      queryInforOutsourcFile(qry).then(res => {
        if (res.data.status === 200) {
          this.form.fileList = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 获取编号
    setData(val, date) {
      const param = { seqtype: val, seqprefix: date, formatLength: '4' }
      getNextSeqNo(param).then(res => {
        if (res.data.status === 200) {
          this.form.outReqNum = val + res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 下载
    downloadFile(row, index) {
      this.$confirm('是否导出' + row.fileName, { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        downloadRdProjectFile({ id: row.id, _isDown: 'Y' }).then(res => {
          common.download(res.data, self, row.fileName)
        })
      })
    },
    // 预览
    openNtkoWindow(obj, index) {
      const formJudge = obj.fileName.split('.')[
        obj.fileName.split('.').length - 1
      ]
      if (formJudge === 'jpg' || formJudge === 'png' || formJudge === 'PNG') {
        this.ylDialog = true
        const pos = obj.tag1.lastIndexOf('/')
        const str = obj.tag1.substr(0, pos)
        if (obj.tag2 !== undefined) {
          this.ylPhotoUrl = str + '/' + obj.tag2
        } else {
          this.ylPhotoUrl = obj.tag1
        }
      } else if (formJudge === 'zip' || formJudge === 'rar' || formJudge === 'txt') {
        this.$message.error('不能预览压缩文件或txt文件格式')
      } else {
        this.ntkoYl(obj.id)
      }
    },
    // ntko预览方法
    ntkoYl(objectId) {
      var url = window.location.href
      const ntkoed = ntkoBrowser.ExtensionInstalled()
      if (ntkoed) {
        ntkoBrowser.openWindow(
          url.substring(0, url.indexOf('#')) + '#/ntko/?id=' + objectId
        )
      } else {
        window.open(
          url.substring(0, url.indexOf('#')) + '#/ntko/?id=' + objectId
        )
      }
      // 注册回调方法
      window.ntkoCloseEvent = function() {
        alert('跨浏览器插件应用程序窗口已关闭!')
      }
    },
    // 删除
    deleteFile(row, index) {
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteFiles(row)
      })
    },
    deleteFiles(row) {
      const loading = common.loading(this)
      deleteInforFile({ id: row.id }).then(res => {
        loading.close()
        if (res.data.status === 200) {
          this.$message.success('删除成功')
          this.queryFile()
        } else {
          this.$message.error(res.data.message ? res.data.message : '删除失败')
        }
      }).catch(error => {
        loading.close()
        this.$message.error(error)
      })
    },
    // 模板下载
    mbDownload() {
      getDownloadUrl({ key: 'PPM_NEW_XXKJ' }).then(res => {
        if (res.data.status === 200) {
          const self = this
          common.downloadJl(res.data.results.documentno, self)
        } else {
          this.$message.error('获取下载地址失败')
        }
      })
    }

  }
}
</script>

<style scoped>
.typeset-container {
  padding: 10px;
}
.typeset-head {
  width: 100%;
  height: 100%;
  background-color: #ffffff;
  padding: 10px;
  padding-top: 20px;
}
.module_input {
  background-color: #ffffff;
  padding: 10px;
  padding-top: 20px;
  padding-left: 30px;
}
.nav_name {
  width: 180px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}
.upload_box{
  display: flex;
  align-items: center;
}
.navd_name {
  width: 200px;
  height: 24px;
  font-size: 14px;
  font-weight: 600;
  color: rgb(90, 89, 89);
  line-height: 24px;
  display: inline-block;
}
.button_zb {
  background-color: #ffffff;
  padding: 10px;
  width: 85%;
  display: flex;
  justify-content: flex-end;
  gap: 5px;
}
</style>
