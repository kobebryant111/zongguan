<!--
 * @Author: chenhuocan
 * @Date: 2020-03-30 18:19:20
 * @LastEditors: amberself
 * @LastEditTime: 2020-08-04 14:17:46
 * @Description: 数据字典
 -->
<template>
  <div class="page-container">
    <el-form ref="paramForm" :inline="true" :model="searchForm" class="search-container">
      <el-form-item prop="dctTpNm" label="类型名称">
        <el-input v-model="searchForm.dctTpNm" placeholder="请输入类型名称" />
      </el-form-item>
      <el-form-item prop="dctTpCdLike" label="字典类型">
        <el-input v-model="searchForm.dctTpCdLike" placeholder="请输入字典类型" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="searchClick">查询</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="refreshClick">重置</el-button>
    </el-form>
    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="addClick"> 新增</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="modify">修改</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="deleteClick">删除</el-button>
      <el-button type="primary" icon="el-icon-document-copy" @click="copyClick">复制</el-button>
    </el-button-group>

    <ts-multi-table @row-click="selectClick" ref="paramListTable" :query="searchForm" :options="tableOptions">
      <el-table-column prop="dctKey" label="字典键" ></el-table-column>
      <el-table-column prop="dctTpCd" label="字典类型" ></el-table-column>
      <el-table-column prop="dctTpNm" label="字典类型名称" ></el-table-column>
      <el-table-column prop="dctVal" label="字典值" ></el-table-column>
      <el-table-column prop="dctSeq" label="排序" ></el-table-column>
      <el-table-column prop="dctDsc" label="描述" ></el-table-column>
      <el-table-column prop="stCd" label="状态" >
        <template slot-scope="scope">
          {{scope.row.stCd | tsConvert('StCd')}}
        </template>
      </el-table-column>
    </ts-multi-table>
    <ts-multi-dialog :visible.sync="visible" :options="dialogOptions"></ts-multi-dialog>
	</div>
</template>

<script>
import FormContent from './data-page'
export default {
  name: 'Index',
  components: { },
  data() {
    return {
      tableOptions: {
        type: 'radio',
        apiName: this.$api.dictList,
        paramsOrdata: 'data'
      },
      // 搜索
      searchForm: {
        dctTpCdLike: '',
        dctTpNm: ''
      },
      selected: false,
      selectData: '',
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      formLabelWidth: '100px',
      formLabelWidth1: '90px',
      dialogOptions: {
        disabled: false,
        data: {},
        title: '新增',
        resource: FormContent,
        callbacks: [null, null]
      }
    }
  },
  methods: {
    selectClick() {
      // 行点击
      const result = this.$refs['paramListTable'].getRadioData()
      if (result) {
        this.selected = true
        this.selectData = result
      }
    },
    searchClick() {
      // 查询
      this.$refs['paramListTable'].serchHandel()
    },
    refreshClick() {
      // 重置
      this.searchForm = {}
    },
    addClick() {
      // 新增
      this.dialogOptions.title = '新增'
      this.visible = true
      this.dialogOptions.data = {}
    },
    modify() {
      const self = this
      const result = this.$refs['paramListTable'].getRadioData()
      this.dialogOptions.title = '修改'
      if (result) {
        this.dialogOptions.data = Object.assign({}, this.selectData)
        this.visible = true
      } else {
        self.$alert('请选择有效数据', '信息', { confirmButtonText: '确定' })
      }
    },
    deleteClick() {
      // 删除
      const result = this.$refs['paramListTable'].getRadioData()
      if (result) {
        this.$confirm('删除该记录，删除后不可恢复，是否继续?').then(() => {
          const param = { id: this.selectData.id }
          this.$request({
            url: this.$api.dictDelete,
            data: param
          }).then((response) => {
            this.$message.success(response.description)
            this.$refs['paramListTable'].refreshDataSource()
          })
        }).catch(() => {})
      } else {
        this.$alert('请选中一条记录')
      }
    },
    copyClick() {
      // 复制
      const self = this
      const result = this.$refs['paramListTable'].getRadioData()
      this.dialogOptions.title = '复制'
      if (result) {
        this.visible = true
        const modelVariable = Object.assign({}, this.dialogOptions.data)
        for (const key in this.dialogOptions.data) {
          if (this.selectData[key]) {
            modelVariable[key] = this.selectData[key]
          }
        }
        this.dialogOptions.data = Object.assign({}, modelVariable)
      } else {
        self.$alert('请选择有效数据', '信息', { confirmButtonText: '确定' })
      }
    },
    cancel(data, done) {
      done()
    },
    submit(data, done) {
      this.$request({
        url: this.$api.dictSave,
        data: Object.assign({}, data)
      }).then((response) => {
        this.$message.success(response.description)
        this.$refs['paramListTable'].serchHandel()
        done()
      })
    }
  }
}
</script>

