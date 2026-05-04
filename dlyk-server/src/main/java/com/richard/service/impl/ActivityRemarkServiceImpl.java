package com.richard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richard.constant.Constants;
import com.richard.mapper.TActivityRemarkMapper;
import com.richard.model.TActivityRemark;
import com.richard.query.ActivityRemarkQuery;
import com.richard.service.ActivityRemarkService;
import com.richard.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {


    @Resource
    private TActivityRemarkMapper tActivityRemarkMapper;

    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {

        TActivityRemark tActivityRemark = new TActivityRemark();

        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);
        tActivityRemark.setCreateTime(new Date());

        Integer id = JWTUtils.parseTUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setCreateBy(id);

        return tActivityRemarkMapper.insert(tActivityRemark);
    }

    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery) {

        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);
        List<TActivityRemark> list = tActivityRemarkMapper.selectActivityRemarkByPage(activityRemarkQuery);
        PageInfo<TActivityRemark> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public TActivityRemark getActivityRemarkById(Integer id) {
        return tActivityRemarkMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery) {

        TActivityRemark tActivityRemark = new TActivityRemark();
        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);
        tActivityRemark.setEditTime(new Date());

        Integer id = JWTUtils.parseTUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setEditBy(id);
        return tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);
    }
}
