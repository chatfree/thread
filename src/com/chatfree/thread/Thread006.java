package com.chatfree.thread;


import java.util.concurrent.TimeUnit;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/5
 *
 * 不要以字符串常量作为锁的对象
 * m1 m2锁的是同一个对象，有可能会发生死锁情况
*/

public class Thread006 {

    String a = "Hello";
    String b = "Hello";
    volatile static Object object = new Object();
    public  void m1(){
       synchronized (object){
            while (true){

            }
       }
    }
    public  void m2(){
        synchronized (object){
            System.out.println("m2");
        }
    }


    public static void main(String[] args) {
        Thread006 t = new Thread006();
        new Thread(t::m1).start();
        new Thread(t::m2).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        object = new Object();

    }
}
