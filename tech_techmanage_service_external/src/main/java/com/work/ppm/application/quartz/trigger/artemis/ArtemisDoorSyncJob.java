package com.work.ppm.application.quartz.trigger.artemis;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.integratedmanager.IPpmZjManagerService;
import com.work.ppm.commons.artemis.ArtemisPageInfo;
import com.work.ppm.commons.artemis.request.DoorReqt;
import com.work.ppm.commons.artemis.response.DoorResp;
import com.work.ppm.commons.artemis.service.ArtemisService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * 闸机门禁点同步任务
 *
 */
@Slf4j
public class ArtemisDoorSyncJob implements IQuartzExecutorJob {
    // 分页大小
    public static final Integer PAGE_SIZE = 20;

    /**
     * 海康卫视闸机服务
     */
    private ArtemisService artemisService = SpringUtil.getBean(ArtemisService.class);

    private IPpmZjManagerService ppmZjManagerService = SpringUtil.getBean(IPpmZjManagerService.class);

    @Override
    public void executeInternal() throws JobExecutionException {
        try {

            log.warn("闸机门禁点同步任务开始");
            DoorReqt doorReqt = new DoorReqt();
            doorReqt.setPageNo(1);
            doorReqt.setPageSize(PAGE_SIZE);
            ArtemisPageInfo<DoorResp> pageInfo = artemisService.getDoorV2(doorReqt);
            if (null != pageInfo) {
                ppmZjManagerService.syncDoors(pageInfo.getResult());
                // 同步更多分页数据
                this.loopReqGetDoorV2(pageInfo, doorReqt);
            }
            log.warn("闸机门禁点同步任务结束");
        } catch (Exception e) {
            log.warn("闸机门禁点同步任务异步", e);
        }

    }

    /**
     * 循环同步闸机门禁点
     *
     * @param pageInfo
     * @param doorReqt
     */
    void loopReqGetDoorV2(ArtemisPageInfo pageInfo, DoorReqt doorReqt) {
        // 如果有多页时遍历请求数据
        if (pageInfo.getTotal() > PAGE_SIZE) {
            int totalPage = new Double(Math.ceil(1.0 * pageInfo.getTotal() / PAGE_SIZE)).intValue();
            totalPage += 1;//排除第一页
            for (int i = 2; i <= totalPage; i++) {
                doorReqt.setPageNo(i);
                pageInfo = artemisService.getDoorV2(doorReqt);
                if (null != pageInfo) {
                    ppmZjManagerService.syncDoors(pageInfo.getResult());
                }
            }
        }
    }
}
