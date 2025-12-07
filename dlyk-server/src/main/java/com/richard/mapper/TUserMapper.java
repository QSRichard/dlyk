package com.richard.mapper;

import com.richard.commons.DataScope;
import com.richard.model.TUser;
import com.richard.query.BaseQuery;

import java.util.List;

public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByLoginAct(String username);

    @DataScope(tableAlias = "tu", tableField = "id")
    List<TUser> selectUserByPage(BaseQuery query);

    TUser selectDetailById(Integer id);

    int batchDeleteByPrimaryKeys(List<String> idList);
}