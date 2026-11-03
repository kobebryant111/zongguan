package com.tansun.wf.decision.service;


import com.alibaba.fastjson.JSON;
import com.tansun.wf.decision.model.BaseVo;
import com.tansun.wf.decision.model.ThirdInterface;
import com.tansun.wf.decision.model.ThirdPartyDataSourceConfigModel;
import com.tansun.wf.decision.util.WorkflowHttpUtil;
import com.tansun.workflow.api.IWorkflowActExpandTracelogService;
import com.tansun.workflow.model.ActExpandTracelog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 外部系统提供的服务接口
 */
@Service
public class ExternalService implements ApplicationContextAware{

	private static Logger logger = LoggerFactory.getLogger(ExternalService.class);

	private ApplicationContext applicationContext;

	@Autowired
    private InterfaceConfigParserService interfaceConfigParserService;

	@Autowired(required = false)
	private IWorkflowActExpandTracelogService iWorkflowActExpandTracelogService;

	/**
	 * 插入轨迹日志服务
	 * @throws Exception 
	 */
	public void logTrace(ActExpandTracelog log) throws Exception{
		logger.info("======> " + JSON.toJSONString(log));
		if(iWorkflowActExpandTracelogService != null){
			iWorkflowActExpandTracelogService.logTrace(log);
		}
		return ;
	}
	
	/**
	 * 推送结果
	 * @throws Exception 
	 */
	public void pushWorkflowResult(Map<String,Object> result) throws Exception{
		logger.info("审批结果：" + JSON.toJSONString(result));
		return ;
	}
	
	/**
	 * 调用第三方接口
	 * @throws Exception
	 */
	public ThirdInterface invokeThirdInterface(ThirdInterface third) throws Exception{
        ThirdPartyDataSourceConfigModel interfaceConfig = interfaceConfigParserService.getInterfaceConfig(third.getInterfaceKey());
        String dealBeanName = interfaceConfig.getBeanName();
        InterfaceService service = null;
        try {
            service = applicationContext.getBean(dealBeanName,InterfaceService.class);
        } catch (BeansException e) {
            logger.error("dealBeanName is null," + e.getMessage(),e);
            throw e;
        }
        String url = interfaceConfig.getUrl();
        if(StringUtils.isBlank(url)){
            url = interfaceConfig.getDefaultUrl();
        }
        Map<String, Object> result = service.syncInvoke(url, third.getReq());
        String jsonResult = JSON.toJSONString(result);
        third.setDataJson(jsonResult);
        third.setSyncDataJson(jsonResult);
        return third;

	}
	
	
	/**
	 * @param taskMap
	 * @throws Exception 
	 */
	public BaseVo createTask(Map<String,Object> taskMap) throws Exception{
		try {
			String url =   "?transCode=M200303&channelNo=1";
			BaseVo result = WorkflowHttpUtil.httpPost(url, taskMap, BaseVo.class);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
}
