package com.tansun.wf.activiti.model;

public class SequentialAddedTaskState{

    /**
     *  正常任务：0
     */
    public static final String NORMAL_TASK = "0";

    /**
     * 加签任务：1
     */
    public static final String ADD_TASK = "1";

    /**
     * 往前加签时被忽略的任务：2
     */
    public static final String IGNORE_TASK = "2";

    /**
     * 往前加签时被移到后方的任务：3
     */
    public static final String MOVE_TASK = "3";

    /**
     * 流程变量key
     */
    public static final String STATE_VARIABLE_KEY = "_SEQUENTIAL_ADDED_TASK_STATES";

    /**
     * 加签任务类型key
     */
    public static final String TASK_TYPE_KEY = "_ADDED_TASK_TYPE";

    /**
     * 前加签
     */
    public static final String FRONT = "front";

    /**
     * 后加签
     */
    public static final String BACK = "back";

    public SequentialAddedTaskState(){
        super();
    }


    public SequentialAddedTaskState(String taskType) {
        this.taskType = taskType;
    }

    public SequentialAddedTaskState( String user, String taskType) {
        this.user = user;
        this.taskType = taskType;
    }


    /**
     * 用户
     */
    private String user;

    /**
     * 正常任务：0、加签任务：1、往前加签被忽略的任务：2、往前加签时被移到后方的任务：3
     */
    private String taskType;

    /**
     * 操作用户
     */
    private String operator;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}