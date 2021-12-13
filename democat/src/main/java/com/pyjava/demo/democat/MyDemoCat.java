package com.pyjava.demo.democat;

import com.pyjava.demo.democat.web.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaojj9
 */
public class MyDemoCat {
    private ServerSocket serverSocket;
    private boolean isShutDown = false;

    private int port = 8080;
    public static Map<String, BaseServlet> urlServletMap = new ConcurrentHashMap<>(16);

    public static void main(String[] args) {
        MyDemoCat myDemoCat = new MyDemoCat();
        myDemoCat.start();
    }

    public void start() {
        this.start(8080);
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            initServletMapping();
            System.out.println("democat is running:http://localhost:" + port);
            this.receive(); //调用接收请求信息的方法
        } catch (IOException e) {
            isShutDown = true;
        }
    }

    private void receive() {
        try {
            while (!isShutDown) {
                //监听
                Socket client = serverSocket.accept();
                //创建线程类的对象
                Dispatcher dis = new Dispatcher(client);
                //创建线程的代理类，并启动线程
                // todo 线程池
                new Thread(dis).start();
            }

        } catch (IOException e) {
            //关闭服务器
            this.stop();
        }

    }

    public MyDemoCat() {
    }

    public MyDemoCat(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void initServletMapping() {
        try {
            for (ServletMapping servletMapping : ServletMappingConfig.servletMappings) {
                Class<?> aClass = Class.forName(servletMapping.getClazz());
                Class<? extends BaseServlet> servletClass = aClass.asSubclass(BaseServlet.class);
                BaseServlet myServlet = servletClass.newInstance();
                urlServletMap.put(servletMapping.getUrl(), myServlet);
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        isShutDown = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
