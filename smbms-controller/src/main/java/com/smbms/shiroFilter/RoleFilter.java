package com.smbms.shiroFilter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义shiro角色过滤器，重写isAccessAllowed方法，使原来的方法变为 "或"的关系
 */
public class RoleFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
               boolean flag=false;
                Subject subject = this.getSubject(request, response);
                    String[] roles=(String[])mappedValue;
                    //没有角色访问拦截，放行
                    if(roles==null || roles.length==0){
                        flag=false;
                    }else {//根据角色进行控制
                        for(String role:roles){
                            if(subject.hasRole(role)){//一旦匹配到了指定的角色，放行
                                flag=true;
                            }
                        }
                    }
                    return flag;
    }
}
