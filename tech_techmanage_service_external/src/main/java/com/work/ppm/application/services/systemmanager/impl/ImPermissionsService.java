package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.model.systemmanager.ImMenus;
import com.work.ppm.application.model.systemmanager.OperaAuthInfo;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.dto.systemmanager.ImPermissionsDto;
import com.work.ppm.application.model.systemmanager.ImPermissions;
import com.work.ppm.application.services.systemmanager.IImPermissionsService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 角色菜单关联接口
 */
@Service("imPermissionsService")
public class ImPermissionsService implements IImPermissionsService {
    /**
     * 实体类路径
     */
    private static String IMPERMISSIONS = "com.work.ppm.application.model.systemmanager.ImPermissions";
    private static String OPERA_AUTH_INFO = "com.work.ppm.application.model.systemmanager.OperaAuthInfo";
    private static String IMMENUS = "com.work.ppm.application.model.systemmanager.ImMenus";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public List<ImPermissions> queryRidForList(String rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rid", rid);
        map.put("sysType", "1");
        return myBatisDao.selectListT(IMPERMISSIONS, map,"getRidForList");
    }

    @Override
    @Transactional(transactionManager = "defaultTx")
    public Integer addImPermissions(ImPermissionsDto imPermissions) {
        ImPermissions entity = null;
        // 根据rid 判断是否存在数据
        List<ImPermissions> list = this.queryRidForList(imPermissions.getRid());
        int delRowId = 0;
        if(null != list && !list.isEmpty()) {
            delRowId = this.deleteImPermissions(imPermissions.getRid());
        }
        // 如果存在 则先删除，反之则直接添加
        if(delRowId <= 0 && (null != list && !list.isEmpty())) {
           throw new BizException(ApiCode.FAIL,"更新数据时出错");
        }

        for (String mid :imPermissions.getMids()) {
            entity = new ImPermissions();
            entity.setMid(mid);
            entity.setRid(imPermissions.getRid());
            entity.setCreatedate(new Date());
            entity.setSysType(1);
            myBatisDao.executionPojo(IMPERMISSIONS, entity, IMyBatisDao.SQL.INSTER,"addPojo");
        }
        return 1;
    }

    @Override
    public Integer deleteImPermissions(String rid) {
        ImPermissions imPermissions = new ImPermissions();
        imPermissions.setRid(rid);
        imPermissions.setSysType(1);
        return myBatisDao.executionPojo(IMPERMISSIONS, imPermissions, IMyBatisDao.SQL.DELETE,"deleteByRid");
    }

    @Override
    public Integer deleteImPermissionsForMid(String mid) {
        ImPermissions imPermissions = new ImPermissions();
        imPermissions.setMid(mid);
        imPermissions.setSysType(1);
        return myBatisDao.executionPojo(IMPERMISSIONS, imPermissions, IMyBatisDao.SQL.DELETE,"deleteByRid");
    }

    @Override
    public boolean getAuthPathRelForMid(String mid) {
        Map<String,Object> map = new HashMap<>();
        map.put("mid", mid);
        map.put("sysType", 1);
        long id = myBatisDao.selectCount(IMPERMISSIONS, map,"authRelForMid");
        return id > 0L;
    }

    public List<String> getParentId (String mid , List<String> mids) {
        Map<String , String> map = new HashMap<>();
        map.put("mid" , mid);
        if (!mids.contains(mid)) {
            mids.add(mid);
        }
        List<ImMenus> operaMenus = myBatisDao.selectListT(IMMENUS, map, "queryParentId");
        if (operaMenus.size()>0) {
            String parentId = operaMenus.get(0).getParentid();
            if (StringUtil.isNotEmpty(parentId) && !"-1".equals(parentId)) {
                getParentId(parentId , mids);
            }
        }
        return mids;
    }

    @Override
    @Transactional(transactionManager = "defaultTx")
    public Integer addOperaPermissions(ImPermissionsDto imPermissions) {
        ImPermissions entity = null;
        // 根据rid 判断是否存在数据
        List<ImPermissions> list = this.queryOperaList(imPermissions.getRid());
        List<OperaAuthInfo> listEncoding = this.queryAuthEnCodingList(imPermissions.getRid());
        int delRowId = 0;
        int authId = 0;
        if(null != list && !list.isEmpty()) {
            delRowId = this.deleteOperaPermissions(imPermissions.getRid());
        }
        // 如果存在 则先删除，反之则直接添加
        if(delRowId <= 0 && (null != list && !list.isEmpty())) {
            throw new BizException(ApiCode.FAIL,"更新按钮权限数据时出错");
        }

        if(null != listEncoding && !listEncoding.isEmpty()) {
            authId = this.deleteAuthEncoding(imPermissions.getRid());
        }
        // 如果存在 则先删除，反之则直接添加
        if(authId <= 0 && (null != listEncoding && !listEncoding.isEmpty())) {
            throw new BizException(ApiCode.FAIL,"更新按钮权限码数据时出错");
        }

        List<String> newMids = new ArrayList();
        //查询当前目录以上所有的父类。
        for (String mid :imPermissions.getMids()) {
            newMids = getParentId(mid , newMids);
        }

        for (String mid : newMids) {
            entity = new ImPermissions();
            entity.setMid(mid);
            entity.setId(UUID.randomUUID().toString());
            entity.setRid(imPermissions.getRid());
            entity.setSysType(1);
            entity.setCreatedate(new Date());
            myBatisDao.executionPojo(IMPERMISSIONS, entity, IMyBatisDao.SQL.INSTER,"addOperaPermissions");
            //添加权限码
            createAuthEncoding(entity);
        }
        return 1;
    }

    public void createAuthEncoding(ImPermissions entity){
        String rid = entity.getRid();//获取校色ID
        String mid = entity.getMid();//获取目录ID
//        String authEncoding = rid + "_" + mid;
        String authEncoding = mid + "_" + "LICENCE";
        Map map = new HashMap<>();
        map.put("authId",UUID.randomUUID().toString());
        map.put("authName",rid);
        map.put("rolId",rid);
        map.put("authEncoding",authEncoding);
        map.put("creatTime",new Date());
        myBatisDao.executionPojo(OPERA_AUTH_INFO, map, IMyBatisDao.SQL.INSTER,"addOperaAuthInfo");
    }


    @Override
    public List<ImPermissions> queryOperaList(String rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rid", rid);
        map.put("sysType", "1");
        return myBatisDao.selectListT(IMPERMISSIONS, map,"getOperaList");
    }

    @Override
    public Integer deleteOperaPermissions(String rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rid", rid);
        map.put("sysType", 1);
        return myBatisDao.executionPojo(IMPERMISSIONS, map, IMyBatisDao.SQL.DELETE,"deleteOperaPermissions");
    }


    public List<OperaAuthInfo> queryAuthEnCodingList(String rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rolId", rid);
        return myBatisDao.selectListT(OPERA_AUTH_INFO, map,"queryAuthEnCodingList");
    }

    public Integer deleteAuthEncoding(String rid) {
        Map<String,Object> map = new HashMap<>();
        map.put("rolId", rid);
        return myBatisDao.executionPojo(OPERA_AUTH_INFO, map, IMyBatisDao.SQL.DELETE,"deleteAuthEncoding");
    }

}
