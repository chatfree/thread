package com.chatfree.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/6
 * ReentrantLock用于替换synchronized 比synchronized灵活
 * ReentrantLock是手工锁，必须手动释放
 * m1锁定this 只有m1执行完毕m2才能执行
 *
*/

public class ReentrantLock1 {
    Lock lock = new ReentrantLock();

    void m1(){
        try {
            lock.lock();//synchronized(this)
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }catch (Exception e){

        }finally {
            lock.unlock();
        }

    }

    /**
     *tryLock可以设置时间尝试锁定，根据返回值做处理
     */
    void m2(){
//        lock.lock();
        boolean locked = false;
        try {
            locked = lock.tryLock(2, TimeUnit.SECONDS);

//            lock.lockInterruptibly();
            System.out.println("m2 ..."+locked);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        if(locked){
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ReentrantLock1 r1 = new ReentrantLock1();
        Thread t1 = new Thread(r1::m1);
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(()->{
            r1.m2();
        });
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        t2.interrupt();
    }
}
