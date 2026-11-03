<!--
 * @Author: Evan-lian
 * @Date: 2020-06-05 15:03:23
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-16 14:33:11
 * @Description:
 -->
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <!-- 方案1 靠的文件中name,无法保障人为添加的文件都能赋值唯一性的name
      非路由的name 没有实战意义
      所以废弃 cachedViews 顶多就是知道有几个页面被缓存而已-->
      <!-- <keep-alive :include="cachedViews">
        <router-view :key="key" />
      </keep-alive> -->
      <!-- 方案2 靠keepalive 做缓存 弊端业务逻辑涉及到路由beforeRouteLeave beforeRouteEnter
      通过处理keepaliive 源码 this.$vnode.parent.componentInstance.cache 动态赋值清除缓存-->
       <div>
            <keep-alive>
                <router-view v-if="$route.meta.keepAlive" ref="routerView" @hook:mounted="routerMounted"></router-view>
            </keep-alive>
            <router-view v-if="!$route.meta.keepAlive"></router-view>
        </div>
    </transition>
    <!-- {{$route.meta}} -->
    <!-- {{cachedViews}} -->
  </section>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
  },
  methods:{
    routerMounted(){
      // console.log('routerMounted')
      // console.log(this.$refs.routerView.$vnode)
      if (this.$route.query.newtag === 'newtag' || this.$route.query.newtag === undefined) {
        //新开的一级页签
        const vnode = this.$refs.routerView.$vnode
        let tag = this.$refs.routerView.$vnode.tag
        let key = vnode.key == null
            ? vnode.componentOptions.Ctor.cid + (vnode.componentOptions.tag ? `::${vnode.componentOptions.tag}` : '')
            : vnode.key
        this.$set(this.$route.meta, 'cachedtag', tag)
        this.$set(this.$route.meta, 'cachedkey', key)
        this.reStoreLocalViews(this.$route)
      }
    },
    reStoreLocalViews(router){
      // 更新本地缓存
      try {
        const newvisitedViews = this.$store.state.tagsView.visitedViews
        //浏览器刷新后，cachedtag、cachedkey 未能更新处理
        newvisitedViews.forEach((item,index)=>{
          if (router.path === item.path && item.meta.cachedtag !== router.meta.cachedtag){
            newvisitedViews[index].meta.cachedtag = router.meta.cachedtag
            newvisitedViews[index].meta.cachedkey = router.meta.cachedkey
          }
        })
        localStorage.setItem('VISITEDVIEWS', JSON.stringify(newvisitedViews))
      } catch (error) {
        console.log(error)
        console.log('路由对象循环引用报错')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  /*min-height: calc(100vh - 50px);*/
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header+.app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    /*min-height: calc(100vh - 84px);*/
  }

  .fixed-header+.app-main {
    padding-top: 84px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
