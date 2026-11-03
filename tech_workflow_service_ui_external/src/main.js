/*
 * @Author: Evan-lian
 * @Date: 2020-03-10 11:02:04
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-01 10:21:14
 * @Description:
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'normalize.css/normalize.css' // CSS 复位的现代替代方案
import '@/plugins/index'// 第三方插件 管理入口
import i18n from '@/plugins/lang/index' // 多语言设置
import '@/directives/index' // 注册全局自定义指令

import TansunComponents from '@/components/index' // tansun组件
Vue.use(TansunComponents)

// 引入大数据表格插件
import plTable from 'pl-table'
import 'pl-table/themes/index.css'
Vue.use(plTable)

// tansun组件库
import 'tsvue/lib/tstheme/index.css'
import 'tsvue/lib/tstheme/common/iconfont/iconfont.css'
import '@/styles/workflow/index.css'

import '@/assets/iconfont/iconfont.css'

import tsVue from 'tsvue'
Vue.use(tsVue)

// 全局request
import request from '@/utils/request'
Vue.prototype.$request = request
Vue.prototype.tpnode = null// 存储缓存的执行的上下文环境

// 全局api
import api from '@/api/api'
Vue.prototype.$api = api

Vue.config.productionTip = false

import singleSpaVue from 'single-spa-vue'
const vueOptions = {
  router,
  store,
  i18n,
  render: h => h(App)
}
if (!window.singleSpaNavigate) {
  // 单体
  Vue.prototype.micro = false
  new Vue(vueOptions).$mount('#app')
} else {
  // 微前端
  vueOptions.el = '#workflow'
  Vue.prototype.micro = true
  vueOptions.mounted = function() {
    $(this.$el).parent().css('height', '768px')
    this.$store.dispatch('user/login', {
      username: '',
      password: '',
      captcha: '',
      msgAuthCode: ''
    }).then(() => {
      if (this.$route.query.redirect) {
        this.$router.push({ path: this.$route.query.redirect || '/' })
      }
      this.loading = false
    }).catch(() => {
      this.loading = false
    })
  }
}
const vueLifecycles = singleSpaVue({
  Vue,
  appOptions: vueOptions
})

export const bootstrap = vueLifecycles.bootstrap
export const mount = vueLifecycles.mount
export const unmount = vueLifecycles.unmount
export default vueLifecycles


