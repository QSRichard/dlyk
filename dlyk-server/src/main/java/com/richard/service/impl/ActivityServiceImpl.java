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

    @Override
    public TActivity getActivityById(Integer id) {
        return activityMapper.selectDetailByPrimaryKey(id);
    }

    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity activity = new TActivity();

        // spring 提供的工具类 将对象属性拷贝到另一个对象中（需要 两个对象的 属性名和属性类型相同）
        BeanUtils.copyProperties(activityQuery, activity);


        activity.setEditTime(new Date());

        // 设置更新人ID
        Integer editId = JWTUtils.parseTUserFromJWT(activityQuery.getToken()).getId();
        activity.setEditBy(editId);

        return activityMapper.updateByPrimaryKeySelective(activity);
    }

}
