package com.work.ppm.application.dto.banktxmanager;
import lombok.Data;
import java.util.Date;

@Data
public class PpmAprivalInfo {
    /**
     * 姓名
     */
    private String empName;
    /**
     * 工号
     */
    private String badge;
    /**
     * 时间区间
     */
    private String[] entryTime;
    /**
     *开始日期
     */
    private Date startDate;
    /**
     *结束日期
     */
    private Date endDate;
    /**
     *审批状态
     */
    private String approvalStatus;


}
