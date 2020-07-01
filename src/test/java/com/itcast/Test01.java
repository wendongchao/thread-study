package com.itcast;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test01 {

    @Test
    public void test01(){
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(200);
        for(int i=0;i<200;i++){
            final int j = i;
            Runnable run = new Runnable() {
                public void run() {
                    System.out.println(j);
                }
            };
        }
    }
}
