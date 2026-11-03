import request from '@/utils/request'
import { stringify } from 'querystring'
import XLSX from 'xlsx'

export default {

  /**
 *  日期格式化
 * @param {} dateTime
 */
  dateFormat(dateTime) {
    const date = new Date(dateTime)
    const str = date.getUTCFullYear() + '-' +
      (date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)) + '-' +
      (date.getDate() > 9 ? date.getDate() : '0' + date.getDate()) + ' ' +
      (date.getHours() > 9 ? date.getHours() : '0' + date.getHours()) + ':' +
      (date.getMinutes() > 9 ? date.getMinutes() : '0' + dage.getMinutes()) + ':' +
      (date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds())
    return str
  },
  dateFormatOv(dateTime) {
    const date = new Date(dateTime)
    const str = date.getUTCFullYear() + '-' +
      (date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)) + '-' +
      (date.getDate() > 9 ? date.getDate() : '0' + date.getDate()) + ' ' +
      (date.getHours() > 9 ? date.getHours() : '0' + date.getHours()) + ':' +
      (date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes()) + ':' +
      (date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds())
    return str
  },
  dateFormat(dateTime, fmt) {
    if (dateTime.indexOf('T') !== -1) {
      dateTime = (dateTime.replace('T', ' ')).substr(0, dateTime.indexOf('.'))
    }
    const date = new Date(dateTime)
    const o = {
      'M+': date.getMonth() + 1, // 月份
      'd+': date.getDate(), // 日
      'H+': date.getHours(), // 小时
      'm+': date.getMinutes(), // 分
      's+': date.getSeconds(), // 秒
      'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
      'S': date.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (const k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
      }
    }
    return fmt
  },

  // 时间戳格式化
  dateFormatStamp(dateTime, fmt) {
    // if(dateTime.indexOf('T') != -1) {
    //   dateTime = (dateTime.replace('T',' ')).substr(0,dateTime.indexOf('.'));
    // }
    const date = new Date(dateTime)
    const o = {
      'M+': date.getMonth() + 1, // 月份
      'd+': date.getDate(), // 日
      'H+': date.getHours(), // 小时
      'm+': date.getMinutes(), // 分
      's+': date.getSeconds(), // 秒
      'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
      'S': date.getMilliseconds() // 毫秒
    }
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
    }
    for (const k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
      }
    }
    return fmt
  },
  /**
   * 参数格式化 供请求使用
   * @param {*} params
   */
  paramsFormat(params) {
    const searchParmas = new URLSearchParams()
    const data = params || {}
    if (data) {
      for (const item in data) {
        const element = data[item]
        searchParmas.append(item, element)
      }
    }
    return searchParmas
  },
  /**
   *  JSON 转 map
   * @param {*} obj
   */
  objToStrMap(obj) {
    const strMap = new Map()
    for (const key in Object.keys(obj)) {
      strMap.set(key, obj[key])
    }
    return strMap
  },

  /**
   * 清理form表单参数
   * @param {} param
   */
  cleanFrom(param) {
    Object.keys(param).forEach(key => (param[key] = ''))
  },
  /**
     * 文件下载
     * @param {数据} data
     * @param {文件名称} fileName
     */
  download(data, self, fileName) {
    console.log('文件：', data)
    if (!data) {
      return
    }
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
      // 兼容IE/Edge
      navigator.msSaveBlob(data, fileName)
    } else {
      const url = window.URL.createObjectURL(new Blob([data], { type: 'application/vnd.ms-excel' }))
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      window.URL.revokeObjectURL(link.href)
      document.body.removeChild(link)
    }
    self.$message.success('下载成功')
  }, // 文件下载 end

  // 面试流程简历下载
  downloadJl(docId, self) {
    request({
      url: '/ppmDocument/checkedDownloadAuth?documentno=' + docId,
      method: 'get'
    }).then(res => {
      if (res.data.status !== 200) {
        self.$alert(res.data.message, { type: 'error' })
        return false
      }
      const url = '/techapi/ppmInterviewApply/download/' + docId
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.setAttribute('download', '文件.xlsx')
      link.setAttribute('target', '_back')
      document.body.appendChild(link)
      link.click()
      console.log('link', link)
      window.URL.revokeObjectURL(link.href)
      document.body.removeChild(link)
    })
  },

  // 入职流程文件下载
  downloadDoc(docId, self) {
    const url = '/techapi/ppmDocument/download/' + docId
    console.log('文档下载', url, docId)
    const link = document.createElement('a')
    link.style.display = 'none'
    link.href = url
    link.setAttribute('download', '文件.xlsx')
    link.setAttribute('target', '_back')
    document.body.appendChild(link)
    link.click()
    window.URL.revokeObjectURL(link.href)
    document.body.removeChild(link)
  },

  // 入职流程文件下载
  onKeyDownload(instanceId, self) {
    const url = '/techapi/ppmOutsourcingAccessapply/download/' + instanceId
    console.log('文档下载', url, instanceId)
    const link = document.createElement('a')
    link.style.display = 'none'
    link.href = url
    link.setAttribute('download', '文件.zip')
    link.setAttribute('target', '_back')
    document.body.appendChild(link)
    link.click()
    window.URL.revokeObjectURL(link.href)
    document.body.removeChild(link)
  },

  /**
   * 提示流程信息
   */
  getFlowMessge(msg) {
    // let msg = "流程ID:1244492未结束,当前环节:二级部主持工作经理审批,当前活动定义ID:manualActivity1,参与者: 080054,周坚";
    let message
    if (msg == null || msg.length <= 0) {
      // this.$alter('流程状态未查询到', { type: 'error' })
      // return false;
      message = '流程状态未查询到'
      return message
    }
    if (msg.split(':').length === 2) {
      // this.$alter('流程' + msg.substring(msg.length - 3), { type: 'error' })
      // return false
      message = '流程' + msg.substring(msg.length - 3)
      return message
    }
    const newMsg = '流程未结束,下一个环节：'
    const msgs = msg.split(',')
    // 下一个环节
    const nextNodes = msgs[1].split(':')
    // 参与者
    const participants = msgs[3]
    const split_participants = msg.substring(msg.indexOf('参与者:') + 4, msg.length).trim()
    console.log('split_participants::', split_participants)
    const temps_cyz = split_participants.split(' ')
    const new_participants = []
    for (let i = 0; i < temps_cyz.length; i++) {
      const users = temps_cyz[i].split(',')
      const temps = users[1] + '(' + users[0] + ')'
      new_participants.push(temps)
    }
    const msg_info = newMsg + nextNodes[1] + ',参与者：' + new_participants.join(',')
    // this.$alter(msg_info, { type: 'success' })

    return msg_info
  },

  /**
   * 提示流程信息
   */
  getFlowMessgeNew(msg) {
    // let msg = "流程ID:null未结束,当前环节:二级部经理审批,当前活动定义ID:UserTask_8MS5Xatm,参与者:190820-马万乐";
    let message
    if (msg == null || msg.length <= 0) {
      // this.$alter('流程状态未查询到', { type: 'error' })
      // return false;
      message = '流程状态未查询到'
      return message
    }
    if (msg.split(':').length === 2) {
      // this.$alter('流程' + msg.substring(msg.length - 3), { type: 'error' })
      // return false
      message = '流程' + msg.substring(msg.length - 3)
      return message
    }
    const newMsg = '流程未结束,下一个环节：'
    const msgs = msg.split(',')
    // 下一个环节
    const nextNodes = msgs[1].split(':')
    // 参与者
    const split_participants = msg.substring(msg.indexOf('参与者:') + 4, msg.length).trim()
    console.log('split_participants::', split_participants)
    const temps_cyz = split_participants.split('-')
    const new_participants = []
    if (temps_cyz.length > 0) {
      const ids = temps_cyz[0].split(',')
      const names = temps_cyz[1].split(',')
      for (let i = 0; i < ids.length; i++) {
        const temps = ids[i] + '(' + names[i] + ')'
        new_participants.push(temps)
      }
    }
    const msg_info = newMsg + nextNodes[1] + ',参与者：' + new_participants.join(',')
    return msg_info
  },

  /**
   * 显示遮罩层
   */
  loading(_this) {
    const loading = _this.$loading({
      lock: true,
      text: '请稍等',
      spinner: 'el-icon-loading',
      background: 'rgba(0,0,0,0.7)'
    })
    return loading
  },

  // 获取身份证号码
  getBirthdayFromIdCard(idCard) {
    var brithday = ''
    if (idCard != null && idCard !== '') {
      if (idCard.length === 15) {
        brithday = '19' + idCard.substr(6, 6)
      } else if (idCard.length === 18) {
        brithday = idCard.substr(6, 8)
      }
      brithday = brithday.replace(/(.{4})(.{2})/, '$1-$2-')
    }
    return brithday
  },

  /**
    * 获取blob
    * @param {String} url 目标文件地址
    * @return {Promise}
    */
  getBlob(url) {
    return new Promise(resolve => {
      const xhr = new XMLHttpRequest()
      xhr.open('GET', url, true)
      xhr.responseType = 'blob'
      xhr.onload = () => {
        if (xhr.status === 200) {
          resolve(xhr.response)
        }
      }
      xhr.send()
    })
  },

  /**
      * 获取blob
      * @param {Blob} blob
      * @return {String} filename 想要保存的文件名称
      */
  saveAs(blob, filename) {
    console.log('下载测试', blob)
    if (window.navigator.msSaveOrOpenBlob) {
      navigator.msSaveBlob(blob, filename)
    } else {
      const link = document.createElement('a')
      const body = document.querySelector('body')
      // link.href = window.URL.createObjectURL(blob);
      link.href = blob
      link.download = filename
      link.style.display = 'none'
      body.appendChild(link)
      link.click()
      body.removeChild(link)
      window.URL.revokeObjectURL(link.herf)
    }
  },

  /**
    * 获取blob
    * @param {String} url 目标文件地址
    * @return {String} filename 想要保存的文件名称
    */
  downloadCommon(url, filename) {
    this.getBlob(url).then(blob => {
      this.saveAs(blob, filename)
    })
  },

  // 检查身份证号最后一位
  checkSfzhLast(sfzh) {
    const index_array = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
    const sfzh_array = new Array()
    let total = 0
    let last_number
    const start = 0
    const end = 1
    for (let i = 0; i < sfzh.length - 1; i++) {
      const temp = sfzh.slice(start + i, end + i)
      sfzh_array.push(temp)
    }

    for (let i = 0; i < index_array.length; i++) {
      const temp = sfzh_array[i] * index_array[i]
      total = total + temp
    }

    switch (total % 11) {
      case 0:
        last_number = 1
        break
      case 1:
        last_number = 0
        break
      case 2:
        last_number = 'X'
        break
      case 3:
        last_number = 9
        break
      case 4:
        last_number = 8
        break
      case 5:
        last_number = 7
        break
      case 6:
        last_number = 6
        break
      case 7:
        last_number = 5
        break
      case 8:
        last_number = 4
        break
      case 9:
        last_number = 3
        break
      case 10:
        last_number = 2
        break
    }

    if (sfzh.slice(17, 18) == last_number) {
      return true
    } else {
      return false
    }
  },

  // 判断是否JSON格式
  isJson(str) {
    if (typeof str === 'string') {
      try {
        const obj = JSON.parse(str)
        if (obj && typeof obj === 'object') {
          return true
        } else {
          return false
        }
      } catch (e) {
        console.log('$isJSon error', e)
        return false
      }
    } else {
      return false
    }
  },

  // 判断数组和对象
  isAo(v) {
    const da = Array.isArray(v)
    if (da === true) {
      return true
    } else {
      return false
    }
  },

  // JS乘法精度问题
  accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString()
    try {
      m += s1.split('.')[1].length
    } catch (e) { }
    try {
      m += s2.split('.')[1].length
    } catch (e) { }
    return Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m)
  },
  // 消息提示
  messageNotify(messageVal, self) {
    const messageSec = messageVal.slice(2).join('，')
    self.$notify({
      title: '提示',
      dangerouslyUseHTMLString: true,
      message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
      duration: 0
    })
  },

  // JS减法精度丢失问题
  numSub(num1, num2) {
    var baseNum, baseNum1, baseNum2
    var precision // 精度
    try {
      baseNum1 = num1.toString().split('.')[1].length
    } catch (e) {
      baseNum1 = 0
    }
    try {
      baseNum2 = num2.toString().split('.')[1].length
    } catch (e) {
      baseNum2 = 0
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2))
    precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2
    return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision)
  },

  // JS加法精度丢失问题
  numAdd(num1, num2) {
    var baseNum, baseNum1, baseNum2
    try {
      baseNum1 = num1.toString().split('.')[1].length
    } catch (e) {
      baseNum1 = 0
    }
    try {
      baseNum2 = num2.toString().split('.')[1].length
    } catch (e) {
      baseNum2 = 0
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2))
    return (num1 * baseNum + num2 * baseNum) / baseNum
  },

  // JS除法精度问题
  accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2
    try {
      t1 = arg1.toString().split('.')[1].length
    } catch (e) {}
    try {
      t2 = arg2.toString().split('.')[1].length
    } catch (e) {}
    r1 = Number(arg1.toString().replace('.', ''))
    r2 = Number(arg2.toString().replace('.', ''))
    return (r1 / r2) * Math.pow(10, t2 - t1)
  }

}
