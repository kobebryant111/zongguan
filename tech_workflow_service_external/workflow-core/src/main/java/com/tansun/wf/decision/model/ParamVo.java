package com.tansun.wf.decision.model;

/**
 * 数据集中入参对象
 */
public class ParamVo {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数键值
     */
    private String paramKey;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 参数类型
     */
    private String paramType;

    /**
     * 是否集合
     */
    private String isCollections;

    /**
     * 参数输出类型
     */
    private String paramFor;

    /**
     * 表单映射字段
     */
    private FormDetailVo formDetail;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getIsCollections() {
        return isCollections;
    }

    public void setIsCollections(String isCollections) {
        this.isCollections = isCollections;
    }

    public String getParamFor() {
        return paramFor;
    }

    public void setParamFor(String paramFor) {
        this.paramFor = paramFor;

    }

    public FormDetailVo getFormDetail() {
        return formDetail;
    }

    public void setFormDetail(FormDetailVo formDetail) {
        this.formDetail = formDetail;
    }
}
