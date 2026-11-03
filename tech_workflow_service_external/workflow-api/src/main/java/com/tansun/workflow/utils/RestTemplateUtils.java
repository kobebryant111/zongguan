package com.tansun.workflow.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtils {

    static private RestTemplate restTemplate = new RestTemplate();

    public static String post(String url, String paramsJson) {
        return post(url, paramsJson, restTemplate);
    }

    public static String post(String url, String paramsJson,RestTemplate r ) {
        if (r != null) {
            restTemplate = r;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        httpHeaders.set("token","");
        HttpEntity requestEntity = new HttpEntity(paramsJson, httpHeaders);;
        String result = null;
        try {
            result = restTemplate.postForObject(url, requestEntity, String.class);
        } catch (RestClientException e) {
            if (e instanceof HttpServerErrorException){
                throw new RuntimeException(((HttpServerErrorException) e).getResponseBodyAsString());
            }else {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }

    public static void replaceWithCloudRestTemplate(RestTemplate r) {
        restTemplate = r;
    }
}
