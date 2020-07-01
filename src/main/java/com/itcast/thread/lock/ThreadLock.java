package com.itcast.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLock {
    public static void main(String[] args) {
        MyService myService = new MyService();
        MyThread a1 = new MyThread(myService);
        MyThread a2 = new MyThread(myService);
        MyThread a3 = new MyThread(myService);
        MyThread a4 = new MyThread(myService);
        MyThread a5 = new MyThread(myService);
        a1.run();
        a2.run();
        a3.run();
        a4.run();
        a5.run();
    }
}
class MyThread extends Thread{
    private MyService myService;
    public MyThread(MyService myService){
        super();
        this.myService=myService;
    }
    @Override
    public void run(){
        myService.testMethod();
    }
}

/**
 * lock.lock():调用该方法的线程持有“对象监听器”，其他线程只有等待锁被释放再次争抢
 * lock.unlock();//释放锁
 */
class MyService {
    private Lock lock = new ReentrantLock();
    public void testMethod(){
        //lock()必须紧跟try代码块，unlock()必须在finally的第一行
        lock.lock();//获取锁
        try {
            for (int i = 1; i < 5; i++) {
                System.out.println("threadName:" + Thread.currentThread().getName() + " " + (i + 1));
            }
        }finally {
            lock.unlock();//释放锁
        }
    }
}