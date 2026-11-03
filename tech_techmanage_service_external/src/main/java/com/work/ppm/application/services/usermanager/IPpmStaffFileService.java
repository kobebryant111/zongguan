package com.work.ppm.application.services.usermanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.usermanager.PpmStaffFileDto;
import com.work.ppm.application.model.usermanager.PpmStaffFile;

/**
 * 行员档案信息接口
 */
public interface IPpmStaffFileService {

    /**
     * 分页
     * @param ppmStaffFile 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmStaffFile ppmStaffFile, Pager pager) ;

    /**
     * 查询档案员工
     * @param badge 员工编号
     * @return PpmStaffFileDto
     */
    PpmStaffFileDto queryUserFile(String badge) ;

    /**
     * 更新
     * @param ppmStaffFile 对象
     * @return Integer
     */
    Integer updatePpmStaffFile(PpmStaffFile ppmStaffFile) ;

    /**
     * 删除
     * @param ppmStaffFile 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmStaffFile(PpmStaffFile ppmStaffFile) ;

    /**
     * 上传图片
     * @param fileDto 对象
     * @return String
     */
    String uploadImage( PpmStaffFileDto fileDto) ;
}
