package com.smbms.service.role;

import com.smbms.dao.role.RoleMapper;
import com.smbms.dao.user.UserMapper;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;
import com.smbms.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;


    @Override
    public List<Role> getRoleList() throws Exception {
        return roleMapper.getRoleList();
    }

    @Override
    public Role getRoleById(int roleId) {
        return roleMapper.getRoleById(roleId);
    }
}
