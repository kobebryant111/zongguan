package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcUpdateDto;

/**
  *  信息变更申请信息表 接口描述
  */
public interface IPpmOutsourcUpdateService {

    /**
     * 添加
     * @param ppmOutsourcUpdateDto 对象
     * @return int
     */
    int addPpmOutsourcUpdate(PpmOutsourcUpdateDto  ppmOutsourcUpdateDto) ;

    /**
     * 查询详情
     * @param ppmOutsourcUpdateDto 对象
     * @return PpmOutsourcUpdateDto
     */
    PpmOutsourcUpdateDto queryDetail(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) ;

    /**
     * 更新
     * @param ppmOutsourcUpdateDto  对象
     * @return int
     */
    int updatePpmOutsourcUpdate(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) ;

    /**
     * 删除
     * @param ppmOutsourcUpdateDto 对象
     * @return int
     */
    int deletePpmOutsourcUpdate(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) ;

    /**
     * 草稿 未发起
     * 已创建
     * @param ppmOutsourcUpdateDto 对象
     * @return String
     */
    String managerCreated(PpmOutsourcUpdateDto ppmOutsourcUpdateDto);

    /**
     * 项目经理添加并发起流程
     * 已发起
     * @param ppmOutsourcUpdateDto 对象
     * @return String
     */
    String managerInit(PpmOutsourcUpdateDto ppmOutsourcUpdateDto) ;

    /**
     * 二级部经理审批
     * 处理中
     * @param ppmOutsourcUpdateDto 对象
     * @return String
     */
    String departmentHeadApproval(PpmOutsourcUpdateDto ppmOutsourcUpdateDto);

    /**
     * 一级部总经理室审批
     * 处理中 / 已结束
     * @param ppmOutsourcUpdateDto 对象
     * @return String
     */
    String generalManagerApproval(PpmOutsourcUpdateDto ppmOutsourcUpdateDto);


}
