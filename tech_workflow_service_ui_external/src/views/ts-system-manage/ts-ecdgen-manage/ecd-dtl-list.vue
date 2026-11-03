<template>
  <div>
    <el-button-group class="handle-container" v-show="showBtn">
      <el-button type="primary" icon="el-icon-plus" size="small" @click="addEcdDlt">新增</el-button>
      <el-button type="primary" icon="el-icon-edit" size="small" @click="modifyEcdDlt">修改</el-button>
      <el-button type="primary" icon="el-icon-delete" size="small" @click="deleteEcdDlt">删除</el-button>
    </el-button-group>
    <ts-multi-table
        ref="ecdDtlTable"
        :options="tableOptions">
      <el-table-column prop="ecdDtlId" label="编码明细编号"></el-table-column>
      <el-table-column prop="ecdTpId" label="编码类型编号"></el-table-column>
      <el-table-column prop="ruleSrc" label="编码生成规则编号">
        <template v-slot="scope">
          <span>{{ scope.row.ruleSrc | tsConvert('EcdRuleSrc')}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sn" label="序号"></el-table-column>
      <el-table-column prop="ecdVal" label="编码值"></el-table-column>
      <el-table-column prop="ecdLen" label="编码长度"></el-table-column>
      <el-table-column prop="stCd" label="状态">
        <template v-slot="scope">
          <span>{{ scope.row.stCd | tsConvert('StCd')}}</span>
        </template>
      </el-table-column>
    </ts-multi-table>

    <div>
      <el-button type="primary" icon="el-icon-close" size="small" @click="back">返回</el-button>
    </div>
    <ts-multi-dialog
        :visible.sync="dialogVisible"
        :options="dialogOptions">
    </ts-multi-dialog>

  </div>
</template>

<script>
import EcdDtlForm from './ecd-dtl-form'
export default {
  name: 'EcdDtlList',
  components: {},
  props: {
    params: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      showBtn: true,
      selected: false,
      selectData: {},
      // 表格属性设置
      tableOptions: {
        type: 'radio',
        apiName: this.$api.encodeDtlList,
        paramsOrdata: 'data',
        apiRequiredParams: {
          ecdTpId: ''
        }
      },
      // 表单
      dialogVisible: false,
      dialogOptions: {
        title: '新增',
        width: '850px',
        resource: EcdDtlForm, // [必填] 在弹框中展示的组件页面
        data: {
          ecdDtlId: '', // 编码明细编号
          ecdTpId: '', // 编码类型编号
          ruleSrc: '', // 编码生成规则
          sn: '', // 序号
          ecdVal: '', // 编码值
          ecdLen: '', // 编码长度
          stCd: '', // 状态
          id: ''
        },
        beforeClose: (data, done) => { done() },
        callbacks: [(data, done) => { done() }, this.confirm]
      }
    }
  },
  created() {
    this.showBtn = this.params.stCd !== '1'
    this.tableOptions.apiRequiredParams.ecdTpId = this.params.ecdTpId
  },
  methods: {
    selectClick() {
      const result = this.$refs['ecdDtlTable'].getRadioData()
      if (result) {
        this.selected = true
        this.selectData = result
      }
    },
    getEncodeDtlList() {
      // 查询
      this.$refs['ecdDtlTable'].refreshDataSource()
    },
    getEncodeDtl() {
      // 获取编码明细编号
      this.$request({
        url: this.$api.getEncodeDtl,
        data: { ecdTpId: this.params.ecdTpId },
        paramMode: 'data'
      }).then((response) => {
        this.dialogOptions.data.id = ''
        this.dialogOptions.data.ecdDtlId = response.data.ecdDtlId
        this.dialogOptions.data.ecdTpId = this.params.ecdTpId
        this.dialogVisible = true
      })
    },
    confirm(data, done) {
      this.$request({
        url: this.$api.saveEncodeDtl,
        data: data,
        paramMode: 'data'
      }).then((response) => {
        this.$message.success(response.description)
        this.dialogVisible = false
        done()
        this.getEncodeDtlList()
      })
    },
    addEcdDlt() {
      // 新增
      this.getEncodeDtl()
    },
    modifyEcdDlt() {
      // 修改
      this.selectData = this.$refs['ecdDtlTable'].getRadioData()
      if (this.selectData) {
        this.dialogVisible = true

        this.dialogOptions.data.ecdDtlId = this.selectData.ecdDtlId
        this.dialogOptions.data.ecdTpId = this.selectData.ecdTpId
        this.dialogOptions.data.ruleSrc = this.selectData.ruleSrc
        this.dialogOptions.data.sn = this.selectData.sn
        this.dialogOptions.data.ecdVal = this.selectData.ecdVal
        this.dialogOptions.data.ecdLen = this.selectData.ecdLen
        this.dialogOptions.data.stCd = this.selectData.stCd
        this.dialogOptions.data.id = this.selectData.id
      } else {
        this.$alert('请选中一条记录')
      }
    },
    deleteEcdDlt() {
      // 删除
      this.selectData = this.$refs['ecdDtlTable'].getRadioData()
      if (this.selectData) {
        this.$confirm('您确定删除这条记录吗?', '提示').then(() => {
          this.$request({
            url: this.$api.deleteEncodeDtl,
            data: { id: this.selectData.id },
            paramMode: 'data'
          }).then((response) => {
            this.$message.success(response.description)
            this.getEncodeDtlList()
          })
        }).catch(() => {
        })
      } else {
        this.$alert('请选中一条记录')
      }
    },
    back() {
      this.$emit('back')
    }
  }
}
</script>

