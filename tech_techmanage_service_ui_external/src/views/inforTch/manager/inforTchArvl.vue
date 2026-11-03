<!-- 信息科技外包申请 -->
<template>
  <div>
    <div class="typeset-container">
      <div class="typeset-head">
        <p class="nav_name">待审批信息</p>

        <div class="module_input">
          <el-table
            :data="data"
            border
            height="700"
            align="center"
            style="width:1200px"
          >
            <el-table-column prop="outreqName" align="center" label="外包需求名称" width="200"/>
            <el-table-column prop="outType" align="center" label="外包类别" width="200">
              <template slot-scope="scope">
                <span>{{ getDictName(personAttrDictId,scope.row.outType) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="empname" align="center" label="申请人" width="200"/>
            <el-table-column :formatter="formatDate" prop="alctionTime" align="center" label="申请时间" width="200"/>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  v-if="(scope.row.flag == 0) || (scope.row.flag == -1)"
                  type="text"
                  size="small"
                  @click="delBox(scope.row.instanceid)">删 除</el-button>
                <el-button
                  type="text"
                  size="small"
                  @click="aplyDetail(scope.row,scope.$index)">申请详情</el-button>
                <el-button
                  type="text"
                  size="small"
                  @click="signProvide(scope.row,scope.$index)">签署意见</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import common from '@/api/common'
import { getDsp, getArvlDetail, doDel } from '@/api/inforTch/inforTchSelt.js'
import { downloadRdProjectFile } from '@/api/inforTch/inforTchAply.js'
// eslint-disable-next-line
import { getDictName } from '@/utils/dict'
const ntkoBrowser = require('@/components/NtkoComponents/ntkobackground2.min').ntkoBrowser // 引入ntko

export default {
  name: 'InforTchArvl',
  data() {
    return {
      personAttrDictId: 'XXJK_ARVL_OUTTYPE',
      cascaderProps: {
        emitPath: false
      },
      conclusion: '',
      suggest: '',
      orgTreeData: [],
      xklx: {},
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
      data: [],
      dialogVisible: false,
      dialogSignVisible: false
    }
  },
  created() {
    this.initData()
  },
  methods: {
    // 初始化
    initData() {
      // 查询待审批
      getDsp().then(res => {
        if (res.data.status !== 200) {
          if (res.data.status === 5009) {
            self.$confirm(res.data.message, {
              confirmButtonText: '确定',
              type: 'error'
            }).then(() => {
              this.$store.dispatch('LogOut').then(() => {
                location.reload()
              })
            }).catch()
          } else {
            self.$alert(res.data.message, { type: 'error' })
          }
          return false
        }
        if (res.data.results.taskList !== undefined) {
          res.data.results.taskList.map(val => {
            const params = {}
            params.instanceid = val.processInstanceId
            const data = common.paramsFormat(params)
            getArvlDetail(data).then(res => {
              if (res.data.status === 200) {
                let datas = {}
                datas = { ...datas, ...res.data.results }
                datas.bps = val
                this.data.push(datas)
                this.data.sort((a, b) => {
                  if (b.alctionTime.localeCompare(a.alctionTime) !== 0) {
                    return b.alctionTime.localeCompare(a.alctionTime)
                  }
                  return b.instanceid.localeCompare(a.instanceid)
                })
              } else {
                this.$message.error(res.data.message)
              }
            })
          })
        }
        console.log(this.data, '------------')
      })
    },
    // 提交
    proving() {
      const data = {}
      data.conclusion = this.conclusion
      data.suggest = this.suggest
      let status = new Promise(() => { })
      status = this.commit(data).then((val) => {
        return val
      })
      return status
    },
    // 申请详情
    aplyDetail(row, index) {
      row.buttonStutas = 'xq'
      this.$router.push({
        name: row.flowid,
        query: row
      })
    },
    // 日期格式
    formatDate(row, column, cellValue) {
      if (!cellValue) {
        return ''
      }
      const date = new Date(cellValue)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    // 下载
    downloadFile(row, index) {
      this.$confirm('是否导出' + row.fileName, { confirmButtonText: '是', cancelButtonText: '否', type: 'warning' }).then(_ => {
        downloadRdProjectFile({ id: row.id, _isDown: 'Y' }).then(res => {
          common.download(res.data, self, row.fileName)
        })
      })
    },
    // 打开删除messageBox窗口
    delBox(data) {
      this.$confirm('确认删除该流程吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delPro(data)
      }).catch(() => {

      })
    },
    // 删除流程
    delPro(data) {
      const loading = common.loading(this)
      const params = {}
      params.instanceid = data
      const datas = common.paramsFormat(params)
      doDel(datas).then(res => {
        // 关闭loading
        loading.close()
        if (res.data.status === 200) {
          this.$message.success('流程删除成功')
          this.data = []
          this.initData()
        } else {
          this.$message.error('流程删除失败')
        }
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
    signProvide(row, index) {
      row.buttonStutas = 'qs'
      this.$router.push({
        name: row.flowid,
        query: row
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
  height: 870px;
  background-color: #ffffff;
  padding: 10px;
  padding-top: 20px;
}
.module_input {
  width: 100%;
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

</style>
