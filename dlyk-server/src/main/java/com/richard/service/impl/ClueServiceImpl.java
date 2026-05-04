package com.richard.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richard.constant.Constants;
import com.richard.mapper.TClueMapper;
import com.richard.model.TClue;
import com.richard.query.BaseQuery;
import com.richard.service.ClueService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClueServiceImpl implements ClueService {


    @Resource
    private TClueMapper tClueMapper;

    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {


        System.out.println("============current=========" + current);
        PageHelper.startPage(current, Constants.DEFAULT_PAGE_SIZE);
        List<TClue> list = tClueMapper.selectClueByPage(BaseQuery.builder().build());
        PageInfo<TClue> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
