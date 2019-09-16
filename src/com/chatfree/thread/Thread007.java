package com.chatfree.thread;


import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/5
 *
 * wait会释放锁，notify不会释放锁(wait和notify必须写在synchronized代码块里)
 * t2先执行size不等于5就先wait
 * t1再执行当size等于5时要notify t2，但是notify不释放锁，只能让t1 wait t2才能正确执行
 * t2执行完以后要notify t1整个程序才能执行完成
 * CountDownLatch/CyclicBarrier/Semaphore(门栓) 不涉及锁定
 * 当latch 为零时await的线程继续执行
 *
*/

public class Thread007 {

    volatile List list = new ArrayList();

    public  void add(Object o){
       list.add(o);
    }
    public int size(){
        return list.size();
    }

    /**
     * t2和t1锁定lock对象
     * t2 判断size不等于5进入wait
     * t1 size等于5时notify(不释放锁),并且wait
     * t2得到锁，继续执行,t2执行结束后必须notify
     */
    public static void waitAndNotify(){

        Thread007 t = new Thread007();
        final Object lock = new Object();

        new Thread(()->{
            synchronized (lock){
                if(t.size() != 5){
                    try {
                        System.out.println("1");
                        lock.wait();
                        System.out.println("2");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                lock.notify();
            }
        },"t2").start();
        new Thread(()->{
            synchronized (lock){
                for (int i = 0; i < 10; i++) {
                    t.add(new Object());
                    System.out.println("add"+i);
                    if(t.size() == 5){
                        try {
                            lock.notify();
                            lock.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                    latch.countDown();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();


    }

    /**
     * 1个门栓
     * t2先等待
     * 当t1线程size等于5时t1打开门栓
     * t2继续执行
     */
    public static void latch(){
        CountDownLatch latch = new CountDownLatch(1);

        Thread007 t = new Thread007();
        new Thread(()->{
            if(t.size() != 5){
                try {
                    latch.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("t2 结束");
        },"t2").start();

        new Thread(()->{

            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add"+i);
                if(t.size() == 5){
                    try {
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    latch.countDown();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

    }

    /**
     * 申请2个屏障
     * t2先冲破一个屏障，等t1再冲破屏障时t2打印结束
     */
    public static void barrier(){

        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread007 t = new Thread007();
        new Thread(()->{
            if(t.size() != 5){
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("t2 结束");
        },"t2").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add"+i);
                if(t.size() == 5){
                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }

    /**
     * t1先获取凭证，t1先执行当size等于5时释放
     * t2获取到凭证,t2结束后t1再执行
     */
    public static void semaphore(){
        Semaphore semaphore = new Semaphore(1);

        Thread007 t = new Thread007();

        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add"+i);
                if(t.size() == 5){
                    try {
                        semaphore.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            if(t.size() != 5){
                try {
                    System.out.println("1");
                    semaphore.acquire();
                    System.out.println("2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("t2 结束");
        },"t2").start();
    }

    public static void main(String[] args) {
//        waitAndNotify();
//        latch();
        barrier();
        semaphore();
    }
}
