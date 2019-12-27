package com.smbms.service.user;

import javax.annotation.Resource;

import com.smbms.dao.user.UserMapper;

import org.springframework.stereotype.Service;
import com.smbms.pojo.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;



    //利用shiro组件来认证用户
    @Override
    public User getLoginUser(String userCode) {

        return userMapper.getLoginUser(userCode);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        return userMapper.getUserList(userName, userRole, currentPageNo, pageSize);
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) throws Exception {
        return userMapper.getUserCount(queryUserName, queryUserRole);
    }

    @Override
    public boolean addUser(User user) throws Exception {
        return userMapper.addUser(user);
    }

    @Override
    public User getUserCountByUserCode(String userCode) throws Exception {
        return userMapper.getUserCountByUserCode(userCode);
    }

    @Override
    public User getUserById(int id) throws Exception {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean modify(User user) throws Exception {
        return userMapper.modify(user);
    }

    @Override
    public boolean deleteUserById(int id) throws Exception {
        return userMapper.deleteUserById(id);
    }

    @Override
    public boolean updatePwd(int id, String pwd) throws Exception {
        return userMapper.updatePwd(id, pwd);
    }


}
