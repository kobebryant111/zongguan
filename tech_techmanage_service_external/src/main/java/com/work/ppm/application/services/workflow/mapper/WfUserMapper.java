package com.work.ppm.application.services.workflow.mapper;

import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.model.UserOrgRoleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WfUserMapper {

    List<Map<String, Object>> findUserOrgDeptRelList(Map<String, Object> params);

    List<UserOrgRoleInfo> findOrgList(EditorInfo editorInfo);

    List<UserOrgRoleInfo> findRoleList(EditorInfo editorInfo);

    List<UserOrgRoleInfo> findOrgUserList(EditorInfo editorInfo);

    List<UserOrgRoleInfo> findRoleUserList(EditorInfo editorInfo);

    List<Map<String, Object>> findAllPostInfo(EditorInfo editorInfo);

    List<UserOrgRoleInfo> findDeptList(EditorInfo editorInfo);

    List<UserOrgRoleInfo> findDeptUserList(EditorInfo editorInfo);
}
