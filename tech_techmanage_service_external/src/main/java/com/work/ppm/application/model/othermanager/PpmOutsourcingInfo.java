package com.work.ppm.application.model.othermanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 描述
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PpmOutsourcingInfo {
    /**
     * PPM_OUTSOURCING_INFO_ID ??EOS??????
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
     * 是否合作
     */
    private String isFeat;



}
