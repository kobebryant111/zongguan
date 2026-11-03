package com.tansun.workflow.model;

import java.io.Serializable;

public class ValueType implements Serializable {


	private static final long serialVersionUID = -4078375963225381041L;
	
	private   String   key;
	
	private   Object    value;
	
	private   Class     clazz = String.class;

	public ValueType() {
	}

	public ValueType(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public ValueType(String key, Object value, Class clazz) {
		this.key = key;
		this.value = value;
		this.clazz = clazz;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
