import { config } from './index'

const preLink = process.env.NODE_ENV === 'production' ? config.prod.url : config.dev.url

export default {
  // ///////////用户管理/////////////
  // 查询用户分页列表接口
  queryUsermg: `${preLink}/usermg/query`,
  // 根据用户id查询一条用户信息
  fetchUsermg: `${preLink}/usermg/get`,
  // 新增 编辑 生效(statusCd=1) 失效(statusCd=2)
  usermgSave: `${preLink}/usermg/save`,
  // 删除
  usermgDelete: `${preLink}/usermg/delete`,
  // 重置用户密码
  usermgResetPassword: `${preLink}/usermg/resetPassword`,

  // ///////////文章管理/////////////
  // 查询文章列表
  queryArticle: `${preLink}/sysArticle/query`,
  // 新增、修改文章
  saveArticle: `${preLink}/sysArticle/save`,
  // 删除文章
  deleteArticle: `${preLink}/sysArticle/delete`,
  // 查看文章详情
  getArticle: `${preLink}/sysArticle/get`,
  // 文章撤回
  retractArticle: `${preLink}/sysArticle/retract`,
  // 文章发布
  publishArticle: `${preLink}/sysArticle/publish`,

  // ///////////租户管理/////////////
  // 查询租户
  queryTenant: `${preLink}/tenant/query`,
  // 删除租户
  deleteTenant: `${preLink}/tenant/delete`,
  // 获取租户信息
  getTenant: `${preLink}/tenant/get`,
  // 租户-失效
  invalidTenant: `${preLink}/tenant/invalid`,
  // 获取新增修改-生效
  effectTenant: `${preLink}/tenant/effect`,
  // 获取新增修改-保存
  saveTenant: `${preLink}/tenant/save`,
  // 租户菜单树
  getTenantMenus: `${preLink}/tenant/roleMenu/menuTree`,
  // 租户分配菜单
  saveTenantMenus: `${preLink}/tenant/roleMenu/save`,

  // ///////////角色管理/////////////
  // 查询角色树
  roleTree: `${preLink}/role/roleTree`,
  // 保存角色
  roleSave: `${preLink}/role/save`,
  // 删除角色
  roleDelete: `${preLink}/role/delete`,
  // 查询一个角色
  roleGet: `${preLink}/role/get`,
  // 查询用户和角色关联信息
  roleUser: `${preLink}/role/roleUser`,
  // 查询用户和菜单关联信息
  roleMenu: `${preLink}/role/roleMenu`,
  // 保存菜单信息
  saveRoleMenu: `${preLink}/role/saveRoleMuen`,
  // 新增或修改用户和部门关联信息
  queryUserRoleRel: `${preLink}/usermg/userAndRoleRel/query`,
  // 查询用户所属某个角色的信息 3288新环境用get
  queryUserRoleInfo: `${preLink}/usermg/userAndRoleRel/get`,
  // 新增或更新用户和角色关联信息
  saveUserAndRoleRel: `${preLink}/usermg/userAndRoleRel/save`,
  // 删除用户和角色关联信息
  deleteUserAndRoleRel: `${preLink}/usermg/userAndRoleRel/delete`,

  // ///////////参数管理/////////////
  // 查询参数分页列表接口
  paramList: `${preLink}/param/list`,
  // 新增或修改参数信息
  paramSave: `${preLink}/param/save`,
  // 删除参数信息
  paramDelete: `${preLink}/param/delete`,

  // ///////////菜单管理/////////////
  // 查询菜单树
  menuTree: `${preLink}/menu/menuTree`,
  // 保存菜单
  menuSave: `${preLink}/menu/save`,
  // 删除菜单
  menuDelete: `${preLink}/menu/delete`,
  // 查询一个菜单信息
  menuGet: `${preLink}/menu/get`,
  // 查询按钮事件信息
  eventRelQuery: `${preLink}/menu/eventRel/query`,
  // 按钮事件添加
  eventRelSave: `${preLink}/menu/eventRel/save`,
  // 按钮事件删除
  eventRelDelete: `${preLink}/menu/eventRel/delete`,
  // 查询按钮事件资源列表
  eventResQuery: `${preLink}/eventRes/query`,
  // 按钮事件资源同步
  eventResSync: `${preLink}/eventRes/sync`,
  // 按钮事件资源新增
  eventResSave: `${preLink}/eventRes/save`,
  // 按钮事件资源删除
  eventResDelete: `${preLink}/eventRes/delete`,

  // ///////////编号管理/////////////
  // 查询编号列表
  encodeTypeList: `${preLink}/ecdgen/doFindEncodeTypeListPage`,
  // 检查编号是否已存在
  checkEncodeTp: `${preLink}/ecdgen/doCheckEncodeTp`,
  // 新增编号
  saveEncodeTp: `${preLink}/ecdgen/doSaveEncodeTp`,
  // 删除编号
  deleteEncodeTp: `${preLink}/ecdgen/doDeleteEncodeTp`,
  // 生效
  ecdgenValid: `${preLink}/ecdgen/valid`,
  // 失效
  ecdgenInvalid: `${preLink}/ecdgen/invalid`,
  // 查询维护明细列表
  encodeDtlList: `${preLink}/ecdgen/doFindEncodeDtlListPage`,
  // 获取编码明细编号
  getEncodeDtl: `${preLink}/ecdgen/doGetEncodeDtl`,
  // 新增维护明细
  saveEncodeDtl: `${preLink}/ecdgen/doSaveEncodeDtl`,
  // 删除维护明细
  deleteEncodeDtl: `${preLink}/ecdgen/doDeleteEncodeDtl`,

  // ///////////数据字典管理/////////////
  // 查询参数分页列表接口
  dictList: `${preLink}/dict/list`,
  // 新增或修改参数信息
  dictSave: `${preLink}/dict/save`,
  // 删除参数信息
  dictDelete: `${preLink}/dict/delete`,

  // ///////////数据权限/////////////
  // 查询参数分页列表接口
  dataauthQuery: `${preLink}/dataauth/query`,
  // 新增或修改参数信息
  dataauthSave: `${preLink}/dataauth/save`,
  // 根据id查询一条参数信息
  // dataauthQuery: `${preLink}/dataauth/query`,
  // 删除参数信息
  dataauthDelete: `${preLink}/dataauth/delete`,
  // 数据权限类型
  dataauthModelSelector: `${preLink}/dataauth/modelSelector`,
  // 用户
  usermgSelector: `${preLink}/usermg/selector`,
  // 角色
  roleSelector: `${preLink}/role/selector`,
  // 部门
  departmentSelector: `${preLink}/department/selector`,
  // 机构
  orgSelector: `${preLink}/org/selector`,

  // ///////////部门管理/////////////
  // 部门树
  getDepartmentTree: `${preLink}/department/departmentTree`,
  // 部门机构
  departOrgSelector: `${preLink}/usermg/userAndOrgRel/selector`,
  // 添加部门
  departmentSave: `${preLink}/department/save`,
  // 当前节点的数据
  departmentGet: `${preLink}/department/get`,
  // 删除部门
  departmentDelete: `${preLink}/department/delete`,
  // 用户列表
  userAndDeptRelQuery: `${preLink}/usermg/userAndDeptRel/query`,
  // 新增或修改用户和部门关联信息
  saveUserAndDeptRel: `${preLink}/usermg/userAndDeptRel/save`,
  // 查询用户所属某个部门的信息 3288新环境用get
  queryUserDeptInfo: `${preLink}/usermg/userAndDeptRel/get`,
  // 删除用户和部门关联信息
  deleteUserAndDeptRel: `${preLink}/usermg/userAndDeptRel/delete`,

  // ///////////机构管理/////////////
  // 机构树
  fetchOrgTree: `${preLink}/org/orgTree`,
  // 保存机构
  saveOrgTree: `${preLink}/org/save`,
  // 新增或更新用户和机构关联信息
  saveUserAndOrgRel: `${preLink}/usermg/userAndOrgRel/save`,
  // 删除用户和机构关联信息
  deleteUserAndOrgRel: `${preLink}/usermg/userAndOrgRel/delete`,
  // 查询用户所属某条机构的信息 3288新环境用get
  queryUserOrgInfo: `${preLink}/usermg/userAndOrgRel/get`,
  // 查询机构的用户
  fetchOrgUser: `${preLink}/usermg/userAndOrgRel/query`,
  // 查询机构信息
  fetchOrgInfo: `${preLink}/org/get`,
  // 删除机构
  deleteOrgById: `${preLink}/org/delete`,
  // ///////////节假日管理/////////////
  // 节假日模板
  holMgtXlsx: `/static/template/holMgt.xlsx`,
  // 节假日列表
  holMgtList: `${preLink}/user/holMgtList/pagingHolMgtQueryList`,
  // 设定节假日
  holMgtSet: `${preLink}/user/holMgtList/setHolMgt`,
  // 节假日导入
  holMgtImport: `${preLink}/user/holMgtList/import`,
  // 节假日删除
  holMgtDelete: `${preLink}/user/holMgtList/doDeleteHol`,

  // ///////////数据权限模型管理/////////////
  // 列表查询
  queryAuthModel: `${preLink}/dataauth/queryAuthModel`,
  // 删除
  delAuthModel: `${preLink}/dataauth/delAuthModel`,
  // 保存
  saveAuthModel: `${preLink}/dataauth/saveAuthModel`,

  // 外建过滤字段规则
  queryModelFkcolumn: `${preLink}/dataauth/queryModelFkcolumn`,
  // 保存 外建过滤字段规则
  saveModelFkcolumn: `${preLink}/dataauth/saveModelFkcolumn`,
  // 删除
  delModelFkcolumn: `${preLink}/dataauth/delModelFkcolumn`

}

