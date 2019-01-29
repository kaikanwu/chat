package com.kk.controller;

import com.kk.pojo.Users;
import com.kk.pojo.vo.UsersVO;
import com.kk.service.UserService;
import com.kk.utils.IMoocJSONResult;
import com.kk.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/hello")
    public String test() {
        return "It's ok!";
    }

    @PostMapping("/registerOrLogin")
    public IMoocJSONResult registerOrLogin(@RequestBody Users users) throws Exception {

        System.out.println("get a message !!!!!!!");
        // 0. 判断用户名和密码不为空，任意一个为空则直接返回
        if (StringUtils.isBlank(users.getUsername())||
                StringUtils.isBlank(users.getPassword())){
            return IMoocJSONResult.errorMsg("Username or password can not be empty");

        }
        // 1. 判断用户名是否存在，如果存在就登陆，如果不存在则注册
        boolean usernameIsExist = userService.queryUsernameIsExist(users.getUsername());

        /**
         * 用户结果
         */
        Users userResult = null;
        if (usernameIsExist) {
            //  1.1 查询到用户名已经存在，进行 登陆 判断
            userResult = userService.queryUserForLogin(users.getUsername(), MD5Utils.getMD5Str(users.getPassword()));

            if (userResult == null) {
                return IMoocJSONResult.errorMsg("用户名或者密码不正确！");
            }
        } else {
            //  1.2 注册
            users.setNickname(users.getUsername());
            users.setFaceImage("");
            users.setFaceImageBig("");
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            userResult = userService.saveUser(users);
        }


        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userResult, usersVO);

        // 将 usersVO 返回给前端
        return IMoocJSONResult.ok(usersVO);
    }
}
