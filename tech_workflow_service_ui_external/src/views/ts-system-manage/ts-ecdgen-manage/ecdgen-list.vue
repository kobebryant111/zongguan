<template>
  <div>
    <el-form ref="searchForm" v-model="searchForm" :inline="true" size="small" class="search-container">
      <el-form-item prop="ecdTpId" label="编码类型编号">
        <el-input v-model="searchForm.ecdTpId" placeholder="请输入编码类型编号"/>
      </el-form-item>
      <el-form-item prop="ecdTpNm" label="编码类型名称">
        <el-input v-model="searchForm.ecdTpNm" placeholder="请输入编码类型名称"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="resetSearchFrm">重置</el-button>
      </el-form-item>
    </el-form>

    <el-button-group class="handle-container">
      <el-button type="primary" icon="el-icon-plus" @click="addEcd">新增</el-button>
      <el-button v-show="!isValid" type="primary" icon="el-icon-edit" @click="modifyEcd">修改</el-button>
      <el-button v-show="!isValid" type="primary" icon="el-icon-delete" @click="deleteEcd">删除</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="maintain">维护明细</el-button>
      <el-button
        type="primary"
        :icon="isValid?'el-icon-close':'el-icon-check'"
        @click="changeValid"
      >{{ isValid? '失效':'生效' }}
      </el-button>
    </el-button-group>
    <ts-multi-table
        @row-click="selectClick"
        ref="ecdgenListTable"
        :query="searchForm"
        :options="tableOptions">
      <el-table-column prop="ecdTpId" label="编码类型编号"></el-table-column>
      <el-table-column prop="ecdTpNm" label="编码类型名称"></el-table-column>
      <el-table-column prop="rmrk" label="备注"></el-table-column>
      <el-table-column prop="stCd" label="状态">
        <template v-slot="scope">
          <span>{{ scope.row.stCd | tsConvert('StCd')}}</span>
        </template>
      </el-table-column>
    </ts-multi-table>
    <ts-multi-dialog
        :visible.sync="dialogVisible"
        :options="dialogOptions">
    </ts-multi-dialog>
  </div>
</template>

<script>
import EcdgenForm from './ecdgen-form'

export default {
  name: 'EcdgenList',
  components: {},
  data() {
    return {
      dialogVisible: false,
      dialogOptions: {
        title: '新增',
        resource: EcdgenForm, // [必填] 在弹框中展示的组件页面
        data: {
          id: '',
          ecdTpId: '',
          ecdTpNm: '',
          stCd: '',
          rmrk: '',
          formDisabled: false
        },
        beforeClose: (data, done) => { done() },
        callbacks: [(data, done) => { done() }, this.confirm]
      },
      tableOptions: {
        type: 'radio',
        apiName: this.$api.encodeTypeList,
        paramsOrdata: 'data'
      },
      isValid: false,
      selected: false,
      selectData: {},
      // 表单
      ecdgenForm: {
        ecdTpId: '', // 编码类型编号
        ecdTpNm: '', // 编码类型名称
        rmrk: '', // 备注
        stCd: ''// 状态 1 生效 2 失效
      },
      // 搜索
      searchForm: {
        ecdTpNm: '',
        ecdTpId: ''
      }
    }
  },
  created() {
  },
  methods: {
    // 列表单选
    selectClick() {
      const result = this.$refs['ecdgenListTable'].getRadioData()
      if (result) {
        this.selected = true
        this.selectData = result
        this.isValid = this.selectData.stCd === '1'
      }
    },
    // 用户搜索
    search() {
      this.selected = false // 取消选中
      this.$refs['ecdgenListTable'].serchHandel()
    },
    resetSearchFrm() {
      this.searchForm = {}
    },
    confirm(data, done) {
      // 校验编号是否重复
      this.$request({
        url: this.$api.checkEncodeTp,
        data: data,
        paramMode: 'data'
      }).then((res) => {
        if (res.status === 200) {
          // 保存信息
          this.$request({
            url: this.$api.saveEncodeTp,
            data: data,
            paramMode: 'data'
          }).then((response) => {
            this.$message.success(response.description)
            this.$refs['ecdgenListTable'].getDataSource()
            done()
          })
        }
      })
    },
    addEcd() {
      // 新增
      this.dialogOptions.data = {
        id: '',
        ecdTpId: '',
        ecdTpNm: '',
        stCd: '',
        rmrk: '',
        formDisabled: false
      }
      this.dialogVisible = true
    },
    modifyEcd() {
      // 修改
      if (this.selected) {
        const result = this.$refs['ecdgenListTable'].getRadioData()

        this.dialogOptions.data.id = result.id
        this.dialogOptions.data.ecdTpId = result.ecdTpId
        this.dialogOptions.data.ecdTpNm = result.ecdTpNm
        this.dialogOptions.data.stCd = result.stCd
        this.dialogOptions.data.rmrk = result.rmrk
        this.dialogOptions.data.formDisabled = true
        this.dialogVisible = true
      } else {
        this.$alert('请选中一条记录')
      }
    },
    deleteEcd() {
      // 删除
      if (this.selected) {
        this.$confirm('删除该记录，会联动删除明细记录，删除后不可恢复，是否继续?', '提示').then(() => {
          this.$request({
            url: this.$api.deleteEncodeTp,
            data: { id: this.selectData.id },
            paramMode: 'data'
          }).then((response) => {
            this.$message.success(response.description)
            this.$refs['ecdgenListTable'].getDataSource()
          })
        }).catch(() => {
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },
    changeValid() {
      // 生效 失效
      if (this.selected) {
        if (this.isValid) {
          this.$confirm('确定失效该记录吗?', '提示').then(() => {
            this.$request({
              url: this.$api.ecdgenInvalid,
              data: { id: this.selectData.id },
              paramMode: 'data'
            }).then((response) => {
              this.$message.success(response.description)
              this.$refs['ecdgenListTable'].getDataSource()
            })
          }).catch(() => {
          })
        } else {
          this.$confirm('确定生效该记录吗?', '提示').then(() => {
            this.$request({
              url: this.$api.ecdgenValid,
              data: { id: this.selectData.id },
              paramMode: 'data'
            }).then((response) => {
              this.$message.success(response.description)
              this.$refs['ecdgenListTable'].getDataSource()
            })
          }).catch(() => {
          })
        }
      } else {
        this.$alert('请选中一条记录')
      }
    },
    maintain() {
      if (this.selected) {
        this.$emit('maintain', {
          id: this.selectData.id,
          ecdTpId: this.selectData.ecdTpId,
          stCd: this.selectData.stCd
        })
      } else {
        this.$alert('请选中一条记录')
      }
    }
  }
}
</script>

<style scoped>

</style>
