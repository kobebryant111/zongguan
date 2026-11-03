package com.work.ppm.application.dto.esb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * XStreamAlias 转换名称
 * XStreamOmitField 忽略
 *
 */
@Data
public class EsbMassage<T> {
    /**
     * X
     */
    @XStreamAlias("SYS_HEAD")
    private SysHead sysHead;
    /**
     * X
     */
    @XStreamAlias("APP_HEAD")
    private AppHead appHead;
    /**
     * X
     */
    @XStreamAlias("LOCAL_HEAD")
    private LocalHead localHead;




    /**
     * X
     */
    @XStreamAlias("BODY")
    private T body;

//    /**
//     * X
//     */
//    @XStreamAlias("BODY")
//    private Object body;

//    /**
//     * 初始化公共数据
//     *
//     * @param esbMessage
//     *            esbMessage
//     * @return EsbMessage
//     */
//    public EsbMessage initEsbMessage(EsbMessage esbMessage) {
//
//        SysHead sysHead = new SysHead();
//        sysHead.setConsumerId(consumerId);
//        sysHead.setTranDate(DateUtil.getCurrentDate2());
//        sysHead.setConsumerSeqNo(this.getConsumerSeqNo());
//        sysHead.setFileFlag("0");
//        sysHead.setChannelType("1");
//        sysHead.setTranTime(DateUtil.getNowTimeStamp());
//        sysHead.setOrgConsumerSeqNo(getConsumerSeqNo());
//
//        Ret retInfo = new Ret();
//        retInfo.setReturnCode("");
//        retInfo.setReturnMsg("");
//        sysHead.listData.add(retInfo);
//        esbMessage.setSysHead(sysHead);
//        AppHead appHead = new AppHead();
//        appHead.setTranTellerNo("1101CM");
//        //appHead.setTranTellerNo("093551"); // 贵金属风险
//        if(null != this.getUserSession()){
//            appHead.setTranBranchId(this.getUserSession().getCurrentOrg().getCode());
//        }
//        //ESB定存金接口需要机构id，而这个接口业务是定时器getDeposit跑的，做判断写死机构id
//        String tranCode =  esbMessage.getBody().getStr("TranCode");
//        if("0800300001702".equals(tranCode)){
//            appHead.setTranBranchId("01101");
//        }
//        //appHead.setTranBranchId("01101");
//        LocalHead localHead = new LocalHead();
//        localHead.setTranCode("1058");
//        esbMessage.setAppHead(appHead);
//        esbMessage.setLocalHead(localHead);
//        return esbMessage;
//    }
//
//    /**
//     * 处理错误信息
//     * @return boolean
//     */
//    public boolean handleError(){
//        boolean result = false;
//        this.getLocalHead();
//        this.getLocalHead().getTranCode();
//        return result;
//    }
}
