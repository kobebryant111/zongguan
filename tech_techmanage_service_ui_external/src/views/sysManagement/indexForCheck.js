/**
 * 系统复核组件索引
 * auth 161206
 */

import userAuthDetail from "./userAuth.vue"; // 客群详情



// 复核详情组件
export const systemCheckComponents = {
  userAuthDetail,
}

// 复核详情组件映射
export const systemCheckMapping = {
  "IM001003003" : "userAuthDetail",
  "IM001003002" : "userAuthDetail",
  "IM001003001" : "userAuthDetail"
}