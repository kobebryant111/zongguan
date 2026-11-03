/*
 * @Author: amberself
 * @Date: 2020-04-01 20:52:24
 * @LastEditors: your name
 * @LastEditTime: 2020-04-03 12:43:39
 * @Description:
 */
import request from '@/utils/request'
import { config } from '../index'
const preLink = process.env.NODE_ENV === 'production' ? config.prod.commonServiceUrl : config.dev.commonServiceUrl

/**
 * 根据登录名查询向导标识, 以判断是否首次登录，或者是否是新增功能后的首次登录
 * @params {string} 当前账号的loginName
 * @returns {object|number} 返回null表示首次登录，返回数字表示登录过
 */
export function fetchGuideFlag(params) {
  return request({
    url: `${preLink}/helpGuideFlagAction/selectByLoginName`,
    method: 'get',
    params
  })
}

/**
 * 新增或更新向导标识对象，当ID为空时是新增，当ID不为空时是修改。
 * 修改时，ID来自/helpGuideFlagAction/selectByLoginName查询的对象的ID属性。
 * 当向导展示给用户后，要执行该方法来新增或更新向导标识。
 * @params {string} 当前账号的USER_ID
 * @returns {object}
 */
export function updateGuideFlag(params) {
  return request({
    url: `${preLink}/helpGuideFlagAction/saveOrUpdate`,
    method: 'post',
    data: params
  })
}
// ///////////异常/////////////
/**
 * 查询定义异常的信息，返回对象
 */
export function fetchDefExceptionList() {
  return request({
    url: `${preLink}/helpDefExceptionAction/findDefExceptionList`,
    method: 'get'
  })
}

/**
 * 根据异常代码查询异常信息
 * @params {string} mName模块名称
 */
export function fetchDefExceptionByCode(params) {
  return request({
    url: `${preLink}/helpDefExceptionAction/getDefExceptionByCode`,
    method: 'get',
    data: params
  })
}

/**
 * 查询{id}异常信息
 */
export function fetchDefExceptionById(id) {
  return request({
    url: `${preLink}/helpDefExceptionAction/${id}`,
    method: 'get'
  })
}

/**
 * 根据异常代码新增异常信息，异常代码存在则修改，不存在则新增
 */
export function updateExceptionDefine(data) {
  return request({
    url: `${preLink}/helpDefExceptionAction/saveOrUpdate`,
    method: 'post',
    data: data
  })
}

/**
 * 根据异常id删除异常信息
 * @params {string} code代码
 */
export function deleteDefExceptionByCode(params) {
  return request({
    url: `${preLink}/helpDefExceptionAction/deleteDefExceptionByCode`,
    method: 'post',
    data: params
  })
}

// ////////////反馈///////////////
/**
 * 根据模块名称查询反馈信息
 * @param params
 */
export function getFeedbackByMName(params) {
  return request({
    url: `${preLink}/helpFeedbackAction/getFeedbackByMName`,
    method: 'get',
    params: params
  })
}

/**
 * 查询反馈所有反馈信息
 * @param params
 */
export function findFeedbackList(params) {
  return request({
    url: `${preLink}/helpFeedbackAction/findFeedbackList`,
    method: 'get',
    params: params
  })
}

/**
 * 查询{id}反馈信息
 * @param id
 */
export function findFeedbackByID(id) {
  return request({
    url: `${preLink}/helpFeedbackAction/${id}`,
    method: 'get'
  })
}

/**
 * 根据反馈id删除反馈信息
 * @param params
 */
export function deleteFeedbackById(params) {
  return request({
    url: `${preLink}/helpFeedbackAction/deleteFeedbackById`,
    method: 'post',
    params: params
  })
}

/**
 * 反馈异常信息
 * @param data
 */
export function saveFeedbackInfo(data) {
  return request({
    url: `${preLink}/helpFeedbackAction/saveOrUpdate`,
    method: 'post',
    data
  })
}

// ////////////提示///////////////
/**
 * 根据提示代码查询提示信息，并将参数填充到提示信息里
 * @param params
 */
export function getTipByCode(params) {
  return request({
    url: `${preLink}/helpTipsAction/getTipByCode`,
    method: 'get',
    params: params
  })
}

/**
 * 查询所有提示信息，返回对象集合
 */
export function findTipList() {
  return request({
    url: `${preLink}/helpTipsAction/findTipList`,
    method: 'get'
  })
}

/**
 * 根据提示代码新增提示信息，提示代码存在则修改，不存在则新增
 * @param params
 */
export function saveTipByCode(params) {
  return request({
    url: `${preLink}/helpTipsAction/saveTipByCode`,
    method: 'post',
    params: params
  })
}

/**
 * 根据提示代码修改提示信息
 * @param params
 */
export function updateTipByCode(params) {
  return request({
    url: `${preLink}/helpTipsAction/updateTipByCode`,
    method: 'post',
    params: params
  })
}

/**
 * 根据提示代码删除提示信息
 * @param params
 */
export function deleteTipByCode(params) {
  return request({
    url: `${preLink}/helpTipsAction/deleteTipByCode`,
    method: 'post',
    params: params
  })
}
