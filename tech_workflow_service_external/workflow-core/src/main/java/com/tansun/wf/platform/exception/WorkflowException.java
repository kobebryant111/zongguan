package com.tansun.wf.platform.exception;


/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/8
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WorkflowException extends RuntimeException {
    private static final long serialVersionUID = 581432832305321682L;



    public WorkflowException() {
        super();
    }

    /**
     * message 错误信息
     * @param message
     */
    public WorkflowException(String message) {
        super(message);

    }
}
