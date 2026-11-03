<template>
  <div class="typeset-container">
    <el-row :gutter="20">
      <rProject-administ-select :show-red="true" :tree-item-click.sync="handleNodeClick" />
      <el-col :span="18" >
        <div v-if="showUserData" class="user-data">
          <p>姓名：{{ userData.empname }}</p>
          <p>工号：{{ userData.badge }}</p>
          <p>所属部门：{{ userData.chargeName }}</p>
          <p v-if="!checkFunc(['00000_HIDDEN'])">研发人员性质：{{ getDictName('NEWKG_RD_NATURE',userData.nature) }}</p>
          <p>在岗状态：{{ getDictName('NEWKG_RD_EMPSTATUS',userData.empstatus) }}</p>
          <!-- 已完结项目 -->
          <div style="border:1px solid #EBEEF5;padding:3px; margin-top: 10px">
            <el-button :icon="clickDiolog1 ? 'el-icon-arrow-down' : 'el-icon-arrow-right' " type="text" @click="clickDiolog"/> 已完结项目
            <span v-if="finDocState !== 3" :class="posClass(0,0)">（上传状态:</span>
            <el-tooltip v-if="finDocState !== 3" :content = "contenTop" effect="light" >
              <span v-if="finDocState !== 3" :class="posClass(0,1)"/>
            </el-tooltip>
            <span v-if="finDocState !== 3" :class="posClass(0,2)">）</span>
            <el-table
              v-loading="loading"
              v-if="clickDiolog1"
              :data="projectData1"
              element-loading-text="数据加载中"
              style="width: 100%;"
            >
              <el-table-column type="expand" width="60">
                <!-- 嵌套列表折叠 -->
                <template slot-scope="props" >
                  <div style="border:1px  solid #EBEEF5; margin: 0 70px; padding:5px 5px 15px 5px; width: 80%">
                    <el-row class="align-rowOther">
                      <el-col :span="3">
                        <el-button v-hasFunc="['1365_LICENCE']" type="primary" size="small" @click="handleExpand(props.row)">上传文件</el-button>
                      </el-col>
                      <el-col :span="3">
                        <el-button v-hasFunc="['1366_LICENCE']" type="primary" size="small" @click="handleDownloads(props.row)">批量下载</el-button>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" class="align-row">
                      <el-col :span="2" class="align-col">序号</el-col>
                      <el-col :span="7" class="align-col">文件名称</el-col>
                      <el-col :span="4" class="align-col">文件类型</el-col>
                      <!-- <el-col :span="4" class="align-col">上传时间</el-col> -->
                      <el-col :span="4" class="align-col">操作</el-col>
                    </el-row>
                    <hr style="border: none; border-top: 1px solid #ddd;">
                    <el-row v-if="props.row.children.length===0" class="align-row">
                      <el-col class="align-cols">暂无数据</el-col>
                    </el-row>

                    <div v-for = "(column,index) in props.row.children" :key="index">
                      <el-row :gutter="20" class="align-row">
                        <el-col :span="2" class="align-col"> {{ index + 1 }} </el-col>
                        <el-col :span="7" class="align-col">
                          <span v-if="!column.edited" :show-overflow-tooltip="true">{{ truncatedName(column.fileName) }}</span>
                          <el-input v-if="column.edited" v-model="column.sourceFileName" clearable />
                        </el-col>
                        <el-col :span="4" class="align-col">
                          <span v-if="!column.edited">{{ getDictName(dicttypeid,column.type) }}</span>
                          <el-select v-if="column.edited" v-model="column.type" filterable placeholder="请选择">
                            <el-option
                              v-for="item in textTeamList"
                              :key="item.dictId"
                              :label="item.dictName"
                              :value="item.dictId"
                            />
                          </el-select>
                        </el-col>
                        <!-- <el-col :span="4" class="align-col"> {{ column.createDate }} </el-col> -->
                        <el-col :span="4" class="align-col">
                          <el-button v-hasFunc="['1362_LICENCE']" v-if="!column.edited" type="text" size="mini" @click="handleEdit(column)">修改</el-button>
                          <el-button v-if="column.edited" type="text" size="mini" @click="handleCancel(column)">取消</el-button>
                          <el-button v-if="column.edited" type="text" size="mini" @click="handleSave(column, props.row)">保存</el-button>
                          <el-button v-hasFunc="['1363_LICENCE']" type="text" size="mini" @click="handleDel(column, props.row)">删除</el-button>
                          <el-button v-hasFunc="['1364_LICENCE']" type="text" size="mini" @click="handleDownload(column)">下载</el-button>
                        </el-col>
                      </el-row>
                      <hr style="border: none; border-top: 1px solid #ddd;">
                    </div>

                  </div>
                </template>
              </el-table-column>
              <el-table-column type="index" label="序号" width="60" align="center"/>
              <el-table-column prop="projectCode" label="研发项目编号" width="120" align="center"/>
              <el-table-column prop="projectName" label="项目名称" width="120" align="center" />
              <el-table-column :formatter="(row,column,cellvalue,index)=>(getDictName('RD_PROJECT_STATE',cellvalue))" prop="projectState" label="项目状态" align="center" width="120" />
              <el-table-column prop="startDate" label="项目开始" width="120" align="center"/>
              <el-table-column prop="endDate" label="项目结束" width="120" align="center"/>
              <el-table-column label="操作" width="80" align="center">
                <template slot-scope="scope">
                  <el-link
                    v-hasFunc="['1361_LICENCE']"
                    :underline="false"
                    type="warning"
                    @click="handleUpdate(scope.row)"
                  >查看</el-link
                  >
                </template>
              </el-table-column>
              <el-table-column label="上传状态" width="80" align="center">
                <template slot-scope="scope">
                  <el-tooltip :content = "scope.row.children.length !== 0 ? '[已完结项目]上传已完成': '[已完结项目]上传未完成' " effect="light" >
                    <el-span :class="potClass(scope.row)" style="margin-right:10px"/>
                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 未完结项目 -->
          <div style="border:1px solid #EBEEF5;padding:3px; margin-top: 10px" >
            <el-button :icon="clickDiolog2 ? 'el-icon-arrow-down' : 'el-icon-arrow-right' " type="text" @click="clickDiologTwo"/> 未完结项目
            <span v-if="unFinDocState !== 3" :class="posClass(1,0)">（上传状态:</span>
            <el-tooltip v-if="unFinDocState !== 3" :content = "unContenTop" effect="light" >
              <span v-if="unFinDocState !== 3" :class="posClass(1,1)"/>
            </el-tooltip>
            <span v-if="unFinDocState !== 3" :class="posClass(1,2)">）</span>
            <el-dialog
              :visible.sync="dialogVisible"
              :before-close="handleClose"
              width="30%"
              title="文件上传"
            >
              <el-form label-width="120px">
                <el-row :gutter="10">
                  <el-col :span="8">
                    <p class="inline-p" style="fontsize: 20px">第一步:选择文件</p><p class="inline-p" style="color: red"> *</p>
                    <p class="smallFont" style="fontsize: 10px; color: red">注:上传文件不要超过30MB</p>
                  </el-col>
                  <el-col :span="8">
                    <el-upload
                      ref="upload"
                      :auto-upload="false"
                      :limit="1"
                      :on-exceed="handleExceed"
                      :action="uploadUrl"
                      :data="fileInfo"
                      :show-file-list="true"
                      :on-success="handleSuccess"
                      :on-error="handleError"
                      :on-change="handleChange"
                      :before-remove="beforeRemove"
                      class="upload-demo"
                    >
                      <el-button type="primary" size="small" @click="handleBefore">点击上传</el-button>
                    </el-upload>
                  </el-col>
                </el-row>
                <div class="space"/>
                <el-row :gutter="20">
                  <el-col :span="10">
                    <p class="inline-p" width="30%">第二步:选择文件类型</p><p class="inline-p" style="color: red"> *</p>
                    <div style="height: 10px;"/>
                    <el-select v-model="fileInfo.type" filterable clearable placeholder="请选择文件类型" @change="changeSelect">
                      <el-option
                        v-for="item in textTeamList"
                        :key="item.dictId"
                        :label="item.dictName"
                        :value="item.dictId"
                      />
                    </el-select>
                  </el-col>
                </el-row>
                <div class="dialog-footer">
                  <el-button type="primary" size="small" @click="uploadSuccess">确认上传</el-button>
                  <el-button @click="dialogCancel">取消</el-button>
                </div>
              </el-form>
            </el-dialog>
            <el-table
              v-loading="loading"
              v-if="clickDiolog2"
              :data="projectData2"
              element-loading-text="数据加载中"
              style="width: 100%;"
            >
              <el-table-column type="expand" width="60">
                <!-- 嵌套列表折叠 -->
                <template slot-scope="props" >
                  <div style="border:1px  solid #EBEEF5; margin: 0 70px; padding:5px 5px 15px 5px; width: 80%">

                    <el-row class="align-rowOther">
                      <el-col :span="3">
                        <el-button v-hasFunc="['1365_LICENCE']" type="primary" size="small" @click="handleExpand(props.row)">上传文件</el-button>
                      </el-col>
                      <el-col :span="3">
                        <el-button v-hasFunc="['1366_LICENCE']" type="primary" size="small" @click="handleDownloads(props.row)">批量下载</el-button>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20" class="align-row">
                      <el-col :span="2" class="align-col">序号</el-col>
                      <el-col :span="7" class="align-col">文件名称</el-col>
                      <el-col :span="4" class="align-col">文件类型</el-col>
                      <!-- <el-col :span="4" class="align-col">上传时间</el-col> -->
                      <el-col :span="4" class="align-col">操作</el-col>
                    </el-row>
                    <hr style="border: none; border-top: 1px solid #ddd;">
                    <el-row v-if="props.row.children.length===0" class="align-row">
                      <el-col class="align-cols">暂无数据</el-col>
                    </el-row>

                    <div v-for = "(column,index) in props.row.children" :key="index">
                      <el-row :gutter="20" class="align-row">
                        <el-col :span="2" class="align-col"> {{ index + 1 }} </el-col>
                        <el-col :span="7" class="align-col">
                          <span v-if="!column.edited" :show-overflow-tooltip="true">{{ truncatedName(column.fileName) }}</span>
                          <el-input v-if="column.edited" v-model="column.sourceFileName" clearable />
                        </el-col>
                        <el-col :span="4" class="align-col">
                          <span v-if="!column.edited">{{ getDictName(dicttypeid,column.type) }}</span>
                          <el-select v-if="column.edited" v-model="column.type" filterable placeholder="请选择">
                            <el-option
                              v-for="item in textTeamList"
                              :key="item.dictId"
                              :label="item.dictName"
                              :value="item.dictId"
                            />
                          </el-select>
                        </el-col>
                        <!-- <el-col :span="4" class="align-col"> {{ column.createDate }} </el-col> -->
                        <el-col :span="4" class="align-col">
                          <el-button v-hasFunc="['1362_LICENCE']" v-if="!column.edited" type="text" size="mini" @click="handleEdit(column)">修改</el-button>
                          <el-button v-if="column.edited" type="text" size="mini" @click="handleCancel(column)">取消</el-button>
                          <el-button v-if="column.edited" type="text" size="mini" @click="handleSave(column, props.row)">保存</el-button>
                          <el-button v-hasFunc="['1363_LICENCE']" type="text" size="mini" @click="handleDel(column, props.row)">删除</el-button>
                          <el-button v-hasFunc="['1364_LICENCE']" type="text" size="mini" @click="handleDownload(column)">下载</el-button>
                        </el-col>
                      </el-row>
                      <hr style="border: none; border-top: 1px solid #ddd;">
                    </div>

                  </div>
                </template>
              </el-table-column>
              <el-table-column type="index" label="序号" width="60" align="center"/>
              <el-table-column prop="projectCode" label="研发项目编号" width="120" align="center"/>
              <el-table-column prop="projectName" label="项目名称" width="120" align="center" />
              <el-table-column :formatter="(row,column,cellvalue,index)=>(getDictName('RD_PROJECT_STATE',cellvalue))" prop="projectState" label="项目状态" align="center" width="120" />
              <el-table-column prop="startDate" label="项目开始" width="120" align="center"/>
              <el-table-column prop="endDate" label="项目结束" width="120" align="center"/>
              <el-table-column label="操作" width="80" align="center">
                <template slot-scope="scope">
                  <el-link
                    v-hasFunc="['1361_LICENCE']"
                    :underline="false"
                    type="warning"
                    @click="handleUpdate(scope.row)"
                  >查看</el-link>
                </template>
              </el-table-column>
              <el-table-column label="上传状态" width="80" align="center">
                <template slot-scope="scope">
                  <el-tooltip :content = "scope.row.children.length !== 0 ? '[未完结项目]上传已完成': '[未完结项目]上传未完成' " effect="light" >
                    <el-span :class="potClass(scope.row)" style="margin-right:10px"/>

                  </el-tooltip>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import RProjectAdministSelect from './rProjectAdministSelect.vue'
import { queryResourceByPerson, documentsChanged, documentsDelete, downloadFile, downloadFiles, renewChildren } from '@/api/researchManager/rprojextmess'
// eslint-disable-next-line
import { getDictName, getTypeDicts } from '@/utils/dict'
import common from '@/api/common'
export default {
  name: 'RProjectAdministration',
  components: {
    RProjectAdministSelect
  },
  data() {
    return {
      fileList: [],
      dicttypeid: 'FILE_TYPE', // 文件类似字典
      finDocState: '',
      contentWord: '',
      contenTop: '上传状态',
      unContenTop: '上传状态',
      unFinDocState: '',
      userData: {},
      showUserData: false,
      projectData: [],
      clickDiolog1: false, // 已完成的项目‘>’状态
      clickDiolog2: false, // 未完成的项目‘>’状态
      projectData1: [], // 已完成的项目集合
      projectData2: [], // 未完成的项目集合
      uploadUrl: process.env.BASE_API + `/researchProAllocation/upload`,
      fileInfo: {},
      rowData: {},
      dialogVisible: false,
      canUpload: true // 是否显示文件上传弹窗

    }
  },

  computed: {

    textTeamList() {
      // debugger
      return getTypeDicts(this.dicttypeid)
    }
  },
  created() {
    // debugger
    if (this.$route.params.userData !== undefined) {
      this.showUserData = true
      this.clickDiolog1 = this.$route.params.clickDiolog1
      this.clickDiolog2 = this.$route.params.clickDiolog2
      this.handleNodeClick(this.$route.params.userData)
    } else {
    //  (this.$route.params !== undefined) {
      console.log(this.$route.params)
      this.handleNodeClick(this.$route.params)
    }
  },

  methods: {
    handleExceed() {
      this.$message.warning('最多只能上传一个文件!')
    },
    handleSuccess(response, file, fileList) {
      this.$refs.upload.clearFiles()
      this.fileList = []
      this.fileInfo = {}
      this.dialogVisible = false
      this.canUpload = true
      this.refrushNewData(this.rowData)
      this.$message({ type: 'success', message: '文件上传成功。' })
    },
    handleError() {
      this.$message({ type: 'error', message: '文件上传失败！' })
      this.fileList = []
    },
    handleChange(file, fileList) {
      const maxSize = 30 * 1024 * 1024
      if (file.size > maxSize) {
        this.$message.error('文件大小超出30MB, 请重新上传!')
        this.$refs.upload.clearFiles()
        return false
      }
      if (fileList.length > 0) {
        const newList = []
        newList.push(file)
        this.fileList = newList
      }
    },
    beforeRemove(file, filList) {
      // this.fileList = filList.filter(item => item.uid !== file.uid)
      const newList = []
      for (var i = 0; i < filList.length; i++) {
        if (filList[i].uid !== file.uid) {
          newList.push(filList[i])
        }
      }
      this.fileList = newList
    },

    submitUpload() {
      if (this.filList) {
        this.$refs.upload.submit()
      } else {
        this.$message.error('请先选择文件!')
      }
    },

    handleNodeClick(itemData) {
      if (itemData === undefined) {
        this.showUserData = false
        this.userData = {}
        return
      }

      if (itemData.badge !== undefined) {
        this.showUserData = true
        this.userData = itemData
        this.refrushData()
      }
    },
    // 状态灯显示
    potClass(data) {
      if (data.projectState === '9') {
        if (data.children.length === 0) {
          return 'el-icon-warning red'
        } else {
          return 'el-icon-success green'
        }
      } else {
        if (data.children.length === 0) {
          return 'el-icon-warning orange'
        } else {
          return 'el-icon-success blue'
        }
      }
    },
    posClass(type, data) {
      if (type === 0) {
        if ((data === 0 || data === 2) && this.finDocState === 0) {
          return 'red'
        } else if (data === 1 && this.finDocState === 0) {
          return 'el-icon-warning red'
        } else if ((data === 0 || data === 2) && this.finDocState === 1) {
          return 'green'
        } else if (data === 1 && this.finDocState === 1) {
          return 'el-icon-success green'
        }
      } else {
        if ((data === 0 || data === 2) && this.unFinDocState === 0) {
          return 'orange'
        } else if (data === 1 && this.unFinDocState === 0) {
          return 'el-icon-warning orange'
        } else if ((data === 0 || data === 2) && this.unFinDocState === 1) {
          return 'blue'
        } else if (data === 1 && this.unFinDocState === 1) {
          return 'el-icon-success blue'
        }
      }
    },
    // 刷新数据
    refrushData() {
      this.getPager(this.size, 1)
    },
    // 分页
    getPager: function(size, index) {
      const params = {}
      params.size = size
      params.index = index
      this.getResource(params)
    },
    getResource(params) {
      this.loading = true
      params.eid = this.userData.eid
      params.badge = this.userData.badge
      queryResourceByPerson(params).then(res => {
        this.loading = false
        if (res.data.status === 200) {
          this.projectData1 = res.data.results.filter(item => item.projectState === '9')
          if (this.projectData1.length === 0) {
            this.finDocState = 3
            this.contenTop = '上传状态'
          } else if (this.projectData1.every(item => item.children.length !== 0)) {
            this.finDocState = 1
            this.contenTop = '[已完结项目]上传已完成'
          } else {
            this.finDocState = 0
            this.contenTop = '[已完结项目]上传未完成'
          }
          this.projectData2 = res.data.results.filter(item => item.projectState === '1' || item.projectState === '2')
          if (this.projectData2.length === 0) {
            this.unFinDocState = 3
            this.unContenTop = '上传状态'
          } else if (this.projectData2.every(item => item.children.length !== 0)) {
            this.unFinDocState = 1
            this.unContenTop = '[未完结项目]上传已完成'
          } else {
            this.unFinDocState = 0
            this.unContenTop = '[未完结项目]上传未完成'
          }
          this.totalNumber = res.data.total
        }
      })
    },
    // 获取序号
    getIndex(row, column, cellvalue, i) {
      return this.size * (this.index - 1) + i + 1
    },
    // >(已完成的项目)点击事件
    clickDiolog() {
      this.clickDiolog1 = !this.clickDiolog1
    },
    // >(未完成的项目)点击事件
    clickDiologTwo() {
      this.clickDiolog2 = !this.clickDiolog2
    },
    // 修改研发项目信息
    handleUpdate(data) {
      const routeData = this.$router.resolve({
        name: 'researchProjectEdit',
        query: {
          isEdit: 0,
          projectParams: JSON.stringify(data)
        }
      })
      window.open(routeData.href, '_blank')

      // this.$router.push({
      //   name: 'researchProjectEdit',
      //   params: {
      //     isEdit: 0,
      //     projectParams: data,
      //     userData: this.userData,
      //     clickDiolog1: this.clickDiolog1,
      //     clickDiolog2: this.clickDiolog2
      //   }
      // })
    },
    // 文件类型选择
    changeSelect() {
      if (this.fileInfo.type === '' || this.fileInfo.type === undefined) {
        this.canUpload = true
      } else {
        this.canUpload = false
      }
    },
    // fileName文件名称只显示前15个字符
    truncatedName(data) {
      return data.length > 15 ? data.slice(0, 15) + '...' : data
    },
    // 文件上传之前
    handleExpand(date) {
      // debugger
      this.rowData = date
      this.dialogVisible = true
    },
    // 弹窗关闭
    handleClose() {
      this.fileInfo = {}
      this.dialogVisible = false
      this.canUpload = true
      this.rowData = {}
      this.fileList = []
      this.$refs.upload.clearFiles()
    },
    // 文件上传
    handleBefore(event, date) {
      console.log(this.rowData)
      this.fileInfo.rdProjectId = this.rowData.pkId
      this.fileInfo.badge = this.rowData.badge
      // if (this.fileInfo.type === '' || this.fileInfo.type === undefined) {
      //   this.$message({ type: 'error', message: '文件类型不能为空' })
      //   return
      // }
      console.log(this.fileInfo.rdProjectId)
    },
    // 文件上传成功
    uploadSuccess() {
      if (this.fileInfo.type === '' || this.fileInfo.type === undefined) {
        this.$message({ type: 'error', message: '文件类型不能为空' })
        return
      }
      if (this.fileList.length > 0) {
        this.$refs.upload.submit()
        this.dialogVisible = false
        this.canUpload = true
        this.fileInfo = {}
      } else {
        this.$message.error('请先选择文件!')
      }
    },
    // 修改
    handleEdit(data) {
      // 去除文件名称类型后缀 修改时不能修改文件类型
      data.sourceFileName = data.fileName.split('.').slice(0, -1).join('.')
      this.$set(data, 'edited', true)
    },
    // 取消
    handleCancel(data) {
      this.$set(data, 'edited', false)
    },
    // 保存 同名加后缀
    handleSave(data, row) {
      this.$set(data, 'edited', false)
      if (data.sourceFileName === '' || data.sourceFileName === undefined || data.type === '' || data.type === undefined) {
        this.$message({ type: 'error', message: '文件名称或文件类型不能为空' })
        // this.refrushData()
        return
      }
      data.fileName = data.sourceFileName + '.' + data.fileName.split('.').pop()
      documentsChanged(data).then(res => {
        if (res.data.status === 200) {
          this.$message({ type: 'success', message: '保存成功！' })
          this.refrushNewData(row)
        } else {
          this.$message({ type: 'error', message: '保存失败！' })
        }
      })
    },
    // 删除
    handleDel(data, row) {
      this.$confirm('是否删除' + data.fileName, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        documentsDelete(data).then(res => {
          if (res.data.status === 200) {
            this.$message({ type: 'success', message: '删除成功！' })
            this.refrushNewData(row)
          } else {
            this.$message({ type: 'error', message: '删除失败！' })
          }
        })
      })
    },
    // 更新行children值
    refrushNewData(row) {
      // row.children = row.children.slice(1)
      console.log(row)
      renewChildren({ pkId: row.pkId, badge: row.badge }).then(res => {
        if (res.data.status === 200) {
          row.children = res.data.results
        } else {
          this.$message({ type: 'error', message: '更新行文件失败！' })
        }
      })
    },
    // 下载
    handleDownload(data) {
      downloadFile({ pkId: data.pkId, _isDown: 'Y' }).then(res => {
        common.download(res.data, self, data.fileName)
      })
    },
    // 批量下载
    handleDownloads(data) {
      downloadFiles({ pkId: data.pkId, belongUser: data.badge, _isDown: 'Y' }).then(res => {
        common.download(res.data, self, 'output.zip')
      })
      // data.children.forEach((item, idex) => {
      //   console.log(item)
      //   downloadFile({ pkId: item.pkId, _isDown: 'Y' }).then(res => {
      //     common.download(res.data, self, item.fileName)
      //   })
      // }
      // )
    },

    /**
     * 取消按钮,关闭dialog
     */
    dialogCancel: function() {
      this.fileList = []
      this.fileInfo = {}
      this.rowData = {}
      // this.fileInfo.type = null
      this.dialogVisible = false
      this.$refs.upload.clearFiles()
    }

  }
}
</script>

<style scoped>
.smallFont{
  font-size: 12px;
}
.space {
  height: 30px;
}
.inline-p {
  display: inline;
  margin: 0;
}
.transfer-panel-header span:last-child {
  position: absolute;
  right: 15px;
}
.dialog-footer {
    margin-top: 10px;
    text-align: right;
}
.typeset-container {
  padding: 20px;
}

.user-data{
    background: white;
    padding: 10px 20px;
}
.align-row{
  display: flex;
  align-items: center;
}
.align-rowOther{
  display: flex;
  align-items: center;
  margin-bottom: 10px
}
.align-col{
  display: flex;
  align-items: center;
  justify-content: center
}
.align-cols{
  color: #7a7777e1;
  display: flex;
  align-items: center;
  justify-content: center
}

/* .name-container{
  max-width: 190px;
  overflow: hidden;
  white-space: nowrap;
} */
</style>
