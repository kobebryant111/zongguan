import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg组件
import RightToolbar from '@/components/RightToolbar'
import EditCell from '@/components/EditCell'
// register globally
Vue.component('svg-icon', SvgIcon)
Vue.component('RightToolbar', RightToolbar)
Vue.component('EditCell', EditCell)

const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
