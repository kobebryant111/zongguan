<template>
  <aisuda-page v-if="loaded" :page-url="pageUrl" :data="customData" />
</template>

<script>
import AisudaPage from '@/views/plugins/components/AisudaPage';

export default {
  components: {
    AisudaPage
  },

  data() {
    return {
      pageUrl: '/tech/static/aisuda/53e0fa7f6d72-374f1f98/rakE4zB781-41992f55.js',
      customData: {},
      loaded: false,
    };
  },
  methods: {
    init(params) {
      let showDrawback = params.showDrawback != undefined ? false : true;
      this.customData = {
        instanceId : params.processInstID,
        showDrawback: showDrawback,
      };
      this.loaded = true;
    }
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