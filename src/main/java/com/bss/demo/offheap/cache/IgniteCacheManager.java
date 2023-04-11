package com.bss.demo.offheap.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@EnableCaching
public class IgniteCacheManager {
    private boolean cacheInitialized;

    @Bean
    public SpringCacheManager cacheManager() {
        SpringCacheManager mgr = new SpringCacheManager();
        mgr.setConfigurationPath(System.getProperty("ConfigDir") + "/ignite-cache.xml");

        cacheInitialized = true;

        return mgr;
    }

    public boolean isCacheInitialized() {
        return cacheInitialized;
    }
}


