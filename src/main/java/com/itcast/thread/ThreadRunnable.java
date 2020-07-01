package com.itcast.thread;

public class ThreadRunnable {
    public static void main(String[] args) {
        DieLock dieLock1 = new DieLock(true);
        DieLock dieLock2 = new DieLock(false);
        Thread t1 = new Thread(dieLock1);
        Thread t2 = new Thread(dieLock2);
        t1.start();
        t2.start();
    }
}
class MyLock{
    public static Object obj1 = new Object();
    public static Object obj2 = new Object();
}
//实现Runnable接口，Runnable需要先重写run方法，之后再调用Thread的start方法
class DieLock implements Runnable{
    private boolean flag;
    DieLock(boolean flag){
        this.flag = flag;
    }
    //当一个持有锁的A，调用另一个持有锁的B时，因为B对象被锁，所以A对象会从running-->runnable，而等待B对象释放锁
    //那么当这种情况是两组的情况
    //当一个持有锁的B，调用另一个持有锁的A时，因为A对象被锁，所以B对象会从running-->runnable，而等待A对象释放锁
    //所以这种情况下，因为各自的线程相互获取不到对象，而处于死锁状态
    public void run(){
        if(flag){
            while (true){
                synchronized (MyLock.obj1){
                    System.out.println(Thread.currentThread().getName()+"....if...obj1...");
                    synchronized (MyLock.obj2){
                        System.out.println(Thread.currentThread().getName()+"....if...obj2...");
                    }
                }
            }
        }else{
            while (true){
                synchronized (MyLock.obj2){
                    System.out.println(Thread.currentThread().getName()+"....else...obj2...");
                    synchronized (MyLock.obj1){
                        System.out.println(Thread.currentThread().getName()+"....else...obj1...");
                    }
                }
            }
        }
    }
}
