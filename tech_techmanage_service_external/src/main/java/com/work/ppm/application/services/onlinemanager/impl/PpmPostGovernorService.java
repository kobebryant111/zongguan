package com.work.ppm.application.services.onlinemanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.onlinemanager.PpmPostGovernorDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.onlinemanager.PpmPostGovernor;
import com.work.ppm.application.model.onlinemanager.ext.PpmPostGovernorInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.quartz.interfaces.PpmPostGovernorJobService;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.onlinemanager.IPpmPostGovernorService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
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
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
  * 描述
  */
@Service("ppmPostGovernorService")
@Slf4j
public class PpmPostGovernorService extends BaseDaoSupper implements IPpmPostGovernorService
        , ICommonDeleteFlowService, PpmPostGovernorJobService {
    /**
      * 实体类路径
      */
    private static final String PPMPOSTGOVERNOR = "com.work.ppm.application.model.onlinemanager.PpmPostGovernor";

    /**
     * 流程实例名称
     */
    private static final String FLOW_NAME="com.work.ppm.bps.daCenterPostGovernor";

    /**
     * 流程编号前缀
     */
    private static final String DEMAND_NO_PREFIX="HDFK";
    /**
     * 标题前缀
     */
    private static final String TITLE_PREFIX="后督反馈_";

    /**
     * 数据中心后督管理员角色ID
     */
    private static final String ROLE_ID= "HD0001";

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 系统角色查询接口
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;

    /**
     * 流程对象接口
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;

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

    @Override
    public void queryPager(PpmPostGovernorDto ppmPostGovernorDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmPostGovernorDto,PpmPostGovernorDto.class);
        myBatisDao.selectPager(PPMPOSTGOVERNOR, map,pager,"queryPagerList");
    }


    @Override
    public List<PpmPostGovernorInfo> selectList(PpmPostGovernorDto ppmPostGovernorDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmPostGovernorDto, PpmPostGovernorDto.class);
        return myBatisDao.selectListT(PPMPOSTGOVERNOR,map,"queryPagerList");
    }

    @Override
    public List<PpmPostGovernorDto> exportPostGovernorInputDatas(PpmPostGovernorDto ppmPostGovernorDto) {
        // 处理 录入记录 选择导入 并且要兼顾 查询条件内容
        if(StringUtil.isNotBlank(ppmPostGovernorDto.getIds())){
            String[] ids=ppmPostGovernorDto.getIds().split(",");
            StringBuilder stringBuilder=new StringBuilder();
            for (String id: ids
                 ) {
                stringBuilder.append("'"+id+"',");
            }
            ppmPostGovernorDto.setIds(stringBuilder.toString()
                    .substring(0,stringBuilder.toString().length()-1));
        }
        List<PpmPostGovernorInfo> ppmPostGovernorInfos=this.selectList(ppmPostGovernorDto);
        return ppmPostGovernorInfos.stream().map(item->{
                    PpmPostGovernorDto postGovernorDto=new PpmPostGovernorDto();
                    BeanUtils.copyProperties(item,postGovernorDto);
                    postGovernorDto.setProblemType(item.getProblemTypeName());
                    return postGovernorDto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional("defaultTx")
    public int addPpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto, CommOperatorDoLoginDto user) {
        PpmPostGovernor ppmPostGovernor = JsonUtil.jsonToClass(ppmPostGovernorDto, PpmPostGovernor.class);
        Long id = getNextSequence(PpmPostGovernor.class);
        ppmPostGovernor.setApplyDate(new Date());
        //设置Id
        ppmPostGovernor.setApplyBy(user.getUserid());
        if(StringUtil.isNotBlank(ppmPostGovernor.getDept())){
            ppmPostGovernor.setDept(ppmPostGovernor.getDept().replace("dep",""));
        }
        //未触发反馈流程 状态都设置草稿状态
        ppmPostGovernor.setStatus("2");
        ppmPostGovernor.setTitle(TITLE_PREFIX+ppmPostGovernorDto.getPsireName());
        // 如果需要请set 主键id
        ppmPostGovernor.setId(id);
        myBatisDao.executionPojo(PPMPOSTGOVERNOR, ppmPostGovernor, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmPostGovernorInfo queryDetail(PpmPostGovernorDto ppmPostGovernorDto) {
        PpmPostGovernor ppmPostGovernor = JsonUtil.jsonToClass(ppmPostGovernorDto, PpmPostGovernor.class);
        PpmPostGovernorInfo data = myBatisDao.selectClass(PPMPOSTGOVERNOR, ppmPostGovernor,
        "queryDetail");
        return JsonUtil.jsonToClass(data, PpmPostGovernorInfo
                .class);
    }

    @Override
    @Transactional("defaultTx")
    public int updatePpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto) {
        PpmPostGovernor ppmPostGovernor = JsonUtil.jsonToClass(ppmPostGovernorDto, PpmPostGovernor.class);
        if(StringUtil.isNotBlank(ppmPostGovernorDto.getPsireName())){
            ppmPostGovernor.setTitle(TITLE_PREFIX+ppmPostGovernorDto.getPsireName());
        }
        return myBatisDao.executionPojo(PPMPOSTGOVERNOR, ppmPostGovernor, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmPostGovernor(PpmPostGovernorDto ppmPostGovernorDto) {
        PpmPostGovernor ppmPostGovernor = JsonUtil.jsonToClass(ppmPostGovernorDto, PpmPostGovernor.class);
        return myBatisDao.executionPojo(PPMPOSTGOVERNOR, ppmPostGovernor, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    @Transactional("defaultTx")
    public boolean importExcelInfo(MultipartFile file, CommOperatorDoLoginDto user) {
        boolean flag=false;
        try {
            List<PpmPostGovernorDto> ppmPostGovernorDtos = ExcelUtil.importExcel(file, PpmPostGovernorDto.class);
            ppmPostGovernorDtos.stream().forEach(item-> {
                //用来判断 excel 删除单元格内容后 提交数据转换为null 的情况
                if(!(item.getPsireName()==null && item.getPsireNo()==null
                        &&item.getRelationUserName()==null&& item.getProblemType()==null
                        &&item.getProblemDetail()==null)){
                    this.setPpmPostGovernorDto(item);
                    this.addPpmPostGovernor(item,user);
                }
            });
            flag=true;
        }catch (Exception e){
            log.info("模板导入出错，错误信息{}",e);
            throw new BizException(ApiCode.EXCEL_IMPORT_FAIL,e.getMessage());
        }
        return flag;
    }

    /**
     *  excel 导入洗数据
     * @param item
     * @return ppmPostGovernorDto
     */
    private PpmPostGovernorDto setPpmPostGovernorDto(PpmPostGovernorDto item){
        item.setDeptName("");
        String linkStr="-";
        String badgeId="";
        // 用来处理导入人员格式 姓名(工号)  并且兼容只有工号
        if(StringUtil.isNotBlank(item.getRelationUserName())){
            if(item.getRelationUserName().contains("(")&&item.getRelationUserName().contains(")")){
                badgeId=item.getRelationUserName().substring(item.getRelationUserName().indexOf('(')+1,
                        item.getRelationUserName().lastIndexOf(')'));
            }else if(item.getRelationUserName().contains("（")&&item.getRelationUserName().contains("）")){
                badgeId=item.getRelationUserName().substring(item.getRelationUserName().indexOf('（')+1,
                        item.getRelationUserName().lastIndexOf('）'));
            }else{
                badgeId=item.getRelationUserName();
            }
        }
        CommEmployee commEmployee=commEmployeeService.getCommEmployeeInfo(badgeId);
        if(commEmployee !=null){
            item.setRelationUserName(commEmployee.getEmpname()+"("+commEmployee.getBadge()+")");
            item.setDept(String.valueOf(commEmployee.getDepid()));
            CommDepartmentDto  commDepartmentDto=commDepartmentService.queryParentDeptId(String.valueOf(commEmployee.getDepid()));
            item.setDeptName(commDepartmentDto.getLocation().substring(commDepartmentDto.getLocation().lastIndexOf(linkStr)+1
                    ,commDepartmentDto.getLocation().length()));
            item.setRelationUser(badgeId);
        }else {
            item.setRelationUser("");
            item.setRelationUserName("");
            item.setDeptName("");
        }
        List<EosDictEntry> eoss=eosDictEntryService.getTypeidForList("NEWKG_WTFL");
        boolean flag=true;
        for (EosDictEntry dict:eoss
             ) {
            if(dict.getDictname().equals(item.getProblemType())){
                item.setProblemType(dict.getDictid());
                flag=false;
            }
        }
        //处理类型填错了 和字典匹配不上 把录入的值清空
        if(flag){
            item.setProblemType("");
        }
        PpmProjectFlowDto ppmProjectFlowDto= ppmProjectFlowService.queryDetailForProjectno(
                item.getPsireNo()==null?"":item.getPsireNo());
        if(ppmProjectFlowDto==null){
            item.setPsireNo("");
        }
        return item;
    }

    @Override
    @Transactional("defaultTx")
    public int batchDeletePpmPostGovernor(List<PpmPostGovernorDto> ppmPostGovernorDtos) {
        int i=0;
        for (PpmPostGovernorDto item:ppmPostGovernorDtos
             ) {
            item.setStatus("0");
            i+=this.updatePpmPostGovernor(item);
        }
      return i;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult flowStartAndRun(List<PpmPostGovernorDto> ppmPostGovernorDtos, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        ppmPostGovernorDtos.stream().forEach(item ->{
            BpsWorkFlowDto bps = item.getBps();
            if(null == bps) {
                bps = new BpsWorkFlowDto();
            }
            //设置 用户id
            bps.setUserId(user.getUserid());
            //当前事务id
            String xid = "";
            try {
                //修改当前记录状态为正常
                item.setStatus("1");
                Map<String, String> map = BpsDto.getInstance().setStatus("-1")
                        .setUsers(item.getRelationUser()).build();
                bps.setRelaData(map);
                bps.setApplydesc("流程触发");
                bps.setConclusion("01");
                bps.setFlowUrl("daCenterPostGovernor");
                bps.setProcessInstDesc("流程触发");
                bps.setSuggest("提交");
                bps.setProcessInstName(item.getTitle());
                bps.setProcessDefName(FLOW_NAME);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                String instanceid = BpsUtil.createProcessAndStart(bps, xid);
                //流程发起失败
                if ("-1".equals(instanceid)) {
                    throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 创建发起流转流程失败");
                }
                bps.setProcessInstId(instanceid);
                item.setProcessInstId(instanceid);
                item.setPsireFeedbackNo(CodeGenerater.generate(DEMAND_NO_PREFIX, 16, "yyyyMMdd"));
                this.updatePpmPostGovernor(item);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            }catch (Exception e){
                log.error("科管数据中心后督管理需求-发起并流转流程失败，失败原因{}",e.getMessage(), e);
                BpsUtil.transactionRollback(bps.getUserId(), xid);
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 创建发起流转流程失败");

            }
        });
        result.success();
        return result;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult relationUserRunFlowInfo(PpmPostGovernorDto ppmPostGovernorDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmPostGovernorDto.getBps();
        String xid = "";
        try {
            //下个接口二级部经理组审批
            List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers(String.valueOf(ppmPostGovernorDto.getDept()));
            StringJoiner joiner = new StringJoiner(",");
            employs.stream().forEach(ii -> joiner.add(ii.getBadge()));
            Map<String, String> map;
            // 设置当前流程状态
            map = BpsDto.getInstance()
                        .setStatus("-1")
                        .setUsers(joiner.toString()).build();
            bps.setRelaData(map);
            bps.setApplydesc("确认并反馈");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps,xid);
            this.updatePpmPostGovernor(ppmPostGovernorDto);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            log.error("后督管理流程申请关联人员确认与反馈，失败原因{}",e.getMessage(), e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 后督管理流程申请关联人员确认与反馈流程失败");
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult managerRunFlowInfo(PpmPostGovernorDto ppmPostGovernorDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmPostGovernorDto.getBps();
        String xid = "";
        try {
            //查询详情
            PpmPostGovernorInfo  ppmPostGovernorDetail=this.queryDetail(ppmPostGovernorDto);
            Map<String, String> map;
            // 设置当前流程状态
            if("02".equals(bps.getConclusion())) {
                map = BpsDto.getInstance()
                        .setStatus("01")
                        .setUsers(ppmPostGovernorDetail.getRelationUser()).build();
            }else {
                //下个接口后督管理人员审批
                List<EosDictEntry> eoss=eosDictEntryService.getTypeidForList("NEWKG_HDGLRY");
                StringJoiner joiner = new StringJoiner(",");
                eoss.stream().forEach(ii ->
                        joiner.add(ii.getDictid())
                );
                map = BpsDto.getInstance()
                        .setStatus("-1")
                        .setUsers(joiner.toString()).build();
                //设置二级部审批意见
                ppmPostGovernorDetail.setTwoManagerSuggestion(ppmPostGovernorDto.getBps().getSuggest());
                //更新二级部审批已经
                this.updatePpmPostGovernor(JsonUtil.jsonToClass(ppmPostGovernorDetail, PpmPostGovernorDto
                        .class));
            }
            bps.setRelaData(map);
            bps.setApplydesc("二级部经理审批");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps,xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            String errorMsg="后督管理流程申请二级部经理确审核";
            log.error(errorMsg+"失败原因{}",e.getMessage(), e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 后督管理流程申请二级部经理确审核流程失败");
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    @Transactional("defaultTx")
    public ApiResult endRunFlowInfo(PpmPostGovernorDto ppmPostGovernorDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmPostGovernorDto.getBps();
        //查询详情
        String xid = "";
        try {
            Map<String, String> map;
            // 设置当前流程状态
             map = BpsDto.getInstance().setStatus("01")
                        .setUsers(bps.getUserId()).build();
            bps.setRelaData(map);
            bps.setApplydesc("后督审核");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps,xid);
            this.updatePpmPostGovernor(ppmPostGovernorDto);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            String errorMsg="后督管理流程申请后督审核";
            log.error(errorMsg+"失败原因{}",e.getMessage(), e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL,"请求BPS 后督管理流程申请后督审核流程失败");
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    @Transactional("defaultTx")
    public void doDelete(BpsWorkFlowDto bps) {
        PpmPostGovernorDto ppmPostGovernorDto=new PpmPostGovernorDto();
        ppmPostGovernorDto.setProcessInstId(bps.getProcessInstId());
        PpmPostGovernorInfo ppmPostGovernorInfo= this.queryDetail(ppmPostGovernorDto);
        ppmPostGovernorDto=JsonUtil.jsonToClass(ppmPostGovernorInfo, PpmPostGovernorDto
                .class);
        ppmPostGovernorDto.setStatus("0");
        this.updatePpmPostGovernor(ppmPostGovernorDto);
    }

    @Override
    @Transactional("defaultTx")
    public void upPostGovernorAdminInfo() {
        List<ImUserRoleRel> imUserRoleRels = imUserRoleRelService.queryUserIdForRoleId(ROLE_ID);
        //数据中心后督管理员角色人员同步 全部删除存量的角色人员数据
        if (!imUserRoleRels.isEmpty()) {
            int result = imUserRoleRelService.deleteImUserRoleRel(ROLE_ID);
            if (result == 0) {
                throw new BizException(ApiCode.DELETE_FAIL, "数据中心后督管理员角色人员信息批量删除失败");
            }
        }
        // 1.查询科技部行员
        List<CommEmployeeDto> commEmployeeDtos = commEmployeeService.queryOnTheJobUsers();
        List<ImUserRoleRel> imUserRoleRelList = new ArrayList<>();
        commEmployeeDtos.stream().forEach(ii -> {
            ImUserRoleRel imUserRoleRel = new ImUserRoleRel();
            imUserRoleRel.setUrrRoleid(ROLE_ID);
            imUserRoleRel.setUrrUserid(ii.getBadge());
            imUserRoleRel.setSysType(1);
            imUserRoleRelList.add(imUserRoleRel);
        });

        //当前关联部门外包人员获取
        Map param = new HashMap();
        param.put("deptId", "'3636'");
        param.put("state", "0");
        List<PpmOutsourcingBasic> ppmOutsourcingBasics = iPpmOutsourcingBasicService.queryOutsList(param);
        ppmOutsourcingBasics.stream().forEach(ii -> {
            ImUserRoleRel imUserRoleRel = new ImUserRoleRel();
            imUserRoleRel.setUrrRoleid(ROLE_ID);
            imUserRoleRel.setUrrUserid(ii.getJobId());
            imUserRoleRel.setSysType(1);
            imUserRoleRelList.add(imUserRoleRel);
        });
        //批量插入员工到 数据中心需求管理员角色
        imUserRoleRelService.addImUserRoleRel(imUserRoleRelList);
    }
}
