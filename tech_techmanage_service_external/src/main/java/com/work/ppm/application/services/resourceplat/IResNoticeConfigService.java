package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.resourceplat.ResNoticeConfigDto;
import com.work.ppm.application.model.resourceplat.ResNoticeConfig;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
/**
 * service
 */
public interface IResNoticeConfigService {

    /**
     * 分页查询
     * @param modelData 对象
     * @param pager       分页对象
     */
    void queryPager(ResNoticeConfigDto modelData, Pager pager);

    /**
     * 列表查询
     * @param modelData 对象
     * @return List<ResNoticeConfig>
     */
    List<ResNoticeConfig> queryList(ResNoticeConfigDto modelData);

    /**
     * 保存/更新
     * @param modelData
     */
    void save(ResNoticeConfigDto modelData);

    /**
     * 删除
     * @param modelData
     */
    void delete(ResNoticeConfigDto modelData);

    /**
     * 执行通知
     */
    void doNotice();

}
