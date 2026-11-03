package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.onlinemanager.PpmDacenterDemandDto;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.onlinemanager.PpmDacenterDemand;
import com.work.ppm.application.model.onlinemanager.ext.PpmDacenterDemandInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.quartz.interfaces.IPpmDacenterDemandJobService;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.onlinemanager.IPpmDacenterDemandService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.work.workflow.client.soentity.BPSWorkItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
  * 描述
  */
@Service("ppmDacenterDemandService")
@Slf4j
public class PpmDacenterDemandService extends BaseDaoSupper implements IPpmDacenterDemandService,
        ICommonDeleteFlowService, IPpmDacenterDemandJobService {
    /**
      * 实体类路径
      */
    private static final String PPMDACENTERDEMAND = "com.work.ppm.application.model.onlinemanager.PpmDacenterDemand";

    /**
     * 流程实例名称
     */
    private static final String FLOW_NAME="com.work.ppm.bps.daCenterDemandManager";

    /**
     * 流程编号前缀
     */
    private static final String DEMAND_NO_PREFIX="SJZXXQ";
    /**
     * 管理员角色ID
     */
    private static final String ROLE_ID= "DEMANDADMIN";

    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 系统角色查询接口
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;

    /**
     * 科技人员接口
     */
    @Autowired
    private ICommEmployeeService iCommEmployeeService;
    /**
     * 外包人员接口
     */
    @Autowired
    private IPpmOutsourcingBasicService iPpmOutsourcingBasicService;

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     *公共流程接口Service
     */
    @Autowired
    private ICommonFlowService commonFlowService;

    @Override
    public void queryPager(PpmDacenterDemandDto ppmDacenterDemandDto, Pager pager) {
        this.setPpmDacenterDemandDto(ppmDacenterDemandDto);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDacenterDemandDto,PpmDacenterDemandDto.class);
        myBatisDao.selectPager(PPMDACENTERDEMAND, map,pager,"queryPagerList");
        List<PpmDacenterDemandInfo> ppmDacenterDemandInfos= (List<PpmDacenterDemandInfo>) pager.getList();
        String linkStr="-";
        ppmDacenterDemandInfos.stream().forEach(i ->{
                    if(i.getDeptName().lastIndexOf(linkStr) !=-1){
                        i.setDeptName(i.getDeptName()
                                .substring(i.getDeptName().lastIndexOf(linkStr)+1,
                                        i.getDeptName().length()));
                    }
                }
        );
    }

    @Override
    public List<PpmDacenterDemandInfo> selectList(PpmDacenterDemandDto ppmDacenterDemandDto) {
        this.setPpmDacenterDemandDto(ppmDacenterDemandDto);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmDacenterDemandDto,PpmDacenterDemandDto.class);
        return myBatisDao.selectListT(PPMDACENTERDEMAND,map,"queryPagerList");
    }

    /**
     * 用于做关联需求状态 和 同步需求状态 输入的拆分
     * @param ppmDacenterDemandDto
     * @return  ppmDacenterDemandDto
     */
    private PpmDacenterDemandDto setPpmDacenterDemandDto(PpmDacenterDemandDto ppmDacenterDemandDto){
        //用于做关联需求状态 和 同步需求状态 输入的拆分
        if(StringUtil.isNotBlank(ppmDacenterDemandDto.getRelationDemandStatus())){
            // 新科管关联需求状态码值
            String[] statusArr=  ppmDacenterDemandDto.getRelationDemandStatus().split(",");
            StringBuilder relationDemandStatus=new StringBuilder();
            StringBuilder demanStatus=new StringBuilder();
            for (String status:statusArr
            ) {
                EosDictEntry newKgXqzt=eosDictEntryService.getTypeidForDetail("NEWKG_XQZT",status);
                if(newKgXqzt !=null){
                    relationDemandStatus.append(status+",");
                    continue;
                }
                EosDictEntry oldKgXqzt=eosDictEntryService.getTypeidForDetail("BUSI_DEMAND_STATUS",status);
                if(oldKgXqzt !=null){
                    demanStatus.append(status+",");
                }
            }
            String relationDemandStatusStr=relationDemandStatus.toString();
            String demanStatusStr=demanStatus.toString();
            if(StringUtil.isNotBlank(relationDemandStatusStr)){
                ppmDacenterDemandDto.setRelationDemandStatus(relationDemandStatusStr.substring(0,relationDemandStatusStr.length()-1));
            }
            if(StringUtil.isNotBlank(demanStatusStr)){
                ppmDacenterDemandDto.setDemanStatus(demanStatusStr.substring(0,demanStatusStr.length()-1));
            }
        }
        return ppmDacenterDemandDto;
    }

    @Override
    public int addPpmDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto) {
        PpmDacenterDemand ppmDacenterDemand = JsonUtil.jsonToClass(ppmDacenterDemandDto, PpmDacenterDemand.class);
        Long id = getNextSequence(PpmDacenterDemand.class);
        // 如果需要请set 主键id
        ppmDacenterDemand.setId(id);
        myBatisDao.executionPojo(PPMDACENTERDEMAND, ppmDacenterDemand, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDacenterDemandInfo queryDetail(PpmDacenterDemandDto ppmDacenterDemandDto) {
        PpmDacenterDemand ppmDacenterDemand = JsonUtil.jsonToClass(ppmDacenterDemandDto, PpmDacenterDemand.class);
        PpmDacenterDemandInfo item = myBatisDao.selectClass(PPMDACENTERDEMAND, ppmDacenterDemand,
        "queryDetail");
        if(StringUtil.isNotBlank(item.getProcessInstId())){
            List<PpmDocumentDto> list = ppmDocumentService.getList(item.getProcessInstId());
            item.setDocList(list ==null?new ArrayList<>():list);
        }
        return item;
    }

    @Override
    public int updatePpmDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto) {
        if(ppmDacenterDemandDto.getId() ==null && !StringUtil.isStringNotEmpty(ppmDacenterDemandDto.getProcessInstId())){
            throw new BizException(ApiCode.UPATE_FAIL,"更新唯一主键为空！");
        }
        PpmDacenterDemand ppmDacenterDemand = JsonUtil.jsonToClass(ppmDacenterDemandDto, PpmDacenterDemand.class);
        return myBatisDao.executionPojo(PPMDACENTERDEMAND, ppmDacenterDemand, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto) {
        PpmDacenterDemand ppmDacenterDemand = JsonUtil.jsonToClass(ppmDacenterDemandDto, PpmDacenterDemand.class);
        return myBatisDao.executionPojo(PPMDACENTERDEMAND, ppmDacenterDemand, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult saveDraft(PpmDacenterDemandDto ppmDacenterDemandDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmDacenterDemandDto.getBps();
        if(null == bps) {
            bps = new BpsWorkFlowDto();
        }
        //设置 用户id
        bps.setUserId(user.getUserid());
        //当前流程状态
        String xid = "";
        String instanceid=bps.getProcessInstId();
        try {
            //设置需求状态
            ppmDacenterDemandDto.setStatus("2");
            // 新增
            if(!StringUtil.isNotBlank(bps.getProcessInstId())) {
                bps.setApplydesc("需求发起");
                bps.setProcessInstName(ppmDacenterDemandDto.getTitle());
                bps.setProcessDefName(FLOW_NAME);
                bps.setFlowUrl("daCenterDemandManager");
                bps.setProcessInstDesc("需求发起");
                xid=BpsUtil.transactionBegin(bps.getUserId());
                //调用bps 流程 返回流程编码
                instanceid = BpsUtil.createProcessInstance(bps,xid);
                //流程发起失败
                if("-1".equals(instanceid)){
                    throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 创建发起流转流程失败");
                }
                bps.setProcessInstId(instanceid);
                ppmDacenterDemandDto.setProcessInstId(instanceid);
                ppmDacenterDemandDto.setCreateDate(new Date());
                ppmDacenterDemandDto.setDemandNo(CodeGenerater.generate(DEMAND_NO_PREFIX,18,"yyyyMMdd"));
                this.addPpmDacenterDemand(ppmDacenterDemandDto);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            }else {
             //修改
                ppmDacenterDemandDto.setCreateDate(new Date());
                this.updatePpmDacenterDemand(ppmDacenterDemandDto);
            }
            // 根据 文档编号更新文档信息
            if(StringUtil.isNotBlank(ppmDacenterDemandDto.getFiles())) {
                PpmDocumentDto doc = new PpmDocumentDto();
                doc.setObjectno(bps.getProcessInstId());
                doc.setStage("需求发起");
                doc.setDocumentnos(ppmDacenterDemandDto.getFiles());
                ppmDocumentService.updatePpmDocument(doc);
            }
            result.setResults(instanceid);
            result.success(BpsUtil.queryProcessInfo(bps));
        }catch (Exception e){
            log.error("科管数据中心需求管理线上化需求-草稿异常,异常原因{}",e.getMessage(), e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("科管数据中心需求管理线上化流程保存草稿异常");
            return result;
        }
        return result;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult flowStartAndRun(PpmDacenterDemandDto ppmDacenterDemandDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmDacenterDemandDto.getBps();
        if(null == bps) {
            bps = new BpsWorkFlowDto();
        }
        //设置 用户id
        bps.setUserId(user.getUserid());
        //当前流程状态
        String xid = "";
        String instanceid=bps.getProcessInstId();
        try {
            //设置需求状态
            ppmDacenterDemandDto.setStatus("1");
            //获取二级部经理组
            List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers
                    (String.valueOf(ppmDacenterDemandDto.getDemandDept()));
            StringJoiner joiner = new StringJoiner(",");
            employs.stream().forEach(ii -> joiner.add(ii.getBadge()));
            Map<String, String> map = BpsDto.getInstance().setStatus("-1")
                    .setUsers(joiner.toString()).build();
            bps.setRelaData(map);
            bps.setApplydesc("需求发起");
            bps.setConclusion("01");
            bps.setFlowUrl("daCenterDemandManager");
            bps.setProcessInstDesc("需求发起");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            if(!StringUtil.isNotBlank(bps.getProcessInstId())) {
                // 直接发起流转流程
                bps.setProcessInstName(ppmDacenterDemandDto.getTitle());
                bps.setProcessDefName(FLOW_NAME);
                //调用bps 流程 返回流程编码
                instanceid = BpsUtil.createProcessAndStart(bps, xid);
                //流程发起失败
                if ("-1".equals(instanceid)) {
                    throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 创建发起流转流程失败");
                }
                bps.setProcessInstId(instanceid);
                ppmDacenterDemandDto.setProcessInstId(instanceid);
                ppmDacenterDemandDto.setCreateDate(new Date());
                ppmDacenterDemandDto.setDemandNo(CodeGenerater.generate(DEMAND_NO_PREFIX, 18, "yyyyMMdd"));
                this.addPpmDacenterDemand(ppmDacenterDemandDto);
            }else {
                // 从草稿过来发起
                ppmDacenterDemandDto.setCreateDate(new Date());
                this.updatePpmDacenterDemand(ppmDacenterDemandDto);
                BpsUtil.finshWorkItem(bps,xid);
            }
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
            // 根据 文档编号更新文档信息
            if(StringUtil.isNotBlank(ppmDacenterDemandDto.getFiles())) {
                PpmDocumentDto doc = new PpmDocumentDto();
                doc.setObjectno(bps.getProcessInstId());
                doc.setStage("需求发起");
                doc.setDocumentnos(ppmDacenterDemandDto.getFiles());
                ppmDocumentService.updatePpmDocument(doc);
            }
            result.setResults(instanceid);
            result.success(BpsUtil.queryProcessInfo(bps));
        }catch (Exception e){
            log.error("科管数据中心需求管理线上化需求-发起并流转流程失败，失败原因{}",e.getMessage(), e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("科管数据中心需求管理线上化流程发起并流转流程异常");
            return result;
        }
        return result;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult runFlowInfo(PpmDacenterDemandDto ppmDacenterDemandDto, BPSWorkItem item) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmDacenterDemandDto.getBps();
        String xid = "";
        String activityDefID=item.getActivityDefID();
        try {
            Map<String,String> map;
            // 设置当前流程状态
            if("02".equals(bps.getConclusion())) {
                map = BpsDto.getInstance()
                        .setStatus("01").build();
            }else {
                map = BpsDto.getInstance()
                        .setStatus("-1").build();
            }
            bps.setWorkItemId(String.valueOf(item.getWorkItemID()));
            if("manualActivity1".equals(activityDefID)){
                // 二级部经理审批节点
                bps.setApplydesc("二级部经理审批");
                //获取下一个节点审批人 运营管理部收口人员
                List<EosDictEntry> eoss=eosDictEntryService.getTypeidForList("NEWKG_YWBSK");
                StringJoiner joiner = new StringJoiner(",");
                eoss.stream().forEach(ii ->
                    joiner.add(ii.getDictid())
                );
                map.put("users",joiner.toString());
            }else if("manualActivity2".equals(activityDefID)){
                // 运营管理部收口
                bps.setApplydesc("需求收口");
                this.updatePpmDacenterDemand(ppmDacenterDemandDto);
                map.put("users",bps.getUserId());
                // 根据 文档编号更新文档信息
                if(StringUtil.isNotBlank(ppmDacenterDemandDto.getFiles())) {
                    PpmDocumentDto doc = new PpmDocumentDto();
                    doc.setObjectno(bps.getProcessInstId());
                    doc.setStage("需求收口");
                    doc.setDocumentnos(ppmDacenterDemandDto.getFiles());
                    ppmDocumentService.updatePpmDocument(doc);
                }
            }
            bps.setRelaData(map);
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps,xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            String errorMsg="数据中心需求申请/";
            if("manualActivity1".equals(activityDefID)){
                errorMsg=errorMsg+"二级部经理审批:";
            }else {
                errorMsg=errorMsg+"运营管理部收口:";
            }
            log.error(errorMsg+"失败原因{}",e.getMessage(), e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure(errorMsg+"流转失败,失败原因");
            return result;
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    public PpmDacenterDemandInfo queryMobileDetail(PpmDacenterDemandDto ppmDacenterDemandDto) {
        PpmDacenterDemandInfo item=this.queryDetail(ppmDacenterDemandDto);
        // 手机端
        String mobilePath = ppmDacenterDemandDto.getDir()
                + ppmDacenterDemandDto.getProcessInstId()
                + ppmDacenterDemandDto.getSeparator()
                ;
        item.getDocList().stream().forEach(ii -> {
            String fileName1 = StringUtil.isNotBlank(ii.getTag2()) ? ii.getTag2() : ii.getDocname();
            String sourceFilePath = eosDictEntryService
                    .getTypeidForDetail("NEW_MOBILE_FILE","fileurl").getDictname()
                    + "?stockPath=" + ii.getStockpath()
                    + "&docname=" + fileName1;
            ii.setSourceFilePath(sourceFilePath);
            ii.setMobilePath(mobilePath + fileName1);
        });
        return item;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult modifyDacenterDemand(PpmDacenterDemandDto ppmDacenterDemandDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        int rsult=this.updatePpmDacenterDemand(ppmDacenterDemandDto);
        // 根据 文档编号更新文档信息
        if(StringUtil.isNotBlank(ppmDacenterDemandDto.getFiles())) {
            BPSWorkItem item = commonFlowService.queryWorkItem(ppmDacenterDemandDto.getBps());
            String activityDefID=item.getActivityDefID();
            PpmDocumentDto doc = new PpmDocumentDto();
            doc.setObjectno(ppmDacenterDemandDto.getBps().getProcessInstId());
            //用来判断提交前上传文件后 保存文件所属应该在那个节点
            if("manualActivity2".equals(activityDefID)){
                doc.setStage("需求收口");
            }else {
                doc.setStage("需求发起");
            }
            doc.setDocumentnos(ppmDacenterDemandDto.getFiles());
            ppmDocumentService.updatePpmDocument(doc);
        }
        if(rsult==1){
            result.success();
        }else {
            result.failure("更新失败");
        }


        return result;
    }

    @Override
    @Transactional("defaultTx")
    public void doDelete(BpsWorkFlowDto bps) {

         PpmDacenterDemandDto ppmDacenterDemandDto = new PpmDacenterDemandDto();
         ppmDacenterDemandDto.setProcessInstId(bps.getProcessInstId());
         //设置需求状态
         ppmDacenterDemandDto.setStatus("0");
         this.updatePpmDacenterDemand(ppmDacenterDemandDto);
    }



    @Override
    @Transactional("defaultTx")
    public void upDacenterAdminInfo() {
        List<ImUserRoleRel> imUserRoleRels=imUserRoleRelService.queryUserIdForRoleId(ROLE_ID);
        //科管数据中心需求管理员角色员工信息同步 全部删除存量的角色人员数据
        if(!imUserRoleRels.isEmpty()){
           int result= imUserRoleRelService.deleteImUserRoleRel(ROLE_ID);
           if(result ==0){
               throw new BizException(ApiCode.DELETE_FAIL,"管数据中心需求管理员角色员工信息批量删除失败");
           }
        }
        // 查询固定部门的人员数据 （运维管理部、系统管理部、网络管理部、运行服务部、基础设施部、应用管理部）
        CommEmployeeDto commEmployeeDto=new CommEmployeeDto();
        commEmployeeDto.setDeps("'3636','141','142','2494','7969','5070'");
        commEmployeeDto.setIsdisabled("0");
        //当前关联部门行员获取
        List<CommEmployeeDto> commEmployeeDtos= iCommEmployeeService.getEmployeeLists(commEmployeeDto);
        List<ImUserRoleRel> imUserRoleRelList=new ArrayList<>();
        commEmployeeDtos.stream().forEach(ii -> {
            ImUserRoleRel imUserRoleRel=new ImUserRoleRel();
            imUserRoleRel.setUrrRoleid(ROLE_ID);
            imUserRoleRel.setUrrUserid(ii.getBadge());
            imUserRoleRel.setSysType(1);
            imUserRoleRelList.add(imUserRoleRel);
        } );
        //当前关联部门外包人员获取
        Map param=new HashMap();
        param.put("deptId","'3636','141','142','2494','7969','5070'");
        param.put("state","0");
        List<PpmOutsourcingBasic> ppmOutsourcingBasics=iPpmOutsourcingBasicService.queryOutsList(param);
        ppmOutsourcingBasics.stream().forEach(ii -> {
            ImUserRoleRel imUserRoleRel=new ImUserRoleRel();
            imUserRoleRel.setUrrRoleid(ROLE_ID);
            imUserRoleRel.setUrrUserid(ii.getJobId());
            imUserRoleRel.setSysType(1);
            imUserRoleRelList.add(imUserRoleRel);
        } );
        //批量插入员工到 数据中心需求管理员角色
        imUserRoleRelService.addImUserRoleRel(imUserRoleRelList);
    }
}
