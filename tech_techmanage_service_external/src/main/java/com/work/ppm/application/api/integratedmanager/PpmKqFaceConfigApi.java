package com.work.ppm.application.api.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.FaceDeviceInfoDto;
import com.work.ppm.application.dto.integratedmanager.FaceDeviceNameDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqFaceConfigDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceInfoService;
import com.work.ppm.application.services.integratedmanager.IFaceDeviceNameService;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceConfigService;
import com.work.ppm.application.services.integratedmanager.IPpmKqFaceDataService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤人脸配置 API接口
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmKqFaceConfigApi"})
@RestController
@RequestMapping("kqFace")
@Slf4j
public class PpmKqFaceConfigApi extends BaseApi {

    /**
     * 人脸考勤数据接收服务接口
     */
    @Autowired
    private IPpmKqFaceDataService ppmKqFaceDataService;

    /**
     * 人脸考勤查询下发接口
     */
    @Autowired
    private IPpmKqFaceConfigService ppmKqFaceConfigService;

    /**
     * 人脸考勤机配置接口
     */
    @Autowired
    private IFaceDeviceInfoService faceDeviceInfoService;

    /**
     * 人脸考勤机名称接口
     */
    @Autowired
    private IFaceDeviceNameService faceDeviceNameService;

    /**
     * 分页
     *
     * @param ppmKqFaceConfigDto 对象
     * @param pager            分页对象
     * @param request 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "工号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "部门编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryPager(PpmKqFaceConfigDto ppmKqFaceConfigDto,Pager pager,HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto user = this.getCurrentUser(request);
        ppmKqFaceConfigService.queryPager(ppmKqFaceConfigDto, pager,user);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 上传照片
     *
     * @param ppmKqFaceConfigDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "uploadFacePicture")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> uploadFacePicture(PpmKqFaceConfigDto ppmKqFaceConfigDto) {
        ApiResult<Object> result = new ApiResult<>();
        ppmKqFaceConfigService.uploadFacePicture(ppmKqFaceConfigDto);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 获取考勤机信息
     * @param faceDeviceInfoDto 对象
     * @return ApiResult<Object>
     */
    @PostMapping(value = "queryFaceDevice")
    @ApiOperation(value = "获取考勤机信息", notes = "获取考勤机信息")
    public ApiResult<Object> queryFaceDevice(@RequestBody FaceDeviceInfoDto faceDeviceInfoDto){
        ApiResult<Object> result = new ApiResult<>();
        List<FaceDeviceInfoDto> list = faceDeviceInfoService.queryBaseList(faceDeviceInfoDto);
        result.setStatus(ApiCode.SUCCESS);
        result.setResults(list);
        return result;
    }

    /**
     * 确认下发考勤机
     * @param faceDeviceInfoDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "handFaceDevice")
    @ApiOperation(value = "确认下发考勤机", notes = "确认下发考勤机")
    public ApiResult<Object> handFaceDevice(@RequestBody FaceDeviceInfoDto faceDeviceInfoDto){
        ApiResult<Object> result = new ApiResult<>();

        List<PpmKqFaceConfigDto> faceConfigDtoList = faceDeviceInfoDto.getFaceConfigDtoList();
        if(faceConfigDtoList == null ){
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("无人员数据");
            return result;
        }

        long error = faceConfigDtoList.stream().filter(x -> StringUtils.isEmpty(x.getPictureUrl())).count();
        if(error > 0){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("所选信息存在未上传照片的信息");
            return result;
        }

        String[] ids = faceDeviceInfoDto.getFaceDeviceInfoDtoList();
        List<FaceDeviceInfoDto> list = new ArrayList<>();
        if(ids == null){
            result.setStatus(ApiCode.FAIL);
            result.setMessage("下发失败");
            return result;
        }

        for(String id : ids){
            FaceDeviceInfoDto faceDeviceInfoDto1 = new FaceDeviceInfoDto();
            faceDeviceInfoDto1.setId(Long.valueOf(id));
            list.add(faceDeviceInfoDto1);
        }

        Map<String,Object> returnMap =
                faceDeviceInfoService.handFaceDevice(list,faceDeviceInfoDto.getFaceConfigDtoList(),true);
        Map<String,List<PpmKqFaceConfigDto>> map = new HashMap<>();
        int succussFlag = 0;
        int failFlag = 0;
        for(String id : ids){
            Object obj = returnMap.get(id);
            if(obj instanceof String){
                failFlag++;
            }else if(obj instanceof ArrayList){
                List<PpmKqFaceConfigDto> faceConfigDtos = JsonUtil.jsonToList(obj,PpmKqFaceConfigDto.class);
                if(faceConfigDtos != null){
                    long falseNum = faceConfigDtos.stream().filter(x -> Boolean.TRUE.equals(x.getRetCode())).count();
                    if(falseNum == faceConfigDtos.size()){
                        map.put(id,faceConfigDtos);
                        succussFlag++;
                    }else if (falseNum == 0){
                        map.put(id,faceConfigDtos);
                        failFlag++;
                    }else {
                        map.put(id,faceConfigDtos);
                    }
                }
            }else {
                failFlag++;
            }
        }

        if(succussFlag == ids.length){
            result.setStatus(ApiCode.SUCCESS);
            result.setResults(map);
            result.setMessage("下发成功");
        }else if (failFlag == ids.length){
            result.setStatus(ApiCode.FAIL);
            result.setResults(map);
            result.setMessage("下发失败");
        }else {
            result.setStatus(ApiCode.FAIL);
            result.setResults(map);
            result.setMessage("部分下发成功");
        }

        return result;
    }

    /**
     * 分页
     *
     * @param faceDeviceInfoDto 对象
     * @param pager            分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryFaceDevicePager")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult<Object> queryFaceDevicePager(FaceDeviceInfoDto faceDeviceInfoDto, Pager pager) {
        ApiResult<Object> result = new ApiResult<>();
        faceDeviceInfoService.queryPager(faceDeviceInfoDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 更新
     * @param faceDeviceNameDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "updateFaceDevice")
    @ApiOperation(value = "更新", notes = "更新")
    public ApiResult<Object> updateFaceDevice(FaceDeviceNameDto faceDeviceNameDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id ;
        if(faceDeviceNameService.queryDetail(faceDeviceNameDto) == null){
            id = faceDeviceNameService.addFaceDeviceName(faceDeviceNameDto);
        }else {
            id = faceDeviceNameService.updateFaceDeviceName(faceDeviceNameDto);
        }

        if(id > 0){
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

//    @Autowired
//    IPpmKqFaceConfigCalculateJobService configCalculateJobService;
//    /**
//     * 定时任务测试
//     * @return ApiResult
//     */
//    @GetMapping(value = "emptyFaceDevice")
//    @ApiOperation(value = "测试", notes = "测试")
//    public ApiResult<Object> emptyFaceDevice() {
//        ApiResult<Object> result = new ApiResult<>();
//        configCalculateJobService.handFaceDeviceLeavingTimer();
//        result.setStatus(ApiCode.SUCCESS);
//        return result;
//    }

}
