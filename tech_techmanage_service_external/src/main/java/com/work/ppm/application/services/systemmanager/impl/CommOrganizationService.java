package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.CommOrganization;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理
 */
@Service("commOrganizationService")
public class CommOrganizationService implements ICommOrganizationService {
    /**
     * 实体类路径
     */
    private static String COMMORGANIZATION = "com.work.ppm.application.model.systemmanager.CommOrganization";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 人员接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Override
    public void queryPager(CommOrganization commOrganization, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(commOrganization,CommOrganization.class);
        myBatisDao.selectPager(COMMORGANIZATION, map,pager,"queryPagerList");
    }

    @Override
    public CommOrganization queryDetail(CommOrganization commOrganization) {
        return myBatisDao.selectClass(COMMORGANIZATION, commOrganization,"queryDetail");
    }

    @Override
    public Integer updateCommOrganization(CommOrganization commOrganization) {
        return myBatisDao.executionPojo(COMMORGANIZATION, commOrganization, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteCommOrganization(CommOrganization commOrganization) {
        return myBatisDao.executionPojo(COMMORGANIZATION, commOrganization, IMyBatisDao.SQL.DELETE,"deletePojo");
    }

    /**
     * 机构结婚
     * @param adminid 父级编号
     * @return  List<CommOrganizationDto>
     */
    private List<CommOrganizationDto> queryCommOrganizationList(long adminid) {
        CommOrganization entity = new CommOrganization();
        entity.setAdminid(adminid);
        List<CommOrganization> list = myBatisDao.selectListT(COMMORGANIZATION, entity, "queryListById");
        List<CommOrganizationDto> dtoList = JsonUtil.jsonToList(list,CommOrganizationDto.class);
        return dtoList;
    }


    @Override
    public List<CommOrganizationDto> getTree() {
        List<CommOrganizationDto> parenList = this.queryCommOrganizationList(-1);
        List<CommOrganizationDto> childList = this.queryCommOrganizationList(1);
        parenList.get(0).setChildren(childList);
//        for (CommOrganizationDto item: childList ) {
//            CommOrganizationDto dtoList = this.queryDgTree(item);
//        }
        return parenList;
    }

    @Override
    public List<CommTreeDto> queryOrgTreeComponents() {
        List<CommTree> treeList = myBatisDao.selectListT(COMMORGANIZATION, null, "queryOrgTreeComponents");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(treeList, CommTreeDto.class);
        List<CommTreeDto> dtos = CommTreeDto.recursiveListToTree(treeDtos);
        return dtos;
    }

    @Override
    public List<CommTreeDto> queryOrgTree(String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        List<CommTree> trees = myBatisDao.selectListT(COMMORGANIZATION, map, "queryOrgTree");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(trees, CommTreeDto.class);
        return  treeDtos;
    }

    @Override
    public int hasOrg(String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        Long count = myBatisDao.selectCount(COMMORGANIZATION, map, "hasOrg");
        return null != count ? count.intValue() : 0;
    }

    @Override
    public CommOrganizationDto queryDateil(String orgName) {
        CommOrganization orgItem = new CommOrganization();
        orgItem.setLocation(orgName);
        CommOrganization item = this.queryDetail(orgItem);
        CommOrganizationDto dto = JsonUtil.jsonToClass(item, CommOrganizationDto.class);
        return dto;
    }

    @Override
    public CommOrganizationDto queryDateil(long compid) {
        CommOrganization orgItem = new CommOrganization();
        orgItem.setCompid(compid);
        CommOrganization item = this.queryDetail(orgItem);
        CommOrganizationDto dto = JsonUtil.jsonToClass(item, CommOrganizationDto.class);
        return dto;
    }

    @Override
    public CommOrganizationDto queryDateilForCompCode(String compcode) {
        CommOrganization org = new CommOrganization();
        org.setCompcode(compcode);
        CommOrganization orgitem = this.queryDetail(org);
        CommOrganizationDto dto = JsonUtil.jsonToClass(orgitem, CommOrganizationDto.class);
        return dto;
    }

    @Override
    public CommOrganizationDto queryFHIdforUserId(String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        CommEmployee emp = commEmployeeService.getCommEmployeeInfo(userId);
        CommOrganization item = myBatisDao.selectClass(COMMORGANIZATION, map, "queryFHIdforUserId");
        if(item.getAdminid() == 1) {
            item.setCompid(emp.getCompid());
            return JsonUtil.jsonToClass(item, CommOrganizationDto.class);
        }
        CommOrganizationDto dd = getRecursive(item.getAdminid());
        return dd;
    }

    @Override
    public List<CommTreeDto> queryBranchThree() {
        List<CommTree> list = myBatisDao.selectListT(COMMORGANIZATION, null, "queryBranchThree");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(list, CommTreeDto.class);
        return treeDtos;
    }

    /**
     * 递归查询分行id
     * @param adminId 上级机构编号
     * @return CommOrganizationDto
     */
    private CommOrganizationDto getRecursive(long adminId) {
        CommOrganizationDto item = this.queryDateil(adminId);
        if(item.getAdminid() == 1) {
            return item;
        } else {
            return getRecursive(item.getAdminid());
        }
    }

    /**
     * 私有方法 递归查询树结构
     * @param item 对象
     * @return CommOrganizationDto
     */
    private CommOrganizationDto queryDgTree(CommOrganizationDto item) {
        List<CommOrganizationDto> item1 = this.queryCommOrganizationList(item.getCompid());
        if(null != item1 && !item1.isEmpty()) {
            item.setChildren(item1);
            for (CommOrganizationDto dd: item1) {
                return this.queryDgTree(dd);
            }
        }
        return  item;
    }
}
