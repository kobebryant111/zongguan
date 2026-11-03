/*
@Author: chenhuocan
@Date: 2020-07-06 10:12:02
@LastEditors:
@LastEditTime:
@Description: 数据权限模型管理
*/

<template>
  <div class="page-container">
    <el-form  ref="paramForm" :inline="true" :model="searchForm" class="search-container" size="small">
      <el-form-item prop="modelName" label="数据权限名称:">
        <el-input v-model="searchForm.modelName" placeholder="请输入数据权限名称" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="searchClick">查询</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="refreshClick">重置</el-button>
    </el-form>

    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="addClick"> 新增</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="modify">修改</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="deleteClick">删除</el-button>
    </el-button-group>

    <ts-multi-table @row-click="selectClick" ref="paramListTable" :query="searchForm" :options="tableOptions">
      <el-table-column prop="modelCode" label="权限类型编号" ></el-table-column>
      <el-table-column prop="modelName" label="数据权限名称" ></el-table-column>
      <el-table-column prop="modelId" label="数据权限类型" ></el-table-column>
      <el-table-column prop="remark" label="备注" ></el-table-column>
    </ts-multi-table>
	</div>
</template>

<script>
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      tableOptions: {
        type: 'radio',
        apiName: this.$api.queryAuthModel,
        paramsOrdata: 'data'
      },
      // 搜索
      searchForm: {
        modelName: ''
      },
      selected: false,
      selectData: ''
    }
  },
  created() {
  },
  mounted() {
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
      this.$tsVue.tansunPushNoAddTag(this, '/dataAuthortyModelManage/add')
    },
    modify() {
      const self = this
      const result = this.$refs['paramListTable'].getRadioData()
      if (result) {
        const selectDataJson = JSON.stringify(this.selectData)
        localStorage.setItem('tsDataAuthortyModelManage', selectDataJson)// 用于对象传给详情页，避免地址栏过多参数
        const selectData = { type: 'change' }
        this.$tsVue.tansunPushNoAddTag(this, '/dataAuthortyModelManage/add', selectData)
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
            url: this.$api.delAuthModel,
            data: param
          }).then((response) => {
            this.$message.success(response.description)
            this.$refs['paramListTable'].refreshDataSource()
          })
        }).catch(() => {})
      } else {
        this.$alert('请选中一条记录')
      }
    }
  }
}
</script>

