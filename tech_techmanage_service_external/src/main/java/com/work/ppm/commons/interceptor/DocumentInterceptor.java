package com.work.ppm.commons.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 静态资源拦截器
 */
@Slf4j
public class DocumentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.error("参数-Object:{}", handler);
        // 获取地址
        String url = request.getRequestURI();
        // 只能访问图片，如果要下载其他文件需要有权限
        String imageSuffix = url.substring(url.lastIndexOf(".")+1);
        List<String> suffixList = new ArrayList<>();
        suffixList.add("jpg");
        suffixList.add("jpeg");
        suffixList.add("png");
        suffixList.add("gif");
        suffixList.add("bmp");
        if(suffixList.contains(imageSuffix.toLowerCase())) {
            return true;
        }

        return false;
    }

}
