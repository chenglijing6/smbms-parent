package com.smbms.controller;


import com.smbms.pojo.User;
import com.smbms.service.user.UserService;
import com.smbms.tools.Constants;
import com.smbms.tools.ExportExcelUtil;
import com.smbms.tools.ImportExcelUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class ExcelController {
    private Logger logger = Logger.getLogger(ExcelController.class);
    @Resource
    private UserService userService;

    //使用工具类导出excel
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) {
        //Sheet 名称
        String sheetName = "员工信息";
        //需要放到excel中的数据
        List<User> allUsers = userService.getAllUsers();
        ServletOutputStream outputStream = null;
        //生成一个文档对象
        try {
            Workbook workbook = ExportExcelUtil.writeNewExcel(sheetName, allUsers);
            //将内容写出到流中   excel文档格式
            //contentType
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //下载   content-disposition
            response.setHeader("content-disposition", "attachement;filename=user.xlsx");
            outputStream = response.getOutputStream();
            //输出  下载
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    //下载excel模板
    //模板文件放在web项目的WEB-INF中clases下WEB-INF下
    @RequestMapping("/downloadExcel1")
    public void downloadModel(@RequestParam("filename") String filename, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        //要下载的模板文件的位置
        //   。。。。。。\WEB-INF\classes
        String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        //。。。。。。\WEB-INF\demo.xlsx
        path = path.substring(0, path.lastIndexOf("classes"));
        //要下载的文档的路径是
        path = path + filename;
        File file = new File(path);
        //生成一个文档对象
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            //将内容写出到流中   excel文档格式
            //contentType
            //下载   content-disposition
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachement;fileName=" + filename);
            outputStream = response.getOutputStream();
            //输出  下载
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //导入excel
    @RequestMapping("/uploadExcel.html")
    public String upload(@RequestParam("uploadExcel") MultipartFile myFile, HttpSession session) throws IOException {
        System.out.println("进入/uploadExcel.html");
        //获取文件名
        String originalFilename = myFile.getOriginalFilename();
        InputStream inputStream = null;
        Boolean flag = false;
        try {
            //流
            inputStream = myFile.getInputStream();
            //获取数据
            List<List<Object>> lists = ImportExcelUtil.getBankListByExcel(inputStream, originalFilename);
            //排除表头
            lists.remove(0);
            //遍历
            for (List<Object> list : lists) {
                String userCode = (String) list.get(0);//用户编号
                String userName = (String) list.get(1);//用户姓名
                String gender = (String) list.get(2);//用户性别
                String address = (String) list.get(3);//用户地址
                String userRole = (String) list.get(4);//用户角色
                String userPassword = (String) list.get(5);//用户密码
                String phone = (String) list.get(6);//用户电话
                String _birthday = (String) list.get(7);//出生日期
                //将数据封装到实体类对象中
                User user0 = userService.getUserCountByUserCode(userCode);
                if (user0 == null) {
                    User user = new User();
                    user.setUserName(userName);
                    user.setUserCode(userCode);
                    user.setGender(Integer.valueOf(gender));
                    user.setAddress(address);
                    user.setUserRole(Integer.valueOf(userRole));
                    user.setUserPassword(userPassword);
                    user.setPhone(phone);
                    user.setBirthday(DateUtil.parseYYYYMMDDDate(_birthday));
                    user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
                    user.setCreationDate(new Date());
                    //存
                    flag = userService.addUser(user);
                } else {
                    throw new Exception("用户已存在！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag) {
            return "redirect:/sys/user/userlist.html";
        } else {
            return "505";
        }
    }
}
