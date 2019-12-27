package com.smbms.service.role;

import com.smbms.pojo.Role;
import com.smbms.pojo.User;

import java.util.List;

public interface RoleService {
    public List<Role> getRoleList() throws Exception;
    Role  getRoleById(int roleId);
}
