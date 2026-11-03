package com.work.ppm.application.services.depkpi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.depkpi.PpmDepkpiModelDto;

import java.util.List;

/**
  * 部门模板
  */
public interface IPpmDepkpiModelService {

    /**
     * 分页
     * @param ppmDepkpiModelDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDepkpiModelDto ppmDepkpiModelDto, Pager pager) ;

    /**
     * 查询详情
     * @param ppmDepkpiModelDto 对象
     * @return PpmDepkpiModelDto
     */
    PpmDepkpiModelDto queryDetail(PpmDepkpiModelDto ppmDepkpiModelDto) ;

    /**
     * 更新
     * @param ppmDepkpiModelDto  对象
     * @param userid 对象
     * @return int
     */
    int updateModel(PpmDepkpiModelDto ppmDepkpiModelDto, String userid) ;

    /**
     * 删除模板
     * @param ppmDepkpiModelDto 模板对象
     * @param userid 对象
     * @return int
     */
    int deleteModel(PpmDepkpiModelDto ppmDepkpiModelDto, String userid) ;

    /**
     * 模板库新增并添加到关联表
     * @param ppmDepkpiModelDto 对象
     * @param userid 对象
     * @return int
     */
    int addModel(PpmDepkpiModelDto ppmDepkpiModelDto, String userid);

    /**
     * 模板列表查询
     * @param ppmDepkpiModelDto 对象
     * @return 对象
     */
    List<PpmDepkpiModelDto> queryModelList(PpmDepkpiModelDto ppmDepkpiModelDto);
}
