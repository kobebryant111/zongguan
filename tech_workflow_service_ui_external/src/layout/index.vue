<!--
@Author: chenhuocan
@Date: 2020-03-12 13:53:29
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-27 14:27:50
@Description: layout 主入口
-->

<template>
  <div :class="classObj" class="app-wrapper">
    <el-scrollbar class="page-component__nav layout-page">
      <template v-if="!micro">
      <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
      <!--<div style="position:fixed; top:0;left:0; right:0; z-index:999">-->
      <div class="header-container-workflow">
        <navbar/>
        <!--<sidebar class="sidebar-container"/>-->
      </div>
      <tags-view/>
      </template>
      <!--</div>-->
      <div class="main-container">
        <app-main v-if="routerAlive"/>
      </div>
    </el-scrollbar>
    <v-tour class="tour-theme" v-if="!micro" name="mytour" :steps="steps" :options="tourOptions"/>
  </div>
</template>

<script>
import throttle from 'throttle-debounce/throttle'
import { AppMain, Navbar, Settings, Sidebar, TagsView, RightPanel } from './components'
import { fetchGuideFlag, updateGuideFlag } from '@/api/helpCenter'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'

export default {
  name: 'Layout',
  provide() { // 在父组件中创建属性
    return {
      routerRefresh: this.routerRefresh
    }
  },
  components: {
    AppMain,
    Navbar,
    Sidebar,
    TagsView
  },
  mixins: [ResizeMixin],
  data() {
    return {
		  routerAlive: true,
      scrollTop: 0,
      showHeader: true,
      componentScrollBar: null,
      componentScrollBoxElement: null,
      guideFlag: false,
      guideInfo: null,
      tourOptions: {
        labels: {
          buttonSkip: '知道了',
          buttonPrevious: '上一步',
          buttonNext: '下一步',
          buttonStop: '完成'
        }
      },
      steps: [
        {
          target: '.v-step-0',
          content: `个人设置中心`,
          params: {
            placement: 'right',
            highlight: false
          }
        },
        {
          target: '.v-step-1',
          content: '功能菜单',
          params: {
            placement: 'left',
            highlight: true
          }
        },
        {
          target: '.v-step-2',
          content: '当前位置',
          params: {
            placement: 'top'
          }
        }
      ]
    }
  },
  computed: {
    ...mapState({
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      showSettings: state => state.settings.showSettings,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  created() {

  },
  methods: {
    routerRefresh() {
      this.routerAlive = false
      this.$nextTick(() => {
        this.routerAlive = true
      })
    },
    excuteGuideAction() {
      console.log('excute guide action')
      this.$tours['mytour'].start()
    },
    getGuideFlag() {
      const params = {
        loginName: this.userInfo && this.userInfo.loginName
      }
      fetchGuideFlag(params).then(res => {
        this.guideFlag = res.data
        // 返回null 则更新guide标志
        if (this.guideFlag === null) {
          // 仅执行一次
          this.excuteGuideAction()
          this.alterGuideFlag()
        }
      })
    },
    alterGuideFlag() {
      const { id, loginName } = this.userInfo
      const query = {
        id: '',
        userId: id,
        loginName: loginName,
        loginFirst: '0', // 首次登录(0=否，1=是)
        newLoginFirst: '0', // 新功能首次登录(0=否，1=是)
        delInd: 0 // 删除标志(0=未删除，1=已删除)
      }
      updateGuideFlag(query).then(res => {
        this.guideInfo = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  }

}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixins/mixin.scss";
  @import "~@/styles/common/variables.scss";

  ::v-deep .page-component__nav.layout-page {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    transition: padding-top .3s;

    > .el-scrollbar__wrap {
      overflow-x: auto;
    }

    .el-scrollbar__view {
      height: 100%;
    }

    &.is-extended {
      padding-top: 0;
    }
  }

  .header-container-workflow {
    position: relative;
    background: #FEFEFE;
  }

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

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px)
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>
