package com.work.ppm.commons.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送配置
 */
public class WechatSendProperties {
    /**
     * mpa
     */
    private final static Map<String, String> map = new HashMap<>();

    /**
     * getConfig
     * @return map
     */
    public static Map<String, String> getConfig() {
        map.put("com.work.ppm.bps.newxmlx", "WEIXIN_URL:02,TradeType:newxmlx");
        map.put("com.work.ppm.bps.sjzxxmlx", "WEIXIN_URL:02,TradeType:sjzxxmlx");

        map.put("com.work.ppm.bps.rjkflxmlx", "WEIXIN_URL:02,TradeType:PutNotice1");
        map.put("com.work.ppm.bps.jfryjcgl", "WEIXIN_URL:02,TradeType:ITRoom");
        map.put("com.work.ppm.bps.jhtc", "WEIXIN_URL:02,TradeType:PlanProduct");
        map.put("com.work.ppm.bps.jhtcnew", "WEIXIN_URL:02,TradeType:PlanProductNew");
        map.put("com.work.ppm.bps.fjhtc", "WEIXIN_URL:02,TradeType:PlanProduct");
        map.put("com.work.ppm.bps.fjhtcnew", "WEIXIN_URL:02,TradeType:PlanProductNew");
        map.put("com.work.ppm.bps.bdtc", "WEIXIN_URL:02,TradeType:PlanProduct");
        map.put("com.work.ppm.bps.xmsx", "WEIXIN_URL:01,TradeType:PutNotice");
        map.put("com.work.ppm.bps.ywxqgl", "WEIXIN_URL:01,TradeType:PutNotice");
        map.put("com.work.ppm.bps.ccgl", "WEIXIN_URL:02,TradeType:BusinessTrip");
        map.put("com.work.ppm.bps.wlbg", "WEIXIN_URL:02,TradeType:NetWorkChange,BisnessLine:WLBG");
        map.put("com.work.ppm.bps.sjcxgl", "WEIXIN_URL:02,TradeType:TecSearch");
        map.put("com.work.ppm.bps.jjwh", "WEIXIN_URL:02,TradeType:JJWHapply");
        map.put("com.work.ppm.bps.kjgzgl", "WEIXIN_URL:02,TradeType:FaultApply");
        map.put("com.work.ppm.bps.ywsjcx", "WEIXIN_URL:02,TradeType:BusinessDataSearch");
        map.put("com.work.ppm.bps.jjsjcx", "WEIXIN_URL:02,TradeType:UrgentQuery");
        map.put("com.work.ppm.bps.sjwhdlc", "WEIXIN_URL:02,TradeType:dataWH");
        map.put("com.work.ppm.bps.xmzbtx", "WEIXIN_URL:02,TradeType:ProjectWeekReport");
        map.put("com.work.ppm.bps.xmbg", "WEIXIN_URL:02,TradeType:projectChange");
        map.put("com.work.ppm.bps.zxmbl", "WEIXIN_URL:02,TradeType:SubProCheckIn");
        map.put("com.work.ppm.bps.fwqbg", "WEIXIN_URL:02,TradeType:ServeChange");
        map.put("com.work.ppm.bps.bdfxgl", "WEIXIN_URL:02,TradeType:PpmPatchServiceWX");
        map.put("com.work.ppm.bps.newsjwh", "WEIXIN_URL:02,TradeType:newSJWH");
        map.put("com.work.ppm.bps.jjsjcxpl", "WEIXIN_URL:02,TradeType:Jjcxpl");
        map.put("com.work.ppm.bps.jklc", "WEIXIN_URL:02,TradeType:MonitorChange");
        map.put("com.work.ppm.bps.cdjy", "WEIXIN_URL:02,TradeType:Cdjy");
        map.put("com.work.ppm.bps.pwd", "WEIXIN_URL:02,TradeType:Pwd");
        map.put("com.work.ppm.bps.wbdwxxtg", "WEIXIN_URL:02,TradeType:OutOrg");
        map.put("default", "WEIXIN_URL:02,TradeType:PushNotice");
        map.put("com.work.ppm.bps.newdemand", "WEIXIN_URL:02,TradeType:NewDemandService");
        map.put("com.work.ppm.bps.xmsxNew", "WEIXIN_URL:01,TradeType:PutNotice");

        map.put("com.work.ppm.bps.onlineChange", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.wbRiskInspect", "WEIXIN_URL:02,TradeType:wbRiskJK");
        map.put("com.work.ppm.bps.wbRiskMonitor", "WEIXIN_URL:02,TradeType:wbRiskPG");
        map.put("com.work.ppm.bps.fxwtgl", "WEIXIN_URL:02,TradeType:fxwtgl");
        map.put("com.work.ppm.bps.fwqbgfa", "WEIXIN_URL:02,TradeType:ServeChangefa");

        map.put("com.work.ppm.bps.newProjectBuildSoft", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.newzxml", "WEIXIN_URL:02,TradeType:PutNotice");

        map.put("com.work.ppm.bps.psglnew", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.overtimeinfofillin", "WEIXIN_URL:02,TradeType:overtime");
        map.put("com.work.ppm.bps.accessApply", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.sjbg", "WEIXIN_URL:02,TradeType:PutNotice");

        map.put("com.work.ppm.bps.jdzgl", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.zdxmhgps", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.zlsbip", "WEIXIN_URL:02,TradeType:PutNotice");

        map.put("com.work.ppm.bps.zdywsxsb", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.yjqhyl", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.fxaqwtgl", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.xtxx", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.sortie", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.hjtjbl", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.slayq", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.wtdb", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.inspectionerror", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.wwsq", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.fhwlbg", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.autoworkchange", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.wbryzrlc", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.wbrylzsq", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.performanceService", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.wbszr", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.glzxzdgz", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.creditChange", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.ywzzcx", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.fhfzyxq", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.fhfzyxmsx", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.yybg", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.yybgzlc", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.sjzxxmbg", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.sjzxxmjx", "WEIXIN_URL:02,TradeType:PutNotice");

        map.put("com.work.ppm.bps.systemEvaluation", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.fhxmlx", "WEIXIN_URL:02,TradeType:fhxmlx");
        map.put("com.work.ppm.bps.kjcqrlwbcgsp", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.bdywfy", "WEIXIN_URL:02,TradeType:bdywfy");
        map.put("com.work.ppm.bps.wtdbyq", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.xqlc", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.hyjlwdqr", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.daCenterDemandManager", "WEIXIN_URL:02,TradeType:PutNotice");
        map.put("com.work.ppm.bps.daCenterPostGovernor","WEIXIN_URL:02,TradeType:PutNotice");
        return map;
    }
}
