package com.itcast.thread;

/**
 * 三个售票窗口同时出售20张票
 * 程序分析：
 *     (1)票数要使用同一个静态值
 *     (2)为保证不会出现卖出同一个票数，要java多线程同步锁。
 * 设计思路：
 *     (1)创建一个站台类Station，继承Thread，重写run方法，在run方法里面执行售票操作！售票要使用同步锁：即有一个站台卖这张票时，其他站台要等这张票卖完！
 *     (2)创建主方法调用类
 */
public class ThreadStation extends Thread {
    //通过构造方法给线程名字赋值
    public ThreadStation(String name){
        super(name);
    }
    //为了保持票数的一致，票数要静态
    static int stock=20;
    //创建一个静态锁
    static Object obj = "aa";
    //重写run方法，实现买票操作
    @Override
    public void run(){
        while (stock>0){
            synchronized (obj) {
                if (stock > 0) {
                    System.out.println(this.getName() + "卖出了第" + stock + "张票");
                    stock--;
                } else {
                    System.out.println("票卖完了");
                }
            }
            try {//模拟中途请求时间
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Test{
    public static void main(String[] args) {
        ThreadStation ThreadStation1 = new ThreadStation("窗口1");
        ThreadStation ThreadStation2 = new ThreadStation("窗口2");
        ThreadStation ThreadStation3 = new ThreadStation("窗口3");
        ThreadStation1.start();
        ThreadStation2.start();
        ThreadStation3.start();
    }
}
