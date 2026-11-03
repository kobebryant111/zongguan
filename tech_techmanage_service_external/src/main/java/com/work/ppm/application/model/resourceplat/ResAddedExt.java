package com.work.ppm.application.model.resourceplat;

import lombok.Data;

import java.util.Date;

/**
 * iaas资源新增项-扩展信息
 */
@Data
public class ResAddedExt {
    /**
     *	主键
     **/
    private String id;

    /**
     *	addedItemId
     **/
    private String addedItemId;

    /**
     *	机房
     **/
    private String machineRoom;

    /**
     *	机柜
     **/
    private String cabinet;

    /**
     *	机柜位
     **/
    private String cabinetPos;

    /**
     *	系列号(序列号) 系统部-填写
     **/
    private String sn;

    /**
     * F5 IP
     **/
    private String f5ip;

    /**
     * 本地IP
     **/
    private String ip;

    /**
     * 服务IP
     **/
    private String serverIp;

    /**
     * SCANIP
     **/
    private String scanIp;

    /**
     * VCSIP
     **/
    private String vcsIp;

    /**
     * VIP
     **/
    private String vip;

    /**
     *	VLAN
     **/
    private String vlan;

    /**
     *	GATEWAY
     **/
    private String gateway;

    /**
     *	MASK
     **/
    private String mask;

    /**
     *	CREATETIME
     **/
    private Date createTime;

    /**
     * UPDATETIME
     **/
    private Date updateTime;

}
