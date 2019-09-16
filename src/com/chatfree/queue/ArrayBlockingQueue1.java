package com.chatfree.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
* @Author: huijie.deng
* @CreateDate: 2019/9/16
*/
public class ArrayBlockingQueue1 {

    static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            queue.put("a"+i);

        }
        boolean flag = false;
        /**
         * 根据返回值可以判断是否添加成功
         */
//        flag = queue.offer("2");
//        flag = queue.offer("2",1, TimeUnit.SECONDS);
        /**
         * 底层也是调用offer,如果添加失败抛出异常(IllegalStateException: Queue full)
         */
//        flag = queue.add("1");
        System.out.println(flag);


        /**
         * 如果集合满了会一直等待
         */
//        queue.put("1");
        System.out.println(queue.poll());//等于list remove
//        System.out.println(queue.peek());// 等于list get
//        System.out.println(queue.size());
    }


    /**
    * @Author: huijie.deng
    * @CreateDate: 2019/9/16
    */
    public static class ConcurrentLinkedQueue1 {

        static Queue<String> queue = new ConcurrentLinkedQueue<>();
        public static void main(String[] args) {

            for (int i = 0; i < 10; i++) {
                queue.offer("a"+i);

            }

            System.out.println(queue.poll());//等于list remove
            System.out.println(queue.peek());// 等于list get
            System.out.println(queue.size());
        }


    }
}
