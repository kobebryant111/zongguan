package com.work.ppm.application.services.workflow;


import com.github.pagehelper.PageInfo;
import com.work.ppm.application.services.workflow.mapper.WfUserMapper;
import com.tansun.workflow.api.AbstractWorkflowEditorService;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.model.UserOrgRoleInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformEditorService extends AbstractWorkflowEditorService {

    @Autowired
    private WfUserMapper wfUserMapper;

    /**
     * 查询用户
     */
    @Override
    public EditorBean findUsersList(EditorInfo info) {
        Map<String, Object> paramMap = new HashMap<>(12);
        paramMap.put("userNum", info.getUserId());//
        paramMap.put("loginName", info.getUserLoginName());
        paramMap.put("realname", info.getUserName());
        paramMap.put("loginNameLike", info.getUserLoginNameLike());//登录名模糊查询
        paramMap.put("realnameLike", info.getUserNameLike());//用户名模糊查询
        paramMap.put("pageNum", info.getCurrentPage());
        paramMap.put("pageSize", info.getPageSize());
        paramMap.put("deptCodes", info.getDeptCodes());//部门编号
        paramMap.put("orgCodes", info.getOrgCodes());//机构编号
        paramMap.put("roleCodes", info.getRoleCodes());//角色编号
        paramMap.put("userLoginNames", info.getUserLoginNames());
        paramMap.put("delInd", "0");
        paramMap.put("tenantId", info.getTenantId());
        // currentPage 当前页码，第一页为1（重要）
        // pageSize 每页数据大小（重要）

        // 压力测试20次会出现系统卡死问题
        // PageHelper.startPage(info.getCurrentPage(), info.getPageSize());
        PageInfo<Map<String, Object>> page = new PageInfo(wfUserMapper.findUserOrgDeptRelList(paramMap));
        List<Map<String, Object>> userList = page.getList();
        List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> u : userList) {
            Map<String, Object> user = new HashMap<>();
            user.put("useId", u.get("userNum"));
            user.put("userName", u.get("userRealname"));
            user.put("userLoginName", u.get("userLoginName"));
            user.put("deptName", u.get("deptName"));
            user.put("roleName", u.get("roleName"));
            user.put("orgName", u.get("orgName"));
            users.add(user);
        }
        EditorBean bean = new EditorBean();
        bean.setRetCode(SUCCEED);
        bean.setTotal(Long.valueOf(page.getTotal()));
        bean.setCurrentPageIndex(page.getPageNum());
        bean.setUserList(users);
        bean.setHasNextPage(page.isHasNextPage());
        bean.setTotal(page.getTotal());
        return bean;
    }

    /**
     * 查询参与者列表
     * type：目前有
     *  user（用户）
     *  org（机构）
     *  role（角色）
     */
    @Override
    public EditorBean findUserOrgRoleList(EditorInfo editorInfo) {
        List<UserOrgRoleInfo> total = new ArrayList<>();
        UserOrgRoleInfo topOrgInfo = new UserOrgRoleInfo();
        topOrgInfo.setId("#org");
        topOrgInfo.setName("机构");
        topOrgInfo.setPid("");
        topOrgInfo.setType("");
        total.add(topOrgInfo);

        // 机构树
        List<UserOrgRoleInfo> orgList = wfUserMapper.findOrgList(editorInfo);
        total.addAll(orgList);
        // 用户关联机构
        List<UserOrgRoleInfo> orgUserList = wfUserMapper.findOrgUserList(editorInfo);
        total.addAll(orgUserList);

        UserOrgRoleInfo topRoleInfo = new UserOrgRoleInfo();
        topRoleInfo.setId("#role");
        topRoleInfo.setName("角色");
        topRoleInfo.setPid("");
        topRoleInfo.setType("");
        total.add(topRoleInfo);

        // 角色树
        List<UserOrgRoleInfo> roleList = wfUserMapper.findRoleList(editorInfo);
        total.addAll(roleList);
        // 用户关联角色
        List<UserOrgRoleInfo> roleUserList = wfUserMapper.findRoleUserList(editorInfo);
        total.addAll(roleUserList);

        // 部门树
        List<UserOrgRoleInfo> deptList = wfUserMapper.findDeptList(editorInfo);
        total.addAll(deptList);
        // 用户关联部门
        List<UserOrgRoleInfo> deptUserList = wfUserMapper.findDeptUserList(editorInfo);
        total.addAll(deptUserList);

        EditorBean editorBean = new EditorBean();
        editorBean.setUserOrgRoleTree(total);
        editorBean.setRetCode(SUCCEED);
        editorBean.setTotal(Long.valueOf(total.size()));
        return editorBean;
    }

    /**
     * 入参：userId(用户id)
     * @param editorInfo
     * @return
     *
     */
    @Override
    public EditorBean findAllPostInfo(EditorInfo editorInfo) {
        if(StringUtils.isBlank(editorInfo.getUserId())){
            throw new RuntimeException("userId不能为空");
        }
        List<PostInfo> postInfos = new ArrayList<>();
        // 添加user
        PostInfo user = new PostInfo();
        user.setUserId(editorInfo.getUserId());
        user.setType("user");
        postInfos.add(user);

        // 查询用户的岗位
        List<Map<String, Object>> allPostInfo = wfUserMapper.findAllPostInfo(editorInfo);
        if(CollectionUtils.isNotEmpty(allPostInfo)){
            for (Map<String, Object> postInfoMap : allPostInfo) {
                PostInfo postInfo = new PostInfo();
                String orgCode = MapUtils.getString(postInfoMap, "orgCode");
                String roleCode = MapUtils.getString(postInfoMap, "roleCode");
                String deptCode = MapUtils.getString(postInfoMap, "deptCode");

                if((StringUtils.isNotBlank(orgCode) && StringUtils.isNotBlank(roleCode))
                        || (StringUtils.isNotBlank(roleCode) && StringUtils.isNotBlank(deptCode))){
                    // 机构和角色都不是空，表示岗位
                    postInfo.setType("post");
                    postInfo.setOrgCode(orgCode);
                    postInfo.setRoleCode(roleCode);
                    postInfo.setDeptCode(deptCode);
                }else if(StringUtils.isBlank(orgCode) && StringUtils.isNotBlank(roleCode) && StringUtils.isBlank(deptCode)){
                    // 机构部门是空，表示角色
                    postInfo.setType("role");
                    postInfo.setRoleCode(roleCode);
                }else if(StringUtils.isNotBlank(orgCode) && StringUtils.isBlank(roleCode) && StringUtils.isBlank(deptCode)){
                    // 角色部门是空，表示机构
                    postInfo.setType("org");
                    postInfo.setOrgCode(orgCode);
                }else if(StringUtils.isBlank(orgCode) && StringUtils.isBlank(roleCode) && StringUtils.isNotBlank(deptCode)){
                    // 角色机构是空，表示部门
                    postInfo.setType("dept");
                    postInfo.setDeptCode(deptCode);
                }
                postInfos.add(postInfo);
            }
        }

        EditorBean editorBean = new EditorBean();
        editorBean.setPostInfos(postInfos);
        editorBean.setRetCode(SUCCEED);
        editorBean.setTotal(Long.valueOf(postInfos.size()));
        return editorBean;
    }
}