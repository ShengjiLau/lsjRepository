package com.lcdt.warehouse.service;

import com.baomidou.mybatisplus.service.IService;
import com.lcdt.warehouse.entity.CostComputeSet;

public interface CostComputeSetService extends IService<CostComputeSet>{
    CostComputeSet updateSet(Long companyId, String set);
}
