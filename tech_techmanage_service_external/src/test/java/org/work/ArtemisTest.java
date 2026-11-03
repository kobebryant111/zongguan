package org.work;

import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.work.ppm.commons.artemis.ArtemisPageInfo;
import com.work.ppm.commons.artemis.request.DoorReqt;
import com.work.ppm.commons.artemis.request.PersonReqt;
import com.work.ppm.commons.artemis.response.DoorResp;
import com.work.ppm.commons.artemis.response.PersonResp;
import com.work.ppm.commons.artemis.service.ArtemisService;
import org.junit.Test;


public class ArtemisTest {

    static {
        ArtemisConfig.host = "";
        ArtemisConfig.appKey = "";
        ArtemisConfig.appSecret = "";
    }


    @Test
    public void getDoorV2() {
        DoorReqt doorReqt = new DoorReqt();
        doorReqt.setPageNo(1);
        doorReqt.setPageSize(50);
        ArtemisService artemisService = new ArtemisService();
        ArtemisPageInfo<DoorResp> result = artemisService.getDoorV2(doorReqt);
        System.out.println(result);
    }


    @Test
    public void getPersonV2() {
        PersonReqt personReqt = new PersonReqt();
        personReqt.setPageNo(1);
        personReqt.setPageSize(50);
        ArtemisService artemisService = new ArtemisService();
        ArtemisPageInfo<PersonResp> result = artemisService.getPersonV2(personReqt);
        System.out.println(result);
    }


}
