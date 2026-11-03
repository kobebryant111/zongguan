package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.AapSystem;
import lombok.Data;


/**
 * 描述
 * @date 2020-08-06
 *
 */
@Data
public class AapSystemInfo extends AapSystem {
	/**
	 * id
	 */
    private String sysids;
	/**
	 * 名称
	 */
	private String sysnames;
	/**
	 *	用户id
	 */
	private String userids;
	/**
	 *	用户名称
	 */
	private String usernames;
	/**
	 * 系统负责人
	 */
	private String principala;
}
