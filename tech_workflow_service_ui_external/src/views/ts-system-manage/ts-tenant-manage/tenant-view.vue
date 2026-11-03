<!--
 * @Author: your name
 * @Date: 2020-04-07 09:47:49
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-17 17:27:19
 * @Description:
-->
<template>
  <div>
    <el-form ref="form" :model="form" label-width="120px" class="single-col-form">
      <el-form-item label="租户名称">
        <el-input v-model="form.tenantName" :disabled="true" />
      </el-form-item>
<!--      <el-form-item label="社会信用代码">-->
<!--        <el-input v-model="form.orgCode" :disabled="true" />-->
<!--      </el-form-item>-->
      <el-form-item label="管理员名称">
        <el-input v-model="form.managerName" :disabled="true" />
      </el-form-item>
      <el-form-item label="登录名">
        <el-input v-model="form.loginName" :disabled="true" />
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="form.mobile" :disabled="true" />
      </el-form-item>
    </el-form>
    <div class="button-box">
      <el-button type="primary" icon="el-icon-back" size="small" @click="back">返回</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TenantView',
  components: {},
  props: {
    params: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      form: {
        tenantName: '',
        orgCode: '',
        managerName: '',
        loginName: '',
        mobile: ''
      }
    }
  },
  created() {
    this.getTenant()
  },
  methods: {
    getTenant() {
      this.$request({
        url: this.$api.getTenant,
        data: {
          id: this.params.id,
          tenantId: this.params.tenantId
        }
      }).then((response) => {
        this.form = response.data
      })
    },
    back() {
      this.$emit('back')
    }
  }
}
</script>

<style scoped>

</style>
