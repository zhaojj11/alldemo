package com.pyjava.demo.democat.util;

import com.pyjava.demo.democat.constants.HttpStatus;

/**
 * @author zhaojj9
 */
public class HttpUtil {

    public static String getHttpResponseContext(int code, String content, String errorMsg){
        if(code == HttpStatus.OK){
            return "HTTP/1.1 200 OK \n"+
                    "Content-Type: text/html\n" +
                    "\r\n" + content;
        }else if(code == HttpStatus.Internal_Server_Error){
            return "HTTP/1.1 500 Internal Error="+ errorMsg + " \n"+
                    "Content-Type: text/html\n" +
                    "\r\n";
        }

        return "HTTP/1.1 404 NOT Found \n"+
                "Content-Type: text/html\n" +
                "\r\n" +
                "<h1>404 Not Found</h1>";
    }
}
