package com.chatfree.thread;

/**
 * @Author huijie.deng
 * @CreateDate: 2019/9/4
 *
 * synchronized支持重入的
 * 同步方法可以调用同步方法
 * 子类synchronized可以调用父类synchronized方法不会造成死锁
 */

public class Thread003 {

    String name;
    double balance;

    public synchronized void set(String name,double balance){
        this.name=name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance=balance;
    }

    /**
     * synchronized
     * 读数据的时候也需要加锁，不然会出现脏读
     * @param name
     * @return
     */
    public synchronized double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Thread003 t = new Thread003();
        /**
         * java8写法
         */

        new Thread(()->t.set("张三",100.0) ,"t2").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("a"+t.getBalance("张三"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.getBalance("张三"));

    }
}
