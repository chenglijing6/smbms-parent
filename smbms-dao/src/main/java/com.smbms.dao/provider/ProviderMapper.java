package com.smbms.dao.provider;

import com.smbms.pojo.Bill;
import com.smbms.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    //获取账单列表
    public List<Provider> getProviderList();

    //模糊查询列表
    public List<Provider> getProviderListAll(@Param("proName") String proName,
                                             @Param("from") int currentPageNo, @Param("pageSize") int pageSize);

    //记录条数
    public int getProviderCount(@Param("proName") String proName);

    //根据Id查看供应商信息
    public Provider getProviderById(@Param("id") int id);

    //增加供应商
    public boolean addProvider(Provider provider);

    //修改供应商
    public boolean updateProvider(Provider provider);

    //删除供应商
    public boolean deleteprovider(@Param("id") int id);
}
