package com.lcdt.manage.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lcdt.manage.entity.TNoticeCategory;
import com.lcdt.manage.mapper.TNoticeCategoryMapper;
import com.lcdt.manage.service.TNoticeCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
@Service
public class TNoticeCategoryServiceImpl extends ServiceImpl<TNoticeCategoryMapper,TNoticeCategory> implements TNoticeCategoryService {
    @Autowired
    private TNoticeCategoryMapper categoryMapper;
    @Override
    public TNoticeCategory findByName(String name) {
        Map<String,Object> p = new HashMap<>();
        p.put("category_name",name);
         List<TNoticeCategory> list = this.selectByMap(p);
        if(list!=null&&list.size()>0){
            return  list.get(0);
        }
        return null;
    }

    @Override
    public boolean findExistTNoticeCategory(TNoticeCategory category) {
        Map<String,Object> p = new HashMap<>();
        p.put("category_Name",category.getCategoryName());
        List<TNoticeCategory> list = this.selectByMap(p);

        if(list!=null&&list.size()>0){
            TNoticeCategory category1 =  list.get(0);
            //新增时，已经存在了
            if(category.getCategoryId()==null) {
                return true;
            }
            //修改时，条件id与结果id一样表示同一条记录
            else if(!category1.getCategoryId().equals(category.getCategoryId())) {
                return true;
            }
            else {
                return false;
            }
        }
        //不存在记录返回false
        return false;
    }
}
