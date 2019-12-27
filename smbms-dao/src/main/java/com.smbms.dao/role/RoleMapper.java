package com.smbms.dao.role;

import com.smbms.pojo.Role;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface RoleMapper {
    List<Role> getRoleList() throws Exception;
    Role getRoleById(@Param("roleId")int roleId);
}
