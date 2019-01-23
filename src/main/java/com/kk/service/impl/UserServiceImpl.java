package com.kk.service.impl;

import com.kk.mapper.UsersMapper;
import com.kk.pojo.Users;
import com.kk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public boolean queryUsernameIsExist(String username) {


        Users user = new Users();
        user.setUsername(username);



        Users result = usersMapper.selectOne(user);


        return result != null;
    }

}
