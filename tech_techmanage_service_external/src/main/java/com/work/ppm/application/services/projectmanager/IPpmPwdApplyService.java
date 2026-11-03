package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmPwdApplyDto;
import com.work.ppm.commons.ApiResult;

/**
  * 密管密码流程
  */
public interface IPpmPwdApplyService {
    /**
     * 查询详情
     * @param ppmPwdApplyDto 对象
     * @return PpmPwdApplyDto
     */
    PpmPwdApplyDto queryDetail(PpmPwdApplyDto ppmPwdApplyDto) ;

    /**
     * 更新
     * @param ppmPwdApplyDto 对象
     * @return 影响行数
     */
    int updatePpmPwdApply(PpmPwdApplyDto ppmPwdApplyDto);

    /**
     * 是否隐藏按钮
     * @param ppmPwdApplyDto 对象
     * @return ApiResult
     */
    ApiResult ifHiddenButton(PpmPwdApplyDto ppmPwdApplyDto);
}
