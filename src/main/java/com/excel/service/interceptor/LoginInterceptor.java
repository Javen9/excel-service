package com.excel.service.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            if (!"/login".equals(request.getServletPath())) {
                //控制iframe与ajax登录跳转问题
                response.getWriter().write("<script>window.parent.parent.location.href='/login'</script>");
                return false;
            }
        }
        return true;
    }
}