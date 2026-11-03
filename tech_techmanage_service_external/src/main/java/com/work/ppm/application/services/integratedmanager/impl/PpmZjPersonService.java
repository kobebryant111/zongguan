package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmZjPersonDto;
import com.work.ppm.application.model.integratedmanager.PpmZjPerson;
import com.work.ppm.application.services.integratedmanager.IPpmZjPersonService;
import com.work.ppm.commons.artemis.response.PersonResp;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * 闸机人员信息 接口实现类
 *
 */
@Service("ppmZjPersonService")
public class PpmZjPersonService extends BaseDaoSupper implements IPpmZjPersonService {
    /**
     * 实体类路径
     */
    private static String PPMZJPERSON = "com.work.ppm.application.model.integratedmanager.PpmZjPerson";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PpmZjPersonDto> queryList(PpmZjPersonDto ppmZjPersonDto) {
        PpmZjPerson ppmZjPerson = JsonUtil.jsonToClass(ppmZjPersonDto, PpmZjPerson.class);
        return myBatisDao.selectListT(PPMZJPERSON, ppmZjPerson, "getList");
    }

    @Override
    public void queryPager(PpmZjPersonDto ppmZjPersonDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmZjPersonDto, PpmZjPersonDto.class);
        myBatisDao.selectPager(PPMZJPERSON, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmZjPerson(PpmZjPersonDto ppmZjPersonDto) {
        PpmZjPerson ppmZjPerson = JsonUtil.jsonToClass(ppmZjPersonDto, PpmZjPerson.class);
        myBatisDao.executionPojo(PPMZJPERSON, ppmZjPerson, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmZjPersonDto queryDetail(PpmZjPersonDto ppmZjPersonDto) {
        PpmZjPerson ppmZjPerson = JsonUtil.jsonToClass(ppmZjPersonDto, PpmZjPerson.class);
        PpmZjPerson data = myBatisDao.selectClass(PPMZJPERSON, ppmZjPerson, "queryDetail");
        PpmZjPersonDto item = JsonUtil.jsonToClass(data, PpmZjPersonDto.class);
        return item;
    }

    @Override
    public int updatePpmZjPerson(PpmZjPersonDto ppmZjPersonDto) {
        PpmZjPerson ppmZjPerson = JsonUtil.jsonToClass(ppmZjPersonDto, PpmZjPerson.class);
        return myBatisDao.executionPojo(PPMZJPERSON, ppmZjPerson, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmZjPerson(PpmZjPersonDto ppmZjPersonDto) {
        PpmZjPerson ppmZjPerson = JsonUtil.jsonToClass(ppmZjPersonDto, PpmZjPerson.class);
        return myBatisDao.executionPojo(PPMZJPERSON, ppmZjPerson, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public boolean checkExitsPerson(String certificateNo) {
        PpmZjPersonDto ppmZjPersonDto = new PpmZjPersonDto();
        ppmZjPersonDto.setCertificateNo(certificateNo);
        List<PpmZjPersonDto> result = this.queryList(ppmZjPersonDto);
        if (!CollectionUtils.isEmpty(result)) {
            return true;
        }
        return false;
    }

    @Override
    public String getPersonId(String certificateNo) {
        PpmZjPerson ppmZjPerson = new PpmZjPerson();
        ppmZjPerson.setCertificateNo(certificateNo);
        PpmZjPerson result = myBatisDao.selectClass(PPMZJPERSON, ppmZjPerson, "getPersonId");
        if (!ObjectUtils.isEmpty(result)) {
            return result.getPersonId();
        }
        return null;
    }

    @Override
    public void syncPerson(List<PersonResp> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        for (PersonResp resp : result) {
            PpmZjPersonDto ppmZjPersonDto = JsonUtil.jsonToClass(resp, PpmZjPersonDto.class);
            PpmZjPersonDto entity = this.queryDetail(ppmZjPersonDto);
            if (ObjectUtils.isEmpty(entity)) {
                this.addPpmZjPerson(ppmZjPersonDto);
            } else {
                this.updatePpmZjPerson(ppmZjPersonDto);
            }
        }
    }
}
