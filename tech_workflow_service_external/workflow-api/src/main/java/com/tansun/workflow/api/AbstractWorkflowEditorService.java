package com.tansun.workflow.api;

import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;

public class AbstractWorkflowEditorService implements IWorkflowEditorService{
    @Override
    @Deprecated
    public EditorBean findOrganizationList(EditorInfo editorInfo) {
        return new EditorBean();
    }

    @Override
    @Deprecated
    public EditorBean findDepartmentList(EditorInfo editorInfo) {
        return new EditorBean();
    }

    @Override
    @Deprecated
    public EditorBean findRoleList(EditorInfo editorInfo) {
        return new EditorBean();
    }

    @Override
    public EditorBean findUsersList(EditorInfo editorInfo) {
        return new EditorBean();
    }

    @Override
    public EditorBean findUserOrgRoleList(EditorInfo editorInfo) {
        return new EditorBean();
    }

    @Override
    public EditorBean findAllPostInfo(EditorInfo editorInfo) {
        return new EditorBean();
    }
}
