package com.work.ppm.application.model.resourceplat;

import lombok.Data;

import java.util.Date;

/**
 * 软件版本报价
 */
@Data
public class ResConfigSoft {
    /**
     *	主键
     **/
    private String id;

    /**
     *	分类
     **/
    private String type;

    /**
     *	子项
     **/
    private String subtype;

    /**
     *	软件名称
     **/
    private String softname;

    /**
     *	版本号
     **/
    private String softversion;

    /**
     *	计费模式
     **/
    private String billingmode;

    /**
     *	价格（元）
     **/
    private String price;



    /**
     *	状态; 1新建、2已发布、3作废、4删除
     **/
    private String status;

    /**
     *	创建人
     **/
    private String creator;

    /**
     *	创建时间
     **/
    private Date createtime;

    /**
     *	作废标识
     **/
    private String iscancel;
}
