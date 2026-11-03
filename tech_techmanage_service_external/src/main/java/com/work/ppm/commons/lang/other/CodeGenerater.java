package com.work.ppm.commons.lang.other;

import com.work.ppm.application.model.othermanager.PpmUtilCodegenerate;
import com.work.ppm.application.services.othermanager.IPpmUtilCodegenerateService;
import com.work.ppm.commons.utils.SpringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流水号 工具
 */
public class CodeGenerater {
    /**
     * 接口
     */
    private static IPpmUtilCodegenerateService ppmUtilCodegenerateService =
            (IPpmUtilCodegenerateService) SpringUtil.getBean("ppmUtilCodegenerateService");
    /**
     * 唯一流水号
     * @param prefix 流水号前缀
     * @param length 长度
     * @param format 时间格式
     * @return String
     */
    public synchronized  static String generate(String prefix,int length,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date = sdf.format(new Date());
		String newPrefix = prefix.toUpperCase() + date;
		StringBuffer sb = new StringBuffer(newPrefix);
		long nextVal = 1L;
        PpmUtilCodegenerate item = ppmUtilCodegenerateService.queryDetail(newPrefix);
        if(null != item) {
            nextVal = item.getValue().longValue() + 1L;
            ppmUtilCodegenerateService.updatePpmUtilCodegenerate(newPrefix,nextVal);
        } else {
            ppmUtilCodegenerateService.addPpmUtilCodegenerate(newPrefix, nextVal);
        }
        int s = prefix.length() + date.length()
                + String.valueOf(nextVal).length();
        if (s > length) {
            return "0";
        }
        for (; s < length; s++) {
            sb.append("0");
        }
        sb.append(nextVal);
        return sb.toString().toUpperCase();
    }
}
