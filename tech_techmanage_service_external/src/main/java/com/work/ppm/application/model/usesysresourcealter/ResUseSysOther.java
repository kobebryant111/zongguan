package com.work.ppm.application.model.usesysresourcealter;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;


/**
 * 应用系统变更附表
 */
@Data
public class ResUseSysOther extends ResUseSys {
   /**
   *	逻辑设备状态
   **/
   private String logicalresourcestats;
   /**
   *	物理设备状态
   **/
   private String physicalresourcestats;
   /**
   *	用户类型
   **/
   private String usertype;
   /**
   *	特例说明
   **/
   private String explain;
   /**
   *	序列号
   **/
   private String serialno;
   /**
   *	主键
   **/
   @Id
   private String id;
   /**
   *	动作：1新增,2删除,修改
   **/
   private String deloradd;
   /**
   *	资源名称
   **/
   private String ciname;
   /**
   *	本地ip
   **/
   private String localip;
   /**
   *	关联RES_USE_SYS的id
   **/
   private String resusesysid;
   /**
   *	资源分类
   **/
   private String ciclassifykey;
   /**
   *	操作系统版本
   **/
   private String osversion;

    /**
     *	删除标识
     **/
    private String isdelete;

    /**
     *	当前节点
     **/
    private String activitydefid;

    /**
     *	ciid
     **/
    private String ciid;
    /**
     * ciinstcondition
     */
    private String ciinstcondition;

}
