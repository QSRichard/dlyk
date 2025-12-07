package com.richard.web;


import com.github.pagehelper.PageInfo;
import com.richard.model.TUser;
import com.richard.query.UserQuery;
import com.richard.result.R;
import com.richard.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/api/login/free")
    public R freeLogin() {
        return R.OK(200, "Success");
    }

    @GetMapping(value = "/api/users")
    public R userPage(@RequestParam(value = "current", required = false) Integer current) {
        // required 为false 可以不传参数

        if (current == null) {
            current = 1;
        }
        PageInfo<TUser> userList = userService.getUserByPage(current);

        return R.OK(userList);
    }

    @GetMapping(value = "/api/user/{id}")
    public R getUserDetail(@PathVariable Integer id) {
        TUser user = userService.getUserDetailById(id);
        return R.OK(user);

    }

    @PostMapping(value = "/api/user/create")
    public R addUser(UserQuery userQuery, @RequestHeader(value = "Authorization") String token) {

        userQuery.setToken(token);
        System.out.println("UserQuery=-===============" + userQuery);


        int save = userService.saveUser(userQuery);
        if (save >= 1) {
            return R.OK("SUCCESS");
        }
        return R.Fail("FAIL");
    }
}
