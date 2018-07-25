package com.learning.example.thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolDemo implements Runnable {
    @Override
    public void run() {
        log.info(Thread.currentThread().getName());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1 / 0);
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(15, 15, 1000L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        int i = 0;
        while (i < 1000) {
            executorService.execute(new ThreadPoolDemo());
            i++;
        }
    }
}
