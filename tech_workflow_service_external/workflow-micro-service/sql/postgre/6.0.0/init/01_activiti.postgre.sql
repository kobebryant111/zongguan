create table ACT_GE_PROPERTY (
    NAME_ varchar(64),
    VALUE_ varchar(300),
    REV_ integer,
    primary key (NAME_)
);

insert into ACT_GE_PROPERTY
values ('schema.version', '5.20.0.2', 1);

insert into ACT_GE_PROPERTY
values ('schema.history', 'create(5.20.0.2)', 1);

insert into ACT_GE_PROPERTY
values ('next.dbid', '1', 1);

create table ACT_GE_BYTEARRAY (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    DEPLOYMENT_ID_ varchar(64),
    BYTES_ bytea,
    GENERATED_ boolean,
    primary key (ID_)
);

create table ACT_RE_DEPLOYMENT (
    ID_ varchar(64),
    NAME_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    DEPLOY_TIME_ timestamp,
    primary key (ID_)
);

create table ACT_RE_MODEL (
    ID_ varchar(64) not null,
    REV_ integer,
    NAME_ varchar(255),
    KEY_ varchar(255),
    CATEGORY_ varchar(255),
    CREATE_TIME_ timestamp,
    LAST_UPDATE_TIME_ timestamp,
    VERSION_ integer,
    META_INFO_ varchar(4000),
    DEPLOYMENT_ID_ varchar(64),
    EDITOR_SOURCE_VALUE_ID_ varchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_RU_EXECUTION (
    ID_ varchar(64),
    REV_ integer,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PARENT_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SUPER_EXEC_ varchar(64),
    ACT_ID_ varchar(255),
    IS_ACTIVE_ boolean,
    IS_CONCURRENT_ boolean,
    IS_SCOPE_ boolean,
    IS_EVENT_SCOPE_ boolean,
    SUSPENSION_STATE_ integer,
    CACHED_ENT_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    LOCK_TIME_ timestamp,
    primary key (ID_)
);

create table ACT_RU_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ boolean,
    EXECUTION_ID_ varchar(64),
    PROCESS_INSTANCE_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_RE_PROCDEF (
    ID_ varchar(64) NOT NULL,
    REV_ integer,
    CATEGORY_ varchar(255),
    NAME_ varchar(255),
    KEY_ varchar(255) NOT NULL,
    VERSION_ integer NOT NULL,
    DEPLOYMENT_ID_ varchar(64),
    RESOURCE_NAME_ varchar(4000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ varchar(4000),
    HAS_START_FORM_KEY_ boolean,
    HAS_GRAPHICAL_NOTATION_ boolean,
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_RU_TASK (
    ID_ varchar(64),
    REV_ integer,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    TASK_DEF_KEY_ varchar(255),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    DELEGATION_ varchar(64),
    PRIORITY_ integer,
    CREATE_TIME_ timestamp,
    DUE_DATE_ timestamp,
    CATEGORY_ varchar(255),
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    FORM_KEY_ varchar(255),
    primary key (ID_)
);

create table ACT_RU_IDENTITYLINK (
    ID_ varchar(64),
    REV_ integer,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar (64),
    primary key (ID_)
);

create table ACT_RU_VARIABLE (
    ID_ varchar(64) not null,
    REV_ integer,
    TYPE_ varchar(255) not null,
    NAME_ varchar(255) not null,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    TASK_ID_ varchar(64),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double precision,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
);

create table ACT_RU_EVENT_SUBSCR (
    ID_ varchar(64) not null,
    REV_ integer,
    EVENT_TYPE_ varchar(255) not null,
    EVENT_NAME_ varchar(255),
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTIVITY_ID_ varchar(64),
    CONFIGURATION_ varchar(255),
    CREATED_ timestamp not null,
    PROC_DEF_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_EVT_LOG (
    LOG_NR_ SERIAL PRIMARY KEY,
    TYPE_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    TIME_STAMP_ timestamp not null,
    USER_ID_ varchar(255),
    DATA_ bytea,
    LOCK_OWNER_ varchar(255),
    LOCK_TIME_ timestamp null,
    IS_PROCESSED_ smallint default 0
);

create table ACT_PROCDEF_INFO (
	ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    REV_ integer,
    INFO_JSON_ID_ varchar(64),
    primary key (ID_)
);

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);
create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);
create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);
create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);

create index ACT_IDX_BYTEAR_DEPL on ACT_GE_BYTEARRAY(DEPLOYMENT_ID_);




create index ACT_IDX_EXE_PROCINST on ACT_RU_EXECUTION(PROC_INST_ID_);


create index ACT_IDX_EXE_PARENT on ACT_RU_EXECUTION(PARENT_ID_);


create index ACT_IDX_EXE_SUPER on ACT_RU_EXECUTION(SUPER_EXEC_);


create index ACT_IDX_EXE_PROCDEF on ACT_RU_EXECUTION(PROC_DEF_ID_);


create index ACT_IDX_TSKASS_TASK on ACT_RU_IDENTITYLINK(TASK_ID_);


create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);


create index ACT_IDX_IDL_PROCINST on ACT_RU_IDENTITYLINK(PROC_INST_ID_);


create index ACT_IDX_TASK_EXEC on ACT_RU_TASK(EXECUTION_ID_);


create index ACT_IDX_TASK_PROCINST on ACT_RU_TASK(PROC_INST_ID_);


create index ACT_IDX_TASK_PROCDEF on ACT_RU_TASK(PROC_DEF_ID_);


create index ACT_IDX_VAR_EXE on ACT_RU_VARIABLE(EXECUTION_ID_);


create index ACT_IDX_VAR_PROCINST on ACT_RU_VARIABLE(PROC_INST_ID_);


create index ACT_IDX_VAR_BYTEARRAY on ACT_RU_VARIABLE(BYTEARRAY_ID_);


create index ACT_IDX_JOB_EXCEPTION on ACT_RU_JOB(EXCEPTION_STACK_ID_);


create index ACT_IDX_EVENT_SUBSCR on ACT_RU_EVENT_SUBSCR(EXECUTION_ID_);


create index ACT_IDX_MODEL_SOURCE on ACT_RE_MODEL(EDITOR_SOURCE_VALUE_ID_);


create index ACT_IDX_MODEL_SOURCE_EXTRA on ACT_RE_MODEL(EDITOR_SOURCE_EXTRA_VALUE_ID_);


create index ACT_IDX_MODEL_DEPLOYMENT on ACT_RE_MODEL(DEPLOYMENT_ID_);


create index ACT_IDX_PROCDEF_INFO_JSON on ACT_PROCDEF_INFO(INFO_JSON_ID_);


create index ACT_IDX_PROCDEF_INFO_PROC on ACT_PROCDEF_INFO(PROC_DEF_ID_);


alter table ACT_PROCDEF_INFO
    add constraint ACT_UNIQ_INFO_PROCDEF
    unique (PROC_DEF_ID_);

create table ACT_HI_PROCINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    BUSINESS_KEY_ varchar(255),
    PROC_DEF_ID_ varchar(64) not null,
    START_TIME_ timestamp not null,
    END_TIME_ timestamp,
    DURATION_ bigint,
    START_USER_ID_ varchar(255),
    START_ACT_ID_ varchar(255),
    END_ACT_ID_ varchar(255),
    SUPER_PROCESS_INSTANCE_ID_ varchar(64),
    DELETE_REASON_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    primary key (ID_),
    unique (PROC_INST_ID_)
);

create table ACT_HI_ACTINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    EXECUTION_ID_ varchar(64) not null,
    ACT_ID_ varchar(255) not null,
    TASK_ID_ varchar(64),
    CALL_PROC_INST_ID_ varchar(64),
    ACT_NAME_ varchar(255),
    ACT_TYPE_ varchar(255) not null,
    ASSIGNEE_ varchar(255),
    START_TIME_ timestamp not null,
    END_TIME_ timestamp,
    DURATION_ bigint,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_HI_TASKINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_KEY_ varchar(255),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ timestamp not null,
    CLAIM_TIME_ timestamp,
    END_TIME_ timestamp,
    DURATION_ bigint,
    DELETE_REASON_ varchar(4000),
    PRIORITY_ integer,
    DUE_DATE_ timestamp,
    FORM_KEY_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
);

create table ACT_HI_VARINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(100),
    REV_ integer,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double precision,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    CREATE_TIME_ timestamp,
    LAST_UPDATED_TIME_ timestamp,
    primary key (ID_)
);

create table ACT_HI_DETAIL (
    ID_ varchar(64) not null,
    TYPE_ varchar(255) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    ACT_INST_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(64),
    REV_ integer,
    TIME_ timestamp not null,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double precision,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
);

create table ACT_HI_COMMENT (
    ID_ varchar(64) not null,
    TYPE_ varchar(255),
    TIME_ timestamp not null,
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTION_ varchar(255),
    MESSAGE_ varchar(4000),
    FULL_MSG_ bytea,
    primary key (ID_)
);

create table ACT_HI_ATTACHMENT (
    ID_ varchar(64) not null,
    REV_ integer,
    USER_ID_ varchar(255),
    NAME_ varchar(255),
    DESCRIPTION_ varchar(4000),
    TYPE_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    URL_ varchar(4000),
    CONTENT_ID_ varchar(64),
    TIME_ timestamp,
    primary key (ID_)
);

create table ACT_HI_IDENTITYLINK (
    ID_ varchar(64),
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    primary key (ID_)
);


create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST(END_TIME_);
create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST(BUSINESS_KEY_);
create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST(START_TIME_);
create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST(END_TIME_);
create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL(PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL(ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL(TIME_);
create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL(NAME_);
create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL(TASK_ID_);
create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST(PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST(NAME_, VAR_TYPE_);
create index ACT_IDX_HI_PROCVAR_TASK_ID on ACT_HI_VARINST(TASK_ID_);
create index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST(PROC_INST_ID_, ACT_ID_);
create index ACT_IDX_HI_ACT_INST_EXEC on ACT_HI_ACTINST(EXECUTION_ID_, ACT_ID_);
create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK(USER_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK(TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK(PROC_INST_ID_);
create index ACT_IDX_HI_TASK_INST_PROCINST on ACT_HI_TASKINST(PROC_INST_ID_);

create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
);

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
);

create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    primary key (ID_)
);

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ bytea,
    PARENT_ID_ varchar(255),
    primary key (ID_)
);

create index ACT_IDX_MEMB_GROUP on ACT_ID_MEMBERSHIP(GROUP_ID_);

create index ACT_IDX_MEMB_USER on ACT_ID_MEMBERSHIP(USER_ID_);




COMMENT ON TABLE act_evt_log IS '事件日志表';
COMMENT ON COLUMN act_evt_log.LOG_NR_ IS 'ID';
COMMENT ON COLUMN act_evt_log.TYPE_ IS '日志类型';
COMMENT ON COLUMN act_evt_log.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_evt_log.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_evt_log.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_evt_log.TASK_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_evt_log.TIME_STAMP_ IS '创建时间';
COMMENT ON COLUMN act_evt_log.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_evt_log.DATA_ IS '日志内容';
COMMENT ON COLUMN act_evt_log.LOCK_OWNER_ IS '锁定者';
COMMENT ON COLUMN act_evt_log.LOCK_TIME_ IS '锁定时间';
COMMENT ON COLUMN act_evt_log.IS_PROCESSED_ IS '是否已处理';


COMMENT ON TABLE act_ge_bytearray IS '流程定义和流程资源';
COMMENT ON COLUMN act_ge_bytearray.ID_ IS 'ID';
COMMENT ON COLUMN act_ge_bytearray.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ge_bytearray.NAME_ IS '部署的文件名称';
COMMENT ON COLUMN act_ge_bytearray.DEPLOYMENT_ID_ IS '部署的ID';
COMMENT ON COLUMN act_ge_bytearray.BYTES_ IS '二进制字节';
COMMENT ON COLUMN act_ge_bytearray.GENERATED_ IS '是否是引擎生成(0,用户;1,ACTIVITI)';


COMMENT ON TABLE act_ge_property IS '系统通用参数表';
COMMENT ON COLUMN act_ge_property.NAME_ IS '属性名称';
COMMENT ON COLUMN act_ge_property.VALUE_ IS '属性值';
COMMENT ON COLUMN act_ge_property.REV_ IS '乐观锁版本';


COMMENT ON TABLE act_hi_actinst IS '节点实例历史表';
COMMENT ON COLUMN act_hi_actinst.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_actinst.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_hi_actinst.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_actinst.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_hi_actinst.ACT_ID_ IS '流程节点ID';
COMMENT ON COLUMN act_hi_actinst.TASK_ID_ IS '任务实例ID';
COMMENT ON COLUMN act_hi_actinst.CALL_PROC_INST_ID_ IS '调用外部的流程实例ID';
COMMENT ON COLUMN act_hi_actinst.ACT_NAME_ IS '节点名称';
COMMENT ON COLUMN act_hi_actinst.ACT_TYPE_ IS '节点类型';
COMMENT ON COLUMN act_hi_actinst.ASSIGNEE_ IS '签收的参与者';
COMMENT ON COLUMN act_hi_actinst.START_TIME_ IS '开始时间';
COMMENT ON COLUMN act_hi_actinst.END_TIME_ IS '结束时间';
COMMENT ON COLUMN act_hi_actinst.DURATION_ IS '耗时（毫秒）';
COMMENT ON COLUMN act_hi_actinst.TENANT_ID_ IS '系统标识ID';


COMMENT ON TABLE act_hi_attachment IS '附件历史表';
COMMENT ON COLUMN act_hi_attachment.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_attachment.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_hi_attachment.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_hi_attachment.NAME_ IS '名称';
COMMENT ON COLUMN act_hi_attachment.DESCRIPTION_ IS '描述';
COMMENT ON COLUMN act_hi_attachment.TYPE_ IS '附件类型';
COMMENT ON COLUMN act_hi_attachment.TASK_ID_ IS '节点类型ID';
COMMENT ON COLUMN act_hi_attachment.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_attachment.URL_ IS '附件地址';
COMMENT ON COLUMN act_hi_attachment.CONTENT_ID_ IS '二进制字节表ID';
COMMENT ON COLUMN act_hi_attachment.TIME_ IS '创建时间';


COMMENT ON TABLE act_hi_comment IS '审批意见历史表';
COMMENT ON COLUMN act_hi_comment.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_comment.TYPE_ IS '类型';
COMMENT ON COLUMN act_hi_comment.TIME_ IS '填写时间';
COMMENT ON COLUMN act_hi_comment.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_hi_comment.TASK_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_hi_comment.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_comment.ACTION_ IS '行为类型';
COMMENT ON COLUMN act_hi_comment.MESSAGE_ IS '基本内容';
COMMENT ON COLUMN act_hi_comment.FULL_MSG_ IS '附件内容';


COMMENT ON TABLE act_hi_detail IS '表单变量历史数据表';
COMMENT ON COLUMN act_hi_detail.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_detail.TYPE_ IS '类型';
COMMENT ON COLUMN act_hi_detail.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_detail.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_hi_detail.TASK_ID_ IS '任务实例ID';
COMMENT ON COLUMN act_hi_detail.ACT_INST_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_hi_detail.NAME_ IS '名称';
COMMENT ON COLUMN act_hi_detail.VAR_TYPE_ IS '参数类型';
COMMENT ON COLUMN act_hi_detail.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_hi_detail.TIME_ IS '创建时间';
COMMENT ON COLUMN act_hi_detail.BYTEARRAY_ID_ IS '二进制字节ID';
COMMENT ON COLUMN act_hi_detail.DOUBLE_ IS '双精度浮点值';
COMMENT ON COLUMN act_hi_detail.LONG_ IS '长整型值';
COMMENT ON COLUMN act_hi_detail.TEXT_ IS '字符串';
COMMENT ON COLUMN act_hi_detail.TEXT2_ IS 'JPA持久化对象ID';


COMMENT ON TABLE act_hi_identitylink IS '流程实例任务参与者历史表';
COMMENT ON COLUMN act_hi_identitylink.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_identitylink.GROUP_ID_ IS '用户组ID';
COMMENT ON COLUMN act_hi_identitylink.TYPE_ IS '用户组类型';
COMMENT ON COLUMN act_hi_identitylink.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_hi_identitylink.TASK_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_hi_identitylink.PROC_INST_ID_ IS '流程实例ID';


COMMENT ON TABLE act_hi_procinst IS '流程运行实例历史表';
COMMENT ON COLUMN act_hi_procinst.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_procinst.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_procinst.BUSINESS_KEY_ IS '业务主键';
COMMENT ON COLUMN act_hi_procinst.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_hi_procinst.START_TIME_ IS '开始时间';
COMMENT ON COLUMN act_hi_procinst.END_TIME_ IS '结束时间';
COMMENT ON COLUMN act_hi_procinst.DURATION_ IS '耗时';
COMMENT ON COLUMN act_hi_procinst.START_USER_ID_ IS '流程发起人';
COMMENT ON COLUMN act_hi_procinst.START_ACT_ID_ IS '开始节点ID';
COMMENT ON COLUMN act_hi_procinst.END_ACT_ID_ IS '结束节点ID';
COMMENT ON COLUMN act_hi_procinst.SUPER_PROCESS_INSTANCE_ID_ IS '父流程实例ID';
COMMENT ON COLUMN act_hi_procinst.DELETE_REASON_ IS '删除原因';
COMMENT ON COLUMN act_hi_procinst.TENANT_ID_ IS '系统标识ID';
COMMENT ON COLUMN act_hi_procinst.NAME_ IS '系统标识名称';


COMMENT ON TABLE act_hi_taskinst IS '任务实例历史表';
COMMENT ON COLUMN act_hi_taskinst.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_taskinst.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_hi_taskinst.TASK_DEF_KEY_ IS '节点定义主键';
COMMENT ON COLUMN act_hi_taskinst.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_taskinst.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_hi_taskinst.NAME_ IS '名称';
COMMENT ON COLUMN act_hi_taskinst.PARENT_TASK_ID_ IS '父节点实例ID';
COMMENT ON COLUMN act_hi_taskinst.DESCRIPTION_ IS '描述';
COMMENT ON COLUMN act_hi_taskinst.OWNER_ IS '任务所有者';
COMMENT ON COLUMN act_hi_taskinst.ASSIGNEE_ IS '签收人或被委托人';
COMMENT ON COLUMN act_hi_taskinst.START_TIME_ IS '开始时间';
COMMENT ON COLUMN act_hi_taskinst.CLAIM_TIME_ IS '提醒时间';
COMMENT ON COLUMN act_hi_taskinst.END_TIME_ IS '结束时间';
COMMENT ON COLUMN act_hi_taskinst.DURATION_ IS '耗时';
COMMENT ON COLUMN act_hi_taskinst.DELETE_REASON_ IS '删除原因';
COMMENT ON COLUMN act_hi_taskinst.PRIORITY_ IS '优先级';
COMMENT ON COLUMN act_hi_taskinst.DUE_DATE_ IS '过期时间';
COMMENT ON COLUMN act_hi_taskinst.FORM_KEY_ IS '表单主键';
COMMENT ON COLUMN act_hi_taskinst.CATEGORY_ IS '分类';
COMMENT ON COLUMN act_hi_taskinst.TENANT_ID_ IS '系统标识';


COMMENT ON TABLE act_hi_varinst IS '流程变量历史数据表';
COMMENT ON COLUMN act_hi_varinst.ID_ IS 'ID';
COMMENT ON COLUMN act_hi_varinst.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_hi_varinst.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_hi_varinst.TASK_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_hi_varinst.NAME_ IS '名称';
COMMENT ON COLUMN act_hi_varinst.VAR_TYPE_ IS '参数类型';
COMMENT ON COLUMN act_hi_varinst.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_hi_varinst.BYTEARRAY_ID_ IS '二进制字节ID';
COMMENT ON COLUMN act_hi_varinst.DOUBLE_ IS '双精度值';
COMMENT ON COLUMN act_hi_varinst.LONG_ IS '长整数值';
COMMENT ON COLUMN act_hi_varinst.TEXT_ IS '字符串值';
COMMENT ON COLUMN act_hi_varinst.TEXT2_ IS 'JPA持久化对象ID';
COMMENT ON COLUMN act_hi_varinst.CREATE_TIME_ IS '创建时间';
COMMENT ON COLUMN act_hi_varinst.LAST_UPDATED_TIME_ IS '最新修改时间';


COMMENT ON TABLE act_id_group IS '用户组表';
COMMENT ON COLUMN act_id_group.ID_ IS 'ID';
COMMENT ON COLUMN act_id_group.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_id_group.NAME_ IS '名称';
COMMENT ON COLUMN act_id_group.TYPE_ IS '类型';


COMMENT ON TABLE act_id_info IS '用户扩展信息表';
COMMENT ON COLUMN act_id_info.ID_ IS 'ID';
COMMENT ON COLUMN act_id_info.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_id_info.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_id_info.TYPE_ IS '类型';
COMMENT ON COLUMN act_id_info.KEY_ IS '扩展键';
COMMENT ON COLUMN act_id_info.VALUE_ IS '扩展值';
COMMENT ON COLUMN act_id_info.PASSWORD_ IS '密码';
COMMENT ON COLUMN act_id_info.PARENT_ID_ IS '父信息ID';


COMMENT ON TABLE act_id_membership IS '用户和用户组关联表';
COMMENT ON COLUMN act_id_membership.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_id_membership.GROUP_ID_ IS '用户组ID';


COMMENT ON TABLE act_id_user IS '用户表';
COMMENT ON COLUMN act_id_user.ID_ IS 'ID';
COMMENT ON COLUMN act_id_user.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_id_user.FIRST_ IS '名';
COMMENT ON COLUMN act_id_user.LAST_ IS '姓';
COMMENT ON COLUMN act_id_user.EMAIL_ IS '邮箱';
COMMENT ON COLUMN act_id_user.PWD_ IS '密码';
COMMENT ON COLUMN act_id_user.PICTURE_ID_ IS '图片ID';


COMMENT ON TABLE act_procdef_info IS '流程定义详情';
COMMENT ON COLUMN act_procdef_info.ID_ IS 'ID';
COMMENT ON COLUMN act_procdef_info.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_procdef_info.REV_ IS '乐观锁';
COMMENT ON COLUMN act_procdef_info.INFO_JSON_ID_ IS 'INFO_JSON_ID_';


COMMENT ON TABLE act_re_deployment IS '流程部署表';
COMMENT ON COLUMN act_re_deployment.NAME_ IS '部署名称';
COMMENT ON COLUMN act_re_deployment.CATEGORY_ IS '分类';
COMMENT ON COLUMN act_re_deployment.TENANT_ID_ IS '系统标识';
COMMENT ON COLUMN act_re_deployment.DEPLOY_TIME_ IS '部署时间';


COMMENT ON TABLE act_re_model IS '定义模型信息';
COMMENT ON COLUMN act_re_model.ID_ IS 'ID';
COMMENT ON COLUMN act_re_model.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_re_model.NAME_ IS '模型名称';
COMMENT ON COLUMN act_re_model.KEY_ IS '业务主键';
COMMENT ON COLUMN act_re_model.CATEGORY_ IS '分类';
COMMENT ON COLUMN act_re_model.CREATE_TIME_ IS '创建时间';
COMMENT ON COLUMN act_re_model.LAST_UPDATE_TIME_ IS '最新修改时间';
COMMENT ON COLUMN act_re_model.VERSION_ IS '版本号';
COMMENT ON COLUMN act_re_model.META_INFO_ IS '数据源信息';
COMMENT ON COLUMN act_re_model.DEPLOYMENT_ID_ IS '部署ID';
COMMENT ON COLUMN act_re_model.EDITOR_SOURCE_VALUE_ID_ IS '编辑器源码值ID';
COMMENT ON COLUMN act_re_model.EDITOR_SOURCE_EXTRA_VALUE_ID_ IS '编辑源码值外值ID';
COMMENT ON COLUMN act_re_model.TENANT_ID_ IS '系统标识';


COMMENT ON TABLE act_re_procdef IS '流程定义表';
COMMENT ON COLUMN act_re_procdef.ID_ IS 'ID';
COMMENT ON COLUMN act_re_procdef.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_re_procdef.CATEGORY_ IS '分类';
COMMENT ON COLUMN act_re_procdef.NAME_ IS '名称';
COMMENT ON COLUMN act_re_procdef.KEY_ IS '业务键';
COMMENT ON COLUMN act_re_procdef.VERSION_ IS '版本号';
COMMENT ON COLUMN act_re_procdef.DEPLOYMENT_ID_ IS '部署ID';
COMMENT ON COLUMN act_re_procdef.RESOURCE_NAME_ IS 'BPMN资源名称';
COMMENT ON COLUMN act_re_procdef.DGRM_RESOURCE_NAME_ IS 'PNG图片名称';
COMMENT ON COLUMN act_re_procdef.DESCRIPTION_ IS '描述';
COMMENT ON COLUMN act_re_procdef.HAS_START_FORM_KEY_ IS '是否存在开始节点表单键';
COMMENT ON COLUMN act_re_procdef.HAS_GRAPHICAL_NOTATION_ IS '是否图表标识';
COMMENT ON COLUMN act_re_procdef.SUSPENSION_STATE_ IS '是否挂起';
COMMENT ON COLUMN act_re_procdef.TENANT_ID_ IS '系统标识';


COMMENT ON TABLE act_ru_event_subscr IS '事件订阅表';
COMMENT ON COLUMN act_ru_event_subscr.ID_ IS 'ID';
COMMENT ON COLUMN act_ru_event_subscr.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ru_event_subscr.EVENT_TYPE_ IS '事件类型';
COMMENT ON COLUMN act_ru_event_subscr.EVENT_NAME_ IS '事件名称';
COMMENT ON COLUMN act_ru_event_subscr.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_ru_event_subscr.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_ru_event_subscr.ACTIVITY_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_ru_event_subscr.CONFIGURATION_ IS '配置';
COMMENT ON COLUMN act_ru_event_subscr.CREATED_ IS '创建时间默认系统时间戳';
COMMENT ON COLUMN act_ru_event_subscr.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_ru_event_subscr.TENANT_ID_ IS '系统标识';


COMMENT ON TABLE act_ru_execution IS '流程执行记录表';
COMMENT ON COLUMN act_ru_execution.ID_ IS 'ID';
COMMENT ON COLUMN act_ru_execution.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ru_execution.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_ru_execution.BUSINESS_KEY_ IS '业务键';
COMMENT ON COLUMN act_ru_execution.PARENT_ID_ IS '父节点实例ID';
COMMENT ON COLUMN act_ru_execution.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_ru_execution.SUPER_EXEC_ IS '父执行ID';
COMMENT ON COLUMN act_ru_execution.ACT_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_ru_execution.IS_ACTIVE_ IS '是否激活';
COMMENT ON COLUMN act_ru_execution.IS_CONCURRENT_ IS '是否并行';
COMMENT ON COLUMN act_ru_execution.IS_SCOPE_ IS 'IS_SCOPE_';
COMMENT ON COLUMN act_ru_execution.IS_EVENT_SCOPE_ IS 'IS_EVENT_SCOPE_';
COMMENT ON COLUMN act_ru_execution.SUSPENSION_STATE_ IS '暂停状态';
COMMENT ON COLUMN act_ru_execution.CACHED_ENT_STATE_ IS '缓存环境状态';
COMMENT ON COLUMN act_ru_execution.TENANT_ID_ IS '系统标识';
COMMENT ON COLUMN act_ru_execution.NAME_ IS '系统名称';
COMMENT ON COLUMN act_ru_execution.LOCK_TIME_ IS '锁定时间';


COMMENT ON TABLE act_ru_identitylink IS '任务参与者关联表';
COMMENT ON COLUMN act_ru_identitylink.ID_ IS 'ID';
COMMENT ON COLUMN act_ru_identitylink.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ru_identitylink.GROUP_ID_ IS '用户组ID';
COMMENT ON COLUMN act_ru_identitylink.TYPE_ IS '类型';
COMMENT ON COLUMN act_ru_identitylink.USER_ID_ IS '用户ID';
COMMENT ON COLUMN act_ru_identitylink.TASK_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_ru_identitylink.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_ru_identitylink.PROC_DEF_ID_ IS '流程定义ID';


COMMENT ON TABLE act_ru_job IS '定时任务表';
COMMENT ON COLUMN act_ru_job.ID_ IS 'ID';
COMMENT ON COLUMN act_ru_job.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ru_job.TYPE_ IS '类型';
COMMENT ON COLUMN act_ru_job.LOCK_EXP_TIME_ IS '锁定释放时间';
COMMENT ON COLUMN act_ru_job.LOCK_OWNER_ IS '锁定者';
COMMENT ON COLUMN act_ru_job.EXCLUSIVE_ IS '是否排他';
COMMENT ON COLUMN act_ru_job.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_ru_job.PROCESS_INSTANCE_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_ru_job.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_ru_job.RETRIES_ IS '重试次数';
COMMENT ON COLUMN act_ru_job.EXCEPTION_STACK_ID_ IS '异常信息ID';
COMMENT ON COLUMN act_ru_job.EXCEPTION_MSG_ IS '异常信息';
COMMENT ON COLUMN act_ru_job.DUEDATE_ IS '到期时间';
COMMENT ON COLUMN act_ru_job.REPEAT_ IS '重复';
COMMENT ON COLUMN act_ru_job.HANDLER_TYPE_ IS '处理类型';
COMMENT ON COLUMN act_ru_job.HANDLER_CFG_ IS '处理标识';
COMMENT ON COLUMN act_ru_job.TENANT_ID_ IS '系统标识';


COMMENT ON TABLE act_ru_task IS '流程任务表';
COMMENT ON COLUMN act_ru_task.ID_ IS 'ID';
COMMENT ON COLUMN act_ru_task.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ru_task.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_ru_task.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_ru_task.PROC_DEF_ID_ IS '流程定义ID';
COMMENT ON COLUMN act_ru_task.NAME_ IS '节点定义名称';
COMMENT ON COLUMN act_ru_task.PARENT_TASK_ID_ IS '父节点实例ID';
COMMENT ON COLUMN act_ru_task.DESCRIPTION_ IS '节点定义描述';
COMMENT ON COLUMN act_ru_task.TASK_DEF_KEY_ IS '节点定义的业务键';
COMMENT ON COLUMN act_ru_task.OWNER_ IS '任务所有者';
COMMENT ON COLUMN act_ru_task.ASSIGNEE_ IS '签收人或委托人';
COMMENT ON COLUMN act_ru_task.DELEGATION_ IS '委托类型';
COMMENT ON COLUMN act_ru_task.PRIORITY_ IS '优先级别';
COMMENT ON COLUMN act_ru_task.CREATE_TIME_ IS '创建时间';
COMMENT ON COLUMN act_ru_task.DUE_DATE_ IS '过期时间';
COMMENT ON COLUMN act_ru_task.CATEGORY_ IS '分类';
COMMENT ON COLUMN act_ru_task.SUSPENSION_STATE_ IS '挂起状态';
COMMENT ON COLUMN act_ru_task.TENANT_ID_ IS '系统标识';
COMMENT ON COLUMN act_ru_task.FORM_KEY_ IS '表单业务键';


COMMENT ON TABLE act_ru_variable IS '流程变量表';
COMMENT ON COLUMN act_ru_variable.ID_ IS 'ID';
COMMENT ON COLUMN act_ru_variable.REV_ IS '乐观锁版本';
COMMENT ON COLUMN act_ru_variable.TYPE_ IS '类型';
COMMENT ON COLUMN act_ru_variable.NAME_ IS '名称';
COMMENT ON COLUMN act_ru_variable.EXECUTION_ID_ IS '执行实例ID';
COMMENT ON COLUMN act_ru_variable.PROC_INST_ID_ IS '流程实例ID';
COMMENT ON COLUMN act_ru_variable.TASK_ID_ IS '节点实例ID';
COMMENT ON COLUMN act_ru_variable.BYTEARRAY_ID_ IS '二进制字节ID';
COMMENT ON COLUMN act_ru_variable.DOUBLE_ IS '双精度值';
COMMENT ON COLUMN act_ru_variable.LONG_ IS '长整数值';
COMMENT ON COLUMN act_ru_variable.TEXT_ IS '字符串值';
COMMENT ON COLUMN act_ru_variable.TEXT2_ IS 'JPA对象ID';

CREATE INDEX idx_act_hi_taskinst_assignee_ ON act_hi_taskinst  (assignee_);
CREATE INDEX idx_act_hi_taskinst_endtime_ ON act_hi_taskinst  (end_time_);
CREATE INDEX idx_act_ru_task_assignee_ ON act_ru_task  (assignee_);
CREATE INDEX act_ru_task_rev ON act_ru_task  (rev_);