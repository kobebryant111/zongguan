package com.work.ppm.application.dto.testmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;

/**
 * packageName com.work.ppm.application.dto.testmanager
 *
 * @version JDK 8
 * @className TestApectacularsInfoExportDto
 * @date 2024/8/2
 * @description 测试看板信息实体
 */
@Data
@ExcelTarget("TestApectacularsInfoExportDto")
public class TestApectacularsInfoExportDto {

   /**
   *	故事编号
   **/
   @Excel(name = "故事编号", orderNum = "1")
   private String storyNum;
   /**
   *	故事名称
   **/
   @Excel(name = "故事名称", orderNum = "2")
   private String storyName;
   /**
   *	上线时间
   **/
   @Excel(name = "上线时间", orderNum = "3" , format = "yyyy-MM-dd")
   private Date onLineDate;
   /**
   *	冒烟案例数
   **/
   @Excel(name = "冒烟案例数", orderNum = "4")
   private String smokeCaseNum;
   /**
   *	SIT案例数
   **/
   @Excel(name = "SIT案例数", orderNum = "5")
   private String sitCaseNum;
   /**
   *	UAT案例数
   **/
   @Excel(name = "UAT案例数", orderNum = "6")
   private String uatCaseNum;
   /**
   *	测试案例总数(上线时间后)
   **/
   @Excel(name = "测试案例总数(上线时间后)", orderNum = "7")
   private String testCaseAmount;
   /**
   *	功能正案例数
   **/
   @Excel(name = "功能正案例数", orderNum = "8")
   private String funProCaseNum;
   /**
   *	整体反案例总数
   **/
   @Excel(name = "整体反案例总数", orderNum = "9")
   private String entProCaseAmount;
   /**
   *	功能反案例数
   **/
   @Excel(name = "功能反案例数", orderNum = "10")
   private String funContraCaseNum;
   /**
   *	整体正案例总数
   **/
   @Excel(name = "整体正案例总数", orderNum = "11")
   private String entContraCaseAmount;
   /**
   *	功能正反案例比
   **/
   @Excel(name = "功能正反案例比", orderNum = "12")
   private String funProetCasescale;
   /**
   *	整体正反案例比
   **/
   @Excel(name = "整体正反案例比", orderNum = "13")
   private String entProetCasescale;
   /**
   *	高级缺陷数
   **/
   @Excel(name = "高级缺陷数", orderNum = "14")
   private String highDefectNum;
   /**
   *	中级缺陷数
   **/
   @Excel(name = "中级缺陷数", orderNum = "15")
   private String midDefectNum;
   /**
   *	低级缺陷数
   **/
   @Excel(name = "低级缺陷数", orderNum = "16")
   private String lowDefectNum;
   /**
   *	SIT缺陷率
   **/
   @Excel(name = "SIT缺陷率", orderNum = "17")
   private String sitDefectScale;
   /**
   *	UAT缺陷率
   **/
   @Excel(name = "UAT缺陷率", orderNum = "18")
   private String uatDefectScale;
   /**
   *	预估工作量
   **/
   @Excel(name = "预估工作量", orderNum = "19")
   private String forecastWorkload;
   /**
   *	实际工作量
   **/
   @Excel(name = "实际工作量", orderNum = "20")
   private String reaWorkload;
   /**
   *	偏差比(实际-预估)
   **/
   @Excel(name = "偏差比(实际-预估)", orderNum = "21")
   private String deviationRatio;
   /**
   *	计划交付日期
   **/
   @Excel(name = "计划交付日期", orderNum = "22", format = "yyyy-MM-dd")
   private String planDeliveryDate;
   /**
   *	实际交付日期
   **/
   @Excel(name = "实际交付日期", orderNum = "23", format = "yyyy-MM-dd")
   private String reaDeliveryDate;
   /**
   *	当前测试进度
   **/
   @Excel(name = "当前测试进度", orderNum = "24")
   private String curTestProgress;
   /**
    *   计划状态
    **/
   @Excel(name = "计划状态", orderNum = "25", replace = {"计划已评估_0","测试设计中_1","案例评审中_2",
           "计划待测试_3", "计划测试中_4","测试已完成_5","项目已终止_6","项目已关闭_7"})
   private String planState;
   /**
    *	计划上线时间
    **/
   @Excel(name = "计划上线时间", orderNum = "26", format = "yyyy-MM-dd")
   private String planOnLineDate;
   /**
    *	实际上线时间
    **/
   @Excel(name = "实际上线时间", orderNum = "27", format = "yyyy-MM-dd")
   private String reaOnLineDate;


}
