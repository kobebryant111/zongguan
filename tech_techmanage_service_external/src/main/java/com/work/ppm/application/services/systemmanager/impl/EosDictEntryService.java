package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.EosDictEntryDto;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.RemoteAddrType;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务字典从表
 */
@Service("eosDictEntryService")
public class EosDictEntryService implements IEosDictEntryService, InitializingBean {
    /**
     * 实体类路径
     */
    private static String EOSDICTENTRY = "com.work.ppm.application.model.systemmanager.EosDictEntry";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(EosDictEntry eosDictEntry, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(eosDictEntry,EosDictEntry.class);
        myBatisDao.selectPager(EOSDICTENTRY, map,pager,"queryPagerList");
    }

    @Override
    public EosDictEntry queryDetail(EosDictEntry eosDictEntry) {
        return myBatisDao.selectClass(EOSDICTENTRY, eosDictEntry,"queryDetail");
    }

    @Override
    public Integer updateEosDictEntry(EosDictEntry eosDictEntry) {
        return myBatisDao.executionPojo(EOSDICTENTRY, eosDictEntry, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteEosDictEntry(EosDictEntry eosDictEntry) {
        return myBatisDao.executionPojo(EOSDICTENTRY, eosDictEntry, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public String getDomainName(String ip) {
        String env = this.getProdEnv(ip);
        List<EosDictEntry> domain = getTypeidForList("PPM_NEW_URL");
        EosDictEntry item = null;
        if("VD".equals(env)) {
            item = getTypeidForDetail("PPM_NEW_URL","vdOld");
        } else {
            item = getTypeidForDetail("PPM_NEW_URL","oaOld");
        }
        if(null != item) {
            return item.getDictname();
        }
        return null;
    }

    @Override
    public String getProdEnv(String ip) {
        List<EosDictEntry> list = this.getTypeidForList("remoteIpDict");
        return getInternetDev(list,ip);
    }


    @Override
    public List<EosDictEntry> getTypeidForList(String dicttypeid) {
        Map<String, Object> map = new HashMap<>();
        map.put("dicttypeid", dicttypeid);
        return myBatisDao.selectListT(EOSDICTENTRY, map, "getList");
    }

    @Override
    public EosDictEntry getTypeidForDetail(String dicttypeid, String dictid) {
        Map<String, Object> map = new HashMap<>();
        map.put("dicttypeid",dicttypeid);
        map.put("dictid", dictid);
        return myBatisDao.selectClass(EOSDICTENTRY, map, "getList");
    }

    @Override
    public String getDictName(String dicttypeid, String dictid) {
        Map<String, Object> map = new HashMap<>();
        map.put("dicttypeid",dicttypeid);
        map.put("dictid", dictid);
        return myBatisDao.selectClass(EOSDICTENTRY, map, "getDictName");
    }

    @Override
    public EosDictEntry getTypenameForDetail(String dictName, String dictTypeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("dicttypeid", dictTypeId);
        map.put("dictname",dictName);
        return myBatisDao.selectClass(EOSDICTENTRY, map, "getList");
    }

    @Override
    public List<EosDictEntry> getTypeidForList(String[] dicttypeid) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sb = new StringBuilder(dicttypeid.length);
        for (String item: dicttypeid) {
            sb.append("'").append(item).append("',");
        }
        String sql = sb.toString().substring(0,sb.toString().length()-1);
        map.put("dicttypeids", sql);
        return  myBatisDao.selectListT(EOSDICTENTRY, map, "getList");
    }

    @Override
    public List<EosDictEntryDto> getList(EosDictEntry eosDictEntry) {
        List<EosDictEntry> list = myBatisDao.selectListT(EOSDICTENTRY, eosDictEntry, "getListForParams");
        return JsonUtil.jsonToList(list, EosDictEntryDto.class);
    }

    /**
     * 根据传入的ip数据获取网段信息
     * @param list 集合
     * @param ip ip
     * @return String
     */
    private String getInternetDev(List<EosDictEntry> list, String ip) {
        String dev = "other";
        for (int i = list.size() - 1; i >= 0; i--) {
            if (RemoteAddrType.analysisIp(ip, list.get(i).getDictname())){
                dev = list.get(i).getDictid();
                break;
            }
        }
        return dev;
    }

    @Override
    public Integer addEosDictEntry(EosDictEntry eosDictEntry) {
        eosDictEntry.setStatus(1L);
        eosDictEntry.setRank(1L);
        eosDictEntry.setSeqno("."+eosDictEntry.getDictid()+".");
        return myBatisDao.executionPojo(EOSDICTENTRY, eosDictEntry, IMyBatisDao.SQL.INSTER,"addPojo");
    }

    @Override
    public List<EosDictEntry> queryAll() {
        return this.myBatisDao.selectListT(EOSDICTENTRY,new EosDictEntry(),"queryPagerListSortNo");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
