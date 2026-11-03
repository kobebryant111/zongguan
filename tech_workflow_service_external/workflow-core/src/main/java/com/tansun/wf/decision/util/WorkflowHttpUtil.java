package com.tansun.wf.decision.util;



import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http url 工具类
 */
@SuppressWarnings("unchecked")
public class WorkflowHttpUtil {

	private static Logger logger = LoggerFactory.getLogger(WorkflowHttpUtil.class);

	public static <T> T httpGet(String url, Class<T> resultClass) throws Exception {
		String result = "{}";
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet get = new HttpGet(url);
			logger.info(url);
			CloseableHttpResponse httpResponse = httpClient.execute(get);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					result = EntityUtils.toString(entity);
					if(statusCode >= 400 && statusCode <= 505){
						throw new Exception(result);
					}
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw e;
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(String.class==resultClass){
			return (T) result;
		}else{
			T t = JSON.parseObject(result, resultClass);
			return t;
		}
	}
	
	/**
	 * http post请求
	 * @param params 
	 * 			params 为对象时，遍历属性拼装为表单数据
	 * 			params 为map时，遍历属性拼装为表单数据
	 * @param resultClass
	 * @return
	 * @throws Exception 
	 */
	public static <T> T httpPost(String url,Object params, Class<T> resultClass) throws Exception {
		String result = "{}";
		// 创建参数列表
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null) {
			if (params instanceof Map) {
				Map<String, String> mapParams = (Map<String, String>) params;
				for (String key : mapParams.keySet()) {
					list.add(new BasicNameValuePair(key, mapParams.get(key)));
				}
			} else {
				Map<String, String> objMap = objToMap(params);
				for (String key : objMap.keySet()) {
					list.add(new BasicNameValuePair(key, objMap.get(key)));
				}
			}
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			// url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
			post.setEntity(uefEntity);
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					result = EntityUtils.toString(entity);
					logger.info("http back :" + JSON.toJSON(result));
					if (statusCode >= 400 && statusCode <= 505) {
						throw new Exception(result);
					}
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw e;
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (String.class == resultClass) {
			return (T) result;
		} else {
			T t = JSON.parseObject(result, resultClass);
			return t;
		}
		

	}
	
	private static Map<String,String> objToMap(Object param) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		//使用反射获取每个对象的属性
		Field[] fields = param.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			//忽略序列化的值
			if("serialVersionUID".equals(fieldName)){
				continue;
			}
			Object value = null;
			//获取值
			if(field.isAccessible()){   
				value = field.get(param);
			}else{
				field.setAccessible(true);
				value = field.get(param);
				field.setAccessible(false);
			}
			//空值忽略返回
			if(value == null || StringUtils.isBlank(String.valueOf(value))){
				continue;
			}
			//类型判断
			if(String.class.equals(field.getType())){
				//String 
				map.put(fieldName, value.toString());
			}
			if(Integer.class.equals(field.getType())){
				//String 
				map.put(fieldName, value+"");
			}
		}
		return map;
	}


}




