package org.work.utils;

import org.work.constant.Constant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class FileUtil {
    /**
     * 普通获取参数
     *
     * @param param
     * @return
     */
    public static String getParam(String param) {
        return Constant.PLACEHOLDER_START + param + Constant.PLACEHOLDER_END;
    }

    /**
     * XML 获取参数
     *
     * @param param
     * @return
     */
    public static String getParamXml(String param) {
        return Constant.PLACEHOLDER_XML_START + param + Constant.PLACEHOLDER_XML_END;
    }

    /**
     * Constant.
     * 公共部分
     *
     * @param context
     * @return
     */
    public static String common(String context) {
        context = context.replaceAll(getParam("author"), System.getProperty("user.name"));
        context = context.replaceAll(getParam("date"), DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        return context;
    }

    /**
     * 读取文件并返回内容
     *
     * @param fileName 模板名称
     * @return
     * @throws IOException
     */
    public static String readertext(String fileName) throws IOException {
        FileReader reader = null;
        BufferedReader br = null;
        try {
            // 读取模板内容
            String url = getTemplateUrl();
            String path = url + Constant.FILE_PATH + fileName + Constant.TEMP_SUFF;
            File file = new File(path);
            reader = new FileReader(file);
            br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            // 内容读取
            while (br.ready()) {
                sb.append(br.readLine()).append("\n");
            }
            return common(sb.toString());
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            br.close();
            reader.close();
        }
    }

    /**
     * 获取模板路径
     *
     * @return
     */
    private static String getTemplateUrl() {
        URL url = FileUtil.class.getResource("/");
        String file = url.getFile();
        StringBuilder sb = new StringBuilder(file.substring(1));
        String temUrl = sb.toString();
        return temUrl;
    }

    /**
     * 写入文件
     *
     * @param context
     * @param action
     * @throws IOException
     */
    public static void writeContext(String context, String action) throws IOException {
        writeContext(context, action, Constant.JAVA);
    }

    /**
     * 写入文件
     *
     * @param context
     * @param action
     * @param suffix
     * @throws IOException
     */
    public static void writeContext(String context, String action, String suffix) throws IOException {
        // 输出文件
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File dirFile = new File(Constant.CREATE_JAVA_DIR);
            if (!dirFile.isDirectory()) {
                dirFile.mkdirs();
            }
            fw = new FileWriter(Constant.CREATE_JAVA_DIR + "\\" + action + suffix);
            bw = new BufferedWriter(fw);
            bw.write(context);
            bw.flush();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            bw.close();
            fw.close();
        }

    }
}
