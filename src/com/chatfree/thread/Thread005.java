package com.chatfree.thread;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author huijie.deng
 * @CreateDate: 2019/9/4
 * volatile 修饰的变量发送变更时会通知其他线程重新加载变量
 * 线程空下来的时候会刷新一下缓冲区的running
 * volatile 和 synchronized 区别是：
 * volatile 只保证可见性 效率比volatile
 * synchronized 既保证可见性又保证原子性，比较笨重
 * AtomicXXX都是原子类效率比synchronized高很多
 * jion方法很神奇
 * synchronized语句代码越少效率越高
 * 锁定的信息记录在堆内存的，对象变成了新的对象锁就会释放
 */

public class Thread005 {

    int count = 0;

    AtomicInteger count1 = new AtomicInteger(0);

    public synchronized void m(){

        for (int i = 0; i < 10000; i++) {
//            System.out.print(Thread.currentThread().getName()+":"+i);
            count++;
        }
    }
    public  void m1(){
        for (int i = 0; i < 10000; i++) {
            /**
             * count1.get()具有原子性
             * count1.incrementAndGet();也具有原子性
             * 二者组合就不保证原子性
             */
//            if(count1.get()<1000){
//                count1.incrementAndGet();
//            }
//
            count1.incrementAndGet();
        }
    }
    public static void call(String name){
        Thread005 t = new Thread005();
        /**
         * java8写法
         */
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if("m".equals(name)){
                threads.add(new Thread(t::m,"t"+i));
            }else {
                threads.add(new Thread(t::m1,"t"+i));
            }

        }
        threads.forEach((thread)->thread.start());
        threads.forEach((thread)-> {
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("m".equals(name)?t.count:t.count1);
    }


    public static void main(String[] args) {

//        call("m");
        call("m1");


    }
}
