<!--
 * @Author: chenhuocan
 * @Date: 2020-03-30 18:32:52
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-23 17:15:13
 * @Description: 参数管理
 -->

<template>
  <div class="page-container">
    <el-form ref="paramForm" :inline="true" :model="searchForm" class="search-container">
      <el-form-item prop="parmIdLike" label="参数编码">
        <el-input v-model="searchForm.parmIdLike" placeholder="请输入参数编码" />
      </el-form-item>
      <el-form-item prop="parmTpNm" label="类型名称">
        <el-input v-model="searchForm.parmTpNm" placeholder="请输入类型名称" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="searchClick">查询</el-button>
      <el-button type="primary" icon="el-icon-refresh" @click="refreshClick">重置</el-button>
    </el-form>

    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" v-ts-permission="'param.add'" @click="addClick"> 新增</el-button>
      <el-button type="primary" icon="el-icon-edit" v-ts-permission="'param.edit'" @click="modify">修改</el-button>
      <el-button type="primary" icon="el-icon-delete" v-ts-permission="permission" @click="deleteClick">删除</el-button>
    </el-button-group>

    <ts-multi-table @row-click="selectClick" ref="paramListTable" :query="searchForm" :options="tableOptions">
      <el-table-column prop="parmTpCd" label="参数类型" ></el-table-column>
      <el-table-column prop="parmTpNm" label="类型名称" ></el-table-column>
      <el-table-column prop="parmId" label="参数编号" ></el-table-column>
      <el-table-column prop="parval" label="参数值" ></el-table-column>
      <el-table-column prop="parmDsc" label="参数描述" ></el-table-column>
      <el-table-column prop="wthrCchCd" label="是否缓存" >
        <template v-slot="scope">
          <span>{{ scope.row.wthrCchCd === '1' ? '是' : '否' }}</span>
        </template>
      </el-table-column>
    </ts-multi-table>
    <ts-multi-dialog :visible.sync="visible" :options="options"></ts-multi-dialog>

	</div>

</template>

<script>
import FormContent from './form'

export default {
  name: 'Index',
  data() {
    return {
      permission: 'param.del',
      tableOptions: {
      	type: 'radio',
        apiName: this.$api.paramList,
        paramsOrdata: 'data'
      },
      // 搜索
      searchForm: {
        parmIdLike: '',
        parmTpNm: ''
      },
      selected: false,
      selectData: '',
      // 表单数据
      visible: false, // [必填] 是否显示Dialog，必须添加.sync修饰符
      options: {
        title: '新增', // [必填]
        width: '30%', // [可不填] 宽度设置百分比或具体px均可，默认值50%，高度不用填会自适应
        beforeClose: (data, done) => { done() }, // [可不填] 关闭右上角弹框前的回调
        resource: FormContent, // [必填] 在弹框中展示的组件页面
        data: {
          parmTpCd: '',
          parmTpNm: '',
          parmId: '',
          parval: '',
          wthrCchCd: '',
          parmDsc: '',
          isModify: false
        },
        callbacks: [this.cancel, this.submit] // [必填] 若取消按钮没有回调，可写默认值null 如[null, this.submit]
      }
    }
  },
  created() {

  },
  methods: {
    selectClick() {
      // 行点击
      const result = this.$refs['paramListTable'].getRadioData()
      console.log('selection:', result)
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
      this.searchForm = { parmIdLike: '', parmTpNm: '' }
      this.$refs['paramListTable'].resetData()
    },
    addClick() {
      // 新增
      this.visible = true
      this.options.data = {}
    },
    modify() {
      const self = this
      const result = this.$refs['paramListTable'].getRadioData()
      if (result) {
        self.visible = true
        this.options.data = this.selectData
        this.options.data.isModify = true
      } else {
        self.$alert('请选择有效数据', '信息')
      }
    },
    deleteClick() {
      // 删除
      if (this.selected) {
        this.$confirm('删除该记录，删除后不可恢复，是否继续?').then(() => {
          const param = { id: this.selectData.id }
          this.$request({
            url: this.$api.paramDelete,
            data: param
          }).then((response) => {
            this.$message.success(response.description)
            this.$refs['paramListTable'].serchHandel()
          })
        }).catch(() => {})
      } else {
        this.$alert('请选中一条记录')
      }
    },
    cancel(data, done) {
      done()
    },
    submit(data, done) {
      this.$request({
        url: this.$api.paramSave,
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

