package com.tansun.wf.platform.model;

import java.util.Date;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/3
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class ActHiCommentEntity {
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * type_
     */
    private String type;
    /**
     * time_
     */
    private Date time;
    /**
     *  task_id_
     */
    private String taskId;
    /**
     *  proc_inst_id_
     */
    private String procInstId;
    /**
     *  action
     */
    private String action;
    /**
     * message_
      */
    private String message;
    /**
     *full_msg_
      */
    private String fullMsg;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullMsg() {
        return fullMsg;
    }

    public void setFullMsg(String fullMsg) {
        this.fullMsg = fullMsg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
