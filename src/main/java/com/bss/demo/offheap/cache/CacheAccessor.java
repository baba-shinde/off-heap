package com.bss.demo.offheap.cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/*@Service
public class CacheAccessor {
    @Autowired
    private IgniteCacheManager igniteCacheManager;

    @EventListener(ApplicationReadyEvent.class)
    public void createCache() throws Exception {
        while (!igniteCacheManager.isCacheInitialized()) {
            Thread.sleep(1000);
        }

        Ignite ignite = Ignition.ignite(System.getProperty("ignite.node.name"));
        IgniteConfiguration configuration = ignite.configuration();

        System.out.println("Cache Present !");

    }

}*/
