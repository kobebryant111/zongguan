package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.dto.othermanager.PpmQuestionListDto;
import com.work.ppm.application.model.othermanager.PpmQuestionList;
import com.work.ppm.application.model.othermanager.PpmQuestionOption;
import com.work.ppm.application.model.othermanager.ext.PpmQuestionListInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.othermanager.IPpmQuestionListService;
import com.work.ppm.application.services.othermanager.IPpmQuestionOptionService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主题库表
 */
@Service("ppmQuestionListService")
public class PpmQuestionListService extends BaseDaoSupper implements IPpmQuestionListService {
    /**
     * 实体类路径
     */
    private static String PPMQUESTIONLIST = "com.work.ppm.application.model.othermanager.PpmQuestionList";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 主题库子表接口
     */
    @Autowired
    private IPpmQuestionOptionService ppmQuestionOptionService;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmQuestionList ppmQuestionList, Pager pager) {
        ppmQuestionList.setIsdel(0);
        Map<String, Object> map = StringUtil.objectTOMap(ppmQuestionList, PpmQuestionList.class);
        myBatisDao.selectPager(PPMQUESTIONLIST, map, pager, "queryPagerList");
    }

    @Override
    public PpmQuestionListDto queryDetail(PpmQuestionList ppmQuestionList) {
        ppmQuestionList.setIsdel(0);
        Map<String, Object> map = JsonUtil.objectTOMap(ppmQuestionList, PpmQuestionList.class);
        PpmQuestionListInfo item = myBatisDao.selectClass(PPMQUESTIONLIST, map, "queryDetail");
        PpmQuestionListDto itemdto = JsonUtil.jsonToClass(item, PpmQuestionListDto.class);
        itemdto.setOptions(item.getResult());
        return itemdto;
    }

    @Transactional(value = "defaultTx")
    @Override
    public Integer updatePpmQuestionList(PpmQuestionListDto ppmQuestionList) {
        // 添加
        if (!StringUtil.isStringNotEmpty(ppmQuestionList.getQuestionno())) {
            // 获取主键id
            Long id = getNextSequence(PpmQuestionList.class);
//                    myBatisDao.selectCount(PPMQUESTIONLIST, new HashMap<>(), "getPrimaryKey");
            if (null == id || id < 0) {
                id = 0L;
            }
            // 获取排序后第一条数据
            String no = this.getQuestionno();
            ppmQuestionList.setQuestionno(no);
            ppmQuestionList.setIsdel(0);
            ppmQuestionList.setId(id.intValue());
            ppmQuestionList.setCreatetime(new Date());
            myBatisDao.executionPojo(PPMQUESTIONLIST, ppmQuestionList, IMyBatisDao.SQL.INSTER, "addPojo");
            // 添加子表
            if (null != ppmQuestionList.getOptions()) {
                ppmQuestionOptionService.batchAddPpmQuestionOption(ppmQuestionList.getOptions(), no);
            }
        } else {
            // 更新
            myBatisDao.executionPojo(PPMQUESTIONLIST, ppmQuestionList, IMyBatisDao.SQL.UPDATE, "updatePojo");

            // 添加子表
            if (null != ppmQuestionList.getOptions() && !ppmQuestionList.getOptions().isEmpty()) {
                ppmQuestionList.getOptions().stream().forEach(item -> item.setQuestionno(ppmQuestionList.getQuestionno()) );
                ppmQuestionOptionService.batchAddPpmQuestionOption(ppmQuestionList.getOptions(), "EXCEL");
            }
        }
        return 1;
    }

    @Override
    public Integer deletePpmQuestionList(PpmQuestionList ppmQuestionList) {
        ppmQuestionList.setIsdel(1);
        return myBatisDao.executionPojo(PPMQUESTIONLIST, ppmQuestionList, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public boolean exportExcel(PpmQuestionList ppmQuestionList, HttpServletResponse response) {
        ppmQuestionList.setIsdel(0);
        List<PpmQuestionListInfo> list = myBatisDao.selectListT(PPMQUESTIONLIST, ppmQuestionList, "queryPagerList");
        ExcelUtil.exportExcel(list, PpmQuestionListInfo.class, "tk.xls", response);
        return true;
    }

    @Override
    public int importExcel(MultipartFile file, HttpServletResponse response) {
        List<PpmQuestionListDto> list = ExcelUtil.importExcel(file, PpmQuestionListDto.class);
        if (null == list || list.isEmpty()) {
            return 0;
        }
        // 定义变量
        List<PpmQuestionListDto> qList = new ArrayList<>();
        List<PpmQuestionOption> oList = null;
        PpmQuestionOption oItem = null;
        for (PpmQuestionListDto item : list) {
            // 分割选项表数据 格式 D:OC|A:OA邮箱|B:VD|C:外网邮箱
            // 先分割 | 在分割 ：
            if (StringUtil.isStringNotEmpty(item.getItemOption())) {

                oList = new ArrayList<>();
                String[] options = item.getItemOption().split("\\|");
                for (String str : options) {
                    oItem = new PpmQuestionOption();
                    String[] xxstr = str.split(":");
                    // 判断 ： 分割后数据长度 来确定存在的数据
                    if (xxstr.length == 1) {
                        oItem.setOptioncode(xxstr[0]);
                        oItem.setOptioncontent("");
                    } else if (xxstr.length == 2) {
                        oItem.setOptioncode(xxstr[0]);
                        oItem.setOptioncontent(xxstr[1]);
                    } else {
                        continue;
                    }
                    oItem.setQuestionno(item.getQuestionno());
                    oItem.setIsdel("0");
                    oList.add(oItem);
                }
                item.setOptions(oList);
            }
            qList.add(item);
        }
        List<EosDictEntry> eosList = eosDictEntryService.getTypeidForList("KJXXGL_TMLX");
        eosList.stream().forEach(item -> {
            qList.stream().filter(ff -> item.getDictname().equals(ff.getQuestiontype()) && !StringUtil.isNumber(ff.getQuestiontype())).forEach(ls -> {
                ls.setQuestiontype(item.getDictid());
            });
        });
        // 添加
        for (PpmQuestionListDto item : qList) {
            this.updatePpmQuestionList(item);
        }
        return 1;
    }

    @Transactional(value = "defaultTx")
    @Override
    public int batchDeletes(List<PpmQuestionList> list) {
        if (null != list && list.isEmpty()) {
            return -1;
        }
        Map<String, Object> map = new HashMap<>();
        int size = list.size();
        StringBuilder sb = new StringBuilder(size);
        List<String> noList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sb.append("'").append(list.get(i).getQuestionno()).append("',");
            noList.add(list.get(i).getQuestionno());
        }
        String sql = sb.toString().substring(0, sb.toString().length() - 1);
        map.put("questionno", sql);
        map.put("isdel", 1);
        myBatisDao.executionMap(PPMQUESTIONLIST, map, IMyBatisDao.SQL.UPDATE, "batchDeletes");
        // 删除从表
        int rid = ppmQuestionOptionService.deletePpmQuestionOption(sql);
        if (rid == -1) {
            throw new BizException(ApiCode.FAIL, "系统异常，删除失败");
        }
        return 1;
    }

    /**
     * 生成唯一编号
     *
     * @return String
     */
    private String getQuestionno() {
        PpmQuestionList item = myBatisDao.selectClass(PPMQUESTIONLIST, new HashMap<>(), "descTopOne");
        String no = "";
        if (null != item) {
            no = item.getQuestionno().substring(4);
        } else {
            no = "0";
        }
        // 转int类型
        Integer id = Integer.parseInt(no) + 1;
        String newNo = String.format("%06d", id);
        return "KJTM" + newNo;
    }
}
