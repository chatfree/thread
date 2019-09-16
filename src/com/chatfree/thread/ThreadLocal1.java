package com.chatfree.thread;


/**
* @Author: huijie.deng
* @CreateDate: 2019/9/6
 * ThreadLocal用空间换时间
*/

public class ThreadLocal1<T>{

    static ThreadLocal<Person> tl = new ThreadLocal<>();
    volatile static  Person p = new Person();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
    protected static class Person{
        String name = "张三";
    }
}
