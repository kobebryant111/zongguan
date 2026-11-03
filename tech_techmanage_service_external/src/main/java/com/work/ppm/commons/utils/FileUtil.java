package com.work.ppm.commons.utils;

import com.google.common.collect.Lists;
import com.work.ppm.commons.config.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具
 *
 */
@Slf4j
public class FileUtil {

    /**
     * 创建文件
     *
     * @param bytes    字节
     * @param fileName 文件名称
     */
    public static void getFile(byte[] bytes, String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            fos.close();
        }
    }

    /**
     * 解析parseTxt文本
     *
     * @param filePath 文件路径
     * @throws IOException
     */
    public static List<String> parseTxt(String filePath) throws IOException {
        List<String> result = null;
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream inputStream = null;
            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;
            try {
                String lineText; //行记录
                result = Lists.newArrayList();
                inputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                // 遍历读取数据
                while (null != (lineText = bufferedReader.readLine())) {
                    result.add(lineText);
                }
            } catch (Exception e) {
                throw new IOException(e);
            } finally {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            }
        }
        return result;
    }

    /**
     * 将文件发送到前端
     *
     * @param file 对象
     * @param res  Response
     */
    public static void responseTo(File file, HttpServletResponse res) {
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("content-Disposition", "attachment:filename=" + file.getName());
        byte[] buff = new byte[2048];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            log.error("FileUtil", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ie) {
                    log.error("FileUtil", ie);
                }
            }
        }
    }

    /**
     * 将文件转base64 加密
     *
     * @param path 文件路径
     * @return string
     */
    public static String encodeBase64File(String path) {
        byte[] buffer = new byte[0];
        FileInputStream inputFile = null;
        try {
            File file = new File(path);
            inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (Exception e) {
            log.error("IO异常：", e);
        } finally {
            try {
                if (inputFile != null) {
                    inputFile.close();
                }
            } catch (IOException e1) {
                log.error("IO异常：", e1);
            }
        }
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 将文件转base64 解码
     *
     * @param base64Code 加密的base64
     * @param targetPath 解密后字符串-路径
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        FileOutputStream out = null;
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out = new FileOutputStream(targetPath);
            out.write(buffer);
        } catch (Exception e) {
            log.error("base64 解密异常");
        } finally {
            out.close();
        }
    }

    /**
     * 对象存储下载文件
     * @param objectId 对象存储id
     * @param filePath 存储文件id
     */
    public static boolean downloadObject(String ossUrl,String ossAuthorization,String objectId, String filePath){
        boolean flag = false;
        long start = System.currentTimeMillis();

        /**
         * 通过测试环境Nginx代理访问
         * 设置host的方式为： user-host
         */
        String urlStr = ossUrl + objectId;
        HttpURLConnection connection = null;
        try {
            /**
             * 创建url
             */
            URL url = new URL(urlStr);

            /**
             * 创建connection
             */
            connection = (HttpURLConnection) url.openConnection();

            /**
             * 设置HttpURLConnection信息 其实主要是设置HTTP HEADER
             */
//			connection.setRequestMethod("PUT"); // 指定使用PUT请求方式
            connection.setDoInput(true); // 向连接中写入数据
            connection.setDoOutput(true); // 从连接中读取数据
            connection.setUseCaches(false); // 禁止缓存
            connection.setRequestProperty("Authorization",ossAuthorization);

            InputStream in = connection.getInputStream();

            byte[] data = new byte[1];
            List<Byte> bytes = new ArrayList<Byte>();
            // 从输入流中逐个字节读取
            while (in.read(data) != -1) {
                bytes.add(data[0]);
            }
            // 关闭数据输出流
            in.close();

            if (bytes.size() > 0) {
                Byte[] tempData = bytes.toArray(new Byte[]{});
                data = new byte[tempData.length];
                for (int j = 0; j < tempData.length; j++) {
                    data[j] = tempData[j];
                }

                // 写文件
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    fos.write(data);
                    log.info("{}存储成功。", filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            System.out.println(connection.getResponseCode()+":"+connection.getResponseMessage());
            // 判断是否创建成功
            if (connection.getResponseCode() == 200) {
                {
                    // 通过数据签名验证验证本地数据与上传成功的数据一致
                    String etag = connection.getHeaderField("ETag");
                    String localEtag = "\"" + DigestUtils.md5Hex(data) + "\""; // 此处为MD5计算，无需第三方lib
                    if (etag.equals(localEtag)) {
                        log.info("succ,download objectId: " + objectId);
                        flag = true;
                    } else {
                        log.info("调用RESTFUL服务下载对象成功，但是ETag校验content失败！");
                    }
                }
            } else {
//                System.out.println("fail");
                log.info("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        long end = System.currentTimeMillis();
        log.info("time cost: " + ( end - start) + " ms");
        return flag;
    }

    /**
     * 图片转byte类型
     * @param url 图片路径
     * @return byte[]
     */
    public static byte[] imageToBytes(String url){
        byte[] bytes = new byte[0];
        try (FileImageInputStream input = new FileImageInputStream(new File(url))){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead;
            while ((numBytesRead = input.read(buf)) != -1){
                output.write(buf,0,numBytesRead);
            }
            bytes = output.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }



    /**
     * 获得文件格式
     * @param fileName
     * @return String
     */
    public static String getFileType(String fileName) {
        AssertUtil.notNull(fileName,"文件路径不能为空");
        String[] strs = fileName.split("\\.");
        return strs[strs.length-1];
    }

    /**
     * 获得上传文件路径
     * @param bizType
     * @param fileType
     * @return String
     */
    public static String generateFilePath(String bizType,String fileType){
//        String basePath = DictUtil.getDictName("SYSTEM_CONFIG",bizType+"_PATH");
//        if(StringUtil.isStringNotEmpty(basePath)){
//            SysConfig sysConfig = SpringUtil.getBean(SysConfig.class);
//            basePath = sysConfig.getFileUploadPath();
//            basePath = FilenameUtils.concat(basePath,bizType);
//        }
        SysConfig sysConfig = SpringUtil.getBean(SysConfig.class);
        String basePath = sysConfig.getFileUploadPath();
        basePath = FilenameUtils.concat(basePath,bizType);
        basePath = FilenameUtils.concat(basePath,DateUtil.getCurrentDate().replaceAll("-",""));
        File file = new File(basePath);
        if(!file.exists()){
            file.mkdirs();
        }
        String fileName = String.format("%s.%s",StringUtil.getPrimarykey(),fileType);
        return  FilenameUtils.concat(basePath,fileName);
    }

    /**
     * 获得上传文件路径
     *
     * @return String
     */
    public static String generateFilePath(String fileType){
        SysConfig sysConfig = SpringUtil.getBean(SysConfig.class);
        String basePath = sysConfig.getFileUploadPath();
        basePath = FilenameUtils.concat(basePath, DateUtil.getCurrentDate().replaceAll("-",""));
        File file = new File(basePath);
        if(!file.exists()){
            file.mkdirs();
        }
        String fileName = String.format("%s.%s", StringUtil.getPrimarykey(), fileType);
        return  FilenameUtils.concat(basePath, fileName);
    }

}
