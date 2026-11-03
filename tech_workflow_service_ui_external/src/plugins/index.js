/*
@Author: chenhuocan
@Date: 2020-03-10 17:01:00
 * @LastEditors: your name
 * @LastEditTime: 2020-04-01 20:58:16
@Description: 全局注册插件，样式，组件
*/
import Vue from 'vue'

/* ---------开始引用本地插件--------*/
import './permission' // 权限控制
import '@/styles/index.scss'// 全局样式(主题)入口

/* ---------结束引用本地插件--------*/

/* ---------开始引用第三方插件--------*/
// 使用例子
import VueTour from 'vue-tour' // 指引组件
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)
Vue.use(VueTour)

/* ---------结束引用第三方插件--------*/

/* ---------其他方法--------*/
// 获取svg图片资源
const req = require.context('@/assets/icons', true, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
