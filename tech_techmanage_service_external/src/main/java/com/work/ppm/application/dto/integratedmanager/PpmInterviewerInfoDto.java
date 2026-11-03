package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import lombok.Data;

import java.util.List;

/**
 * 面试官信息
 */
@Data
public class PpmInterviewerInfoDto {
    /**
     *	id
     **/
    private Long id;
    /**
     *	流程id
     **/
    private String instanceid;
    /**
     *	面试官工号
     **/
    private String msgid;
    /**
     *	面试官姓名
     **/
    private String msgname;
    /**
     *	面试官部门名称
     **/
    private String msgdeptname;
    /**
     *	面试官部门id
     **/
    private String msgdeptno;
    /**
     * 人力需求 编号
     */
    private String rlId;

    /**
     是否符合要求（0：否，1：是）
     */
    private Integer meet;

    /**
     不符合原因
     */
    private String remarks;

    /**
     * 外包人员信息
     */
    private List<PpmIntervieweeInfo> eeList;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;
    /**
     * 面试打分
     */
    private List<PpmIntervieweeScoreinfo> ivsList;
}
