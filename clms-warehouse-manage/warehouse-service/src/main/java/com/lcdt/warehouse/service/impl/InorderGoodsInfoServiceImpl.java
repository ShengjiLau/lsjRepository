package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.mapper.InorderGoodsInfoMapper;
import com.lcdt.warehouse.service.InorderGoodsInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@Service
public class InorderGoodsInfoServiceImpl extends ServiceImpl<InorderGoodsInfoMapper, InorderGoodsInfo> implements InorderGoodsInfoService {

    @Override
    public int deleteGoodsInfo(Long companyId, Long relationId) {
        int result=0;
        InorderGoodsInfo inorderGoodsInfo=new InorderGoodsInfo();
        inorderGoodsInfo.setCompanyId(companyId);
        inorderGoodsInfo.setRelationId(relationId);
        EntityWrapper<InorderGoodsInfo> wrapper=new EntityWrapper<>();
        wrapper.setEntity(inorderGoodsInfo);
        result=baseMapper.delete(wrapper);
        return result;
    }
}
