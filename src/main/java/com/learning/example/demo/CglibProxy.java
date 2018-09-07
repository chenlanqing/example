package com.learning.example.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Chen Lanqing 2018/8/23
 * @version 1.0.0
 */
public class CglibProxy implements MethodInterceptor{

    public Object getProxy(Class clazz){
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        enhancer.setUseCache(false);

        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("前置代理");
        // 通过代理类调用父类中的方法
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("后置代理");
        return result;
    }
}
