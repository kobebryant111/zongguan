package com.work.ppm.commons.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Slf4j
@UtilityClass
public class PinyinUtils {

    /**
     * 将汉字转为拼音
     *
     * @param text      文本
     * @param separator 分割符
     * @return
     */
    public String getPinyin(String text, String separator) {
        char[] chars = text.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String[] s;
        String rs = "";
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                if (String.valueOf(chars[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    s = PinyinHelper.toHanyuPinyinStringArray(chars[i], format);
                    if (null != s) {
                        sb.append(s[0].substring(0, 1).toUpperCase() + s[0].substring(1)).append(separator);
                        continue;
                    }
                }
                sb.append(chars[i]);
                if ((i + 1 >= chars.length) || String.valueOf(chars[i + 1]).matches("[\\u4E00-\\u9FA5]+")) {
                    sb.append(separator);
                }
            }
            rs = sb.substring(0, sb.length() - 1);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error("汉字转为拼音失败：{}", e.getStackTrace());
        }
        return rs;
    }

}
