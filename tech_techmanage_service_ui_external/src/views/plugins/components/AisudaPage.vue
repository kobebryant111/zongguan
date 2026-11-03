<template>
  <div ref="current">Loading...</div>
</template>

<script>
export default {
  data() {
    return {
      loaderUrl: '/tech/static/jssdk/loader-19e48d8c.js',
      unmountedStatus: false,
      currentPage: null
    };
  },
  props: {
    data: {
      type: Object,
      default: function () {
        return {};
      }
    },
    pageUrl: {
      type: String,
      default: ''
    }
  },
  mounted() {
    this.loadAMISLoader(this.loaderUrl, loader => {
      if (this.unmountedStatus) {
        return;
      }

      loader.load(this.pageUrl, page => {
        // 替换请求域名
        const data = {
          AISUDA_HOST: 'http://m1-fex-platform-04.m1.baidu.com:8818',
          HOST: '',
          ...this.data
        };

        const env = {
          // 配置请求适配器
          requestAdaptor: function(api) {
            return api
          }
        }
        this.currentPage = page;
        this.$refs.current &&
          !this.unmountedStatus &&
          page.mount(this.$refs.current, page.schema, data, env);
      });
    });
  },
  watch: {
    data: function (val) {
      this.currentPage &&
        this.currentPage.updateProps({
          data: val
        });
    }
  },
  unmounted() {
    this.unmountedStatus = true;
    this.currentPage && this.currentPage.unmount();
  },
  methods: {
    loadAMISLoader(url, callback) {
      if (window.amisLoader) {
        callback && callback(window.amisLoader);
        return;
      }

      const script = document.createElement('script');
      script.setAttribute('type', 'text/javascript');
      script.onload = () => {
        callback(window.amisLoader);
      };
      script.setAttribute('src', url);

      document.body.appendChild(script);
    }
  }
};
</script>
