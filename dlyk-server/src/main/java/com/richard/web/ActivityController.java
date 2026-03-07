package com.richard.web;


import com.github.pagehelper.PageInfo;
import com.richard.model.TActivity;
import com.richard.result.R;
import com.richard.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {


    @Resource
    private ActivityService activityService;

    @GetMapping(value = "/api/activity")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current) {
        // required 为false 可以不传参数

        if (current == null) {
            current = 1;
        }
        PageInfo<TActivity> userList = activityService.getActivityByPage(current);

        return R.OK(userList);
    }

}
