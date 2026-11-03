-- ----------------------------
-- Table structure for wf_gen_table
-- ----------------------------
DROP TABLE
IF EXISTS wf_gen_table;

CREATE TABLE wf_gen_table (
	ID VARCHAR (64) NOT NULL,
	process_design_id VARCHAR (64),
	process_def_id VARCHAR (64),
	task_def_id VARCHAR (64),
	NAME VARCHAR (200),
	comments VARCHAR (500),
	class_name VARCHAR (100),
	style_type CHAR (1),
	parent_table VARCHAR (200),
	parent_table_fk VARCHAR (100),
	create_by VARCHAR (64),
	create_date TIMESTAMP (6),
	update_by VARCHAR (64),
	update_date TIMESTAMP (6),
	remarks VARCHAR (255),
	del_flag CHAR (1) NOT NULL
);

COMMENT ON TABLE wf_gen_table IS '业务表';

COMMENT ON COLUMN wf_gen_table. ID IS '编号';

COMMENT ON COLUMN wf_gen_table.process_design_id IS '流程方案id';

COMMENT ON COLUMN wf_gen_table.task_def_id IS '任务id';

COMMENT ON COLUMN wf_gen_table. NAME IS '名称';

COMMENT ON COLUMN wf_gen_table.comments IS '描述';

COMMENT ON COLUMN wf_gen_table.class_name IS '实体类名称';

COMMENT ON COLUMN wf_gen_table.style_type IS '表单样式(1:单列；0：双列)';

COMMENT ON COLUMN wf_gen_table.parent_table IS '关联父表';

COMMENT ON COLUMN wf_gen_table.parent_table_fk IS '关联父表外键';

COMMENT ON COLUMN wf_gen_table.create_by IS '创建者';

COMMENT ON COLUMN wf_gen_table.create_date IS '创建时间';

COMMENT ON COLUMN wf_gen_table.update_by IS '更新者';

COMMENT ON COLUMN wf_gen_table.update_date IS '更新时间';

COMMENT ON COLUMN wf_gen_table.remarks IS '备注信息';

COMMENT ON COLUMN wf_gen_table.del_flag IS '删除标记（0：正常；1：删除）';

-- ----------------------------
-- Table structure for wf_gen_table_column
-- ----------------------------
DROP TABLE
IF EXISTS wf_gen_table_column;

CREATE TABLE wf_gen_table_column (
	ID VARCHAR (64) NOT NULL,
	wf_gen_table_id VARCHAR (64),
	NAME VARCHAR (200),
	comments VARCHAR (500),
	jdbc_type VARCHAR (100),
	java_type VARCHAR (500),
	java_field VARCHAR (200),
	is_pk CHAR (1),
	is_null CHAR (1),
	is_insert CHAR (1),
	is_edit CHAR (1),
	is_list CHAR (1),
	is_query CHAR (1),
	query_type VARCHAR (200),
	show_type VARCHAR (200),
	dict_type VARCHAR (200),
	settings VARCHAR (2000),
	sort NUMERIC (10),
	create_by VARCHAR (64),
	create_date TIMESTAMP (6),
	update_by VARCHAR (64),
	update_date TIMESTAMP (6),
	remarks VARCHAR (255),
	del_flag CHAR (1) NOT NULL
);

COMMENT ON TABLE wf_gen_table_column IS '业务表字段';

COMMENT ON COLUMN wf_gen_table_column. ID IS '编号';

COMMENT ON COLUMN wf_gen_table_column.wf_gen_table_id IS '归属表编号';

COMMENT ON COLUMN wf_gen_table_column. NAME IS '名称';

COMMENT ON COLUMN wf_gen_table_column.comments IS '描述';

COMMENT ON COLUMN wf_gen_table_column.jdbc_type IS '列的数据类型的字节长度';

COMMENT ON COLUMN wf_gen_table_column.java_type IS 'JAVA类型';

COMMENT ON COLUMN wf_gen_table_column.java_field IS 'JAVA字段名';

COMMENT ON COLUMN wf_gen_table_column.is_pk IS '是否主键';

COMMENT ON COLUMN wf_gen_table_column.is_null IS '是否可为空';

COMMENT ON COLUMN wf_gen_table_column.is_insert IS '是否为插入字段';

COMMENT ON COLUMN wf_gen_table_column.is_edit IS '是否编辑字段';

COMMENT ON COLUMN wf_gen_table_column.is_list IS '是否列表字段';

COMMENT ON COLUMN wf_gen_table_column.is_query IS '是否查询字段';

COMMENT ON COLUMN wf_gen_table_column.query_type IS '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）';

COMMENT ON COLUMN wf_gen_table_column.show_type IS '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）';

COMMENT ON COLUMN wf_gen_table_column.dict_type IS '字典类型';

COMMENT ON COLUMN wf_gen_table_column.settings IS '其它设置（扩展字段JSON）';

COMMENT ON COLUMN wf_gen_table_column.sort IS '排序（升序）';

COMMENT ON COLUMN wf_gen_table_column.create_by IS '创建者';

COMMENT ON COLUMN wf_gen_table_column.create_date IS '创建时间';

COMMENT ON COLUMN wf_gen_table_column.update_by IS '更新者';

COMMENT ON COLUMN wf_gen_table_column.update_date IS '更新时间';

COMMENT ON COLUMN wf_gen_table_column.remarks IS '备注信息';

COMMENT ON COLUMN wf_gen_table_column.del_flag IS '删除标记（0：正常；1：删除）';

-- ----------------------------
-- Table structure for wf_hi_task_expand_data
-- ----------------------------
DROP TABLE
IF EXISTS wf_hi_task_expand_data;

CREATE TABLE wf_hi_task_expand_data (
	ID VARCHAR (64) NOT NULL,
	task_expand_plan_id VARCHAR (64) NOT NULL,
	process_inst_id VARCHAR (64) NOT NULL,
	process_def_id VARCHAR (64) NOT NULL,
	task_id VARCHAR (64) NOT NULL,
	task_node_id VARCHAR (64) NOT NULL,
	field01 VARCHAR (256),
	field02 VARCHAR (256),
	field03 VARCHAR (256),
	field04 VARCHAR (256),
	field05 VARCHAR (256),
	field06 VARCHAR (256),
	field07 VARCHAR (256),
	field08 VARCHAR (256),
	field09 VARCHAR (256),
	field10 VARCHAR (256),
	field11 VARCHAR (256),
	field12 VARCHAR (256),
	field13 VARCHAR (256),
	field14 VARCHAR (256),
	field15 VARCHAR (256),
	field16 VARCHAR (256),
	field17 VARCHAR (256),
	field18 VARCHAR (256),
	field19 VARCHAR (256),
	field20 VARCHAR (256)
);

COMMENT ON TABLE wf_hi_task_expand_data IS '待办扩展历史数据表';

COMMENT ON COLUMN wf_hi_task_expand_data. ID IS 'ID';

COMMENT ON COLUMN wf_hi_task_expand_data.task_expand_plan_id IS '代办拓展方案ID';

COMMENT ON COLUMN wf_hi_task_expand_data.process_inst_id IS '流程实例ID';

COMMENT ON COLUMN wf_hi_task_expand_data.process_def_id IS '流程定义ID';

COMMENT ON COLUMN wf_hi_task_expand_data.task_id IS '待办任务ID';

COMMENT ON COLUMN wf_hi_task_expand_data.task_node_id IS '待办任务节点ID';

COMMENT ON COLUMN wf_hi_task_expand_data.field01 IS '拓展字段01';

COMMENT ON COLUMN wf_hi_task_expand_data.field02 IS '拓展字段02';

COMMENT ON COLUMN wf_hi_task_expand_data.field03 IS '拓展字段03';

COMMENT ON COLUMN wf_hi_task_expand_data.field04 IS '拓展字段04';

COMMENT ON COLUMN wf_hi_task_expand_data.field05 IS '拓展字段05';

COMMENT ON COLUMN wf_hi_task_expand_data.field06 IS '拓展字段06';

COMMENT ON COLUMN wf_hi_task_expand_data.field07 IS '拓展字段07';

COMMENT ON COLUMN wf_hi_task_expand_data.field08 IS '拓展字段08';

COMMENT ON COLUMN wf_hi_task_expand_data.field09 IS '拓展字段09';

COMMENT ON COLUMN wf_hi_task_expand_data.field10 IS '拓展字段10';

COMMENT ON COLUMN wf_hi_task_expand_data.field11 IS '拓展字段11';

COMMENT ON COLUMN wf_hi_task_expand_data.field12 IS '拓展字段12';

COMMENT ON COLUMN wf_hi_task_expand_data.field13 IS '拓展字段13';

COMMENT ON COLUMN wf_hi_task_expand_data.field14 IS '拓展字段14';

COMMENT ON COLUMN wf_hi_task_expand_data.field15 IS '拓展字段15';

COMMENT ON COLUMN wf_hi_task_expand_data.field16 IS '拓展字段16';

COMMENT ON COLUMN wf_hi_task_expand_data.field17 IS '拓展字段17';

COMMENT ON COLUMN wf_hi_task_expand_data.field18 IS '拓展字段18';

COMMENT ON COLUMN wf_hi_task_expand_data.field19 IS '拓展字段19';

COMMENT ON COLUMN wf_hi_task_expand_data.field20 IS '拓展字段20';

-- ----------------------------
-- Table structure for wf_process_classify
-- ----------------------------
DROP TABLE
IF EXISTS wf_process_classify;

CREATE TABLE wf_process_classify (
	ID VARCHAR (64) NOT NULL,
	parent_id VARCHAR (64) NOT NULL,
	parent_ids VARCHAR (2000) NOT NULL,
	NAME VARCHAR (100) NOT NULL,
	sort NUMERIC (10) NOT NULL,
	code VARCHAR (100) NOT NULL,
	TYPE CHAR (1) NOT NULL,
	USEABLE VARCHAR (64) NOT NULL,
	create_by VARCHAR (64) NOT NULL,
	create_date TIMESTAMP (6) NOT NULL,
	update_by VARCHAR (64) NOT NULL,
	update_date TIMESTAMP (6) NOT NULL,
	remarks VARCHAR (256),
	tenant_id VARCHAR (256),
	del_flag CHAR (1) NOT NULL
);

COMMENT ON TABLE wf_process_classify IS '流程分类表';

COMMENT ON COLUMN wf_process_classify. ID IS '编号';

COMMENT ON COLUMN wf_process_classify.parent_id IS '父级编号';

COMMENT ON COLUMN wf_process_classify.parent_ids IS '所有父级编号';

COMMENT ON COLUMN wf_process_classify. NAME IS '名称';

COMMENT ON COLUMN wf_process_classify.sort IS '排序';

COMMENT ON COLUMN wf_process_classify.code IS '分类编号';

COMMENT ON COLUMN wf_process_classify. TYPE IS '分类类型';

COMMENT ON COLUMN wf_process_classify.USEABLE IS '是否启用';

COMMENT ON COLUMN wf_process_classify.create_by IS '创建者';

COMMENT ON COLUMN wf_process_classify.create_date IS '创建时间';

COMMENT ON COLUMN wf_process_classify.update_by IS '更新者';

COMMENT ON COLUMN wf_process_classify.update_date IS '更新时间';

COMMENT ON COLUMN wf_process_classify.remarks IS '备注信息';

COMMENT ON COLUMN wf_process_classify.tenant_id IS '租户编号';

COMMENT ON COLUMN wf_process_classify.del_flag IS '删除标记';

-- ----------------------------
-- Table structure for wf_process_variable
-- ----------------------------
DROP TABLE
IF EXISTS wf_process_variable;

CREATE TABLE wf_process_variable (
	ID VARCHAR (36) NOT NULL,
	task_def_id VARCHAR (64),
	process_def_id VARCHAR (64) NOT NULL,
	variable_key VARCHAR (64),
	variable_val VARCHAR (64),
	variable_name VARCHAR (32),
	task_def_name VARCHAR (64),
	process_design_id VARCHAR (255)
);

COMMENT ON TABLE wf_process_variable IS '变量配置';

COMMENT ON COLUMN wf_process_variable. ID IS 'ID';

COMMENT ON COLUMN wf_process_variable.task_def_id IS '任务定义ID';

COMMENT ON COLUMN wf_process_variable.process_def_id IS '流程定义ID';

COMMENT ON COLUMN wf_process_variable.variable_key IS '变量标识';

COMMENT ON COLUMN wf_process_variable.variable_val IS '变量值';

COMMENT ON COLUMN wf_process_variable.variable_name IS '变量名称';

COMMENT ON COLUMN wf_process_variable.task_def_name IS '任务定义名称';

COMMENT ON COLUMN wf_process_variable.process_design_id IS '流程设计方案id';

-- ----------------------------
-- Table structure for wf_task_expand_data
-- ----------------------------
DROP TABLE
IF EXISTS wf_task_expand_data;

CREATE TABLE wf_task_expand_data (
	ID VARCHAR (64) NOT NULL,
	task_expand_plan_id VARCHAR (64) NOT NULL,
	process_inst_id VARCHAR (64) NOT NULL,
	process_def_id VARCHAR (64) NOT NULL,
	task_id VARCHAR (64) NOT NULL,
	task_node_id VARCHAR (64) NOT NULL,
	tenant_id VARCHAR (256),
	field01 VARCHAR (256),
	field02 VARCHAR (256),
	field03 VARCHAR (256),
	field04 VARCHAR (256),
	field05 VARCHAR (256),
	field06 VARCHAR (256),
	field07 VARCHAR (256),
	field08 VARCHAR (256),
	field09 VARCHAR (256),
	field10 VARCHAR (256),
	field11 VARCHAR (256),
	field12 VARCHAR (256),
	field13 VARCHAR (256),
	field14 VARCHAR (256),
	field15 VARCHAR (256),
	field16 VARCHAR (256),
	field17 VARCHAR (256),
	field18 VARCHAR (256),
	field19 VARCHAR (256),
	field20 VARCHAR (256)
);

COMMENT ON TABLE wf_task_expand_data IS '流程待办扩展运行数据表';

COMMENT ON COLUMN wf_task_expand_data. ID IS 'ID';

COMMENT ON COLUMN wf_task_expand_data.task_expand_plan_id IS '代办拓展方案ID';

COMMENT ON COLUMN wf_task_expand_data.process_inst_id IS '流程实例ID';

COMMENT ON COLUMN wf_task_expand_data.process_def_id IS '流程定义ID';

COMMENT ON COLUMN wf_task_expand_data.task_id IS '待办任务ID';

COMMENT ON COLUMN wf_task_expand_data.task_node_id IS '待办任务节点ID';

COMMENT ON COLUMN wf_task_expand_data.tenant_id IS '系统租户ID';

COMMENT ON COLUMN wf_task_expand_data.field01 IS '拓展字段01';

COMMENT ON COLUMN wf_task_expand_data.field02 IS '拓展字段02';

COMMENT ON COLUMN wf_task_expand_data.field03 IS '拓展字段03';

COMMENT ON COLUMN wf_task_expand_data.field04 IS '拓展字段04';

COMMENT ON COLUMN wf_task_expand_data.field05 IS '拓展字段05';

COMMENT ON COLUMN wf_task_expand_data.field06 IS '拓展字段06';

COMMENT ON COLUMN wf_task_expand_data.field07 IS '拓展字段07';

COMMENT ON COLUMN wf_task_expand_data.field08 IS '拓展字段08';

COMMENT ON COLUMN wf_task_expand_data.field09 IS '拓展字段09';

COMMENT ON COLUMN wf_task_expand_data.field10 IS '拓展字段10';

COMMENT ON COLUMN wf_task_expand_data.field11 IS '拓展字段11';

COMMENT ON COLUMN wf_task_expand_data.field12 IS '拓展字段12';

COMMENT ON COLUMN wf_task_expand_data.field13 IS '拓展字段13';

COMMENT ON COLUMN wf_task_expand_data.field14 IS '拓展字段14';

COMMENT ON COLUMN wf_task_expand_data.field15 IS '拓展字段15';

COMMENT ON COLUMN wf_task_expand_data.field16 IS '拓展字段16';

COMMENT ON COLUMN wf_task_expand_data.field17 IS '拓展字段17';

COMMENT ON COLUMN wf_task_expand_data.field18 IS '拓展字段18';

COMMENT ON COLUMN wf_task_expand_data.field19 IS '拓展字段19';

COMMENT ON COLUMN wf_task_expand_data.field20 IS '拓展字段20';

-- ----------------------------
-- Table structure for wf_task_expand_plan
-- ----------------------------
DROP TABLE
IF EXISTS wf_task_expand_plan;

CREATE TABLE wf_task_expand_plan (
	ID VARCHAR (64) NOT NULL,
	wf_class_id VARCHAR (64) NOT NULL,
	wf_class_name VARCHAR (256) NOT NULL,
	field_key VARCHAR (64),
	field_name VARCHAR (256),
	field_value VARCHAR (256),
	field_type VARCHAR (256),
	field_url VARCHAR (2000),
	field_status VARCHAR (16),
	tenant_id VARCHAR (256)
);

COMMENT ON TABLE wf_task_expand_plan IS '流程待办扩展方案表';

COMMENT ON COLUMN wf_task_expand_plan. ID IS 'ID';

COMMENT ON COLUMN wf_task_expand_plan.wf_class_id IS '流程分类ID';

COMMENT ON COLUMN wf_task_expand_plan.wf_class_name IS '流程分类名称';

COMMENT ON COLUMN wf_task_expand_plan.field_key IS '拓展字段键值';

COMMENT ON COLUMN wf_task_expand_plan.field_name IS '拓展字段名称';

COMMENT ON COLUMN wf_task_expand_plan.field_value IS '拓展字段数值';

COMMENT ON COLUMN wf_task_expand_plan.field_type IS '类型';

COMMENT ON COLUMN wf_task_expand_plan.field_url IS '链接';

COMMENT ON COLUMN wf_task_expand_plan.field_status IS '是否隐藏';

COMMENT ON COLUMN wf_task_expand_plan.tenant_id IS '系统租户ID';

-- ----------------------------
-- Table structure for wf_tenant
-- ----------------------------
DROP TABLE
IF EXISTS wf_tenant;

CREATE TABLE wf_tenant (
	ID VARCHAR (64) NOT NULL,
	tenant_id VARCHAR (256),
	NAME VARCHAR (256),
	create_by VARCHAR (256),
	create_date TIMESTAMP (6),
	update_by VARCHAR (64),
	update_date TIMESTAMP (6),
	remarks VARCHAR (256),
	del_flag CHAR (1) NOT NULL
);

COMMENT ON TABLE wf_tenant IS '租户表';

COMMENT ON COLUMN wf_tenant. ID IS '主键';

COMMENT ON COLUMN wf_tenant.tenant_id IS '租户标识';

COMMENT ON COLUMN wf_tenant. NAME IS '租户名称';

COMMENT ON COLUMN wf_tenant.create_by IS '创建者';

COMMENT ON COLUMN wf_tenant.create_date IS '创建时间';

COMMENT ON COLUMN wf_tenant.update_by IS '更新者';

COMMENT ON COLUMN wf_tenant.update_date IS '更新时间';

COMMENT ON COLUMN wf_tenant.remarks IS '备注信息';

COMMENT ON COLUMN wf_tenant.del_flag IS '删除标记（0：正常；1：删除）';

-- ----------------------------
-- Table structure for WF_DEFINED_BUTTON
-- ----------------------------
CREATE TABLE WF_DEFINED_BUTTON (
  ID VARCHAR(36) NOT NULL,
  KEY VARCHAR(200) NULL,
  NAME VARCHAR(200) NULL,
  ALIAS VARCHAR(200) NULL,
  STATUS VARCHAR(8) NULL
);
COMMENT ON TABLE WF_DEFINED_BUTTON IS '按钮自定义设置表';

COMMENT ON COLUMN WF_DEFINED_BUTTON. ID IS 'ID';

COMMENT ON COLUMN WF_DEFINED_BUTTON. KEY IS '按钮key';

COMMENT ON COLUMN WF_DEFINED_BUTTON. NAME IS '按钮名称';

COMMENT ON COLUMN WF_DEFINED_BUTTON. ALIAS IS '按钮别名';

COMMENT ON COLUMN WF_DEFINED_BUTTON. STATUS IS '状态1失效0生效';

ALTER TABLE WF_DEFINED_BUTTON ADD PRIMARY KEY (ID);

INSERT INTO WF_DEFINED_BUTTON VALUES ('0a86e8a3526d4223afd3d8e3102c910c', 'nextTask', '下一步', '下一步', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('3f11484ccb5141bd84c304bcb4bf419b', 'processMsg', '查看流程过程信息', '查看流程过程信息', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('444ec5ae0d0a4f9681c9e6049483fe03', 'terminateProcess', '终止流程', '终止流程', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('4d13d7b836c547a4975cc5bf0438f259', 'lastTask', '回退至上一步', '回退至上一步', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('7290ac3e2842491e849a217cf395d816', 'suspendProcess', '挂起流程', '挂起流程', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('7caa66e5afa2431fb4da0ab5c57093f3', 'addGroupUser', '加签', '加签', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('afab98ffc20d474db2809bf12531c4b4', 'getTask', '获取任务', '获取任务', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('b3634ec91f2245428220ae79116ab98b', 'activateProcess', '激活流程', '激活流程', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('cbc443de2bf5494fb7e432ec5a2b869e', 'relay', '转发', '转发', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('cce811bab4974e93bfd83a53e8695ea7', 'releaseTask', '释放任务', '释放任务', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('eb6ca12f5002470094f1e798914c5df0', 'delGroupUser', '减签', '减签', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('f660c65a1ca44763a633e46bcb246356', 'firstTask', '退回第一步', '退回第一步', '1');
INSERT INTO WF_DEFINED_BUTTON VALUES ('fc0c5d20771147d7beab59717dba3e8e', 'getBack', '取回', '取回', '3');
INSERT INTO WF_DEFINED_BUTTON VALUES ('fc7ba9635f1948468ef0175c63d71dad', 'appointTask', '退回指定节点', '退回指定节点', '1');

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------
-- ----------------------------
-- Indexes structure for table wf_gen_table
-- ----------------------------
CREATE INDEX wf_gen_table_del_flag ON wf_gen_table USING btree (del_flag);

CREATE INDEX wf_gen_table_name ON wf_gen_table USING btree (NAME);

-- ----------------------------
-- Primary Key structure for table wf_gen_table
-- ----------------------------
ALTER TABLE wf_gen_table ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table wf_gen_table_column
-- ----------------------------
CREATE INDEX wf_gen_table_column_del_flag ON wf_gen_table_column USING btree (del_flag);

CREATE INDEX wf_gen_table_column_name ON wf_gen_table_column USING btree (NAME);

CREATE INDEX wf_gen_table_column_sort ON wf_gen_table_column USING btree (sort);

CREATE INDEX wf_gen_table_column_table_id ON wf_gen_table_column USING btree (wf_gen_table_id);

-- ----------------------------
-- Primary Key structure for table wf_gen_table_column
-- ----------------------------
ALTER TABLE wf_gen_table_column ADD PRIMARY KEY (ID);

-- ----------------------------
-- Primary Key structure for table wf_hi_task_expand_data
-- ----------------------------
ALTER TABLE wf_hi_task_expand_data ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table wf_process_classify
-- ----------------------------
CREATE INDEX wf_process_del_flag ON wf_process_classify USING btree (del_flag);

CREATE INDEX wf_process_parent_id ON wf_process_classify USING btree (parent_id);

CREATE INDEX wf_process_type ON wf_process_classify USING btree (TYPE);

-- ----------------------------
-- Primary Key structure for table wf_process_classify
-- ----------------------------
ALTER TABLE wf_process_classify ADD PRIMARY KEY (ID);

-- ----------------------------
-- Primary Key structure for table wf_process_variable
-- ----------------------------
ALTER TABLE wf_process_variable ADD PRIMARY KEY (ID);

-- ----------------------------
-- Primary Key structure for table wf_task_expand_data
-- ----------------------------
ALTER TABLE wf_task_expand_data ADD PRIMARY KEY (ID);

-- ----------------------------
-- Primary Key structure for table wf_task_expand_plan
-- ----------------------------
ALTER TABLE wf_task_expand_plan ADD PRIMARY KEY (ID);

-- ----------------------------
-- Primary Key structure for table wf_tenant
-- ----------------------------
ALTER TABLE wf_tenant ADD PRIMARY KEY (ID);

DROP TABLE
IF EXISTS wf_timer_config;
CREATE TABLE wf_timer_config (
	id varchar(36) NOT NULL,
    proc_def_id varchar(64),
	task_def_key varchar(255),
	proc_inst_id varchar(64),
	execution_id varchar(64),
	cron_expression varchar(255),
    time_type varchar(64),
	begin_time TIMESTAMP(6),
	end_time TIMESTAMP(6),
	cfrequency NUMERIC(20),
    remind_num NUMERIC(20),
	send_type varchar(64),
	create_time TIMESTAMP(6),
	note varchar(2000),
	status varchar(8),
    template varchar(1000),
    tenant_id varchar(256)
);

COMMENT ON COLUMN wf_timer_config.id IS '定时配置信息ID';
COMMENT ON COLUMN wf_timer_config.proc_def_id IS '流程定义ID';
COMMENT ON COLUMN wf_timer_config.task_def_key IS '任务节点ID';
COMMENT ON COLUMN wf_timer_config.proc_inst_id IS '流程实例ID';
COMMENT ON COLUMN wf_timer_config.execution_id IS '执行实例ID';
COMMENT ON COLUMN wf_timer_config.cron_expression IS 'cron时间表达式';
COMMENT ON COLUMN wf_timer_config.time_type IS '时间类型：工作日,自然日';
COMMENT ON COLUMN wf_timer_config.begin_time IS '提醒相对时间';
COMMENT ON COLUMN wf_timer_config.end_time IS '结束相对时间';
COMMENT ON COLUMN wf_timer_config.cfrequency IS '提醒频率,单位秒';
COMMENT ON COLUMN wf_timer_config.remind_num IS '提醒次数';
COMMENT ON COLUMN wf_timer_config.send_type IS '提醒方式：email邮件shortMessage短信withinMessage';
COMMENT ON COLUMN wf_timer_config.create_time IS '创建时间';
COMMENT ON COLUMN wf_timer_config.note IS '定时任务备注';
COMMENT ON COLUMN wf_timer_config.status IS '状态0正常1失效';
COMMENT ON COLUMN wf_timer_config.template IS '消息模板';
COMMENT ON COLUMN wf_timer_config.tenant_id IS '租户标识';

DROP TABLE
IF EXISTS WF_LEAVE_TRANSFER;

CREATE TABLE WF_LEAVE_TRANSFER (
	ID VARCHAR (36) NOT NULL,
	CLASSIFY_ID VARCHAR(60) NOT NULL,
  	CLASSIFY_NAME VARCHAR(100),
	USER_ID VARCHAR (64) NOT NULL,
	TRANSFER_ID VARCHAR (64) NOT NULL,
	START_TIME TIMESTAMP (6) NOT NULL,
	END_TIME TIMESTAMP (6) NOT NULL,
	DESCRIBE_ VARCHAR (200),
	STATUS VARCHAR (8) NOT NULL,
	START_STATUS VARCHAR(8) NOT NULL
);

COMMENT ON TABLE WF_LEAVE_TRANSFER IS '离岗移交信息表';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.ID IS 'ID';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.CLASSIFY_ID IS '流程分类ID';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.CLASSIFY_NAME IS '流程分类名称';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.USER_ID IS 'login_name登录名';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.TRANSFER_ID IS '交接人ID';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.START_TIME IS '开始时间';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.END_TIME IS '结束时间';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.DESCRIBE_ IS '描述';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.STATUS IS '状态1失效0生效';

COMMENT ON COLUMN WF_LEAVE_TRANSFER.START_STATUS IS '离岗移交执行状态1未执行0已执行';

-- ----------------------------
-- Primary Key structure for table WF_LEAVE_TRANSFER
-- ----------------------------
ALTER TABLE WF_LEAVE_TRANSFER ADD PRIMARY KEY (ID);



DROP TABLE IF EXISTS WF_HI_CARBON_COPY;
CREATE TABLE WF_HI_CARBON_COPY (
ID varchar(64) NOT NULL,
TASK_ID varchar(64),
PROC_INST_ID varchar(64),
PROC_DEF_ID varchar(64),
TYPE varchar(64),
USER_ID varchar(255),
ROLE_ID varchar(255),
IS_READ varchar(255),
CREATE_TIME timestamp(6)
);
COMMENT ON TABLE WF_HI_CARBON_COPY IS '任务抄送表';
COMMENT ON COLUMN WF_HI_CARBON_COPY.TASK_ID IS '任务id';
COMMENT ON COLUMN WF_HI_CARBON_COPY.PROC_INST_ID IS '流程实例ID';
COMMENT ON COLUMN WF_HI_CARBON_COPY.PROC_DEF_ID IS '流程定义ID';
COMMENT ON COLUMN WF_HI_CARBON_COPY.TYPE IS '抄送类型(role:角色,user:用户)';
COMMENT ON COLUMN WF_HI_CARBON_COPY.USER_ID IS '用户id';
COMMENT ON COLUMN WF_HI_CARBON_COPY.ROLE_ID IS '角色id';
COMMENT ON COLUMN WF_HI_CARBON_COPY.IS_READ IS '是否已读';
COMMENT ON COLUMN WF_HI_CARBON_COPY.CREATE_TIME IS '抄送时间';


-- ----------------------------
-- Table structure for wf_form_detail
-- ----------------------------
DROP TABLE IF EXISTS wf_form_detail;
CREATE TABLE wf_form_detail (
id VARCHAR(64) NOT NULL,
form_id VARCHAR(64) ,
name VARCHAR(200) ,
comments VARCHAR(500) ,
field_type VARCHAR(500) ,
default_value VARCHAR(500) ,
editable CHAR(1) ,
show_type VARCHAR(255) ,
dict_type VARCHAR(255) ,
sort NUMERIC(10),
create_by VARCHAR(64) ,
create_date TIMESTAMP(6),
update_by VARCHAR(64) ,
update_date TIMESTAMP(6),
remarks VARCHAR(255) ,
del_flag CHAR(1) NOT NULL
);

COMMENT ON TABLE wf_form_detail IS '流程表单详情表';
COMMENT ON COLUMN wf_form_detail.id IS '编号';
COMMENT ON COLUMN wf_form_detail.form_id IS '表单主键';
COMMENT ON COLUMN wf_form_detail.name IS '字段名称';
COMMENT ON COLUMN wf_form_detail.comments IS '描述';
COMMENT ON COLUMN wf_form_detail.field_type IS '字段类型';
COMMENT ON COLUMN wf_form_detail.default_value IS '是否可为空';
COMMENT ON COLUMN wf_form_detail.editable IS '可编辑字段(Y:可编辑，N:不可编辑)';
COMMENT ON COLUMN wf_form_detail.dict_type IS '字典类型';
COMMENT ON COLUMN wf_form_detail.sort IS '排序（升序）';
COMMENT ON COLUMN wf_form_detail.create_by IS '创建者';
COMMENT ON COLUMN wf_form_detail.create_date IS '创建时间';
COMMENT ON COLUMN wf_form_detail.update_by IS '更新者';
COMMENT ON COLUMN wf_form_detail.update_date IS '更新时间';
COMMENT ON COLUMN wf_form_detail.remarks IS '备注信息';
COMMENT ON COLUMN wf_form_detail.del_flag IS '删除标记（0：正常；1：删除）';


-- ----------------------------
-- Table structure for wf_form
-- ----------------------------
DROP TABLE IF EXISTS wf_form;
CREATE TABLE wf_form (
id varchar(36) NOT NULL,
form_code varchar(64) NOT NULL,
form_name varchar(64) NOT NULL,
form_url varchar(100),
create_by varchar(64),
create_date timestamp(6),
update_by varchar(64),
update_date timestamp(6),
remarks varchar(255),
del_flag char(1) NOT NULL,
tenant_id varchar(64)
);

COMMENT ON TABLE wf_form IS '流程表单管理表';
COMMENT ON COLUMN wf_form.id IS 'ID';
COMMENT ON COLUMN wf_form.form_code IS '表单编号';
COMMENT ON COLUMN wf_form.form_name IS '表单名称';
COMMENT ON COLUMN wf_form.form_url IS '表单地址';
COMMENT ON COLUMN wf_form.create_by IS '创建人';
COMMENT ON COLUMN wf_form.create_date IS '创建时间';
COMMENT ON COLUMN wf_form.update_by IS '修改人';
COMMENT ON COLUMN wf_form.update_date IS '修改时间';
COMMENT ON COLUMN wf_form.remarks IS '备注信息';
COMMENT ON COLUMN wf_form.del_flag IS '删除标记（0：正常；1：删除）';

CREATE TABLE wf_hi_task_pool_execute_data (
	ID VARCHAR (64) NOT NULL,
	TASK_ID_ VARCHAR (64) NOT NULL,
	SYS_ID_ VARCHAR (255),
	USER_ID_ VARCHAR (255),
	IS_ALLOT_ VARCHAR (8) NOT NULL,
	CREATE_DATE_ TIMESTAMP (6) NOT NULL,
	TENANT_ID_ VARCHAR (256)
);

COMMENT ON TABLE wf_hi_task_pool_execute_data IS '任务池任务分配及释放历史记录表';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.ID IS 'ID';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.TASK_ID_ IS '任务ID';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.SYS_ID_ IS '管理员ID';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.USER_ID_ IS '普通用户ID';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.IS_ALLOT_ IS '状态1分配0释放';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.CREATE_DATE_ IS '创建时间';

COMMENT ON COLUMN wf_hi_task_pool_execute_data.TENANT_ID_ IS '租户ID';

UPDATE ACT_GE_PROPERTY SET VALUE_ = '5.22.0.0', REV_ = '2' WHERE NAME_ = 'schema.version';
UPDATE ACT_GE_PROPERTY SET VALUE_ = 'create(5.20.0.2) upgrade(5.20.0.2->5.22.0.0)', REV_ = '2' WHERE NAME_ = 'schema.history';




alter table WF_LEAVE_TRANSFER add PROC_INST_ID VARCHAR(256);
comment on column WF_LEAVE_TRANSFER.PROC_INST_ID
  is '流程实例ID';

  alter table WF_LEAVE_TRANSFER add PROC_INST_NAME VARCHAR(256);
comment on column WF_LEAVE_TRANSFER.PROC_INST_NAME
  is '流程名称';

  alter table WF_LEAVE_TRANSFER add ROOT_ID VARCHAR(256);
comment on column WF_LEAVE_TRANSFER.ROOT_ID
  is '管理员ID';

  ALTER TABLE WF_TASK_EXPAND_DATA
 ADD  column COMMENT_MSG VARCHAR ( 2024 ),
 ADD  column OPINION VARCHAR ( 256 ),
 ADD  column OPION VARCHAR ( 256 ),
 ADD  column OPION_KEY VARCHAR ( 128 ),
 ADD  column ASSIGNEE_ORG_NAME VARCHAR ( 256 ),
 ADD  column ASSIGNEE_ORG_CODE VARCHAR ( 256 ),
 ADD  column ASSIGNEE_DEPT_NAME VARCHAR ( 256 ),
 ADD  column ASSIGNEE_DEPT_CODE VARCHAR ( 256 ),
 ADD  column ASSIGNEE_ROLE_NAME VARCHAR ( 256 ),
 ADD  column ASSIGNEE_ROLE_CODE VARCHAR ( 256 ),
 ADD  column CANDIDATE_ORGS VARCHAR ( 1024 ),
 ADD  column CANDIDATE_DEPTS VARCHAR ( 1024 ),
 ADD  column CANDIDATE_ROLES VARCHAR ( 1024 ),
 ADD  column SELECT_USER_MODEL VARCHAR ( 64 ),
 ADD  column IS_MULTI VARCHAR ( 64 ),
 ADD  column ACT_BUTTON VARCHAR ( 1024 ),
 ADD  column FORM_SCENE VARCHAR ( 256 ),
 ADD  column TASK_END_TIME timestamp,
 ADD  column TASK_CREATE_TIME timestamp,
 ADD  column IS_START_NODE VARCHAR ( 8 ),
 ADD  column TASK_TYPE VARCHAR ( 8 ),
 ADD  column TASK_NAME VARCHAR ( 256 ),
 ADD  column START_USER_ORG_CODE VARCHAR ( 256 ),
 ADD  column START_USER_PARENT_ORG_CODE VARCHAR ( 256 ),
 ADD  column START_USER_ROLE_CODE VARCHAR ( 256 ),
 ADD  column START_USER_DEPT_CODE VARCHAR ( 256 ),
 ADD  column START_USER_ID VARCHAR ( 256 ),
 ADD  column PROCESS_END_TIME timestamp,
 ADD  column PROCESS_START_TIME timestamp,
 ADD  column SUPER_PROCESS_INSTANCE_ID VARCHAR ( 128 ),
 ADD  column PROCESS_CLASSIFY_CODE VARCHAR ( 256 ),
 ADD  column PROCESS_NAME VARCHAR ( 256 ),
 ADD  column PROCESS_CLASSIFY_ID VARCHAR ( 256 ),
 ADD  column BUSINESS_KEY VARCHAR ( 256 ),
 ADD  column PROCESS_DEF_KEY VARCHAR ( 256 );

COMMENT ON COLUMN WF_TASK_EXPAND_DATA.COMMENT_MSG IS '审批意见详情';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.OPINION IS '流程操作业务名称,例如 同意';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.OPION IS '流程操作名称，例如提交';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.OPION_KEY IS '流程操作键值，例如submit';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ASSIGNEE_ORG_NAME IS '处理人机构名称';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ASSIGNEE_ORG_CODE IS '处理人机构编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ASSIGNEE_DEPT_NAME IS '处理人部门名称';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ASSIGNEE_DEPT_CODE IS '处理人部门编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ASSIGNEE_ROLE_NAME IS '处理人角色名称';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ASSIGNEE_ROLE_CODE IS '处理人角色编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.CANDIDATE_ORGS IS '候选机构编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.CANDIDATE_DEPTS IS '候选部门编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.CANDIDATE_ROLES IS '候选角色编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.SELECT_USER_MODEL IS '处理人模式';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.IS_MULTI IS '是否会签';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.ACT_BUTTON IS '操作权限,字段长度确认';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.FORM_SCENE IS '表单场景';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.TASK_END_TIME IS '任务结束时间';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.TASK_CREATE_TIME IS '任务创建时间';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.IS_START_NODE IS '是否为发起节点';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.TASK_TYPE IS '任务类型';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.TASK_NAME IS '任务名称';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.START_USER_ORG_CODE IS '发起人机构编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.START_USER_PARENT_ORG_CODE IS '发起人父级机构编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.START_USER_ROLE_CODE IS '发起人角色编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.START_USER_DEPT_CODE IS '发起人部门编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.START_USER_ID IS '发起人用户';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.PROCESS_END_TIME IS '流程结束时间，实现逻辑待定';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.PROCESS_START_TIME IS '流程发起时间';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.SUPER_PROCESS_INSTANCE_ID IS '父流程实例id';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.PROCESS_CLASSIFY_CODE IS '流程分类编码';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.PROCESS_NAME IS '流程名称';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.PROCESS_CLASSIFY_ID IS '流程分类id';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.BUSINESS_KEY IS '业务主键';
COMMENT ON COLUMN WF_TASK_EXPAND_DATA.PROCESS_DEF_KEY IS '流程定义键值';

ALTER TABLE wf_hi_task_expand_data
 ADD  column  COMMENT_MSG VARCHAR ( 2024 ),
 ADD  column  OPINION VARCHAR ( 256 ),
 ADD  column  OPION VARCHAR ( 256 ),
 ADD  column  OPION_KEY VARCHAR ( 128 ),
 ADD  column  ASSIGNEE_ORG_NAME VARCHAR ( 256 ),
 ADD  column  ASSIGNEE_ORG_CODE VARCHAR ( 256 ),
 ADD  column  ASSIGNEE_DEPT_NAME VARCHAR ( 256 ),
 ADD  column  ASSIGNEE_DEPT_CODE VARCHAR ( 256 ),
 ADD  column  ASSIGNEE_ROLE_NAME VARCHAR ( 256 ),
 ADD  column  ASSIGNEE_ROLE_CODE VARCHAR ( 256 ),
 ADD  column  CANDIDATE_ORGS VARCHAR ( 1024 ),
 ADD  column  CANDIDATE_DEPTS VARCHAR ( 1024 ),
 ADD  column  CANDIDATE_ROLES VARCHAR ( 1024 ),
 ADD  column  SELECT_USER_MODEL VARCHAR ( 64 ),
 ADD  column  IS_MULTI VARCHAR ( 64 ),
 ADD  column  ACT_BUTTON VARCHAR ( 1024 ),
 ADD  column  FORM_SCENE VARCHAR ( 256 ),
 ADD  column  TASK_END_TIME timestamp,
 ADD  column  TASK_CREATE_TIME timestamp,
 ADD  column  IS_START_NODE VARCHAR ( 8 ),
 ADD  column  TASK_TYPE VARCHAR ( 8 ),
 ADD  column  TASK_NAME VARCHAR ( 256 ),
 ADD  column  START_USER_ORG_CODE VARCHAR ( 256 ),
 ADD  column  START_USER_PARENT_ORG_CODE VARCHAR ( 256 ),
 ADD  column  START_USER_ROLE_CODE VARCHAR ( 256 ),
 ADD  column  START_USER_DEPT_CODE VARCHAR ( 256 ),
 ADD  column  START_USER_ID VARCHAR ( 256 ),
 ADD  column  PROCESS_END_TIME timestamp,
 ADD  column  PROCESS_START_TIME timestamp,
 ADD  column  SUPER_PROCESS_INSTANCE_ID VARCHAR ( 128 ),
 ADD  column  PROCESS_CLASSIFY_CODE VARCHAR ( 256 ),
 ADD  column  PROCESS_NAME VARCHAR ( 256 ),
 ADD  column  PROCESS_CLASSIFY_ID VARCHAR ( 256 ),
 ADD  column  BUSINESS_KEY VARCHAR ( 256 ),
 ADD  column  PROCESS_DEF_KEY VARCHAR ( 256 ),
 ADD  column  tenant_id VARCHAR ( 256 );

COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.COMMENT_MSG IS '审批意见详情';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.OPINION IS '流程操作业务名称,例如 同意';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.OPION IS '流程操作名称，例如提交';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.OPION_KEY IS '流程操作键值，例如submit';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ASSIGNEE_ORG_NAME IS '处理人机构名称';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ASSIGNEE_ORG_CODE IS '处理人机构编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ASSIGNEE_DEPT_NAME IS '处理人部门名称';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ASSIGNEE_DEPT_CODE IS '处理人部门编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ASSIGNEE_ROLE_NAME IS '处理人角色名称';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ASSIGNEE_ROLE_CODE IS '处理人角色编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.CANDIDATE_ORGS IS '候选机构编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.CANDIDATE_DEPTS IS '候选部门编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.CANDIDATE_ROLES IS '候选角色编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.SELECT_USER_MODEL IS '处理人模式';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.IS_MULTI IS '是否会签';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.ACT_BUTTON IS '操作权限,字段长度确认';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.FORM_SCENE IS '表单场景';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.TASK_END_TIME IS '任务结束时间';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.TASK_CREATE_TIME IS '任务创建时间';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.IS_START_NODE IS '是否为发起节点';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.TASK_TYPE IS '任务类型';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.TASK_NAME IS '任务名称';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.START_USER_ORG_CODE IS '发起人机构编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.START_USER_PARENT_ORG_CODE IS '发起人父级机构编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.START_USER_ROLE_CODE IS '发起人角色编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.START_USER_DEPT_CODE IS '发起人部门编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.START_USER_ID IS '发起人用户';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.PROCESS_END_TIME IS '流程结束时间，实现逻辑待定';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.PROCESS_START_TIME IS '流程发起时间';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.SUPER_PROCESS_INSTANCE_ID IS '父流程实例id';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.PROCESS_CLASSIFY_CODE IS '流程分类编码';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.PROCESS_NAME IS '流程名称';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.PROCESS_CLASSIFY_ID IS '流程分类id';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.BUSINESS_KEY IS '业务主键';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.PROCESS_DEF_KEY IS '流程定义键值';
COMMENT ON COLUMN WF_HI_TASK_EXPAND_DATA.tenant_id IS '租户id';

alter table WF_LEAVE_TRANSFER ALTER COLUMN classify_id DROP NOT NULL;

ALTER TABLE WF_LEAVE_TRANSFER ADD IS_TRANSFER VARCHAR(8);
COMMENT ON COLUMN WF_LEAVE_TRANSFER.IS_TRANSFER IS '是否永久移交';

ALTER TABLE WF_LEAVE_TRANSFER ADD IS_POWER VARCHAR(8);
COMMENT ON COLUMN WF_LEAVE_TRANSFER.IS_POWER IS '是否还有权限';

ALTER TABLE WF_LEAVE_TRANSFER ADD TENANT_ID VARCHAR(128);
COMMENT ON COLUMN WF_LEAVE_TRANSFER.TENANT_ID IS '租户id';

ALTER TABLE WF_LEAVE_TRANSFER ADD IS_TRANSFER_HISTORICAL_TASKS VARCHAR(8);
COMMENT ON COLUMN WF_LEAVE_TRANSFER.IS_TRANSFER_HISTORICAL_TASKS IS '是否转办历史任务';

alter table wf_defined_button add BUTTON_SORT int4 DEFAULT 0;
comment on column wf_defined_button.BUTTON_SORT is '排序';

alter table wf_defined_button add tenant_id VARCHAR(256);
comment on column wf_defined_button.tenant_id is '租户id';


ALTER TABLE wf_task_expand_plan ADD is_query_condition VARCHAR(128);
COMMENT ON COLUMN wf_task_expand_plan.is_query_condition IS '是否作为查询条件';

ALTER TABLE wf_tenant RENAME "name" TO "tenant_name";

-- ----------------------------
-- Table structure for WF_DATE_PRESERVE
-- ----------------------------
DROP TABLE IF EXISTS WF_DATE_PRESERVE;
CREATE TABLE WF_DATE_PRESERVE (
ID VARCHAR(36) NOT NULL ,
DATE_PRESERVE VARCHAR(64) NOT NULL ,
DATE_TYPE VARCHAR(64) NOT NULL
);
COMMENT ON TABLE WF_DATE_PRESERVE IS '工作日历管理表';
COMMENT ON COLUMN WF_DATE_PRESERVE.ID IS 'ID';
COMMENT ON COLUMN WF_DATE_PRESERVE.DATE_PRESERVE IS '日期';
COMMENT ON COLUMN WF_DATE_PRESERVE.DATE_TYPE IS '日期类型';

-- ----------------------------
-- Table structure for WF_PROCESS_VARIABLE_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS WF_PROCESS_VARIABLE_CONFIG;
CREATE TABLE WF_PROCESS_VARIABLE_CONFIG (
ID varchar(36) NOT NULL,
CLASSIFY_ID varchar(60) NOT NULL,
CLASSIFY_NAME varchar(255),
VAR_CODE varchar(60) NOT NULL,
VAR_NAME varchar(255) NOT NULL,
VAR_TYPE varchar(100) NOT NULL,
VAR_CLASS varchar(8) NOT NULL,
USEABLE varchar(8) NOT NULL,
TENANT_ID varchar(200)
);

COMMENT ON TABLE WF_PROCESS_VARIABLE_CONFIG IS '流程变量配置表';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.ID IS 'ID';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.CLASSIFY_ID IS '流程分类ID';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.CLASSIFY_NAME IS '流程分类名称';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.VAR_CODE IS '变量编号';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.VAR_NAME IS '变量名称';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.VAR_TYPE IS '变量值类型（字符串、布尔、时间、数组、整数、Long、Double）';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.VAR_CLASS IS '变量分类（系统变量，业务变量）';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.USEABLE IS '是否有效';
COMMENT ON COLUMN WF_PROCESS_VARIABLE_CONFIG.TENANT_ID IS '租户编号';
