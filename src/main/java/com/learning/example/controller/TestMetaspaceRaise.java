package com.learning.example.controller;

import com.learning.example.demo.CglibProxy;
import com.learning.example.demo.SayHello;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Chen Lanqing 2018/8/23
 * @version 1.0.0
 */
@Controller
public class TestMetaspaceRaise {


    @ResponseBody
    @RequestMapping("/test")
    public void test(){
        CglibProxy proxy = new CglibProxy();
        for (int i = 0; i < 10000; i++) {
            //通过生成子类的方式创建代理类
            SayHello proxyTmp = (SayHello) proxy.getProxy(SayHello.class);
            proxyTmp.say();
        }
    }
}
