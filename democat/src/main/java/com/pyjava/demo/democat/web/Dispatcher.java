package com.pyjava.demo.democat.web;

import java.io.IOException;
import java.net.Socket;

import static com.pyjava.demo.democat.MyDemoCat.urlServletMap;

/**
 * <p>描述: [功能描述] </p>
 *
 * @author zhaojj11
 * @since 1.0
 */
public class Dispatcher implements Runnable {
    private Request req;
    private Response rep;
    private Socket client;
    private int code = 200;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            req = new Request(this.client.getInputStream());
            rep = new Response(this.client.getOutputStream());
        } catch (IOException e) {
            code = 500;
        }
    }

    @Override
    public void run() {
        BaseServlet servlet = urlServletMap.get(req.getUrl());
        if (servlet == null) {
            this.code = 404;
        } else {
            try {
                servlet.service(req, rep);
            } catch (Exception e) {
                this.code = 500;
            }
        }
        //将响应结果推送到客户机的浏览器
        rep.out(code);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
