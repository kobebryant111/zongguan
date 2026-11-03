

-- ----------------------------
-- Table structure for base_ddct
-- ----------------------------
DROP TABLE IF EXISTS "base_ddct";
CREATE TABLE "base_ddct" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dct_key" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dct_tp_cd" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dct_tp_nm" varchar(100) COLLATE "pg_catalog"."default",
  "dct_val" varchar(32) COLLATE "pg_catalog"."default",
  "dct_val_nm" varchar(100) COLLATE "pg_catalog"."default",
  "dct_grp" varchar(32) COLLATE "pg_catalog"."default",
  "dct_dsc" varchar(32) COLLATE "pg_catalog"."default",
  "dct_seq" int4,
  "st_cd" varchar(1) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "sys_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "base_ddct"."id" IS '主键ID';
COMMENT ON COLUMN "base_ddct"."dct_key" IS '字典键';
COMMENT ON COLUMN "base_ddct"."dct_tp_cd" IS '字典类型';
COMMENT ON COLUMN "base_ddct"."dct_tp_nm" IS '字典类型名称';
COMMENT ON COLUMN "base_ddct"."dct_val" IS '字典值';
COMMENT ON COLUMN "base_ddct"."dct_val_nm" IS '字典值名称';
COMMENT ON COLUMN "base_ddct"."dct_grp" IS '字典组';
COMMENT ON COLUMN "base_ddct"."dct_dsc" IS '字典描述';
COMMENT ON COLUMN "base_ddct"."dct_seq" IS '字典排序';
COMMENT ON COLUMN "base_ddct"."st_cd" IS '状态：无效0\有效1';
COMMENT ON COLUMN "base_ddct"."create_time" IS '创建时间';
COMMENT ON COLUMN "base_ddct"."create_user" IS '创建人';
COMMENT ON COLUMN "base_ddct"."update_time" IS '更新时间';
COMMENT ON COLUMN "base_ddct"."update_user" IS '更新人';
COMMENT ON COLUMN "base_ddct"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "base_ddct"."del_ind" IS '删除标志';
COMMENT ON COLUMN "base_ddct"."version" IS '版本号';
COMMENT ON COLUMN "base_ddct"."tenant_id" IS '租户id';
COMMENT ON COLUMN "base_ddct"."sys_id" IS '系统ID';
COMMENT ON TABLE "base_ddct" IS '数据字典';

-- ----------------------------
-- Records of base_ddct
-- ----------------------------
INSERT INTO "base_ddct" VALUES ('1', 'MenuType_0', 'MenuType', '菜单类型', 'view', '页面', '', '', 0, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-06-16 15:01:56', 'USR170120000087', '', '0', 19, '', NULL);
INSERT INTO "base_ddct" VALUES ('10', 'OrgLevel_0', 'OrgLevel', '机构等级', 'LV00', '零级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('17', 'Gnd_1', 'Gnd', '性别', '1', '男', NULL, '', 1, '1', '2016-11-25 17:50:01', 'systemAuto', '2020-06-18 09:36:08', 'USR170120000087', NULL, '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('18', 'Gnd_2', 'Gnd', '性别', '2', '女', NULL, NULL, 2, '1', '2016-11-25 17:50:01', 'systemAuto', '2016-11-25 17:50:01', 'systemAuto', NULL, '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('2', 'MenuType_1', 'MenuType', '菜单类型', 'click', '按钮', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('23', '10004', 'EcdRuleSrc', '编号明细', 'sysDateFill', '系统日期', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 15:53:59', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('24', '10005', 'EcdRuleSrc', '编号明细', 'zeroFill', '序列', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('25', 'constantFill', 'EcdRuleSrc', '编号明细', 'constantFill', '常量', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('26', 'variableFill', 'EcdRuleSrc', '编号明细', 'variableFill', '变量', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('27', 'dynamicSerialFill', 'EcdRuleSrc', '编号明细', 'dynamicSerialFill', '动态序列', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('28', 'cascadeSerialFill', 'EcdRuleSrc', '编号明细', 'cascadeSerialFill', '级联序列', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('3', 'StCd_0', 'StCd', '通用状态代码', '0', '未生效', '1', '', 1, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('4', 'StCd_1', 'StCd', '通用状态代码', '1', '生效', '1', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('48', 'Indicator_0', 'Indicator', '指示器', '0', '否', NULL, NULL, 1, '1', NULL, NULL, '2020-06-16 14:49:15', 'USR170120000087', NULL, '0', 1, '', NULL);
INSERT INTO "base_ddct" VALUES ('49', 'Indicator_1', 'Indicator', '指示器', '1', '是', NULL, NULL, 2, '1', NULL, NULL, '2020-06-16 14:49:37', 'USR170120000087', NULL, '0', 1, '', NULL);
INSERT INTO "base_ddct" VALUES ('630', 'StCd_2', 'StCd', '通用状态代码', '2', '失效', '1', NULL, 3, '1', '2017-11-01 17:53:19', 'systemAuto', '2017-11-01 17:53:29', 'systemAuto', NULL, '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('636', 'MenuType_2', 'MenuType', '菜单类型', 'no-source', '目录', '', '', 3, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', NULL);
INSERT INTO "base_ddct" VALUES ('654', 'BizSceneType_0', 'BizSceneType', '业务场景类型', '*', '*', '1', '', 1, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 18:45:21', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('655', 'BizSceneType_1', 'BizSceneType', '业务场景类型', 'CprsvEnqr', '综合查询', '1', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 18:59:32', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('656', 'BizSceneType_2', 'BizSceneType', '业务场景类型', 'tenantManage', '租户管理', '0', '', 3, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 18:59:47', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('657', 'BizSceneType_3', 'BizSceneType', '业务场景类型', 'Circle', '圈链场景', '1', '', 4, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 19:36:51', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('658', 'BizSceneType_4', 'BizSceneType', '业务场景类型', 'RcvbTrs', '应收账款转让场景', '1', '', 5, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 21:12:42', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('659', 'BizSceneType_5', 'BizSceneType', '业务场景类型', 'Approve', '审批场景', '1', '', 6, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 21:08:07', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('660', 'BizSceneType_6', 'BizSceneType', '业务场景类型', 'Network', '项目方案场景', '1', '', 7, '1', '2016-11-14 20:02:59', 'systemAuto', '2020-07-08 21:03:47', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('675', 'BizSceneType_7', 'BizSceneType', '业务场景类型', 'Cst', '客户管理场景', '1', '', 8, '1', '2017-11-21 14:15:38', 'systemAuto', '2020-07-08 21:46:45', NULL, '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('749', 'OrgLevel_1', 'OrgLevel', '机构等级', 'LV01', '一级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('750', 'OrgLevel_2', 'OrgLevel', '机构等级', 'LV02', '二级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('751', 'OrgLevel_3', 'OrgLevel', '机构等级', 'LV03', '三级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('752', 'OrgLevel_4', 'OrgLevel', '机构等级', 'LV04', '四级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('753', 'OrgLevel_5', 'OrgLevel', '机构等级', 'LV05', '五级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', '');
INSERT INTO "base_ddct" VALUES ('754', 'OrgLevel_6', 'OrgLevel', '机构等级', 'LV06', '六级', '', '', 2, '1', '2016-11-14 20:02:59', 'systemAuto', '2016-11-14 20:03:18', 'systemAuto', '', '0', 0, '', NULL);

-- ----------------------------
-- Table structure for base_ecd_dtl
-- ----------------------------
DROP TABLE IF EXISTS "base_ecd_dtl";
CREATE TABLE "base_ecd_dtl" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "ecd_dtl_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "ecd_tp_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "rule_id" varchar(32) COLLATE "pg_catalog"."default",
  "rule_src" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "sn" int4,
  "ecd_len" int4,
  "ecd_val" varchar(32) COLLATE "pg_catalog"."default",
  "st_cd" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "base_ecd_dtl"."id" IS '编码明细ID';
COMMENT ON COLUMN "base_ecd_dtl"."ecd_dtl_id" IS '编码明细编号';
COMMENT ON COLUMN "base_ecd_dtl"."ecd_tp_id" IS '编码类型编号';
COMMENT ON COLUMN "base_ecd_dtl"."rule_id" IS '编码生成规则编号';
COMMENT ON COLUMN "base_ecd_dtl"."rule_src" IS '规则源，解析规则的服务名称';
COMMENT ON COLUMN "base_ecd_dtl"."sn" IS '序号';
COMMENT ON COLUMN "base_ecd_dtl"."ecd_len" IS '编码长度';
COMMENT ON COLUMN "base_ecd_dtl"."ecd_val" IS '编码值';
COMMENT ON COLUMN "base_ecd_dtl"."st_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "base_ecd_dtl"."create_time" IS '创建时间';
COMMENT ON COLUMN "base_ecd_dtl"."create_user" IS '创建人';
COMMENT ON COLUMN "base_ecd_dtl"."update_time" IS '更新时间';
COMMENT ON COLUMN "base_ecd_dtl"."update_user" IS '更新人';
COMMENT ON COLUMN "base_ecd_dtl"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "base_ecd_dtl"."del_ind" IS '删除标志';
COMMENT ON COLUMN "base_ecd_dtl"."version" IS '版本号';
COMMENT ON COLUMN "base_ecd_dtl"."tenant_id" IS '租户编号';
COMMENT ON TABLE "base_ecd_dtl" IS '编码明细';

-- ----------------------------
-- Records of base_ecd_dtl
-- ----------------------------

-- ----------------------------
-- Table structure for base_ecd_serial
-- ----------------------------
DROP TABLE IF EXISTS "base_ecd_serial";
CREATE TABLE "base_ecd_serial" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "prefix_code" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "sn_tp_cd" varchar(12) COLLATE "pg_catalog"."default" NOT NULL,
  "sn_val" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "ecd_tp_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "base_ecd_serial"."id" IS '主键ID';
COMMENT ON COLUMN "base_ecd_serial"."prefix_code" IS '编码前缀代码';
COMMENT ON COLUMN "base_ecd_serial"."sn_tp_cd" IS '序列类型(cascade级联序列/dynamic动态序列)';
COMMENT ON COLUMN "base_ecd_serial"."sn_val" IS '编码值';
COMMENT ON COLUMN "base_ecd_serial"."create_time" IS '创建时间';
COMMENT ON COLUMN "base_ecd_serial"."create_user" IS '创建人';
COMMENT ON COLUMN "base_ecd_serial"."update_time" IS '更新时间';
COMMENT ON COLUMN "base_ecd_serial"."update_user" IS '更新人';
COMMENT ON COLUMN "base_ecd_serial"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "base_ecd_serial"."del_ind" IS '删除标志';
COMMENT ON COLUMN "base_ecd_serial"."version" IS '版本号';
COMMENT ON TABLE "base_ecd_serial" IS '业务编码规则动态序列和级联序列明细';

-- ----------------------------
-- Records of base_ecd_serial
-- ----------------------------

-- ----------------------------
-- Table structure for base_ecd_tp
-- ----------------------------
DROP TABLE IF EXISTS "base_ecd_tp";
CREATE TABLE "base_ecd_tp" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "ecd_tp_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "ecd_tp_nm" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "rmrk" varchar(100) COLLATE "pg_catalog"."default",
  "st_cd" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "base_ecd_tp"."id" IS '编码生成规则ID';
COMMENT ON COLUMN "base_ecd_tp"."ecd_tp_id" IS '编码类型编号';
COMMENT ON COLUMN "base_ecd_tp"."ecd_tp_nm" IS '编码类型名称';
COMMENT ON COLUMN "base_ecd_tp"."rmrk" IS '备注';
COMMENT ON COLUMN "base_ecd_tp"."st_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "base_ecd_tp"."create_time" IS '创建时间';
COMMENT ON COLUMN "base_ecd_tp"."create_user" IS '创建人';
COMMENT ON COLUMN "base_ecd_tp"."update_time" IS '更新时间';
COMMENT ON COLUMN "base_ecd_tp"."update_user" IS '更新人';
COMMENT ON COLUMN "base_ecd_tp"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "base_ecd_tp"."del_ind" IS '删除标志';
COMMENT ON COLUMN "base_ecd_tp"."version" IS '版本号';
COMMENT ON COLUMN "base_ecd_tp"."tenant_id" IS '租户编号';
COMMENT ON TABLE "base_ecd_tp" IS '编码类型';

-- ----------------------------
-- Records of base_ecd_tp
-- ----------------------------

-- ----------------------------
-- Table structure for sys_clntend
-- ----------------------------
DROP TABLE IF EXISTS "sys_clntend";
CREATE TABLE "sys_clntend" (
  "id" int4 NOT NULL,
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_nm" varchar(50) COLLATE "pg_catalog"."default",
  "clntend_secret" varchar(50) COLLATE "pg_catalog"."default",
  "clntend_domain" varchar(50) COLLATE "pg_catalog"."default",
  "clntend_st" varchar(10) COLLATE "pg_catalog"."default",
  "create_time" date,
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" date,
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_clntend"."clntend_id" IS '客户端编号';
COMMENT ON COLUMN "sys_clntend"."clntend_nm" IS '客户端名称';
COMMENT ON COLUMN "sys_clntend"."clntend_secret" IS '客户端安全码';
COMMENT ON COLUMN "sys_clntend"."clntend_domain" IS '客户端域名';
COMMENT ON COLUMN "sys_clntend"."clntend_st" IS '客户端状态(1:启用，2:禁用)';
COMMENT ON COLUMN "sys_clntend"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_clntend"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_clntend"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_clntend"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_clntend"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_clntend"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_clntend"."version" IS '版本号';
COMMENT ON COLUMN "sys_clntend"."tenant_id" IS '租户编号';
COMMENT ON TABLE "sys_clntend" IS '客户端表';

-- ----------------------------
-- Records of sys_clntend
-- ----------------------------
INSERT INTO "sys_clntend" VALUES (1, '10001', '协同前台', 'abcd', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, 0, '');
INSERT INTO "sys_clntend" VALUES (2, '10002', '协同后台', 'abcd', '', '1', '2016-12-26', 'systemAuto', '2016-12-26', 'systemAuto', '', '', 0, '');

-- ----------------------------
-- Table structure for sys_data_auth
-- ----------------------------
DROP TABLE IF EXISTS "sys_data_auth";
CREATE TABLE "sys_data_auth" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "data_auth_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "model_code" varchar(32) COLLATE "pg_catalog"."default",
  "auth_conditions" varchar(256) COLLATE "pg_catalog"."default",
  "conditions_remark" varchar(256) COLLATE "pg_catalog"."default",
  "remark" varchar(126) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "dept_code" varchar(32) COLLATE "pg_catalog"."default",
  "role_code" varchar(32) COLLATE "pg_catalog"."default",
  "org_code" varchar(32) COLLATE "pg_catalog"."default",
  "user_num" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default",
  "biz_scene" varchar(32) COLLATE "pg_catalog"."default",
  "event_code" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_data_auth"."id" IS '主键ID';
COMMENT ON COLUMN "sys_data_auth"."data_auth_code" IS '数据权限编号';
COMMENT ON COLUMN "sys_data_auth"."model_code" IS '数据权限类型编码';
COMMENT ON COLUMN "sys_data_auth"."auth_conditions" IS '过滤条件';
COMMENT ON COLUMN "sys_data_auth"."conditions_remark" IS '过滤条件备注';
COMMENT ON COLUMN "sys_data_auth"."remark" IS '备注';
COMMENT ON COLUMN "sys_data_auth"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_data_auth"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_data_auth"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_data_auth"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_data_auth"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_data_auth"."del_ind" IS '删除标志:0-未删除；1-删除';
COMMENT ON COLUMN "sys_data_auth"."version" IS '版本号';
COMMENT ON COLUMN "sys_data_auth"."tenant_id" IS '租户id';
COMMENT ON COLUMN "sys_data_auth"."dept_code" IS '部门代码';
COMMENT ON COLUMN "sys_data_auth"."role_code" IS '角色编号';
COMMENT ON COLUMN "sys_data_auth"."org_code" IS '机构代码';
COMMENT ON COLUMN "sys_data_auth"."user_num" IS '用户编号';
COMMENT ON COLUMN "sys_data_auth"."clntend_id" IS '客户端编号';
COMMENT ON COLUMN "sys_data_auth"."biz_scene" IS '业务场景';
COMMENT ON COLUMN "sys_data_auth"."event_code" IS '事件编号';
COMMENT ON TABLE "sys_data_auth" IS '系统数据权限表';

-- ----------------------------
-- Records of sys_data_auth
-- ----------------------------

-- ----------------------------
-- Table structure for sys_data_auth_fkcolumn
-- ----------------------------
DROP TABLE IF EXISTS "sys_data_auth_fkcolumn";
CREATE TABLE "sys_data_auth_fkcolumn" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "fkcolumn_id" varchar(32) COLLATE "pg_catalog"."default",
  "model_code" varchar(32) COLLATE "pg_catalog"."default",
  "fkcolumn_st_cd" varchar(1) COLLATE "pg_catalog"."default",
  "fkcolumn_res_sql" varchar(200) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "create_user" varchar(200) COLLATE "pg_catalog"."default",
  "update_user" varchar(200) COLLATE "pg_catalog"."default",
  "expd_id" varchar(200) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "version" varchar(10) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."id" IS '主键';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."fkcolumn_id" IS '外键规则编号';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."model_code" IS '数据权限模型关联编号';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."fkcolumn_st_cd" IS '外键关联状态';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."fkcolumn_res_sql" IS '外键过滤查询语句';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."del_ind" IS '删除标识';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."version" IS '版本号';
COMMENT ON COLUMN "sys_data_auth_fkcolumn"."tenant_id" IS '租户编号';
COMMENT ON TABLE "sys_data_auth_fkcolumn" IS '数据权限外键字段规则表';

-- ----------------------------
-- Records of sys_data_auth_fkcolumn
-- ----------------------------

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS "sys_department";
CREATE TABLE "sys_department" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dept_code" varchar(32) COLLATE "pg_catalog"."default",
  "dept_name" varchar(100) COLLATE "pg_catalog"."default",
  "parent_dept_code" varchar(32) COLLATE "pg_catalog"."default",
  "dept_level_cd" varchar(32) COLLATE "pg_catalog"."default",
  "sort" int4,
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_department"."id" IS '主键ID';
COMMENT ON COLUMN "sys_department"."dept_code" IS '部门代码';
COMMENT ON COLUMN "sys_department"."dept_name" IS '部门名称';
COMMENT ON COLUMN "sys_department"."parent_dept_code" IS '上级部门代码';
COMMENT ON COLUMN "sys_department"."dept_level_cd" IS '部门级别(一级1二级2三级3四级4)';
COMMENT ON COLUMN "sys_department"."sort" IS '排序';
COMMENT ON COLUMN "sys_department"."status_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "sys_department"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_department"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_department"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_department"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_department"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_department"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_department"."version" IS '版本号';
COMMENT ON COLUMN "sys_department"."remark" IS '备注';
COMMENT ON COLUMN "sys_department"."tenant_id" IS '租户编号';
COMMENT ON COLUMN "sys_department"."clntend_id" IS '客户端ID';
COMMENT ON TABLE "sys_department" IS '系统部门信息';

-- ----------------------------
-- Records of sys_department
-- ----------------------------

-- ----------------------------
-- Table structure for sys_org_dept_rel
-- ----------------------------
DROP TABLE IF EXISTS "sys_org_dept_rel";
CREATE TABLE "sys_org_dept_rel" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "org_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dept_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_org_dept_rel"."id" IS '主键编号';
COMMENT ON COLUMN "sys_org_dept_rel"."org_code" IS '机构编码';
COMMENT ON COLUMN "sys_org_dept_rel"."dept_code" IS '部门编码';
COMMENT ON COLUMN "sys_org_dept_rel"."status_cd" IS '状态';
COMMENT ON COLUMN "sys_org_dept_rel"."remark" IS '备注';
COMMENT ON COLUMN "sys_org_dept_rel"."del_ind" IS '删除标识';
COMMENT ON COLUMN "sys_org_dept_rel"."create_user" IS '创建者';
COMMENT ON COLUMN "sys_org_dept_rel"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_org_dept_rel"."update_user" IS '更新者';
COMMENT ON COLUMN "sys_org_dept_rel"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_org_dept_rel"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_org_dept_rel"."version" IS '版本号';
COMMENT ON COLUMN "sys_org_dept_rel"."tenant_id" IS '租户编号';
COMMENT ON TABLE "sys_org_dept_rel" IS '系统机构与部门关系表';

-- ----------------------------
-- Records of sys_org_dept_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS "sys_organization";
CREATE TABLE "sys_organization" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "org_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "org_name" varchar(100) COLLATE "pg_catalog"."default",
  "parent_org_code" varchar(32) COLLATE "pg_catalog"."default",
  "org_level_cd" varchar(32) COLLATE "pg_catalog"."default",
  "sort" int4,
  "ext_json_data" varchar(100) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_organization"."org_code" IS '机构代码';
COMMENT ON COLUMN "sys_organization"."org_name" IS '机构名称';
COMMENT ON COLUMN "sys_organization"."parent_org_code" IS '父节点机构代码';
COMMENT ON COLUMN "sys_organization"."org_level_cd" IS '机构等级';
COMMENT ON COLUMN "sys_organization"."sort" IS '排序';
COMMENT ON COLUMN "sys_organization"."status_cd" IS '状态';
COMMENT ON COLUMN "sys_organization"."remark" IS '备注';
COMMENT ON COLUMN "sys_organization"."del_ind" IS '删除标志:0-未删除；1-删除；';
COMMENT ON COLUMN "sys_organization"."create_user" IS '创建者';
COMMENT ON COLUMN "sys_organization"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_organization"."update_user" IS '更新者';
COMMENT ON COLUMN "sys_organization"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_organization"."version" IS '版本号';
COMMENT ON COLUMN "sys_organization"."tenant_id" IS '租户编号';
COMMENT ON COLUMN "sys_organization"."clntend_id" IS '客户端编号';
COMMENT ON TABLE "sys_organization" IS '系统机构表';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS "sys_permission";
CREATE TABLE "sys_permission" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "permission_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "permission_type_cd" varchar(32) COLLATE "pg_catalog"."default",
  "res_code" varchar(32) COLLATE "pg_catalog"."default",
  "permission_name" varchar(100) COLLATE "pg_catalog"."default",
  "is_built_in" varchar(1) COLLATE "pg_catalog"."default",
  "ext_json_data" varchar(100) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_permission"."id" IS '主键ID';
COMMENT ON COLUMN "sys_permission"."permission_code" IS '权限编号';
COMMENT ON COLUMN "sys_permission"."permission_type_cd" IS '权限类型(0菜单1页面元素2文件3数据权限)';
COMMENT ON COLUMN "sys_permission"."res_code" IS '资源编号';
COMMENT ON COLUMN "sys_permission"."permission_name" IS '权限名称';
COMMENT ON COLUMN "sys_permission"."is_built_in" IS '是否系统内置权限(否0是1)';
COMMENT ON COLUMN "sys_permission"."ext_json_data" IS '扩展数据';
COMMENT ON COLUMN "sys_permission"."status_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "sys_permission"."remark" IS '备注';
COMMENT ON COLUMN "sys_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_permission"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_permission"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_permission"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_permission"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_permission"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_permission"."version" IS '版本号';
COMMENT ON COLUMN "sys_permission"."tenant_id" IS '租户id';
COMMENT ON COLUMN "sys_permission"."clntend_id" IS '客户端编号';
COMMENT ON TABLE "sys_permission" IS '系统权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO "sys_permission" VALUES ('1', 'AHR0001', '0', 'RES10001', '我的设置', '0', NULL, '1', NULL, '2016-11-05 15:33:37', 'systemAuto', '2016-11-05 15:33:37', 'systemAuto', NULL, '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('11', 'AHR41211', 'view', 'RES10012', '部门管理', '0', NULL, '1', NULL, '2018-04-25 16:53:48', 'systemAuto', '2018-07-11 12:03:02', 'systemAuto', NULL, '0', 1, '', '10002');
INSERT INTO "sys_permission" VALUES ('1613', 'AHR1592206017431', 'click', 'MENU1592206017375', '新增或修改用户', NULL, NULL, '1', NULL, '2020-06-15 15:26:57', NULL, '2020-06-15 15:26:57', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('1615', 'AHR1592209745281', 'click', 'MENU1592209745256', '机构事件管理', NULL, NULL, '1', NULL, '2020-06-15 16:29:05', NULL, '2020-06-15 16:29:05', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('1616', 'AHR1592210170770', 'click', 'MENU1592210170735', '部门事件管理', NULL, NULL, '1', NULL, '2020-06-15 16:36:11', NULL, '2020-06-15 16:36:11', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('1617', 'AHR1592210276363', 'click', 'MENU1592210276320', '用户事件管理', NULL, NULL, '1', NULL, '2020-06-15 16:37:56', NULL, '2020-06-15 16:37:56', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('1618', 'AHR1592210396958', 'click', 'MENU1592210396925', '角色事件管理', NULL, NULL, '1', NULL, '2020-06-15 16:39:57', NULL, '2020-06-15 16:39:57', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('1619', 'AHR1592210600149', 'click', 'MENU1592210600121', '菜单事件管理', NULL, NULL, '1', NULL, '2020-06-15 16:43:20', NULL, '2020-06-15 16:43:20', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('1621', 'AHR1592210710429', 'click', 'MENU1592210710398', '租户事件管理', NULL, NULL, '1', NULL, '2020-06-15 16:45:10', NULL, '2020-06-15 16:45:10', NULL, NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('2', 'AHR0002', '0', 'RES10002', '系统管理', '0', NULL, '1', NULL, '2016-11-05 15:37:01', 'systemAuto', '2016-11-05 15:37:01', 'systemAuto', NULL, '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('22', 'AHR41214', 'view', 'RES10023', '租户管理', '0', NULL, '1', NULL, '2018-05-09 15:45:58', 'systemAuto', '2018-05-09 15:45:58', 'systemAuto', NULL, '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('3', 'AHR0003', '0', 'RES10003', '用户管理', '0', '', '1', '', '2016-11-05 15:45:47', 'systemAuto', '2016-11-05 15:45:47', 'systemAuto', '', '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('4', 'AHR0004', '0', 'RES10004', '角色管理', '0', NULL, '1', NULL, '2016-11-05 15:45:47', 'systemAuto', '2016-11-05 15:45:47', 'systemAuto', '', '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('5', 'AHR0005', '0', 'RES10005', '菜单管理', '0', NULL, '1', NULL, '2016-11-05 15:37:01', 'systemAuto', '2016-11-05 15:37:01', 'systemAuto', '', '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('55', 'SYSAHR001', 'click', 'SYSRES001', '系统权限', NULL, NULL, '0', NULL, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', NULL, '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('6', 'AHR0006', '0', 'RES10006', '机构管理', '0', NULL, '1', NULL, '2016-11-05 15:33:37', 'systemAuto', '2016-11-05 15:33:37', 'systemAuto', '', '0', 0, '', '10002');
INSERT INTO "sys_permission" VALUES ('727106481864474624', 'AHR727106481818337280', 'view', 'MENU727106481587650560', '编号管理', NULL, NULL, '1', NULL, '2020-06-29 10:21:34', 'USR170120000087', '2020-06-29 10:21:34', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('727204996775645184', 'AHR727204996737896448', 'click', 'MENU727204996565929984', '编号事件管理', NULL, NULL, '1', NULL, '2020-06-29 16:53:02', 'USR170120000087', '2020-06-29 16:53:02', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('728555967059054592', 'AHR728555967025500160', 'view', 'MENU728555966975168512', '权限管理', NULL, NULL, '1', NULL, '2020-07-03 10:21:18', 'USR170120000087', '2020-07-03 10:21:18', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('728556381091385344', 'AHR728556381032665088', 'click', 'MENU728556380969750528', '权限事件管理', NULL, NULL, '1', NULL, '2020-07-03 10:22:57', 'USR170120000087', '2020-07-03 10:22:57', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('729642449754009600', 'AHR729642449728843776', 'view', 'MENU729642449586237440', '数据权限模型管理', NULL, NULL, '1', NULL, '2020-07-06 10:18:36', 'USR170120000087', '2020-07-06 10:18:36', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('729729587669512192', 'AHR729729587652734976', 'click', 'MENU729729587614986240', '数据权限模型管理', NULL, NULL, '1', NULL, '2020-07-06 16:04:51', 'USR170120000087', '2020-07-06 16:04:51', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('729732602652667904', 'AHR729732602640084992', 'click', 'MENU729732602610724864', '新增修改', NULL, NULL, '1', NULL, '2020-07-06 16:16:50', 'USR170120000087', '2020-07-06 16:16:50', 'USR170120000087', NULL, '0', NULL, '', '10002');
INSERT INTO "sys_permission" VALUES ('9', 'AHR0009', '0', 'RES10009', '数据字典', '0', '', '1', '', '2016-11-05 15:33:37', 'systemAuto', '2016-11-05 15:33:37', 'systemAuto', '', '0', 0, '', '10002');

-- ----------------------------
-- Table structure for sys_res_data_auth_model
-- ----------------------------
DROP TABLE IF EXISTS "sys_res_data_auth_model";
CREATE TABLE "sys_res_data_auth_model" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "model_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "model_id" varchar(32) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "model_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "sys_res_data_auth_model"."id" IS '主键ID';
COMMENT ON COLUMN "sys_res_data_auth_model"."model_code" IS '权限类型编码';
COMMENT ON COLUMN "sys_res_data_auth_model"."model_id" IS '数据权限类型';
COMMENT ON COLUMN "sys_res_data_auth_model"."remark" IS '备注';
COMMENT ON COLUMN "sys_res_data_auth_model"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_res_data_auth_model"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_res_data_auth_model"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_res_data_auth_model"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_res_data_auth_model"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_res_data_auth_model"."del_ind" IS '删除标志:0-未删除；1-删除';
COMMENT ON COLUMN "sys_res_data_auth_model"."version" IS '版本号';
COMMENT ON COLUMN "sys_res_data_auth_model"."tenant_id" IS '租户id';
COMMENT ON COLUMN "sys_res_data_auth_model"."model_name" IS '数据权限类型名称';
COMMENT ON TABLE "sys_res_data_auth_model" IS '系统权限类型表';

-- ----------------------------
-- Records of sys_res_data_auth_model
-- ----------------------------

-- ----------------------------
-- Table structure for sys_res_event
-- ----------------------------
DROP TABLE IF EXISTS "sys_res_event";
CREATE TABLE "sys_res_event" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "event_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "event_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_res_event"."id" IS '主键ID';
COMMENT ON COLUMN "sys_res_event"."event_code" IS '事件编号';
COMMENT ON COLUMN "sys_res_event"."event_name" IS '事件名称';
COMMENT ON COLUMN "sys_res_event"."url" IS '菜单URL';
COMMENT ON COLUMN "sys_res_event"."clntend_id" IS '客户端编号';
COMMENT ON COLUMN "sys_res_event"."status_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "sys_res_event"."remark" IS '备注';
COMMENT ON COLUMN "sys_res_event"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_res_event"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_res_event"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_res_event"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_res_event"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_res_event"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_res_event"."version" IS '版本号';
COMMENT ON COLUMN "sys_res_event"."tenant_id" IS '租户编号';
COMMENT ON TABLE "sys_res_event" IS '系统事件资源信息表';

-- ----------------------------
-- Records of sys_res_event
-- ----------------------------
INSERT INTO "sys_res_event" VALUES ('1', 'SYSEVENT001', '获取菜单', '/msdp-system-service/menu', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('10', 'SYSEVENT010', '删除菜单', '/msdp-system-service/menu/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('11', 'SYSEVENT011', '保存/更新菜单', '/msdp-system-service/menu/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('12', 'SYSEVENT012', '菜单资源权限查询', '/msdp-system-service/menu/eventRel/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('13', 'SYSEVENT013', '菜单资源权限删除', '/msdp-system-service/menu/eventRel/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('14', 'SYSEVENT014', '菜单资源权限保存', '/msdp-system-service/menu/eventRel/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('15', 'SYSEVENT015', '菜单数据权限查询', '/msdp-system-service/menu/eventDataauth/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('16', 'SYSEVENT016', '资源查询', '/msdp-system-service/eventRes/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('17', 'SYSEVENT017', '资源详情', '/msdp-system-service/eventRes/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('18', 'SYSEVENT018', '删除资源', '/msdp-system-service/eventRes/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('19', 'SYSEVENT019', '保存资源', '/msdp-system-service/eventRes/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('2', 'SYSEVENT002', '查询用户', '/msdp-system-service/user/queryUser', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('20', 'SYSEVENT020', '机构树查询', '/msdp-system-service/org/orgTree', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('21', 'SYSEVENT021', '机构详情', '/msdp-system-service/org/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('22', 'SYSEVENT022', '删除机构', '/msdp-system-service/org/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('23', 'SYSEVENT023', '保存机构', '/msdp-system-service/org/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('24', 'SYSEVENT024', '机构选择器', '/msdp-system-service/org/selector', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('25', 'SYSEVENT025', '角色树查询', '/msdp-system-service/role/roleTree', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('26', 'SYSEVENT026', '角色详情', '/msdp-system-service/role/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('27', 'SYSEVENT027', '删除角色', '/msdp-system-service/role/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('28', 'SYSEVENT028', '保存角色', '/msdp-system-service/role/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('29', 'SYSEVENT029', '角色选择器', '/msdp-system-service/role/selector', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('3', 'SYSEVENT003', '校验密码', '/msdp-system-service/valiPass', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('30', 'SYSEVENT030', '角色菜单查询', '/msdp-system-service/role/roleMenu', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('31', 'SYSEVENT031', '角色菜单保存', '/msdp-system-service/role/saveRoleMuen', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('32', 'SYSEVENT032', '角色菜单分配', '/msdp-system-service/role/roleMenuAssigned', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('33', 'SYSEVENT033', '字典查询', '/msdp-system-service/dict/list', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('34', 'SYSEVENT034', '字典详情', '/msdp-system-service/dict/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('35', 'SYSEVENT035', '删除字典', '/msdp-system-service/dict/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('36', 'SYSEVENT036', '保存字典', '/msdp-system-service/dict/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('41', 'SYSEVENT041', '用户角色查询', '/msdp-system-service/role/roleUser', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('42', 'SYSEVENT042', '用户机构部门查询', '/msdp-system-service/admin/userOrgDept', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('43', 'SYSEVENT043', '用户切换角色', '/msdp-system-service/admin/changer', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('44', 'SYSEVENT044', '部门树查询', '/msdp-system-service/department/departmentTree', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('45', 'SYSEVENT045', '部门详情', '/msdp-system-service/department/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('46', 'SYSEVENT046', '部门删除', '/msdp-system-service/department/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('47', 'SYSEVENT047', '部门保存', '/msdp-system-service/department/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('48', 'SYSEVENT048', '部门选择器', '/msdp-system-service/department/selector', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('55', 'SYSEVENT055', '用户查询', '/msdp-system-service/usermg/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('56', 'SYSEVENT056', '用户保存', '/msdp-system-service/usermg/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('57', 'SYSEVENT057', '用户详情', '/msdp-system-service/usermg/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('58', 'SYSEVENT058', '用户删除', '/msdp-system-service/usermg/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('59', 'SYSEVENT059', '用户选择器', '/msdp-system-service/usermg/selector', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('60', 'SYSEVENT060', '用户修改密码', '/msdp-system-service/usermg/changePassword', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('61', 'SYSEVENT061', '用户重置密码', '/msdp-system-service/usermg/resetPassword', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('62', 'SYSEVENT062', '用户机构查询', '/msdp-system-service/usermg/userAndOrgRel/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('63', 'SYSEVENT063', '用户机构保存', '/msdp-system-service/usermg/userAndOrgRel/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('64', 'SYSEVENT064', '用户机构详情', '/msdp-system-service/usermg/userAndOrgRel/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('65', 'SYSEVENT065', '用户机构删除', '/msdp-system-service/usermg/userAndOrgRel/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('66', 'SYSEVENT066', '用户机构选择器', '/msdp-system-service/usermg/userAndOrgRel/selector', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('67', 'SYSEVENT067', '用户部门查询', '/msdp-system-service/usermg/userAndDeptRel/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('68', 'SYSEVENT068', '用户部门保存', '/msdp-system-service/usermg/userAndDeptRel/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('69', 'SYSEVENT069', '用户部门详情', '/msdp-system-service/usermg/userAndDeptRel/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('70', 'SYSEVENT070', '用户部门删除', '/msdp-system-service/usermg/userAndDeptRel/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('71', 'SYSEVENT071', '用户角色查询', '/msdp-system-service/usermg/userAndRoleRel/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('72', 'SYSEVENT072', '用户角色保存', '/msdp-system-service/usermg/userAndRoleRel/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('727205853978136576', 'SYSEVENT086', '编码生成类型分页查询', '/msdp-system-service/ecdgen/doFindEncodeTypeListPage', '10002', '1', NULL, '2020-06-29 16:56:26', 'USR170120000087', '2020-06-29 16:56:26', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727206136422567936', 'SYSEVENT087', '获取编码类型信息', '/msdp-system-service/ecdgen/doGetEncodeTp', '10002', '1', NULL, '2020-06-29 16:57:34', 'USR170120000087', '2020-06-29 16:57:34', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727207685198680064', 'SYSEVENT088', '编码明细分页查询', '/msdp-system-service/ecdgen/doFindEncodeDtlListPage', '10002', '1', NULL, '2020-06-29 17:03:43', 'USR170120000087', '2020-06-29 17:03:43', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727208297776779264', 'SYSEVENT089', '获取编码明细信息', '/msdp-system-service/ecdgen/doGetEncodeDtl', '10002', '1', NULL, '2020-06-29 17:06:09', 'USR170120000087', '2020-06-29 17:06:09', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727208530959110144', 'SYSEVENT090', '保存编码类型前，校验是否可保存', '/msdp-system-service/ecdgen/doCheckEncodeTp', '10002', '1', NULL, '2020-06-29 17:07:05', 'USR170120000087', '2020-06-29 17:07:05', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727208732579303424', 'SYSEVENT091', '保存或更新编码生成类型', '/msdp-system-service/ecdgen/doSaveEncodeTp', '10002', '1', NULL, '2020-06-29 17:07:53', 'USR170120000087', '2020-06-29 17:07:53', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727208898430472192', 'SYSEVENT092', '保存或更新编码明细', '/msdp-system-service/ecdgen/doSaveEncodeDtl', '10002', '1', NULL, '2020-06-29 17:08:32', 'USR170120000087', '2020-06-29 17:08:32', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727209206569209856', 'SYSEVENT093', '删除编码明细', '/msdp-system-service/ecdgen/doDeleteEncodeDtl', '10002', '1', NULL, '2020-06-29 17:09:46', 'USR170120000087', '2020-06-29 17:09:46', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727209342028451840', 'SYSEVENT094', '删除编码类型', '/msdp-system-service/ecdgen/doDeleteEncodeTp', '10002', '1', NULL, '2020-06-29 17:10:18', 'USR170120000087', '2020-06-29 17:10:18', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727209493895811072', 'SYSEVENT095', '生效生效编号类型', '/msdp-system-service/ecdgen/valid', '10002', '1', NULL, '2020-06-29 17:10:54', 'USR170120000087', '2020-06-29 17:10:54', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('727209745415639040', 'SYSEVENT096', '失效编号类型', '/msdp-system-service/ecdgen/invalid', '10002', '1', NULL, '2020-06-29 17:11:54', 'USR170120000087', '2020-06-29 17:11:54', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('728556977299116032', 'SYSEVENT097', '根据条件请求获取数据权限分页信息', '/msdp-system-service/dataauth/query', '10002', '1', NULL, '2020-07-03 10:25:19', 'USR170120000087', '2020-07-03 10:25:19', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('728557287967019008', 'SYSEVENT098', '根据编号ID获取参数详细信息', '/msdp-system-service/dataauth/get', '10002', '1', NULL, '2020-07-03 10:26:33', 'USR170120000087', '2020-07-03 10:26:33', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('728557415062818816', 'SYSEVENT099', '保存或修改数据权限实例', '/msdp-system-service/dataauth/save', '10002', '1', NULL, '2020-07-03 10:27:04', 'USR170120000087', '2020-07-03 10:27:04', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('728558130841767936', 'SYSEVENT100', '根据编号删除对象的数据', '/msdp-system-service/dataauth/delete', '10002', '1', NULL, '2020-07-03 10:29:54', 'USR170120000087', '2020-07-03 10:29:54', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('728558851746156544', 'SYSEVENT101', '分页获取数据权限类型', '/msdp-system-service/dataauth/modelSelector', '10002', '1', NULL, '2020-07-03 10:32:46', 'USR170120000087', '2020-07-03 10:32:46', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729706468573589504', 'SYSEVENT102', '分页查询数据权限模型', '/msdp-system-service/dataauth/queryAuthModel', '10002', '1', NULL, '2020-07-06 14:32:59', 'USR170120000087', '2020-07-06 14:32:59', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729708095477002240', 'SYSEVENT103', '根据编号ID获取参数详细信息', '/msdp-system-service/dataauth/getAuthModel', '10002', '1', NULL, '2020-07-06 14:39:27', 'USR170120000087', '2020-07-06 14:39:27', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729717859581833216', 'SYSEVENT104', '删除数据权限', '/msdp-system-service/dataauth/delAuthModel', '10002', '1', NULL, '2020-07-06 15:18:15', 'USR170120000087', '2020-07-06 15:18:15', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729721289209688064', 'SYSEVENT105', '保存或更新数据权限', '/msdp-system-service/dataauth/saveAuthModel', '10002', '1', NULL, '2020-07-06 15:31:53', 'USR170120000087', '2020-07-06 15:31:53', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729726591883685888', 'SYSEVENT106', '分页查询外键过滤信息', '/msdp-system-service/dataauth/queryModelFkcolumn', '10002', '1', NULL, '2020-07-06 15:52:57', 'USR170120000087', '2020-07-06 15:52:57', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729726909962924032', 'SYSEVENT107', '根据ID获取外键过滤信息', '/msdp-system-service/dataauth/getModelFkcolumn', '10002', '1', NULL, '2020-07-06 15:54:13', 'USR170120000087', '2020-07-06 15:54:13', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729727399459172352', 'SYSEVENT108', '新增或修改外键过滤信息', '/msdp-system-service/dataauth/saveModelFkcolumn', '10002', '1', NULL, '2020-07-06 15:56:10', 'USR170120000087', '2020-07-06 15:56:10', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('729727644377165824', 'SYSEVENT109', '删除外键过滤信息', '/msdp-system-service/dataauth/delModelFkcolumn', '10002', '1', NULL, '2020-07-06 15:57:08', 'USR170120000087', '2020-07-06 15:57:08', 'USR170120000087', NULL, '0', 0, NULL);
INSERT INTO "sys_res_event" VALUES ('73', 'SYSEVENT073', '用户角色详情', '/msdp-system-service/usermg/userAndRoleRel/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('74', 'SYSEVENT074', '用户角色删除', '/msdp-system-service/usermg/userAndRoleRel/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('77', 'SYSEVENT077', '权限查询', '/msdp-system-service/sys/indexMenu/permission', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('78', 'SYSEVENT078', '租户查询', '/msdp-system-service/tenant/query', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('79', 'SYSEVENT079', '租户详情', '/msdp-system-service/tenant/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('8', 'SYSEVENT008', '菜单树', '/msdp-system-service/menu/menuTree', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('80', 'SYSEVENT080', '租户保存', '/msdp-system-service/tenant/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('81', 'SYSEVENT081', '租户生效', '/msdp-system-service/tenant/effect', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('82', 'SYSEVENT082', '租户失效', '/msdp-system-service/tenant/invalid', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('83', 'SYSEVENT083', '租户删除', '/msdp-system-service/tenant/delete', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('84', 'SYSEVENT084', '租户菜单保存', '/msdp-system-service/tenant/roleMenu/save', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('85', 'SYSEVENT085', '租户菜单查询', '/msdp-system-service/tenant/roleMenu/menuTree', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_event" VALUES ('9', 'SYSEVENT009', '菜单详情', '/msdp-system-service/menu/get', '10002', '1', NULL, '2019-01-10 16:44:15', 'USR170120000087', '2019-01-10 16:44:15', 'USR170120000087', NULL, '0', 0, '');

-- ----------------------------
-- Table structure for sys_res_menu
-- ----------------------------
DROP TABLE IF EXISTS "sys_res_menu";
CREATE TABLE "sys_res_menu" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_menu_code" varchar(32) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "leaf_flag_cd" varchar(1) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "menu_type_cd" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_res_menu"."id" IS '主键ID';
COMMENT ON COLUMN "sys_res_menu"."menu_code" IS '菜单编号';
COMMENT ON COLUMN "sys_res_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "sys_res_menu"."parent_menu_code" IS '父菜单编号';
COMMENT ON COLUMN "sys_res_menu"."url" IS '菜单URL';
COMMENT ON COLUMN "sys_res_menu"."sort" IS '排序';
COMMENT ON COLUMN "sys_res_menu"."leaf_flag_cd" IS '叶子节点标识(非叶子节点0/叶子节点1)';
COMMENT ON COLUMN "sys_res_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "sys_res_menu"."menu_type_cd" IS '菜单类型(页面view/按钮click)';
COMMENT ON COLUMN "sys_res_menu"."clntend_id" IS '客户端编号';
COMMENT ON COLUMN "sys_res_menu"."status_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "sys_res_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_res_menu"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_res_menu"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_res_menu"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_res_menu"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_res_menu"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_res_menu"."version" IS '版本号';
COMMENT ON COLUMN "sys_res_menu"."tenant_id" IS '租户编号';
COMMENT ON TABLE "sys_res_menu" IS '系统菜单资源表';

-- ----------------------------
-- Records of sys_res_menu
-- ----------------------------
INSERT INTO "sys_res_menu" VALUES ('1', 'RES10001', '我的设置', '10002', '', 3, '0', 'iconmenu04', 'no-source', '10002', '1', '', '2016-11-05 16:08:48', 'systemAuto', '2018-05-08 11:24:53', 'systemAuto', '', '0', 9, '');
INSERT INTO "sys_res_menu" VALUES ('11', 'RES10012', '部门管理', 'RES10002', '/department/tree', 2, '1', '', 'view', '10002', '1', '部门管理', NULL, 'USR170120000087', '2020-06-12 16:17:55', 'USR170120000087', NULL, '0', 1, '');
INSERT INTO "sys_res_menu" VALUES ('130', 'MENU1592206017375', '新增或修改', 'RES10003', '/user/usermg/personAddMsg', NULL, '1', '', 'click', '10002', '1', '', NULL, 'USR170120000087', '2020-06-17 09:37:42', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('132', 'MENU1592209745256', '机构事件管理', 'RES10006', '', NULL, '1', '', 'click', '10002', '1', '', '2020-06-15 16:29:05', 'USR170120000087', '2020-06-15 16:29:05', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('133', 'MENU1592210170735', '部门事件管理', 'RES10012', '', NULL, '1', '', 'click', '10002', '1', '', '2020-06-15 16:36:11', 'USR170120000087', '2020-06-15 16:36:11', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('134', 'MENU1592210276320', '用户事件管理', 'RES10003', '', NULL, '1', '', 'click', '10002', '1', '', '2020-06-15 16:37:56', 'USR170120000087', '2020-06-15 16:37:56', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('135', 'MENU1592210396925', '角色事件管理', 'RES10004', '', NULL, '1', '', 'click', '10002', '1', '', NULL, 'USR170120000087', '2020-06-15 16:42:12', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('136', 'MENU1592210600121', '菜单事件管理', 'RES10005', '', NULL, '1', '', 'click', '10002', '1', '', '2020-06-15 16:43:20', 'USR170120000087', '2020-06-15 16:43:20', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('138', 'MENU1592210710398', '租户事件管理', 'RES10023', '', NULL, '1', '', 'click', '10002', '1', '', '2020-06-15 16:45:10', 'USR170120000087', '2020-06-15 16:45:10', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('14', 'RES10023', '租户管理', 'RES10002', '/tenant/tenantmg/tenantMgList', 16, '1', NULL, 'view', '10002', '1', '租户管理', '2018-05-09 15:45:57', 'USR170120000087', '2018-05-09 15:45:57', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('2', 'RES10002', '系统管理', 'RES10001', NULL, 1, '1', NULL, 'no-source', '10002', '1', NULL, '2017-09-19 16:15:18', 'ceshishuju', '2017-11-09 10:37:11', 'ceshishuju', NULL, '0', 5, '');
INSERT INTO "sys_res_menu" VALUES ('27', 'SYSRES001', '系统权限', 'RES10002', NULL, 0, '1', NULL, 'click', '10002', '1', NULL, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('3', 'RES10003', '用户管理', 'RES10002', '/user/usermg/personMgList', 4, '1', 'iconmenu09', 'view', '10002', '1', '', NULL, 'systemAuto', '2020-06-09 14:47:40', 'USR170120000087', '', '0', 7, '');
INSERT INTO "sys_res_menu" VALUES ('4', 'RES10004', '角色管理', 'RES10002', '/role/tree', 4, '1', 'iconlistmenu05', 'view', '10002', '1', '', '2016-11-05 16:08:48', 'systemAuto', '2018-03-08 10:18:23', 'systemAuto', '', '0', 6, '');
INSERT INTO "sys_res_menu" VALUES ('5', 'RES10005', '菜单管理', 'RES10002', '/menu/tree', 7, '1', NULL, 'view', '10002', '1', '', '2017-07-25 09:24:57', 'superadmin', '2018-02-11 16:50:58', 'superadmin', NULL, '0', 6, '');
INSERT INTO "sys_res_menu" VALUES ('6', 'RES10006', '机构管理', 'RES10002', '/org/tree', 1, '1', '', 'view', '10002', '1', '', NULL, 'test100', '2020-06-12 16:17:40', 'USR170120000087', NULL, '0', 8, '');
INSERT INTO "sys_res_menu" VALUES ('727106481772199936', 'MENU727106481587650560', '编号管理', 'RES10002', '/ecdgen/list', 13, '1', '', 'view', '10002', '1', '编号管理列表', NULL, 'USR170120000087', '2020-06-29 10:27:21', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('727204996704342016', 'MENU727204996565929984', '编号事件管理', 'MENU727106481587650560', '', NULL, '1', '', 'click', '10002', '1', '', '2020-06-29 16:53:02', 'USR170120000087', '2020-06-29 16:53:02', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('728555967008722944', 'MENU728555966975168512', '数据权限管理', 'RES10002', '/dataauth/query', 15, '1', '', 'view', '10002', '1', '', NULL, 'USR170120000087', '2020-07-09 13:44:37', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('728556381007499264', 'MENU728556380969750528', '权限事件管理', 'MENU728555966975168512', '', NULL, '1', '', 'click', '10002', '1', '', '2020-07-03 10:22:57', 'USR170120000087', '2020-07-03 10:22:57', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('729642449716260864', 'MENU729642449586237440', '数据权限模型管理', 'RES10002', '/dataAuthortyModelManage', 14, '1', '', 'view', '10002', '1', '数据权限模型管理', NULL, 'USR170120000087', '2020-07-07 11:12:44', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('729729587635957760', 'MENU729729587614986240', '数据权限模型管理', 'MENU729642449586237440', '', NULL, '1', '', 'click', '10002', '1', '', '2020-07-06 16:04:51', 'USR170120000087', '2020-07-06 16:04:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('729732602627502080', 'MENU729732602610724864', '新增修改', 'MENU729642449586237440', '/dataAuthortyModelManage/add', NULL, '1', '', 'click', '10002', '1', '', '2020-07-06 16:16:50', 'USR170120000087', '2020-07-06 16:16:50', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu" VALUES ('9', 'RES10009', '数据字典', 'RES10002', '/dict/list', 10, '1', '', 'view', '10002', '1', '', '2020-06-22 15:11:08', 'USR170120000087', '2020-06-03 15:14:56', 'USR170120000087', '', '0', 2, '');

-- ----------------------------
-- Table structure for sys_res_menu_event_rel
-- ----------------------------
DROP TABLE IF EXISTS "sys_res_menu_event_rel";
CREATE TABLE "sys_res_menu_event_rel" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "event_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_res_menu_event_rel"."id" IS '主键ID';
COMMENT ON COLUMN "sys_res_menu_event_rel"."menu_code" IS '菜单编号';
COMMENT ON COLUMN "sys_res_menu_event_rel"."event_code" IS '事件编号';
COMMENT ON COLUMN "sys_res_menu_event_rel"."status_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "sys_res_menu_event_rel"."remark" IS '备注';
COMMENT ON COLUMN "sys_res_menu_event_rel"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_res_menu_event_rel"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_res_menu_event_rel"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_res_menu_event_rel"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_res_menu_event_rel"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_res_menu_event_rel"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_res_menu_event_rel"."version" IS '版本号';
COMMENT ON COLUMN "sys_res_menu_event_rel"."tenant_id" IS '租户编号';
COMMENT ON TABLE "sys_res_menu_event_rel" IS '系统菜单事件关系表';

-- ----------------------------
-- Records of sys_res_menu_event_rel
-- ----------------------------
INSERT INTO "sys_res_menu_event_rel" VALUES ('140', 'MENU1592209745256', 'SYSEVENT020', '1', NULL, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('141', 'MENU1592209745256', 'SYSEVENT021', '1', NULL, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('142', 'MENU1592209745256', 'SYSEVENT022', '1', NULL, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('143', 'MENU1592209745256', 'SYSEVENT023', '1', NULL, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('144', 'MENU1592209745256', 'SYSEVENT024', '1', NULL, '2020-06-15 16:35:43', 'USR170120000087', '2020-06-15 16:35:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('150', 'MENU1592210170735', 'SYSEVENT044', '1', NULL, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('151', 'MENU1592210170735', 'SYSEVENT045', '1', NULL, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('152', 'MENU1592210170735', 'SYSEVENT046', '1', NULL, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('153', 'MENU1592210170735', 'SYSEVENT047', '1', NULL, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('154', 'MENU1592210170735', 'SYSEVENT048', '1', NULL, '2020-06-15 16:36:31', 'USR170120000087', '2020-06-15 16:36:31', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('155', 'MENU1592210276320', 'SYSEVENT002', '1', NULL, '2020-06-15 16:39:05', 'USR170120000087', '2020-06-15 16:39:05', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('156', 'MENU1592210276320', 'SYSEVENT041', '1', NULL, '2020-06-15 16:39:05', 'USR170120000087', '2020-06-15 16:39:05', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('159', 'MENU1592210276320', 'SYSEVENT055', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('160', 'MENU1592210276320', 'SYSEVENT056', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('161', 'MENU1592210276320', 'SYSEVENT057', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('162', 'MENU1592210276320', 'SYSEVENT058', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('163', 'MENU1592210276320', 'SYSEVENT059', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('165', 'MENU1592210276320', 'SYSEVENT061', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('166', 'MENU1592210276320', 'SYSEVENT062', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('167', 'MENU1592210276320', 'SYSEVENT063', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('168', 'MENU1592210276320', 'SYSEVENT064', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('169', 'MENU1592210276320', 'SYSEVENT065', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('170', 'MENU1592210276320', 'SYSEVENT066', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('171', 'MENU1592210276320', 'SYSEVENT067', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('172', 'MENU1592210276320', 'SYSEVENT068', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('173', 'MENU1592210276320', 'SYSEVENT069', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('174', 'MENU1592210276320', 'SYSEVENT070', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('175', 'MENU1592210276320', 'SYSEVENT071', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('176', 'MENU1592210276320', 'SYSEVENT072', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('177', 'MENU1592210276320', 'SYSEVENT073', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('178', 'MENU1592210276320', 'SYSEVENT074', '1', NULL, '2020-06-15 16:39:06', 'USR170120000087', '2020-06-15 16:39:06', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('179', 'MENU1592210396925', 'SYSEVENT025', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('180', 'MENU1592210396925', 'SYSEVENT026', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('181', 'MENU1592210396925', 'SYSEVENT027', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('182', 'MENU1592210396925', 'SYSEVENT028', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('183', 'MENU1592210396925', 'SYSEVENT029', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('184', 'MENU1592210396925', 'SYSEVENT030', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('185', 'MENU1592210396925', 'SYSEVENT031', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('186', 'MENU1592210396925', 'SYSEVENT032', '1', NULL, '2020-06-15 16:42:51', 'USR170120000087', '2020-06-15 16:42:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('187', 'MENU1592210600121', 'SYSEVENT001', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('188', 'MENU1592210600121', 'SYSEVENT008', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('189', 'MENU1592210600121', 'SYSEVENT009', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('190', 'MENU1592210600121', 'SYSEVENT010', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('191', 'MENU1592210600121', 'SYSEVENT011', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('192', 'MENU1592210600121', 'SYSEVENT012', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('193', 'MENU1592210600121', 'SYSEVENT013', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('194', 'MENU1592210600121', 'SYSEVENT014', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('195', 'MENU1592210600121', 'SYSEVENT015', '1', NULL, '2020-06-15 16:43:55', 'USR170120000087', '2020-06-15 16:43:55', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('196', 'MENU1592210660764', 'SYSEVENT033', '1', NULL, '2020-06-15 16:44:43', 'USR170120000087', '2020-06-15 16:44:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('197', 'MENU1592210660764', 'SYSEVENT034', '1', NULL, '2020-06-15 16:44:43', 'USR170120000087', '2020-06-15 16:44:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('198', 'MENU1592210660764', 'SYSEVENT035', '1', NULL, '2020-06-15 16:44:43', 'USR170120000087', '2020-06-15 16:44:43', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('199', 'MENU1592210660764', 'SYSEVENT036', '1', NULL, '2020-06-15 16:44:44', 'USR170120000087', '2020-06-15 16:44:44', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('200', 'MENU1592210710398', 'SYSEVENT078', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('201', 'MENU1592210710398', 'SYSEVENT079', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('202', 'MENU1592210710398', 'SYSEVENT080', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('203', 'MENU1592210710398', 'SYSEVENT081', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('204', 'MENU1592210710398', 'SYSEVENT082', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('205', 'MENU1592210710398', 'SYSEVENT083', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('206', 'MENU1592210710398', 'SYSEVENT084', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('207', 'MENU1592210710398', 'SYSEVENT085', '1', NULL, '2020-06-15 16:45:29', 'USR170120000087', '2020-06-15 16:45:29', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('208', 'MENU1592210600121', 'SYSEVENT016', '1', NULL, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('209', 'MENU1592210600121', 'SYSEVENT017', '1', NULL, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('210', 'MENU1592210600121', 'SYSEVENT018', '1', NULL, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('211', 'MENU1592210600121', 'SYSEVENT019', '1', NULL, '2020-06-15 16:47:21', 'USR170120000087', '2020-06-15 16:47:21', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('212', 'MENU1592210600121', 'SYSEVENT077', '1', NULL, '2020-06-15 16:57:17', 'USR170120000087', '2020-06-15 16:57:17', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('254', 'SYSRES001', 'SYSEVENT060', '1', NULL, '2020-06-17 17:03:42', 'USR170120000087', '2020-06-17 17:03:42', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('3', 'SYSRES001', 'SYSEVENT003', '1', NULL, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('42', 'SYSRES001', 'SYSEVENT042', '1', '', '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', '', '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('43', 'SYSRES001', 'SYSEVENT043', '1', NULL, '2019-01-10 16:44:16', 'USR170120000087', '2019-01-10 16:44:16', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798704271360', 'MENU727204996565929984', 'SYSEVENT086', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798737825792', 'MENU727204996565929984', 'SYSEVENT087', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798771380224', 'MENU727204996565929984', 'SYSEVENT088', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798800740352', 'MENU727204996565929984', 'SYSEVENT089', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798825906176', 'MENU727204996565929984', 'SYSEVENT090', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798846877696', 'MENU727204996565929984', 'SYSEVENT091', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798872043520', 'MENU727204996565929984', 'SYSEVENT092', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798893015040', 'MENU727204996565929984', 'SYSEVENT093', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798913986560', 'MENU727204996565929984', 'SYSEVENT094', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798934958080', 'MENU727204996565929984', 'SYSEVENT095', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('727209798960123904', 'MENU727204996565929984', 'SYSEVENT096', '1', NULL, '2020-06-29 17:12:07', 'USR170120000087', '2020-06-29 17:12:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('728558182045831168', 'MENU728556380969750528', 'SYSEVENT097', '1', NULL, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('728558182079385600', 'MENU728556380969750528', 'SYSEVENT098', '1', NULL, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('728558182108745728', 'MENU728556380969750528', 'SYSEVENT099', '1', NULL, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('728558182142300160', 'MENU728556380969750528', 'SYSEVENT100', '1', NULL, '2020-07-03 10:30:07', 'USR170120000087', '2020-07-03 10:30:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('728558880942706688', 'MENU728556380969750528', 'SYSEVENT101', '1', NULL, '2020-07-03 10:32:53', 'USR170120000087', '2020-07-03 10:32:53', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729706493319983104', 'MENU729705775867506688', 'SYSEVENT102', '1', NULL, '2020-07-06 14:33:05', 'USR170120000087', '2020-07-06 14:33:05', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729708114473005056', 'MENU729705775867506688', 'SYSEVENT103', '1', NULL, '2020-07-06 14:39:32', 'USR170120000087', '2020-07-06 14:39:32', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729717912648167424', 'MENU729705775867506688', 'SYSEVENT104', '1', NULL, '2020-07-06 15:18:28', 'USR170120000087', '2020-07-06 15:18:28', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729721864429121536', 'MENU729705775867506688', 'SYSEVENT105', '1', NULL, '2020-07-06 15:34:10', 'USR170120000087', '2020-07-06 15:34:10', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729726634124521472', 'MENU729705775867506688', 'SYSEVENT106', '1', NULL, '2020-07-06 15:53:07', 'USR170120000087', '2020-07-06 15:53:07', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729727067530342400', 'MENU729705775867506688', 'SYSEVENT107', '1', NULL, '2020-07-06 15:54:51', 'USR170120000087', '2020-07-06 15:54:51', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729727424591441920', 'MENU729705775867506688', 'SYSEVENT108', '1', NULL, '2020-07-06 15:56:16', 'USR170120000087', '2020-07-06 15:56:16', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729727678946619392', 'MENU729705775867506688', 'SYSEVENT109', '1', NULL, '2020-07-06 15:57:16', 'USR170120000087', '2020-07-06 15:57:16', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729732055837061120', 'MENU729729587614986240', 'SYSEVENT102', '1', NULL, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729732055862226944', 'MENU729729587614986240', 'SYSEVENT103', '1', NULL, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729732055883198464', 'MENU729729587614986240', 'SYSEVENT104', '1', NULL, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729732055899975680', 'MENU729729587614986240', 'SYSEVENT105', '1', NULL, '2020-07-06 16:14:40', 'USR170120000087', '2020-07-06 16:14:40', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729990539656704000', 'MENU729732602610724864', 'SYSEVENT106', '1', NULL, '2020-07-07 09:21:47', 'USR170120000087', '2020-07-07 09:21:47', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729990579976548352', 'MENU729732602610724864', 'SYSEVENT107', '1', NULL, '2020-07-07 09:21:57', 'USR170120000087', '2020-07-07 09:21:57', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729990627384766464', 'MENU729732602610724864', 'SYSEVENT108', '1', NULL, '2020-07-07 09:22:08', 'USR170120000087', '2020-07-07 09:22:08', 'USR170120000087', NULL, '0', 0, '');
INSERT INTO "sys_res_menu_event_rel" VALUES ('729990627397349376', 'MENU729732602610724864', 'SYSEVENT109', '1', NULL, '2020-07-07 09:22:08', 'USR170120000087', '2020-07-07 09:22:08', 'USR170120000087', NULL, '0', 0, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "sys_role";
CREATE TABLE "sys_role" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "role_code" varchar(32) COLLATE "pg_catalog"."default",
  "role_name" varchar(100) COLLATE "pg_catalog"."default",
  "parent_role_code" varchar(32) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default",
  "sort" int4
)
;
COMMENT ON COLUMN "sys_role"."id" IS '主键ID';
COMMENT ON COLUMN "sys_role"."role_code" IS '角色编号';
COMMENT ON COLUMN "sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "sys_role"."parent_role_code" IS '父级节点编号';
COMMENT ON COLUMN "sys_role"."status_cd" IS '状态：无效0有效1';
COMMENT ON COLUMN "sys_role"."remark" IS '备注';
COMMENT ON COLUMN "sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_role"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_role"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_role"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_role"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_role"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_role"."version" IS '版本号';
COMMENT ON COLUMN "sys_role"."tenant_id" IS '租户id';
COMMENT ON COLUMN "sys_role"."clntend_id" IS '客户端ID';
COMMENT ON COLUMN "sys_role"."sort" IS '序号';
COMMENT ON TABLE "sys_role" IS '系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "sys_role" VALUES ('1', 'rl0', '平台管理员', NULL, '1', 'superadmin', '2017-01-11 10:47:06', 'systemAuto', '2020-07-09 07:03:08', 'USR170120000087', NULL, '0', 24, '', '10002', 1);

-- ----------------------------
-- Table structure for sys_role_permission_rel
-- ----------------------------
DROP TABLE IF EXISTS "sys_role_permission_rel";
CREATE TABLE "sys_role_permission_rel" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "permission_code" varchar(32) COLLATE "pg_catalog"."default",
  "role_code" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4 NOT NULL,
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_role_permission_rel"."id" IS '主键ID';
COMMENT ON COLUMN "sys_role_permission_rel"."permission_code" IS '权限编码';
COMMENT ON COLUMN "sys_role_permission_rel"."role_code" IS '角色编码';
COMMENT ON COLUMN "sys_role_permission_rel"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_role_permission_rel"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_role_permission_rel"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_role_permission_rel"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_role_permission_rel"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_role_permission_rel"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_role_permission_rel"."version" IS '版本号';
COMMENT ON COLUMN "sys_role_permission_rel"."status_cd" IS '状态STATUS:无效0/有效1';
COMMENT ON COLUMN "sys_role_permission_rel"."remark" IS ' 备注';
COMMENT ON COLUMN "sys_role_permission_rel"."tenant_id" IS '租户id';
COMMENT ON TABLE "sys_role_permission_rel" IS '系统角色与权限关系表';

-- ----------------------------
-- Records of sys_role_permission_rel
-- ----------------------------
INSERT INTO "sys_role_permission_rel" VALUES ('738443977891057664', 'AHR0009', 'rl0', '2020-07-30 17:12:43.954', 'USR170120000087', '2020-07-30 17:12:43.954', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443977912029184', 'AHR733692301735002112', 'rl0', '2020-07-30 17:12:43.959', 'USR170120000087', '2020-07-30 17:12:43.959', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443977949777920', 'AHR731091419746082816', 'rl0', '2020-07-30 17:12:43.968', 'USR170120000087', '2020-07-30 17:12:43.968', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443977970749440', 'AHR731091215772884992', 'rl0', '2020-07-30 17:12:43.973', 'USR170120000087', '2020-07-30 17:12:43.973', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443977991720960', 'AHR730797018180296704', 'rl0', '2020-07-30 17:12:43.978', 'USR170120000087', '2020-07-30 17:12:43.978', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978042052608', 'AHR729732602640084992', 'rl0', '2020-07-30 17:12:43.99', 'USR170120000087', '2020-07-30 17:12:43.99', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978058829824', 'AHR729729587652734976', 'rl0', '2020-07-30 17:12:43.994', 'USR170120000087', '2020-07-30 17:12:43.994', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978079801344', 'AHR729642449728843776', 'rl0', '2020-07-30 17:12:43.999', 'USR170120000087', '2020-07-30 17:12:43.999', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978104967168', 'AHR728556381032665088', 'rl0', '2020-07-30 17:12:44.005', 'USR170120000087', '2020-07-30 17:12:44.005', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978125938688', 'AHR728555967025500160', 'rl0', '2020-07-30 17:12:44.01', 'USR170120000087', '2020-07-30 17:12:44.01', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978146910208', 'AHR727204996737896448', 'rl0', '2020-07-30 17:12:44.014', 'USR170120000087', '2020-07-30 17:12:44.014', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978167881728', 'AHR727106481818337280', 'rl0', '2020-07-30 17:12:44.02', 'USR170120000087', '2020-07-30 17:12:44.02', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978188853248', 'AHR726758469198745600', 'rl0', '2020-07-30 17:12:44.025', 'USR170120000087', '2020-07-30 17:12:44.025', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978201436160', 'AHR726758247454281728', 'rl0', '2020-07-30 17:12:44.028', 'USR170120000087', '2020-07-30 17:12:44.028', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978222407680', 'AHR0006', 'rl0', '2020-07-30 17:12:44.033', 'USR170120000087', '2020-07-30 17:12:44.033', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978276933632', 'SYSAHR001', 'rl0', '2020-07-30 17:12:44.046', 'USR170120000087', '2020-07-30 17:12:44.046', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978310488064', 'AHR0005', 'rl0', '2020-07-30 17:12:44.054', 'USR170120000087', '2020-07-30 17:12:44.054', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978327265280', 'AHR0004', 'rl0', '2020-07-30 17:12:44.058', 'USR170120000087', '2020-07-30 17:12:44.058', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978348236800', 'MENU1591336356244', 'rl0', '2020-07-30 17:12:44.063', 'USR170120000087', '2020-07-30 17:12:44.063', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978369208320', 'MENU1591336169316', 'rl0', '2020-07-30 17:12:44.068', 'USR170120000087', '2020-07-30 17:12:44.068', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978385985536', 'AHR0003', 'rl0', '2020-07-30 17:12:44.072', 'USR170120000087', '2020-07-30 17:12:44.072', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978402762752', 'AHR0002', 'rl0', '2020-07-30 17:12:44.076', 'USR170120000087', '2020-07-30 17:12:44.076', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978419539968', 'AHR1592210600149', 'rl0', '2020-07-30 17:12:44.08', 'USR170120000087', '2020-07-30 17:12:44.08', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978440511488', 'AHR1592210396958', 'rl0', '2020-07-30 17:12:44.085', 'USR170120000087', '2020-07-30 17:12:44.085', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978457288704', 'AHR1592210276363', 'rl0', '2020-07-30 17:12:44.089', 'USR170120000087', '2020-07-30 17:12:44.089', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978474065920', 'AHR1592210170770', 'rl0', '2020-07-30 17:12:44.093', 'USR170120000087', '2020-07-30 17:12:44.093', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978490843136', 'AHR1592209745281', 'rl0', '2020-07-30 17:12:44.097', 'USR170120000087', '2020-07-30 17:12:44.097', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978516008960', 'AHR1592206017431', 'rl0', '2020-07-30 17:12:44.103', 'USR170120000087', '2020-07-30 17:12:44.103', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978536980480', 'AHR41211', 'rl0', '2020-07-30 17:12:44.108', 'USR170120000087', '2020-07-30 17:12:44.108', 'USR170120000087', NULL, '0', 0, '1', NULL, '');
INSERT INTO "sys_role_permission_rel" VALUES ('738443978553757696', 'AHR0001', 'rl0', '2020-07-30 17:12:44.112', 'USR170120000087', '2020-07-30 17:12:44.112', 'USR170120000087', NULL, '0', 0, '1', NULL, '');

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS "sys_tenant";
CREATE TABLE "sys_tenant" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_num" varchar(32) COLLATE "pg_catalog"."default",
  "tenant_name" varchar(32) COLLATE "pg_catalog"."default",
  "manager_name" varchar(50) COLLATE "pg_catalog"."default",
  "login_name" varchar(32) COLLATE "pg_catalog"."default",
  "password" varchar(50) COLLATE "pg_catalog"."default",
  "salt" varchar(50) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "org_code" varchar(32) COLLATE "pg_catalog"."default",
  "telephone" varchar(12) COLLATE "pg_catalog"."default",
  "mobile" varchar(12) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "version" int4,
  "user_num" varchar(32) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_tenant"."id" IS '主键';
COMMENT ON COLUMN "sys_tenant"."tenant_num" IS '租户编号';
COMMENT ON COLUMN "sys_tenant"."tenant_name" IS '租户名称';
COMMENT ON COLUMN "sys_tenant"."manager_name" IS '管理员名称';
COMMENT ON COLUMN "sys_tenant"."login_name" IS '登录名';
COMMENT ON COLUMN "sys_tenant"."password" IS '密码';
COMMENT ON COLUMN "sys_tenant"."salt" IS '盐';
COMMENT ON COLUMN "sys_tenant"."status_cd" IS '状态STATUS:无效0/有效1';
COMMENT ON COLUMN "sys_tenant"."org_code" IS '统一社会信用证代码';
COMMENT ON COLUMN "sys_tenant"."telephone" IS '????';
COMMENT ON COLUMN "sys_tenant"."mobile" IS '???';
COMMENT ON COLUMN "sys_tenant"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_tenant"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_tenant"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_tenant"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_tenant"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_tenant"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_tenant"."version" IS '版本号';
COMMENT ON TABLE "sys_tenant" IS '系统租户信息表';

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO "sys_tenant" VALUES ('1', NULL, '默认租户', 'superadmin', 'superadmin', 'b401f11862ae17d6397a7202cf985dc3', 'ec198545761841649208913eb95b10e0', '1', '91371581MA3C9XCT6J', NULL, '18959922908', '2020-06-17 09:56:14', 'USR170120000087', '2020-06-18 01:51:43', 'USR170120000087', NULL, '0', 0, 'USR170120000087', '');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "sys_user";
CREATE TABLE "sys_user" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_num" varchar(32) COLLATE "pg_catalog"."default",
  "realname" varchar(50) COLLATE "pg_catalog"."default",
  "login_name" varchar(32) COLLATE "pg_catalog"."default",
  "password" varchar(128) COLLATE "pg_catalog"."default",
  "salt" varchar(50) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "sex_cd" varchar(1) COLLATE "pg_catalog"."default",
  "certificate_type_cd" varchar(1) COLLATE "pg_catalog"."default",
  "certificate_num" varchar(32) COLLATE "pg_catalog"."default",
  "qq" varchar(32) COLLATE "pg_catalog"."default",
  "wechat" varchar(32) COLLATE "pg_catalog"."default",
  "telephone" varchar(11) COLLATE "pg_catalog"."default",
  "mobile" varchar(11) COLLATE "pg_catalog"."default",
  "email" varchar(50) COLLATE "pg_catalog"."default",
  "fax" varchar(11) COLLATE "pg_catalog"."default",
  "pass_error_count" int4,
  "login_succ_count" int4,
  "lock_time" date,
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "province" varchar(32) COLLATE "pg_catalog"."default",
  "city" varchar(32) COLLATE "pg_catalog"."default",
  "district" varchar(32) COLLATE "pg_catalog"."default",
  "address" varchar(100) COLLATE "pg_catalog"."default",
  "clntend_id" varchar(32) COLLATE "pg_catalog"."default",
  "clntend_nm" varchar(100) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "last_login_time" timestamp(6),
  "create_inst" varchar(32) COLLATE "pg_catalog"."default",
  "update_inst" varchar(32) COLLATE "pg_catalog"."default",
  "hdl_user" varchar(32) COLLATE "pg_catalog"."default",
  "hdl_inst" varchar(32) COLLATE "pg_catalog"."default",
  "hdl_time" timestamp(6),
  "grayscale_version" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_user"."id" IS '主键';
COMMENT ON COLUMN "sys_user"."user_num" IS '用户编号';
COMMENT ON COLUMN "sys_user"."realname" IS '用户名';
COMMENT ON COLUMN "sys_user"."login_name" IS '用户登录名';
COMMENT ON COLUMN "sys_user"."password" IS '密码';
COMMENT ON COLUMN "sys_user"."salt" IS '盐';
COMMENT ON COLUMN "sys_user"."status_cd" IS '状态STATUS:无效0/有效1';
COMMENT ON COLUMN "sys_user"."sex_cd" IS '性别(SEX:男1\女2)';
COMMENT ON COLUMN "sys_user"."certificate_type_cd" IS '证件类型(CERTIFICATE_TYPE:身份证0\学生证1\工作证2\士兵证3\军官证4\护照5)';
COMMENT ON COLUMN "sys_user"."certificate_num" IS '证件号码';
COMMENT ON COLUMN "sys_user"."qq" IS 'QQ';
COMMENT ON COLUMN "sys_user"."wechat" IS '微信';
COMMENT ON COLUMN "sys_user"."telephone" IS '固定电话';
COMMENT ON COLUMN "sys_user"."mobile" IS '手机号';
COMMENT ON COLUMN "sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "sys_user"."fax" IS '传真';
COMMENT ON COLUMN "sys_user"."pass_error_count" IS '密码错误次数';
COMMENT ON COLUMN "sys_user"."login_succ_count" IS '登录成功次数';
COMMENT ON COLUMN "sys_user"."lock_time" IS '锁定时间';
COMMENT ON COLUMN "sys_user"."remark" IS '备注';
COMMENT ON COLUMN "sys_user"."province" IS '省级';
COMMENT ON COLUMN "sys_user"."city" IS '城市级';
COMMENT ON COLUMN "sys_user"."district" IS '区域级';
COMMENT ON COLUMN "sys_user"."address" IS '地址';
COMMENT ON COLUMN "sys_user"."clntend_id" IS '客户端编号';
COMMENT ON COLUMN "sys_user"."clntend_nm" IS '客户端名称';
COMMENT ON COLUMN "sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_user"."create_user" IS '创建人';
COMMENT ON COLUMN "sys_user"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_user"."update_user" IS '更新人';
COMMENT ON COLUMN "sys_user"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_user"."del_ind" IS '删除标志';
COMMENT ON COLUMN "sys_user"."version" IS '版本号';
COMMENT ON COLUMN "sys_user"."tenant_id" IS '租户编号';
COMMENT ON COLUMN "sys_user"."last_login_time" IS '上次登录时间';
COMMENT ON COLUMN "sys_user"."grayscale_version" IS '灰度版本号';
COMMENT ON TABLE "sys_user" IS '系统用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "sys_user" VALUES ('43', 'USR170120000087', 'superadmin', 'superadmin', 'b401f11862ae17d6397a7202cf985dc3', 'ec198545761841649208913eb95b10e0', '1', '1', NULL, NULL, NULL, NULL, '13215981234', '18959922908', '332431195@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '10002', NULL, '2017-01-20 15:37:10', 'USR170120000087', '2020-07-23 01:28:48', 'USR170120000087', NULL, '0', 0, '', '2020-01-13 09:45:58', 'systemAuto', 'systemAuto', 'systemAuto', 'systemAuto', '2019-10-09 17:44:43', NULL);

-- ----------------------------
-- Table structure for sys_user_dept_rel
-- ----------------------------
DROP TABLE IF EXISTS "sys_user_dept_rel";
CREATE TABLE "sys_user_dept_rel" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_num" varchar(32) COLLATE "pg_catalog"."default",
  "dept_code" varchar(32) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "org_code" varchar(32) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "version" int4,
  "expd_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_user_dept_rel"."user_num" IS '用户编号';
COMMENT ON COLUMN "sys_user_dept_rel"."dept_code" IS '部门代码';
COMMENT ON COLUMN "sys_user_dept_rel"."status_cd" IS '状态';
COMMENT ON COLUMN "sys_user_dept_rel"."remark" IS '备注';
COMMENT ON COLUMN "sys_user_dept_rel"."del_ind" IS '删除标识';
COMMENT ON COLUMN "sys_user_dept_rel"."create_user" IS '创建者';
COMMENT ON COLUMN "sys_user_dept_rel"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_user_dept_rel"."update_user" IS '更新者';
COMMENT ON COLUMN "sys_user_dept_rel"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_user_dept_rel"."org_code" IS '机构代码';
COMMENT ON COLUMN "sys_user_dept_rel"."tenant_id" IS '租户编号';
COMMENT ON COLUMN "sys_user_dept_rel"."version" IS '版本号';
COMMENT ON COLUMN "sys_user_dept_rel"."expd_id" IS '扩展编号';
COMMENT ON TABLE "sys_user_dept_rel" IS '系统用户与部门关系表';

-- ----------------------------
-- Records of sys_user_dept_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_org_rel
-- ----------------------------
DROP TABLE IF EXISTS "sys_user_org_rel";
CREATE TABLE "sys_user_org_rel" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_num" varchar(32) COLLATE "pg_catalog"."default",
  "org_code" varchar(32) COLLATE "pg_catalog"."default",
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "version" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "default_ind" varchar(1) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_user_org_rel"."id" IS 'ID编号';
COMMENT ON COLUMN "sys_user_org_rel"."user_num" IS '用户编码';
COMMENT ON COLUMN "sys_user_org_rel"."org_code" IS '机构代码';
COMMENT ON COLUMN "sys_user_org_rel"."status_cd" IS '状态(STATUS:无效0\有效1)';
COMMENT ON COLUMN "sys_user_org_rel"."remark" IS '备注';
COMMENT ON COLUMN "sys_user_org_rel"."del_ind" IS '删除标识';
COMMENT ON COLUMN "sys_user_org_rel"."create_user" IS '创建者';
COMMENT ON COLUMN "sys_user_org_rel"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_user_org_rel"."update_user" IS '更新者';
COMMENT ON COLUMN "sys_user_org_rel"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_user_org_rel"."version" IS '版本号';
COMMENT ON COLUMN "sys_user_org_rel"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_user_org_rel"."tenant_id" IS '租户编码';
COMMENT ON COLUMN "sys_user_org_rel"."default_ind" IS '默认值';
COMMENT ON TABLE "sys_user_org_rel" IS '系统用户与机构关系表';

-- ----------------------------
-- Records of sys_user_org_rel
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS "sys_user_role_rel";
CREATE TABLE "sys_user_role_rel" (
  "id" char(32) COLLATE "pg_catalog"."default" NOT NULL,
  "user_num" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "role_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status_cd" varchar(1) COLLATE "pg_catalog"."default",
  "remark" varchar(100) COLLATE "pg_catalog"."default",
  "del_ind" varchar(1) COLLATE "pg_catalog"."default",
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "version" varchar(32) COLLATE "pg_catalog"."default",
  "expd_id" varchar(32) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default",
  "default_ind" varchar(1) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_user_role_rel"."user_num" IS '用户编码';
COMMENT ON COLUMN "sys_user_role_rel"."role_code" IS '角色编码';
COMMENT ON COLUMN "sys_user_role_rel"."status_cd" IS '状态';
COMMENT ON COLUMN "sys_user_role_rel"."remark" IS '备注';
COMMENT ON COLUMN "sys_user_role_rel"."create_user" IS '创建者';
COMMENT ON COLUMN "sys_user_role_rel"."create_time" IS '创建时间';
COMMENT ON COLUMN "sys_user_role_rel"."update_user" IS '更新者';
COMMENT ON COLUMN "sys_user_role_rel"."update_time" IS '更新时间';
COMMENT ON COLUMN "sys_user_role_rel"."version" IS '版本号';
COMMENT ON COLUMN "sys_user_role_rel"."expd_id" IS '扩展编号';
COMMENT ON COLUMN "sys_user_role_rel"."tenant_id" IS '租户编号';
COMMENT ON COLUMN "sys_user_role_rel"."default_ind" IS '默认值';
COMMENT ON TABLE "sys_user_role_rel" IS '系统用户与角色关系表';

-- ----------------------------
-- Records of sys_user_role_rel
-- ----------------------------
INSERT INTO "sys_user_role_rel" VALUES ('25', 'USR170120000087', 'rl0', '1', NULL, '0', 'USR170120000087', '2015-11-28 10:20:19', 'USR170120000087', '2020-06-12 10:46:11', '23', NULL, '', '1');

-- ----------------------------
-- Table structure for t_dev_initializr
-- ----------------------------
DROP TABLE IF EXISTS "t_dev_initializr";
CREATE TABLE "t_dev_initializr" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "yml_content" varchar(2000) COLLATE "pg_catalog"."default",
  "dependencies_content" varchar(2000) COLLATE "pg_catalog"."default",
  "del_flag" varchar(1) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_dev_initializr"."id" IS '主键';
COMMENT ON COLUMN "t_dev_initializr"."tenant_id" IS '租户编号';

-- ----------------------------
-- Records of t_dev_initializr
-- ----------------------------
INSERT INTO "t_dev_initializr" VALUES ('1', 'msdp-sysmgr', '系统管理', '', '<dependency>
		    <groupId>com.jeedev.msdp.common</groupId>
		    <artifactId>msdp-sysmgr</artifactId>

		</dependency>', '0', '');
INSERT INTO "t_dev_initializr" VALUES ('2', 'msdp-iweb', 'web组件', '', '<dependency>
	    <groupId>com.jeedev.msdp.common</groupId>
	    <artifactId>msdp-iweb</artifactId>
	</dependency>', '0', '');
INSERT INTO "t_dev_initializr" VALUES ('3', 'diskeygen', '雪花主键生成', '', '<dependency>
				<groupId>com.tansun.tjmas</groupId>
				<artifactId>tjmas-cloud-starter-base-diskeygen</artifactId>
			</dependency>', '0', '');
INSERT INTO "t_dev_initializr" VALUES ('4', 'msdp-iencode', '编号生成器', '', '<dependency>
			    <groupId>com.jeedev.msdp.common</groupId>
			    <artifactId>msdp-iencode</artifactId>
			</dependency>', '0', '');
INSERT INTO "t_dev_initializr" VALUES ('5', 'msdp-swagger2', 'swagger2在线接口', '', '<dependency>
		<groupId>com.jeedev.msdp.common</groupId>
	  	<artifactId>msdp-swagger2</artifactId>
	</dependency>', '0', '');
INSERT INTO "t_dev_initializr" VALUES ('6', 'msdp-iaddon', '可选插件', '', '<dependency>
	    <groupId>com.jeedev.msdp.common</groupId>
	    <artifactId>msdp-iaddon</artifactId>
	  </dependency>', '0', '');
INSERT INTO "t_dev_initializr" VALUES ('7', 'tjmas-cloud-starter-base-seata', '分布式事务', 'seata:  # 依赖全新天魔斯seata1.3组件客户端配置,支持最新的官方seata1.3
  enabled: true   # 本地事务和分布式事务的切换开关，默认为true,微服务分布式事务；当切换为单体的时候，则设置成false，则为本地事务
  application-id: seata-disms-demo-a
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true    #开启本地自动数据源代理，默认是自动开启
  use-jdk-proxy: false      #默认为false，采用CGLIB作为数据源自动代理的实现方式。
#  excludes-for-auto-proxying: firstClassNameForExclude,secondClassNameForExclude
  config:
    #  #    #    type: file       #配置中心为本地文件
    type: nacos
    nacos:
      namespace: "5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca"  #nacos命名空间ID
      serverAddr: ${spring.cloud.nacos.config.server-addr}   #配置中心IP和端口 测试环境 ::8848
      group: SEATA_GROUP  #nacos命名组
      username: ""        #nacos登陆用户名
      password: ""        #nacos登陆密码
  registry:
    #    type: file    #注册中心类型是file,可以本地项目调试验证
    #    type: eureka
    #    eureka:    #注册中心类型支持eureka
    #      weight: 1     #配置注册中心权重
    #      application: default   #应用名 默认
    #      service-url: http://localhost:8761/eureka   #配置注册中心IP和端口
    type: nacos
    nacos:        #注册中心类型是nacos
      application: seata-server
      server-addr: ${spring.cloud.nacos.config.server-addr}   #注册中心IP和端口 测试环境 ::8848
      group: "SEATA_GROUP"       #nacos命名组
      namespace: ""#nacos命名空间ID,此处默认为空
      username: ""#nacos登陆用户名
      password: "" #nacos登陆密码
      cluster: "default"            #nacos seata集群', ' <dependency>
            <groupId>com.tansun.tjmas</groupId>
            <artifactId>tjmas-cloud-starter-base-seata</artifactId>
            <version>1.3.0-RELEASE</version>
        </dependency>', '0', '');

-- ----------------------------
-- Table structure for t_help_def_exception
-- ----------------------------
DROP TABLE IF EXISTS "t_help_def_exception";
CREATE TABLE "t_help_def_exception" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "e_type" varchar(100) COLLATE "pg_catalog"."default",
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(200) COLLATE "pg_catalog"."default",
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "update_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_help_def_exception"."id" IS 'ID';
COMMENT ON COLUMN "t_help_def_exception"."type" IS '类型(0=模块，1=异常)';
COMMENT ON COLUMN "t_help_def_exception"."e_type" IS '异常类型';
COMMENT ON COLUMN "t_help_def_exception"."code" IS '代码';
COMMENT ON COLUMN "t_help_def_exception"."name" IS '名称';
COMMENT ON COLUMN "t_help_def_exception"."content" IS '内容';
COMMENT ON COLUMN "t_help_def_exception"."pid" IS '父ID';
COMMENT ON COLUMN "t_help_def_exception"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_help_def_exception"."create_user" IS '创建人';
COMMENT ON COLUMN "t_help_def_exception"."update_time" IS '更新时间';
COMMENT ON COLUMN "t_help_def_exception"."update_user" IS '更新人';
COMMENT ON COLUMN "t_help_def_exception"."del_ind" IS '删除标志(0=未删除，1=已删除)';
COMMENT ON COLUMN "t_help_def_exception"."version" IS '版本';
COMMENT ON COLUMN "t_help_def_exception"."tenant_id" IS '租户ID';
COMMENT ON TABLE "t_help_def_exception" IS '异常信息定义表';

-- ----------------------------
-- Records of t_help_def_exception
-- ----------------------------
INSERT INTO "t_help_def_exception" VALUES ('1', '1', '数据库操作异常', 'common-00001', '数据库操作异常', '', '', '2020-04-27 15:49:25', '1', '2020-04-27 15:49:22', '1', '1', NULL, '');

-- ----------------------------
-- Table structure for t_help_feedback
-- ----------------------------
DROP TABLE IF EXISTS "t_help_feedback";
CREATE TABLE "t_help_feedback" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "f_user_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "e_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "mid" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "m_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "update_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_help_feedback"."id" IS 'ID';
COMMENT ON COLUMN "t_help_feedback"."f_user_id" IS '反馈人(用户ID)';
COMMENT ON COLUMN "t_help_feedback"."content" IS '内容';
COMMENT ON COLUMN "t_help_feedback"."e_code" IS '异常代码';
COMMENT ON COLUMN "t_help_feedback"."mid" IS '模块ID';
COMMENT ON COLUMN "t_help_feedback"."m_name" IS '模块名称';
COMMENT ON COLUMN "t_help_feedback"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_help_feedback"."create_user" IS '创建人';
COMMENT ON COLUMN "t_help_feedback"."update_time" IS '更新时间';
COMMENT ON COLUMN "t_help_feedback"."update_user" IS '更新人';
COMMENT ON COLUMN "t_help_feedback"."del_ind" IS '删除标志(0=未删除，1=已删除)';
COMMENT ON COLUMN "t_help_feedback"."version" IS '版本';
COMMENT ON COLUMN "t_help_feedback"."tenant_id" IS '租户ID';
COMMENT ON TABLE "t_help_feedback" IS '反馈信息表';

-- ----------------------------
-- Records of t_help_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_guide_flag
-- ----------------------------
DROP TABLE IF EXISTS "t_help_guide_flag";
CREATE TABLE "t_help_guide_flag" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "login_name" varchar(32) COLLATE "pg_catalog"."default",
  "login_first" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "new_login_first" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "update_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_help_guide_flag"."id" IS 'ID';
COMMENT ON COLUMN "t_help_guide_flag"."login_name" IS '用户登录名';
COMMENT ON COLUMN "t_help_guide_flag"."login_first" IS '首次登录(0=否，1=是)';
COMMENT ON COLUMN "t_help_guide_flag"."new_login_first" IS '新功能首次登录(0=否，1=是)';
COMMENT ON COLUMN "t_help_guide_flag"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_help_guide_flag"."create_user" IS '创建人';
COMMENT ON COLUMN "t_help_guide_flag"."update_time" IS '更新时间';
COMMENT ON COLUMN "t_help_guide_flag"."update_user" IS '更新人';
COMMENT ON COLUMN "t_help_guide_flag"."del_ind" IS '删除标志(0=未删除，1=已删除)';
COMMENT ON COLUMN "t_help_guide_flag"."version" IS '版本';
COMMENT ON COLUMN "t_help_guide_flag"."tenant_id" IS '租户ID';
COMMENT ON TABLE "t_help_guide_flag" IS '用户向导标识表';

-- ----------------------------
-- Records of t_help_guide_flag
-- ----------------------------

-- ----------------------------
-- Table structure for t_help_tips
-- ----------------------------
DROP TABLE IF EXISTS "t_help_tips";
CREATE TABLE "t_help_tips" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "title" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(200) COLLATE "pg_catalog"."default",
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "update_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_help_tips"."id" IS 'ID';
COMMENT ON COLUMN "t_help_tips"."type" IS '类型(0=提示类别，1=提示)';
COMMENT ON COLUMN "t_help_tips"."code" IS '代码';
COMMENT ON COLUMN "t_help_tips"."title" IS '标题';
COMMENT ON COLUMN "t_help_tips"."content" IS '内容';
COMMENT ON COLUMN "t_help_tips"."pid" IS '父ID';
COMMENT ON COLUMN "t_help_tips"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_help_tips"."create_user" IS '创建人';
COMMENT ON COLUMN "t_help_tips"."update_time" IS '更新时间';
COMMENT ON COLUMN "t_help_tips"."update_user" IS '更新人';
COMMENT ON COLUMN "t_help_tips"."del_ind" IS '删除标志(0=未删除，1=已删除)';
COMMENT ON COLUMN "t_help_tips"."version" IS '版本';
COMMENT ON COLUMN "t_help_tips"."tenant_id" IS '租户ID';
COMMENT ON TABLE "t_help_tips" IS '操作提示表';

-- ----------------------------
-- Records of t_help_tips
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_module
-- ----------------------------
DROP TABLE IF EXISTS "t_sys_module";
CREATE TABLE "t_sys_module" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(1) COLLATE "pg_catalog"."default",
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(200) COLLATE "pg_catalog"."default",
  "pid" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "create_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "update_user" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "del_ind" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "version" int4,
  "tenant_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_sys_module"."id" IS 'ID';
COMMENT ON COLUMN "t_sys_module"."type" IS '类型';
COMMENT ON COLUMN "t_sys_module"."code" IS '代码';
COMMENT ON COLUMN "t_sys_module"."name" IS '名称';
COMMENT ON COLUMN "t_sys_module"."description" IS '描述';
COMMENT ON COLUMN "t_sys_module"."pid" IS '父ID';
COMMENT ON COLUMN "t_sys_module"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_sys_module"."create_user" IS '创建人';
COMMENT ON COLUMN "t_sys_module"."update_time" IS '更新时间';
COMMENT ON COLUMN "t_sys_module"."update_user" IS '更新人';
COMMENT ON COLUMN "t_sys_module"."del_ind" IS '删除标志(0=未删除，1=已删除)';
COMMENT ON COLUMN "t_sys_module"."version" IS '版本';
COMMENT ON COLUMN "t_sys_module"."tenant_id" IS '租户ID';
COMMENT ON TABLE "t_sys_module" IS '系统模块表';

-- ----------------------------
-- Records of t_sys_module
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table base_ddct
-- ----------------------------
ALTER TABLE "base_ddct" ADD CONSTRAINT "base_ddct_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table base_ecd_dtl
-- ----------------------------
ALTER TABLE "base_ecd_dtl" ADD CONSTRAINT "base_ecd_dtl_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table base_ecd_serial
-- ----------------------------
ALTER TABLE "base_ecd_serial" ADD CONSTRAINT "base_ecd_serial_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table base_ecd_tp
-- ----------------------------
ALTER TABLE "base_ecd_tp" ADD CONSTRAINT "base_ecd_tp_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_clntend
-- ----------------------------
ALTER TABLE "sys_clntend" ADD CONSTRAINT "sys_clntend_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_data_auth
-- ----------------------------
ALTER TABLE "sys_data_auth" ADD CONSTRAINT "sys_data_auth_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_data_auth_fkcolumn
-- ----------------------------
ALTER TABLE "sys_data_auth_fkcolumn" ADD CONSTRAINT "sys_data_auth_fkcolumn_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_department
-- ----------------------------
ALTER TABLE "sys_department" ADD CONSTRAINT "sys_department_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_org_dept_rel
-- ----------------------------
ALTER TABLE "sys_org_dept_rel" ADD CONSTRAINT "sys_org_dept_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_organization
-- ----------------------------
ALTER TABLE "sys_organization" ADD CONSTRAINT "sys_organization_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_permission
-- ----------------------------
ALTER TABLE "sys_permission" ADD CONSTRAINT "sys_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_res_data_auth_model
-- ----------------------------
ALTER TABLE "sys_res_data_auth_model" ADD CONSTRAINT "sys_res_data_auth_model_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_res_event
-- ----------------------------
ALTER TABLE "sys_res_event" ADD CONSTRAINT "sys_res_event_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_res_menu
-- ----------------------------
ALTER TABLE "sys_res_menu" ADD CONSTRAINT "sys_res_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_res_menu_event_rel
-- ----------------------------
ALTER TABLE "sys_res_menu_event_rel" ADD CONSTRAINT "sys_res_menu_event_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_permission_rel
-- ----------------------------
ALTER TABLE "sys_role_permission_rel" ADD CONSTRAINT "sys_role_permission_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_tenant
-- ----------------------------
ALTER TABLE "sys_tenant" ADD CONSTRAINT "sys_tenant_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_dept_rel
-- ----------------------------
ALTER TABLE "sys_user_dept_rel" ADD CONSTRAINT "sys_user_dept_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_org_rel
-- ----------------------------
ALTER TABLE "sys_user_org_rel" ADD CONSTRAINT "sys_user_org_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_role_rel
-- ----------------------------
ALTER TABLE "sys_user_role_rel" ADD CONSTRAINT "sys_user_role_rel_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_dev_initializr
-- ----------------------------
ALTER TABLE "t_dev_initializr" ADD CONSTRAINT "t_dev_initializr_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_help_def_exception
-- ----------------------------
ALTER TABLE "t_help_def_exception" ADD CONSTRAINT "t_help_def_exception_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_help_feedback
-- ----------------------------
ALTER TABLE "t_help_feedback" ADD CONSTRAINT "t_help_feedback_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_help_guide_flag
-- ----------------------------
ALTER TABLE "t_help_guide_flag" ADD CONSTRAINT "t_help_guide_flag_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_help_tips
-- ----------------------------
ALTER TABLE "t_help_tips" ADD CONSTRAINT "t_help_tips_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_sys_module
-- ----------------------------
ALTER TABLE "t_sys_module" ADD CONSTRAINT "t_sys_module_pkey" PRIMARY KEY ("id");
