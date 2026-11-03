<template>
  <div>
    <el-form ref="formRef" :model="model" :rules="rules" label-width="150px">
      <el-form-item label="机构名称：" prop="orgName">
        <ts-multi-select v-model="model.orgName" :params="orgParam" @change="selectOrg"></ts-multi-select>
      </el-form-item>

      <el-form-item label="部门名称：" prop="deptName">
        <ts-multi-select v-model="model.deptName" :params="departParam" @change="selectDepart"></ts-multi-select>
      </el-form-item>

      <el-form-item label="部门用户关联状态：" prop="statusCd">
        <ts-multi-select v-model="model.statusCd" :params="StCdType"></ts-multi-select>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>

export default {
  data() {
    return {
      model: {
        userNum: '',
        orgName: '',
        orgCode: '',
        deptName: '',
        deptCode: '',
        statusCd: ''
      },
      StCdType: { key: 'StCd', group: '1', width: '100%', placeholder: '请选择' },
      orgParam: {
        width: '100%',
        visibeInit: false,
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
        api: this.$api.departOrgSelector, // 接口
        param: { statusCd: 1, userNum: this.userNumPara }
      },
      departParam: {
        width: '100%',
        visibeInit: false,
        request: true, // 非必填 是否通过接口获取数据
        pagination: true, // 非必填 是否分页
        filterable: true,
        remote: true, // 非必填 是否通过远程搜索
        optionWidth: 600, // 非必填 宽度默认400
        header: ['部门名称', '部门编号'], // 表格的头部
        body: ['deptName', 'deptCode'], // 显示在列表中的字段名称 字典取值用|线分割 放后边
        text: 'deptName', // 下拉框显示内容，根据需要修改字段名称
        value: 'deptName', // 下拉框对应文本的值，根据需要修改字段名称
        alias: 'deptName', // 查询条件的别名
        api: this.$api.departmentSelector, // 接口
        param: { statusCd: 1, userNum: this.userNumPara, orgCode: '' }
      },
      rules: {
        orgName: [
          { required: true, message: '请选择机构名称', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '请选择部门名称', trigger: 'blur' }
        ],
        statusCd: [
          { required: true, message: '请选择部门用户关联状态', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    userNumPara() {
      return this.model.userNum
    }
  },
  watch: {
    userNumPara(newVal, oldVal) {
      if (newVal) {
        this.getSelectData()
      }
    }
  },
  methods: {
    getSelectData() {
      const userNum = this.model.userNum
      const param = {
        statusCd: 1,
        userNum
      }
      this.orgParam.param = param
    },

    selectOrg(value, select) {
      const len = select.length
      if (len > 0) {
        const { orgCode } = select[0]
        this.model.orgCode = orgCode
        this.departParam.param = Object.assign({ orgCode: orgCode }, this.orgParam.param)
      }
    },

    selectDepart(value, select) {
      const len = select.length
      if (len > 0) {
        const { deptCode } = select[0]
        this.model.deptCode = deptCode
      }
    }

  }
}
</script>
