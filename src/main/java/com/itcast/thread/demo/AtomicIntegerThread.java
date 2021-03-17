package com.itcast.thread.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程顺序打印ABC
 */
public class AtomicIntegerThread implements Runnable {
    private AtomicInteger currentCount = new AtomicInteger(0);

    private static final Integer MAX_COUNT = 30;

    private static String[] chars = {"a", "b", "c"};

    private String name;

    public AtomicIntegerThread(String name,AtomicInteger currentCount) {
        this.name = name;
        this.currentCount = currentCount;
    }

    @Override
    public void run() {
        while (currentCount.get() < MAX_COUNT) {
            if (this.name.equals(chars[currentCount.get() % 3])) {
                printAndPlusOne(this.name);
            }
        }
    }

    public void printAndPlusOne(String content) {
        System.out.print(content);
        currentCount.getAndIncrement();
    }

    public static void main(String[] args) {

        AtomicInteger currentCount = new AtomicInteger(0);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 20, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(3));
        threadPoolExecutor.execute(new AtomicIntegerThread("a",currentCount));
        threadPoolExecutor.execute(new AtomicIntegerThread("b",currentCount));
        threadPoolExecutor.execute(new AtomicIntegerThread("c",currentCount));
        threadPoolExecutor.shutdown();

    }
}
