package com.smbms.service.bill;


import com.smbms.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillService {
    //获取账单列表
/*    public List<Bill> getBillList(String productName, int providerId, int isPayment, int currentPageNo, int pageSize) throws Exception;*/
   //采用pagehelper
    public List<Bill> getBillList(String productName, int providerId, int isPayment) throws Exception;

    //获取记录条数
    public int getBillCount(String productName, int providerId, int isPayment) throws Exception;

    //根据id查看账单信息
    public Bill getBillById(@Param("id") int id);

    //增加账单
    public boolean addBill(Bill bill);

    //修改账单
    public boolean updateBill(Bill bill);

    //删除账单
    public boolean deleteBill(int id);

    int getBillByPid(int providerId);


}