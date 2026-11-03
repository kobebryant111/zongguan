 CREATE TABLE IF NOT EXISTS  WF_TEST_PLAN  (
  id varchar(256) NOT NULL COMMENT '主键',
  plan_code varchar(256) NULL COMMENT '测试计划编码',
  plan_name varchar(256) NULL COMMENT '测试计划名称',
  tenant_id varchar(256) NULL COMMENT '流程租户id',
  tenant_name varchar(256) NULL COMMENT '流程租户名称',
  classify_id varchar(256) NULL COMMENT '流程分类id',
  classify_code varchar(256) NULL COMMENT '流程分类名称',
  classify_name varchar(256) NULL COMMENT '流程分类名称',
  process_def_key varchar(256) NULL COMMENT '关联的流程键值',
  create_time datetime NULL COMMENT '创建时间',
  PRIMARY KEY (id)
);

 CREATE TABLE IF NOT EXISTS  WF_SKIP_TASK_CONFIG  (
  id varchar(256) NOT NULL COMMENT '主键',
  tenant_id varchar(256) NULL COMMENT '流程租户id',
  tenant_name varchar(256) NULL COMMENT '流程租户名称',
  process_def_key varchar(256) NULL COMMENT '关联的流程键值',
  process_name varchar(256) NULL COMMENT '关联的流程名称',
  task_assignee varchar(256) NULL COMMENT '任务处理人',
  target_node_id varchar(256) NULL COMMENT '目标节点ID',
  target_node_Name varchar(256) NULL COMMENT '目标节点名称',
  is_open varchar(256) NULL COMMENT '是否生效：Y 生效，N 失效',
  is_global varchar(256) NULL COMMENT '是否全局配置：Y 生效，N 失效',
  create_time datetime NULL COMMENT '创建时间',
  PRIMARY KEY (id)
);
 CREATE TABLE IF NOT EXISTS  WF_TEST_INS   (
  id varchar(256) NOT NULL COMMENT '主键',
  parent_id varchar(256) NOT NULL COMMENT '父节点id',
  plan_code varchar(256) NULL COMMENT '测试计划编码',
  process_instance_id varchar(256) NULL COMMENT '流程实例id',
  process_def_key varchar(256) NULL COMMENT '流程键值',
  process_info CLOB null COMMENT '流程基本信息',
  create_time datetime NULL COMMENT '创建时间',
  PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS PROCESS_DEF_KEY_INDEX ON WF_SKIP_TASK_CONFIG (PROCESS_DEF_KEY );
CREATE INDEX IF NOT EXISTS TENANT_ID_INDEX ON WF_SKIP_TASK_CONFIG (TENANT_ID );



