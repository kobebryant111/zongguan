package com.work.ppm.application.api.depkpi;

import com.work.ppm.application.dto.depkpi.PpmDepkpiDepCheckDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.depkpi.ext.PpmDepkpiDepCheckInfo;
import com.work.ppm.application.services.depkpi.IPpmDepkpiDepCheckService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.BaseApi;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"PpmDepkpiDepCheckApi"})
@RestController
@RequestMapping("ppmDepkpiDepCheck")
@Slf4j
public class PpmDepkpiDepCheckApi extends BaseApi {
    /**
     * 考核表
     */
    @Autowired
    private IPpmDepkpiDepCheckService ppmDepkpiDepCheckService;

    /**
     * 管理员
     */
    private static final String ADMINSTRING="DEPKPIADMIN";
    /**
     * 考核分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryCheckPage")
    @ApiOperation(value = "考核分页查询", notes = "考核分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "suggest", value = "退回意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；" +
                    "5：待初评；6：待终评；7：已完成；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dbuser", value = "待办人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryCheckPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto currentUser = super.getCurrentUser(request);
        List<ImRoleDoLoginDto> roles = currentUser.getRoles();
        List<String> roleList = roles.stream().map(ImRoleDoLoginDto::getRoleId).collect(Collectors.toList());
        String userid = currentUser.getUserid();
        ppmDepkpiDepCheckDto.setUserid(userid);
        //管理员
        if (roleList.contains(ADMINSTRING)){
            ppmDepkpiDepCheckDto.setUserid(null);
        }
        ppmDepkpiDepCheckService.queryCheckPage(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 公共考核分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryGgCheckPage")
    @ApiOperation(value = "公共考核分页查询", notes = "公共考核分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "suggest", value = "退回意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；" +
                    "5：待初评；6：待终评；7：已完成；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dbuser", value = "待办人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryGgCheckPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto currentUser = super.getCurrentUser(request);
        List<ImRoleDoLoginDto> roles = currentUser.getRoles();
        List<String> roleList = roles.stream().map(ImRoleDoLoginDto::getRoleId).collect(Collectors.toList());
        String userid = currentUser.getUserid();
        ppmDepkpiDepCheckDto.setUserid(userid);
        //管理员
        if (roleList.contains(ADMINSTRING)){
            ppmDepkpiDepCheckDto.setUserid(null);
        }
        ppmDepkpiDepCheckService.queryGgCheckPage(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 公共考核明细分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryGgCheckDetailPage")
    @ApiOperation(value = "公共考核明细分页查询", notes = "公共考核明细分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；" +
                    "5：待初评；6：待终评；7：已完成；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dbuser", value = "待办人", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryGgCheckDetailPage(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto currentUser = super.getCurrentUser(request);
        List<ImRoleDoLoginDto> roles = currentUser.getRoles();
        List<String> roleList = roles.stream().map(ImRoleDoLoginDto::getRoleId).collect(Collectors.toList());
        String userid = currentUser.getUserid();
        ppmDepkpiDepCheckDto.setUserid(userid);
        //管理员
        if (roleList.contains(ADMINSTRING)){
            ppmDepkpiDepCheckDto.setUserid(null);
        }
        ppmDepkpiDepCheckService.queryGgCheckDetailPage(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 考核列表查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getListPage")
    @ApiOperation(value = "考核列表查询", notes = "考核列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "suggest", value = "退回意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；5：待初评；6：待终评；7：已完成；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dbuser", value = "待办人", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult getList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager,
                             HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiDepCheckService.getListPage(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setTotal(pager.getTotal());
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 我的待办数量查询
     *
     * @param request 对象
     * @return ApiResult 对象
     */
    @PostMapping(value = "queryMyDbNum")
    @ApiOperation(value = "我的待办数量查询", notes = "我的待办数量查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelid", value = "模板id", dataType = "Long", paramType = "query"),
    })
    public ApiResult queryMyDbNum(HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        CommOperatorDoLoginDto user = super.getCurrentUser(request);
        String userid = user.getUserid();
        List<PpmDepkpiDepCheckInfo> items =
                ppmDepkpiDepCheckService.queryMyDbNum(userid);
        result.setResults(items);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 自定义指标制定分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryZdyzbzdPagerList")
    @ApiOperation(value = "自定义指标制定分页查询", notes = "自定义指标制定分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryZdyzbzdPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager
            , HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        ppmDepkpiDepCheckService.queryZdyzbzdPagerList(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 自定义指标自评分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryZdyzbzpPagerList")
    @ApiOperation(value = "自定义指标自评分页查询", notes = "自定义指标自评分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),

            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryZdyzbzpPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager
            , HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        ppmDepkpiDepCheckService.queryZdyzbzpPagerList(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 公共指标制定分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryGgzbzdPagerList")
    @ApiOperation(value = "公共指标制定分页查询", notes = "公共指标制定分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名称", dataType = "string", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryGgzbzdPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager
            , HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        ppmDepkpiDepCheckService.queryGgzbzdPagerList(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 评价分页查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @param pager                分页对象
     * @return ApiResult
     */
    @PostMapping(value = "queryPjPagerList")
    @ApiOperation(value = "评价分页查询", notes = "评价分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "centerid", value = "中心id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "二级部id", dataType = "long", paramType = "query"),
            // 分页公共参数
            @ApiImplicitParam(name = "size", value = "每页显示数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "index", value = "第几页", dataType = "int", paramType = "query"),
    })
    public ApiResult queryPjPagerList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto, Pager pager, HttpServletRequest request) {
        ApiResult<List> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        ppmDepkpiDepCheckService.queryPjPagerList(ppmDepkpiDepCheckDto, pager);
        result.setResults(pager.getList());
        result.setStatus(ApiCode.SUCCESS);
        result.setTotal(pager.getTotal());
        return result;
    }

    /**
     * 自评提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "zpCommit")
    @ApiOperation(value = "自评提交", notes = "自评提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "部门级别", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> zpCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        ApiResult<Object> result = new ApiResult<>();
        int id = ppmDepkpiDepCheckService.zpCommit(ppmDepkpiDepCheckDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        }else if (id==-1){
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("自评项未全部自评！");
        }else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("提交失败");
        }
        return result;
    }

    /**
     * 自定义评价提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "zdyPjCommit")
    @ApiOperation(value = "自定义评价提交", notes = "自定义评价提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "部门级别", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> zdyPjCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto,HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        int id = ppmDepkpiDepCheckService.zdyPjCommit(ppmDepkpiDepCheckDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if (id == -1) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("考核内容未完全评价");
        }else if (id == -2) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("初评人或终评人皆非您，不能提交评价");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("提交失败");
        }
        return result;
    }

    /**
     * 公共评价提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "ggPjCommit")
    @ApiOperation(value = "公共评价提交", notes = "公共评价提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "部门id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "部门级别", dataType = "long", paramType = "query"),
    })
    public ApiResult<Object> ggPjCommit(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto,HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        int id = ppmDepkpiDepCheckService.ggPjCommit(ppmDepkpiDepCheckDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if (id == -1) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("考核内容未完全评价");
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("提交失败");
        }
        return result;
    }


    /**
     * 考核制定提交
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "commitzbzd")
    @ApiOperation(value = "自定义考核制定提交", notes = "自定义考核制定提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),

    })
    public ApiResult<Object> commitZbzd(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        ApiResult<Object> result = new ApiResult<>();
        int res = ppmDepkpiDepCheckService.commitZbzd(ppmDepkpiDepCheckDto);
        if (res > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else if(res==-1) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("基准分配置不正确！");
        }else if(res==-2) {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("基准分配置不正确！");
        }else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("更新失败！");
        }
        return result;
    }

    /**
     * 修改考核项状态
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "upCheckStatus")
    @ApiOperation(value = "修改考核项状态", notes = "修改考核项状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "考核项状态", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> upCheckStatus(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto,HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        CommOperatorDoLoginDto currentUser = super.getCurrentUser(request);
        List<ImRoleDoLoginDto> roles = currentUser.getRoles();
        List<String> roleList = roles.stream().map(ImRoleDoLoginDto::getRoleId).collect(Collectors.toList());
        //非管理员不能修改
        if (!roleList.contains(ADMINSTRING)){
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("您不是管理员，不能修改状态！");
            return result;
        }
        int id = ppmDepkpiDepCheckService.upCheckStatus(ppmDepkpiDepCheckDto);
        if (id > 0) {
            result.setStatus(ApiCode.SUCCESS);
        } else {
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("修改失败");
        }
        return result;
    }


    /**
     * 获取考核项树列表
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "getCheckTree")
    @ApiOperation(value = "获取考核项树列表", notes = "获取考核项树列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核项ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "suggest", value = "退回意见", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "depid", value = "部门ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targetid", value = "指标ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "parentid", value = "父级ID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "basepoint", value = "基准分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "hasson", value = "是否有子节点：0:无子节点；1：有子节点；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "所属组织级别：1：部门； 2：中心 ；3：一级部 ；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "指标名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "levelnum", value = "层级", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "targettype", value = "指标类型：1：公共指标；2：自定义指标；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "evaltype", value = "评价方式：1：档位；2：任意分值；3：其他分值；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "selfeval", value = "是否需要自评：1：需要；2：不需要", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "maxpoint", value = "加减分上限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "minpoint", value = "加减分下限分", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "考核年份", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：0：草稿中；1：待初审；2：待终审；3：执行中；4：待自评；" +
                    "5：待初评；6：待终评；7：已完成；", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dbuser", value = "待办人", dataType = "string", paramType = "query"),

    })
    public ApiResult<List<PpmDepkpiDepCheckDto>> getCheckTree(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        ApiResult<List<PpmDepkpiDepCheckDto>> result = new ApiResult<>();
        List<PpmDepkpiDepCheckDto> list = ppmDepkpiDepCheckService.getCheckTree(ppmDepkpiDepCheckDto);
        result.setResults(list);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 公共考核数据查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "queryGgSumPoint")
    @ApiOperation(value = "公共考核数据查询", notes = "公共考核数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "DEPID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "tyear", value = "年份", dataType = "String", paramType = "query"),
    })
    public ApiResult<Object> getSumPointList(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto,HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<>();
        ppmDepkpiDepCheckDto.setUserid(super.getCurrentUser(request).getUserid());
        PpmDepkpiDepCheckInfo item = ppmDepkpiDepCheckService.queryCheckSum(ppmDepkpiDepCheckDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 考核数据查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "querySumPoint")
    @ApiOperation(value = "考核数据查询", notes = "考核数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depid", value = "DEPID", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "sign", value = "sign", dataType = "long", paramType = "query"),

    })
    public ApiResult<Object> getSumPoint(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        ApiResult<Object> result = new ApiResult<>();
        PpmDepkpiDepCheckDto item = ppmDepkpiDepCheckService.queryCheckSumAndPoint(ppmDepkpiDepCheckDto);
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * 单个指标汇总总分查询
     *
     * @param ppmDepkpiDepCheckDto 对象
     * @return ApiResult
     */
    @PostMapping(value = "querySumPointById")
    @ApiOperation(value = "单个指标汇总总分查询", notes = "单个指标汇总总分查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "long", paramType = "query"),
    })
    public ApiResult querySumPointById(PpmDepkpiDepCheckDto ppmDepkpiDepCheckDto) {
        ApiResult<Object> result = new ApiResult<>();
        PpmDepkpiDepCheckInfo item = ppmDepkpiDepCheckService.querySumPointById(ppmDepkpiDepCheckDto);
        if(item ==null){
            result.setStatus(ApiCode.UPATE_FAIL);
            result.setMessage("查询失败");
        }
        result.setResults(item);
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }
}
