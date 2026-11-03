package com.tansun.wf.decision.model;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSet {
	
	public static final String LONG = "Long"; 
	
	public static final String DOUBLE = "Double"; 
	
	public static final String STRING = "String";

    private Logger logger = LoggerFactory.getLogger(DataSet.class);

	/**
	 * 数据集名称
	 */
	private String dataSetName;
	
	/**
	 * 数据集key
	 */
	private String dataSetKey;
	
	/**
	 * 参数名称与key
	 * {"paramName": "name","paramKey": "key",defaultValue":"0","paramType":"Double","isCollections":"true/false","paramFor":"in/out"}
	 */
	
	private List<ParamVo> params;

	private Object fieldMap;

	/**
	 * 获取一个数据集的默认参数和数据集
	 * @return
	 */
	public Map<String,Object> getDefaultRuleParams(){
		Map<String,Object> ruleParams = new HashMap<String, Object>();
        if (params == null) {
            logger.error("dataset params is null");
        }else{
            logger.info("dataset params is : {}" , JSON.toJSONString(params) );
        }
        for (ParamVo param : params) {
            String paramType = param.getParamType();
            String value = String.valueOf( param.getDefaultValue());
            String isCollections = param.getIsCollections();
            if("true".equals(isCollections)){ //集合类型默认使用 空数组[]
                ruleParams.put(String.valueOf(param.getParamKey()), new String[]{});
            }else{
                switch (paramType) {
                    case DataSet.LONG:
                        ruleParams.put(String.valueOf(param.getParamKey()),StringUtils.isNumeric(value)?Long.parseLong(value):0);
                        break;
                    case DataSet.DOUBLE:
                        ruleParams.put(String.valueOf(param.getParamKey()),StringUtils.isNumeric(value)?Double.parseDouble(value):0);
                        break;
                    default:
                        ruleParams.put(String.valueOf(param.getParamKey()),value);
                };
            }
        }
		return ruleParams;
		
	}

	public String getParamType(String key){
		if(StringUtils.isBlank(key)){
			return STRING;
		}
        for (ParamVo param : params) {
            if(key.equals(param.getParamKey())){
                return param.getParamType();
            }
        }

		return STRING;
	}
	public String getParamFor(String key){
        for (ParamVo param : params) {
			if(key.equals(param.getParamKey())){
				return param.getParamFor();
			}
		}
		return null;
	}
	
	public String getDataSetName() {
		return dataSetName;
	}

	public String getDataSetKey() {
		return dataSetKey;
	}

	public List<ParamVo> getParams() {
		return params;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	public void setDataSetKey(String dataSetKey) {
		this.dataSetKey = dataSetKey;
	}

	public void setParams(List<ParamVo> params) {
		this.params = params;
	}

    /**
     * 读取配置获取映射的FormKey
     * @param key
     */
    public String getMappedFormKey(String key) {
        String mappedKey = null;
        for (ParamVo param : params) {
            if(key.equals(param.getParamKey())){
                FormDetailVo formDetailVo = param.getFormDetail();
                if (formDetailVo != null) {
                    String name = formDetailVo.getName();
                    if(StringUtils.isNotBlank(name)) {
                        mappedKey = name;
                    }
                }
            }
        }
        return mappedKey;
    }

    /**
     * 读取配置获取映射的表单上的默认值
     * @param key
     */
    public String getFormDefaultValue(String key) {
        String defaultValue = null;
        for (ParamVo param : params) {
            if(key.equals(param.getParamKey())){
                FormDetailVo formDetailVo = param.getFormDetail();
                if (formDetailVo != null) {
                    String value = formDetailVo.getDefaultValue();
                    if(StringUtils.isNotBlank(value)) {
                        defaultValue = value;
                    }
                }
            }
        }
        return defaultValue;
    }

    public Object getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Object fieldMap) {
        this.fieldMap = fieldMap;
    }
}
