package com.lcdt.userinfo.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.dto.DictionaryQueryDto;
import com.lcdt.userinfo.model.Dictionary;

import java.util.List;

public interface DictionaryService {

    int deleteByPrimaryKey(Integer dictionaryId);

    int insert(Dictionary record);

    Dictionary selectByPrimaryKey(Integer dictionaryId);

    List<Dictionary> selectAll(DictionaryQueryDto queryDto);

    int updateByPrimaryKey(Dictionary record);
}