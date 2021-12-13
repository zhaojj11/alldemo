package com.pyjava.demo.democat;

import com.pyjava.demo.democat.web.ServletMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojj9
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappings = new ArrayList<>();
    static {
        servletMappings.add(new ServletMapping("read", "/read", "com.pyjava.demo.democat.servlet.ReadServlet"));
        servletMappings.add(new ServletMapping("write", "/write", "com.pyjava.demo.democat.servlet.WriteServlet"));
    }
}
