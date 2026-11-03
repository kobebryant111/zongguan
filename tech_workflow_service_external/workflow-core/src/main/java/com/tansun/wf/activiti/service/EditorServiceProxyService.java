package com.tansun.wf.activiti.service;

import com.tansun.workflow.api.AbstractWorkflowEditorService;
import com.tansun.workflow.api.IWorkflowEditorService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;

/**
 * 组织机构服务代理类
 */
public class EditorServiceProxyService extends AbstractWorkflowEditorService implements ApplicationContextAware {

    /**
     * spring容器中，组织机构实现类beanId
     */
    private String editorServiceBeanId;

    private IWorkflowEditorService iWorkflowEditorService;

    private ApplicationContext applicationContext;

    @Override
    public EditorBean findOrganizationList(EditorInfo editorInfo) {
        EditorBean result = getWorkflowEditorService().findOrganizationList(editorInfo);
        if (result != null && CollectionUtils.isNotEmpty(result.getOrgList())) {
            List<Map<String, Object>> orgList = result.getOrgList();
            CollectionUtils.transform(orgList, new Transformer() {
                @Override
                public Object transform(Object input) {
                    Map<String, Object> org = (Map<String, Object>) input;
                    org.put("name", org.get("orgName") + "("+org.get("orgCode")+")");
                    return org;
                }
            });
            result.setOrgList(orgList);
        }
        return result;
    }

    @Override
    public EditorBean findDepartmentList(EditorInfo editorInfo) {
        EditorBean result = getWorkflowEditorService().findDepartmentList(editorInfo);
        if (result != null && CollectionUtils.isNotEmpty(result.getDeptList())) {
            List<Map<String, Object>> deptList = result.getDeptList();
            CollectionUtils.transform(deptList, new Transformer() {
                @Override
                public Object transform(Object input) {
                    Map<String, Object> dept = (Map<String, Object>) input;
                    dept.put("name", dept.get("deptName") + "("+dept.get("deptCode")+")");
                    return dept;
                }
            });
            result.setDeptList(deptList);
        }
        return result;
    }

    @Override
    public EditorBean findUsersList(EditorInfo editorInfo) {
        return getWorkflowEditorService().findUsersList(editorInfo);
    }

    @Override
    public EditorBean findRoleList(EditorInfo editorInfo) {
        return getWorkflowEditorService().findRoleList(editorInfo);
    }

    @Override
    public EditorBean findUserOrgRoleList(EditorInfo editorInfo) {
        return getWorkflowEditorService().findUserOrgRoleList(editorInfo);
    }

    @Override
    public EditorBean findAllPostInfo(EditorInfo editorInfo) {
        return getWorkflowEditorService().findAllPostInfo(editorInfo);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getEditorServiceBeanId() {
        return editorServiceBeanId;
    }

    public void setEditorServiceBeanId(String editorServiceBeanId) {
        this.editorServiceBeanId = editorServiceBeanId;
    }

    /**
     * 获取spring容器中IWorkflowEditorService的实现类型
     *
     * @return IWorkflowEditorService
     */
    private IWorkflowEditorService getWorkflowEditorService() {
        if (iWorkflowEditorService == null) {
            return iWorkflowEditorService = applicationContext.getBean(editorServiceBeanId, IWorkflowEditorService.class);
        } else {
            return iWorkflowEditorService;
        }
    }
}
