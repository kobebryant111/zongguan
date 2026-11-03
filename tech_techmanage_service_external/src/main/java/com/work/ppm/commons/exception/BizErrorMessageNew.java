package com.work.ppm.commons.exception;

/**
 * 异常
 */
public interface BizErrorMessageNew {


	/**
	 * xx
	 * @return  String
	 */
	String getMessage();
	
	/**
	 * xx
	 * @return  int
	 */
	int getHttpCode();
	/**
	 * xx
	 * @return  String
	 */
	String getErrorCode();
}
