import request from '@/utils/request'
import { formDataHeaders, headers } from './api_header_conf'
import { type } from 'os';

export default {

  /**
 * 加载菜单树
 */
  loadPermissions(param){
    return request({
      headers,
      url: '/permiss/' + param,
      method: 'get',
    })
  },

  /**
   * 添加权限，提交
   * @param {*} data 
   * @param {*} rid 
   */
  permissionsSubmit(data,rid){
    return request({
      headers,
      url: '/permiss/add/' + rid,
      data : data,
      method: 'post',
      type : "json"
    })
  }


}