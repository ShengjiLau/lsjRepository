package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.entity.CostComputeSet;
import com.lcdt.warehouse.mapper.CostComputeSetMapper;
import com.lcdt.warehouse.service.CostComputeSetService;
import org.springframework.stereotype.Service;

@Service
public class CostComputeSetServiceImpl extends ServiceImpl<CostComputeSetMapper,CostComputeSet> implements CostComputeSetService {

    public CostComputeSet updateSet(Long companyId, String set) {

        CostComputeSet computeSet = selectById(companyId);
        if (computeSet == null) {
            CostComputeSet costComputeSet = new CostComputeSet();
            costComputeSet.setCompanyId(companyId);
            costComputeSet.setComputeType(set);
            insert(costComputeSet);
            return costComputeSet;
        }else {
            computeSet.setComputeType(set);
            updateById(computeSet);
            return computeSet;
        }
    }


}
