<!--
 * @Author: amberself
 * @Date: 2020-03-17 17:47:42
 * @LastEditTime : 2020-07-14 20:32:54
 * @LastEditors  : Evan-lian
 * @Description: 顶部导航菜单
 -->
<template>
<div>
    <div class="ts-horizontal-menu" ref="menuRef">
        <ul>
            <li v-for="(menu,key) in routeMenu" :ref="'liRef'+key" :key="menu.id" class="ts-menu-list" @mouseenter="changeImageSrc('hover',key)" @mouseleave="changeImageSrc('',key)">
                <div class="ts-menu">
                    <div class="tsmenu-center">
                        <div class="ts-menu-pic"><img class="ts-menu-icon" :src="getImg(menu.img,key)" /></div>
                        <div class="ts-menu-title">{{ menu.text }}</div>
                    </div>
                </div>
                <div :class="['ts-submenu', {'hide-submenu': status}]" :style="{top:menuHeight+'px'}">
                    <dl class="ts-submenu-item" v-for="submenu in menu.menu" :key="submenu.id">
                        <dt class="ts-submenu-title">{{ submenu.text }}</dt>
                        <dd class="ts-submenu-link" v-for="route in submenu.menu" :key="route.id" :title="route.text" :path="route.url" @click="hideMenu">
                            <!-- <router-link :to="getRouterUrl(route)">{{ route.text }}</router-link> -->
                            <a @click="goTo(route)">{{ route.text }}</a>
                        </dd>
                    </dl>
                </div>
            </li>
        </ul>
    </div>
</div>
</template>

<script>
import {
  mapGetters,
  mapState
} from 'vuex'
import menuImg0 from '@/assets/images/menu/menu_icon20_0_bak.png'
import menuImg1 from '@/assets/images/menu/menu_icon20_1.png'

export default {
  data() {
    return {
      menuImg0,
      menuImg1,
      status: false,
      selectIndex: -1,
      menuHeight: 90
    }
  },
  computed: {
    ...mapGetters(['routeMenu', 'visitedViews']),
    ...mapState({
      'nowThemeInfo': state => state.theme.nowThemeInfo
    })
  },
  mounted() {
    this.getUserRouteMenu()
  },
  methods: {
    // getRouterUrl(route) {
    //   return {
    //     path: route.url === null ? '' : route.url
    //   }
    // },
    goTo(route) {
      const {
        text
      } = route
      const _this = this
      const url = route.url === null ? '' : route.url
      // let condition = false;
      // for (let item of this.visitedViews) {
      //     if (item.module == url && item.backCahinPath.length && item.backCahinPath[0] == url) {
      //         if (item.path != _this.$route.path) {
      //             this.$router.push(url)
      //             // this.$message.warning(`【${text}】页签已存在，请手动切换至所在页签`);
      //             return
      //         }
      //         condition = true;
      //         break
      //     }
      // }
      // if (condition) {
      //     this.$route.meta.stopcache = true;
      //     this.$route.meta.stopcacheself = true;
      // }
      this.$router.push({
        path: url,
        query: { part: url.replace(/\//g, '') }
      })
    },
    getUserRouteMenu() {
      this.$store.dispatch('permission/setRouteMenu')
        .then(res => {})
        .catch(error => {
          console.log('getUserRouteMenu error:', error)
        })
    },
    hideMenu() {
      const _this = this
      this.status = true
      setTimeout(function() {
        _this.status = false
      }, 100)
    },
    getImg(img, index) {
      if (this.selectIndex === index) {
        return menuImg1
      } else {
        if (this.nowThemeInfo === 'white-theme') {
          return menuImg0
        } else {
          return menuImg1
        }
      }
    },
    changeImageSrc(hover, key) {
      if (this.$refs['liRef' + key] && this.$refs['liRef' + key][0].offsetTop) {
        this.menuHeight = this.$refs['liRef' + key][0].offsetTop + 40
      } else {
        this.menuHeight = 90
      }

      if (hover === 'hover') {
        this.selectIndex = key
      } else {
        this.selectIndex = -1
      }
    }
  }
}
</script>
