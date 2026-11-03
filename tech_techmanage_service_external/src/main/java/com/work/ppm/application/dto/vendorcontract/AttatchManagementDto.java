package com.work.ppm.application.dto.vendorcontract;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.model.vendormanager.Doc;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttatchManagementDto extends AttatchManagement {
    /**
     * 附件文档
     */
    List<Doc> docList;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;
    /**
     * 流程实例id
     */
    private String instanceId;

    /**
     * 生效结束日期
     */
    private String startDateE;

    /**
     * 失效结束日期
     */
    private String endDateE;
}
