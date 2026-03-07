package com.richard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richard.constant.Constants;
import com.richard.mapper.TActivityMapper;
import com.richard.model.TActivity;
import com.richard.query.BaseQuery;
import com.richard.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {


    @Resource
    private TActivityMapper activityMapper;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current) {

        // 1 设置PageHelper
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);

        List<TActivity> userList = activityMapper.selectActivityByPage(BaseQuery.builder().build());

        PageInfo<TActivity> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }
}
