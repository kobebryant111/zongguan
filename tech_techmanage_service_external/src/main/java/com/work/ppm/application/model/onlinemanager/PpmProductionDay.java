package com.work.ppm.application.model.onlinemanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
  * 描述
  */
@Data
public class PpmProductionDay {
    /**
     *	ID
     **/
    @Id
    private Long id;
    /**
     *	子项目编号
     **/
    private String year;
    /**
     *	上线日期
     **/
    private String productdate;
    /**
     *	是否删除
     **/
    private Integer isdel;
    /**
     *	创建时间
     **/
    private Date createtime;
    /**
     *	创建者
     **/
    private String creators;
    /**
     *	更新时间
     **/
    private Date lastmodtime;
    /**
     *	更新者
     **/
    private String lastmoduser;
    /**
     *	字段名
     **/
    private String risktype;
}
