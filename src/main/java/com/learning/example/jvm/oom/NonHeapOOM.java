package com.learning.example.jvm.oom;

import com.learning.example.utils.AsmClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:MetaspaceSize=32M -XX:MaxMetaspaceSize=32M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./<br>
 * <p>
 * 这里非堆溢出针对的是JDK8中的元空间
 *
 * @author bluefish 2018/7/21
 * @version 1.0.0
 */
public class NonHeapOOM {

    private static List<Class<?>> classList = new ArrayList<Class<?>>();

    public static void main(String[] args) {
        while (true) {
            classList.addAll(AsmClassUtils.createClasses());
        }
    }
}
