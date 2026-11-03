package com.tansun.wf.activiti.service;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.tansun.wf.activiti.util.TransformUtil;
import com.tansun.workflow.model.EditorBean;
import com.tansun.workflow.model.EditorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 流程运行时用户服务类
 *
 */
@Service
public class WorkflowUserService{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${editor.service.translateName:false}")
	private boolean translateName;

	@Autowired
	private EditorServiceProxyService editorServiceProxyService;

    public Set<String> getAllCandidateUsers(EditorInfo editorInfo){
		EditorBean result = editorServiceProxyService.findUsersList(editorInfo);
		Set<String> users = getUsersFromResult(result, "userLoginName");
		logger.info("generate candidate users params is {}, result is {} ",JSON.toJSONString(editorInfo), JSON.toJSONString(users));
        return users;
    }



	/**
	 * 获取角色待办
	 * @param tenantId
	 * @param roleList
	 * @return
	 */
	public Set<String> findUserByRoleList(final String tenantId, final List<String> roleList) {
		EditorInfo editorInfo = new EditorInfo();
		editorInfo.setTenantId(tenantId);
		editorInfo.setRoleCodes(roleList);
		EditorBean result = this.editorServiceProxyService.findUsersList(editorInfo);

		Set<String> users = getUsersFromResult(result, "userLoginName");
		logger.info("findUserByRoleList users is:{}",users);
		return users;
	}

	private Set<String> getUsersFromResult(EditorBean result, final String propertyName) {
		Set<String> users = new HashSet<>();
		if(result != null && result.getUserList() != null){
			Collection<String> outUsers = Collections2.transform(result.getUserList(), new Function<Map<String, Object>, String>() {
				@Override
				public String apply(Map<String, Object> input) {
					return (String) input.get(propertyName);
				}
			});
			users.addAll(outUsers);
		}
		return users;
	}

	/**
	 * 根据userIds（sysUser的Login_name）查询
	 */
	public List<String> findUserEmails(final List<String> userList){
		EditorInfo editorInfo = new EditorInfo();
		editorInfo.setUserLoginNames(userList);
		EditorBean result = this.editorServiceProxyService.findUsersList(editorInfo);

		Set<String> users = getUsersFromResult(result, "email");
		logger.info("findUsersList users is:{}",users);
		return users == null?null:new ArrayList(users);

	}

	/**
	 * 翻译用户中文名
	 * @param userIds
	 * @return Map<userId,userName>
	 */
	public Map<String,Object> transferUserName(Set<String> userIds){
		EditorInfo editorInfo = new EditorInfo();
		editorInfo.setDelInd("1");
		editorInfo.setState("0");
		editorInfo.setUserLoginNames(new ArrayList<String>(userIds));
		Map<String, Object> resultMap = new HashMap<>();
		if(translateName){
			try {
				EditorBean editorBean = this.editorServiceProxyService.findUsersList(editorInfo);
				if(editorBean!=null){
					resultMap = TransformUtil.extract(editorBean.getUserList(), "userLoginName", "userName");
				}
			}catch (Exception e){
				// 翻译的时候接口报错不需要往外抛，不影响客户查询记录
				logger.info(e.getMessage());
			}
		}
		return resultMap;
	}


}
