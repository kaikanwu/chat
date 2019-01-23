package com.kk.controller;

import com.kk.pojo.Users;
import com.kk.service.UserService;
import com.kk.utils.IMoocJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/registerOrLogin")
    public IMoocJSONResult registerOrLogin(@RequestBody Users users) {

        // 0. 判断用户名和密码不为空，一个为空则直接返回
        if (StringUtils.isBlank(users.getUsername())||
                StringUtils.isBlank(users.getPassword())){
            return IMoocJSONResult.errorMsg("Username or password can not be empty");

        }
        // 1. 判断用户名是否存在，如果存在就登陆，如果不存在则注册
        boolean usernameIsExist = userService.queryUsernameIsExist(users.getUsername());

        if (usernameIsExist) {
            //  登陆

        } else {
            //  注册


        }



        return IMoocJSONResult.ok();
    }
}
