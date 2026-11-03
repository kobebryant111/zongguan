/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : workflow

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2017-08-11 10:14:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wf_gen_table
-- ----------------------------
DROP TABLE IF EXISTS `wf_gen_table`;
CREATE TABLE `wf_gen_table` (
  `id` varchar(64)  NOT NULL COMMENT '编号',
  `process_design_id` varchar(64)  DEFAULT NULL COMMENT '流程方案id',
  `process_def_id` varchar(64)  DEFAULT NULL,
  `task_def_id` varchar(64)  DEFAULT NULL COMMENT '任务id',
  `name` varchar(200)  DEFAULT NULL COMMENT '名称',
  `comments` varchar(500)  DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100)  DEFAULT NULL COMMENT '实体类名称',
  `style_type` char(1)  DEFAULT '1' COMMENT '表单样式(1:单列；0：双列)',
  `parent_table` varchar(200)  DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100)  DEFAULT NULL COMMENT '关联父表外键',
  `create_by` varchar(64)  DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1)  NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `wf_gen_table_name` (`name`) USING BTREE,
  KEY `wf_gen_table_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=COMPACT COMMENT='业务表';

-- ----------------------------
-- Table structure for wf_gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `wf_gen_table_column`;
CREATE TABLE `wf_gen_table_column` (
  `id` varchar(64)  NOT NULL COMMENT '编号',
  `wf_gen_table_id` varchar(64)  DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200)  DEFAULT NULL COMMENT '名称',
  `comments` varchar(500)  DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100)  DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500)  DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200)  DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1)  DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1)  DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1)  DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1)  DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1)  DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1)  DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200)  DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200)  DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200)  DEFAULT NULL COMMENT '字典类型',
  `settings` varchar(2000)  DEFAULT NULL COMMENT '其它设置（扩展字段JSON）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(64)  DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1)  NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `wf_gen_table_column_table_id` (`wf_gen_table_id`) USING BTREE,
  KEY `wf_gen_table_column_name` (`name`) USING BTREE,
  KEY `wf_gen_table_column_sort` (`sort`) USING BTREE,
  KEY `wf_gen_table_column_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=COMPACT COMMENT='业务表字段';

-- ----------------------------
-- Table structure for `wf_process_variable`
-- ----------------------------
DROP TABLE IF EXISTS `wf_process_variable`;
CREATE TABLE `wf_process_variable` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT 'ID',
  `task_def_id` varchar(64)  DEFAULT NULL COMMENT '任务定义ID',
  `variable_key` varchar(64)  DEFAULT NULL COMMENT '变量标识',
  `variable_val` varchar(64)  DEFAULT NULL COMMENT '变量值',
  `variable_name` varchar(32)  DEFAULT NULL COMMENT '变量名称',
  `task_def_name` varchar(64)  DEFAULT NULL COMMENT '任务定义名称',
  `process_design_id` varchar(255)  DEFAULT NULL COMMENT '流程设计方案id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='变量配置';

-- ----------------------------
-- Table structure for wf_process_classify
-- ----------------------------
DROP TABLE IF EXISTS `wf_process_classify`;
CREATE TABLE `wf_process_classify` (
  `id` varchar(64)  NOT NULL COMMENT '编号',
  `parent_id` varchar(64) COMMENT '父级编号',
  `parent_ids` varchar(2000) COMMENT '所有父级编号',
  `name` varchar(100) COMMENT '名称',
  `sort` decimal(10,0) COMMENT '排序',
  `code` varchar(100) COMMENT '分类编号',
  `type` char(1) COMMENT '分类类型',
  `USEABLE` varchar(64) COMMENT '是否启用',
  `create_by` varchar(64) COMMENT '创建者',
  `create_date` datetime COMMENT '创建时间',
  `update_by` varchar(64) COMMENT '更新者',
  `update_date` datetime COMMENT '更新时间',
  `remarks` varchar(256)  DEFAULT NULL COMMENT '备注信息',
  `tenant_id` varchar(256)  DEFAULT NULL COMMENT '租户编号',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `wf_process_parent_id` (`parent_id`) USING BTREE,
  KEY `wf_process_del_flag` (`del_flag`) USING BTREE,
  KEY `wf_process_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='流程分类表';


-- ----------------------------
-- Table structure for wf_task_expand_data
-- ----------------------------
DROP TABLE IF EXISTS `wf_task_expand_data`;
CREATE TABLE `wf_task_expand_data` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `task_expand_plan_id` varchar(64) NOT NULL COMMENT '代办拓展方案ID',
  `process_inst_id` varchar(64) NOT NULL COMMENT '流程实例ID',
  `process_def_id` varchar(64) NOT NULL COMMENT '流程定义ID',
  `task_id` varchar(64) NOT NULL COMMENT '待办任务ID',
  `task_node_id` varchar(64) NOT NULL COMMENT '待办任务节点ID',
  `tenant_id` varchar(255) DEFAULT NULL COMMENT '系统租户ID',
    `field01` varchar(256) DEFAULT NULL COMMENT '拓展字段01',
    `field02` varchar(256) DEFAULT NULL COMMENT '拓展字段02',
    `field03` varchar(256) DEFAULT NULL COMMENT '拓展字段03',
	`field04` varchar(256) DEFAULT NULL COMMENT '拓展字段04',
	`field05` varchar(256) DEFAULT NULL COMMENT '拓展字段05',
	`field06` varchar(256) DEFAULT NULL COMMENT '拓展字段06',
	`field07` varchar(256) DEFAULT NULL COMMENT '拓展字段07',
	`field08` varchar(256) DEFAULT NULL COMMENT '拓展字段08',
	`field09` varchar(256) DEFAULT NULL COMMENT '拓展字段09',
	`field10` varchar(256) DEFAULT NULL COMMENT '拓展字段10',
	`field11` varchar(256) DEFAULT NULL COMMENT '拓展字段11',
	`field12` varchar(256) DEFAULT NULL COMMENT '拓展字段12',
	`field13` varchar(256) DEFAULT NULL COMMENT '拓展字段13',
	`field14` varchar(256) DEFAULT NULL COMMENT '拓展字段14',
	`field15` varchar(256) DEFAULT NULL COMMENT '拓展字段15',
	`field16` varchar(256) DEFAULT NULL COMMENT '拓展字段16',
	`field17` varchar(256) DEFAULT NULL COMMENT '拓展字段17',
	`field18` varchar(256) DEFAULT NULL COMMENT '拓展字段18',
	`field19` varchar(256) DEFAULT NULL COMMENT '拓展字段19',
	`field20` varchar(256) DEFAULT NULL COMMENT '拓展字段20',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程待办扩展运行数据表';


--   ALTER TABLE WF_TASK_EXPAND_DATA ADD (
-- COMMENT_MSG VARCHAR ( 2024 ),
-- OPINION VARCHAR ( 256 ),
-- OPION VARCHAR ( 256 ),
-- OPION_KEY VARCHAR ( 128 ),
-- ASSIGNEE_ORG_NAME VARCHAR ( 256 ),
-- ASSIGNEE_ORG_CODE VARCHAR ( 256 ),
-- ASSIGNEE_DEPT_NAME VARCHAR ( 256 ),
-- ASSIGNEE_DEPT_CODE VARCHAR ( 256 ),
-- ASSIGNEE_ROLE_NAME VARCHAR ( 256 ),
-- ASSIGNEE_ROLE_CODE VARCHAR ( 256 ),
-- CANDIDATE_ORGS VARCHAR ( 1024 ),
-- CANDIDATE_DEPTS VARCHAR ( 1024 ),
-- CANDIDATE_ROLES VARCHAR ( 1024 ),
-- SELECT_USER_MODEL VARCHAR ( 64 ),
-- IS_MULTI VARCHAR ( 64 ),
-- ACT_BUTTON VARCHAR ( 1024 ),
-- FORM_SCENE VARCHAR ( 256 ),
-- TASK_END_TIME timestamp,
-- TASK_CREATE_TIME timestamp,
-- IS_START_NODE VARCHAR ( 8 ),
-- TASK_TYPE VARCHAR ( 8 ),
-- TASK_NAME VARCHAR ( 256 ),
-- START_USER_ORG_CODE VARCHAR ( 256 ),
-- START_USER_PARENT_ORG_CODE VARCHAR ( 256 ),
-- START_USER_ROLE_CODE VARCHAR ( 256 ),
-- START_USER_DEPT_CODE VARCHAR ( 256 ),
-- START_USER_ID VARCHAR ( 256 ),
-- PROCESS_END_TIME timestamp,
-- PROCESS_START_TIME timestamp,
-- SUPER_PROCESS_INSTANCE_ID VARCHAR ( 128 ),
-- PROCESS_CLASSIFY_CODE VARCHAR ( 256 ),
-- PROCESS_NAME VARCHAR ( 256 ),
-- PROCESS_CLASSIFY_ID VARCHAR ( 256 ),
-- BUSINESS_KEY VARCHAR ( 256 ),
-- PROCESS_DEF_KEY VARCHAR ( 256 ));

-- ----------------------------
-- Table structure for wf_task_expand_plan
-- ----------------------------
DROP TABLE IF EXISTS `wf_task_expand_plan`;
CREATE TABLE `wf_task_expand_plan` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `wf_class_id` varchar(64) NOT NULL COMMENT '流程分类ID',
  `wf_class_name` varchar(256) NOT NULL COMMENT '流程分类名称',
  `field_key` varchar(64) DEFAULT NULL COMMENT '拓展字段键值',
  `field_name` varchar(256) DEFAULT NULL COMMENT '拓展字段名称',
  `field_value` varchar(256) DEFAULT NULL COMMENT '拓展字段数值',
  `field_type` varchar(256) DEFAULT NULL COMMENT '拓展字段类型',
  `field_url` varchar(2000) DEFAULT NULL COMMENT '拓展字段url',
  `field_status` varchar(256) DEFAULT NULL COMMENT '拓展字段状态',
  `tenant_id` varchar(255) DEFAULT NULL COMMENT '系统租户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程待办扩展方案表';

-- ----------------------------
-- Table structure for wf_time_content
-- ----------------------------
DROP TABLE IF EXISTS `wf_hi_task_expand_data`;
CREATE TABLE `wf_hi_task_expand_data` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `task_expand_plan_id` varchar(64) NOT NULL COMMENT '代办拓展方案ID',
  `process_inst_id` varchar(64) NOT NULL COMMENT '流程实例ID',
  `process_def_id` varchar(64) NOT NULL COMMENT '流程定义ID',
  `task_id` varchar(64) NOT NULL COMMENT '待办任务ID',
  `task_node_id` varchar(64) NOT NULL COMMENT '待办任务节点ID',
  `field01` varchar(256) DEFAULT NULL COMMENT '拓展字段01',
  `field02` varchar(256) DEFAULT NULL COMMENT '拓展字段02',
  `field03` varchar(256) DEFAULT NULL COMMENT '拓展字段03',
  `field04` varchar(256) DEFAULT NULL COMMENT '拓展字段04',
  `field05` varchar(256) DEFAULT NULL COMMENT '拓展字段05',
  `field06` varchar(256) DEFAULT NULL COMMENT '拓展字段06',
  `field07` varchar(256) DEFAULT NULL COMMENT '拓展字段07',
  `field08` varchar(256) DEFAULT NULL COMMENT '拓展字段08',
  `field09` varchar(256) DEFAULT NULL COMMENT '拓展字段09',
  `field10` varchar(256) DEFAULT NULL COMMENT '拓展字段10',
  `field11` varchar(256) DEFAULT NULL COMMENT '拓展字段11',
  `field12` varchar(256) DEFAULT NULL COMMENT '拓展字段12',
  `field13` varchar(256) DEFAULT NULL COMMENT '拓展字段13',
  `field14` varchar(256) DEFAULT NULL COMMENT '拓展字段14',
  `field15` varchar(256) DEFAULT NULL COMMENT '拓展字段15',
  `field16` varchar(256) DEFAULT NULL COMMENT '拓展字段16',
  `field17` varchar(256) DEFAULT NULL COMMENT '拓展字段17',
  `field18` varchar(256) DEFAULT NULL COMMENT '拓展字段18',
  `field19` varchar(256) DEFAULT NULL COMMENT '拓展字段19',
  `field20` varchar(256) DEFAULT NULL COMMENT '拓展字段20',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办扩展历史数据表';

-- ----------------------------
-- Table structure for wf_tenant
-- ----------------------------
DROP TABLE IF EXISTS `wf_tenant`;
CREATE TABLE `wf_tenant` (
  `id` varchar(64)  NOT NULL COMMENT '主键',
  `tenant_id` varchar(256)  default NULL COMMENT '租户标识',
  `name` varchar(256)  default NULL COMMENT '租户名称',
  `create_by` varchar(256)  default NULL COMMENT '创建者',
  `create_date` datetime default NULL COMMENT '创建时间',
  `update_by` varchar(64)  default NULL COMMENT '更新者',
  `update_date` datetime default NULL COMMENT '更新时间',
  `remarks` varchar(256)  default NULL COMMENT '备注信息',
  `del_flag` char(1)  NOT NULL default '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=COMPACT COMMENT='租户表';

-- ----------------------------
-- 定时配置表
-- ----------------------------
DROP TABLE IF EXISTS `wf_timer_config`;
CREATE TABLE `wf_timer_config` (
  `id` varchar(64)  NOT NULL COMMENT '定时配置信息ID',
	`proc_def_id` varchar(64)  DEFAULT NULL COMMENT '流程定义ID',
	`task_def_key` varchar(255)  DEFAULT NULL COMMENT '任务节点ID',
	`proc_inst_id` varchar(64)  DEFAULT NULL COMMENT '流程实例ID',
	`execution_id` varchar(64)  DEFAULT NULL COMMENT '执行实例ID',
	`cron_expression` varchar(255)  DEFAULT NULL COMMENT 'cron时间表达式',
  `time_type` varchar(64)  DEFAULT NULL COMMENT '时间类型：工作日,自然日',
	`begin_time` datetime DEFAULT NULL COMMENT '提醒相对时间',
	`end_time` datetime DEFAULT NULL COMMENT '结束相对时间',
	`cfrequency` bigint(20)  DEFAULT NULL COMMENT '提醒频率,单位秒',
  `remind_num` bigint(20)  DEFAULT NULL COMMENT '提醒次数',
	`send_type` varchar(64)  DEFAULT NULL COMMENT '提醒方式：email邮件shortMessage短信withinMessage',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`note` varchar(2000)  DEFAULT NULL COMMENT '定时任务备注',
	`status` varchar(8) DEFAULT NULL DEFAULT '0' COMMENT '状态0正常1失效',
  `template` varchar(1000)  default NULL COMMENT '消息模板',
  `tenant_id` varchar(256)  default NULL COMMENT '租户标识',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=COMPACT COMMENT='定时配置信息表';

-- ----------------------------
-- Table structure for `wf_defined_button`
-- ----------------------------
DROP TABLE IF EXISTS `wf_defined_button`;
CREATE TABLE `wf_defined_button` (
  `ID` varchar(36)  NOT NULL COMMENT 'ID',
  `KEY` varchar(200)  DEFAULT NULL COMMENT '按钮key',
  `NAME` varchar(200)  DEFAULT NULL COMMENT '按钮名称',
  `ALIAS` varchar(200)  DEFAULT NULL COMMENT '按钮别名',
  `STATUS` varchar(8)  DEFAULT NULL COMMENT '状态1失效0生效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='按钮自定义设置表';

-- ----------------------------
-- Records of wf_defined_button
-- ----------------------------
INSERT INTO `wf_defined_button` VALUES ('0a86e8a3526d4223afd3d8e3102c910c', 'nextTask', '下一步', '下一步,提交', '1');
INSERT INTO `wf_defined_button` VALUES ('3f11484ccb5141bd84c304bcb4bf419b', 'processMsg', '查看流程过程信息', '查看流程过程信息', '1');
INSERT INTO `wf_defined_button` VALUES ('444ec5ae0d0a4f9681c9e6049483fe03', 'terminateProcess', '终止流程', '终止流程', '1');
INSERT INTO `wf_defined_button` VALUES ('4d13d7b836c547a4975cc5bf0438f259', 'lastTask', '回退至上一步', '回退至上一步,回退', '1');
INSERT INTO `wf_defined_button` VALUES ('7290ac3e2842491e849a217cf395d816', 'suspendProcess', '挂起流程', '挂起流程', '1');
INSERT INTO `wf_defined_button` VALUES ('7caa66e5afa2431fb4da0ab5c57093f3', 'addGroupUser', '加签', '加签', '1');
INSERT INTO `wf_defined_button` VALUES ('afab98ffc20d474db2809bf12531c4b4', 'getTask', '获取任务', '获取任务', '1');
INSERT INTO `wf_defined_button` VALUES ('b3634ec91f2245428220ae79116ab98b', 'activateProcess', '激活流程', '激活流程', '1');
INSERT INTO `wf_defined_button` VALUES ('cbc443de2bf5494fb7e432ec5a2b869e', 'relay', '转发', '转发', '1');
INSERT INTO `wf_defined_button` VALUES ('cce811bab4974e93bfd83a53e8695ea7', 'releaseTask', '释放任务', '释放任务', '1');
INSERT INTO `wf_defined_button` VALUES ('eb6ca12f5002470094f1e798914c5df0', 'delGroupUser', '减签', '减签', '1');
INSERT INTO `wf_defined_button` VALUES ('f660c65a1ca44763a633e46bcb246356', 'firstTask', '退回第一步', '退回第一步', '1');
INSERT INTO `wf_defined_button` VALUES ('fc0c5d20771147d7beab59717dba3e8e', 'getBack', '取回', '取回', '3');
INSERT INTO `wf_defined_button` VALUES ('fc7ba9635f1948468ef0175c63d71dad', 'appointTask', '退回指定节点', '退回指定节点', '1');


-- ----------------------------
-- 定时配置表
-- ----------------------------
DROP TABLE IF EXISTS `wf_timer_config`;
CREATE TABLE `wf_timer_config` (
  `id` varchar(64)  NOT NULL COMMENT '定时配置信息ID',
	`proc_def_id` varchar(64)  DEFAULT NULL COMMENT '流程定义ID',
	`task_def_key` varchar(255)  DEFAULT NULL COMMENT '任务节点ID',
	`proc_inst_id` varchar(64)  DEFAULT NULL COMMENT '流程实例ID',
	`execution_id` varchar(64)  DEFAULT NULL COMMENT '执行实例ID',
	`cron_expression` varchar(255)  DEFAULT NULL COMMENT 'cron时间表达式',
  `time_type` varchar(64)  DEFAULT NULL COMMENT '时间类型：工作日,自然日',
	`begin_time` datetime DEFAULT NULL COMMENT '提醒相对时间',
	`end_time` datetime DEFAULT NULL COMMENT '结束相对时间',
	`cfrequency` bigint(20)  DEFAULT NULL COMMENT '提醒频率,单位秒',
  `remind_num` bigint(20)  DEFAULT NULL COMMENT '提醒次数',
	`send_type` varchar(64)  DEFAULT NULL COMMENT '提醒方式：email邮件shortMessage短信withinMessage',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`note` varchar(2000)  DEFAULT NULL COMMENT '定时任务备注',
	`status` varchar(8) DEFAULT NULL DEFAULT '0' COMMENT '状态0正常1失效',
  `template` varchar(1000)  default NULL COMMENT '消息模板',
  `tenant_id` varchar(256)  default NULL COMMENT '租户标识',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  ROW_FORMAT=COMPACT COMMENT='定时配置信息表';




-- 索引
CREATE INDEX parent_task_id_index ON act_ru_task(parent_task_id_);
CREATE INDEX task_id_index ON wf_task_expand_data (task_id);
CREATE INDEX task_id_index ON act_ru_identitylink(task_id_);
CREATE INDEX name_index ON act_ru_variable(name_);
CREATE INDEX execution_id_index ON act_ru_variable(execution_id_);


DROP TABLE IF EXISTS `wf_leave_transfer`;
CREATE TABLE `wf_leave_transfer` (
  `ID` varchar(36)  NOT NULL COMMENT 'ID',
  `CLASSIFY_ID` varchar(60)  NOT NULL DEFAULT '' COMMENT '流程分类ID',
  `CLASSIFY_NAME` varchar(100)  DEFAULT '' COMMENT '流程分类名称',
  `USER_ID` varchar(200)  NOT NULL COMMENT 'login_name登录名',
  `TRANSFER_ID` varchar(200)  NOT NULL COMMENT '交接人ID',
  `START_TIME` datetime NOT NULL COMMENT '开始时间',
  `END_TIME` datetime NOT NULL COMMENT '结束时间',
  `DESCRIBE_` varchar(2000)  DEFAULT NULL COMMENT '描述',
  `STATUS` varchar(8)  NOT NULL COMMENT '状态1失效0生效',
  `START_STATUS` varchar(8)  NOT NULL COMMENT '离岗移交执行状态1未执行0已执行',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='离岗移交信息表';



-- 抄送表
CREATE TABLE `WF_HI_CARBON_COPY` (
  `ID` varchar(64)  NOT NULL DEFAULT '',
  `TASK_ID` varchar(64)  DEFAULT NULL COMMENT '任务id',
  `PROC_INST_ID` varchar(64)  DEFAULT NULL COMMENT '流程实例ID',
  `PROC_DEF_ID` varchar(64)  DEFAULT NULL COMMENT '流程定义ID',
  `TYPE` varchar(64)  DEFAULT NULL COMMENT '抄送类型(role:角色,user:用户)',
  `USER_ID` varchar(255)  DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` varchar(255)  DEFAULT NULL COMMENT '角色id',
  `IS_READ` varchar(255)  DEFAULT NULL COMMENT '是否已读',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '抄送时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='任务抄送表';

-- ----------------------------
-- Table structure for wf_form
-- ----------------------------
DROP TABLE IF EXISTS `wf_form`;
CREATE TABLE `wf_form` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `form_code` varchar(64) NOT NULL COMMENT '表单编号',
  `form_name` varchar(64) NOT NULL COMMENT '表单名称',
  `form_url` varchar(100) DEFAULT NULL COMMENT '表单地址',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL COMMENT '删除标记（0：正常；1：删除）',
  `tenant_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程表单管理表';

-- ----------------------------
-- Records of wf_form
-- ----------------------------

-- ----------------------------
-- Table structure for wf_form_detail
-- ----------------------------
DROP TABLE IF EXISTS `wf_form_detail`;
CREATE TABLE `wf_form_detail` (
  `id` varchar(64)  NOT NULL COMMENT '编号',
  `form_id` varchar(64)  DEFAULT NULL COMMENT '表单主键',
  `name` varchar(200)  DEFAULT NULL COMMENT '字段名称',
  `comments` varchar(500)  DEFAULT NULL COMMENT '描述',
  `field_type` varchar(500)  DEFAULT NULL COMMENT '字段类型',
  `default_value` varchar(500)  DEFAULT NULL COMMENT '是否可为空',
  `editable` char(1)  DEFAULT NULL COMMENT '可编辑字段(Y:可编辑，N:不可编辑)',
  `show_type` varchar(255)  DEFAULT NULL,
  `dict_type` varchar(255)  DEFAULT NULL COMMENT '字典类型',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(64)  DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1)  NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='流程表单详情表';

CREATE TABLE `wf_hi_task_pool_execute_data` (
  `ID` varchar(64)  NOT NULL COMMENT 'ID',
  `TASK_ID_` varchar(64)  NOT NULL COMMENT '任务ID',
  `SYS_ID_` varchar(255)  DEFAULT NULL COMMENT '管理员ID',
  `USER_ID_` varchar(255)  DEFAULT NULL COMMENT '普通用户ID',
  `IS_ALLOT_` varchar(8)  NOT NULL COMMENT '状态1分配0释放',
  `CREATE_DATE_` datetime NOT NULL COMMENT '创建时间',
  `TENANT_ID_` varchar(256)  DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='任务池任务分配及释放历史记录表';

ALTER TABLE `act_ru_execution`
  ADD INDEX `index_proc_inst_id` (`PROC_INST_ID_`) USING BTREE ;

--   UPDATE ACT_GE_PROPERTY SET VALUE_ = '5.22.0.0', REV_ = '2' WHERE NAME_ = 'schema.version';
-- UPDATE ACT_GE_PROPERTY SET VALUE_ = 'create(5.20.0.2) upgrade(5.20.0.2->5.22.0.0)', REV_ = '2' WHERE NAME_ = 'schema.history';


alter table WF_LEAVE_TRANSFER add PROC_INST_ID varchar(36)  DEFAULT NULL COMMENT '流程实例ID' after CLASSIFY_NAME;

alter table WF_LEAVE_TRANSFER add PROC_INST_NAME varchar(255)  DEFAULT NULL COMMENT '流程名称' after PROC_INST_ID;

ALTER TABLE WF_LEAVE_TRANSFER MODIFY COLUMN CLASSIFY_ID VARCHAR(60) DEFAULT NULL COMMENT '流程分类ID';

ALTER TABLE WF_LEAVE_TRANSFER MODIFY COLUMN CLASSIFY_NAME VARCHAR(100) DEFAULT NULL COMMENT '流程分类名称';

alter table WF_LEAVE_TRANSFER add ROOT_ID varchar(200)  DEFAULT NULL COMMENT '管理员ID' after CLASSIFY_NAME;

alter table wf_defined_button add BUTTON_SORT decimal(10,0)  DEFAULT 0 COMMENT '按钮排序';
ALTER TABLE wf_defined_button ADD COLUMN `tenant_id` varchar(255) NULL COMMENT '租户编码' ;


ALTER TABLE `wf_task_expand_data`
ADD COLUMN `COMMENT_MSG` varchar(2024) NULL COMMENT '审批意见详情' AFTER `tenant_id`,
ADD COLUMN `OPINION` varchar(256) NULL COMMENT '流程操作业务名称,例如 同意' AFTER `tenant_id`,
ADD COLUMN `OPION` varchar(256) NULL COMMENT '流程操作名称，例如提交' AFTER `tenant_id`,
ADD COLUMN `OPION_KEY` varchar(128) NULL COMMENT '流程操作键值，例如submit' AFTER `tenant_id`,
ADD COLUMN `ASSIGNEE_ORG_NAME` varchar(256) NULL COMMENT '处理人机构名称' AFTER `tenant_id`,
ADD COLUMN `ASSIGNEE_ORG_CODE` varchar(256) NULL COMMENT '处理人机构编码' AFTER `tenant_id`,
ADD COLUMN `ASSIGNEE_DEPT_NAME` varchar(256) NULL COMMENT '处理人部门名称' AFTER `tenant_id`,
ADD COLUMN `ASSIGNEE_DEPT_CODE` varchar(256) NULL COMMENT '处理人部门编码' AFTER `tenant_id`,
ADD COLUMN `ASSIGNEE_ROLE_NAME` varchar(256) NULL COMMENT '处理人角色名称' AFTER `tenant_id`,
ADD COLUMN `ASSIGNEE_ROLE_CODE` varchar(256) NULL COMMENT '处理人角色编码' AFTER `tenant_id`,
ADD COLUMN `CANDIDATE_ORGS` varchar(1024) NULL COMMENT '候选机构编码' AFTER `tenant_id`,
ADD COLUMN `CANDIDATE_DEPTS` varchar(1024) NULL COMMENT '候选部门编码' AFTER `tenant_id`,
ADD COLUMN `CANDIDATE_ROLES` varchar(1024) NULL COMMENT '候选角色编码' AFTER `tenant_id`,
ADD COLUMN `SELECT_USER_MODEL` varchar(64) NULL COMMENT '处理人模式' AFTER `tenant_id`,
ADD COLUMN `IS_MULTI` varchar(64) NULL COMMENT '是否会签' AFTER `tenant_id`,
ADD COLUMN `ACT_BUTTON` varchar(1024) NULL COMMENT '操作权限,字段长度确认' AFTER `tenant_id`,
ADD COLUMN `FORM_SCENE` varchar(256) NULL COMMENT '表单场景' AFTER `tenant_id`,
ADD COLUMN `TASK_END_TIME` timestamp(3) NULL COMMENT '任务结束时间' AFTER `tenant_id`,
ADD COLUMN `TASK_CREATE_TIME` timestamp(3) NULL COMMENT '任务创建时间' AFTER `tenant_id`,
ADD COLUMN `IS_START_NODE` varchar(8) NULL COMMENT '是否为发起节点' AFTER `tenant_id`,
ADD COLUMN `TASK_TYPE` varchar(8) NULL COMMENT '任务类型' AFTER `tenant_id`,
ADD COLUMN `TASK_NAME` varchar(256) NULL COMMENT '任务名称' AFTER `tenant_id`,
ADD COLUMN `START_USER_ORG_CODE` varchar(256) NULL COMMENT '发起人机构编码' AFTER `tenant_id`,
ADD COLUMN `START_USER_PARENT_ORG_CODE` varchar(256) NULL COMMENT '发起人父级机构编码' AFTER `tenant_id`,
ADD COLUMN `START_USER_ROLE_CODE` varchar(256) NULL COMMENT '发起人角色编码' AFTER `tenant_id`,
ADD COLUMN `START_USER_DEPT_CODE` varchar(256) NULL COMMENT '发起人部门编码' AFTER `tenant_id`,
ADD COLUMN `START_USER_ID` varchar(256) NULL COMMENT '发起人用户' AFTER `tenant_id`,
ADD COLUMN `PROCESS_END_TIME` timestamp(3) NULL COMMENT '流程结束时间，实现逻辑待定' AFTER `tenant_id`,
ADD COLUMN `PROCESS_START_TIME` timestamp(3) NULL COMMENT '流程发起时间' AFTER `tenant_id`,
ADD COLUMN `SUPER_PROCESS_INSTANCE_ID` varchar(128) NULL COMMENT '父流程实例id' AFTER `tenant_id`,
ADD COLUMN `PROCESS_CLASSIFY_CODE` varchar(256) NULL COMMENT '流程分类编码' AFTER `tenant_id`,
ADD COLUMN `PROCESS_NAME` varchar(256) NULL COMMENT '流程名称' AFTER `tenant_id`,
ADD COLUMN `PROCESS_CLASSIFY_ID` varchar(256) NULL COMMENT '流程分类id' AFTER `tenant_id`,
ADD COLUMN `BUSINESS_KEY` varchar(256) NULL COMMENT '业务主键' AFTER `tenant_id`,
ADD COLUMN `PROCESS_DEF_KEY` varchar(256) NULL COMMENT '流程定义键值' AFTER `tenant_id`;


        ALTER TABLE `wf_hi_task_expand_data`
ADD COLUMN `COMMENT_MSG` varchar(2024) NULL COMMENT '审批意见详情' AFTER `task_node_id`,
ADD COLUMN `OPINION` varchar(256) NULL COMMENT '流程操作业务名称,例如 同意' AFTER `task_node_id`,
ADD COLUMN `OPION` varchar(256) NULL COMMENT '流程操作名称，例如提交' AFTER `task_node_id`,
ADD COLUMN `OPION_KEY` varchar(128) NULL COMMENT '流程操作键值，例如submit' AFTER `task_node_id`,
ADD COLUMN `ASSIGNEE_ORG_NAME` varchar(256) NULL COMMENT '处理人机构名称' AFTER `task_node_id`,
ADD COLUMN `ASSIGNEE_ORG_CODE` varchar(256) NULL COMMENT '处理人机构编码' AFTER `task_node_id`,
ADD COLUMN `ASSIGNEE_DEPT_NAME` varchar(256) NULL COMMENT '处理人部门名称' AFTER `task_node_id`,
ADD COLUMN `ASSIGNEE_DEPT_CODE` varchar(256) NULL COMMENT '处理人部门编码' AFTER `task_node_id`,
ADD COLUMN `ASSIGNEE_ROLE_NAME` varchar(256) NULL COMMENT '处理人角色名称' AFTER `task_node_id`,
ADD COLUMN `ASSIGNEE_ROLE_CODE` varchar(256) NULL COMMENT '处理人角色编码' AFTER `task_node_id`,
ADD COLUMN `CANDIDATE_ORGS` varchar(1024) NULL COMMENT '候选机构编码' AFTER `task_node_id`,
ADD COLUMN `CANDIDATE_DEPTS` varchar(1024) NULL COMMENT '候选部门编码' AFTER `task_node_id`,
ADD COLUMN `CANDIDATE_ROLES` varchar(1024) NULL COMMENT '候选角色编码' AFTER `task_node_id`,
ADD COLUMN `SELECT_USER_MODEL` varchar(64) NULL COMMENT '处理人模式' AFTER `task_node_id`,
ADD COLUMN `IS_MULTI` varchar(64) NULL COMMENT '是否会签' AFTER `task_node_id`,
ADD COLUMN `ACT_BUTTON` varchar(1024) NULL COMMENT '操作权限,字段长度确认' AFTER `task_node_id`,
ADD COLUMN `FORM_SCENE` varchar(256) NULL COMMENT '表单场景' AFTER `task_node_id`,
ADD COLUMN `TASK_END_TIME` timestamp(3) NULL COMMENT '任务结束时间' AFTER `task_node_id`,
ADD COLUMN `TASK_CREATE_TIME` timestamp(3) NULL COMMENT '任务创建时间' AFTER `task_node_id`,
ADD COLUMN `IS_START_NODE` varchar(8) NULL COMMENT '是否为发起节点' AFTER `task_node_id`,
ADD COLUMN `TASK_TYPE` varchar(8) NULL COMMENT '任务类型' AFTER `task_node_id`,
ADD COLUMN `TASK_NAME` varchar(256) NULL COMMENT '任务名称' AFTER `task_node_id`,
ADD COLUMN `START_USER_ORG_CODE` varchar(256) NULL COMMENT '发起人机构编码' AFTER `task_node_id`,
ADD COLUMN `START_USER_PARENT_ORG_CODE` varchar(256) NULL COMMENT '发起人父级机构编码' AFTER `task_node_id`,
ADD COLUMN `START_USER_ROLE_CODE` varchar(256) NULL COMMENT '发起人角色编码' AFTER `task_node_id`,
ADD COLUMN `START_USER_DEPT_CODE` varchar(256) NULL COMMENT '发起人部门编码' AFTER `task_node_id`,
ADD COLUMN `START_USER_ID` varchar(256) NULL COMMENT '发起人用户' AFTER `task_node_id`,
ADD COLUMN `PROCESS_END_TIME` timestamp(3) NULL COMMENT '流程结束时间，实现逻辑待定' AFTER `task_node_id`,
ADD COLUMN `PROCESS_START_TIME` timestamp(3) NULL COMMENT '流程发起时间' AFTER `task_node_id`,
ADD COLUMN `SUPER_PROCESS_INSTANCE_ID` varchar(128) NULL COMMENT '父流程实例id' AFTER `task_node_id`,
ADD COLUMN `PROCESS_CLASSIFY_CODE` varchar(256) NULL COMMENT '流程分类编码' AFTER `task_node_id`,
ADD COLUMN `PROCESS_NAME` varchar(256) NULL COMMENT '流程名称' AFTER `task_node_id`,
ADD COLUMN `PROCESS_CLASSIFY_ID` varchar(256) NULL COMMENT '流程分类id' AFTER `task_node_id`,
ADD COLUMN `BUSINESS_KEY` varchar(256) NULL COMMENT '业务主键' AFTER `task_node_id`,
ADD COLUMN `PROCESS_DEF_KEY` varchar(256) NULL COMMENT '流程定义键值' AFTER `task_node_id`,
ADD COLUMN `tenant_id` varchar(256) NULL COMMENT '租户id' AFTER `task_node_id`;



alter table WF_LEAVE_TRANSFER add IS_TRANSFER varchar(8)  DEFAULT NULL COMMENT '是否永久移交' after ROOT_ID;

alter table WF_LEAVE_TRANSFER add IS_POWER varchar(8)  DEFAULT NULL COMMENT '是否具有权限' after IS_TRANSFER;

alter table WF_LEAVE_TRANSFER add TENANT_ID varchar(128)  DEFAULT NULL COMMENT '租户id' after IS_POWER;

alter table WF_LEAVE_TRANSFER add IS_TRANSFER_HISTORICAL_TASKS varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '是否转办历史任务' after IS_POWER;

alter table wf_process_variable add process_def_id varchar(128)  DEFAULT NULL COMMENT '流程定义ID' after task_def_id;

alter table wf_task_expand_plan  add is_query_condition varchar(8)  DEFAULT NULL COMMENT '是否作为查询条件' after tenant_id;

ALTER TABLE `wf_tenant`
CHANGE COLUMN `name` `tenant_name`  varchar(256) CHARACTER SET utf8  NULL DEFAULT NULL AFTER `tenant_id`;

-- ----------------------------
-- Table structure for wf_date_preserve
-- ----------------------------
DROP TABLE IF EXISTS `wf_date_preserve`;
CREATE TABLE `wf_date_preserve` (
  `id` varchar(36) NOT NULL COMMENT 'ID',
  `date_preserve` varchar(64) DEFAULT NULL COMMENT '日期',
  `date_type` varchar(64) NOT NULL COMMENT '日期类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT  COMMENT='工作日历管理表';


CREATE TABLE WF_DELEGATE (
  ID VARCHAR(64 ) NOT NULL ,
  TASK_ID VARCHAR(64 ) ,
  TRUST_USER VARCHAR(64 ) ,
  AGENT_USER VARCHAR(64 ) ,
  DELEGATE_TIME DATE ,
  STATUS VARCHAR(20 ) ,
  PROCESS_DEF_ID VARCHAR(64 ) ,
  TASK_DEF_KEY VARCHAR(64 ) ,
  TENANT_ID VARCHAR(64 )
);
ALTER TABLE WF_DELEGATE MODIFY COLUMN TASK_ID VARCHAR(64) NOT NULL COMMENT '任务ID';
ALTER TABLE WF_DELEGATE MODIFY COLUMN TRUST_USER VARCHAR(64) NOT NULL COMMENT '办理人';
ALTER TABLE WF_DELEGATE MODIFY COLUMN AGENT_USER VARCHAR(64) NOT NULL COMMENT '代理人';
ALTER TABLE WF_DELEGATE MODIFY COLUMN DELEGATE_TIME VARCHAR(64) NOT NULL COMMENT '代理执行时间';
ALTER TABLE WF_DELEGATE MODIFY COLUMN STATUS VARCHAR(20) NOT NULL COMMENT '状态';
ALTER TABLE WF_DELEGATE MODIFY COLUMN PROCESS_DEF_ID VARCHAR(64) NOT NULL COMMENT '流程定义ID';
ALTER TABLE WF_DELEGATE MODIFY COLUMN TASK_DEF_KEY VARCHAR(64) NOT NULL COMMENT '任务定义ID';
ALTER TABLE WF_DELEGATE MODIFY COLUMN TENANT_ID VARCHAR(64) NOT NULL COMMENT '租户ID';
ALTER TABLE WF_DELEGATE ADD  PRIMARY KEY (ID);


-- ----------------------------
-- Table structure for WF_PROCESS_VARIABLE_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `WF_PROCESS_VARIABLE_CONFIG`;
CREATE TABLE `WF_PROCESS_VARIABLE_CONFIG` (
  `ID` varchar(36)  NOT NULL COMMENT 'ID',
  `CLASSIFY_ID` varchar(60)  NOT NULL COMMENT '流程分类ID',
  `CLASSIFY_NAME` varchar(255)  DEFAULT NULL COMMENT '流程分类名称',
  `VAR_CODE` varchar(60)  NOT NULL COMMENT '变量编号',
  `VAR_NAME` varchar(255)  NOT NULL COMMENT '变量名称',
  `VAR_TYPE` varchar(100)  NOT NULL COMMENT '变量值类型（字符串、布尔、时间、数组、整数、Long、Double）',
  `VAR_CLASS` varchar(8)  NOT NULL COMMENT '变量分类（系统变量，业务变量）',
  `USEABLE` varchar(8)  NOT NULL COMMENT '是否有效',
  `TENANT_ID` varchar(200)  DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='流程变量配置表';

CREATE TABLE `WF_EMAIL_TEMPLATE` (
	`ID` VARCHAR(36) NOT NULL COMMENT 'ID',
	`TENANT_ID` VARCHAR(64) NOT NULL COMMENT '租户ID',
	`TEMP_NAME` VARCHAR(50) NOT NULL COMMENT '模板名称',
	`TEMP_TAG` VARCHAR(50) NOT NULL COMMENT '模板标签',
	`TEMP_BODY` VARCHAR(2000) NOT NULL COMMENT '模板内容',
	`CREATE_DATE` DATE NOT NULL COMMENT '创建时间',
	`UPDATE_DATE` DATE NOT NULL COMMENT '修改时间',
	`DEL_FLAG` VARCHAR(1) NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	`TEMP_STATUS` VARCHAR(1) NOT NULL COMMENT '状态1失效0生效',
	INDEX `TENANT_ID_TEMP_NAME_TEMP_TAG_CREATE_DATE` (`TENANT_ID`, `TEMP_NAME`, `TEMP_TAG`, `CREATE_DATE`)
)
COMMENT='流程邮件任务内容模板'
ENGINE=InnoDB;

ALTER  TABLE ACT_RU_IDENTITYLINK  ADD  DUE_DATE  DATE;