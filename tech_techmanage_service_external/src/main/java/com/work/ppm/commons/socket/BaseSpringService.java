package com.work.ppm.commons.socket;

import com.work.ppm.commons.socket.system.CacheReloadEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * BaseSpringService
 *
 */
public abstract class BaseSpringService {

    /**
     * onStart spring
     */
    protected void onStart(){

    }

    /**
     * on stop spring
     */
    protected void onStop(){

    }

    /**
     * on init service bean
     */
    protected void onInit(){

    }

    /**
     * afterPropertiesSet
     */
    public void afterPropertiesSet()  {
        this.onInit();
    }

    private static boolean aFlag = false;
    /**
     * Spring容器初始化、停止事件处理
     * @param evt ApplicationEvent
     */
    public void onApplicationEvent(ApplicationEvent evt) {
        if (evt instanceof ContextRefreshedEvent) {
            if(!aFlag) {
                aFlag = true;
                this.onStart();
            }
        } else if (evt instanceof CacheReloadEvent){

            this.onCacheReload((CacheReloadEvent)evt);
        } else if (evt instanceof ContextClosedEvent) {
            if(!aFlag) {
                aFlag = true;
                this.onStop();
            }
        }
    }

    /**
     * onCacheReload
     * @param evt CacheReloadEvent
     */
    protected void onCacheReload(CacheReloadEvent evt) {

    }
}

