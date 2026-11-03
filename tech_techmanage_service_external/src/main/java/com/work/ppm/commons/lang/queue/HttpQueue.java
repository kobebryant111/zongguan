package com.work.ppm.commons.lang.queue;

import com.work.ppm.application.model.othermanager.PpmWeixin;
import com.work.ppm.application.services.othermanager.IPpmWeixinService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.lang.notice.SendMessage;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.application.quartz.core.ExecutorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * http队列
 */
@Slf4j
@Component
public class HttpQueue  {
    /**
     * 初始化
     */
    private static BlockingQueue<MobileHttpQueue> queue = new LinkedBlockingQueue<>(50);

    /**
     * 构造
     */
    public HttpQueue() {
        super();
    }
    
    /**
     * 有参构造
     */
    public HttpQueue(MobileHttpQueue e) {
        this.add(e);
    }
    
    /**
     * 工作平台接口
     */
    @Autowired
    private IPpmWeixinService ppmWeixinService;
    /**
     * 业务字典接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     * 添加列队
     */
    public void add(MobileHttpQueue e) {
        boolean falge = queue.offer(e);
        log.info("queue.take()::{}", falge);
    }

    /**
     * 异步执行队列信息
     */
    @PostConstruct
    public void run() {
        try{
            ExecutorManager.getExecutor()
                    .execute(()->{
                MobileHttpQueue httpQueue = null;
               try{
                   for(;;) {
                       httpQueue = queue.take();
                       String data = HttpClient.post(httpQueue.getUrl(),httpQueue.getParams(), httpQueue.getToken(),
                               httpQueue.getSecertKey());
                       String josnData = JsonUtil.toJSONString(data);
                       Map<String,Object> map = JsonUtil.jsonToMap(josnData);
                       String status = map.get("status") + "";
                       if("200".equals(status)) {
                           ppmWeixinService.updatePpmWeixin("0", httpQueue.getProcessInstId());
                       } else {
                           log.error("=======>获取队列信息异常{},数据：{},状态码：{}", httpQueue.getWorkitemId(),josnData, status);
                           errorSendSMS(httpQueue.getWorkitemId());
                       }
                   }
               }catch (Exception e) {
                   log.error("获取队列信息异常{}", e);
                   errorSendSMS(httpQueue.getWorkitemId());
               }
            });
            
        }catch (Exception e) {
            log.error("因队列异常,将退出轮询{}", e);
        }

    }

    /**
     * 流程异常发送短信
     * @param workitemId 流程工作项id
     */
    private void errorSendSMS(String workitemId) {
        try{
            // 流程流转失败，发送短信
            PpmWeixin weixin = ppmWeixinService.queryDetail(workitemId);
            String content="【"+weixin.getFlowtext()+"】流转失败。审批人：【"+weixin.getUsers()+"】，"
                    + "流程id：【"+weixin.getInstanceid() +"】，节点id【"+weixin.getActivityinstid()+"】。【新应用移动端】";
            String userId = eosDictEntryService.getTypeidForDetail("MOBILEOASUBMITUSERS", "01").getDictname();
            SendMessage sendMessage = new SendMessage();
            sendMessage.sendESBText(userId, content);
        }catch (Exception e) {
            log.error("发送短信异常:{}", e);
        }
    }
}
