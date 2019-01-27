package com.kk.service.impl;

import com.kk.mapper.UsersMapper;
import com.kk.pojo.Users;
import com.kk.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;


    @Autowired
    private Sid sid;

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {


        Users user = new Users();
        user.setUsername(username);

        Users result = usersMapper.selectOne(user);

        return result != null;
    }




    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();

        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);


        Users result = usersMapper.selectOneByExample(userExample);



        return result;
    }


    /**
     * 注册用户的impl
     * @param user 用户对象
     * @return
     */
    @Override
    public Users saveUser(Users user) {

        // sid 中的 nextshort() 方法会生成16位的唯一的 id
        String userId = sid.nextShort();

        // TODO 为每个用户生成唯一的二维码
        user.setQrcode("");

        user.setId(userId);
        // 最后使用 userMapper 把生成的User 保存到数据库
        usersMapper.insert(user);

        return user;
    }


}
