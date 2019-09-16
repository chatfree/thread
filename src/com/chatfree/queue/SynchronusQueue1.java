package com.chatfree.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/16
 *
*/
public class SynchronusQueue1 {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * 阻塞等着消费
         */
        queue.put("aaa");
//        queue.add("aaa");//报错
        System.out.println(queue.size());
    }



}
