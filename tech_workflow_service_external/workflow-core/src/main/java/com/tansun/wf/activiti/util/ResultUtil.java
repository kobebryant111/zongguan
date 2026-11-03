package com.tansun.wf.activiti.util;

import com.tansun.workflow.model.BaseBean;

/**
 * 返回结果工具类
 *
 */
public class ResultUtil {
	// 赋值返回结果
	public static void setReturnCode(BaseBean baseBean, String code, String retMsg) {
		baseBean.setRetCode(code);
		baseBean.setRetMsg(retMsg);
	}
	
}
