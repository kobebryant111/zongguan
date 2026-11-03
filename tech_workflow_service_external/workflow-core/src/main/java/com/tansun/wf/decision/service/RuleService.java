package com.tansun.wf.decision.service;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.decision.model.DataSet;
import com.tansun.wf.decision.model.RuleResultEntity;
import com.tansun.wf.decision.util.WorkflowHttpUtil;
import com.tansun.wf.decision.webservice.RuleDataBusinessService;
import com.tansun.wf.decision.webservice.RuleDataBusinessServiceImplService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则调用服务类
 */
public class RuleService {

    private static Logger logger = LoggerFactory.getLogger(RuleService.class);
    /**
     * 规则启用开关
     */
    private boolean ruleOpen;
    /**
     * 规则调用地址
     */
	private String ruleAddress;

    /**
     * 规则入参类型
     */
    private static final String PARAM_JSON = "json";

    /**
     * 调用方式(http，webservice)
     */
    private String invokeType ="http";
    /**
     * 调用规则引擎
     * @param projectName
     * @param enterPoint
     * @param params
     * @return
     */
    public String invoke(String projectName, String enterPoint, Map<String, Object> params) {
        if(!ruleOpen){
            return null;
        }
        String jsonString = JSON.toJSONString(params);
        logger.info("before call rule projectName is: {}, enterPoint is: {}, params is: {}", projectName, enterPoint, params);
        String outString = null;
        if ("webservice".equals(invokeType)) {
            RuleDataBusinessService cxfClient = create();
            outString = cxfClient.ruleEngineService(projectName, enterPoint, PARAM_JSON, jsonString);
        } else {
            String url = ruleAddress + "/ruleService/ruleEngineService";
            Map<String,String> requestMap = new HashMap<String,String>();
            requestMap.put("projectCode", projectName);
            requestMap.put("enterPoint", enterPoint);
            requestMap.put("businessInput", jsonString);
            try {
                outString =  RuleHttpUtils.sendPost(requestMap,url);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                e.printStackTrace();
            }

        }
        logger.info("after call rule result is:{}", outString);
        return outString;
    }

    /**
     * 调用规则
     * @param projectName
     * @param enterPoint
     * @param dataSets 调用规则涉及的数据集
     * @param ruleParams 规则入参对应的键值
     * @return
     */
    public Map<String, Object> invoke(String projectName, String enterPoint, List<DataSet> dataSets, Map<String, Object> ruleParams) {
        Map<String,Object> invokeParams = new HashedMap();
        for (DataSet dataSet : dataSets) {
            Map<String,Object> defaultRuleParams = dataSet.getDefaultRuleParams();
            this.fillParamsValue(dataSet,ruleParams, defaultRuleParams);
            invokeParams.put(dataSet.getDataSetKey(),defaultRuleParams);
        }
        String outString = invoke(projectName, enterPoint, invokeParams);
        RuleResultEntity ruleResultEntity = new RuleResultEntity(outString);
        if(ruleResultEntity.isSuccess()){
            return ruleResultEntity.getCalcResult();
        }else{
            throw new RuntimeException(ruleResultEntity.getRuleSystem().getException());
        }
    }




    /**
     * 获取规则入参信息
     * @return
     */
    public String getRuleParamInfo() throws Exception{
        if(!ruleOpen){
            return null;
        }
        if ("webservice".equals(invokeType)){
            RuleDataBusinessService cxfClient = create();
            return cxfClient.ruleProjectParamInfo();
        }else{
            String url = ruleAddress + "/ruleService/ruleProjectParamInfo";
            return RuleHttpUtils.sendPost(null,url);
        }
    }

    public static void main(String[] args) throws MalformedURLException {

        String ruleParamInfo =  new RuleDataBusinessServiceImplService(new URL("")).getRuleDataBusinessServiceImplPort().ruleProjectParamInfo();
        System.out.println(ruleParamInfo);
    }

    /**
     * 创建webservice client
     * @return
     */
    private RuleDataBusinessService create(){
        if(!ruleOpen){
            return null;
        }
        URL url = null;
        try {
            String address = ruleAddress + "/service/RulesData/?wsdl";
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new RuleDataBusinessServiceImplService(url).getRuleDataBusinessServiceImplPort();

    }


    /**
     * 填写某个数据集的规则入参值
     * @param dataSet
     * @param dataBlock
     * @param defaultParams
     */
	public void fillParamsValue(DataSet dataSet, Map<String, Object> dataBlock, Map<String, Object> defaultParams) {
        for(String key : defaultParams.keySet()){
            if("out".equals(dataSet.getParamFor(key))){ // 输出参数使用默认值
                continue;
            }

            String mappedFormKey = dataSet.getMappedFormKey(key);
            Object value = dataBlock.get(mappedFormKey);//使用映射的key去获取值
            if(value == null){//值为空时则直接用正常的key去取数据
                value = dataBlock.get(key);
            }
            if(value == null){//值还为空则使用表单配置的默认值做处理
                value = dataSet.getFormDefaultValue(key);
            }
            if(value == null || StringUtils.isBlank(value +"") || "null".equals(value +"")){//值又为空,不做处理
                continue;
            }

            //数据类型转化
            String paramType = dataSet.getParamType(key);
            switch (paramType) {
                case DataSet.LONG:
                    defaultParams.put(key, Long.parseLong(value + ""));
                    break;
                case DataSet.DOUBLE:
                    defaultParams.put(key, Double.parseDouble(value +""));
                    break;
                default:
                    defaultParams.put(key, value);
            }
        }
	}

    public boolean getRuleOpen() {
        return ruleOpen;
    }

    public void setRuleOpen(boolean ruleOpen) {
        this.ruleOpen = ruleOpen;
    }

    public String getRuleAddress() {
        return ruleAddress;
    }

    public void setRuleAddress(String ruleAddress) {
        this.ruleAddress = ruleAddress;
    }

    public String getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }

    public List<Object> getRuleInBank() throws Exception {
        return WorkflowHttpUtil.httpPost(ruleAddress, null, List.class);
    }
}