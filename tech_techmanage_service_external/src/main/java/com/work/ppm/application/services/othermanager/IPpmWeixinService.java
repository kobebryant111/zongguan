package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.model.othermanager.PpmWeixin;
import com.work.ppm.commons.lang.queue.MobileHttpQueue;
import com.work.ppm.commons.pager.Pager;

/**
 * 工作平台接口
 */
public interface IPpmWeixinService {

    /**
     * 分页
     * @param ppmWeixin 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmWeixin ppmWeixin, Pager pager) ;

    /**
     * 添加
     * @param ppmWeixin 对象
     * @return int
     */
    int addPpmWeixin(PpmWeixin ppmWeixin) ;

    /**
     * 查询详情
     * @param workitemid 工作项id
     * @return PpmWeixin
     */
    PpmWeixin queryDetail(String workitemid) ;

    /**
     * 更新
     * @param status 状态
     * @param workitemid 工作项id
     * @return Integer
     */
    Integer updatePpmWeixin(String status,String workitemid) ;

    /**
     * 删除
     * @param ppmWeixin 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmWeixin(PpmWeixin ppmWeixin) ;

    /**
     * 请求存放到队列
     * @param dto 对象
     */
    void queuePpmWeixin(MobileHttpQueue dto);
}
