package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonPageShowDto;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiLevelPersonService {

    /**
     * 分页
     * @param ppmKpiLevelPersonDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiLevelPersonDto ppmKpiLevelPersonDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiLevelPersonDto 对象
     * @return int 对象
     */
     @Deprecated
    int addPpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) ;

    /**
     * 查询详情
     * @param ppmKpiLevelPersonDto 对象
     * @return PpmKpiLevelPersonDto 对象
     */
    @Deprecated
    PpmKpiLevelPersonDto queryDetail(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) ;

    /**
     * 更新
     * @param ppmKpiLevelPersonDto  对象
     * @return int 对象
     */
    @Deprecated
    int updatePpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) ;

    /**
     * 删除
     * @param ppmKpiLevelPersonDto 对象
     * @return int 对象
     */
    @Deprecated
    int deletePpmKpiLevelPerson(PpmKpiLevelPersonDto ppmKpiLevelPersonDto) ;


    /**
     * 登记人数配置页面查询
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param pager 对象
     */
    void queryShowPager(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, Pager pager);

    /**
     * 根据条件查询人数比例数据
     * @param ppmKpiLevelPersonDto 对象
     * @return List<PpmKpiLevelPersonDto> 对象
     */
    List<PpmKpiLevelPersonDto> getPpmKpiLevelPersons(PpmKpiLevelPersonDto ppmKpiLevelPersonDto);


    /**
     * 查询设置比例数的显示数据
     * @param ppmKpiLevelPersonInfoDto 对象
     * @return  List<PpmKpiLevelPersonPageShowDto> 对象
     */
    List<PpmKpiLevelPersonPageShowDto> queryShowList(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto);


    /**
     * 导出
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param response 对象
     */
    void exportData(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, HttpServletResponse response);

    /**
     * 导入
     * @param file 对象
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param userid 对象
     * @return boolean 对象
     */
    boolean importExcel(MultipartFile file,PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,String userid);


    /**
     * 更新修改
     * @param ppmKpiLevelPersonDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int updateOrAdd(PpmKpiLevelPersonDto ppmKpiLevelPersonDto ,String userid);

    /**
     * 根据条件查询详情
     * @param ppmKpiLevelPersonDto 对象
     * @return PpmKpiLevelPersonDto 对象
     */
    PpmKpiLevelPersonDto queryDetailByCondition(PpmKpiLevelPersonDto ppmKpiLevelPersonDto);

    /**
     *  取得没有被设置人数的等级包括设置为0的
     * @param ppmKpiLevelPersonDto 对象
     * @return List<PpmKpiLevelPersonDto> 对象
     */
    List<PpmKpiLevelPersonDto> getNoSetLevel(PpmKpiLevelPersonDto ppmKpiLevelPersonDto);


    /**
     * 根据条件查询数据
     * @param ppmKpiLevelPersonInfoDto  对象
     * @return List<PpmKpiLevelPersonInfoDto> 对象
     */
    List<PpmKpiLevelPersonInfoDto> getPpmKpiLevelPersonsByCondition(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto);


    /**
     * 同步数据
     * @param userid 对象
     * @return int
     */
    int syncLevelPersonData(String userid);

    /**
     * 添加等级
     * @param eosDictEntry     对象
     * @param userid 对象
     * @return int
     */
    int addKpiLevelPerson(EosDictEntry eosDictEntry, String userid);

    /**
     * 删除等级
     * @param eosDictEntry 对象
     * @param userid 对象
     * @return int
     */
    int deleteKpiLevelPerson(EosDictEntry eosDictEntry,String userid);

}
