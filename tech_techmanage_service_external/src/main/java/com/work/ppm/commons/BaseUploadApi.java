package com.work.ppm.commons;

import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.othermanager.PpmDocumentCatalog;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.SpringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 基础上传公共类
 *
 */
@Api(produces = "application/json;charset=UTF-8", tags = {"BaseUploadApi"}, description = "基础上传公共类")
@Slf4j
public class BaseUploadApi extends BaseApi {

    /**
     * 图片上传公共方法
     *
     * @param objectNo  编号
     * @param type      类型
     * @param catalogNo 目录编号
     * @return String
     */
    public String uploadImage(String objectNo, String type, String catalogNo, HttpServletRequest request) throws IOException {
        OutputStream os = null;
        try {
            Date date = new Date();
            String newName = "";
            String orgFileName = getFile().getOriginalFilename();
            IPpmDocumentCatalogService ppmDocumentCatalogService = (IPpmDocumentCatalogService) SpringUtil.getBean("ppmDocumentCatalogService");
            IPpmDocumentService ppmDocumentService = (IPpmDocumentService) SpringUtil.getBean("ppmDocumentService");
            IEosDictEntryService eosDictEntryService = (IEosDictEntryService) SpringUtil.getBean("eosDictEntryService");
            CommOperatorDoLoginDto system = super.getCurrentUser(request);

            // 文件别名
            String aliasName = DateUtil.dateToStr(date, "yyyyMMddHHmmss");
            // 文件后缀
            String suffix = orgFileName.substring(orgFileName.lastIndexOf("."));

            if (orgFileName.indexOf("\\") != -1) {
                orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\") + 1);
            }
            // 新文件
            newName = aliasName + suffix;

            // 获取上传路径
            PpmDocumentCatalog catalog = ppmDocumentCatalogService.queryDetail(catalogNo);
            // 添加记录
            PpmDocument document = new PpmDocument();
            document.setCatalogno(catalogNo);
            document.setObjectno(objectNo);
            document.setObjecttype(type);
            document.setStockpath(catalog.getStockpath());
            document.setDocname(orgFileName);
            document.setTag2(newName);  // 真实上传文件名称
            document.setCreateorid(system.getUserid());
            document.setCreators(system.getOperatorname() + "(" + system.getUserid() + ")");
            document.setCreatetime(date);
            document.setLastmodtime(date);
            String docNo = ppmDocumentService.getDocmentnoSeq();
            document.setDocumentno(docNo);
            document.setIparea(getIp());
            document.setLastmoduser(system.getOperatorname());
            ppmDocumentService.addDocment(document);

            // 上传图片
            EosDictEntry dicItem = eosDictEntryService.getTypeidForDetail("FileuploadRootpath", "rootPath");
            String uploadPath = dicItem.getDictname() + "/ppmtest" + document.getStockpath();
            File newfile = new File(uploadPath, newName);

            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            os = new FileOutputStream(newfile, true);
            os.write(getFile().getBytes());
            os.flush();
            return docNo;
        } catch (Exception e) {
            log.error("上传异常",e);
            throw new BizException(ApiCode.FAIL, "图片上传异常");
        } finally {
            os.close();
        }
    }

    /**
     * 下载文件
     *
     * @param documentno 文档编号
     * @param response   response
     */
    public void download(String documentno, HttpServletResponse response) throws IOException {
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
        InputStream inputStream = null;
        try {
            IPpmDocumentService ppmDocumentService = (IPpmDocumentService) SpringUtil.getBean("ppmDocumentService");
            PpmDocumentDto documentDto = ppmDocumentService.getDownloadFilePath(documentno, null, null);
            OutputStream out = response.getOutputStream();
            log.warn("===========》下载地址：{}", documentDto.getTag1());
            inputStream = new FileInputStream(documentDto.getTag1());

            String host = null != documentDto.getDocname() ? documentDto.getDocname() : documentDto.getDocdomain();
            response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode("" + host + "", "UTF-8") + "\"");
            IOUtils.copy(inputStream, out);
            inputStream.close();
            response.getOutputStream().flush();

        } catch (Exception e) {
            log.error("文件下载异常:{}", e);
            throw new BizException(e);
        } finally {
            inputStream.close();
        }
    }

    /**
     * 查询是否有下载文档权限
     *
     * @param documentno  文档编号
     * @param versionName 版本
     * @param type        类型
     * @param user        session
     * @return PpmDocumentDto 对象
     */
    public PpmDocumentDto getDocumentAuth(String documentno, String type, String versionName,
                                          CommOperatorDoLoginDto user) {
        log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>本地ip：{}<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", ip);
        IPpmDocumentService ppmDocumentService = (IPpmDocumentService) SpringUtil.getBean("ppmDocumentService");
        // 文档权限
        PpmDocumentDto documentDto = ppmDocumentService.authDocument(documentno, ip,
                versionName, type, user.getUserid(), user.getOperatorname());
        return documentDto;
    }

    /**
     * 对象
     */
    private MultipartFile file;
    /**
     * ip
     */
    private String ip;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static Logger getLog() {
        return log;
    }
}
