package com.rain.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        String requestPath = uri.substring(uri.lastIndexOf("/") + 1, uri.length());

        if ("LoginServlet".equals(requestPath) || "login.jsp".equals(requestPath) || "ExitServlet".equals(requestPath) || "".equals(requestPath)
                || uri.contains("/static/") || "register.jsp".equals(requestPath) || "RegisterServlet".equals(requestPath)
                 ) {
            filterChain.doFilter(request, response);
        } else {
            if (session != null) {
                Object aid = session.getAttribute("aid");
                if (aid != null) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    uri = "/login.jsp";
                }
            } else {
                uri = "/login.jsp";
            }
            //request.getRequestDispatcher(uri).forward(request, response);
            session.setAttribute("state","૮(ﾟ∀ﾟ)ა没登录呢，不能访问！！");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }

    @Override
    public void destroy() {

    }
}
