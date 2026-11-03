package com.work.ppm.application.dto.resourceplat;

import com.work.ppm.application.model.resourceplat.ResConfigCombo;
import com.work.ppm.application.model.resourceplat.ResConfigSoft;
import com.work.ppm.application.model.resourceplat.ResConfigVm;

import java.util.List;

/**
 * ResAddedItemPartsDto
 */
public class ResConfigQo {

    /**
     * 保存状态区分
     **/
    private String saveFlag;

    /**
     * 归档数据标识
     **/
    private String isspecialFlag;


    /**
     * 模块区分标识
     **/
    private String comboOneId;

    /**
     * 物理机套餐
     **/
    private List<ResConfigCombo> list;

    /**
     * 虚拟机套餐
     **/
    private List<ResConfigVm> vmList;

    /**
     * 软件套餐
     **/
    private List<ResConfigSoft> softList;

    public String getSaveFlag() {
        return saveFlag;
    }

    public List<ResConfigVm> getVmList() {
        return vmList;
    }

    public void setVmList(List<ResConfigVm> vmList) {
        this.vmList = vmList;
    }

    public void setSaveFlag(String saveFlag) {
        this.saveFlag = saveFlag;
    }

    public String getComboOneId() {
        return comboOneId;
    }

    public void setComboOneId(String comboOneId) {
        this.comboOneId = comboOneId;
    }

    public List<ResConfigCombo> getList() {
        return list;
    }

    public void setList(List<ResConfigCombo> list) {
        this.list = list;
    }

    public List<ResConfigSoft> getSoftList() {
        return softList;
    }

    public void setSoftList(List<ResConfigSoft> softList) {
        this.softList = softList;
    }

    public String getIsspecialFlag() {
        return isspecialFlag;
    }

    public void setIsspecialFlag(String isspecialFlag) {
        this.isspecialFlag = isspecialFlag;
    }
}
