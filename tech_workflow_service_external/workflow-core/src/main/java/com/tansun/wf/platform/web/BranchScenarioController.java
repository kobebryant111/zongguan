package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfBranchScenarioEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2021/1/21
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
// camunda-TODO
@RestController
@RequestMapping("wf/branchScenario")
public class BranchScenarioController {
    // camunda-TODO
//    @Autowired
//    WfTestExecutionPathService wfTestExecutionPathService;
    @RequestMapping("list")
    public WfResponseVO list (String processDefineId,Integer pageSize,Integer pageNum){
        if(StringUtils.isBlank(processDefineId)){
            return WfResponseVO.errorResponse("流程定义id为空");
        }
        // camunda-TODO
//        List<WfBranchScenarioEntity> list = wfTestExecutionPathService.getExecutionList(processDefineId);
        List<WfBranchScenarioEntity> list = new ArrayList<>();
        if(pageSize == null || pageNum == null){
            PageInfo<WfBranchScenarioEntity> result = new PageInfo<>(list);
            result.setTotal(list.size());
            return WfResponseVO.successResponse(result);
        }
        // 记录总数
        Integer count = list.size();
        // 页数
        Integer pageCount = 0;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        // 开始索引
        int fromIndex = 0;
        // 结束索引
        int toIndex = 0;
        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        PageInfo<WfBranchScenarioEntity> result = new PageInfo<>(list.subList(fromIndex, toIndex));
        result.setTotal(count);
        result.setPageSize(pageSize);
        result.setPageNum(pageNum);
        return WfResponseVO.successResponse(result);
    }

}
