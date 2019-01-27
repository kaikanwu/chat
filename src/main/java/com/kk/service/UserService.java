package com.kk.service;

import com.kk.pojo.Users;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 查询用户是否存在
     * @param username
     * @param password
     * @return
     */
    public Users queryUserForLogin(String username, String password);


    /**
     * 注册一个用户
     * @param users 用户对象
     * @return
     */
    Users saveUser(Users users);
}
