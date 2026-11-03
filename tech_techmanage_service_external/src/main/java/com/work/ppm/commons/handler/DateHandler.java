package com.work.ppm.commons.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间转换器
 */
@ControllerAdvice
@Slf4j
public class DateHandler {
    /**
     * 系统初始化绑定
     * @param binder 对象
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text)  {
                String datestr = "";
                if(null == text || "".equals(text) || "null".equals(text)) {
                    setValue(null);
                } else {
                    if(text.contains("T")) {
                        text = text.replaceAll("T"," ").substring(0,text.lastIndexOf("."));
                    }
                    // yyyy-MM-dd HH:mm:ss
                    // yyyy-MM-dd
                    // HH:mm:ss
                    // HH:mm
                    String format = getDateFormat(text);
                    DateFormat dateFormat = new SimpleDateFormat(format);
                    try {
                        setValue(dateFormat.parse(text));
                    } catch (ParseException e) {
                        log.error("DateHandler", e);
                    }
                }

            }

            /**
             * 时间格式化
             * @param text 文本时间
             * @return    String
             */
            private String getDateFormat(String text) {
                String dateFormat = "";
                Pattern pattern = Pattern.compile("(\\d{4}-\\d{0,2}-\\d{2}[ ]\\d{2}:\\d{2}:\\d{2})|(\\d{4}-\\d{0,2}-\\d{2})|(\\d{2}:\\d{2}:\\d{2})|(\\d{2}:\\d{2})|(\\d{0,2}-\\d{2})");
                Matcher matcher = pattern.matcher(text);
                boolean bol = matcher.matches();
                if(bol) {
                    for (int i = 1; i <= 5 ; i++) {
                        dateFormat = matcher.group(i);
                        if(null == dateFormat) {
                            continue;
                        }
                        switch (i) {
                            case 1:
                                return "yyyy-MM-dd HH:mm:ss";
                            case 2:
                                return  "yyyy-MM-dd";

                            case 3:
                                return  "HH:mm:ss";

                            case 4:
                                return "HH:mm";

                            case 5:
                                return "MM-dd";

                            default:
                                return  "yyyy-MM-dd HH:mm:ss";
                        }
                    }
                }
                return dateFormat;
            }
        });
    }
}
