/*
 * @Author: Evan-lian
 * @Date: 2020-03-12 16:26:20
 * @LastEditors: amberself
 * @LastEditTime: 2020-07-14 23:00:18
 * @Description:
 */

import TsMultiDialogs from '@/components/TsMultiDialogs/index'
import TsTooltip from '@/components/TsTooltip/index'
import TsMultiBigTab from '@/components/TsMultiTab/index'
import MultiStep from '@/components/TsMultiStep/index'
import TsTrees from '@/components/TsTrees/index'

const components = [
  TsMultiDialogs,
  TsTooltip,
  TsMultiBigTab,
  MultiStep,
  TsTrees
]

const TansunComponents = function(Vue) {
  components.forEach(component => {
    Vue.component(component.name, component)
  })
}

export default TansunComponents
