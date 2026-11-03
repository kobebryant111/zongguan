package com.tansun.wf.platform.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.platform.mapper.WfUserMapper;
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
     * 返回例子：
     * {
     *     "userList": [
     *         {
     *             "deptName": null,
     *             "orgName": "总部",
     *             "useId": "USR170120000087",
     *             "roleName": "平台管理员",
     *             "userLoginName": "superadmin", // 重要
     *             "userName": "superadmin" // 重要
     *         },
     *         {
     *             "deptName": "科技部",
     *             "orgName": "福建省行",
     *             "useId": "USR847063039651254272",
     *             "roleName": "平台管理员",
     *             "userLoginName": "zhangsan",
     *             "userName": "张三"
     *         },
     *         {
     *             "deptName": null,
     *             "orgName": "厦门分行",
     *             "useId": "USR851781655834910720",
     *             "roleName": "平台管理员",
     *             "userLoginName": "li4",
     *             "userName": "李四"
     *         }
     *     ],
     *     "total": 3,
     *     "currentPageIndex": 1,
     *     "hasNextPage": false,
     *     "retCode": "0"
     *
     * }
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
     *
     * 返回例子：
     * {
     *     "retCode": "0",
     *     "description": null,
     *     "data": [
     *         {
     *             "id": "#org",// #开头表示顶级，在流程图上是不可选的
     *             "name": "机构",
     *             "pid": "",
     *             "type": ""
     *         },
     *         {
     *             "id": "3310000000",// 机构编码
     *             "name": "杭州分行",
     *             "pid": "A0001",// 父机构编码
     *             "type": "org"
     *         },
     *         {
     *             "id": "A0001",
     *             "name": "总行",
     *             "pid": "#org",
     *             "type": "org"
     *         },
     *         {
     *             "id": "3310100000",
     *             "name": "杭州城北支行",
     *             "pid": "3310000000",
     *             "type": "org"
     *         },
     *         {
     *             "id": "wangning",
     *             "name": "王宁",
     *             "pid": "3310000000",
     *             "type": "user"
     *         },
     *         {
     *             "id": "wangwu",
     *             "name": "王五",
     *             "pid": "A0001",
     *             "type": "user"
     *         },
     *         {
     *             "id": "liming",
     *             "name": "李明",
     *             "pid": "3310100000",
     *             "type": "user"
     *         },
     *         {
     *             "id": "zhangsan",
     *             "name": "张三",
     *             "pid": "3310000000",
     *             "type": "user"
     *         },
     *         {
     *             "id": "admin",
     *             "name": "反欺诈",
     *             "pid": "A0001",
     *             "type": "user"
     *         },
     *         {
     *             "id": "fxq01",
     *             "name": "反洗钱人员01",
     *             "pid": null,
     *             "type": "user"
     *         },
     *         {
     *             "id": "#role",
     *             "name": "角色",
     *             "pid": "",
     *             "type": ""
     *         },
     *         {
     *             "id": "RL868148006593052672",
     *             "name": "总行质量管理员",
     *             "pid": "TENA862391216766664704",
     *             "type": "role"
     *         },
     *         {
     *             "id": "RL868907588294602752",
     *             "name": "业务人员",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "TENA862391216766664704",
     *             "name": "反欺诈管理员",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R7000000",
     *             "name": "分行行长",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R3000001",
     *             "name": "部门经理",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R5000000",
     *             "name": "宁波地区一级支行行长",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R9000005",
     *             "name": "总行反洗钱中心人员",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R9000000",
     *             "name": "总行质量管理员",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R7000001",
     *             "name": "分行质量管理员",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "R5000001",
     *             "name": "宁波地区一级支行质量管理员",
     *             "pid": "#role",
     *             "type": "role"
     *         },
     *         {
     *             "id": "wangwu",
     *             "name": "王五",
     *             "pid": "R3000001",
     *             "type": "user"
     *         },
     *         {
     *             "id": "zhangsan",
     *             "name": "张三",
     *             "pid": "RL868907588294602752",
     *             "type": "user"
     *         },
     *         {
     *             "id": "liming",
     *             "name": "李明",
     *             "pid": "R7000000",
     *             "type": "user"
     *         },
     *         {
     *             "id": "wangning",
     *             "name": "王宁",
     *             "pid": "R5000000",
     *             "type": "user"
     *         },
     *         {
     *             "id": "fxq01",
     *             "name": "反洗钱人员01",
     *             "pid": "R9000005",
     *             "type": "user"
     *         },
     *         {
     *             "id": "admin",
     *             "name": "反欺诈",
     *             "pid": "TENA862391216766664704",
     *             "type": "user"
     *         },
     *         {
     *             "id": "zhangsan",
     *             "name": "张三",
     *             "pid": "R3000001",
     *             "type": "user"
     *         }
     *     ]
     * }
     * @param editorInfo
     * @return
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
     * 返回例子：
     * {
     *     "currentPageIndex": 0,
     *     "hasNextPage": false,
     *     "postInfos": [
     *         {
     *             "type": "user",
     *             "userId": "zhangsan"
     *         },
     *         {
     *             "orgCode": "3310000000",
     *             "type": "org"
     *         },
     *         {
     *             "roleCode": "RL0002",
     *             "type": "role"
     *         },
     *         {
     *             "orgCode": "3310000000",
     *             "roleCode": "RL0002",
     *             "type": "post"
     *         }
     *     ],
     *     "retCode": "0",
     *     "total": 4
     * }
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

                if((StringUtils.isNotBlank(orgCode) && StringUtils.isNotBlank(roleCode)) || (StringUtils.isNotBlank(roleCode) && StringUtils.isNotBlank(deptCode))){
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
                    // 角色机构是空，表示机构
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