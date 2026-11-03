package com.work.ppm.application.dto.testmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * packageName com.work.ppm.application.dto.testmanager
 *
 * @version JDK 8
 * @className TestApectacularsDto
 * @date 2024/8/2
 * @description 测试看板参数实体
 */
@Data
public class TestApectacularsDto {
   /**
   *  主键ID
   **/
   @Id
   private String id;
   /**
   *	故事编号
   **/
   private String storyNum;
   /**
   *	故事名称
   **/
   private String storyName;
   /**
   *	上线时间
   **/
   private String onLineDate;
   /**
   *	冒烟案例数
   **/
   private String smokeCaseNum;
   /**
   *	SIT案例数
   **/
   private String sitCaseNum;
   /**
   *	UAT案例数
   **/
   private String uatCaseNum;
   /**
   *	测试案例总数(上线时间后)
   **/
   private String testCaseAmount;
   /**
   *	功能正案例数
   **/
   private String funProCaseNum;
   /**
   *	整体反案例总数
   **/
   private String entProCaseAmount;
   /**
   *	功能反案例数
   **/
   private String funContraCaseNum;
   /**
   *	整体正案例总数
   **/
   private String entContraCaseAmount;
   /**
   *	功能正反案例比
   **/
   private String funProetCasescale;
   /**
   *	整体正反案例比
   **/
   private String entProetCasescale;
   /**
   *	高级缺陷数
   **/
   private String highDefectNum;
   /**
   *	中级缺陷数
   **/
   private String midDefectNum;
   /**
   *	低级缺陷数
   **/
   private String lowDefectNum;
   /**
   *	SIT缺陷率
   **/
   private String sitDefectScale;
   /**
   *	UAT缺陷率
   **/
   private String uatDefectScale;
   /**
   *	预估工作量
   **/
   private String forecastWorkload;
   /**
   *	实际工作量
   **/
   private String reaWorkload;
   /**
   *	偏差比(实际-预估)
   **/
   private String deviationRatio;
   /**
   *	计划交付日期
   **/
   private String planDeliveryDate;
   /**
   *	实际交付日期
   **/
   private String reaDeliveryDate;
   /**
   *	当前测试进度
   **/
   private String curTestProgress;
   /**
   *	创建人
   **/
   private String creatUser;
   /**
   *   创建时间
   **/
   private String creatTime;
   /**
   *	修改人
   **/
   private String updateUser;
   /**
   *	修改时间
   **/
   private String updateTime;
   /**
    *   计划状态
    **/
   private String planState;
   /**
    *	计划上线时间
    **/
   private String planOnLineDate;
   /**
    *	实际上线时间
    **/
   private String reaOnLineDate;
   /**
    *
    **/
   private String[] entryTime;
   /**
    *
    **/
   private Date entryStartDate;
   /**
    *
    **/
   private Date entryEndDate;


}
