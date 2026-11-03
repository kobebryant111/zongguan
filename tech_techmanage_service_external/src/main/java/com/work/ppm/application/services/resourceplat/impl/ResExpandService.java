package com.work.ppm.application.services.resourceplat.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.resourceplat.ResExpandDto;
import com.work.ppm.application.dto.resourceplat.ResExpandItemDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResExpandItem;
import com.work.ppm.application.model.resourceplat.ResExpandItemParts;
import com.work.ppm.application.model.resourceplat.ResFlowPeople;
import com.work.ppm.application.services.resourceplat.IResExpandService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
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
import com.work.ppm.commons.utils.WebConnect;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源扩容
 *
 */
@Service("ResExpandService")
public class ResExpandService extends BaseDaoSupper implements IResExpandService {
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
     * 新资源扩容项
     *
     * @param resExpandItem
     */
    public void insertResourceExpandItem(ResExpandItem resExpandItem) {
        myBatisDao.executionPojo(RES_EXPAND_ITEM, resExpandItem, IMyBatisDao.SQL.INSTER, "insertResourceExpandItem");
    }

    /**
     * 新资源扩容项配置
     *
     * @param resExpandItemParts
     */
    public void insertResourceExpandItemParts(ResExpandItemParts resExpandItemParts) {
        myBatisDao.executionPojo(RES_EXPAND_ITEM_PARTS, resExpandItemParts,
                IMyBatisDao.SQL.INSTER, "insertResourceExpandItemParts");
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
     * 根据资源申请ID，查询资源配置项数据
     *
     * @param expandId
     */
    @Override
    public List<ResExpandItem> queryResourceExpandItem(String expandId) {
        return myBatisDao.selectListT(RES_EXPAND_ITEM, expandId, "queryResourceExpandItem");
    }

    /**
     * 根据资源配置项ID，查询资源配置项配置数据
     *
     * @param expandItemId
     */
    @Override
    public List<ResExpandItemParts> queryResourceExpandItemParts(String expandItemId) {
        return myBatisDao.selectListT(RES_EXPAND_ITEM_PARTS, expandItemId, "queryResourceExpandItemParts");
    }

    /**
     * 资源申请新增
     *
     * @param resExpandDto
     * @return
     */
    @Override
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
     * 资源申请删除
     *
     * @param id
     */
    public void deleteResourceExpand(String id) {
        //1根据申请主键删除配置项信息
        myBatisDao.executionPojo(RESADDED, id, IMyBatisDao.SQL.DELETE, "deleteResourceAdded");
        //2根据申请主键删除配置项配置资源信息
        this.deleteResourceExpandItem(id);
        //3删除申请表数据
        this.deleteResourceExpandParts(id);
    }

    /**
     * 资源申请提交
     *
     * @param resExpandDto
     * @return
     */
    @Override
    public String submitResourceExpand(ResExpandDto resExpandDto) {
        String xid = null;
        boolean flag = false;
        String processInfo = "";
        try {
            xid = BpsUtil.transactionBegin(resExpandDto.getCreator());
            //发起工作流
            BpsWorkFlowDto bpsWorkFlowDto = new BpsWorkFlowDto();
            bpsWorkFlowDto.setProcessDefName("com.work.ppm.bps.resourceFlow");
            //新增的提交，先生成序列号
            if (StringUtils.isEmpty(resExpandDto.getId())) {
                String priceReqNo = CodeGenerater.generate("RE", 16, "yyyyMMdd");
                resExpandDto.setPriceReqNo(priceReqNo);
            }
            bpsWorkFlowDto.setProcessInstName(resExpandDto.getAppName() + ResAddedConstant.RESOURCE_EXPAN);
            bpsWorkFlowDto.setProcessInstDesc(resExpandDto.getAppName() + ResAddedConstant.RESOURCE_EXPAN);
            bpsWorkFlowDto.setUserId(resExpandDto.getCreator());
            bpsWorkFlowDto.setApplydesc(resExpandDto.getAppName() + ResAddedConstant.RESOURCE_EXPAN);
            Map<String, String> map = new HashMap<>();
            //设置审批
            String spr = this.getSprInfo(ResConstant.MANUALACTIVITY1);
            if (StringUtils.isEmpty(spr)) {
                return "false";
            }
            map.put("users", spr);
            //设置流程流转数据
            map.put(ResConstant.STATUS, resExpandDto.getSourceType());
            bpsWorkFlowDto.setRelaData(map);
            String processInstID = BpsUtil.createProcessAndFinishFirst(bpsWorkFlowDto, xid);
            //设置流程实例ID
            resExpandDto.setProcessInstID(processInstID);
            //状态设置为2待审核
            resExpandDto.setStatus("2");
            //设置流程实例ID
            flag = insertResourceExpand(resExpandDto);
            if (flag) {
                BpsUtil.transactionCommit(resExpandDto.getCreator(), xid);
                processInfo = BpsUtil.queryProcessInfo(resExpandDto.getProcessInstID());
                BpsUtil.flowLog(bpsWorkFlowDto);
            } else {
                BpsUtil.transactionRollback(resExpandDto.getCreator(), xid);
                processInfo = "false";
            }

        } catch (Exception e) {
            BpsUtil.transactionRollback(resExpandDto.getCreator(), xid);
            throw new BizException(e);
        }
        return processInfo;
    }

    /**
     * 资源扩容编辑初始化
     *
     * @param id
     * @return
     */
    @Override
    public ResExpandDto queryResourceExpand(String id) {
        //1查询资源申请明细
        ResExpandDto resExpandDto = new ResExpandDto();
        //获取申请资源明细
        ResAdded resAdd = myBatisDao.selectClass(RESADDED, id, ResAddedConstant.QUERYRESINFO);
        //数据对象转换
        geterateToResExpand(resAdd, resExpandDto);
        //2根据资源申请ID，查询资源申请项数据
        List<ResExpandItem> resExpandItemList = this.queryResourceExpandItem(id);
        //3循环遍历资源申请项，查询申请项配置信息列表
        if (null != resExpandItemList && !resExpandItemList.isEmpty()) {
            List<ResExpandItemDto> tmpList = new ArrayList<>();
            ResExpandItemDto resExpandItemDto = null;
            for (int i = 0; i < resExpandItemList.size(); i++) {
                ResExpandItem resExpandItem = resExpandItemList.get(i);
                resExpandItemDto = new ResExpandItemDto();
                resExpandItemDto.setId(resExpandItem.getId());
                resExpandItemDto.setExtendId(resExpandItem.getExtendId());
                resExpandItemDto.setIp(resExpandItem.getIp());
                resExpandItemDto.setOwnCenter(resExpandItem.getOwnCenter());
                resExpandItemDto.setResType(resExpandItem.getResType());
                resExpandItemDto.setPrice(String.valueOf(resExpandItem.getPrice()));
                resExpandItemDto.setCreateTime(resExpandItem.getCreateTime());
                resExpandItemDto.setCiId(resExpandItem.getCiId());
                resExpandItemDto.setCiName(resExpandItem.getCiName());
                resExpandItemDto.setCpuNum(resExpandItem.getCpuNum());
                resExpandItemDto.setCpuCore(resExpandItem.getCpuCore());
                resExpandItemDto.setActCpuNum(resExpandItem.getActCpuNum());
                resExpandItemDto.setActCpuCore(resExpandItem.getActCpuCore());
                //设置配置项配置信息
                resExpandItemDto.setResExpandItemPartsList(this.queryResourceExpandItemParts(resExpandItem.getId()));
                tmpList.add(resExpandItemDto);
            }
            //把资源项列表，设置给资源申请
            resExpandDto.setResExpandItemList(tmpList);
        }
        return resExpandDto;
    }

    /**
     * 配置资源查询
     *
     * @param selectType   1物理机资源2虚拟机资源
     * @param sysOperLevel 系统运维等级
     * @param isFundTrans  是否涉及资金交易
     * @param ids          已选配件
     * @return
     */
    @Override
    public List<Map<String, Object>> queryConfgiItemList(
            String selectType, String sysOperLevel, String isFundTrans, String ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("selectType", selectType);
        map.put("sysOperLevel", sysOperLevel);
        map.put("isFundTrans", isFundTrans);
        if (StringUtils.isNotEmpty(ids)) {
            map.put("ids", ids.split(","));
        }
        //物理机
        if ("1".equals(selectType)) {
            return myBatisDao.selectListT(RES_EXPAND_ITEM, map, "queryConfigItemActrual");
        } else if ("2".equals(selectType)) {//虚拟机
            return myBatisDao.selectListT(RES_EXPAND_ITEM, map, "queryConfigItemVircual");
        } else { //数据库实例
            return myBatisDao.selectListT(RES_EXPAND_ITEM, map, "queryConfigItemPaas");
        }
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

    /**
     * 资源扩充c查询明细时，转换对象
     *
     * @param resAdded
     * @param resExpandDto
     */
    private void geterateToResExpand(ResAdded resAdded, ResExpandDto resExpandDto) {
        resExpandDto.setId(resAdded.getId());
        resExpandDto.setPriceReqNo(resAdded.getPriceReqNo());
        resExpandDto.setAppNo(resAdded.getAppNo());
        resExpandDto.setAppName(resAdded.getAppName());
        resExpandDto.setProjectName(resAdded.getProjectName());
        resExpandDto.setReNewProcessNo(resAdded.getReNewProcessNo());
        resExpandDto.setExpectedOnlineTime(resAdded.getExpectedOnlineTime());
        resExpandDto.setIsCmdbUsed(resAdded.getIsCmdbUsed());
        resExpandDto.setAmount(String.valueOf(resAdded.getAmount()));
        resExpandDto.setStatus(resAdded.getStatus());
        resExpandDto.setCreator(resAdded.getCreator());
        resExpandDto.setCreatorName(resAdded.getCreatorName());
        resExpandDto.setSourceType(resAdded.getStatus());
        resExpandDto.setBusiDeptName(resAdded.getBusiDeptName());
        resExpandDto.setTechDeptName(resAdded.getTechDeptName());
        resExpandDto.setSysOperLevel(resAdded.getSysOperLevel());
        resExpandDto.setIsFundTrans(resAdded.getIsFundTrans());
        resExpandDto.setCreateTime(resAdded.getCreateTime());
        resExpandDto.setProjectNo(resAdded.getProjectNo());
        resExpandDto.setBudgetNo(resAdded.getBudgetNo());
        resExpandDto.setCiid(resAdded.getCiid());
        resExpandDto.setBusiDeptId(resAdded.getBusiDeptId());
        resExpandDto.setTechDeptId(resAdded.getTechDeptId());
        resExpandDto.setProcessInstID(resAdded.getProcessInstID());
        resExpandDto.setDocmId(resAdded.getDocmId());
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
     *
     * @param resExpandItemParts
     * @param cpuNum
     * @param cpuCore
     * @param num
     * @param price
     * @param totalPrice
     * @return totalPrice
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
     *
     * @param num
     * @param price
     * @return totalPrice
     */
    private float getTotalPrice(float num, float price) {
        return gBprice(num, price);
    }

    /**
     * 计算总价
     *
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
     * 获取归档资源信息
     *
     * @param resExpandItemParts
     * @param num
     * @param price
     * @return totalPrice
     */
    private float getTotalPriceOne(ResExpandItemParts resExpandItemParts, float num, float price) {
        float totalPrice;
        List<ResConfigCombo> list = myBatisDao.selectListT(RES_CONFIG_COMBO, "", "querySanComboViewInfo");
        //主项总价
        float subPrice = gBprice(num, price);
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
     * 获取DB云的容量单价
     *
     * @param resExpandItemParts
     * @param num
     * @return totalPrice
     */
    private float getTotalPrices(ResExpandItemParts resExpandItemParts, float num) {
        float totalPrice;
        float memPrice /*= 0L*/;
        Map<String, Object> mapMem = myBatisDao.selectClass(RES_EXPAND_ITEM,
                resExpandItemParts.getResConfigId(), "queryDbMemPrice");
        if (null == mapMem || mapMem.isEmpty()) { //查不到，使用对象里面单价
            memPrice = Float.parseFloat(resExpandItemParts.getMemPrice());
        } else {
            memPrice = Float.parseFloat(mapMem.get("DISKCAPACITY").toString());
        }
        float memTotal = gBprice(num, memPrice);
        //保留一位小数
        totalPrice = memTotal;
        //设置保存DB云存储总价，后面报表使用
        resExpandItemParts.setMemTotalPrice(memTotal);
        return totalPrice;
    }

    /**
     * 根据容量（M）和单价（元/G）计算总价
     *
     * @param num
     * @param price
     * @return float
     */
    private float gBprice(float num, float price) {
        BigDecimal size = BigDecimal.valueOf(num);
        BigDecimal simplePrice = BigDecimal.valueOf(price);
        BigDecimal gb = BigDecimal.valueOf(1000);
        return size.divide(gb).multiply(simplePrice).setScale(1, BigDecimal.ROUND_UP).floatValue();
    }

    /**
     * 设置归档数据ID，容量，单价,比例
     *
     * @param resExpandItemParts
     * @param list
     * @return gdPrice
     */
    private float getGdPrice(ResExpandItemParts resExpandItemParts, List<ResConfigCombo> list) {
        float gdPrice;
        float sanRelateNum = Float.parseFloat(resExpandItemParts.getSanRelateNum());
        gdPrice = gBprice(sanRelateNum, Float.parseFloat(list.get(0).getPrice()));
        //保留一位小数
        BigDecimal tmp = BigDecimal.valueOf(gdPrice);
        gdPrice = tmp.setScale(1, BigDecimal.ROUND_UP).floatValue();
        return gdPrice;
    }

    /**
     * 配置应用系统ID，获取资源列表
     *
     * @param ciid 应用系统标识
     * @return 资源列表
     */
    public List<Map<String, Object>> queryCmdbResourceList(String ciid) {
        List<Map<String, Object>> list = new ArrayList<>();
        WebConnect conn = new WebConnect();
        String value = "{" +
                "\"transactionId\": \"QueryReleaxCiAttrBatchAction\"," +
                "\"body\":{\"direction\":\"from\"," +
                "\"reqJson\":{\"ordCiList\":[{" +
                "\"ciId\":\"" + ciid + "\"}]," +
                "\"destClassKeys\":[{\"classKey\":\"C_machine\"},{\"classKey\":\"C_VM\"}," +
                "{\"classKey\":\"C_database\"}]," +
                "\"destCiAttrList\":[{\"ciAttrKey\":\"A_20160318_000019\"}," +
                "{\"ciAttrKey\":\"A_20171106_000153\"}," +
                "{\"ciAttrKey\":\"A_20160318_000032\"}," +
                "{\"ciAttrKey\":\"A_20171211_000181\"}," +
                "{\"ciAttrKey\":\"A_20160318_000033\"}]" +
                "}}}";
        String url = CmdbServiceUtil.getCmdbServiceAddress();
        String headertype = "application/json";
        String values = conn.connect(url, value, headertype);
        JSONObject jsonObject = JSONObject.parseObject(values);
        if ("0000".equals(jsonObject.getString("retCode"))) {
            isREspJosn(list, jsonObject);
        }
        return list;
    }

    /**
     * 设置资源信息
     *
     * @param list
     * @param jsonObject
     */
    private void isREspJosn(List<Map<String, Object>> list, JSONObject jsonObject) {
        Map<String, Object> map = null;
        JSONArray destCiList = jsonObjects(jsonObject);
        for (int i = 0; i < destCiList.size(); i++) {
            JSONObject obj = destCiList.getJSONObject(i);
            map = new HashMap<>();
            //资源类型-物理机 虚拟机
            map.put("resType", obj.getString("ciClassifyName"));
            JSONArray destCiAttrList = obj.getJSONArray("destCiAttrList");
            for (int j = 0; j < destCiAttrList.size(); j++) {
                deskCIatt(map, destCiAttrList, j);

            }
            //设置资源的CIIC
            map.put("ciId", obj.getString("ciId"));
            //设置节点名称
            map.put("ciName", obj.getString("ciName"));
            list.add(map);
        }
    }

    /**
     * 数据转换
     *
     * @param jsonObject
     * @return destCiList
     */
    private JSONArray jsonObjects(JSONObject jsonObject) {
        JSONObject array = jsonObject.getJSONObject("data");
        JSONObject respJson = array.getJSONObject("respJson");
        JSONArray ciList = respJson.getJSONArray("ciList");
        JSONObject ci = ciList.getJSONObject(0);
        return ci.getJSONArray("destCiList");
    }

    /**
     * 设置ip、所属中心、cpu
     *
     * @param map
     * @param destCiAttrList
     * @param j
     */
    private void deskCIatt(Map<String, Object> map, JSONArray destCiAttrList, int j) {
        JSONObject destCiAttr = destCiAttrList.getJSONObject(j);
        if ("A_20160318_000019".equalsIgnoreCase(destCiAttr.getString(ResConstant.CIATTRKEY))) {
            //IP地址
            map.put("ip", destCiAttr.getString(ResConstant.CIATTRVALUE));
        } else if ("A_20171106_000153".equalsIgnoreCase(destCiAttr.getString(ResConstant.CIATTRKEY))) {
            //所属中心
            getOwnCenter(map, destCiAttr);
        } else if ("A_20160318_000032".equalsIgnoreCase(destCiAttr.getString(ResConstant.CIATTRKEY))) {
            //CPU个数
            String tmp = destCiAttr.getString(ResConstant.CIATTRVALUE);
            if (StringUtils.isEmpty(tmp)) {
                tmp = "0";
            }
            map.put("cpuNum", tmp);
        } else if ("A_20160318_000033".equalsIgnoreCase(destCiAttr.getString(ResConstant.CIATTRKEY))) {
            //CPU核数
            String tmp = destCiAttr.getString(ResConstant.CIATTRVALUE);
            if (StringUtils.isEmpty(tmp)) {
                tmp = "0";
            }
            map.put("cpuCore", tmp);
        } else if ("A_20171211_000181".equalsIgnoreCase(destCiAttr.getString(ResConstant.CIATTRKEY))) {
            map.put("dbLivingExample", destCiAttr.getString(ResConstant.CIATTRVALUE));
        }
    }

    /**
     * 所属中心
     *
     * @param map
     * @param destCiAttr
     */
    private void getOwnCenter(Map<String, Object> map, JSONObject destCiAttr) {
        if (destCiAttr.getString(ResConstant.CIATTRVALUE).equals(ResAddedConstant.JBZX)) {
            map.put(ResConstant.OWNCENTER, "江北");
        } else if (destCiAttr.getString(ResConstant.CIATTRVALUE).equals(ResAddedConstant.YZZX)) {
            map.put(ResConstant.OWNCENTER, "鄞州");
        } else {
            map.put(ResConstant.OWNCENTER, "其它");
        }
    }

    /**
     * 根据虚拟机的CIID，获取对应物理机信息
     *
     * @param ciid 虚拟机的CIID
     * @return map
     */
    public Map<String, Object> queryCmdbRelateResourceList(String ciid) {
        WebConnect conn = new WebConnect();
        String value = "{" +
                "\"transactionId\": \"QueryReleaxCiAttrBatchAction\"," +
                "\"body\":{\"direction\":\"from\"," +
                "\"reqJson\":{\"ordCiList\":[{" +
                "\"ciId\":\"" + ciid + "\"}]," +
                "\"destClassKeys\":[{\"classKey\":\"C_machine\"}]," +
                "\"destCiAttrList\":[{\"ciAttrKey\":\"A_20171106_000153\"}]" +
                "}}}";
        String url = CmdbServiceUtil.getCmdbServiceAddress();
        String headertype = "application/json";
        String values = conn.connect(url, value, headertype);
        JSONObject jsonObject = JSONObject.parseObject(values);
        Map<String, Object> map = new HashMap<>();
        if ("0000".equals(jsonObject.getString("retCode"))) {
            JSONObject array = jsonObject.getJSONObject("data");
            JSONObject respJson = array.getJSONObject("respJson");
            JSONArray ciList = respJson.getJSONArray("ciList");
            JSONObject ci = ciList.getJSONObject(0);
            JSONArray destCiList = ci.getJSONArray("destCiList");
            for (int i = 0; i < destCiList.size(); i++) {
                JSONObject obj = destCiList.getJSONObject(i);
                //资源类型-物理机 虚拟机
                map.put("resType", obj.getString("ciClassifyName"));
                JSONArray destCiAttrList = obj.getJSONArray("destCiAttrList");
                for (int j = 0; j < destCiAttrList.size(); j++) {
                    JSONObject destCiAttr = destCiAttrList.getJSONObject(j);
                    if ("A_20171106_000153".equalsIgnoreCase(destCiAttr.getString("ciAttrKey"))) {
                        //所属中心
                        getOwnCenter(map, destCiAttr);
                    }
                }
                //设置资源的CIIC
                map.put("ciId", obj.getString("ciId"));
            }
        }
        return map;
    }

    /**
     * 根据SAN资源，获取总的价格信息
     *
     * @param id    SAN资源ID
     * @param space 总的空间GB
     * @return map
     */
    public Map<String, Object> querySanInfo(String id, String space) {
        //获取资源信息
        ResConfigCombo resConfigCombo = myBatisDao.selectClass(RES_CONFIG_COMBO, id, "queryConfigComboViewInfo");
        //获取归档资源信息
        List<ResConfigCombo> list = myBatisDao.selectListT(RES_CONFIG_COMBO, "", "querySanComboViewInfo");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        //设置归档数据ID，容量，单价,比例
        if (null != list && !list.isEmpty()) {
            //归档数据ID
            map.put("SANRELATEID", list.get(0).getId());
            //容量
            String sanRelateRatio = list.get(0).getRatio();
            String ratio = resConfigCombo.getRatio();
            //计算容量
            map.put("SANRELATENUM", changeRadit(space, ratio, sanRelateRatio));
            //单价
            map.put("SANRELATEPRICE", list.get(0).getPrice());
            //比例
            map.put("SANRELATERATIO", list.get(0).getRatio());
            //
        } else {
            //归档数据ID
            map.put("SANRELATEID", "");
            //容量
            //计算容量
            map.put("SANRELATENUM", "");
            //单价
            map.put("SANRELATEPRICE", "");
            //比例
            map.put("SANRELATERATIO", "");
        }
        return map;
    }

    /**
     * 根据比例转换容量，保存整数
     *
     * @param space          SAN申请容量
     * @param ratio          SAN比例
     * @param sanRelateRatio 归档空间占比
     * @return val
     */
    public String changeRadit(String space, String ratio, String sanRelateRatio) {
        BigDecimal spaceBig = new BigDecimal(space);
        BigDecimal ratioBig = new BigDecimal(ratio);
        BigDecimal sanRelateRatioBig = new BigDecimal(sanRelateRatio);
        BigDecimal tmp = spaceBig.multiply(sanRelateRatioBig);
        BigDecimal result = tmp.divide(ratioBig, 0, BigDecimal.ROUND_UP);
        float val = result.floatValue();
        return String.valueOf(val);
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
     * 根据主表ID查询已作废的数据
     *
     * @param resExpandDto 主表
     * @return ApiResult<String> 作废信息
     */
    @Override
    public ApiResult<String> validOutOfDateMain(ResExpandDto resExpandDto) {
        ApiResult<String> result = new ApiResult<>();
        StringBuilder errorMessage = new StringBuilder();
        List<ResExpandItemDto> resExpandItemList = resExpandDto.getResExpandItemList();
        for (int i=0;i<resExpandItemList.size();i++){
            boolean ishead = false;
            ResExpandItemDto resExpandItemDto = resExpandItemList.get(i);
            List<ResExpandItemParts> resExpandItemPartsList = resExpandItemDto.getResExpandItemPartsList();
            for (int j = 0;j<resExpandItemPartsList.size();j++){
                ResExpandItemParts resExpandItemParts = resExpandItemPartsList.get(j);
                Map<String,Object> retMap = myBatisDao.selectClass(RESADDED,resExpandItemParts.getResConfigId()
                ,"validOutofDateDataResubmit");
                if(retMap!=null){
                    if(!ishead){
                        errorMessage.append(resExpandItemDto.getIp()+"-"+resExpandItemDto.getCiName()+"-"
                                +resExpandItemDto.getOwnCenter()+"-"+resExpandItemDto.getResType()+":\n");
                    }
                    errorMessage.append("\t"+retMap.get("DESCRIPTION")+"--已作废\n");
                    ishead = true;
                }
            }
        }
        if("".equals(errorMessage.toString())){
            result.setStatus(ApiCode.SUCCESS);
        }else{
            result.setStatus(ApiCode.FAIL);
            result.setMessage(errorMessage.toString());
        }
        return result;
    }
}
