package com.chatfree.queue;

import java.util.concurrent.LinkedTransferQueue;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/16
 *
*/
public class TransferQueue1 {

    static LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * 只有被消费了才能执行后面的
         */
        queue.transfer("aaa");
//        new Thread(()->{
//            try {
//                System.out.println(queue.take());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }



}
