package com.work.ppm.commons.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Map;

/**
 * http通用类
 * @date 2020/9/27 15:30
 * @version 1.0
 */
public class HttpClientUtil {

    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager connectionManager;

    /**
     * 字符集
     */
    private static Charset utf8 = Charset.forName("UTF-8");



    /**
     * 15秒
     */
    public static final int TIME_OUT = 150 * 1000;

    /**
     * 重复请求次数
     */
    private static final int RETRY_COUNT = 3;

    /**
     * HttpType
     */
    public enum HttpType {
        /**
         * get
         */
        GET,
        /**
         * DELETE
         */
        DELETE,
        /**
         * POST
         */
        POST,
        /**
         * PUT
         */
        PUT,
        /**
         * PATCH
         */
        PATCH
    }

    /**
     * 初始化
     */
    private static void init() {
        if (connectionManager == null) {
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            registryBuilder.register("http", new PlainConnectionSocketFactory());
            //指定信任密钥存储对象和连接套接字工厂
            registryBuilder.register("https", createSSLConnSocketFactory());
            Registry<ConnectionSocketFactory> registry = registryBuilder.build();
            connectionManager = new PoolingHttpClientConnectionManager(registry);
            connectionManager.setMaxTotal(1000); // 整个连接池最大连接数
            connectionManager.setDefaultMaxPerRoute(500); // 每路由最大连接数，默认值是2
        }
    }


    /**
     * 请求超时
     *
     * @return requestConfig
     */
    private static RequestConfig getTimeOutConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIME_OUT)
                .setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
        return requestConfig;
    }


    /**
     * 重试机制
     *
     * @return httpRequestRetryHandler
     */
    private static HttpRequestRetryHandler getHttpRequestRetryHandler() {
        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                // 如果已经重试了3次，就放弃
                if (executionCount > RETRY_COUNT) {
                    return false;
                }
                // 如果服务器丢掉了连接，那么就重试
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                // 不要重试SSL握手异常
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }
                // 超时
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                // 目标服务器不可达
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                // 连接被拒绝
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                // SSL握手异常
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext
                        .adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
        return httpRequestRetryHandler;
    }



    /**
     * 创建SSL安全连接
     * @return sslsf
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            //信任所有的证书
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, (arg0, arg1) -> true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    /**
     * 通过连接池获取HttpClient
     * @return CloseableHttpClient
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(connectionManager)
                .setRetryHandler(getHttpRequestRetryHandler()).build();
    }


    /**
     * get请求
     * @param url
     * @param headers
     * @param params
     * @return HttpResponse
     */
    public static HttpResponse getHttp(String url, Map<String, String> headers, Map<String, String> params) {
        return httpRequest1(HttpType.GET, url, headers, params);
    }


    /**
     * 请求
     * @param httpType
     * @param url
     * @param headers
     * @param params
     * @return HttpResponse
     */
    public static HttpResponse httpRequest1(HttpType httpType, String url,
                                            Map<String, String> headers, Map<String, String> params) {

        URIBuilder ub;
        try {
            ub = new URIBuilder(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Url can't parse correctly", e);
        }

        if (params != null) {
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.addParameters(pairs);
        }

        HttpRequestBase httpRequest;
        try {
            switch (httpType) {
                case GET:
                    httpRequest = new HttpGet(ub.build());
                    break;
                case DELETE:
                    httpRequest = new HttpDelete(ub.build());
                    break;
                default:
                    throw new IllegalArgumentException("" + httpType);
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("");
        }
        httpRequest.setConfig(getTimeOutConfig());
        if (headers != null) {
            for (Map.Entry<String, String> param : headers.entrySet()) {
                httpRequest.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        return getResult1(httpRequest);
    }

    /**
     * post 请求
     * @param url
     * @param headers
     * @param params
     * @return String
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) {
        return httpRequest(HttpType.POST, url, headers, params, null);
    }

    /**
     * post 请求
     * @param url
     * @param headers
     * @param data
     * @return String
     */
    public static String post(String url, Map<String, String> headers, String data) {
        return httpRequest(HttpType.POST, url, headers, null, data);
    }

    /**
     * put请求
     * @param url
     * @param headers
     * @param params
     * @return String
     */
    public static String put(String url, Map<String, String> headers, Map<String, String> params) {
        return httpRequest(HttpType.POST, url, headers, params, null);
    }


    /**
     * 用于POST、PUT、PATCH
     *
     * @param httpType
     * @param url      请求地址
     * @param headers  请求头
     * @param params   请求参数
     * @param data     文本、json等格式的数据
     * @return String
     */
    public static String httpRequest(HttpType httpType, String url,
                                     Map<String, String> headers, Map<String, String> params, String data) {
        if (params != null && params.size() != 0 && !StringUtils.isBlank(data)) {
            throw new IllegalArgumentException("params or data can only have one");
        }

        HttpEntityEnclosingRequestBase httpRequest;
        switch (httpType) {
            case POST:
                httpRequest = new HttpPost(url);
                break;
            case PUT:
                httpRequest = new HttpPut(url);
                break;
            case PATCH:
                httpRequest = new HttpPatch(url);
                break;
            default:
                throw new IllegalArgumentException("" + httpType);
        }
        httpRequest.setConfig(getTimeOutConfig());

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> param : headers.entrySet()) {
                httpRequest.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        if (params != null && !params.isEmpty()) {
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            httpRequest.setEntity(new UrlEncodedFormEntity(pairs, utf8));
        }

        if (!StringUtils.isBlank(data)) {
            httpRequest.setEntity(new StringEntity(data, utf8));
        }

        return getResult(httpRequest);
    }

    /**
     * 转化方法
     * @param params
     * @return String
     */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }

        return pairs;
    }

    /**
     * 处理Http请求
     * @return String
     */
    private static String getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient;
        httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity, utf8);
                response.close();
                return result;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 对接口返回数据的编码格式做处理
     *
     * @param request
     * @return response
     */

    private static HttpResponse getResult1(HttpRequestBase request) {

        CloseableHttpClient httpClient;
        httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (Exception e) {
            throw new IllegalArgumentException("request failed", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }


}
