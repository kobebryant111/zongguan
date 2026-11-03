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
      pageUrl: '/tech/static/aisuda/53e0fa7f6d72-374f1f98/KGmEoW6EoM-81d3fc90.js',
      customData: {},
      loaded: false,
    };
  },
  methods: {
    init(params) {
      this.customData = {
        instanceId : params.processInstID,
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