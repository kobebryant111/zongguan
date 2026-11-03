package com.work.ppm.application.dto.kpi;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 描述
 */
@Data
public class PpmKpiLevelPersonPageShowDto {
   /**
   *	主键
   **/
   private Long id;
   /**
   *	部门
   **/
   private String depid;
   /**
   *	中心
   **/
   private String centerid;
   /**
   *	类型（1：二级部，2：中心，3：一级部）
   **/
   private String otype;
   /**
   *	应用角色
   **/
   private String role;
   /**
   *	等级A。。。
   **/
   private String tlevel;
   /**
   *	人数
   **/
   private Long nums;
   /**
   *	创建时间
   **/
   private Date createtime;
   /**
   *	创建人
   **/
   private String createuserid;

   /**
    *	部门名称
    **/
   private String depname;
   /**
    *	中心名称
    **/
   private String centername;

   /**
    * 获得下面数据集合
    */
   private List<PpmKpiLevelPersonDto> persons;


}
