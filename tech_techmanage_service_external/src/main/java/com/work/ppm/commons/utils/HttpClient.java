package com.work.ppm.commons.utils;


import com.alibaba.fastjson.JSONObject;
import com.work.ppm.commons.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http工具
 *
 */
@Slf4j
public class HttpClient {
    /**
     * post
     * @param url 地址
     * @param params map
     * @param token token
     * @param secertKey 密钥
     * @return string
     */
    public static String post(String url, String params, String token, String secertKey) {
        String data = "" ;
        try {
            CloseableHttpClient clients = HttpClients.createDefault();
            HttpClientParams params1 = new HttpClientParams();
            // 超时
            params1.setConnectionManagerTimeout(3000L);
            params1.setContentCharset(SystemConstant.CHARSET_UTF8);
            params1.setSoTimeout(3000);
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");

            post.addHeader(SystemConstant.XTOKEN, token);
            post.addHeader(SystemConstant.SECERTKEY, secertKey);

            // 构件消息体
            StringEntity entity = new StringEntity(params, SystemConstant.CHARSET_UTF8);
            entity.setContentType("application/json; charset=utf-8");
            entity.setContentEncoding(SystemConstant.CHARSET_UTF8);
            post.setEntity(entity);
            log.warn("================>>>>>>http-post参数报文：{}", EntityUtils.toString(entity));
            HttpResponse response = clients.execute(post);
            int code = response.getStatusLine().getStatusCode();
            data = EntityUtils.toString(response.getEntity());
            log.warn("==============>http-post返回报文：{},状态码：{}", data, code);
        } catch (IOException e) {
            log.error("http-post请求异常", e);
        }
        return data;
    }

    /**
     * post
     *
     * @param url    地址
     * @param params 参数
     * @return String
     */
    public static String sendPost(String url, Map<String, String> params) {
        try {
            org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            String paramJson = JSONObject.toJSONString(params);
            log.info("请求参数：{}", paramJson);
            return httpCommon(params, post, httpClient);
        } catch (Exception E) {
            log.error("http-post请求异常", E);
        }
        return "";
    }

    /**
     * 公共方法
     *
     * @param params     参数
     * @param post       对象
     * @param httpClient 对象
     * @return String
     * @throws Exception 异常
     */
    private static String httpCommon(Map<String, String> params, HttpPost post, org.apache.http.client.HttpClient httpClient) throws Exception {
        List<NameValuePair> nvpList = new ArrayList<>();
        Set<Map.Entry<String, String>> entrys = null;
        if (null != params && !params.isEmpty()) {
            entrys = params.entrySet();
            for (Map.Entry<String, String> entry : entrys) {
                nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        InputStream is = null;
        post.setEntity(new UrlEncodedFormEntity(nvpList, SystemConstant.CHARSET_UTF8));
        HttpResponse response;
        response = httpClient.execute(post);
        int code = response.getStatusLine().getStatusCode();
        if (200 == code) {
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                entity = new BufferedHttpEntity(entity);
                is = entity.getContent();
                byte[] read = new byte[1024];
                byte[] all = new byte[0];
                int num;
                while ((num = is.read(read)) > 0) {
                    byte[] temp = new byte[all.length + num];
                    System.arraycopy(all, 0, temp, 0, all.length);
                    System.arraycopy(read, 0, temp, all.length, num);
                    all = temp;
                }
                return new String(all, StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    /**
     * post
     *
     * @param params 参数
     * @param strURL 地址
     * @return String
     */
    public static String post(String strURL, String params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(10000);
//            connection.setRequestProperty("AccessToken", "15a7183809f1d392657dbd7ee2c995b3");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
            out.append(params);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient的post方法出错：", e.getMessage());
        }
        return "调用失败！";
    }

    /**
     * post
     *
     * @param params 参数
     * @param strURL 地址
     * @param headers headers
     * @return String
     */
    public static String  post(String strURL, String params, Map<String,String> headers) {
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            if(null != headers && headers.size() > 0) {
                headers.forEach((k,v) -> {
                    connection.setRequestProperty(k, v);
                });
            }

            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
            out.append(params);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient的post方法出错：", e.getMessage());
        }
        return "调用失败！";
    }

    /**
     * get
     *
     * @param url 地址
     * @param params 参数
     * @param token token
     * @param secertKey 密钥
     * @return string
     */
    public static String get(String url, String params, String token, String secertKey) {
        CloseableHttpResponse response = null;
        String returnString = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            Map<String,Object> map = JsonUtil.jsonToMap(params);
            // 创建 url
            URIBuilder builder = new URIBuilder(url);
            if (null != map) {
                for (String key : map.keySet()) {
                    builder.addParameter(key, map.get(key) + "");
                }
            }
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader(SystemConstant.XTOKEN,token);
            httpGet.addHeader(SystemConstant.SECERTKEY,secertKey);
            log.warn("================>get请求地址:{}", url);
            response = client.execute(httpGet);
            log.warn("================>get返回报文状态:{}", response.getStatusLine().getStatusCode());
            if (200 == response.getStatusLine().getStatusCode()) {
                returnString = EntityUtils.toString(response.getEntity(), SystemConstant.CHARSET_UTF8);
            }
            log.warn("================>get返回报文:{}", returnString);

        } catch (IOException e) {
            log.error("get请求异常：{}", e);
        } catch (URISyntaxException e) {
            log.error("get请求异常：{}", e);
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("释放资源异常：{}", e);
            }
        }
        return returnString;
    }

    /**
     * get
     *
     * @param url 地址
     * @return string
     */
    public static String get(String url, String token) {
        String returnString = get(url, null, token, null);
        return returnString;
    }

}
