package com.work.ppm.application.model.otherdeptmanager.ext;

import com.work.ppm.application.model.otherdeptmanager.PpmAccessCardApplyInfo;
import lombok.Data;


/**
 * 外包人员监保部台账
 */
@Data
public class PpmAccessCardApplyInfoExt extends PpmAccessCardApplyInfo {


   /**
   *	是否发起归还
   **/
   private String ifCreateReturn;
   /**
   *	是否需要制卡
   **/
   private String ifMakeCard;

   /**
   *	银行卡号
   **/
   private String creditCardNumbers;
   /**
   *	户号
   **/
   private String accountNumber;
    /**
     *	开户地
     **/
    private String placeOfAccount;
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
     *	电话
     **/
    private String telephone;
    /**
     *	供应商名称
     **/
    private String outsourceName;
    /**
     *	供应商编号
     **/
    private String outsourceNo;
    /**
     *	性别
     **/
    private String sex;
}
