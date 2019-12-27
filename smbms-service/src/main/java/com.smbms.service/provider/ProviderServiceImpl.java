package com.smbms.service.provider;

import com.smbms.dao.provider.ProviderMapper;
import com.smbms.pojo.Provider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Resource
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProviderList() {
        return providerMapper.getProviderList();
    }

    @Override
    public List<Provider> getProviderListAll(String proName, int currentPageNo, int pageSize) {
        return providerMapper.getProviderListAll(proName, currentPageNo, pageSize);
    }

    @Override
    public int getProviderCount(String proName) {

        return providerMapper.getProviderCount(proName);
    }

    @Override
    public Provider getProviderById(int id) {

        return providerMapper.getProviderById(id);
    }

    @Override
    public boolean addProvider(Provider provider) {
        return providerMapper.addProvider(provider);
    }

    @Override
    public boolean updateProvider(Provider provider) {
        return providerMapper.updateProvider(provider);
    }

    @Override
    public boolean deleteprovider(int id) {
        return providerMapper.deleteprovider(id);
    }
}
