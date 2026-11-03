<!--
 * @Author: liuyang
 * @Date: 2020-04-11 16:19
 * @LastEditors  : liuyang
 * @LastEditTime : 2020-04-11 16:19
 * @Description:
-->
<template>
  <div>
    <el-form ref="orgFormRef"  :model="model" :rules="rules" label-width="120px">
      <el-form-item label="上级机构名称" prop="parentOrgName">
        <el-input v-model="model.parentOrgName" autocomplete="off" :disabled="true"/>
      </el-form-item>
      <el-form-item label="上级机构编号" prop="parentOrgCode">
        <el-input v-model="model.parentOrgCode" autocomplete="off" :disabled="true"/>
      </el-form-item>
      <el-form-item label="机构名称" prop="orgName">
        <el-input v-model="model.orgName" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="机构编号" prop="orgCode">
        <el-input v-model="model.orgCode" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="机构等级" prop="menuName">
        <ts-multi-select v-model="model.orgLevelCd" :params="{ key: 'OrgLevel', width: '100%' }" :disabled="true"></ts-multi-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="model.sort" autocomplete="off"/>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

export default {
  components: {},
  name: 'MenuForm',
  data() {
    return {
      model: {
        orgLevelCd: '',
        orgName: '',
        orgCode: '',
        parentOrgCode: '',
        parentOrgName: '',
        sort: ''
      },
      rules: {
        orgName: [
          { required: true, message: '请输入机构名称', trigger: 'blur' }
        ],
        orgCode: [
          { required: true, message: '请输入机构编号', trigger: 'blur' }
        ],
        orgLevelCd: [
          { required: true, message: '请输入机构等级', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
  }
}
</script>
<style scoped>
</style>

