package com.tansun.wf.decision.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author WY
 */
public class RuleHttpUtils {

    public static String sendPost(Map<String,String> requestMap,String url) throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);  
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();  
		  
        if(requestMap!=null) {
            for(Entry<String, String> entry:requestMap.entrySet()) {
            	NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            	pairs.add(pair);
            }	
        }
		post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
		HttpResponse response = httpClient.execute(post);
		String result = "";
		if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){    
			
			HttpEntity entitys = response.getEntity();  
			if (entitys != null) {  
				result=EntityUtils.toString(entitys);
			}  

		}else{
			throw new Exception(response.getStatusLine().getStatusCode() + "");
		}
		httpClient.close();
		return result;
    }

}


