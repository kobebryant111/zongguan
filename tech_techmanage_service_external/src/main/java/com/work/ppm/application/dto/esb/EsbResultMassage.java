package com.work.ppm.application.dto.esb;


import com.work.ppm.commons.constant.Constants;
import com.work.ppm.commons.exception.BizExceptionNew;
import com.work.ppm.commons.utils.StringUtilNew;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 返回对象
 */
@Data
@XStreamAliasType("service")
public class EsbResultMassage extends EsbMassage {


    /**
     * 异常处理返回扣
     * @return boolean
     */
    public boolean handleError(){
       boolean flag = getTranResult();
       if(!flag){
           throw new BizExceptionNew("ESB_ERROR|"+this.getReturnMsg());
       }
        return flag;
    }

    /**
     * 返回交易信息
     * @return boolean
     */
    public  String getReturnMsg(){
        List<SysResult> sysResults = this.getSysHead().getSysResult();
        if(sysResults==null||sysResults.size()==0){
            return null;
        }
        String errorMsg = sysResults.get(0).getReturnMsg();
        if(StringUtilNew.isNull(errorMsg)){
            errorMsg = "esb接口返回空";
        }
        return errorMsg;

    }

    /**
     * 返回交易结果
     * @return boolean
     */
    public  boolean getTranResult(){
        boolean result = false;
        List<SysResult> sysResults = this.getSysHead().getSysResult();
        if(sysResults==null||sysResults.size()==0){
            return false;
        }
        if(Arrays.stream(Constants.ESB_OK).collect(Collectors.toList())
                .contains(sysResults.get(0).getReturnCode())){
            result = true;
        }
        return result;

    }
}
