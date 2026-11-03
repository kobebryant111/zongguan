/*
@Author: chenhuocan
@Date: 2020-03-10 16:49:16
@LastEditors:
@LastEditTime:
@Description:
*/
<template>
  <div id="app" :data-theme="nowThemeInfo">
    <router-view/>
  </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  name: 'App',
  computed: {
    ...mapState({
      'nowThemeInfo': state => state.theme.nowThemeInfo
    })
  },
  mounted() {
    const _this = this
    document.onkeydown = function(e) {
      const key = window.event.keyCode
      if (key === 112) {
        // f1键，跳转异常详情页
        _this.$router.push({ path: '/help/exceptionInfo' })
      }
    }
  }
}
</script>
<style>
.wf-dialog .diarea{
  width: 100% !important;
}
.el-message {
  z-index: 2050 !important;
}
</style>
