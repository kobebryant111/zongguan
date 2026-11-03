<template>
    <div>
        <el-form ref="formRef" :model="model" label-width="20%">
            <el-form-item label="终止意见:" prop="tenantId">
                <ts-textarea
                            v-model="model.reason"
                             :options="opitons"
                             :disabled="disabledSet"
                             refs="mytextarea">
                </ts-textarea>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
  name: 'WorkFlowInterveneDialog',
  data() {
    return {
      disabledSet: false,
      opitons: {
        idName: 'demo2', // 给元素附自定义id类名
        width: '400px' // 选填
      },
      model: {
        activityId: '',
        activityName: '',
        businessKey: '',
        executionId: '',
        isSuspended: '',
        processDefineId: '',
        processDefineKey: '',
        processDefineName: '',
        processDefineVersion: '',
        processInstanceId: '',
        processVariables: {},
        tenantId: '',
        tsRowIndex: '',
        reason: ''
      },
      selectValue3: ''
    }
  },
  watch: {}
}
</script>

<style scoped>

</style>
