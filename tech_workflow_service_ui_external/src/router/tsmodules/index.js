/*
@Author: chenhuocan
@Date: 2020-03-11 19:46:54
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2020-06-28 11:23:26
@Description: 路由入口，合并所有天阳模块的路由，包括帮助中心，系统管理
*/

import helpCenterRouter from './ts-help-center'
import resourcesRouter from './ts-resources'
import systemManageRouter from './ts-system-manage'
import vuedemo from './ts-demo'
import { workflowRouter } from './wf'
const routers = Object.assign({}, helpCenterRouter, resourcesRouter,
  systemManageRouter, vuedemo, workflowRouter)
export default routers
