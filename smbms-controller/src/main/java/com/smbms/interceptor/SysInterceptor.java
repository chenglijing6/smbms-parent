package com.smbms.interceptor;

import com.smbms.pojo.User;
import com.smbms.tools.Constants;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 *
 * @author 26896
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = Logger.getLogger(SysInterceptor.class);

    //拦截器,除了登录页面之外，其他网页均要拦截
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logger.debug("SysInterceptor preHandle ==========================");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/401.html");
            return false;
        }
        return true;
    }
}
