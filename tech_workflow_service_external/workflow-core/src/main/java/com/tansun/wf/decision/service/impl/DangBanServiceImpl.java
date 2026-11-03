package com.tansun.wf.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.decision.service.InterfaceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 挡板测试数据
 */
@Service("dangBanService")
public class DangBanServiceImpl implements InterfaceService {

    @Override
    public Map<String, Object> syncInvoke(String url, Map<String, Object> params) {
        String data = "{\n" +
                "\t\"cedu__result__educationLevel\": \"硕士研究生\",\n" +
                "\t\"cedu__result__graduateYear\": \"2012\",\n" +
                "\t\"cedu__cellPhoneNumber\": \"13116685133\",\n" +
                "\t\"cedu__result__major\": \"软件工程\",\n" +
                "\t\"cedu__identityType\": \"0\",\n" +
                "\t\"cedu__identityNumber\": \"441827199411120021\",\n" +
                "\t\"cedu__personName\": \"李白\",\n" +
                "\t\"cedu__orderID\": \"8491227548399LcVOxIyHuKHg7lnvaRo\",\n" +
                "\t\"cedu__result__investigationInfo\": \"30004\",\n" +
                "\t\"cedu__result__graduateInstitution\": \"东华大学\",\n" +
                "\t\"lawxin__seconds\": \"0.203\",\n" +
                "\t\"lawxin__fxtj__shixin\": \"0\",\n" +
                "\t\"lawxin__success\": \"s\",\n" +
                "\t\"lawxin__fxtj__qianshui\": \"0\",\n" +
                "\t\"lawxin__fxtj__zhixing\": \"0\",\n" +
                "\t\"lawxin__fxtj__xiangao\": \"0\",\n" +
                "\t\"lawxin__fxtj__caipan\": \"0\",\n" +
                "\t\"lawxin__fxtj__qiankuan\": \"0\",\n" +
                "\t\"lawxin__message\": \"\",\n" +
                "\t\"lawxin__fxtj__zuifan\": \"0\",\n" +
                "\t\"lawxin__fxtj__xianchu\": \"0\",\n" +
                "\t\"lawxin__fxtj__feizheng\": \"0\",\n" +
                "\t\"lawxin__fxtj__weifa\": \"0\",\n" +
                "\t\"lawxin__fxtj__shenpan\": \"0\",\n" +
                "\t\"lawxin__seconds\": \"1.134\",\n" +
                "\t\"lawxin__fxtj__shixin\": \"0\",\n" +
                "\t\"lawxin__success\": \"s\",\n" +
                "\t\"lawxin__fxtj__qianshui\": \"0\",\n" +
                "\t\"lawxin__fxtj__zhixing\": \"0\",\n" +
                "\t\"lawxin__fxtj__xiangao\": \"0\",\n" +
                "\t\"lawxin__fxtj__caipan\": \"0\",\n" +
                "\t\"lawxin__fxtj__qiankuan\": \"0\",\n" +
                "\t\"lawxin__message\": \"\",\n" +
                "\t\"lawxin__fxtj__zuifan\": \"0\",\n" +
                "\t\"lawxin__fxtj__xianchu\": \"0\",\n" +
                "\t\"lawxin__fxtj__feizheng\": \"0\",\n" +
                "\t\"lawxin__fxtj__weifa\": \"0\",\n" +
                "\t\"lawxin__fxtj__shenpan\": \"0\",\n" +
                "\t\"zmwatchlist__error_message\": \"open_id 或 alipay_user_id 不能都为空\",\n" +
                "\t\"zmwatchlist__error_code\": \"ZMCREDIT.openid_parameter_invalid\",\n" +
                "\t\"zmwatchlist__success\": \"false\",\n" +
                "\t\"zmscore__error_message\": \"open_id 或 alipay_user_id 不能都为空\",\n" +
                "\t\"zmscore__error_code\": \"ZMCREDIT.openid_parameter_invalid\",\n" +
                "\t\"zmscore__success\": \"false\",\n" +
                "\t\"rsbl__hit\": \"0\",\n" +
                "\t\"baiqishi__strategySet__strategyScore__0\": \"0\",\n" +
                "\t\"baiqishi__strategySet__strategyId__0\": \"69a016fc54724feeabaabf07050eed40\",\n" +
                "\t\"baiqishi__finalDecision\": \"Accept\",\n" +
                "\t\"baiqishi__strategySet__hitRules__0__score__0\": \"0\",\n" +
                "\t\"baiqishi__strategySet__hitRules__0__ruleName__0\": \"3个月跨合作方身份证号关联手机号过多\",\n" +
                "\t\"baiqishi__finalScore\": \"0\",\n" +
                "\t\"baiqishi__strategySet__strategyName__0\": \"恶意贷款策略\",\n" +
                "\t\"baiqishi__strategySet__strategyDecision__0\": \"Accept\",\n" +
                "\t\"baiqishi__strategySet__hitRules__0__ruleId__0\": \"167119\",\n" +
                "\t\"baiqishi__strategySet__hitRules__0__memo__0\": \"关联个数：10\",\n" +
                "\t\"baiqishi__strategySet__hitRules__0__decision__0\": \"Accept\",\n" +
                "\t\"baiqishi__strategySet__riskType__0\": \"suspiciousAction\",\n" +
                "\t\"baiqishi__strategySet__strategyMode__0\": \"WorstMode\",\n" +
                "\t\"baiqishi__flowNo\": \"1710251108F722062A375D4189B61DDF7A0A4171025\",\n" +
                "\t\"cjdvio__result__lists__area__0\": \"京藏高速J145KM600M\",\n" +
                "\t\"cjdvio__result__lists__locationName__0\": \"河北张家口\",\n" +
                "\t\"cjdvio__result__city\": \"\",\n" +
                "\t\"cjdvio__result__lists__code__0\": \"6050\",\n" +
                "\t\"cjdvio__reason\": \"查询成功\",\n" +
                "\t\"cjdvio__resultcode\": \"200\",\n" +
                "\t\"cjdvio__result__hphm\": \"冀GFZ388\",\n" +
                "\t\"cjdvio__result__lists__handled__0\": \"0\",\n" +
                "\t\"cjdvio__error_code\": \"0\",\n" +
                "\t\"cjdvio__result__hpzl\": \"02\",\n" +
                "\t\"cjdvio__result__lists__fen__0\": \"0\",\n" +
                "\t\"cjdvio__result__lists__money__0\": \"0\",\n" +
                "\t\"cjdvio__result__province\": \"\",\n" +
                "\t\"cjdvio__result__lists__date__0\": \"2016-10-30 13:41:00\",\n" +
                "\t\"cjdvio__result__lists__act__0\": \"驾驶中型以上载客载货汽车、危险物品运输车辆以外的机动车超过规定时速10以下的\",\n" +
                "\t\"cjdvio__id\": \"0310a46b41c842daa03d6ab0ad92d766\",\n" +
                "\t\"hgbi__servicephone\": \"95577\",\n" +
                "\t\"hgbi__logourl\": \"http://www.apix.cn/banklogo/hxb.png\",\n" +
                "\t\"hgbi__bankurl\": \"http://www.hxb.com.cn/\",\n" +
                "\t\"hgbi__city\": \"\",\n" +
                "\t\"hgbi__cardname\": \"华夏普卡\",\n" +
                "\t\"hgbi__cardprefixnum\": \"623020\",\n" +
                "\t\"hgbi__cardtype\": \"借记卡\",\n" +
                "\t\"hgbi__banknum\": \"03040000\",\n" +
                "\t\"hgbi__province\": \"\",\n" +
                "\t\"hgbi__bankname\": \"华夏银行\",\n" +
                "\t\"caxq3101__projectCode\": \"840968666783\",\n" +
                "\t\"caxq3101__userId\": \"591D52D7E8036B3FE05312016B0A6076\",\n" +
                "\t\"caxq3101__isSendVoice\": \"0\",\n" +
                "\t\"caxq3001__authenticationMode\": \"公安部数据联网核查\",\n" +
                "\t\"caxq3001__anXinSignMobilePhone\": \"188****0248\",\n" +
                "\t\"caxq3001__identNo\": \"362203198804104316\",\n" +
                "\t\"caxq3001__identTypeCode\": \"0\",\n" +
                "\t\"caxq3001__userId\": \"547B1DE846D620A5E05311016B0A2DAA\",\n" +
                "\t\"caxq3001__personName\": \"郭小俊\",\n" +
                "\t\"caxq3001__mobilePhone\": \"18810500248\",\n" +
                "\t\"caxq3102__checkCode\": \"935279\",\n" +
                "\t\"caxq3102__projectCode\": \"4022395820\",\n" +
                "\t\"caxq3102__userId\": \"591D52D7E8036B3FE05312016B0A6076\",\n" +
                "\t\"qhcdk__busiData__last1MonMuliAppCnt\": \"0\",\n" +
                "\t\"qhcdk__busiData__records__queryAmtM3__0\": \"\",\n" +
                "\t\"qhcdk__busiData__last3MonMuliAppCnt\": \"0\",\n" +
                "\t\"qhcdk__busiData__records__industry__0\": \"\",\n" +
                "\t\"qhcdk__header__authCode\": \"CRT001A2\",\n" +
                "\t\"qhcdk__busiData__records__idType__0\": \"02\",\n" +
                "\t\"qhcdk__header__authDate\": \"2017-10-25 11:10:33\",\n" +
                "\t\"qhcdk__header__transDate\": \"2017-10-25 11:10:33\",\n" +
                "\t\"qhcdk__header__rtMsg\": \"交易成功\",\n" +
                "\t\"qhcdk__oriMessage\": \"\",\n" +
                "\t\"qhcdk__busiData__records__cnssAmount__0\": \"\",\n" +
                "\t\"qhcdk__busiData__records__idNo__0\": \"441827199411120021\",\n" +
                "\t\"qhcdk__busiData__records__amount__0\": \"\",\n" +
                "\t\"qhcdk__busiData__records__seqNo__0\": \"1324567899\",\n" +
                "\t\"qhcdk__header__orgCode\": \"10000000\",\n" +
                "\t\"qhcdk__busiData__records__p2pAmount__0\": \"\",\n" +
                "\t\"qhcdk__header__transNo\": \"15089010335576478\",\n" +
                "\t\"qhcdk__busiData__records__queryAmt__0\": \"\",\n" +
                "\t\"qhcdk__busiData__records__reasonCode__0\": \"01\",\n" +
                "\t\"qhcdk__busiData__records__erCode__0\": \"E000001\",\n" +
                "\t\"qhcdk__busiData__records__bnkAmount__0\": \"\",\n" +
                "\t\"qhcdk__header__chnlId\": \"qhcs-dcs\",\n" +
                "\t\"qhcdk__header__rtCode\": \"E000000\",\n" +
                "\t\"qhcdk__busiData__records__erMsg__0\": \"证件类型长度超过1\",\n" +
                "\t\"qhcdk__busiData__records__busiDate__0\": \"\",\n" +
                "\t\"qhcdk__busiData__records__queryAmtM6__0\": \"\",\n" +
                "\t\"qhcdk__busiData__records__name__0\": \"谢啊\",\n" +
                "\t\"qhcdk__busiData__batchNo\": \"15089010335572237\",\n" +
                "\t\"zmantifraudlist__risk_code\": \"R_AD_001\",\n" +
                "\t\"zmantifraudlist__hit\": \"yes\",\n" +
                "\t\"zmantifraudlist__biz_no\": \"ZM201702153000000835100652190350\",\n" +
                "\t\"zmantifraudlist__success\": \"true\",\n" +
                "\t\"brtc__code\": \"600000\",\n" +
                "\t\"brtc__product__operation\": \"2\",\n" +
                "\t\"brtc__flag__flag_telCheck\": \"1\",\n" +
                "\t\"brtc__product__result\": \"2\",\n" +
                "\t\"brtc__product__costTime\": \"4\",\n" +
                "\t\"brtc__swift_number\": \"3000610_20171025111055_7071\",\n" +
                "\t\"rsbl__hit\": \"0\",\n" +
                "\t\"brma__als_m6_id_nbank_max_monnum\": \"1\",\n" +
                "\t\"brma__swift_number\": \"3000610_20171025111053_9591\",\n" +
                "\t\"brma__als_m12_id_nbank_selfnum\": \"1\",\n" +
                "\t\"brma__als_m6_id_nbank_max_inteday\": \"\",\n" +
                "\t\"brma__code\": \"00\",\n" +
                "\t\"brma__als_m6_id_tot_mons\": \"1\",\n" +
                "\t\"brma__als_m6_id_min_monnum\": \"0\",\n" +
                "\t\"brma__als_m12_id_nbank_allnum\": \"1\",\n" +
                "\t\"brma__als_m6_id_max_inteday\": \"\",\n" +
                "\t\"brma__als_m6_id_nbank_ca_allnum\": \"1\",\n" +
                "\t\"brma__als_m12_id_nbank_min_inteday\": \"\",\n" +
                "\t\"brma__als_m6_id_nbank_min_monnum\": \"0\",\n" +
                "\t\"brma__als_m6_id_nbank_orgnum\": \"1\",\n" +
                "\t\"brma__als_m6_id_nbank_min_inteday\": \"\",\n" +
                "\t\"brma__als_m12_id_nbank_min_monnum\": \"0\",\n" +
                "\t\"brma__als_m12_id_avg_monnum\": \"1.00\",\n" +
                "\t\"brma__als_m12_id_nbank_ca_orgnum\": \"1\",\n" +
                "\t\"brma__als_m6_id_nbank_ca_orgnum\": \"1\",\n" +
                "\t\"brma__als_m12_id_min_inteday\": \"\",\n" +
                "\t\"brma__als_m6_id_min_inteday\": \"\",\n" +
                "\t\"brma__als_m12_id_nbank_tot_mons\": \"1\",\n" +
                "\t\"brma__als_m12_id_max_monnum\": \"1\",\n" +
                "\t\"brma__als_lst_id_nbank_consnum\": \"1\",\n" +
                "\t\"brma__als_m12_id_max_inteday\": \"\",\n" +
                "\t\"brma__als_m6_id_avg_monnum\": \"1.00\",\n" +
                "\t\"brma__als_m12_id_nbank_max_monnum\": \"1\",\n" +
                "\t\"brma__als_m6_id_nbank_avg_monnum\": \"1.00\",\n" +
                "\t\"brma__flag_applyloanstr\": \"1\",\n" +
                "\t\"brma__als_m12_id_nbank_ca_allnum\": \"1\",\n" +
                "\t\"brma__als_m12_id_tot_mons\": \"1\",\n" +
                "\t\"brma__als_m12_id_nbank_avg_monnum\": \"1.00\",\n" +
                "\t\"brma__als_lst_id_nbank_csinteday\": \"1\",\n" +
                "\t\"brma__als_fst_id_nbank_inteday\": \"103\",\n" +
                "\t\"brma__als_m6_id_nbank_tot_mons\": \"1\",\n" +
                "\t\"brma__als_m6_id_nbank_selfnum\": \"1\",\n" +
                "\t\"brma__als_m12_id_min_monnum\": \"0\",\n" +
                "\t\"brma__als_m6_id_nbank_allnum\": \"1\",\n" +
                "\t\"brma__als_m6_id_max_monnum\": \"1\",\n" +
                "\t\"brma__als_m12_id_nbank_max_inteday\": \"\",\n" +
                "\t\"brma__als_m12_id_nbank_orgnum\": \"1\",\n" +
                "\t\"brma__als_lst_id_nbank_inteday\": \"103\",\n" +
                "\t\"brbl__code\": \"100002\",\n" +
                "\t\"brbl__swift_number\": \"3000610_20171025110955_6546\",\n" +
                "\t\"brbl__flag_specialList_c\": \"0\"\n" +
                "}";
        return JSON.parseObject(data,Map.class);
    }
}
