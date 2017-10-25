package com.lcdt.items.dao;

import com.lcdt.items.model.CalcUnit;

import java.util.List;

public interface CalcUnitMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_calc_unit
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long unitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_calc_unit
     *
     * @mbg.generated
     */
    int insert(CalcUnit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_calc_unit
     *
     * @mbg.generated
     */
    CalcUnit selectByPrimaryKey(Long unitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_calc_unit
     *
     * @mbg.generated
     */
    List<CalcUnit> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_calc_unit
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CalcUnit record);
}