package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.api.AbstractWorkflowEditorService;
import com.tansun.workflow.api.IWorkflowEditorService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.utils.RestTemplateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 远程调用 组织架构实现类
 */
public class HttpEditorService extends AbstractWorkflowEditorService {

    /**
     * 可以支持自定义每个接口方法的地址
     * 如果接口方法地址为空，则使用address + 固定后缀的方式配置（/findOrganizationList 、/findDepartmentList 、/findUsersList、/findRoleList）
     */
    private String address;

    private String userAddress;

    private String roleAddress;

    private String orgAddress;

    private String deptAddress;

    private String userOrgRoleAddress;


    public static Logger logger = LoggerFactory.getLogger(IWorkflowEditorService.class.toString());

    @Override
    public EditorBean findOrganizationList(EditorInfo editorInfo) {
        try {
            String url = address + "/findOrganizationList";
            if (StringUtils.isNotBlank(orgAddress) && !"null".equalsIgnoreCase(orgAddress)) {
                url = orgAddress;
            }
            return doPost(url, editorInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public EditorBean findDepartmentList(EditorInfo editorInfo) {
        try {
            String url = address + "/findDepartmentList";
            if (StringUtils.isNotBlank(deptAddress) && !"null".equalsIgnoreCase(deptAddress)) {
                url = deptAddress;
            }
            return doPost(url, editorInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public EditorBean findUsersList(EditorInfo editorInfo) {
        try {
            String url = address + "/findUsersList";
            if (StringUtils.isNotBlank(userAddress) && !"null".equalsIgnoreCase(userAddress)) {
                url = userAddress;
            }
            return doPost(url, editorInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public EditorBean findRoleList(EditorInfo editorInfo) {
        try {
            String url = address + "/findRoleList";
            if (StringUtils.isNotBlank(roleAddress) && !"null".equalsIgnoreCase(roleAddress)) {
                url = roleAddress;
            }
            return doPost(url, editorInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public EditorBean findUserOrgRoleList(EditorInfo editorInfo) {
        try {
            String url = address + "/findUserOrgRoleList";
            if (StringUtils.isNotBlank(userOrgRoleAddress) && !"null".equalsIgnoreCase(userOrgRoleAddress)) {
                url = roleAddress;
            }
            return doPost(url, editorInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public EditorBean findAllPostInfo(EditorInfo editorInfo) {
        try {
            String url = address + "/findAllPostInfo";
            return doPost(url, editorInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 通用远程调用请求
     * @return
     */
    private  EditorBean doPost(String url, EditorInfo params) throws Exception {
        if (params == null) {
            params = new EditorInfo();
        }
        String paramsJson = JSON.toJSONString(params);
        logger.info("url is :" + url);
        logger.info("mehtod: {}, invoke params is :" + paramsJson);

        String result = RestTemplateUtils.post(url, paramsJson);
        logger.info("result content is :" + result);
        return JSON.parseObject(result, EditorBean.class);

    }

    public String getAddress() {
        return new String(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getRoleAddress() {
        return roleAddress;
    }

    public void setRoleAddress(String roleAddress) {
        this.roleAddress = roleAddress;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getDeptAddress() {
        return deptAddress;
    }

    public void setDeptAddress(String deptAddress) {
        this.deptAddress = deptAddress;
    }

    public String getUserOrgRoleAddress() {
        return userOrgRoleAddress;
    }

    public void setUserOrgRoleAddress(String userOrgRoleAddress) {
        this.userOrgRoleAddress = userOrgRoleAddress;
    }
}

