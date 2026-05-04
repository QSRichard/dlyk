package com.richard.service;

import com.github.pagehelper.PageInfo;
import com.richard.model.TActivity;
import com.richard.query.ActivityQuery;

public interface ActivityService {

    PageInfo<TActivity> getActivityByPage(Integer pageNum, ActivityQuery ActivityQuery);

    int saveActivity(ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int updateActivity(ActivityQuery activityQuery);
}
