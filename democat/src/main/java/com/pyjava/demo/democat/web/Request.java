package com.pyjava.demo.democat.web;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhaojj9
 */
public class Request {
    private String url;
    private String method;
    private InputStream inputStream;

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int length;
        if((length = inputStream.read(bytes)) > 0){
            sb.append(new String(bytes, 0, length));
        }
        String httpRequest = sb.toString();
        System.out.println("http Request = [" + httpRequest + "]");

        int read = inputStream.read(bytes);
        String httpHead = httpRequest.split("\n")[0];
        setUrl(httpHead.split("\\s")[1]);
        setMethod(httpHead.split("\\s")[0]);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", inputStream=" + inputStream +
                '}';
    }
}
