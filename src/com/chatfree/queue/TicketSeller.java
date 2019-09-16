package com.chatfree.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Author huijie.deng
 * @CreateDate: 2019/9/9
 */
public class TicketSeller {
//    static Vector<String> tickets = new Vector<>();
    static Queue<String> tickets = new ConcurrentLinkedDeque<>();
    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("票编号:"+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    String s = tickets.poll();
                    if(s == null){
                        break;
                    }else {
                        System.out.println("销售了--"+s);
                    }
                }
            }).start();
        }
    }
}
