package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.model.resourceplat.ResAddedItem;
import com.work.ppm.application.model.resourceplat.ResAddedItemParts;
import com.work.ppm.application.model.resourceplat.ResAddedItemVersion;
import com.work.ppm.application.model.resourceplat.ResReportInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResAddedImportResReportService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.resourceplat.ResAddedConstant;
import com.work.ppm.commons.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流转完成导入资源库
 * service
 * @date 2021/11/04
 */
@Service("ResAddedImportResReportService")
public class ResAddedImportResReportService extends BaseDaoSupper implements IResAddedImportResReportService {

    /**
     * myBatisDao
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * EOSDICTENTRY
     */
    private static String EOSDICTENTRY = "com.work.ppm.application.model.systemmanager.EosDictEntry";

    /**
     * RESADDED
     */
    private static String RESADDED = "com.work.ppm.application.model.resourceplat.ResAdded";

    /**
     * RES_REPORT_INFO
     */
    private static String RES_REPORT_INFO = "com.work.ppm.application.model.resourceplat.ResReportInfo";

    /**
     * 根据字典对象查询字典信息
     *
     * @param id
     * @return EosDictEntry
     */
    private EosDictEntry getEosDictEntry(String id) {
        EosDictEntry eosDictEntry = new EosDictEntry();
        eosDictEntry.setDictid(id);
        eosDictEntry.setDicttypeid(ResAddedConstant.RES_OWNCENTER);
        return myBatisDao.selectClass(EOSDICTENTRY, eosDictEntry, "queryDetail");
    }

    /**
     * 把报表实体插入报表中
     *
     * @param resReportInfo
     */
    private void insertResReportInfo(ResReportInfo resReportInfo) {
        myBatisDao.executionPojo(RES_REPORT_INFO, resReportInfo, IMyBatisDao.SQL.INSTER, "insertResReportInfo");
    }

    /**
     * 根据id将对应的数据导入报表中
     *
     * @param id           资源新增id
     * @param resAddedItem 资源新增对象
     */
    public void importReport(String id, ResAddedItem resAddedItem) {
        /** --- 获取基本的报表信息 ---**/
        ResReportInfo resReportInfo = getBasicReport(id, resAddedItem);

        Map<String, Object> map = new HashMap<>();
        map.put("nodeType", resAddedItem.getNodeType());
        map.put("modelType", resAddedItem.getModelType());
        Map<String, Object> result = myBatisDao.selectClass(RESADDED, map, "querySelectionConfig");

        String modelConfigType = result.get("MODELCONFIGTYPE").toString();
        /**---【1.物理机套餐,6.WEB云,5.DB云,7.容器云,4.定制服务器】---**/
        importRepoertWithOutVm(resAddedItem, resReportInfo, modelConfigType);
        /** ---【3.虚拟机资源 4.redis+虚拟机，选型配置固定为4C/4G/30G（根据“虚拟机套餐”特殊标志查询】---**/

        /**---【3.虚拟机资源】---**/
        importVmResource(resAddedItem, resReportInfo, modelConfigType);

        /**---【8.SAN】---**/
        if ("1".equals(resAddedItem.getIsStorage())) {
            importSanReport(resAddedItem, resReportInfo);
        }
        /** ---【9.NAS】---**/
        if ("1".equals(resAddedItem.getIsNas())) {
            importNasReport(resAddedItem, resReportInfo);
        }
        /** ---【10.软件：OS】---**/
        importOsReport(resAddedItem, resReportInfo);
        /** ---【11.软件：DB,12.软件：应用中间件,13.软件：消息中间件】---**/
        List<ResAddedItemVersion> softWareData = myBatisDao.selectListT
                (RESADDED, resAddedItem.getId(), "queryResItemVersion");
        for (int m = 0; m < softWareData.size(); m++) {
            ResAddedItemVersion resAddedItemVersion = softWareData.get(m);
            /** --- 11.软件：DB,12.软件：应用中间件,13.软件：消息中间件 ---**/
            inportSoftWareReport(resReportInfo, resAddedItemVersion);
        }
        /** ---14.数据库容量---**/
        importDbReport(resAddedItem, resReportInfo);
    }

    /**
     * 导入虚拟机资源
     * @param resAddedItem
     * @param resReportInfo
     * @param modelConfigType
     */
    private void importVmResource(ResAddedItem resAddedItem, ResReportInfo resReportInfo, String modelConfigType) {
        if (ResAddedConstant.COMBOTYPE_CONFIG_2.equals(modelConfigType) ||
                ResAddedConstant.COMBOTYPE_CONFIG_5.equals(modelConfigType)) {
            //3.虚拟机资源 //4.redis+虚拟机，选型配置固定为4C/4G/30G（根据“虚拟机套餐”特殊标志查询
            /**--- 拆分虚拟机套餐中的CPU核数,内存和硬盘信息 ---**/
            ResReportInfo vmReport = splitVmCombo(resAddedItem);

            int cpuCode = vmReport.getCpu();
            int memory = vmReport.getMemory();
            float disk = vmReport.getDisk();
            float vmTotalPrice = vmReport.getTotalPrice();
            List<ResAddedItemParts> hardWareData = myBatisDao.selectListT
                    (RESADDED, resAddedItem.getId(), "queryResItemParts");
            for (int n = 0; n < hardWareData.size(); n++) {
                ResAddedItemParts resAddedItemParts = hardWareData.get(n);

                if ("1".equals(resAddedItemParts.getPartCategory())) {
                    /**---【2.物理机配件】---**/
                    //物理机配件
                    importHardWareReport(resReportInfo, resAddedItemParts);
                } else if ("2".equals(resAddedItemParts.getPartCategory())) {
                    //虚拟机配件
                    if ("1".equals(resAddedItemParts.getResConfigId())) {
                        //cpu
                        cpuCode += resAddedItemParts.getNum();
                    } else if ("2".equals(resAddedItemParts.getResConfigId())) {
                        //内存
                        memory += resAddedItemParts.getNum();
                    } else if ("3".equals(resAddedItemParts.getResConfigId())) {
                        //硬盘
                        disk += resAddedItemParts.getNum();
                    }
                    vmTotalPrice += resAddedItemParts.getPrice() * resAddedItemParts.getNum();
                }
            }

            /**---【3.虚拟机资源】---**/
            imortVmReport(resReportInfo, modelConfigType, cpuCode, memory, disk, vmTotalPrice);
        }
    }

    /**
     * 获取基本的报表模板信息
     *
     * @param id           资源申请ID
     * @param resAddedItem 资源申请子表对象
     * @return resReportInfo 基本的报表模板
     */
    private ResReportInfo getBasicReport(String id, ResAddedItem resAddedItem) {

        EosDictEntry eosDictEntry = getEosDictEntry(resAddedItem.getOwnCenter());
        String ownCenter = eosDictEntry.getDictname();

        ResReportInfo resReportInfo = new ResReportInfo();
        //所属中心
        resReportInfo.setOwnCenter(ownCenter);
        //addedId
        resReportInfo.setResAddId(id);
        //id
        resReportInfo.setId(UUIDUtils.generateUUID());
        return resReportInfo;
    }

    /**
     * 导入除虚拟机套餐以外的套餐数据进报表
     *
     * @param resAddedItem    资源新增子表
     * @param resReportInfo   基本报表模板
     * @param modelConfigType
     */
    private void importRepoertWithOutVm(ResAddedItem resAddedItem,
                                        ResReportInfo resReportInfo, String modelConfigType) {
        ResReportInfo comboRpi = resReportInfo;
        comboRpi.setDescribe(resAddedItem.getModelConfigName());
        comboRpi.setNum("1");
        comboRpi.setPrice(resAddedItem.getComboTotalPrice());
        comboRpi.setTotalPrice(resAddedItem.getComboTotalPrice());

        if (ResAddedConstant.COMBOTYPE_CONFIG_1.equals(modelConfigType)) {
            //1.物理机套餐
            comboRpi.setResType("物理机套餐");
            comboRpi.setPx(0);
        } else if (ResAddedConstant.COMBOTYPE_CONFIG_3.equals(modelConfigType)) {
            if (ResAddedConstant.NODE_2.equals(resAddedItem.getNodeType())) {
                //6.WEB云
                comboRpi.setResType("WEB云");
                comboRpi.setPx(6);
            } else if (ResAddedConstant.NODE_3.equals(resAddedItem.getNodeType())) {
                //5.DB云
                comboRpi.setResType("DB云");
                comboRpi.setPx(5);
            } else if (ResAddedConstant.NODE_5.equals(resAddedItem.getNodeType())) {
                //7.容器云
                comboRpi.setResType("容器云");
                comboRpi.setPx(7);
            }
        } else if (ResAddedConstant.COMBOTYPE_CONFIG_4.equals(modelConfigType)) {
            //4.定制服务器
            comboRpi.setResType("定制服务器");
            comboRpi.setPx(4);
        }
        if (!"2".equals(modelConfigType) && !"5".equals(modelConfigType)) {
            insertResReportInfo(comboRpi);
        }
    }

    /**
     * 拆分虚拟机套餐中的资源（cpu核数，内存，硬盘）
     *
     * @param resAddedItem    资源配置子表对象
     * @return ResReportInfo
     */
    private ResReportInfo splitVmCombo(ResAddedItem resAddedItem) {
        ResReportInfo vmReport = new ResReportInfo();

        int cpuCode = 0;
        int memory = 0;
        int disk = 0;
        //根据ID拆分CPU核数,内存,硬盘容量
        Map<String, Object> vmComboData = myBatisDao.selectClass(RESADDED,
                resAddedItem.getModelConfig(), "queryComboById");
        if (vmComboData != null) {
            if (StringUtils.isNotEmpty(vmComboData.get("CPUCORE").toString())) {
                cpuCode = Integer.parseInt(vmComboData.get("CPUCORE").toString());
            }
            if (StringUtils.isNotEmpty(vmComboData.get("MEMORY").toString())) {
                memory = Integer.parseInt(vmComboData.get("MEMORY").toString());
            }
            if (StringUtils.isNotEmpty(vmComboData.get(ResAddedConstant.DISKCAPACITY).toString())) {
                disk = Integer.parseInt(vmComboData.get(ResAddedConstant.DISKCAPACITY).toString());
            }
        }
        vmReport.setCpu(cpuCode);
        vmReport.setMemory(memory);
        vmReport.setDisk(disk);
        vmReport.setTotalPrice(resAddedItem.getComboTotalPrice());

        return vmReport;
    }

    /**
     * 导入物理机配件信息进入资产统计报表
     *
     * @param resReportInfo     基本的报表模板
     * @param resAddedItemParts 配件信息对象
     */
    private void importHardWareReport(ResReportInfo resReportInfo, ResAddedItemParts resAddedItemParts) {
        ResReportInfo hardWareRpi = resReportInfo;
        hardWareRpi.setResType("物理机配件");
        hardWareRpi.setDescribe(resAddedItemParts.getDescribe());
        hardWareRpi.setNum(resAddedItemParts.getNum() + "");
        hardWareRpi.setPrice(resAddedItemParts.getPrice());
        hardWareRpi.setTotalPrice(resAddedItemParts.getNum() * resAddedItemParts.getPrice());
        hardWareRpi.setPx(2);
        insertResReportInfo(hardWareRpi);
    }

    /**
     * 将虚拟机资源导入资产统计报表
     *
     * @param resReportInfo   基本报表模板
     * @param modelConfigType 选型配置类型
     * @param cpuCode         cpu核心数
     * @param memory          内存
     * @param disk            硬盘
     * @param vmTotalPrice    虚拟机总价
     */
    private void imortVmReport(ResReportInfo resReportInfo,
                               String modelConfigType, int cpuCode, int memory,
                               float disk, float vmTotalPrice) {
        if ("2".equals(modelConfigType) || "4".equals(modelConfigType)) {
            /**---3.虚拟机资源---**/
            ResReportInfo vmRpi = resReportInfo;
            vmRpi.setResType("虚拟机资源");
            String describe = cpuCode + "C" + memory + "G" + disk + "G";
            vmRpi.setDescribe(describe);
            vmRpi.setNum("1");
            vmRpi.setPrice(0);
            vmRpi.setTotalPrice(vmTotalPrice);
            vmRpi.setPx(3);
            vmRpi.setCpu(cpuCode);
            vmRpi.setMemory(memory);
            vmRpi.setDisk(disk);
            insertResReportInfo(vmRpi);
        }
    }

    /**
     * 导入SAN数据进入资产统计报表
     *
     * @param resAddedItem  资源新增对象
     * @param resReportInfo 基本的报表模板
     */
    private void importSanReport(ResAddedItem resAddedItem, ResReportInfo resReportInfo) {
        //非归档SAN
        ResReportInfo sanRpi = resReportInfo;
        sanRpi.setResType("SAN存储-非归档");

        //san套餐名称
        Map<String, Object> sanParam = new HashMap<>();
        sanParam.put("id", resAddedItem.getSanId());
        Map<String, Object> sanData = queryStorageComboDataById(sanParam);
        if (sanData != null) {
            sanRpi.setDescribe(sanData.get(ResAddedConstant.APPLYSCENE).toString());
        }
        sanRpi.setNum(resAddedItem.getStorageSize() + "");
        sanRpi.setPrice(resAddedItem.getSanPrice());

        BigDecimal tb = BigDecimal.valueOf(1000);
        BigDecimal sanSize = BigDecimal.valueOf(resAddedItem.getStorageSize());
        BigDecimal sanPrice = BigDecimal.valueOf(resAddedItem.getSanPrice());
        sanRpi.setTotalPrice(
            sanSize.divide(tb).multiply(sanPrice).setScale(1, BigDecimal.ROUND_UP).floatValue()
        );
        sanRpi.setPx(8);
        insertResReportInfo(sanRpi);

        //归档SAN
        ResReportInfo sanSpecialRpi = resReportInfo;
        sanSpecialRpi.setResType("SAN存储-归档");

        //归档SAN套餐名称
        Map<String, Object> sanSpecialParam = new HashMap<>();
        sanSpecialParam.put("id", resAddedItem.getSanSpecialId());
        Map<String, Object> sanSpecialData = queryStorageComboDataById(sanSpecialParam);
        if (sanData != null) {
            sanSpecialRpi.setDescribe(sanSpecialData.get(ResAddedConstant.APPLYSCENE).toString());
        }

        sanSpecialRpi.setNum(resAddedItem.getSanSpecialSize() + "");
        sanSpecialRpi.setPrice(resAddedItem.getSanSpecialPrice());

        BigDecimal sanSpecialSize = BigDecimal.valueOf(resAddedItem.getSanSpecialSize());
        BigDecimal sanSpecialPrice = BigDecimal.valueOf(resAddedItem.getSanSpecialPrice());

        sanSpecialRpi.setTotalPrice(
            sanSpecialSize.divide(tb).multiply(sanSpecialPrice).setScale(1, BigDecimal.ROUND_UP).floatValue()
        );
        sanSpecialRpi.setPx(8);
        insertResReportInfo(sanSpecialRpi);
    }

    /**
     * 根据ID查询对那个的存储套餐数据
     *
     * @param param
     * @return map
     */
    private Map<String, Object> queryStorageComboDataById(Map<String, Object> param) {
        return myBatisDao.selectClass(RESADDED, param, "queryStorageComboData");
    }

    /**
     * 导入NAS信息进入资产统计报表
     *
     * @param resAddedItem  资源新增对象
     * @param resReportInfo 基本的报表模板
     */
    private void importNasReport(ResAddedItem resAddedItem, ResReportInfo resReportInfo) {
        ResReportInfo nasRpi = resReportInfo;
        nasRpi.setResType("NAS存储");

        Map<String, Object> param = new HashMap<>();
        param.put("id", resAddedItem.getMountType());
        Map<String, Object> nasData = queryStorageComboDataById(param);
        if (nasData != null) {
            nasRpi.setDescribe(nasData.get(ResAddedConstant.APPLYSCENE).toString());
        }
        nasRpi.setNum(resAddedItem.getNasSize() + "");
        nasRpi.setPrice(resAddedItem.getNasPrice());
        nasRpi.setTotalPrice(resAddedItem.getNasTotalPrice());
        nasRpi.setPx(9);
        insertResReportInfo(nasRpi);
    }

    /**
     * 导入操作系统软件进入资产统计报表
     *
     * @param resAddedItem  资源新增对象
     * @param resReportInfo 基本的报表模板
     */
    private void importOsReport(ResAddedItem resAddedItem, ResReportInfo resReportInfo) {
        ResReportInfo osRpi = resReportInfo;
        osRpi.setResType("软件：OS");

        //计费模式
        EosDictEntry eosDictEntry = getEosDictEntryCountModel(resAddedItem.getOsCountModel());

        osRpi.setDescribe(eosDictEntry.getDictname());
        osRpi.setNum("1");
        osRpi.setPrice(resAddedItem.getOsPrice());
        osRpi.setPx(10);
        osRpi.setTotalPrice(resAddedItem.getOsTotalPrice());
        insertResReportInfo(osRpi);
    }

    /**
     * 计费模式字典转义
     * @param countModel
     * @return EosDictEntry 错误信息
     */
    private EosDictEntry getEosDictEntryCountModel(String countModel) {
        EosDictEntry eosDictEntry = new EosDictEntry();
        eosDictEntry.setDictid(countModel);
        eosDictEntry.setDicttypeid("RES_BILLINGMODE");
        eosDictEntry = myBatisDao.selectClass(EOSDICTENTRY, eosDictEntry, "queryDetail");
        return eosDictEntry;
    }

    /**
     * 导入软件版本信息进入资产统计报表
     *
     * @param resReportInfo       基本的报表模板
     * @param resAddedItemVersion 软件版本对象
     */
    private void inportSoftWareReport(ResReportInfo resReportInfo, ResAddedItemVersion resAddedItemVersion) {
        if ("1".equals(resAddedItemVersion.getSoftCategory())) {
            ResReportInfo rpi = resReportInfo;

            //计费模式
            EosDictEntry eosDictEntry = getEosDictEntryCountModel(resAddedItemVersion.getCountModel());

            rpi.setDescribe(eosDictEntry.getDictname());
            rpi.setNum("1");
            rpi.setPrice(resAddedItemVersion.getPrice());
            rpi.setTotalPrice(resAddedItemVersion.getTotalPrice());

            if ("8".equals(resAddedItemVersion.getSoftCategory())) {
                //11.软件 DB
                rpi.setResType("软件：DB");
                rpi.setPx(11);

            } else if ("9".equals(resAddedItemVersion.getSoftCategory())) {
                //12.软件 应用中间件
                rpi.setResType("软件：应用中间件");
                rpi.setPx(12);
            } else if ("10".equals(resAddedItemVersion.getSoftCategory())) {
                //13.软件 消息中间件
                rpi.setResType("软件：消息中间件");
                rpi.setPx(13);
            }
            insertResReportInfo(rpi);
        }
    }

    /**
     * 导入数据库容量进入资产统计报表
     *
     * @param resAddedItem  资源新增对象
     * @param resReportInfo 基本报表模板
     */
    private void importDbReport(ResAddedItem resAddedItem, ResReportInfo resReportInfo) {
        if ("3".equals(resAddedItem.getNodeType()) && "3".equals(resAddedItem.getModelType())) {
            //仅db 云才计算数据库容量
            ResReportInfo dbRpi = resReportInfo;
            dbRpi.setResType("数据库容量");
            dbRpi.setDescribe("数据库容量");
            dbRpi.setNum(resAddedItem.getDbSize() + "");
            dbRpi.setPrice(resAddedItem.getDbPrice());
            dbRpi.setTotalPrice(resAddedItem.getDbTotalPrice());
            dbRpi.setPx(14);
            insertResReportInfo(dbRpi);
        }
    }
}
