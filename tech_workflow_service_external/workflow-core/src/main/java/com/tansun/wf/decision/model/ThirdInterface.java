package com.tansun.wf.decision.model;


import java.io.Serializable;
import java.util.Map;

/**
 * 调用外部接口对象
 */
public class ThirdInterface implements Serializable {
	private static final long serialVersionUID = -161123581321345589L;

    private String id;
    private String appId;// 数据源查询任务编号 : 数据源查询任务编号 [100,0]
    private String bankSn;// 机构编号 : 机构编号 [100,0]
    private Integer dataSourceDay;// 查询多少天内的数据源 : 查询多少天内的数据源 [10,0]
    private String createTime;// 创建时间 : 创建时间 [32,0]
    private String returnUrl;// 回调路径 : 回调路径 [100,0]
    private String interfaceKey;// 接口编号
    private String syncReturnCode;// 同步返回编码
    private String syncReturnMsg;// 同步返回消息
    private String syncDataJson;// 同步返回数据
    private String asyncReturnCode;// 收到消息后返回编号 : 收到消息后返回编号 [10,0]
    private String asyncReturnMsg;// 收到消息后返回消息 : 收到消息后返回消息 [300,0]
    private String asyncDataJson;// 外部接口端返回的查询的数据源数据 : 外部接口端返回的查询的数据源数据 [65535,0]
    private String dataStatus;// 通信状态 : 通信状态 [10,0]
    private Integer requestNumber;// 向服务端请求次数 : 向服务端请求次数 [10,0]
    private String errorMessage;// 错误信息 : 错误信息 [500,0]
    private String status;// 状态 : 状态 [10,0]
    private String customerNum;// customerNum [50,0]
    private Map<String, Object> req;// JSON格式参数，为数据源请求查询参数 : JSON格式参数，为数据源请求查询参数 [2147483647,0]
    private Map<String, String> channelData;// JSON格式参数，为服务端下次接收时需要的参数 : JSON格式参数，为服务端下次接收时需要的参数 [65535,0]
    private String dataJson;// 外部接口端返回的查询的数据源数据 : 外部接口端返回的查询的数据源数据 [65535,0]


	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getBankSn() {
		return bankSn;
	}
	public void setBankSn(String bankSn) {
		this.bankSn = bankSn;
	}
	public Integer getDataSourceDay() {
		return dataSourceDay;
	}
	public void setDataSourceDay(Integer dataSourceDay) {
		this.dataSourceDay = dataSourceDay;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getInterfaceKey() {
		return interfaceKey;
	}
	public void setInterfaceKey(String interfaceKey) {
		this.interfaceKey = interfaceKey;
	}
	public String getSyncReturnCode() {
		return syncReturnCode;
	}
	public void setSyncReturnCode(String syncReturnCode) {
		this.syncReturnCode = syncReturnCode;
	}
	public String getSyncReturnMsg() {
		return syncReturnMsg;
	}
	public void setSyncReturnMsg(String syncReturnMsg) {
		this.syncReturnMsg = syncReturnMsg;
	}
	public String getAsyncReturnCode() {
		return asyncReturnCode;
	}
	public void setAsyncReturnCode(String asyncReturnCode) {
		this.asyncReturnCode = asyncReturnCode;
	}
	public String getAsyncReturnMsg() {
		return asyncReturnMsg;
	}
	public void setAsyncReturnMsg(String asyncReturnMsg) {
		this.asyncReturnMsg = asyncReturnMsg;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public Integer getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(Integer requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public Map<String, Object> getReq() {
		return req;
	}
	public void setReq(Map<String, Object> req) {
		this.req = req;
	}
	public Map<String, String> getChannelData() {
		return channelData;
	}
	public void setChannelData(Map<String, String> channelData) {
		this.channelData = channelData;
	}
	public String getSyncDataJson() {
		return syncDataJson;
	}
	public void setSyncDataJson(String syncDataJson) {
		this.syncDataJson = syncDataJson;
	}
	public String getAsyncDataJson() {
		return asyncDataJson;
	}
	public void setAsyncDataJson(String asyncDataJson) {
		this.asyncDataJson = asyncDataJson;
	}
	public String getDataJson() {
		return dataJson;
	}
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}