package com.work.ppm.application.dto.esb;


import com.work.ppm.commons.annotaion.esb.EsbConfig;
import com.work.ppm.commons.annotaion.esb.EsbProxy;
import com.work.ppm.commons.aware.OrgConsumerSeqNoAware;
import com.work.ppm.commons.exception.BizExceptionNew;
import com.work.ppm.commons.exception.ExceptionEnum;
import com.work.ppm.commons.utils.StringUtilNew;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import lombok.Data;

/**
 * esb请求对象
 */
@Data
@XStreamAliasType("service")
public class EsbRequestMessage<T> extends EsbMassage {


    /**
     * 初始化
     * @param bodyReq
     * @param esbConfig
     */
    public void init(T bodyReq, EsbConfig esbConfig, EsbProxy esbProxy){
        if(esbConfig==null){
            esbConfig = bodyReq.getClass().getAnnotation(EsbConfig.class);
        }
        if(esbProxy==null){
            esbProxy = bodyReq.getClass().getAnnotation(EsbProxy.class);
        }
        //头信息
        SysHead sysHead = new SysHead();
        sysHead.setServiceCode(esbConfig.serviceCode());
        sysHead.setServiceScene(esbConfig.serviceScene());
        //设置流水
        sysHead.setConsumerSeqNo(StringUtilNew.getPrimarykey());
        if(bodyReq instanceof OrgConsumerSeqNoAware){
            sysHead.setOrgConsumerSeqNo(((OrgConsumerSeqNoAware) bodyReq).getOrgConsumerSeq());
        }else{
            sysHead.setOrgConsumerSeqNo(StringUtilNew.getPrimarykey());
        }
        //转发接口
        if(esbProxy!=null&&bodyReq instanceof  EsbProxyReq){
            EsbProxyReq proxyReq = (EsbProxyReq) bodyReq;
            if(StringUtilNew.isEmpty(esbProxy.svcNm())){
                proxyReq.setSvcNm("esb");//查询服务名称
            }else {
                proxyReq.setSvcNm(esbProxy.svcNm());
            }
            proxyReq.setUrl(esbProxy.url());
            proxyReq.setSendTp(esbProxy.sendTp());
            proxyReq.setPsThgFlg(esbProxy.psThgFlg());
            proxyReq.setTxnCd(esbProxy.txnCd());
        }

        this.setSysHead(sysHead);
        //交易人 交易机构
        AppHead appHead = new AppHead();
        this.setAppHead(appHead);

        LocalHead localHead = new LocalHead();
        localHead.setTranCode(esbConfig.tranCode());
        this.setLocalHead(localHead);




        this.setBody(bodyReq);
    }

    /**
     * 构造方法
     */
    public EsbRequestMessage(T bodyReq, EsbConfig esbConfig, EsbProxy esbProxy){
        init(bodyReq,esbConfig,esbProxy);
    }

    /**
     * 构造方法
     */
    public EsbRequestMessage(T bodyReq, EsbConfig esbConfig){
        EsbProxy esbProxy = bodyReq.getClass().getAnnotation(EsbProxy.class);
        init(bodyReq,esbConfig,esbProxy);
    }


    /**
     * 构造方法
     * @param bodyReq
     */
    public EsbRequestMessage(T bodyReq){
        EsbConfig esbConfig = bodyReq.getClass().getAnnotation(EsbConfig.class);
        EsbProxy esbProxy = bodyReq.getClass().getAnnotation(EsbProxy.class);
        if(esbConfig == null){
            throw new BizExceptionNew(ExceptionEnum.NOT_SET_ESB_CONFIG);
        }
        init(bodyReq,esbConfig,esbProxy);
    }


//    /**
//     * 转xml
//     * @return
//     */
//    public String toEsbXml(){
//        String xml = XmlUtil.beanToXml(this);
//        int length = xml.getBytes().length;
//        String lengthStr = String.format("%6d",length);
//        return lengthStr+xml;
//    }
}
