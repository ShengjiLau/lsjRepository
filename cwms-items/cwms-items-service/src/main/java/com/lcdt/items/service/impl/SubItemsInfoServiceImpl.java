package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.SubItemsInfoMapper;
import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.service.SubItemsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/9
 */
@Transactional
@Service
public class SubItemsInfoServiceImpl implements SubItemsInfoService{

    @Autowired
    private SubItemsInfoMapper subItemsInfoMapper;

    @Override
    public int addSubItemsInfo(SubItemsInfoDto subItemsInfoDto) {
        int result=0;
        try{
            SubItemsInfo subItemsInfo=new SubItemsInfo();
            subItemsInfo.setItemId(subItemsInfo.getItemId());
            subItemsInfo.setImage(subItemsInfoDto.getImage());
            subItemsInfo.setCode(subItemsInfoDto.getCode());
            subItemsInfo.setBarCode(subItemsInfoDto.getBarCode());
            subItemsInfo.setPurchasePrice(subItemsInfoDto.getPurchasePrice());
            subItemsInfo.setWholesalePrice(subItemsInfoDto.getWholesalePrice());
            subItemsInfo.setRetailPrice(subItemsInfoDto.getRetailPrice());
            subItemsInfo.setSpecComb(subItemsInfoDto.getSpecComb());
            subItemsInfo.setCompanyId(subItemsInfoDto.getCompanyId());
            result=subItemsInfoMapper.insert(subItemsInfo);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public int deleteSubItemsInfo(Long subItemId) {
        int result=0;
        try{
            result=subItemsInfoMapper.deleteByPrimaryKey(subItemId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public int modifySubItemsInfo(SubItemsInfoDto subItemsInfoDto) {
        int result=0;
        try{
            SubItemsInfo subItemsInfo=new SubItemsInfo();
            subItemsInfo.setSubItemId(subItemsInfoDto.getSubItemId());
            subItemsInfo.setItemId(subItemsInfo.getItemId());
            subItemsInfo.setImage(subItemsInfoDto.getImage());
            subItemsInfo.setCode(subItemsInfoDto.getCode());
            subItemsInfo.setBarCode(subItemsInfoDto.getBarCode());
            subItemsInfo.setPurchasePrice(subItemsInfoDto.getPurchasePrice());
            subItemsInfo.setWholesalePrice(subItemsInfoDto.getWholesalePrice());
            subItemsInfo.setRetailPrice(subItemsInfoDto.getRetailPrice());
            subItemsInfo.setSpecComb(subItemsInfoDto.getSpecComb());
            subItemsInfo.setCompanyId(subItemsInfoDto.getCompanyId());
            result=subItemsInfoMapper.updateByPrimaryKey(subItemsInfo);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public SubItemsInfo querySubItemsInfoBySubItemId(Long subItemId) {
        SubItemsInfo subItemsInfo=null;
        try{
            subItemsInfo=subItemsInfoMapper.selectByPrimaryKey(subItemId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return subItemsInfo;
        }
    }

    @Override
    public List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId) {
        List<SubItemsInfo> list=null;
        try{
            list=subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    @Override
    public List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId, PageInfo pageInfo) {
        List<SubItemsInfo> list=null;
        try{
            PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
            list=subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    @Override
    public int deleteSubItemsInfoByItemId(Long itemId) {
        int result=0;
        try{
            result=subItemsInfoMapper.deleteSubItemsInfoByItemId(itemId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
