package com.lcdt.traffic.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.WaybillItemsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillDao;
import com.lcdt.traffic.model.WaybillItems;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.vo.WaybillVO;
import com.lcdt.traffic.web.dto.WaybillCustListParamsDto;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.traffic.web.dto.WaybillOwnListParamsDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2017/12/20
 */
@Transactional
@Service
public class WaybillServiceImpl implements WaybillService{
    @Autowired
    private WaybillMapper waybillMapper; //运单
    @Autowired
    private WaybillItemsMapper waybillItemsMapper; //运单货物详细
    @Override
    public int addWaybill(WaybillDto waybillDto) {
        int result=0;
        Waybill waybill=new Waybill();
        BeanUtils.copyProperties(waybillDto,waybill);
        //新增运单
        result+=waybillMapper.insert(waybill);
        //运单货物详细
        if(waybillDto.getWaybillItemsDtoList()!=null&&waybillDto.getWaybillItemsDtoList().size()>0){
            List<WaybillItems> waybillItemsList=new ArrayList<WaybillItems>();
            for(int i=0;i<waybillDto.getWaybillItemsDtoList().size();i++){
                WaybillItems waybillItems=new WaybillItems();
                BeanUtils.copyProperties(waybillDto.getWaybillItemsDtoList().get(i),waybillItems);
                waybillItems.setCreateId(waybill.getCreateId());
                waybillItems.setCreateName(waybill.getCreateName());
                waybillItems.setCompanyId(waybill.getCompanyId());
                waybillItems.setWaybillId(waybill.getId());
                waybillItemsList.add(waybillItems);
            }
            //运单货物详细批量插入
            result+=waybillItemsMapper.insertForBatch(waybillItemsList);
        }
        return result;
    }

    @Override
    public int deleteWaybill(Long waybillId, Long companyId) {
        return 0;
    }

    @Override
    public int modifyOwnWaybill(WaybillDto waybillDto) {
        int result=0;
        Waybill waybill=new Waybill();
        BeanUtils.copyProperties(waybillDto,waybill);
        //新增运单
        result+=waybillMapper.updateByPrimaryKeyAndCompanyId(waybill);
        //运单货物详细
        if(waybillDto.getWaybillItemsDtoList()!=null&&waybillDto.getWaybillItemsDtoList().size()>0){
            List<WaybillItems> waybillItemsUpdateList=new ArrayList<WaybillItems>();
            List<WaybillItems> waybillItemsList=new ArrayList<WaybillItems>();
            for(int i=0;i<waybillDto.getWaybillItemsDtoList().size();i++){
                WaybillItems waybillItems=new WaybillItems();
                BeanUtils.copyProperties(waybillDto.getWaybillItemsDtoList().get(i),waybillItems);
                if(waybillItems.getId()>0){
                    waybillItems.setUpdateId(waybill.getUpdateId());
                    waybillItems.setUpdateName(waybill.getUpdateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItemsUpdateList.add(waybillItems);
                }else{
                    waybillItems.setCreateId(waybill.getCreateId());
                    waybillItems.setCreateName(waybill.getCreateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItems.setWaybillId(waybill.getId());
                    waybillItemsList.add(waybillItems);
                }
            }
            if(waybillItemsUpdateList.size()>0){
                //运单货物详细修改
                result+=waybillItemsMapper.updateForBatch(waybillItemsList);
            }
            if(waybillItemsList.size()>0){
                //运单货物详细批量插入
                result+=waybillItemsMapper.insertForBatch(waybillItemsList);
            }
        }
        return result;
    }

    @Override
    public int modifyCustomerWaybill(WaybillDto waybillDto) {
        int result=0;
        Waybill waybill=new Waybill();
        BeanUtils.copyProperties(waybillDto,waybill);
        //新增运单
        result+=waybillMapper.updateByPrimaryKeyAndCarrierCompanyId(waybill);
        //运单货物详细
        if(waybillDto.getWaybillItemsDtoList()!=null&&waybillDto.getWaybillItemsDtoList().size()>0){
            List<WaybillItems> waybillItemsUpdateList=new ArrayList<WaybillItems>();
            List<WaybillItems> waybillItemsList=new ArrayList<WaybillItems>();
            for(int i=0;i<waybillDto.getWaybillItemsDtoList().size();i++){
                WaybillItems waybillItems=new WaybillItems();
                BeanUtils.copyProperties(waybillDto.getWaybillItemsDtoList().get(i),waybillItems);
                if(waybillItems.getId()>0){
                    waybillItems.setUpdateId(waybill.getUpdateId());
                    waybillItems.setUpdateName(waybill.getUpdateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItemsUpdateList.add(waybillItems);
                }else{
                    waybillItems.setCreateId(waybill.getCreateId());
                    waybillItems.setCreateName(waybill.getCreateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItems.setWaybillId(waybill.getId());
                    waybillItemsList.add(waybillItems);
                }
            }
            if(waybillItemsUpdateList.size()>0){
                //运单货物详细修改
                result+=waybillItemsMapper.updateForBatch(waybillItemsList);
            }
            if(waybillItemsList.size()>0){
                //运单货物详细批量插入
                result+=waybillItemsMapper.insertForBatch(waybillItemsList);
            }
        }
        return result;
    }

    @Override
    public WaybillDao queryOwnWaybill(Long waybillId, Long companyId) {
        Waybill waybill=new Waybill();
        waybill.setId(waybillId);
        waybill.setCompanyId(companyId);
        return waybillMapper.selectOwnWaybillByIdAndCompanyId(waybill);
    }

    @Override
    public WaybillDao queryCustomerWaybill(Long waybillId, Long carrierCompanyId) {
        Waybill waybill=new Waybill();
        waybill.setId(waybillId);
        waybill.setCarrierCompanyId(carrierCompanyId);
        return waybillMapper.selectCustomerWaybillByIdAndCarrierCompanyId(waybill);
    }

    @Override
    public PageInfo queryOwnWaybillList(WaybillOwnListParamsDto dto, PageInfo pageInfo) {
        List<WaybillDao> resultList=null;
        PageInfo page=null;
        WaybillVO waybillVO=new WaybillVO();
        BeanUtils.copyProperties(dto,waybillVO);
        waybillVO.setIsDeleted((short)0);
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        resultList=waybillMapper.selectOwnByCondition(waybillVO);
        page=new PageInfo(resultList);
        return page;
    }

    @Override
    public PageInfo queryCustomerWaybillList(WaybillCustListParamsDto dto, PageInfo pageInfo) {
        List<WaybillDao> resultList=null;
        PageInfo page=null;
        WaybillVO waybillVO=new WaybillVO();
        BeanUtils.copyProperties(dto,waybillVO);
        waybillVO.setIsDeleted((short)0);
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        resultList=waybillMapper.selectCustomerByCondition(waybillVO);
        page=new PageInfo(resultList);
        return page;
    }

    @Override
    public int modifyOwnWaybillStatus(Map map) {
        return waybillMapper.updateOwnWaybillStatus(map);
    }

    @Override
    public int modifyCustomerWaybillStatus(Map map) {
        return waybillMapper.updateCustomerWaybillStatus(map);
    }


}
