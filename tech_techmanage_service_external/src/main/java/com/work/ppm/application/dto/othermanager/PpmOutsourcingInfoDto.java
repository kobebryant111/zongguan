package com.work.ppm.application.dto.othermanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingInfoPriceDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 描述
 *
 */
@Data
public class PpmOutsourcingInfoDto {
    /**
     * 编号
     **/
    @Id
    private Long id;
    /**
     * 字段名
     **/
    private String outsourcname;
    /**
     * 字段名
     **/
    private String orgcode;
    /**
     * 字段名
     **/
    private String regcapital;
    /**
     * 字段名
     **/
    private Date establishtime;
    /**
     * 字段名
     **/
    private String companyaddress;
    /**
     * 字段名
     **/
    private String mainbusiness;
    /**
     * 字段名
     **/
    private String service;
    /**
     * 字段名
     **/
    private String contactsname;
    /**
     * 字段名
     **/
    private String contactspost;
    /**
     * 字段名
     **/
    private String contactsemail;
    /**
     * 字段名
     **/
    private String caseinfo;
    /**
     * 字段名
     **/
    private String qualificationinfo;
    /**
     * 字段名
     **/
    private String companystaser;
    /**
     * 字段名
     **/
    private String telephone;
    /**
     * 字段名
     **/
    private Long companynum;
    /**
     * 字段名
     **/
    private Long isdel;
    /**
     * 字段名
     **/
    private Date createtime;
    /**
     * 字段名
     **/
    private String creators;
    /**
     * 字段名
     **/
    private Date lastmodtime;
    /**
     * 字段名
     **/
    private String lastmoduser;
    /**
     * 简称
     **/
    private String abbreviation;
    /**
     * 合同编号
     **/
    private String contractno;
    /**
     * 合同名称
     **/
    private String contractname;
    /**
     * 采购供应商编号
     */
    private String cgSupplierId;
    /**
     * 供应商编号查询
     */
    private String orgCodeVal;
    /**
     * 是否合作
     */
    private String isFeat;
    /**
     * 供应商详情信息
     */
    @JsonInclude
    private List<PpmSupplierRosterInfoDto> supplierRosterInfoDtos;
    /**
     * 价格信息表
     */
    @JsonInclude
    private List<PpmOutsourcingInfoPriceDto> outsourcingInfoPriceDtos;

}
