package com.work.ppm.application.services.integratedmanager.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.integratedmanager.PpmFacemachineConfig;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmFacemachineConfigService;
import com.work.ppm.application.services.othermanager.impl.PpmDocumentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外包准入流程图片处理类
 *
 */
@Slf4j
@Service("ppmFacemachineConfigService")
public class PpmFacemachineConfigService extends BaseDaoSupper implements IPpmFacemachineConfigService {
    /**
     * 实体类路径
     */
    private static String PPMFACEMACHINECONFIG = "com.work.ppm.application.model.integratedmanager.PpmFacemachineConfig";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 文档接口
     */
    @Autowired
    private PpmDocumentService ppmDocumentService;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Override
    public String sendPhotoToDevice(String badge, String empName, String faceimage) {
        String message = "";
        Map<String, String> param = new HashMap<String, String>();
        List<PpmFacemachineConfig> configs = myBatisDao.selectListT(PPMFACEMACHINECONFIG, param, "getDeviceMessage");
        if (configs != null && configs.size() > 0) {
            String path = getFile(faceimage);
            log.warn("【新应用】工号：{}，名称：{}，图片编号:{},图片地址:{}", badge, empName,faceimage,path);
            if (!"".equals(path)) {
                message = sendPhoto(configs, badge, empName, path);
            } else {
                message = "照片文件不存在";
                log.warn("【新应用】照片文件不存在");
            }
        }
        return message;
    }

    /**
     * 获取路径
     *
     * @param objectno 图片编号
     * @return String
     */
    private String getFile(String objectno) {
        String path = "";
        Map<String, String> param = new HashMap<String, String>();
        param.put("documentno", objectno);
        //获取文件
        List<PpmDocument> docs = ppmDocumentService.queryDocumentsInfos(param);
        if (docs != null && docs.size() > 0) {
            PpmDocument doc = docs.get(0);
            EosDictEntry eos = eosDictEntryService.getTypeidForDetail("FileuploadRootpath", "rootPath");
            String root = eos.getDictname();
            String filePath = root + "/ppmtest" + doc.getStockpath() + doc.getTag2();
            File filetest = new File(filePath);
            if (filetest.exists()) {
                path = filePath;
            }
        }
        return path;
    }


    /**
     * 发放照片
     *
     * @param configs 集合
     * @param badge   工号
     * @param empName 名称
     * @param path    路径
     * @return String
     */
    private String sendPhoto(List<PpmFacemachineConfig> configs, String badge, String empName, String path) {
        String message = "";
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail("RLKQJK", "KQJKTWO");
        try {
            JSONObject json ;
            JSONArray array ;
            JSONObject js ;
            for (int i = 0; i < configs.size(); i++) {
                json = new JSONObject();
                array = new JSONArray();
                js = new JSONObject();
                PpmFacemachineConfig config = configs.get(i);
                String deviceid = config.getDeviceid();
                String deviceIp = config.getDeviceip();

                js.put("badge", badge);
                js.put("empName", empName);
                js.put("faceByte", image2byte(path));
                array.add(js);
                json.put("luserId", config.getIuserid());
                json.put("deviceIP", deviceIp);
                json.put("empList", array);
                log.warn("【新应用】同步考勤机头像提交报文:{}", json.toJSONString());
                String res = HttpClient.post(eos.getDictname(), json.toJSONString());
                log.warn("【新应用】同步考勤机头像返回报文:{}", res);
                JSONObject resJson = JSONObject.parseObject(res);
                if (!"000000".equals(resJson.get("retCode"))) {
                    if ("".equals(message)) {
                        message = deviceid;
                    } else {
                        message = message + "、" + deviceid;
                    }
                } else {
                    log.warn("发放成功");
                }

                if (!"".equals(message)) {
                    message = "照片发放到设备" + message + "失败";
                }
            }
        } catch (Exception e) {
            log.warn("发放失败");
            message = "头像图片发放失败";
        }
        return message;

    }

    /**
     * 根据路径把图片转字节数组
     *
     * @param path 路径
     * @return byte[]
     */
    public static byte[] image2byte(String path) throws IOException {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int num = 0;
            while ((num = input.read(buf)) != -1) {
                output.write(buf, 0, num);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("处理图片出错：", e.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("处理图片出错：", ex.getMessage());
        }finally {
            input.close();
        }
        return data;
    }
}
