package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import lombok.Data;

/**
 * 描述
 * @date 2020-10-12
 *
 */
@Data
public class PpmOutsourcingLzsqInfoDto {
   /**
    * 对象
    */
   private PpmOutsourcingLzsq lzsq;
   /**
    *  对象
    */
   private PpmOutsourcingBasicInfo baseinfo;
   /**
    * bps
    */
   private BpsWorkFlowDto bps;
}
