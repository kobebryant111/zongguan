<template>
  <div class="brand-container">
    <div class="logo">
      <img src="@/assets/LOGO.jpg" alt="logo">
      <h1 :class="{'collapse': collapse}">综合管理系统</h1>
    </div>
  </div>
</template>
<style scoped>
.brand-container{
  height: 60px;
  line-height: 60px;
  position: relative;
  padding-left: 24px;
  /* background-color: #08203f; */
  background-color: #1F1F1F;
  box-shadow: 0 1px 9px -3px rgba(0, 0, 0, .2);
  overflow: hidden;
  transition: all .3s;
}
img{
  display: inline-block;
  vertical-align: middle;
  width: 32px;
}
h1{
  margin: 0 0 0 12px;
  line-height: 60px;
  vertical-align: middle;
  font-size: 18px;
  display: inline-block;
  font-weight: bold;
  color: rgba(255, 255, 255, .9);
}

</style>

<script>
export default {
  name: 'Brand',
  props: {
    collapse: {
      type: Boolean,
      default: false
    }
  }
}
</script>
