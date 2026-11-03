/*
 * @Author: amberself
 * @Date: 2020-03-31 16:16:58
 * @LastEditors: Kinson
 * @LastEditTime: 2020-04-18 20:01:48
 * @Description:
 */

import defaultSettings from '@/plugins/settings'

const sessionMode = defaultSettings.enableSession

const dev_token = {
  gatewayUrl: '',
  url: '',
  commonServiceUrl: 'workflow-micro-service'
}

const dev_session = {
  gatewayUrl: '',
  url: '',
  commonServiceUrl: ''
}

const prod_token = {
  gatewayUrl: '',
  url: '',
  commonServiceUrl: 'workflow-micro-service'
}

const prod_session = {
  gatewayUrl: '',
  url: '',
  commonServiceUrl: ''
}

const dev = sessionMode ? dev_session : dev_token
const prod = sessionMode ? prod_session : prod_token

export const config = {
  dev,
  prod
}
