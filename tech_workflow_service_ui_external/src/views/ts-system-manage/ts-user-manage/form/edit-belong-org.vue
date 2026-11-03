<template>
  <div>
    <el-form ref="formRef" :model="model" :rules="rules" label-width="150px">

      <el-form-item label="机构名称：" prop="orgName">
        <ts-multi-select v-model="model.orgName" :params="selectParam" @change="selectOrg"></ts-multi-select>
      </el-form-item>

      <el-form-item label="机构用户关联状态：" prop="statusCd">
        <ts-multi-select v-model="model.statusCd" :params="StCdType"></ts-multi-select>
      </el-form-item>

      <el-form-item label="是否默认机构：" prop="defaultInd">
        <ts-multi-select v-model="model.defaultInd" :params="BoolType"></ts-multi-select>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>

export default {
  data() {
    return {
      model: {
        orgName: '',
        orgCode: '',
        statusCd: '',
        defaultInd: ''
      },
      StCdType: { key: 'StCd', group: '1', width: '100%', placeholder: '请选择' },
      BoolType: { key: 'Indicator', width: '100%', placeholder: '请选择' },
      selectParam: {
        width: '100%',
        visibeInit: true,
        request: true, // 非必填 是否通过接口获取数据
        pagination: true, // 非必填 是否分页
        filterable: true,
        remote: true, // 非必填 是否通过远程搜索
        optionWidth: 600, // 非必填 宽度默认400
        header: ['机构名称', '机构编号'], // 表格的头部
        body: ['orgName', 'orgCode'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'orgName', // 下拉框显示内容，根据需要修改字段名称
        value: 'orgName', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'orgName', // 查询条件的别名
        api: this.$api.orgSelector, // 接口
        params: { statusCd: '1' }
      },
      rules: {
        orgName: [
          { required: true, message: '请选择机构名称', trigger: 'blur' }
        ],
        statusCd: [
          { required: true, message: '请选择机构用户关联状态', trigger: 'blur' }
        ],
        defaultInd: [
          { required: true, message: '请选择是否默认机构', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {

  },
  methods: {
    selectOrg(value, select) {
      const len = select.length
      if (len > 0) {
        const { gridRowSign, orgCode } = select[0]
        this.model.orgCode = orgCode
      }
    }

  }
}
</script>
