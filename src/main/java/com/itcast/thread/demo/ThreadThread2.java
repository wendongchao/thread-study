package com.itcast.thread.demo;

public class ThreadThread2 {
    public static void main(String[] args) {
        /*
        Thread.currentThread()：返回对当前正在执行的线程对象的引用
         */
        MyThread myThread = new MyThread();
        myThread.setName("MyThread");
        myThread.start();
        try {
            for (int i = 0; i < 5; i++) {
                int time = (int) (Math.random() * 1000);
                Thread.sleep(time);
                System.out.println("run:" + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
//多线程代码的运行结果和运行顺序没有关系。
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int time = (int) (Math.random() * 100);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("run:" + Thread.currentThread().getName());
        }
    }
}