package com.richard.service;

import com.github.pagehelper.PageInfo;
import com.richard.model.TActivityRemark;
import com.richard.query.ActivityRemarkQuery;

public interface ActivityRemarkService {

    int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery);


    TActivityRemark getActivityRemarkById(Integer id);
    

    int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery);
}
