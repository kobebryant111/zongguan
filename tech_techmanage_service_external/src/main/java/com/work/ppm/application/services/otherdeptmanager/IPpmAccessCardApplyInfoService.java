package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoDto;

import java.util.List;

/**
  *  监保部台账门禁卡流程详情 接口描述
  */
public interface IPpmAccessCardApplyInfoService {


    /**
     * 添加
     * @param ppmAccessCardApplyInfoDto 对象
     * @return int
     */
    int addPpmAccessCardApplyInfo(PpmAccessCardApplyInfoDto  ppmAccessCardApplyInfoDto) ;

    /**
     * 查询详情集合
     * @param ppmAccessCardApplyInfoDto 对象
     * @return PpmAccessCardApplyInfoDto
     */
    List<PpmAccessCardApplyInfoDto> queryDetailList(PpmAccessCardApplyInfoDto ppmAccessCardApplyInfoDto,
                                                    String uniqueName) ;

    /**
     * 更新
     * @param ppmAccessCardApplyInfoDto  对象
     * @return int
     */
    int updatePpmAccessCardApplyInfo(PpmAccessCardApplyInfoDto ppmAccessCardApplyInfoDto) ;

}
