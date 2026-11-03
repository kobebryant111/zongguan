package com.work.ppm.application.api.usermanager;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseUploadApi;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.dto.usermanager.PpmStaffFileDto;
import com.work.ppm.application.model.usermanager.PpmStaffFile;
import com.work.ppm.application.services.usermanager.IPpmStaffFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 行员员工档案
 */
@Api( produces = "application/json;charset=UTF-8", tags = {"PpmStaffFileApi"}, description = "行员员工档案")
@RestController
@RequestMapping("ppmStaffFile")
@Slf4j
public class PpmStaffFileApi extends BaseUploadApi {
	/**
	 * 行员员工档案
	 */
    @Autowired
    private IPpmStaffFileService ppmStaffFileService;


    /**
     * 员工档案详情
     * @param badge 工号
     * @return ApiResult<PpmStaffFileDto>
     */
    @GetMapping(value = "queryUserFile/{badge}")
    @ApiOperation(value = "queryUserFile", notes = "员工档案详情")
    public ApiResult<PpmStaffFileDto> queryUserFile(@PathVariable String badge) {
        ApiResult<PpmStaffFileDto> result =new ApiResult<>();
		PpmStaffFileDto item = ppmStaffFileService.queryUserFile(badge);
        result.setResults(item);
        result.success("查询成功");
        return result;
    }

    /**
     * 更新员工档案
     * @param jsonstr json 字符串
     * @return ApiResult<Object>
     */
    @PutMapping(value = "update")
    @ApiOperation(value = "update", notes = "更新员工档案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "integer", paramType = "query"),
			@ApiImplicitParam(name = "badge", value = "工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "empname", value = "姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gender", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certtype", value = "证件类型", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "certno", value = "证件号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "birthday", value = "生日", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "nation", value = "名族", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "domicilelocation", value = "户籍所在地", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "recruitmodle", value = "招聘方式", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "ranksalary", value = "职级薪档", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "joindate", value = "入行时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "worktime", value = "参加工作时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "depid", value = "部门编号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "jobid", value = "岗位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highestedu", value = "最高学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highestfulltimeedu", value = "最高全日制学历", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highestdegree", value = "最高学位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "highestfulltimedegree", value = "最高全日制学位", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "xaddress", value = "现居住地址", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "height", value = "身高", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "weight", value = "体重", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "politiccontenance", value = "政治面貌", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "joinpartytime", value = "入党时间", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "subordinatebranch", value = "所属支部", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "officelocation", value = "办公地点", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "technicalexpertise", value = "技术专长", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "interest", value = "兴趣爱好", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "otherinfos", value = "其他信息", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "shirtsize", value = "衬衫尺寸", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "carno", value = "车牌号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "laundaycard", value = "洗衣卡", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "hospital", value = "体检医院", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "maritalstatus", value = "婚姻状况", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fraternalwill", value = "联谊意愿", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "team", value = "组别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updateuserid", value = "更新人工号", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "updatedate", value = "更新时间", dataType = "date", paramType = "query"),
			@ApiImplicitParam(name = "updateflag", value = "是否更新", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "员工状态", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "image", value = "头像图片", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "isdel", value = "预留删除标志", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "motto", value = "座右铭", dataType = "string", paramType = "query"),
			
    })
    public ApiResult<Object> updatePpmStaffFile(@RequestBody String jsonstr) {
    	// 转化
		PpmStaffFile ppmStaffFile = JsonUtil.stringToObject(jsonstr, PpmStaffFile.class);
				//JsonUtil.mapToObject(map,PpmStaffFile.class);
        ApiResult<Object> result =new ApiResult<>();
        if (!StringUtil.isStringNotEmpty(ppmStaffFile.getBadge())) {
        	result.setStatus(ApiCode.PARAMETER_ERROR);
        	result.setMessage("请求的参数有误");
        	return result;
		}
        int id = ppmStaffFileService.updatePpmStaffFile(ppmStaffFile);
        if(id > 0){
            result.success("更新成功");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败");
        }
        return result;
    }

	/**
	 * 上传头像
	 * @param file 图片
	 * @param request request
	 * @return ApiResult<Object>
	 */
	@PostMapping(value = "uploadImage")
	@ApiOperation(value = "update", notes = "上传头像")
    public ApiResult uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {

		ApiResult<Object> result =new ApiResult<>();
		String userId = request.getParameter("badge");
		String empname = request.getParameter("empname");
		PpmStaffFileDto dto  = new PpmStaffFileDto();
		dto.setBadge(userId);
		dto.setEmpname(empname);
        super.setFile(file);
		dto.setIp(IpUtil.getIpAddress(request));
		// 图片上传
		String imagePath = super.uploadImage(dto.getBadge() + new Date().getTime(),
				"SF","CATALOG2019112600001",request);
		dto.setImage(imagePath);
		ppmStaffFileService.uploadImage(dto);
		if(StringUtil.isStringNotEmpty(imagePath)) {
			result.setResults(imagePath);
			result.success("上传成功");
		} else {
			result.failure("上传异常，请联系相关人员");
		}
		return result;
	}

}
