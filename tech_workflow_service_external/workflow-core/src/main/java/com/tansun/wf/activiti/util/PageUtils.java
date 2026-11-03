package com.tansun.wf.activiti.util;

import com.github.pagehelper.PageInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/21
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class PageUtils {

    /**
     *
     * @param list
     * @param pageNum 页码
     * @param pageSize 页长
     * @return
     */
    public static<T> List<T> subList(List<T> list, Integer pageNum, Integer pageSize) {
        if(null == list){
            return list;
        }
        //为空不截取
        if(null == pageNum || null == pageSize){
            return list;
        }
        int startIndex = 0;
        int endIndex = 0;
        int len=list.size();
        int pages = (int) Math.ceil(len/(float)pageSize);
        if(pageNum >= 1 && pageNum < pages){
            startIndex = (pageNum-1) * pageSize;
            endIndex = pageNum * pageSize;
        }
        if(pageNum >= pages){
            startIndex = (pages-1) * pageSize;
            endIndex = pageNum * pageSize;
            if(endIndex >= len){
                endIndex = len;
            }
        }
        List<T> subList = list.subList(startIndex, endIndex);
        return subList;

    }


    public static Map<String, Object> setPageParams(Map<String, Object> params, Integer pageNum, Integer pageSize) {
        if(null == params){
            params = new HashMap<String,Object>();
        }
        //判断分页信息是否为空，为空则默认查询所有列表
        if(null == pageNum){
            pageNum = 1;
        }
        if(null == pageSize){
            pageSize = 999;
        }
        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));
        return params;
    }

    public static Map<String, Object> convertPageResult(ProcessBean queryResult, PageInfo page){

        if (queryResult == null) {
            return null;
        }
        PageInfo<Map<String, Object>> pageResult = new PageInfo<>(queryResult.getResultList(),8);

        if(StringUtils.isNumeric(queryResult.getTotal())){
            pageResult.setTotal(Long.parseLong(queryResult.getTotal()));
        }else {
            pageResult.setTotal(0);
        }

        pageResult.setPageSize(page.getPageSize());
        long pages = 1;
        if(page.getPageSize()!=0){
            pages = pageResult.getTotal() / page.getPageSize() + 1;
        }
        pageResult.setIsFirstPage(page.getPageNum() == 1);
        pageResult.setIsLastPage(page.getPageNum() == pages);
        pageResult.setHasPreviousPage(page.getPageNum() >1);
        pageResult.setHasNextPage( page.getPageNum() < pages);
        pageResult.setNextPage(page.getPageNum()+1);
        pageResult.setPages((int) pages);


        Map<String, Object> result = new HashMap<>();
        result.put("grid",pageResult);
        return result;
    }

    public static ProcessInfo newProcessInfo(PageInfo page){
        ProcessInfo info = new ProcessInfo();
        info.setPageSize(page.getPageSize() + "");
        info.setCurrentPage(page.getPageNum() + "");
        return info;
    }

}
