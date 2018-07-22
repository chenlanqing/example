package com.learning.example.thread.producer.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static Integer count = 0;
    final Semaphore notFull = new Semaphore(10);
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        SemaphoreExample w = new SemaphoreExample();
        new Thread(w.new Producer()).start();
        new Thread(w.new Producer()).start();
        new Thread(w.new Producer()).start();
        new Thread(w.new Producer()).start();
        new Thread(w.new Producer()).start();
        new Thread(w.new Consumer()).start();
        new Thread(w.new Consumer()).start();
        new Thread(w.new Consumer()).start();
        new Thread(w.new Consumer()).start();
        new Thread(w.new Consumer()).start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    notFull.acquire();
                    mutex.acquire();
                    count++;
                    System.out.println(Thread.currentThread().getName() + " ~~~~~生产者生产， 目前总共有:" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notEmpty.release();
                }
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName() + " ^^^^^^消费者消费， 目前总共有:" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
}