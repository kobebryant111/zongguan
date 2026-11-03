<!--
 * @Author: liuyang
 * @Date: 2020-04-30 14:22
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-20 17:24:12
 * @Description: 节假日管理
-->
<template>
  <div class="page-container">
      <el-form ref="searchFormRef" v-model="searchForm" :inline="true" size="small" class="search-container">
        <el-form-item label="节假日日期">
          <ts-multi-date-picker v-model="searchForm.holDt" :params="dateParams"></ts-multi-date-picker>
        </el-form-item>
        <el-form-item label="设定状态">
          <ts-multi-select v-model="searchForm.setSt" :params="stateType"></ts-multi-select>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
      </el-form>

    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="importHol">导入节假日</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="setHol">设定节假日</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="deleteHol">删除</el-button>
    </el-button-group>

    <!--列表-->
    <ts-multi-table ref="holTable" :query="searchForm" :options="tableOptions" @reset-query="refreshQuery">
      <el-table-column prop="holDt" label="节假日日期">
        <template slot-scope="scope">
          {{scope.row.holDt | tsDate}}
        </template>
      </el-table-column>
      <el-table-column prop="statusCd" label="设定状态">
        <template slot-scope="scope">
          {{scope.row.setSt | tsConvert('StCd')}}
        </template>
      </el-table-column>
      <el-table-column prop="holDsc" label="节假日描述"></el-table-column>
    </ts-multi-table>

    <el-dialog
        title="导入节假日"
        :visible.sync="importDialogVisible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="400px"
    >
      <ts-multi-upload ref="upload" :params="fileParams">
        <!--   样式自定   -->
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 15px;" size="small" type="primary" @click="submitUpload">导入</el-button>
      </ts-multi-upload>
      <el-link target="_blank" :href="fileUrl" :underline="false" style="margin-top: 15px">
        <el-button type="primary" size="small">导入模板下载</el-button>
      </el-link>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      fileUrl: this.$api.holMgtXlsx,
      importDialogVisible: false,
      dateParams: { clearable: true, size: 'small', valueFormat: 'yyyy-MM-dd' },
      stateType: { key: 'StCd', group: '1', size: 'small' },
      searchForm: {
        setSt: '',
        holDt: ''
      },
      tableOptions: {
        type: 'checkbox',
        apiName: this.$api.holMgtList
      },
      fileParams: {
        action: process.env.VUE_APP_BASE_API + this.$api.holMgtImport, // 非必填 默认系统文件上传地址
        exts: 'xlsx', // 限制的文件内容
        name: 'importFile', // 上传的文件字段名
        size: 1024, // 上传大小限制,默认1024
        autoUpload: false,
        showFileList: true,
        limit: 1,
        // 上传成功回调
        onSuccess: (response, file, fileList) => {
          this.$message.success('导入成功')
          this.importDialogVisible = false
          this.$refs['holTable'].serchHandel()
        },
        // 上传失败回调
        onError: (err, file, fileList) => {
          this.$message.error('导入失败')
        }
      }
    }
  },
  created() {

  },
  methods: {
    search() {
      this.$refs['holTable'].serchHandel()
    },
    resetSearchFrm() {
      this.searchForm = {}
    },
    refreshQuery(query) {
      this.searchForm = query
    },
    importHol() {
      // 导入节假日
      this.importDialogVisible = true
    },
    setHol() {
      // 设定节假日
      const selectData = this.$refs.holTable.getSelectedData()
      const params = {
        holMgtList: JSON.stringify(selectData)
      }
      if (selectData.length > 0) {
        this.$request({
          url: this.$api.holMgtSet,
          data: params
        }).then((response) => {
          this.$message.success(response.description)
          this.$refs['holTable'].serchHandel()
        })
      } else {
        this.$message.warning('请至少选择一条数据')
      }
    },
    deleteHol() {
      // 删除
      const selectData = this.$refs.holTable.getSelectedData()
      const params = {
        holMgtList: JSON.stringify(selectData)
      }
      if (selectData.length > 0) {
        this.$request({
          url: this.$api.holMgtDelete,
          data: params
        }).then((response) => {
          this.$message.success(response.description)
          this.$refs['holTable'].serchHandel()
        })
      } else {
        this.$message.warning('请至少选择一条数据')
      }
    },
    submitUpload() {
      this.$refs.upload.submit()
    }
  }
}
</script>

<style scoped>

</style>
