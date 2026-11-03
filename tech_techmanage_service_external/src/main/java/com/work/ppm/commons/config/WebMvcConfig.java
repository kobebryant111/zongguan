package com.work.ppm.commons.config;

import com.work.ppm.commons.constant.FileProperties;
import com.work.ppm.commons.interceptor.DocumentInterceptor;
import com.work.ppm.commons.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 文件属性
     */
    @Autowired
    private FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(fileProperties.getStaticAccessPath())
//                .addResourceLocations("classpath:/static/")
//                .addResourceLocations("classpath:/public")
//                .addResourceLocations("classpath:/resources")
//                .addResourceLocations("classpath:/META-INF/resources")
//                .addResourceLocations("file:" + fileProperties.getUploadPath() + "/");
        // 增加虚拟路径
        registry.addResourceHandler("/res/**","/preview/**")
                .addResourceLocations("file:"+ fileProperties.getUploadPath());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 增加拦截器
        registry.addInterceptor(new DocumentInterceptor()).addPathPatterns("/res/**");
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }
}
