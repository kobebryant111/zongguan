package com.work.ppm.application.services.rdmanager.impl;

import java.security.KeyPair;
import java.security.SecureRandom;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.work.ppm.application.dao.systemmanager.WeakPasswordMapper;
import com.work.ppm.application.dto.esb.req.CommInfArray;
import com.work.ppm.application.dto.esb.req.IssuInfArray;
import com.work.ppm.application.dto.esb.req.PpmKqOutsourcingDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.rdmanager.CommSysPwDto;
import com.work.ppm.application.model.rdmanager.SystemListDto;
import com.work.ppm.application.model.systemmanager.CommOperator;
import com.work.ppm.application.model.systemmanager.WeakPassword;
import com.work.ppm.application.services.external.CallEsbService;
import com.work.ppm.application.services.rdmanager.IPpmOperatorKeyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.SmsContext;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.EdchUtil;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.Sm3Util;
import com.work.ppm.commons.utils.Sm4KeyDerivation;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import edu.emory.mathcs.backport.java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PpmOperatorKeyService extends BaseApi implements IPpmOperatorKeyService {

    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private WeakPasswordMapper weakPasswordMapper;

    private final CallEsbService esbService;

    @Value("${amterm.verificationPubId}")
    private String pubId;
    // 定义包含字母和数字的字符集
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String SMS_SESSION_KEY = "sms_context";

//    private static KeyPair serverKeyPair;
//
//    private static byte[] aesKey;
//
//    static {
//        try {
//            serverKeyPair = EdchUtil.generateKeyPair();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new BizRuntimeException(e.getMessage());
//        }
//    }
    /**
     * 实体类路径
     */
    private static String SYSTEMLISTDTO = "com.work.ppm.application.model.rdmanager.SystemListDto";

    @Override
    public void queryPager(Pager pager, CommOperatorDoLoginDto user) {
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(user, CommOperatorDoLoginDto.class);
        myBatisDao.selectPager(SYSTEMLISTDTO, commRdEmployeeMap, pager, "queryPager");

    }

    @Override
    public void queryAllPager(Pager pager, SystemListDto systemListDto) {
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(systemListDto, SystemListDto.class);
        myBatisDao.selectPager(SYSTEMLISTDTO, commRdEmployeeMap, pager, "queryAllPager");
    }

    @Override
    public void resetUserScodPwd(SystemListDto systemListDto, HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (!"sysadmin".equals(user.getUserid())) {
            throw new BizRuntimeException("请联系管理员重置密码");
        }
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(systemListDto, SystemListDto.class);
        myBatisDao.executionMap(SYSTEMLISTDTO, commRdEmployeeMap, IMyBatisDao.SQL.UPDATE, "resetUserScodPwd");
    }

    @Override
    public void clossverifyCode(HttpServletRequest request) {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        if (!"sysadmin".equals(user.getUserid())) {
            throw new BizRuntimeException("请联系管理员重置密码");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userid", user.getUserid());
        myBatisDao.executionMap(SYSTEMLISTDTO, map, IMyBatisDao.SQL.UPDATE, "clossverifyCode");
    }

    @Override
    public Object selectverifyCode(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        return myBatisDao.selectObject(SYSTEMLISTDTO, map, "selectverifyCode");
    }

    @Override
    public CommOperator firstEnter(CommOperatorDoLoginDto user) {
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(user, CommOperatorDoLoginDto.class);
        return myBatisDao.selectClass(SYSTEMLISTDTO, commRdEmployeeMap, "firstEnter");
    }

    @Override
    public void setPassword(HttpServletRequest request, String verificationCode, String confirmPassWord) {
        HttpSession session = request.getSession(false);
        verifyCode(session, verificationCode);

        final List<WeakPassword> weakPassword = this.getWeakPassword(confirmPassWord);
        AssertUtil.isTrue(CollectionUtils.isEmpty(weakPassword), "新密码为弱口令，请更换设置为复杂的密码");
        String hex = Sm3Util.sm3(confirmPassWord);

        CommOperator commOperator = new CommOperator();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        commOperator.setUserid(user.getUserid());
        commOperator.setOperatorname(user.getOperatorname());
        commOperator.setPassword(hex);
        commOperator.setStatus("running");
        commOperator.setInvaldate(DateUtil.addOrSubDate(new Date(), 90));
        Map<String, Object> map = StringUtil.objectTOMap(commOperator, CommOperator.class);
        myBatisDao.executionMap(SYSTEMLISTDTO, map, IMyBatisDao.SQL.INSTER, "setPassword");
        // 校验成功
        session.removeAttribute(SMS_SESSION_KEY);

    }


    /**
     * 弱口令检查
     *
     * @param newPwd
     * @return
     */
    private List<WeakPassword> getWeakPassword(String newPwd) {
        LambdaUpdateWrapper<WeakPassword> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(WeakPassword::getWeakPassword, newPwd);
        return weakPasswordMapper.selectList(updateWrapper);
    }

    /**
     * 验证码检查
     *
     * @param session
     * @param verificationCode
     * @return
     */
    private void verifyCode(HttpSession session, String verificationCode) {
        Map<String, Object> map = new HashMap<>();
        String verifyCode = myBatisDao.selectClass(SYSTEMLISTDTO, map, "selectverifyCode");

        if ("Y".equals(verifyCode)) {
            if (!StringUtil.isNotBlank(verificationCode)) {
                throw new BizRuntimeException("参数不能为空");
            }
        } else {
            return;
        }

        SmsContext context = null;
        if (session == null) {
            throw new BizRuntimeException("请先获取验证码");
        }
        context = (SmsContext) session.getAttribute(SMS_SESSION_KEY);
        if (context == null) {
            throw new BizRuntimeException("请先获取验证码");
        }
        if (context.isExceeded()) {
            session.removeAttribute(SMS_SESSION_KEY);
            throw new BizRuntimeException("尝试次数过多，请重新获取验证码");
        }
        if (context.isExpired()) {
            session.removeAttribute(SMS_SESSION_KEY);
            throw new BizRuntimeException("验证码已过期");
        }
        if (!context.getCode().equalsIgnoreCase(verificationCode)) {
            context.incrementAttempts();
            throw new BizRuntimeException("密码或验证码错误");
        }
    }


    @Override
    public void verifyCodeAndPassword(HttpServletRequest request, String verificationCode, String passWord) {
        if (!(StringUtil.isNotBlank(passWord))) {
            throw new BizRuntimeException("参数不能为空");
        }
        HttpSession session = request.getSession(false);
        verifyCode(session, verificationCode);

        String hex = Sm3Util.sm3(passWord);
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        CommOperator commOperator = myBatisDao.selectClass(SYSTEMLISTDTO, user.getUserid(), "verifyCodeAndPassword");
        try {
            AssertUtil.isTrue(hex.equals(commOperator.getPassword()), "密码或验证码错误");
        } catch (Exception e) {
            SmsContext context = (SmsContext) session.getAttribute(SMS_SESSION_KEY);
            context.incrementAttempts();
            throw new BizRuntimeException(e.getMessage());
        }
        // 校验成功
        session.removeAttribute(SMS_SESSION_KEY);
    }

    @Override
    public void changePassword(String verificationCode, String confirmPassword, String newPassword, String password, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            AssertUtil.isTrue(StringUtil.isNotBlank(confirmPassword)
                    && StringUtil.isNotBlank(newPassword) && StringUtil.isNotBlank(password), "参数不能为空");
            AssertUtil.isTrue(confirmPassword.equals(newPassword), "两次输入的密码不一致");

            // 验证码校验
            verifyCode(session, verificationCode);
            // 弱口令校验
            final List<WeakPassword> weakPassword = this.getWeakPassword(confirmPassword);
            AssertUtil.isTrue(CollectionUtils.isEmpty(weakPassword), "新密码为弱口令，请更换设置为复杂的密码");
            // 密码校验
            CommOperatorDoLoginDto user = super.getCurrentUser(request);
            CommOperator commOperator = myBatisDao.selectClass(SYSTEMLISTDTO, user.getUserid(), "verifyCodeAndPassword");
            AssertUtil.isTrue(commOperator.getPassword().equals(Sm3Util.sm3(password)), "密码或验证码错误");
            String pwd = Sm3Util.sm3(newPassword);
            AssertUtil.isTrue(!password.equals(newPassword), "新密码与原始密码不能相同");
            if (StringUtil.isNotBlank(commOperator.getOldPassword())) {
                AssertUtil.isTrue(!commOperator.getOldPassword().equals(pwd), "新密码与前次密码相同");
            }
            commOperator.setOldPassword(commOperator.getOldPassword());
            commOperator.setPassword(pwd);
            commOperator.setInvaldate(DateUtil.addOrSubDate(new Date(), 90));
            Map<String, Object> map = StringUtil.objectTOMap(commOperator, CommOperator.class);
            AssertUtil.isTrue(this.updatePwd(map) > 0, "用户密码更新失败");
            // 校验成功
            session.removeAttribute(SMS_SESSION_KEY);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (session != null) {
                SmsContext context = (SmsContext) session.getAttribute(SMS_SESSION_KEY);
                if (context != null) {
                    context.incrementAttempts();
                }
            }
            throw new BizRuntimeException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> ecdhefirst(Map<String, String> body, HttpServletRequest request) throws Exception {
        String clientPubKeyBase64 = body.get("clientPubKey");
//        CommOperatorDoLoginDto user = super.getCurrentUser(request);
//        body.put("userid", user.getUserid());
        List<CommSysPwDto> sysPwDtos = myBatisDao.selectListT(SYSTEMLISTDTO, body, "ecdhefirst");
        HttpSession session = request.getSession();
        // ECDHE 密钥对生成 最终密钥计算
        KeyPair serverKeyPair = EdchUtil.generateKeyPair();
        byte[] aesKey = EdchUtil.deriveAESKey(serverKeyPair.getPrivate(), clientPubKeyBase64);
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("aesKey", aesKey);

//        Map<String, Object> keyMap = EdchUtil.generateClientKey(clientPubKeyBase64, request);
        for (CommSysPwDto sysPwDto : sysPwDtos) {
            int year = sysPwDto.getValidtime();
            // 判断密码是否生效

            String encrypte = sysPwDto.getPsdKey();
            // 主密钥
            byte[] workingKey = Sm4KeyDerivation.loadMasterKey(year);
            // sm4生产密钥
            String encrypteBase64 = Sm4KeyDerivation.decrypt(encrypte, workingKey);
            byte[] Key = Sm4KeyDerivation.getWorkingKey(encrypteBase64, year);
            // sm4解密
            String sm4Psd = Sm4KeyDerivation.decrypt(sysPwDto.getPsd(), Key);
            // Edch加密
            Map<String, String> ivCipherMap = EdchUtil.encryption((byte[]) keyMap.get("aesKey"), sm4Psd);
            sysPwDto.setPsd(ivCipherMap.get("cipher"));
            sysPwDto.setIv(ivCipherMap.get("iv"));
        }
        session.removeAttribute("ECDH_AESKEY");
        Map<String, Object> map = new HashMap<>();
//        map.put("serverPubKeyEncodedBase", keyMap.get("serverPubKeyEncodedBase"));
        map.put("serverPubKeyEncodedBase", EdchUtil.toHexPublicKey(serverKeyPair.getPublic()));
        map.put("sysPwDtos", sysPwDtos);
        return map;
    }

    @Override
    public void deleteSysList(ArrayList<String> body) {
        if (!CollectionUtils.isEmpty(body)) {
            myBatisDao.executionPojo(SYSTEMLISTDTO, body, IMyBatisDao.SQL.DELETE, "deleteSysList");
            myBatisDao.executionPojo(SYSTEMLISTDTO, body, IMyBatisDao.SQL.DELETE, "deleteSytpsdKey");
        }
    }

    @Override
    public Object ecdheAddfirst(Map<String, String> body, HttpServletRequest request) {
        String clientPubKeyBase64 = body.get("clientPubKey");
        String keyMap = null;
        try {
            KeyPair serverKeyPair = EdchUtil.generateKeyPair();
            byte[] aesKey = EdchUtil.deriveAESKey(serverKeyPair.getPrivate(), clientPubKeyBase64);
            request.getSession().setAttribute("ECDH_AESKEY", aesKey);
            keyMap = EdchUtil.toHexPublicKey(serverKeyPair.getPublic());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizException(ApiCode.FAIL, "请重新登录二次密码！");
        }
        return keyMap;
    }

    @Override
    @Transactional
    public void addSysPsd(Map<String, String> body, HttpServletRequest request) throws Exception {
        // 解密
//        try {
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        // 解密

        String json = EdchUtil.decrypt(request, body.get("cipher"), body.get("iv"), body.get("tag"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        map.put("id", UUIDUtils.generateUUID());
        map.put("responsibleName", user.getOperatorname());
        map.put("responsibleJobid", user.getUserid());
        map.put("invaldate", DateUtil.addOrSubDate(new Date(), 90));
        String psd = (String) map.get("psd");
        if (psd.length() > 128) {
            throw new BizRuntimeException("密码长度超过128位");
        }
        // SM4 加密
        getEncrypteBase64(map, LocalDate.now().getYear());
        map.put("validtime", LocalDate.now().getYear());
        myBatisDao.executionPojo(SYSTEMLISTDTO, map, IMyBatisDao.SQL.INSTER, "addSysPsd");
    }

    private void getEncrypteBase64(Map<String, Object> map, int year) throws Exception {
        // 生产密钥
        String bytesKey = Sm4KeyDerivation.generateRandomSm4Key();
        byte[] keyBase64 = Sm4KeyDerivation.getWorkingKey(bytesKey, year);
        // psd加密 encrypteBase64 = 密码的密文 + iv
        String encrypteBase64 = Sm4KeyDerivation.deriveSm4Key((String) map.get("psd"), keyBase64);
        map.put("psd", encrypteBase64);

        byte[] workingKey = Sm4KeyDerivation.loadMasterKey(year);
        // 对密钥加密
        // psdKey = 密钥的密文 + iv
        String psdKey = Sm4KeyDerivation.deriveSm4Key(bytesKey, workingKey);
        map.put("psdKey", psdKey);
        myBatisDao.executionPojo(SYSTEMLISTDTO, map, IMyBatisDao.SQL.INSTER, "addPsdKey");

    }

    @Override
    public void updataSysPsd(Map<String, String> body, HttpServletRequest request) throws Exception {
        String json = EdchUtil.decrypt(request, body.get("cipher"), body.get("iv"), body.get("tag"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        map.put("invaldate", DateUtil.addOrSubDate(new Date(), 90));
        CommSysPwDto commSysPwDto = myBatisDao.selectClass(SYSTEMLISTDTO, map, "sm4SysPsd");

        byte[] workingKey = Sm4KeyDerivation.loadMasterKey(commSysPwDto.getValidtime());
        // 解密 生产密钥
        String encrypteBase64 = Sm4KeyDerivation.decrypt(commSysPwDto.getPsdKey(), workingKey);
        byte[] Key = Sm4KeyDerivation.getWorkingKey(encrypteBase64, commSysPwDto.getValidtime());
        // psd密码明文
        String psd = (String) map.get("psd");
        if (psd.length() > 128) {
            throw new BizRuntimeException("密码长度超过128位");
        }
        // psd加密 unencrypted = 密码的密文 + iv
        String unencrypted = Sm4KeyDerivation.deriveSm4Key(psd, Key);
        map.put("psd", unencrypted);
        myBatisDao.executionMap(SYSTEMLISTDTO, map, IMyBatisDao.SQL.UPDATE, "updataSysPsd");

    }

    @Override
    public ApiResult<Object> getCode(HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        HttpSession session = request.getSession();
        LocalDateTime now = LocalDateTime.now();
        SmsContext context = (SmsContext) session.getAttribute(SMS_SESSION_KEY);
        if (context != null) {
            LocalDateTime lastSendTime = context.getLastSendTime();
            if (lastSendTime != null) {
                long secondsPass = Duration.between(lastSendTime, now).getSeconds();
                if (secondsPass < 60) {
                    result.setStatus(ApiCode.SUCCESS);
                    result.setMessage("验证码发送过于频繁");
                    return result;
                }
            }
        }
        CommOperator commOperator = new CommOperator();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        String code = getVerification(user.getUserid());

        context = new SmsContext(code, 5, now);
        session.setAttribute(SMS_SESSION_KEY, context);
        session.setMaxInactiveInterval(330);
        result.setMessage("验证码已发送");
        return result;
    }

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    private String getVerification(String userid) {
        String code = generateRandomString();

        PpmKqOutsourcingDto empList = new PpmKqOutsourcingDto();
        List<CommInfArray> badges = new ArrayList<>();
        CommInfArray commInfArray = new CommInfArray();
        commInfArray.setEmpNo(userid);
        badges.add(commInfArray);

        IssuInfArray issuInfArray = new IssuInfArray();
        issuInfArray.setList(badges);

        List<IssuInfArray> badgess = new ArrayList<>();
        badgess.add(issuInfArray);
        empList.setList(badgess);
        empList.setID(pubId);
        empList.setSndFlg(false);
        empList.setInfTp("2");
        empList.setRcrdtxt1("验证码为" + code);
        empList.setSvcNm("mobileoa-bnwp-sidecar");
        empList.setUrl("/MobileOaMercury/yunproxy/sendMessage");
        empList.setSendTp("POST");
        empList.setCmdbSysName("cfit");
        empList.setBankSysIP(IpUtil.getLocalIp());
        empList.setPsThgFlg("true");
        empList.setHDBnkInrSysNm("mobileoa");
        empList.setHDBnkOutSysIp(IpUtil.getLocalIp());
        try {
            esbService.sendMessage(empList);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("工作平台消息通知异常");
        }
        return code;
    }

    private Integer updatePwd(Map<String, Object> map) {
        return myBatisDao.executionMap(SYSTEMLISTDTO, map, IMyBatisDao.SQL.UPDATE, "changePassword");
    }
}
