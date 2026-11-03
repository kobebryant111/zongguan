<!--
 * @Author: your name
 * @Date: 2020-04-07 09:47:49
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-20 17:25:39
 * @Description:
-->
<template>
  <div class="page-container">
    <TenantList
      v-if="show"
      @maintain="onmaintain"
      @toAddView="toAddView"></TenantList>
    <TenantAdd
      v-if="showTenantAdd"
      :params="params"
      @back="onback"></TenantAdd>
    <TenantView
      v-if="showTenantView"
      :params="params"
      @back="onback"></TenantView>
  </div>
</template>

<script>
import TenantList from './tenant-list'
import TenantAdd from './tenant-add'
import TenantView from './tenant-view'

export default {
  name: 'TsTenantManage',
  components: { TenantList, TenantAdd, TenantView },
  data() {
    return {
      show: true,
      showTenantView: false,
      showTenantAdd: false,
      params: {}
    }
  },
  created() {

  },
  methods: {
    onmaintain(params) {
      this.params = params
      this.showTenantView = true
      this.show = false
    },
    toAddView(params) {
      this.params = params
      this.showTenantAdd = true
      this.show = false
    },
    onback() {
      this.showTenantAdd = false
      this.showTenantView = false
      this.show = true
    }
  }
}
</script>

<style scoped>

</style>

