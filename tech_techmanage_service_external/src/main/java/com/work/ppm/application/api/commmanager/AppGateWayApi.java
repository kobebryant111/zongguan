package com.work.ppm.application.api.commmanager;

import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.othermanager.IPpmWeixinService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.constant.SystemConstant;
import com.work.ppm.commons.lang.queue.MobileHttpQueue;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 手机端网管
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"AppGateWayApi"}, description = "手机端网关")
@Slf4j
@RestController
@RequestMapping
public class AppGateWayApi {
    /**
     * env
     */
    @Autowired
    private Environment env;
    /**
     * 参数
     */
    private String techParams;
    /**
     * url
     */
    private String techUrl;
    /**
     * post || get
     */
    private String techMethodType;
    /**
     * token
     */
    private String techToken;
    /**
     * 密钥
     */
    private String secertKey;
    /**
     * 工作平台接口
     */
    @Autowired
    private IPpmWeixinService ppmWeixinService;
    /**
     * 业务字典接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 网关
     *
     * @param request  request
     * @param response response
     * @return ApiResult
     */
    @ApiOperation(value = "手机端网关", notes = "手机端网关")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "json", value = "json格式字符串", dataType = "string", paramType = "body")
    )
    @PostMapping("app/gateWay")
    public ApiResult<Object> gateWay(HttpServletRequest request, HttpServletResponse response,
                                     @RequestBody String json) {
        // 增加列队功能
        Queue<String> list = new LinkedList();

        log.info("json:{}", json);
        this.techParams = json;
        this.preHadler(request);

        String returnData = "";
        // 本地地址
        String localHost =
                eosDictEntryService.getTypeidForDetail("NEW_GATEWAY","host").getDictname();
//                "http://localhost:9000";

        // 端口
//        String port = env.getProperty(SystemConstant.SPRING_SERVER_PORT);
        // 系统根路径
        String contextPath = env.getProperty(SystemConstant.SPRING_SERVER_SERVLET_CONTEXTPATH);
        // 完整路径
        StringBuilder sb = new StringBuilder();
        sb.append(localHost).append(contextPath).append(this.techUrl);

        if (HttpMethod.POST.name().equalsIgnoreCase(this.techMethodType)) {
            // 新增 【移动端审批】
            Map<String,Object> map = JsonUtil.jsonToMap(this.techParams);
            Map<String,Object> bpsMap = (Map<String,Object>) map.get("bps");
            if(null != bpsMap) {
                String applydesc = bpsMap.get("suggest") + "【移动端审批】";
                bpsMap.put("suggest", applydesc);
            }
            this.techParams = JsonUtil.toJSONString(map);
            ppmWeixinService.queuePpmWeixin(new MobileHttpQueue(sb.toString(),
                    this.techParams, this.techToken, this.secertKey));
            ApiResult<Object> result = new ApiResult<>();
            result.success("审批成功");
            returnData = JsonUtil.toJSONString(result);
        } else if (HttpMethod.GET.name().equalsIgnoreCase(this.techMethodType)) {
            returnData = HttpClient.get(sb.toString(), this.techParams, this.techToken, this.secertKey);
        }

        ApiResult<Object> result = this.afterCompletion(returnData);
        return result;
    }

    /**
     * 网关头部
     *
     * @param request request
     */
    private void preHadler(HttpServletRequest request) {
        // 参数校验
        vaildate(request);

    }

    /**
     * 请求之后 数据处理
     *
     * @param returnData json
     * @return ApiResult
     */
    private ApiResult<Object> afterCompletion(String returnData) {
        if (!StringUtil.isNotBlank(returnData)) {
            return null;
        }
        ApiResult<Object> result = new ApiResult<>();
        String newJson = "";
        // 结果集 处理
        String josnData = JsonUtil.toJSONString(returnData);
        // 将过滤的条件数据存放到结果集
        List<EosDictEntry> list = eosDictEntryService.getTypeidForList("PPM_XML_STR_REPLACE");
        // 对数据进行过滤
        EosDictEntry eos;
        for (int i = list.size() - 1; i >= 0; i--) {
            eos = list.get(i);
            if (josnData.contains(eos.getDictid())) {
                josnData = josnData.replaceAll(eos.getDictid(), eos.getDictname());
            }
        }

        Map<String, Object> map = JsonUtil.jsonToMap(josnData);
        String code = null == map.get("status") ? null : map.get("status") + "";
        ApiCode apiCode = null;

        if (StringUtil.isNotBlank(code)) {
            apiCode = ApiCode.getMethodByCode(Integer.parseInt(code));
        }


        result.setResults(map.get("results"));
        result.setStatus(apiCode);
        return result;
    }

    /**
     * 参数验证
     *
     * @param request request
     */
    private void vaildate(HttpServletRequest request) {
        String code = request.getHeader(SystemConstant.XTOKEN);
        String secertKey = request.getHeader(SystemConstant.SECERTKEY);
        AssertUtil.notNull(code, "【Token】不能为空");
        AssertUtil.notNull(secertKey, "【SecertKey】不能为空");

        AssertUtil.notNull(this.techParams, "参数不能为空");
        // 转map
        Map<String, Object> map = JsonUtil.jsonToMap(this.techParams);

        // 提取特殊字段
        // url
        String url = null == map.get("techUrl") ? "" : map.get("techUrl") + "";
        AssertUtil.notNull(url, "【techUrl】不能为空");
        // methodType
        String methodType = null == map.get("techMethodType") ? "" : map.get("techMethodType") + "";
        AssertUtil.notNull(methodType, "【techMethodType】不能为空");
        this.techMethodType = methodType;
        this.techUrl = url;
        this.techToken = code;
        this.secertKey = secertKey;
    }

}
