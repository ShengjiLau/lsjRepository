package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.dao.ConversionRelMapper;
import com.lcdt.items.dto.ConversionRelDto;
import com.lcdt.items.model.ConversionRel;
import com.lcdt.items.service.ConversionRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyqishan on 06/11/2017
 */
@Transactional
@Service
public class ConversionRelServiceImpl implements ConversionRelService{

    @Autowired
    private ConversionRelMapper conversionRelMapper;

    @Override
    public ConversionRel addConversionRel(ConversionRelDto conversionRelDto) {
        ConversionRel conversionRel=null;
        try{
            conversionRel=new ConversionRel();
            //最小单位
            conversionRel.setUnitId(conversionRelDto.getUnitId());
            conversionRel.setUnitName(conversionRelDto.getUnitName());
            //多单位 1
            conversionRel.setUnitId1(conversionRelDto.getUnitId1());
            conversionRel.setUnitName1(conversionRelDto.getUnitName1());
            conversionRel.setData1(conversionRelDto.getData1());
            //多单位 2
            conversionRel.setUnitId2(conversionRelDto.getUnitId2());
            conversionRel.setUnitName2(conversionRelDto.getUnitName2());
            conversionRel.setData2(conversionRelDto.getData2());

            conversionRelMapper.insert(conversionRel);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return conversionRel;
        }
    }

    @Override
    public int deleteConversionRel(Long converId) {
        int result=0;
        try{
            result=conversionRelMapper.deleteByPrimaryKey(converId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public int modifyConversionRel(ConversionRelDto conversionRelDto) {
        int result=0;
        try{
            ConversionRel conversionRel=new ConversionRel();
            //最小单位
            conversionRel.setUnitId(conversionRelDto.getUnitId());
            conversionRel.setUnitName(conversionRelDto.getUnitName());
            //多单位 1
            conversionRel.setUnitId1(conversionRelDto.getUnitId1());
            conversionRel.setUnitName1(conversionRelDto.getUnitName1());
            conversionRel.setData1(conversionRelDto.getData1());
            //多单位 2
            conversionRel.setUnitId2(conversionRelDto.getUnitId2());
            conversionRel.setUnitName2(conversionRelDto.getUnitName2());
            conversionRel.setData2(conversionRelDto.getData2());

            result=conversionRelMapper.insert(conversionRel);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public ConversionRel queryConversionRel(Long converId) {
        ConversionRel conversionRel=null;
        try{
            conversionRel=conversionRelMapper.selectByPrimaryKey(converId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return conversionRel;
        }
    }
}
