package com.work.ppm.application.api.resourceplat;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.resourceplat.ResAddedDto;
import com.work.ppm.application.dto.resourceplat.ResNoticeConfigDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
import com.work.ppm.application.model.resourceplat.ResNoticeConfig;
import com.work.ppm.application.model.resourceplat.ResReportInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.resourceplat.IResAddedService;
import com.work.ppm.application.services.resourceplat.IResReportService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 资产统计报表
 * @date 2021/09/03
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"resReportApi"})
@RestController
@RequestMapping("resReport")
@Slf4j
public class ResReportApi extends BaseApi {

    /**
     * 日志模板
     */
    private static final String  LOG_BEFORE_API_TEMPLATE = "account[{}],name[{}],action[{}],Params[{}]";

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(ResReportApi.class);

    /**
     * IResAddedService
     */
    @Autowired
    private IResAddedService resAddedService;

    /**
     * IResReportService
     */
    @Autowired
    private IResReportService resReportService;

    /**
     * 分页
     *
     * @param resAddedDto 对象
     * @param pager       分页对象
     *
     * @return ApiResult<List>
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priceReqNo", value = "申请单号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "creator", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "busiDeptId", value = "所属业务部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "techDeptId", value = "所属技术部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rangeDate", value = "申请时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "expectedOnlineSTimeRangDate", value = "预计上线时间",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sourceType", value = "申请类型", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query")
    })
    public ApiResult<Map> queryPager(ResAddedDto resAddedDto, Pager pager) {

        // 对申请时间做特殊处理
        String rangeDate = resAddedDto.getRangeDate();
        if (StringUtils.isNotEmpty(rangeDate)) {
            String[] tmp = rangeDate.split(",");
            resAddedDto.setStartTime(tmp[0]);
            resAddedDto.setEndTime(tmp[1]);
        }

        // 对预期上线时间做特殊处理
        String expectedOnlineTimeRangDate = resAddedDto.getExpectedOnlineTimeRangDate();
        if (StringUtils.isNotEmpty(expectedOnlineTimeRangDate)) {
            String[] tmp = expectedOnlineTimeRangDate.split(",");
            resAddedDto.setExpectedOnlineStartTime(tmp[0]);
            resAddedDto.setExpectedOnlineEndTime(tmp[1]);
        }
        ApiResult<Map> result = new ApiResult<>();
        resAddedDto.setStatus("4");
        resAddedService.queryPager(resAddedDto, pager);

        // 根据查询出的list进一步查询出统计信息
        List<ResAdded> resAddedList = resReportService.queryResourceReportList(resAddedDto);
        List<ResReportInfo> resReportInfos = null;
        if(!resAddedList.isEmpty()) {
            List <String> resAddIdList = new LinkedList<>();
            for (int i = 0; i < resAddedList.size(); i++) {
                resAddIdList.add(resAddedList.get(i).getId());
            }
            resReportInfos = resReportService.queryStatisticReportInfo(resAddIdList);
        }
        // 这边最终返回的是个map map中的mainList表示主列表的数据 subList表示统计信息列表的数据
        HashMap<String, Object> resultHashMap = new HashMap<>();
        resultHashMap.put("mainList",pager.getList());
        resultHashMap.put("subList",resReportInfos);
        result.setResults(resultHashMap);
        result.setTotal(pager.getTotal());
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 导出资产统计报表
     * @param resAddedDto 新增Dto
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    @PostMapping(value = "/exportResReportEasyPoi")
    @ApiOperation(value = "资产报表下载", notes = "资产报表下载", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "creator", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "busiDeptId", value = "所属业务部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "techDeptId", value = "所属技术部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rangeDate", value = "申请时间", dataType = "string", paramType = "query")
    })
    public void exportResReport_EasyPoi(ResAddedDto resAddedDto, HttpServletRequest request, HttpServletResponse response) {

        // 对申请时间做特殊处理
        String rangeDate = resAddedDto.getRangeDate();
        if (StringUtils.isNotEmpty(rangeDate)) {
            String[] tmp = rangeDate.split(",");
            resAddedDto.setStartTime(tmp[0]);
            resAddedDto.setEndTime(tmp[1]);
        }

        // 对预期上线时间做特殊处理
        String expectedOnlineTimeRangDate = resAddedDto.getExpectedOnlineTimeRangDate();
        if (StringUtils.isNotEmpty(expectedOnlineTimeRangDate)) {
            String[] tmp = expectedOnlineTimeRangDate.split(",");
            resAddedDto.setExpectedOnlineEndTime(tmp[1]);
            resAddedDto.setExpectedOnlineStartTime(tmp[0]);
        }
        resAddedDto.setStatus("4");
        List<ResAdded> resAddeds = resReportService.queryResourceReportList(resAddedDto);
        List<String> resAddIdList = new LinkedList<>();
        if (resAddeds != null && !resAddeds.isEmpty()) {
            for (int i = 0; i < resAddeds.size(); i++) {
                resAddIdList.add(resAddeds.get(i).getId());
            }
        }

        List<ResReportInfo> resReportInfos = resReportService.queryStatisticReportInfo(resAddIdList);
        List<ResReportInfo> resReportInfos2 = resReportService.queryStatisticReportInfo(resAddIdList);
        ExportParams exportParams1 = new ExportParams();
        exportParams1.setSheetName("test1");
        Map<String, Object> map1 = new HashMap<>(16);
        map1.put("title", exportParams1);
        map1.put("entity", ResReportInfo.class);
        map1.put("data", resReportInfos);

        ExportParams exportParams2 = new ExportParams();
        exportParams2.setSheetName("test2");
        Map<String, Object> map2 = new HashMap<>(16);
        map2.put("title", exportParams2);
        map2.put("entity", ResReportInfo.class);
        map2.put("data", resReportInfos2);

        List<Map<String, Object>> sheetList = new ArrayList<>();
        sheetList.add(map1);
        sheetList.add(map2);

        Workbook workbook = ExcelExportUtil.exportExcel(sheetList, ExcelType.HSSF);
        try {
            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            log.error("异常消息");
        }
    }

    /**
     * 导出资产统计报表
     * @param resAddedDto 新增Dto
     * @param request request
     * @param response response
     * @throws IOException IOException
     */
    @PostMapping(value = "/exportResReport")
    @ApiOperation(value = "资产报表下载", notes = "资产报表下载", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "creator", value = "申请人", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "busiDeptId", value = "所属业务部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "techDeptId", value = "所属技术部ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rangeDate", value = "申请时间", dataType = "string", paramType = "query")
    })
    public void exportResReport(ResAddedDto resAddedDto, HttpServletRequest request, HttpServletResponse response) {
        CommOperatorDoLoginDto user =  super.getCurrentUser(request);
        logger.info(LOG_BEFORE_API_TEMPLATE, user.getUserid(), user.getOperatorname(),
                "资产统计报表下载,downLoad",resAddedDto);
        resReportService.exportResReport(resAddedDto, response);
    }

    /**
     * 查询关联系统树
     *
     * @return ApiResult<CommTreeDto>
     */
    @GetMapping("queryRepDeptTree")
    @ApiOperation(value = "查询关联系统树", notes = "查询关联系统树-2")
    @ApiImplicitParams({
    })
    public ApiResult queryRelaSysTree() {
        ApiResult result = new ApiResult<>();
        List<CommTreeDto> list = resReportService.queryRepDeptTree();
        result.success("获取成功");
        result.setResults(list);
        return result;
    }

    /**
     * 校验登陆人是否为系统管理部人员
     * @param request
     * @return 1
     */
    @PostMapping(value = "getDeptIdLogin")
    @ApiImplicitParams({})
    public ApiResult<String> loginUserIdentity(HttpServletRequest request) {
        ApiResult<String> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);

        // 查询当前登陆人是否为系统管理部人员
        String retData = resReportService.getDeptIdLogin(user.getUserid());
        result.setResults(retData);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

}
