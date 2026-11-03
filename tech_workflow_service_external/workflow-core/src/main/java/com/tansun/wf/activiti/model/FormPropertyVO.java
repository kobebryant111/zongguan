package com.tansun.wf.activiti.model;

import java.io.Serializable;

public class FormPropertyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private   String   id;
	
	private   String   name;
	
	private   FormType  type;
	
	private   String   value;
	
	private   boolean   readable;
	
	private   boolean   writable;
	
	private   boolean   required;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public FormType getType() {
		return type;
	}

	public void setType(FormType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(boolean readable) {
		this.readable = readable;
	}

	public boolean isWritable() {
		return writable;
	}

	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	
	
	

}
