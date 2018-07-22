package com.learning.example.jvm.oom;

import com.learning.example.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 参数：-Xms8M -Xmx8M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./
 */
public class HeapOOM {

    private static List<UserInfo> list = new ArrayList<>();

    public static void main(String[] args) {
        int i = 0;
        while (true) {
            list.add(new UserInfo(i++, UUID.randomUUID().toString()));
        }
    }
}
