package com.work.ppm.application.oss.utils;

import com.component.work.fileupload.common.result.ResultCode;
import com.work.ppm.application.dto.inforTechOut.OssObjectVo;
import com.work.ppm.application.oss.mercury.Mercuryoa;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Component
@Slf4j
public class FileManagerOss {
    /**
     * 对象存储配置
     */
    private static Mercuryoa mercuryoa;

    /**
     * 缓冲区大小
     */
    private static final int BUFF_SIZE = 2 * 1024;

    @Autowired
    public void setMercuryoa(Mercuryoa mercuryoa) {
        FileManagerOss.mercuryoa = mercuryoa;
    }

    public static String createObjectId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 4) + "/" + uuid.substring(4, 6) + "/" + uuid.subSequence(6, uuid.length());
    }

    public static boolean uploadobject(MultipartFile multipartFile, OssObjectVo ossObject, String endpointBucket, String publicNginxXJHG) {
        log.error("上传文件即将开始！客户端地址：{}，ObjectId为：{}", multipartFile.getOriginalFilename(), ossObject.getObjectId());
        byte[] data = null;
        OutputStream out = null;
        HttpURLConnection connection = null;
        try {
            data = multipartFile.getBytes();
            String httpUrl = publicNginxXJHG + endpointBucket + "/" + ossObject.getObjectId();
            String[] perUrl = endpointBucket.split("/");
            String uri = "/" + perUrl[1] + "/" + ossObject.getObjectId();
            Map<String, String> customerHeaders = new HashMap();
            String date = generateDate();
            String auth = getAuthHeader("PUT", uri, customerHeaders, date);

            log.info("FileManager_oss.uploadobject 请求方法: PUT");
            log.info("HTTP 请求 URL: " + httpUrl);
            /**
             * 创建url
             */
            URL url = new URL(httpUrl);
            /**
             * 创建connection
             */
            connection = (HttpURLConnection) url.openConnection();
            /**
             * 设置HttpURLConnection信息 其实主要是设置HTTP HEADER
             */
            connection.setRequestMethod("PUT"); // 指定使用PUT请求方式
            connection.setDoInput(true); // 向连接中写入数据
            connection.setDoOutput(true); // 从连接中读取数据
            connection.setUseCaches(false); // 禁止缓存
            connection.setInstanceFollowRedirects(true); // 自动执行HTTP重定向
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", auth);
            connection.setRequestProperty("Date", date);
            if (data != null && data.length > 0) {
                out = connection.getOutputStream(); // 获取输出流
                out.write(data);                 // 将要传递的数据写入数据输出流
                out.flush();                        // 刷新缓存
                out.close();                        // 关闭数据输出流
            }
            // 打印响应信息
            log.info("HTTP 响应状态码: " + connection.getResponseCode());
            if (connection.getResponseCode() == 200) { // 判断是否创建成功
                {
                    // 通过数据签名验证验证本地数据与上传成功的数据一致
                    String etag = connection.getHeaderField("ETag");
                    String localEtag = "\"" + DigestUtils.md5Hex(data) + "\""; // 此处为MD5计算，无需第三方lib
                    if (etag.equals(localEtag)) {
                        log.info("上传成功！");
                        return true;
                    } else {
                        log.error("上传失败！");
                        return false;
                    }
                }
            } else {
                log.error("上传失败！失败代码：{}", connection.getResponseCode());
                return false;
            }

        } catch (MalformedURLException e) {
            log.error("URL格式错误: {}", e);
            e.printStackTrace();
        } catch (ProtocolException e) {
            log.error("协议错误: {}", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("I/O错误: {}", e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("", e);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return true;
    }

    public static void download(String objectId, String originName, String endpointBucket, String publicNginxXJHG, HttpServletResponse response) throws Exception {
        response.setContentType("application/x-download");
        //设置响应头
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(originName, "UTF-8"));
        response.setHeader("Cache-Control", "no-cache");
        byte[] buff = new byte[BUFF_SIZE];
        OutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        HttpURLConnection connection = null;
        InputStream in = null;
        int len = 0;
        try {
            String[] perUrl = endpointBucket.split("/");
            //endpoint-bucket参数是否有/来判断是否走非国产/国产集群
            String uri = "/" + perUrl[1] + "/" + objectId;
            //国产化改造则修改该地址
//            String httpUrl = (perUrl.length==1?mercuryoa.getPublicNginxXeos():mercuryoa.getPublicNginxXJHG())
//                    + endpointBucket + "/" + objectId;
            String httpUrl = publicNginxXJHG + endpointBucket + "/" + objectId;
            Map<String, String> customerHeaders = new HashMap();
            String date = generateDate();
            String auth = getAuthHeader("GET", uri, customerHeaders, date);
            log.info("认证字段：" + auth);
            log.info("FileManager_oss.download 请求方法: GET");
            log.info("HTTP 请求 URL: " + httpUrl);

            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true); // 向连接中写入数据
            connection.setDoOutput(true); // 从连接中读取数据
            connection.setUseCaches(false); // 禁止缓存
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", auth);
            connection.setRequestProperty("Date", date);
            in = connection.getInputStream();

            // 打印响应信息
            log.info("HTTP 响应状态码: " + connection.getResponseCode());
            log.info("开始通讯");

            bis = new BufferedInputStream(in);
            while ((len = bis.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            log.error("对象存储无该ObjectID" + objectId + "文件" + e.getMessage());
            e.printStackTrace();
            throw new BizException(e);
        } catch (IOException e) {
            log.error("对象存储接口失败" + objectId + "文件" + e.getMessage());
            e.printStackTrace();
            throw new BizException(e);
        } finally {
            // 尝试读取错误响应
            if (connection != null) {
                try (InputStream errorStream = connection.getErrorStream()) {
                    if (errorStream != null) {
                        String errorBody = IOUtils.toString(errorStream, StandardCharsets.UTF_8);
                        log.error("HTTP 错误响应正文: {}", errorBody);
                    }
                }
            }
            IOUtils.close(in, out, bis);
            connection.disconnect();
        }
    }

    /**
     * 生成日期
     *
     * @return String
     */
    public static String generateDate() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
        return now.format(formatter);
    }

    /**
     * 加密认证
     *
     * @param method
     * @param path
     * @param customerHeaders
     * @param date
     * @return string
     */
    public static String getAuthHeader(String method, String path, Map<String, String> customerHeaders, String date) {
        StringBuilder fullPath = new StringBuilder(path);
        //默认为空，后续有需要可添加
//        if (query != null && !query.isEmpty()) {
//            fullPath.append("?").append(query);
//        }
        //使用默认值，后续有需要在赋值
        String contentType = "";
        Map<String, String> dateHeaders = new HashMap();
        dateHeaders.put("Date", date);
        String canonicalizedHeaders = getCanonicalizedHeaders(customerHeaders, dateHeaders);
        String stringToSign = method + "\n\n" + contentType + "\n" + canonicalizedHeaders + fullPath;

//        byte[] signingKey;
        String signature = "";
        try {
//            Charset utf = Charset.forName("UTF-8");
//            signingKey = secretKey.getBytes(utf);
            byte[] signingKey = mercuryoa.getSecretKey().getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(signingKey, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            byte[] signatureBytes = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
//            byte[] signatureBytes = mac.doFinal(stringToSign.getBytes(utf));

            signature = Base64.getEncoder().encodeToString(signatureBytes);
//		    BASE64Encoder encoder = new BASE64Encoder();
//	        signature = encoder.encode(signatureBytes);
//            signature = Base64.encodeBase64String(signatureBytes);
        } catch (InvalidKeyException e) {
            log.error("加密认证失败", e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            log.error("加密认证失败", e);
            e.printStackTrace();
        }

        return "AWS " + mercuryoa.getAccessKey() + ":" + signature;
    }

    /**
     * 加密字段预处理
     *
     * @param customerHeaders
     * @param dateHeaders
     * @return String
     */
    private static String getCanonicalizedHeaders(Map<String, String> customerHeaders, Map<String, String> dateHeaders) {
        Map<String, String> sortedHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sortedHeaders.putAll(customerHeaders);

        StringBuilder canonicalizedHeaders = new StringBuilder();

        for (String value : dateHeaders.values()) {
            canonicalizedHeaders.append(value).append("\n");
        }

        for (Map.Entry<String, String> entry : sortedHeaders.entrySet()) {
            canonicalizedHeaders.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return canonicalizedHeaders.toString();
    }

}
