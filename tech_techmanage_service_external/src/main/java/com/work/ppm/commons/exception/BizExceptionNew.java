package com.work.ppm.commons.exception;

/**
 * 业务Exception基础类
 */
public class BizExceptionNew extends RuntimeException implements BizErrorMessageNew {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3843159392119526740L;
	/**
	 * 用于返回前端的Http错误Code
	 */
	private int httpCode = 400;

	/**
	 * 异常
	 * @param exEnum
	 */
	public BizExceptionNew(ExceptionEnum exEnum) {
		super(exEnum.getErrorMsg());
		this.httpCode = 400;
		this.errorCode = exEnum.getErrorCode();

	}


	public int getHttpCode() {
		return httpCode;
	}


	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	/**
	 * errorCode
	 */
	private String errorCode;

	/**
	 * BizException
	 *
	 * @param httpCode
	 *            httpCode
	 * @param message message
	 */
	public BizExceptionNew(int httpCode, String message) {
		super(message);
		this.httpCode = httpCode;
		this.errorCode = httpCode + "";
	}

	/**
	 * BizException
	 *
	 * @param httpCode
	 *            httpCode
	 */
	public BizExceptionNew(int httpCode) {
		this.httpCode = httpCode;
		this.errorCode = httpCode + "";
	}

	/**
	 * BizException
	 *
	 * @param message
	 *            message
	 */
	public BizExceptionNew(String message) {
		super(message);
	}

	/**
	 * BizException
	 *
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public BizExceptionNew(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * BizException
	 *
	 * @param errorCode
	 *            errorCode
	 * @param message
	 *            message
	 */
	public BizExceptionNew(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * 构造方法
	 *
	 * @param errorCode
	 *            自定义错误代码
	 * @param message
	 *            自定义错误信息
	 * @param cause
	 *            {@link Throwable}
	 */
	public BizExceptionNew(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * BizException
	 *
	 * @param httpCode
	 *            httpCode
	 * @param errorCode
	 *            errorCode
	 * @param message
	 *            message
	 */
	public BizExceptionNew(int httpCode, String errorCode, String message) {
		super(message);
		this.httpCode = httpCode;
		this.errorCode = errorCode;
	}

	/**
	 * BizException
	 *
	 * @param httpCode
	 *            httpCode
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public BizExceptionNew(int httpCode, String message, Throwable cause) {
		super(message, cause);
		this.httpCode = httpCode;
	}

	/**
	 * BizException
	 *
	 * @param httpCode
	 *            httpCode
	 * @param errorCode
	 *            errorCode
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public BizExceptionNew(int httpCode, String errorCode, String message,
						   Throwable cause) {
		super(message, cause);
		this.httpCode = httpCode;
		this.errorCode = errorCode;
	}

	 
	public String getErrorCode() {
		return errorCode;
	}

 
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
