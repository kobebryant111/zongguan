package com.work.ppm.commons.authentication;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 描述：缓存管理工具类
 * @date 2019-12-10
 */
@Component
@Slf4j
public class EhcacheConfig {
    /**
     * 变量
     */
    public static final String INITLOADOBJECT = "initLoadObject";

    /**
     * 获取缓存
     * @return CacheManager
     */
    @Bean
    public CacheManager getCacheManager(){
        CacheConfiguration config = new CacheConfiguration();
        config.setName(INITLOADOBJECT);
        //是否永不过期，为false则过期需要通过timeToIdleSeconds,
        config.setEternal(false);
        config.setMemoryStoreEvictionPolicy("LFU");
        config.setMaxEntriesLocalHeap(100000L);
        config.setMaxEntriesLocalDisk(20000L);
        config.setTimeToIdleSeconds(86500L);
        config.setOverflowToDisk(false);
        config.setDiskPersistent(false);

        //设置ehcache配置文件，获取CacheManager
        Configuration configuration = new Configuration();
        configuration.addCache(config);
        CacheManager cacheManager = CacheManager.newInstance(configuration);
        return  cacheManager;
    }
}
