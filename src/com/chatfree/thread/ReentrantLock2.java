package com.chatfree.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/6
 * ReentrantLock指定为公平锁
 * 谁等的时间长谁先得到锁
*/

public class ReentrantLock2 extends Thread{
   static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally {
                lock.unlock();
            }
        }
    }




    public static void main(String[] args) {

        ReentrantLock2 r1 = new ReentrantLock2();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        t1.start();
        t2.start();
    }
}
