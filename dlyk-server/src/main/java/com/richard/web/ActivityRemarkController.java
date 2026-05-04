package com.richard.web;


import com.github.pagehelper.PageInfo;
import com.richard.model.TActivityRemark;
import com.richard.query.ActivityRemarkQuery;
import com.richard.result.R;
import com.richard.service.ActivityRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRemarkController {


    @Resource
    private ActivityRemarkService activityService;

    @PostMapping(value = "/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(value = "Authorization") String token) {
        // axios 提交post 请求 提交过来的是json 数据，使用RequestBody注解接收

        activityRemarkQuery.setToken(token);
        int save = activityService.saveActivityRemark(activityRemarkQuery);
        if (save > 0) {
            return R.OK(save);
        }
        return R.Fail(save);
    }

    @GetMapping(value = "/api/activity/remark")
    public R getActivityRemarkPage(@RequestParam(value = "current", required = false) Integer current, @RequestParam(value = "activityId", required = false) Integer activityId) {
        if (current == null) {
            current = 1;
        }

        ActivityRemarkQuery activityRemarkQuery = new ActivityRemarkQuery();
        activityRemarkQuery.setActivityId(activityId);

        PageInfo<TActivityRemark> pageInfo = activityService.getActivityRemarkByPage(current, activityRemarkQuery);
        return R.OK(pageInfo);
    }

    @GetMapping(value = "/api/activity/remark/{id}")
    public R getActivityRemark(@PathVariable(value = "id") Integer id) {
        TActivityRemark tActivityRemark = activityService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }


    @PutMapping(value = "/api/activity/remark")
    public R editActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(value = "Authorization") String token) {
        // axios 提交post 请求 提交过来的是json 数据，使用RequestBody注解接收

        activityRemarkQuery.setToken(token);
        System.out.println("+++++++++++++++++" + activityRemarkQuery);
        int update = activityService.updateActivityRemark(activityRemarkQuery);
        if (update > 0) {
            return R.OK(update);
        }
        return R.Fail(update);
    }
}
