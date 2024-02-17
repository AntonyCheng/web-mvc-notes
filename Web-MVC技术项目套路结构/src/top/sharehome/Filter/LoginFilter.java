package com.atguigu.filter;

import com.atguigu.bean.CommonResult;
import com.atguigu.bean.User;
import com.atguigu.constant.BookStoreConstants;
import com.atguigu.utils.JsonUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Leevi
 * 日期2021-05-18  14:20
 */
public class LoginFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1. 判断当前是否已登录
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute(BookStoreConstants.USERSESSIONKEY);
        if (loginUser == null) {

            //响应数据给客户端，告诉客户端未登录
            CommonResult commonResult = CommonResult.error().setMessage("unlogin");
            JsonUtils.writeResult(response,commonResult);
            return;
        }

        //3. 如果已登录，则放行
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
