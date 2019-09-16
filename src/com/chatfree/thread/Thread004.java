package com.chatfree.thread;

/**
 * @Author huijie.deng
 * @CreateDate: 2019/9/4
 * volatile 修饰的变量发送变更时会通知其他线程重新加载变量
 * 线程空下来的时候会刷新一下缓冲区的running
 * volatile 和 synchronized 区别是：
 * volatile 只保证可见性 效率比volatile
 * synchronized 既保证可见性又保证原子性，比较笨重
 */

public class Thread004 {

    boolean running = true;

    public  void m(){
        System.out.println("m start");
        while (running){

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("m end");
    }



    public static void main(String[] args) {
        Thread004 t = new Thread004();
        /**
         * java8写法
         */

        new Thread(t::m,"t1").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running=false;

    }
}
