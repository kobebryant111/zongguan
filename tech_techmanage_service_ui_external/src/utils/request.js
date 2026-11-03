import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken, getSecretToken } from '@/utils/auth'
import common from "@/api/common"

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 140000, // request timeout
  validateStatus: status => { // 200、400、500
    return status == 200 || status == 400 || status == 500
  }
})

function handerDownQuery(config){
  //是否下载
  var isDown = false;
  if(config.params&&config.params._isDown=='Y'){
    isDown = true;
  }else if(config.data&&config.data._isDown=='Y'){
    isDown = true;
  }
  if(isDown){
    config.responseType = 'blob'
  }
  console.info('handerDownQuery----',config.responseType)
}

// request interceptor
service.interceptors.request.use(
  config => {

    // Do something before request is sent
    if (store.getters.token) {
      // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
      config.headers['X-Token'] = getToken()
      config.headers['secretkey'] = getSecretToken()
    }
    config.url = config.url + (config.url.indexOf('?') > 0 ? '&' : '?') + 't=' + Date.now()
    // 过滤 null
    try {
      let ifJSON = !common.isJson(config.data);
      if (config.method === 'post' && config.data && ifJSON) {
        let isArr = common.isAo(config.data)
        if (isArr) {
        } else {
          let data = new FormData();
          for (let ii of config.data.entries()) {
            if (ii[1] != 'null') {
              data.append(ii[0], ii[1]);
            }
          }
          config.data = data;
        }
      }
    } catch (error) {
      console.log('error', error)
    }
    handerDownQuery(config)
    // 防止IE浏览器get请求缓存
    // if(config.method === 'get' && config.params) {
    //   config.params.t = Date.now() / 1000
    // } else if(config.method === 'post') {

    // } // post end
    // console.log('method', config)
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)


// response interceptor
// 返回报文拦截器 200/400/500 状态码走response 其他走error
service.interceptors.response.use(response => {
  if (response.data.code == '403') {
    MessageBox.confirm(response.data.message, {
      confirmButtonText: '重新登录',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      store.dispatch('FedLogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    })
  }
  return response
},
  error => {
    // if (error.response.data.code === 4001) {
    //   MessageBox.confirm(response.data.message, {
    //     confirmButtonText: '重新登录',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     store.dispatch('FedLogOut').then(() => {
    //       location.reload() // 为了重新实例化vue-router对象 避免bug
    //     })
    //   })
    // } else {
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    // }
    return Promise.reject(error)
  }
)

export default service
