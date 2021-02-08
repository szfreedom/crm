package com.sz.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodeingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
            req.setCharacterEncoding("utf-8");

            res.setContentType("text/html;charset=utf-8");
            filterChain.doFilter(req,res);
    }

    @Override
    public void destroy() {

    }
}
