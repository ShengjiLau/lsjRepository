package com.lcdt.userinfo.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.dao.DictionaryMapper;
import com.lcdt.userinfo.dto.DictionaryQueryDto;
import com.lcdt.userinfo.model.Dictionary;
import com.lcdt.userinfo.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    DictionaryMapper dictionaryMapper;

    @Override
    public int deleteByPrimaryKey(Integer dictionaryId) {
        return dictionaryMapper.deleteByPrimaryKey(dictionaryId);
    }

    @Override
    public int insert(Dictionary record) {
        return dictionaryMapper.insert(record);
    }

    @Override
    public Dictionary selectByPrimaryKey(Integer dictionaryId) {
        return dictionaryMapper.selectByPrimaryKey(dictionaryId);
    }

    @Override
    public List<Dictionary> selectAll(DictionaryQueryDto queryDto) {
        return dictionaryMapper.selectAll(queryDto);
    }

    @Override
    public int updateByPrimaryKey(Dictionary record) {
        return dictionaryMapper.updateByPrimaryKey(record);
    }
}
