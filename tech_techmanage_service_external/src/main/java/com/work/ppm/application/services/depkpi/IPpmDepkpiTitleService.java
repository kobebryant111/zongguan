package com.work.ppm.application.services.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiTitleDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiTitleInfoDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiTitle;

import java.util.List;

/**
 * 标题
 *
 */
public interface IPpmDepkpiTitleService {

    /**
     * 无子集标题列表查询
     *
     * @param ppmDepkpiTitleDto 对象
     * @return PpmDepkpiTitleDto
     */
    List<PpmDepkpiTitleDto> queryTitleList(PpmDepkpiTitleDto ppmDepkpiTitleDto);

    /**
     * 考核标题树查询
     *
     * @param ppmDepkpiTitleDto 对象
     * @return PpmDepkpiTitleDto
     */
    List<PpmDepkpiTitleInfoDto> queryTitleTreeData(PpmDepkpiTitleDto ppmDepkpiTitleDto);

    /**
     * 新增标题（考核标题表）
     *
     * @param ppmDepkpiTitleDto 对象
     * @param userid            userid
     * @return int
     */
    int addPpmDepkpiTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, String userid);

    /**
     * 修改标题
     *
     * @param ppmDepkpiTitleDto 对象
     * @param userid            userid
     * @return int
     */
    int updatePpmDepkpiTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, String userid);

    /**
     * 删除标题数据
     *
     * @param ppmDepkpiTitleDto 对象
     * @param userid            userid
     * @return int
     */
    int updateIsdelById(PpmDepkpiTitleDto ppmDepkpiTitleDto, String userid);

    /**
     * 根据考核id查询标题
     *
     * @param id 对象
     * @return PpmDepkpiTitle
     */
    List<PpmDepkpiTitle> queryByDetailId(Long id);
}
