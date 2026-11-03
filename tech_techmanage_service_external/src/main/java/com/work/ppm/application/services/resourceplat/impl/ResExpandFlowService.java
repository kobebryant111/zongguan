package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.resourceplat.ResExpandDto;
import com.work.ppm.application.dto.resourceplat.ResExpandItemDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResExpandItem;
import com.work.ppm.application.model.resourceplat.ResExpandItemParts;
import com.work.ppm.application.model.resourceplat.ResFlowPeople;
import com.work.ppm.application.model.resourceplat.ResReportInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResExpandFlowService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.resourceplat.ResAddedConstant;
import com.work.ppm.commons.resourceplat.ResConstant;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源扩容
 *
 */
@Service("ResExpandFlowService")
public class ResExpandFlowService extends BaseDaoSupper implements IResExpandFlowService  {
    /**
     * 资源新增实体类
     */
    private static String RESADDED = "com.work.ppm.application.model.resourceplat.ResAdded";
    /**
     * 资源扩容项实体类
     */
    private static String RES_EXPAND_ITEM = "com.work.ppm.application.model.resourceplat.ResExpandItem";
    /**
     * 资源扩容项配置实体类
     */
    private static String RES_EXPAND_ITEM_PARTS = "com.work.ppm.application.model.resourceplat.ResExpandItemParts";
    /**
     * 资源统计报表实体类
     */
    private static String RES_REPORT_INFO = "com.work.ppm.application.model.resourceplat.ResReportInfo";
    /**
     * 字典项配置实体类
     */
    private static String EOSDICTENTRY = "com.work.ppm.application.model.systemmanager.EosDictEntry";
    /**
     * 资源配置主表实体类
     */
    private static String RES_CONFIG_COMBO = "com.work.ppm.application.model.resourceplat.ResConfigCombo";
    /**
     * 流程配置人员实体类
     */
    private static String RES_FLOW_PEOPLE = "com.work.ppm.application.model.resourceplat.ResFlowPeople";

    /**
     * 价格
     */
    private static String PRICE = "PRICE";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 资源新增接口
     */
    @Autowired
    private ResAddedService resAddedService;
    /**
     * 资源新增-扩展信息接口
     */
    @Autowired
    private ResAddedExtService resAddedExtService;
    /**
     * 字典表
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 根据流程实例ID，获取业务信息
     *
     * @param processInstId
     */
    @Override
    public ResAdded queryBusInfoByProcessInstId(String processInstId) {
        return myBatisDao.selectClass(RESADDED, processInstId, "queryBusInfoByProcessInstId");
    }

    /**
     * 提交工作项
     *
     * @param flowDto
     */
    @Override
    public boolean submitWorkItem(FlowDto flowDto) {
        if (submitWrok(flowDto)) {
            return false;
        }
        return true;
    }

    /**
     * 提交工作项
     * @param flowDto
     * @return boolean
     */
    private boolean submitWrok(FlowDto flowDto) {
        String xid = "";
        BpsWorkFlowDto bpsWorkFlowDto = new BpsWorkFlowDto();
        try {
            bpsWorkFlowDto(flowDto, bpsWorkFlowDto);
            Map<String, String> map = new HashMap<>();

            //获取系统部确认审批人审批人
            String nodeType = "";
            String auditUserId = "";
            //系统部确认
            if (ResConstant.MANUALACTIVITY1.equals(flowDto.getActivityDefID())) {
                //走测试部
                nodeType = isConclusion(flowDto, nodeType);
            } else if (ResConstant.MANUALACTIVITY2.equals(flowDto.getActivityDefID())) {//架构部
                nodeType = isNdetype(flowDto, nodeType);
            } else if (ResConstant.MANUALACTIVITY3.equals(flowDto.getActivityDefID())) {//综合部审批
                if ("01".equals(flowDto.getConclusion())) {
                    //设置人员，设置成系统部确认人员
                    ResAdded resAdd = myBatisDao.selectClass(RESADDED, flowDto.getId(), "queryResInfo");
                    if (null != resAdd) {
                        auditUserId = resAdd.getSysDealUser();
                        nodeType = "";
                    } else {
                        //获取不到，重新查一次配置人员
                        nodeType = ResConstant.MANUALACTIVITY1;
                    }
                }
            } else if (ResConstant.MANUALACTIVITY5.equals(flowDto.getActivityDefID())) {//测试部确认
                if ("01".equals(flowDto.getConclusion())) {
                    //架构部
                    nodeType = ResConstant.MANUALACTIVITY2;
                }
            } else if (ResConstant.MANUALACTIVITY.equals(flowDto.getActivityDefID())) {//重新提交
                nodeType = ResConstant.MANUALACTIVITY1;
            }
            //需要取审批人员
            if (isBpsFlow(flowDto, bpsWorkFlowDto, map, nodeType, auditUserId)){
                return true;
            }
        } catch (Exception e) {
            BpsUtil.transactionRollback(bpsWorkFlowDto.getUserId(), xid);
            throw new BizException(e);
        }
        return false;
    }

    /**
     * 设置工作流参数
     * @param flowDto
     * @param bpsWorkFlowDto
     */
    private void bpsWorkFlowDto(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto) {
        bpsWorkFlowDto.setProcessInstId(flowDto.getProcessInstId());
        bpsWorkFlowDto.setWorkItemId(flowDto.getWorkItemId());
        bpsWorkFlowDto.setUserId(flowDto.getCreator());
        bpsWorkFlowDto.setApplydesc(flowDto.getApplydesc());
        bpsWorkFlowDto.setConclusion(flowDto.getConclusion());
        bpsWorkFlowDto.setSuggest(flowDto.getAudit());
    }

    /**
     * 是否走架构部
     * @param flowDto
     * @param nodeType
     * @return nodeType
     */
    private String isConclusion(FlowDto flowDto, String nodeType) {
        if ("01".equals(flowDto.getConclusion())) {
            nodeType = ResConstant.MANUALACTIVITY5;
        }
        return nodeType;
    }

    /**
     * 需要取审批人员
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param map
     * @param nodeType
     * @param auditUserId
     * @return boolean
     */
    private boolean isBpsFlow(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map,
                              String nodeType, String auditUserId) {
        if (StringUtils.isNotEmpty(nodeType)) {
            //设置审批
            auditUserId = this.getSprInfo(nodeType);
            if (StringUtils.isEmpty(auditUserId)) {
                return true;
            }
        }
        getBpsFlow(flowDto, bpsWorkFlowDto, map, auditUserId);
        return false;
    }

    /**
     * 设置流程流转数据
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param map
     * @param auditUserId
     * @return xid
     */
    private String getBpsFlow(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map,
                              String auditUserId) {
        String xid;
        xid = getXid(flowDto, bpsWorkFlowDto, map, auditUserId);
        //设置流程流转数据

        //如果是系统部确认节点
        isManutl(flowDto, xid, bpsWorkFlowDto, map);
        BpsUtil.transactionCommit(bpsWorkFlowDto.getUserId(), xid);
        BpsUtil.flowLog(bpsWorkFlowDto);
        return xid;
    }

    /**
     * 结束，数据入报表库
     * @param flowDto
     * @param xid
     * @param bpsWorkFlowDto
     * @param map
     */
    private void isManutl(FlowDto flowDto, String xid, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map) {
        isBpsWork(flowDto, xid, bpsWorkFlowDto, map);
        //结束，数据入报表库
        if ("manualActivity4".equals(flowDto.getActivityDefID()) && "01".equals(flowDto.getConclusion())) {
            isActivcityDef(flowDto);
        }
    }

    /**
     * 数据入报表库
     * @param flowDto
     */
    private void isActivcityDef(FlowDto flowDto) {
        if ("0".equals(flowDto.getSourceType())) {
            //如果选择了补录编号后，则流转完成之后CMDB已使用改为是
            myBatisDao.executionPojo(RESADDED,flowDto.getAudit(),IMyBatisDao.SQL.UPDATE,"setCmdbUsed");
            resAddedService.addDataTransToReport(flowDto.getId());
            resAddedExtService.setIassInfoOrget(flowDto.getId());
        } else {
            dataTransToReport(flowDto.getId());
        }
    }

    /**
     * 资产扩容，根据主键，把扩容信息转移到报表中
     *
     * @param id
     */
    private void dataTransToReport(String id) {
        //查询扩容信息列表
        //1查询资源申请明细
        //2根据资源申请ID，查询资源申请项数据
        List<ResExpandItem> resExpandItemList = this.queryResourceExpandItem(id);
        //3循环遍历资源申请项，查询申请项配置信息列表
        if (null != resExpandItemList && !resExpandItemList.isEmpty()) {
            forRexExpanItem(id, resExpandItemList);
        }
    }

    /**
     * 查询申请项配置信息列表
     * @param id
     * @param resExpandItemList
     */
    private void forRexExpanItem(String id, List<ResExpandItem> resExpandItemList) {
        for (int i = 0; i < resExpandItemList.size(); i++) {
            ResExpandItem resExpandItem = resExpandItemList.get(i);
            //所属中心
            String ownCenter = resExpandItem.getOwnCenter();
            //获取所有扩容资源
            List<ResExpandItemParts> list = this.queryResourceExpandItemParts(resExpandItem.getId());
            for (int j = 0; j < list.size(); j++) {
                //获取资源信息，更新到报表中
                ResExpandItemParts resExpandItemParts = list.get(j);
                ResReportInfo resReportInfo = new ResReportInfo();
                resReportInfo.setId(UUIDUtils.generateUUID());
                //所属中心
                resReportInfo.setOwnCenter(ownCenter);
                //申请ID
                resReportInfo.setResAddId(id);

                //物理机配件
                isExecution(id, ownCenter, resExpandItemParts, resReportInfo);
            }
        }
    }

    /**
     * 物理机配件
     * @param id
     * @param ownCenter
     * @param resExpandItemParts
     * @param resReportInfo
     */
    private void isExecution(String id, String ownCenter, ResExpandItemParts resExpandItemParts,
                             ResReportInfo resReportInfo) {
        if ("2".equals(resExpandItemParts.getCategory())) {
            //资源类型中文
            //根据ID获取资源类型
            cateGory(resExpandItemParts, resReportInfo);

        } else if ("4".equals(resExpandItemParts.getCategory())) { //虚拟机配件
            //资源类型中文
            //根据ID获取资源类型
            eosDictEntry(resExpandItemParts, resReportInfo);

        } else if ("5".equals(resExpandItemParts.getCategory())) {//存储套餐
            resType(id, ownCenter, resExpandItemParts, resReportInfo);
        } else if ("6".equals(resExpandItemParts.getCategory())) {//仅统计数据库容量
            isDbSize(id, ownCenter, resExpandItemParts);
        } else if ("7".equals(resExpandItemParts.getCategory()) ||
                "8".equals(resExpandItemParts.getCategory()) ||
                "9".equals(resExpandItemParts.getCategory()) ||
                "10".equals(resExpandItemParts.getCategory())) {//操作系统软件
            if ("7".equals(resExpandItemParts.getCategory())) {
                resReportInfo.setResType("软件：OS");
                //排序号
                resReportInfo.setPx(10);
            } else if ("8".equals(resExpandItemParts.getCategory())) {
                resReportInfo.setResType("软件：DB");
                //排序号
                resReportInfo.setPx(11);
            } else if ("9".equals(resExpandItemParts.getCategory())) {
                resReportInfo.setResType("软件：应用中间件");
                //排序号
                resReportInfo.setPx(12);
            } else if ("10".equals(resExpandItemParts.getCategory())) {
                resReportInfo.setResType("软件：消息中间件");
                //排序号
                resReportInfo.setPx(13);
            }
            //配置,存计费模式
            EosDictEntry eosDictEntry = new EosDictEntry();
            eosDictEntry.setDictid(resExpandItemParts.getBillingMode());
            eosDictEntry.setDicttypeid("RES_BILLINGMODE");
            eosDictEntry = myBatisDao.selectClass(EOSDICTENTRY, eosDictEntry, "queryDetail");
            resReportInfo.setDescribe(eosDictEntry.getDictname());
            //数量
            resReportInfo.setNum(resExpandItemParts.getNum());
            //单价
            resReportInfo.setPrice(Float.parseFloat(resExpandItemParts.getPrice()));
            //总价
            resReportInfo.setTotalPrice(resExpandItemParts.getTotalPrice());
            myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER,
                    ResConstant.INSERTRESREPORTINFO);
        }
    }

    /**
     * 数据库容量
     * @param id
     * @param ownCenter
     * @param resExpandItemParts
     */
    private void isDbSize(String id, String ownCenter, ResExpandItemParts resExpandItemParts){
        if ("61".equals(resExpandItemParts.getSubCategory())) { //oracle ->映射DB云
            //设置数据库容量数据
            ResReportInfo resReportInfoTwo = new ResReportInfo();
            resReportInfoTwo.setId(UUIDUtils.generateUUID());
            //所属中心
            resReportInfoTwo.setOwnCenter(ownCenter);
            //申请ID
            resReportInfoTwo.setResAddId(id);
            resReportInfoTwo.setResType("数据库容量");
            //配置
            resReportInfoTwo.setDescribe("数据库容量");
            //数量
            resReportInfoTwo.setNum(resExpandItemParts.getNum());
            //单价
            resReportInfoTwo.setPrice(Float.parseFloat(resExpandItemParts.getMemPrice()));
            //总价
            resReportInfoTwo.setTotalPrice(resExpandItemParts.getMemTotalPrice());
            //排序号
            resReportInfoTwo.setPx(8);
            myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfoTwo, IMyBatisDao.SQL.INSTER,
                    ResConstant.INSERTRESREPORTINFO);
        }
    }

    /**
     * 存储套餐
     * @param id
     * @param ownCenter
     * @param resExpandItemParts
     * @param resReportInfo
     */
    private void resType(String id, String ownCenter, ResExpandItemParts resExpandItemParts,
                         ResReportInfo resReportInfo) {
        if ("51".equals(resExpandItemParts.getSubCategory())) {
            //如果是非存储，则拆分两个数据 1个SAN数据 1个归档数据
            if (!"1".equals(resExpandItemParts.getIsSpecial())) {
                resReportInfo(id, ownCenter, resExpandItemParts, resReportInfo);
            } else { //归档，直接处理
                resReportInfo.setResType("SAN存储-归档");
                //配置
                resReportInfo.setDescribe("归档数据," + resExpandItemParts.getDescribe());
                //容量
                resReportInfo.setNum(resExpandItemParts.getNum());
                //单价
                resReportInfo.setPrice(Float.parseFloat(resExpandItemParts.getPrice()));
                //总价
                resReportInfo.setTotalPrice(resExpandItemParts.getTotalPrice());
                //排序号
                resReportInfo.setPx(8);
                myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER, "insertResReportInfo");
            }
        } else if ("52".equals(resExpandItemParts.getSubCategory())) {
            resReportInfo.setResType("NAS存储");
            //配置
            resReportInfo.setDescribe(resExpandItemParts.getDescribe());
            //数量
            resReportInfo.setNum(resExpandItemParts.getNum());
            //单价
            resReportInfo.setPrice(Float.parseFloat(resExpandItemParts.getPrice()));
            //总价
            resReportInfo.setTotalPrice(resExpandItemParts.getTotalPrice());
            //排序号
            resReportInfo.setPx(9);
            myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER,
                    ResConstant.INSERTRESREPORTINFO);
        }
    }

    /**
     * 设置扩容数据
     * @param id
     * @param ownCenter
     * @param resExpandItemParts
     * @param resReportInfo
     */
    private void resReportInfo(String id, String ownCenter, ResExpandItemParts resExpandItemParts,
                               ResReportInfo resReportInfo) {
        //增加
        resReportInfo.setResType("SAN存储-非归档");
        //配置
        resReportInfo.setDescribe(resExpandItemParts.getDescribe());
        //容量
        resReportInfo.setNum(resExpandItemParts.getNum());
        //单价
        resReportInfo.setPrice(Float.parseFloat(resExpandItemParts.getPrice()));
        //总价，只计算非归档的 用总的价格-归档的价格
        resReportInfo.setTotalPrice(resExpandItemParts.getTotalPrice() - resExpandItemParts.getSanRelateTotalPrice());
        //排序号
        resReportInfo.setPx(8);
        myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER,
                ResConstant.INSERTRESREPORTINFO);
        //增加归档的数据
        ResReportInfo resReportInfoTwo = new ResReportInfo();
        resReportInfoTwo.setId(UUIDUtils.generateUUID());
        //所属中心
        resReportInfoTwo.setOwnCenter(ownCenter);
        //申请ID
        resReportInfoTwo.setResAddId(id);
        resReportInfoTwo.setResType("SAN存储-归档T");
        resReportInfoTwo.setDescribe("归档数据,单中心无数据保护");
        resReportInfoTwo.setNum(resExpandItemParts.getSanRelateNum());
        resReportInfoTwo.setPrice(Float.parseFloat(resExpandItemParts.getSanRelatePrice()));
        resReportInfoTwo.setTotalPrice(resExpandItemParts.getSanRelateTotalPrice());
        //排序号
        resReportInfoTwo.setPx(8);
        myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfoTwo, IMyBatisDao.SQL.INSTER,
                ResConstant.INSERTRESREPORTINFO);
        //排序号
        resReportInfo.setPx(8);
    }

    /**
     * 获取字典项数据
     * @param resExpandItemParts
     * @param resReportInfo
     */
    private void eosDictEntry(ResExpandItemParts resExpandItemParts, ResReportInfo resReportInfo) {
        resReportInfo.setResType("虚拟机资源");
        //配置
        resReportInfo.setDescribe(resExpandItemParts.getDescribe());
        //数量
        resReportInfo.setNum(resExpandItemParts.getNum());
        //单价
        resReportInfo.setPrice(Float.parseFloat(resExpandItemParts.getPrice()));
        //总价
        resReportInfo.setTotalPrice(resExpandItemParts.getTotalPrice());
        //获取资源信息，用于保存CPU，MEM，DISK
        String type = resExpandItemParts.getSubCategory();
        if ("1".equals(type)) { //CPU
            resReportInfo.setCpu(Integer.parseInt(resExpandItemParts.getNum()));
            resReportInfo.setMemory(0);
            resReportInfo.setDisk(0);
        } else if ("2".equals(type)) {//内存
            resReportInfo.setCpu(0);
            resReportInfo.setMemory(Integer.parseInt(resExpandItemParts.getNum()));
            resReportInfo.setDisk(0);
        } else if ("3".equals(type)) {//硬盘
            resReportInfo.setCpu(0);
            resReportInfo.setMemory(0);
            resReportInfo.setDisk(Float.parseFloat(resExpandItemParts.getNum()));
        }
        //排序号
        resReportInfo.setPx(3);
        myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER,
                ResConstant.INSERTRESREPORTINFO);
    }

    /**
     * 根据ID获取资源类型
     *
     * @param resExpandItemParts
     * @param resReportInfo
     */
    private void cateGory(ResExpandItemParts resExpandItemParts, ResReportInfo resReportInfo) {
        EosDictEntry eosDictEntry = new EosDictEntry();
        eosDictEntry.setDictid(resExpandItemParts.getCategory());
        eosDictEntry.setDicttypeid("RES_CATEGORY");
        eosDictEntry = myBatisDao.selectClass(EOSDICTENTRY, eosDictEntry, ResConstant.QUERYDETAIL);
        resReportInfo.setResType(eosDictEntry.getDictname());
        //配置
        resReportInfo.setDescribe(resExpandItemParts.getDescribe());
        //数量
        resReportInfo.setNum(resExpandItemParts.getNum());
        //单价
        resReportInfo.setPrice(Float.parseFloat(resExpandItemParts.getPrice()));
        //总价
        resReportInfo.setTotalPrice(resExpandItemParts.getTotalPrice());
        //排序号
        resReportInfo.setPx(2);
        myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER,
                ResConstant.INSERTRESREPORTINFO);
    }

    /**
     * 根据资源配置项ID，查询资源配置项配置数据
     *
     * @param expandItemId
     * @return List
     */
    public List<ResExpandItemParts> queryResourceExpandItemParts(String expandItemId) {
        return myBatisDao.selectListT(RES_EXPAND_ITEM_PARTS, expandItemId, "queryResourceExpandItemParts");
    }

    /**
     * 根据资源申请ID，查询资源配置项数据
     *
     * @param expandId
     * @return List
     */
    public List<ResExpandItem> queryResourceExpandItem(String expandId) {
        return myBatisDao.selectListT(RES_EXPAND_ITEM, expandId, "queryResourceExpandItem");
    }

    /**
     * 状态设置为3审核中
     * @param flowDto
     * @param xid
     * @param bpsWorkFlowDto
     * @param map
     */
    private void isBpsWork(FlowDto flowDto, String xid, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map) {
        isSource(flowDto, map);
        bpsWorkFlowDto.setRelaData(map);
        BpsUtil.finshWorkItem(bpsWorkFlowDto, xid);

        //如果是系统部确认，并且通过，则状态设置为3审核中
        Map<String, String> mapRes = new HashMap<>();
        mapRes.put("id", flowDto.getId());
        isDetemine(flowDto, bpsWorkFlowDto, mapRes);
        myBatisDao.executionPojo(RESADDED, mapRes, IMyBatisDao.SQL.INSTER, "updateWorkFlowRelateInfo");
    }

    /**
     * 流程状态区分
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param mapRes
     */
    private void isDetemine(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> mapRes) {
        if (ResConstant.MANUALACTIVITY1.equals(flowDto.getActivityDefID()) && "01".equals(flowDto.getConclusion())) {
            mapRes.put(ResConstant.STATUS, "3");
            //保存系统部人员，最后系统部审批节点，默认设置给该人员
            mapRes.put("sysDealUser", bpsWorkFlowDto.getUserId());
        } else if ("02".equals(flowDto.getConclusion())) { //退回，则状态设置成5拒绝
            mapRes.put(ResConstant.STATUS, "5");
        } else if (ResConstant.MANUALACTIVITY.equals(flowDto.getActivityDefID())) { //重提提交节点，则设置状态为2待审核
            mapRes.put(ResConstant.STATUS, "2");
        } else if (ResConstant.MANUALACTIVITY4.equals(flowDto.getActivityDefID())
                && "01".equals(flowDto.getConclusion())) { //系统部审批
            //结束
            mapRes.put(ResConstant.STATUS, "4");
        } else if (ResConstant.MANUALACTIVITY3.equals(flowDto.getActivityDefID())
                && "01".equals(flowDto.getConclusion())) { //如果是研发管理部确认，则设置预算编号
            mapRes.put("budgetNo", flowDto.getBudgetNo());
            mapRes.put(ResConstant.STATUS, "3");
        } else if ("manualActivity2".equals(flowDto.getActivityDefID()) &&
                "01".equals(flowDto.getConclusion())) {//架构部评审
            mapRes.put("evaluateNo", flowDto.getEvaluateNo());
            mapRes.put("evaluateUrl", flowDto.getEvaluateUrl());
            mapRes.put(ResConstant.STATUS, "3");
        } else if ("manualActivity5".equals(flowDto.getActivityDefID()) &&
                "01".equals(flowDto.getConclusion())) {//c测试部
            mapRes.put(ResConstant.STATUS, "3");
        }
    }

    /**
     * 设置审批人
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param map
     * @param auditUserId
     * @return xid
     */
    private String getXid(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map, String auditUserId) {
        String xid;
        xid = BpsUtil.transactionBegin(bpsWorkFlowDto.getUserId());
        //设置审批人，非最后一个节点,并且不是驳回
        if (!ResConstant.MANUALACTIVITY4.equals(flowDto.getActivityDefID()) && "01".equals(flowDto.getConclusion())) {
            map.put("users", auditUserId);
        }
        return xid;
    }
    /**
     * 节点区分
     * @param flowDto
     * @param map
     */
    private void isSource(FlowDto flowDto, Map<String, String> map) {
        if (ResConstant.MANUALACTIVITY1.equals(flowDto.getActivityDefID())) {
            //审批通过，并且是资源新增，则设置走测试部
            isSourceType(flowDto, map);
        } else if (ResConstant.MANUALACTIVITY2.equals(flowDto.getActivityDefID())){
            // 是否走综合部
            String orgFlag =isManualActivity3(flowDto);
            if ("01".equals(flowDto.getConclusion()) && "1".equals(orgFlag)) {
                map.put("orgFlag", orgFlag);
            } else if ("02".equals(flowDto.getConclusion())) { //退回
                map.put(ResConstant.STATUS, "09");
            } else {
                map.put(ResConstant.STATUS, "-1");
            }
        } else { //其他节点正常处理
            if ("02".equals(flowDto.getConclusion())) { //退回
                map.put(ResConstant.STATUS, "09");
            } else {
                map.put(ResConstant.STATUS, "-1");
            }
        }
    }

    /**
     * 审批通过，并且是资源新增，则设置走测试部
     * @param flowDto
     * @param map
     */
    private void isSourceType(FlowDto flowDto, Map<String, String> map) {
        if ("01".equals(flowDto.getConclusion())) {
            map.put(ResConstant.STATUS, "01");
        } else if ("02".equals(flowDto.getConclusion())) { //退回
            map.put(ResConstant.STATUS, "09");
        } else {
            map.put(ResConstant.STATUS, "-1");
        }
    }

    /**
     * 根据节点，获取审批人员列表
     *
     * @param nodeId
     * @return 审批人
     */
    private String getSprInfo(String nodeId) {
        //获取系统部确认审批人审批人
        List<ResFlowPeople> resFlowPeopleList = myBatisDao.selectListT(
                RES_FLOW_PEOPLE, nodeId, "queryFlowPeopleByNodeId");
        if (resFlowPeopleList.isEmpty()) {
            return "";
        }
        StringBuilder spr = new StringBuilder("");
        for (int i = 0; i < resFlowPeopleList.size(); i++) {
            spr.append(resFlowPeopleList.get(i).getUserId());
            if (i < resFlowPeopleList.size() - 1) {
                spr.append(",");
            }
        }
        return spr.toString();
    }

    /**
     * 是否走综合部
     * @param flowDto
     * @param nodeType
     * @return nodeType
     */
    private String isNdetype(FlowDto flowDto, String nodeType) {
        String orgFlag =isManualActivity3(flowDto);
        if ("01".equals(flowDto.getConclusion()) && "1".equals(orgFlag)) {
            //系统部
            nodeType = ResConstant.MANUALACTIVITY1;
        } else {
            //综合部
            nodeType = ResConstant.MANUALACTIVITY3;
        }
        return nodeType;
    }

    /**
     * 是否走综合部
     * @param flowDto
     * @return orgFlag
     */
    private String isManualActivity3(FlowDto flowDto){
        //获取申请单信息
        ResAdded resAdd = myBatisDao.selectClass(RESADDED, flowDto.getId(), ResAddedConstant.QUERYRESINFO);
        List<EosDictEntry> entryList = eosDictEntryService.getTypeidForList("RES_SUBSIDIARY");
        // 是否走综合部
        // 获取申请人工号
        String userid =resAdd.getCreator();
        String compid = myBatisDao.selectClass(RESADDED, userid, "getCompid");

        String orgFlag ="";
        if(entryList.size()>0){
            for (int i=0; i<entryList.size(); i++){
                if(compid.equals(entryList.get(i).getDictid())){
                    orgFlag ="1";
                    break;
                }
            }
        }
        return orgFlag;
    }

    /**
     * 重新提交发起工作项
     *
     * @param flowDto
     * @param resExpandDto
     * @return boolean
     */
    public boolean reSubmitWorkFlow(FlowDto flowDto, ResExpandDto resExpandDto) {
        //保存节点信息
        Boolean flag = this.insertResourceExpand(resExpandDto);
        if (flag) {
            return this.submitWorkItem(flowDto);
        } else {
            return false;
        }
    }

    /**
     * 资源申请新增
     *
     * @param resExpandDto
     * @return boolean
     */
    public boolean insertResourceExpand(ResExpandDto resExpandDto) {
        //判断ID是否存在，存在的更新，否则则新增

        //y映射DTO到ResAdded实体
        ResAdded resAdded = new ResAdded();
        this.geterateToResAdd(resAdded, resExpandDto);
        //新增
        if (StringUtils.isEmpty(resExpandDto.getId())) {
            //1插入资源申请的数据
            //主键
            String id = UUIDUtils.generateUUID();
            resAdded.setId(id);
            //价格申请单号,如果是保存，则生成序列号，如果是提交申请，则已经生成序列号，不需要重新生成
            if (StringUtils.isEmpty(resAdded.getPriceReqNo())) {
                String priceReqNo = CodeGenerater.generate("RE", 16, "yyyyMMdd");
                resAdded.setPriceReqNo(priceReqNo);
            }
            //创建时间
            resAdded.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            //保存配置项和配置项配置信息
            float allTotalPrice = this.saveItemAndPatrs(resExpandDto, id);
            //设置总价
            resAdded.setAmount(allTotalPrice);
            myBatisDao.executionPojo(RESADDED, resAdded, IMyBatisDao.SQL.INSTER, "addResource");

        } else {//编辑
            //1根据申请主键删除配置项信息
            this.deleteResourceExpandItem(resExpandDto.getId());
            //2根据申请主键删除配置项配置资源信息
            this.deleteResourceExpandParts(resExpandDto.getId());
            //保存配置项和配置项配置信息
            float allTotalPrice = this.saveItemAndPatrs(resExpandDto, resExpandDto.getId());
            //设置总价
            resAdded.setAmount(allTotalPrice);
            //4更新资源申请数据
            myBatisDao.executionPojo(RESADDED, resAdded, IMyBatisDao.SQL.UPDATE, "updateResource");
        }
        return true;
    }

    /**
     * 保存配置项和配置项配置信息
     *
     * @param resExpandDto 数据实体
     * @param id           申请ID
     * @return allTotalPrice
     */
    private float saveItemAndPatrs(ResExpandDto resExpandDto, String id) {
        //3循环资源配置项，
        List<ResExpandItemDto> resExpandItemList = resExpandDto.getResExpandItemList();

        float allTotalPrice = 0;
        //2循环资源配置项，
        for (int i = 0; i < resExpandItemList.size(); i++) {
            //2.1插入配置项信息
            ResExpandItemDto resExpandItemDto = resExpandItemList.get(i);
            ResExpandItem resExpandItem = new ResExpandItem();
            this.geterateToResExpandItem(resExpandItem, resExpandItemDto);
            //设置主键
            String itemId = UUIDUtils.generateUUID();
            resExpandItem.setId(itemId);
            //设置申请外键
            resExpandItem.setExtendId(id);
            //设置创建时间
            resExpandItem.setCreateTime(DateUtil.dateToIsoDateFormat(new Date()));
            float itemTotalPrice = 0;
            //2.2循环配置项配置资源信息，插入数据
            List<ResExpandItemParts> resExpandItemPartsList = resExpandItemDto.getResExpandItemPartsList();
            for (int j = 0; j < resExpandItemPartsList.size(); j++) {
                ResExpandItemParts resExpandItemParts = resExpandItemPartsList.get(j);
                //设置主键
                resExpandItemParts.setId(UUIDUtils.generateUUID());
                //设置申请外键
                resExpandItemParts.setExtendId(id);
                //设置配置项外键
                resExpandItemParts.setExtendItemId(itemId);
                //设置创建时间
                resExpandItemParts.setCreateTime(DateUtil.dateToIsoDateFormat(new Date()));
                resExpandItemParts.setRemark("");
                //获取总价
                getTotalPrice(resExpandItemParts, resExpandItem.getActCpuNum(), resExpandItem.getActCpuCore());
                //配置项价格增加
                itemTotalPrice = itemTotalPrice + resExpandItemParts.getTotalPrice();
                myBatisDao.executionPojo(RES_EXPAND_ITEM_PARTS, resExpandItemParts,
                        IMyBatisDao.SQL.INSTER, "insertResourceExpandItemParts");
            }
            //设置配置项总价
            resExpandItem.setPrice(itemTotalPrice);
            //设置申请总价
            allTotalPrice = allTotalPrice + itemTotalPrice;
            //配置表添加
            myBatisDao.executionPojo(RES_EXPAND_ITEM, resExpandItem,
                    IMyBatisDao.SQL.INSTER, "insertResourceExpandItem");
        }
        return allTotalPrice;
    }

    /**
     * 根据单价和数量，获取单项总价，单价从数据库获取一遍，在计算总价
     *
     * @param resExpandItemParts
     * @param cpuNum             CPU数量
     * @param cpuCore            CPU核数
     */
    private void getTotalPrice(ResExpandItemParts resExpandItemParts, float cpuNum, float cpuCore) {
        float num = Float.parseFloat(resExpandItemParts.getNum());
        //根据配置项ID，获取最新的价格
        float price = 0;
        Map<String, Object> map = myBatisDao.selectClass(RES_EXPAND_ITEM,
                resExpandItemParts.getResConfigId(), "queryConfigItemPrice");
        if (null == map || map.isEmpty()) { //查不到，使用对象里面单价
            price = Float.parseFloat(resExpandItemParts.getPrice());
        } else {
            price = Float.parseFloat(map.get(PRICE).toString());
            //更新单价为最新的
            resExpandItemParts.setPrice(map.get(PRICE).toString());
        }
        float totalPrice = 0;
        //SAN处理
        totalPrice = isTotalPrice(resExpandItemParts, cpuNum, cpuCore, num, price, totalPrice);
        resExpandItemParts.setTotalPrice(totalPrice);
    }

    /**
     * 计算总价
     * @param resExpandItemParts
     * @param cpuNum
     * @param cpuCore
     * @param num
     * @param price
     * @param totalPrice
     * @return totalPrice
     * bak :
     * //2021029 k4886 根据业务需求资源扩容Paas套餐不计算租户的价格
     * //else if ("6".equals(resExpandItemParts.getCategory()) &&
     * ("62".equals(resExpandItemParts.getSubCategory()) ||
     * "63".equals(resExpandItemParts.getSubCategory()))) {////WEB云 容器云
     * //    totalPrice = num * price;querySanInfo
     * //}
     */
    private float isTotalPrice(ResExpandItemParts resExpandItemParts, float cpuNum, float cpuCore, float num,
                               float price, float totalPrice) {
        if ("5".equals(resExpandItemParts.getCategory()) && "51".equals(resExpandItemParts.getSubCategory())
                && !"1".equals(resExpandItemParts.getIsSpecial())) { //存储套餐-SAN存储-非归档
            //获取归档资源信息
            totalPrice = getTotalPriceOne(resExpandItemParts, num, price);
        } else if ("5".equals(resExpandItemParts.getCategory()) && "51".equals(resExpandItemParts.getSubCategory())
                && "1".equals(resExpandItemParts.getIsSpecial())) {//存储套餐-SAN存储-归档
            totalPrice = getTotalPrice(num, price);
        } else if ("5".equals(resExpandItemParts.getCategory()) && "52".equals(resExpandItemParts.getSubCategory())) {
            //存储套餐-NAS存储
            totalPrice = getTotalPrice(num, price);
        } else if ("2".equals(resExpandItemParts.getCategory()) || "4".equals(resExpandItemParts.getCategory())) {
            //物理机配件,虚拟机报价
            totalPrice = price * num;
        } else if ("7".equals(resExpandItemParts.getCategory()) || "8".equals(resExpandItemParts.getCategory())
                || "9".equals(resExpandItemParts.getCategory()) || "10".equals(resExpandItemParts.getCategory())) {
            // 获取软件价格
            totalPrice = getPrice(resExpandItemParts, cpuNum, cpuCore, num, price);
        } else if ("6".equals(resExpandItemParts.getCategory()) && "61".equals(resExpandItemParts.getSubCategory())) {
            //获取DB云的容量单价
            //DB云 租户价格+容量/1000*容量单价
            totalPrice = getTotalPrices(resExpandItemParts, num);
        }
        return totalPrice;
    }

    /**
     * 总价
     * @param num
     * @param price
     * @return totalPrice
     */
    private float getTotalPrice(float num, float price) {
        return gBprice(num,price);
    }

    /**
     * 获取归档资源信息
     * @param resExpandItemParts
     * @param num
     * @param price
     * @return totalPrice
     */
    private float getTotalPriceOne(ResExpandItemParts resExpandItemParts, float num, float price) {
        float totalPrice;
        List<ResConfigCombo> list = myBatisDao.selectListT(RES_CONFIG_COMBO, "", "querySanComboViewInfo");
        //主项总价
        float subPrice = gBprice(num,price);
        float gdPrice = 0;
        //设置归档数据ID，容量，单价,比例
        if (!list.isEmpty()) {
            gdPrice = getGdPrice(resExpandItemParts, list);
        }
        totalPrice = subPrice + gdPrice;
        //保留一位小数
        BigDecimal tmp = BigDecimal.valueOf(totalPrice);
        totalPrice = tmp.setScale(1, BigDecimal.ROUND_UP).floatValue();
        //设置配套归档的总价
        resExpandItemParts.setSanRelateTotalPrice(gdPrice);
        return totalPrice;
    }

    /**
     * 设置归档数据ID，容量，单价,比例
     * @param resExpandItemParts
     * @param list
     * @return gdPrice
     */
    private float getGdPrice(ResExpandItemParts resExpandItemParts, List<ResConfigCombo> list) {
        float gdPrice;
        float sanRelateNum = Float.parseFloat(resExpandItemParts.getSanRelateNum());
        gdPrice = gBprice(sanRelateNum,Float.parseFloat(list.get(0).getPrice()));
        //保留一位小数
        BigDecimal tmp = BigDecimal.valueOf(gdPrice);
        gdPrice = tmp.setScale(1, BigDecimal.ROUND_UP).floatValue();
        return gdPrice;
    }
    /**
     * 获取DB云的容量单价
     * @param resExpandItemParts
     * @param num
     * @return totalPrice
     */
    private float getTotalPrices(ResExpandItemParts resExpandItemParts, float num) {
        float totalPrice;
        float memPrice /*= 0L*/;
        Map<String, Object> mapMem = myBatisDao.selectClass(
                RES_EXPAND_ITEM, resExpandItemParts.getResConfigId(), "queryDbMemPrice");
        if (null == mapMem || mapMem.isEmpty()) { //查不到，使用对象里面单价
            memPrice = Float.parseFloat(resExpandItemParts.getMemPrice());
        } else {
            memPrice = Float.parseFloat(mapMem.get("DISKCAPACITY").toString());
        }
        float memTotal = gBprice(num,memPrice);
        //保留一位小数
        totalPrice = memTotal;
        //设置保存DB云存储总价，后面报表使用
        resExpandItemParts.setMemTotalPrice(memTotal);
        return totalPrice;
    }

    /**
     * 根据容量（M）和单价（元/G）计算总价
     * @param num
     * @param price
     * @return float
     */
    private float gBprice(float num,float price){
        BigDecimal size = BigDecimal.valueOf(num);
        BigDecimal simplePrice = BigDecimal.valueOf(price);
        BigDecimal gb = BigDecimal.valueOf(1000);
        return size.divide(gb).multiply(simplePrice).setScale(1,BigDecimal.ROUND_UP).floatValue();
    }

    /**
     * 计算总价
     * @param resExpandItemParts
     * @param cpuNum
     * @param cpuCore
     * @param num
     * @param price
     * @return totalPrice
     */
    private float getPrice(ResExpandItemParts resExpandItemParts, float cpuNum, float cpuCore, float num, float price) {
        float totalPrice;
        if ("1".equals(resExpandItemParts.getBillingMode())) {//cpu数量
            totalPrice = num * price * cpuNum;
        } else if ("2".equals(resExpandItemParts.getBillingMode())) {//cpu核数
            totalPrice = num * price * cpuCore;
        } else {//整机
            totalPrice = num * price;
        }
        return totalPrice;
    }

    /**
     * 资源扩充新增时，转换对象
     *
     * @param resAdded
     * @param resExpandDto
     */
    private void geterateToResAdd(ResAdded resAdded, ResExpandDto resExpandDto) {
        resAdded.setId(resExpandDto.getId());
        resAdded.setPriceReqNo(resExpandDto.getPriceReqNo());
        resAdded.setAppNo(resExpandDto.getAppNo());
        resAdded.setAppName(resExpandDto.getAppName());
        resAdded.setProjectNo(resExpandDto.getProjectNo());
        resAdded.setProjectName(resExpandDto.getProjectName());
        resAdded.setReNewProcessNo(resExpandDto.getReNewProcessNo());
        resAdded.setExpectedOnlineTime(resExpandDto.getExpectedOnlineTime());
        if (StringUtil.isNotBlank(resExpandDto.getAmount())) {
            String amoount = resExpandDto.getAmount();
            resAdded.setAmount(Float.parseFloat(amoount));
        } else {
            resAdded.setAmount(0);
        }
        resAdded.setStatus(resExpandDto.getStatus());
        resAdded.setSourceType("1");
        resAdded.setBusiDeptName(resExpandDto.getBusiDeptName());
        resAdded.setTechDeptName(resExpandDto.getTechDeptName());
        resAdded.setSysOperLevel(resExpandDto.getSysOperLevel());
        resAdded.setIsFundTrans(resExpandDto.getIsFundTrans());
        resAdded.setCreator(resExpandDto.getCreator());
        resAdded.setCreatorName(resExpandDto.getCreatorName());
        resAdded.setCiid(resExpandDto.getCiid());
        resAdded.setProcessInstID(resExpandDto.getProcessInstID());
        resAdded.setBusiDeptId(resExpandDto.getBusiDeptId());
        resAdded.setTechDeptId(resExpandDto.getTechDeptId());
        resAdded.setDocmId(resExpandDto.getDocmId());
    }

    /**
     * 根据资源申请ID，删除资源配置项数据
     *
     * @param expandId
     */
    public void deleteResourceExpandItem(String expandId) {
        myBatisDao.executionPojo(RES_EXPAND_ITEM, expandId, IMyBatisDao.SQL.DELETE, "deleteResourceExpandItem");
    }

    /**
     * 根据资源申请ID，删除资源配置项配置数据
     *
     * @param expandId
     */
    public void deleteResourceExpandParts(String expandId) {
        myBatisDao.executionPojo(RES_EXPAND_ITEM_PARTS, expandId, IMyBatisDao.SQL.DELETE, "deleteResourceExpandParts");
    }

    /**
     * 资源扩充配置项时，转换对象
     *
     * @param resExpandItem
     * @param resExpandItemDto
     */
    private void geterateToResExpandItem(ResExpandItem resExpandItem, ResExpandItemDto resExpandItemDto) {
        resExpandItem.setIp(resExpandItemDto.getIp());
        resExpandItem.setOwnCenter(resExpandItemDto.getOwnCenter());
        resExpandItem.setResType(resExpandItemDto.getResType());
        if (StringUtil.isNotBlank(resExpandItemDto.getPrice())) {
            resExpandItem.setPrice(Float.parseFloat(resExpandItemDto.getPrice()));
        } else {
            resExpandItem.setPrice(0);
        }
        resExpandItem.setCiId(resExpandItemDto.getCiId());
        resExpandItem.setCiName(resExpandItemDto.getCiName());
        resExpandItem.setCpuNum(resExpandItemDto.getCpuNum());
        resExpandItem.setCpuCore(resExpandItemDto.getCpuCore());
        resExpandItem.setActCpuNum(resExpandItemDto.getActCpuNum());
        resExpandItem.setActCpuCore(resExpandItemDto.getActCpuCore());
    }
}
