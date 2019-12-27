package com.smbms.dao.bill;

import com.smbms.pojo.Bill;
import com.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    //获取账单列表
/*    public List<Bill> getBillList(@Param("productName") String productName,
                                  @Param("providerId") int providerId,
                                  @Param("isPayment") int isPayment,
                                  @Param("from") int currentPageNo, @Param("pageSize") int pageSize) throws Exception;*/
        public List<Bill> getBillList(@Param("productName") String productName,
                                  @Param("providerId") int providerId,
                                  @Param("isPayment") int isPayment
                                 ) throws Exception;
    //获取记录条数
    public int getBillCount(@Param("productName") String productName,
                            @Param("providerId") int providerId,
                            @Param("isPayment") int isPayment) throws Exception;

    //根据id查看账单信息
    public Bill getBillById(@Param("id") int id);

    //增加账单
    public boolean addBill(Bill bill);

    //修改账单
    public boolean updateBill(Bill bill);

    //删除账单
    public boolean deleteBill(@Param("id") int id);

    int getBillByPid(@Param("providerId") int providerId);
}
