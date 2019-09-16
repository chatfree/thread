package com.chatfree.thread;

import java.util.LinkedList;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/6
 * 生产者和消费者模式
*/

public class MyContainer1<T>{
    private final LinkedList<T> list = new LinkedList<T>();
    final private int MAX = 10;
    public int count = 0;


    public synchronized void put(T t){
        /**
         * 为什么用while而不是if？
         * 当线程被唤醒的时候还需要校验一下数组的长度
         */
        while (list.size() == MAX){

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        /**
         * 通知消费者线程赶快消费
         * 不能是notify，因为有可能唤醒的也是生产者，然后死掉了
         */
        this.notifyAll();
    }
    public synchronized T get(){
        T t = null;
        while (list.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.removeFirst();
        count--;
        /**
         * 通知生产者线程生产
         */
        this.notifyAll();
        return t;
    }




    public static void main(String[] args) {

        MyContainer1<String> c = new MyContainer1();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            },"c"+i).start();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName()+""+j);
                }
            },"p"+i).start();
        }
    }
}
