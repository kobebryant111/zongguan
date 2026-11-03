<!--
 * @Author: your name
 * @Date: 2020-04-07 09:47:49
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-20 17:23:42
 * @Description:
-->
<template>
  <div class="page-container">
    <EcdgenList
      v-show="!showDtl"
      @maintain="onmaintain"></EcdgenList>
    <EcdDtlList
      v-if="showDtl"
      :params="params"
      @back="onback"></EcdDtlList>
  </div>
</template>

<script>
import EcdgenList from './ecdgen-list'
import EcdDtlList from './ecd-dtl-list'

export default {
  name: 'TsEcdgenManage',
  components: { EcdgenList, EcdDtlList },
  data() {
    return {
      showDtl: false,
      params: {}
    }
  },
  created() {

  },
  methods: {
    onmaintain(params) {
      this.params = params
      this.showDtl = true
    },
    onback() {
      this.showDtl = false
    }
  }
}
</script>

<style scoped>

</style>
