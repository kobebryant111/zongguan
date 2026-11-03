package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import lombok.Data;

/**
 * 描述
 * @date 2020-08-24
 *
 */
@Data
public class PpmIntervieweeInfoInfo extends PpmIntervieweeInfo {
	/**
	 * 外包商公司名称
	 */
	private String outsourcName;
	/**
	 * 性别
	 */
	private String sexName;
	/**
	 * 岗位名称
	 */
	private String positionName;
	/**
	 * 面试人数
	 */
	private Integer sumNum;
	/**
	 * 通过人数
	 */
	private Integer tgNum;
	/**
	 * 人力需求编号
	 */
	private Integer rlId;
}
