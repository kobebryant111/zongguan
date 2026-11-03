package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.flowmanager.PpmFlowTypeDto;
import com.work.ppm.application.model.flowmanager.PpmFlowType;
import com.work.ppm.application.services.flowmanager.IPpmFlowTypeService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程类型
 */
@Service("ppmFlowTypeService")
public class PpmFlowTypeService extends BaseDaoSupper implements IPpmFlowTypeService {
    /**
     * 实体类路径
     */
    private static String PPMFLOWTYPE = "com.work.ppm.application.model.flowmanager.PpmFlowType";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<PpmFlowTypeDto> queryFlowTree(PpmFlowType ppmFlowType) {
        List<PpmFlowType> menusList = myBatisDao.selectListT(PPMFLOWTYPE, ppmFlowType, "getList");
        List<PpmFlowTypeDto> dtos = JsonUtil.jsonToList(menusList, PpmFlowTypeDto.class);
        List<PpmFlowTypeDto> treeList = recursiveListToTree(dtos);
        return treeList;
    }

    @Override
    public PpmFlowType queryDetail(PpmFlowType ppmFlowType) {
        ppmFlowType.setIsdel(0);
        return myBatisDao.selectClass(PPMFLOWTYPE, ppmFlowType,"queryDetail");
    }

    @Override
    public Integer updatePpmFlowType(PpmFlowType ppmFlowType) {
        if(StringUtil.isNotEmpty(ppmFlowType.getId())){
            myBatisDao.executionPojo(PPMFLOWTYPE, ppmFlowType, IMyBatisDao.SQL.UPDATE,"updatePojo");
            return 2;
        }else{
            Long id = super.getNextSequence(PpmFlowType.class);
            ppmFlowType.setId(id);
            ppmFlowType.setIsdel(0);
            myBatisDao.executionPojo(PPMFLOWTYPE, ppmFlowType, IMyBatisDao.SQL.UPDATE,"addPojo");
            return 1;
        }
    }

    @Override
    public Integer deletePpmFlowType(PpmFlowType ppmFlowType) {
        String parentid = ppmFlowType.getParentid();
        if("0".equals(parentid)){
            return myBatisDao.executionPojo(PPMFLOWTYPE, ppmFlowType, IMyBatisDao.SQL.DELETE,"deleteByParentid");
        }else{
            return myBatisDao.executionPojo(PPMFLOWTYPE, ppmFlowType, IMyBatisDao.SQL.DELETE,"deleteById");
        }
    }

    @Override
    public List<PpmFlowTypeDto> getListAll() {
        List<PpmFlowType> list = myBatisDao.selectListT(PPMFLOWTYPE, new PpmFlowType(), "getList");
        List<PpmFlowTypeDto> listDto = JsonUtil.jsonToList(list, PpmFlowTypeDto.class);
        return listDto;
    }

    @Override
    public List<PpmFlowTypeDto> queryTwoLevelFlowTree(PpmFlowType ppmFlowType) {
        List<PpmFlowType> menusList = myBatisDao.selectListT(PPMFLOWTYPE, ppmFlowType, "getTwoLevelList");
        List<PpmFlowTypeDto> dtos = JsonUtil.jsonToList(menusList, PpmFlowTypeDto.class);
        List<PpmFlowTypeDto> treeList = recursiveListToTree(dtos);
        return treeList;
    }

    @Override
    public PpmFlowType queryDetail(String flowdraftno) {
        PpmFlowType type = new PpmFlowType();
        type.setFlowdraftno(flowdraftno);
        return this.queryDetail(type);
    }

    //    递归 菜单树 begin ------

    /**
     * 递归树
     * @param nodes 节点集合
     * @return  List<PpmFlowTypeDto>
     */
    private List<PpmFlowTypeDto> recursiveListToTree(List<PpmFlowTypeDto> nodes) {
        boolean extisRootNode = false;
        List<PpmFlowTypeDto> childrenNode = new ArrayList<>();
        for (PpmFlowTypeDto item: nodes) {
            // 判断节点是否有根节点
            if(isRootNode(item, nodes)) {
                childrenNode.add(findChildrenNode(item, nodes));
                extisRootNode = true;
            }
        }
        if(!extisRootNode) {
            return nodes;
        }
        return childrenNode;
    }

    /**
     * 递归查找子节点
     * @param parentItem 当前节点
     * @param nodes 节点集合
     * @return PpmFlowTypeDto
     */
    private PpmFlowTypeDto findChildrenNode(PpmFlowTypeDto parentItem, List<PpmFlowTypeDto> nodes) {
        List<PpmFlowTypeDto> childrens = new ArrayList<>();
        for (PpmFlowTypeDto it: nodes) {
            if(parentItem.getFlowdraftno().equals(it.getParentid())) {
                childrens.add(findChildrenNode(it,nodes));
                parentItem.setChildren(childrens);
            }
        }
        return parentItem;
    }

    /**
     * 判断节点是否有根节点
     * @param item 当前节点
     * @param nodes 节点集合
     * @return boolean
     */
    private boolean isRootNode(PpmFlowTypeDto item, List<PpmFlowTypeDto> nodes) {
        for (PpmFlowTypeDto it: nodes) {
            if(it.getFlowdraftno().equals(item.getParentid())) {
                return false;
            }
        }
        return true;
    }
//    递归 菜单树 end ------
}
