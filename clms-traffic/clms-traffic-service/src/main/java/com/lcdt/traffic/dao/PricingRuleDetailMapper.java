package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.PricingRuleDetail;

public interface PricingRuleDetailMapper {
    int deleteByPrimaryKey(Long prdId);

    int insert(PricingRuleDetail record);

    int insertSelective(PricingRuleDetail record);

    PricingRuleDetail selectByPrimaryKey(Long prdId);

    int updateByPrimaryKeySelective(PricingRuleDetail record);

    int updateByPrimaryKey(PricingRuleDetail record);
}