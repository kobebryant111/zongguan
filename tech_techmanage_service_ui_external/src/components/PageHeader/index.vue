<template>
  <div class="container">
    <h1>{{ pageTitle }}</h1>
    <p>{{ pageDescription }}</p>
  </div>
</template>

<script>
export default {
  name: 'PageHeader',
  props: {
    pageTitle: {
      type: String,
      default: ''
    },
    pageDescription: {
      type: String,
      default: ''
    }
  }
}
</script>

<style scoped>
.container {
  /* padding: 16px 32px 0; */
  background-color: #fff;
}
h1{
  font-size: 28px;
  color: #595959;
  margin: 0;
}
</style>
