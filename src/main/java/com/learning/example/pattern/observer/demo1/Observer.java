package com.learning.example.pattern.observer.demo1;

public interface Observer {
    /**
     * 更新
     * @param subject
     */
    void update(Subject subject);
}
