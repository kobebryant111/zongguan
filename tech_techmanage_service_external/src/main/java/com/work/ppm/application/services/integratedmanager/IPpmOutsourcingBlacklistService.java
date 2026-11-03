package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBlacklistDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
  * 外包黑名单
  */
public interface IPpmOutsourcingBlacklistService {

    /**
     * 分页
     * @param ppmOutsourcingBlacklistDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto, Pager pager) ;



    /**
     * 查询详情
     * @param ppmOutsourcingBlacklistDto 对象
     * @return PpmOutsourcingBlacklistDto
     */
    PpmOutsourcingBlacklistDto queryDetail(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) ;

    /**
     * 更新
     * @param ppmOutsourcingBlacklistDto  对象
     * @return int
     */
    int updatePpmOutsourcingBlacklist(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) ;

    /**
     * 删除
     * @param ppmOutsourcingBlacklistDto 对象
     * @return int
     */
    int deletePpmOutsourcingBlacklist(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto) ;

    /**
     * 导入
     * @param file 文件
     * @param badge 登录人工号
     * @param name 登录人名称
     * @return boolean
     */
    boolean importExcel(MultipartFile file,String badge,String name);

    /**
     * 根据条件查询多条
     * @param ppmOutsourcingBlacklistDto 对象
     * @return list
     */
    List<PpmOutsourcingBlacklistDto> getList(PpmOutsourcingBlacklistDto ppmOutsourcingBlacklistDto);
}
