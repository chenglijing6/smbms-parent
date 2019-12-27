package com.smbms.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.service.role.RoleService;
import com.smbms.service.user.UserService;
import com.smbms.tools.Constants;
import com.smbms.tools.MD5;
import com.smbms.tools.PageSupport;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @RequestMapping(value = "/userlist.html")
    public String getUserList(Model model,
                              @RequestParam(value = "queryname", required = false) String queryname,
                              @RequestParam(value = "queryuserrole", required = false) String queryuserrole,
                              @RequestParam(value = "pageIndex", required = false) String pageIndex,
                              HttpSession session) throws Exception {
        //查询用户列表
        int _queryuserrole = 0;
        List<User> userList = null;
        //当前页码容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        if (queryname == null) {
            queryname = "";
        }
        if (queryuserrole != null && !queryuserrole.equals("")) {
            _queryuserrole = Integer.parseInt(queryuserrole);
        }
        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);

            } catch (NumberFormatException e) {
                return "redirect:/user/syserror.html";
            }
        }
        //总记录数
        int totalCount = userService.getUserCount(queryname, _queryuserrole);
        //总页数
        PageSupport param = new PageSupport();
        param.setCurrentPageNo(currentPageNo);
        param.setPageSize(pageSize);
        param.setTotalCount(totalCount);
        int totalPageCount = param.getTotalPageCount();
        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }
        userList = userService.getUserList(queryname, _queryuserrole, (currentPageNo - 1) * pageSize, pageSize);
        //将得到的数据放到Model中，用于回显
        session.setAttribute("param", param);
        model.addAttribute("userList", userList);
        List<Role> roeList = roleService.getRoleList();
        model.addAttribute("roeList", roeList);
        model.addAttribute("queryname", queryname);
        model.addAttribute("queryuserrole", queryuserrole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }

    //新增用户
  /*  @RequestMapping(value="/useradd.html", method= RequestMethod.GET)
    public String addUser(@ModelAttribute("user")User user){
        return "useradd";
    }
    @RequestMapping(value="/addsave.html",method=RequestMethod.POST)
	public String addUserSave(User user, HttpSession session,
                              HttpServletRequest request,
                              @RequestParam(value="a_idPicPath",required=false) MultipartFile attach) throws Exception {
		//判断文件是否为空
		String idPicPath=null;
		if(!attach.isEmpty()){
			//定义上传目标路径
			String path=request.getSession().getServletContext().getRealPath("static"+ File.separator+"uploadfiles");
			String oldFileName=attach.getOriginalFilename();
			String prefix= FilenameUtils.getExtension(oldFileName);
			int fileSize=5000000;
			if(attach.getSize()>fileSize){
				request.setAttribute("uploadfileError", "* 上传大小不得超过500kB");
				return "useradd";
			}else if( prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("png")
					|| prefix.equalsIgnoreCase("jpeg")
					|| prefix.equalsIgnoreCase("pneg") ){
				//如果上传条件符合，定义文件名和格式为：当前系统时间+随机数+ _Personal.jpg，保证不重名
				String fileName=System.currentTimeMillis()
						        + RandomUtils.nextInt(1000000)+" _Personal.jpg";
					File targetFile=new File(path,fileName);
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					try {
					    //将内存中内容传入磁盘
						attach.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("uploadfileError", "* 上传失败");
						return "useradd";
					}
					idPicPath=path+File.separator+fileName;
			}else{
				request.setAttribute("uploadfileError", "* 上传格式不正确");
				return "useradd";
			}
		}
		user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setCreationDate(new Date());
		user.setIdPicPath(idPicPath);
		if(userService.addUser(user)){
			return "redirect:/sys/user/userlist.html";//新增之后，重定向到userlist界面，而不是直接跳到逻辑视图名 return "userlist";
		}else{
			return "useradd";
		}
    }*/
    @RequestMapping(value = "/useradd.html", method = RequestMethod.GET)
    public String addUser(@ModelAttribute("user") User user) {
        return "useradd";
    }

    //多文件上传
    @RequestMapping(value = "/addsave.html", method = RequestMethod.POST)
    public String addUserSave(User user, HttpSession session,
                              HttpServletRequest request,
                              @RequestParam(value = "attachs", required = false) MultipartFile[] attachs) throws Exception {
        //判断文件是否为空
        String idPicPath = null;
        String workPicPath = null;
        String errorinfo = null;
        String path = request.getSession().getServletContext().getRealPath("static" + File.separator + "uploadfiles");
        for (int i = 0; i < attachs.length; i++) {
            MultipartFile attach = attachs[i];
            if (!attach.isEmpty()) {
                if (i == 0) {
                    errorinfo = "uploadfileError";
                } else if (i == 1) {
                    errorinfo = "uploadwpError";
                }
                //定义上传目标路径
                String oldFileName = attach.getOriginalFilename();
                String prefix = FilenameUtils.getExtension(oldFileName);
                int fileSize = 5000000;
                if (attach.getSize() > fileSize) {
                    request.setAttribute(errorinfo, "* 上传大小不得超过500kB");
                    return "useradd";
                } else if (prefix.equalsIgnoreCase("jpg")
                        || prefix.equalsIgnoreCase("png")
                        || prefix.equalsIgnoreCase("jpeg")
                        || prefix.equalsIgnoreCase("pneg")) {
                    //如果上传条件符合，定义文件名和格式为：当前系统时间+随机数+ _Personal.jpg，保证不重名
                    String fileName = System.currentTimeMillis()
                            + RandomUtils.nextInt(1000000) + " _Personal.jpg";
                    File targetFile = new File(path, fileName);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    try {
                        attach.transferTo(targetFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute(errorinfo, "* 上传失败");
                        return "useradd";
                    }
                    if (i == 0) {
                        idPicPath = path + File.separator + fileName;
                    } else if (i == 1) {
                        workPicPath = path + File.separator + fileName;
                    }
                } else {
                    request.setAttribute(errorinfo, "* 上传格式不正确");
                    return "useradd";
                }
            }
        }
        user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        user.setCreationDate(new Date());
        user.setIdPicPath(idPicPath);
        user.setWorkPicPath(workPicPath);
        //盐值，可以时userId,userName,这里的随机字符串
        String salt = RandomStringUtils.randomAlphabetic(20);
        String password = MD5.md5(user.getUserPassword(), salt);
        user.setUserPassword(password);
        user.setSalt(salt);
        if (userService.addUser(user)) {
            return "redirect:/sys/user/userlist.html";//新增之后，重定向到userlist界面，而不是直接跳到逻辑视图名 return "userlist";
        } else {
            return "useradd";
        }
    }

    @RequestMapping(value = "/ucexist")
    @ResponseBody  //异步请求
    public Object userCodeIsExist(@RequestParam String userCode) throws Exception {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            resultMap.put("userCode", "exist");
        } else {
            User user = userService.getUserCountByUserCode(userCode);
            if (user != null) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "noexist");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }


    //根据用户id来查看用户信息，rest风格
    @RequestMapping(value = "/view/{id}")
    public String viewUser(@PathVariable Integer id, Model model) throws Exception {
        User user = userService.getUserById(id);
        model.addAttribute(user);
        return "userview";
    }
    //根据Id来修改用户信息，@RequestParam带请求的参数

    @RequestMapping(value = "/usermodify.html", method = RequestMethod.GET)
    public String getUserById(@RequestParam String id, Model model) throws Exception {
        int id0=Integer.parseInt(id);
        User user = userService.getUserById(id0);
        model.addAttribute(user);
        return "usermodify";
    }

    @RequestMapping(value = "/usermodifysave.html", method = RequestMethod.POST)
    public String modifyUserSave(User user, HttpSession session) throws Exception {
        User user0 = (User) session.getAttribute(Constants.USER_SESSION);
        user.setModifyBy(user0.getId());
        user.setModifyDate(new Date());
        if (userService.modify(user)) {
          if(user.getId()==user0.getId()){
              session.removeAttribute(Constants.USER_SESSION);
            session.setAttribute(Constants.USER_SESSION,user);

          }
            return "redirect:/sys/user/userlist.html";
        }
        return "usermodify";
    }
    //根据用户id查看用户信息1
    /*@RequestMapping(value = "/view",method=RequestMethod.GET)
    @ResponseBody
    public Object view(@RequestParam String  id ){
        String cjson="";
        if(null == id || "".equals(id)){
            return "nodata";
        }else{
            try {
                User user = userService.getUserById(id);
                logger.info(user.getCreationDate());
                cjson  = JSON.toJSONString(user);
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
        return cjson;
    }*/

    //根据用户id查看用户信息2,配置了日期消息转换器之后
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public User view(@RequestParam Integer id) {
        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    //删除用户
  /* @ResponseBody
    @RequestMapping(value="/deleteUser",method=RequestMethod.GET)
    public Object deleteUser(@RequestParam String id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String,String> delResult=new HashMap<String,String>();
        if(userService.deleteUserById(id)){
            delResult.put("message","true");
            }else{
            delResult.put("message","false");
            }
            return JSON.toJSONString(delResult);
        }*/
        /*@RequestMapping(value="/deleteUser",method=RequestMethod.GET)
        @ResponseBody
        public ModelAndView delNews(@RequestParam String id,ModelAndView modelAndView) throws Exception {
            modelAndView.addObject("flag",userService.deleteUserById(id)?true:false);
            modelAndView.setViewName("redirect:/sys/user/userlist.html");
            return modelAndView;
        }*/
    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public boolean deleteUser(@RequestParam String id) {
        boolean flag = false;
        try {
            int id0 = Integer.parseInt(id);
            flag = userService.deleteUserById(id0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
        //验证原密码是否正确
    @ResponseBody
    @RequestMapping(value="/pwdmodify.html" ,method=RequestMethod.GET)
    public  Object validatePwd(@RequestParam String oldpassword,@RequestParam String id) throws Exception {
        HashMap<String,String> delResult=new HashMap<String,String>();
        if(oldpassword==null || oldpassword==""){
            delResult.put("message","error");
        }
        int id0 = Integer.parseInt(id);
        User user = userService.getUserById(id0);
        String userPassword=user.getUserPassword();
        String salt=user.getSalt();
        String passowrd0=MD5.md5(oldpassword,salt);
        if(passowrd0.equals(userPassword)){
            delResult.put("message","true");
        }else if(!passowrd0.equals(userPassword)){
            delResult.put("message","false");
        }
        return JSON.toJSONString(delResult);
    }
    //修改用户密码
    @RequestMapping(value = "/updatepwd.html", method = RequestMethod.GET)
    public String updatePwd(@RequestParam String id, Model model) throws Exception {
        int id0 = Integer.parseInt(id);
        User user = userService.getUserById(id0);
        model.addAttribute("user", user);
        return "password";
    }

    @RequestMapping(value = "/savepwd.html", method = RequestMethod.POST)
    public String updatePassword(@RequestParam String id, @RequestParam String newPassword) throws Exception {
        int id0 = Integer.parseInt(id);
        User user = userService.getUserById(id0);
        String salt = user.getSalt();
        String password = MD5.md5(user.getUserPassword(), salt);
        user.setUserPassword(password);
        if (userService.updatePwd(id0, password)) {
            return "redirect:/sys/main.html";
        } else {
            return "password";
        }
    }

    @RequestMapping(value = "/sys/main.html")
    public String main() {
        return "frame";
    }
}
