package com.smbms.controller;

import com.smbms.pojo.User;
import com.smbms.security.CustomToken;
import com.smbms.service.user.UserService;
import com.smbms.tools.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Resource
    private UserService userService;

    @RequestMapping(value = {"/login.html", "/"},method=RequestMethod.GET)
    public String tologin() {
        return "login";
    }

    @RequestMapping(value = "/dologin.html", method = RequestMethod.POST)
    public String login(@RequestParam("userCode") String userCode,
                        @RequestParam("userPassword") String userPassword, Model model, HttpSession session) {
        //获取当前用户对象
        Subject subject = SecurityUtils.getSubject();
        //生成令牌(传入用户输入的账号和密码)
        CustomToken token = new CustomToken(userCode, userPassword, false);
        //访问realm  进行处理,把数据交给realm
        //这里会加载自定义的realm
        try {
            //把令牌放到login里面进行查询,如果查询账号和密码时候匹配,
            // 如果匹配就把user对象获取出来,失败就抛异常
            subject.login(token);
        } catch (UnknownAccountException e) {
            model.addAttribute("error", "用户不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("error", "密码错误");
            return "login";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "登录频繁，请稍后登录");
            return "login";
        }
       // User user = userService.getLoginUser(userCode);
        User user=(User)subject.getPrincipal();
        session.setAttribute(Constants.USER_SESSION, user);
        return "redirect:/sys/main.html";
    }


    @RequestMapping(value = "/sys/main.html")
    public String main() {
        return "frame";
    }


  /*  @RequestMapping(value={"/login.html","/"})
    public String login(){
        return "login";
    }
    //登录
    @RequestMapping(value="/dologin.html", method= RequestMethod.POST)
    public String dologin(@RequestParam String userCode, @RequestParam String userPassword,
                          HttpSession session, HttpServletRequest request) throws Exception{
        User user = userService.login(userCode, userPassword);
        if(user != null){
            session.setAttribute(Constants.USER_SESSION, user);
            return "redirect:/sys/main.html";
        }else{
            request.setAttribute("error", "用户名或密码不正确");
            return "login";
        }
    }*/

    /*//注销
    @RequestMapping(value = "/logout.html")
    public String logout(HttpSession session) {
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }*/
    //实现退出
    @RequestMapping(value = "/logout.html",method = RequestMethod.GET)
    public String logout1(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
    //401.jsp
    @RequestMapping(value = "/401.html")
    public String toError() {
        return "redirect:/401.jsp";
    }
}
