<!-- 风险管理部负责人审批-->
<template>
  <div class="typeset-container">
    <div class="wrapper_right">
      <el-col :span="1.5">
        <button-common :button-name="buttonClose" :size="size" :fns="close"/>
      </el-col>
    </div>
    <div v-if="buttonStutas == 'xq'" class="typeset-head">

      <p class="nav_name">申请信息</p>
      <el-form ref="form" :rules="rules" :model="form">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="申请人" label-width="120px">
              <el-input v-model="form.empname" :disabled="true" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="申请时间" label-width="120px">
              <el-input :value="formatDate(form.alctionTime)" v-model="form.alctionTime" :disabled="true" placeholder="请输入"/>
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
              <el-select v-model="form.outType" :disabled="true" placeholder="请输入">
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
              <el-input v-model="form.outreqName" :disabled="true" placeholder="请输入"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="申请部门负责人" prop="alctionDirector" label-width="120px">
              <!-- <el-input v-model="form.alctionDirector" placeholder="请输入"/> -->
              <el-select v-model="form.alctionDirector" :disabled="true" placeholder="请输入">
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
              <el-select v-model="form.outDepid" :disabled="true" placeholder="请输入" @change="orgTreeDataChange()">
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
              <el-input v-model="form.outreqExplan" :disabled="true" type="textarea" placeholder="请输入"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="upload_box">
        <p style="color: rgb(255,110,110)">* </p>
        <p class="navd_name">外包业务需求审批报告</p>
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
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-if="buttonStutas == 'qs'" class="typeset-head">
      <p class="nav_name">签署意见</p>
      <div>
        <el-radio-group v-model="conclusion" @change="conclusionChange">
          <el-radio v-model="conclusion" label="01" >同意</el-radio>
          <el-radio v-model="conclusion" label="02" >不同意</el-radio>
        </el-radio-group>
        <div v-if="conclusion === '02'" class="upload_box">
          <p style="color: rgb(255,110,110)">* </p>
          <p class="navd_name">退回意见：</p>
          <el-input v-model="suggest" type="text" maxlength="100" show-word-limit clearable placeholder="请输入" style="width: 50%"/>
        </div>
        <div class="button_zb">
          <!-- <el-button slot="trigger" type="primary" @click="save(form)">保存</el-button> -->
          <el-button slot="trigger" type="primary" @click="reset(form)">重置</el-button>
          <el-button slot="trigger" type="primary" @click="liuzhuanStart(form)">提交</el-button>
          <!-- <el-button v-show="ture" slot="trigger" type="primary">提交</el-button> -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import PersonTable from '@/api/researchManager/rpersonTable'
import { getArvlDetail, resPerApprove } from '@/api/inforTch/inforTchSelt.js'
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import common from '@/api/common'
import { getAplyDirector, downloadRdProjectFile } from '@/api/inforTch/inforTchAply.js'
const ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser // 引入ntko

export default {
  name: 'ResPerCharge',
  components: { buttonCommon },
  data() {
    return {
      buttonClose: '关 闭', // 取消按钮名字
      size: 'mini', // 按钮大小
      cascaderProps: {
        emitPath: false
      },
      conclusion: '',
      suggest: '',
      buttonStutas: '',
      orgTreeData: [],
      options: [],
      form: {
        id: '',
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
        fileList: []
      },
      personAttrDictId: 'XXJK_ARVL_OUTTYPE',
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
  },
  methods: {
    // 初始化
    init(item) {
      this.buttonStutas = item.buttonStutas
      this.bps = item.bps
      console.log(this.bps, 'this.bps')
      this.bps.processInstId = item.bps.processInstanceId
      this.getXxkjwblxDetail(item.bps)
      const self = this
      PersonTable.queryOrgTree().then(res => {
        if (res.data.status === 200) {
          self.orgTreeData = res.data.results[0].children
        } else {
          this.$message.error(res.data.message)
        }
      })
      // 获取申请部门负责人
      getAplyDirector({ depid: item.alctionDepid }).then(res => {
        if (res.data.status === 200) {
          this.options = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    transformTree(list) {
      return list.map(item => {
        const newItem = {
          value: item.id,
          label: item.text,
          children: item.children
        }
        if (item.children) {
          newItem.children = this.transformTree(item.children)
        }
        return newItem
      })
    },
    getXxkjwblxDetail(val) {
      const params = {}
      params.instanceid = val.processInstanceId
      const data = common.paramsFormat(params)
      getArvlDetail(data).then(res => {
        if (res.data.status === 200) {
          this.form = { ...this.form, ...res.data.results }
          console.log(res.data.results, this.form, 'this.form')
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 重置
    reset() {
      this.conclusion = ''
      this.suggest = ''
    },
    // 提交
    liuzhuanStart() {
      const data = {}
      data.conclusion = this.conclusion
      data.suggest = this.suggest
      if (this.conclusion === '01') {
        this.proving(data).then((val) => {
          if (undefined === val) {
            return false
          }
          if (val.status === 200) {
            if (val.message.indexOf('已结束') === -1) {
              const message = common.getFlowMessgeNew(val.message)
              console.log('message', message)
              const messageArr = message.split(',')
              this.$nextTick(() => {
                this.messageNotify(messageArr)
                this.$router.push({ name: 'InforTchArvl' })
              })
            } else {
              this.messageNotify2(val.message)
              this.$router.push({ name: 'InforTchArvl' })
            }
          } else {
            this.errorMessage = '操作失败'
            this.messageError()
          }
        })
      } else if (this.conclusion === '02') {
        if (!this.suggest || this.suggest === '') {
          this.$message.error('该项不能为空')
          return
        }
        this.proving(data).then((val) => {
          console.log('退回', val)
          if (val.status === 200) {
            if (val.message.indexOf('已结束') === -1) {
              const message = common.getFlowMessgeNew(val.message)
              console.log('message', message)
              const messageArr = message.split(',')
              this.$nextTick(() => {
                this.messageNotify(messageArr)
                // this.$router.back();
                this.$router.push({ name: 'InforTchArvl' })
              })
            } else {
              this.$nextTick(() => {
                this.messageNotify2(val.message)
                // this.$router.back();
                this.$router.push({ name: 'InforTchArvl' })
              })
            }
          } else {
            this.errorMessage = '操作失败'
            this.messageError()
          }
        })
      } else {
        this.$message.error('该项不能为空')
      }
    },
    // 失败消息提示
    messageError() {
      this.$message.error(this.errorMessage)
    },
    proving(data) {
      let status = new Promise(() => { })
      status = this.commit(data).then((val) => {
        return val
      })
      return status
    },
    // 日期格式
    formatDate(value) {
      if (!value) {
        return ''
      }
      return value.substring(0, 10)
    },
    // 消息提示
    messageNotify(messageVal) {
      if (messageVal.length > 3) {
        messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(')')[0] + ')'
      }
      const messageSec = messageVal.slice(2).join('，')
      console.log('提示信息', messageSec, messageVal)
      this.$notify({
        title: '提示',
        dangerouslyUseHTMLString: true,
        message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
        duration: 0
      })
    },
    messageNotify2(messageVal) {
      this.$notify({
        title: '提示',
        message: messageVal,
        duration: 0
      })
    },
    // 提交本页面数据
    commit(jljy) {
      const loading = common.loading(this)
      this.$set(this.bps, 'conclusion', jljy.conclusion)
      this.$set(this.bps, 'suggest', jljy.suggest)
      this.$set(this.form, 'bps', this.bps)
      return resPerApprove(this.form).then(res => {
        loading.close()
        if (res.data.status === 200) {
          return res.data
        } else {
          this.$message.error('操作失败')
        }
      }).finally(() =>
        loading.close()
      )
    },
    conclusionChange(val) {
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
    // 关闭按钮
    close() {
      this.$router.push({ name: 'InforTchArvl' })
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
  padding-top: 20px;
  display: flex;
  align-items: center;
}
.button_zb {
  background-color: #ffffff;
  padding: 10px;
  width: 85%;
  display: flex;
  justify-content: flex-end;
  gap: 5px;
}
.wrapper_right {
  background-color: #ffffff;
  padding: 10px;
  width: 100%;
  display: flex;
  justify-content: flex-end;
}
</style>
