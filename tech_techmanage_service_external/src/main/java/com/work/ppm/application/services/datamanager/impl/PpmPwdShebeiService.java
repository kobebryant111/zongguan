package com.work.ppm.application.services.datamanager.impl;

import com.isprint.am.xmlrpc.clientcore.AuthResultTO;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiConfigureDto;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.dto.systemmanager.PpmFhpwdXqDto;
import com.work.ppm.application.model.datamanager.PpmPwdShebei;
import com.work.ppm.application.model.datamanager.PpmPwdShebeiConfigure;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiConfigureService;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiService;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmFhpwdXqService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.other.UCMGetPasswordClient;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
  * 密管设备
  */
@Service("ppmPwdShebeiService")
public class PpmPwdShebeiService extends BaseDaoSupper implements IPpmPwdShebeiService {
    /**
     * 实体类路径
     */
    private static String PPMPWDSHEBEI = "com.work.ppm.application.model.datamanager.PpmPwdShebei";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 分行密码权限
     */
    @Autowired
    private IPpmFhpwdXqService ppmFhpwdXqService;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 机构
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;
    /**
     * 密管设备
     */
    @Autowired
    private IPpmPwdShebeiService ppmPwdShebeiService;
    /**
     * 密管设备配置接口
     */
    @Autowired
    private IPpmPwdShebeiConfigureService ppmPwdShebeiConfigureService;
    /**
     * 系统接口
     */
    @Autowired
    private IAapSystemService aapSystemService;


    @Override
    public void queryPager(PpmPwdShebeiDto ppmPwdShebeiDto, Pager pager) {
        CommEmployee employeeDto = UsersUtil.getCommEmployeeInfo(ppmPwdShebeiDto.getApplyer());
        CommOrganizationDto org= commOrganizationService.queryFHIdforUserId(ppmPwdShebeiDto.getApplyer());
        // //当前人员所在机构不是总行和XX银行
        if(!"9999999999".equals(org.getCompcode())
                && !"9000000000".equals(org.getCompcode())) {
            CommOrganizationDto organizationDto = commOrganizationService.queryDateil(org.getCompid());
            // 密管用户密码
            List<EosDictEntry> eoslist = eosDictEntryService.getTypeidForList("PWD_LOGIN_USER");
            String user = eoslist.get(0).getDictid();
            String paswd = eoslist.get(0).getDictname();
            PpmFhpwdXqDto xqDto = ppmFhpwdXqService.queryForCompid(org.getCompcode());
            String rootqx = "";
            String ywqx = "";
            if(null != xqDto) {
                rootqx = "," + xqDto.getAdmindepid() + ",";
                ywqx = "," +xqDto.getDepid() + ",";
            }
            // 密管登录
            AuthResultTO auth = UCMGetPasswordClient.login(user,paswd);
            String status = "";
            // 判断当前人员是否是“root权限组”
            if(rootqx.indexOf("," + employeeDto.getDepid() + ",") != -1) {
                PpmPwdShebeiConfigureDto configureDto = new PpmPwdShebeiConfigureDto();
                configureDto.setDepid(organizationDto.getLocation());
                ppmPwdShebeiConfigureService.queryPager(configureDto,pager);
                List<PpmPwdShebeiConfigure> sbclist1 =
                        (List<PpmPwdShebeiConfigure>)pager.getList();
                List<PpmPwdShebeiConfigureDto> sbclist = JsonUtil.jsonToList(sbclist1,PpmPwdShebeiConfigureDto.class);
                if(null == sbclist || sbclist.isEmpty()) {
                    return ;
                }

                for (int i = 0; i < sbclist.size(); i++) {
                    PpmPwdShebeiConfigureDto tt = sbclist.get(i);
                    if(!StringUtil.isNotBlank(tt.getIp())) {
                        status = UCMGetPasswordClient.getStatus(auth,tt.getUserid());
                    } else {
                        status = UCMGetPasswordClient.getStatus(auth,tt.getUserid() + "@" + tt.getIp());
                    }
                    if("AVAILABLE_FOR_CHECKOUT".equals(status)) {
                        status="可供检出";

                    } else if ("CHECKED_OUT".equals(status)) {
                        status = "已检出";
                        // 设置属性
                        setApplyerPro(tt);
                    } else if ("PENDING_RESET".equals(status)) {
                        status = "待重置";
                    } else {
                        status = "用户不存在";
                    }
                    tt.setStatus(status);
                }
                pager.setList(sbclist);
                //判断当前人所在部门是否是“运维权限组”
            } else if(ywqx.indexOf("," + employeeDto.getDepid() + ",") != -1) {
                /*
                 * 对于非白名单系统，申请人所属部门不是系统管理部，查询可见只包括本部门系统的应用运维用户和数据库用户。
                 * 20181025 去除白名单概念,非核心系统
                 */
                PpmPwdShebeiConfigureDto conf = new PpmPwdShebeiConfigureDto();
                conf.setDepid(org.getCompcode());
                // 系统名称不为空
                if(StringUtil.isNotBlank(ppmPwdShebeiDto.getSystemname())) {
                    String[] sys = ppmPwdShebeiDto.getSystemname().split(",");
                    StringJoiner joiner = new StringJoiner("','","'","'");
                    for (int i = sys.length - 1; i >= 0; i--) {
                        joiner.add(sys[i]);
                    }
                    conf.setSystemid(joiner.toString());
                }
                conf.setIp(ppmPwdShebeiDto.getIp());
                conf.setApplyer(ppmPwdShebeiDto.getApplyer());
                conf.setUsertype(ppmPwdShebeiDto.getUsertype());
                conf.setUserid(ppmPwdShebeiDto.getUserid());
                List<PpmPwdShebeiConfigureDto> clist =
                        ppmPwdShebeiConfigureService.queryList(conf);
                if(null == clist || clist.isEmpty()) {
                    return;
                }
                List<PpmPwdShebeiConfigureDto> res = new ArrayList<>();
                // sonar 修改
                sonarFor(res,clist,employeeDto);
                pager.setTotal(res.size());
                // 分页
                List<PpmPwdShebeiConfigureDto> pagers =
                        res.stream().skip(pager.getSize() * (pager.getIndex() - 1))
                                .limit(pager.getSize())
                                .collect(Collectors.toList());
                for (int i = pagers.size() - 1; i >= 0; i--) {
                    PpmPwdShebeiConfigureDto tt = pagers.get(i);
                    // sonar 修改
                    status = sonarFor(tt, auth);
                    tt.setStatus(status);
                }       // for end
                pager.setList(pagers);
            }
        }

    }

    /**
     * sonarfor
     * @param tt 对象
     * @param auth 对象
     * @return string
     */
    private String sonarFor(PpmPwdShebeiConfigureDto tt,
                          AuthResultTO auth) {
        String status;
        if (!StringUtil.isNotBlank(tt.getIp())) {
            status = UCMGetPasswordClient.getStatus(auth, tt.getUserid());
        } else {
            status = UCMGetPasswordClient.getStatus(auth, tt.getUserid() + "@" + tt.getIp());
        }
        if ("AVAILABLE_FOR_CHECKOUT".equals(status)) {
            status = "可供检出";

        } else if ("CHECKED_OUT".equals(status)) {
            status = "已检出";
            PpmPwdShebeiDto shebeiDtoTemp = this.getApplyerForShebei(tt.getIp(), tt.getUserid());
            if (null != shebeiDtoTemp) {
                tt.setApplyer(shebeiDtoTemp.getApplyer());
            }
        } else if ("PENDING_RESET".equals(status)) {
            status = "待重置";
        } else {
            status = "用户不存在";
        }
        return status;
    }

    /**
     * sonar修改
     * @param res 集合
     * @param clist 集合
     * @param employeeDto 对象
     */
    private void sonarFor(List<PpmPwdShebeiConfigureDto> res,
                          List<PpmPwdShebeiConfigureDto> clist, CommEmployee employeeDto) {
        for (int i = 0; i < clist.size(); i++) {
            PpmPwdShebeiConfigureDto tt = clist.get(i);
            if ("应用运维用户".equals(tt.getUsertype())) {
                dealPersonal(tt,employeeDto,res);
            } else if ("数据库用户".equals(tt.getUsertype())) {
                res.add(tt);
            } else {    // 管理员用户
                if ("是".equals(tt.getIswhite())) {
                    res.add(tt);
                }
            }
        }   // for end
    }

    /**
     * 处理个人账号
     * @param tt 对象
     * @param employeeDto 对象
     * @param res 集合
     */
    private void dealPersonal(PpmPwdShebeiConfigureDto tt,
                              CommEmployee employeeDto, List<PpmPwdShebeiConfigureDto> res) {
        String user = tt.getUserid();
        if ("个人账号".equals(tt.getSystemid())) {
            if (user.equals("c" + employeeDto.getBadge())
                    || user.equals("C" + employeeDto.getBadge())
                    || "work".equals(user)) {
                res.add(tt);
            }
        } else {
            res.add(tt);
        }
    }

    /**
     * 设置属性
     * @param tt 对象
     */
    private void setApplyerPro(PpmPwdShebeiConfigureDto tt) {
        PpmPwdShebeiDto shebeiDtoTemp = this.getApplyerForShebei(tt.getIp(), tt.getUserid());
        if (null != shebeiDtoTemp) {
            tt.setApplyer(shebeiDtoTemp.getApplyer());
        }
    }

    @Override
    public List<PpmPwdShebeiDto> getList(String instanceid) {
        PpmPwdShebei shebei = new PpmPwdShebei();
        shebei.setInstanceid(instanceid);
        List<PpmPwdShebei> list = myBatisDao.selectListT(PPMPWDSHEBEI, shebei, "getList");
        List<PpmPwdShebeiDto> dtos = JsonUtil.jsonToList(list, PpmPwdShebeiDto.class);
        return dtos;
    }

    @Override
    public int addSheBei(List<PpmPwdShebeiDto> pwdList, String instanceid) {
        if(null == pwdList || pwdList.isEmpty()) {
            return -1;
        }
        // 删除原先得数据
        Map<String, Object> map = new HashMap<>();
        map.put("instanceid", instanceid);
        myBatisDao.executionMap(PPMPWDSHEBEI, map,
                IMyBatisDao.SQL.DELETE, "delForInstanceid");
        pwdList.stream().forEach(ii -> {
            ii.setInstanceid(instanceid);
            long id = getNextSequence(PpmPwdShebei.class);
            ii.setSystemname(ii.getSystemid());
            ii.setId(id);
            ii.setUsername(ii.getUserid());
        });
        myBatisDao.executionPojo(PPMPWDSHEBEI,
                pwdList, IMyBatisDao.SQL.INSTER, "batchAdd");
        return 1;
    }

    @Override
    @Transactional(value = "defaultTx", propagation= Propagation.REQUIRES_NEW,isolation= Isolation.READ_COMMITTED)
    public int addSheBei(PpmPwdShebeiDto ppmPwdShebeiDto) {
        ppmPwdShebeiDto.setStatus("2");
        PpmPwdShebei shebei = JsonUtil.jsonToClass(ppmPwdShebeiDto, PpmPwdShebei.class);
        int row = myBatisDao.executionPojo(PPMPWDSHEBEI,
                shebei, IMyBatisDao.SQL.INSTER, "addPojo");
        return row;
    }

    @Override
    public int deleteShebei(Long id) {
        PpmPwdShebei shebei = new PpmPwdShebei();
        shebei.setId(id);
        myBatisDao.executionPojo(PPMPWDSHEBEI, shebei, IMyBatisDao.SQL.DELETE, "deleteShebeiById");
        return 1;
    }

    @Override
    public int deleteShebei(String instanceid) {
        Map<String,Object> map = new HashMap<>();
        map.put("instanceid", instanceid);
        return myBatisDao.executionMap(PPMPWDSHEBEI, map,
                IMyBatisDao.SQL.DELETE, "delForInstanceid");
    }

    @Override
    public PpmPwdShebeiDto getSheBei(PpmPwdShebeiDto ii) {
        ii.setStatus("-1");
        PpmPwdShebei sb = JsonUtil.jsonToClass(ii, PpmPwdShebei.class);
        PpmPwdShebei item = myBatisDao.selectClass(PPMPWDSHEBEI, sb, "queryDetail");
        PpmPwdShebeiDto dto = JsonUtil.jsonToClass(item, PpmPwdShebeiDto.class);
        return dto;
    }

    @Override
    public int updatePwdShebei(PpmPwdShebeiDto ppmPwdShebeiDto) {
        PpmPwdShebei shebei = JsonUtil.jsonToClass(ppmPwdShebeiDto, PpmPwdShebei.class);
        int row = myBatisDao.executionPojo(PPMPWDSHEBEI, shebei, IMyBatisDao.SQL.UPDATE, "updatePojo");
        return row;
    }

    @Override
    public PpmPwdShebeiDto querySheBeiEndTime(PpmPwdShebeiDto ppmPwdShebeiDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmPwdShebeiDto, PpmPwdShebeiDto.class);
        PpmPwdShebei item = myBatisDao.selectClass(PPMPWDSHEBEI, map, "querySheBeiEndTime");
        PpmPwdShebeiDto dto = JsonUtil.jsonToClass(item, PpmPwdShebeiDto.class);
        return dto;
    }

    @Override
    public List<PpmPwdShebeiDto> getPwdList(PpmPwdShebeiDto ppmPwdShebeiDto) {
        AssertUtil.notNull(ppmPwdShebeiDto.getInstanceid(), "[Instanceid] 不能为空");
        List<PpmPwdShebeiDto> list = this.getList(ppmPwdShebeiDto.getInstanceid());
        list = list.stream().filter(ii -> !"-1".equals(ii.getStatus())).collect(Collectors.toList());
        Date date = new Date();
        AtomicReference<String> key = new AtomicReference<>("");
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("PWD_LOGIN_USER");
        String login = eos.get(3).getDictid();
        String password = eos.get(3).getDictname();
        AuthResultTO result = UCMGetPasswordClient.login(login, password);

        list.stream().forEach(ii -> {
            String status;
            if (StringUtil.isNotBlank(ii.getIp())) {
                key.set(ii.getUsername() + "@" + ii.getIp());
            } else {
                key.set(ii.getUsername());
            }
            if ("数据库用户".equals(ii.getUsertype()) || "数据库查询用户".equals(ii.getUsertype())) {
                PpmPwdShebeiConfigureDto conf = ppmPwdShebeiConfigureService.getSheBei(ii);
                if (null != conf && StringUtil.isNotBlank(conf.getGroupname())) {
                    key.set(conf.getGroupname());
                }
            }
            status = UCMGetPasswordClient.getStatus(result, key.get());
            if ("AVAILABLE_FOR_CHECKOUT".equals(status)) {
                status = "可供检出";
                ii.setPwd("");
                ii.setApplyer("");
            } else if ("CHECKED_OUT".equals(status)) {
                status = "已检出";
                if (StringUtil.isNotEmpty(ii.getEndtime())
                        && StringUtil.isNotBlank(ii.getPwd())) {
                    // 该流程设备到期时间
                    long endTime = ii.getEndtime().getTime();
                    if (date.getTime() >= endTime) {     //当前时间大于密码到期时间
                        ii.setPwd("");
                        this.updatePwdShebei(ii);
                    }
                }
                if (StringUtil.isNotBlank(ii.getPwd()) && ppmPwdShebeiDto.getShowPwd()) {
                    String newPwd = null;
                    newPwd = new String(CryptoUtil.base64Decode(ii.getPwd()), StandardCharsets.UTF_8);
                    ii.setPwd(newPwd);
                } else {
                    ii.setPwd("");
                }
                Map<String, Object> map = new HashMap<>();
                map.put("ip", ii.getIp());
                map.put("username", ii.getUsername());
                map.put("nowdate", DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss"));

                List<PpmPwdShebei> shebeis = myBatisDao.selectListT(PPMPWDSHEBEI, map, "getSheBeiList");
                if (null != shebeis && !shebeis.isEmpty()) {
                    PpmPwdShebeiDto shebeiDto = new PpmPwdShebeiDto();
                    shebeiDto.setEndtime(shebeis.get(0).getEndtime());
                    shebeiDto.setPwd(shebeis.get(0).getPwd());
                    StringJoiner joiner = new StringJoiner(",");
                    shebeis.stream().forEach(tt -> {
                        joiner.add(tt.getApplyer());
                    });
                    shebeiDto.setApplyer(joiner.toString());
                    ii.setApplyer(shebeiDto.getApplyer());
                }
            } else if ("PENDING_RESET".equals(status)) {
                status = "待重置";
                ii.setPwd("");
                ii.setApplyer("");
            } else {
                status = "用户不存在";
                ii.setPwd("");
                ii.setApplyer("");
            }
            ii.setPwdStatus(status);
        });
        return list;
    }

    /**
     * 获取设备申请人
     * @param ip ip
     * @param userName 用户名称
     * @return PpmPwdShebeiDto
     */
    private PpmPwdShebeiDto getApplyerForShebei(String ip,String userName) {
        PpmPwdShebeiDto shebeiDto = new PpmPwdShebeiDto();
        HashMap<String, String> map = new HashMap<>();
        map.put("ip", ip);
        map.put("username", userName);
        List<PpmPwdShebei> list = myBatisDao.selectListT(PPMPWDSHEBEI, map, "getApplyerForShebei");
        if(null == list || list.isEmpty()) {
            return null;
        }
        List<PpmPwdShebei> sbList =
                list.stream().filter(ii -> null != ii ).collect(Collectors.toList());
        shebeiDto.setEndtime(sbList.get(0).getEndtime());
        shebeiDto.setPwd(sbList.get(0).getPwd());
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < sbList.size(); i++) {
            if(joiner.toString().contains(sbList.get(i).getApplyer())) {
                continue;
            }
            joiner.add(sbList.get(i).getApplyer());
        }
        shebeiDto.setApplyer(joiner.toString());
        return shebeiDto;
    }
}
