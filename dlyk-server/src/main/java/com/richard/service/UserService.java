package com.richard.service;

import com.github.pagehelper.PageInfo;
import com.richard.model.TUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserDetailById(Integer id);
}
