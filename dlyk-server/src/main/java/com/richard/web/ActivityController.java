package com.richard.web;


import com.github.pagehelper.PageInfo;
import com.richard.model.TActivity;
import com.richard.query.ActivityQuery;
import com.richard.result.R;
import com.richard.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {


    @Resource
    private ActivityService activityService;

    @GetMapping(value = "/api/activity")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current, ActivityQuery query) {
        // required 为false 可以不传参数

        if (current == null) {
            current = 1;
        }

        System.out.println(query);
        PageInfo<TActivity> userList = activityService.getActivityByPage(current, query);

        return R.OK(userList);
    }

    @PostMapping(value = "api/activity")
    public R addActivity(ActivityQuery query, @RequestHeader(value = "Authorization") String token) {

        query.setToken(token);
        int save = activityService.saveActivity(query);
        return save >= 1 ? R.OK(save) : R.Fail(save);
    }

}
