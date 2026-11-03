package com.work.ppm.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 调用接口通用类
 */
public class WebConnect {

    /**
     * 接口通用方法
     * @param url
     * @param content
     * @param contentType
     * @param timeout
     * @return String
     */
    public  String connect(String url,String content,String contentType,int timeout){

        HttpURLConnection conn=null;
        OutputStream out=null;
        BufferedReader in=null;
        InputStreamReader reader = null;
        InputStream inputStream=null;
        String result="";
        try{
            URL u=new URL(url);
            conn=(HttpURLConnection)u.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(timeout);

            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            isContent(contentType, conn);
            conn.connect();
            out =conn.getOutputStream();
            if(content!=null){
                out.write(content.getBytes("utf-8"));
            }
            inputStream = conn.getInputStream();
            reader = new InputStreamReader(inputStream,"utf-8");
            //获取数据
            in=new BufferedReader(reader);
            String line;
            while((line=in.readLine())!=null){
                result+=line;
            }
        }catch(Exception e){
            e.printStackTrace();
            result="error:WebConnect错误"+e.getMessage();
        }finally{
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    /**
     * 判断数据
     * @param contentType
     * @param conn
     */
    private void isContent(String contentType, HttpURLConnection conn) {
        if(contentType==null){
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }else{
            conn.setRequestProperty("Content-Type", contentType);
        }
    }

    /**
     *链接同时获取返回内容
     * @return String
     **/
    public  String connect(String url,String content,String contentType) {
        return connect(url,content,contentType,30*1000);
    }
}
