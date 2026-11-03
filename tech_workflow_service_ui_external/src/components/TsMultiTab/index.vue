<!--
 * @Author: amberself
 * @Date: 2020-04-10 16:02:22
 * @LastEditors: amberself
 * @LastEditTime: 2020-09-11 12:11:08
 * @Description: Tabs大页面
 -->

<template>
  <div class="plugin-container" ref="commonRef">
    <div class="plugin-nav">
      <el-scrollbar class="page-component__scroll">
        <ul class="plugin-nav-wrap">
          <li
            v-for="(item, index) in tabsContent"
            :class="['plugin-nav-list', {'finished': index !== activeIndex && finishTabs.includes(index)}, {'active': index === activeIndex} ]"
            :key="index"
            @click="changeNav(index)"
          >
            <div class="nav-line"></div>
            <div class="nav-node"></div>
            <div class="nav-content">{{`${index+1}. ${item.title}`}}</div>
          </li>
        </ul>
      </el-scrollbar>
    </div>
    <div class="plugin-content">
      <ul
        class="plugin-content-list"
        v-on:scroll="onScroll"
        v-infinite-scroll="loadContent"
        infinite-scroll-disabled="disabled"
        infinite-scroll-immediate="immediateLoad"
      >
        <li v-for="(item, index) in scrollTabs" :key="index">
          <div
            v-if="item && item.showPage && item.showTab"
            :ref="'tabItem'+index"
            :class="['content-wrap', {'hide-content-btn': viewType === 'look'}]"
          >
            <div class="plugin-content-title">
              <!-- <span class="text">{{`${index+1}. ${item.title}`}}</span> -->
              <span class="text">{{`${item.title}`}}</span>
            </div>
            <component
              :ref="'tabsRef'+item.name"
              :is="item.resource"
              :global-data="globalData"
              plugin-sign="tabPlugin"
              :plugin-id="item.id"
              @save="changeNavState(index)"
              @update="updateData"
              @updateFlowData="updateFlowData"
            >
            </component>
            <div v-if="!item.customBtn" class="plugin-content-btns">
              <el-button size="mini" type="primary" @click="saveCurrentPage(item.name)">保存</el-button>
            </div>
          </div>
        </li>
      </ul>
      <div class="plugin-content-back">
        <el-button v-if="options.showBackBtn !== false" icon="el-icon-back" type="primary" @click="back">返回</el-button>
      </div>

    </div>

  </div>
</template>

<script>
import $ from 'jquery'
import { debounce } from 'lodash'

export default {
  name: 'TsMultiBigtab',
  props: {
    options: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      viewType: this.$route.query.viewType,
      activeIndex: 0,
      immediateLoad: false,
      loadIndexArr: [], // 已加载节点的索引号
      tabsOffsetTop: [], // 已加载节点的OffsetTop
      saveOffsetSign: false, // 是否重新计算各个节点的OffsetTop
      listenScroll: true, // 是否监听滚动来切换当前的activeIndex
      finishTabs: [], // 已点保存按钮的节点,样式为绿色
      scrollTabs: [],
      globalData: { beforeFucList: [] }
    }
  },
  computed: {
    // 用计算属性是因为this.options.tabsContent参数如果通过push、pop方法改变，组件数据能够响应
    // 如果直接通过赋值，组件无法响应变化
    tabsContent() {
      return this.getPlugins(this.options.tabsContent)
    },
    disabled() {
      return this.tabsContent.length <= this.loadIndexArr.length
    }
  },
  mounted() {
    this.loadContent()
  },
  methods: {
    getPlugins(data) {
      const plugins = []
      data.forEach((item, index) => {
        // showTab主要是为了兼容旧代码，正式版以showPage为主
        const { id, name, url, title, customBtn = false, showPage = true, showTab = true } = item
        if (!showPage || !showTab) return

        let resource
        if (typeof url === 'string') {
          resource = () => import(`@/views/${url}`)
        } else {
          resource = item.url
        }

        const component = {
          id,
          name,
          title,
          customBtn,
          resource,
          showPage,
          showTab
        }
        plugins.push(component)
      })
      return plugins
    },

    // 切换tab节点
    changeNav(index) {
      const _this = this
      this.listenScroll = false
      if (index === 0) {
        $('.plugin-content-list').animate({ scrollTop: 0 }, { duration: 400, easing: 'swing' })
      } else {
        const refName = 'tabItem' + index
        const refObj = this.$refs[refName] && this.$refs[refName][0]
        if (refObj) {
          this.scrollToTab(refObj)
          this.disabled ? '' : this.loadIndexContent(index + 1, 2)
        } else {
          this.loadIndexContent(index, 3)
          this.$nextTick(() => {
            const _refObj = this.$refs[refName] && this.$refs[refName][0]
            this.scrollToTab(_refObj)
          })
        }
      }
      this.activeIndex = index // 状态指定当前节点
      setTimeout(function() {
        _this.listenScroll = true
      }, 2000)
    },

    // 滚动到指定位置
    scrollToTab(refObj) {
      const itemOffset = refObj.offsetTop
      itemOffset && $('.plugin-content-list').animate({ scrollTop: itemOffset + 'px' }, { duration: 400, easing: 'swing' })
    },

    // 滚动时通过定位激活到当前节点
    onScroll: debounce(async function() {
      if (!this.listenScroll) return
      // 若节点全部加载完毕，则不再计算每个节点的offsetTop值
      if (!this.saveOffsetSign) {
        await this.saveTabsOffset()
        this.disabled ? this.saveOffsetSign = true : ''
      }

      // 加150是为了不用在滚到最顶部的时候才切换激活点
      const scrollHeight = $('.plugin-content-list').scrollTop() + 150
      // console.log('scrollHeight:', scrollHeight)

      let activeIndex = 0
      this.tabsOffsetTop.forEach((item, index) => {
        if (item) {
          const { showPage = true, showTab = true } = item
          if (showPage && showTab && scrollHeight > item) {
            activeIndex = index
          }
        }
      })
      this.activeIndex = activeIndex
    }, 800),

    // 保存各tab节点的offsetTop
    saveTabsOffset() {
      const offsetArr = []
      this.scrollTabs.forEach((item, index) => {
        if (item && item.showPage && item.showTab) {
          const itemObj = this.$refs['tabItem' + index][0]
          const itemOffset = itemObj.offsetTop
          offsetArr.push(itemOffset)
        } else {
          offsetArr.push(null)
        }
      })
      this.tabsOffsetTop = offsetArr
      // console.log('this.tabsOffsetTop:', this.tabsOffsetTop)
    },

    // 改变tab节点为已保存状态
    changeNavState(index) {
      // console.log('changeNavState!', index)
      const finishTabs = [...this.finishTabs]
      finishTabs.push(index)
      this.finishTabs = [...new Set(finishTabs)]
    },

    // 调用每个子页面的保存方法，子页面的方法名必须是savePage
    saveCurrentPage(itemName) {
      const refName = 'tabsRef' + itemName
      const tabsRefArr = this.$refs[refName]
      const currentRef = tabsRefArr && tabsRefArr[0]

      // saveTabs是为了兼容旧代码，正式版用savePage
      if (currentRef.savePage) {
        currentRef.savePage()
      } else if (currentRef.saveTabs) {
        currentRef.saveTabs()
      } else if (currentRef.saveStep) {
        currentRef.saveStep()
      }
    },

    // 更新globalData
    updateData(data) {
      this.globalData = Object.assign({}, this.globalData, data)
    },

    // 更新globalData.flowData
    updateFlowData(data) {
      const flowData = this.globalData.flowData || {}
      this.globalData.flowData = Object.assign({}, flowData, data)
    },

    // 返回到上一页
    back() {
      this.$tsVue.tansunBack(this)
    },

    // 递归加载某个索引的内容
    loadIndexContent(index, increment) {
      if (this.disabled) return
      const lastTabIndex = this.tabsContent.length - 1
      if (increment === 0 || index > lastTabIndex) return
      if (!this.scrollTabs[index]) {
        this.scrollTabs.splice(index, 1, this.tabsContent[index])
        this.loadIndexArr.push(index)
      }
      this.loadIndexContent(index + 1, increment - 1)
      // console.log('loadIndexContent:', this.scrollTabs)
    },

    // 滚动到底部时再加载后续的tab内容
    loadContent() {
      const totalSize = this.tabsContent.length
      let toLoadNum = 0 // 首次预加载从0开始
      if (this.loadIndexArr.length === 0) {
        this.tabsContent.forEach((item, index) => {
          this.scrollTabs.push(null)
        })
      } else {
        const sortLoadIndexArr = [...this.loadIndexArr].sort()
        const lastIndex = sortLoadIndexArr[sortLoadIndexArr.length - 1]
        toLoadNum = lastIndex + 1
      }
      this.loadIndexContent(toLoadNum, 3)
    }

  }
}
</script>

<style lang="scss">
$topHeight: 84px; // 定义顶部导航栏的高度 vueTemplate工程顶部136px
$activeColor: #007BFD;
$finishColor: #16D556;

.ts-scrollbar {
  &::-webkit-scrollbar {
    width: 8px;
    height: 8px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: #3395FF;
    // background-color: rgba(0, 123, 253, 0.5);
    border-radius: 5px;
  }
}

.plugin-container {
  height: calc(100vh - #{$topHeight});
  position: relative;
  padding: 20px;
  background: #F6F7F9;
  overflow: auto;
}
.plugin-nav {
  position: absolute;
  top: 30px;
  width: 240px;
  height: calc(100vh - #{$topHeight} - 60px);
  padding: 15px 0;
  transition: padding-top .3s;
  background: #F6F7F9;
  .el-scrollbar__thumb {
    background-color: #3395FF;
  }
  &-wrap {
    height: 100%;
    margin: 0;
    padding: 0;
    padding-left: 5px;
    list-style: none;
  }
  &-list {
    position: relative;
    height: 46px;
    cursor: pointer;
    font-size: 13px;
    color: #808080;
    .nav-line {
      position: absolute;
      left: 6px;
      height: 46px;
      border-left: 2px solid #CCCCCC;
    }
    .nav-node {
      position: absolute;
      top: 0;
      width: 14px;
      height: 14px;
      border-radius: 50%;
      border: 2px solid #ffffff;
      background: #CCCCCC;
    }
    .nav-content {
      padding-left: 30px;
      padding-right: 13px;
      line-height: 18px;
      font-size: 14px;
      color: #808080;
    }
    &:last-child .nav-line {
      border: none;
    }
  }
  &-list.active {
    color: #333333;
    .nav-node {
      position: absolute;
      left: -2px;
      width: 20px;
      height: 20px;
      border: 2px solid #C2DFFF;
      background: $activeColor;
      &:after {
        content: '...';
        position: absolute;
        left: 0;
        top: -7px;
        width: 20px;
        height: 20px;
        font-size: 18px;
        color: #ffffff;
      }
    }
    .nav-content {
      color: $activeColor;
    }
    // .nav-line {
    //   border-color: $activeColor;
    // }
  }
  &-list.finished {
    .nav-node {
      left: -2px;
      width: 20px;
      height: 20px;
      border: 2px solid #A7FEAF;
      background: $finishColor;
      &:after {
        content: '✓';
        position: absolute;
        left: 2px;
        top: -2px;
        width: 20px;
        height: 20px;
        font-size: 14px;
        font-weight: bold;
        color: #ffffff;
      }
    }
    .nav-content {
      color: #333333;
    }
    .nav-line {
      border-color: $activeColor;
    }
  }
}
.plugin-content {
  position: relative;
  height: calc(100vh - #{$topHeight} - 60px);
  margin-left: 270px;
  box-sizing: border-box;
  // background: #ffffff;
  overflow: hidden;
  &-back {
    position: absolute;
    top: 0;
    right: 0;
    .el-button--mini {
      border-radius: 0;
    }
  }
  &-list {
    margin: 0;
    padding: 0;
    height: 100%;
    // height: calc(100vh - #{$topHeight} - 140px);
    overflow: auto;
    list-style: none;
    @extend .ts-scrollbar;
    .content-wrap {
      margin-bottom: 20px;
      padding-bottom: 30px;
      background: #fff;
      border-radius: 4px;
      border: 1px solid rgba(0, 0, 0, .06);
      box-shadow: 2px 2px 10px rgba(0, 0, 0, .05);
    }
    .hide-content-btn {
      .el-button {
        display: none;
      }
      .el-button[notHide="true"] {
        display: inline-block;
      }
    }
  }
  &-title {
    margin-bottom: 25px;
    height: 40px;
    line-height: 40px;
    padding-left: 20px;
    font-weight: bold;
    font-size: 13px;
    color: #1A1A1A;
    border-bottom: 1px solid #E5E5E5;
    .text {
      display: inline-block;
      height: 18px;
      line-height: 18px;
      padding-left: 8px;
      font-weight: bold;
      border-left: 3px solid #3395FF;
    }
  }
  &-btns {
    padding: 35px 0;
    text-align: center;
    .el-button--mini {
      padding: 8px 15px;
    }
  }
  .btns-wrap {
    height: 80px;
    padding-top: 30px;
    text-align: center;
  }
  // 修复其他子页面组件样式冲突
  .page-container {
    box-shadow: none;
    margin: 0;
  }
}
.page-component__scroll {
  height: 100%;
}
.page-component__scroll .el-scrollbar__wrap {
  overflow-x: hidden;
  overflow-y: auto;
}
.plugin-content-title+.page-container,
.plugin-content-title+.app-container {
  padding-top: 0;
}
</style>
