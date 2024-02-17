package com.atguigu.filter;

import com.atguigu.utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Leevi
 * 日期2021-05-19  14:12
 */
public class CloseConnectionFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.releaseConnection();
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
