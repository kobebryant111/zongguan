package com.work.ppm.commons.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 对request操作的工具类型
 * 
 * 
 */
@Slf4j
public class RequestUtil {

	/**
	 * 获取HttpServletRequest对象
	 *
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes requestAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes==null?null:requestAttributes.getRequest();
	}


	/**
	 * 获取HttpServletResponse对象
	 *
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getHttpServletResponse() {
		ServletRequestAttributes requestAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return requestAttributes==null?null:requestAttributes.getResponse();
	}


	/**
	 * 请求地址
	 *
	 * @return String
	 */
	public static String getUri() {
		HttpServletRequest req = getHttpServletRequest();
		return req==null?null:req.getRequestURI();
	}


	/**
	 * 请求地址
	 *
	 * @return String
	 */
	public static String getReqIp() {
		HttpServletRequest req = getHttpServletRequest();
		return req==null?null:req.getRemoteAddr();
	}


}
