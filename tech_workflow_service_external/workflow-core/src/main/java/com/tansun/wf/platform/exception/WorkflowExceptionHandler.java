package com.tansun.wf.platform.exception;

import com.tansun.wf.extend.vo.WfResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/14
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */



@ResponseBody
@ControllerAdvice(basePackages = "com.tansun.wf.platform.web")
public class WorkflowExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(WorkflowExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WfResponseVO exception(Exception e) {
        logger.error("error",e);
        if(e instanceof RuntimeException ){
            return WfResponseVO.errorResponse(this.getRealMessage(e));
        }else{
            return WfResponseVO.errorResponse(this.getRealMessage(e));
        }

    }

    public  String getRealMessage(Throwable e) {
        // 如果e不为空，则去掉外层的异常包装
        while (e != null) {
            Throwable cause = e.getCause();
            if (cause == null) {
                return e.getMessage();
            }
            e = cause;
        }
        return "";
    }

}
