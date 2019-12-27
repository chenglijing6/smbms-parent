package com.smbms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.pojo.Bill;
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
import java.util.List;

@Controller
@RequestMapping("/sys/bill")
public class BillController {
    private Logger logger = Logger.getLogger(BillController.class);
    @Resource
    private BillService billService;
    @Resource
    private ProviderService providerService;

   /* @RequestMapping(value = "/billlist.html")
    public String getBillList(Model model,
                              @RequestParam(value = "productName", required = false) String productName,
                              @RequestParam(value = "providerId", required = false) String providerId,
                              @RequestParam(value = "isPayment", required = false) String isPayment,
                              @RequestParam(value = "pageIndex", required = false) String pageIndex,
                              HttpSession session) throws Exception {
        int _providerId = 0;
        int _isPayment = 0;
        int currentPageNo = 1;
        List<Bill> billList = null;
        //当前页码容量
        int pageSize = Constants.pageSize;
        //当前页码
        if (productName == null) {
            productName = "";
        }
        if (providerId != null && !providerId.equals("")) {
            _providerId = Integer.parseInt(providerId);
        }
        if (isPayment != null && !isPayment.equals("")) {
            _isPayment = Integer.parseInt(isPayment);
        }
        if (pageIndex != null) {
            *//* try {*//*
            currentPageNo = Integer.valueOf(pageIndex);
            *//*} catch (NumberFormatException e) {
                return "redirect:/bill/syserror.html";
            }*//*
        }
        //总记录数
        int totalCount = billService.getBillCount(productName, _providerId, _isPayment);
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
        billList = billService.getBillList(productName, _providerId, _isPayment, (currentPageNo - 1) * pageSize, pageSize);
        //将得到的数据放到Model中，用于回显
        session.setAttribute("param", param);
        model.addAttribute("billList", billList);
        List<Provider> providerList = providerService.getProviderList();
        for (Provider provider : providerList) {
            logger.info("供应商id为：" + provider.getId() + "  名称为：" + provider.getProName());
        }
        session.setAttribute("providerList", providerList);
        model.addAttribute("providerList", providerList);
        model.addAttribute("productName", productName);
        model.addAttribute("providerId", providerId);
        model.addAttribute("isPayment", isPayment);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "billlist";
    }*/

    //利用pagehelper
    @RequestMapping(value = "/billlist.html")
    public String getBillList(Model model,
                              @RequestParam(value = "productName", required = false) String productName,
                              @RequestParam(value = "providerId", required = false) String providerId,
                              @RequestParam(value = "isPayment", required = false) String isPayment,
                              @RequestParam(value = "pageIndex",defaultValue = "1", required = false) String pageIndex,
                              HttpSession session) throws Exception {
        int _providerId = 0;
        int _isPayment = 0;
        int currentPageNo =0;
        List<Bill> billList = null;
        //当前页码容量
        int pageSize = Constants.pageSize;
        //当前页码
        if (productName == null) {
            productName = "";
        }
        if (providerId != null && !providerId.equals("")) {
            _providerId = Integer.parseInt(providerId);
        }
        if (isPayment != null && !isPayment.equals("")) {
            _isPayment = Integer.parseInt(isPayment);
        }
        if (pageIndex != null) {
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                return "redirect:/bill/syserror.html";
            }
        }
        PageHelper.startPage(currentPageNo,pageSize);
        billList = billService.getBillList(productName,_providerId,_isPayment);
        PageInfo page = new PageInfo<>(billList,pageSize);
        //将得到的数据放到Model中，用于回显
        model.addAttribute("page", page);
        //  model.addAttribute("billList", billList);
        List<Provider> providerList = providerService.getProviderList();
        model.addAttribute("providerList", providerList);
        return "billlist1";
    }
    @RequestMapping(value = "/syserror.html")
    public String syserror() {
        return "syserror";
    }


    //根据id查看具体账单bill信息
    @ResponseBody
    @RequestMapping(value = "/viewBill", method = RequestMethod.GET)
    public Bill viewBill(@RequestParam int id, Model model) {
        Bill bill = null;
        try {
            bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    //增加账单
    @RequestMapping(value = "/addBill.html", method = RequestMethod.GET)
    public String addBill(@ModelAttribute("bill") Bill bill) {
        return "billadd";
    }

    @RequestMapping(value = "/tosaveBill.html", method = RequestMethod.POST)
    public String saveBill(Bill bill, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        bill.setCreatedBy(user.getId());
        bill.setCreationDate(new Date());
        if (billService.addBill(bill))
            return "redirect:/sys/bill/billlist.html";
        else
            return "billadd";
    }

    //修改账单
    @RequestMapping(value = "/updateBill.html", method = RequestMethod.GET)
    public String updateBill(@RequestParam int id, Model model) {
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill", bill);
        return "billupdate";
    }

    @RequestMapping(value = "/saveBill.html", method = RequestMethod.POST)
    public String saveUpdateBill(Bill bill, HttpSession session) {
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        bill.setModifyBy(user.getId());
        bill.setModifyDate(new Date());
        if (billService.updateBill(bill)) {
            return "redirect:/sys/bill/billlist.html";
        } else {
            return "billupdate";
        }
    }

    //删除账单
    @ResponseBody
    @RequestMapping(value = "/deletebill", method = RequestMethod.GET)
    public boolean deleteBill(@RequestParam String billid) {
        boolean flag = false;
        try {
            logger.info("==============deletebill");
            int id0 = Integer.parseInt(billid);
            flag = billService.deleteBill(id0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return flag;
    }
}