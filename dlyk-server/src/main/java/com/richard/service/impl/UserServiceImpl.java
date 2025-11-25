package com.richard.service.impl;

import com.richard.mapper.TUserMapper;
import com.richard.model.TUser;
import com.richard.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Resource
    private TUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TUser user = userMapper.selectByLoginAct(username);

        if (user == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }
        System.out.println(user);
        return user;
    }
}
