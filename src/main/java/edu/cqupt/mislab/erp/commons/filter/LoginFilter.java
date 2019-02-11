package edu.cqupt.mislab.erp.commons.filter;

import edu.cqupt.mislab.erp.user.constant.UserConstant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/*",initParams = {
        @WebInitParam(name = "exclusions",value = "/user/student/login",description = "不拦截登录请求")
})
public class LoginFilter implements Filter {

    //显示未登录信息
    private final byte[] responseMessage = ("{ \"code\": " + HttpStatus.UNAUTHORIZED + ",\"data\": {},\"msg\": \"请先登录\" }").getBytes(StandardCharsets.UTF_8);

    //用来记录需要放行的URL
    private Set<String> exclusionsUrl ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{

        final String exclusions = filterConfig.getInitParameter("exclusions");

        this.exclusionsUrl = new HashSet<>(Arrays.asList(exclusions.split(",")));
    }

    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException{

        if(request instanceof HttpServletRequest){

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            final HttpSession session = httpServletRequest.getSession(true);

            final Boolean isLogin = (Boolean) session.getAttribute(UserConstant.USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME);

            if(exclusionsUrl.contains(httpServletRequest.getRequestURI()) || isLogin != null && isLogin){

                //放行
                chain.doFilter(request,response);
            }else {

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.setContentLength(responseMessage.length);

                response.getWriter().println(new String(responseMessage,StandardCharsets.UTF_8));

                response.flushBuffer();
            }
        }
    }

    @Override
    public void destroy(){ }
}
