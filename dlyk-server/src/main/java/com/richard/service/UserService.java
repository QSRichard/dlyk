package com.richard.service;

import com.github.pagehelper.PageInfo;
import com.richard.model.TUser;
import com.richard.query.UserQuery;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserDetailById(Integer id);

    int saveUser(UserQuery user);

    int updateUser(UserQuery user);

    int deleteUserById(Integer id);

    int batchDeleteUserByIds(List<String> ids);
}
