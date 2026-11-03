import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import errorLog from './modules/errorLog'
import permission from './modules/permission'
import tagsView from './modules/tagsView'
import user from './modules/user'
import appComponentConfig from './modules/appComponentConfig'
import getters from './getters'
import wbryMessage from './modules/wbryMessage' //外包人员信息
import keepAlive from './modules/keepAlive' //路由动态缓存
import dict from './modules/dict' //数据字典缓存

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    errorLog,
    permission,
    tagsView,
    user,
    appComponentConfig,
    wbryMessage,
    keepAlive,
    dict
  },
  getters
})

export default store
