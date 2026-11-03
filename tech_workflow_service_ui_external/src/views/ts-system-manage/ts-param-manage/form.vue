<template>
	<div>
		<el-form class="content" ref="ParmForm" :model="model" :rules="rules" label-width="120px">
			<el-form-item prop="parmTpCd" label="参数类型">
				<el-input v-model="model.parmTpCd" :disabled="model.isModify" />
			</el-form-item>
			<el-form-item prop="parmTpNm" label="类型名称">
				<el-input v-model="model.parmTpNm"/>
			</el-form-item>
			<el-form-item prop="parmId" label="参数编码">
				<el-input v-model="model.parmId" :disabled="model.isModify"/>
			</el-form-item>
			<el-form-item prop="parval" label="参数值">
				<el-input v-model="model.parval" />
			</el-form-item>
			<el-form-item prop="wthrCchCd" label="是否缓存">
				<ts-multi-select v-model="model.wthrCchCd" placeholder="请选择" :params="wthrCchCds"></ts-multi-select>
			</el-form-item>
			<el-form-item prop="parmDsc" label="描述">
				<el-input v-model="model.parmDsc" />
			</el-form-item>

		</el-form>
	</div>
</template>

<script>
export default {
  name: 'Form',
  components: {},
  data() {
    return {
      wthrCchCds: {
        width: '100%',
        options: [{ text: '是', value: '1' }, { text: '否', value: '2' }]
      },
      // 表单
      model: {
        parmTpCd: '',
        parmTpNm: '',
        parmId: '',
        parval: '',
        wthrCchCd: '',
        parmDsc: '',
        id: '',
        isModify: false
      },
      rules: {
        parmTpCd: [
          { required: true, message: '请输入参数类型', trigger: 'blur' }
        ],
        parmTpNm: [
          { required: true, message: '请输入类型名称', trigger: 'blur' }
        ],
        parmId: [
          { required: true, message: '请输入参数编码', trigger: 'blur' }
        ],
        parval: [
          { required: true, message: '请输入参数值', trigger: 'blur' }
        ],
        wthrCchCd: [
          { required: true, message: '请选择是否缓存', trigger: 'change' }
        ],
        parmDsc: [
          { required: true, message: '请输入描述', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {}
}
</script>

<style lang=scss scoped>
	.content{
		::v-deep .el-form-item__label {
			color: #1a1a1a;
			font-weight: 400;
		}
		::v-deep 	.el-table td{
			padding: 5px 0 5px 0;
		}
		::v-deep .el-table {
			color: #333333;
			font-size: 14px;
		}
		::v-deep .el-table .F7F7F7 {
			background: #F7F7F7;
		}

		::v-deep .el-table .FFFFFF {
			background: #FFFFFF;
		}

	}

</style>
