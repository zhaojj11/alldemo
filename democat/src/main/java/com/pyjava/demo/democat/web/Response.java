package com.pyjava.demo.democat.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhaojj9
 */
public class Response {
    private final OutputStream outputStream;

    /**
     * 响应头
     */
    private StringBuilder responseHeader;
    /**
     * 响应内容
     */
    private StringBuilder responseBody;

    /**
     * 换行
     */
    private static final String CRLF = "\r\n";
    /**
     * 空格
     */
    private static final String BLANK = " ";
    /**
     * 响应内容的长度
     */
    private int length;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
        responseHeader = new StringBuilder();
        responseBody = new StringBuilder();
    }

    /**
     * 构造响应头
     *
     * @param code 异常码
     */
    private void createResponseHeader(int code) {
        responseHeader.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                responseHeader.append("OK");
                break;
            case 500:
                responseHeader.append("SERVER ERROR");
                break;
            default:
                responseHeader.append("NOT FOUND");
                break;
        }
        responseHeader.append(CRLF);
        responseHeader.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        responseHeader.append("Content-Length:").append(length).append(CRLF);
        responseHeader.append(CRLF);
    }

    public Response write(String info) {
        responseBody.append(info).append(CRLF);
        length += (info + CRLF).getBytes(StandardCharsets.UTF_8).length;
        return this;
    }

    public void out(int code) {
        if (responseHeader == null) {
            code = 500;
        }
        try {
            //调用本类中的构造响应头
            this.createResponseHeader(code);
            outputStream.write(responseHeader.toString().getBytes());
            outputStream.write(responseBody.toString().getBytes());
            System.out.println(responseHeader.toString());
            System.out.println(responseBody.toString());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
