package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.resourceplat.ResNoticeLogDto;
import com.work.ppm.application.model.resourceplat.ResNoticeConfig;
import com.work.ppm.application.model.resourceplat.ResNoticeLog;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 资源配置管理
 */
public interface IResNoticeLogService {

    /**
     * 分页查询
     * @param modelData 对象
     * @param pager       分页对象
     */
    void queryPager(ResNoticeLogDto modelData, Pager pager);

    /**
     * 列表查询
     * @param modelData 对象
     * @return List<ResNoticeConfig>
     */
    List<ResNoticeConfig> queryList(ResNoticeLogDto modelData);

    /**
     * 保存
     * @param model 对象
     */
    void save(ResNoticeLog model);

    /**
     * 保存/更新
     * @param modelData
     */
    void save(ResNoticeLogDto modelData);

    /**
     * 删除
     * @param modelData
     */
    void delete(ResNoticeLogDto modelData);

}
