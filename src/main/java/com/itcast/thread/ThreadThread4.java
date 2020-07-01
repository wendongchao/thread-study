package com.itcast.thread;

/**
 * synchronized(非this对象)与同步synchronized方法是异步调用的
 * 同步代码块放在非同步synchronized方法中进行声明，并不能保证调用方法的线程的执行同步/顺序性，
 * 也就是线程调用方法的顺序是无序的，虽然在同步块中执行的顺序是同步的，但是这样容易出现“脏读”
 */
public class ThreadThread4 {
    public static void main(String[] args) {
        Service2 service = new Service2();
        //使用同一个对象监视器service
        ThreadA2 a = new ThreadA2(service);
        a.setName("A");
        a.start();
        ThreadB2 b = new ThreadB2(service);
        b.setName("B");
        b.start();
    }
}

class Service2{
    private String str = new String();
    public void a(){
        try {
            /**
             * 当str为同一个对象监视器时synchronized同步代码块为同步操作
             * 当str不为同一个对象监视器时synchronized同步代码块为异步操作
             */
            //String str = new String();
            synchronized (str){
                System.out.println("a begin");
                Thread.sleep(3000);
                System.out.println("a end");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    synchronized public void b(){
        System.out.println("b begin");
        System.out.println("b end");
    }
}
class ThreadA2 extends Thread{
    private Service2 service;
    public ThreadA2(Service2 service){
        super();
        this.service = service;
    }
    @Override
    public void run(){
        service.a();
    }
}
class ThreadB2 extends Thread{
    private Service2 service;
    public ThreadB2(Service2 service){
        super();
        this.service = service;
    }
    @Override
    public void run(){
        service.b();
    }
}