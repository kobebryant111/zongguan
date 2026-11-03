package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.othermanager.PpmWorkKjlCghtDto;
import com.work.ppm.application.dto.usermanager.PpmDaTeamGroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
  * 描述
  */
@Data
public class PpmOutsourcingChangeInfosDto {
	/**
	*	老外包方式名称
	**/
	private String oldOutsourcingtypename;
	/**
	*	新外包方式名称
	**/
	private String newOutsourcingtypename;
	/**
	*	老岗位名称
	**/
	private String oldPositionname;
	/**
	*	新岗位名称
	**/
	private String newPositionname;
	/**
	*	老结算方式名称
	**/
	private String newUsetypename;
	/**
	*	新结算方式名称
	**/
	private String oldUsetypename;
	/**
	*	主键
	**/
	private Long id;
	/**
	*	工号
	**/
	private String jobid;
	/**
	*	姓名
	**/
	private String username;
	/**
	*	身份证
	**/
	private String certnumber;
	/**
	*	公司编号
	**/
	private String companyid;
	/**
	*	公司名称
	**/
	private String companyname;
	/**
	*	老部门编号
	**/
	private String oldDepid;
	/**
	*	老部门名称
	**/
	private String oldDepname;
	/**
	*	新部门编号
	**/
	private String newDepid;
	/**
	*	新部门名称
	**/
	private String newDepname;
	/**
	*	老行内负责人
	**/
	private String oldOrganizers;
	/**
	*	老行内负责人姓名
	**/
	private String oldOrganizersname;
	/**
	*	新行内负责人
	**/
	private String newOrganizers;
	/**
	*	新行内负责人姓名
	**/
	private String newOrganizersname;
	/**
	*	老小组
	**/
	private String oldGroupid;
	/**
	*	老小组名称
	**/
	private String oldGroupname;
	/**
	*	新小组
	**/
	private String newGroupid;
	/**
	*	新小组名称
	**/
	private String newGroupname;
	/**
	*	老项目编号
	**/
	private String oldProjectno;
	/**
	*	老项目名称
	**/
	private String oldProjectname;
	/**
	*	新项目编号
	**/
	private String newProjectno;
	/**
	*	新项目名称
	**/
	private String newProjectname;
	/**
	*	老系统编号
	**/
	private String oldSysno;
	/**
	*	老系统名称
	**/
	private String oldSyaname;
	/**
	*	新系统编号
	**/
	private String newSysno;
	/**
	*	新系统名称
	**/
	private String newSysname;
	/**
	*	老外包方式
	**/
	private String oldOutsourcingtype;
	/**
	*	新外包方式
	**/
	private String newOutsourcingtype;
	/**
	*	老结算方式
	**/
	private String oldUsetype;
	/**
	*	新结算方式
	**/
	private String newUsetype;
	/**
	*	老岗位
	**/
	private String oldPosition;
	/**
	*	新岗位
	**/
	private String newPosition;
	/**
	*	老价格
	**/
	private String oldPrice;
	/**
	*	新价格
	**/
	private String newPrice;
	/**
	*	老合同编号
	**/
	private String oldContractid;
	/**
	*	老合同名称
	**/
	private String oldContractname;
	/**
	*	新合同编号
	**/
	private String newContractid;
	/**
	*	新合同名称
	**/
	private String newContractname;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	面试时间
	**/
	private String interviewtime;
	/**
	*	专业分
	**/
	private Double professionscore;
	/**
	*	综合分
	**/
	private Double comprehensivescore;
	/**
	*	价值观分
	**/
	private Double valuescore;
	/**
	*	形象分
	**/
	private Double imagescore;
	/**
	*	评论
	**/
	private String comments;
	/**
	*	年限
	**/
	private Long years;
	/**
	*	等级
	**/
	private String grade;
	/**
	*	成绩
	**/
	private Double totalscore;
	/**
	*	结果
	**/
	private String result;
	/**
	*	建议价格
	**/
	private Long recommendedPrice;
	/**
	*	原因
	**/
	private String reason;

	/**
	 * 小组
	 */
	@ApiModelProperty(value = "小组集合", name = "teams")
	private List<PpmDaTeamGroupDto> teams;

	/**
	 * 行内负责人
	 */
	@ApiModelProperty(value = "小组集合", name = "orgonizers")
	private List<CommTreeDto> organizers;

	/**
	 * 人力合同
	 */
	@ApiModelProperty(value = "人力合同", name = "personnelContracts")
	private List<PpmRlwbhtInfoDto> personnelContracts;

	/**
	 * 项目合同
	 */
	@ApiModelProperty(value = "项目合同", name = "projectContracts")
	private List<PpmWorkKjlCghtDto> projectContracts;
}
