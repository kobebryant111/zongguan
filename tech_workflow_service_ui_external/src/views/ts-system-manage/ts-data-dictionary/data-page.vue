<!--
 * @Author: Evan-lian
 * @Date: 2020-06-04 15:16:53
 * @LastEditors: amberself
 * @LastEditTime: 2020-08-04 17:40:21
 * @Description:
 -->
<template>
	<div class="content">
		<el-form ref="ruleForm" :model="model" :disabled="options && options.disabled">
			<el-row>
				<el-col :span="12">
					<el-form-item prop="dctTpNm" label="字典类型名称" :label-width="formLabelWidth">
						<el-input v-model="model.dctTpNm" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item prop="dctTpCd" label="字典类型" :label-width="formLabelWidth1">
						<el-input v-model="model.dctTpCd" />
					</el-form-item>
				</el-col>
			</el-row>

			<el-row>
				<el-col :span="12">
					<el-form-item prop="dctKey" label="字典键" :label-width="formLabelWidth">
						<el-input v-model="model.dctKey" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item prop="dctVal" label="字典值" :label-width="formLabelWidth1">
						<el-input v-model="model.dctVal" />
					</el-form-item>
				</el-col>
			</el-row>

			<el-row>
				<el-col :span="12">
					<el-form-item prop="dctValNm" label="字典值名称" :label-width="formLabelWidth">
						<el-input v-model="model.dctValNm" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item prop="dctGrp" label="字典组" :label-width="formLabelWidth1">
						<el-input v-model="model.dctGrp" />
					</el-form-item>
				</el-col>
			</el-row>

			<el-row>
				<el-col :span="12">
					<el-form-item prop="dctSeq" label="排序" :label-width="formLabelWidth">
						<el-input v-model="model.dctSeq" />
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item prop="dctDsc" label="描述" :label-width="formLabelWidth1">
						<el-input v-model="model.dctDsc" />
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>
	</div>

</template>

<script>
export default {
  name: 'Form',
  components: {},
  props: {
    options: {
      type: Object
    }
  },
  data() {
    return {
      formLabelWidth: '100px',
      formLabelWidth1: '90px',
      // 表单
      model: {
        dctTpNm: '',
        dctKey: '',
        dctTpCd: '',
        dctVal: '',
        dctValNm: '',
        dctGrp: '',
        dctSeq: '',
        dctDsc: ''
      }
    }
  },
  created() {
    const selectDataJson = localStorage.getItem('detailqueryObject')
    const modelquery = JSON.parse(selectDataJson)
    if (modelquery.type === 'change') {
      this.model = Object.assign({}, modelquery.foramData)
    }
    if (modelquery.type === 'copy') {
      const modelVariable = Object.assign({}, this.model)
      for (const key in this.model) {
        if (modelquery.foramData[key]) {
          modelVariable[key] = modelquery.foramData[key]
        }
      }
      this.model = Object.assign({}, modelVariable)
	    console.log('this', this.model)
    }
  },
  mounted() {
  },
  methods: {

  }
}
</script>

