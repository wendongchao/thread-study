package com.itcast.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadConditionLock2 {
    public static void main(String[] args) throws Exception {
        MyServiceConditionLock2 MyServiceConditionLock2 = new MyServiceConditionLock2();
        ThreadA2 a = new ThreadA2(MyServiceConditionLock2);
        a.setName("A");
        a.start();
        ThreadB2 b = new ThreadB2(MyServiceConditionLock2);
        b.setName("B");
        b.start();
        Thread.sleep(3000);
        MyServiceConditionLock2.signalAll_A();
    }
}
class ThreadA2 extends Thread {
    private MyServiceConditionLock2 MyServiceConditionLock2;
    public ThreadA2(MyServiceConditionLock2 MyServiceConditionLock2){
        super();
        this.MyServiceConditionLock2=MyServiceConditionLock2;
    }

    //重写run方法
    @Override
    public void run() {
        MyServiceConditionLock2.awaitA();
    }
}
class ThreadB2 extends Thread{
    private MyServiceConditionLock2 MyServiceConditionLock2;
    public ThreadB2(MyServiceConditionLock2 MyServiceConditionLock2){
        super();
        this.MyServiceConditionLock2=MyServiceConditionLock2;
    }

    @Override
    public void run() {
        MyServiceConditionLock2.awaitB();

    }
}
class MyServiceConditionLock2 {
    private Lock lock = new ReentrantLock();
    public Condition conditionA = lock.newCondition();
    public Condition conditionB = lock.newCondition();
    public void awaitA(){
            lock.lock();
        try {
            System.out.println("begin awaitA时间为："+System.currentTimeMillis()+
                    " ThreadName= "+Thread.currentThread().getName());
            conditionA.await();
            System.out.println("end awaitA时间为："+System.currentTimeMillis()+
                    " ThreadName= "+Thread.currentThread().getName());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();

        }
    }
    public void awaitB(){
            lock.lock();
        try {
            System.out.println("begin awaitB时间为："+System.currentTimeMillis()+
                    " ThreadName= "+Thread.currentThread().getName());
            conditionB.await();
            System.out.println("end awaitB时间为："+System.currentTimeMillis()+
                    " ThreadName= "+Thread.currentThread().getName());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();

        }
    }
    public void signalAll_A(){
            lock.lock();
        try{
            System.out.println("begin signalAll_A时间为："+System.currentTimeMillis()+
                    " ThreadName= "+Thread.currentThread().getName());
            conditionA.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void signalAll_B(){
            lock.lock();
        try{
            System.out.println("begin signalAll_B时间为："+System.currentTimeMillis()+
                    " ThreadName= "+Thread.currentThread().getName());
            conditionB.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
