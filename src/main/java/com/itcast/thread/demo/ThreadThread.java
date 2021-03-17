package com.itcast.thread.demo;

public class ThreadThread extends Thread {

    // 1. 新建一个类继承 Thread 类，并重写 Thread 类的 run() 方法。
    @Override
    public void run() {
        System.out.println("Hello Thread");
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        // 2. 创建 Thread 子类的实例。
        ThreadThread threadDemo = new ThreadThread();
        // 3. 调用该子类实例的 start() 方法启动该线程。
        threadDemo.start();
    }
}
