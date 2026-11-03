<!-- 人员列表 -->
<template>
  <div class="list-container interviewList">
    <!-- 表单部分 -->

    <div class="table_box">
      <div class="requireContainer_head">
        <div class="requireContainer_head_en"/>
        <el-row>
          <el-col :span="6">
            <button-orange
              :button-name="buttonAdd"
              :size="sizes"
              :fns="handleAdd"
            />
            <!-- v-show="auth" -->
          </el-col>
        </el-row>
      </div>
      <el-table
        :data="crewData"
        highlight-current-row
        fixed="left"
      >
        <el-table-column
          label="序号"
          align="center"
          prop="seq"
          width="100"
          show-overflow-tooltip
        />
        <el-table-column
          label="约谈标题"
          align="center"
          prop="title"
          show-overflow-tooltip
        />

        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-link
              :underline="false"
              type="warning"
              @click="handleDetail(scope.row.picId,'0')"
            >照片下载</el-link
            >
            <el-link
              :underline="false"
              type="warning"
              @click="handleDetail(scope.row.wordId)"
            >内容下载</el-link
            >
            <el-link
              :underline="false"
              type="warning"
              @click="handleDel(scope.row.personnelInterviewId)"
            >删除</el-link
            >
          </template>
        </el-table-column>
      </el-table>

      <div class="pager_zb">
        <pager
          ref="pager"
          :fns="getPager"
          :total="totalNumber"
          :page-size="size"
          v-model="$attrs"
        />
      </div>
    </div>
    <el-dialog :visible.sync="drawer" title="新增约谈" class="" >
      <div>
        <el-form ref="formHmd" :model="hmdUpdateData">
          <div class="hmd-row-block">
            <div>
              <p>约谈标题 *</p>
              <el-form-item :rules="[{ required: true, message: '请填写约谈标题', trigger: 'blur'}]" prop="title">
                <el-input v-model="hmdUpdateData.title" maxlength="50" show-word-limit/>
              </el-form-item>
            </div>
          </div>
          <div class="hmd-row-block">
            <div>
              <p>约谈照片</p>
              <el-form-item prop="userName">
                <el-upload
                  ref="upload"
                  :auto-upload="false"
                  :limit="1"
                  :on-exceed="handleExceed"
                  :action="uploadUrl"
                  :data="fileInfo"
                  :show-file-list="true"
                  :on-change="handleChange"
                  :before-remove="beforeRemove"
                  accept="image/*"
                  class="upload-demo"
                >
                  <el-button type="primary" @click="handleBefore('1')">上传</el-button>
                </el-upload>
              </el-form-item>
            </div>
          </div>
          <div class="hmd-row-block">
            <div>
              <p>约谈内容</p>
              <el-form-item prop="userName">
                <el-upload
                  ref="upload1"
                  :auto-upload="false"
                  :limit="1"
                  :on-exceed="handleExceed"
                  :action="uploadUrl"
                  :data="fileInfo"
                  :show-file-list="true"
                  :on-change="handleChange1"
                  :before-remove="beforeRemove"
                  accept=".pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx"
                  class="upload-demo"
                >
                  <el-button slot="trigger" type="primary" @click="handleBefore('2')">上传</el-button>
                  <el-button type="primary" @click="surePeoDownLoad">模板下载</el-button>

                </el-upload>

              </el-form-item>
            </div>
          </div>
        </el-form>
        <div class="hmddialog-bottom">
          <button-orange :button-name="sureName" :size="sizes" :fns="surePeo"/>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import pager from '@/components/Pager'
import buttonOrange from '@/components/eleButton/buttonOrange' // 引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon' // 引入按钮组件2
import { queryUpload, queryPager, downLoad, delContract } from '@/api/formalTalk/formalTalk'
import common from '@/api/common'

export default {
  name: 'FormalTalk',
  components: { pager, buttonOrange, buttonCommon },
  data() {
    return {
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      paramsForm: {
      },
      crewData: [], // 列表分页数据
      drawer: false, // 默认不显示
      buttonAdd: '新 增', // 新增
      sureName: '保 存',
      sizes: 'small', // 按钮大小
      hmdUpdateData: {
        title: ''
      },
      uploadUrl: process.env.BASE_API + `/personnelInterview/upload`,
      fileInfo: {},
      fileList: [],
      rowData: {},
      personnelInterviewId: null
    }
  },
  created() {
  },
  mounted() {
    this.loadInit()
  },
  methods: {

    /** 分页初始化方法 */
    loadInit: function() {
      const params = {}
      // const queryParams = JSON.parse(window.localStorage.getItem('queryParams'))
      // if (queryParams == null) {
      params.size = this.size
      params.index = this.index
      // } else {
      // this.paramsForm = queryParams
      // params = queryParams
      // }
      this.getPagerList(params)
    },

    /** 获取列表数据 */
    getPagerList(params) {
      //   let data = common.paramsFormat(params);
      const self = this
      queryPager(params).then((res) => {
        if (res.data.status == 200) {
          console.log(res, '响应数据')
          self.crewData = res.data.results
          self.totalNumber = res.data.total
        }
      })
    },
    // 刷新数据
    refrushData() {
      this.getPager(this.size, 1)
    },

    /** 分页触发 */
    getPager: function(size, index) {
      const params = {}
      const depid = this.paramsForm.deptno
      // params.deptno = depid.replace("dep", "");
      // params.msname = this.paramsForm.msname;
      // params.wbsid = this.paramsForm.wbsid;
      // params.depid = this.paramsForm.depid;
      // params.usertype = this.paramsForm.usertype;
      params.size = size
      params.index = index
      this.size = size
      this.index = index
      this.getPagerList(params)
    },
    /** 新增 **/
    handleAdd() {
      this.fileInfo = {}
      this.hmdUpdateData.title = ''
      this.drawer = true
      if (this.$refs.upload) {
        this.$refs.upload.clearFiles()
      }
      if (this.$refs.upload1) {
        this.$refs.upload1.clearFiles()
      }
    },
    /** 保存 */
    surePeo(params) {
      this.$refs['formHmd'].validate(valid => {
        if (valid) {
          this.uploadSuccess()
          this.fileList = []
          this.fileInfo = {}
          this.hmdUpdateData.title = ''
          this.drawer = false
        } else {
          this.$message.error('请填写必填项')
          return false
        }
      })
    },
    /** 删除 */
    handleDel(row) {
      this.$confirm('确认删除吗?', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          delContract({ personnelInterviewIdList: [row] })
            .then((res) => {
              if (res.data.status == 200) {
                this.$message.success('删除成功')
                this.refrushData()
              } else {
                this.$message.error(res.data.message)
              }
            })
            .catch((error) => {
              this.$message.error(error)
            })
        })
        .catch((error) => {})
    },
    handleExceed() {
      this.$message.warning('最多只能上传一个文件!')
    },
    handleChange(file, fileList) {
      console.log(file)

      const maxSize = 30 * 1024 * 1024
      if (file.size > maxSize) {
        this.$message.error('文件大小超出30MB, 请重新上传!')
        this.$refs.upload.clearFiles()
        return false
      }
      file.fType = this.fileInfo.type
      this.fileList.push(file)
      console.log(this.fileList)
    },
    handleChange1(file, fileList) {
      console.log(file)

      const maxSize = 30 * 1024 * 1024
      if (file.size > maxSize) {
        this.$message.error('文件大小超出30MB, 请重新上传!')
        this.$refs.upload1.clearFiles()
        return false
      }
      file.fType = this.fileInfo.type
      this.fileList.push(file)
      console.log(this.fileList)
    },
    beforeRemove(file, filList) {
      // this.fileList = filList.filter(item => item.uid !== file.uid)
      const newList = []
      for (var i = 0; i < this.fileList.length; i++) {
        if (this.fileList[i].uid !== file.uid) {
          newList.push(this.fileList[i])
        }
      }
      this.fileList = newList
      console.log(this.fileList)
    },
    handleBefore(data) {
      // this.fileInfo.personnelInterviewId = this.personnelInterviewId
      this.fileInfo.type = data
      console.log(this.fileInfo)
    },
    uploadSuccess() {
      console.log(111)
      const formData = new FormData()
      if (this.fileList.length > 0) {
        this.fileList.forEach(element => {
          if (element.fType == '1') {
            formData.append('picFile', element.raw)
          } else if (element.fType == '2') {
            formData.append('wordFile', element.raw)
          }
        })
      }
      formData.append('title', this.hmdUpdateData.title)

      queryUpload(formData).then(res => {
        if (res.data.status === 200) {
          this.$message({ type: 'success', message: '新增成功！' })
          this.refrushData()
        } else {
          this.$message({ type: 'error', message: '新增失败！' })
        }
      })
      // this.$refs.upload.submit()
      this.fileInfo = {}
    },
    handleDetail(row, type) {
      if (!row) {
        this.$message.error('新增时没有上传无法下载')
        return
      }
      const loading = common.loading(this)

      downLoad({ type: '1', fileId: row }).then(res => {
        loading.close()
        if (res.data.status != '400') {
          const self = this
          if (type == '0') {
            this.oiDownLoad(res.data, self, '图片', type)
          } else {
            this.oiDownLoad(res.data, self, '内容', type)
          }
        } else {
          this.$message.error('下载失败')
        }
      })
    },
    surePeoDownLoad() {
      const loading = common.loading(this)

      downLoad({ type: '0', fileId: '' }).then(res => {
        console.log(res)

        loading.close()
        if (res.data.status != '400') {
          const self = this
          this.oiDownLoad(res.data, self)
        } else {
          this.$message.error('下载失败')
        }
      })
    },
    oiDownLoad(data, self, fileName = '模板', type) {
      console.log('文件：', data)
      if (!data) {
        return
      }
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      // 兼容IE/Edge
        navigator.msSaveBlob(data, fileName)
      } else {
        let typ = ''
        // type=0为图片，不为0是文件
        if (type == '0') {
          typ = 'image/png'
        } else {
          typ = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
        }
        console.log(typ)

        const url = window.URL.createObjectURL(new Blob([data], { type: typ }))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
      }
      self.$message.success('下载成功')
    }
  }
}
</script>
<style scoped>
.interviewList {
  width: 100%;
  /* height: 100%; */
  padding: 15px;
}
.form_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px 18px 0 18px;
}
.table_box {
  width: 100%;
  background-color: #ffffff;
  padding: 18px;
}
.requireContainer_head {
  float: right;
}
.margin_bottom {
  margin-bottom: 15px;
}
/* 分页组件位置 */
.pager_zb {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}
.hmddialog-bottom {
  margin-top: 50px;
  text-align: center;
}
</style>

<style>
.el-button {
  margin-left: 15px;
}
.el-row {
  margin-bottom: 15px;
}
</style>

