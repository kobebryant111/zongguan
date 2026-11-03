package com.work.ppm.application.quartz.trigger.artemis;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.integratedmanager.IPpmZjPersonService;
import com.work.ppm.commons.artemis.ArtemisPageInfo;
import com.work.ppm.commons.artemis.request.PersonReqt;
import com.work.ppm.commons.artemis.response.PersonResp;
import com.work.ppm.commons.artemis.service.ArtemisService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * 闸机人员信息同步任务
 *
 */
@Slf4j
public class ArtemisPersonSyncJob implements IQuartzExecutorJob {

    // 分页大小
    public static final Integer PAGE_SIZE = 20;

    /**
     * 海康卫视闸机服务
     */
    private ArtemisService artemisService = SpringUtil.getBean(ArtemisService.class);

    private IPpmZjPersonService ppmZjPersonService = SpringUtil.getBean(IPpmZjPersonService.class);

    @Override
    public void executeInternal() throws JobExecutionException {
        try {

            log.warn("闸机人员信息同步任务开始");
            PersonReqt personReqt = new PersonReqt();
            personReqt.setPageNo(1);
            personReqt.setPageSize(PAGE_SIZE);
            ArtemisPageInfo<PersonResp> pageInfo = artemisService.getPersonV2(personReqt);
            if (null != pageInfo) {
                ppmZjPersonService.syncPerson(pageInfo.getResult());
                // 同步更多分页数据
                this.loopReqGetPerson(pageInfo, personReqt);
            }
            log.warn("闸机人员信息同步任务结束");
        } catch (Exception e) {
            log.warn("闸机人员信息同步任务异步", e);
        }

    }

    /**
     * 循环同步闸机门禁点
     *
     * @param pageInfo
     * @param personReqt
     */
    void loopReqGetPerson(ArtemisPageInfo pageInfo, PersonReqt personReqt) {
        // 如果有多页时遍历请求数据
        if (pageInfo.getTotal() > PAGE_SIZE) {
            int totalPage = new Double(Math.ceil(1.0 * pageInfo.getTotal() / PAGE_SIZE)).intValue();
            totalPage += 1;//排除第一页
            for (int i = 2; i <= totalPage; i++) {
                personReqt.setPageNo(i);
                pageInfo = artemisService.getPersonV2(personReqt);
                if (null != pageInfo) {
                    ppmZjPersonService.syncPerson(pageInfo.getResult());
                }
            }
        }
    }
}
