/*
@Author: chenhuocan
@Date: 2020-03-11 19:35:52
@LastEditors:
@LastEditTime:
@Description: 路由重定向
*/
<script>
export default {
  created() {
    const { params, query } = this.$route
    const { path } = params
    this.$router.replace({ path: '/' + path, query })
  },
  render: function(h) {
    return h() // 避免警告消息
  }
}
</script>
