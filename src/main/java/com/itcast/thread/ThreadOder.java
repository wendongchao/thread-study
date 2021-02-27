package com.itcast.thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序打印ABC
 */
public class ThreadOder {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();//线程协作
    public static void main(String[] args) {
        ThreadOder oder = new ThreadOder();
        PrintThread a = oder.new PrintThread("A", true);
        PrintThread b = oder.new PrintThread("B", false);
        PrintThread c = oder.new PrintThread("C", false);

        a.setNext(b);
        b.setNext(c);
        c.setNext(a);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 6, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3)
        );
        for (int i = 0; i < 3; i++) {
            poolExecutor.execute(a);
            poolExecutor.execute(b);
            poolExecutor.execute(c);
        }
        poolExecutor.shutdown();
    }


    class PrintThread implements Runnable {
        private String name;
        private PrintThread next;
        private boolean execute;

        public PrintThread(String name, boolean execute) {
            name = name;
            execute = execute;
        }

        public PrintThread(String name, PrintThread next, boolean execute) {
            name = name;
            next = next;
            execute = execute;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                while (true) {
                    if (execute) {
                        //执行当前业务
                        print();
                        //当前业务执行完之后，把execute修改为false
                        execute = false;
                        //将下一个相邻的任务状态设置为true
                        next.setExecute(true);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //通知其他任务
                        condition.signalAll();
                        break;
                    } else {
                        try {
                            //若不是执行状态就等待
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }finally {
                lock.unlock();
            }
        }
        public void print(){
            System.out.println(name);
        }
        public void setExecute(boolean execute){
            this.execute = execute;
        }
        public void setNext(PrintThread next){
            this.next = next;
        }
    }
}