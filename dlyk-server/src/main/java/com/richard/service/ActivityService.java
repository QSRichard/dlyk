package com.richard.service;

import com.github.pagehelper.PageInfo;
import com.richard.model.TActivity;

public interface ActivityService {

    PageInfo<TActivity> getActivityByPage(Integer pageNum);
}
