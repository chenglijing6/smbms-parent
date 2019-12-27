package com.smbms.security;

import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.service.role.RoleService;
import com.smbms.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class MyShiroReam extends AuthorizingRealm {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo sai=new SimpleAuthorizationInfo();
        System.out.println("授权认证====================");
         User user = (User)SecurityUtils.getSubject().getPrincipal();
           Integer userRole = user.getUserRole();
           int roleId=userRole;
           Role role = roleService.getRoleById(roleId);
           sai.addRole(role.getRoleCode());
           return sai;
    }


    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        System.out.println("进入shiro验证================================");
        String userCode0 = (String) token.getPrincipal();
        String userPassword0 = new String((char[]) token.getCredentials());
        User user = userService.getLoginUser(userCode0);
        System.out.println(user);
        System.out.println("进入shiro验证2222222222================================");
        if (user == null) {
            throw new UnknownAccountException();
        }
				/*if(!user.getUserPassword().equals(userPassword0)){
						throw new IncorrectCredentialsException();
				}*/
        String salt = user.getSalt();
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        //没有加盐
        //  SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user, user.getUserPassword(), getName());
        //加盐认证，
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPassword(), byteSource, getName());
        System.out.println("进入shiro验证1111111111================================");
        return info;
        //除此之外，遇到的异常自动进入	AuthenticationException
        //认证缓存信息
        // 1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
        // 2). credentials: 密码.
        // 3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        //Shiro封装的校验Principals 和 credentials 的具体实现方法


    }



}
