package com.work.ppm.commons.utils;


import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import org.springframework.stereotype.Component;

/**
 * 字典工具类
 */
@Component
public class DictUtil {
    /**
     * 字典服务类
     */
    private static IEosDictEntryService dictEntryService;

    /**
     * 获取字典名称
     * @Param [dictTypeId, dictId]
     * @return java.lang.String
     **/
    public static String getDictName(String dictTypeId,String dictId){
        EosDictEntry eosDictEntry = getIEosDictEntryService().getTypeidForDetail(dictTypeId, dictId);
        return eosDictEntry == null?null:eosDictEntry.getDictname();
    }

    /**
     * 获得字典服务
     * @return EosDictService
     */
    public static IEosDictEntryService getIEosDictEntryService(){
        if(dictEntryService==null){
            dictEntryService=(IEosDictEntryService)SpringUtil.getBean("eosDictEntryService");
        }
        return dictEntryService;
    }

}
