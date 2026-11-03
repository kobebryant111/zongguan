package com.tansun.wf.extend.vo;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class WfResponseVO {
	/**
	 * 正确响应状态
	 */
	public static final int OK = 200;
	/**
	 * 错误响应状态
	 */
	public static final int ERROR = 100;

	/**
	 * 提示响应状态
	 */
	public static final int WARN = 300;
	
	/**
	 * 状态值
	 */
	private int status;
	/**
	 * 描述信息
	 */
	private String description;
	/**
	 * 响应数据
	 */
	private Object data;


	/**
	 * 正确响应
	 */
	public static WfResponseVO successResponse(Object data) {
		return WfResponseVO.successResponse(data, null);
	}


	/**
	 * 正确响应
	 */
	public static WfResponseVO successResponse(String description) {
		return WfResponseVO.successResponse(null, description);
	}

	/**
	 * 正确响应
	 */
	public static WfResponseVO successResponse(Object data, String description) {
		return WfResponseVO.createResponse(data, WfResponseVO.OK, description);
	}

	/**
	 * 错误响应
	 */
	public static WfResponseVO errorResponse(Object data) {
		return WfResponseVO.errorResponse(data, null);
	}

	/**
	 * 错误响应
	 */
	public static WfResponseVO errorResponse(String description) {
		return WfResponseVO.errorResponse(null, description);
	}

	/**
	 * 错误响应
	 */
	public static WfResponseVO errorResponse(Object data, String description) {
		return WfResponseVO.createResponse(data, WfResponseVO.ERROR, description);
	}

	/**
	 * 警告响应
	 */
	public static WfResponseVO warnResponse(Object data) {
		return WfResponseVO.warnResponse(data, null);
	}

	/**
	 * 警告响应
	 */
	public static WfResponseVO warnResponse(String description) {
		return WfResponseVO.warnResponse(null, description);
	}


	/**
	 * 警告响应
	 */
	public static WfResponseVO warnResponse(Object data, String description) {
		return WfResponseVO.createResponse(data, WfResponseVO.WARN , description);
	}

	/**
	 * 创建响应对象
	 */
	public static WfResponseVO createResponse(Object data, Integer status, String description) {
		WfResponseVO response = new WfResponseVO();
		//判断是否为分页或列表信息 ，进行二次组装
		if(null!=data && data instanceof PageInfo){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("grid",data);
			response.setData(result);
		}else{
			response.setData(data);
		}
		response.setStatus(status);
		response.setDescription(description);

		return response;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
