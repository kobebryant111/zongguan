<template>
  <div id="app">
    <router-view />
    <!-- <keep-alive :include="keepAlives">
      <router-view />
    </keep-alive> -->
  </div>
</template>

<script>
export default {
  name: 'App',
  computed: {
    // ...mapGetters(["keepAlives"])
    // keepAlives() {
    //   return this.$store.getters.keepAlives
    // }
  }
}
</script>
