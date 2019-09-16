package com.chatfree.thread;

/**
 * @Author huijie.deng
 * @CreateDate: 2019/9/4
 *
 * static方法中不能用synchronized (this)
 */

public class Thread001 implements Runnable{

    private  int count = 10;

    /**
     * 不使用synchronized会出现打印相同数
     * 因为count-- 后，print 前会有其他线程进来
     */
    @Override
    public synchronized  void run(){
        count--;
        System.out.println(Thread.currentThread().getName()+":count="+count);
    }

    public static void main(String[] args) {
        Thread001 t = new Thread001();
        for (int i = 0; i < 10; i++) {
            new Thread(t,"thread"+i).start();;
        }
    }
}
