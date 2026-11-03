package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.dto.projectmanager.DemandFileDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.model.othermanager.PpmDocument;

import java.util.List;
import java.util.Map;

/**
 * 文档
 */
public interface IPpmDocumentService {

    /**
     * 分页
     * @param ppmDocument 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmDocument ppmDocument, Pager pager) ;

    /**
     * 获取图片地址
     * @param documentno     对象
     * @return PpmDocument
     */

    PpmDocument getImageUrl(String documentno) ;

    /**
     * 更新
     * @param ppmDocument 对象
     * @return Integer
     */
    Integer updatePpmDocument(PpmDocumentDto ppmDocument) ;

    /**
     * 删除
     * @param ppmDocument 对象
     * @return Integer
     */
    Integer deletePpmDocument(PpmDocumentDto ppmDocument) ;

    /**
     * 获取文档名称
     * @return String
     */
    String getDocmentnoSeq() ;

    /**
     * 添加
     * @param ppmDocument 对象
     * @return int
     */
    int addDocment(PpmDocument ppmDocument) ;

    /**
     * 获取文档是否有下载权限
     * @param documentno 文档编号
     * @param clientIp  客户端ip
     * @param versionName 版本名称
     * @param type 类型
     * @param userId 当前登录id
     * @param name 当前登录名称
     * @return PpmDocumentDto
     */
    PpmDocumentDto authDocument(String documentno, String clientIp, String versionName,
                                String type ,String userId,String name);

    /**
     * 根据编号查询详情
     * @param documentno 编号
     * @return PpmDocument
     */
    PpmDocument getDetail(String documentno);

    /**
     * 根据编号查询详情
     * @param objectno 对象编号
     * @return PpmDocumentDto
     */
    PpmDocumentDto getDetail(CharSequence objectno);
    /**
     * 根据编号查询详情
     * @param stockpath 路径
     * @param docname 名称
     * @return PpmDocument
     */
    PpmDocument getDetail(String stockpath, String docname);
    /**
     * 根据ObjectType和ObjectNo查询文件
     * @param param 集合
     * @return List<PpmDocument>
     */
     List<PpmDocument> queryDocumentsInfos(Map param);

    /**
     * 预览
     * @param documentno 文档编号
     * @return PpmDocument
     */
    PpmDocument getPreview(String documentno);

    /**
     * 获取下载链接
     * @param dto  对象
     * @return PpmDocumentDto
     */
    PpmDocumentDto getDownloadUrl(PpmDocumentDto dto);

    /**
     * 获取文档路径和名字
     * @param documentno
     * @return String
     */
    String getFileDetail(PpmDocumentDto documentno);

    /**
     * 获取文档下载路径
     * @param documentno 编号
     * @param type 类型
     * @param versionName 版本
     * @return PpmDocumentDto
     */
    PpmDocumentDto getDownloadFilePath(String documentno,String type,String versionName);

    /**
     * 添加文档
     * @param demandFileList 集合
     * @param demandNo 意向编号
     * @param userId 用户编号
     * @return int
     */
    int addDocment(List<DemandFileDto> demandFileList,String demandNo,String userId);

    /**
     * 根据意向编号查询集合
     * @param objectNo 意向编号
     * @return list
     */
    List<PpmDocumentDto> getList(String objectNo);
}
