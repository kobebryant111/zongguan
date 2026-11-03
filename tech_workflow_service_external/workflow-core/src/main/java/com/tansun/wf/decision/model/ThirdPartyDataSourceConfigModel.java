package com.tansun.wf.decision.model;

import java.util.List;

/**
 * 第三方数据源
 */
public class ThirdPartyDataSourceConfigModel {

    /**
     * 接口标识
     */
    private String dataSource;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口处理类
     */
    private String beanName;

    /**
     * 远程地址
     */
    private String url;

    /**
     * 默认远程地址
     */
    private String defaultUrl;

    private List<TirdPartyParam> params;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public List<TirdPartyParam> getParams() {
        return params;
    }

    public void setParams(List<TirdPartyParam> params) {
        this.params = params;
    }
}
