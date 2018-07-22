package com.learning.example.jvm.cpu;

/**
 * @author bluefish 2018/7/21
 * @version 1.0.0
 */
public class DeadLock {
    public static void main(String[] args) {
        new DeadLock().deadLock();
    }

    private final Object o1 = new Object();
    private final Object o2 = new Object();

    public void deadLock(){
        new Thread(() ->{
            synchronized (o1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (o2){
                    System.out.println("Thread-1 over");
                }
            }
        }).start();

        new Thread(() ->{
            synchronized (o2){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (o1){
                    System.out.println("Thread-1 over");
                }
            }
        }).start();
    }
}
