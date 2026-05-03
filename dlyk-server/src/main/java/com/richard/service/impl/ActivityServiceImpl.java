package com.richard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richard.constant.Constants;
import com.richard.mapper.TActivityMapper;
import com.richard.model.TActivity;
import com.richard.query.ActivityQuery;
import com.richard.service.ActivityService;
import com.richard.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {


    @Resource
    private TActivityMapper activityMapper;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery) {

        // 1 设置PageHelper
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);

        List<TActivity> userList = activityMapper.selectActivityByPage(activityQuery);

        PageInfo<TActivity> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public int saveActivity(ActivityQuery activityQuery) {

        TActivity activity = new TActivity();
        BeanUtils.copyProperties(activityQuery, activity);
        activity.setCreateTime(new Date());

        Integer loginUserId = JWTUtils.parseTUserFromJWT(activityQuery.getToken()).getId();

        activity.setCreateBy(loginUserId);

        return activityMapper.insertSelective(activity);
    }

}
