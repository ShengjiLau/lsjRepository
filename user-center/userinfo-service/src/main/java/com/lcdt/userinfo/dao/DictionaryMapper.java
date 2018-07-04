package com.lcdt.userinfo.dao;


import com.lcdt.userinfo.dto.DictionaryQueryDto;
import com.lcdt.userinfo.model.Dictionary;

import java.util.List;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer dictionaryId);

    int insert(Dictionary record);

    Dictionary selectByPrimaryKey(Integer dictionaryId);

    List<Dictionary> selectAll(DictionaryQueryDto queryDto);

    int updateByPrimaryKey(Dictionary record);
}