package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.vendorcontract.AttatchMUApplyDto;
import com.work.ppm.application.dto.vendorcontract.AttatchManagementDto;
import com.work.ppm.application.enums.AttatchTypeEnums;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.model.vendormanager.AttatchManagement;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.vendormanager.IAttatchMUApplyService;
import com.work.ppm.application.services.vendormanager.IAttatchManagementService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 附件管理 服务实现类
 * </p>
 *
 * @since 2022-10-26
 */

@Slf4j
@RequiredArgsConstructor
@Service("attatchManagementService")
public class AttatchManagementService extends BaseDaoSupper implements IAttatchManagementService {

    /**
     * 实体类路径
     */
    private static String ATTATCHMANAGEMENT= "com.work.ppm.application.model.vendormanager.AttatchManagement";
    /**
     * 已关联
     */
    private static String IS_ASSOCIATED = "Y";

    private static String NOT_EFFECTIVE ="未生效";
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;
    // 附件管理修改申请
    private final IAttatchMUApplyService attatchMUApplyService;
    // 流程日志
    private final IPpmFlowLogService ppmFlowLogService;
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(AttatchManagement attatchManagement, Pager pager) {
        Map<String, Object> mapDto = StringUtil.objectTOMap(attatchManagement, AttatchManagementDto.class);
        Map<String, Object> map = StringUtil.objectTOMap(attatchManagement, AttatchManagement.class);
        mapDto.putAll(map);
        myBatisDao.selectPager(ATTATCHMANAGEMENT, mapDto, pager, "queryPagerList");
    }

    @Override
    public AttatchManagement queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return myBatisDao.selectClass(ATTATCHMANAGEMENT, map, "queryDetailsById");
    }

    @Override
    public long count(HashMap map) {
        return myBatisDao.selectCount(ATTATCHMANAGEMENT, map, "selectCount");
    }

    @Override
    public AttatchManagement selectOneDesc(){
       return  myBatisDao.selectClass(ATTATCHMANAGEMENT, null, "selectOneDesc");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addAttatchManagement(AttatchManagement attatchManagement) {
        myBatisDao.executionPojo(ATTATCHMANAGEMENT, attatchManagement, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateWithFlow(AttatchManagementDto dto, CommOperatorDoLoginDto user) {
        // 工作流程常规参数
        BpsWorkFlowDto bps = dto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(user.getUserid());
        bps.setUserName(user.getOperatorname());
        // 数据转换
        AttatchManagement entity = JsonUtil.jsonToClass(dto, AttatchManagement.class);
        AttatchMUApplyDto applyDto = JsonUtil.jsonToClass(entity, AttatchMUApplyDto.class);
        applyDto.setRecordId(entity.getId());
        applyDto.setId(null);
        // 非总办议案 <无需审批>
        if (!AttatchTypeEnums.ZBYA.getCode().equalsIgnoreCase(entity.getFileType())){
            return updateAttatchManagement(entity);
        }
        // 总人数修改时
        int totals = entity.getTotals();
        int numberUsed = entity.getNumberUsed();
        // 可用人数计算
        int availableCount = totals - numberUsed;
        entity.setAvailableCount(availableCount);
        // 文件为总办议案时, 总人数 < 已用人数
        if (availableCount < 0) {
            return -1;
        }
        // 二级部门经理及以上 <无需审批>
        if(this.level2ManagerAbove(user.getUserid())) {
            return updateAttatchManagement(entity);
        }
        // 文件为总办议案 && 总人数修改 <进行审批>
        AttatchManagement dbNumbers = queryDetailsById(entity.getId());
        int totalsDb = dbNumbers.getTotals();
        if(totals != totalsDb) {
           return attatchMUApplyService.approvalCirculate(applyDto, bps);
        }
        return updateAttatchManagement(entity);
    }

    @Override
    public boolean level2ManagerAbove(String jobId){
        if (!StringUtil.isStringNotEmpty(jobId)){
            return false;
        }
        AtomicReference<Boolean> flag = new AtomicReference<>(false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","金融科技部");
        List<String> list =myBatisDao.selectListT(ATTATCHMANAGEMENT, map,"level2ManagerAbove");
        list.forEach(x->{
            if (jobId.equalsIgnoreCase(x)) {
                flag.set(true);
            }
        });
        return flag.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateAttatchManagement(AttatchManagement attatchManagement) {
        return myBatisDao.executionPojo(ATTATCHMANAGEMENT, attatchManagement, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteByIdsWithTerms(Long ids) {
        if(!StringUtil.isNotEmpty(ids)) {
            return -1;
        }
        AttatchManagement entity = this.queryDetailsById(ids);
        entity.setIsDel(LogicEnums.INVALID.getCode());
        // [已生效的|已失效的]附件：不能删除
        if (!NOT_EFFECTIVE.equalsIgnoreCase(entity.getStatus())) {
            return -1;
        }
        // 若总办议案关联了合同
        if (IS_ASSOCIATED.equalsIgnoreCase(entity.getIsAssociated())) {
            return -1;
        }
        // 文件类型：总办议案/发票/其他
        String type = entity.getFileType();
        // 已用人数
        int numUsed = entity.getNumberUsed();
        // 总办议案时,且关联外包花名册有人员关联
        if (AttatchTypeEnums.ZBYA.getCode().equalsIgnoreCase(type) && numUsed>0) {
            return -1;
        }
        return this.updateAttatchManagement(entity);
    }

    @Override
    public List<AttatchManagement> selectByMapSmall(HashMap map) {
        List<AttatchManagement> res = myBatisDao.selectListT(ATTATCHMANAGEMENT, map, "selectByMapSmall");
        return res;
    }

    @Override
    public boolean judgeMotionNumber (Long beforeId, Long afterId){
        boolean flag;

        if(beforeId == null && afterId != null){
            flag = judgeMotionNumberPlus(afterId);
        }else if (beforeId != null && afterId == null){
            flag = judgeMotionNumberMinus(beforeId);
        }else {
            flag = judgeMotionNumberPlus(afterId) && judgeMotionNumberMinus(beforeId);
        }

        return flag;
    }

    @Override
    public boolean judgeMotionNumberPlus(Long id) {
        if(id == null){
            return false;
        }
        AttatchManagement attatchManagement =  queryDetailsById(id);
        if(attatchManagement == null){
            return false;
        }

        if(attatchManagement.getTotals() == 0){
            return false;
        }

        if(attatchManagement.getAvailableCount() == 0){
            return false;
        }

        return true;
    }

    @Override
    public boolean judgeMotionNumberMinus(Long id) {
        if(id == null){
            return false;
        }

        AttatchManagement attatchManagement =  queryDetailsById(id);
        if(attatchManagement == null){
            return false;
        }

        //总数 = 0
        if(attatchManagement.getTotals() == 0){
            return false;
        }

        //已用人数 = 0
        if(attatchManagement.getNumberUsed() == 0){
            return false;
        }

        return true;
    }


    @Override
    public boolean calMotionNumber (Long beforeId, Long afterId){
        boolean flag;

        if(beforeId == null && afterId != null){
            flag = calMotionNumberPlus(afterId);
        }else if (beforeId != null && afterId == null){
            flag = calMotionNumberMinus(beforeId);
        }else {
            flag = calMotionNumberPlus(afterId) && calMotionNumberMinus(beforeId);
        }

        return flag;
    }

    @Override
    public boolean calMotionNumberPlus(Long id) {
        if(id == null){
            return false;
        }
        AttatchManagement attatchManagement =  queryDetailsById(id);
        if(attatchManagement == null){
            return false;
        }

        if(attatchManagement.getTotals() == 0){
            return false;
        }

        if(attatchManagement.getAvailableCount() == 0){
            return false;
        }

        int numberUserd = attatchManagement.getNumberUsed() + 1;
        int availableCount = attatchManagement.getAvailableCount() - 1 ;
        AttatchManagementDto managementDto = new AttatchManagementDto();
        managementDto.setId(id);
        managementDto.setNumberUsed(numberUserd);
        managementDto.setAvailableCount(availableCount);
        return updateAttatchManagement(managementDto) != 0;
    }

    @Override
    public boolean calMotionNumberMinus(Long id) {
        if(id == null){
            return false;
        }

        AttatchManagement attatchManagement =  queryDetailsById(id);
        if(attatchManagement == null){
            return false;
        }

        //总数 = 0
        if(attatchManagement.getTotals() == 0){
            return false;
        }

        //已用人数 = 0
        if(attatchManagement.getNumberUsed() == 0){
            return false;
        }

        int numberUserd = attatchManagement.getNumberUsed() - 1;
        int availableCount = attatchManagement.getAvailableCount() + 1 ;
        AttatchManagementDto managementDto = new AttatchManagementDto();
        managementDto.setId(id);
        managementDto.setNumberUsed(numberUserd);
        managementDto.setAvailableCount(availableCount);
        return updateAttatchManagement(managementDto) != 0;
    }

}