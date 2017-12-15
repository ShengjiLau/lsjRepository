package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.Driver;
import java.util.List;

public interface DriverMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(Driver record);

    Driver selectByPrimaryKey(Long userId);

    List<Driver> selectAll();

    int updateByPrimaryKey(Driver record);
}