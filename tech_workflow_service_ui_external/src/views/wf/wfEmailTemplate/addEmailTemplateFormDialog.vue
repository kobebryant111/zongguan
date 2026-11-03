<template>
    <div>
        <el-form ref="formRef" :model="model" :rules="rules" label-width="100px" style="height: 180px">
            <div class="col_td1" v-show="false">
                <el-form-item label="租户："  style="width: 83%">
                    <el-input    v-model="model.tenantName"  :readonly="true" ></el-input>
                </el-form-item>
            </div>
            <div class="col_td1">
                <el-form-item label="模板名称" prop="tempName">
                    <el-input  class="buttonClass" size="mini" v-model.trim="model.tempName"></el-input>
                </el-form-item>
            </div>
            <div class="col_td1">
                <el-form-item label="模板标签" prop="tempTag">
                    <el-input class="buttonClass" size="mini" v-model.trim="model.tempTag"></el-input>
                </el-form-item>
            </div>
            <div class="col_td2">
                <el-form-item label="模板内容" prop="tempBody">
                    <el-input type="textarea" class="buttonClass" size="mini" v-model.trim="model.tempBody"></el-input>
                </el-form-item>
            </div>
        </el-form>
    </div>
</template>
<script>
export default {
  name: 'AddButtonFormDialog',
  data() {
    return {
      model: {
        id: '',
        tempName: '',
        tempTag: '',
        tempBody: '',
        tenantId: '',
        tenantName: ''
      },
      rules: {
        tempName: [
          { required: true, message: '请输入模板名称', trigger: 'blur' }
        ],
        tempTag: [
          { required: true, message: '请输入模板标签', trigger: 'blur' }
        ],
        tempBody: [
          { required: true, message: '请输入模板内容', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    const dialogObj = this.$tsVue.tansunDialogData(this)
    const { initData } = dialogObj
    const _this = this
    this.$nextTick(() => {
      _this.model.tenantId = initData.tenantId
      _this.model.tenantName = initData.tenantName
    })
  }
}
</script>
<style scoped>
    .col_td {
        width:50%;
        float:left;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
        margin-top: 22px;
    }
    .buttonClass{
        width:95%;
    }
</style>

