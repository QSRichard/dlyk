package com.richard.mapper;

import com.richard.commons.DataScope;
import com.richard.model.TActivityRemark;
import com.richard.query.ActivityRemarkQuery;

import java.util.List;

public interface TActivityRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);


    @DataScope(tableAlias = "tar", tableField = "create_by")
    List<TActivityRemark> selectActivityRemarkByPage(ActivityRemarkQuery query);
}