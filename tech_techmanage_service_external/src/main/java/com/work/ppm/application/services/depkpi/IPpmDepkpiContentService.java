package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiContentDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiDepCheckDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiContent;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiContentInfo;

import java.util.List;

/**
 * 处理接口
 *
 */
public interface IPpmDepkpiContentService {

    /**
     * 查询内容列表
     *
     * @param ppmDepkpiContentDto 对象
     * @return PpmDepkpiContentDto
     */
    List<PpmDepkpiContentInfo> queryContentList(PpmDepkpiContentDto ppmDepkpiContentDto);

    /**
     * 查询代办列表查询
     *
     * @param ppmDepkpiContentDto 对象
     * @return PpmDepkpiContentDto
     */
    List<PpmDepkpiContentInfo> queryGgDbContentList(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

    /**
     * 考核内容查询返回初评人还是终评人
     *
     * @param ppmDepkpiContentDto 对象
     * @return PpmDepkpiContentDto
     */
    List<PpmDepkpiContentInfo> getEvalContentList(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

    /**
     * 修改内容
     *
     * @param ppmDepkpiContentDto 对象
     * @param userid              修改人
     * @return int
     */
    int updatePpmDepkpiContent(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

    /**
     * 删除考核内容
     *
     * @param ppmDepkpiContentDto 对象
     * @param userid              修改人
     * @return int
     */
    int updateIsdelById(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

    /**
     * 新增考核内容
     *
     * @param ppmDepkpiContentDto 对象
     * @param userid              修改人
     * @return int
     */
    int addPpmDepkpiContent(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

    /**
     * 评价
     *
     * @param ppmDepkpiContentDto 对象
     * @param userid              修改人
     * @return int
     */
    int eval(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

    /**
     * 查询自定义内容提交记录
     * @param ppmDepkpiContent 对象
     * @return list
     */
    List<PpmDepkpiContent> queryZdyCommit(PpmDepkpiDepCheckDto ppmDepkpiContent);
    /**
     * 查询公共内容提交记录
     * @param ppmDepkpiContent 对象
     * @return list
     */
    List<PpmDepkpiContent> queryGgCommit(PpmDepkpiDepCheckDto ppmDepkpiContent);

    /**
     * 删除标题时删除考核内容
     *
     * @param ppmDepkpiContentDto 对象
     * @param userid              修改人
     * @return int
     */
    int updateIsdelByTitleid(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);


    /**
     * 考核汇总查询总分自定义、公共得分
     *
     * @param ppmDepkpiContentDto 对象
     * @return int
     */
    PpmDepkpiContentDto querySumPointAndTargettype(PpmDepkpiContentDto ppmDepkpiContentDto,String userid);

    /**
     * 根据标题id查询
     *
     * @param id 对象
     * @return PpmDepkpiContentDto
     */
    List<PpmDepkpiContent> queryByTitleId(Long id);

    /**
     * 自评提交
     *
     * @param ppmDepkpiContentDto 对象
     * @param userid              修改人
     * @return int
     */
    int upSelfeval(PpmDepkpiContentDto ppmDepkpiContentDto, String userid);

}

