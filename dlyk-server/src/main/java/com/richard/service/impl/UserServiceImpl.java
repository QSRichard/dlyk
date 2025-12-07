package com.richard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richard.constant.Constants;
import com.richard.mapper.TUserMapper;
import com.richard.model.TUser;
import com.richard.query.UserQuery;
import com.richard.service.UserService;
import com.richard.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    @Resource
    private TUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

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

    @Override
    public int saveUser(UserQuery userQuery) {
        TUser user = new TUser();

        System.out.println(userQuery);
        // spring 提供的工具类 将对象属性拷贝到另一个对象中（需要 两个对象的 属性名和属性类型相同）
        BeanUtils.copyProperties(userQuery, user);

        // 密码加密
        user.setLoginPwd(passwordEncoder.encode(user.getLoginPwd()));
        user.setCreateTime(new Date());

        // 设置创建人ID
        Integer createId = JWTUtils.parseTUserFromJWT(userQuery.getToken()).getId();
        user.setCreateBy(createId);

        return userMapper.insert(user);
    }


    @Override
    public int updateUser(UserQuery userQuery) {
        TUser user = new TUser();

        System.out.println(userQuery);

        // spring 提供的工具类 将对象属性拷贝到另一个对象中（需要 两个对象的 属性名和属性类型相同）
        BeanUtils.copyProperties(userQuery, user);

        // 重新设置密码
        if (StringUtils.hasText(userQuery.getLoginPwd())) {
            user.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));
        }

        user.setEditTime(new Date());

        // 设置创建人ID
        Integer editId = JWTUtils.parseTUserFromJWT(userQuery.getToken()).getId();
        user.setEditBy(editId);

        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchDeleteUserByIds(List<String> ids) {
        return userMapper.batchDeleteByPrimaryKeys(ids);
    }
}
