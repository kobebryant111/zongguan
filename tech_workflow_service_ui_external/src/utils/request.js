/*
@Author: chenhuocan
@Date: 2020-03-10 16:58:33
 * @LastEditors  : Evan-lian
 * @LastEditTime : 2020-07-28 14:36:42
@Description: 基础网络请求类
*/
import axios from 'axios'
import store from '@/store'
import router from '@/router'
import Qs from 'qs'
import { MessageBox, Message, Loading } from 'element-ui'
import common from 'tsvue/lib/utils/ts-common'
import defaultSettings from '@/plugins/settings'

let loading = null
const loadingOptions = {
  lock: true,
  text: '加载中...',
  spinner: 'el-icon-loading',
  background: 'rgba(0, 0, 0, 0)',
  fullscreen: true
}

// 采用token模式
const tokenMode = {
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 30000, /* 请求时间*/
  method: 'post'
}

// 采用session模式
const sessionMode = {
  ...tokenMode,
  headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  transformRequest: [function(data) { // 在请求之前对data传参进行格式转换
    data = Qs.stringify(data)
    return data
  }]
}

const axiosHeader = defaultSettings.enableSession ? sessionMode : tokenMode

function getQueryParam(url, name) {
  var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)')
  if (url.indexOf('?') > 0) {
    var r = url.split('?')[1].match(reg)
    if (r != null) return unescape(r[2])
    return null
  }
  return null
}

function getErrorMsg(errorCode, retryNum) {
  let errMsg = ''
  switch (errorCode) {
    case '10001': {
      if (retryNum == null) {
        errMsg = '用户名错误'
      } else {
        errMsg = '用户名密码错误,还有' + retryNum + '次输入机会'
      }
      break
    }
    case '10002':
      errMsg = '用户已被锁定，请30分钟后再登录'
      break
    case '10003':
      errMsg = '验证码为空或者不正确'
      break
    case '10004':
      errMsg = '该用户已失效，无法登录'
      break
    case '10099':
      errMsg = '请登录'
      break
    case '10005':
      errMsg = '短信验证码不正确或已过期,请重新输入'
      break
    default:
      errMsg = '系统异常，请联系管理员'
  }

  return errMsg
}

const pending = []
const CancelToken = axios.CancelToken
const removePending = (ever) => {
  for (const p in pending) {
    if (pending[p].u === ever.url) {
      pending[p].f()
      pending.splice(p, 1)
    }
  }
}

/* 创建 axios 实例。*/
const service = axios.create(axiosHeader)

/* 请求拦截器*/
service.interceptors.request.use(config => {
  /* 在发送请求之前执行某些操作*/

  config.cancelToken = new CancelToken((c) => {
    pending.push({ u: config.url, f: c })
  })
  config.url ? '' : removePending(config)
  if (store.getters.token) {
    // 让每个请求携带令牌token
    config.headers['token'] = common.tansunGetToken()
  }

  // 扩展$request方法，改变method方式，和传参模式
  const { type = 'post', paramMode = 'data', formdata = false } = config
  const methodArray = ['get', 'post']
  const modeArray = ['data', 'params']

  if (methodArray.includes(type)) {
    config.method = type
  }
  if (modeArray.includes(paramMode)) {
    config[paramMode] = config.data
  }

  // 是否需要loading 默认true
  let showLoading = true
  if (config.loading !== undefined) {
    showLoading = config.loading
  }
  if (showLoading) {
    loading = Loading.service(loadingOptions)
  }

  // token 模式下 使用 formdata
  if (!defaultSettings.enableSession && formdata) {
    config.headers = Object.assign(config.headers, { 'Content-Type': 'application/x-www-form-urlencoded' })
    config.transformRequest = [function(data) { // 在请求之前对data传参进行格式转换
      data = Qs.stringify(data)
      return data
    }]
  }

  return config
},
error => {
  // d对请求错误执行某些操作。
  return Promise.reject(error)
})

/* 响应拦截器*/ // 涉及的多套后台返回的格式了。为什么不能统一呢
service.interceptors.response.use(response => {
  loading && loading.close()
  if (response.config.responseType === 'blob') {
    // 过滤blob 流的下载
    return response
  }
  // 如果要获取 http 信息，如标头或状态。在这边操作
  const res = response.data
  if (store.getters.enableSession) {
    const responseURL = response.request.responseURL
    if (responseURL.indexOf('/login') > 0) {
      const errorCode = getQueryParam(responseURL, 'error')
      const retryNum = getQueryParam(responseURL, 'retryNum')
      if (errorCode !== null && errorCode !== undefined && errorCode !== '') {
        const errMsg = getErrorMsg(errorCode, retryNum) // 获取错误信息
        Message({
          message: errMsg,
          type: 'error',
          duration: 3 * 1000
        })
        return Promise.reject(errMsg)
      } else {
        if (store.getters.userSession) {
          store.dispatch('user/resetSession').then(() => {
            location.reload()
          })
        }
      }
    } else if (responseURL.indexOf('/index') > 0) {
      return res
    }
    if (res.code !== '0' || res.status !== 200) {
      // 当status为100 弹框通知用户描述异常
      if ((res.status === 100 || res.status === 403) && res.description) {
        Message({
          message: res.description || 'Error',
          type: 'error',
          duration: 3 * 1000
        })
        return Promise.reject(new Error(res.description || 'Error'))
      } else {
        return res
      }
    } else {
      return res
    }
  } else {
    if (res.code !== '0' && res.status !== 200) {
      // 当status为100 弹框通知用户描述异常
      if (res.status === 100 && res.description) {
        // store.commit('helpCenter/SET_CATCHEXCEPTIONLOG', res)
        // router.push('/exceptionPrompt/index')
        Message({
          message: res.description,
          type: 'error',
          duration: 3 * 1000
        })
        return Promise.reject(new Error(res.description || 'Error'))
      } else {
        if (res.code === '1') {
          Message({
            message: res.msg,
            type: 'error',
            duration: 3 * 1000
          })
          return Promise.reject(new Error(res.msg || 'Error'))
        } else {
          Message({
            message: res.msg ? res.msg : 'Error',
            type: 'error',
            duration: 3 * 1000
          })
          return Promise.reject(new Error(res.description || 'Error'))
        }
      }
    } else {
      return res
    }
  }
},
error => {
  loading && loading.close()
  // session模式报错
  if (!error.response) {
    if (error.message === 'Network Error') {
      Message({
        message: '服务器连接失败',
        type: 'error',
        duration: 3 * 1000
      })
      store.dispatch('user/resetSession').then(() => {
      })
      return Promise.reject(error)
    }
    // 解决信息信息是空的时候 让他不提示
    if (error.message !== undefined) {
      Message({
        message: error,
        type: 'error',
        duration: 3 * 1000
      })
    }
    return Promise.reject(error)
  }

  // token模式报错
  if (error.response.status === 401) {
    // 无权限
    if (error.response.data.code === 1005) {
      MessageBox.alert(error.response.data.msg, '提示')
    }
    // token过期
    if (error.response.data.code === 4001) {
      MessageBox.confirm('您的Token已过期，您可以取消以停留在此页，或重新登录', '确认注销', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    }
  } else {
    Message({
      message: error.response && error.response.data && error.response.data.message || error.response && error.response.statusText || 'Error',
      type: 'error',
      duration: 3 * 1000
    })
  }
  return Promise.reject(error)
})

export default service

