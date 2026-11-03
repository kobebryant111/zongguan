package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgApplyDto;
import com.work.ppm.application.dto.projectmanager.PpmPwdApplyDto;
import com.work.ppm.application.model.projectmanager.PpmPwdApply;
import com.work.ppm.application.services.projectmanager.IPpmFhyyxtbgApplyService;
import com.work.ppm.application.services.projectmanager.IPpmPwdApplyService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 密管密码
 */
@Service("ppmPwdApplyService")
public class PpmPwdApplyService extends BaseDaoSupper implements IPpmPwdApplyService {
    /**
      * 实体类路径
      */
    private static String PPMPWDAPPLY = "com.work.ppm.application.model.projectmanager.PpmPwdApply";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 分行应用变更接口
     */
    @Autowired
    private IPpmFhyyxtbgApplyService ppmFhyyxtbgApplyService;

    @Override
    public PpmPwdApplyDto queryDetail(PpmPwdApplyDto ppmPwdApplyDto) {
        PpmPwdApply ppmPwdApply = JsonUtil.jsonToClass(ppmPwdApplyDto, PpmPwdApply.class);
        PpmPwdApply data = myBatisDao.selectClass(PPMPWDAPPLY, ppmPwdApply,
        "queryDetail");
        PpmPwdApplyDto item = JsonUtil.jsonToClass(data, PpmPwdApplyDto
        .class);
        return item;
    }

    @Override
    public int updatePpmPwdApply(PpmPwdApplyDto ppmPwdApplyDto) {
        PpmPwdApply apply = JsonUtil.jsonToClass(ppmPwdApplyDto, PpmPwdApply.class);
        int row = myBatisDao.executionPojo(PPMPWDAPPLY, apply, IMyBatisDao.SQL.UPDATE, "updatePojo");
        return row;
    }

    @Override
    public ApiResult<PpmPwdApplyDto> ifHiddenButton(PpmPwdApplyDto ppmPwdApplyDto) {
        AssertUtil.notNull(ppmPwdApplyDto.getRelateinstanceid(), "【Relateinstanceid】不能为空");
        ApiResult<PpmPwdApplyDto> result = new ApiResult<>();
        boolean daochu = false;//导出
        boolean update = false;//新增/删除设备
        PpmPwdApplyDto item = this.queryDetail(ppmPwdApplyDto);
        PpmFhyyxtbgApplyDto applyDto = new PpmFhyyxtbgApplyDto();
        applyDto.setInstanceid(ppmPwdApplyDto.getRelateinstanceid());

        PpmFhyyxtbgApplyDto applyDto1 =  ppmFhyyxtbgApplyService.queryDetail(applyDto);
        Date date = new Date();
        //当前时间大于计划结束时间
        if(null != applyDto1 && date.after(applyDto.getUseendtime())) {
            daochu = false;
        } else {
            daochu = true;
            if(null == item) {
                update = true;
            }
        }
        ppmPwdApplyDto.setDaoChuBtn(daochu);
        ppmPwdApplyDto.setUpdateBtn(update);
        result.success("查询成功");
        return result;
    }
}
