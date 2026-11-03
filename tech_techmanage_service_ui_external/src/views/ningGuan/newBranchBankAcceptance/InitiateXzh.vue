<template>
  <aisuda-page :page-url="pageUrl" :data="customData"/>
</template>

<script>
import AisudaPage from '@/views/plugins/components/AisudaPage';

export default {
  components: {
    AisudaPage
  },

  data() {
    return {
      pageUrl: '/tech/static/aisuda/53e0fa7f6d72-374f1f98/N36Zg8wEkW-c9bade98.js',
      customData: {}
    };
  },
  methods:{
    init(){},
  }
};
</script>
<style>
html{
  font-size: 16px
}
body{
  font-size: 14px
}
</style>

