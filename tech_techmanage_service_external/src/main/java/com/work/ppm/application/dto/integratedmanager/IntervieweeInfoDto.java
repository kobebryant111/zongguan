package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmIntervieweeOverinfoInfo;
import lombok.Data;

import java.util.List;


/**
 * 描述
 * @date 2020-09-08
 *
 */
@Data
public class IntervieweeInfoDto {
	/**
	 * 外包面试最终信息表
	 */
	private PpmIntervieweeOverinfoInfo ppmIntervieweeOverinfoInfo;

	/**
	 * 面试官信息表
	 */
	private List<PpmIntervieweeScoreinfo> ppmIntervieweeScoreinfoList;

}
