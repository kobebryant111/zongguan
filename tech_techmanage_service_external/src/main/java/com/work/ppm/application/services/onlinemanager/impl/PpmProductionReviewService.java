package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.onlinemanager.PpmProjectNewonlineDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftDto;
import com.work.ppm.application.services.onlinemanager.IPpmProjectNewonline1Service;
import com.work.ppm.application.services.projectmanager.IPpmProjectBuildSoftService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.onlinemanager.PpmProductionReview;
import com.work.ppm.application.dto.onlinemanager.PpmProductionReviewDto;
import com.work.ppm.application.services.onlinemanager.IPpmProductionReviewService;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmProductionReviewService")
@Slf4j
public class PpmProductionReviewService extends BaseDaoSupper implements IPpmProductionReviewService {
    /**
      * 实体类路径
      */
    private static String PPMPRODUCTIONREVIEW = "com.work.ppm.application.model.onlinemanager.PpmProductionReview";
    /**
     * 流程FLOWID
     */
    private static String FH_SXYZ_FJH = "com.work.ppm.bps.newfhsxyzFJH";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 员工业务类
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 项目类
     */
    @Autowired
    private IPpmProjectBuildSoftService ppmProjectBuildSoftService;
    /**
     * 业务字典类
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 上线类
     */
    @Autowired
    private IPpmProjectNewonline1Service ppmProjectNewonlineService1;

    @Override
    public void queryPager(PpmProductionReviewDto ppmProductionReviewDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProductionReviewDto,PpmProductionReviewDto.class);
        myBatisDao.selectPager(PPMPRODUCTIONREVIEW, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmProductionReview(PpmProductionReviewDto ppmProductionReviewDto) {
        PpmProductionReview ppmProductionReview = JsonUtil.
                jsonToClass(ppmProductionReviewDto, PpmProductionReview.class);
        Long id = getNextSequence(PpmProductionReview.class);

        // 如果需要请set 主键id
        ppmProductionReview.setId(id);
        myBatisDao.executionPojo(PPMPRODUCTIONREVIEW, ppmProductionReview,
                IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmProductionReviewDto queryDetail(PpmProductionReviewDto ppmProductionReviewDto) {
        PpmProductionReview ppmProductionReview = JsonUtil.
                jsonToClass(ppmProductionReviewDto, PpmProductionReview.class);
        PpmProductionReview data = myBatisDao.selectClass(PPMPRODUCTIONREVIEW, ppmProductionReview,
        "queryDetail");
        PpmProductionReviewDto item = JsonUtil.jsonToClass(data, PpmProductionReviewDto
        .class);
        return item;
    }

    @Override
    public int updatePpmProductionReview(PpmProductionReviewDto ppmProductionReviewDto) {
        PpmProductionReview ppmProductionReview = JsonUtil.
                jsonToClass(ppmProductionReviewDto, PpmProductionReview.class);
        return myBatisDao.executionPojo(PPMPRODUCTIONREVIEW, ppmProductionReview,
                IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    @Transactional("defaultTx")
    public int addPpmProductionReviewFlow(PpmProductionReviewDto ppmProductionReviewDto, String userId) {
        int success = 0;
        BpsWorkFlowDto bps = null;
        String instanceid;
        String xid = "";
        try {
            PpmProjectBuildSoftDto ppmProjectBuildSoftDto =
                    ppmProjectBuildSoftService.getProjectInfoByOnlineno(ppmProductionReviewDto.getOnlineno());
            PpmProjectNewonlineDto ppmProjectNewonlineDto =
                    ppmProjectNewonlineService1.queryDetailByOnlineno(ppmProductionReviewDto.getOnlineno());
            if (StringUtil.isNotEmpty(ppmProjectBuildSoftDto) && StringUtil.isNotEmpty(ppmProjectNewonlineDto)) {
                HashMap<String, String> relaData = new HashMap<String, String>();
                relaData.put("users", ppmProjectBuildSoftDto.getBusiuserid());
                relaData.put("status", "-1");
                // =================== 流程信息 ===========
                bps = new BpsWorkFlowDto();
                // 开启bps事务
                bps.setFlowUrl("newfhsxyzFJH");
                bps.setProcessDefName(FH_SXYZ_FJH);
                String projectname = ppmProjectBuildSoftDto.getProjectname() +
                        eosDictEntryService.getTypeidForDetail("BUSI_ORGAREA",
                                ppmProjectNewonlineDto.getOrgarea()).getDictname() +
                        DateUtil.dateToStr(ppmProjectNewonlineDto.getPlanonline(), "yyyyMMdd");
                bps.setProcessInstName(projectname);
                bps.setProcessInstDesc("发起上线验证");
                bps.setUserId(userId);
                bps.setRelaData(relaData);
                bps.setConclusion("01");
                bps.setApplydesc(bps.getProcessInstDesc());
                xid = BpsUtil.transactionBegin(userId);
                //发起流程,processInstID为返回的流程id 唯一
                instanceid = BpsUtil.createProcessAndFinishFirst(bps, xid);

                // 提交事务
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);

                PpmProductionReviewDto review = ppmProductionReviewDto;
                review.setIsdel(0);
                review.setFlowid(FH_SXYZ_FJH);
                review.setInstanceid(instanceid);
                review.setCreators(commEmployeeService.getCommEmployeeInfo(bps.getUserId()).getEmpname());
                review.setCreatetime(Calendar.getInstance().getTime());
                review.setPlanonline(DateUtil.dateToStr(ppmProjectNewonlineDto.getPlanonline(), "yyyy-MM-dd"));
                review.setChgdate(ppmProjectNewonlineDto.getPlanonline());
                if ("02".equals(ppmProjectNewonlineDto.getPlanonline())) {
                    review.setIsqbsx("1");//全部上线
                } else if ("01".equals(ppmProjectNewonlineDto.getPlanonline())) {
                    review.setIsqbsx("2");//部分上线
                }
                review.setProductionno(ppmProductionReviewDto.getProductionno());
                review.setOnlineno(ppmProjectNewonlineDto.getOnlineno());
                review.setProjectno(ppmProjectNewonlineDto.getProjectno());
                review.setProjectname(ppmProjectBuildSoftDto.getProjectname());
                this.addPpmProductionReview(review);

                success++;
            }
        } catch (Exception e) {
            try{
                if(bps != null){
                    BpsUtil.transactionRollback(userId, xid);
                }
            }catch(Exception e1){
                log.error("流程提交报错:", e1);
            }

            log.error(e.getMessage(), e);
            throw new BizException(ApiCode.FAIL, "上线验证流程创建异常！");
        }

        return success;
    }

    @Override
    @Transactional("defaultTx")
    public String approveReviewByBusjl(PpmProductionReviewDto ppmProductionReviewDto, BpsWorkFlowDto bps) {
        String xid = "";
        try {
            Map<String, String> map = new HashMap<>();
            map.put("users", bps.getUserId());
            map.put("status", "-1");
            bps.setRelaData(map);
            bps.setApplydesc("业务部门上线验证");
            bps.setConclusion("01");

            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);

            ppmProductionReviewDto.setLastmodtime(new Date());
            ppmProductionReviewDto.setLastmoduserid(bps.getUserId());
            ppmProductionReviewDto.setLastmoduser(
                    commEmployeeService.getCommEmployeeInfo(bps.getUserId()).getEmpname());
            this.updatePpmProductionReview(ppmProductionReviewDto);
        } catch (Exception e) {
            try{
                if(bps != null){
                    BpsUtil.transactionRollback(bps.getUserId(), xid);
                }
            }catch(Exception e1){
                log.error("流程提交报错:", e1);
            }

            log.error(e.getMessage(), e);
        }

        return  BpsUtil.queryProcessInfo(bps);
    }

    @Override
    public PpmProductionReviewDto queryDetailByInstanceid(PpmProductionReviewDto ppmProductionReviewDto) {
        PpmProductionReview ppmProductionReview = JsonUtil.
                jsonToClass(ppmProductionReviewDto, PpmProductionReview.class);
        PpmProductionReview data = myBatisDao.selectClass(PPMPRODUCTIONREVIEW, ppmProductionReview,
                "queryDetailByInstanceid");
        PpmProductionReviewDto item = JsonUtil.jsonToClass(data, PpmProductionReviewDto
                .class);
        return item;
    }

    @Override
    public int updateMoblie(PpmProductionReviewDto ppmProductionReviewDto) {
        BpsWorkFlowDto bps = ppmProductionReviewDto.getBps();
        AssertUtil.notNull(bps.getProcessInstId(),"【Instanceid】不能为空");
        ppmProductionReviewDto.setInstanceid(bps.getProcessInstId());
        return this.updatePpmProductionReview(ppmProductionReviewDto);
    }

}
