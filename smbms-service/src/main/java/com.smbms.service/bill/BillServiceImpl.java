package com.smbms.service.bill;

import com.smbms.dao.bill.BillMapper;
import com.smbms.dao.user.UserMapper;
import com.smbms.pojo.Bill;
import com.smbms.pojo.User;
import com.smbms.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BillServiceImpl implements BillService {
    @Resource
    private BillMapper billMapper;


  /*  @Override
    public List<Bill> getBillList(String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception {
        return billMapper.getBillList(productName, providerId, isPayment, currentPageNo, pageSize);
    }*/


    @Override
    public List<Bill> getBillList(String productName, int providerId, int isPayment) throws Exception {
        return billMapper.getBillList(productName, providerId, isPayment);
    }

    @Override
    public int getBillCount(String productName, int providerId, int isPayment) throws Exception {
        return billMapper.getBillCount(productName, providerId, isPayment);
    }

    @Override
    public Bill getBillById(int id) {
        return billMapper.getBillById(id);
    }

    @Override
    public boolean addBill(Bill bill) {
        return billMapper.addBill(bill);
    }

    @Override
    public boolean updateBill(Bill bill) {
        return billMapper.updateBill(bill);
    }

    @Override
    public boolean deleteBill(int id) {
        return billMapper.deleteBill(id);
    }

    @Override
    public int getBillByPid(int providerId) {
        return billMapper.getBillByPid(providerId);
    }


}
