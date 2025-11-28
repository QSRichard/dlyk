package com.richard.web;


import com.richard.model.TUser;
import com.richard.result.R;
import com.richard.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 获取登录人信息
    @GetMapping(value = "/api/login/info")
    public R loginInfo(Authentication authentication) {
        TUser user = (TUser) authentication.getPrincipal();
        return R.OK(user);
    }


    // 免登录
    @GetMapping(value = "api/login/free")
    public R freeLogin() {
        return R.OK(200, "Success");
    }

    @GetMapping(value = "/api/users")
    public R userPage(@RequestParam(value = "current", required = false) Integer current) {
        // required 为false 可以不传参数

        if (current == null) {
            current = 1;
        }
        userService.getUserByPage(current);
        return R.OK(200, "Success");
    }
}
