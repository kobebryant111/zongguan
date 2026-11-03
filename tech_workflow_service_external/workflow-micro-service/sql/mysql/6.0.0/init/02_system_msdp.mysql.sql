

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_ddct
-- ----------------------------
DROP TABLE IF EXISTS `base_ddct`;
CREATE TABLE `base_ddct` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `DCT_KEY` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '字典键',
  `DCT_TP_CD` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '字典类型',
  `DCT_TP_NM` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '字典类型名称',
  `DCT_VAL` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '字典值',
  `DCT_VAL_NM` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '字典值名称',
  `DCT_GRP` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '字典组',
  `DCT_DSC` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '字典描述',
  `DCT_SEQ` int(3) DEFAULT NULL COMMENT '字典排序',
  `ST_CD` varchar(1) CHARACTER SET utf8  DEFAULT '1' COMMENT '状态：无效0\\有效1',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL DEFAULT '0' COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户id',
  `SYS_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '系统ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据字典';

-- ----------------------------
-- Records of base_ddct
-- ----------------------------
INSERT INTO `base_ddct` VALUES ('1', 'MenuType_0', 'MenuType', '菜单类型', 'view', '页面', '', '', '0', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-06-16 15:01:56', 'USR170120000087', '', '0', '19', '', null);
INSERT INTO `base_ddct` VALUES ('10', 'OrgLevel_0', 'OrgLevel', '机构等级', 'LV00', '零级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('17', 'Gnd_1', 'Gnd', '性别', '1', '男', null, '', '1', '1', '2016-11-25 17:50:01', 'systemAuto', '2020-06-18 09:36:08', 'USR170120000087', null, '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('18', 'Gnd_2', 'Gnd', '性别', '2', '女', null, null, '2', '1', '2016-11-25 17:50:01', 'systemAuto', '2016-11-25 17:50:01', 'systemAuto', null, '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('2', 'MenuType_1', 'MenuType', '菜单类型', 'click', '按钮', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('23', '10004', 'EcdRuleSrc', '编号明细', 'sysDateFill', '系统日期', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 15:53:59', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('24', '10005', 'EcdRuleSrc', '编号明细', 'zeroFill', '序列', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('25', 'constantFill', 'EcdRuleSrc', '编号明细', 'constantFill', '常量', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('26', 'variableFill', 'EcdRuleSrc', '编号明细', 'variableFill', '变量', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('27', 'dynamicSerialFill', 'EcdRuleSrc', '编号明细', 'dynamicSerialFill', '动态序列', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('28', 'cascadeSerialFill', 'EcdRuleSrc', '编号明细', 'cascadeSerialFill', '级联序列', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('3', 'StCd_0', 'StCd', '通用状态代码', '0', '未生效', '1', '', '1', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('4', 'StCd_1', 'StCd', '通用状态代码', '1', '生效', '1', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('48', 'Indicator_0', 'Indicator', '指示器', '0', '否', null, null, '1', '1', null, null, '2020-06-16 14:49:15', 'USR170120000087', null, '0', '1', '', null);
INSERT INTO `base_ddct` VALUES ('49', 'Indicator_1', 'Indicator', '指示器', '1', '是', null, null, '2', '1', null, null, '2020-06-16 14:49:37', 'USR170120000087', null, '0', '1', '', null);
INSERT INTO `base_ddct` VALUES ('630', 'StCd_2', 'StCd', '通用状态代码', '2', '失效', '1', null, '3', '1', '2017-11-01 17:53:19', 'systemAuto', '2017-11-01 17:53:29', 'systemAuto', null, '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('636', 'MenuType_2', 'MenuType', '菜单类型', 'no-source', '目录', '', '', '3', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', null);
INSERT INTO `base_ddct` VALUES ('654', 'BizSceneType_0', 'BizSceneType', '业务场景类型', '*', '*', '1', '', '1', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 18:45:21', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('655', 'BizSceneType_1', 'BizSceneType', '业务场景类型', 'CprsvEnqr', '综合查询', '1', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 18:59:32', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('656', 'BizSceneType_2', 'BizSceneType', '业务场景类型', 'tenantManage', '租户管理', '0', '', '3', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 18:59:47', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('657', 'BizSceneType_3', 'BizSceneType', '业务场景类型', 'Circle', '圈链场景', '1', '', '4', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 19:36:51', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('658', 'BizSceneType_4', 'BizSceneType', '业务场景类型', 'RcvbTrs', '应收账款转让场景', '1', '', '5', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 21:12:42', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('659', 'BizSceneType_5', 'BizSceneType', '业务场景类型', 'Approve', '审批场景', '1', '', '6', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 21:08:07', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('660', 'BizSceneType_6', 'BizSceneType', '业务场景类型', 'Network', '项目方案场景', '1', '', '7', '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 21:03:47', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('675', 'BizSceneType_7', 'BizSceneType', '业务场景类型', 'Cst', '客户管理场景', '1', '', '8', '1', '2017-11-21 14:15:38', 'systemAuto', '2020-07-08 21:46:45', null, '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('749', 'OrgLevel_1', 'OrgLevel', '机构等级', 'LV01', '一级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('750', 'OrgLevel_2', 'OrgLevel', '机构等级', 'LV02', '二级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('751', 'OrgLevel_3', 'OrgLevel', '机构等级', 'LV03', '三级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('752', 'OrgLevel_4', 'OrgLevel', '机构等级', 'LV04', '四级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('753', 'OrgLevel_5', 'OrgLevel', '机构等级', 'LV05', '五级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', '');
INSERT INTO `base_ddct` VALUES ('754', 'OrgLevel_6', 'OrgLevel', '机构等级', 'LV06', '六级', '', '', '2', '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', '0', '', null);

-- ----------------------------
-- Table structure for base_ecd_dtl
-- ----------------------------
DROP TABLE IF EXISTS `base_ecd_dtl`;
CREATE TABLE `base_ecd_dtl` (
  `ID` char(32) NOT NULL COMMENT '编码明细ID',
  `ECD_DTL_ID` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '编码明细编号',
  `ECD_TP_ID` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '编码类型编号',
  `RULE_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '编码生成规则编号',
  `RULE_SRC` varchar(50) CHARACTER SET utf8  NOT NULL COMMENT '规则源，解析规则的服务名称',
  `SN` int(10) DEFAULT '1' COMMENT '序号',
  `ECD_LEN` int(10) DEFAULT NULL COMMENT '编码长度',
  `ECD_VAL` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '编码值',
  `ST_CD` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '状态：无效0有效1',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编码明细';

-- ----------------------------
-- Records of base_ecd_dtl
-- ----------------------------

-- ----------------------------
-- Table structure for base_ecd_serial
-- ----------------------------
DROP TABLE IF EXISTS `base_ecd_serial`;
CREATE TABLE `base_ecd_serial` (
  `ID` char(32)  NOT NULL COMMENT '主键ID',
  `PREFIX_CODE` varchar(64)  NOT NULL COMMENT '编码前缀代码',
  `SN_TP_CD` varchar(12)  NOT NULL COMMENT '序列类型(cascade级联序列/dynamic动态序列)',
  `SN_VAL` varchar(64)  DEFAULT NULL COMMENT '编码值',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32)  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1)  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32)  DEFAULT NULL,
  `ECD_TP_ID` varchar(32)  DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='业务编码规则动态序列和级联序列明细';

-- ----------------------------
-- Records of base_ecd_serial
-- ----------------------------

-- ----------------------------
-- Table structure for base_ecd_tp
-- ----------------------------
DROP TABLE IF EXISTS `base_ecd_tp`;
CREATE TABLE `base_ecd_tp` (
  `ID` char(32) NOT NULL COMMENT '编码生成规则ID',
  `ECD_TP_ID` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '编码类型编号',
  `ECD_TP_NM` varchar(50) CHARACTER SET utf8  NOT NULL COMMENT '编码类型名称',
  `RMRK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `ST_CD` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '状态：无效0有效1',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编码类型';

-- ----------------------------
-- Records of base_ecd_tp
-- ----------------------------

-- ----------------------------
-- Table structure for base_file_rsc
-- ----------------------------
-- DROP TABLE IF EXISTS `base_file_rsc`;
-- CREATE TABLE `base_file_rsc` (
--   `ID` char(32)  NOT NULL COMMENT 'ID',
--   `FILE_ID` varchar(32)  DEFAULT NULL COMMENT '文件编号',
--   `SUPR_FILE_ID` varchar(32)  DEFAULT NULL COMMENT '上级文件编号',
--   `FILE_NM` varchar(200)  DEFAULT NULL COMMENT '文件名称',
--   `FILE_EXPD_NM` varchar(12)  DEFAULT NULL COMMENT '文件扩展名',
--   `FILE_SIZE` decimal(32,0) DEFAULT NULL COMMENT '文件大小',
--   `FILE_RTE` varchar(500)  DEFAULT NULL COMMENT '文件路径',
--   `MDL_ID` varchar(20)  DEFAULT NULL COMMENT '模块标识',
--   `ATCH_NM` varchar(200)  DEFAULT NULL COMMENT '附件名称',
--   `TBL_BL_ID` varchar(32)  DEFAULT NULL COMMENT '表单标识',
--   `FILE_TP_CD` varchar(10)  DEFAULT NULL COMMENT '文件类型 0.权属证明类资料，1.协议合同类资料，2.证件证明类资料，3.贷后管理类资料',
--   `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
--   `CREATE_USER` varchar(32)  DEFAULT NULL COMMENT '创建人',
--   `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
--   `UPDATE_USER` varchar(32)  DEFAULT NULL COMMENT '更新人',
--   `EXPD_ID` varchar(32)  DEFAULT NULL COMMENT '扩展编号',
--   `DEL_IND` varchar(1)  DEFAULT NULL COMMENT '删除标志',
--   `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
--   `TENANT_ID` varchar(32)  DEFAULT NULL COMMENT '租户编号',
--   PRIMARY KEY (`ID`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=DYNAMIC COMMENT='文件资源表';

-- ----------------------------
-- Records of base_file_rsc
-- ----------------------------

-- ----------------------------
-- Table structure for sys_clntend
-- ----------------------------
DROP TABLE IF EXISTS `sys_clntend`;
CREATE TABLE `sys_clntend` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端编号',
  `CLNTEND_NM` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端名称',
  `CLNTEND_SECRET` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端安全码',
  `CLNTEND_DOMAIN` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端域名',
  `CLNTEND_ST` varchar(10) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端状态(1:启用，2:禁用)',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` date DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标志',
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户端表';

-- ----------------------------
-- Records of sys_clntend
-- ----------------------------
INSERT INTO `sys_clntend` VALUES ('1', '10001', '协同前台', 'abcd', null, '1', null, null, null, null, null, null, '0', '');
INSERT INTO `sys_clntend` VALUES ('2', '10002', '协同后台', 'abcd', '', '1', '2016-12-26', 'systemAuto', '2016-12-26', 'systemAuto', '', '', '0', '');

-- ----------------------------
-- Table structure for sys_data_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_auth`;
CREATE TABLE `sys_data_auth` (
  `ID` char(32)  NOT NULL COMMENT '主键ID',
  `DATA_AUTH_CODE` varchar(32)  NOT NULL COMMENT '数据权限编号',
  `MODEL_CODE` varchar(32)  DEFAULT NULL COMMENT '数据权限类型编码',
  `AUTH_CONDITIONS` varchar(256)  DEFAULT NULL COMMENT '过滤条件',
  `CONDITIONS_REMARK` varchar(256)  DEFAULT NULL COMMENT '过滤条件备注',
  `REMARK` varchar(126)  DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32)  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1)  NOT NULL COMMENT '删除标志:0-未删除；1-删除',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32)  DEFAULT NULL COMMENT '租户id',
  `DEPT_CODE` varchar(32)  DEFAULT '*' COMMENT '部门代码',
  `ROLE_CODE` varchar(32)  DEFAULT '*' COMMENT '角色编号',
  `ORG_CODE` varchar(32)  DEFAULT '*' COMMENT '机构代码',
  `USER_NUM` varchar(32)  DEFAULT '*' COMMENT '用户编号',
  `CLNTEND_ID` varchar(32)  DEFAULT '*' COMMENT '客户端编号',
  `BIZ_SCENE` varchar(32)  DEFAULT '*' COMMENT '业务场景',
  `EVENT_CODE` varchar(32)  DEFAULT '*' COMMENT '事件编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=DYNAMIC COMMENT='系统数据权限表';

-- ----------------------------
-- Records of sys_data_auth
-- ----------------------------

-- ----------------------------
-- Table structure for sys_data_auth_fkcolumn
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_auth_fkcolumn`;
CREATE TABLE `sys_data_auth_fkcolumn` (
  `ID` char(32)  NOT NULL COMMENT '主键',
  `FKCOLUMN_ID` varchar(32)  DEFAULT NULL COMMENT '外键规则编号',
  `MODEL_CODE` varchar(32)  DEFAULT NULL COMMENT '数据权限模型关联编号',
  `FKCOLUMN_ST_CD` varchar(1)  DEFAULT NULL COMMENT '外键关联状态',
  `FKCOLUMN_RES_SQL` varchar(200)  DEFAULT NULL COMMENT '外键过滤查询语句',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_USER` varchar(200)  DEFAULT NULL COMMENT '创建人',
  `UPDATE_USER` varchar(200)  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(200)  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1)  DEFAULT NULL COMMENT '删除标识',
  `VERSION` varchar(10)  DEFAULT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32)  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='数据权限外键字段规则表';

-- ----------------------------
-- Records of sys_data_auth_fkcolumn
-- ----------------------------

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `DEPT_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '部门代码',
  `DEPT_NAME` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '部门名称',
  `PARENT_DEPT_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '上级部门代码',
  `DEPT_LEVEL_CD` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '部门级别(一级1二级2三级3四级4)',
  `SORT` int(3) DEFAULT NULL COMMENT '排序',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT '1' COMMENT '状态：无效0有效1',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统部门信息';

-- ----------------------------
-- Records of sys_department
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `ID` char(32) NOT NULL,
  `ORG_CODE` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '机构代码',
  `ORG_NAME` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '机构名称',
  `PARENT_ORG_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '父节点机构代码',
  `ORG_LEVEL_CD` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '机构等级',
  `SORT` int(3) DEFAULT NULL COMMENT '排序',
  `EXT_JSON_DATA` varchar(100) CHARACTER SET utf8  DEFAULT NULL,
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标志:0-未删除；1-删除；',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL,
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统机构表';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------

-- ----------------------------
-- Table structure for sys_org_dept_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_dept_rel`;
CREATE TABLE `sys_org_dept_rel` (
  `ID` char(32) NOT NULL COMMENT '主键编号',
  `ORG_CODE` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '机构编码',
  `DEPT_CODE` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '部门编码',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标识',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统机构与部门关系表';

-- ----------------------------
-- Records of sys_org_dept_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `PERMISSION_CODE` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '权限编号',
  `PERMISSION_TYPE_CD` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '权限类型(0菜单1页面元素2文件3数据权限)',
  `RES_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '资源编号',
  `PERMISSION_NAME` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '权限名称',
  `IS_BUILT_IN` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '是否系统内置权限(否0是1)',
  `EXT_JSON_DATA` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展数据',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态：无效0有效1',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标志',
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户id',
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `SYS_PERMISSION` VALUES ('112345678910111213', 'AHR0001', '0', 'RES10001', '我的设置', '0', null, '1', null, '2016-11-05 15:33:37', 'systemAuto', '2016-11-05 15:33:37', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('112345678910111214', 'AHR41211', 'view', 'RES10012', '部门管理', '0', null, '1', null, '2018-04-25 16:53:48', 'systemAuto', '2018-07-11 12:03:02', 'systemAuto', null, '0', '1', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('161345678910111213', 'AHR1592206017431', 'click', 'MENU1592206017375', '新增或修改用户', null, null, '1', null, '2020-06-15 15:26:57', null, '2020-06-15 15:26:57', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('161545678910111213', 'AHR1592209745281', 'click', 'MENU1592209745256', '机构事件管理', null, null, '1', null, '2020-06-15 16:29:05', null, '2020-06-15 16:29:05', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('161645678910111213', 'AHR1592210170770', 'click', 'MENU1592210170735', '部门事件管理', null, null, '1', null, '2020-06-15 16:36:11', null, '2020-06-15 16:36:11', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('161745678910111213', 'AHR1592210276363', 'click', 'MENU1592210276320', '用户事件管理', null, null, '1', null, '2020-06-15 16:37:56', null, '2020-06-15 16:37:56', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('161845678910111213', 'AHR1592210396958', 'click', 'MENU1592210396925', '角色事件管理', null, null, '1', null, '2020-06-15 16:39:57', null, '2020-06-15 16:39:57', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('161945678910111213', 'AHR1592210600149', 'click', 'MENU1592210600121', '菜单事件管理', null, null, '1', null, '2020-06-15 16:43:20', null, '2020-06-15 16:43:20', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('162145678910111213', 'AHR1592210710429', 'click', 'MENU1592210710398', '租户事件管理', null, null, '1', null, '2020-06-15 16:45:10', null, '2020-06-15 16:45:10', null, null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('212345678910111213', 'AHR0002', '0', 'RES10002', '系统管理', '0', null, '1', null, '2016-11-05 15:37:01', 'systemAuto', '2016-11-05 15:37:01', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('222345678910111213', 'AHR41214', 'view', 'RES10023', '租户管理', '0', null, '1', null, '2018-05-09 15:45:58', 'systemAuto', '2018-05-09 15:45:58', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('312345678910111213', 'AHR0003', '0', 'RES10003', '用户管理', '0', null, '1', null, '2016-11-05 15:45:47', 'systemAuto', '2016-11-05 15:45:47', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('412345678910111213', 'AHR0004', '0', 'RES10004', '角色管理', '0', null, '1', null, '2016-11-05 15:45:47', 'systemAuto', '2016-11-05 15:45:47', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('512345678910111213', 'AHR0005', '0', 'RES10005', '菜单管理', '0', null, '1', null, '2016-11-05 15:37:01', 'systemAuto', '2016-11-05 15:37:01', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('552345678910111213', 'SYSAHR001', 'click', 'SYSRES001', '系统权限', null, null, '0', null, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('612345678910111213', 'AHR0006', '0', 'RES10006', '机构管理', '0', null, '1', null, '2016-11-05 15:33:37', 'systemAuto', '2016-11-05 15:33:37', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('727106481864474624', 'AHR727106481818337280', 'view', 'MENU727106481587650560', '编号管理', null, null, '1', null, '2020-06-29 10:21:34', 'USR170120000087', '2020-06-29 10:21:34', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('727204996775645184', 'AHR727204996737896448', 'click', 'MENU727204996565929984', '编号事件管理', null, null, '1', null, '2020-06-29 16:53:02', 'USR170120000087', '2020-06-29 16:53:02', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('728555967059054592', 'AHR728555967025500160', 'view', 'MENU728555966975168512', '权限管理', null, null, '1', null, '2020-07-03 10:21:18', 'USR170120000087', '2020-07-03 10:21:18', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('728556381091385344', 'AHR728556381032665088', 'click', 'MENU728556380969750528', '权限事件管理', null, null, '1', null, '2020-07-03 10:22:57', 'USR170120000087', '2020-07-03 10:22:57', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('729642449754009600', 'AHR729642449728843776', 'view', 'MENU729642449586237440', '数据权限模型管理', null, null, '1', null, '2020-07-06 10:18:36', 'USR170120000087', '2020-07-06 10:18:36', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('729729587669512192', 'AHR729729587652734976', 'click', 'MENU729729587614986240', '数据权限模型管理', null, null, '1', null, '2020-07-06 16:04:51', 'USR170120000087', '2020-07-06 16:04:51', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('729732602652667904', 'AHR729732602640084992', 'click', 'MENU729732602610724864', '新增修改', null, null, '1', null, '2020-07-06 16:16:50', 'USR170120000087', '2020-07-06 16:16:50', 'USR170120000087', null, '0', null, 'defaultTenantId', '10002');
INSERT INTO `SYS_PERMISSION` VALUES ('912345678910111213', 'AHR0009', '0', 'RES10009', '数据字典', '0', null, '1', null, '2016-11-05 15:33:37', 'systemAuto', '2016-11-05 15:33:37', 'systemAuto', null, '0', '0', 'defaultTenantId', '10002');

-- ----------------------------
-- Table structure for sys_res_data_auth_model
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_data_auth_model`;
CREATE TABLE `sys_res_data_auth_model` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `MODEL_CODE` varchar(32) NOT NULL COMMENT '权限类型编码',
  `MODEL_ID` varchar(32) DEFAULT NULL COMMENT '数据权限类型',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) NOT NULL COMMENT '删除标志:0-未删除；1-删除',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) DEFAULT NULL COMMENT '租户id',
  `MODEL_NAME` varchar(32) NOT NULL DEFAULT '' COMMENT '数据权限类型名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统权限类型表';

-- ----------------------------
-- Records of sys_res_data_auth_model
-- ----------------------------

-- ----------------------------
-- Table structure for sys_res_event
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_event`;
CREATE TABLE `sys_res_event` (
  `ID` char(32)  NOT NULL COMMENT '主键ID',
  `EVENT_CODE` varchar(32)  NOT NULL COMMENT '事件编号',
  `EVENT_NAME` varchar(100)  NOT NULL COMMENT '事件名称',
  `URL` varchar(255)  NOT NULL COMMENT '菜单URL',
  `CLNTEND_ID` varchar(32)  DEFAULT NULL COMMENT '客户端编号',
  `STATUS_CD` varchar(1)  DEFAULT NULL COMMENT '状态：无效0有效1',
  `REMARK` varchar(100)  DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32)  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1)  DEFAULT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32)  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=DYNAMIC COMMENT='系统事件资源信息表';

-- ----------------------------
-- Records of sys_res_event
-- ----------------------------
INSERT INTO `sys_res_event` VALUES ('1', 'SYSEVENT001', '获取菜单', '/msdp-system-service/menu', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('10', 'SYSEVENT010', '删除菜单', '/msdp-system-service/menu/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('11', 'SYSEVENT011', '保存/更新菜单', '/msdp-system-service/menu/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('12', 'SYSEVENT012', '菜单资源权限查询', '/msdp-system-service/menu/eventRel/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('13', 'SYSEVENT013', '菜单资源权限删除', '/msdp-system-service/menu/eventRel/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('14', 'SYSEVENT014', '菜单资源权限保存', '/msdp-system-service/menu/eventRel/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('15', 'SYSEVENT015', '菜单数据权限查询', '/msdp-system-service/menu/eventDataauth/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('16', 'SYSEVENT016', '资源查询', '/msdp-system-service/eventRes/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('17', 'SYSEVENT017', '资源详情', '/msdp-system-service/eventRes/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('18', 'SYSEVENT018', '删除资源', '/msdp-system-service/eventRes/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('19', 'SYSEVENT019', '保存资源', '/msdp-system-service/eventRes/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('2', 'SYSEVENT002', '查询用户', '/msdp-system-service/user/queryUser', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('20', 'SYSEVENT020', '机构树查询', '/msdp-system-service/org/orgTree', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('21', 'SYSEVENT021', '机构详情', '/msdp-system-service/org/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('22', 'SYSEVENT022', '删除机构', '/msdp-system-service/org/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('23', 'SYSEVENT023', '保存机构', '/msdp-system-service/org/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('24', 'SYSEVENT024', '机构选择器', '/msdp-system-service/org/selector', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('25', 'SYSEVENT025', '角色树查询', '/msdp-system-service/role/roleTree', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('26', 'SYSEVENT026', '角色详情', '/msdp-system-service/role/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('27', 'SYSEVENT027', '删除角色', '/msdp-system-service/role/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('28', 'SYSEVENT028', '保存角色', '/msdp-system-service/role/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('29', 'SYSEVENT029', '角色选择器', '/msdp-system-service/role/selector', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('3', 'SYSEVENT003', '校验密码', '/msdp-system-service/valiPass', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('30', 'SYSEVENT030', '角色菜单查询', '/msdp-system-service/role/roleMenu', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('31', 'SYSEVENT031', '角色菜单保存', '/msdp-system-service/role/saveRoleMuen', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('32', 'SYSEVENT032', '角色菜单分配', '/msdp-system-service/role/roleMenuAssigned', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('33', 'SYSEVENT033', '字典查询', '/msdp-system-service/dict/list', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('34', 'SYSEVENT034', '字典详情', '/msdp-system-service/dict/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('35', 'SYSEVENT035', '删除字典', '/msdp-system-service/dict/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('36', 'SYSEVENT036', '保存字典', '/msdp-system-service/dict/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('41', 'SYSEVENT041', '用户角色查询', '/msdp-system-service/role/roleUser', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('42', 'SYSEVENT042', '用户机构部门查询', '/msdp-system-service/admin/userOrgDept', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('43', 'SYSEVENT043', '用户切换角色', '/msdp-system-service/admin/changer', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('44', 'SYSEVENT044', '部门树查询', '/msdp-system-service/department/departmentTree', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('45', 'SYSEVENT045', '部门详情', '/msdp-system-service/department/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('46', 'SYSEVENT046', '部门删除', '/msdp-system-service/department/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('47', 'SYSEVENT047', '部门保存', '/msdp-system-service/department/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('48', 'SYSEVENT048', '部门选择器', '/msdp-system-service/department/selector', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('55', 'SYSEVENT055', '用户查询', '/msdp-system-service/usermg/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('56', 'SYSEVENT056', '用户保存', '/msdp-system-service/usermg/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('57', 'SYSEVENT057', '用户详情', '/msdp-system-service/usermg/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('58', 'SYSEVENT058', '用户删除', '/msdp-system-service/usermg/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('59', 'SYSEVENT059', '用户选择器', '/msdp-system-service/usermg/selector', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('60', 'SYSEVENT060', '用户修改密码', '/msdp-system-service/usermg/changePassword', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('61', 'SYSEVENT061', '用户重置密码', '/msdp-system-service/usermg/resetPassword', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('62', 'SYSEVENT062', '用户机构查询', '/msdp-system-service/usermg/userAndOrgRel/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('63', 'SYSEVENT063', '用户机构保存', '/msdp-system-service/usermg/userAndOrgRel/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('64', 'SYSEVENT064', '用户机构详情', '/msdp-system-service/usermg/userAndOrgRel/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('65', 'SYSEVENT065', '用户机构删除', '/msdp-system-service/usermg/userAndOrgRel/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('66', 'SYSEVENT066', '用户机构选择器', '/msdp-system-service/usermg/userAndOrgRel/selector', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('67', 'SYSEVENT067', '用户部门查询', '/msdp-system-service/usermg/userAndDeptRel/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('68', 'SYSEVENT068', '用户部门保存', '/msdp-system-service/usermg/userAndDeptRel/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('69', 'SYSEVENT069', '用户部门详情', '/msdp-system-service/usermg/userAndDeptRel/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('70', 'SYSEVENT070', '用户部门删除', '/msdp-system-service/usermg/userAndDeptRel/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('71', 'SYSEVENT071', '用户角色查询', '/msdp-system-service/usermg/userAndRoleRel/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('72', 'SYSEVENT072', '用户角色保存', '/msdp-system-service/usermg/userAndRoleRel/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('727205853978136576', 'SYSEVENT086', '编码生成类型分页查询', '/msdp-system-service/ecdgen/doFindEncodeTypeListPage', '10002', '1', null, '2020-06-29 16:56:26', 'USR170120000087', '2020-06-29 16:56:26', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727206136422567936', 'SYSEVENT087', '获取编码类型信息', '/msdp-system-service/ecdgen/doGetEncodeTp', '10002', '1', null, '2020-06-29 16:57:34', 'USR170120000087', '2020-06-29 16:57:34', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727207685198680064', 'SYSEVENT088', '编码明细分页查询', '/msdp-system-service/ecdgen/doFindEncodeDtlListPage', '10002', '1', null, '2020-06-29 17:03:43', 'USR170120000087', '2020-06-29 17:03:43', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727208297776779264', 'SYSEVENT089', '获取编码明细信息', '/msdp-system-service/ecdgen/doGetEncodeDtl', '10002', '1', null, '2020-06-29 17:06:09', 'USR170120000087', '2020-06-29 17:06:09', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727208530959110144', 'SYSEVENT090', '保存编码类型前，校验是否可保存', '/msdp-system-service/ecdgen/doCheckEncodeTp', '10002', '1', null, '2020-06-29 17:07:05', 'USR170120000087', '2020-06-29 17:07:05', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727208732579303424', 'SYSEVENT091', '保存或更新编码生成类型', '/msdp-system-service/ecdgen/doSaveEncodeTp', '10002', '1', null, '2020-06-29 17:07:53', 'USR170120000087', '2020-06-29 17:07:53', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727208898430472192', 'SYSEVENT092', '保存或更新编码明细', '/msdp-system-service/ecdgen/doSaveEncodeDtl', '10002', '1', null, '2020-06-29 17:08:32', 'USR170120000087', '2020-06-29 17:08:32', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727209206569209856', 'SYSEVENT093', '删除编码明细', '/msdp-system-service/ecdgen/doDeleteEncodeDtl', '10002', '1', null, '2020-06-29 17:09:46', 'USR170120000087', '2020-06-29 17:09:46', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727209342028451840', 'SYSEVENT094', '删除编码类型', '/msdp-system-service/ecdgen/doDeleteEncodeTp', '10002', '1', null, '2020-06-29 17:10:18', 'USR170120000087', '2020-06-29 17:10:18', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727209493895811072', 'SYSEVENT095', '生效生效编号类型', '/msdp-system-service/ecdgen/valid', '10002', '1', null, '2020-06-29 17:10:54', 'USR170120000087', '2020-06-29 17:10:54', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('727209745415639040', 'SYSEVENT096', '失效编号类型', '/msdp-system-service/ecdgen/invalid', '10002', '1', null, '2020-06-29 17:11:54', 'USR170120000087', '2020-06-29 17:11:54', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('728556977299116032', 'SYSEVENT097', '根据条件请求获取数据权限分页信息', '/msdp-system-service/dataauth/query', '10002', '1', null, '2020-07-03 10:25:19', 'USR170120000087', '2020-07-03 10:25:19', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('728557287967019008', 'SYSEVENT098', '根据编号ID获取参数详细信息', '/msdp-system-service/dataauth/get', '10002', '1', null, '2020-07-03 10:26:33', 'USR170120000087', '2020-07-03 10:26:33', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('728557415062818816', 'SYSEVENT099', '保存或修改数据权限实例', '/msdp-system-service/dataauth/save', '10002', '1', null, '2020-07-03 10:27:04', 'USR170120000087', '2020-07-03 10:27:04', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('728558130841767936', 'SYSEVENT100', '根据编号删除对象的数据', '/msdp-system-service/dataauth/delete', '10002', '1', null, '2020-07-03 10:29:54', 'USR170120000087', '2020-07-03 10:29:54', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('728558851746156544', 'SYSEVENT101', '分页获取数据权限类型', '/msdp-system-service/dataauth/modelSelector', '10002', '1', null, '2020-07-03 10:32:46', 'USR170120000087', '2020-07-03 10:32:46', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729706468573589504', 'SYSEVENT102', '分页查询数据权限模型', '/msdp-system-service/dataauth/queryAuthModel', '10002', '1', null, '2020-07-06 14:32:59', 'USR170120000087', '2020-07-06 14:32:59', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729708095477002240', 'SYSEVENT103', '根据编号ID获取参数详细信息', '/msdp-system-service/dataauth/getAuthModel', '10002', '1', null, '2020-07-06 14:39:27', 'USR170120000087', '2020-07-06 14:39:27', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729717859581833216', 'SYSEVENT104', '删除数据权限', '/msdp-system-service/dataauth/delAuthModel', '10002', '1', null, '2020-07-06 15:18:15', 'USR170120000087', '2020-07-06 15:18:15', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729721289209688064', 'SYSEVENT105', '保存或更新数据权限', '/msdp-system-service/dataauth/saveAuthModel', '10002', '1', null, '2020-07-06 15:31:53', 'USR170120000087', '2020-07-06 15:31:53', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729726591883685888', 'SYSEVENT106', '分页查询外键过滤信息', '/msdp-system-service/dataauth/queryModelFkcolumn', '10002', '1', null, '2020-07-06 15:52:57', 'USR170120000087', '2020-07-06 15:52:57', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729726909962924032', 'SYSEVENT107', '根据ID获取外键过滤信息', '/msdp-system-service/dataauth/getModelFkcolumn', '10002', '1', null, '2020-07-06 15:54:13', 'USR170120000087', '2020-07-06 15:54:13', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729727399459172352', 'SYSEVENT108', '新增或修改外键过滤信息', '/msdp-system-service/dataauth/saveModelFkcolumn', '10002', '1', null, '2020-07-06 15:56:10', 'USR170120000087', '2020-07-06 15:56:10', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('729727644377165824', 'SYSEVENT109', '删除外键过滤信息', '/msdp-system-service/dataauth/delModelFkcolumn', '10002', '1', null, '2020-07-06 15:57:08', 'USR170120000087', '2020-07-06 15:57:08', 'USR170120000087', null, '0', '0', null);
INSERT INTO `sys_res_event` VALUES ('73', 'SYSEVENT073', '用户角色详情', '/msdp-system-service/usermg/userAndRoleRel/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('74', 'SYSEVENT074', '用户角色删除', '/msdp-system-service/usermg/userAndRoleRel/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('77', 'SYSEVENT077', '权限查询', '/msdp-system-service/sys/indexMenu/permission', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('78', 'SYSEVENT078', '租户查询', '/msdp-system-service/tenant/query', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('79', 'SYSEVENT079', '租户详情', '/msdp-system-service/tenant/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('8', 'SYSEVENT008', '菜单树', '/msdp-system-service/menu/menuTree', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('80', 'SYSEVENT080', '租户保存', '/msdp-system-service/tenant/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('81', 'SYSEVENT081', '租户生效', '/msdp-system-service/tenant/effect', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('82', 'SYSEVENT082', '租户失效', '/msdp-system-service/tenant/invalid', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('83', 'SYSEVENT083', '租户删除', '/msdp-system-service/tenant/delete', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('84', 'SYSEVENT084', '租户菜单保存', '/msdp-system-service/tenant/roleMenu/save', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('85', 'SYSEVENT085', '租户菜单查询', '/msdp-system-service/tenant/roleMenu/menuTree', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_event` VALUES ('9', 'SYSEVENT009', '菜单详情', '/msdp-system-service/menu/get', '10002', '1', null, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', null, '0', '0', '');

-- ----------------------------
-- Table structure for sys_res_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_menu`;
CREATE TABLE `sys_res_menu` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `MENU_CODE` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '菜单编号',
  `MENU_NAME` varchar(100) CHARACTER SET utf8  NOT NULL COMMENT '菜单名称',
  `PARENT_MENU_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '父菜单编号',
  `URL` varchar(255) CHARACTER SET utf8  DEFAULT NULL COMMENT '菜单URL',
  `SORT` int(3) DEFAULT NULL COMMENT '排序',
  `LEAF_FLAG_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '叶子节点标识(非叶子节点0/叶子节点1)',
  `ICON` varchar(255) CHARACTER SET utf8  DEFAULT NULL COMMENT '菜单图标',
  `MENU_TYPE_CD` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '菜单类型(页面view/按钮click)',
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端编号',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '状态：无效0有效1',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统菜单资源表';

-- ----------------------------
-- Records of sys_res_menu
-- ----------------------------
INSERT INTO `SYS_RES_MENU` VALUES ('112345678910111213', 'RES10001', '我的设置', '10002', null, '3', '0', 'iconmenu04', 'no-source', '10002', '1', null, '2016-11-05 16:08:48', 'systemAuto', '2018-05-08 11:24:53', 'systemAuto', null, '0', '9', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('112345678910111214', 'RES10012', '部门管理', 'RES10002', '/department/tree', '2', '1', null, 'view', '10002', '1', '部门管理', null, 'USR170120000087', '2020-06-12 16:17:55', 'USR170120000087', null, '0', '1', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('130234567891011121', 'MENU1592206017375', '新增或修改', 'RES10003', '/user/usermg/personAddMsg', null, '1', null, 'click', '10002', '1', null, null, 'USR170120000087', '2020-06-17 09:37:42', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('132234567891011121', 'MENU1592209745256', '机构事件管理', 'RES10006', null, null, '1', null, 'click', '10002', '1', null, '2020-06-15 16:29:05', 'USR170120000087', '2020-06-15 16:29:05', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('133234567891011121', 'MENU1592210170735', '部门事件管理', 'RES10012', null, null, '1', null, 'click', '10002', '1', null, '2020-06-15 16:36:11', 'USR170120000087', '2020-06-15 16:36:11', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('134234567891011121', 'MENU1592210276320', '用户事件管理', 'RES10003', null, null, '1', null, 'click', '10002', '1', null, '2020-06-15 16:37:56', 'USR170120000087', '2020-06-15 16:37:56', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('135234567891011121', 'MENU1592210396925', '角色事件管理', 'RES10004', null, null, '1', null, 'click', '10002', '1', null, null, 'USR170120000087', '2020-06-15 16:42:12', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('136234567891011121', 'MENU1592210600121', '菜单事件管理', 'RES10005', null, null, '1', null, 'click', '10002', '1', null, '2020-06-15 16:43:20', 'USR170120000087', '2020-06-15 16:43:20', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('138234567891011121', 'MENU1592210710398', '租户事件管理', 'RES10023', null, null, '1', null, 'click', '10002', '1', null, '2020-06-15 16:45:10', 'USR170120000087', '2020-06-15 16:45:10', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('142345678910111213', 'RES10023', '租户管理', 'RES10002', '/tenant/tenantmg/tenantMgList', '16', '1', null, 'view', '10002', '1', '租户管理', '2018-05-09 15:45:57', 'USR170120000087', '2018-05-09 15:45:57', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('212345678910111213', 'RES10002', '系统管理', 'RES10001', null, '1', '1', null, 'no-source', '10002', '1', null, '2017-09-19 16:15:18', 'ceshishuju', '2017-11-09 10:37:11', 'ceshishuju', null, '0', '5', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('272345678910111213', 'SYSRES001', '系统权限', 'RES10002', null, '0', '1', null, 'click', '10002', '1', null, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('312345678910111213', 'RES10003', '用户管理', 'RES10002', '/user/usermg/personMgList', '4', '1', 'iconmenu09', 'view', '10002', '1', null, null, 'systemAuto', '2020-06-09 14:47:40', 'USR170120000087', null, '0', '7', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('412345678910111213', 'RES10004', '角色管理', 'RES10002', '/role/tree', '4', '1', 'iconlistmenu05', 'view', '10002', '1', null, '2016-11-05 16:08:48', 'systemAuto', '2018-03-08 10:18:23', 'systemAuto', null, '0', '6', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('512345678910111213', 'RES10005', '菜单管理', 'RES10002', '/menu/tree', '7', '1', null, 'view', '10002', '1', null, '2017-07-25 09:24:57', 'superadmin', '2018-02-11 16:50:58', 'superadmin', null, '0', '6', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('612345678910111213', 'RES10006', '机构管理', 'RES10002', '/org/tree', '1', '1', null, 'view', '10002', '1', null, null, 'test100', '2020-06-12 16:17:40', 'USR170120000087', null, '0', '8', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('727106481772199936', 'MENU727106481587650560', '编号管理', 'RES10002', '/ecdgen/list', '13', '1', null, 'view', '10002', '1', '编号管理列表', null, 'USR170120000087', '2020-06-29 10:27:21', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('727204996704342016', 'MENU727204996565929984', '编号事件管理', 'MENU727106481587650560', null, null, '1', null, 'click', '10002', '1', null, '2020-06-29 16:53:02', 'USR170120000087', '2020-06-29 16:53:02', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('728555967008722944', 'MENU728555966975168512', '数据权限管理', 'RES10002', '/dataauth/query', '15', '1', null, 'view', '10002', '1', null, null, 'USR170120000087', '2020-07-09 13:44:37', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('728556381007499264', 'MENU728556380969750528', '权限事件管理', 'MENU728555966975168512', null, null, '1', null, 'click', '10002', '1', null, '2020-07-03 10:22:57', 'USR170120000087', '2020-07-03 10:22:57', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('729642449716260864', 'MENU729642449586237440', '数据权限模型管理', 'RES10002', '/dataAuthortyModelManage', '14', '1', null, 'view', '10002', '1', '数据权限模型管理', null, 'USR170120000087', '2020-07-07 11:12:44', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('729729587635957760', 'MENU729729587614986240', '数据权限模型管理', 'MENU729642449586237440', null, null, '1', null, 'click', '10002', '1', null, '2020-07-06 16:04:51', 'USR170120000087', '2020-07-06 16:04:51', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('729732602627502080', 'MENU729732602610724864', '新增修改', 'MENU729642449586237440', '/dataAuthortyModelManage/add', null, '1', null, 'click', '10002', '1', null, '2020-07-06 16:16:50', 'USR170120000087', '2020-07-06 16:16:50', 'USR170120000087', null, '0', '0', 'defaultTenantId');
INSERT INTO `SYS_RES_MENU` VALUES ('912345678910111213', 'RES10009', '数据字典', 'RES10002', '/dict/list', '10', '1', null, 'view', '10002', '1', null, '2020-06-22 15:11:08', 'USR170120000087', '2020-06-03 15:14:56', 'USR170120000087', null, '0', '2', 'defaultTenantId');



-- ----------------------------
-- Table structure for sys_res_menu_event_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_menu_event_rel`;
CREATE TABLE `sys_res_menu_event_rel` (
  `ID` char(32)  NOT NULL COMMENT '主键ID',
  `MENU_CODE` varchar(32)  NOT NULL COMMENT '菜单编号',
  `EVENT_CODE` varchar(32)  NOT NULL COMMENT '事件编号',
  `STATUS_CD` varchar(1)  DEFAULT NULL COMMENT '状态：无效0有效1',
  `REMARK` varchar(100)  DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32)  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1)  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32)  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=DYNAMIC COMMENT='系统菜单事件关系表';

-- ----------------------------
-- Records of sys_res_menu_event_rel
-- ----------------------------
INSERT INTO `sys_res_menu_event_rel` VALUES ('140', 'MENU1592209745256', 'SYSEVENT020', '1', null, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('141', 'MENU1592209745256', 'SYSEVENT021', '1', null, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('142', 'MENU1592209745256', 'SYSEVENT022', '1', null, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('143', 'MENU1592209745256', 'SYSEVENT023', '1', null, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('144', 'MENU1592209745256', 'SYSEVENT024', '1', null, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('150', 'MENU1592210170735', 'SYSEVENT044', '1', null, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('151', 'MENU1592210170735', 'SYSEVENT045', '1', null, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('152', 'MENU1592210170735', 'SYSEVENT046', '1', null, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('153', 'MENU1592210170735', 'SYSEVENT047', '1', null, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('154', 'MENU1592210170735', 'SYSEVENT048', '1', null, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('155', 'MENU1592210276320', 'SYSEVENT002', '1', null, '2020-06-15 16:39:05', 'USR170120000087', '2020-06-15 16:39:05', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('156', 'MENU1592210276320', 'SYSEVENT041', '1', null, '2020-06-15 16:39:05', 'USR170120000087', '2020-06-15 16:39:05', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('159', 'MENU1592210276320', 'SYSEVENT055', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('160', 'MENU1592210276320', 'SYSEVENT056', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('161', 'MENU1592210276320', 'SYSEVENT057', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('162', 'MENU1592210276320', 'SYSEVENT058', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('163', 'MENU1592210276320', 'SYSEVENT059', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('165', 'MENU1592210276320', 'SYSEVENT061', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('166', 'MENU1592210276320', 'SYSEVENT062', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('167', 'MENU1592210276320', 'SYSEVENT063', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('168', 'MENU1592210276320', 'SYSEVENT064', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('169', 'MENU1592210276320', 'SYSEVENT065', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('170', 'MENU1592210276320', 'SYSEVENT066', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('171', 'MENU1592210276320', 'SYSEVENT067', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('172', 'MENU1592210276320', 'SYSEVENT068', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('173', 'MENU1592210276320', 'SYSEVENT069', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('174', 'MENU1592210276320', 'SYSEVENT070', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('175', 'MENU1592210276320', 'SYSEVENT071', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('176', 'MENU1592210276320', 'SYSEVENT072', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('177', 'MENU1592210276320', 'SYSEVENT073', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('178', 'MENU1592210276320', 'SYSEVENT074', '1', null, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('179', 'MENU1592210396925', 'SYSEVENT025', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('180', 'MENU1592210396925', 'SYSEVENT026', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('181', 'MENU1592210396925', 'SYSEVENT027', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('182', 'MENU1592210396925', 'SYSEVENT028', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('183', 'MENU1592210396925', 'SYSEVENT029', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('184', 'MENU1592210396925', 'SYSEVENT030', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('185', 'MENU1592210396925', 'SYSEVENT031', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('186', 'MENU1592210396925', 'SYSEVENT032', '1', null, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('187', 'MENU1592210600121', 'SYSEVENT001', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('188', 'MENU1592210600121', 'SYSEVENT008', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('189', 'MENU1592210600121', 'SYSEVENT009', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('190', 'MENU1592210600121', 'SYSEVENT010', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('191', 'MENU1592210600121', 'SYSEVENT011', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('192', 'MENU1592210600121', 'SYSEVENT012', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('193', 'MENU1592210600121', 'SYSEVENT013', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('194', 'MENU1592210600121', 'SYSEVENT014', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('195', 'MENU1592210600121', 'SYSEVENT015', '1', null, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('196', 'MENU1592210660764', 'SYSEVENT033', '1', null, '2020-06-15 16:44:43', 'USR170120000087', '2020-06-15 16:44:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('197', 'MENU1592210660764', 'SYSEVENT034', '1', null, '2020-06-15 16:44:43', 'USR170120000087', '2020-06-15 16:44:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('198', 'MENU1592210660764', 'SYSEVENT035', '1', null, '2020-06-15 16:44:43', 'USR170120000087', '2020-06-15 16:44:43', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('199', 'MENU1592210660764', 'SYSEVENT036', '1', null, '2020-06-15 16:44:44', 'USR170120000087', '2020-06-15 16:44:44', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('200', 'MENU1592210710398', 'SYSEVENT078', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('201', 'MENU1592210710398', 'SYSEVENT079', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('202', 'MENU1592210710398', 'SYSEVENT080', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('203', 'MENU1592210710398', 'SYSEVENT081', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('204', 'MENU1592210710398', 'SYSEVENT082', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('205', 'MENU1592210710398', 'SYSEVENT083', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('206', 'MENU1592210710398', 'SYSEVENT084', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('207', 'MENU1592210710398', 'SYSEVENT085', '1', null, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('208', 'MENU1592210600121', 'SYSEVENT016', '1', null, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('209', 'MENU1592210600121', 'SYSEVENT017', '1', null, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('210', 'MENU1592210600121', 'SYSEVENT018', '1', null, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('211', 'MENU1592210600121', 'SYSEVENT019', '1', null, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('212', 'MENU1592210600121', 'SYSEVENT077', '1', null, '2020-06-15 16:57:17', 'USR170120000087', '2020-06-15 16:57:17', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('254', 'SYSRES001', 'SYSEVENT060', '1', null, '2020-06-17 17:03:42', 'USR170120000087', '2020-06-17 17:03:42', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('3', 'SYSRES001', 'SYSEVENT003', '1', null, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('42', 'SYSRES001', 'SYSEVENT042', '1', '', '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', '', '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('43', 'SYSRES001', 'SYSEVENT043', '1', null, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798704271360', 'MENU727204996565929984', 'SYSEVENT086', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798737825792', 'MENU727204996565929984', 'SYSEVENT087', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798771380224', 'MENU727204996565929984', 'SYSEVENT088', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798800740352', 'MENU727204996565929984', 'SYSEVENT089', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798825906176', 'MENU727204996565929984', 'SYSEVENT090', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798846877696', 'MENU727204996565929984', 'SYSEVENT091', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798872043520', 'MENU727204996565929984', 'SYSEVENT092', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798893015040', 'MENU727204996565929984', 'SYSEVENT093', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798913986560', 'MENU727204996565929984', 'SYSEVENT094', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798934958080', 'MENU727204996565929984', 'SYSEVENT095', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('727209798960123904', 'MENU727204996565929984', 'SYSEVENT096', '1', null, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('728558182045831168', 'MENU728556380969750528', 'SYSEVENT097', '1', null, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('728558182079385600', 'MENU728556380969750528', 'SYSEVENT098', '1', null, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('728558182108745728', 'MENU728556380969750528', 'SYSEVENT099', '1', null, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('728558182142300160', 'MENU728556380969750528', 'SYSEVENT100', '1', null, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('728558880942706688', 'MENU728556380969750528', 'SYSEVENT101', '1', null, '2020-07-03 10:32:53', 'USR170120000087', '2020-07-03 10:32:53', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729706493319983104', 'MENU729705775867506688', 'SYSEVENT102', '1', null, '2020-07-06 14:33:05', 'USR170120000087', '2020-07-06 14:33:05', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729708114473005056', 'MENU729705775867506688', 'SYSEVENT103', '1', null, '2020-07-06 14:39:32', 'USR170120000087', '2020-07-06 14:39:32', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729717912648167424', 'MENU729705775867506688', 'SYSEVENT104', '1', null, '2020-07-06 15:18:28', 'USR170120000087', '2020-07-06 15:18:28', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729721864429121536', 'MENU729705775867506688', 'SYSEVENT105', '1', null, '2020-07-06 15:34:10', 'USR170120000087', '2020-07-06 15:34:10', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729726634124521472', 'MENU729705775867506688', 'SYSEVENT106', '1', null, '2020-07-06 15:53:07', 'USR170120000087', '2020-07-06 15:53:07', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729727067530342400', 'MENU729705775867506688', 'SYSEVENT107', '1', null, '2020-07-06 15:54:51', 'USR170120000087', '2020-07-06 15:54:51', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729727424591441920', 'MENU729705775867506688', 'SYSEVENT108', '1', null, '2020-07-06 15:56:16', 'USR170120000087', '2020-07-06 15:56:16', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729727678946619392', 'MENU729705775867506688', 'SYSEVENT109', '1', null, '2020-07-06 15:57:16', 'USR170120000087', '2020-07-06 15:57:16', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729732055837061120', 'MENU729729587614986240', 'SYSEVENT102', '1', null, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729732055862226944', 'MENU729729587614986240', 'SYSEVENT103', '1', null, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729732055883198464', 'MENU729729587614986240', 'SYSEVENT104', '1', null, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729732055899975680', 'MENU729729587614986240', 'SYSEVENT105', '1', null, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729990539656704000', 'MENU729732602610724864', 'SYSEVENT106', '1', null, '2020-07-07 09:21:47', 'USR170120000087', '2020-07-07 09:21:47', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729990579976548352', 'MENU729732602610724864', 'SYSEVENT107', '1', null, '2020-07-07 09:21:57', 'USR170120000087', '2020-07-07 09:21:57', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729990627384766464', 'MENU729732602610724864', 'SYSEVENT108', '1', null, '2020-07-07 09:22:08', 'USR170120000087', '2020-07-07 09:22:08', 'USR170120000087', null, '0', '0', '');
INSERT INTO `sys_res_menu_event_rel` VALUES ('729990627397349376', 'MENU729732602610724864', 'SYSEVENT109', '1', null, '2020-07-07 09:22:08', 'USR170120000087', '2020-07-07 09:22:08', 'USR170120000087', null, '0', '0', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `ROLE_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '角色编号',
  `ROLE_NAME` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '角色名称',
  `PARENT_ROLE_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '父级节点编号',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态：无效0有效1',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户id',
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端ID',
  `SORT` int(10) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'rl0', '平台管理员', null, '1', 'superadmin', '2017-01-11 10:47:06', 'systemAuto', '2020-07-09 07:03:08', 'USR170120000087', null, '0', '24', 'defaultTenantId', '10002', '1');

-- ----------------------------
-- Table structure for sys_role_permission_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_rel`;
CREATE TABLE `sys_role_permission_rel` (
  `ID` char(32) NOT NULL COMMENT '主键ID',
  `PERMISSION_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '权限编码',
  `ROLE_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '角色编码',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  NOT NULL COMMENT '删除标志',
  `VERSION` int(10) NOT NULL COMMENT '版本号',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态STATUS:无效0/有效1',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT ' 备注',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统角色与权限关系表';

-- ----------------------------
-- Records of sys_role_permission_rel
-- ----------------------------
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127825350656', 'AHR0009', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127896653824', 'AHR733692301735002112', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127900848128', 'AHR731091419746082816', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127909236736', 'AHR731091215772884992', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127913431040', 'AHR730797018180296704', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127917625344', 'AHR729732602640084992', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127921819648', 'AHR729729587652734976', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127926013952', 'AHR729642449728843776', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127926013953', 'AHR728556381032665088', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127930208256', 'AHR728555967025500160', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127938596864', 'AHR727204996737896448', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127942791168', 'AHR727106481818337280', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127946985472', 'AHR726758469198745600', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127951179776', 'AHR726758247454281728', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127955374080', 'AHR0006', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127959568384', 'SYSAHR001', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127963762688', 'AHR0005', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127963762689', 'AHR0004', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127967956992', 'MENU1591336356244', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127972151296', 'MENU1591336169316', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127972151297', 'AHR0003', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127976345600', 'AHR0002', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127980539904', 'AHR1592210600149', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127980539905', 'AHR1592210396958', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127984734208', 'AHR1592210276363', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127988928512', 'AHR1592210170770', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731127997317120', 'AHR1592209745281', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731128005705728', 'AHR1592206017431', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731128014094336', 'AHR41211', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('737731128018288640', 'AHR0001', 'rl0', '2020-07-28 18:00:07', 'USR170120000087', '2020-07-28 18:00:07', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');
INSERT INTO `SYS_ROLE_PERMISSION_REL` VALUES ('738405414211354624', 'AHR41214', 'rl0', '2020-07-30 14:39:30', 'USR170120000087', '2020-07-30 14:39:30', 'USR170120000087', null, '0', '0', '1', null, 'defaultTenantId');

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `ID` char(32) NOT NULL COMMENT '主键',
  `TENANT_NUM` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  `TENANT_NAME` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户名称',
  `MANAGER_NAME` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '管理员名称',
  `LOGIN_NAME` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '登录名',
  `PASSWORD` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '密码',
  `SALT` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '盐',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态STATUS:无效0/有效1',
  `ORG_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '统一社会信用证代码',
  `TELEPHONE` varchar(12) CHARACTER SET utf8  DEFAULT NULL COMMENT '????',
  `MOBILE` varchar(12) CHARACTER SET utf8  DEFAULT NULL COMMENT '???',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标志',
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `USER_NUM` varchar(32) DEFAULT NULL,
  `TENANT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统租户信息表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES ('1', null, '默认租户', 'superadmin', 'superadmin', 'b401f11862ae17d6397a7202cf985dc3', 'ec198545761841649208913eb95b10e0', '1', '91371581MA3C9XCT6J', null, '18959922908', '2020-06-17 09:56:14', 'USR170120000087', '2020-06-18 01:51:43', 'USR170120000087', null, '0', '0', 'USR170120000087', 'defaultTenantId');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` char(32) NOT NULL COMMENT '主键',
  `USER_NUM` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '用户编号',
  `REALNAME` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '用户名',
  `LOGIN_NAME` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '用户登录名',
  `PASSWORD` varchar(128) DEFAULT NULL COMMENT '密码',
  `SALT` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '盐',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态STATUS:无效0/有效1',
  `SEX_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '性别(SEX:男1\\女2)',
  `CERTIFICATE_TYPE_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '证件类型(CERTIFICATE_TYPE:身份证0\\学生证1\\工作证2\\士兵证3\\军官证4\\护照5)',
  `CERTIFICATE_NUM` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '证件号码',
  `QQ` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT 'QQ',
  `WECHAT` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '微信',
  `TELEPHONE` varchar(11) CHARACTER SET utf8  DEFAULT NULL COMMENT '固定电话',
  `MOBILE` varchar(11) CHARACTER SET utf8  DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(50) CHARACTER SET utf8  DEFAULT NULL COMMENT '邮箱',
  `FAX` varchar(11) CHARACTER SET utf8  DEFAULT NULL COMMENT '传真',
  `PASS_ERROR_COUNT` int(10) DEFAULT NULL COMMENT '密码错误次数',
  `LOGIN_SUCC_COUNT` int(10) DEFAULT NULL COMMENT '登录成功次数',
  `LOCK_TIME` date DEFAULT NULL COMMENT '锁定时间',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `PROVINCE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '省级',
  `CITY` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '城市级',
  `DISTRICT` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '区域级',
  `ADDRESS` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '地址',
  `CLNTEND_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端编号',
  `CLNTEND_NM` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '客户端名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新人',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标志',
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '上次登录时间',
  `CREATE_INST` varchar(32) DEFAULT NULL,
  `UPDATE_INST` varchar(32) DEFAULT NULL,
  `HDL_USER` varchar(32) DEFAULT NULL,
  `HDL_INST` varchar(32) DEFAULT NULL,
  `HDL_TIME` datetime DEFAULT NULL,
  `GRAYSCALE_VERSION` varchar(32) DEFAULT NULL COMMENT '灰度版本号',
  `CIPHER` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------

INSERT INTO `SYS_USER` VALUES ('431234567891011121', 'USR170120000087', 'superadmin', 'superadmin', 'b401f11862ae17d6397a7202cf985dc3', 'ec198545761841649208913eb95b10e0', '1', '1', null, null, null, null, '13215981234', '18959922908', '332431195@qq.com', null, null, null, null, null, null, null, null, null, '10002', null, '2017-01-20 15:37:10', 'USR170120000087', '2020-07-30 15:56:28', 'USR170120000087', null, '0', '0', 'defaultTenantId', '2020-01-13 09:45:58', 'systemAuto', 'systemAuto', 'systemAuto', 'systemAuto', '2019-10-09 17:44:43', null,null);
-- ----------------------------
-- Table structure for sys_user_dept_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept_rel`;
CREATE TABLE `sys_user_dept_rel` (
  `ID` char(32) NOT NULL,
  `USER_NUM` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '用户编号',
  `DEPT_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '部门代码',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标识',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `ORG_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '机构代码',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  `VERSION` int(10) DEFAULT NULL COMMENT '版本号',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户与部门关系表';

-- ----------------------------
-- Records of sys_user_dept_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_org_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_org_rel`;
CREATE TABLE `sys_user_org_rel` (
  `ID` char(32) NOT NULL COMMENT 'ID编号',
  `USER_NUM` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '用户编码',
  `ORG_CODE` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '机构代码',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态(STATUS:无效0\\有效1)',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '删除标识',
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `VERSION` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '版本号',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编码',
  `DEFAULT_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户与机构关系表';

-- ----------------------------
-- Records of sys_user_org_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_rel`;
CREATE TABLE `sys_user_role_rel` (
  `ID` char(32) NOT NULL,
  `USER_NUM` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '用户编码',
  `ROLE_CODE` varchar(32) CHARACTER SET utf8  NOT NULL COMMENT '角色编码',
  `STATUS_CD` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '状态',
  `REMARK` varchar(100) CHARACTER SET utf8  DEFAULT NULL COMMENT '备注',
  `DEL_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL,
  `CREATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '更新者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `VERSION` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '版本号',
  `EXPD_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '扩展编号',
  `TENANT_ID` varchar(32) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户编号',
  `DEFAULT_IND` varchar(1) CHARACTER SET utf8  DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户与角色关系表';

-- ----------------------------
-- Records of sys_user_role_rel
-- ----------------------------
INSERT INTO `sys_user_role_rel` VALUES ('25', 'USR170120000087', 'rl0', '1', null, '0', 'USR170120000087', '2015-11-28 10:20:19', 'USR170120000087', '2020-06-12 10:46:11', '23', null, '', '1');

-- ----------------------------
-- Table structure for t_dev_initializr
-- ----------------------------
DROP TABLE IF EXISTS `t_dev_initializr`;
CREATE TABLE `t_dev_initializr` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `yml_content` varchar(2000) DEFAULT NULL,
  `dependencies_content` varchar(2000) DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT '0',
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_dev_initializr
-- ----------------------------
INSERT INTO `t_dev_initializr` VALUES ('1', 'msdp-sysmgr', '系统管理', '', '<dependency>\r\n		    <groupId>com.jeedev.msdp.common</groupId>\r\n		    <artifactId>msdp-sysmgr</artifactId>\r\n		   \r\n		</dependency>', '0', '');
INSERT INTO `t_dev_initializr` VALUES ('2', 'msdp-iweb', 'web组件', '', '<dependency>\r\n	    <groupId>com.jeedev.msdp.common</groupId>\r\n	    <artifactId>msdp-iweb</artifactId>\r\n	</dependency>', '0', '');
INSERT INTO `t_dev_initializr` VALUES ('3', 'diskeygen', '雪花主键生成', '', '<dependency> \r\n				<groupId>com.tansun.tjmas</groupId> \r\n				<artifactId>tjmas-cloud-starter-base-diskeygen</artifactId> \r\n			</dependency>', '0', '');
INSERT INTO `t_dev_initializr` VALUES ('4', 'msdp-iencode', '编号生成器', '', '<dependency>\r\n			    <groupId>com.jeedev.msdp.common</groupId>\r\n			    <artifactId>msdp-iencode</artifactId>\r\n			</dependency>', '0', '');
INSERT INTO `t_dev_initializr` VALUES ('5', 'msdp-swagger2', 'swagger2在线接口', '', '<dependency>\r\n		<groupId>com.jeedev.msdp.common</groupId>\r\n	  	<artifactId>msdp-swagger2</artifactId>\r\n	</dependency>', '0', '');
INSERT INTO `t_dev_initializr` VALUES ('6', 'msdp-iaddon', '可选插件', '', '<dependency>\r\n	    <groupId>com.jeedev.msdp.common</groupId>\r\n	    <artifactId>msdp-iaddon</artifactId>\r\n	  </dependency>', '0', '');
INSERT INTO `t_dev_initializr` VALUES ('7', 'tjmas-cloud-starter-base-seata', '分布式事务', 'seata:                                 # 依赖全新天魔斯seata1.3组件客户端配置,支持最新的官方seata1.3\r\n  enabled: true   # 本地事务和分布式事务的切换开关，默认为true,微服务分布式事务；当切换为单体的时候，则设置成false，则为本地事务\r\n  application-id: seata-disms-demo-a\r\n  tx-service-group: my_test_tx_group\r\n  enable-auto-data-source-proxy: true    #开启本地自动数据源代理，默认是自动开启\r\n  use-jdk-proxy: false      #默认为false，采用CGLIB作为数据源自动代理的实现方式。\r\n#  excludes-for-auto-proxying: firstClassNameForExclude,secondClassNameForExclude\r\n  config:\r\n    #  #    #    type: file       #配置中心为本地文件\r\n    type: nacos\r\n    nacos:\r\n      namespace: \"5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca\"  #nacos命名空间ID\r\n      serverAddr: ${spring.cloud.nacos.config.server-addr}   #配置中心IP和端口 测试环境 ::8848\r\n      group: SEATA_GROUP                #nacos命名组\r\n      username: \"\"                      #nacos登陆用户名\r\n      password: \"\"                      #nacos登陆密码\r\n  registry:\r\n    #    type: file    #注册中心类型是file,可以本地项目调试验证\r\n    #    type: eureka\r\n    #    eureka:    #注册中心类型支持eureka\r\n    #      weight: 1     #配置注册中心权重\r\n    #      application: default   #应用名 默认\r\n    #      service-url: http://localhost:8761/eureka   #配置注册中心IP和端口\r\n    type: nacos\r\n    nacos:        #注册中心类型是nacos\r\n      application: seata-server\r\n      server-addr: ${spring.cloud.nacos.config.server-addr}   #注册中心IP和端口 测试环境 ::8848\r\n      group: \"SEATA_GROUP\"                     #nacos命名组\r\n      namespace: \"\"                               #nacos命名空间ID,此处默认为空\r\n      username: \"\"                              #nacos登陆用户名\r\n      password: \"\"                                #nacos登陆密码\r\n      cluster: \"default\"                          #nacos seata集群', ' <dependency>\r\n            <groupId>com.tansun.tjmas</groupId>\r\n            <artifactId>tjmas-cloud-starter-base-seata</artifactId>\r\n            <version>1.3.0-RELEASE</version>\r\n        </dependency>', '0', '');

-- ----------------------------
-- Table structure for t_help_def_exception
-- ----------------------------
DROP TABLE IF EXISTS `t_help_def_exception`;
CREATE TABLE `t_help_def_exception` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `TYPE` varchar(1) NOT NULL COMMENT '类型(0=模块，1=异常)',
  `E_TYPE` varchar(100) DEFAULT NULL COMMENT '异常类型',
  `CODE` varchar(50) NOT NULL COMMENT '代码',
  `NAME` varchar(100) NOT NULL COMMENT '名称',
  `CONTENT` varchar(200) DEFAULT NULL COMMENT '内容',
  `PID` varchar(32) DEFAULT NULL COMMENT '父ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) NOT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) NOT NULL COMMENT '更新人',
  `DEL_IND` varchar(1) NOT NULL COMMENT '删除标志(0=未删除，1=已删除)',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TENANT_ID` varchar(32) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常信息定义表';

-- ----------------------------
-- Records of t_help_def_exception
-- ----------------------------
INSERT INTO `t_help_def_exception` VALUES ('1', '1', '数据库操作异常', 'common-00001', '数据库操作异常', '', '', '2020-04-27 15:49:25', '1', '2020-04-27 15:49:22', '1', '1', null, '');

-- ----------------------------
-- Table structure for t_help_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_help_feedback`;
CREATE TABLE `t_help_feedback` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `F_USER_ID` varchar(32) NOT NULL COMMENT '反馈人(用户ID)',
  `CONTENT` varchar(200) NOT NULL COMMENT '内容',
  `E_CODE` varchar(50) NOT NULL COMMENT '异常代码',
  `MID` varchar(32) NOT NULL COMMENT '模块ID',
  `M_NAME` varchar(100) NOT NULL COMMENT '模块名称',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) NOT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) NOT NULL COMMENT '更新人',
  `DEL_IND` varchar(1) NOT NULL COMMENT '删除标志(0=未删除，1=已删除)',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TENANT_ID` varchar(32) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='反馈信息表';

-- ----------------------------
-- Records of t_help_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_guide_flag
-- ----------------------------
DROP TABLE IF EXISTS `t_help_guide_flag`;
CREATE TABLE `t_help_guide_flag` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `LOGIN_NAME` varchar(32) DEFAULT NULL COMMENT '用户登录名',
  `LOGIN_FIRST` varchar(1) NOT NULL COMMENT '首次登录(0=否，1=是)',
  `NEW_LOGIN_FIRST` varchar(1) NOT NULL COMMENT '新功能首次登录(0=否，1=是)',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) NOT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) NOT NULL COMMENT '更新人',
  `DEL_IND` varchar(1) NOT NULL COMMENT '删除标志(0=未删除，1=已删除)',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TENANT_ID` varchar(32) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户向导标识表';

-- ----------------------------
-- Records of t_help_guide_flag
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_tips
-- ----------------------------
DROP TABLE IF EXISTS `t_help_tips`;
CREATE TABLE `t_help_tips` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `TYPE` varchar(1) NOT NULL COMMENT '类型(0=提示类别，1=提示)',
  `CODE` varchar(50) NOT NULL COMMENT '代码',
  `TITLE` varchar(100) NOT NULL COMMENT '标题',
  `CONTENT` varchar(200) DEFAULT NULL COMMENT '内容',
  `PID` varchar(32) DEFAULT NULL COMMENT '父ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) NOT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) NOT NULL COMMENT '更新人',
  `DEL_IND` varchar(1) NOT NULL COMMENT '删除标志(0=未删除，1=已删除)',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TENANT_ID` varchar(32) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作提示表';

-- ----------------------------
-- Records of t_help_tips
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_module
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_module`;
CREATE TABLE `t_sys_module` (
  `ID` varchar(32) NOT NULL COMMENT 'ID',
  `TYPE` varchar(1) DEFAULT NULL COMMENT '类型',
  `CODE` varchar(50) NOT NULL COMMENT '代码',
  `NAME` varchar(100) NOT NULL COMMENT '名称',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '描述',
  `PID` varchar(32) DEFAULT NULL COMMENT '父ID',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(32) NOT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(32) NOT NULL COMMENT '更新人',
  `DEL_IND` varchar(1) NOT NULL COMMENT '删除标志(0=未删除，1=已删除)',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本',
  `TENANT_ID` varchar(32) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统模块表';

-- ----------------------------
-- Records of t_sys_module
-- ----------------------------
