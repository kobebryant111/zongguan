/*
 * @Author: amberself
 * @Date: 2020-03-31 16:16:59
 * @LastEditors: amberself
 * @LastEditTime: 2020-05-30 16:22:45
 * @Description:
 */
module.exports = {
  /**
   * @type {boolean} true | false
   * @description Whether show the settings right-panel
   */
  showSettings: true,

  /**
   * @type {boolean} true | false
   * @description Whether need tagsView
   */
  tagsView: true,

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: false,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production',
  /**
   * 使用session模式 设置为true, 默认false 使用token机制
   */
  enableSession: false,
  /**
   * 最大打开标签数
   */
  maxTags: 8,
  /**
   * @type {boolean} true | false
   * @description 是否缓存页签执行keepAlive
   */
  keepAlive: true
}
