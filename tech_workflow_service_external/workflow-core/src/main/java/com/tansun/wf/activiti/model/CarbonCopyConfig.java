package com.tansun.wf.activiti.model;


import java.util.ArrayList;
import java.util.List;

public class CarbonCopyConfig {

    private String type;

    private List<String> userLoginNames;

    private List<String> roleCodes;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getUserLoginNames() {
        return userLoginNames == null? new ArrayList<String>(): userLoginNames;
    }

    public void setUserLoginNames(List<String> userLoginNames) {
        this.userLoginNames = userLoginNames;
    }

    public List<String> getRoleCodes() {
        return roleCodes == null? new ArrayList<String>(): roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

}
