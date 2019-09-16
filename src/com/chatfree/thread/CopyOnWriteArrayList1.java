package com.chatfree.thread;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/12
*/
public class CopyOnWriteArrayList1 {

    public static void main(String[] args) {
        List<String> list =
//                new ArrayList<>();
                new Vector<>();
//        new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];
        for (int i = 0; i < ths.length; i++) {
           Runnable task = new Runnable() {
               @Override
               public void run() {
                   for (int j = 0; j < 1000; j++) {
                       list.add("a"+r.nextInt(10000));
                   }
               }
           };
           ths[i]=new Thread(task);
        }
        runAndCOmputeTime(ths);
        System.out.println(list.size());
    }

    private static void runAndCOmputeTime(Thread[] ths) {
        long start = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t->t.start());
        Arrays.asList(ths).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(System.currentTimeMillis()-start);
    }
}
