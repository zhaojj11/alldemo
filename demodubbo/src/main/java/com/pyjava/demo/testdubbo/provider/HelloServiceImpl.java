package com.pyjava.demo.testdubbo.provider;

import com.pyjava.demo.testdubbo.inter.HelloService;

/**
 * <p>描述: [功能描述] </p>
 *
 * @author zhaojj11
 * @since 1.0
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;
    //当有消费方调用该方法时， 就返回一个结果
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        System.out.println();
        //根据mes 返回不同的结果
        if(mes != null) {
            return "你好客户端, 我已经收到你的消息。消息为：[" + mes + "] ，第" + (++count) + " 次 \n";
        } else {
            return "你好客户端, 我已经收到你的消息 ";
        }
    }
}
