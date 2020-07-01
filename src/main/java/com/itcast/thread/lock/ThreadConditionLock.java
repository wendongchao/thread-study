package com.itcast.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock类
 * 可以实现锁的功能，比synchronized更加的强大
 * ReentrantLock使用lock()和unlock()方法实现对代码块或者代码的加锁，
 * 当对象监听器只有一个时，lock()后代码块实现阻塞策略(顺序执行)
 * 当对象监听器为多个时，lock()后代码块在当前对象监听器中实现阻塞策略(顺序执行)，与其他对象监听器对比为非阻塞策略(异步执行)
 * ReentrantLock比synchronized更加的强大和灵活的原因：
 *   ReentrantLock中有一个Condition对象，Condition对象也是一个对象监听器，它可以在Lock()对象中创建多个Condition(对象监听器)实例
 *   Thread线程对象可以注册在指定的Condition中，从而可以有选择地进行线程通知，实现对每个线程都精细化的加锁，这样可以调度多线程，
 *   Condition使用await()/signal()、signalAll()，对绑定在Condition(对象监听器)上的Thread线程对象实现等待/通知
 *
 * Object类中的wait()方法相当于Condition类中的await()方法
 * Object类中的wait(long timeout)方法相当于Condition类中的await(long time,timeUnit unit)方法
 * Object类中的notify()方法相当于Condition类中的signal()方法
 * Object类中的notifyAll()方法相当于Condition类中的signalAll()方法
 */
/**
 * 使用ReentrantLock中Condition对象的await()/signal()、signalAll()方法实现线程的
 * 一对一交替，多对多交替  打印输出
 * 使用Condition对象的前提：必须是ReentrantLock上锁，
 * ReentrantLock操作Condition是一个封闭的状态，
 * ReentrantLock相当于一个盒子，Condition相当于盒子中用于切断线路的控制器，
 * 每一个线路都有一个控制器，线路相当于线程Thread,ReentrantLock顶层这个盒子操控控制器对线路的闭合开启
 * 所以ReentrantLock必须在Condition的方法操作之前，拿到这个盒子的控制权，也就是上锁lock();
 *
 * Condition对象监听器中有一个或多个Thread,
 * 当Condition对象监听其中有多个线程时就会出现假死
 * 假死原因：一个对象监听器中有多个Thread，在使用等待、通知这种场景时会造成线程假死，
 * 原因是唤醒的线程有可能是同类，
 * 假死可以看到运行的按钮保持红色
 */
public class ThreadConditionLock {
    public static void main(String[] args) {
        MyServiceConditionLock MyServiceConditionLock = new MyServiceConditionLock();
        //一对一交替
//        ThreadA threadA  = new ThreadA(MyServiceConditionLock);
//        threadA.start();
//        ThreadB threadB  = new ThreadB(MyServiceConditionLock);
//        threadB.start();
        //多对多交替
        ThreadA[] threadAS = new ThreadA[10];
        ThreadB[] threadBS = new ThreadB[10];
        for(int i=0;i<10;i++){
            threadAS[i] = new ThreadA(MyServiceConditionLock);
            threadBS[i] = new ThreadB(MyServiceConditionLock);
            threadAS[i].start();
            threadBS[i].start();
        }
    }
}
class MyServiceConditionLock {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    private boolean hasValue=false;
    public void set(){
            reentrantLock.lock();//加锁
            try {
            while (hasValue==true){//根据boolean的值使线程处于等待/启动
//                System.out.println("有可能AA连续");
//                System.out.println("当前线程："+Thread.currentThread().getName());
                condition.await();//线程等待，可以看到当前线程在等待的状态中，WATTING
            }
            System.out.println("打印A");
            hasValue=true;
            //     System.out.println("当前线程："+Thread.currentThread().getName());
            condition.signal();//线程通知，通知等待的线程启动，具体通知哪个线程待定？？？？
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();//释放锁，抛出异常也释放锁
        }
    }
    public void get(){
            reentrantLock.lock();
        try {
            while (hasValue==false){
                //System.out.println("有可能BB连续");
                condition.await();
            }
            System.out.println("打印B");
            hasValue=false;
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reentrantLock.unlock();
        }
    }
}
class ThreadA extends Thread {
    private MyServiceConditionLock MyServiceConditionLock;
    public ThreadA(MyServiceConditionLock MyServiceConditionLock){
        super();
        this.MyServiceConditionLock=MyServiceConditionLock;
    }

    //重写run方法
    @Override
    public void run() {
        for (int i=0;i<50;i++){
            MyServiceConditionLock.set();
        }

    }
}
class ThreadB extends Thread{
    private MyServiceConditionLock MyServiceConditionLock;
    public ThreadB(MyServiceConditionLock MyServiceConditionLock){
        super();
        this.MyServiceConditionLock=MyServiceConditionLock;
    }

    @Override
    public void run() {
        for (int i=0;i<50;i++){
            MyServiceConditionLock.get();
        }
    }
}