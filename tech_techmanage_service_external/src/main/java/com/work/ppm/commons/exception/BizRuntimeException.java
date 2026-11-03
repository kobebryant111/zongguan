package com.work.ppm.commons.exception;

/**
 * 自定义运行时异常
 */
public class BizRuntimeException extends RuntimeException implements BizErrorMessageNew {
    /**
     * 用于返回前端的Http错误Code
     */
    private  int httpCode = 400;

    /**
     * errorCode
     */
    private String errorCode;


    /**
     * 构造方法
     */
    public BizRuntimeException(){
        super();
    }

    /**
     * 构造方法
     * @param message
     */
    public BizRuntimeException(String message){
        super(message);
    }

    /**
     *  请求
     * @return int
     */
    @Override
    public int getHttpCode() {
        return this.httpCode;
    }

    /**
     * 错误码
     * @return String
     */
    @Override
    public String getErrorCode() {
        return this.errorCode;
    }
}
