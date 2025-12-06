package com.richard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richard.constant.Constants;
import com.richard.mapper.TUserMapper;
import com.richard.model.TUser;
import com.richard.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


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

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {

        // 1 设置PageHelper
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);

        List<TUser> userList = userMapper.selectUserByPage();

        PageInfo<TUser> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public TUser getUserDetailById(Integer id) {
        return userMapper.selectDetailById(id);
    }
}
