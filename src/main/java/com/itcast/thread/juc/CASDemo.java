package com.itcast.thread.juc;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    // CAS compareAndSet : 比较与交换
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2020);

        // 期望，更新
        // public final boolean compareAndSet(int expect,int update)
        // 如果我期望的值达到了，那么就更新，否则，就不更新，CAS 是CPU的并发原语!
        System.out.println(atomicInteger.compareAndSet(2020,2021));
        System.out.println(atomicInteger.get());
        // 以原子方式将当前值增加一
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.compareAndSet(2020,2021));
        System.out.println(atomicInteger.get());
    }
}
