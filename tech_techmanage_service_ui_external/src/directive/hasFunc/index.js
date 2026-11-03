import hasFunc from './hasFunc'

const install = function(Vue) {
  Vue.directive('hasFunc', hasFunc)
}

if (window.Vue) {
  window['hasFunc'] = hasFunc
  Vue.use(install); // eslint-disable-line
}

hasFunc.install = install
export default hasFunc

