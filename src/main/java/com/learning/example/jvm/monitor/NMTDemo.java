package com.learning.example.jvm.monitor;

/**
 * 开启NMT：-XX:NativeMemoryTracking=summary<br/>
 * 为了方便获取和对比NMT的输出，选择在应用退出时打印NMT统计信息：<br/>
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics
 * <br/>
 *  -XX:NativeMemoryTracking=summary -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics
 *  -XX:-TieredCompilation -XX:+UseParallelGC
 *
 * @author bluefish 2018/8/13
 * @version 1.0.0
 */
public class NMTDemo {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
