package com.work.ppm.application.services.resourceplat.impl;

import com.work.ppm.application.dto.resourceplat.ResConfigDto;
import com.work.ppm.application.dto.resourceplat.ResConfigQo;
import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResConfigSoft;
import com.work.ppm.application.model.resourceplat.ResConfigVm;
import com.work.ppm.application.services.resourceplat.IResConfigService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 资源配置
 *
 */
@Slf4j
@Service("resConfigService")
public class ResConfigService extends BaseDaoSupper implements IResConfigService {
    /**
     * 常量1
     */
    public static final String RESMSG_1 ="不能重复发布归档数据";
    /**
     * 常量2
     */
    public static final String RESMSG_2 ="已发布过相同配置的套餐";
    /**
     * 常量3
     */
    public static final String RESMSG_3 ="已发布，不可重复发布！";
    /**
     * 常量4
     */
    public static final String RESMSG_4 ="有未完成的报价订单，不可作废！";
    /**
     * 常量5
     */
    public static final String CONFIGCOMBOINSERTSQL ="addConfigComboData";
    /**
     * 常量6
     */
    public static final String STATUS ="status";
    /**
     * 常量7
     */
    public static final String ISCANCEL ="iscancel";
    /**
     * 常量8
     */
    public static final String COMBOINDEX ="5";


    /**
     * 物理机实体类路径
     */
    private static String RESCONFIGCOMBO = "com.work.ppm.application.model.resourceplat.ResConfigCombo";

    /**
     * 虚拟机实体类路径
     */
    private static String RESCONFIGVM = "com.work.ppm.application.model.resourceplat.ResConfigVm";

    /**
     * 软件实体类路径
     */
    private static String RESCONFIGSOFT = "com.work.ppm.application.model.resourceplat.ResConfigSoft";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public void queryConfigComboList(ResConfigDto resConfigDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(resConfigDto, ResConfigDto.class);
        if("0".equals(resConfigDto.getViewFlag())){
            myBatisDao.selectPager(RESCONFIGCOMBO, map, pager, "queryConfigComboList");
        } else {
            myBatisDao.selectPager(RESCONFIGCOMBO, map, pager, "queryConfigComboViewList");
        }
    }

    @Override
    public void queryVMPartsList(ResConfigDto resConfigDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(resConfigDto, ResConfigDto.class);
        if("0".equals(resConfigDto.getViewFlag())) {
            myBatisDao.selectPager(RESCONFIGVM, map, pager, "queryVMPartsList");
        } else {
            myBatisDao.selectPager(RESCONFIGVM, map, pager, "queryVMPartsViewList");
        }
    }

    @Override
    public void querySoftList(ResConfigDto resConfigDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(resConfigDto, ResConfigDto.class);
        if("0".equals(resConfigDto.getViewFlag())) {
            myBatisDao.selectPager(RESCONFIGSOFT, map, pager, "querySoftList");
        } else {
            myBatisDao.selectPager(RESCONFIGSOFT, map, pager, "querySoftViewList");
        }
    }

    @Override
    public List<ResConfigSoft> querySoftData(ResConfigDto resConfigDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(resConfigDto, ResConfigDto.class);
        return myBatisDao.selectListT(RESCONFIGSOFT, map, "querySoftViewList");
    }

    /**
     * 校验SAN套餐是否重复发布
     * @param resConfigCombo
     * @return String
     */
    String checkInSan(List<ResConfigCombo> resConfigCombo){
        for(int i =0; i<resConfigCombo.size(); i++){
            String subcategory =resConfigCombo.get(i).getSubcategory(); //存储套餐类型
            String isspecial =resConfigCombo.get(i).getIsspecial(); //存储套餐类型
            // SAN套餐是否重复发布
            if("51".equals(subcategory) && "0".equals(isspecial)){
                int cnt = getPublishCount(resConfigCombo.get(i));
                if(cnt >0){
                    // 已发布过相同配置的套餐
                    return "02";
                }
            }

        }
        return "1";
    }

    /**
     * 校验归档数据是否重复发布
     * @param resConfigQo
     * @param saveFlag
     * @param comboOneId
     * @return String
     */
    String checkMemoryCombo(ResConfigQo resConfigQo, String saveFlag, String comboOneId){
        List<ResConfigCombo> resConfigCombo = resConfigQo.getList();
        String isspecialFlag =resConfigQo.getIsspecialFlag();// 是否归档
        for (int i = 0; i < resConfigCombo.size(); i++) {
            // 存储套餐
            if (COMBOINDEX.indexOf(comboOneId) != -1 && "2".equals(saveFlag)) {
                if ("1".equals(isspecialFlag)) {
                    // 归档数据是否已发布
                    int cnt = getDdCnt(resConfigCombo.get(i).getCategory());
                    if (cnt > 0) {
                        // 不能重复发布归档数据
                        return "01";
                    }
                }
                String checkInSan = checkInSan(resConfigCombo);
                if (!"1".equals(checkInSan)) {
                    return checkInSan;
                }
            }
        }
        return "1";
    }

    /**
     * 设置资源配置
     * @param resConfigCombo
     * @return  describe
     */
    String getPhysicalData(ResConfigCombo resConfigCombo){
        Integer cpunum = resConfigCombo.getCpunum(); // 数量
        Integer cpucore = resConfigCombo.getCpucore(); //核数
        String frequency = resConfigCombo.getFrequency(); //主频
        String cpustr = cpunum + "/";
        String cpucorestr =cpucore + "C/";
        String frequencystr =frequency + "GHz/";

        Integer memory = resConfigCombo.getMemory(); // 内存
        String memorystr = memory + "G/";

        Integer disknum1 = resConfigCombo.getDisknum1(); // 磁盘数量1
        String diskconfig1 = resConfigCombo.getDiskconfig1(); //磁盘配置1
        String diskstr1 = disknum1 + "*" + diskconfig1+"/";

        Integer disknum2 = resConfigCombo.getDisknum2(); // 磁盘数量2
        String diskconfig2 = resConfigCombo.getDiskconfig2(); //磁盘配置2
        String diskstr2 = disknum2 + "*" + diskconfig2+"/";

        Integer disknum3 = resConfigCombo.getDisknum3(); // 磁盘数量3
        String diskconfig3 = resConfigCombo.getDiskconfig3(); //磁盘配置3
        String diskstr3 = disknum3 + "*" + diskconfig3+"/";
        StringBuilder describestr = new StringBuilder();
        if (StringUtil.isNotEmpty(cpunum) && cpunum>0) {
            describestr.append(cpustr);
        }
        if (StringUtil.isNotEmpty(cpucore) && cpucore>0) {
            describestr.append(cpucorestr);
        }
        if (StringUtil.isNotBlank(frequency)) {
            describestr.append(frequencystr);
        }
        if (StringUtil.isNotEmpty(memory) && memory>0) {
            describestr.append(memorystr);
        }

        if (StringUtil.isNotEmpty(disknum1) && disknum1>0) {
            describestr.append(diskstr1);
        }
        if (StringUtil.isNotEmpty(disknum2) && disknum2>0) {
            describestr.append(diskstr2);
        }
        if (StringUtil.isNotEmpty(disknum3)&& disknum3>0) {
            describestr.append(diskstr3);
        }
        setPhysicalGpu(resConfigCombo,describestr);
        String describe ="";
        if(StringUtil.isNotBlank(describestr.toString())){
            describe =describestr.substring(0,describestr.length()-1);
        }

        return describe;
    }

    /**
     * 设置配置
     * @param resConfigCombo
     * @param describestr
     */
    void setPhysicalGpu(ResConfigCombo resConfigCombo,StringBuilder describestr){
        Integer netcardnum = resConfigCombo.getNetcardnum(); //万兆网卡数量

        Integer fibercardnum = resConfigCombo.getFibercardnum(); //万光纤卡HBA数量

        Integer gpunum = resConfigCombo.getGpunum(); //GPU数量
        String gputype = resConfigCombo.getGputype(); //GPU型号
        String gpustr = gpunum + "*" + gputype+"/";
        String fibercardnumstr = fibercardnum + "*HBA/";
        String netcardnumstr = netcardnum + "*万兆网卡/";
        if (StringUtil.isNotEmpty(netcardnum)&& netcardnum>0) {
            describestr.append(netcardnumstr);
        }
        if (StringUtil.isNotEmpty(fibercardnum)&& fibercardnum>0) {
            describestr.append(fibercardnumstr);
        }
        if (StringUtil.isNotEmpty(gpunum)&& gpunum>0) {
            describestr.append(gpustr);
        }
    }

    /**
     * 新增物理机套餐
     * @param resConfigCombo
     */
    void setPhysicalCombo(ResConfigCombo resConfigCombo){
        // 设置资源配置
        String describestr =getPhysicalData(resConfigCombo);
        resConfigCombo.setDescribe(describestr);
        myBatisDao.executionPojo(RESCONFIGCOMBO, resConfigCombo,
                IMyBatisDao.SQL.INSTER, CONFIGCOMBOINSERTSQL);
    }

    /**
     * 新增虚拟机套餐
     * @param resConfigCombo
     */
    void setVmCombo(ResConfigCombo resConfigCombo){
            Integer cpucore = resConfigCombo.getCpucore(); //核数
            String cpustr = cpucore + "C/";

            Integer memory = resConfigCombo.getMemory(); // 内存
            String memorystr = memory + "G/";

            Integer diskcapacity = resConfigCombo.getDiskcapacity(); //系统盘（G）
            String diskcapacitystr = diskcapacity + "GB/";

            StringBuilder describestr = new StringBuilder();
            if (StringUtil.isNotEmpty(cpucore) && cpucore>0) {
                describestr.append(cpustr);

            }
            if (StringUtil.isNotEmpty(memory) && memory>0) {
                describestr.append(memorystr);
            }

            if (StringUtil.isNotEmpty(diskcapacity)&& diskcapacity>0) {
                describestr.append(diskcapacitystr);
            }
            String describe ="";
            if(StringUtil.isNotBlank(describestr.toString())){
                describe =describestr.substring(0,describestr.length()-1);
            }
            resConfigCombo.setDescribe(describe);
            resConfigCombo.setCpunum(cpucore);
            myBatisDao.executionPojo(RESCONFIGCOMBO, resConfigCombo,
                    IMyBatisDao.SQL.INSTER, CONFIGCOMBOINSERTSQL);
    }

    /**
     * 新增paas套餐
     * @param resConfigCombo
     */
    void setPaasCombo(ResConfigCombo resConfigCombo){
            resConfigCombo.setCpunum(0);
            myBatisDao.executionPojo(RESCONFIGCOMBO, resConfigCombo,
                    IMyBatisDao.SQL.INSTER, CONFIGCOMBOINSERTSQL);
    }

    @Override
    public String addConfigComboData(ResConfigQo resConfigQo, String saveFlag, String comboOneId) {
        List<ResConfigCombo> resConfigCombo = resConfigQo.getList();
        String creator = resConfigCombo.get(0).getCreator();
        if ("0".equals(saveFlag)) {
            saveFlag = "1";
        }
        // 校验存储套餐是否重复发布
        String checkFlag = checkMemoryCombo(resConfigQo,saveFlag,comboOneId);
        if(!"1".equals(checkFlag)){
            return checkFlag;
        }

        for (int i = 0; i < resConfigCombo.size(); i++) {
            String id=UUIDUtils.generateUUID();
            resConfigCombo.get(i).setId(id);
            resConfigCombo.get(i).setStatus(saveFlag);
            resConfigCombo.get(i).setCategory(comboOneId);
            resConfigCombo.get(i).setCreatetime(new Date());
            resConfigCombo.get(i).setCreator(creator);

            if ("1".equals(comboOneId)) {
                // 设置物理机套餐数据
                setPhysicalCombo(resConfigCombo.get(i));
            } else if("3".equals(comboOneId)){
                // 设置虚拟机套餐数据
                setVmCombo(resConfigCombo.get(i));
            } else if("6".equals(comboOneId)){
                // 设置paas套餐数据
                setPaasCombo(resConfigCombo.get(i));
            } else {
                myBatisDao.executionPojo(RESCONFIGCOMBO, resConfigCombo.get(i),
                        IMyBatisDao.SQL.INSTER, CONFIGCOMBOINSERTSQL);
            }
        }
        return "1";
    }

    @Override
    public int addVMPartsData(List<ResConfigVm> resConfigVm, String saveFlag) {
        String creator = resConfigVm.get(0).getCreator();
        if ("0".equals(saveFlag)) {
            saveFlag = "1";
        }
        for (int i = 0; i < resConfigVm.size(); i++) {
            String id=UUIDUtils.generateUUID();
            resConfigVm.get(i).setId(id);
            resConfigVm.get(i).setStatus(saveFlag);
            resConfigVm.get(i).setCreatetime(new Date());
            resConfigVm.get(i).setCreator(creator);
            myBatisDao.executionPojo(RESCONFIGVM, resConfigVm.get(i), IMyBatisDao.SQL.INSTER, "addVMPartsData");
        }

        return 1;
    }

    @Override
    public int addSoftData(List<ResConfigSoft> resConfigSoft, String saveFlag, String comboOneId) {

        String creator = resConfigSoft.get(0).getCreator();
        if ("0".equals(saveFlag)) {
            saveFlag = "1";
        }
        for (int i = 0; i < resConfigSoft.size(); i++) {
            String id=UUIDUtils.generateUUID();
            resConfigSoft.get(i).setId(id);
            resConfigSoft.get(i).setType(comboOneId);
            resConfigSoft.get(i).setStatus(saveFlag);
            resConfigSoft.get(i).setCreatetime(new Date());
            resConfigSoft.get(i).setCreator(creator);
            myBatisDao.executionPojo(RESCONFIGSOFT, resConfigSoft.get(i), IMyBatisDao.SQL.INSTER, "addSoftData");
        }
        return 1;
    }

    /**
     * 校验存储套餐
     * @param resConfigDto
     * @return 1
     */
    public Integer checkData(ResConfigDto resConfigDto){
        String saveFlag = resConfigDto.getSaveFlag();
        String comboOneId = resConfigDto.getComboOneId();
        String isspecial = resConfigDto.getIsspecial();
        String subcategory =resConfigDto.getSubcategory();
        // 存储套餐
        if(COMBOINDEX.indexOf(comboOneId) != -1 && "2".equals(saveFlag)){
            if("1".equals(isspecial)){
                // 归档数据是否已发布
                int cnt = getDdCnt(resConfigDto.getCategory());
                if(cnt >0){
                    // 不能重复发布归档数据
                    return -2;
                }
            }
            if("51".equals(subcategory) && "0".equals(isspecial)){
                ResConfigCombo resConfigCombo = new ResConfigCombo();
                resConfigCombo.setDevopslevel(resConfigDto.getDevopslevel());
                resConfigCombo.setIsrelatefund(resConfigDto.getIsrelatefund());
                int cnt = getPublishCount(resConfigCombo);
                if(cnt >0){
                    //已发布过相同配置的套餐
                    return -3;
                }
            }
        }
        return 1;
    }

    /**
     * 设置配置项
     * @param resConfigDto
     */
    void getComboData (ResConfigDto resConfigDto){
        Integer cpunum =0;
        if(StringUtil.isNotBlank(resConfigDto.getCpunum())){
            cpunum = Integer.valueOf(resConfigDto.getCpunum()); // 数量
        }
        resConfigDto.setCpunum(String.valueOf(cpunum));

        Integer cpucore =0;
        if(StringUtil.isNotBlank(resConfigDto.getCpucore())){
            cpucore = Integer.valueOf(resConfigDto.getCpucore()); //核数
        }
        resConfigDto.setCpucore(String.valueOf(cpucore));

        String frequency ="0";
        if(StringUtil.isNotBlank(resConfigDto.getFrequency())){
            frequency = resConfigDto.getFrequency(); //主频
        }
        resConfigDto.setFrequency(frequency);

        Integer memory =0;
        if(StringUtil.isNotBlank(resConfigDto.getMemory())){
            memory = Integer.valueOf(resConfigDto.getMemory()); // 内存
        }
        resConfigDto.setMemory(String.valueOf(memory));

        Integer disknum1 =0;
        if(StringUtil.isNotBlank(resConfigDto.getDisknum1())){
            disknum1 = Integer.valueOf(resConfigDto.getDisknum1()); // 磁盘配置1
        }
        resConfigDto.setDisknum1(String.valueOf(disknum1));

        Integer disknum2 =0;
        if(StringUtil.isNotBlank(resConfigDto.getDisknum2())){
            disknum2 = Integer.valueOf(resConfigDto.getDisknum2()); // 磁盘配置2
        }
        resConfigDto.setDisknum2(String.valueOf(disknum2));

        Integer disknum3 =0;
        if(StringUtil.isNotBlank(resConfigDto.getDisknum3())){
            disknum3 = Integer.valueOf(resConfigDto.getDisknum3()); // 磁盘配置3
        }
        resConfigDto.setDisknum3(String.valueOf(disknum3));

        Integer netcardnum =0;
        if(StringUtil.isNotBlank(resConfigDto.getNetcardnum())){
            netcardnum = Integer.valueOf(resConfigDto.getNetcardnum()); //万兆网卡数量
        }
        resConfigDto.setNetcardnum(String.valueOf(netcardnum));

        Integer fibercardnum =0;
        if(StringUtil.isNotBlank(resConfigDto.getFibercardnum())){
            fibercardnum = Integer.valueOf(resConfigDto.getFibercardnum()); //万光纤卡HBA数量
        }
        resConfigDto.setFibercardnum(String.valueOf(fibercardnum));

        Integer gpunum =0;
        if(StringUtil.isNotBlank(resConfigDto.getGpunum())){
            gpunum = Integer.valueOf(resConfigDto.getGpunum()); //GPU数量
        }
        resConfigDto.setGpunum(String.valueOf(gpunum));

        Integer diskcapacity =0;
        if(StringUtil.isNotBlank(resConfigDto.getDiskcapacity())){
            diskcapacity = Integer.valueOf(resConfigDto.getDiskcapacity()); //系统盘（G）
        }
        resConfigDto.setDiskcapacity(String.valueOf(diskcapacity));

        comboOneId (resConfigDto);

    }

    /**
     * 设置参数
     * @param resConfigDto
     */
    void comboOneId (ResConfigDto resConfigDto){
        String comboOneId = resConfigDto.getComboOneId();
        Integer cpucore =Integer.valueOf(resConfigDto.getCpucore());
        Integer memory =Integer.valueOf(resConfigDto.getMemory());
        Integer diskcapacity =Integer.valueOf(resConfigDto.getDiskcapacity());

        // 设置物理机套餐数据
        setPhysicalMachineCombo(resConfigDto);
        if("3".equals(comboOneId)){
            String cpustr = cpucore + "C/";

            String memorystr = memory + "G/";

            String diskcapacitystr = diskcapacity + "GB/";

            String describestr = "";
            if (cpucore>0) {
                describestr += cpustr;
                resConfigDto.setCpunum(String.valueOf(cpucore));
            }
            if (memory>0) {
                describestr += memorystr;
            }
            if (diskcapacity>0) {
                describestr += diskcapacitystr;
            }
            String describe ="";
            if(StringUtil.isNotBlank(describestr)){
                describe =describestr.substring(0,describestr.length()-1);
            }
            resConfigDto.setDescribe(describe);

        } else if("6".equals(comboOneId)){
            resConfigDto.setCpunum("0");

        }
    }

    /**
     * 设置物理机套餐
     * @param resConfigDto
     */
    void setPhysicalMachineCombo(ResConfigDto resConfigDto){
        String comboOneId = resConfigDto.getComboOneId();
        Integer cpucore =Integer.valueOf(resConfigDto.getCpucore());
        Integer memory =Integer.valueOf(resConfigDto.getMemory());
        Integer cpunum =Integer.valueOf(resConfigDto.getCpunum());
        String frequency =resConfigDto.getFrequency();


        if ("1".equals(comboOneId)) {
            String cpustr = cpunum + "/";
            String cpucorestr = cpucore + "C/";
            String frequencystr = frequency + "GHz/";

            String memorystr = memory + "G/";



            String describestr = "";
            if (cpunum>0) {
                describestr += cpustr;
            }
            if (cpucore>0) {
                describestr += cpucorestr;
            }
            if (StringUtil.isNotBlank(frequency)) {
                describestr += frequencystr;
            }
            if (memory>0) {
                describestr += memorystr;
            }

            describestr =setPhysicalMachineComboGpu(resConfigDto,describestr);

            String describe ="";
            if(StringUtil.isNotBlank(describestr)){
                describe =describestr.substring(0,describestr.length()-1);
            }
            resConfigDto.setDescribe(describe);
        }

    }

    /**
     * 设置gpu等参数
     * @param resConfigDto
     * @param describestr
     * @return describestr
     */
    String setPhysicalMachineComboGpu(ResConfigDto resConfigDto,String describestr){
        Integer netcardnum =Integer.valueOf(resConfigDto.getNetcardnum());
        Integer fibercardnum =Integer.valueOf(resConfigDto.getFibercardnum());
        Integer gpunum =Integer.valueOf(resConfigDto.getGpunum());
        String gputype = resConfigDto.getGputype(); //GPU型号
        Integer disknum1 =Integer.valueOf(resConfigDto.getDisknum1());
        Integer disknum2 =Integer.valueOf(resConfigDto.getDisknum2());
        Integer disknum3 =Integer.valueOf(resConfigDto.getDisknum3());
        String diskconfig1 =resConfigDto.getDiskconfig1();
        String diskconfig2 =resConfigDto.getDiskconfig2();
        String diskconfig3 =resConfigDto.getDiskconfig3();
        String diskstr1 = disknum1 + "*" + diskconfig1+"/";
        String diskstr2 = disknum2 + "*" + diskconfig2+"/";
        String diskstr3 = disknum3 + "*" + diskconfig3+"/";
        String netcardnumstr = netcardnum + "*万兆网卡/";
        String gpustr = gpunum + "*" + gputype+"/";
        String fibercardnumstr = fibercardnum + "*HBA/";
        if (disknum1>0) {
            describestr += diskstr1;
        }
        if (disknum2>0) {
            describestr += diskstr2;
        }
        if (disknum3>0) {
            describestr += diskstr3;
        }
        if (netcardnum>0) {
            describestr += netcardnumstr;
        }
        if (fibercardnum>0) {
            describestr += fibercardnumstr;
        }
        if (gpunum>0) {
            describestr += gpustr;
        }
        return describestr;
    }

    @Override
    public Integer upConfigComboData(ResConfigDto resConfigDto) {
        String saveFlag = resConfigDto.getSaveFlag();

        // 存储套餐
        int check =checkData(resConfigDto);
        if(check >0){
            getComboData(resConfigDto);
        } else {
            return check;
        }

        resConfigDto.setStatus(saveFlag);
        return myBatisDao.executionPojo(RESCONFIGCOMBO, resConfigDto, IMyBatisDao.SQL.UPDATE, "upConfigComboData");
    }

    @Override
    public Integer upSoftData(ResConfigDto resConfigDto) {
        String saveFlag = resConfigDto.getSaveFlag();
        resConfigDto.setStatus(saveFlag);
        return myBatisDao.executionPojo(RESCONFIGSOFT, resConfigDto, IMyBatisDao.SQL.UPDATE, "upSoftData");
    }

    @Override
    public Integer upVMPartsData(ResConfigDto resConfigDto) {
        String saveFlag = resConfigDto.getSaveFlag();
        resConfigDto.setStatus(saveFlag);
        return myBatisDao.executionPojo(RESCONFIGVM, resConfigDto, IMyBatisDao.SQL.UPDATE, "upVMPartsData");
    }

    /**
     * 校验虚拟机配件
     * @param resConfigDto
     * @param retMap
     * @return retMap
     */
    Map<String, Object> checkVm(ResConfigDto resConfigDto,Map<String, Object> retMap){
        String ids = resConfigDto.getId();
        String comboOneId = resConfigDto.getComboOneId();
        String saveFlag = resConfigDto.getSaveFlag();
        // 拼接id
        String[] splitid = ids.split(",");
        // 虚拟机配件是否重复发布
        if ("2".equals(saveFlag) && "4".equals(comboOneId)) {
            for (int i = 0; i < splitid.length; i++) {
                // 获取该类型是否已发布
                int cnt = querVmPartsCount(splitid[i].split("_")[1]);
                if (cnt > 0) {
                    String typeName = "";
                    if ("1".equals(splitid[i].split("_")[1])) {
                        typeName = "cpu";
                    } else if ("2".equals(splitid[i].split("_")[1])) {
                        typeName = "内存";
                    } else if ("3".equals(splitid[i].split("_")[1])) {
                        typeName = "硬盘";
                    }
                    retMap.put("msg",typeName+RESMSG_3);
                    retMap.put("code",-4);
                    return retMap;
                }
            }
        }
        retMap.put("code",1);
        return retMap;
    }

    /**
     * 校验存储套餐是否重复发布
     * @param resConfigDto
     * @param retMap
     * @return retMap
     */
    Map<String, Object> checkSanCombo (ResConfigDto resConfigDto,Map<String, Object> retMap){
        // SAN套餐是否重复发布
        String ids = resConfigDto.getId();
        String comboOneId = resConfigDto.getComboOneId();
        String saveFlag = resConfigDto.getSaveFlag();
        // 拼接id
        String[] splitid = ids.split(",");
        // 存储套餐
        if("5".equals(comboOneId) && "2".equals(saveFlag)){
            for (int i = 0; i < splitid.length; i++) {
                if("51".equals(splitid[i].split("_")[2]) && "0".equals(splitid[i].split("_")[1])){
                    ResConfigCombo resConfigCombo = new ResConfigCombo();
                    resConfigCombo.setDevopslevel(splitid[i].split("_")[3]);
                    resConfigCombo.setIsrelatefund(splitid[i].split("_")[4]);
                    int cnt = getPublishCount(resConfigCombo);
                    if(cnt >0){
                        retMap.put("msg",RESMSG_2);
                        retMap.put("code",-3);
                        return retMap;
                    }
                }

            }
        }
        retMap.put("code",1);
        return retMap;
    }

    /**
     * 发布前校验
     * @param resConfigDto
     * @param retMap
     * @return retMap
     */
    Map<String, Object> checkStatus(ResConfigDto resConfigDto,Map<String, Object> retMap){
        String ids = resConfigDto.getId();
        String comboOneId = resConfigDto.getComboOneId();
        String saveFlag = resConfigDto.getSaveFlag();
        // 拼接id
        String[] splitid = ids.split(",");

        // 虚拟机配件是否重复发布
        retMap =checkVm(resConfigDto,retMap);
        Integer stsFlag= (Integer)retMap.get("code");
        if(stsFlag <0){
            return  retMap;
        }

        // 校验SAN套餐是否重复发布
        retMap =checkSanCombo(resConfigDto,retMap);
        Integer sanFlag= (Integer)retMap.get("code");
        if(sanFlag <0){
            return  retMap;
        }
        // 存储套餐
        if(COMBOINDEX.indexOf(comboOneId) != -1 && "2".equals(saveFlag)){
            int iszy=0;
            for (int i = 0; i < splitid.length; i++) {
                if("1".equals(splitid[i].split("_")[1])){

                    // 归档数据或专用数据是否已发布
                    int cnt = getDdCnt(comboOneId);
                    if (cnt > 0 || iszy >0) {
                        retMap.put("msg",RESMSG_1);
                        retMap.put("code",-2);
                        return retMap;
                    }
                    iszy++;
                }
            }
        }
        retMap.put("code",1);
        return  retMap;
    }


    @Override
    public Map<String, Object> upConfigStatus(ResConfigDto resConfigDto) {
        Map<String, Object> retMap = new HashMap();
        String comboOneId = resConfigDto.getComboOneId();
        String saveFlag = resConfigDto.getSaveFlag();
        String ids = resConfigDto.getId();

        // 对已发布的数据进行校验
        retMap= checkStatus(resConfigDto,retMap);
        Integer stsFlag= (Integer)retMap.get("code");
        if(stsFlag <0){
            return  retMap;
        }
        String[] splitid = ids.split(",");

        StringBuilder sbid =new StringBuilder();
        for (int i = 0; i < splitid.length; i++) {
            sbid.append(splitid[i].split("_")[0]);
            sbid.append(',');
        }
        // 去除,
        String idss ="";
        if(StringUtil.isNotEmpty(sbid)){
           idss =sbid.toString().substring(0,sbid.length()-1);
        }
        String[] splitids = idss.split(",");
        Map<String, Object> map = new HashMap<>();

        map.put("ids", splitids);
        if ("2".equals(saveFlag)) {
            map.put(STATUS, "2");
            map.put(ISCANCEL,"0");
        } else if ("3".equals(saveFlag)) {
            map.put(STATUS, "");
            map.put(ISCANCEL,"1");
        }else if ("4".equals(saveFlag)) {
            map.put(STATUS, "4");
            map.put(ISCANCEL,"");
        }
        int id =0;
        if ("4".equals(comboOneId)) {
            id = myBatisDao.executionPojo(RESCONFIGVM, map, IMyBatisDao.SQL.UPDATE, "upVMPartsStatus");
        } else if ("7,8,9".indexOf(comboOneId) != -1 || "10".equals(comboOneId)) {
            id= myBatisDao.executionPojo(RESCONFIGSOFT, map, IMyBatisDao.SQL.UPDATE, "upSoftStatus");
        } else {
            id= myBatisDao.executionPojo(RESCONFIGCOMBO, map, IMyBatisDao.SQL.UPDATE, "upConfigComboStatus");
        }
        retMap.put("code",id);
        return retMap;
    }

    /**
     * 根据id查询虚拟机配件是否发布
     *
     * @param type 编号
     * @return int
     */
    public int querVmPartsCount(String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        Long count = myBatisDao.selectCount(RESCONFIGVM, map, "querVmPartsCount");
        return null != count ? count.intValue() : 0;
    }

    /**
     * 存储套餐归档数据的唯一性
     *
     * @return int
     */
    public int getDdCnt(String category) {
        Map<String, Object> map = new HashMap<>();
        map.put("category",category);
        Long count = myBatisDao.selectCount(RESCONFIGCOMBO, map, "getDdCnt");
        return null != count ? count.intValue() : 0;
    }

    /**
     * 存储套餐归SAN套餐的唯一性
     *
     * @return int
     */
    public int getPublishCount(ResConfigCombo resConfigCombo) {
        String isrelatefund =resConfigCombo.getIsrelatefund(); //是否涉及资金交易
        String devopslevel =resConfigCombo.getDevopslevel(); //系统运维等级
        Map<String, Object> map = new HashMap<>();
        map.put("isrelatefund",isrelatefund);
        map.put("devopslevel",devopslevel);
        Long count = myBatisDao.selectCount(RESCONFIGCOMBO, map, "getPublishCount");
        return null != count ? count.intValue() : 0;
    }

    @Override
    public List<ResConfigCombo> queryPhysicalParts() {
        Map<String, Object> map = new HashMap<>();
        return myBatisDao.selectListT(RESCONFIGCOMBO, map, "queryPhysicalParts");
    }

}
