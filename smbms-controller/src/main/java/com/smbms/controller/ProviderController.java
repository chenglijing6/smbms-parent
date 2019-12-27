package com.smbms.controller;

import com.alibaba.fastjson.JSON;
import com.smbms.pojo.Provider;
import com.smbms.pojo.User;
import com.smbms.service.bill.BillService;
import com.smbms.service.provider.ProviderService;
import com.smbms.tools.Constants;
import com.smbms.tools.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/sys/provider")
public class ProviderController {
    private Logger logger = Logger.getLogger(ProviderController.class);
    @Resource
    private ProviderService providerService;
    @Resource
    private BillService billService;

    //遍历列表
    @RequestMapping(value = "/providerlist.html")
    public String getProviderList(@RequestParam(value = "proName", required = false) String proName,
                                  @RequestParam(value = "pageIndex", required = false) String pageIndex,
                                  Model model, HttpSession session) {
        List<Provider> providerList = null;
        //当前页码容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        if (proName == null) {
            proName = "";
        }

        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                return "redirect:/user/syserror.html";
            }
        }
        //总记录数
        int totalCount = providerService.getProviderCount(proName);
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
        providerList = providerService.getProviderListAll(proName, (currentPageNo - 1) * pageSize, pageSize);
        //将得到的数据放到Model中，用于回显
        session.setAttribute("param", param);
        model.addAttribute("providerList", providerList);
        model.addAttribute("proName", proName);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "providerlist";
    }

    //根据id查看供应商信息,异步请求
    @ResponseBody
    @RequestMapping(value = "/viewProvider", method = RequestMethod.GET)
    public Object viewProvider(@RequestParam int id) {
        Provider provider = null;
        try {
            provider = providerService.getProviderById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provider;
    }

    //增加供应商
    @RequestMapping(value = "/addProvider.html", method = RequestMethod.GET)
    public String addProvider(@ModelAttribute Provider provider) {
        return "provideradd";
    }

    @RequestMapping(value = "/addsave.html", method = RequestMethod.POST)
    public String saveProvider(Provider provider, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        provider.setCreatedBy(user.getId());
        provider.setCreationDate(new Date());
        if (providerService.addProvider(provider)) {
            return "redirect:/sys/provider/providerlist.html";
        } else {
            return "prvideradd";
        }
    }

    //修改用户
    @RequestMapping(value = "/updateprovider.html", method = RequestMethod.GET)
    public String updateProvider(@RequestParam int id, Model model) {
        Provider provider = providerService.getProviderById(id);
        model.addAttribute("provider", provider);
        return "providerupdate";
    }

    @RequestMapping(value = "/updatesave.html", method = RequestMethod.POST)
    public String updateSave(Provider provider, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        provider.setModifyBy(user.getId());
        provider.setModifyDate(new Date());
        if (providerService.updateProvider(provider)) {
            return "redirect:/sys/provider/providerlist.html";
        } else {
            return "providerupdate";
        }
    }

    //删除供应商
    @ResponseBody
    @RequestMapping(value = "/deleteprovider", method = RequestMethod.POST)
    public Object deleteProvider(@RequestParam String providerId) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        int _providerId = Integer.parseInt(providerId);
        int count = billService.getBillByPid(_providerId);
        logger.info("=============================deleteprovider");
        if (count != 0) {
            resultMap.put("message", "SORRY");
        } else if (count == 0) {
            if (providerService.deleteprovider(_providerId)) {
                resultMap.put("message", "YES");
            } else {
                resultMap.put("message", "No");
            }
        }
        return JSON.toJSONString(resultMap);
    }

}
