package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.vendormanager.OutsrcingPriceChgDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.vendormanager.OutsrcingPriceChg;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IOutsrcingPriceChgService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 单价调整 服务实现类
 * </p>
 *
 * @since 2022-12-18
 */

@Slf4j
@RequiredArgsConstructor
@Service("outsrcingPriceChgService")
public class OutsrcingPriceChgService extends BaseDaoSupper implements IOutsrcingPriceChgService {

    /**
     * 实体类路径
     */
    private static String OUTSRCINGPRICECHG= "com.work.ppm.application.model.vendormanager.OutsrcingPriceChg";
    /**
     * 员工查询
     */
    private final ICommEmployeeService commEmployeeService;
    /**
     * 工作流服务
     */
    private final WorkFlowHttpClientService workFlowHttpClientService;
    /**
     * 外包花名册
     */
    private final IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 流程定义ID
     */
    private static final String FLOW_ID = "";
    /**
     * 流程名称
     */
    private static final String FLOW_TITLE = "";

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<OutsrcingPriceChgDto> queryPager(OutsrcingPriceChgDto outsrcingPriceChg, Pager pager) {
        if(StringUtils.isNotEmpty(outsrcingPriceChg.getDeptId())){
            outsrcingPriceChg.setDeptId(
                    outsrcingPriceChg.getDeptId().replace("dep",""));
        }
        Map<String, Object> map = StringUtil.objectTOMap(outsrcingPriceChg, OutsrcingPriceChgDto.class);
        Map<String, Object> map2 = StringUtil.objectTOMap(outsrcingPriceChg, OutsrcingPriceChg.class);
        map.putAll(map2);
        myBatisDao.selectPager(OUTSRCINGPRICECHG, map, pager, "queryPagerList");
        List<OutsrcingPriceChgDto> dtoList =
                JsonUtil.jsonToList(pager.getList(), OutsrcingPriceChgDto.class);
        dtoList.forEach(x -> x.setDeptName(BusinessUtil.getDeptNameValue(x.getDeptName(),1)));
        return dtoList;
    }

    @Override
    public List<OutsrcingPriceChg> queryList(OutsrcingPriceChgDto outsrcingPriceChg) {
        List<OutsrcingPriceChg> res = myBatisDao.selectListT(OUTSRCINGPRICECHG, outsrcingPriceChg, "getList");
        return res;
    }


    @Override
    public OutsrcingPriceChgDto queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        OutsrcingPriceChgDto dto = myBatisDao.selectClass(OUTSRCINGPRICECHG, map, "selectOneById");
        dto.setDeptName(BusinessUtil.getDeptNameValue(dto.getDeptName(),1));
        return dto;
    }


    @Override
    public List<OutsrcingPriceChgDto> queryListByInsId(String instanceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceId", instanceId);
        List<OutsrcingPriceChgDto> dtoList = myBatisDao.selectListT(OUTSRCINGPRICECHG, map, "selectListByInstanceId");
        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOutsrcingPriceChg(OutsrcingPriceChg outsrcingPriceChg) {
        return myBatisDao.executionPojo(OUTSRCINGPRICECHG, outsrcingPriceChg, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchAddOutsrcingPriceChg(List<OutsrcingPriceChgDto> outsrcingPriceChgList) {
        return myBatisDao.executionPojo(OUTSRCINGPRICECHG, outsrcingPriceChgList, IMyBatisDao.SQL.INSTER, "batchAdd");
    }

    @Override
    public int updateOutsrcingPriceChg(OutsrcingPriceChg outsrcingPriceChg) {
        return myBatisDao.executionPojo(OUTSRCINGPRICECHG, outsrcingPriceChg, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public void priceCron(){
        OutsrcingPriceChgDto outsrcingPriceChg= new OutsrcingPriceChgDto();
        outsrcingPriceChg.setIsDel("0");
        outsrcingPriceChg.setDispatched("0");
        outsrcingPriceChg.setApprovalStatus("1");
        List<OutsrcingPriceChg> list = queryList(outsrcingPriceChg);
        DecimalFormat decimalFormat = new DecimalFormat("###########.#######");
        list.stream().forEach(x ->{
            Date chgDate = DateUtil.strToDate( x.getChgDate(), "yyyy-MM-dd");
            if (chgDate.getTime() <= System.currentTimeMillis()) {
                PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
                basic.setJobId(x.getUserCode());
                basic.setPrice(decimalFormat.format(x.getPostPrice()));
                basic.setGrade(x.getServiceYear());
                // 防止整表更新
                if (Objects.isNull(basic.getJobId()) ||"".equalsIgnoreCase(basic.getJobId())) {return;}
                ppmOutsourcingBasicService.updatePpmOutsourcingBasic(basic);
                x.setDispatched("1");
                this.updateOutsrcingPriceChg(x);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByInstanceId(OutsrcingPriceChg outsrcingPriceChg) {
        return myBatisDao.executionPojo(OUTSRCINGPRICECHG, outsrcingPriceChg,
                                         IMyBatisDao.SQL.UPDATE, "updateByInstanceId");
    }
}