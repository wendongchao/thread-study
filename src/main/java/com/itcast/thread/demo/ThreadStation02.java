package com.itcast.thread.demo;

/**
 * 多线程售票：实现Runnable,并使用synchronized锁定检票操作
 *
 */
public class ThreadStation02 {
    public static void main(String[] args) {
            Station02 station02 = new Station02();
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(station02, "窗口" + i);
            thread.start();
        }
    }
}
class Station02 implements Runnable{

    private static int num = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (num > 0) {
                    System.out.println(Thread.currentThread().getName() + "售票");
                    num--;
                }else{
                    System.out.println(Thread.currentThread().getName() + "售票完毕");
                    break;
                }
            }
        }
    }
}
