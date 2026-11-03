package com.work.ppm.application.services.workflowmanager.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.integratedmanager.PpmKqAnnualVacationDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.EosDictEntryDto;
import com.work.ppm.application.dto.workflowmanager.attendance.AbnormalAttendanceDetailDto;
import com.work.ppm.application.enums.FlowMessageEnums;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmKqAnnualVacationService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IAttendanceHandleService;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("attendanceHandleService")
public class AttendanceHandleService extends WorkflowOutputService implements IAttendanceHandleService {

    @Autowired
    private IPpmKqRecordService ppmKqRecordService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmKqAnnualVacationService ppmKqAnnualVacationService;

    @Value("${appUrl.sendRecord}")
    private String sendRecordValue;

    @Value("${appSendMessage.pubId}")
    private String pubId;

    @Override
    public Map<String, Object> abnormalAttendanceHandle(PpmKqRecordDto kqRecordDto) {
        PpmKqRecordDto ppmKqRecordDto = ppmKqRecordService.queryDetail(kqRecordDto);
        if (ppmKqRecordDto == null) {
            return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL.getMessage(), null);
        }

        if ("AM".equals(kqRecordDto.getDataType())){
            String whetherHandle = ppmKqRecordDto.getAmWhetherHandle();
            if (!"1".equals(whetherHandle)){
                return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL.getMessage(), null);
            }
            PpmKqRecordDto recordDto = new PpmKqRecordDto();
            recordDto.setId(ppmKqRecordDto.getId());
            recordDto.setAmWhetherHandle("2");
            recordDto.setAmWhetherAbnormal(kqRecordDto.getWhetherAbnormal());
            recordDto.setAmAbnormalReason(kqRecordDto.getAbnormalReason());
            recordDto.setAmRemark(kqRecordDto.getRemark());
            recordDto.setModifiedBy(kqRecordDto.getPmId());
            recordDto.setModifiedTime(new Date());
            recordDto.setInfoId(ppmKqRecordDto.getInfoId());
            ppmKqRecordService.updatePpmKqRecordAndInfo(recordDto);

            if ("3".equals(kqRecordDto.getAbnormalReason())){
                PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
                ppmKqAnnualVacationDto.setJobId(ppmKqRecordDto.getJobId());
                ppmKqAnnualVacationService.validAnnualVacationMinus(ppmKqAnnualVacationDto,0.5);
            }
            return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(), null);
        }else if ("PM".equals(kqRecordDto.getDataType())){
            String whetherHandle = ppmKqRecordDto.getPmWhetherHandle();
            if (!"1".equals(whetherHandle)){
                return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL.getMessage(), null);
            }
            PpmKqRecordDto recordDto = new PpmKqRecordDto();
            recordDto.setId(ppmKqRecordDto.getId());
            recordDto.setPmWhetherHandle("2");
            recordDto.setPmWhetherAbnormal(kqRecordDto.getWhetherAbnormal());
            recordDto.setPmAbnormalReason(kqRecordDto.getAbnormalReason());
            recordDto.setPmRemark(kqRecordDto.getRemark());
            recordDto.setModifiedBy(kqRecordDto.getPmId());
            recordDto.setModifiedTime(new Date());
            recordDto.setInfoId(ppmKqRecordDto.getInfoId());
            ppmKqRecordService.updatePpmKqRecordAndInfo(recordDto);

            if ("3".equals(kqRecordDto.getAbnormalReason())){
                PpmKqAnnualVacationDto ppmKqAnnualVacationDto = new PpmKqAnnualVacationDto();
                ppmKqAnnualVacationDto.setJobId(ppmKqRecordDto.getJobId());
                ppmKqAnnualVacationService.validAnnualVacationMinus(ppmKqAnnualVacationDto,0.5);
            }
            return returnMap(FlowMessageEnums.PASS.getCode(), FlowMessageEnums.PASS.getMessage(), null);
        }

        return returnMap(FlowMessageEnums.FAIL.getCode(), FlowMessageEnums.FAIL.getMessage(), null);
    }

    @Override
    public Map<String, Object> queryAbnormalAttendanceDetail(String pmId) {
        PpmKqRecordDto ppmKqRecordDto = new PpmKqRecordDto();
        ppmKqRecordDto.setPmId(pmId);
        ppmKqRecordDto.setWhetherHandle("1");
        List<PpmKqRecordDto> list = ppmKqRecordService.queryBaseInfoList(ppmKqRecordDto);

        EosDictEntry entry = new EosDictEntry();
        entry.setDicttypeid("KQ_RECORD_REASON_DICT");
        entry.setStatus(1L);
        List<EosDictEntryDto> eosDictEntryDtos = eosDictEntryService.getList(entry);

        list.forEach(record -> {
            //获取有效年假天数
            double validAnnualVacation =
                    record.getAnnualVacationDays() + record.getAnnualVacationSurplus();

            String abnormalReason = record.getAutoAbnormalReason();
            PpmKqRecordDto ppmKqRecordDto2 = new PpmKqRecordDto();
            ppmKqRecordDto2.setAttendanceDate(DateUtil.addOrSubDate(record.getAttendanceDate(),-1));
            ppmKqRecordDto2.setIsHoliday("0");
            ppmKqRecordDto2.setWhetherDaysOff("Y");
            ppmKqRecordDto2.setJobId(record.getJobId());
            PpmKqRecordDto ppmKqRecordDto3 = ppmKqRecordService.queryDetail(ppmKqRecordDto2);
            boolean flag = ppmKqRecordDto3 != null && "AM".equals(record.getDataType());

            List<Map> jsonArrays = new ArrayList<>();
            for (EosDictEntryDto item : eosDictEntryDtos){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",item.getDictid());
                jsonObject.put("name",item.getDictname());
                jsonObject.put("sortno",item.getSortno());

                boolean disableFlag = true;
                if ("12".equals(abnormalReason) && ("12".equals(item.getDictid()) ||
                        "15".equals(item.getDictid()) ||
                        ("13".equals(item.getDictid()) && "AM".equals(record.getDataType())))){
                    jsonObject.put("disabled",false);
                    jsonArrays.add(jsonObject);
                    disableFlag = false;
                }
                if ("13".equals(abnormalReason) && ("13".equals(item.getDictid()) || "15".equals(item.getDictid()))){
                    jsonObject.put("disabled",false);
                    jsonArrays.add(jsonObject);
                    disableFlag = false;
                }
                if ("14".equals(abnormalReason) && ("14".equals(item.getDictid()) ||
                        "15".equals(item.getDictid()) ||
                        ("12".equals(item.getDictid())))){
                    jsonObject.put("disabled",false);
                    jsonArrays.add(jsonObject);
                    disableFlag = false;
                }

                if (flag && "11".equals(item.getDictid())){
                    jsonObject.put("disabled",false);
                    jsonArrays.add(jsonObject);
                    disableFlag = false;
                }

                if (validAnnualVacation > 0 && "3".equals(item.getDictid())){
                    jsonObject.put("disabled",false);
                    jsonArrays.add(jsonObject);
                    disableFlag = false;
                }

                if (disableFlag){
                    jsonObject.put("disabled",true);
                    jsonArrays.add(jsonObject);
                }
            }
            record.setReasonSelect(jsonArrays);

            record.setDeptName(BusinessUtil.getDeptNameValue(record.getDeptName(),1));
        });

        List<AbnormalAttendanceDetailDto> zList = JsonUtil.jsonToList(list, AbnormalAttendanceDetailDto.class);
        zList.forEach(x-> {
            x.setWhetherHandle("未处理");
            x.setAbnormalReasonList(x.getReasonSelect());
            x.setReasonSelect(null);
        });
        return returnMap(FlowMessageEnums.PASS.getCode(),"",zList);
    }

    /**
     * 消息推送
     * @param jsonArray 经理工号
     */
    public void sendMessage(JSONArray jsonArray){
        String text = "外包人员异常考勤未处理，请及时登录手机工作平台处理";
        sendMassageXy(jsonArray,sendRecordValue,pubId,"5",null,text);
    }

}
