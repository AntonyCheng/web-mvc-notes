package com.atguigu.filter;

import com.atguigu.utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Leevi
 * 日期2021-05-19  14:34
 */
public class TransactionFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Connection conn = null;
        try {
            //开启事务
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);

            chain.doFilter(req, resp);
            //提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e.getMessage());
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
