package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 描述
 * @date 2020-09-08
 *
 */
@Data
public class PpmManpowerdemandApplyInfoOrgInfo extends PpmManpowerdemandApplyInfo {

	/**
	 * 供应商信息集合
	 */
	@ApiModelProperty(value = "oinfos", name = "oinfos")
	private List<PpmOutsourcingInfo> oinfos;

}
