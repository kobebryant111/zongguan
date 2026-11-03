package com.work.ppm.application.services.depkpi.impl;

import com.work.ppm.application.dto.depkpi.PpmDepkpiContentDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiTitleDto;
import com.work.ppm.application.dto.depkpi.PpmDepkpiTitleInfoDto;
import com.work.ppm.application.model.depkpi.PpmDepkpiTitle;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiContentInfo;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiTitleInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiContentService;
import com.work.ppm.application.services.depkpi.IPpmDepkpiTitleService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 标题业务类
 *
 */
@Service("ppmDepkpiTitleService")
public class PpmDepkpiTitleService extends BaseDaoSupper implements IPpmDepkpiTitleService {
    /**
     * 实体类路径
     */
    private static String PPMDEPKPITITLE = "com.work.ppm.application.model.depkpi.PpmDepkpiTitle";

    /**
     * 更新方法
     */
    private static String updatePojo = "updatePojo";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 查询考核内容接口
     */
    @Autowired
    private IPpmDepkpiContentService ippmDepkpiContentService;

    @Override
    @Transactional("defaultTx")
    public int addPpmDepkpiTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, String userid) {
        //新增标题
        if (ppmDepkpiTitleDto.getParentid() != null) {
            //查询标题下的考核内容（新增标题前查询上个标题是否有考核内容）
            PpmDepkpiContentDto ppmDepkpiContentDto = new PpmDepkpiContentDto();
            ppmDepkpiContentDto.setIsdel(0L);
            ppmDepkpiContentDto.setTitleid(ppmDepkpiTitleDto.getParentid());
            List<PpmDepkpiContentInfo> contentList = ippmDepkpiContentService.queryContentList(ppmDepkpiContentDto);
            if (!contentList.isEmpty()) {
                return -1;
            }
            PpmDepkpiTitle ppmDepkpiTitle1 = new PpmDepkpiTitle();
            ppmDepkpiTitle1.setHasson(1L);
            ppmDepkpiTitle1.setId(ppmDepkpiTitleDto.getParentid());
            ppmDepkpiTitle1.setLastmodtime(new Date());
            ppmDepkpiTitle1.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMDEPKPITITLE, ppmDepkpiTitle1, IMyBatisDao.SQL.UPDATE, updatePojo);

        }
        //新增标题
        PpmDepkpiTitle ppmDepkpiTitle = JsonUtil.jsonToClass(ppmDepkpiTitleDto, PpmDepkpiTitle.class);
        Long id = getNextSequence(PpmDepkpiTitle.class);
        ppmDepkpiTitle.setIsdel(0L);
        ppmDepkpiTitle.setId(id);
        ppmDepkpiTitle.setLastmodtime(new Date());
        ppmDepkpiTitle.setLastmoduserid(userid);
        ppmDepkpiTitle.setHasson(0L);
        myBatisDao.executionPojo(PPMDEPKPITITLE, ppmDepkpiTitle, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int updatePpmDepkpiTitle(PpmDepkpiTitleDto ppmDepkpiTitleDto, String userid) {
        //修改标题
        PpmDepkpiTitle ppmDepkpiTitle = JsonUtil.jsonToClass(ppmDepkpiTitleDto, PpmDepkpiTitle.class);
        //查询是否有子标题（只能修改最下级标题）
        PpmDepkpiTitle pp =new PpmDepkpiTitle();
        pp.setParentid(ppmDepkpiTitle.getId());
        List<PpmDepkpiTitle>list=myBatisDao.selectListT(PPMDEPKPITITLE,
                pp, "queryByParentId");
        if(!Collections.isEmpty(list)){
            return -1;
        }
        ppmDepkpiTitle.setLastmodtime(new Date());
        ppmDepkpiTitle.setLastmoduserid(userid);
        return myBatisDao.executionPojo(PPMDEPKPITITLE, ppmDepkpiTitle, IMyBatisDao.SQL.UPDATE, "updateById");

    }

    @Override
    @Transactional("defaultTx")
    public int updateIsdelById(PpmDepkpiTitleDto ppmDepkpiTitleDto, String userid) {
        //刪除标题
        PpmDepkpiTitle ppmDepkpiTitle = JsonUtil.jsonToClass(ppmDepkpiTitleDto, PpmDepkpiTitle.class);
        ppmDepkpiTitle.setIsdel(1L);
        ppmDepkpiTitle.setLastmoduserid(userid);
        ppmDepkpiTitle.setLastmodtime(new Date());
        myBatisDao.executionPojo(PPMDEPKPITITLE, ppmDepkpiTitle, IMyBatisDao.SQL.UPDATE, "updateIsdelById");

        //删除内容数据
        PpmDepkpiContentDto pc = new PpmDepkpiContentDto();
        pc.setTitleid(ppmDepkpiTitleDto.getId());
        ippmDepkpiContentService.updateIsdelByTitleid(pc,userid);

        // 查询已删除对象（修改该标题是否有子节点）
        PpmDepkpiTitle title = myBatisDao.selectClass(PPMDEPKPITITLE, ppmDepkpiTitle, "selectTitleById");
        if(title.getParentid() == null){
            return 1;
        }
        PpmDepkpiTitle param = new PpmDepkpiTitle();
        param.setParentid(title.getParentid());
        param.setIsdel(0L);
        // 查询所有相同父级数据（用被删除的父级id查询，如果有数据说明被删除数据的父级还有子节点 不用修改hasson或者修改）
        List<PpmDepkpiTitle> titleList = myBatisDao.selectListT(PPMDEPKPITITLE, param, "selectList");
        // 判断数据是否为0，如果为0，表示此父级无子节点，更新hasson
        if (Collections.isEmpty(titleList)) {
            param = new PpmDepkpiTitle();
            param.setHasson(0L);
            param.setId(title.getParentid());
            param.setLastmodtime(new Date());
            param.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMDEPKPITITLE, param, IMyBatisDao.SQL.UPDATE, updatePojo);
        }
        return 1;
    }


    @Override
    public List<PpmDepkpiTitleDto> queryTitleList(PpmDepkpiTitleDto ppmDepkpiTitleDto) {
        PpmDepkpiTitle ppmDepkpiTitle = JsonUtil.jsonToClass(ppmDepkpiTitleDto, PpmDepkpiTitle.class);
        ppmDepkpiTitle.setIsdel(0L);
        List<PpmDepkpiTitleInfo> list = myBatisDao.selectListT(PPMDEPKPITITLE, ppmDepkpiTitle, "selectList");
        return JsonUtil.jsonToList(list, PpmDepkpiTitleDto.class);
    }

    @Override
    public List<PpmDepkpiTitleInfoDto> queryTitleTreeData(PpmDepkpiTitleDto ppmDepkpiTitleDto) {
        PpmDepkpiTitle ppmDepkpiTitle = JsonUtil.jsonToClass(ppmDepkpiTitleDto, PpmDepkpiTitle.class);
        ppmDepkpiTitle.setIsdel(0L);
        List<PpmDepkpiTitleInfo> list = myBatisDao.selectListT(PPMDEPKPITITLE, ppmDepkpiTitle, "queryTitleTreeData");
        return JsonUtil.jsonToList(list, PpmDepkpiTitleInfoDto.class);
    }

    @Override
    public List<PpmDepkpiTitle> queryByDetailId(Long id) {
        PpmDepkpiTitle ppmDepkpiTitle=new PpmDepkpiTitle();
        ppmDepkpiTitle.setDetailid(id);
        ppmDepkpiTitle.setHasson(0L);
        ppmDepkpiTitle.setIsdel(0L);
        return myBatisDao.selectListT(PPMDEPKPITITLE, ppmDepkpiTitle, "queryByDetailId");
    }


}
