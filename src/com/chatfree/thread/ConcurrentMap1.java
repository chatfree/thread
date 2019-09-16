package com.chatfree.thread;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/12
*/
public class ConcurrentMap1 {

    public static void main(String[] args) {
//        Map<String,String> map = new ConcurrentHashMap<>();
        /**
         * 高并发并且排序
         */
//        Map<String,String> map = new ConcurrentSkipListMap<>();
//        Map<String,String> map = new Hashtable<>();
        Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
//        Map<String,String> map = new TreeMap<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    map.put("a"+r.nextInt(100000),"a"+r.nextInt(100000));
                }
                latch.countDown();
            });
        }
        Arrays.asList(ths).forEach(t->t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
