package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmDocumentCatalog {
	/**
	*	??PPM_DOCUMENT_CATALOG_ID ??EOS?????????
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String catalogno;
	/**
	*	字段名
	**/
	private String pno;
	/**
	*	字段名
	**/
	private String catalogname;
	/**
	*	字段名
	**/
	private String stockpath;
	/**
	*	1-????0-???
	**/
	private Long isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
	/**
	*	字段名
	**/
	private String depono;
}
