package com.chatfree.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/6
 * 精准通知生产者和消费者提高效率
*/

public class MyContainer2<T>{
    private final LinkedList<T> list = new LinkedList<T>();
    final private int MAX = 10;
    public int count = 0;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();
    public void put(T t){
        /**
         * 为什么用while而不是if？
         * 当线程被唤醒的时候还需要校验一下数组的长度
         */
        try {
        lock.lock();
            while (list.size() == MAX){
                producer.await();
            }
            list.add(t);
            ++count;
            /**
             * 通知消费者线程赶快消费
             * 不能是notify，因为有可能唤醒的也是生产者，然后死掉了
             */
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public T get(){
        T t = null;
        try {
            lock.lock();
            while (list.size() == 0){
                consumer.await();
            }
            t = list.removeFirst();
            count--;
            /**
             * 通知生产者线程生产
             */
            producer.signalAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        return t;
    }




    public static void main(String[] args) {

        MyContainer2<String> c = new MyContainer2();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName()+":"+c.get());
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
