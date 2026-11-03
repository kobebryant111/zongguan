import Vue from 'vue'

import Cookies from 'js-cookie'
import 'babel-polyfill'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'

import i18n from './lang' // Internationalization
import './icons' // icon
import './errorLog' // error log
import './permission' // permission control
// import './mock' // simulation data
import 'url-search-params-polyfill'
import * as filters from './filters' // global filters
import md5 from 'js-md5'
import VCharts from 'v-charts'
import NTKOGLOBAL from '@/components/NtkoComponents/global_variable' // ntko

import hasFunc from "@/directive/hasFunc";
import permission from "@/directive/permission";

import {getDictName,getTypeDicts} from '@/utils/dict'
import {checkFunc} from '@/utils/permission'



Vue.use(hasFunc);
Vue.use(permission);

Vue.prototype.getDictName = getDictName
Vue.prototype.getTypeDicts = getTypeDicts
Vue.prototype.checkFunc = checkFunc


// import Print from "@/api/print" //jquery打印插件 
// Vue.use(Print)
Vue.prototype.NTKOGLOBAL = NTKOGLOBAL
// import "lib-flexible"
Vue.prototype.$md5 = md5
Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value) // 国际化
})

Vue.use(VCharts)

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false
/**
 * 全局函数 
 * 
 */
Vue.prototype.$reset = function(formRef, ...excludeFields) {
  this.$refs[formRef].resetFields();
  let obj1 = this.$data;
  let obj2 = this.$options.data.call(this);
  // console.log('reset', obj1, obj2, excludeFields)
  if(!excludeFields || excludeFields.length == 0) {
    excludeFields = ["ruleValidate"];
  }
  for(let attrName in obj1) {
    // console.log('attrName', attrName, excludeFields.includes(attrName))
    if(excludeFields && excludeFields.includes(attrName)) {
      continue;
    }
    obj1[attrName] = obj2[attrName]
  }

}
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  data : {
    Bus:new Vue()
  },
  render: h => h(App)
})
