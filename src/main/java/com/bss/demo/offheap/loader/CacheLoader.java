package com.bss.demo.offheap.loader;

import com.bss.demo.offheap.pojo.Order;
import com.bss.demo.offheap.pojo.Side;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CacheLoader {
    private static final int TOTAL_ORDERS = 10000;
    private static final String CACHE_NAME = "orderCache";
    private IgniteCache<String, Order> orderCache;
    private ThreadPoolExecutor dataLoaderExecutor;
    private ThreadPoolExecutor dataReaderExecutor;
    private Random random = new Random();
    
    private Timer loadTimer = new Timer();
    private Timer readTimer = new Timer();

    @EventListener(ApplicationReadyEvent.class)
    public void createCache() throws Exception {
        Ignite ignite = Ignition.ignite(System.getProperty("ignite.node.name"));
        this.orderCache = ignite.getOrCreateCache(CACHE_NAME);

        log.info("Cache {} created !", CACHE_NAME);

        dataLoaderExecutor = new ThreadPoolExecutor(4, 4, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        dataReaderExecutor = new ThreadPoolExecutor(4, 4, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        this.loadTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                loadTickData();

                //readTimer.cancel();
            }
        }, 5000);
        log.info("Scheduled timer task for loading elements !");

        readTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                while(true) {
                    dataReaderExecutor.execute(()->{
                        int num = getRandomNumber();
                        String key = "Order-XX-" + num;
                        Order order = orderCache.get(key);
                        if (order != null) {
                            order.setQuantity(order.getQuantity() + 1);
                            orderCache.put(order.getOrderId(), order);
                            try {
                                TimeUnit.MILLISECONDS.sleep(25);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }, 6000);

        
    }

    private int getRandomNumber() {
        return random.ints(0, TOTAL_ORDERS)
                .findFirst()
                .getAsInt();
    }

    //Order-XX-
    private void loadTickData() {
        for (int j=0; j<TOTAL_ORDERS; j++) {
            final int i = j;
            dataLoaderExecutor.execute(()->{
                String id = "-XX-" + i;
                Order order = Order.builder()
                        .orderId("Order" + id)
                        .ricCode("Ric" + id)
                        .side(Side.SELL)
                        .date(new Date())
                        .price(1323.3*i)
                        .micCode("XNSE")
                        .quantity(5000)
                        .build();

                this.orderCache.put(order.getOrderId(), order);
                try {
                    TimeUnit.MILLISECONDS.sleep(25);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        log.info("Order Cache is fully populated !");
    }


}