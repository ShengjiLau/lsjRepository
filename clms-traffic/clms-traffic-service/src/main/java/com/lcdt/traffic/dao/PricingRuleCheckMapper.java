package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.PricingRuleCheck;

public interface PricingRuleCheckMapper {
    int deleteByPrimaryKey(Long prcId);

    int insert(PricingRuleCheck record);

    int insertSelective(PricingRuleCheck record);

    PricingRuleCheck selectByPrimaryKey(Long prcId);

    int updateByPrimaryKeySelective(PricingRuleCheck record);

    int updateByPrimaryKey(PricingRuleCheck record);
}