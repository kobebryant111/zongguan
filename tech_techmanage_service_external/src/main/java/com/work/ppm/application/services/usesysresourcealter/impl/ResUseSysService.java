package com.work.ppm.application.services.usesysresourcealter.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.usesysresourcealter.ResUseSysDto;
import com.work.ppm.application.model.resourceplat.ResFlowPeople;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.usesysresourcealter.ResUseSys;
import com.work.ppm.application.model.usesysresourcealter.ResUseSysOther;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.usesysresourcealter.IResUseSysService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.resourceplat.ResConstant;
import com.work.ppm.commons.usesysresourcealter.ResUseSysConstant;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.utils.HttpClientUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 应用系统资源变更
 *
 */
@Slf4j
@Service("resUseSysService")
public class ResUseSysService extends BaseDaoSupper implements IResUseSysService {
    /**
     * 应用系统变更实体类路径
     */
    private static String RESUSESYS = "com.work.ppm.application.model.usesysresourcealter.ResUseSys";
    /**
     * 流程人员实体类路径
     */
    private static String RES_FLOW_PEOPLE = "com.work.ppm.application.model.resourceplat.ResFlowPeople";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 字典表
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 分页查询应用系统流程列表
     * @param resUseSysDto 对象
     * @param pager       分页对象
     */
    @Override
    public void queryPager(ResUseSysDto resUseSysDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(resUseSysDto, ResUseSysDto.class);
        myBatisDao.selectPager(RESUSESYS, map, pager, "queryResUseSysAlterList");
    }

    /**
     * 获取应用系统下的数据
     * @param appNo 应用系统id
     * @param ids
     * @return List
     */
    @Override
    public List<Map<String,Object>> queryUseSysList(String appNo,String ids) {
        List<Map<String,Object>> list =new ArrayList<>();
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail(ResUseSysConstant.RES_CMDB_URL, "01");
        String  url=eos.getDictname();
        String value = "{" +
                "\"transactionId\":\"QueryReleaxCiAttrBatchAction\"," +
                " \"body\": {" +
                "\"direction\": \"from\"," +
                "\"reqJson\":{" +
                "\"ordCiList\":[{\"ciId\":\""+appNo+"\"} ]," +
                "\"destClassKeys\":[{\"classKey\":\"C_machine\"},{\"classKey\":\"C_VM\"}," +
                "{\"classKey\":\"C_midware\"}," +
                "{\"classKey\":\"C_special\"},{\"classKey\":\"C_database\"}," +
                "{\"classKey\":\"C_AS400\"},{\"classKey\":\"C_ADN_POOL\"}]," +
                "\"destCiAttrList\":[{\"ciAttrKey\":\"A_20160318_000019\"},{"+
                "\"ciAttrKey\":\"A_20160318_000024\"},{"+
                "\"ciAttrKey\":\"A_20160318_000039\"},{"+
                "\"ciAttrKey\":\"A_20160318_000023\"},{"+
                "\"ciAttrKey\":\"A_20200227_000481\"},{"+
                "\"ciAttrKey\":\"A_20200916_000553\"},{"+
                "\"ciAttrKey\":\"A_20160318_000026\"}]"+
                "}" +
                "}" +
                "}";
        String data = HttpClient.post(url,value);
        String josnData = JsonUtil.toJSONString(data);
        Map<String,Object> map = JsonUtil.jsonToMap(josnData);
        String retCode = map.get(ResUseSysConstant.RETCODE) + "";
        if("0000".equals(retCode)){
            String jsonStr = map.get("data")+"";
            JSONObject jsonData= JSON.parseObject(jsonStr);
            String respJsonList=jsonData.getString("respJson");
            JSONObject jsonCiListData=JSON.parseObject(respJsonList);
            String ciList=jsonCiListData.getString("ciList");
            JSONArray jsonCiList=JSON.parseArray(ciList);
            for (int i =0; i<jsonCiList.size(); i++){

                JSONObject jsonObjData =(JSONObject)jsonCiList.get(i);

                String ciAttrList= jsonObjData.getString("destCiList");
                JSONArray jsonArrCiList = JSON.parseArray(ciAttrList);
                // 获取本地ip
                if(!jsonArrCiList.isEmpty()){
                    for (int j =0; j<jsonArrCiList.size(); j++) {

                        Map<String, Object> mapList = new HashMap<>();
                        JSONObject jsonArrCiData =(JSONObject)jsonArrCiList.get(j);
                        String destCiAttrList= jsonArrCiData.getString("destCiAttrList");
                        JSONArray jsonDestCiAttrList = JSON.parseArray(destCiAttrList);
                        setDestCiAttrList(jsonDestCiAttrList,mapList);
                        mapList.put("ciclassifykey",jsonArrCiData.getString("ciClassifyKey"));//分类key
                        mapList.put("ciclassifyname",jsonArrCiData.getString("ciClassifyName"));//分类
                        mapList.put("ciname",jsonArrCiData.getString("ciName"));// ci标识
                        mapList.put("ciinstcondition",jsonArrCiData.getString("ciInstCondition"));// ci状态
                        mapList.put("deloradd","");// 删除或是新增动作
                        mapList.put("ciid",jsonArrCiData.getString("ciId"));// ciid

                        list.add(mapList);
                    }
                }
            }
            if (!ids.isEmpty()){
                return setListData(list,ids);
            } else {
                return list;
            }
        } else {
            throw new BizException(ApiCode.FAIL, "CMDB接口调用失败");
        }
    }

    /**
     * 过滤已选择的数据
     * @param list
     * @return retList
     */
    private List<Map<String,Object>> setListData(List<Map<String,Object>> list,String ids){
        String[] arrIds =ids.split(",");
        for (int j=0 ; j<arrIds.length; j++) {
            for(int i=0; i<list.size(); i++){
                if (arrIds[j].equals(list.get(i).get("ciid"))) {
                    list.remove(i);
                }
            }
        }
        return list;
    }

    /**
     * 获取返回数据
     * @param jsonDestCiAttrList
     * @param mapList
     */
    void setDestCiAttrList(JSONArray jsonDestCiAttrList,Map<String, Object> mapList){
        if(!jsonDestCiAttrList.isEmpty()) {
            for (int k = 0; k < jsonDestCiAttrList.size(); k++) {
                JSONObject jsonDestCiAttrData =(JSONObject)jsonDestCiAttrList.get(k);
                if ("A_20160318_000019".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))) {
                    mapList.put("localip", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE)); //本地ip
                } else if("A_20160318_000039".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))){
                    //操作系统版本
                    mapList.put("osversion", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE));
                } else if("A_20160318_000024".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))){
                    //逻辑设备状态
                    mapList.put("logicalresourcestats", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE));
                } else if("A_20160318_000023".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))){
                    //物理设备状态
                    mapList.put("physicalresourcestats", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE));
                } else if("A_20200227_000481".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))){
                    mapList.put("usertype", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE)); //用户类型
                } else if("A_20200916_000553".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))){
                    mapList.put("explain", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE)); //特例说明
                } else if("A_20160318_000026".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))){
                    mapList.put("serialno", jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE)); //序列号
                }
            }
        }
    }

    /**
     * 获取需要新增的数据
     * @param resUseSysDto
     * @param pager
     * @return list
     */
    @Override
    public List<Map<String,Object>> queryUseSysPageList(ResUseSysDto resUseSysDto, Pager pager) {
        List<Map<String,Object>> list =new ArrayList<>();
        String resclassify= resUseSysDto.getResclassify();
        int pageNumber=pager.getSize();
        int pageSize =pager.getIndex();
        String ip =resUseSysDto.getIp();
        String serialno =resUseSysDto.getSerialno();
        String value="";
        String ipserialno=setIpAndSerialno(ip,serialno);
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail(ResUseSysConstant.RES_CMDB_URL,"01");
        String  url=eos.getDictname();
        value = "{" +
                "\"transactionId\":\"QueryCiAttrBatchAction\"," +
                "\"body\": {" +
                "\"queryPage\": \""+pageSize+"\"," +
                "\"pageNumber\": \""+pageNumber+"\"," +
                "\"reqJson\":{";
        if(!ipserialno.isEmpty()){
            value+=ipserialno;
        }
        value+="\"classList\":[{\"classKey\":\""+resclassify+"\" } ]," +
                "\"ciAttrList\":[{\"ciAttrKey\":\"A_20160318_000019\"},{"+
                "\"ciAttrKey\":\"A_20160318_000024\"},{"+
                "\"ciAttrKey\":\"A_20160318_000039\"},{"+
                "\"ciAttrKey\":\"A_20160318_000023\"},{"+
                "\"ciAttrKey\":\"A_20200227_000481\"},{"+
                "\"ciAttrKey\":\"A_20200916_000553\"},{"+
                "\"ciAttrKey\":\"A_20160318_000026\"}]"+
                "}" +
                "}" +
                "}";
        String data = HttpClient.post(url,value);
        String josnData = JsonUtil.toJSONString(data);
        Map<String,Object> map = JsonUtil.jsonToMap(josnData);
        String retCode = map.get(ResUseSysConstant.RETCODE) + "";
        if("0000".equals(retCode)){
            String jsonStr = map.get("data")+"";
            JSONObject jsonData=JSON.parseObject(jsonStr);
            String respJsonList=jsonData.getString("respJson");
            JSONObject jsonCiListData=JSON.parseObject(respJsonList);
            String ciList=jsonCiListData.getString("ciList");
            JSONArray jsonCiList=JSON.parseArray(ciList);

            for (int i =0; i<jsonCiList.size(); i++){
                Map<String, Object> mapList = new HashMap<>();
                JSONObject jsonObjData =(JSONObject)jsonCiList.get(i);
                if(jsonObjData != null){
                    mapList.put("ciclassifykey",jsonObjData.getString("ciClassifyKey"));//分类key
                    mapList.put("ciclassifyname",jsonObjData.getString("ciClassifyName"));//分类
                    mapList.put("ciname",jsonObjData.getString("ciName"));// 资源名称
                    mapList.put("ciinstcondition",jsonObjData.getString("ciInstCondition"));// ci状态
                    mapList.put("deloradd","");// 删除或是新增动作
                    mapList.put("ciid",jsonObjData.getString("ciId"));// ciid
                    mapList.put("totalPage",jsonData.getString("count")); //总条数
                    String ciAttrList= jsonObjData.getString("ciAttrList");
                    JSONArray jsonArrCiList = JSON.parseArray(ciAttrList);
                    // 获取本地ip
                    if(jsonArrCiList != null){
                        setJsonArrCiList(jsonArrCiList,mapList);
                    }
                }
                list.add(mapList);
            }
            if (!resUseSysDto.getIds().isEmpty()){
                return setList(list,resUseSysDto);
            } else {
                return list;
            }
        } else {
            throw new BizException(ApiCode.FAIL, "CMDB接口调用失败");
        }
    }

    /**
     * 拼接ip或者蓄序列号
     * @param ip
     * @param serialno
     * @return value
     */
    String setIpAndSerialno(String ip ,String serialno){
        String ipStr="";
        if(!ip.isEmpty() && serialno.isEmpty()){
            ipStr+= "\"queryCiAttrList\": [" +
                    "                  {\"queryType\":\"like\"," +
                    "                   \"ciAttrValue\":[{\"value\" :\""+ip+"\" }],"+
                    "                   \"ciAttrKey\":\"A_20160318_000019\"}],";
        }
        if(!serialno.isEmpty() && ip.isEmpty()){
            ipStr+="\"queryCiAttrList\":[ " +
                    "                {\"queryType\":\"like\"," +
                    "                   \"ciAttrValue\":[{\"value\":\""+serialno+"\"} ] ,"+
                    "                   \"ciAttrKey\":\"A_20160318_000026\"}],";
        }
        if(!serialno.isEmpty() && !ip.isEmpty()){
            ipStr+="\"queryCiAttrList\":[" +
                    "           {\"queryType\":\"like\"," +
                    "                   \"ciAttrValue\":[{\"value\": \""+ip+"\"}]  ,"+
                    "                   \"ciAttrKey\":\"A_20160318_000019\"},"+
                    "                   {\"queryType\":\"like\"," +
                    "                   \"ciAttrValue\":[{\"value\":\""+serialno+"\" }] ,"+
                    "                   \"ciAttrKey\":\"A_20160318_000026\"}],";
        }
        return ipStr;

    }

    /**
     * 获取返回数据
     * @param jsonArrCiList
     * @param mapList
     */
    void setJsonArrCiList(JSONArray jsonArrCiList,Map<String, Object> mapList){
        for (int j =0; j<jsonArrCiList.size(); j++) {
            JSONObject jsonArrCiListData =(JSONObject)jsonArrCiList.get(j);
            if ("A_20160318_000019".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))) {
                mapList.put("localip", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE)); //本地ip
            } else if("A_20160318_000039".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))){
                mapList.put("osversion", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE)); //操作系统版本
            } else if("A_20160318_000024".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))){
                //逻辑设备状态
                mapList.put("logicalresourcestats", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE));
            } else if("A_20160318_000023".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))){
                //物理设备状态
                mapList.put("physicalresourcestats", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE));
            } else if("A_20200227_000481".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))){
                mapList.put("usertype", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE)); //用户类型
            } else if("A_20200916_000553".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))){
                mapList.put("explain", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE)); //特例说明
            } else if("A_20160318_000026".equals(jsonArrCiListData.getString(ResUseSysConstant.CIKEY))){
                mapList.put("serialno", jsonArrCiListData.getString(ResUseSysConstant.CIVALUE)); //序列号
            }
        }
    }

    /**
     * 过滤已选择的数据
     * @param list
     * @return retList
     */
    private List<Map<String,Object>> setList(List<Map<String,Object>> list,ResUseSysDto resUseSysDto){
        String ids =resUseSysDto.getIds();
        String[] arrIds =ids.split(",");
        for (int j=0 ; j<arrIds.length; j++) {
            for(int i=0; i<list.size(); i++){
                if (arrIds[j].equals(list.get(i).get("ciid"))) {
                    list.remove(i);
                }
            }
        }
        return list;
    }

    /**
     * 应用系统资源变更申请流程
     *
     * @param resUseSysDto 资源申请数据
     * @return
     */
    @Override
    public String submitResUseSysData(ResUseSysDto resUseSysDto) {
        ResUseSys resUseSys = resUseSysDto.getList();

        String xid = null;
        String processInstID = "";
        String processInfo = "";

        //获取二级部经理审批人
        if(resUseSys.getDepid().isEmpty()){
            return "9999";
        }
        String spr =  getEjbjl(resUseSys.getDepid());
        if (StringUtils.isEmpty(spr)) {
            return "9999";
        }
        Map<String, String> map = new HashMap<>();
        map.put("users", spr);
        map.put("status", "0");

        BpsWorkFlowDto bpsWorkFlowDto = new BpsWorkFlowDto();
        try {
            xid = BpsUtil.transactionBegin(resUseSys.getCreator());
            //发起工作流
            bpsWorkFlowDto.setProcessDefName("com.work.ppm.bps.useSysResourceAlter");
            bpsWorkFlowDto.setProcessInstName(resUseSys.getAppsysname()+"资源变更申请");
            bpsWorkFlowDto.setProcessInstDesc(ResUseSysConstant.MSG);
            bpsWorkFlowDto.setUserId(resUseSys.getCreator());
            bpsWorkFlowDto.setApplydesc(ResUseSysConstant.MSG);
            bpsWorkFlowDto.setRelaData(map);
            processInstID = BpsUtil.createProcessAndFinishFirst(bpsWorkFlowDto, xid);
            bpsWorkFlowDto.setProcessInstId(processInstID);
            BpsUtil.transactionCommit(resUseSys.getCreator(), xid);
            BpsUtil.flowLog(bpsWorkFlowDto);

            //设置流程实例ID
            resUseSysDto.getList().setProcessInstID(processInstID);
            //设置流程实例ID
            saveUseSysData(resUseSysDto,"3");

            processInfo = BpsUtil.queryProcessInfo(bpsWorkFlowDto);
        } catch (Exception e) {
            BpsUtil.transactionRollback(resUseSys.getCreator(), xid);
        }
        return processInfo;
    }

    /**
     * 获取二级部经理
     * @param depid
     * @return str
     */
    String getEjbjl(String depid){
        List<CommEmployeeDto> employs = UsersUtil.queryMinistryManagers(depid);
        StringJoiner joiner = new StringJoiner(",");
        employs.stream().forEach(ii -> joiner.add(ii.getBadge()));
        return joiner.toString();
    }

    /**
     * 保存业务数据
     * @param resUseSysDto
     */
    public void saveUseSysData(ResUseSysDto resUseSysDto,String status){
        List<ResUseSysOther> resUseSysOtherList=resUseSysDto.getSubList();
        ResUseSys resUseSys = resUseSysDto.getList();
        // 判断数据是否存在
        String id ="";
        int cnt =getUseSysCnt(resUseSys.getId());
        if(cnt >0){
            id =resUseSys.getId();
            Map<String,String> map =new HashMap<>();
            map.put("id",id);
            resUseSys.setStatus(status);
            // 更新主表
            myBatisDao.executionPojo(RESUSESYS, resUseSys, IMyBatisDao.SQL.UPDATE, "upResUseSys");
            // 删除附表
            myBatisDao.executionPojo(RESUSESYS, map, IMyBatisDao.SQL.DELETE, "delResOther");
        } else {
            id= UUIDUtils.generateUUID();
            resUseSys.setId(id); //主键
            resUseSys.setAltercontent("1");// 变更内容 1：资源
            resUseSys.setCreatetime(new Date());
            resUseSys.setStatus(status);
            myBatisDao.executionPojo(RESUSESYS, resUseSys, IMyBatisDao.SQL.INSTER, "saveResUseSysData");
        }
        for (int i =0; i<resUseSysOtherList.size() ;i++){
            String subid= UUIDUtils.generateUUID();
            resUseSysOtherList.get(i).setId(subid);
            resUseSysOtherList.get(i).setResusesysid(id);
            myBatisDao.executionPojo(RESUSESYS, resUseSysOtherList.get(i), IMyBatisDao.SQL.INSTER,
                    "saveResUseSysOtherData");
        }
    }

    /**
     * 判断数据是否存在
     * @param id
     * @return int
     */
    public int getUseSysCnt(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        Long count = myBatisDao.selectCount(RESUSESYS, map, "getUseSysCnt");
        return null != count ? count.intValue() : 0;
    }

    /**
     * 重新提交发起工作项
     * @param flowDto
     * @param resUseSysDto
     * @return boolean
     */
    public boolean reSubmitWorkFlow(FlowDto flowDto, ResUseSysDto resUseSysDto) {
        //保存数据
        boolean flag = setResOther(resUseSysDto);
        if (flag) {
            flowDto.setDepid(resUseSysDto.getList().getDepid());
            return submitWorkItem(flowDto);
        } else {
            return false;
        }
    }

    /**
     * 保存应用系统资源变更附表数据
     *
     * @param resUseSysDto
     * @return boolean
     */
    public boolean setResOther(ResUseSysDto resUseSysDto) {
        // 主表数据
        ResUseSys resUseSys =resUseSysDto.getList();
        Map<String,String> map =new HashMap<>();
        String id =resUseSys.getId();
        map.put("id",id);
        // 附表数据
        List<ResUseSysOther> resUseSysOtherList=resUseSysDto.getSubList();
        //删除附表数据
        myBatisDao.executionPojo(RESUSESYS, map, IMyBatisDao.SQL.DELETE, "delResOther");
        for (int i =0; i<resUseSysOtherList.size() ;i++){
            String subid= UUIDUtils.generateUUID();
            resUseSysOtherList.get(i).setId(subid);
            resUseSysOtherList.get(i).setResusesysid(id);
            myBatisDao.executionPojo(RESUSESYS, resUseSysOtherList.get(i), IMyBatisDao.SQL.INSTER,
                    "saveResUseSysOtherData");
        }
        return true;
    }

    /**
     * 获取应用系统资源变更数据
     * @param resUseSys
     * @return resUseSysDto
     */
    public List<ResUseSysOther> queryResUseSysAlterData(ResUseSys resUseSys){
        return  myBatisDao.selectListT(RESUSESYS, resUseSys, "queryResUseSysAlterData");
    }

    /**
     * 删除
     *
     * @param resUseSysOther
     */
    public void delUseSysAlterData(ResUseSysOther resUseSysOther) {
        myBatisDao.executionPojo(RESUSESYS, resUseSysOther, IMyBatisDao.SQL.UPDATE, "delUseSysAlterData");
    }

    /**
     * 根据节点，获取审批人员列表
     *
     * @param nodeId
     * @return spr
     */
    private String getSprInfo(String nodeId) {
        //获取系统部确认审批人审批人
        List<ResFlowPeople> resFlowPeopleList = myBatisDao.selectListT(RES_FLOW_PEOPLE, nodeId,
                "queryFlowPeopleByNodeId");
        if (resFlowPeopleList.isEmpty()) {
            return "";
        }
        StringBuilder spr = new StringBuilder("");
        for (int i = 0; i < resFlowPeopleList.size(); i++) {
            spr.append(resFlowPeopleList.get(i).getUserId());
            if (i < resFlowPeopleList.size() - 1) {
                spr.append(",");
            }
        }
        return spr.toString();
    }

    /**
     * 提交工作项
     *
     * @param flowDto
     */
    @Override
    public boolean submitWorkItem(FlowDto flowDto) {
        if (submitWrok(flowDto)){
            return false;
        }
        return true;
    }

    /**
     * 提交工作项
     * @param flowDto
     * @return boolean
     */
    private boolean submitWrok(FlowDto flowDto) {
        String xid = "";
        BpsWorkFlowDto bpsWorkFlowDto = new BpsWorkFlowDto();
        try {
            bpsWorkFlowDto(flowDto, bpsWorkFlowDto);
            Map<String, String> map = new HashMap<>();

            //获取系统部确认审批人审批人
            String nodeType = "";
            String auditUserId = "";
            //系统部确认
            if (ResUseSysConstant.EJBMANAGE.equals(flowDto.getActivityDefID())) {
                if ("01".equals(flowDto.getConclusion())) {
                    //配置管理岗
                    nodeType = ResUseSysConstant.CONFIGMANAGER;
                }
            } else if (ResUseSysConstant.CONFIGMANAGER.equals(flowDto.getActivityDefID())) {//配置管理岗
                if ("01".equals(flowDto.getConclusion())) {
                    nodeType = ResUseSysConstant.SYSMANAGER;
                } else {
                    nodeType = ResUseSysConstant.EJBMANAGE;
                }
            } else if (ResConstant.MANUALACTIVITY.equals(flowDto.getActivityDefID())) {//重新提交
                nodeType = ResUseSysConstant.EJBMANAGE;
            }
            //需要取审批人员
            if (isBpsFlow(flowDto, bpsWorkFlowDto, map, nodeType, auditUserId)) {
                return true;
            }
        } catch (Exception e) {
            BpsUtil.transactionRollback(bpsWorkFlowDto.getUserId(), xid);
            throw new BizException(e);
        }
        return false;
    }

    /**
     * 设置工作流参数
     * @param flowDto
     * @param bpsWorkFlowDto
     */
    private void bpsWorkFlowDto(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto) {
        bpsWorkFlowDto.setProcessInstId(flowDto.getProcessInstId());
        bpsWorkFlowDto.setWorkItemId(flowDto.getWorkItemId());
        bpsWorkFlowDto.setUserId(flowDto.getCreator());
        bpsWorkFlowDto.setApplydesc(flowDto.getApplydesc());
        bpsWorkFlowDto.setConclusion(flowDto.getConclusion());
        bpsWorkFlowDto.setSuggest(flowDto.getAudit());
    }

    /**
     * 需要取审批人员
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param map
     * @param nodeType
     * @param auditUserId
     * @return boolean
     */
    private boolean isBpsFlow(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map,
                              String nodeType, String auditUserId) {
        if (StringUtils.isNotEmpty(nodeType)) {
            if(ResUseSysConstant.EJBMANAGE.equals(nodeType)){
                auditUserId = getEjbjl(flowDto.getDepid());// 获取二级部经理
            } else if(ResUseSysConstant.SYSMANAGER.equals(nodeType)){
                String part = myBatisDao.selectClass(RESUSESYS, flowDto.getProcessInstId(),
                        "resConfigPart");
                auditUserId = splitByStr(part);
            }else{
                //设置审批
                auditUserId = this.getSprInfo(nodeType);
            }
            if (StringUtils.isEmpty(auditUserId)) {
                return true;
            }
        }
        getBpsFlow(flowDto, bpsWorkFlowDto, map, auditUserId);
        return false;
    }

    /**
     * 根据字符串截取工号
     * @param str 字符串
     * @return 截取工号组成的字符串
     */
    private String splitByStr(String str){
        String result = "";
        if(StringUtils.isNotEmpty(str)){
            String[] arry = str.split(",");
            for (int i = 0;i<arry.length;i++){
                result+= arry[i].substring(arry[i].indexOf('(')+1,arry[i].indexOf(')'))+",";
            }
        }
        if(result.length() >0){
            result =result.substring(0,result.length()-1);
        }
        return result;
    }

    /**
     * 设置流程流转数据
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param map
     * @param auditUserId
     * @return xid
     */
    private String getBpsFlow(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto,
                              Map<String, String> map, String auditUserId) {
        String xid;
        xid = getXid(flowDto, bpsWorkFlowDto, map, auditUserId);
        //设置流程流转数据
        isBpsWork(flowDto, xid, bpsWorkFlowDto, map);
        BpsUtil.transactionCommit(bpsWorkFlowDto.getUserId(), xid);
        BpsUtil.flowLog(bpsWorkFlowDto);
        return xid;
    }

    /**
     * 状态设置为3审核中
     * @param flowDto
     * @param xid
     * @param bpsWorkFlowDto
     * @param map
     */
    private void isBpsWork(FlowDto flowDto, String xid,
                           BpsWorkFlowDto bpsWorkFlowDto, Map<String, String> map) {
        isSource(flowDto, map);
        bpsWorkFlowDto.setRelaData(map);
        BpsUtil.finshWorkItem(bpsWorkFlowDto, xid);

        //如果是系统部确认，并且通过，则状态设置为3审核中
        Map<String, String> mapRes = new HashMap<>();
        mapRes.put("id", flowDto.getId());
        isDetemine(flowDto, mapRes);
        myBatisDao.executionPojo(RESUSESYS, mapRes,
                IMyBatisDao.SQL.UPDATE, "updateWorkFlowRelateInfo");
    }

    /**
     * 节点区分
     * @param flowDto
     * @param map
     */
    private void isSource(FlowDto flowDto, Map<String, String> map) {
        if ("02".equals(flowDto.getConclusion())) { //退回
            map.put(ResConstant.STATUS, "09");
        } else {
            map.put(ResConstant.STATUS, "-1");
        }
    }

    /**
     * 流程状态区分
     * @param flowDto
     * @param mapRes
     */
    private void isDetemine(FlowDto flowDto, Map<String, String> mapRes) {
        if (ResUseSysConstant.EJBMANAGE.equals(flowDto.getActivityDefID()) && "01".equals(flowDto.getConclusion())) {
            mapRes.put(ResConstant.STATUS, "3");
        } else if ("02".equals(flowDto.getConclusion())) { //退回，则状态设置成5拒绝
            mapRes.put(ResConstant.STATUS, "5");
        } else if (ResConstant.MANUALACTIVITY.equals(flowDto.getActivityDefID())) { //重提提交节点，则设置状态为2待审核
            mapRes.put(ResConstant.STATUS, "3");
        } else if (ResUseSysConstant.SYSMANAGER.equals(flowDto.getActivityDefID()) &&
                "01".equals(flowDto.getConclusion())) { //系统部审批
            ResUseSys resUseSys = new ResUseSys();
            resUseSys.setProcessInstID(flowDto.getProcessInstId());
            List<ResUseSysOther> useSysOthers = queryResUseSysAlterData(resUseSys);
            // 通过cmdb更新数据
            xiuGaiCmdbCi(useSysOthers,flowDto.getProcessInstId());
            //结束
            mapRes.put(ResConstant.STATUS, "4");
        } else if (ResUseSysConstant.CONFIGMANAGER.equals(flowDto.getActivityDefID()) &&
                "01".equals(flowDto.getConclusion())) {//架构部评审
            mapRes.put(ResConstant.STATUS, "3");
        }
    }

    /**
     * 设置审批人
     * @param flowDto
     * @param bpsWorkFlowDto
     * @param map
     * @param auditUserId
     * @return xid
     */
    private String getXid(FlowDto flowDto, BpsWorkFlowDto bpsWorkFlowDto,
                          Map<String, String> map, String auditUserId) {
        String xid;
        xid = BpsUtil.transactionBegin(bpsWorkFlowDto.getUserId());
        //设置审批人，非最后一个节点,并且不是驳回
        if (!ResUseSysConstant.SYSMANAGER.equals(flowDto.getActivityDefID()) && "01".equals(flowDto.getConclusion())) {
            map.put("users", auditUserId);
        } else if(ResUseSysConstant.CONFIGMANAGER.equals(flowDto.getActivityDefID())
                && "02".equals(flowDto.getConclusion())){
            map.put("users", auditUserId);
        }
        return xid;
    }

    /**
     * 修改CMDB数据
     * @param processInstId 流程id
     * @throws RemoteException
     * @throws JSONException
     */
    private void xiuGaiCmdbCi(List<ResUseSysOther> useSysOthers,String processInstId) {
        if (!useSysOthers.isEmpty()) {
            Map<String, String> headMap = new HashMap<>();
            EosDictEntry eos = eosDictEntryService.getTypeidForDetail(ResUseSysConstant.RES_CMDB_URL, "02");
            String url = eos.getDictname();
            headMap.put("SOAPAction", url+"?wsdl");
            // 查询此流程实例关联的业务数据
            callCmdb(useSysOthers, processInstId, headMap, url);
        }
    }

    /**
     * 调用cmdb接口
     * @param useSysOthers
     * @param processInstId
     * @param headMap
     * @param url
     */
    private void callCmdb(List<ResUseSysOther> useSysOthers, String processInstId,
                          Map<String, String> headMap,String url) {
        String alterFlag="0";
        String upCiFlag ="0";
        String delAndUpCiFlag ="0";
        StringBuilder addAlterCilist =new StringBuilder();//新增系统关系参数
        StringBuilder delAlterCilist =new StringBuilder();//删除系统关系参数
        StringBuilder upDelCilist =new StringBuilder();//更新删除关系后数据的参数
        StringBuilder upCilist =new StringBuilder();//更新参数
        for (int i = 0; i < useSysOthers.size(); i++) {
            String ciclassifykey = useSysOthers.get(i).getCiclassifykey();
            String deloradd = useSysOthers.get(i).getDeloradd();
            if ("1".equals(deloradd)) {// 新增
                alterFlag="1";
                alterData(useSysOthers.get(i),addAlterCilist);
            } else if ("2".equals(deloradd)) {//删除
                if((ResUseSysConstant.C_MACHINE).equals(ciclassifykey)){
                    delAndUpCiFlag ="1";
                    ciUpDelData(useSysOthers.get(i),upDelCilist,ciclassifykey);
                }
                alterFlag="2";
                alterData(useSysOthers.get(i),delAlterCilist);
            } else if ("3".equals(deloradd)) {//修改
                upCiFlag="3";
                ciUpData(useSysOthers.get(i),upCilist,ciclassifykey);
            }
        }
        if("1".equals(alterFlag) || "2".equals(alterFlag)){
            // 调用修改ci关系接口
            webServiceAlter(addAlterCilist,delAlterCilist,processInstId,url,headMap,
                    useSysOthers.get(0).getAppsysno());
            if("1".equals(delAndUpCiFlag)){
                // 物理机时将状态改为上架中
                webServicePostUp(upDelCilist,processInstId,url,headMap);
            }
        }
        // 调用修改接口
        callUp(upCiFlag,upCilist,processInstId,url,headMap);

    }

    /**
     * 调用修改接口
     * @param upCiFlag
     * @param upCilist
     * @param processInstId
     * @param url
     * @param headMap
     */
    void callUp(String upCiFlag,StringBuilder upCilist,String processInstId,String url,Map<String, String> headMap){
        if("3".equals(upCiFlag)){
            // 调用修改接口
            webServicePostUp(upCilist,processInstId,url,headMap);
        }
    }

    /**
     * 更新接口参数拼接
     * @param useSysOthers
     * @param upCilist
     * @param ciclassifykey
     */
    private void ciUpData(ResUseSysOther useSysOthers,StringBuilder upCilist,String ciclassifykey){
        upCilist.append(" {\"ciId\":\"" + useSysOthers.getCiid() + "\",") ;
        upCilist.append("\"ciName\":\"" + useSysOthers.getCiname() + "\",") ;
        upCilist.append("\"ciAttrList\":[") ;
        if ((ResUseSysConstant.C_MACHINE).equals(ciclassifykey)) {
            upCilist.append("{\"ciAttrKey\":\"A_20160318_000023\"," ) ;
            upCilist.append( "  \"ciAttrValue\":\""+ useSysOthers.getPhysicalresourcestats()+"\"}] }," ) ;
        }
        if (("C_VM").equals(ciclassifykey)) {
            upCilist.append("{\"ciAttrKey\":\"A_20160318_000024\",") ;
            upCilist.append("\"ciAttrValue\":\""+ useSysOthers.getLogicalresourcestats()+"\" } ] },") ;
        }
    }

    /**
     * 删除关系后更新接口参数拼接
     * @param useSysOthers
     * @param upDelCilist
     * @param ciclassifykey
     */
    private void ciUpDelData(ResUseSysOther useSysOthers,StringBuilder upDelCilist,String ciclassifykey){
        upDelCilist.append("{\"ciId\":\"" + useSysOthers.getCiid() + "\",") ;
        upDelCilist.append("\"ciAttrList\":[") ;
        if ((ResUseSysConstant.C_MACHINE).equals(ciclassifykey)) {
            upDelCilist.append("{\"ciAttrKey\":\"A_20160318_000023\"," ) ;
            upDelCilist.append( "  \"ciAttrValue\":\"7\"}] }," ) ;
        }
    }

    /**
     * 拼接新增系统关系接口参数
     * @param useSysOthers
     * @param alterCilist
     */
    private void alterData(ResUseSysOther useSysOthers,StringBuilder alterCilist){
        alterCilist.append(  "{\"ciId\":\""+useSysOthers.getCiid()+"\",");
        alterCilist.append( "\"releaxId\":\"23b2e6e9851641bfa833c2a804f9ff7e\",");
        alterCilist.append( "\"ciReleaxDirection\":\"to\"},");
    }

    /**
     * 调用cmdb新增接口
     * @param processInstId
     * @param url
     * @param headMap
     */
    private void webServiceAlter(StringBuilder addAlterCilist,StringBuilder delAlterCilist,  String processInstId,
                                 String url,Map<String, String> headMap,String appSysNo){
        String addAlterCilistStr=addAlterCilist.toString();
        if(addAlterCilistStr.length()>0){
            addAlterCilistStr=addAlterCilistStr.substring(0,addAlterCilistStr.length()-1);
        }
        String delAlterCilistStr=delAlterCilist.toString();
        if(delAlterCilistStr.length()>0){
            delAlterCilistStr=delAlterCilistStr.substring(0,delAlterCilistStr.length()-1);
        }
        String requestBody =" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                " <soapenv:Body>"+
                "<alterCiRelation xmlns=\"http://schemas.xmlsoap.org\">" +
                "   <alterUser>flow</alterUser>"+
                "   <flowId>"+processInstId +"</flowId>"+
                "   <ciId>"+appSysNo +"</ciId>"+
                "   <json>"+
                "      {\"addList\":["+addAlterCilistStr+"],"+
                "      \"deleteList\":["+delAlterCilistStr+"]}"+
                "   </json>"+
                "</alterCiRelation>"+
                " </soapenv:Body>"+
                "  </soapenv:Envelope>";

        log.info("调用科管修改ci关系接口的地址：{}，请求头：{}，请求体：{}",
                url,
                JSON.toJSONString(headMap),
                requestBody);
        String retData= HttpClientUtil.post(url, headMap, requestBody);
        // 解析返回报文
        Map<String, Object> xmlMap=xmlMap(retData);
        String respCode = xmlMap.get(ResUseSysConstant.RESPCODE).toString();
        if("0000".equals(respCode)){
            log.info("修改ci关系成功");
        } else{
            String respMsg = xmlMap.get("respMsg").toString();
            log.info("修改ci关系失败:"+respMsg);
        }
    }

    /**
     * 调用cmdb修改接口
     * @param upCilist
     * @param processInstId
     * @param url
     * @param headMap
     */
    private void webServicePostUp(StringBuilder upCilist,String processInstId,
                                  String url,Map<String, String> headMap){
        String upCilistStr =upCilist.toString();
        if(upCilistStr.length()>0){
            upCilistStr=upCilistStr.substring(0,upCilistStr.length()-1);
            String requestBody ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                    "<soapenv:Body>"+
                    "<updateCiData xmlns=\"http://schemas.xmlsoap.org\">" +
                    "  <json>"+
                    "      {\"ciList\":["+
                    upCilistStr+
                    "]}"+
                    "   </json>"+
                    "   <flowId>"+processInstId +"</flowId>"+
                    "   <alterUser>flow</alterUser>"+
                    "</updateCiData>"+
                    "</soapenv:Body>"+
                    "</soapenv:Envelope>";

            log.info("调用科管更新接口的地址：{}，请求头：{}，请求体：{}",
                    url,
                    JSON.toJSONString(headMap),
                    requestBody);
            String retData= HttpClientUtil.post(url, headMap, requestBody);
            // 解析返回报文
            Map<String, Object> xmlMap=xmlMap(retData);
            String respCode = xmlMap.get(ResUseSysConstant.RESPCODE).toString();
            log.info("retCode{}",respCode);
            if("0000".equals(respCode)){
                log.info("修改成功");
            } else{
                log.info("修改失败");
            }
        }
    }

    /**
     * 解析XMl
     * @param xml
     * @return map
     */
    public static Map<String, Object> xmlMap(String xml) {
        Map<String, Object> map = new HashMap<>();
        Document document;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
            return map;
        }
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        return getAllDocument(list, map);
    }

    /**
     * 报文解析
     * @param childElements
     * @param map
     * @return map
     */
    public static Map<String, Object> getAllDocument(List<Element>
                                                             childElements,Map<String, Object> map) {
        for (Element e : childElements ) {
            map.put(e.getName(), e.getText());
            if(!e.elements().isEmpty()){
                getAllDocument(e.elements(), map);
            }
        }
        return map;
    }

    /**
     * 流程撤回时回滚数据
     * @param resUseSysOther
     * @return int
     */
    public Integer updateUseSysFlowData(ResUseSysOther resUseSysOther){
        return myBatisDao.executionPojo(RESUSESYS, resUseSysOther,
                IMyBatisDao.SQL.UPDATE, "updateUseSysFlowData");
    }

    /**
     * 获取系统管理员A岗
     * @param appNo
     * @return String
     */
    public String querySystemUser(String appNo){
        String sysUser="";
        Map<String, String> headMap = new HashMap<>();
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail(ResUseSysConstant.RES_CMDB_URL, "02");
        String url = eos.getDictname();
        headMap.put("SOAPAction", url+"?wsdl");
        String requestBody ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                "<soapenv:Body>"+
                "<queryCiAttr xmlns=\"http://schemas.xmlsoap.org\">" +
                "   <ciId>"+appNo+"</ciId>"+
                "</queryCiAttr>"+
                "</soapenv:Body>"+
                "</soapenv:Envelope>";

        log.info("调用科管更新接口的地址：{}，请求头：{}，请求体：{}",
                url,
                JSON.toJSONString(headMap),
                requestBody);
        String retData= HttpClientUtil.post(url, headMap, requestBody);
        // 解析返回报文
        Map<String, Object> xmlMap=xmlMap(retData);
        String respCode = xmlMap.get(ResUseSysConstant.RESPCODE).toString();
        log.info("retCode{}",respCode);
        if("0000".equals(respCode)){
            String jsonStr = xmlMap.get("json")+"";
            JSONObject jsonData= JSON.parseObject(jsonStr);
            String ciAttrList=jsonData.getString("ciAttrList");
            JSONArray jsonArrCiList = JSON.parseArray(ciAttrList);
            for (int k = 0; k < jsonArrCiList.size(); k++) {
                JSONObject jsonDestCiAttrData =(JSONObject)jsonArrCiList.get(k);
                if ("A_20160426_000086".equals(jsonDestCiAttrData.getString(ResUseSysConstant.CIKEY))) {
                    sysUser =jsonDestCiAttrData.getString(ResUseSysConstant.CIVALUE); //系统管理员A岗
                }
            }
        }
        return sysUser;
    }

    /**
     * 生成流水号
     * @return flowid
     */
    @Override
    public String getFlowId(){
        return CodeGenerater.generate("JGZY", 14, "yyyyMMdd");
    }

}
