package com.work.ppm.commons.socket.system;

import org.springframework.context.ApplicationEvent;

/**
 * CacheReloadEvent
 *
 */
public class CacheReloadEvent extends ApplicationEvent{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * cacheName
     */
    private String cacheName;

    /**
     * CacheReloadEvent
     * @param source Object
     * @param cacheName cacheName
     */
    public CacheReloadEvent(Object source, String cacheName) {
        super(source);
        this.cacheName=cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

}

