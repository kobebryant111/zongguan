package com.work.ppm.commons.exception;

/**
 * 异常处理
 */
public enum ExceptionEnum {

    /**
     *没有权限
     */
    NOT_PERMISSION("P001", "没有权限"),

    /**
     *没有职位
     */
    NOT_POSITION("P002", "职位不存在或者已删除"),

    /**
     * 密码错误
     */
    ERROR_PWD ("L001", "用户或密码错误"),

    /**
     * 密码错误
     */
    NOT_LOGIN ("L002", "未登录"),

    /**
     *token解析异常
     */
    ERROR_TOKEN ("T001", "token校验失败"),

    /**
     * 路径不存在
     */
    NOT_FOUND("404", "路径不存在"),

    /**
     * 申请案件不存在
     */
    NOT_APPLY_CASE_ID("AP001", "申请记录不存在"),

    /**
     * 不是节点的审批人
     */
    NOT_TASK_APPROVE_USER("AP002", "不是节点的审批人"),
    /**
     * 不是申请人
     */
    NOT_TASK_APPLY_USER("AP003", "不是申请人"),

    /**
     * 申请客户存在未完结案件或者审批中
     */
    EXISTS_CASE_ING("AP004", "申请客户存在未完结案件或者审批中"),

    /**
     * 申请数据案件状态不正确
     */
    CASE_STATUS_ERROR("AP005", "申请数据案件状态不正确"),

    /**
     * 客户没有未到期贷款
     */
    NOT_EXISTS_CUST_LOAN("AP005", "客户没有未到期贷款"),

    /**
     * 没有申请权限
     */
    NOT_CASE_APPLY("AP006", "没有申请权限"),

    /**
     * 存在诉讼中的案件
     */
    EXISTS_INDICT_CASE("AP007", "存在诉讼中的案件"),

    /**
     *记录不存在
     */
    RECORD_NOT_EXISTS("406", "记录不存在"),


    /**
     *token不能为空
     */
    NULL_TOKEN ("T002", "token不能为空"),

    /**
     * 没有配置esb交易码
     */
    NOT_SET_ESB_CONFIG ("ESB001","没有配置esb交易码"),

    /**
     * 正在异步下载中，稍后我的下载菜单进行下载......
     */
    DOWN_ING ("DW001","正在异步下载中，稍后我的下载菜单进行下载......"),

    /**
     * 数组字典配置错误
     */
    DICT_CONFIG_NOT_EXISTS("407","数组字典配置错误!");


    /**
     * 编码
     */
    private final String errorCode;

    /**
     * 信息
     */
    private final String errorMsg;

    /**
     * 构造函数
     *
     * @param errorCode 编码
     * @param errorMsg  信息
     */
    ExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 获取编码
     *
     * @return String 结果
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return String 结果
     */
    public String getErrorMsg() {
        return errorMsg;
    }
}
