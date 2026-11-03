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
    <div v-if="buttonStutas == 'sp'" class="typeset-head">
      <p class="nav_name">审批信息</p>
      <el-table
        :data="crewData"
        border
        style="width:900px"
        height="650"
      >
        <el-table-column prop="applydesc" label="流程节点" width="200"/>
        <el-table-column prop="username" label="处理人" width="200"/>
        <el-table-column prop="conclusion" label="处理意见" width="200">
          <template slot-scope="scope">
            <span v-if="scope.row.conclusion == '01'">通过</span>
            <span v-else-if="scope.row.conclusion == '02'">退回{{ '(' + scope.row.suggest + ')' }}</span>
            <span v-else-if="scope.row.conclusion == '03'">授权</span>
            <span v-else-if="scope.row.conclusion == '04'">撤回</span>
            <span v-else-if="scope.row.conclusion == '05'">调度</span>
          </template>
        </el-table-column>
        <el-table-column :formatter="formatDate1" prop="createtime" label="时间" width="200"/>
      </el-table>
    </div>
  </div>
</template>
<script>
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import PersonTable from '@/api/researchManager/rpersonTable'
import { getSignDetail } from '@/api/inforTch/inforTchSelt.js'
import common from '@/api/common'
import { getAplyDirector, downloadRdProjectFile } from '@/api/inforTch/inforTchAply.js'
const ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser // 引入ntko

export default {
  name: 'TchSeltDetail',
  components: { buttonCommon },
  data() {
    return {
      sizes: 10, // 初始值 默认：10
      index: 1, // 初始值 默认：1
      buttonClose: '关 闭', // 取消按钮名字
      size: 'mini', // 按钮大小
      flowItem: this.$route.query, // 流程对象
      options: [],
      orgTreeData: [],
      crewData: [],
      buttonStutas: '',
      from: {},
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
  mounted() {
    this.init()
  },
  methods: {
    // 初始化
    init() {
      this.form = { ...this.form, ...this.flowItem }
      console.log(this.form, 'this.form')
      this.buttonStutas = this.form.buttonStutas
      this.sizes = this.form.size
      this.index = this.form.index
      this.from = this.form.form
      PersonTable.queryOrgTree().then(res => {
        if (res.data.status === 200) {
          this.orgTreeData = res.data.results[0].children
        } else {
          this.$message.error(res.data.message)
        }
      })
      const params = {}
      params.flowcaseno = this.form.instanceid
      params.conclusion = this.form.flag
      const data1 = common.paramsFormat(params)
      getSignDetail(data1).then(res => {
        if (res.data.status === 200) {
          this.crewData = res.data.results
          console.log(res.data.results, this.crewData, 'this.crewData')
        } else {
          this.$message.error(res.data.message)
        }
      })
      // 获取申请部门负责人
      getAplyDirector({ depid: this.form.alctionDepid }).then(res => {
        if (res.data.status === 200) {
          this.options = res.data.results
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 日期格式
    formatDate(value) {
      if (!value) {
        return ''
      }
      return value.substring(0, 10)
    },
    // 日期格式
    formatDate1(row, column, cellValue) {
      if (!cellValue) {
        return ''
      }
      const date = new Date(cellValue)
      const y = date.getFullYear()
      const m = String(date.getMonth() + 1).padStart(2, '0')
      const d = String(date.getDate()).padStart(2, '0')
      const h = String(date.getHours()).padStart(2, '0')
      const min = String(date.getMinutes()).padStart(2, '0')
      const s = String(date.getSeconds()).padStart(2, '0')
      return `${y}-${m}-${d} ${h}:${min}:${s}`
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
      const pager = {}
      pager.size = this.sizes
      pager.index = this.index
      pager.form = this.from
      this.$router.push({
        name: 'InforTchSelt',
        query: pager
      })
    }
  }

}
</script>
<style scoped>
.typeset-container {
  padding: 10px;
}
.wrapper_right {
  background-color: #ffffff;
  padding: 10px;
  width: 100%;
  display: flex;
  justify-content: flex-end;
}
.typeset-head {
  width: 100%;
  height: 100%;
  background-color: #ffffff;
  padding: 10px;
  padding-top: 20px;
}
.upload_box{
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
.nav_name {
  width: 180px;
  height: 24px;
  font-size: 16px;
  font-weight: 600;
  color: rgba(38, 38, 38, 1);
  line-height: 24px;
  display: inline-block;
}
</style>
