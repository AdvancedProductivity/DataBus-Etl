package org.adp.databus.app.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@WebFilter(filterName = "custom scs", urlPatterns = "/*")
public class TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("url is: " + request.getRequestURI());
        final String[] shops = parameterMap.get("shop");
        String shop = "";
        if (shops != null) {
            System.out.println("shop param is: " + Arrays.toString(shops));
            if (shops.length > 0) {
                shop = shops[0];
            }
        } else {
            System.out.println("have no shop");
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = "frame-ancestors " + shop + " admin.xxx.com";
        response.addHeader("content-security-policy", header);
        response.addHeader("zzq", header);
        System.out.println("add header " + header);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
