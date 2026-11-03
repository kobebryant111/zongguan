package com.work.ppm.application.services.integratedmanager.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.ppm.application.dto.integratedmanager.PpmKqInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContract;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContractRequest;
import com.work.ppm.application.model.integratedmanager.PpmSupplierContractResponse;
import com.work.ppm.application.model.integratedmanager.PpmSupplierOutsourcing;
import com.work.ppm.application.model.integratedmanager.PpmSupplierOutsourcingRequest;
import com.work.ppm.application.model.integratedmanager.ext.PpmWorkloadKqInfoView;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmKqInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmSupplierContractMapper;
import com.work.ppm.application.services.integratedmanager.IPpmSupplierOutsourcingMapper;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.SnowflakeIdWorker;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 供应商合同管理实现
 *
 */
@Service
@Slf4j
public class PpmSupplierContractService {
    /**
     * IPpmSupplierContractMapper
     */
    @Autowired
    IPpmSupplierContractMapper supplierContractDao;
    /**
     * PpmSupplierContract Impl
     */
    @Autowired
    PpmSupplierContractMapperImpl supplierContractMapperImpl;
    /**
     * PpmSupplierOutsourcingMapper
     */
    @Autowired
    IPpmSupplierOutsourcingMapper supplierOutsourcingDao;
    /**
     * supplierOutsourcingMapperImpl
     */
    @Autowired
    PpmSupplierOutsourcingMapperImpl supplierOutsourcingMapperImpl;
    /**
     * 外包人员信息服务
     */
    @Autowired
    IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 考勤记录服务
     */
    @Autowired
    IPpmKqRecordService ppmKqRecordService;
    /**
     * 考勤信息服务
     */
    @Autowired
    IPpmKqInfoService ppmKqInfoService;
    /**
     * 字典
     */
    @Autowired
    IEosDictEntryService eosDictEntryService;

    /**
     * 供应商合同分页查询
     *
     * @param ppmSupplierContract 对象
     * @param pager               分页
     */
    public void queryPagerList(PpmSupplierContract ppmSupplierContract, Pager pager) {
        supplierContractMapperImpl.queryPagerList(ppmSupplierContract, pager);
        List<PpmSupplierContractResponse> contractList = (List<PpmSupplierContractResponse>) pager.getList();
        if (CollectionUtil.isNotEmpty(contractList)) {
            contractList.forEach(item -> {
                Integer count = supplierOutsourcingMapperImpl.getOutsourceNum(item.getId());
                item.setOutsourceNum(count);
            });
        }
    }

    /**
     * 查询合同详情
     *
     * @param request 对象
     * @return item
     */
    public PpmSupplierContractResponse queryDetail(PpmSupplierContractRequest request) {
        List<PpmSupplierContractResponse> list = supplierContractDao.queryDetail(request);
        return CollectionUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * 新增或修改合同
     *
     * @param request 对象
     * @return 结果
     */
    public boolean updateSupplierContract(PpmSupplierContractRequest request) {
        // 修改
        if (request.getId() != null) {
            PpmSupplierContract contract = supplierContractMapperImpl.getById(request.getId());
            if (!StrUtil.equals(contract.getContractNo(), request.getContractNo())) {
                // 校验合同信息
                preCheckContract(request);
            }
            // 校验级别人数修改
            checkContractLevelNum(request, contract);
            return supplierContractMapperImpl.updateById(request);
        }

        // 校验合同信息
        preCheckContract(request);
        // 查询是否存在失效合同
        LambdaQueryWrapper<PpmSupplierContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PpmSupplierContract::getContractNo, request.getContractNo());
        PpmSupplierContract contract = supplierContractDao.selectOne(queryWrapper);

        if (contract != null) {
            request.setId(contract.getId());
            supplierContractDao.deleteById(contract.getId());
        } else {
            request.setId(SnowflakeIdWorker.getSnowId());
        }
        request.setContractState("Y");
        request.setCreateDate(new Date());
        return supplierContractMapperImpl.save(request);

    }

    /**
     * 校验合同信息
     *
     * @param request 实体
     */
    private void preCheckContract(PpmSupplierContract request) {
        // 是否存在生效合同
        LambdaQueryWrapper<PpmSupplierContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PpmSupplierContract::getContractNo, request.getContractNo());
        queryWrapper.eq(PpmSupplierContract::getContractState, "Y");
        Integer count = supplierContractDao.selectCount(queryWrapper);
        if (count > 0) {
            log.error("{}合同已存在{}", request.getContractNo(), JSONUtil.toJsonStr(request));
            throw new BizException(ApiCode.PARAMETER_ERROR, "合同编号已存在");
        }
    }

    /**
     * 校验合同级别人数
     *
     * @param request  请求
     * @param contract 合同数据库记录
     */
    private void checkContractLevelNum(PpmSupplierContract request, PpmSupplierContract contract) {
        checkLevelNum(request, contract, request.getJuniorNum(), contract.getJuniorNum(),
                "junior", "初级");
        checkLevelNum(request, contract, request.getIntermediateNum(), contract.getIntermediateNum(),
                "intermediate", "中级");
        checkLevelNum(request, contract, request.getSeniorNum(), contract.getSeniorNum(),
                "senior", "高级");
        checkLevelNum(request, contract, request.getExpertNum(), contract.getExpertNum(),
                "expert", "资深");
    }

    /**
     * 通用级别人数校验逻辑
     *
     * @param request   请求
     * @param contract  数据库记录
     * @param newNum    新值
     * @param oldNum    旧值
     * @param levelType 级别类型
     * @param levelName 级别名称
     */
    private void checkLevelNum(PpmSupplierContract request, PpmSupplierContract contract,
                               Integer newNum, Integer oldNum, String levelType, String levelName) {
        // 数值发生变化时
        if (Objects.equals(newNum, oldNum)) {
            return;
        }

        int assignedNum = supplierOutsourcingMapperImpl
                .getLevelNum(String.valueOf(contract.getId()), levelType);

        if (assignedNum > 0 && (newNum == null || newNum < assignedNum)) {
            log.warn("合同[{}]修改{}人数拦截：已关联[{}]人，新限制[{}]人。请求详情: {}",
                    request.getContractNo(), levelName, assignedNum, newNum, JSONUtil.toJsonStr(request));
            throw new BizException(ApiCode.PARAMETER_ERROR, "请先解除外包关联再修改" + levelName + "限制人数");
        }
    }

    /**
     * 删除合同
     *
     * @param request 对象
     * @return 结果
     */
    public boolean deletePpmSupplierContract(PpmSupplierContractRequest request) {
        // 逻辑删除
        PpmSupplierContract contract = new PpmSupplierContract();
        contract.setId(request.getId());
        contract.setContractState("N");
        return supplierContractMapperImpl.updateById(contract);
    }

    /**
     * 查询合同关联外包人员及合同详情
     *
     * @param request 对象
     * @return res
     */
    public PpmSupplierContractResponse queryOutsourceList(PpmSupplierContractRequest request) {
        // 查询合同详情
        PpmSupplierContractResponse contractDetail = queryDetail(request);

        // 查询关联外包人员
        LambdaQueryWrapper<PpmSupplierOutsourcing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PpmSupplierOutsourcing::getRelateId, String.valueOf(request.getId()));
        queryWrapper.eq(request.getRelateState() != null,
                PpmSupplierOutsourcing::getRelateState, request.getRelateState())
                .orderByDesc(PpmSupplierOutsourcing::getCreateDate);
        List<PpmSupplierOutsourcing> outsourceList = supplierOutsourcingDao.selectList(queryWrapper);

        if (CollectionUtil.isNotEmpty(outsourceList)) {
            // 有效关联
            List<PpmSupplierOutsourcing> validRelateList = outsourceList.stream()
                    .filter(item -> "Y".equals(item.getRelateState()))
                    .collect(Collectors.toList());
            List<PpmSupplierOutsourcing> inValidRelateList = outsourceList.stream()
                    .filter(item -> !"Y".equals(item.getRelateState()))
                    .collect(Collectors.toList());
            validRelateList.addAll(inValidRelateList);
            contractDetail.setOutsourceList(validRelateList);
        }
        return contractDetail;
    }

    /**
     * 新增合同与外包人员关联
     *
     * @param request 对象
     * @return boolean
     */
    public boolean addOutsource(PpmSupplierOutsourcingRequest request) {
        if (request.getId() != null) {
            //更新外包关联信息
            request.setUpdateDate(new Date());
            return supplierOutsourcingMapperImpl.updateById(request);
        }
        // 新增关联校验
        preCheckOutsource(request);

        request.setId(SnowflakeIdWorker.getSnowId());
        request.setCreateDate(new Date());
        return supplierOutsourcingMapperImpl.save(request);
    }

    /**
     * 是否关联校验通过
     *
     * @param request 对象
     */
    private void preCheckOutsource(PpmSupplierOutsourcingRequest request) {
        // 查询合同是否有效
        PpmSupplierContract contract = supplierContractDao.selectById(request.getRelateId());
        if (contract == null || !"Y".equals(contract.getContractState())) {
            log.error("{}该开发人员关联的合同不存在{}", request.getJobId(), JSONUtil.toJsonStr(request));
            throw new BizException(ApiCode.PARAMETER_ERROR, "合同不存在");
        }

        // 是否关联其他合同
        List<PpmSupplierOutsourcing> outsourcingList = supplierOutsourcingMapperImpl
                .getRelateByJobId(request.getJobId());
        if (CollectionUtil.isNotEmpty(outsourcingList)) {
            long count = outsourcingList.stream().filter(item -> "Y".equals(item.getRelateState())).count();
            if (count > 0) {
                log.error("{}该开发人员已关联合同{}", request.getJobId(), JSONUtil.toJsonStr(request));
                throw new BizException(ApiCode.PARAMETER_ERROR, "开发人员已关联合同");
            }
            // 在场时段校验
            boolean hasTimeOverlap = hasTimeConflict(outsourcingList,
                    request.getWorkBeginDate(), request.getWorkEndDate());
            if (hasTimeOverlap) {
                log.error("{}该员工在同一时间段内已关联其他合同{}", request.getJobId(), JSONUtil.toJsonStr(request));
                throw new BizException(ApiCode.PARAMETER_ERROR, "同一时间段内开发人员已关联合同");
            }
        }

        // 资质人数限制校验
        String evaluationLevel = request.getEvaluationLevel();
        if (!StringUtil.isNotBlank(evaluationLevel)) {
            return;
        }

        Integer limitNum = 0;
        if ("junior".equals(evaluationLevel)) {
            //关联初级人数限制
            limitNum = contract.getJuniorNum();
        } else if ("intermediate".equals(evaluationLevel)) {
            //关联中级人数限制
            limitNum = contract.getIntermediateNum();
        } else if ("senior".equals(evaluationLevel)) {
            //关联高级人数限制
            limitNum = contract.getSeniorNum();
        } else if ("expert".equals(evaluationLevel)) {
            //关联资深人数限制
            limitNum = contract.getExpertNum();
        }
        int levelNum = supplierOutsourcingMapperImpl.getLevelNum(request.getRelateId(), evaluationLevel);
        if (limitNum == null || levelNum >= limitNum) {
            log.error("{}该级别开发人员超限{}", request.getJobId(), JSONUtil.toJsonStr(request));
            throw new BizException(ApiCode.PARAMETER_ERROR, "该级别人数超限");
        }

    }

    /**
     * 检查外包员工时间段是否有冲突
     *
     * @param outsourcingList  关联合同
     * @param requestBeginDate 开始日期
     * @param requestEndDate   结束日期
     * @return 是否冲突
     */
    private boolean hasTimeConflict(List<PpmSupplierOutsourcing> outsourcingList,
                                    Date requestBeginDate, Date requestEndDate) {
        Date checkEndDate = requestEndDate != null ? requestEndDate : new Date();
        long requestBeginMillis = requestBeginDate.getTime();
        long checkEndMillis = checkEndDate.getTime();

        return outsourcingList.stream()
                .filter(item -> !"Y".equals(item.getRelateState()))
                .anyMatch(item -> {
                    Date itemEndDate = item.getWorkEndDate() != null ? item.getWorkEndDate() : new Date();
                    long itemBeginMillis = item.getWorkBeginDate().getTime();
                    long itemEndMillis = itemEndDate.getTime();

                    // 判断是否有交集
                    return !(itemEndMillis < requestBeginMillis || itemBeginMillis > checkEndMillis);
                });
    }

    /**
     * 解除合同与外包人员关联
     *
     * @param request 对象
     * @return boolean
     */
    public boolean deleteOutsource(PpmSupplierOutsourcingRequest request) {
        Date date = new Date();
        List<PpmSupplierOutsourcing> list = request.getList();
        list.stream().forEach(item -> {
            item.setRelateState("N");
            item.setUpdateDate(date);
        });
        return supplierOutsourcingMapperImpl.updateBatchById(list);
    }

    /**
     * 删除合同与外包人员关联
     *
     * @param request 对象
     * @return boolean
     */
    public boolean removeOutsource(PpmSupplierOutsourcingRequest request) {
        List<PpmSupplierOutsourcing> list = request.getList();
        List<Long> idList = list.stream()
                .map(PpmSupplierOutsourcing::getId)
                .collect(Collectors.toList());
        return supplierOutsourcingMapperImpl.removeByIds(idList);
    }

    /**
     * 供应商名单导出
     *
     * @param request 对象
     * @return list
     */
    public List<PpmSupplierContractResponse> exportSupplier(PpmSupplierContractRequest request) {
        return supplierContractMapperImpl.exportSupplier(request);
    }

    /**
     * 工作量明细导出
     * 1. 查询外包关联合同（考勤年月内）
     * 2. 截止时间为空默认当前时间，根据工号查询起始时间和截止时间内的考勤记录
     * 3. 统计考勤，已经结束的外包人员设置考勤标记，记录考勤数据
     *
     * @param ppmKqInfoDto 实体
     * @param user         用户信息
     * @return 工作量考勤视图列表
     */
    public List<PpmWorkloadKqInfoView> exportWorkload(PpmKqInfoDto ppmKqInfoDto,
                                                      CommOperatorDoLoginDto user) {
        // 1. 处理权限
        handleAuthCode(ppmKqInfoDto, user);

        // 2. 转换查询参数
        Map<String, Object> paramMap = buildQueryParamMap(ppmKqInfoDto);
        if ("0".equals(paramMap.get("dateCheck"))) {
            log.info("考勤年月开始日期大于当前时间无考勤记录");
            return Collections.emptyList();
        }

        // 3. 查询外包关联合同
        List<PpmWorkloadKqInfoView> outsourcingList = supplierOutsourcingDao.selectWorkload(paramMap);

        // 4. 填充考勤统计数据
        if (CollectionUtil.isNotEmpty(outsourcingList)) {
            fillAttendanceStatistics(outsourcingList, paramMap);
        }

        return outsourcingList;
    }

    /**
     * 构建查询参数 Map
     *
     * @param dto 请求
     * @return 查询参数
     */
    private Map<String, Object> buildQueryParamMap(PpmKqInfoDto dto) {
        Map<String, Object> paramMap = JsonUtil.objectTOMap(dto, PpmKqInfoDto.class);

        // 处理考勤月份时间范围
        if (StringUtil.isStringNotEmpty(dto.getAttendanceMonth())
                && dto.getAttendanceMonth().contains(",")) {
            String[] months = dto.getAttendanceMonth().split(",");
            if (months.length < 2) {
                return paramMap;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

                // 起始日期：月初
                YearMonth startYearMonth = YearMonth.parse(months[0], formatter);
                LocalDate startDate = startYearMonth.atDay(1);

                // 结束日期：月末 +1 天（包含月末当天）
                YearMonth endYearMonth = YearMonth.parse(months[1], formatter);
                LocalDate endDate = endYearMonth.atEndOfMonth().plusDays(1);

                paramMap.put("dateCheck", startDate.isAfter(LocalDate.now()) ? "0" : "1");
                paramMap.put("startDate", Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                paramMap.put("endDate", Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } catch (DateTimeParseException e) {
                log.error("考勤月份格式解析失败：{}", dto.getAttendanceMonth(), e);
            }
        }

        return paramMap;
    }

    /**
     * 填充考勤统计数据
     *
     * @param outsourcingList 关联外包
     * @param paramMap        查询参数
     */
    private void fillAttendanceStatistics(List<PpmWorkloadKqInfoView> outsourcingList, Map<String, Object> paramMap) {
        // 1. 加载评价等级字典
        Map<String, String> levelDict = loadEvaluationLevelDict();

        // 2. 查询考勤记录
        outsourcingList.stream().forEach(item -> {
            List<PpmKqRecordDto> ppmKqRecords = queryAttendanceRecords(item);
            // 3. 统计考勤数据
            processAttendanceItem(item, levelDict, ppmKqRecords, paramMap);
        });

    }

    /**
     * 加载评价等级字典
     *
     * @return map
     */
    private Map<String, String> loadEvaluationLevelDict() {
        List<EosDictEntry> dictList =
                eosDictEntryService.getTypeidForList(new String[]{"EVALUATION_LEVEL"});

        return dictList.stream()
                .collect(Collectors.toMap(
                        EosDictEntry::getDictid,
                        EosDictEntry::getDictname,
                        (v1, v2) -> v1,  // 重复时取第一个
                        HashMap::new
                ));
    }

    /**
     * 查询关联外包考勤记录
     *
     * @param item 关联外包
     * @return 考勤记录
     */
    private List<PpmKqRecordDto> queryAttendanceRecords(PpmWorkloadKqInfoView item) {

        // 设置默认结束日期
        if (item.getWorkEndDate() == null) {
            item.setWorkEndDate(new Date());
        }

        // 查询考勤记录
        PpmKqRecordDto queryParams = new PpmKqRecordDto();
        queryParams.setJobId(item.getJobId());
        queryParams.setAttendanceDateSt(DateUtil.dateToIsoDateFormat(item.getIntersectStart()));
        queryParams.setAttendanceDateEnd(DateUtil.dateToIsoDateFormat(item.getIntersectEnd()));

        return ppmKqRecordService.getAttendanceRecord(queryParams);
    }

    /**
     * 统计关联外包考勤数据
     *
     * @param item                关联外包
     * @param evaluationLevelDict 等级
     * @param ppmKqRecords        考勤记录
     * @param paramMap            查询参数
     */
    private void processAttendanceItem(PpmWorkloadKqInfoView item, Map<String, String> evaluationLevelDict,
                                       List<PpmKqRecordDto> ppmKqRecords, Map<String, Object> paramMap) {

        //每月可减免的异常扣减天数
        long monthlyReasonableAbnormalDays = (long)paramMap.get("monthlyReasonableAbnormalDays");
        //忘记打卡次数
        long forgetClockInNumber = 0;
        //迟到次数
        long beLateNumber = 0;
        //早退次数
        long leaveEarlyNumber = 0;
        //请假半天次数
        long leaveNumber = 0;
        //请假一天次数
        long leaveDoubleNumber = 0;
        //新请假半天次数
        long leaveNumberNew = 0;
        //加班时长 小时
        double overtimeHours = 0.0;
        //调休天数
        double cumulativeOffDays = 0.0;
        //累计工作人月，按月累加
        double cumulativeWorkingMonth = 0.0;
        //应出勤天数
        double requiredAttendanceDays = 0.0;
        //其他异常次数
        long otherNumber = 0;

        /**
         * 考勤异常原因
         * 已失效：0-忘记打卡 1-迟到 2-早退 3-年假半天 5-事假半天 6-事假一天 7-旷工半天
         * 生效中：11-调休 12-忘打卡 13-迟到 14-早退 15-事假半天 16-其他 17-正常出勤
         */
        if (CollectionUtil.isNotEmpty(ppmKqRecords)) {

            // 0. 其他异常次数
            otherNumber = judgeAbnormalNumber(ppmKqRecords, "16");
            item.setOtherNumber(otherNumber);

            // 1. 累计请假天数
            leaveNumber = judgeAbnormalNumber(ppmKqRecords, "5");
            leaveDoubleNumber = judgeAbnormalNumber(ppmKqRecords, "6");
            leaveNumberNew = judgeAbnormalNumber(ppmKqRecords, "15");
            item.setCumulativeLeaveDays((leaveNumber + leaveNumberNew) * 0.5 + leaveDoubleNumber);

            // 2. 忘记打卡次数
            forgetClockInNumber = judgeAbnormalNumber(ppmKqRecords, "0")
                    + judgeAbnormalNumber(ppmKqRecords, "12");
            item.setForgetClockInNumber(forgetClockInNumber);

            // 3. 迟到次数
            beLateNumber = judgeAbnormalNumber(ppmKqRecords, "1")
                    + judgeAbnormalNumber(ppmKqRecords, "13");
            item.setBeLateNumber(beLateNumber);

            // 4. 早退次数
            leaveEarlyNumber = judgeAbnormalNumber(ppmKqRecords, "2")
                    + judgeAbnormalNumber(ppmKqRecords, "14");
            item.setLeaveEarlyNumber(leaveEarlyNumber);

            // 6. 加班时长
            overtimeHours = ppmKqRecords.stream().filter(x -> x.getOvertimeHours() != null).
                    mapToDouble(PpmKqRecordDto::getOvertimeHours).sum();
            item.setOvertimeHours(BigDecimal.valueOf(overtimeHours / 60)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue());

            // 7. 调休次数
            cumulativeOffDays = judgeAbnormalNumber(ppmKqRecords, "11");
            item.setCumulativeOffDays(cumulativeOffDays / 2);

            // 8. 计算累计工作人月 + 实际出勤天数 + 异常扣减天数
            Map<Long, PpmWorkloadKqInfoView> result = calculateByInfoId(ppmKqRecords, monthlyReasonableAbnormalDays);
            // 按月累加
            cumulativeWorkingMonth = result.values().stream()
                    .mapToDouble(s -> s.getCumulativeWorkingMonth() != null ? s.getCumulativeWorkingMonth() : 0.0)
                    .sum();
            // 保留两位小数
            item.setCumulativeWorkingMonth(BigDecimal.valueOf(cumulativeWorkingMonth)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue());

            // 实际出勤天数 按月累加
            double actualAttendanceDays = result.values().stream()
                    .mapToDouble(s -> s.getActualAttendanceDays() != null ? s.getActualAttendanceDays() : 0.0)
                    .sum();
            item.setActualAttendanceDays(actualAttendanceDays);

            // 异常扣减天数 按月累加
            double exceptionalDeductionNumber = result.values().stream()
                    .mapToDouble(s -> s.getExceptionalDeductionNumber() != null ? s.getExceptionalDeductionNumber() : 0.0)
                    .sum();
            item.setExceptionalDeductionNumber(exceptionalDeductionNumber);

            ppmKqRecords.clear();
        }

        // 9. 计算应出勤天数
        requiredAttendanceDays = ppmKqInfoService.queryCalendarPeriod(
                paramMap.get("startDate") != null ? (Date) paramMap.get("startDate") : item.getIntersectStart(),
                paramMap.get("endDate") != null ? (Date) paramMap.get("endDate") : item.getIntersectEnd(), "N");
        item.setRequiredAttendanceDays(requiredAttendanceDays);

        // 10. 转换评价等级
        if (StringUtil.isNotBlank(item.getEvaluationLevel())) {
            item.setEvaluationLevel(evaluationLevelDict.get(item.getEvaluationLevel()));
        }

        // 11. 设置在场周期
        item.setOnSitePeriod(formatDateRange(item.getWorkBeginDate(), item.getWorkEndDate()));
    }

    /**
     * 计算次数
     *
     * @param list   集合
     * @param dictId 字典dictId
     * @return long
     */
    private long judgeAbnormalNumber(List<PpmKqRecordDto> list, final String dictId) {
        return
                list.stream().filter(x -> "0".equals(x.getIsHoliday()))
                        .filter(x -> judgeAbnormalReason(x.getAmAbnormalReason(), dictId))
                        .filter(x -> judgeWhetherHandle(x.getAmWhetherHandle(),"2"))//已处理
                        .count() +
                list.stream().filter(x -> "0".equals(x.getIsHoliday()))
                        .filter(x -> judgeAbnormalReason(x.getPmAbnormalReason(), dictId))
                        .filter(x -> judgeWhetherHandle(x.getPmWhetherHandle(),"2"))//已处理
                        .count();
}

    /**
     * 判断是否处理是否为已处理
     * @param whetherHandle 是否处理
     * @param key 参数
     * @return boolean
     */
    public boolean judgeWhetherHandle(String whetherHandle,String key){
        if(StringUtils.isNotEmpty(whetherHandle)){
            return whetherHandle.equals(key);
        }
        return false;
    }

    /**
     * 判断异常原因是否包含参数
     *
     * @param abnormalReason 异常原因
     * @param key            参数
     * @return boolean
     */
    public boolean judgeAbnormalReason(String abnormalReason, String key) {
        if (StringUtils.isNotEmpty(abnormalReason)) {
            if (abnormalReason.contains("/")) {
                return abnormalReason.contains(key);
            } else {
                return abnormalReason.equals(key);
            }

        }
        return false;
    }

    /**
     * 按 INFO_ID 分组计算考勤统计
     *
     * @param list 考勤记录
     * @param monthlyReasonableAbnormalDays 可减免的异常扣减天数
     * @return 按月分组统计
     */
    public Map<Long, PpmWorkloadKqInfoView> calculateByInfoId(List<PpmKqRecordDto> list, long monthlyReasonableAbnormalDays) {
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }

        //先分组
        Map<Long, List<PpmKqRecordDto>> grouped = list.stream()
                .collect(Collectors.groupingBy(PpmKqRecordDto::getInfoId));
        //再并行处理
        return grouped.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> buildGroupStats(e.getValue(), monthlyReasonableAbnormalDays)
                ));
    }

    /**
     * 构建单个分组的统计数据
     *
     * @param records 按月考勤记录
     * @param monthlyReasonableAbnormalDays 可减免的异常扣减天数
     * @return 按月统计
     */
    private PpmWorkloadKqInfoView buildGroupStats(List<PpmKqRecordDto> records, long monthlyReasonableAbnormalDays) {
        PpmWorkloadKqInfoView stats = new PpmWorkloadKqInfoView();

        double amWhetherDays = records.stream().filter(x -> "0".equals(x.getIsHoliday())).
                filter(x -> "0".equals(x.getAmWhetherAbnormal())).
                filter(x -> !"5".equals(x.getAmAbnormalReason())).
                filter(x -> !"6".equals(x.getAmAbnormalReason())).
                filter(x -> !"15".equals(x.getAmAbnormalReason())).count();

        double pmWhetherDays = records.stream().filter(x -> "0".equals(x.getIsHoliday())).
                filter(x -> "0".equals(x.getPmWhetherAbnormal())).
                filter(x -> !"5".equals(x.getAmAbnormalReason())).
                filter(x -> !"6".equals(x.getAmAbnormalReason())).
                filter(x -> !"15".equals(x.getPmAbnormalReason())).count();

        // 忘记打卡次数
        long forgetClockInNumber = judgeAbnormalNumber(records, "0")
                + judgeAbnormalNumber(records, "12");
        // 迟到次数
        long beLateNumber = judgeAbnormalNumber(records, "1")
                + judgeAbnormalNumber(records, "13");
        // 早退次数
        long leaveEarlyNumber = judgeAbnormalNumber(records, "2")
                + judgeAbnormalNumber(records, "14");
        long abnormalTotal = forgetClockInNumber + beLateNumber + leaveEarlyNumber;
        //实际出勤天数合计 外包人员每个月忘记打卡+迟到+早退总次数不超过可减免的异常扣减天数时不扣除出勤天数
        double actualAttendanceDays;
        //异常扣减天数
        double exceptionalDeductionNumber = 0;
        if (abnormalTotal > monthlyReasonableAbnormalDays) {
            exceptionalDeductionNumber = (abnormalTotal - monthlyReasonableAbnormalDays) / 2.0;
            actualAttendanceDays = (amWhetherDays + pmWhetherDays + monthlyReasonableAbnormalDays) / 2;
        } else {
            actualAttendanceDays = (amWhetherDays + pmWhetherDays + abnormalTotal) / 2;
        }
        stats.setActualAttendanceDays(actualAttendanceDays);
        stats.setExceptionalDeductionNumber(exceptionalDeductionNumber);

        //工作日天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(records.get(0).getAttendanceDate());

        Long month = Long.valueOf(calendar.get(Calendar.MONTH) + 1L);
        Long years = Long.valueOf(calendar.get(Calendar.YEAR));
        double requiredAttendanceDays = ppmKqInfoService.queryCalendarListSize(month, years, "N");
        stats.setRequiredAttendanceDays(requiredAttendanceDays);

        //工作量
        stats.setCumulativeWorkingMonth(actualAttendanceDays / requiredAttendanceDays);
        return stats;
    }

    /**
     * 处理权限
     *
     * @param ppmKqInfoDto ppmKqInfoDto
     * @param user         user
     */
    public void handleAuthCode(PpmKqInfoDto ppmKqInfoDto, CommOperatorDoLoginDto user) {
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            ppmKqInfoDto.setDeptValue(user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            ppmKqInfoDto.setPmId(user.getUserid());
            //系统管理员和一级部经理不做限制
        }
    }

    /**
     * 格式化日期范围
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 在场时段
     */
    private String formatDateRange(Date startDate, Date endDate) {
        return DateUtil.dateToIsoDateFormat(startDate) + " ~ " + DateUtil.dateToIsoDateFormat(endDate);
    }
}
