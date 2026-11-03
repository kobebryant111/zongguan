<template>
  <div :class="classObj" class="app-wrapper">

    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar class="sidebar-container" />

    <div class="main-container">
      <navbar class="navbar" :navName="navName" />
      <!-- <tags-view/> -->
      <app-main class="appMain" />
    </div>

  </div>
</template>

<script>
  import { Navbar, Sidebar, AppMain } from './components'
  //修改前
  // import { Navbar, Sidebar, AppMain, TagsView } from './components'
  import ResizeMixin from './mixin/ResizeHandler'

  export default {
    name: 'Layout',
    components: {
      Navbar,
      Sidebar,
      AppMain
      //TagsView
    },
    data() {
      return {
        navName: ''
      }
    },
    beforeCreate() {

    },
    created() {
      this.getRouterMessage();
    },
    mixins: [ResizeMixin],
    computed: {
      sidebar() {
        return this.$store.state.app.sidebar
      },
      device() {
        return this.$store.state.app.device
      },
      classObj() {
        return {
          hideSidebar: !this.sidebar.opened,
          openSidebar: this.sidebar.opened,
          withoutAnimation: this.sidebar.withoutAnimation,
          mobile: this.device === 'mobile'
        }
      }
    },
    methods: {
      handleClickOutside() {
        this.$store.dispatch('closeSideBar', { withoutAnimation: false })
      },
      //获取路由元信息
      getRouterMessage() {
        console.log("路由");
        this.navName = this.$route.meta.title;
        console.log(this.navName);
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "src/styles/mixin.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  /* .app-wrapper .navbar{
    padding: 0;
    margin: 0;
  } */

  /* .main-container {
    height: 100%;
  } */
  
  .app-wrapper .appMain {
    /* padding: 0; */
    /* padding: 5px 0 0 5px; */
    /* height: 100%; */
    border-top: 1px solid rgba(0, 0, 0, 0.04);
    /* border-bottom: 1px solid #ffffff; */
  }
</style>