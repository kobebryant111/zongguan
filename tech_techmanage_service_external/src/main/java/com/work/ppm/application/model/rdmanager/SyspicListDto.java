package com.work.ppm.application.model.rdmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyspicListDto {
    /**
     * id
     */
    private String id;
    /**
     * 系统编号
     */
    private String systemNo;
    /**
     * 负责人姓名
     */
    private String nameResponsible;
    /**
     * 负责人工号
     */
    private String jobidResponsible;

}
