package com.tansun.wf.extend.exception;

/**
 * 暂停执行实例继续往下执行异常
 */
public class WorkflowPauseExecutionException extends RuntimeException {
    public final static String PAUSE_PROCESS = "PAUSE_PROCESS";

    public WorkflowPauseExecutionException(String message) {
        super(PAUSE_PROCESS + " reason is  : " + message);
    }
}
