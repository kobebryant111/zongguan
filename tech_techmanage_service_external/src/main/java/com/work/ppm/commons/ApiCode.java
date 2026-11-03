package com.work.ppm.commons;

/**
 * 业务字典api 字段
 */
public enum ApiCode {
    /**
     * 正常
     */
    SUCCESS(200,"success"),
    /**
     * 为获取到数据
     */
    UPDATEMSGISNULL(305,"没有需要修改的数据!"),
    /**
     * 为获取到数据
     */
    ISNULL(300,"为获取到数据信息!"),
    /**
     * 业务异常
     */
    FAIL(5000,"业务异常，未正确捕获异常"),
    /**
     * 工号验证失败
     */
    BADGE_VERIFICATION_ERROR(5009, "工号验证失败"),
     /**
      * 参数错误
     */
    PARAMETER_ERROR(1001, "请求参数错误"),
    /**
     * 校验失败
     */
    ATTENDANCE_ERROR(100020,"校验失败"),

    /**
     * 内部错误
     */
    INTERNAL_SERVER_ERROR(1002, "internal server error"),

    /**
     * 创建用户时，姓名为空
     */
    USER_NAME_NULL_WHEN_CREATE(100001, "创建用户时，姓名为空"),

    /**
     * 创建用户时，调用外部依赖失败
     */
    CREATE_FAIL_EXT_CALL_FAIL(100001, "创建用户时，调用外部依赖失败"),
    /**
     * 更新失败
     */
    UPATE_FAIL(100002,"更新失败"),
    /**
     * 删除失败
     */
    DELETE_FAIL(100003,"删除失败"),
    /**
     * 账号或密码不正确
     */
    LOGIN_FAIL(100004,"账号或密码不正确"),
    /**
     * Excel导入失败
     */
    EXCEL_IMPORT_FAIL(100005, "Excel导入失败"),
    /**
     * 流程异常
     */
    BPS_WORKFLOW_FAIL(100006,"流程异常"),
    /**
     * 文件未授权
     */
    FILE_NOT_AUTHORIZED(100007, "文件未授权"),
    /**
     * 流程通知异常
     */
    FLOW_NOTICE_ERROR(100008,"流程通知异常"),
    /**
     * ESB调用失败
     */
    ESB_SERVER_TRANSFER_ERROR(100009, "ESB服务调用失败"),
    /**
     * 查询数据失败
     */
    SELECT_DATA_FAIL(100010, "查询数据失败"),
    /**
     * 时间转换失败
     */
    DATE_CONVERTER_ERROR(100011, "时间转换失败"),
    /**
     * 流程撤回失败
     */
    FLOW_WITHDRAW_FAIL(100012, "流程撤回失败"),
    /**
     * 采购系统失败
     */
    PROCUREMENT_SYSTEM_ERROR(100019,"采购系统失败"),
    /**
     * 流程流转失败
     */
    FLOW_CIRCULATION_FAAIL(100013,"流程流转失败"),
    /**
     * 考核返回0
     */
    KPI_RELATION_FAIL(100014,"考核关系错误"),
    /**
     * 考核返回2
     */
    KPI_NO_SUBMIT(100015,"没有可提交数据"),
    /**
     * 考核返回3
     */
    KPI_SCORE_NULL(100016,"评分或者评语为空"),
    /**
     * 考核返回4
     */
    KPI_STATUS_ERROR(100017,"状态不对"),
    /**
     * 登录日志采集失败
     */
    LOGIN_LOG_COLLECT(100018,"登录日志采集失败");

    /**
     * 编号
     */
    private int code;
    /**
     * 消息
     */
    private String msg;

    /**
     * 构造函数
     */
    ApiCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 查实体类
     * @param code  code
     * @return ApiCode
     */
    public static ApiCode getMethodByCode(int code) {
        for (ApiCode value : ApiCode.values()) {
            if(code == value.getCode()) {
                return value;
            }
        }
        return null;
    }

    public void setCode(int code) {

        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
