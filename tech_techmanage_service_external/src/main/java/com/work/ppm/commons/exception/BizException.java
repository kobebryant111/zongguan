package com.work.ppm.commons.exception;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import lombok.Getter;

/**
 * 统一异常类
 */
@Getter
@MercuryLog
public class BizException extends  RuntimeException{
    /**
     * 错误类枚举
     */
    private ApiCode code;
    /**
     * 错误信息
     */
    private String msg;

    /**
     * 构造函数
     */
    public BizException() {
        super();
    }

    /**
     * 有参构造函数
     */
    public BizException(Exception e) {
        if( e instanceof BizException) {
            this.msg =  ((BizException) e).getMsg();
            this.code = ((BizException) e).code;
        } else {
            this.msg = e.getMessage();
            this.code = ApiCode.INTERNAL_SERVER_ERROR;
        }
    }
    /**
     * 有参构造函数
     */
    public BizException(ApiCode code,String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    /**
     * 有参构造函数
     */
    public BizException(ApiCode code) {
        this.code = code;
        this.msg = code.getMsg();
    }

    /**
     * 过期
     * @return  ApiResult
     */
    @Deprecated
    public ApiResult getApiResult() {
        ApiResult result = new ApiResult();
        result.setStatus(this.getCode());
        result.setMessage(this.getMsg());
        return result;
    }
}
