package com.pyjava.demo.democat.web;

import com.pyjava.demo.democat.constants.HttpMethod;
import com.pyjava.demo.democat.servlet.Servlet;

import java.io.IOException;

/**
 * @author zhaojj9
 */
public abstract class BaseServlet implements Servlet {
    /**
     * get方法
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException io异常
     */
    public abstract void doGet(Request request, Response response) throws IOException;

    /**
     * post方法
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException io异常
     */
    public abstract void doPost(Request request, Response response) throws IOException;

    @Override
    public void service(Request request, Response response) throws IOException, NoSuchMethodException {
        if (HttpMethod.GET.equals(request.getMethod())) {
            doGet(request, response);
        } else if (HttpMethod.POST.equals(request.getMethod())) {
            doPost(request, response);
        } else {
            throw new NoSuchMethodException("not support");
        }
    }
}
