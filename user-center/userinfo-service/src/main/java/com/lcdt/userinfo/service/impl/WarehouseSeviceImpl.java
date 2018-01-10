package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.WarehousseMapper;
import com.lcdt.userinfo.model.Warehouse;
import com.lcdt.userinfo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/10.
 */
@Service
public class WarehouseSeviceImpl implements WarehouseService {

    @Autowired
    private WarehousseMapper warehousseMapper;


    @Transactional(readOnly = true)
    @Override
    public PageInfo warehouseList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Warehouse> list = warehousseMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }
}
