package com.work.ppm.application.model.resourceplat;

import lombok.Data;

import java.util.Date;

/**
 * 资源配置主表
 */
@Data
public class ResConfigCombo {

    /**
     *	主键
     **/
    private String id;

    /**
     *	套餐大类
     **/
    private String category;

    /**
     *	套餐小项
     **/
    private String subcategory;

    /**
     *	适用场景
     **/
    private String applyscene;

    /**
     *	资源配置
     **/
    private String describe;

    /**
     *	cpu数量（块）
     **/
    private Integer cpunum;

    /**
     *	cpu核数（C）
     **/
    private Integer cpucore;

    /**
     *	主频（GHz）
     **/
    private String frequency;

    /**
     *	内存（G）
     **/
    private Integer memory;

    /**
     *	系统盘（G）数据库表空间（T）
     **/
    private Integer diskcapacity;

    /**
     *	磁盘数量1（块）
     **/
    private Integer disknum1;

    /**
     *	磁盘配置1（G）
     **/
    private String diskconfig1;

    /**
     *	磁盘数量2（块）
     **/
    private Integer disknum2;

    /**
     *	磁盘配置2（G）
     **/
    private String diskconfig2;

    /**
     *	磁盘数量3（块）
     **/
    private Integer disknum3;

    /**
     *	磁盘配置3（G）
     **/
    private String diskconfig3;

    /**
     *	磁盘配置id1
     **/
    private String diskconfigid1;

    /**
     *	磁盘配置id2
     **/
    private String diskconfigid2;

    /**
     *	磁盘配置id3
     **/
    private String diskconfigid3;

    /**
     *	万兆网卡数量（块）
     **/
    private Integer netcardnum;

    /**
     *	光纤卡HBA数量（块）
     **/
    private Integer fibercardnum;

    /**
     *	GPU数量（块）
     **/
    private Integer gpunum;

    /**
     *	GPU型号
     **/
    private String gputype;

    /**
     *	GPU卡ID
     **/
    private String gpuid;

    /**
     *	系统运维等级
     **/
    private String devopslevel;

    /**
     *	是否涉及资金交易
     **/
    private String isrelatefund;

    /**
     *	是否专用 0否;针对：redis+虚拟机,其他+专用设备
     **/
    private String isspecial;

    /**
     *	价格（元）
     **/
    private String price;

    /**
     *	状态; 1新建、2已发布、3作废、4删除
     **/
    private String status;

    /**
     *	比例
     **/
    private String ratio;

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
