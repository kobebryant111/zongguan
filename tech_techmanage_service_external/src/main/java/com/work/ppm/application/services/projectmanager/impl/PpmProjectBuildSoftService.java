package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.PpmDemandApplyDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftTreeDto;
import com.work.ppm.application.model.projectmanager.PpmProjectBuildSoft;
import com.work.ppm.application.model.projectmanager.ext.PpmProjectBuildSoftInfo;
import com.work.ppm.application.model.projectmanager.ext.PpmProjectBuildSoftTree;
import com.work.ppm.application.services.projectmanager.IPpmDemandApplyService;
import com.work.ppm.application.services.projectmanager.IPpmProjectBuildSoftService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 项目管理
 */
@Slf4j
@Service("ppmProjectBuildSoftService")
public class PpmProjectBuildSoftService extends BaseDaoSupper implements IPpmProjectBuildSoftService {
    /**
     * 实体类路径
     */
    private static String PPMPROJECTBUILDSOFT = "com.work.ppm.application.model.projectmanager.PpmProjectBuildSoft";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 业务意向接口类
     */
    @Autowired
    private IPpmDemandApplyService ppmDemandApplyService;

    @Override
    public void queryPager(PpmProjectBuildSoft ppmProjectBuildSoft, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProjectBuildSoft,PpmProjectBuildSoft.class);
        myBatisDao.selectPager(PPMPROJECTBUILDSOFT, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) {
        //Long key = this.getNextSequence(PpmProjectBuildSoft.class);
        // 如果需要请set 主键id
        //ppmProjectBuildSoft.setId(key);
        myBatisDao.executionPojo(PPMPROJECTBUILDSOFT, ppmProjectBuildSoft, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmProjectBuildSoft queryDetail(PpmProjectBuildSoft ppmProjectBuildSoft) {
        return myBatisDao.selectClass(PPMPROJECTBUILDSOFT, ppmProjectBuildSoft,"queryDetail");
    }

    @Override
    public Integer updatePpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) {
        return myBatisDao.executionPojo(PPMPROJECTBUILDSOFT, ppmProjectBuildSoft, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmProjectBuildSoft(PpmProjectBuildSoft ppmProjectBuildSoft) {
        return myBatisDao.executionPojo(PPMPROJECTBUILDSOFT, ppmProjectBuildSoft, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<PpmProjectBuildSoftTreeDto> queryProjectTree() {
        Map<String,Object> map = new HashMap<>();
        List<PpmProjectBuildSoftTree> list = myBatisDao.selectListT(PPMPROJECTBUILDSOFT, map, "queryProjectTree");
        list = Optional.ofNullable(list).orElse(new ArrayList<>());
        List<PpmProjectBuildSoftTree> uniqueList =  list.stream()
                .filter(ii->StringUtil.isNotEmpty(ii.getProjectno()))
                .filter(ii->StringUtil.isNotEmpty(ii.getPid())).collect(
                collectingAndThen(toCollection(()->
                        new TreeSet<>(Comparator.comparing(PpmProjectBuildSoftTree::getProjectno))), ArrayList::new)
        );
        List<PpmProjectBuildSoftTreeDto> dtoList = JsonUtil.jsonToList(uniqueList, PpmProjectBuildSoftTreeDto.class);
        List<PpmProjectBuildSoftTreeDto> treeList = recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<PpmProjectBuildSoftDto> queryMAS(String projectno) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectno",projectno);
        List<PpmProjectBuildSoftInfo> list = myBatisDao.selectListT(PPMPROJECTBUILDSOFT, map, "queryMAS");
        List<PpmProjectBuildSoftDto> dtos = JsonUtil.jsonToList(list, PpmProjectBuildSoftDto.class);
        return dtos;
    }

    @Override
    public List<PpmProjectBuildSoftDto> querySystemPrincipal(String sysno) {
        if(!StringUtil.isStringNotEmpty(sysno)) {
            return null;
        }
        List<String> list = new ArrayList<>();

        String[] sytemids = sysno.split(",");
        for (int i = sytemids.length - 1; i >= 0; i--) {
            list.add(sytemids[i].trim());
        }
        List<PpmProjectBuildSoft> bsList = myBatisDao.selectListT(PPMPROJECTBUILDSOFT, list,"querySystemPrincipal");
        List<PpmProjectBuildSoftDto> dlist = JsonUtil.jsonToList(bsList, PpmProjectBuildSoftDto.class);
        return dlist;
    }

    @Override
    public String queryProjectNoForProattrbutes(String projectNo) {
        PpmProjectBuildSoft soft = new PpmProjectBuildSoft();
        soft.setProjectno(projectNo);
        PpmProjectBuildSoft item = myBatisDao.selectClass(PPMPROJECTBUILDSOFT, soft, "queryDetail");
        return null != item ? item.getProattrbutes() : null;
    }

    /**
     * 递归树
     * @param nodes 节点结合
     * @return  List<PpmProjectBuildSoftTreeDto>
     */
    private List<PpmProjectBuildSoftTreeDto> recursiveListToTree(List<PpmProjectBuildSoftTreeDto> nodes) {

        if(null == nodes || nodes.isEmpty()) {
            return  null;
        }

        List<PpmProjectBuildSoftTreeDto> treeList = new ArrayList<>();
        for (PpmProjectBuildSoftTreeDto item: nodes) {
            // 查询是否有根节点
            if(extisRootNode(item, nodes)) {
                // 查询子节点
                PpmProjectBuildSoftTreeDto node = findChildrenNode(item, nodes);
                treeList.add(node);
            }
        }
        return treeList;
    }

    /**
     * 查询子节点
     * @param item 当前节点
     * @param nodes 节点结合
     * @return  PpmProjectBuildSoftTreeDto
     */
    private PpmProjectBuildSoftTreeDto findChildrenNode(PpmProjectBuildSoftTreeDto item,
                                                        List<PpmProjectBuildSoftTreeDto> nodes) {
        List<PpmProjectBuildSoftTreeDto> childList = new ArrayList<>();
        for (PpmProjectBuildSoftTreeDto node: nodes) {
            if(null == item.getProjectno()) {
                continue;
            }
            if(item.getProjectno().equals(node.getPid())) {
                childList.add(findChildrenNode(node, nodes));
                item.setChildren(childList);
            }
        }
        return item;
    }

    /**
     * 查询是否有根节点
     * @param item 当前节点
     * @param nodes 节点结合
     * @return  boolean
     */
    private boolean extisRootNode(PpmProjectBuildSoftTreeDto item, List<PpmProjectBuildSoftTreeDto> nodes) {
        for (PpmProjectBuildSoftTreeDto node: nodes) {
            if(item.getPid().equals(node.getProjectno())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public PpmProjectBuildSoftInfo getProjectBuildSoftByDenamdID(String projectNo) {
        PpmProjectBuildSoft soft = new PpmProjectBuildSoft();
        soft.setDemandno(projectNo);
        PpmProjectBuildSoftInfo item = myBatisDao.selectClass(PPMPROJECTBUILDSOFT, soft,
                "getProjectBuildSoftByDenamdID");
        return item;
    }

    @Override
    public String getBmfzrByProjectno(String projectno) {
        PpmProjectBuildSoft soft = new PpmProjectBuildSoft();
        soft.setProjectno(projectno);
        PpmProjectBuildSoft softNew = this.queryDetail(soft);

        PpmDemandApplyDto demandApply = ppmDemandApplyService.getdemandByDemandno(softNew.getDemandno());
        String bmfzr = "";
        if (demandApply != null) {
            bmfzr = demandApply.getBmfzr();
        }

        return bmfzr;
    }

    @Override
    public PpmProjectBuildSoftDto getProjectInfoByOnlineno(String onlineno) {
        Map<String, Object> query = new HashMap<>();
        query.put("onlineno", onlineno);
        PpmProjectBuildSoftInfo info = myBatisDao.selectClass(PPMPROJECTBUILDSOFT, query,
                "getProjectInfoByOnlineno");
        return JsonUtil.jsonToClass(info, PpmProjectBuildSoftDto.class);
    }
}
