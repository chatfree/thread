package com.chatfree.thread;

/**
 * @Author huijie.deng
 * @CreateDate: 2019/9/4
 *
 * 同步方法不影响其他线程非同步方法执行
 * synchronized 异常会释放锁，需要回滚
 */

public class Thread002{

    public  void m1(){
        synchronized (Thread002.class){


            System.out.println(Thread.currentThread().getName()+" m1 start...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" m1 end");
        }
//        while (true){
//            System.out.println(System.currentTimeMillis());
//            int a=1/0;//synchronized 异常会释放锁，如果不想释放就需要catch异常自己处理
//        }
    }
    public synchronized void m2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" m2");
    }


    public static void main(String[] args) {
        Thread002 t = new Thread002();
        /**
         * java8写法
         */
        new Thread(()->t.m1(),"t1").start();
//        new Thread(()->t.m1(),"t2").start();
        new Thread(t::m2,"t2").start();
        /**
         原始写法
         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                t.m2();
//            }
//        },"t2").start();
    }
}
