import request from '@/utils/request'
import { formDataHeaders, headers } from './api_header_conf'
import { type } from 'os';

export default {

  /**
 * 加载菜单树
 */
  loadMenu(){
    return request({
      headers,
      url: '/menu/loadMenu',
      method: 'get',
    })
  },


  /**
 * 添加菜单
 * @param {*} data 
 */
  addMenu(data){
    return request({
      headers,
      url: '/menu/add',
      method: 'post',
      data : data,
      type : "json"
    })
  },

/**
 * 根据菜单ID获得菜单
 * @param {*} param 
 */
  getMenuByID(param){
    return request({
      headers,
      url: '/menu/find/' + param,
      method: 'get'
    })
  },

  /**
   * 删除菜单
   * @param {*} param 
   */
  deleteMenu(param){
    return request({
      headers,
      url : "/menu/delete/" + param,
      method : 'delete'
    })
  },

  /**
   * 更新菜单
   * @param {*} data 
   */
  updateMenu(data){
    return request({
      headers,
      url: '/menu/update',
      method: 'post',
      data : data,
      type : "json"
    })
  },
  /**
   * 添加菜单日志
   * @param {*} data 
   */
  addMenuLog(data) {
    return request({
      url : '/ppmMenuLog/addMenuLog',
      method : 'post',
      data
    })
  },
  /**
   * 查询分页菜单日志
   * @param {*} data 
   */
  menuLogPage(data) {
    return request({
      url : '/ppmMenuLog/queryPager',
      method : 'post',
      data
    })
  }
}