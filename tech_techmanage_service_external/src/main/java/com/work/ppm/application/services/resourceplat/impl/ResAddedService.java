package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemPartsDto;
import com.work.ppm.application.dto.resourceplat.ResAddedItemVersionDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.application.model.resourceplat.ResAddedItemParts;
import com.work.ppm.application.model.resourceplat.ResAddedItemVersion;
import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResFlowPeople;
import com.work.ppm.application.services.resourceplat.IResAddedService;
import com.work.ppm.application.services.resourceplat.IResExpandFlowService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.resourceplat.ResAddedConstant;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * service
 * @date 2021/09/03
 */
@Slf4j
@Service("ResAddedService")
public class ResAddedService extends BaseDaoSupper implements IResAddedService {
    /**
     * RESADDED
     */
    private static String RESADDED = "com.work.ppm.application.model.resourceplat.ResAdded";
    /**
     * ResAddedExt实体
     */
    private static String RESADDEDEXT = "com.work.ppm.application.model.resourceplat.ResAddedExt";
    /**
     * RES_FLOW_PEOPLE
     */
    private static String RES_FLOW_PEOPLE = "com.work.ppm.application.model.resourceplat.ResFlowPeople";
    /**
     * RES_EXPAND_ITEM
     */
    private static String RES_EXPAND_ITEM = "com.work.ppm.application.model.resourceplat.ResExpandItem";
    /**
     * RES_CONFIG_COMBO
     */
    private static String RES_CONFIG_COMBO = "com.work.ppm.application.model.resourceplat.ResConfigCombo";

    /**
     * myBatisDao
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * resExpandService
     */
    @Autowired
    private ResExpandService resExpandService;
    /**
     * 资源扩容
     */
    @Autowired
    private IResExpandFlowService resExpandFlowService;
    /**
     * ResAddedReportService
     */
    @Autowired
    private ResAddedReportService resAddedReportService;
    /**
     * ResAddedImportResReportService
     */
    @Autowired
    private ResAddedImportResReportService resAddedImportResReportService;

    /**
     * 分页查询资源新增列表
     * @param resAddedDto 对象
     * @param pager       分页对象
     */
    @Override
    public void queryPager(ResAddedDto resAddedDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(resAddedDto, ResAddedDto.class);
        myBatisDao.selectPager(RESADDED, map, pager, "queryResAddList");
    }

    /**
     * 删除资源新增主表
     * @param resAddedDto
     */
    @Override
    public void deleteResourceAdded(ResAddedDto resAddedDto) {
        String iaasFlag=resAddedDto.getIaasFlag();
        if("1".equals(iaasFlag)){
            String resType =resAddedDto.getSourceType();
            if("0".equals(resType)){
                delIaasData(resAddedDto.getId());
            }
        }
        this.delConfigBeforeEdit(resAddedDto.getId());
    }

    /**
     * 查询关联系统树
     * @return newTreeList 关联系统树据列表
     */
    @Override
    public List<CommTreeDto> queryRelaSysTree() {
        List<CommTree> list = myBatisDao.selectListT(RESADDED, null, "queryRelaSysTree");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        return CommTreeDto.recursiveListToTree(dtoList);
    }

    /**
     * 根据项目code查询对应的系统详细信息
     * @param pcode 项目code
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> queryRelaSysData(String pcode) {
        return myBatisDao.selectListT(RESADDED, pcode, "queryRelaSysData");
    }

    /**
     * 查询网络区域列表
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> queryIpApplyNetAddress() {
        return myBatisDao.selectListT(RESADDED, null, "queryIpApplyNetAddress");
    }

    /**
     * 查询关联项目树
     * @return newTreeList 关联项目树数据列表
     */
    @Override
    public List<CommTreeDto> queryRelaProgramTree() {
        List<CommTree> list = myBatisDao.selectListT(RESADDED, null, "queryRelaProgramTree");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        return CommTreeDto.recursiveListToTree(dtoList);
    }

    /**
     * 查询预先设置的14种配置信息
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> querySelectionConfig() {
        return myBatisDao.selectListT(RESADDED, null, "querySelectionConfig");
    }

    /**
     * 查询所有的配件信息
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> queryHardWareRela() {
        return myBatisDao.selectListT(RESADDED, null, "queryHardWareRela");
    }

    /**
     * 查询所有的软件信息
     * @return List<Map<String, Object>>
     */
    @Override
    public List<Map<String, Object>> querySoftWareRela() {
        return myBatisDao.selectListT(RESADDED, null, "querySoftWareRela");
    }

    /**
     * 根据套餐类型和节点类型查询套餐数据
     * @param comboType 套餐类型
     * @param nodeType  节点类型
     * @return result 套餐列表数据
     */
    @Override
    public ApiResult<List> queryRelaComboList(String comboType, String nodeType) {
        ApiResult<List> result = new ApiResult<>();
        Map<String, Object> param = new HashMap<>();
        /**
         * 1.物理机套餐,2.虚拟机套餐,3.paas服务套餐
         * 4.专用物理机套餐,5.专用虚拟机套餐
         **/
        if (ResAddedConstant.COMBOTYPE_CONFIG_1.equals(comboType)) {//物理机套餐
            param.put(ResAddedConstant.CATEGORY, ResAddedConstant.COMBOTYPE_1);
        } else if (ResAddedConstant.COMBOTYPE_CONFIG_2.equals(comboType)) {//虚拟机套餐
            param.put(ResAddedConstant.CATEGORY, ResAddedConstant.COMBOTYPE_3);
        } else if (ResAddedConstant.COMBOTYPE_CONFIG_3.equals(comboType)) {//paas服务套餐
            param.put(ResAddedConstant.CATEGORY, ResAddedConstant.COMBOTYPE_6);
            if (ResAddedConstant.NODE_2.equals(nodeType)) {//web
                param.put(ResAddedConstant.SUBCATEGORY, ResAddedConstant.SUBCOMBOTYPE_62);//PaaS服务套餐（Nginx）
            } else if (ResAddedConstant.NODE_3.equals(nodeType)) {//db
                param.put(ResAddedConstant.SUBCATEGORY, ResAddedConstant.SUBCOMBOTYPE_61);//PaaS服务套餐（Oracle）
            } else if (ResAddedConstant.NODE_5.equals(nodeType)) {//其他
                param.put(ResAddedConstant.SUBCATEGORY, ResAddedConstant.SUBCOMBOTYPE_63);//PaaS服务套餐（容器云）
            } else {
                result.setStatus(ApiCode.FAIL);
            }
        } else if (ResAddedConstant.COMBOTYPE_CONFIG_4.equals(comboType)) {//专用物理机套餐
            param.put(ResAddedConstant.CATEGORY, ResAddedConstant.COMBOTYPE_1);
            param.put("isSpecial", ResAddedConstant.ISSPECIAL);
        } else if (ResAddedConstant.COMBOTYPE_CONFIG_5.equals(comboType)) {//专用虚拟机套餐
            param.put(ResAddedConstant.CATEGORY, ResAddedConstant.COMBOTYPE_3);
            param.put("isSpecial", ResAddedConstant.ISSPECIAL);
        } else {
            result.setStatus(ApiCode.FAIL);
        }
        List<Map<String, Object>> list = myBatisDao.selectListT(RESADDED, param, "queryRelaComboList");
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 提交资源新增申请流程
     * @param resAddedDto 资源申请数据
     * @return
     */
    @Override
    public String submitResourceAdded(ResAddedDto resAddedDto) {
        String xid = null;
        String processInstID = "";
        String processInfo = "";

        //获取系统部确认审批人审批人
        String spr = this.getSprInfo("manualActivity1");
        if (StringUtils.isEmpty(spr)) {
            return "获取下一个节点处理人失败";
        }
        Map<String, String> map = new HashMap<>();
        map.put("users", spr);
        map.put("status", "0");

        BpsWorkFlowDto bpsWorkFlowDto = new BpsWorkFlowDto();
        try {
            xid = BpsUtil.transactionBegin(resAddedDto.getCreator());
            //发起工作流
            bpsWorkFlowDto.setProcessDefName("com.work.ppm.bps.resourceFlow");
            bpsWorkFlowDto.setProcessInstName(resAddedDto.getAppName() + "资源申请");
            bpsWorkFlowDto.setProcessInstDesc("资源申请");
            bpsWorkFlowDto.setUserId(resAddedDto.getCreator());
            bpsWorkFlowDto.setApplydesc("资源申请");
            bpsWorkFlowDto.setRelaData(map);
            processInstID = BpsUtil.createProcessAndFinishFirst(bpsWorkFlowDto, xid);
            bpsWorkFlowDto.setProcessInstId(processInstID);
            BpsUtil.transactionCommit(resAddedDto.getCreator(), xid);
            BpsUtil.flowLog(bpsWorkFlowDto);
            //设置流程实例ID
            resAddedDto.setProcessInstID(processInstID);
            //状态设置为2待审核
            resAddedDto.setStatus("2");
            ResAdded resAdded = new ResAdded();
            this.geterateToResAdd(resAdded, resAddedDto);
            myBatisDao.executionPojo(RESADDED,resAdded,IMyBatisDao.SQL.UPDATE,"saveSubmit");
            processInfo = BpsUtil.queryProcessInfo(bpsWorkFlowDto);
        } catch (Exception e) {
            BpsUtil.transactionRollback(resAddedDto.getCreator(), xid);
        }
        return processInfo;
    }

    /**
     * 根据节点，获取审批人员列表
     * @param nodeId
     * @return spr
     */
    private String getSprInfo(String nodeId) {
        //获取系统部确认审批人审批人
        List<ResFlowPeople> resFlowPeopleList = myBatisDao.selectListT
                (RES_FLOW_PEOPLE, nodeId, "queryFlowPeopleByNodeId");
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
     * 根据计费模式，单价以及cpu数，cpu核数计算总价
     * @param countModel 计费模式
     * @param price      单价
     * @param cpuNum     cpu数
     * @param cpuCode    cpu核数
     * @return totalCount 总价
     */
    private float calculateByCountModel(String countModel, Float price, int cpuNum, int cpuCode) {
        float totalCount = 0;
        if ("1".equals(countModel)) {
            //按CPU数量
            totalCount = cpuNum * price;
        } else if ("2".equals(countModel)) {
            totalCount = cpuCode * price;
        } else if ("3".equals(countModel)) {
            totalCount = price;
        }
        return totalCount;
    }

    /**
     * 验算价格重新存入dto
     * @param resAddedDto 资源配置主表Dto
     * @return ResAddedDto 资源配置主表Dto
     */
    public ResAddedDto reCountPrice(ResAddedDto resAddedDto) {
        float amount = 0;
        List<ResAddedItemDto> newItemDtoList = new ArrayList<>();
        List<ResAddedItemDto> resAddedItemDtoList = resAddedDto.getDynamicForms();
        for (int i = 0; i < resAddedItemDtoList.size(); i++) {
            ResAddedItemDto resAddedItemDto = resAddedItemDtoList.get(i);
            getEveryCardPrice(resAddedDto.getSysOperLevel(),
                    resAddedDto.getIsFundTrans(), resAddedItemDto);
            amount += Float.parseFloat(resAddedItemDto.getPrice());
            newItemDtoList.add(resAddedItemDto);
        }
        resAddedDto.setDynamicForms(newItemDtoList);
        resAddedDto.setAmount(amount + "");
        return resAddedDto;
    }

    /**
     * 计算每一个配置卡片的总价
     * @param sysOperLevel    系统运维等级
     * @param isFundTrans     是否涉及资金交易
     * @param resAddedItemDto 资源申请子表Dto
     * @return resAddedItemDto 验算过没一个配置卡片价格的dto
     */
    @Override
    public ResAddedItemDto getEveryCardPrice(String sysOperLevel,
                                              String isFundTrans, ResAddedItemDto resAddedItemDto) {
        BigDecimal tb = new BigDecimal(1000);
        // 计算套餐总价
        getComboTotalPrice(resAddedItemDto);
        // 计算数据库容量总价
        getDbTotalPrice(resAddedItemDto);
        // 计算NAS总价
        getNasTotalPrice(tb, resAddedItemDto);
        // 计算SAN总价
        getSanTotalPrice(tb, sysOperLevel, isFundTrans, resAddedItemDto);
        // 计算配件总价
        getHardWareTotalPrice(resAddedItemDto);
        // 计算软件总价
        getSoftWareTotalPrice(resAddedItemDto, resAddedItemDto.getCpuTotalNum(), resAddedItemDto.getCpuTotalCode());
        // 计算操作系统总价
        getOsTotalPrice(resAddedItemDto, resAddedItemDto.getCpuTotalNum(), resAddedItemDto.getCpuTotalCode());
        //计算所有部分的总价
        float allCardprice =
                Float.parseFloat(resAddedItemDto.getComboTotalPrice())
                        + Float.parseFloat(resAddedItemDto.getDbTotalPrice())
                        + Float.parseFloat(resAddedItemDto.getNasTotalPrice())
                        + Float.parseFloat(resAddedItemDto.getSanTotalPrice())
                        + Float.parseFloat(resAddedItemDto.getHardWareTotalPrice())
                        + Float.parseFloat(resAddedItemDto.getSoftWareTotalPrice())
                        + Float.parseFloat(resAddedItemDto.getOsTotalPrice());
        //更新card内的总价
        resAddedItemDto.setPrice(allCardprice + "");
        return resAddedItemDto;
    }

    /**
     * 计算配件总价格
     * @param resAddedItemDto 资源申请子表Dto
     * @return resAddedItemDto 验算过配件价格的dto
     */
    private ResAddedItemDto getHardWareTotalPrice(ResAddedItemDto resAddedItemDto) {//6.配件的价格
        float hardWareTotalPrice = 0;
        int cpuNum = Integer.parseInt(resAddedItemDto.getCpuNum());
        int cpuCode = Integer.parseInt(resAddedItemDto.getCpuCode());
        List<ResAddedItemPartsDto> hardWareDataList = new ArrayList<>();
        List<ResAddedItemPartsDto> resAddedItemPartsDtoList = resAddedItemDto.getHardWareData();
        for (int x = 0; x < resAddedItemPartsDtoList.size(); x++) {
            ResAddedItemPartsDto resAddedItemPartsDto = resAddedItemPartsDtoList.get(x);
            int num = Integer.parseInt(resAddedItemPartsDto.getNum());
            float price = 0;
            Map<String, Object> hardWareData = getConfigItemPrice(resAddedItemPartsDto.getPartId());
            if (hardWareData == null || hardWareData.isEmpty()) {
                //查不到数据，使用对象中的配置
                price = Float.parseFloat(resAddedItemPartsDto.getPrice());
            } else {
                //更新单价
                price = Float.parseFloat(hardWareData.get(ResAddedConstant.PRICE).toString());
                resAddedItemPartsDto.setPrice(hardWareData.get(ResAddedConstant.PRICE).toString());
            }
            float rowTotalPrice = price * num;
            resAddedItemPartsDto.setTotalPrice(rowTotalPrice);
            hardWareTotalPrice += rowTotalPrice;
            hardWareDataList.add(resAddedItemPartsDto);
            //计算虚拟机配件中多出的CPU配件
            if ("2".equals(resAddedItemDto.getModelType()) && "1".equals(resAddedItemPartsDto.getResConfigId())) {
                cpuNum = cpuNum + num;
                cpuCode = cpuCode + num;
            } else {
                log.info("不是虚拟机配件不做统计");
            }
        }
        resAddedItemDto.setCpuTotalNum(cpuNum);
        resAddedItemDto.setCpuTotalCode(cpuCode);
        resAddedItemDto.setHardWareData(hardWareDataList);
        resAddedItemDto.setHardWareTotalPrice(hardWareTotalPrice + "");
        return resAddedItemDto;
    }

    /**
     * 根据ID获取配置价格
     * @param id
     * @return map
     */
    private Map<String, Object> getConfigItemPrice(String id) {
        return myBatisDao.selectClass(RES_EXPAND_ITEM, id, "queryConfigItemPrice_added");
    }

    /**
     * 计算操作系统价格(已经计入虚拟机配件增加的CPU数)
     * @param resAddedItemDto 资源申请子表Dto
     * @param cpuNum          CPU数量
     * @param cpuCode         CPU核心数
     * @return resAddedItemDto 验算过操作系统的Dto
     */
    private ResAddedItemDto getOsTotalPrice(ResAddedItemDto resAddedItemDto, int cpuNum, int cpuCode) {
        float osTotalPrice = 0;
        Map<String, Object> osData = getConfigItemPrice(resAddedItemDto.getOs());
        float osPrice = 0;
        String countModel = resAddedItemDto.getOsCountModel();
        if (osData == null || osData.isEmpty()) {
            //查不到数据，使用对象中的配置
            osPrice = Float.parseFloat(resAddedItemDto.getOsPrice());
        } else {
            //更新操作系统单价
            osPrice = Float.parseFloat(osData.get(ResAddedConstant.PRICE).toString());
            resAddedItemDto.setOsPrice(osData.get(ResAddedConstant.PRICE).toString());
        }
        osTotalPrice = calculateByCountModel(countModel, osPrice, cpuNum, cpuCode);
        resAddedItemDto.setOsTotalPrice(osTotalPrice + "");
        return resAddedItemDto;
    }

    /**
     * 计算 软件的价格(已经计入虚拟机配件增加的CPU数)
     *
     * @param resAddedItemDto 资源申请子表Dto
     * @param cpuNum          CPU数量
     * @param cpuCode         CPU核心数
     * @return resAddedItemDto 验算过软件价格的dto
     */
    private ResAddedItemDto getSoftWareTotalPrice(ResAddedItemDto resAddedItemDto, int cpuNum, int cpuCode) {
        float softWareTotalPrice = 0;
        List<ResAddedItemVersionDto> softWareDataList = new ArrayList<>();
        List<ResAddedItemVersionDto> resAddedItemVersionDtoList = resAddedItemDto.getSoftWareData();
        for (int y = 0; y < resAddedItemVersionDtoList.size(); y++) {
            ResAddedItemVersionDto resAddedItemVersionDto = resAddedItemVersionDtoList.get(y);
            float price = 0;
            String countModel = resAddedItemVersionDto.getCountModel();
            if ("1".equals(resAddedItemVersionDto.getType())) {
                Map<String, Object> softWareData = getConfigItemPrice(resAddedItemVersionDto.getSoftId());
                if (softWareData == null || softWareData.isEmpty()) {
                    //查不到数据，使用对象中的配置
                    price = Float.parseFloat(resAddedItemVersionDto.getPrice());
                } else {
                    //更新单价
                    price = Float.parseFloat(softWareData.get(ResAddedConstant.PRICE).toString());
                    resAddedItemVersionDto.setPrice(softWareData.get(ResAddedConstant.PRICE).toString());
                }
                float rowTotalPrice = calculateByCountModel(countModel, price, cpuNum, cpuCode);
                resAddedItemVersionDto.setTotalPrice(rowTotalPrice);
                softWareTotalPrice += rowTotalPrice;
            } else {
                //自定义软件无需计费、
                softWareTotalPrice += 0;
            }
            softWareDataList.add(resAddedItemVersionDto);
        }
        resAddedItemDto.setSoftWareData(softWareDataList);
        resAddedItemDto.setSoftWareTotalPrice(softWareTotalPrice + "");
        return resAddedItemDto;
    }

    /**
     * 计算SAN总价
     *
     * @param tb              单位转换进制（1000）
     * @param sysOperLevel    系统运维等级
     * @param isFundTrans     是否涉及资金交易
     * @param resAddedItemDto 资源申请子表Dto
     * @return ResAddedItemDto 验算过SAN价格的dto
     */
    private ResAddedItemDto getSanTotalPrice(BigDecimal tb,
                                             String sysOperLevel, String isFundTrans,
                                             ResAddedItemDto resAddedItemDto) {
        float sanTotalPrice = 0;
        if ("1".equals(resAddedItemDto.getIsStorage())) {
            BigDecimal sanSize = new BigDecimal(resAddedItemDto.getStorageSize());
            //5.1 根据系统运维等级和是否涉及资金交易查询的SAN套餐的价格
            Map<String, Object> param = new HashMap<>();
            param.put("sysOperLevel", sysOperLevel);
            param.put("isFundTrans", isFundTrans);
            Map<String, Object> sanComboData = myBatisDao.selectClass(RESADDED, param, "querySanCombo");
            float sanPrice = 0;
            if (sanComboData == null || sanComboData.isEmpty()) {
                //查不到数据，使用对象中的配置
                sanPrice = Float.parseFloat(resAddedItemDto.getSanPrice());
            } else {
                //更新单价
                sanPrice = Float.parseFloat(sanComboData.get(ResAddedConstant.PRICE).toString());
                resAddedItemDto.setSanPrice(sanComboData.get(ResAddedConstant.PRICE).toString());
            }
            int sanSpecialPrice = 0;
            BigDecimal sanSpecialRatio = new BigDecimal(resAddedItemDto.getSanSpecialRatio());
            BigDecimal sanRatio = new BigDecimal(resAddedItemDto.getSanRatio());
            BigDecimal sanSpecialSize2 = sanSize.multiply(sanSpecialRatio).divide(sanRatio, 0,BigDecimal.ROUND_UP);

            //5.2 归档的SAN套餐的价格
            List<ResConfigCombo> list = myBatisDao.selectListT(RES_CONFIG_COMBO, "", "querySanComboViewInfo");
            if (list.isEmpty()) {
                ResConfigCombo resConfigCombo = list.get(0);
                //更新归档SAN单价
                sanSpecialPrice = Integer.valueOf(resConfigCombo.getPrice());
                resAddedItemDto.setSanSpecialPrice(sanSpecialPrice + "");
            } else {
                sanSpecialPrice = Integer.parseInt(resAddedItemDto.getSanSpecialPrice());
            }
            BigDecimal sanPrice2 = BigDecimal.valueOf(sanPrice);
            BigDecimal sanSpecialPrice2 = BigDecimal.valueOf(sanSpecialPrice);
            BigDecimal sanTotalPriceOne = sanSize.divide(tb).multiply(sanPrice2);
            BigDecimal sanTotalPriceTwo = sanSpecialSize2.divide(tb).multiply(sanSpecialPrice2);
            sanTotalPrice = sanTotalPriceOne.add(sanTotalPriceTwo).setScale(1, BigDecimal.ROUND_UP).floatValue();
            resAddedItemDto.setSanTotalPrice(sanTotalPrice + "");
        }
        return resAddedItemDto;
    }

    /**
     * 计算NAS总价
     * @param tb              单位转换进制（1000）
     * @param resAddedItemDto 资源申请子表Dto
     * @return ResAddedItemDto 验算过NAS价格的dto
     */
    private ResAddedItemDto getNasTotalPrice(BigDecimal tb, ResAddedItemDto resAddedItemDto) {
        float nasTotalPrice = 0;
        if ("1".equals(resAddedItemDto.getIsNas())) {
            Map<String, Object> nasData = getConfigItemPrice(resAddedItemDto.getMountType());
            float nasPrice = 0;
            if (nasData == null || nasData.isEmpty()) {
                //查不到数据，使用对象中的配置
                nasPrice = Float.parseFloat(resAddedItemDto.getNasPrice());
            } else {
                //更新NAS单价
                nasPrice = Float.parseFloat(nasData.get(ResAddedConstant.PRICE).toString());
                resAddedItemDto.setNasPrice(nasData.get(ResAddedConstant.PRICE).toString());
            }
            BigDecimal nasSize = new BigDecimal(resAddedItemDto.getNasSize());
            BigDecimal nasPrice2 = BigDecimal.valueOf(nasPrice);
            nasTotalPrice = nasSize.divide(tb).multiply(nasPrice2).setScale(1, BigDecimal.ROUND_UP).floatValue();
            resAddedItemDto.setNasTotalPrice(nasTotalPrice + "");
        }
        return resAddedItemDto;
    }

    /**
     * 计算数据库容量总价
     * @param resAddedItemDto 资源申请子表Dto
     * @return dbTotalPrice 数据库容量总价
     */
    private ResAddedItemDto getDbTotalPrice(ResAddedItemDto resAddedItemDto) {
        float dbTotalPrice = 0;
        if ("3".equals(resAddedItemDto.getNodeType()) && "3".equals(resAddedItemDto.getModelType())) {
            float dbPrice = 0;
            int dbSize = Integer.parseInt(resAddedItemDto.getDbSize());
            Map<String, Object> dbData = myBatisDao.selectClass(RESADDED,
                    resAddedItemDto.getModelConfig(), "querydbPrice");
            if (dbData == null || dbData.isEmpty()) {
                //查不到数据，使用对象中的配置
                dbPrice = Float.parseFloat(resAddedItemDto.getDbPrice());
            } else {
                //更新数据库单价
                dbPrice = Float.parseFloat(dbData.get(ResAddedConstant.DISKCAPACITY).toString());
                resAddedItemDto.setDbPrice(dbData.get(ResAddedConstant.DISKCAPACITY).toString());
            }
            BigDecimal increase = new BigDecimal(1000);
            BigDecimal size = BigDecimal.valueOf(dbSize);
            BigDecimal price = BigDecimal.valueOf(dbPrice);
            dbTotalPrice = size.divide(increase).multiply(price).floatValue();
            resAddedItemDto.setDbTotalPrice(dbTotalPrice + "");
        }
        return resAddedItemDto;
    }

    /**
     * 计算套餐总价
     * @param resAddedItemDto 资源申请子表Dto
     * @return comboTotalPrice 套餐总价
     */
    private ResAddedItemDto getComboTotalPrice(ResAddedItemDto resAddedItemDto) {
        float comboTotalPrice = 0;
        Map<String, Object> comboData = getConfigItemPrice(resAddedItemDto.getModelConfig());
        if (comboData == null || comboData.isEmpty()) {
            //查不到数据，使用对象中的配置
            comboTotalPrice = Float.parseFloat(resAddedItemDto.getComboTotalPrice());
        } else {
            //更新总价
            comboTotalPrice = Float.parseFloat(comboData.get(ResAddedConstant.PRICE).toString());
            resAddedItemDto.setComboTotalPrice(comboData.get(ResAddedConstant.PRICE).toString());
        }
        resAddedItemDto.setComboTotalPrice(comboTotalPrice + "");
        return resAddedItemDto;
    }

    /**
     * 重新提交发起工作项
     * @param flowDto
     * @param resAddedDto
     * @return boolean
     */
    public boolean reSubmitWorkFlow(FlowDto flowDto, ResAddedDto resAddedDto) {
        boolean flag = this.addResource(reCountPrice(resAddedDto));
        if (flag) {
            return resExpandFlowService.submitWorkItem(flowDto);
        } else {
            return false;
        }
    }

    /**
     * 保存资源新增主表数据
     * @param resAddedDto
     * @return
     */
    @Override
    public boolean addResource(ResAddedDto resAddedDto) {
        //1.保存主表数据
        ResAdded resAdded = new ResAdded();
        this.geterateToResAdd(resAdded, resAddedDto);
        myBatisDao.executionPojo(RESADDED, resAdded, IMyBatisDao.SQL.INSTER, "addResource");
        //2.保存子项
        List<ResAddedItemDto> resAddedItemDtoList = resAddedDto.getDynamicForms();
        addResItem(resAddedItemDtoList, resAddedDto.getId());
        return true;
    }

    /**
     * 根据ID获取资源新增数据
     * @param id
     * @return ResAdded
     */
    private ResAdded queryResource(String id) {
        return myBatisDao.selectClass(RESADDED, id, "queryResource");
    }

    /**
     * 查询资源新增数据是否已经存在
     * @param resAddedDto 资源新增主表对象
     * @return
     */
    @Override
    public ResAddedDto queryResourceRepeat(ResAddedDto resAddedDto) {
        if (StringUtils.isNotEmpty(resAddedDto.getId())) {
            //获取原先的数据相关
            ResAdded resAdded = queryResource(resAddedDto.getId());
            resAddedDto.setPriceReqNo(resAdded.getPriceReqNo());
            resAddedDto.setCreateTime(resAdded.getCreateTime());
            //先删除所有的关联数据
            delConfigBeforeEdit(resAddedDto.getId());
        } else {
            String addedId = UUIDUtils.generateUUID();
            String priceReqNo = CodeGenerater.generate("RA", 16, "yyyyMMdd");//价格申请单号
            resAddedDto.setId(addedId);
            resAddedDto.setPriceReqNo(priceReqNo);
            resAddedDto.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        return resAddedDto;
    }

    /**
     * 查询资源新增数据是否已经存在
     * @param resAddedDto 资源新增主表对象
     * @return
     */
    @Override
    public ResAddedDto queryResourceRepeatShop(ResAddedDto resAddedDto) {
        if (StringUtils.isNotEmpty(resAddedDto.getId())) {
            //获取原先的数据相关
            ResAdded resAdded = queryResource(resAddedDto.getId());
            resAddedDto.setPriceReqNo(resAdded.getPriceReqNo());
            resAddedDto.setCreateTime(resAdded.getCreateTime());
            //先删除所有的关联数据
            myBatisDao.executionPojo(RESADDED, resAddedDto.getId(),
                    IMyBatisDao.SQL.DELETE,"delResource");
        } else {
            String addedId = UUIDUtils.generateUUID();
            String priceReqNo = CodeGenerater.generate("RA", 16, "yyyyMMdd");//价格申请单号
            resAddedDto.setId(addedId);
            resAddedDto.setPriceReqNo(priceReqNo);
            resAddedDto.setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        return resAddedDto;
    }

    /**
     * 保存资源新增子表数据
     * @param resAddedItemDtoList 资源新增子表集合
     * @param addedId             资源新增主表对象
     */
    private void addResItem(List<ResAddedItemDto> resAddedItemDtoList, String addedId) {
        for (int i = 0; i < resAddedItemDtoList.size(); i++) {
            ResAddedItemDto resAddedItemDto = resAddedItemDtoList.get(i);
            String addedItemId = UUIDUtils.generateUUID();
            resAddedItemDto.setId(addedItemId);
            resAddedItemDto.setAddedId(addedId);
            ResAddedItem resAddedItem = new ResAddedItem();
            this.geterateToResAddItem(resAddedItem, resAddedItemDto);
            myBatisDao.executionPojo(RESADDED, resAddedItem, IMyBatisDao.SQL.INSTER, "addResItem");
            List<ResAddedItemPartsDto> resAddedItemPartsDtoList = resAddedItemDto.getHardWareData();
            addResItemParts(resAddedItemPartsDtoList, addedId, addedItemId);
            List<ResAddedItemVersionDto> resAddedItemVersionDtoList = resAddedItemDto.getSoftWareData();
            addResItemVersion(resAddedItemVersionDtoList, addedId, addedItemId);
        }
    }

    /**
     * 保存资源新增配件信息数据
     * @param resAddedItemPartsDtoList 资源新增配件列表
     * @param addedId                  资源新增主表ID
     * @param addedItemId              资源新增主表对象
     */
    public void addResItemParts(List<ResAddedItemPartsDto> resAddedItemPartsDtoList,
                                String addedId, String addedItemId) {
        for (int i = 0; i < resAddedItemPartsDtoList.size(); i++) {
            ResAddedItemPartsDto resAddedItemPartsDto = resAddedItemPartsDtoList.get(i);
            resAddedItemPartsDto.setId(UUIDUtils.generateUUID());
            resAddedItemPartsDto.setAddedId(addedId);
            resAddedItemPartsDto.setAddedItemId(addedItemId);
            ResAddedItemParts resAddedItemParts = new ResAddedItemParts();
            this.geterateToResAddItemParts(resAddedItemParts, resAddedItemPartsDto);
            myBatisDao.executionPojo(RESADDED, resAddedItemParts,
                    IMyBatisDao.SQL.INSTER, "addResItemParts");
        }
    }

    /**
     * 保存资源新增软件版本数据
     * @param resAddedItemVersionDtoList 资源新增软件列表
     * @param addedId                    资源新增主表ID
     * @param addedItemId                资源新增主表对象
     */
    public void addResItemVersion(List<ResAddedItemVersionDto>
        resAddedItemVersionDtoList, String addedId,String addedItemId) {
        for (int i = 0; i < resAddedItemVersionDtoList.size(); i++) {
            ResAddedItemVersionDto resAddedItemVersionDto = resAddedItemVersionDtoList.get(i);
            resAddedItemVersionDto.setId(UUIDUtils.generateUUID());
            resAddedItemVersionDto.setAddedId(addedId);
            resAddedItemVersionDto.setAddedItemId(addedItemId);
            ResAddedItemVersion resAddedItemVersion = new ResAddedItemVersion();
            this.geterateToResAddItemVersion(resAddedItemVersion, resAddedItemVersionDto);
            myBatisDao.executionPojo(RESADDED, resAddedItemVersion, IMyBatisDao.SQL.INSTER, "addResItemVersion");
        }
    }

    /**
     * 根据资源新增Id查询对应的资源信息的所有信息
     * @param id 资源新增id
     * @return
     */
    @Override
    public ResAdded queryResAddedConfig(String id) {
        ResAdded resAdded = queryResource(id);
        List<ResAddedItem> dynamicForms = myBatisDao.selectListT
                (RESADDED, resAdded.getId(), "queryResItem");
        for (int j = 0; j < dynamicForms.size(); j++) {
            ResAddedItem resAddedItem = dynamicForms.get(j);
            List<ResAddedItemParts> hardWareData = myBatisDao.selectListT
                    (RESADDED, resAddedItem.getId(), "queryResItemParts");
            List<ResAddedItemVersion> softWareData = myBatisDao.selectListT
                    (RESADDED, resAddedItem.getId(), "queryResItemVersion");
            resAddedItem.setHardWareData(hardWareData);
            resAddedItem.setSoftWareData(softWareData);
        }
        resAdded.setDynamicForms(dynamicForms);
        return resAdded;
    }

    /**
     * 根据资源新增id删除所有的资源新增数据
     * @param id
     */
    @Override
    public void delConfigBeforeEdit(String id) {
        myBatisDao.executionPojo(RESADDED, id, IMyBatisDao.SQL.DELETE, "delResource");
        deleteConfigItem(id);
    }

    /**
     * 根据资源新增id删除iaas库数据
     * @param id
     */
    public void delIaasData(String id) {
        myBatisDao.executionPojo(RESADDEDEXT, id, IMyBatisDao.SQL.DELETE, "delIaasResource");
        myBatisDao.executionPojo(RESADDEDEXT, id, IMyBatisDao.SQL.DELETE, "delIaasResItem");
        myBatisDao.executionPojo(RESADDEDEXT, id, IMyBatisDao.SQL.DELETE, "delIaasResItemParts");
        myBatisDao.executionPojo(RESADDEDEXT, id, IMyBatisDao.SQL.DELETE, "delIaasResItemVersion");
    }


    /**
     * 根据资源新增删除所有Item表 parts表 version表数据
     * @param id 资源新增Id
     */
    @Override
    public void deleteConfigItem(String id) {
        myBatisDao.executionPojo(RESADDED, id, IMyBatisDao.SQL.DELETE, "delResItem");
        myBatisDao.executionPojo(RESADDED, id, IMyBatisDao.SQL.DELETE, "delResItemParts");
        myBatisDao.executionPojo(RESADDED, id, IMyBatisDao.SQL.DELETE, "delResItemVersion");
    }

    /**
     * 资源新增时，转换对象
     * @param resAdded
     * @param resAddedDto
     */
    @Override
    public void geterateToResAdd(ResAdded resAdded, ResAddedDto resAddedDto) {
        resAdded.setId(resAddedDto.getId());
        resAdded.setPriceReqNo(resAddedDto.getPriceReqNo());
        resAdded.setAppNo(resAddedDto.getAppNo());
        resAdded.setAppName(resAddedDto.getAppName());
        resAdded.setProjectNo(resAddedDto.getProjectNo());
        resAdded.setProjectName(resAddedDto.getProjectName());
        resAdded.setExpectedOnlineTime(resAddedDto.getExpectedOnlineTime());
        if (StringUtil.isNotBlank(resAddedDto.getAmount())) {
            String amount = resAddedDto.getAmount();
            resAdded.setAmount(Float.parseFloat(amount));
        } else {
            resAdded.setAmount(0);
        }
        resAdded.setStatus(resAddedDto.getStatus());
        resAdded.setCreator(resAddedDto.getCreator());
        resAdded.setCreatorName(resAddedDto.getCreatorName());
        resAdded.setCreateTime(resAddedDto.getCreateTime());
        resAdded.setSourceType("0");
        resAdded.setBusiDeptName(resAddedDto.getBusiDeptName());
        resAdded.setTechDeptName(resAddedDto.getTechDeptName());
        resAdded.setSysOperLevel(resAddedDto.getSysOperLevel());
        resAdded.setIsFundTrans(resAddedDto.getIsFundTrans());
        resAdded.setCiid(resAddedDto.getCiid());
        resAdded.setBusiDeptId(resAddedDto.getBusiDeptId());
        resAdded.setTechDeptId(resAddedDto.getTechDeptId());
        resAdded.setProcessInstID(resAddedDto.getProcessInstID());
        resAdded.setDocmId(resAddedDto.getDocmId());
        resAdded.setReNewProcessNo(resAddedDto.getReNewProcessNo());
    }

    /**
     * 转换对象 新增附表
     * @param resAddedItem
     * @param resAddedItemDto
     */
    @Override
    public void geterateToResAddItem(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        resAddedItem.setId(resAddedItemDto.getId());
        resAddedItem.setAddedId(resAddedItemDto.getAddedId());
        resAddedItem.setNodeType(resAddedItemDto.getNodeType());
        resAddedItem.setModelType(resAddedItemDto.getModelType());
        resAddedItem.setModelConfig(resAddedItemDto.getModelConfig());
        resAddedItem.setNodeName(resAddedItemDto.getNodeName());
        resAddedItem.setOwnCenter(resAddedItemDto.getOwnCenter());
        resAddedItem.setNetworkArea(resAddedItemDto.getNetworkArea());
        resAddedItem.setNodeArch(resAddedItemDto.getNodeArch());
        switchNasWithTypeChange(resAddedItem, resAddedItemDto);//NAS
        switchSanWithTypeChange(resAddedItem, resAddedItemDto);//SAN
        switchSpecialSanWithTypeChange(resAddedItem, resAddedItemDto);//归档SAN
        switchDbWithTypeChange(resAddedItem, resAddedItemDto);//db
        switchOsWithTypeChange(resAddedItem, resAddedItemDto);//os
        switchItemWithTypeChange(resAddedItem, resAddedItemDto);//配件和软件
        resAddedItem.setModelConfigName(resAddedItemDto.getModelConfigName());
        resAddedItem.setNetworkAreaName(resAddedItemDto.getNetworkAreaName());
        resAddedItem.setSoftId(resAddedItemDto.getSoftId());
        switchModelWithTypeChange(resAddedItem, resAddedItemDto);
    }

    /**
     * Dto转Model 归档SAN部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchSpecialSanWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        resAddedItem.setSanSpecialId(resAddedItemDto.getSanSpecialId());
        if (StringUtil.isNotBlank(resAddedItemDto.getSanSpecialSize())) {
            resAddedItem.setSanSpecialSize(Integer.valueOf(resAddedItemDto.getSanSpecialSize()));
        } else {
            resAddedItem.setSanSpecialSize(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getSanSpecialPrice())) {
            resAddedItem.setSanSpecialPrice(Float.parseFloat(resAddedItemDto.getSanSpecialPrice()));
        } else {
            resAddedItem.setSanSpecialPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getSanSpecialRatio())) {
            resAddedItem.setSanSpecialRatio(Integer.valueOf(resAddedItemDto.getSanSpecialRatio()));
        } else {
            resAddedItem.setSanSpecialRatio(1);
        }
    }

    /**
     * Dto转Model SAN部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchSanWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        resAddedItem.setIsStorage(resAddedItemDto.getIsStorage());
        resAddedItem.setSanId(resAddedItemDto.getSanId());
        if (StringUtil.isNotBlank(resAddedItemDto.getStorageSize())) {
            resAddedItem.setStorageSize(Integer.valueOf(resAddedItemDto.getStorageSize()));
        } else {
            resAddedItem.setStorageSize(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getSanPrice())) {
            resAddedItem.setSanPrice(Float.valueOf(resAddedItemDto.getSanPrice()));
        } else {
            resAddedItem.setSanPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getSanRatio())) {
            resAddedItem.setSanRatio(Integer.valueOf(resAddedItemDto.getSanRatio()));
        } else {
            resAddedItem.setSanRatio(1);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getSanTotalPrice())) {
            resAddedItem.setSanTotalPrice(Float.parseFloat(resAddedItemDto.getSanTotalPrice()));
        } else {
            resAddedItem.setSanTotalPrice(0);
        }
    }

    /**
     * Dto转Model NAS部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchNasWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        resAddedItem.setIsNas(resAddedItemDto.getIsNas());
        resAddedItem.setMountType(resAddedItemDto.getMountType());
        if (StringUtil.isNotBlank(resAddedItemDto.getNasSize())) {
            resAddedItem.setNasSize(Integer.valueOf(resAddedItemDto.getNasSize()));
        } else {
            resAddedItem.setNasSize(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getNasPrice())) {
            resAddedItem.setNasPrice(Integer.valueOf(resAddedItemDto.getNasPrice()));
        } else {
            resAddedItem.setNasPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getNasTotalPrice())) {
            resAddedItem.setNasTotalPrice(Float.parseFloat(resAddedItemDto.getNasTotalPrice()));
        } else {
            resAddedItem.setNasTotalPrice(0);
        }
    }

    /**
     * Dto转Model 转换套餐部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchModelWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        if (StringUtil.isNotBlank(resAddedItemDto.getPrice())) {
            resAddedItem.setPrice(Float.parseFloat(resAddedItemDto.getPrice()));
        } else {
            resAddedItem.setPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getComboTotalPrice())) {
            resAddedItem.setComboTotalPrice(Float.parseFloat(resAddedItemDto.getComboTotalPrice()));
        } else {
            resAddedItem.setComboTotalPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getCpuNum())) {
            resAddedItem.setCpuNum(Integer.valueOf(resAddedItemDto.getCpuNum()));
        } else {
            resAddedItem.setCpuNum(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getCpuCode())) {
            resAddedItem.setCpuCode(Integer.valueOf(resAddedItemDto.getCpuCode()));
        } else {
            resAddedItem.setCpuCode(0);
        }

    }

    /**
     * Dto转Model 其他转换部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchItemWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        if (StringUtil.isNotBlank(resAddedItemDto.getHardWareTotalPrice())) {
            resAddedItem.setHardWareTotalPrice(Float.parseFloat(resAddedItemDto.getHardWareTotalPrice()));
        } else {
            resAddedItem.setHardWareTotalPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getSoftWareTotalPrice())) {
            resAddedItem.setSoftWareTotalPrice(Float.parseFloat(resAddedItemDto.getSoftWareTotalPrice()));
        } else {
            resAddedItem.setSoftWareTotalPrice(0);
        }
    }

    /**
     * Dto转Model 转换操作系统部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchOsWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        resAddedItem.setOs(resAddedItemDto.getOs());
        resAddedItem.setOsCountModel(resAddedItemDto.getOsCountModel());
        if (StringUtil.isNotEmpty(resAddedItemDto.getOsPrice())) {
            resAddedItem.setOsPrice(Float.parseFloat(resAddedItemDto.getOsPrice()));
        } else {
            resAddedItem.setOsPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getOsTotalPrice())) {
            resAddedItem.setOsTotalPrice(Float.parseFloat(resAddedItemDto.getOsTotalPrice()));
        } else {
            resAddedItem.setOsTotalPrice(0);
        }
    }

    /**
     * Dto转Model 转换数据库容量部分
     * @param resAddedItem
     * @param resAddedItemDto
     */
    private void switchDbWithTypeChange(ResAddedItem resAddedItem, ResAddedItemDto resAddedItemDto) {
        if (StringUtil.isNotBlank(resAddedItemDto.getDbSize())) {
            resAddedItem.setDbSize(Integer.valueOf(resAddedItemDto.getDbSize()));
        } else {
            resAddedItem.setDbSize(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getDbTotalPrice())) {
            resAddedItem.setDbTotalPrice(Float.parseFloat(resAddedItemDto.getDbTotalPrice()));
        } else {
            resAddedItem.setDbTotalPrice(0);
        }
        if (StringUtil.isNotBlank(resAddedItemDto.getDbPrice())) {
            resAddedItem.setDbPrice(Float.parseFloat(resAddedItemDto.getDbPrice()));
        } else {
            resAddedItem.setDbPrice(0);
        }
    }

    /**
     * Dto转Model 转换配件部分
     * @param resAddedItemParts
     * @param resAddedItemPartsDto
     */
    private void geterateToResAddItemParts(ResAddedItemParts resAddedItemParts
            , ResAddedItemPartsDto resAddedItemPartsDto) {
        resAddedItemParts.setId(resAddedItemPartsDto.getId());
        resAddedItemParts.setAddedItemId(resAddedItemPartsDto.getAddedItemId());
        resAddedItemParts.setAddedId(resAddedItemPartsDto.getAddedId());
        resAddedItemParts.setResConfigId(resAddedItemPartsDto.getResConfigId());
        resAddedItemParts.setApplyScene(resAddedItemPartsDto.getApplyScene());
        if (StringUtil.isNotBlank(resAddedItemPartsDto.getNum())) {
            resAddedItemParts.setNum(Integer.valueOf(resAddedItemPartsDto.getNum()));
        } else {
            resAddedItemParts.setNum(0);
        }
        if (StringUtil.isNotBlank(resAddedItemPartsDto.getPrice())) {
            resAddedItemParts.setPrice(Float.parseFloat(resAddedItemPartsDto.getPrice()));
        } else {
            resAddedItemParts.setPrice(0);
        }
        resAddedItemParts.setPartId(resAddedItemPartsDto.getPartId());
        resAddedItemParts.setDescribe(resAddedItemPartsDto.getDescribe());
        resAddedItemParts.setPartCategory(resAddedItemPartsDto.getPartCategory());
        resAddedItemParts.setTotalPrice(resAddedItemPartsDto.getTotalPrice());
    }

    /**
     * Dto转Model 转换软件版本部分
     * @param resAddedItemVersion
     * @param resAddedItemVersionDto
     */
    private void geterateToResAddItemVersion(ResAddedItemVersion resAddedItemVersion,
                                             ResAddedItemVersionDto resAddedItemVersionDto) {
        resAddedItemVersion.setId(resAddedItemVersionDto.getId());
        resAddedItemVersion.setAddedItemId(resAddedItemVersionDto.getAddedItemId());
        resAddedItemVersion.setAddedId(resAddedItemVersionDto.getAddedId());
        resAddedItemVersion.setType(resAddedItemVersionDto.getType());
        resAddedItemVersion.setName(resAddedItemVersionDto.getName());
        resAddedItemVersion.setVersion(resAddedItemVersionDto.getVersion());
        if (StringUtil.isNotBlank(resAddedItemVersionDto.getPrice())) {
            resAddedItemVersion.setPrice(Float.parseFloat(resAddedItemVersionDto.getPrice()));
        } else {
            resAddedItemVersion.setPrice(0);
        }
        resAddedItemVersion.setSoftId(resAddedItemVersionDto.getSoftId());
        resAddedItemVersion.setCountModel(resAddedItemVersionDto.getCountModel());
        resAddedItemVersion.setSoftCategory(resAddedItemVersionDto.getSoftCategory());
        resAddedItemVersion.setTotalPrice(resAddedItemVersionDto.getTotalPrice());
    }

    /**
     * 查询所有的存储套餐数据
     * @return
     */
    @Override
    public List<Map<String, Object>> queryStorageComboData() {
        return myBatisDao.selectListT(RESADDED, null, "queryStorageComboData");
    }

    /**
     * 获取通知列表
     * @param noticeDateStr -- 当天需要通知的预计上线日期，格式：yyyy-MM-dd
     * @return List<ResAdded>
     */
    @Override
    public List<ResAdded> findListByNotice(String noticeDateStr) {
        return myBatisDao.selectListT(RESADDED, noticeDateStr, "findListByNotice");
    }

    /**
     * 增加
     * @param resAddedDto 对象
     * @param pager  分页对象
     */
    @Override
    public void queryAdditionalList(ResAddedDto resAddedDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(resAddedDto, ResAddedDto.class);
        myBatisDao.selectPager(RESADDED, map, pager, "queryAdditionalList");
    }

    /**
     * 资产新增，根据主键，把新增所有信息转移到报表中
     * @param id
     */
    @Override
    public void addDataTransToReport(String id) {
        ResAdded resAdded = queryResource(id);
        List<ResAddedItem> dynamicForms = myBatisDao.selectListT(RESADDED, resAdded.getId(), "queryResItem");
        for (int j = 0; j < dynamicForms.size(); j++) {
            ResAddedItem resAddedItem = dynamicForms.get(j);
            resAddedImportResReportService.importReport(id, resAddedItem);
        }
    }

    /**
     * 导出纪平，总办维度的统计报表
     * @param id
     * @param response
     */
    public void exportZb(String id, HttpServletResponse response) {
        //获取申请单信息
        try (HSSFWorkbook wb = new HSSFWorkbook()) {
            ResAdded resAdd = myBatisDao.selectClass(RESADDED, id, "queryResInfo");
            resAddedReportService.generateReport(wb, resAdd);
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode("测试.xls", "UTF-8"))));
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("资源新增导出-资源新增技评总办报表失败",e);
        }
    }

    /**
     * 终止流程且删除业务数据
     * @param resAddedDto
     * @param userId
     */
    public void  deleteProcessInstance(ResAddedDto resAddedDto,String userId){
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        bps.setProcessInstId(resAddedDto.getProcessInstID());
        bps.setUserId(userId);
        BpsUtil.terminateProcessInstance(bps);
        if("0".equals(resAddedDto.getSourceType())){
            deleteResourceAdded(resAddedDto);
        }else {
            resExpandService.deleteResourceExpand(resAddedDto.getId());
        }
    }
}
