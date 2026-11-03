package com.tansun.wf.platform.query;

import com.tansun.wf.extend.db.WfBaseQuery;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/9
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfLeaveTransferQuery  extends WfBaseQuery {
    /**
     * 分类ID
     */
    private String classifyId;
    /**
     * 交接人
     */
    private String transferId;
    /**
     * 登录名
     */
    private String userId;
    /**
     * 状态
     */
    private String status;
    
    
    private  String   tenantId;

    public String getClassifyId() {
        return classifyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
    
    
}
