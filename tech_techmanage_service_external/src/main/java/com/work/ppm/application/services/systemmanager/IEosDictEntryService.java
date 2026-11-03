package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.EosDictEntryDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.EosDictEntry;

import java.util.List;
/**
 * 业务字典从表
 */
public interface IEosDictEntryService {
    /**
     * 公用方法 根据id查询集合
     * @param dicttypeid 主表编号
     * @return List<EosDictEntry>
     */
    List<EosDictEntry> getTypeidForList(String dicttypeid) ;

    /**
     * 查询业务字典
     * @param dicttypeid 主表编号
     * @param dictid 业务属性key
     * @return EosDictEntry
     */
    EosDictEntry getTypeidForDetail(String dicttypeid,String dictid) ;

    /**
     * 查询业务字典
     * @param dicttypeid 主表编号
     * @param dictid 业务属性key
     * @return EosDictEntry
     */
    String getDictName(String dicttypeid,String dictid) ;

    /**
     * 查询业务字典
     * @param dictName 主表编号
     * @param dictTypeId 业务属性key
     * @return String
     */
    EosDictEntry getTypenameForDetail(String dictName,String dictTypeId) ;
    /**
     * 公用方法 根据id集合 查询 集合
     * @param dicttypeid 主表编号
     * @return List<EosDictEntry>
     */
    List<EosDictEntry> getTypeidForList(String[] dicttypeid) ;

    /**
     * 公用方法 根据id集合 查询 集合
     * @param eosDictEntry 主表编号
     * @return List<EosDictEntry>
     */
    List<EosDictEntryDto> getList(EosDictEntry eosDictEntry) ;
    /**
     * 分页
     * @param eosDictEntry 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(EosDictEntry eosDictEntry, Pager pager) ;

    /**
     * 查询详情
     * @param eosDictEntry 对象
     * @return EosDictEntry
     */
    @Deprecated
    EosDictEntry queryDetail(EosDictEntry eosDictEntry) ;

    /**
     * 更新
     * @param eosDictEntry 对象
     * @return Integer
     */
    Integer updateEosDictEntry(EosDictEntry eosDictEntry) ;

    /**
     * 删除根据dictid和DICTTYPEID进行删除
     * @param eosDictEntry 对象
     * @return Integer
     */
    Integer deleteEosDictEntry(EosDictEntry eosDictEntry) ;

    /**
     * 根据ip 获取生产域名
     * @param ip ip
     * @return String
     */
    String getDomainName(String ip);

    /**
     * 获取系统环境 OA VD 等
     * @param ip ip
     * @return String
     */
    String getProdEnv(String ip);

    /**
     * 添加详细业务字典
     * @param eosDictEntry
     * @return Integer
     */
    Integer addEosDictEntry(EosDictEntry eosDictEntry);

    /**
     * 查询所有字典
     * @return List<EosDictEntry>
     */
    List<EosDictEntry> queryAll();
}
