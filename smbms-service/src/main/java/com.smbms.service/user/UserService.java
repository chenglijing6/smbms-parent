package com.smbms.service.user;

import com.smbms.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    /**
     * 用户登录验证方法
     */
    User getLoginUser(String userCode);
    //User login(String userCode, String userPassword) throws Exception;

    List<User> getAllUsers();

    /**
     * 通过条件查询-userList
     */
    List<User> getUserList(String userName, int userRole, int currentPageNo,
                           int pageSize) throws Exception;

    /**
     * 获取用户个数
     */
    int getUserCount(String queryUserName, int queryUserRole) throws Exception;

    /**
     * 增加用户信息
     *
     * @return
     */
    boolean addUser(User user) throws Exception;

    /**
     * 验证userCode唯一性
     */
    User getUserCountByUserCode(String userCode) throws Exception;

    /**
     * 通过userId获取user
     */
    User getUserById(int id) throws Exception;

    /**
     * 修改用户信息
     */
    boolean modify(User user) throws Exception;

    /**
     * 通过userId删除user
     */
    boolean deleteUserById(int id) throws Exception;

    /**
     * 修改当前用户密码
     */
    boolean updatePwd(int id, String pwd) throws Exception;

}
