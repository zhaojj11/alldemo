package com.pyjava.demo.democat.servlet;

import com.pyjava.demo.democat.web.Request;
import com.pyjava.demo.democat.web.Response;

/**
 * <p>描述: [功能描述] </p>
 *
 * @author zhaojj11
 * @since 1.0
 */
public interface Servlet {
    /**
     * 初始化方法
     *
     * @throws Exception 异常
     */
    void init() throws Exception;

    /**
     * 操作
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     */
    void service(Request request, Response response) throws Exception;

    /**
     * destroy
     */
    void destroy();
}
