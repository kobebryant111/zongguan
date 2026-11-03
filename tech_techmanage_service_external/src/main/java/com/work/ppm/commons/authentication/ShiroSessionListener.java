package com.work.ppm.commons.authentication;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * shiro 监听器
 */
public class ShiroSessionListener implements SessionListener {
    /**
     * 变量
     */
    private final AtomicInteger sessionCount = new AtomicInteger(0);

    /**
     * 开始
     * @param session session
     */
    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }
    /**
     * 停止
     * @param session session
     */
    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }
    /**
     * onExpiration
     * @param session session
     */
    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
