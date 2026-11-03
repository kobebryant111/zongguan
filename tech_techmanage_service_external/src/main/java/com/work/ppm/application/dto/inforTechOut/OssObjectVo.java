package com.work.ppm.application.dto.inforTechOut;

import com.component.work.fileupload.domain.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "oss对象Vo")
public class OssObjectVo extends BaseModel implements Serializable {


    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private String id;

    /*    @ApiModelProperty(
            value = "主键",
            example = "1",
            accessMode = ApiModelProperty.AccessMode.READ_ONLY,
            hidden = true
    )
    private String id;*/
    @ApiModelProperty(
            value = "文件对象UUID",
            example = "f02bdd3a-2679-49cc-b77f-be67acb359fb",
            position = 1
    )
    private String objectId;
    @ApiModelProperty(
            value = "版本來源（指向ID）",
            example = "@3A7F8EBD",
            position = 2
    )
    private String versionRef;
    @ApiModelProperty(
            value = "原文件名",
            example = "import.excel",
            position = 3
    )
    private String originName;
    @ApiModelProperty(
            value = "文件名",
            example = "@3A7F8EBD.excel",
            position = 4
    )
    private String name;
    @ApiModelProperty(
            value = "文件后缀名",
            example = ".jpg",
            position = 5
    )
    private String suffix;
    @ApiModelProperty(
            value = "文件描述",
            example = "关于文件xxx的描述",
            position = 6
    )
    private String description;


    @ApiModelProperty(
            value = "业务类型（区分来源如：标准文本的正文）",
            example = "NB_STANDARD_TEXT/ZW",
            position = 7
    )
    private String bizType;


    @ApiModelProperty(
            value = "文件Content-Type",
            example = "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            position = 8
    )
    private String contentType;
    @ApiModelProperty(
            value = "文件大小（单位：B）",
            example = "136484642",
            position = 9
    )
    private Long fileSize;
    @ApiModelProperty(
            value = "相对路径",
            example = "sub/a/b",
            position = 10
    )
    private String relativePath;
    @ApiModelProperty(
            value = "绝对路径",
            example = "/shareDir/upload/sub/a/b",
            position = 11
    )
    private String absolutePath;
    @ApiModelProperty(
            value = "文件校验码",
            example = "a4dddb65c2d18f542571ab699d46c04b32eef27c",
            position = 12
    )
    private String hash;
    @ApiModelProperty(
            value = "文件上传时间",
            example = "2019-09-04 19:02:25",
            position = 13,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    protected Date uploadTime;
    @ApiModelProperty(
            value = "上传人",
            example = "sysadmin",
            position = 14,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    protected String uploadOperator;

    /**
     * 存储桶
     */
    @ApiModelProperty(value = "存储桶", example = "mercuryoa-bucket", position = 20)
    private String bucket;


    @ApiModelProperty(
            value = "业务主键",
            example = "a4dddb65c2d18f542571ab699d46c04b32eef27c",
            position = 21
    )
    private String bizKey;


    @ApiModelProperty(
            value = "是否當前版本",
            example = "1",
            position = 22
    )
    private String curtVersion= "1";

    @ApiModelProperty(
            value = "是否正常（0否1是）",
            example = "1",
            position = 23
    )
    private String flag;


    @ApiModelProperty(
            value = "上传人姓名",
            example = "张三",
            position = 24,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    protected String uploadOperatorName;


    /**
     * 构造函数
     */
    public OssObjectVo() {

    }

    /**
     * 构造函数 通过上传文件对象和tenant.bucket
     *
     * @param multipartFile 上传文件对象
     * @param //tenant        租户 tenant,String tenant,
     * @param bucket        存储桶
     */
    public OssObjectVo(MultipartFile multipartFile, String bucket) {
        init(multipartFile,  bucket);
    }

    /**
     * 通过MultipartFile对象初始化
     *
     * @param multipartFile 上传文件对象
     * @param //tenant        租户 String tenant,
     * @param bucket        存储桶
     */
    private void init(MultipartFile multipartFile,  String bucket) {
        String tempSuffix = "";
        if (multipartFile.getOriginalFilename().lastIndexOf('.') > 0) {
            tempSuffix = multipartFile.getOriginalFilename()
                    .substring(multipartFile.getOriginalFilename().lastIndexOf('.')).toLowerCase();
        }
//        String operator = UserManager.currentUserAccount();
//        this.tenant = tenant;
        this.versionRef = versionRef;
        this.bucket = bucket;
        this.originName= multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().replace("\\","/").lastIndexOf("/")+1);
        this.suffix = tempSuffix;
        this.contentType = multipartFile.getContentType();
        this.fileSize = multipartFile.getSize();
        this.uploadTime = new Date();
//        this.uploadOperator = operator;
        this.lastUpdateTime = new Date();
//        this.lastUpdateOperator = operator;
    }
}

