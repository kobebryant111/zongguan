package com.work.ppm.application.model.otherdeptmanager.ext;

import com.work.ppm.application.model.otherdeptmanager.PpmAccessCardLedger;
import lombok.Data;


/**
 * 外包人员监保部台账 实体对象
 */
@Data
public class PpmAccessCardLedgerExt extends PpmAccessCardLedger {

   /**
    * 姓名
    */
   private String userName;
   /**
   *	流程编号
   **/
   private String deptName;
   /**
   *	工号
   **/
   private String idCardNo;
   /**
   *	是否发起归还
   **/
   private String telephone;
   /**
   *	是否需要制卡
   **/
   private String outsourceName;
   /**
    * id 数组
    */
   private String idList;
   /**
    * 性别
    */
   private String sex;


}
