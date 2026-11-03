package com.tansun.wf.mocktest.model;

// camunda-TODO
import com.tansun.wf.extend.db.WfBaseEntity;

import java.util.Date;

    /**
     * 任务抄送表
     */
    public class WfTestInsEntity extends WfBaseEntity {

        private String id;
        private String ParentId;
        private String planCode;
        private String processInstanceId;
        private String processDefKey;
        private String processInfo;
        private Date createTime;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getPlanCode() {
            return planCode;
        }

        public void setPlanCode(String planCode) {
            this.planCode = planCode;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String parentId) {
            ParentId = parentId;
        }

        public String getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public String getProcessDefKey() {
            return processDefKey;
        }

        public void setProcessDefKey(String processDefKey) {
            this.processDefKey = processDefKey;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getProcessInfo() {
            return processInfo;
        }

        public void setProcessInfo(String processInfo) {
            this.processInfo = processInfo;
        }
    }


