package com.richard.web;


import com.richard.model.TUser;
import com.richard.result.R;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // 获取登录人信息
    @GetMapping(value = "/api/login/info")
    public R loginInfo(Authentication authentication) {
        TUser user = (TUser) authentication.getPrincipal();
        return R.OK(user);
    }

//    // 获取登录人信息
//    @GetMapping(value = "/api/login")
//    public R login(Authentication authentication) {
////        TUser user = (TUser) authentication.getPrincipal();
//        return R.OK(0, "success");
//    }
}
