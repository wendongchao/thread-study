package com.itcast.thread;

/**
 * synchronized(非this对象x)同步代码块：
 * 1）、在多个线程持有“对象监视器”为同一个对象的前提下，
 *      同一时间只有一个线程可以执行synchronized(非this对象x)同步代码块中的代码。
 * 2）、在多个线程持有“对象监视器”不为同一个对象的前提下，
 *      同一时间可以有多个个线程可以执行synchronized(非this对象x)同步代码块中的代码。（异步执行）
 */
public class ThreadThread3 {
    public static void main(String[] args) {
        Service service = new Service();
        //使用同一个对象监视器service
        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();
    }
}
class Service{
    private String username;
    private String password;
    //	private String str = new String();
    public void setUsernamePassword(String username,String password){
        try {
            /**
             * 当str为同一个对象监视器时synchronized同步代码块为同步操作
             * 当str不为同一个对象监视器时synchronized同步代码块为异步操作
             */
            String str = new String();
            synchronized (str){
                System.out.println("线程名称为："+Thread.currentThread().getName()
                        +"在"+System.currentTimeMillis()+"进入同步块");
                username = username;
                Thread.sleep(3000);
                password = password;
                System.out.println("线程名称为："+Thread.currentThread().getName()
                        +"在"+System.currentTimeMillis()+"离开同步块");
                System.out.println("username:"+username);
                System.out.println("password:"+password);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
class ThreadA extends Thread{
    private Service service;
    public ThreadA(Service service){
        super();
        this.service = service;
    }
    @Override
    public void run(){
        service.setUsernamePassword("a","aa");
    }
}
class ThreadB extends Thread{
    private Service service;
    public ThreadB(Service service){
        super();
        this.service = service;
    }
    @Override
    public void run(){
        service.setUsernamePassword("b","bb");
    }
}