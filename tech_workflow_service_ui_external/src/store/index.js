/*
@Author: chenhuocan
@Date: 2020-03-10 17:02:41
@LastEditors:
@LastEditTime:
@Description: Vuex入口
* */

import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'

Vue.use(Vuex)

// you do not need `import app from './modules/app'`
// it will auto require all vuex module from modules file
const modulesFiles = require.context('./modules', true, /\.js$/)
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // set './app.js' => 'app'
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})

export default new Vuex.Store({
  getters,
  modules
})
