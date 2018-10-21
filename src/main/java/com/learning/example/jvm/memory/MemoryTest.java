package com.learning.example.jvm.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * 每个方法的参数m都是表示对应区间分配多少M内存<br/>
 * JVM参数：-verbose:gc -XX:+PrintGCDetails -Xmx2g -Xms2g -Xmn1g -XX:PretenureSizeThreshold=2M -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
 * -XX:CMSInitiatingOccupancyFraction=90 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxDirectMemorySize=512m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m <br/>
 * <p>
 * <a href='https://mp.weixin.qq.com/s/c7Jh44_LgQNgo1ijvey8rg'>JVM内存占用情况深入分析</a>
 *
 * @author bluefish 2018/10/21
 * @version 1.0.0
 */
public class MemoryTest {
    private static final int _1m = 1024 * 1024;

    private static final long THREAD_SLEEP_MS = 10 * 1000;

    public static void main(String[] args) throws Exception {
        youngAllocate(1000);
        oldAllocate(1000);
        metaspaceAllocate(200000);
        directMemoryAllocate(400);

        Thread.sleep(60000);
    }

    /**
     * 直接内存
     *
     * @param m
     */
    private static void directMemoryAllocate(int m) {
        System.out.println("direct memory : " + m + "m");
        for (int i = 0; i < m; i++) {
            ByteBuffer.allocateDirect(_1m);
        }
        System.out.println("direct memory end");
    }

    /**
     * 元空间
     *
     * @param count
     * @throws Exception
     */
    private static void metaspaceAllocate(int count) throws Exception {
        System.out.println("metaspace object count : " + count);
        Method declaredMethod = ClassLoader.class.getDeclaredMethod("defineClass", new Class[]{String.class, byte[].class, int.class, int.class});
        declaredMethod.setAccessible(true);

        File classFile = new File("MyCalc.class");
        byte[] bcs = new byte[(int) classFile.length()];
        try (InputStream is = new FileInputStream(classFile)) {
            while ((is.read(bcs)) != -1) {
            }
        }

        int outputCount = count / 10;
        for (int i = 1; i <= count; i++) {
            try {
                declaredMethod.invoke(MemoryTest.class.getClassLoader(), new Object[]{"MyCalc", bcs, 0, bcs.length});
            } catch (Throwable e) {
                System.err.println(e.getCause().getLocalizedMessage());
            }
            if (i >= outputCount && i % outputCount == 0) {
                System.out.println("i = " + i);
            }
        }

        System.out.println("metaspace end");

    }

    /**
     * 需要配置参数：-XX:PretenureSizeThreshold=2M，并且结合CMS
     *
     * @param m
     */
    private static void oldAllocate(int m) {
        System.out.println("old : " + m + "m");
        for (int i = 0; i < m / 5; i++) {
            byte[] test = new byte[_1m * 5];
        }
        System.out.println("old end");
    }

    private static void youngAllocate(int m) {
        System.out.println("young : " + m + "m");
        for (int i = 0; i < m; i++) {
            byte[] test = new byte[_1m];
        }
        System.out.println("young end");
    }

    private static void threadStackAllocate(int m) {
        int threadCount = m / 10;
        System.out.println("Thread stack count : " + threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(()->{
                System.out.println("thread name :" + Thread.currentThread().getName());
                try {
                    while (true) {
                        Thread.sleep(THREAD_SLEEP_MS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("Thread stack end : " + threadCount);
    }

}
