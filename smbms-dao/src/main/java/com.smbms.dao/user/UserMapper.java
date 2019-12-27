package com.smbms.dao.user;

import org.apache.ibatis.annotations.Param;

import com.smbms.pojo.User;

import java.util.List;

public interface UserMapper {
    /**
     * 通过userCode获取User
     */
    public User getLoginUser(@Param("userCode") String userCode);

    /**
     * 获取所有用户信息
     */
    List<User> getAllUsers();

    /**
     * 通过条件查询-userList
     */


    public List<User> getUserList(@Param("userName") String userName, @Param("userRole") int userRole,
                                  @Param("from") int currentPageNo, @Param("pageSize") int pageSize) throws Exception;

    /**
     * 获取用户个数
     */
    public int getUserCount(@Param("userName") String userName, @Param("userRole") int userRole) throws Exception;

    /**
     * 增加用户信息
     */
    public boolean addUser(User user) throws Exception;

    /**
     * 验证userCode唯一性
     */
    public User getUserCountByUserCode(@Param("userCode") String userCode) throws Exception;

    /**
     * 通过userId获取user
     */
    public User getUserById(@Param("id") int id) throws Exception;

    /**
     * 修改用户信息
     */
    public boolean modify(User user) throws Exception;

    /**
     * 通过userId删除user
     */
    public boolean deleteUserById(@Param("id") int id) throws Exception;

    /**
     * 修改当前用户密码
     */
    public boolean updatePwd(@Param("id") int id, @Param("userPassword") String pwd) throws Exception;


}
