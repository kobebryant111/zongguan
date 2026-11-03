package com.work.ppm.application.services.systemmanager.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.component.work.auth.sso.WebSsoFeignClient;
import com.work.ppm.application.dao.systemmanager.WeakPasswordMapper;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.dto.systemmanager.LoginDto;
import com.work.ppm.application.model.systemmanager.WeakPassword;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.application.model.systemmanager.ext.CommOperatorInfo;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.IImRoleService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.authentication.ShiroRealm;
import com.work.ppm.commons.constant.FileProperties;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 员工登录
 */
@Slf4j
@Service("commOperatorService")
public class CommOperatorService extends BaseDaoSupper implements ICommOperatorService {
    /**
     * 实体类路径
     */
    private static String COMMOPERATOR = "com.work.ppm.application.model.systemmanager.CommOperator";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 角色接口
     */
    @Autowired
    private IImRoleService imRoleService;
    /**
     * 单点
     */
    @Autowired
    private WebSsoFeignClient webSsoFeignClient;

    /**
     * 文件属性对象
     */
    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private WeakPasswordMapper weakPasswordMapper;

    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Override
    public void updateUserPassword(String userId, String oldPwd, String newPwd, String confirmPwd) {
        // 参数校验
        checkParams(userId, oldPwd, newPwd, confirmPwd);
        // 密码设置与更新
        CommOperator commOperator = new CommOperator();
        commOperator.setUserid(userId);
        CommOperator user = this.getList(commOperator).get(0);
        String passwd =CryptoUtil.digestByMD5(newPwd);
        if (StringUtil.isNotBlank(user.getOldPassword())) {
            AssertUtil.isTrue(!user.getOldPassword().equals(passwd),"新密码与前次密码相同");
        }
        user.setPassword(CryptoUtil.digestByMD5(newPwd));
        user.setOldPassword(CryptoUtil.digestByMD5(oldPwd));
        user.setInvaldate(DateUtil.addOrSubDate(new Date(), 90));
        AssertUtil.isTrue(this.updateCommOperatorCus(user) >0,"用户密码更新失败");
    }

    private void checkParams(String userId, String oldPwd, String newPwd, String confirmPwd) {
        CommOperator commOperator = new CommOperator();
        commOperator.setUserid(userId);
        List<CommOperator> tmpList = this.getList(commOperator);
        AssertUtil.isTrue(StringUtils.isNotBlank(userId)
                &&  null != tmpList
                && tmpList.size() > 0,
                "用户未登录或不存在！");
        AssertUtil.isTrue(StringUtils.isNotBlank(oldPwd), "请输入原始密码");
        AssertUtil.isTrue(StringUtils.isNotBlank(newPwd), "请输入新密码");
        AssertUtil.isTrue(StringUtils.isNotBlank(confirmPwd), "请输入确认密码");
        AssertUtil.isTrue(tmpList.get(0).getPassword().equals(CryptoUtil.digestByMD5(oldPwd)),"原始密码不正确");
        AssertUtil.isTrue(newPwd.equals(confirmPwd),"新密码输入不一致");
        AssertUtil.isTrue(!oldPwd.equals(newPwd),"新密码与原始密码不能相同");
        final List<WeakPassword> weakPassword = this.getWeakPassword(newPwd);
        AssertUtil.isTrue(CollectionUtils.isEmpty(weakPassword), "新密码为弱口令，请更换设置为复杂的密码");
    }

    private List<WeakPassword> getWeakPassword(String newPwd) {
        LambdaUpdateWrapper<WeakPassword> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(WeakPassword::getWeakPassword, newPwd);
        return weakPasswordMapper.selectList(updateWrapper);
    }

    @Override
    public List<CommOperator> getList(CommOperator commOperator) {
        return myBatisDao.selectListT(COMMOPERATOR, commOperator, "getList");
    }

    @Override
    public void queryPager(CommOperator commOperator, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commOperator, CommOperator.class);
        myBatisDao.selectPager(COMMOPERATOR, map, pager, "queryPagerList");
    }

    @Override
    public int addCommOperator(CommOperator commOperator) {
        myBatisDao.executionPojo(COMMOPERATOR, commOperator, IMyBatisDao.SQL.INSTER, "addPojo");
        return commOperator.getOperatorid().intValue();
    }

    @Override
    public CommOperator queryDetail(CommOperator commOperator) {
        return myBatisDao.selectClass(COMMOPERATOR, commOperator, "queryDetail");
    }

    @Override
    public Integer updateCommOperator(CommOperator commOperator) {
        return myBatisDao.executionPojo(COMMOPERATOR, commOperator, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer updateCommOperatorCus(CommOperator commOperator) {
        return myBatisDao.executionPojo(COMMOPERATOR, commOperator, IMyBatisDao.SQL.UPDATE, "updatePojoCus");
    }
    @Override
    public String getSecretKey(String userid,String ip){
        log.warn("CommOperatorService：：ip:{},userid:{}",ip,userid);
        String secertKey="";
        //ip置为空
        ip="";
        if(StringUtil.isNotEmpty(userid)){
            String dateStr= DateUtil.dateToStr(new Date(),"yyyyMMdd");
            String prefix="techppm"+ip+dateStr;
            //设置加密后的工号
            secertKey= CryptoUtil.digestByMD5(prefix+userid);
        }
        return secertKey;
    }


    @Override
    public CommOperatorDoLoginDto doLogin(CommOperator commOperator) {
        CommOperatorInfo item = myBatisDao.selectClass(COMMOPERATOR, commOperator, "doLogin");

        if (null != item) {
            CommOperatorDoLoginDto itemDto = JsonUtil.jsonToClass(item, CommOperatorDoLoginDto.class);
            //获取ip地址
            Subject subject = SecurityUtils.getSubject();
            String ip=subject.getSession().getHost();
            //工号加密
            String secertKey=getSecretKey(item.getUserid(),ip);
            itemDto.setSecretKey(secertKey);
            // 处理头像
            String avatar = "/ppmtest" + item.getStockpath();
            if(StringUtil.isNotBlank(item.getDocdomain())) {
                avatar += item.getDocdomain();
            } else {
                avatar += item.getDocname();
            }
            itemDto.setAvatar(fileProperties.getHost() + avatar);
            //查询所有的权限码 funcs.add("aaaa");
            List<String> funcs = queryOperaAuthEncoding(item.getUserid());
            itemDto.setFuncs(funcs);
            List<ImRoleDoLoginDto> roleDto = imRoleService.getImRole(itemDto.getUserid());
            itemDto.setRoles(roleDto);
            return itemDto;
        }
        return null;
    }

    private List<String> queryOperaAuthEncoding(String userId) {
        List list = commEmployeeService.queryOperaAuthEncoding(userId);
        return list;
    }
    @Override
    public void updateLastlogin(String username){
        final CommOperator commOperator = new CommOperator();
        commOperator.setUserid(username);
        commOperator.setLastlogin(new Date());
        myBatisDao.executionPojo(COMMOPERATOR, commOperator, IMyBatisDao.SQL.UPDATE, "updateLastlogin");
    }
    @Override
    public ApiResult singleLogin(LoginDto loginPojo) {
        ApiResult result = new ApiResult();
        // 验证 MD5
        StringBuilder sb = new StringBuilder();
        String newMd5 = "";
        if(!StringUtil.isNotBlank(loginPojo.getLoginType())
            && !"2".equals(loginPojo.getLoginType())) {

            sb.append(loginPojo.getUserId()).append(":").append(loginPojo.getClientIP()).append(":").append(DateUtil.dateToNoBar(new Date()));

            log.warn("[Token]失效：：userId:{},ip:{},date:{},本地Token:{},远程Token:{}",
                    loginPojo.getUserId(), loginPojo.getClientIP(), DateUtil.dateToNoBar(new Date()), newMd5, loginPojo.getToken());

        } else {    // 永远有效
            sb.append("userId:" + loginPojo.getUserId());
        }
        newMd5 = CryptoUtil.encoderByMd5(sb.toString());
        if (!newMd5.equals(loginPojo.getToken())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("[Token]失效");
            return result;
        }
        String userId = loginPojo.getUserId();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userId, ShiroRealm.SINGLE_TOKE);
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    @Override
    public String getNextRoleUser(String roleid) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleid", roleid);
        List<CommOperator> item = myBatisDao.selectClass(COMMOPERATOR, map, "getNextRoleUser");
        int size = item.size();
        StringBuilder sb = new StringBuilder(size);
        if (null != item && !item.isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (size == i + 1) {
                    sb.append(item.get(i).getUserid());
                } else {
                    sb.append(item.get(i).getUserid()).append(",");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String getNextRoleUser(String roleid, int type) {
        if(!StringUtil.isNotBlank(roleid)) {
            return "";
        }
        String[] roleids = roleid.split(",");
        StringJoiner joiner = new StringJoiner("',","'","'");
        for (int i = roleids.length - 1; i >= 0; i--) {
            joiner.add(roleids[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("roleid", joiner.toString());
        List<CommOperator> list = myBatisDao.selectClass(COMMOPERATOR, map, "getNextRoleUser");
        if (null == list || list.isEmpty()) {
            return "";
        }
        int size = list.size();
        StringBuilder sb = new StringBuilder(size);
        if (1 == type) {
            for (int i = 0; i < size; i++) {
                if (size == i + 1) {
                    sb.append(list.get(i).getOperatorname()).append("(").append(list.get(i).getUserid()).append(")");
                } else {
                    sb.append(list.get(i).getOperatorname()).append("(").append(list.get(i).getUserid()).append("),");
                }
            }
        } else if (2 == type) {
            for (int i = 0; i < size; i++) {
                if (size == i + 1) {
                    sb.append(list.get(i).getUserid());
                } else {
                    sb.append(list.get(i).getUserid()).append(",");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String getRoleUserFormat(String roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleid", roleId);
        List<CommOperator> list = myBatisDao.selectClass(COMMOPERATOR, map, "getNextRoleUser");
        if (null == list || list.isEmpty()) {
            return "";
        }
        StringBuffer ids = new StringBuffer(list.size());
        StringBuffer names = new StringBuffer(list.size());
        list.stream().forEach(item -> {
            ids.append(item.getUserid()).append("|");
            names.append(item.getUserid()).append("|");
        });
        String userIdStr = ids.toString().substring(0, ids.toString().lastIndexOf("|"));
        String userNameStr = names.toString().substring(0, names.toString().lastIndexOf("|"));

        return userIdStr + "@" +userNameStr;
    }

    @Override
    public String getUserNameByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        CommOperator item = myBatisDao.selectClass(COMMOPERATOR, map, "getUserNameByUserId");
        if (null != item) {
            return item.getOperatorname();
        }
        return null;
    }

    @Override
    public String getUserIdByRoleId(String roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        List<CommOperator> list = myBatisDao.selectClass(COMMOPERATOR, map, "getNextRoleUser");
        if (null == list || list.isEmpty()) {
            return "";
        }
        StringBuffer ids = new StringBuffer(list.size());
        list.stream().forEach(item -> {
            ids.append(item.getUserid()).append(",");
        });
        String userIdStr = ids.toString().substring(0, ids.toString().lastIndexOf(","));

        return userIdStr;
    }

    @Override
    public String getUserNameByRoleId(String roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        List<CommOperator> list = myBatisDao.selectClass(COMMOPERATOR, map, "getNextRoleUser");
        if (null == list || list.isEmpty()) {
            return "";
        }
        StringBuffer names = new StringBuffer(list.size());
        list.stream().forEach(item -> {
            names.append(item.getUserid()).append(",");
        });
        String userNameStr = names.toString().substring(0, names.toString().lastIndexOf(","));

        return userNameStr;
    }
}
