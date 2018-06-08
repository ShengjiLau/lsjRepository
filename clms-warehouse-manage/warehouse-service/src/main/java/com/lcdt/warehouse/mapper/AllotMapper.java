package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.entity.Allot;

import java.util.List;
import java.util.Map;

public interface AllotMapper {
    int deleteByPrimaryKey(Long allotId);

    int insert(Allot record);

    int insertSelective(Allot record);

    Allot selectByPrimaryKey(Long allotId);

    int updateByPrimaryKeySelective(Allot record);

    int updateByPrimaryKey(Allot record);

    List<AllotDto> selectByCondition(Pagination page, Map m);
    /**
     * 删除（is_deleted=1）
     * @param allotId
     * @return
     */
    int updateAllotIsDelete(Long allotId);

    AllotDto selectByAllotId(Long allotId);
}