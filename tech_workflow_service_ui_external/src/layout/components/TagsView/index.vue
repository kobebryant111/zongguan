<!--
 * @Author: Evan-lian
 * @Date: 2020-05-21 16:36:05
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-08-04 15:31:54
 * @Description: 页签核心文件
 -->
<template>
<div id="tags-view-container" class="tags-view-container-workflow">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper">
        <div class="tags-view-item" v-for="(tag,index) in visitedViews" :key="index">
            <span ref="tag" :path="tag.path" :fullPath="tag.fullPath" :part="tag.module" :class="isActive(tag)?'active':''" class="tags-view-item-link" @click='goTo(tag)'> {{ tag.title }}</span>
            <span v-if="!isAffix(tag)" class="tag-close" @click.prevent.stop="closeSelectedTag(tag)">X</span>
        </div>
        <el-dropdown trigger="click" class="tags-view-close" @command="handleCommand">
            <span class="el-dropdown-link">
                <i class="el-icon-arrow-down el-icon--right" />
            </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="a">刷新页面</el-dropdown-item>
                <el-dropdown-item v-if="!$route.meta.affix" command="b">关闭当前</el-dropdown-item>
                <el-dropdown-item command="c">关闭其他</el-dropdown-item>
                <el-dropdown-item command="d">关闭所有</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </scroll-pane>
</div>
</template>

<script>
import ScrollPane from './ScrollPane'
import path from 'path'

export default {
  inject: ['routerRefresh'],
  components: {
    ScrollPane
  },
  data() {
    return {
      selectedTag: {},
      affixTags: []
    }
  },
  computed: {
    visitedViews() {
      return this.$store.state.tagsView.visitedViews
    },
    routes() {
      return this.$store.state.permission.routes
    }
  },
  watch: {
    $route() {
      this.addTags()
      this.moveToCurrentTagNew()
    }
  },
  mounted() {
    const dataArray = JSON.parse(localStorage.getItem('VISITEDVIEWS'))
    if (!dataArray && typeof (dataArray) != 'undefined' && dataArray !== 0 || dataArray.length === 0) {
      this.initTags() // 初始化  //首页的路由
    } else {
      dataArray.forEach(value => {
        if (value.query) {
          if (value.query.goBack && value.query.goBack === 'goBack' || value.query.newtag !== undefined) {
            value.query.newtag = 'newtag'
          }
        }
        this.$store.dispatch('tagsView/addView', value)
      })
    }
  },
  methods: {
    // 页签跳转的自定义逻辑
    goTo(tag) {
      // 来回切换不需要goback参数
      delete tag.query.goBack
      tag.query.part = tag.module
      if (tag.path == this.$route.path && tag.module !== this.$route.query.part) {
        // this.routerRefresh();//为了处理同一个路径不同参数单例共享的问题，此处刷新威力太大暂时注释
      }
      this.$router.push({
        path: tag.path,
        query: tag.query
      })
    },
    // 是否当前页签
    isActive(route) {
      return route.module == this.$route.query.part
    },
    // 是否是可删页签
    isAffix(tag) {
      return tag.meta && tag.meta.affix
    },
    // 过滤可删页签
    filterAffixTags(routes, basePath = '/') {
      let tags = []
      routes.forEach(route => {
        if (route.meta && route.meta.affix) {
          const tagPath = path.resolve(basePath, route.path)
          tags.push({
            fullPath: tagPath,
            path: tagPath,
            name: route.name,
            query: {
              part: tagPath.replace(/\//g, '')
            },
            meta: {
              ...route.meta
            }
          })
        }
        if (route.children) {
          const tempTags = this.filterAffixTags(route.children, route.path)
          if (tempTags.length >= 1) {
            tags = [...tags, ...tempTags]
          }
        }
      })
      return tags
    },
    // 初始化页签
    initTags() {
      const affixTags = this.affixTags = this.filterAffixTags(this.routes)
      for (const tag of affixTags) {
        // Must have tag name必须带有name的路径才会进新增页签的逻辑
        if (tag.name) {
          this.$store.dispatch('tagsView/addVisitedView', tag)
        }
      }
    },
    // 新增页签
    addTags() {
      const {
        name
      } = this.$route
      if (name) { // 须带有name的路径才会进新增页签的逻辑
        this.$route.meta.keepAlive = true
        this.$store.dispatch('tagsView/addView', this.$route)
      }
      return false
    },
    // 新的
    moveToCurrentTagNew() {
      const tags = this.$refs.tag
      this.$nextTick(() => {
        for (const tag of tags) {
          const attr_part = tag.getAttribute('part')
          const attr_path = tag.getAttribute('path')
          const attr_fullPath = tag.getAttribute('fullPath')
          if (attr_path === this.$route.path) {
            this.$refs.scrollPane.moveToTarget(tag)
            // when query is different then update
            if (attr_fullPath !== this.$route.fullPath && attr_part === this.$route.part) {
              this.$store.dispatch('tagsView/updateVisitedView', this.$route)
            }
            break
          }
        }
      })
    },
    // 刷新页签
    refreshSelectedTag(view) {
      this.$tsVue.tansunPushNoAddTag(this, '/emptyPage')
    },
    // 关闭页签 (关闭当前+非当前页签关闭)
    closeSelectedTag(view) {
      // if (Object.keys(view).length) {
      //   view.meta.stopcache = true // 标记结束缓存
      //   this.isActive(view) ? view.meta.stopcacheself = true : view.meta.stopcacheone = true
      // }
      if (Object.keys(view).length) {
        let keepAlive = this.getKeepAlive()
        let key = view.meta.cachedkey
        if(keepAlive && keepAlive.componentInstance.cache){
          let cache = keepAlive.componentInstance.cache
          let keys = keepAlive.componentInstance.keys
          if (cache[key] && keys.length && keys.indexOf(key) > -1) {
            keys.splice(keys.indexOf(key), 1)
            delete cache[key]
          }
        }
      }
      this.$store.dispatch('tagsView/delView', view).then(({
        visitedViews
      }) => {
        if (this.isActive(view)) {
          this.toLastView(visitedViews, view)
        }
      })
    },
    // 关闭其他
    closeOthersTags(view) {
      // if (Object.keys(view).length) {
      //   view.meta.stopcache = true // 标记结束缓存
      //   view.meta.stopcacheothers = true // 标记关闭其他页签缓存
      // }
      let keepAlive = this.getKeepAlive()
      if(keepAlive && keepAlive.componentInstance.cache){
        let cache = keepAlive.componentInstance.cache
        let keys = keepAlive.componentInstance.keys
        if(Object.keys(cache).length > 0){
          keys = [view.meta.cachedkey]
          Object.keys(cache).forEach((key)=>{
            if(view.meta.cachedkey !== key){
              delete cache[key]
            }
          })
        }
      }
      this.$store.dispatch('tagsView/delOthersViews', this.selectedTag).then(() => {
        this.moveToCurrentTagNew()
      })
    },
    // 关闭所有
    closeAllTags(view) {
      // if (Object.keys(view).length) {
      //   view.meta.stopcache = true // 标记结束缓存
      //   view.meta.stopcacheall = true // 标记关闭所有页签缓存
      // }
      let keepAlive = this.getKeepAlive()
      if(keepAlive && keepAlive.componentInstance.cache){
        keepAlive.componentInstance.cache = {}
        keepAlive.componentInstance.keys = []
      }
      this.$store.dispatch('tagsView/delAllViews').then(({
        visitedViews
      }) => {
        if (this.affixTags.some(tag => tag.path === view.path)) {
          return
        }
        this.toLastView(visitedViews, view)
      })
    },
    // 跳转到最近的一个页签
    toLastView(visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        console.log(latestView)
        this.$router.push(latestView)
      } else {
        this.$router.push({
          path: '/',
          query: {
            part: 'home'
          }
        })
      }
    },
    handleCommand(command) {
      for (const tags of this.visitedViews) {
        if (this.isActive(tags)) {
          this.selectedTag = tags
        }
      }
      switch (command) {
        case 'a':
          this.refreshSelectedTag(this.selectedTag)
          break
        case 'b':
          this.closeSelectedTag(this.selectedTag)
          break
        case 'c':
          this.closeOthersTags(this.selectedTag)
          break
        case 'd':
          this.closeAllTags(this.selectedTag)
          break
      }
    },
    getKeepAlive(){
      //获取到keep-alive
      let children = this.$parent.$vnode.child.$children
      let appMain = null
      for (let i = 0; i < children.length; i++) {
        if(children[i].$vnode.tag.indexOf('AppMain') > -1){
          appMain = children[i]
        }
      }
      if(appMain.$children && appMain.$children.length > 0){
        return appMain.$children[0].$vnode.parent
      } else {
        return null
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/common/theme.scss";

.tags-view-container-workflow {
    width: 100%;
    height: 47px;
    padding-left: 10px;
    background: linear-gradient(180deg, #ECEDEE, #FFFFFF, #FFFFFF, #FFFFFF);
    border-bottom: 1px solid $tag-select-color;

    .tags-view-wrapper {
        .tags-view-item {
            display: inline-block;
            position: relative;
            margin-top: 15px;
        }

        .tags-view-item-link {
            display: inline-block;
            position: relative;
            cursor: pointer;
            line-height: 32px;
            color: #666666;
            padding: 0 20px;
            font-size: 14px;
            margin-left: 8px;
            height: 32px;
            background: #F4FBFF;
            border: 1px solid #d0d0d0;
            border-bottom: 1px solid $tag-select-color;
            border-top-left-radius: 4px;
            border-top-right-radius: 4px;

            &:first-of-type {
                margin-left: 15px;
            }

            /*&:last-of-type {*/
            /*  margin-right: 15px;*/
            /*}*/
            &:hover,
            &.active {
                font-size: 14px;
                font-weight: bold;
                color: #ffffff;
                border: 1px solid $tag-select-color;
                background: $tag-select-color;
            }

            &.active::after,
            &:hover::after {
                left: 50%;
                bottom: -1px;
                transform: translate(-50%, 0%);
                content: '';
                position: absolute;
                border-bottom: solid 4px #f2f2f2;
                border-left: solid 8px transparent;
                border-right: solid 8px transparent;
            }
        }

        .tags-view-close {
            display: inline-block;
            cursor: pointer;
            height: 32px;
            line-height: 32px;
            margin-top: 15px;
            color: #495060;
            padding-left: 3px;
            padding-right: 15px;
            position: absolute;
            border-bottom: 1px solid $tag-select-color;
            background: #ffffff;
            right: 0;
        }

        .tag-close {
            position: absolute;
            cursor: pointer;
            top: 0px;
            right: 0px;
            width: 18px;
            height: 16px;
            font-size: 12px;
            text-align: center;
            text-indent: 3px;
            color: #ffffff;
            background: #e22635;
            border-top-right-radius: 4px;
            border-bottom-left-radius: 12px;
            box-shadow: 0px 2px 1px 0px rgba(227, 0, 18, 0.09);
        }
    }
}
</style>
