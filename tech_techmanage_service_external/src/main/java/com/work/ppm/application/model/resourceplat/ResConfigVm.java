package com.work.ppm.application.model.resourceplat;

import lombok.Data;

import java.util.Date;

/**
 * 虚拟机资源报价
 */
@Data
public class ResConfigVm {

    /**
     *	主键
     **/
    private String id;

    /**
     *	类型：1cpu、2内存、3硬盘
     **/
    private Integer vmtype;

    /**
     *	配置：默认1G，不可修改
     **/
    private String describe;

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
