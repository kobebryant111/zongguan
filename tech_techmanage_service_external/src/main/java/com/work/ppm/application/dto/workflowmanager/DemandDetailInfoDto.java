package com.work.ppm.application.dto.workflowmanager;

import com.work.ppm.application.dto.workflowmanager.ext.ReadyParamsDto;
import lombok.Data;

import java.util.Date;

@Data
public class DemandDetailInfoDto extends ReadyParamsDto {

    /**
     *  编号
     */
    String id;
    /**
     *  流程实例id
     */
    String instanceid;
    /**
     *  人员类型
     */
    String usertype;
    /**
     *  需求人数
     */
    String usernum;
    /**
     *  联系招聘
     */
    String isadvertise;
    /**
     *  增加原因
     */
    String addreason;
    /**
     *  人员要求
     */
    String personrequire;
    /**
     *  创建时间
     */
    Date createdate;
    /**
     *  创建人编号
     */
    String createuserid;


}
