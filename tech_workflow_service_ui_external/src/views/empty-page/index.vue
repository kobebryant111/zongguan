<!--
 * @Author: Evan-lian
 * @Date: 2020-05-20 16:11:53
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-09 11:30:12
 * @Description: 空白中转页 做页签刷新取巧处理
 -->

<template>
 <div>
 </div>
</template>
<script>
export default {
  data() {
    return {}
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.$router.replace({
       path: from.path,
       query: Object.assign(from.query, {needupdate:'yes'})
      })
    })
  }
}
</script>

