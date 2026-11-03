package com.tansun.wf.decision.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class StandardDataSource {

	/**
	 * 数据源key
	 */
	private String dataSource;

	/**
	 * 数据源名称
	 */
	private String name;
	
	/**
	 * 可接受的缓存天数
	 */
	private String dataSourceDay = "0";
	
	/**
	 * 本地数据源bean
	 */
	private String beanName;

	/**
	 * 标准参数与动态参数映射
	 */
	private List<TirdPartyParam> params;

	public String getDataSource() {
		return dataSource;
	}

	public String getName() {
		return name;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataSourceDay() {
		return dataSourceDay;
	}

	public void setDataSourceDay(String dataSourceDay) {
		this.dataSourceDay = dataSourceDay;
	}

	public List<TirdPartyParam> getParams() {
		return params;
	}

	public void setParams(List<TirdPartyParam> params) {
		this.params = params;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}
