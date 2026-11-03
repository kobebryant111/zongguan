package com.work.ppm.commons;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 统一返回数据对象
 */
@Getter
@Setter
public class ApiResult<E> {
    /**
     * 枚举
     */
    private ApiCode status;
    /**
     * 消息
     */
    private String message;
    /**
     * 分页数
     */
    private Integer total;
    /**
     * 结果
     */
    private E results;



    /**
     * 转json字符串
     * @return json 字符串
     */
    public String toJson() {
        return JSONObject.toJSONString(this, SerializerFeature.IgnoreErrorGetter);
    }

    /**
     * getStatus
     * @return int
     */
    public int getStatus() {
        if(!StringUtil.isStringNotEmpty(message)) {
            message = status.getMsg();
        }
        return status.getCode();
    }

    public void setStatus(ApiCode status) {
        this.status = status;
    }

    /**
     * setMessage
     * @param message 外包
     */
    public void setMessage(String message) {
        this.message = "";
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * 数据正常
     * @param message 消息
     */
    public void success(String message) {
        this.status = ApiCode.SUCCESS;
        this.message = message;
    }
    /**
     * 数据正常
     */
    public void success() {
        this.status = ApiCode.SUCCESS;
        this.message = ApiCode.SUCCESS.getMsg();
    }
    /**
     * 数据失败
     * @param message 消息
     */
    public void failure(String message) {
        this.status = ApiCode.FAIL;
        this.message = message;
    }

    /**
     * 创建成功报文
     * @return ApiResult
     */
    public static ApiResult newSuccess(){
        ApiResult result = new ApiResult();
        result.success();
        return result;
    }


    /**
     * 创建成功报文
     * @param message
     * @return ApiResult
     */
    public static ApiResult newSuccess(String message){
        ApiResult result = new ApiResult();
        result.success(message);
        return result;
    }

    /**
     * 创建成功报文
     * @param data
     * @return ApiResult
     */
    public static <E> ApiResult<E> newSuccessResults(E data){
        ApiResult result = new ApiResult();
        result.success();
        result.setResults(data);
        return result;
    }



    /**
     * 创建失败结果
     * @param message
     * @return ApiResult
     */
    public static ApiResult newFailure(String message){
        ApiResult result = new ApiResult();
        result.failure(message);
        return result;
    }


    /**
     * 返回分页结果
     * @param pager
     * @return ApiResult<List>
     */
    public static ApiResult<List> pageResult(Pager pager) {
        ApiResult<List> result =new ApiResult<>();
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }





}
