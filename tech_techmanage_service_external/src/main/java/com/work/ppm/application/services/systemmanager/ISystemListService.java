package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.model.rdmanager.CommSysPwDto;
import com.work.ppm.application.model.rdmanager.SyspicListDto;
import com.work.ppm.application.model.rdmanager.SystemListDto;

import java.util.List;

public interface ISystemListService {
    /**
     * 删除
     * @param systemListDto
     * @return
     */
    Integer deleteSystemListDto(SystemListDto systemListDto);

    /**
     * 分批插入
     * @param systemListDto
     */
    void insertSystemListDto(List<SystemListDto> systemListDto);

    /**
     * 删除
     * @param syspicListDto
     */
    Integer deleteSyspicListDto(SyspicListDto syspicListDto);

    /**
     * 分批插入
     * @param syspicListDtos
     */
    void insertSyspicListDto(List<SyspicListDto> syspicListDtos);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<CommSysPwDto> selectCommSysPwDto(int page);

    /**
     * 更新密钥
     * @param sysPwDto
     */
    void updateCommSysPwDto(CommSysPwDto sysPwDto);
}
