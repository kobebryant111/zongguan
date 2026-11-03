package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingInfoPriceDto;
import com.work.ppm.application.enums.DataIsDelEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingInfoPrice;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingInfoPriceInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingInfoPriceService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 外包人员价格
 */
@Service("ppmOutsourcingInfoPriceService")
public class PpmOutsourcingInfoPriceService extends BaseDaoSupper implements IPpmOutsourcingInfoPriceService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGINFOPRICE =
            "com.work.ppm.application.model.integratedmanager.PpmOutsourcingInfoPrice";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingInfoPrice,PpmOutsourcingInfoPrice.class);
        myBatisDao.selectPager(PPMOUTSOURCINGINFOPRICE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        Long id = getNextSequence(PpmOutsourcingInfoPrice.class);
        // 如果需要请set 主键id
        //ppmOutsourcingInfoPrice.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGINFOPRICE, ppmOutsourcingInfoPrice, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingInfoPrice queryDetail(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        return myBatisDao.selectClass(PPMOUTSOURCINGINFOPRICE, ppmOutsourcingInfoPrice,"queryDetail");
    }

    @Override
    public Integer updatePpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        return myBatisDao.executionPojo(
                PPMOUTSOURCINGINFOPRICE, ppmOutsourcingInfoPrice, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmOutsourcingInfoPrice(PpmOutsourcingInfoPrice ppmOutsourcingInfoPrice) {
        return myBatisDao.executionPojo(
                PPMOUTSOURCINGINFOPRICE, ppmOutsourcingInfoPrice, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public PpmOutsourcingInfoPriceDto getOutsourcPrices(Long id ,String position) {
        PpmOutsourcingInfoPrice entity = new PpmOutsourcingInfoPrice();
        entity.setId(id);
        entity.setPosition(position);
        PpmOutsourcingInfoPrice item =
                myBatisDao.selectClass(PPMOUTSOURCINGINFOPRICE, entity, "getOutsourcPrices");
        PpmOutsourcingInfoPriceDto priceDto = JsonUtil.jsonToClass(item, PpmOutsourcingInfoPriceDto.class);
        return priceDto;
    }
    @Override
    public List<PpmOutsourcingInfoPriceDto> queryList(PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPrice) {
        PpmOutsourcingInfoPrice map = JsonUtil.jsonToClass(ppmOutsourcingInfoPrice,PpmOutsourcingInfoPrice.class);
        List<PpmOutsourcingInfoPriceInfo> list=
                myBatisDao.selectListT(PPMOUTSOURCINGINFOPRICE, map,"getOutsourcPrices");
        List<PpmOutsourcingInfoPriceDto> listDto = JsonUtil.jsonToList(list, PpmOutsourcingInfoPriceDto.class);
        return listDto;
    }

    @Override
    public List<PpmOutsourcingInfoPriceDto> exportExcel(PpmOutsourcingInfoPriceDto ppmOutsourcingInfoPrice) {
        ppmOutsourcingInfoPrice.setIsDel(DataIsDelEnums.NO_DEL.getCode());
        List<PpmOutsourcingInfoPriceDto> list = queryList(ppmOutsourcingInfoPrice);
        list = list.stream().filter(x-> StringUtil.isNotEmpty(x.getOutsourcname())).collect(Collectors.toList());
        list.forEach(infoPriceDto -> {
            String firstStartPrice =
                    infoPriceDto.getFirststartprie() == null ? "" : infoPriceDto.getFirststartprie()+"";
            String firstEndPrice =
                    infoPriceDto.getFirstendprie() == null ? "" : infoPriceDto.getFirstendprie()+"";
            String secondStartPrice =
                    infoPriceDto.getSecondstartprie() == null ? "" : infoPriceDto.getSecondstartprie()+"";
            String secondEndPrice =
                    infoPriceDto.getSecondendprie() == null ? "" : infoPriceDto.getSecondendprie()+"";
            String thirdStartPrice =
                    infoPriceDto.getThirdstartprie() == null ? "" : infoPriceDto.getThirdstartprie()+"";
            String thirdEndPrice =
                    infoPriceDto.getThirdendprie() == null ? "" : infoPriceDto.getThirdendprie()+"";
            String fourthStartPrice =
                    infoPriceDto.getFourthstartprie() == null ? "" : infoPriceDto.getFourthstartprie()+"";
            String fourthEndPrice =
                    infoPriceDto.getFourthendprie() == null ? "" : infoPriceDto.getFourthendprie()+"";
            infoPriceDto.setFirstPrice(firstStartPrice + "-" + firstEndPrice);
            infoPriceDto.setSecondPrice(secondStartPrice + "-" + secondEndPrice);
            infoPriceDto.setThirdPrice(thirdStartPrice + "-" + thirdEndPrice);
            infoPriceDto.setFourthPrice(fourthStartPrice + "-" + fourthEndPrice);
        });

        return list;
    }

    @Override
    public int deleteInId(List<PpmOutsourcingInfoPriceDto> infoDtos, String orgCode) {

        PpmOutsourcingInfoPriceDto outsourcingInfoPriceDto = new PpmOutsourcingInfoPriceDto();
        if(infoDtos != null && !infoDtos.isEmpty()){
            StringBuffer delVal = new StringBuffer();
            infoDtos.stream().filter(infoDto -> infoDto.getId() != null).collect(Collectors.toList()).
                    forEach(infoDto -> delVal.append("'").append(infoDto.getId()).append("'").append(","));

            if(StringUtils.isNotEmpty(delVal)){
                outsourcingInfoPriceDto.setDelVal(delVal.substring(0,delVal.length()-1));
            }else {
                outsourcingInfoPriceDto.setDelVal(null);
            }
        }

        outsourcingInfoPriceDto.setOrgcode(orgCode);

        return myBatisDao.executionPojo(
                PPMOUTSOURCINGINFOPRICE, outsourcingInfoPriceDto, IMyBatisDao.SQL.UPDATE, "deleteInId");
    }
}
