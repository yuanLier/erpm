package edu.cqupt.mislab.erp.commons.filter;

import edu.cqupt.mislab.erp.commons.response.WebResponseVo.ResponseStatus;
import edu.cqupt.mislab.erp.user.constant.UserConstant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chuyunfei
 * @description 登录状态检查过滤器，用于拦截需要登录的接口
 * @date 12:17 2019/5/3
 **/

public class LoginStatusCheckFilter implements Filter {

    /**
     * 记录配置需要放行的初始化参数的参数名称
     */
    public static final String EXCLUSIONS_URLS_PARAMETER_NAME = "exclusions";

    /**
     * 多个URL时使用什么分隔符来进行分割
     */
    public static final String URL_SEPARATOR = ",";

    /**
     * 未登录时需要响应给前端的信息
     */
    private final byte[] responseMessage = ("{ \"code\": " + ResponseStatus.UNAUTHORIZED.getCode() + ",\"data\": {},\"msg\": \"登录后，发现新天地(｀・ω・´)\" }").getBytes(StandardCharsets.UTF_8);

    /**
     * 用来记录需要放行的URL，也就是用户登录需要用到的接口URL
     */
    private Set<String> exclusionsUrl ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{

        //获取初始化参数来确认那些URL需要被拦截
        final String exclusions = filterConfig.getInitParameter(EXCLUSIONS_URLS_PARAMETER_NAME);

        if(exclusions != null && exclusions.length() != 0){

            try{
                //切字符串
                final String[] urls = exclusions.split(URL_SEPARATOR);
                //初始化需要过滤的字符串
                this.exclusionsUrl = new HashSet<>(Arrays.asList(urls));
            }catch(Exception e){e.printStackTrace();}

        }else {

            //初始化防止空指针异常
            this.exclusionsUrl = new HashSet<>();
        }
    }

    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException, ServletException{

        //防止类型转换失败的异常
        if(request instanceof HttpServletRequest){

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            final HttpSession session = httpServletRequest.getSession(true);

            //获取Session里面的登录状态标志位
            final Boolean isLogin = (Boolean) session.getAttribute(UserConstant.USER_LOGIN_SUCCESS_SESSION_ATTR_TOKEN_NAME);

            //如果：这个接口不被拦截、已经登录，将放行
            final boolean goAhead = exclusionsUrl.contains(httpServletRequest.getRequestURI()) || isLogin != null && isLogin;
            if(goAhead){
                //放行
                chain.doFilter(request,response);
            }else {

                //拦截这个请求
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.setContentLength(responseMessage.length);
                //打印响应信息
                response.getWriter().println(new String(responseMessage,StandardCharsets.UTF_8));
                //刷新缓存数据
                response.flushBuffer();
            }
        }
    }

    @Override
    public void destroy(){ }
}
