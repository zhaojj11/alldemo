package com.pyjava.demo.democat.servlet;

import com.pyjava.demo.democat.web.BaseServlet;
import com.pyjava.demo.democat.web.Request;
import com.pyjava.demo.democat.web.Response;

import java.io.IOException;

/**
 * @author zhaojj9
 */
public class WriteServlet extends BaseServlet {
    @Override
    public void doGet(Request request, Response response) {
        try {
            response.write("I am writing").out(200);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        try {
            response.write("I am writing").out(200);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
