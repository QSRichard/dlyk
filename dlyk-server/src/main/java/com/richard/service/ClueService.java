package com.richard.service;

import com.github.pagehelper.PageInfo;
import com.richard.model.TClue;

public interface ClueService {

    PageInfo<TClue> getClueByPage(Integer current);
}
