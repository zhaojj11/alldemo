package com.pyjava.demo.democat.servlet;

import com.pyjava.demo.democat.web.BaseServlet;
import com.pyjava.demo.democat.web.Request;
import com.pyjava.demo.democat.web.Response;

import java.io.IOException;

/**
 * @author zhaojj9
 */
public class ReadServlet extends BaseServlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            response.write("I am reading").out(200);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            response.write("I am reading").out(200);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
